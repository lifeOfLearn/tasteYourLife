let searchPage = 0
let searchWord
function htmlSubInclude(){
    $('.nav').load( subViews + '/navbar.html', () => {
        ajaxGet('customer', getRunNext);
        $(window).resize(windowResizeLoginHandler)
        $('.listClick').on('mousedown',function(){$('.listMenu').toggle()})
        $('.listProduct').click(getProcutCategoryAndProductPage)
        $('.listCart').click(productToLogin)
        $('.searchBtn').click(searchHandler)
        $('.homeImg').click(homeHandler)
        $('.searchInput').on('keydown', function(e) {
            if (e.key === 'Enter') {
                searchHandler();
            }
        });
    });
}
function homeHandler(){
    window.location.href = '/tyl'
}
function navScrollHandler(){
    var scrollTop = $(window).scrollTop();  
    if (scrollTop < 10){
        $('.navbar').removeClass('opacity-3');
    } else {
        $('.navbar').addClass('opacity-3');
    }
}

function navbarLoadLoginHandler(html){
    $.get(html, loginEnvHandler);
}
    
function getRunNext(response) {
    var code = response['code']
    let html;
    let css;
    let js;
    if (code == 200) {
        ajaxGet('member/subviews/customer.html',function(res){
            $('.nav').html(res);
            $('.searchBtn').click(searchHandler)
            $.getScript('member/script/customer.js')
                .done(function(){
                    customerInit(response['user']);
                });
            $.get('member/css/customer.css', function(css){
                let cusomterlinkCss = $('<style></style>').text(css);
                $('.headStyle').append(cusomterlinkCss);
            });
        });
    } else {
        html = 'subviews/guest.html'
        navbarLoadLoginHandler(html)
    }
    //TODO

}
function listBackgroundMosedownHandler(){
    $('.listBlockBackground').off('mousedown')
    $('.captchaImg').css('background-image', '');
    $('.captchaRefresh').off('mousedown');
    $('.listBlock').hide();
    $('.listBlockBackground').hide();
    $('.inputPasswd').off('mousedown').off('mouseup')
    $('.listMenu').toggle();
    windowResizeLoginHandler();
}    
    
function hasLogin(response){
    let responseId = response['userId'];
    let responseName = response['userName'];
    let responseHtml = response['userHtml'];
    let responseJS = response['userJs'];
    let responseCss = response['userCss'];
    let getView = subViews + "/login.html";
    if (responseHtml == getView)
        navbarLoadLoginHandler(responseHtml);
    else {
        $.get(responseHtml, function(html) {
            $('.listClick').off('mousedown');
            $('.navbar').html(html);
            if (responseJS)
                $.getScript(responseJS)
                    .done(function(){
                        customerInit(responseName);
                    })
                    .fail(function(){
                    });
            if(responseCss)
                $.get(responseCss, function(css){

                    let cusomterlinkCss = $('<style></style>').text(css);
                    $('.headStyle').append(cusomterlinkCss);
            });
        });
    }
}
function searchHandler(){
    if (!reNewPage) {
        reNewPage = true
        $('article').html('').append("<div id='searchNum' class='flex flexRow nform center a-center mt-16 textCentet'></div>")
    }else {
        removeSearchPage()
        $('article').append("<div id='searchNum' class='flex flexRow nform center a-center mt-16 textCentet'></div>")
    }
        let search = $('.searchInput').val().trim()
        if (search !== '') {
            searchPage = 0
            searchWord = search
            getSearchPage()
        }
        $('.searchInput').val('')
        //TODO show 不可為空
}
function getSearchPage(){
    isBusy = true
    ajaxGet(`product/search/${searchWord}?page=` + searchPage,showSearch)
}
function showSearch(response){
    if(response && response.length > 0){
        $('#searchNum').before(`<div id='searchContainer${searchPage}' class='searchPage showPage nform flex flexWrap center a-center textCenter none' data-search=${searchPage}></div>`)
        let superPath = $(`#searchContainer${searchPage}`)
        createData(response,superPath, 'pageS')
        $('#searchNum').append(`<div id='searchPage${searchPage}' class='searchPageNum btn choose ml-8' data-search=${searchPage}>${searchPage + 1}</div>`)
                     .removeClass('none')
        if (searchPage == 0){
            hidePage()
            superPath.removeClass('none')
            $('#searchPage0').addClass('bg-c')
       }
       $(`#searchPage${searchPage}`).click(changeSearchPage)
        searchPage++
        isBusy = false
        getSearchPage()
    } else {
        searchPage = 0
        isBusy = false
    }
}
function changeSearchPage(){
    let e = $(this)
    let thePage = e.data('search')
    $('.searchPageNum').removeClass('bg-c')
    e.addClass('bg-c')
    $('.searchPage').addClass('none')
    $(`#searchContainer${thePage}`).removeClass('none')
    getProductScollTop();
}
function removeSearchPage(){
    if ($('.searchPage').length > 0) {
        $('.searchPage').remove();
    }
    if ($('#searchNum').length > 0) {
        $('#searchNum').remove();
    }
}
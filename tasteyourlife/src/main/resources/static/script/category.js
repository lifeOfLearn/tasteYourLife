let productPage = 0
let theCateid
let catePage = {}
function getProcutCategoryAndProductPage(){
    reNewPage = true
    getProcutCategory()
    getProductPage()
    $('.listProduct').off('click')
    $('article').html('').append("<div id='pageNum' class='flex flexRow nform center a-center mt-16 textCentet'></div>")
}

function getProcutCategory(){
    ajaxGet('product/category',showAllCategory)
}

function getProductPage(){
    if (!isBusy) {
        isBusy = true        
        if (productPage != -1)
            ajaxGet('product/page/' + productPage, showProductPage)
        else 
            isBusy = false
    }
}

function showAllCategory(response){
    $('article').prepend(`<div id='cateContainer' class='form flex flexWrap center textCenter'></div>`)
    let cateContainer = $('#cateContainer')
    for (let cateData of response) {
        cateContainer.append(`
            <div class='cateData btn choose br-10 fs-24 fw-10 pointer' data-id=${cateData['cid']}>
                <div class='cname' data-name=${cateData['cname']}>${cateData['cname']}</div>
            </div>
        `)
    }
    $('.cateData').click(clickTheCategory);
}
function clickTheCategory(){
    theCateid = $(this).data('id')
    if (catePage[theCateid] == -1){
        getCatePage0()
    } else {
        getTheCategory()
    }
}
function getTheCategory(){
    if(!isBusy) {
        isBusy = true
        if (!catePage.hasOwnProperty(theCateid)){
            catePage[theCateid] = 0
            $('article').append(`<div id='cateNum${theCateid}' class='cateNum flex flexRow nform center a-center mt-16 textCentet none'></div>`)
        }
        if (catePage[theCateid] != -1)
            ajaxGet(`product/category/${theCateid}?page=${catePage[theCateid]}`,showCategoryProduct)
        else 
            isBusy = false
    }
}
function showCategoryProduct(response){
    if (response && response.length > 0) {
        let categoryPage = catePage[theCateid]
        let pageId = `catePageContainer_${theCateid}_${categoryPage}`
        let pageClass = `catePage${theCateid} catePage`
        let numId = `catePage_${theCateid}_${categoryPage}`
        let numClass = `catePageNum${theCateid} catePageNum`
        $(`#cateNum${theCateid}`).before(`<div id='${pageId}' class='${pageClass} showPage nform flex flexWrap center a-center textCenter none' date-cate=${theCateid} data-page=${categoryPage}></div>`)
        let superPath = $(`#${pageId}`)
        createData(response,superPath, `catePageP_${theCateid} catePageP`)
        $(`#cateNum${theCateid}`).append(`<div id='${numId}' class='${numClass} btn choose ml-8' data-cate=${theCateid} data-page=${categoryPage}>${categoryPage + 1}</div>`)
                     .removeClass('none')
        $(`#${numId}`).click(changecatePage)
        if (categoryPage == 0) {
            removeSearchPage()
            hidePage()
            superPath.removeClass('none')
            $(`#cateNum${theCateid}`).removeClass('none')
            $(`#${numId}`).addClass('bg-c')
            // $(`.cateData[data-id="${categoryPage}"]`).off('click').click(getCatePage0)
        }
        catePage[theCateid] = categoryPage + 1
        isBusy = false
        getTheCategory()
    } else {
        let theValue = catePage[theCateid]
        if (theValue == 0){
            noHasProduct();
        }
        catePage[theCateid] = -1
        isBusy = false
    }
}
function showProductPage(response){
    if(response && response.length > 0){
        $('#pageNum').before(`<div id='pageContainer${productPage}' class='page showPage nform flex flexWrap center a-center textCenter none' data-page=${productPage}></div>`)
        let superPath = $(`#pageContainer${productPage}`)
        createData(response,superPath, 'pageP')
        $('#pageNum').append(`<div id='page${productPage}' class='pageNum btn choose ml-8' data-page=${productPage}>${productPage + 1}</div>`)
                     .removeClass('none')
        if (productPage == 0){
            removeSearchPage()
            hidePage()
            superPath.removeClass('none')
            $('#pageNum').remove('none')
            $('#page0').addClass('bg-c')
            $('.listProduct').click(getPage0)
        }
        $(`#page${productPage}`).click(changePage)
        productPage++
        isBusy = false
        getProductPage()
    } else {
        productPage = -1
        isBusy = false
    }
}
function changecatePage(){
    let e = $(this)
    let thePage = e.data('page')
    let theCate = e.data('cate')
    $('.catePageNum').removeClass('bg-c')
    e.addClass('bg-c')
    $(`.catePage${theCate}`).addClass('none')
    $(`#catePageContainer_${theCate}_${thePage}`).removeClass('none')
    getProductScollTop();
}
function changePage(){
    let e = $(this)
    let thePage = e.data('page')
    $('.pageNum').removeClass('bg-c')
    e.addClass('bg-c')
    $('.page').addClass('none')
    $(`#pageContainer${thePage}`).removeClass('none')
    getProductScollTop();
}
function getCatePage0(){
    removeSearchPage()
    hidePage()
    $(`#catePageContainer_${theCateid}_0`).removeClass('none')
    $(`#cateNum${theCateid}`).removeClass('none')
    $(`#catePage_${theCateid}_0`).addClass('bg-c')
    getProductScollTop();
}
function getPage0(){
    removeSearchPage()
    if (productPage != -1){
        getProductPage()
    }
    hidePage()
    $('#pageContainer0').removeClass('none')
    $('#pageNum').removeClass('none')
    $('#page0').addClass('bg-c')
    getProductScollTop();
}
function hidePage(){
    $('.page').addClass('none')
    $('.catePage').addClass('none')
    $('#pageNum').addClass('none')
    $('.cateNum').addClass('none')
    $('.catePageNum').removeClass('bg-c')
    $('.pageNum').removeClass('bg-c')
}

function getProductScollTop() {
    $('html, body').animate({ scrollTop: '36px' }, 500)
}

function noHasProduct() {
    removeSearchPage()
    let categoryPage = catePage[theCateid]
    let pageId = `catePageContainer_${theCateid}_${categoryPage}`
    let pageClass = `catePage${theCateid} catePage`
    $(`#cateNum${theCateid}`).before(`<div id='${pageId}' class='${pageClass} showPage nform flex flexWrap center a-center textCenter none' date-cate=${theCateid} data-page=${categoryPage}></div>`)
    $(`#${pageId}`).html('<div class="textCenter" style="font-size:10rem;">暫無產品</div>')
    getCatePage0()
}
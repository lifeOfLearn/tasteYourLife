let subViews = 'subviews';
let captchaUrl = 'captcha/image?new=';
let errorFormGroups;
let resizeEvnetWidth = 540;
let isBusy = false
let reNewPage = false
function ajaxGet(url, success){
console.log("ajaxGET:" + url);
    $.ajax({
            url: url,
            method: 'GET',
            success: function(response){
                console.log(response)
                success(response)
            },
            error: function(response){
                console.log(response)
            }
    });
}

function ajaxPost(url,contextType,body,success,fail){
console.log("ajaxPOST:" + url);
    $.ajax({
        url: url,
        headers: {'Content-Type': contextType},
        method: 'POST',
        data: JSON.stringify(body),
        success: function(response){
            console.log(response)
            success(response)
        },
        error: function(response){
            console.log(response)
        }
    });
}

function windowResizeLoginHandler(){
console.log("in Resize")
    let windowWidth =  $(window).width();
    $('.listBlock').css('marginLeft',(windowWidth - $('.listBlock').outerWidth()) / 2);
    $('.listClick').off('mousedown');
    if (windowWidth <= resizeEvnetWidth) {
        $('.listClick').on('mousedown',()=>$('.listMenu').toggle())
        $('.listMenu').css('display', '')
    } else {
        $('.listMenu').css('display', '')
    }
    if($('.showBlock')) {
        let left = ($(window).width() - $('.showBlock').width()) / 2;
        if (left < 0) left = 0;
        $('.showBlock').addClass('z-14').css({'left' : left + 'px', 'display' : 'block'})
    }
}

function blockScrollHandler(){
console.log('in Scroll')
    let scrollTop = $(this).scrollTop();
    let docH = $(document).height();
    let blockH = $('.listBlock').height();
    // docH : block = wS : bS => bS * docH = ws * block
    let blockScrollTop = -1.1 * scrollTop * (blockH / docH)
    $('.listBlock').css("top", blockScrollTop);
}

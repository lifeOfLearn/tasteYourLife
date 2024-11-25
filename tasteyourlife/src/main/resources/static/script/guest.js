
function loginEnvHandler(html){
    $('.listBlock').html(html);
    $('.listClick').off('mousedown');
    // $(window).off('resize');
    windowResizeLoginHandler();
    // $(window).resize(windowResizeLoginHandler)
    //          .scroll(blockScrollHandler);
    $('.listLogin').off('mousedown').on('mousedown', loginMousedownHandler);
    $('.registerBtn').on('mousedown', showRegister);
    $('.forgetBtn').on('mousedown', showForget);
    $('.loginBtn').on('mousedown', showLogin);
}

function showRegister(){
    scrollAll()
    $('.login').addClass('none')
    $('.register').removeClass('none')
    $('.forget').addClass('none')
    $('.loginBtn').removeClass('none')
    $('.registerBtn').addClass('none')
    $('.forgetBtn').removeClass('none')
}

function showForget(){
    scrollAll()
    $('.login').addClass('none')
    $('.register').addClass('none')
    $('.forget').removeClass('none')
    $('.loginBtn').removeClass('none')
    $('.registerBtn').removeClass('none')
    $('.forgetBtn').addClass('none')
}

function showLogin(){
    scrollAll()
    $('.login').removeClass('none')
    $('.register').addClass('none')
    $('.forget').addClass('none')
    $('.loginBtn').addClass('none')
    $('.registerBtn').removeClass('none')
    $('.forgetBtn').removeClass('none')
}

function loginMousedownHandler(){
    $('.errorForm').text('')
    captchaImageRefresh();
    $('.captchaRefresh').off('mousedown').off('mouseup')
        .on('mousedown', function(){
            $(this).css('box-shadow','inset 0 4px 5px rgba(0, 0, 0, 0.5)');
            captchaImageRefresh();
        })
        .on('mouseup', function(){
            $(this).css('box-shadow','');
        });
    
    $('.listBlock').css({'display':'flex'});
    $('.listBlockBackground').show()
        .on('mousedown',listBackgroundMosedownHandler);
    $('.login').submit(loginSubmitHandler);
    $('.register').submit(registerSubmitHandler);
    $('.forget').submit(forgetSubmitHandler);
    $('.inputPasswd').on('mousedown',showPasswd).on('mouseup',hidePasswd)
}

function loginSubmitHandler(e){
    e.preventDefault();
    errorFormGroups = $('.login .errorForm');
    let ajaxRequest = true;
    let loginUrl = 'customer/login';
    let formData = new FormData(e.target);
    let loginJson = {};
    errorFormGroups.text("");
    formData.forEach((value, key) => {
        value = value.trim();
        if(value){
            loginJson[key] = value;
        }else {
            ajaxRequest = false;
            let errorDiv = $('.login .errorForm[data-error-' + key +']');
            errorDiv.text(errorDiv.data('error-' + key));
        }
    });
    if (ajaxRequest) {
        ajaxPost(loginUrl, 'application/json', loginJson, cehckLoginResponse);
    }
}
      
function registerSubmitHandler(e) {
    e.preventDefault();
    errorFormGroups = $('.register .errorForm');
    let ajaxRequest = true;
    let formData = new FormData(e.target);
    let data = {};
    errorFormGroups.text("");
    formData.forEach((value, key) => {
        value = value.trim();
        if(value){
            data[key] = value;
        }else {
            if (key != 'address') {
                ajaxRequest = false;
                let errorDiv = $('.register .errorForm[data-error-' + key +']');
                errorDiv.text(errorDiv.data('error-' + key));
            }
        }
    });
    if (ajaxRequest) {
        ajaxPost('customer/register', 'application/json', data, checkRegister);
    } else {
        scrollAll()
    }
}
function forgetSubmitHandler(e){
    e.preventDefault();
    errorFormGroups = $('.forget .errorForm');
    let formData = new FormData(e.target);
    let ajaxRequest = true;
    let forgetUrl = 'customer/forget';
    let forgetJson = {};
    errorFormGroups.text("");
    formData.forEach((value, key) => {
        value = value.trim();
        if(value){
            forgetJson[key] = value;
        }else {
            ajaxRequest = false;
            let errorDiv = $('.forget .errorForm[data-error-' + key +']');
            errorDiv.text(errorDiv.data('error-' + key));
        }
    });
    if (ajaxRequest) {
        ajaxPost(forgetUrl, 'application/json', forgetJson, function(response){
            let responseCode = response['code']
            if (responseCode == 200) {
                $('.forget input[name=captcha]').val('')
                errorFormGroups.eq(2).css('color','blue').text("請至信箱中確認郵件");
                setTimeout(function(){
                    listBackgroundMosedownHandler();
                    errorFormGroups.eq(2).css('color','');
                },3000);
            } else {
                let errMsg = response.error
                let imageCss = 'background-image';
                let imagePng = 'url(data:image/png;base64,';
                let inputForm = $('.forget input');
                errorFormGroups.eq(0).text(errMsg.user);
                errorFormGroups.eq(1).text(errMsg.captcha);
                errorFormGroups.eq(2).text(errMsg.other);
                inputForm.filter('[name=captcha]').val('');
                $('.captchaImg').fadeOut(300, function(){
                    $(this).css(imageCss, imagePng + errMsg.reCaptcha + ')').fadeIn(200);
                });
            }
        });

    }
}
function checkRegister(response){
    let responseCode = response['code']
    if (responseCode == 200) {
        RegisterResponseOk(response);
    } else {
        scrollAll()
        RegisterResponseFail(response.error);
    }
}
function cehckLoginResponse(response){
    let responseCode = response['code']
    if (responseCode == 200) {
        loginResponseOK(response);
    } else {
        loginResponseFail(response.error);
    }
}
function RegisterResponseOk(response) {
scrollAll()
$('.listBlock').html('<h1 class="registerOK textCenter"><span>註冊成功，</span><br><span>請至信箱確認驗證信<span></h1>')
}
function loginResponseOK(response) {
    $('.listCart').off()
    $('.listClick').off('mousedown');
    loginOKsubAnime(response.user);
}
function RegisterResponseFail(errMsg){
    $(`.register .errorForm[data-error-name]`).text(errMsg.name)
    $(`.register .errorForm[data-error-phone]`).text(errMsg.phone)
    $(`.register .errorForm[data-error-email]`).text(errMsg.email)
    $(`.register .errorForm[data-error-rocid]`).text(errMsg.rocid)
    $(`.register .errorForm[data-error-passwd]`).text(errMsg.passwd)
    $(`.register .errorForm[data-error-passwd2]`).text(errMsg.passwd2)
    $(`.register .errorForm[data-error-birthday]`).text(errMsg.date)
    $(`.register .errorForm[data-error-captcha]`).text(errMsg.captcha)
    $(`.register .errorForm[data-error-other]`).text(errMsg.other)

    $('.register input').filter('[name=password],[name=password2],[name=captcha]').val('');
    $('.captchaImg').fadeOut(300, function(){
        $(this).css('background-image', 'url(data:image/png;base64,' + errMsg.reCaptcha + ')').fadeIn(200)});
}
function loginResponseFail(errMsg) {
    let imageCss = 'background-image';
    let imagePng = 'url(data:image/png;base64,';
    let inputForm = $('.login input');	
    errorFormGroups.eq(0).text(errMsg.user);
    errorFormGroups.eq(1).text(errMsg.passwd);
    errorFormGroups.eq(2).text(errMsg.captcha);
    errorFormGroups.eq(3).text(errMsg.other);
    inputForm.filter('[name=passwd],[name=captcha]').val('');
    $('.captchaImg').fadeOut(300, function(){
        $(this).css(imageCss, imagePng + errMsg.reCaptcha + ')').fadeIn(200)});
}
   

//TODO 過場
function loginOKsubAnime(name){
    let divTmpName = `<div>歡迎 ~ ${name}</div>`;
    $(".listBlock").addClass('textCenter').empty().append(divTmpName);
    timeoutId = setTimeout(function(){
        listBackgroundMosedownHandler();
        ajaxGet('member/subviews/customer.html',function(res){
            $('.nav').html(res);
            $.getScript('member/script/customer.js')
                .done(function(){
                    customerInit(name);
                });
            $.get('member/css/customer.css', function(css){
                let cusomterlinkCss = $('<style></style>').text(css);
                $('.headStyle').append(cusomterlinkCss);
            });
        });
    },500);
}
function captchaImageRefresh(){
("captchaImageRefresh");
    let theCaptchaUrl  = captchaUrl + new Date().getTime();
    $('.captchaImg').css('background-image', 'url(' + theCaptchaUrl + ')');
}

function showPasswd() {
    $('.password').attr('type','text')
}
function hidePasswd() {
    $('.password').attr('type', 'password')
}
function scrollAll(){
    $('.listBlock').css("top", 0);
    $(window).scrollTop(0);
}
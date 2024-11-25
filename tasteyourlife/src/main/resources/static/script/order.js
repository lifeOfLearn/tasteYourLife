let localStorageId = 0
let showUpdate = false
function addCart(){
    $('.showBlock').find('#addcartP').click(addCartHandler)
    $('.showBlock').find('#buyP').click(getCartHandler)
}
function getCartHandler(){
    let addCart = {'qty' : getQty(), 'sid' : getSid()}
    ajaxPost('order/cart/add', 'application/json', addCart, getCartResponse)
}
function addCartHandler(){
    let addCart = {'qty' : getQty(), 'sid' : getSid()}
    ajaxPost('order/cart/add', 'application/json', addCart, addCartResponse)
}
function showCartResponse(response){
    $('.showBlock').html('').addClass('h-auto').append(`<form class='m-16'><fieldset class='textCenter'><legend class='pb-8 fs-48'>購物車</legend><table class='nform w-100'><thead><tr><th></th><th>品名</th><th>加購</th><th>單價</th><th colspan="3">數量</th><th>小計</th><th></th></tr></thead><tbody class="tbody"></tbody><tfoot><tr><td></td><td></td><td></td><td></td><td></td><td colspan='2'>合計:</td><td><span id="totalPrice" class='red'></span><span> 元</span></td><td></td></tr><tr><td></td><td></td><td></td><td></td><td></td><td><div class='btn choose cartChangeBtn mt-8'>修改</div></td><td class='hidden updateOk red' colspan='2'>修改成功</td><td class='pl-16'><div class='btn choose checkBtn mt-8'>結帳</div></td></tr></tfoot></table></fieldset></form>`)
    if (response.length == 0){
        cartNull()
        return
    }
    let productIndex = 0
    let totalPrice = 0
    for (var responseData of response) {
        let responseAdd = responseData['productPrice'][0]
        let showSize = ""
        if (responseAdd['size']){
            showSize = `(${responseAdd['size']})`
        }
        let qtyNumber = responseData['qty']
        let priceNumber = responseAdd['price']
        let thePrice = qtyNumber * priceNumber
        totalPrice = totalPrice + thePrice
        $('.tbody').append(`
            <tr id="product${productIndex}" class='productSup'>
                <td class="tableImg icon" data-data="${responseData['imagePath']}" style="background-image: url(${responseData['imagePath'].replace(/([^\/]+)$/, 'icon/$1')})"></td>
                <td class="tableName" data-data="${responseData['pname']}" data-data2="${showSize}">${responseData['pname']}${showSize}</td>
                <td class="tableAdd" data-data="${responseAdd['aname']}">${responseAdd['aname']}</td>
                <td class="tablePrice" data-data="${priceNumber}"><span class='changePrice red'>${priceNumber}</span><span> 元</span></td>
                <td class="qtySup" colspan='3'>
                    <div class='flex center a-start'>
                    <div class="pCalc pDown w-px-32"></div>
                    <div class='ml-8'><input class="qty w-px-48" type="number" name="qty" min="1" value="${qtyNumber}" data-data="${responseAdd['sid']}" data-sale="${priceNumber}"></div>
                    <div class="pCalc pUp w-px-32 ml-8"></div>
                </td>
                <td class="subPrice" data-data=""><span class='changeSubPrice red'>${thePrice}</span><span> 元</span></td>
                <td class="dataRemove w-px-48 pointer pl-16"><span class="tableSid pCalc garbage"></span></td>
            </tr>
        `)
    }
    $('#totalPrice').text(totalPrice)
    $('.pCalc').click(totalChangeHandler)
    $('.qty').blur(orderCalcTotal)
    $('.cartChangeBtn').click(updateCartHandler)
    $('.checkBtn').click(getOrderHandler)
    if (showUpdate){
        $('.updateOk').removeClass('hidden')
        setTimeout( function() {
            $('.updateOk').addClass('hidden')
        }, 1000)
        showUpdate = false
    }
}
function updateCartHandler(){
    let updateList = []
    $('.qty').each(function(){
        let e = $(this)
        let data = {
            'sid' : e.data('data'),
            'qty' :e.val()
        }
        updateList.push(data)
    })
    ajaxPost('order/cart', 'application/json', updateList, updateCartAnimate)
}



function getOrderHandler(){
    let updateList = []
    $('.qty').each(function(){
        let e = $(this)
        let data = {
            'sid' : e.data('data'),
            'qty' :e.val()
        }
        updateList.push(data)
    })
    ajaxPost('order/cart', 'application/json', updateList, function(response){
        if (response['code'] == 200) {
            ajaxGet(`order/cart`, getOrderInfo)
        } else {
            productToLogin()
        }
    });
}

function getOrderInfo(response){
    $('.showBlock').html('').addClass('h-auto').append(`<form class='m-16'><fieldset class='orderBlock textCenter'><legend class="pb-8 fs-48">訂購資料</legend><fieldset class='textCenter m-0-auto'><legend class='fs-24'>購物車</legend><table class='nform w-100'><thead><tr><th></th><th>品名</th><th>加購</th><th>單價</th><th>數量</th><th>小計</th></tr></thead><tbody class="tbody"></tbody><tfoot><tr><td></td><td></td><td></td><td></td><td>合計:</td><td><span id="totalPrice" class='red'></span><span> 元</span></td></tr></tfoot></table></fieldset></fieldset></form>`)
    if (response.length == 0){
        cartNull()
        return
    }
    let productIndex = 0
    let totalPrice = 0
    for (var responseData of response) {
        let responseAdd = responseData['productPrice'][0]
        let showSize = ""
        if (responseAdd['size']){
            showSize = `(${responseAdd['size']})`
        }
        let qtyNumber = responseData['qty']
        let priceNumber = responseAdd['price']
        let thePrice = qtyNumber * priceNumber
        totalPrice = totalPrice + thePrice
        $('.tbody').append(`
            <tr id="product${productIndex}" class='productSup'>
                <td class="tableImg icon" data-data="${responseData['imagePath']}" style="background-image: url(${responseData['imagePath'].replace(/([^\/]+)$/, 'icon/$1')})"></td>
                <td class="tableName" data-data="${responseData['pname']}" data-data2="${showSize}">${responseData['pname']}${showSize}</td>
                <td class="tableAdd" data-data="${responseAdd['aname']}">${responseAdd['aname']}</td>
                <td class="tablePrice" data-data="${priceNumber}"><span class='changePrice'>${priceNumber}</span><span> 元</span></td>
                <td class="qtySup">
                    <div class='flex center a-start'>
                    <div class='ml-8'><span class="qty w-px-48" data-data="${responseAdd['sid']}" data-sale="${priceNumber}">${qtyNumber}</span></div>
                    </div>
                </td>
                <td class="subPrice" data-data=""><span class='changeSubPrice red'>${thePrice}</span><span> 元</span></td>
            </tr>
        `)
    }
    $('#totalPrice').text(totalPrice)
    showOrderInputDataHandler()
}
function showOrderInputDataHandler(){
    ajaxGet('customer/info', showOrderInfo)
}
function showOrderInfo(response){
    if (response['code'] == 400){
        productToLogin()
        return;
    }
    scrollBonus = -1.3
    $('.orderBlock').append(`
            <fieldset class="m-0-auto">
                <legend class="fs-24">訂購人資訊</legend>
                <div class='flex flexCol form textLeft'>
                    <div><span>姓名</span><span>:</span><span class='orderName ml-16' data-data=${response['name']}>${response['name']}</span></div>
                    <div class='mt-16'><span>手機</span><span>:</span><span class='orderPhone ml-16' data-data=${response['phone']}>${response['phone']}</span></div>
                    <div class='mt-16'><span>信箱</span><span>:</span><span class='orderMail ml-16' data-data=${response['mail']}>${response['mail']}</span></div>
                    <div class='mt-16'><span>地址</span><span>:</span><span class='orderAddress ml-16' data-data=${response['address']}>${response['address']}</span></div>
                </div>
            </fieldset>
            <fieldset class="m-0-auto">
                <legend class="fs-24">收貨人資訊</legend>
                <div class='flex flexCol form textLeft'>
                    <div class='flex a-center mt-8'><span class='pb-10'>姓名</span><span class='pb-10'>:</span><input type='text' id='recipientName' class='ml-16' value=""  autocomplete="name"><span id='error-recipientName' class='errorRecipient ml-16 red'></span></div>
                    <div class='flex a-center mt-8'><span class='pb-10'>手機</span><span class='pb-10'>:</span><input type='text' id='recipientPhone' class='ml-16' value=""  autocomplete="phone"><span id='error-recipientPhone' class='errorRecipient ml-16 red'></span></div>
                    <div class='flex a-center mt-8'><span class='pb-10'>信箱</span><span class='pb-10'>:</span><input type='text' id='recipientEmail' class='ml-16' value="" autocomplete="email"><span id='error-recipientEmail' class='errorRecipient ml-16 red'></span></div>
                    <div class='flex a-center mt-8'><span class='pb-10'>地址</span><span class='pb-10'>:</span><input type='text' id='recipientAddress' class='ml-16 w-50' value="" autocomplete="address"><span id='error-recipientAddress' class='errorRecipient ml-16 red'></span></div>
                    <!-- <div class='orderSame'>同訂購人</div> -->
                </div>
            </fieldset>
            <div class='w-100'>
                <fieldset>
                    <legend class='fs-24'>付款方式 和 取貨方式</legend>
                    <div id='paymentSup' class='payment textLeft'><span>付款方式：</span></div>
                    <div id='paymentShow' class='hidden mt-16'><span>付款額外費用:</span><span id='paymentFee' class='red pl-8'>0</span><span> 元</span></div>
                    <div class='flex a-center textLeft mt-16'>
                        <span>取貨方式：</span>
                        <span id='shippingSup' class='shipping'></span>
                    </div>
                    <div id='shippingShow' class='hidden mt-16'><span>取貨額外費用:</span><span id='shippingFee' class='red pl-8'>0</span><span> 元</span></div>
                    <div></div>
                </fieldset>
                <div class='mt-8'>
                    <div class='checkoutTotalSup'><span>總計：</span><span id='allTotal' class=' red fs-24'></span><span> 元</span></div>
                    <div class='checkoutTotalResponse none red'></div>
                </div>
                <div class='flex flexRow a-center'>
                    <div class='w-px-80 hidden'>請選擇</div>
                    <div id='submitCheckout' class='submitCheckout pt-8 pb-8'>送出訂單</div>
                    <div class='errorTypeShow w-px-80 hidden'><span>請選擇</span><span class='errorTypeInfo red'></span></div>
                </div>
            </div>`)
        orderPathScrollTop()
        ajaxGet('payment/type', setPaymentAndShipping)
        $('#submitCheckout').click(checkoutClickHandler)
        //$('#linePay').click(linePayHandler)
    //TODO
}
function orderPathScrollTop(){
    let scrollH = ($(document).height() - $(window).height() ) * 2 / 3
    $('.showBlock').css('top', -scrollH)
   // $('.showBlock').animate({ top: -scrollH}, 500)
    $(window).scrollTop(scrollH)
    //$("html, body").animate({ scrollTop: scrollH }, 500)
}
function checkoutClickHandler(){
    $('.errorRecipient').text('')
    $('.errorTypeShow').addClass('hidden')
    $('.errorTypeInfo').text('')
    let checkoutOk = true;
    checkoutOk = checkoutOk && checkRecipientData('recipientName') && checkRecipientData('recipientPhone') 
                            && checkRecipientData('recipientEmail') && checkRecipientData('recipientAddress') 
                            && checkPaymentAndShippingType('paymentType') && checkPaymentAndShippingType('shippingType')
    let recipientName = $('#recipientName').val().trim()
    let recipientPhone = $('#recipientPhone').val().trim()
    let recipientEmail = $('#recipientEmail').val().trim()
    let recipientAddress = $('#recipientAddress').val().trim()
    let paymentType = $('input[name=paymentType]:checked').val()
    let shippingType =$('input[name=shippingType]:checked').val()
    if (checkoutOk){
        let checkoutJson = {}
        checkoutJson['recipientName'] = recipientName
        checkoutJson['recipientPhone'] = recipientPhone
        checkoutJson['recipientEmail'] = recipientEmail
        checkoutJson['recipientAddress'] = recipientAddress
        checkoutJson['paymentType'] = paymentType
        checkoutJson['shippingType'] = shippingType
        ajaxPost('order/checkout', 'application/json', checkoutJson, checkoutResponse)
    }
}
function checkoutResponse(response){
    if (response['code'] == 200){
        $('.checkoutTotalSup').addClass('none')
        $('.checkoutTotalResponse').text(response['msg']).removeClass('none')
        updateSmallCart()
        setTimeout(function(){
            location.reload();
        },1000)

    } else {
        $('.checkoutTotalSup').addClass('none')
        let checkoutErrorMap = response['error']
        let checkoutErrorMsg
        if (checkoutErrorMap['order']) {
            checkoutErrorMsg = checkoutErrorMap['order']
        } else if (checkoutErrorMap['other']) {
            checkoutErrorMsg = checkoutErrorMap['other']
        } else if (checkoutErrorMap['payment']) {
            checkoutErrorMsg = checkoutErrorMap['payment']
        } else if (checkoutErrorMap['shipping']) {
            checkoutErrorMsg = checkoutErrorMap['shipping']
        } else if (checkoutErrorMap['raddress']) {
            checkoutErrorMsg = checkoutErrorMap['raddress']
        } else if (checkoutErrorMap['rname']) {
            checkoutErrorMsg = checkoutErrorMap['rname']
        } else if (checkoutErrorMap['phone']) {
            checkoutErrorMsg = checkoutErrorMap['phone']
        } else if (checkoutErrorMap['email']) {
            checkoutErrorMsg = checkoutErrorMap['email']
        }
        $('.checkoutTotalResponse').text(checkoutErrorMsg).removeClass('none')
        setTimeout(function(){
            $('.checkoutTotalSup').removeClass('none')
            $('.checkoutTotalResponse').text('').addClass('none')
            location.reload();
        },1000)
    }
}
function checkPaymentAndShippingType(id){
    let typeId = $(`input[name=${id}]:checked`)
    if(typeId.val()){
        return true
    }
    if (id == 'paymentType'){
        $('.errorTypeInfo').text('支付')
    }else {
        $('.errorTypeInfo').text('取貨')
    }
    $('.errorTypeShow').removeClass('hidden')
    return false
}
function checkRecipientData(id){
    let reciId = $(`#${id}`)
    if (reciId.val().trim().length == 0){
        $(`#error-${id}`).text(reciId.prev().prev().text() + " 不可為空")
        orderPathScrollTop()
        return false;
    }
    return true;
}
function setPaymentAndShipping(response){
    var i = 0;
    for (var data of response) {
        $('#paymentSup').append(`
            <label class='pointer'>
                <input type='radio' name='paymentType' class='paymentType pointer' data-data='${data['fee']}' data-id='${i}' value='${data['value']}'>
                <span>${data['type']}</span>
            </label>
            `)
        for (var shipping of data['shippingTypes']) {
        $('#shippingSup').append(`
            <label class='pointer shippingTypeLabel none shippingLabel${i}'>
                <input type='radio' name='shippingType' class='shippingType pointer' data-data='${shipping['fee']}' value='${shipping['value']}'>
                <span>${shipping['type']}</span>
            </label>`)
        }
        i++
    }
    orderTotalSum()
    $('.paymentType').on('change', getShippingTypeHandler)
    $('.shippingType').on('change', getShippingFeeHandler)
}
function orderTotalSum(){
    let totalPrice = parseFloat($('#totalPrice').text())
    let paymentFee = parseFloat($('#paymentFee').text())
    let shippingFee = parseFloat($('#shippingFee').text())
    $('#allTotal').text(totalPrice + paymentFee + shippingFee)
}
function getShippingFeeHandler(){
    let e = $(this)
    let fee = e.data('data')
    $('#shippingShow').removeClass('hidden')
    $('#shippingFee').text(fee)
    orderTotalSum()
}
function getShippingTypeHandler(){
    let e = $(this)
    let id = e.data('id')
    let fee = e.data('data')
    $('input[name="shippingType"]').prop("checked", false);
    $('.shippingTypeLabel').addClass('none')
    $(`.shippingLabel${id}`).removeClass('none')
    $('#shippingShow').addClass('hidden')
    $('#paymentShow').removeClass('hidden')
    $('#paymentFee').text(fee)
    $('#shippingFee').text('0')
    orderTotalSum()
}

function linePayHandler(){
    body = JSON.stringify({
        amount: 1000,
        currency: "TWD",
        orderId: "YOUR_ORDER_ID",
        packages: [{ 
            id: "packageId",
            amount: 1000,
            name: "Product Name"
        }],
        redirectUrls: {
            confirmUrl: "https://yourwebsite.com/confirm",
            cancelUrl: "https://yourwebsite.com/cancel"
        }
    })
    ajaxPost('payment/linePay', 'application.json', body, function(){  window.location.href = response.info.paymentUrl.web})
}
function totalChangeHandler(){
    let e = $(this)
    if (e.hasClass('pUp')){
        let btnParent = e.prev()
        let chooseQty = btnParent.children().first()
        let qtyVal = chooseQty.val()
        qtyVal++
        updateCartData(qtyVal, chooseQty, btnParent)
    }else if (e.hasClass('pDown')){
        let btnParent = e.next()
        let chooseQty = btnParent.children().first()
        let qtyVal = chooseQty.val()
        qtyVal--
        if (qtyVal >= 2){
            updateCartData(qtyVal, chooseQty, btnParent)
        }
    }else {
        e.closest('.productSup').remove()
        orderCalcTotal()
        // if ($('.garbage').length == 0){
        //     cartNull()
        // }
    }


    //if (e.hasClass('pUp')){
    //     let chooseQty = btnParent.prev().children().first()
    //     let qtyVal = chooseQty.val()
    //     qtyVal++
    //     updateCartData(qtyVal, chooseQty, btnParent, e);
    // }else if (e.hasClass('pDown')){
    //     let chooseQty = btnParent.next().children().first()
    //     let qtyVal = chooseQty.val()
    //     qtyVal--
    //     if (qtyVal >= 2) {
    //         updateCartData(qtyVal, chooseQty, btnParent, e)
    //     }
    // }else {
    //     e.closest('.productSup').remove()
    //     orderCalcTotal(e)
    // }    
}
function updateCartData(qtyVal, chooseQty, btnParent){
    chooseQty.val(qtyVal)
    let salePrice = chooseQty.data('sale')
    // let showSupPrice = btnParent.nextAll('.subPrice').children().first()
    let showSupPrice = btnParent.closest('.qtySup').next().children().first()
    showSupPrice.text(qtyVal * salePrice)
    orderCalcTotal()
}
function orderCalcTotal(){
    let totalPrice = 0
    $('.changeSubPrice').each(function(){
        totalPrice = totalPrice + parseInt($(this).text())
    })
    $('#totalPrice').text(totalPrice)
}
function getCartResponse(response){
   if(response['code'] == 200) {
        updateSmallCart()
        showCartListHandler()
        $('.showBlock').css('top',72)
    $(window).scrollTop(0)
   } else {
        productToLogin()
   }
}
function updateCartAnimate(response){
    showUpdate = true
    showCartListHandler(response)
}
function showCartListHandler(response){
    setBlockHandler()
    ajaxGet(`order/cart`, showCartResponse)
}
function addCartResponse(response){
    if (response['code'] == 200) {
        updateSmallCart()
        localStorageId++
        $('.showDollarData').addClass('none')
        $('.showCartData').removeClass('none')
        setTimeout( function() {
            $('.showDollarData').removeClass('none')
            $('.showCartData').addClass('none')
        }, 1000)
    } else {
        setCartOnLocalStorage()
    }
}
function setCartOnLocalStorage(){
    try {
        let cartData = `{'qty' : ${getQty()}, 'sid': ${getSid()}}`
        let hasData = localStorage.getItem('addCart');
        let newData
        if (hasData){
            newData = JSON.parse(hasData)
            newData = newData + ',' + cartData
        } else {
            newData = cartData
        }
        localStorage.setItem('addCart', JSON.stringify(newData));
        localStorageId++
        return true;
    } catch(e){
        productToLogin()
    }
}
function productToLogin(){
    displayHandler()
    windowResizeLoginHandler()
    setTimeout( function() {
        loginMousedownHandler()
        showLogin()
    }, 300)
}

function getQty(){
    return $('.qty').val()
}
function getSid(){
    let sid 
    let chooseSid = $('.changeTotal').find('.bg-c').prev().data('sid')
    if (chooseSid) {
        sid = chooseSid
    } else {
        sid = $('#showSid').data('sid')
    }
    return sid
}
function setBlockHandler(){
    $('html, body').css('scrollTop', 0)
    showBlockScrollHandler()
    $(window).scroll(showBlockScrollHandler)
    $('.clickBlock').click(stopShowBlockScroll)
    
}
function stopShowBlockScroll(){
    $(window).off('scroll',showBlockScrollHandler)
    $('.clickBlock').off('click',stopShowBlockScroll)
}
function showBlockScrollHandler(){
    let scrollTop = $(this).scrollTop();
    let docH = $(document).height();
    let blockH = $('.showBlock').height();
    // docH : block = wS : bS => bS * docH = ws * block
    let blockScrollTop = -1.3 * scrollTop * (blockH / docH)
    $('.showBlock').css("top", (parseInt(blockScrollTop) + 72) + 'px')
}
function cartNull(){
    $('table').html('<div class="flex center a-center h-super fs-48 red">購物車目前是空的</div>')
}
function addcarPHandler() {
    let sourceT = parseInt($('.showBlock').css('top'), 10);
    let theT = sourceT;
    let timeT = 600;
    animateBounce(0, sourceT, theT, timeT);
    $('.showBlock').css('boxShadow','0px 0px 0px 0px')
}

function animateBounce(i, sourceT, theT, timeT) {
    if (i >= 5) return;
    $('.showBlock').animate({ top: theT }, timeT, function () {
        theT = sourceT / Math.pow(2, i + 1);
        timeT *= 0.8;
        $('.showBlock').animate({ top: sourceT }, timeT, function () {
            animateBounce(i + 1, sourceT, theT, timeT);
        });
    });
}
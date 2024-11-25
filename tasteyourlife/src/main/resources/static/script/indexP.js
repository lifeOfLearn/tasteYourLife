function getProductTop(){    
    hotUrl='product/new?page=0&size=3'
    newUrl='product/hot?page=0&size=3'
    ajaxGet(hotUrl,inputNewData)
    ajaxGet(newUrl,inputHotData)
}

function inputNewData(response) {
    let productNew = $('.newProduct')
    let productClass = 'newP'
    createData(response, productNew, productClass)
}
function inputHotData(response) {
    let productHot = $('.hotProduct')
    let productClass = 'hotP'
    createData(response, productHot, productClass)
}

function createData(response, superPath, addClass){
    response.forEach(data => htmlContainer(data,superPath, addClass) )

}
function htmlContainer(productData, superPath, addClass){
    let container = `
    <form class="${addClass} btn choose ml-8 pointer">
    <input class="none" name="id" disabled value="${productData.pid}">
    <div class="productImage">
        <input class="none" name="image" disabled value="${productData.imagePath}">
    </div>
    <div class="productName">
        <input class="productD b-0 fw-b textCenter fs-20 pointer" name="name" value="${productData.pname}" disabled>
    </div>
    <div class="productInfo">
        <input class="fs-16 fw-5 productD b-0 fw-b textCenter fs-20 pointer" name="info" value="${productData.pinfo}" disabled>
    </div>
    <div class="flex a-center center mt-16">
        <!-- <input class="buy pointer btn choose" type="submit" value="立刻購買"> -->
        <!-- <input class="addcart ml-16 pointer btn choose" type="submit" value="加入購物車"> -->
    </div>
    </form>
    `
    let element = $(container);
    superPath.append(element);
    let imagePath = productData.imagePath;
    element.find('.productImage').css('background-image', `url(${imagePath})`);
    element.click(getProductAllData)
    
}
function getProductAllData(e){
    e.preventDefault()
    $('article').append(`<div class="showBlock">
        <h1 id="showName" class="textCenter" data-name>產品資訊</h1>
        <div class="flex">
            <div class='flex a-center center h-vh-40'>    
                <div id="showImage" class='ml-16' data-image=""></div>
            </div>
            <div id="showData" class="flex flexCol between textCenter h-40">
                <div id="showId" class="none" data-id=""></div>
                <div>
                    <h3 id="showInfo" class="m0" data-info=""></h3>
                </div>
                <div id="showAdd" data-sid="">
                </div>
                <div class="flex a-center center mt-16">
                    <div class="pt-5">售價:</div>
                    <div class="flex a-center center">
                        <div id="showTotal" class="red ml-16"></div>
                        <div id="trueTotal" class="red ml-16 none"></div>
                        <div class="ml-16">元</div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="clickBlock"></div>`)
    $('article').addClass('z-12')
    $('.showBlock').addClass('z-14')
    $('.clickBlock').addClass('z-13')
    let id = $(e.currentTarget).find('input[name="id"]').val();
    ajaxGet(`/tyl/product/id/${id}`, showDataHandler)
}
function showDataHandler(response){
    if (response) {
        $('#showAdd').text('').append('<div id="showChoose" class="flex a-center center bg-f"></div>')
        setBlockAndProductIfHas(response)
    }
}
function setBlockAndProductIfHas(response){
    let left = ($(window).width() - $('.showBlock').width()) / 2;
    if (left < 0) left = 0;
    $('.showBlock').addClass('z-14').css({'left' : left + 'px', 'display' : 'block'})
    $('.clickBlock').addClass('z-13').show().click(displayHandler)
    if (response){
        inputTheData(response)
    }
}
function displayHandler(e) {
    $('article').removeClass('z-12')
    $('.showBlock').remove()
    $('.clickBlock').remove()
}
function inputTheData(data) {
    $('#showImage').css('backgroundImage', `url(${data['imagePath']})`).data('image',data['imagePath'])
    $('#showId').data('id', data['pid'])
    $('#showName').text(data['pname']).data('name',data['pname'])
    $('#showInfo').text(data['pinfo']).data('info',data['pinfo'])
    let dataAdd = data['productPrice']
    let container
    switch (dataAdd.length) {
        default:
            if(dataAdd[0]['size']) {
                $('#showChoose').append(`<div id="showSid" class='none' data-sid=${dataAdd[0]['sid']}></div>
                    <div class="pt-5">尺寸:</div>`)
                $('#trueTotal').remove()
                for (let size of dataAdd) {
                    let idContainer = ``
                    container = `<div class='form ml-16 changeTotal'>
                            <div id='showBtn'><div>
                            <div id="showAid" class='none' data-aid=${size['aid']}>${size['aid']}</div>
                            <div id="showSid" class='none' data-sid=${size['sid']}>${size['sid']}</div>
                            <div id="showAname" class="btn choose" data-aname=${size['size']}>${size['size']}</div>
                            <div id="showAtotal" class='none' data-total=${size['price']}>${size['price']}</div>
                        </div>`
                    $('#showChoose').before(idContainer).append(container)
                }
                $('.changeTotal').click(sizeChangeTotal)
                $('#showAname').addClass('bg-c')
            } else {
                $('#showChoose').append(`<div id="showSid" class='none' data-sid=${dataAdd[0]['sid']}></div>
                    <div class="pt-5">加價購:</div>`)
                
                for (let i = 1; i < dataAdd.length;i++) {
                    let add = dataAdd[i]
                    let aname = add['aname'].replace(/\+/g, "+<br>")
                    let container = `<div class='form ml-16 changeTotal'>
                            <div id='showBtn'><div>
                            <div id="showAid" class='none' data-aid=${add['aid']}>${add['aid']}</div>
                            <div id="showSid" class='none' data-sid=${add['sid']}>${add['sid']}</div>
                            <div id="showAname" class="showAname btn choose" data-aname=${add['aname']}>${aname}</div>
                            <div id="showAtotal" class='none' data-total=${add['price']}>${add['price']}</div>
                        </div> `
                    $('#showChoose').append(container)
                }
                $('.changeTotal').click(addChangeTotal)
            }
            $('#showTotal').text(dataAdd[0]['price'])
            $('.showBlock').addClass('max-h-70')
            break
        case 1:
            $('#showTotal').text(dataAdd[0]['price'])
            $('#showChoose').append(`<div id="showSid" class='none' data-sid=${dataAdd[0]['sid']}>${dataAdd[0]['sid']}</div><div id="showAid" class='none' data-aid=${dataAdd[0]['aid']}>${dataAdd[0]['aid']}</div>`)
            let showName = $('#showName').text()
            $('#showName').text(`(${dataAdd[0]['size']})${showName}`)
    }
    $('#showData').append(`<div class="flex flexCol a-center center mt-16">
                <div class='mb-16'>
                    <span class="pCalc pDown"></span>
                    <input class="qty" type='number' name='qty' min='1' value='1'>
                    <span class="pCalc pUp"></span>
                </div>
                <div class='mb-16'>
                    <div class='showDollarData'>
                        <span>金額：</span>
                        <span class='priceTotal red'></span>
                        <span class='ml-16'>元</span>
                    </div>
                    <div class='showCartData red textCenter none'>加入購物車成功</div>
                </div>
                <div>
                    <input id='buyP' class="buy pointer btn choose" type="submit" value="立刻購買">
                    <input id='addcartP' class="addcart ml-32 pointer btn choose" type="submit" value="加入購物車"></div></div>`)
    $('#showData').width( $('.showBlock').width() - $('#showImage').outerWidth() - 32 + "px").addClass('ml-16')
    $('#showChoose').css('height', $('#showChoose').height() + 10 + 'px')
    let nameH = 0
    $('.showAname').each(function(){
        let theH = $(this).outerHeight()
        if (nameH < theH) nameH = theH
    }).height(nameH)
    if (dataAdd.length > 4) {
        if(dataAdd.length > 6) {
            $('.showBlock').addClass('max-h-80')    
        }
        $('.showBlock').addClass('max-h-75')
    }
    // productDataBtnSet()
    $('.pCalc').click(qtyChangeHandler)
    calcTotal()
    addCart()
}   

function addChangeTotal(e){
    let element = $(this)
    let trueTotal = $(e.currentTarget).find('#showAtotal').data('total')
    let botton = element.find('#showAname')
    if (botton.hasClass('bg-c')) { 
        botton.removeClass('bg-c')
        $('#showTotal').removeClass('text-del').addClass('red')
        $('#trueTotal').text(trueTotal).addClass('none')
    } else {
        $('.choose').removeClass('bg-c')
        botton.addClass('bg-c')
        $('#showTotal').addClass('text-del').removeClass('red')
        $('#trueTotal').text(trueTotal).removeClass('none')
    }
    calcTotal()
}   
function sizeChangeTotal(e){
    let element = $(this)
    let trueTotal = $(e.currentTarget).find('#showAtotal').data('total')
    let botton = element.find('#showAname')
    if (botton.hasClass('bg-c')) { 
        botton.removeClass('bg-c')
        $('#showTotal').text(trueTotal).addClass('none')
        if ($('.changeTotal').find('.bg-c').length == 0){
            $('#showAname').addClass('bg-c')
            $('#showTotal').text( $('#showAname').next().text()).removeClass('none')
        }
    } else {
        $('.choose').removeClass('bg-c')
        botton.addClass('bg-c')
        $('#showTotal').text(trueTotal).removeClass('none')
    }
    calcTotal()
}
function qtyChangeHandler(){
    let e = $(this)
    let qty = $('.qty').val()
    if (e.hasClass('pUp')){
        qty++
    } else if (e.hasClass('pDown')) {
        if (qty >= 2){
            qty--
        }
    }
    $('.qty').val(qty)
    calcTotal()
}
function calcTotal(){
    let qty = $('.qty').val()
    let price
    if($('#trueTotal').length == 0 ||$('#trueTotal').hasClass('none')){

        price = $('#showTotal').text()
    } else {
        price = $('#trueTotal').text()
    }
    $('.priceTotal').text(price * qty)
}

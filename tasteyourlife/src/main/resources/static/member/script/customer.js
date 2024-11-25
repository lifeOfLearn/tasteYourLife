let customerUser
function customerInit(userName){
	updateSmallCart()
	//customerLoadCss();
	$('.homeImg').click(homeHandler)
	customerUser = userName
	resizeEvnetWidth = 820;
	$(window).off('resize');
	windowResizeLoginHandler();
	$(window).resize(windowResizeLoginHandler);
	$('.listLogout').on('mousedown',customerLogout);
	$('.listUsername').text(userName);
	$('.listProduct').click(getProcutCategoryAndProductPage)
	$('.listCart').click(getListCartHandler)
	$('.listMember').click(showMemberFeature)
}
function showMemberFeature(){
	$('.listMenu').css('display','')
	productPage = 0
	theCateid = 0
	catePage = {}
	$('.listProduct').off('click').click(getProcutCategoryAndProductPage)
	$('article').html('').prepend(`
		<div id='memberFeatureContainer' class='form flex flexWrap center textCenter'>
			<div id='orderHistory' class='featureData btn choose br-10 fs-24 fw-10 pointer'>
                <div class='cname'>歷史訂單</div>
            </div>
			<div id='memberUpdatePasswd' class='featureData btn choose br-10 fs-24 fw-10 pointer'>
                <div class='cname'>修改會員密碼</div>
            </div>
			<div id='memberUpdateInfo' class='featureData btn choose br-10 fs-24 fw-10 pointer'>
                <div class='cname'>修改會員資料</div>
            </div>
		</div>
		<div id='memberShow' class='nform'></div>`)
	changeToOrderHistory()
	$('#memberFeatureContainer').click(changeToOrderHistory)
}
function changeToOrderHistory(){
	if($('#orderHistoryBody').length != 0){
		$('#orderHistoryBody').remove()
	}
	$('#memberShow').before(`
		<div id='orderHistoryBody' class='textCenter flex flexCol a-center min-h-150'>
			<div id='memberTitle' class='fs-48 mt-16 mb-16'>歷史訂單</div>
			<div class='nform flex flexRow center a-center h-px-40'>
				<input id='memberDate' type='date'>
				<span id='searchHistory' class='ml-8 btn choose'>查詢</span>
			</div>
		</div>`)
	let today = new Date().toISOString().split("T")[0];
	$("#memberDate").val(today);
	$('#searchHistory').click(getOrderHistoryHandler)
}
function getOrderHistoryHandler(){
	let after = $('#memberDate').val()
	if (after.length != 0) {
		ajaxGet(`order/history?after=${after}`, orderHistoryResponse)
	}
}
function orderHistoryResponse(response) {
	if (response['code'] == 400) {
		productToLogin()
		return;
	}
	let memberShow = $('#memberShow')
	memberShow.html('')
	var i = 0
	for (var data of response){
		let productInfo = data['productInfo']
		let paymentStatus
		switch (data['paymentStatus']) {
			case 0 : paymentStatus = "訂單成立";break
			case 1 : paymentStatus = "應徵送貨員中";break
			case 2 : paymentStatus = "送貨員趕路中";break
			case 3 : paymentStatus = "商品已送達";break
			case 4 : paymentStatus = "完成訂單";break
		}
		memberShow.append(`
			<table id='orderHistory${i}' class='orderForm orderHistoryTable nform w-100 textCenter bg-f choose'>
				<thead>
					<tr>
						<th>訂單日期</th>
						<th>訂單編號</th>
						<th>數量</th>
						<th>支付額外費用</th>
						<th>運費</th>
						<th>訂單總額</th>
						<th>付款方式</th>
						<th>付款狀態</th>
						<th>取貨方式</th>
						<th>訂單狀態</th>
						<th>收件人</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>${data['time'].toLocaleString("zh-TW", { timeZone: "Asia/Taipei" }).split('T')[0]}</td>
						<td>${data['id']}</td>
						<td>${data['qty']}</td>
						<td>${data['paymentFee']}</td>
						<td>${data['shippingFee']}</td>
						<td>${data['totalPrice']}元</td>
						<td>${data['payment']}</td>
						<td>${data['paymentStatus']}</td>
						<td>${data['shipping']}</td>
						<td>${paymentStatus}</td>
						<td>${data['name']}</td>
					</tr>
				</tbody>
			</table>
			<div id='orderClickShow${i}' class='none'>
				<table id='orderProduct${i}' class='orderProductData bg-c'>
				<thead>
					<tr>
						<th>產品名稱</th>
						<th>產品資訊</th>
						<th>加價購</th>
						<th>數量</th>
						<th>單價</th>
					</tr>
				</thead>
				<tbody id='orderTbody${i}'>
				</tbody>
				</table>
			</div>
		`)
		for(var info of productInfo) {
			let theProductPrice = info['productPrice'][0]
			let showSize = ""
			if (theProductPrice['size'])
				showSize = "(" + theProductPrice['size'] + ")"
			$(`#orderTbody${i}`).append	(`
					<tr class='orderHistorySup'>
						<td class="orderHistoryName" >${info['pname']}${showSize}</td>
						<td class="orderHistoryInfo">${info['pinfo']}</td>
						<td class="orderHistoryAdd">${theProductPrice['aname']}</td>
						<td class="orderHistoryQty">${info['qty']}</td>
						<td class="orderHistoryPrice"><span>${theProductPrice['price']}</span><span> 元</span></td>
					</tr>
			`)
		}
		i++
	}	
	$('.orderHistoryTable').click(showOrderHistoryDataInfo)
}
function showOrderHistoryDataInfo(){
	let e = $(this)
	//let next = e.find('.orderProductData')
	let next = e.next()
	if(next.hasClass('none'))
		next.removeClass('none')
	else
		next.addClass('none')
}
function updateSmallCart(){
	ajaxGet('order/cart/small', setSmallCartHandler)
}
function setSmallCartHandler(response){
	if (response['code'] == 200){
		setTimeout( function() {
            $('.navbar').removeClass('z-13')
            $('.smallCart').removeClass('red')
        }, 1000)
		$('.smallCart').text(`(${response['msg']})`).addClass('red')
		$('.navbar').addClass('z-13')
	} else {
		//???TODO什麼好呢不要OAO
	}
}
function getListCartHandler(){
	$('article').addClass('z-12').append(`<div class="showBlock z-14"></div><div class="clickBlock z-13"></div>`)
	setBlockAndProductIfHas()
	showCartListHandler()
	totalChangeHandler()
	$('.showBlock').css('top',72)
    $(window).scrollTop(0)
	// $('.pCalc').click(totalChangeHandler)
    // $('.garbage').click(removeDataHandler)
    // $('.qty').blur(orderCalcTotal)
}
function customerLoadCss(){
	let cssUrl = 'member/css/customer.css' 
	$("<link>", {
	     rel: "stylesheet",
	     type: "text/css",
	     href: cssUrl
	 }).appendTo("head");
}
function customerLogout(){
	let logoutUrl = 'customer/logout';
	$.ajax({
		url:logoutUrl,
		method:'GET',
		success: function(){
			setTimeout( function() {
				location.reload();
			}, 1000);
			$('.listBlock').addClass('z-11').html(`<div class='textCenter'>掰掰 ~ ${customerUser}</div>`)
				.css({'display':'flex','justify-content':'center'});
			$('.listBlockBackground').show();
		},
		error: function(xhr, status, error) {
	    }
	});
}
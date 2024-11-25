package iot.tyl.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import iot.tyl.model.CheckoutRequest;
import iot.tyl.model.OrderHistoryResponse;
import iot.tyl.model.ProductResponse;
import iot.tyl.model.ResponseDto;
import iot.tyl.model.UpdateCart;
import iot.tyl.service.OrderService;
import iot.tyl.util.contant.CommunityType;
import iot.tyl.util.contant.ErrorKeyType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("${path.order.root}")
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	@PostMapping("${path.order.addCart}")
	public ResponseDto addCart(@RequestBody UpdateCart cartRequest,
//			@RequestParam int sId,
//			@RequestParam int qty,
			HttpServletRequest request){
		String tokenId = getTokenId(request);
		int sId = cartRequest.getSid();
		int qty = cartRequest.getQty();
		orderService.addCart(tokenId, sId, qty);
		return getDto(200,"加入購物車成功");
	}
	
	@GetMapping("${path.order.getCart}")
	public List<ProductResponse> getCart(HttpServletRequest request){
		String tokenId = getTokenId(request);
		List<ProductResponse> response = orderService.getCart(tokenId);
		return response;
	}
	
	@PostMapping("${path.order.getCart}")
	public ResponseDto updateCart(@RequestBody List<UpdateCart> cartRequest, HttpServletRequest request) {
		String tokenId = getTokenId(request);
		orderService.updateCart(tokenId, cartRequest);
		return getDto(200,"更新購物車成功");
	}
	
	@GetMapping("${path.order.smallCart}")
	public ResponseDto getSmallCart(HttpServletRequest request) {
		String tokenId = getTokenId(request);
		int qty = orderService.getQuantity(tokenId);
		return getDto(200,String.valueOf(qty));
	}
	
	@PostMapping("${path.order.checkout}")
	public ResponseDto checkout(@RequestBody @Valid CheckoutRequest checkoutReqest, HttpServletRequest request) {
		String tokenId = getTokenId(request);
		boolean isCheckout = orderService.checkout(tokenId, checkoutReqest);
		if (isCheckout) {
			return getDto(200, "訂單成立");
		}
		ResponseDto dto = new ResponseDto();
		dto.setCode(400);
		Map<ErrorKeyType, String> errMap = new HashMap<>();
		errMap.put(ErrorKeyType.order, "訂單成立失敗:購物車為空");
		dto.setError(errMap);
		return dto;
	}
	
	@GetMapping("${path.order.history}")
	public List<OrderHistoryResponse> getHistory(@RequestParam String after, HttpServletRequest request) {
		String tokenId = getTokenId(request);
		List<OrderHistoryResponse> response = orderService.getOrderHistory(tokenId, after);
		return response;
	}
	
	private ResponseDto getDto(int code, String msg) {
		ResponseDto dto = new ResponseDto();
		dto.setCode(code);
		dto.setMsg(msg);
		return dto;
	}
	
	private String getTokenId(HttpServletRequest request) {
		return getId(request, CommunityType.TOKENID);
	}
	
	private String getId(HttpServletRequest request, CommunityType type) {
		return (String) request.getAttribute(type.name());
	}
}

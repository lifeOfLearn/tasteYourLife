package iot.tyl.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import iot.tyl.model.PaymentData;
import iot.tyl.service.PaymentService;
import iot.tyl.util.contant.CommunityType;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("${path.payment.root}")
public class PaymentController {

	@Autowired
	private PaymentService paymentService;
	
	@GetMapping("${path.payment.type}")
	public List<PaymentData> getPaymentAndShipping(){
		return paymentService.getPaymentData();
	}
	
	@GetMapping("${path.payment.line}")
	public ResponseEntity<String> linePay(HttpServletRequest request) {
		try {
			String tokenId = getTokenId(request);
			ResponseEntity<String> body = paymentService.linePayRequest(tokenId);
			return body;
		} catch (Exception e) {
			//TODO err
			return ResponseEntity.status(HttpStatusCode.valueOf(400)).body("err");
		}
	}
	
	private String getTokenId(HttpServletRequest request) {
		return getId(request, CommunityType.TOKENID);
	}
	
	private String getId(HttpServletRequest request, CommunityType type) {
		return (String) request.getAttribute(type.name());
	}
}

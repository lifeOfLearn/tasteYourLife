package iot.tyl.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import iot.tyl.model.PaymentData;

public interface PaymentService {
	public ResponseEntity<String> linePayRequest(String tokenId);
	public List<PaymentData> getPaymentData();
}

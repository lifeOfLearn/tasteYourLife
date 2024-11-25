package iot.tyl.service.impl;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import iot.tyl.model.LinePayRequest;
import iot.tyl.model.PaymentData;
import iot.tyl.model.PaymentData.ShippingData;
import iot.tyl.service.PaymentService;
import iot.tyl.service.RedisService;
import iot.tyl.util.ExceptionUtil;
import iot.tyl.util.LinPaySignature;
import iot.tyl.util.contant.PaymentType;
import iot.tyl.util.contant.ShippingType;

@Service
public class PaymentServiceImpl implements PaymentService{

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private RedisService redisService;
	
	@Autowired
	private LinPaySignature linPaySignature;
	
	@Autowired
	private ExceptionUtil exceptionUtil;

	@Override
	public List<PaymentData> getPaymentData(){
		List<PaymentData> datas = new ArrayList<>();
		for (PaymentType paymentType : PaymentType.values()) {
			PaymentData data = new PaymentData();
			data.setValue(paymentType.name());
			data.setType(paymentType.getDescription());
			data.setFee(paymentType.getFee());
			List<ShippingData> shippingDatas = new ArrayList<>();
			for (ShippingType shippingType : paymentType.getShippingTypes()) {
				ShippingData shippingData = new ShippingData();
				shippingData.setValue(shippingType.name());
				shippingData.setType(shippingType.getDescription());
				shippingData.setFee(shippingType.getFee());
				shippingDatas.add(shippingData);
			}
			data.setShippingTypes(shippingDatas);
			datas.add(data);
		}
		return datas;
	}
	
	
	@Override
	public ResponseEntity<String> linePayRequest(String tokenId) {
	//public ResponseEntity<String> linePayRequest(String queryString, LinePayRequest request) {
		String clientId = getClientId(tokenId);
		//TODO queryString
		String queryString = "";
		//TODO getRequest from dao
		LinePayRequest request = null;
		try {
			long nonce = System.currentTimeMillis();
			HttpHeaders header = getLinePayHeader(queryString, nonce);
			HttpEntity<LinePayRequest> entity = new HttpEntity<>(request, header);
			ResponseEntity<String> response = restTemplate.exchange(linPaySignature.getUri(), HttpMethod.POST, entity, String.class);
			return response;
		} catch (Exception e) {
			// TODO 
			e.printStackTrace();
			return null;
		}
	}
    
	private HttpHeaders getLinePayHeader(String queryString, long nonce) throws Exception{
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		header.set("X-LINE-ChannelId", linPaySignature.getId());
//		header.set("X-LINE-MerchantDeviceProfileId",...);//test不提供 //https://pay.line.me/jp/developers/apis/onlineApis?locale=zh_TW
		header.set("X-LINE-Authorization-Nonce", String.valueOf(nonce));
		header.set("X-LINE-Authorization", linPaySignature.generateSignature(queryString, nonce));
		return header;
	}
    
	
	private String getClientId(String tokenId) {
		String clientId = null;
		try {
			clientId = get(tokenId);
		} catch (Exception e) {
			
		} finally {
			if (clientId != null)
				return clientId;
		}
		throw exceptionUtil.throwCartGetRedisIdNull(tokenId);
	}
	
	private String get(String key) {
		return redisService.getValue(key);
	}
}

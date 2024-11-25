package iot.tyl.util.impl;

import java.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Component;

import iot.tyl.util.LinPaySignature;;

@Component
public final class LinePaySignatureImpl implements LinPaySignature{
	
	private final String uri = "https://sandbox-api-pay.line.me/v3/payments/request";
	private final String channelId = "2006519599";
	private final String channelSecret = "4c07acb2531c21c78d05027b9f91658a";
	
	@Override
	public String generateSignature(String queryString, long nonce) throws Exception {
		String message = channelSecret + uri + queryString + nonce;
		Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
		SecretKeySpec secretKeySpec = new SecretKeySpec(channelSecret.getBytes(), "HmacSHA256");
		sha256_HMAC.init(secretKeySpec);
		byte[] hash = sha256_HMAC.doFinal(message.getBytes());
		return Base64.getEncoder().encodeToString(hash);
	}
	
	
	@Override
	public final String getId() {
		return channelId;
	}
	
	@Override
	public final String getUri() {
		return uri;
	}
}

package iot.tyl.util;

public interface LinPaySignature {

	public String generateSignature(String queryString, long nonce) throws Exception;

	public String getUri();
	
	public String getId();
}

package iot.tyl.service.impl;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import iot.tyl.config.ParamConfig;
import iot.tyl.config.ParamConfig.Keystore;
import iot.tyl.config.ParamConfig.Token;
import iot.tyl.exception.TylBaseException;
import iot.tyl.service.JwtService;
import jakarta.annotation.PostConstruct;

@Service
public class JwtServiceImpl implements JwtService {

	@Autowired
	private ParamConfig paramConfig;
	
	private PrivateKey privateKey;
	
	private PublicKey publicKey;
	
	@PostConstruct
	public void init() throws TylBaseException {
		Keystore data = paramConfig.getKeystore();
		try (FileInputStream stream = new FileInputStream(data.getPath())) {
			char[] pwd = data.getPasswd().toCharArray();
			KeyStore keystore = KeyStore.getInstance(data.getType());
			keystore.load(stream, pwd);
			privateKey = (PrivateKey) keystore.getKey(data.getAlias(), pwd);
			publicKey = keystore.getCertificate(data.getAlias()).getPublicKey();
			
		} catch (Exception e) {
			throw new TylBaseException("token初始化失敗", e);
		}
	}
	
	@Override
	public String generateToken(String subject, long expireTimeSec) {
		return createToken(subject, expireTimeSec);
	}

	@Override
	public String generateToken(Map<String, Object> claims, String subject, long expireTimeSec) {
		return createToken(claims, subject, expireTimeSec);
	}

	@Override
	public Object getValue(String token, String key) {
		return extractClaimByKey(token, key);
	}

	@Override
	public long remainTime(String token) {
		long endTime = extractClaim(token, Claims::getExpiration).getTime();
		long nowTime = new Date().getTime();
		return endTime - nowTime;
	}

	@Override
	public boolean checkToken(String token, String subject) {
		final String sub = extractClaim(token, Claims::getSubject);
		return sub.equals(subject) && 
				! isTokenExpired(token); 
	}

	@Override
	public boolean isTokenExpired(String token) {
		return extractClaim(token, Claims::getExpiration).before(new Date());
	}
	
	
	private Object extractClaimByKey(String token, String key) {
		return extractClaim(token, claims -> claims.get(key));
	}
	
	private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
	
	private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(publicKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
	
	private String createToken(Map<String,Object> claims, String sub, long expireTimeSec) {
		long expireTime = expireTimeSec * 1000L;
		Token token = paramConfig.getToken();
		
		return Jwts.builder()
				.setClaims(claims)
				.setAudience(token.getAud())
				.setId(token.getJti())//wait using 暫存
				.setIssuer(token.getIss())
				.setSubject(sub)
				.setIssuedAt(getTimeMill())
				.setExpiration(getTimeMill(expireTime))
				.signWith(privateKey, SignatureAlgorithm.ES256)
				.compact();
	}
	
	private String createToken(String sub, long expireTimeSec) {
		long expireTime = expireTimeSec * 1000L;
		Token token = paramConfig.getToken();
		
		return Jwts.builder()
				.setAudience(token.getAud())
				.setId(token.getJti())//wait using 暫存
				.setIssuer(token.getIss())
				.setSubject(sub)
				.setIssuedAt(getTimeMill())
				.setExpiration(getTimeMill(expireTime))
				.signWith(privateKey, SignatureAlgorithm.ES256)
				.compact();
	}
	
	
	private Date getTimeMill() {
		return getTimeMill(0);
	}
	
	private Date getTimeMill(long time) {
		return new Date(System.currentTimeMillis() + time);
	}

}

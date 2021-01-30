/**
 *
 */
package kr.co.test.app.oauth.service;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import kr.co.test.app.oauth.mapper.OAuthMapper;
import kr.co.test.app.oauth.model.OAuthRefreshToken;

/**
 * <pre>
 * 개정이력
 * -----------------------------------
 * 2021. 1. 21. kdk	최초작성
 * </pre>
 *
 *
 * @author kdk
 */
public class OAuthJwtTokenStore extends JwtTokenStore {

	/**
	 * @param jwtTokenEnhancer
	 */
	public OAuthJwtTokenStore(JwtAccessTokenConverter jwtTokenEnhancer) {
		super(jwtTokenEnhancer);
	}

	@Autowired
	private OAuthMapper oAuthMapper;

	@Override
	public void storeRefreshToken(OAuth2RefreshToken refreshToken, OAuth2Authentication authentication) {
		OAuthRefreshToken crt = new OAuthRefreshToken();
		crt.setId(UUID.randomUUID().toString() + UUID.randomUUID().toString());
		crt.setTokenId(extractTokenKey(refreshToken.getValue()));
		crt.setTokenObject(refreshToken);
		crt.setAuthenticationObject(authentication);
		crt.setUsername(authentication.isClientOnly() ? null : authentication.getName());
		crt.setClientId(authentication.getOAuth2Request().getClientId());

		oAuthMapper.saveRefreshToken(crt);
	}

	@Override
	public OAuth2RefreshToken readRefreshToken(String tokenValue) {
		OAuth2RefreshToken token = super.readRefreshToken(tokenValue);

		if (token != null) {
			OAuthRefreshToken refreshToken = oAuthMapper.findRefreshTokenByTokenId(extractTokenKey(tokenValue));
			return refreshToken != null ? refreshToken.getTokenObject() : null;
		}

		return token;
	}

	@Override
	public OAuth2Authentication readAuthenticationForRefreshToken(OAuth2RefreshToken token) {
		OAuth2Authentication oauth = super.readAuthenticationForRefreshToken(token);

		if (token != null) {
			OAuthRefreshToken rtk = oAuthMapper.findRefreshTokenByTokenId(extractTokenKey(token.getValue()));
			return rtk != null ? rtk.getAuthenticationObject() : null;
		}

		return oauth;
	}

	@Override
	public void removeRefreshToken(OAuth2RefreshToken token) {
		super.removeRefreshToken(token);

		OAuthRefreshToken rtk = oAuthMapper.findRefreshTokenByTokenId(extractTokenKey(token.getValue()));

		if (rtk != null) {
			oAuthMapper.deleteRefreshToken(rtk);
		}
	}

	private String extractTokenKey(String value) {
		if (value == null) {
			return null;
		} else {
			MessageDigest digest;

			try {
				digest = MessageDigest.getInstance("MD5");
			} catch (NoSuchAlgorithmException e) {
				throw new IllegalStateException("MD5 algorithm not available.  Fatal (should be in the JDK).");
			}

			byte[] e = digest.digest(value.getBytes(StandardCharsets.UTF_8));
			return String.format("%032x", new Object[] { new BigInteger(1, e) });
		}
	}

}

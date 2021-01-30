/**
 *
 */
package kr.co.test.app.oauth.model;

import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import kr.co.test.app.common.BaseVo;
import kr.co.test.app.common.util.SerializableObjectConverter;
import lombok.Getter;
import lombok.Setter;

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
@Getter
@Setter
public class OAuthRefreshToken extends BaseVo {

	private String id;
	private String tokenId;
	private String token;
	private String authentication;
	private String username;
	private String clientId;

	//------------------------------------
	// Getter, Setter 외 기타
	//------------------------------------
	public OAuth2RefreshToken getTokenObject() {
		return token != null ? SerializableObjectConverter.deserializeRefreshToken(token) : null;
	}

	public void setTokenObject(OAuth2RefreshToken token) {
		this.token = SerializableObjectConverter.serializeRefreshToken(token);
	}

	public OAuth2Authentication getAuthenticationObject() {
		return SerializableObjectConverter.deserializeAuthentication(authentication);
	}

	public void setAuthenticationObject(OAuth2Authentication authentication) {
		this.authentication = SerializableObjectConverter.serializeAuthentication(authentication);
	}

}

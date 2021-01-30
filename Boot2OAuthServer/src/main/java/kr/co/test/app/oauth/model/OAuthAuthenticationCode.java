/**
 *
 */
package kr.co.test.app.oauth.model;

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
public class OAuthAuthenticationCode extends BaseVo {

	private String code;
	private String authentication;

	public OAuth2Authentication getAuthenticationObject() {
		return SerializableObjectConverter.deserializeAuthentication(authentication);
	}

	public void setAuthenticationObject(OAuth2Authentication authentication) {
		this.authentication = SerializableObjectConverter.serializeAuthentication(authentication);
	}

}

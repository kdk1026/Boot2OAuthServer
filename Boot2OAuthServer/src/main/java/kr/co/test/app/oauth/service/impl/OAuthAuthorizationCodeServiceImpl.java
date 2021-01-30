/**
 *
 */
package kr.co.test.app.oauth.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.code.RandomValueAuthorizationCodeServices;
import org.springframework.stereotype.Service;

import kr.co.test.app.oauth.mapper.OAuthMapper;
import kr.co.test.app.oauth.model.OAuthAuthenticationCode;

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
@Service("oAuthCodeService")
public class OAuthAuthorizationCodeServiceImpl extends RandomValueAuthorizationCodeServices {

	@Autowired
	private OAuthMapper oAuthMapper;

	@Override
	protected void store(String code, OAuth2Authentication authentication) {
		OAuthAuthenticationCode oAuthCode = new OAuthAuthenticationCode();
		oAuthCode.setCode(code);
		oAuthCode.setAuthenticationObject(authentication);
		oAuthMapper.saveAuthorizationCode(oAuthCode);
	}

	@Override
	protected OAuth2Authentication remove(String code) {
		OAuth2Authentication authentication = null;

		try {
			OAuthAuthenticationCode oAuthCode = oAuthMapper.findAuthenticationByCode(code);
			authentication = oAuthCode != null ? oAuthCode.getAuthenticationObject() : null;

		} catch (Exception e) {
			return null;
		}

		if ( authentication != null ) {
			oAuthMapper.deleteAuthorizationCode(code);
		}

		return authentication;
	}

}

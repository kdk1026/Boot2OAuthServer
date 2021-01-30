/**
 *
 */
package kr.co.test.app.client.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

import kr.co.test.app.common.BaseVo;

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
public class OAuthClientDetails extends BaseVo implements ClientDetails {

	private static final long serialVersionUID = 1L;

	private String clientId;
	private String clientSecret;
	private String scope;
	private String resourceIds;
	private boolean secretRequired;
	private boolean scoped;
	private String authorizedGrantTypes;
	private String redirectUris;
	private String authorities;
	private int accessTokenValidity;
	private int refreshTokenValidity;
	private boolean autoApprove;

	@Override
	public String getClientId() {
		return clientId;
	}

	@Override
	public Set<String> getResourceIds() {
		return toSet(resourceIds);
	}

	@Override
	public boolean isSecretRequired() {
		return secretRequired;
	}

	@Override
	public String getClientSecret() {
		return clientSecret;
	}

	@Override
	public boolean isScoped() {
		return scoped;
	}

	@Override
	public Set<String> getScope() {
		return toSet(scope);
	}

	@Override
	public Set<String> getAuthorizedGrantTypes() {
		return toSet(authorizedGrantTypes);
	}

	@Override
	public Set<String> getRegisteredRedirectUri() {
		return toSet(redirectUris);
	}

	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> auth = new ArrayList<>();
		auth.add(new SimpleGrantedAuthority(authorities));
		return auth;
	}

	@Override
	public Integer getAccessTokenValiditySeconds() {
		return accessTokenValidity;
	}

	@Override
	public Integer getRefreshTokenValiditySeconds() {
		return refreshTokenValidity;
	}

	@Override
	public boolean isAutoApprove(String scope) {
		return autoApprove;
	}

	@Override
	public Map<String, Object> getAdditionalInformation() {
		return null;
	}

	private static Set<String> toSet(String data) {
		if (data == null) return null;

		String[] arr = data.split(",");

		if (arr != null && arr.length > 0) {
			Set<String> set = new HashSet<>();

			for (String e : arr) {
				if (e == null || "".equals(e)) continue;
				set.add(e.trim());
			}

			return set;
		}

		return null;
	}

}

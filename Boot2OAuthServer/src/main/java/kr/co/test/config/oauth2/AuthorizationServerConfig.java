/**
 *
 */
package kr.co.test.config.oauth2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import kr.co.test.app.oauth.service.OAuthJwtTokenStore;

/**
 * <pre>
 * 개정이력
 * -----------------------------------
 * 2021. 1. 6. kdk	최초작성
 * </pre>
 *
 *
 * @author kdk
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	@Qualifier("oAuthUserDetailsService")
	private UserDetailsService userDetailsService;

	@Autowired
	@Qualifier("oAuthClientDetailsService")
	private ClientDetailsService clientDetailsService;

	@Autowired
	@Qualifier("oAuthApprovalStoreService")
	private ApprovalStore approvalStoreService;

	@Autowired
	@Qualifier("oAuthCodeService")
	private AuthorizationCodeServices authorizationCodeServices;

	@Value("${jwt.secret}")
	private String jwtSecret;

	@Bean
	public TokenStore tokenStore() {
		JwtTokenStore tokenStore = new OAuthJwtTokenStore(this.accessTokenConverter());
		tokenStore.setApprovalStore(approvalStoreService);

		return tokenStore;
	}

	@Primary
	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();

		//------------------------------
		// 암호화 키 방식
		//------------------------------
		converter.setSigningKey(this.jwtSecret);

		//------------------------------
		// 공개키 및 개인키 파일 방식
		//------------------------------
//		KeyPair keypair = new KeyStoreKeyFactory(new ClassPathResource("jwt/kr.co.test.oauth2.server.jks"), "storepass".toCharArray()).getKeyPair("oauth", "keypass".toCharArray());
//		converter.setKeyPair(keypair);

		return converter;
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints
			.authenticationManager(authenticationManager)
			.tokenStore(this.tokenStore())
			.accessTokenConverter(this.accessTokenConverter())
			.approvalStore(approvalStoreService)
			.authorizationCodeServices(authorizationCodeServices)
			.userDetailsService(userDetailsService);
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.withClientDetails(clientDetailsService);
	}

}

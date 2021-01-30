/**
 *
 */
package kr.co.test.config.oauth2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * <pre>
 * 개정이력
 * -----------------------------------
 * 2021. 1. 6. kdk	최초작성
 * </pre>
 * OAuth 예제이므로 분리가 안되어 있으나 리소스 서버는 별도의 서버로 존쟁해야 함
 *
 * @author kdk
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	@Value("${jwt.secret}")
	private String jwtSecret;

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
			.antMatchers("/api/**").authenticated()
			.anyRequest().authenticated();
	}

	@Bean("resourceTokenStore")
	public TokenStore tokenStore() {
		return new JwtTokenStore(this.accessTokenConverter());
	}

	@Bean("resourceAccessTokenConverter")
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();

		//------------------------------
		// 암호화 키 방식
		//------------------------------
		converter.setSigningKey(this.jwtSecret);

		//------------------------------
		// 공개키 및 개인키 파일 방식
		//------------------------------
//		Resource resource = new ClassPathResource("jwt/kr.co.test.oauth2.publickey.txt");
//		String publickey = null;
//
//		try {
//			publickey = asString(resource);
//
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		converter.setVerifierKey(publickey);

		return converter;
	}

//	private static String asString(Resource resource) throws IOException {
//		Reader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8);
//		return FileCopyUtils.copyToString(reader);
//	}

}

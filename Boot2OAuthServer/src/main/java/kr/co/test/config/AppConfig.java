package kr.co.test.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import kr.co.test.config.app.OauthDatabaseConfig;
import kr.co.test.config.app.TransactionAspect;

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
@Configuration
@Import({
	OauthDatabaseConfig.class, TransactionAspect.class
})
public class AppConfig {

}

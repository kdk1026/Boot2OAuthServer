/**
 *
 */
package kr.co.test.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.navercorp.lucy.security.xss.servletfilter.XssEscapeServletFilter;

/**
 * <pre>
 * 개정이력
 * -----------------------------------
 * 2021. 1. 28. kdk	최초작성
 * </pre>
 *
 *
 * @author kdk
 */
@Configuration
public class FilterConfig {

	@Bean
	public FilterRegistrationBean<XssEscapeServletFilter> getXssEscapeServletFilter() {
		FilterRegistrationBean<XssEscapeServletFilter> filterRegistration = new FilterRegistrationBean<>();
        filterRegistration.setFilter(new XssEscapeServletFilter());
        filterRegistration.setOrder(1);
        filterRegistration.addUrlPatterns("/*");
		return filterRegistration;
	}

}

/**
 *
 */
package kr.co.test.app.api.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.test.app.api.model.ResUserProfile;

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
@RestController
@RequestMapping("/api/user")
public class ApiUserProfileController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@GetMapping("/me")
	public ResUserProfile getProfile() {

		ResUserProfile resUserProfile = new ResUserProfile();
		resUserProfile.setId( this.getUsername() );
		resUserProfile.setName("홍길동");
		resUserProfile.setGender("M");
		resUserProfile.setEmail( this.getUsername() + "@gmail.com" );
		resUserProfile.setBirthday("19861026");

		return resUserProfile;
	}

	private String getUsername() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		logger.debug("Principal : {}", principal);

		String username = "";

		if (principal instanceof User) {
			User user = (User) principal;
			username = user.getUsername();
		} else {
			username = (String) principal;
		}

		return username;
	}

}

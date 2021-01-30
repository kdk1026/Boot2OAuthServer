package kr.co.test.app.user.service.impl;

import java.util.Hashtable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import kr.co.test.app.user.mapper.OAuthUserDetailsMapper;
import kr.co.test.app.user.model.OAuthUserDetails;

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
@Service("oAuthUserDetailsService")
public class OAuthUserDetailsServiceImpl implements UserDetailsService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private static Hashtable<String, OAuthUserDetails> cache = null;

	@Autowired
	private OAuthUserDetailsMapper oAuthUserDetailsMapper;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.debug("User Name : {}", username);

		OAuthUserDetails user = (cache != null && cache.containsKey(username)) ? cache.get(username) : oAuthUserDetailsMapper.selectUserById(username);

		if (user == null) {
			throw new UsernameNotFoundException(username);
		}

		this.addCache(username, user);

		logger.debug("User : {}", user);

		return user;
	}

	private void addCache(String username, OAuthUserDetails user) {
		if (username == null || user == null) return;

		synchronized (OAuthUserDetailsServiceImpl.class) {
			if (cache == null) {
				cache = new Hashtable<>();
			}

			cache.put(username, user);
		}
	}

	public void removeCache(String username) {
		if (cache != null) {
			synchronized (OAuthUserDetailsServiceImpl.class) {
				cache.remove(username);
			}
		}
	}

}

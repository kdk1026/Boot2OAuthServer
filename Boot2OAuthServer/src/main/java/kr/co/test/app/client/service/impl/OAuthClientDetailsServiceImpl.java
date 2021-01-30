package kr.co.test.app.client.service.impl;

import java.util.Hashtable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

import kr.co.test.app.client.mapper.OAuthClientDetailsMapper;
import kr.co.test.app.client.model.OAuthClientDetails;

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
@Service("oAuthClientDetailsService")
public class OAuthClientDetailsServiceImpl implements ClientDetailsService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private static Hashtable<String, OAuthClientDetails> cache = null;

	@Autowired
	private OAuthClientDetailsMapper oAuthClientDetailsMapper;

	@Override
	public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
		logger.debug("Client Id : {}", clientId);

		OAuthClientDetails client = (cache != null && cache.containsKey(clientId)) ? cache.get(clientId) : oAuthClientDetailsMapper.selectClientById(clientId);

		if (client == null) {
			logger.debug("Client : null");
			throw new ClientRegistrationException(clientId);
		}

		this.addCache(clientId, client);

		logger.debug("Client : {}", client);

		return client;
	}

	private void addCache(String clientId, OAuthClientDetails details) {
		if (clientId == null || details == null) return;

		synchronized (OAuthClientDetailsServiceImpl.class) {
			if (cache == null) {
				cache = new Hashtable<>();
			}

			cache.put(clientId, details);
		}
	}

	public void removeCache(String clientId) {
		if (cache != null) {
			synchronized (OAuthClientDetailsServiceImpl.class) {
				cache.remove(clientId);
			}
		}
	}

}

package kr.co.test;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Base64.Encoder;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class makeAuthorizationRequestHeader {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Test
	public void test() {
		String oauthClientId = "client2";
		String oauthClientSecret = "1q2w3e4r";

		Encoder encoder = Base64.getEncoder();

		String toEncodeString = String.format("%s:%s", oauthClientId, oauthClientSecret);

		logger.debug("toEncodeString : {}", toEncodeString);

		String authorizationRequestHeader = "Basic " + encoder.encodeToString(toEncodeString.getBytes(StandardCharsets.UTF_8));

		logger.debug("AuthorizationRequestHeader : [{}]", authorizationRequestHeader);
	}

}

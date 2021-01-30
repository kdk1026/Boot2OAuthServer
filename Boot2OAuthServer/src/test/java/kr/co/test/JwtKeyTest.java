/**
 *
 */
package kr.co.test;

import java.io.BufferedWriter;
import java.io.StringWriter;
import java.security.KeyPair;
import java.security.PublicKey;
import java.util.Base64;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

/**
 * <pre>
 * 개정이력
 * -----------------------------------
 * 2021. 1. 8. kdk	최초작성
 * </pre>
 *
 *
 * @author kdk
 */
public class JwtKeyTest {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Test
	public void printKeypair() {
		KeyPair keyPair = new KeyStoreKeyFactory(new ClassPathResource("jwt/com.infoin.oauth2.server.jks"), "storepass".toCharArray()).getKeyPair("oauth", "keypass".toCharArray());
		PublicKey publickey = keyPair.getPublic();
		String pem = writePublicKey(publickey);

		logger.info(pem);
	}

	public static String writePublicKey(PublicKey key) {
		return writeObject("PUBLIC KEY", key.getEncoded());
	}

	private static String writeObject(String type, byte[] bytes) {
		final int LINE_LENGTH = 64;
		StringWriter sw = new StringWriter();
		BufferedWriter bw = null;

		try {
			String obj64 = Base64.getEncoder().encodeToString(bytes);
			bw = new BufferedWriter(sw);
			bw.write("-----BEGIN " + type + "-----");
			bw.newLine();
			int index = 0;
			int length = obj64.length() % LINE_LENGTH == 0 ? obj64.length() / LINE_LENGTH : obj64.length() / LINE_LENGTH + 1;
			while (index < length) {
				int start = LINE_LENGTH * index;
				int end = LINE_LENGTH * (index + 1);
				end = end > obj64.length() ? obj64.length() : end;

				String sub = obj64.substring(start, end);
				bw.append(sub);
				bw.newLine();
				index++;
			}
			bw.write("-----END " + type + "-----");
			bw.newLine();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bw != null) {
				try { bw.flush(); } catch (Exception e) { }
				try { bw.close(); } catch (Exception e) { }
			}
		}

		return sw.toString();
	}

}

package kr.co.test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * <pre>
 * 개정이력
 * -----------------------------------
 * 2021. 1. 8. kdk	최초작성
 * </pre>
 * @see <a href="https://java.ihoney.pe.kr/498">Spring Security 5.0 달라진 점 참고</a>
 *
 * @author kdk
 */
@SpringBootTest
public class PasswordEncoderTest {

	@Test
	public void test() {
		bcryptTest();
	}

	public void bcryptTest() {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

		//String sSecret = passwordEncoder.encode("1q2w3e4r");
		String sSecret = "$2a$10$qqd4rnmJL..tlzi5z7D1UupmPefe.w4DMLdpqda2arltZJVTeY/a.";

		assertTrue(passwordEncoder.matches("PPwvcVNH", sSecret));

		System.out.println(sSecret);
	}

}

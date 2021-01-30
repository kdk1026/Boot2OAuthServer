/**
 *
 */
package kr.co.test.app.oauth.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.test.app.oauth.model.OAuthApproval;
import kr.co.test.app.oauth.model.OAuthAuthenticationCode;
import kr.co.test.app.oauth.model.OAuthRefreshToken;

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
@Mapper
public interface OAuthMapper {

	public OAuthAuthenticationCode findAuthenticationByCode(String code);

	public int saveAuthorizationCode(OAuthAuthenticationCode authCode);

	public int deleteAuthorizationCode(String code);


	public List<OAuthApproval> findByUserIdAndClientId(OAuthApproval approval);

	public int saveApproval(OAuthApproval approval);

	public int refreshApproval(OAuthApproval approval);

	public int expireApproval(OAuthApproval approval);

	public int deleteApproval(OAuthApproval approval);


	public OAuthRefreshToken findRefreshTokenByTokenId(String tokenId);

	public int saveRefreshToken(OAuthRefreshToken refreshToken);

	public int deleteRefreshToken(OAuthRefreshToken refreshToken);

}

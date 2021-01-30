package kr.co.test.app.user.mapper;

import org.apache.ibatis.annotations.Mapper;

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
@Mapper
public interface OAuthUserDetailsMapper {

	public OAuthUserDetails selectUserById(String username);

}

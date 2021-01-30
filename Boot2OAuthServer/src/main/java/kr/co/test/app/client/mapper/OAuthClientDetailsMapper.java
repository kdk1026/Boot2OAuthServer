package kr.co.test.app.client.mapper;

import org.apache.ibatis.annotations.Mapper;

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
@Mapper
public interface OAuthClientDetailsMapper {

	public OAuthClientDetails selectClientById(String clientId);

}

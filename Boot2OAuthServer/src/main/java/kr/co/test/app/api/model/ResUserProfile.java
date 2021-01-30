/**
 *
 */
package kr.co.test.app.api.model;

import kr.co.test.app.common.BaseVo;
import lombok.Getter;
import lombok.Setter;

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
@Getter
@Setter
public class ResUserProfile extends BaseVo {

	private String id;
	private String name;
	private String email;
	private String gender;
	private String birthday;

}

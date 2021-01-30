/**
 *
 */
package kr.co.test.app.common;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * <pre>
 * 개정이력
 * -----------------------------------
 * 2021. 1. 7. kdk	최초작성
 * </pre>
 *
 *
 * @author kdk
 */
public class BaseVo {

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
	}

}

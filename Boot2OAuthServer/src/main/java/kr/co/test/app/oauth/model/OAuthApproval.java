package kr.co.test.app.oauth.model;

import java.sql.Timestamp;

import kr.co.test.app.common.BaseVo;

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
public class OAuthApproval extends BaseVo {

	private String userId;
	private String clientId;
	private String scope;
	private String status;
	private Timestamp expiresAt;
	private Timestamp lastModifiedAt;

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Timestamp getExpiresAt() {
		return expiresAt;
	}
	public void setExpiresAt(Timestamp expiresAt) {
		this.expiresAt = expiresAt;
	}
	public void setExpiresAt(long expiresAt) {
		this.expiresAt = new Timestamp(expiresAt);
	}
	public Timestamp getLastModifiedAt() {
		return lastModifiedAt;
	}
	public void setLastModifiedAt(Timestamp lastModifiedAt) {
		this.lastModifiedAt = lastModifiedAt;
	}
	public void setLastModifiedAt(long lastModifiedAt) {
		this.lastModifiedAt = new Timestamp(lastModifiedAt);
	}

}

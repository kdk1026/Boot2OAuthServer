package kr.co.test.app.oauth.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.approval.Approval;
import org.springframework.security.oauth2.provider.approval.Approval.ApprovalStatus;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.stereotype.Service;

import kr.co.test.app.oauth.mapper.OAuthMapper;
import kr.co.test.app.oauth.model.OAuthApproval;

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
@Service("oAuthApprovalStoreService")
public class OAuthApprovalStoreServiceImpl implements ApprovalStore {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private OAuthMapper oAuthMapper;

	private boolean isHandleRevocationsAsExpiry = false;

	public void setHandleRevocationsAsExpiry(boolean isHandleRevocationsAsExpiry) {
		this.isHandleRevocationsAsExpiry = isHandleRevocationsAsExpiry;
	}

	@Override
	public boolean addApprovals(Collection<Approval> approvals) {
		if ( logger.isDebugEnabled() ) {
			logger.debug("adding approvals: {}", approvals);
		}

		boolean isSuccess = true;

		for (Approval approval : approvals) {
			OAuthApproval appr = new OAuthApproval();
			appr.setExpiresAt(approval.getExpiresAt().getTime());
			appr.setStatus((approval.getStatus() == null ? ApprovalStatus.APPROVED : approval.getStatus()).toString());
			appr.setLastModifiedAt(approval.getLastUpdatedAt().getTime());
			appr.setUserId(approval.getUserId());
			appr.setClientId(approval.getClientId());
			appr.setScope(approval.getScope());

			if ( !refreshApproval(appr) ) {
				if ( !saveApproval(appr) ) {
					isSuccess = false;
				}
			}
		}

		return isSuccess;
	}

	private boolean refreshApproval(final OAuthApproval approval) {
		if ( logger.isDebugEnabled() ) {
			logger.debug("refreshing approval: {}", approval);
		}

		int refreshed = oAuthMapper.refreshApproval(approval);

		if ( refreshed != 1 ) {
			return false;
		}

		return true;
	}

	private boolean saveApproval(final OAuthApproval approval) {
		if ( logger.isDebugEnabled() ) {
			logger.debug("saving approval: {}", approval);
		}

		int saved = oAuthMapper.saveApproval(approval);

		if ( saved != 1 ) {
			return false;
		}

		return true;
	}

	@Override
	public boolean revokeApprovals(Collection<Approval> approvals) {
		if ( logger.isDebugEnabled() ) {
			logger.debug("revoking approval: {}", approvals);
		}

		boolean isSuccess = true;

		for (final Approval approval : approvals) {
			if ( isHandleRevocationsAsExpiry ) {
				OAuthApproval appr = new OAuthApproval();
				appr.setExpiresAt(System.currentTimeMillis());
				appr.setUserId(approval.getUserId());
				appr.setClientId(approval.getClientId());
				appr.setScope(approval.getScope());

				int expired = oAuthMapper.expireApproval(appr);

				if ( expired != 1 ) {
					isSuccess = false;
				}
			} else {
				OAuthApproval appr = new OAuthApproval();
				appr.setUserId(approval.getUserId());
				appr.setClientId(approval.getClientId());
				appr.setScope(approval.getScope());

				int deleted = oAuthMapper.deleteApproval(appr);

				if ( deleted != 1 ) {
					isSuccess = false;
				}
			}
		}

		return isSuccess;
	}

	@Override
	public Collection<Approval> getApprovals(String userId, String clientId) {
		List<Approval> coll = null;

		OAuthApproval appr = new OAuthApproval();
		appr.setUserId(userId);
		appr.setClientId(clientId);

		List<OAuthApproval> list = oAuthMapper.findByUserIdAndClientId(appr);

		if ( !list.isEmpty() ) {
			coll = new ArrayList<>();

			for (OAuthApproval approval : list) {
				String userId1 = approval.getUserId();
				String clientId1 = approval.getClientId();
				String scope = approval.getScope();
				Date expiresAt = new Date(approval.getExpiresAt().getTime());
				String status = approval.getStatus();
				Date lastUpdatedAt = new Date(approval.getLastModifiedAt().getTime());

				coll.add(new Approval(userId1, clientId1, scope, expiresAt, ApprovalStatus.valueOf(status), lastUpdatedAt));
			}
		}

		return coll;
	}

}

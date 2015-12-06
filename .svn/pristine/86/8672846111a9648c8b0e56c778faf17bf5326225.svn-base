package com.unison.lottery.api.forgotpassword.bo;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;

import com.unison.lottery.api.model.User;
import com.unison.lottery.api.protocol.common.SystemStatusKeyNames;
import com.unison.lottery.api.protocol.response.model.ForgotPasswordResponse;
import com.unison.lottery.api.protocol.status.IStatusRepository;
import com.unison.lottery.api.protocol.status.ReturnStatus;
import com.xhcms.lottery.lang.EntityStatus;
import com.xhcms.ucenter.persistent.service.IUserManager;
import com.xhcms.ucenter.persistent.service.IUserService;
import com.xhcms.ucenter.persistent.service.IVerifyService;

public class ForgotPasswordBOImpl implements ForgotPasswordBO {

	@Autowired
	private IStatusRepository statusRepository;

	@Autowired
	@Qualifier("forgotPasswordService")
	private IVerifyService forgotPasswordService;

	@Autowired
	private IUserService userService;

	@Autowired
	private IUserManager userManager;

	@Override
	@Transactional
	public ForgotPasswordResponse resetPassword(String verifyCode, User user) {
		ForgotPasswordResponse response = new ForgotPasswordResponse();
		ReturnStatus succStatus = statusRepository
				.getSystemStatusBySystemKey(SystemStatusKeyNames.ForgotPassword.SUCC);
		ReturnStatus failStatus = statusRepository
				.getSystemStatusBySystemKey(SystemStatusKeyNames.ForgotPassword.FAIL);
		response.setReturnStatus(failStatus);
		if (null != user && StringUtils.isNotBlank(verifyCode)) {
			com.xhcms.lottery.commons.data.User userToResetPassword = userService
					.getUserByUsername(user.getLoginName());
			if (null == userToResetPassword) {
				ReturnStatus userNotFoundStatus = statusRepository
						.getSystemStatusBySystemKey(SystemStatusKeyNames.ForgotPassword.USER_NOT_FOUND);
				response.setReturnStatus(userNotFoundStatus);
			} else {
				if (StringUtils.isNotBlank(userToResetPassword.getMobile())
						&& isBindMobile(userToResetPassword)) {
					forgotPasswordService.verify(userToResetPassword.getId(),
							verifyCode);
					userManager.updatePasswd(userToResetPassword.getId(),
							user.getPassword());
					response.setReturnStatus(succStatus);
				} else {
					ReturnStatus userNotBindMobileStatus = statusRepository
							.getSystemStatusBySystemKey(SystemStatusKeyNames.ForgotPassword.USER_NOT_BIND_MOBILE);
					response.setReturnStatus(userNotBindMobileStatus);
				}

			}
		}

		return response;
	}

	private boolean isBindMobile(
			com.xhcms.lottery.commons.data.User userToResetPassword) {
		return userToResetPassword.getVerifyStatus() == EntityStatus.VERIFY_MOBILE
				|| userToResetPassword.getVerifyStatus() == EntityStatus.VERIFY_EMAIL_MOBILE;

	}

}

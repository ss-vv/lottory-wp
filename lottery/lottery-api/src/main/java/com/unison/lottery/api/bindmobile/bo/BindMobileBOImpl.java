package com.unison.lottery.api.bindmobile.bo;


import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.api.model.User;
import com.unison.lottery.api.protocol.common.SystemStatusKeyNames;
import com.unison.lottery.api.protocol.response.model.BindMobileResponse;
import com.unison.lottery.api.protocol.status.IStatusRepository;
import com.unison.lottery.api.protocol.status.ReturnStatus;
import com.xhcms.lottery.commons.persist.service.VerifyException;
import com.xhcms.ucenter.lang.AppCode;
import com.xhcms.ucenter.persistent.service.IVerifyService;

public class BindMobileBOImpl implements BindMobileBO {
	
	@Autowired
	private IStatusRepository statusRepository;

	private Logger logger=LoggerFactory.getLogger(getClass());

	@Autowired
	private IVerifyService verifyMobileService;
	
	@Override
	public BindMobileResponse bindMobileForUser(String phoneNumber,
			String verifyCode, User user) {
		BindMobileResponse bindMobileResponse=new BindMobileResponse();
		ReturnStatus succStatus = statusRepository.getSystemStatusBySystemKey(SystemStatusKeyNames.BindMobile.SUCC);
		ReturnStatus failStatus = statusRepository.getSystemStatusBySystemKey(SystemStatusKeyNames.BindMobile.FAIL);
		bindMobileResponse.setReturnStatus(failStatus);
		try{
			if(phoneNumberIsBlank(phoneNumber)){
				ReturnStatus phoneNumberIsBlankStatus=statusRepository.getSystemStatusBySystemKey(SystemStatusKeyNames.BindMobile.FAIL_PHONE_NUMBER_IS_BLANK);
				bindMobileResponse.setReturnStatus(phoneNumberIsBlankStatus);
			}
			else{
				if(!haveThatVerifyCode(verifyCode)){
					ReturnStatus verifyCodeIsWrongStatus=statusRepository.getSystemStatusBySystemKey(SystemStatusKeyNames.BindMobile.FAIL_VERIFY_CODE_IS_WRONG);
					bindMobileResponse.setReturnStatus(verifyCodeIsWrongStatus);
				}
				else if(phoneNumberIsNotMatchForVerifyCode(phoneNumber, verifyCode)){
					ReturnStatus phoneNumberIsWrongStatus=statusRepository.getSystemStatusBySystemKey(SystemStatusKeyNames.BindMobile.FAIL_PHONE_NUMBER_IS_WRONG);
					bindMobileResponse.setReturnStatus(phoneNumberIsWrongStatus);
				}
				else{
					verifyMobileService.verify(Long.parseLong(user.getId()), verifyCode);
					bindMobileResponse.setReturnStatus(succStatus);
				}
			}
		}
		catch(VerifyException ve){
			if(AppCode.VERIFY_AFTER_EXPIRETIME==ve.getErrorCode()
					||AppCode.VERIFY_INVALID_CODE==ve.getErrorCode()
					||AppCode.VERIFY_CODE_NULL==ve.getErrorCode()){
				ReturnStatus verifyCodeIsWrongStatus=statusRepository.getSystemStatusBySystemKey(SystemStatusKeyNames.BindMobile.FAIL_VERIFY_CODE_IS_WRONG);
				bindMobileResponse.setReturnStatus(verifyCodeIsWrongStatus);
			}
			else if(AppCode.VERIFY_MOBILE_IS_BIND==ve.getErrorCode()){
				ReturnStatus phoneNumberIsBindToOtherWrongStatus=statusRepository.getSystemStatusBySystemKey(SystemStatusKeyNames.BindMobile.FAIL_PHONE_NUMBER_IS_BIND_TO_OTHER);
				bindMobileResponse.setReturnStatus(phoneNumberIsBindToOtherWrongStatus);
			}
			else{
				logger.error("绑定手机时出现异常:{}",ve.getMessage());
				bindMobileResponse.setReturnStatus(failStatus);
			}
		}
		catch(Exception e){
			logger.error("绑定手机时出现异常: ", e);
			bindMobileResponse.setReturnStatus(failStatus);
		}
		return bindMobileResponse;
	}

	private boolean haveThatVerifyCode(String verifyCode) {
		return verifyMobileService.haveVerifyCode(verifyCode);
	}

	private boolean phoneNumberIsNotMatchForVerifyCode(String phoneNumber,String verifyCode){
		return !StringUtils.equals(phoneNumber, verifyMobileService.getPhoneNumberOfVerifyCode(verifyCode));
	}

	private boolean phoneNumberIsBlank(String phoneNumber) {
		return StringUtils.isBlank(phoneNumber);
	}

}

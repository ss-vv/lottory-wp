package com.unison.lottery.api.modifypassword.bo;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.unison.lottery.api.model.User;
import com.unison.lottery.api.protocol.common.SystemStatusKeyNames;
import com.unison.lottery.api.protocol.response.model.BindBankResponse;
import com.unison.lottery.api.protocol.response.model.ModifyPasswordResponse;
import com.unison.lottery.api.protocol.status.IStatusRepository;
import com.unison.lottery.api.protocol.status.ReturnStatus;
import com.xhcms.exception.XHRuntimeException;
import com.xhcms.ucenter.lang.AppCode;
import com.xhcms.ucenter.persistent.service.IUserManager;

public class ModifyPasswordBOImpl implements ModifyPasswordBO {

	@Autowired
	private IStatusRepository statusRepository;
	
	private Logger logger=LoggerFactory.getLogger(getClass());
	
	@Autowired
	private IUserManager userManager;
	
	
	@Override
	@Transactional
	public ModifyPasswordResponse modifyPasswordForUser(String oldPassword,
			String newPassword, User user,String type) {
		ModifyPasswordResponse modifyPasswordResponse=new ModifyPasswordResponse();
		ReturnStatus succStatus=statusRepository.getSystemStatusBySystemKey(SystemStatusKeyNames.ModifyPassword.SUCC);
		ReturnStatus failStatus=statusRepository.getSystemStatusBySystemKey(SystemStatusKeyNames.ModifyPassword.FAIL);
		modifyPasswordResponse.setReturnStatus(failStatus);
		try{
			if(StringUtils.isBlank(newPassword)){
				ReturnStatus newPasswordIsBlankStatus=statusRepository.getSystemStatusBySystemKey(SystemStatusKeyNames.ModifyPassword.FAIL_NEW_PASSWORD_IS_BLANK);
				modifyPasswordResponse.setReturnStatus(newPasswordIsBlankStatus);
			}
			else{
				userManager.updatePasswdForClient(Long.parseLong(user.getId()), oldPassword, newPassword,type);
				modifyPasswordResponse.setReturnStatus(succStatus);
			}
		}
		catch (XHRuntimeException e){
			if (e.getCode() == AppCode.USER_PASSWORD_OLD_WRONG
					|| e.getCode() == com.xhcms.lottery.lang.AppCode.PASSWD_WH_WRONG) {
				ReturnStatus oldPasswordIsWrongStatus = statusRepository
						.getSystemStatusBySystemKey(SystemStatusKeyNames.ModifyPassword.FAIL_OLD_PASSWORD_IS_WRONG);
				modifyPasswordResponse
						.setReturnStatus(oldPasswordIsWrongStatus);
			}
		}
		catch(Exception e){
			e.printStackTrace();
			logger.error("修改密码时出现异常:{}",e.getMessage());
			modifyPasswordResponse.setReturnStatus(failStatus);
		}
		
		return modifyPasswordResponse;
	}

}

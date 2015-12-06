package com.unison.lottery.api.login.bo;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.unison.lottery.api.login.hx.httpclient.apidemo.EasemobChatGroups;
import com.unison.lottery.api.login.hx.httpclient.apidemo.EasemobIMUsers;
import com.unison.lottery.api.login.service.HX_service;
import com.unison.lottery.api.model.User;
import com.unison.lottery.api.protocol.common.SystemStatusKeyNames;
import com.unison.lottery.api.protocol.response.model.LoginResponse;
import com.unison.lottery.api.protocol.status.IStatusRepository;
import com.unison.lottery.api.protocol.status.ReturnStatus;
import com.unison.lottery.api.vGroup.data.Group;
import com.unison.lottery.weibo.uc.service.UserAccountService;
import com.xhcms.commons.util.Text;
import com.xhcms.lottery.commons.data.Account;
import com.xhcms.lottery.commons.data.HX_user;
import com.xhcms.lottery.commons.persist.entity.UserPO;
import com.xhcms.lottery.commons.persist.service.AccountService;
import com.xhcms.lottery.commons.persist.service.DaVGroupService;
import com.xhcms.lottery.commons.persist.service.HX_userService;
import com.xhcms.lottery.lang.EntityStatus;
import com.xhcms.ucenter.persistent.service.IUserManager;
import com.xhcms.ucenter.persistent.service.IUserValidIdManager;
import com.xhcms.ucenter.persistent.service.exception.LoginNameNotFoundException;
import com.xhcms.ucenter.persistent.service.exception.LoginNameOrPasswordBlankException;
import com.xhcms.ucenter.persistent.service.exception.PasswordWrongException;

@Transactional
public class LoginBOImpl implements LoginBO {
	
	@Autowired
	private IUserManager userManager;
	
	@Autowired
	private IStatusRepository statusRepository;

	private Logger logger=LoggerFactory.getLogger(getClass());

	@Autowired
	private IUserValidIdManager iUserValidIdManager;
	
	@Autowired
    private AccountService accountService;
	
	@Autowired
	private HX_userService hX_userService;
	
	@Autowired
	private UserAccountService userAccountService;
	
	@Autowired
	private DaVGroupService daVGroupService;
	
	@Autowired
	private HX_service hX_service;


	@Override
	public LoginResponse loginWithNameAndPassword(User user) {
		LoginResponse loginResponse=new LoginResponse();
		ReturnStatus succStatus=statusRepository.getSystemStatusBySystemKey(SystemStatusKeyNames.Login.SUCC);
		ReturnStatus failStatus=statusRepository.getSystemStatusBySystemKey(SystemStatusKeyNames.Login.FAIL);
		ReturnStatus needRegisteStatus=statusRepository.getSystemStatusBySystemKey(SystemStatusKeyNames.Login.SHOULD_REGISTE);
		loginResponse.setReturnStatus(failStatus);
		try{
			UserPO userPO = null;
			String validId = null;
			if(StringUtils.isNotBlank(user.getUid()) && StringUtils.isNotBlank(user.getPlatType())){
				userPO = userManager.loginByUidAndType(user.getUid(), user.getPlatType());
			} else {
				userPO = userManager.loginByNameAndPasswordMD5(user.getLoginName(), Text.MD5Encode(user.getPassword()));
			}
			if(null!=userPO){
				if(StringUtils.isNotBlank(user.getUid()) && StringUtils.isNotBlank(user.getPlatType())){
					validId = iUserValidIdManager.createValidIdByUidAndType(user.getUid(), user.getPlatType());
				}else{
					validId=iUserValidIdManager.createValidIdByLoginName(userPO.getUsername());
				}
				loginResponse.setUser(createUserFromPO(userPO,validId));
				Account account=accountService.getAccount(userPO.getId());
				if(null!=account){
					if(StringUtils.isNotBlank(account.getBank())){
						account.setAccountName(loginResponse.getUser().getRealName());
					}
				}
				HX_user hx_user = hX_service.createHx_user(user, userPO.getId(), userPO.getNickName());
				loginResponse.setHx_user(hx_user);
				loginResponse.setAccount(account);
				loginResponse.setNickname(userPO.getNickName());
				loginResponse.setImageUrl(userPO.getHeadImageURL());
				loginResponse.setReturnStatus(succStatus);
			} else {
				loginResponse.setReturnStatus(needRegisteStatus);
			}
		}
		catch(LoginNameNotFoundException loginNameNotFoundException){
			ReturnStatus loginNameNotFoundStatus=statusRepository.getSystemStatusBySystemKey(SystemStatusKeyNames.Login.FAIL_LOGIN_NAME_WRONG);
			loginResponse.setReturnStatus(loginNameNotFoundStatus);
			logger.info("loginName:{} not found!",user.getLoginName());
		}
		catch(PasswordWrongException passwordWrongException){
			ReturnStatus passwordWrongStatus=statusRepository.getSystemStatusBySystemKey(SystemStatusKeyNames.Login.FAIL_PASSWORD_WRONG);
			loginResponse.setReturnStatus(passwordWrongStatus);
			logger.info("loginName:{}'s password:{} is wrong!",user.getLoginName(),user.getPassword());
		}
		catch(LoginNameOrPasswordBlankException e){
			ReturnStatus loginNameOrPasswordBlankStatus=statusRepository.getSystemStatusBySystemKey(SystemStatusKeyNames.Login.FAIL_NAME_OR_PASSWORD_BLANK);
			loginResponse.setReturnStatus(loginNameOrPasswordBlankStatus);
		}
		catch(Exception e){
			e.printStackTrace();
			loginResponse.setReturnStatus(failStatus);
		}
		
		return loginResponse;
	}



	private User createUserFromPO(UserPO userPO, String validId) {
		User user=new User();
		if(null!=userPO&&StringUtils.isNotBlank(validId)){
			user.setValidId(validId);
			user.setPhoneNumber(userPO.getMobile());
			user.setIDCard(userPO.getIdNumber());
			user.setRealName(userPO.getRealname());
			if (userPO.getVerifyStatus() == EntityStatus.VERIFY_MOBILE
					|| userPO.getVerifyStatus() == EntityStatus.VERIFY_EMAIL_MOBILE) {
				user.setVerifyPhoneNumber(true);
			}
		}
		return user;
	}

}


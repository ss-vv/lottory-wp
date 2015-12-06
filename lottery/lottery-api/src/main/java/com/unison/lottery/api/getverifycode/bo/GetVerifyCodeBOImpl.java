package com.unison.lottery.api.getverifycode.bo;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;

import com.unison.lottery.api.login.service.ILoginService;
import com.unison.lottery.api.model.User;
import com.unison.lottery.api.protocol.common.SystemStatusKeyNames;
import com.unison.lottery.api.protocol.response.model.GetVerifyCodeResponse;
import com.unison.lottery.api.protocol.status.IStatusRepository;
import com.unison.lottery.api.protocol.status.ReturnStatus;
import com.xhcms.commons.mq.MessageSender;
import com.xhcms.lottery.commons.event.SMSSendMessage;
import com.xhcms.ucenter.persistent.service.IUserService;
import com.xhcms.ucenter.persistent.service.IVerifyService;

public class GetVerifyCodeBOImpl implements GetVerifyCodeBO {
	
	private static final String USER_NAME_PATTERN = "{userName}";
	private static final String VERIFY_CODE_PATTERN = "{verifyCode}";

	@Autowired
	private IStatusRepository statusRepository;

	private Logger logger=LoggerFactory.getLogger(getClass());

	@Autowired
	@Qualifier("verifyMobileService")
	private IVerifyService verifyMobileService;
	
	@Autowired
	@Qualifier("registeService")
	private IVerifyService registeService;
	
	@Autowired
	@Qualifier("forgotPasswordService")
	private IVerifyService forgotPasswordService;
	
	@Autowired
    private MessageSender messageSender;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private ILoginService loginService;
	
	private String smsTemplate="亲爱的用户{userName}:您的验证码是{verifyCode}。";

	@Override
	@Transactional
	public GetVerifyCodeResponse getVerifyCode(String phoneNumber,User user,String type) {
		
		GetVerifyCodeResponse getVerifyCodeResponse=new GetVerifyCodeResponse();
		ReturnStatus succStatus=statusRepository.getSystemStatusBySystemKey(SystemStatusKeyNames.GetVerifyCode.SUCC);
		ReturnStatus failStatus=statusRepository.getSystemStatusBySystemKey(SystemStatusKeyNames.GetVerifyCode.FAIL);
		getVerifyCodeResponse.setReturnStatus(failStatus);
		try{
			if(StringUtils.isBlank(phoneNumber)){
				ReturnStatus phoneNumberIsBlankStatus=statusRepository.getSystemStatusBySystemKey(SystemStatusKeyNames.GetVerifyCode.FAIL_PHONE_NUMBER_IS_BLANK);
				getVerifyCodeResponse.setReturnStatus(phoneNumberIsBlankStatus);
			}
			else{
				String verifyCode = null;
				if(StringUtils.isNotBlank(type)){
					
					Long userId=null;
					if(type.equals("bindMobile")){
						User userResult=loginService.tryGetAsAuthenticate(user);
						if(null!=userResult){
							userId=Long.parseLong(userResult.getId());
							verifyCode=verifyMobileService.generateCode(userId, phoneNumber);
							
						}
						
					}
					else if(type.equals("forgotPassword")){
						com.xhcms.lottery.commons.data.User userToSendVerifyCode=userService.getUserByVerifyedMobile(phoneNumber);
						if(null!=userToSendVerifyCode){
							userId=userToSendVerifyCode.getId();
							verifyCode=forgotPasswordService.generateCode(userId, phoneNumber);
							
						}
						else{
							ReturnStatus phoneNumberIsNotVerifyStatus=statusRepository.getSystemStatusBySystemKey(SystemStatusKeyNames.GetVerifyCode.FAIL_PHONE_NUMBER_IS_NOT_VERIFY);
							getVerifyCodeResponse.setReturnStatus(phoneNumberIsNotVerifyStatus);
						}
						
						
					}
					else if (type.equals("registe")) {
						com.xhcms.lottery.commons.data.User user1 = userService.getUserByVerifyedMobile(phoneNumber);
						if(user1 == null){
							verifyCode=registeService.generateCode(-1, phoneNumber);
						} else {
							ReturnStatus registedCode = statusRepository.getSystemStatusBySystemKey(SystemStatusKeyNames.Registe.REGISTERED);
							getVerifyCodeResponse.setReturnStatus(registedCode);
						}
					}
					if(StringUtils.isNotBlank(verifyCode)){
						SMSSendMessage sm = new SMSSendMessage();
						sm.setMobile(phoneNumber);
						sm.setContent(createContent(userId,verifyCode));
						//sm.setExt("00000");
						//sm.setMsgId("12345");
						messageSender.send(sm);
						getVerifyCodeResponse.setReturnStatus(succStatus);
					}
					
					
					
				}
				
				
			}
		}
		catch(Exception e){
			e.printStackTrace();
			logger.error("获取验证码时出现异常:{}",e.getMessage());
			getVerifyCodeResponse.setReturnStatus(failStatus);
		}
		return getVerifyCodeResponse;
	}

	private String createContent(Long userId, String verifyCode) {
		return verifyCode;

	}

	public String getSmsTemplate() {
		return smsTemplate;
	}

	public void setSmsTemplate(String smsTemplate) {
		this.smsTemplate = smsTemplate;
	}

}

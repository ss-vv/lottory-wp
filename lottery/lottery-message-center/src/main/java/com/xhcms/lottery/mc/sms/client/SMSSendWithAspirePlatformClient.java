package com.xhcms.lottery.mc.sms.client;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.lottery.mc.sms.SMSSendClientInterface;
import com.xhcms.lottery.mc.sms.SMSSendRequest;
import com.xhcms.lottery.mc.sms.SMSSendResult;
import com.xhcms.lottery.mc.sms.SMSSendResultMessage;
import com.xhcms.lottery.mc.sms.SMSSendResultStatus;
import com.xhcms.lottery.mc.sms.platform.AspireSmsPlatform;
import com.xhcms.lottery.mc.sms.platform.AspireSmsPlatformResult;

public class SMSSendWithAspirePlatformClient implements SMSSendClientInterface {
	
	private String phonePattern = "^(\\+86)?\\d{11}$";
	
	@Autowired
	private AspireSmsPlatform aspireSmsPlatform;
	@Override
	public SMSSendResult sendSMS(SMSSendRequest smsSendRequest) {
		SMSSendResult result=new SMSSendResult();
		if(null==smsSendRequest){
			result.setStatus(SMSSendResultStatus.FAIL);
			result.setMessage(SMSSendResultMessage.FAIL);
			return result;
		}
		if(null==smsSendRequest.getParams()||smsSendRequest.getParams().isEmpty()){
			result.setStatus(SMSSendResultStatus.EMPTY_PARAMS);
			result.setMessage(SMSSendResultMessage.EMPTY_PARAMS);
			result.setFailDestPhones(smsSendRequest.getDestPhones());
			return result;
		}
		if(null==smsSendRequest.getDestPhones()||smsSendRequest.getDestPhones().isEmpty()){
			result.setStatus(SMSSendResultStatus.EMPTY_DEST_PHONES);
			result.setMessage(SMSSendResultMessage.EMPTY_DEST_PHONES);
			return result;
		}
		List<String> wrongFormatDestPhones=new ArrayList<String>();
		List<String> toHandleDestPhones=new ArrayList<String>();
		checkDestPhones(smsSendRequest.getDestPhones(),wrongFormatDestPhones,toHandleDestPhones);
		if(!wrongFormatDestPhones.isEmpty()){
			result.setWrongFormatDestPhones(wrongFormatDestPhones);
		}
		
		try{
			if(!toHandleDestPhones.isEmpty()){
				if(smsSendRequest.getParams().containsKey(SMSSendRequest.VERIFY_CODE_KEY)){
					if(handle(toHandleDestPhones,smsSendRequest)){
						result.setStatus(SMSSendResultStatus.SUCC);
						result.setMessage(SMSSendResultMessage.SUCC);
						result.setSuccDestPhones(toHandleDestPhones);
					}
					
					
				}
				else{
					result.setStatus(SMSSendResultStatus.NEED_VERIFY_CODE_PARAM);
					result.setMessage(SMSSendResultMessage.NEED_VERIFY_CODE_PARAM);
					result.setFailDestPhones(toHandleDestPhones);
				}
				
			}
			else{
				result.setStatus(SMSSendResultStatus.FAIL);
				result.setMessage(SMSSendResultMessage.FAIL);
				
			}
			
			
		}catch(Throwable t){
			t.printStackTrace();
			result.setFailDestPhones(toHandleDestPhones);
		}
		return result;
		
	}

	private boolean handle(List<String> toHandleDestPhones,
			SMSSendRequest smsSendRequest) {
		boolean result=false;
		String phone=StringUtils.join(toHandleDestPhones, ",");
		String data=(String) smsSendRequest.getParams().get(SMSSendRequest.VERIFY_CODE_KEY);
		AspireSmsPlatformResult aspireSmsPlatformResult = aspireSmsPlatform.send(phone, null, data);
		if(null!=aspireSmsPlatformResult&&null!=aspireSmsPlatformResult.getRetCode()&&aspireSmsPlatformResult.getRetCode()==1000 ){
			result= true;
		}
		return result;
		
	}

	private void checkDestPhones(List<String> destPhones,
			List<String> wrongFormatDestPhones, List<String> toHandleDestPhones) {
		for(String destPhone:destPhones){
			
			if(destPhone.matches(phonePattern)){
				toHandleDestPhones.add(StringUtils.right(destPhone, 11));
			}
			else{
				wrongFormatDestPhones.add(destPhone);
			}
			
		}
		
	}

	public String getPhonePattern() {
		return phonePattern;
	}

	public void setPhonePattern(String phonePattern) {
		this.phonePattern = phonePattern;
	}

	public AspireSmsPlatform getAspireSmsPlatform() {
		return aspireSmsPlatform;
	}

	public void setAspireSmsPlatform(AspireSmsPlatform aspireSmsPlatform) {
		this.aspireSmsPlatform = aspireSmsPlatform;
	}

}

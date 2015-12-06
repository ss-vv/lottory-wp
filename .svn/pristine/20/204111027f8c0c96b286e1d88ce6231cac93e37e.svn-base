package com.unison.lottery.api.protocol.request.xml.utils;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.unison.lottery.api.model.User; 
import com.unison.lottery.api.protocol.common.VONames;
import com.unison.lottery.api.protocol.request.json.model.JsonMethodRequest;
import com.unison.lottery.api.protocol.request.xml.model.MethodRequest;



public class UserParser implements IUserParser {

	@Override
	public boolean parseUser(MethodRequest methodRequest,
			HttpServletRequest httpRequest) {
		boolean result=false;
		if(null!=methodRequest&&null!=httpRequest){
			User user = new User();
			user.setValidId(methodRequest.validId);
			user.setChannel(methodRequest.channel);
			user.setClientVersion(methodRequest.clientVersion);
			user.setModelName(methodRequest.modelName);
			user.setDeviceId(methodRequest.deviceId);
			user.setPassword(methodRequest.password);
			user.setLoginName(methodRequest.userName);
			user.setPhoneNumber(methodRequest.phoneNumber);
			user.setRealName(methodRequest.realName);
			user.setIp(getRealIP(httpRequest));
			user.setImageUrl(methodRequest.imageUrl);
			if(StringUtils.isNotBlank(methodRequest.nickName)){
				user.setNickName(methodRequest.nickName);
			}
			if(StringUtils.isNotBlank(methodRequest.nickname)){
				user.setNickName(methodRequest.nickname);
			}
			user.setUid(methodRequest.uid);
			user.setPlatType(methodRequest.platType);
			user.setSecurityCode(methodRequest.verifyCode);
			user.setId(methodRequest.userId);
			
			httpRequest.setAttribute(VONames.USER_VO_NAME, user);
			
			result=true;
		}
		return result;
	}
	@Override
	public boolean parseUser(JsonMethodRequest methodRequest,
			HttpServletRequest httpRequest) {
		boolean result=false;
		if(null!=methodRequest&&null!=httpRequest){
			User user = new User();
			user.setValidId(methodRequest.validId);
			user.setChannel(methodRequest.channel);
			user.setClientVersion(methodRequest.clientVersion);
			user.setModelName(methodRequest.modelName);
			user.setDeviceId(methodRequest.deviceId);
			user.setPassword(methodRequest.password);
			user.setLoginName(methodRequest.userName);
			user.setPhoneNumber(methodRequest.phoneNumber);
			user.setRealName(methodRequest.realName);
			user.setIp(getRealIP(httpRequest));
			httpRequest.setAttribute(VONames.USER_VO_NAME, user);
			
			result=true;
		}
		return result;
	}

	private String getRealIP(HttpServletRequest httpRequest) {
		String realIP="unknown";
		String ips=httpRequest.getHeader("X-Forwarded-For");
		if(StringUtils.isNotBlank(ips)){
			String[] ipList = ips.split(",");
			if(null!=ipList&&ipList.length>0){
				for(String ip:ipList){
					if(StringUtils.isNotBlank(ip)
							&&ip.equalsIgnoreCase("unknown")){
						realIP=ip;
						break;
					}
				}
			}
		}
		else{
			realIP=httpRequest.getRemoteAddr();
		}
		
		return realIP;
	}

}

 package com.unison.lottery.api.protocol.request.json.model;

import java.io.BufferedInputStream;

import javax.servlet.ServletInputStream;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.unison.lottery.api.framework.utils.ProtocolUtils;

public class JsonMethodRequest  {

	private static final Logger logger = LoggerFactory.getLogger(JsonMethodRequest.class);
    /**
     * 加上所需的参数
     */
	public String name;
	
	public String channel;
	
	public String clientVersion;
	
	public String validId;
	
	public String modelName;
	
	public String deviceId;
	
	public String password;
	
	public String userName;
	
	public String phoneNumber;
	
	public String realName;
	
	public String imsi;
	
	public String imei;
	
	public String groupid;
	
	public String packageName;
	
    
	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getImsi() {
		return imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getGroupid() {
		return groupid;
	}

	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}

	public String getClientVersion() {
		return clientVersion;
	}

	public void setClientVersion(String clientVersion) {
		this.clientVersion = clientVersion;
	}

	public String getValidId() {
		return validId;
	}

	public void setValidId(String validId) {
		this.validId = validId;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public static JsonMethodRequest parse(ServletInputStream inputStream) {
		JsonMethodRequest methodRequest = null;
		if (null != inputStream) {
			try {
				BufferedInputStream bis = new BufferedInputStream(inputStream);
				String requestStr = ProtocolUtils.readStrings(bis);
				logger.debug("requestStr=" + requestStr);
				if (StringUtils.isNotEmpty(requestStr)) {
					ObjectMapper mapper = new ObjectMapper();
					methodRequest = mapper.readValue(requestStr, JsonMethodRequest.class);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return methodRequest;
	}
}

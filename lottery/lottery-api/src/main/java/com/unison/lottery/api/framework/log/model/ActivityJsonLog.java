package com.unison.lottery.api.framework.log.model;

import org.codehaus.jackson.map.ObjectMapper;
import com.unison.lottery.api.protocol.response.json.model.JsonResponse;

public class ActivityJsonLog {

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
	
	public String userId;
	
	public String time;
	
	public JsonResponse response;
	
	public String packageName;

	public JsonResponse getResponse() {
		return response;
	}



	public void setResponseContent(JsonResponse response) {
		this.response = response;
	}



	public String getTime() {
		return time;
	}



	public void setTime(String time) {
		this.time = time;
	}



	public String getUserId() {
		return userId;
	}



	public void setUserId(String userId) {
		this.userId = userId;
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



	public String getPackageName() {
		return packageName;
	}



	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}


	public String toJsonStr(ActivityJsonLog activityJsonLog) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = mapper.writeValueAsString(activityJsonLog);
		System.out.println("jsonString :  " + jsonString);
		return jsonString;
	}

}

package com.unison.lottery.api.model;

import java.io.Serializable;
import java.util.Date;



import javax.xml.bind.annotation.XmlAttribute;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;



public class User implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1957785644817775382L;
	public static String defaultSexValue="2";
	private String validId;
	private String clientVersion;
	private String channel;
	private String deviceId;
	private String imei;
	private String modelName;
	private String loginName;
	private String nickName;
	private String sex="2";//默认为保密
	private String id;
	private String outterLoginId;//外部登陆id
	private String outterLoginFrom;//外部登录来源
	private Date createTime;
	private Date lastLoginTime;
	private String phoneNumber;
	private String passwordMD5;//密码明文做md5
	private String password;//密码明文
	private String encryptionPasswordMD5;//密码明文md5后加密
	private String securityCode;//验证码
	private Date birthday;
	private String phoneNumberCity;
	private String phoneNumberCityCode;
	private String phoneNumberProvince;
	private String phoneNumberProvinceCode;
	private String headLogoBase64;//头像图片对应的base64编码
	private String email;
	private boolean verifyEmail;
	private boolean verifyPhoneNumber;
	private int point;
	private int playListVersion;
	private String IDCard;//身份证号码
	private String realName;//真实姓名
	private String ip;
	private String imageUrl;
	private String uid;
	private String platType;
	private String token;
	
	private int allowSeeImportantContentInClient=1;//是否允许查看客户端的重要内容，0为不允许，1为允许

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getPlatType() {
		return platType;
	}

	public void setPlatType(String platType) {
		this.platType = platType;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public void setValidId(String validId) {
		this.validId = validId;
	}

	public String getValidId() {
		return validId;
	}

	/**
	 * @return the clientVersion
	 */
	public String getClientVersion() {
		return clientVersion;
	}

	/**
	 * @param clientVersion the clientVersion to set
	 */
	public void setClientVersion(String clientVersion) {
		this.clientVersion = clientVersion;
	}

	/**
	 * @return the channel
	 */
	public String getChannel() {
		return channel;
	}

	/**
	 * @param channel the channel to set
	 */
	public void setChannel(String channel) {
		this.channel = channel;
	}

	
	/**
	 * @return the modelName
	 */
	public String getModelName() {
		return modelName;
	}

	/**
	 * @param modelName the modelName to set
	 */
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	

	

	public String toString(){
		return ReflectionToStringBuilder.toString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	/**
	 * @return the nickName
	 */
	public String getNickName() {
		return nickName;
	}

	/**
	 * @param nickName the nickName to set
	 */
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	/**
	 * @return the sex
	 */
	public String getSex() {
		return sex;
	}

	/**
	 * @param sex the sex to set
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}

	

	public void setOutterLoginId(String outterLoginId) {
		this.outterLoginId = outterLoginId;
	}

	public String getOutterLoginId() {
		return outterLoginId;
	}

	public void setOutterLoginFrom(String outterLoginFrom) {
		this.outterLoginFrom = outterLoginFrom;
	}

	public String getOutterLoginFrom() {
		return outterLoginFrom;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPasswordMD5(String passwordMD5) {
		this.passwordMD5 = passwordMD5;
	}

	public String getPasswordMD5() {
		return passwordMD5;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public void setSecurityCode(String securityCode) {
		this.securityCode = securityCode;
	}

	public String getSecurityCode() {
		return securityCode;
	}

	/**
	 * @return the phoneNumberCity
	 */
	public String getPhoneNumberCity() {
		return phoneNumberCity;
	}

	/**
	 * @param phoneNumberCity the phoneNumberCity to set
	 */
	public void setPhoneNumberCity(String phoneNumberCity) {
		this.phoneNumberCity = phoneNumberCity;
	}

	/**
	 * @return the phoneNumberCityCode
	 */
	public String getPhoneNumberCityCode() {
		return phoneNumberCityCode;
	}

	/**
	 * @param phoneNumberCityCode the phoneNumberCityCode to set
	 */
	public void setPhoneNumberCityCode(String phoneNumberCityCode) {
		this.phoneNumberCityCode = phoneNumberCityCode;
	}

	/**
	 * @return the phoneNumberProvince
	 */
	public String getPhoneNumberProvince() {
		return phoneNumberProvince;
	}

	/**
	 * @param phoneNumberProvince the phoneNumberProvince to set
	 */
	public void setPhoneNumberProvince(String phoneNumberProvince) {
		this.phoneNumberProvince = phoneNumberProvince;
	}

	/**
	 * @return the phoneNumberProvinceCode
	 */
	public String getPhoneNumberProvinceCode() {
		return phoneNumberProvinceCode;
	}

	/**
	 * @param phoneNumberProvinceCode the phoneNumberProvinceCode to set
	 */
	public void setPhoneNumberProvinceCode(String phoneNumberProvinceCode) {
		this.phoneNumberProvinceCode = phoneNumberProvinceCode;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setHeadLogoBase64(String headLogoBase64) {
		this.headLogoBase64 = headLogoBase64;
	}

	public String getHeadLogoBase64() {
		return headLogoBase64;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getImei() {
		return imei;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	public boolean isVerifyEmail() {
		return verifyEmail;
	}

	public void setVerifyEmail(boolean verifyEmail) {
		this.verifyEmail = verifyEmail;
	}

	public boolean isVerifyPhoneNumber() {
		return verifyPhoneNumber;
	}

	public void setVerifyPhoneNumber(boolean verifyPhoneNumber) {
		this.verifyPhoneNumber = verifyPhoneNumber;
	}

	public int getPlayListVersion() {
		return playListVersion;
	}

	public void setPlayListVersion(int playListVersion) {
		this.playListVersion = playListVersion;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getEncryptionPasswordMD5() {
		return encryptionPasswordMD5;
	}

	public void setEncryptionPasswordMD5(String encryptionPasswordMD5) {
		this.encryptionPasswordMD5 = encryptionPasswordMD5;
	}

	public String getIDCard() {
		return IDCard;
	}

	public void setIDCard(String iDCard) {
		IDCard = iDCard;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getAllowSeeImportantContentInClient() {
		return allowSeeImportantContentInClient;
	}

	public void setAllowSeeImportantContentInClient(
			int allowSeeImportantContentInClient) {
		this.allowSeeImportantContentInClient = allowSeeImportantContentInClient;
	}

}

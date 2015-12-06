package com.davcai.lottery.platform.client.yuanchengchupiao.constants;

import java.io.Serializable;

import com.davcai.lottery.platform.client.yuanchengchupiao.util.DateUtil;
import com.davcai.lottery.platform.client.yuanchengchupiao.util.EnCodeAndDecode;
import com.davcai.lottery.platform.client.yuanchengchupiao.util.RandomString;

/**
 * 每个方法的公共参数
 * 必传
 * @author Next
 *
 */
public class PublicParameter implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	
	private String SECRET="ABCDEFGHIJKLMNOP";
	//API_ID 16位字符串，手工预先分配
	private static String ApiId="TEST123456789012";
	//唯一，格式为，API_ID前4位+YYYYMMDDHHMMSS+6位随机码，共24位
	private String MessageID;
	//Unix时间戳
	private String TimeStamp;
	//MD5（API_ID+SECRET+时间戳），SECRET为16位，手工分配
	private String Validate;
	
	
	public static String getApiId() {
		return ApiId;
	}
	
	public String getMessageID() {
		MessageID = ApiId.substring(4)+DateUtil.getDateString()+RandomString.getRandomString(6);
		return MessageID;
	}
	
	public String getTimeStamp() {
		return TimeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		TimeStamp = timeStamp;
		
	}
	public String getValidate() {
		Validate = EnCodeAndDecode.toMD5(ApiId+SECRET+TimeStamp);
		return Validate;
	}
	
	public String getSECRET() {
		return SECRET;
	}
	
	
	
	
	
	
	
}

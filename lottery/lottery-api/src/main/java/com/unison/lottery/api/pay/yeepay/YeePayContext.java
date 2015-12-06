package com.unison.lottery.api.pay.yeepay;

/**
 * <p>
 * Title: 易宝配置
 * </p>
 * 
 * @author yongli zhu
 */
public class YeePayContext {
	private String memberId;
	private String keyValue;
	private String url;
	private String callback;
	
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getKeyValue() {
		return keyValue;
	}
	public void setKeyValue(String keyValue) {
		this.keyValue = keyValue;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getCallback() {
		return callback;
	}
	public void setCallback(String callback) {
		this.callback = callback;
	}
}

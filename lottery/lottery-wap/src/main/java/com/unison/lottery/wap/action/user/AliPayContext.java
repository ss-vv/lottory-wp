package com.unison.lottery.wap.action.user;

import java.io.Serializable;

/**
 * <p>
 * Title: 支付宝配置
 * </p>
 * 
 * @author yongli zhu
 */
public class AliPayContext implements Serializable {

	private static final long serialVersionUID = -2129634333494421312L;
	
	private String partner;
	private String seller;
	private String rsaPrivate;
	private String rsaAlipayPublic;
	private String key;
	private String notifyUrl;
	private String callbackUrl;
	private String merchantUrl;
	
	public String getPartner() {
		return partner;
	}
	public void setPartner(String partner) {
		this.partner = partner;
	}
	public String getSeller() {
		return seller;
	}
	public void setSeller(String seller) {
		this.seller = seller;
	}
	public String getRsaPrivate() {
		return rsaPrivate;
	}
	public void setRsaPrivate(String rsaPrivate) {
		this.rsaPrivate = rsaPrivate;
	}
	public String getRsaAlipayPublic() {
		return rsaAlipayPublic;
	}
	public void setRsaAlipayPublic(String rsaAlipayPublic) {
		this.rsaAlipayPublic = rsaAlipayPublic;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getNotifyUrl() {
		return notifyUrl;
	}
	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
	public String getCallbackUrl() {
		return callbackUrl;
	}
	public void setCallbackUrl(String callbackUrl) {
		this.callbackUrl = callbackUrl;
	}
	public String getMerchantUrl() {
		return merchantUrl;
	}
	public void setMerchantUrl(String merchantUrl) {
		this.merchantUrl = merchantUrl;
	}
}

package com.unison.lottery.api.pay.alipay;

/**
 * <p>
 * Title: 支付宝配置
 * </p>
 * 
 * @author yongli zhu
 */
public class AliPayContext {
	private String partner;
	private String seller;
	private String rsaPrivate;
	private String rsaPublic;
	private String notifyUrl;
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
	public String getRsaPublic() {
		return rsaPublic;
	}
	public void setRsaPublic(String rsaPublic) {
		this.rsaPublic = rsaPublic;
	}
	public String getNotifyUrl() {
		return notifyUrl;
	}
	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
}

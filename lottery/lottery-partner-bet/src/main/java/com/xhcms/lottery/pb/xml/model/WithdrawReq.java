package com.xhcms.lottery.pb.xml.model;

import javax.xml.bind.annotation.XmlAttribute;
public class WithdrawReq {
	
	private String uuid;
	private String transactionId;
	private double money;
	private String partnerUserId;
	private String bankInfo;
	private String cardNum;
	private String mobile;
	private String idCard;
	private String realname;
	private String province;
	private String city;
	
	private String partnerId;//请求附加信息，由程序注入
	private String ipAddress;//请求附加信息，由程序注入
	
	public String getUuid() {
		return uuid;
	}
	@XmlAttribute
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getTransactionId() {
		return transactionId;
	}
	@XmlAttribute
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public double getMoney() {
		return money;
	}
	@XmlAttribute
	public void setMoney(double money) {
		this.money = money;
	}
	public String getPartnerUserId() {
		return partnerUserId;
	}
	@XmlAttribute
	public void setPartnerUserId(String partnerUserId) {
		this.partnerUserId = partnerUserId;
	}
	public String getBankInfo() {
		return bankInfo;
	}
	@XmlAttribute
	public void setBankInfo(String bankInfo) {
		this.bankInfo = bankInfo;
	}
	public String getCardNum() {
		return cardNum;
	}
	@XmlAttribute
	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}
	public String getMobile() {
		return mobile;
	}
	@XmlAttribute
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getIdCard() {
		return idCard;
	}
	@XmlAttribute
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public String getRealname() {
		return realname;
	}
	@XmlAttribute
	public void setRealname(String realname) {
		this.realname = realname;
	}
	public String getPartnerId() {
		return partnerId;
	}
	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	
	public String getProvince() {
		return province;
	}
	@XmlAttribute
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	@XmlAttribute
	public void setCity(String city) {
		this.city = city;
	}
}

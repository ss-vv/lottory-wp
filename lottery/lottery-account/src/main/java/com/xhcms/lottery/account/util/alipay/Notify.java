package com.xhcms.lottery.account.util.alipay;

import org.nuxeo.common.xmap.annotation.XNode;
import org.nuxeo.common.xmap.annotation.XObject;

//@XObject(value = "notify")
public class Notify {
	//@XNode("seller_email")
	private String sellerEmail;
	
	//@XNode("partner")
	private String partner;
	
	//@XNode("payment_type")
	private String paymentType;
	
	//@XNode("buyer_email")
	private String buyerEmail;
	
	//@XNode("trade_no")
	private String tradeNo;
	
	//@XNode("buyer_id")
	private String buyerId;
	
	//@XNode("quantity")
	private String quantity;
	
	//@XNode("total_fee")
	private String totalFee;
	
	//@XNode("use_coupon")
	private String useCoupon;
	
	//@XNode("is_total_fee_adjust")
	private String isTotalFeeAdjust;
	
	//@XNode("price")
	private String price;
	
	//@XNode("out_trade_no")
	private String outTradeNo;
	
	//@XNode("gmt_create")
	private String gmtCreate;

	//@XNode("seller_id")
	private String sellerId;
	
	//@XNode("subject")
	private String subject;
	
	//@XNode("trade_status")
	private String tradeStatus;
	
	//@XNode("discount")
	private String discount;

	private String notifyType;
	
	private String notifyTime;
	
	private String body;
	
	private String gmtPayment;
	
	private String notifyId;

	
	
	public String getNotifyType() {
		return notifyType;
	}

	public void setNotifyType(String notifyType) {
		this.notifyType = notifyType;
	}

	public String getNotifyTime() {
		return notifyTime;
	}

	public void setNotifyTime(String notifyTime) {
		this.notifyTime = notifyTime;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getGmtPayment() {
		return gmtPayment;
	}

	public void setGmtPayment(String gmtPayment) {
		this.gmtPayment = gmtPayment;
	}

	public String getNotifyId() {
		return notifyId;
	}

	public void setNotifyId(String notifyId) {
		this.notifyId = notifyId;
	}

	public String getSellerEmail() {
		return sellerEmail;
	}

	public void setSellerEmail(String sellerEmail) {
		this.sellerEmail = sellerEmail;
	}

	public String getPartner() {
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getBuyerEmail() {
		return buyerEmail;
	}

	public void setBuyerEmail(String buyerEmail) {
		this.buyerEmail = buyerEmail;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public String getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}

	public String getUseCoupon() {
		return useCoupon;
	}

	public void setUseCoupon(String useCoupon) {
		this.useCoupon = useCoupon;
	}

	public String getIsTotalFeeAdjust() {
		return isTotalFeeAdjust;
	}

	public void setIsTotalFeeAdjust(String isTotalFeeAdjust) {
		this.isTotalFeeAdjust = isTotalFeeAdjust;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public String getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(String gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public String getSellerId() {
		return sellerId;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getTradeStatus() {
		return tradeStatus;
	}

	public void setTradeStatus(String tradeStatus) {
		this.tradeStatus = tradeStatus;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}
	
}

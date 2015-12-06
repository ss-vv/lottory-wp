package com.unison.lottery.wap.utils.alipay.util;

import org.nuxeo.common.xmap.annotation.XNode;
import org.nuxeo.common.xmap.annotation.XObject;

@XObject(value = "notify")
public class Notify {
	@XNode("payment_type")
	private String paymentType;
	
	@XNode("subject")
	private String subject;
	
	@XNode("trade_no")
	private String tradeNo;
	
	@XNode("buyer_email")
	private String buyerEmail;
	
	@XNode("gmt_create")
	private String gmtCreate;
	
	@XNode("notify_type")
	private String notifyType;
	
	@XNode("quantity")
	private String quantity;
	
	@XNode("out_trade_no")
	private String outTradeNo;
	
	@XNode("notify_time")
	private String notifyTime;
	
	@XNode("seller_id")
	private String sellerId;
	
	@XNode("trade_status")
	private String tradeStatus;

	@XNode("is_total_fee_adjust")
	private String isTotalFeeAdjust;
	
	@XNode("total_fee")
	private String totalFee;
	
	@XNode("gmt_payment")
	private String gmtPayment;
	
	@XNode("seller_email")
	private String sellerEmail;
	
	@XNode("gmt_close")
	private String gmtClose;
	
	@XNode("price")
	private String price;
	
	@XNode("buyer_id")
	private String buyerId;
	
	@XNode("notify_id")
	private String notifyId;
	
	@XNode("use_coupon")
	private String useCoupon;

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public String getBuyerEmail() {
		return buyerEmail;
	}

	public void setBuyerEmail(String buyerEmail) {
		this.buyerEmail = buyerEmail;
	}

	public String getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(String gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public String getNotifyType() {
		return notifyType;
	}

	public void setNotifyType(String notifyType) {
		this.notifyType = notifyType;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public String getNotifyTime() {
		return notifyTime;
	}

	public void setNotifyTime(String notifyTime) {
		this.notifyTime = notifyTime;
	}

	public String getSellerId() {
		return sellerId;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}

	public String getTradeStatus() {
		return tradeStatus;
	}

	public void setTradeStatus(String tradeStatus) {
		this.tradeStatus = tradeStatus;
	}

	public String getIsTotalFeeAdjust() {
		return isTotalFeeAdjust;
	}

	public void setIsTotalFeeAdjust(String isTotalFeeAdjust) {
		this.isTotalFeeAdjust = isTotalFeeAdjust;
	}

	public String getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}

	public String getGmtPayment() {
		return gmtPayment;
	}

	public void setGmtPayment(String gmtPayment) {
		this.gmtPayment = gmtPayment;
	}

	public String getSellerEmail() {
		return sellerEmail;
	}

	public void setSellerEmail(String sellerEmail) {
		this.sellerEmail = sellerEmail;
	}

	public String getGmtClose() {
		return gmtClose;
	}

	public void setGmtClose(String gmtClose) {
		this.gmtClose = gmtClose;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}

	public String getNotifyId() {
		return notifyId;
	}

	public void setNotifyId(String notifyId) {
		this.notifyId = notifyId;
	}

	public String getUseCoupon() {
		return useCoupon;
	}

	public void setUseCoupon(String useCoupon) {
		this.useCoupon = useCoupon;
	}
}

package com.xhcms.lottery.pb.po;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "lt_partner_withdraw")
public class PartnerWithdrawPO {
	@Id
	@GeneratedValue(generator = "partnerWithdrawTableGenerator")
	@GenericGenerator(name = "partnerWithdrawTableGenerator", strategy = "assigned")
	private String uuid;
	@Column(name = "transaction_id")
	private String transactionId;
	@Column(name = "partner_user_id")
	private String partnerUserId;
	@Column(name = "user_id")
	private long userId;
	@Column(name = "withdraw_id")
	private long withdrawId;
	@Column(name = "money")
	private double money;
	@Column(name = "bank_info")
	private String bankInfo;
	@Column(name = "card_num")
	private String cardNum;
	@Column(name = "mobile")
	private String mobile;
	@Column(name = "id_card")
	private String idCard;
	@Column(name = "realname")
	private String realname;
	@Column(name = "status")
	private int status;
	@Column(name = "withdraw_notice_time")
	private Date withdrawNoticeTime;
	@Column(name = "create_time")
	private Date createTime;
	
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getPartnerUserId() {
		return partnerUserId;
	}

	public void setPartnerUserId(String partnerUserId) {
		this.partnerUserId = partnerUserId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getWithdrawId() {
		return withdrawId;
	}

	public void setWithdrawId(long withdrawId) {
		this.withdrawId = withdrawId;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public String getBankInfo() {
		return bankInfo;
	}

	public void setBankInfo(String bankInfo) {
		this.bankInfo = bankInfo;
	}

	public String getCardNum() {
		return cardNum;
	}

	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getWithdrawNoticeTime() {
		return withdrawNoticeTime;
	}

	public void setWithdrawNoticeTime(Date withdrawNoticeTime) {
		this.withdrawNoticeTime = withdrawNoticeTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PartnerWithdrawPO other = (PartnerWithdrawPO) obj;
		if (uuid == null) {
			if (other.uuid != null)
				return false;
		} else if (!uuid.equals(other.uuid))
			return false;
		return true;
	}
}

package com.unison.lottery.pm.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.xhcms.lottery.commons.persist.entity.BetSchemePO;

/**
 * 奖上奖活动明细表
 * @author wang lei
 */
@Entity
@Table(name = "pm_wingrant")
public class WinGrantPO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3107491813379832055L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "user_id")
	private long userId; // 用户ID
	
	private String username;//用户登录名
	
	@Column(name = "promotion_id")
	private long promotionId;//活动主表id
	
	@Column(name = "granttype_id")
	private long granttypeId;//赠款类型id

	private BigDecimal amount; // 赠款金额
	
	@Column(name = "grant_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date grantTime; // 创建时间

	@Column(name = "operator_id")
	private int operatorId; // 操作人

	@Column(name = "operator_name")
	private String operatorName; // 操作人账户名

	@Column(name = "operator_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date operatorTime; // 操作时间

	private int status; // 状态 0 未处理 1已提交

	@OneToOne
	private BetSchemePO scheme;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public int getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(int operatorId) {
		this.operatorId = operatorId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public long getPromotionId() {
		return promotionId;
	}

	public void setPromotionId(long promotionId) {
		this.promotionId = promotionId;
	}

	public long getGranttypeId() {
		return granttypeId;
	}

	public void setGranttypeId(long granttypeId) {
		this.granttypeId = granttypeId;
	}

	public Date getGrantTime() {
		return grantTime;
	}

	public void setGrantTime(Date grantTime) {
		this.grantTime = grantTime;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public Date getOperatorTime() {
		return operatorTime;
	}

	public void setOperatorTime(Date operatorTime) {
		this.operatorTime = operatorTime;
	}

	public BetSchemePO getScheme() {
		return scheme;
	}

	public void setScheme(BetSchemePO scheme) {
		this.scheme = scheme;
	}
	
}

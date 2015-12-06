package com.xhcms.lottery.commons.persist.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 活动
 * 
 * @author yongli zhu,wang lei
 */
@Entity
@Table(name = "pm_promotion")
public class PromotionPO implements Serializable{

	private static final long serialVersionUID = -6402487065096056439L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	@Column(name = "granttype_id")
	private Long grantTypeId;
	
	@Column(nullable = false, name = "start_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date startTime;
	
	@Column(nullable = false, name = "end_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date endTime;

	private int status;
	
	private String remark;
	
	@Column(name = "create_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	
	@Column(name = "lottery_id")
    private String lotteryId;
	
	/**玩法id列表*/
	@Column(name = "play_ids")
	private String playIds;
	
	/**方案状态列表*/
	@Column(name = "scheme_status")
	private String schemeStatus;
	
	/**方案认购金额*/
	@Column(name = "buy_amount")
	private int buyAmount;
	
	/** 方案税后金额 */
	@Column(name = "after_tax_bonus")
	private BigDecimal afterTaxBonus;
	
	/** 最大方案税后金额 */
	@Column(name = "after_tax_bonus_max")
	private BigDecimal afterTaxBonusMax;
	
	/**方案类型列表*/
	@Column(name = "scheme_types")
	private String schemeTypes;
	
	/** 是否晒单 1，晒单 0，不晒单 */
	private Integer show;
	
	/**包含过关类型列表*/
	@Column(name = "pass_type_ids")
	private String passTypeIds;
	
	/**排除过关类型列表*/
	@Column(name = "pass_type_ids_logic")
	private String passTypeIdsLogic;
	
	/**每人参与次数*/
	@Column(name = "everyone_join_count")
	private int everyoneJoinCount;
	
	/** 与此活动同时计算次数的赠款类型id。 */
	@Column(name = "count_relevance_granType_ids")
	private String countRelevanceGranTypeIds;
	
	/** 参与次数类型：0不计次数,1每天，2每月 */
	@Column(name = "join_count_type")
	private int joinCountType;


	public BigDecimal getAfterTaxBonusMax() {
		return afterTaxBonusMax;
	}

	public void setAfterTaxBonusMax(BigDecimal afterTaxBonusMax) {
		this.afterTaxBonusMax = afterTaxBonusMax;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getGrantTypeId() {
		return grantTypeId;
	}

	public void setGrantTypeId(Long grantTypeId) {
		this.grantTypeId = grantTypeId;
	}

	public String getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(String lotteryId) {
		this.lotteryId = lotteryId;
	}

	public String getPlayIds() {
		return playIds;
	}

	public void setPlayIds(String playIds) {
		this.playIds = playIds;
	}

	public String getSchemeStatus() {
		return schemeStatus;
	}

	public void setSchemeStatus(String schemeStatus) {
		this.schemeStatus = schemeStatus;
	}

	public String getSchemeTypes() {
		return schemeTypes;
	}

	public void setSchemeTypes(String schemeTypes) {
		this.schemeTypes = schemeTypes;
	}

	public int getEveryoneJoinCount() {
		return everyoneJoinCount;
	}

	public void setEveryoneJoinCount(int everyoneJoinCount) {
		this.everyoneJoinCount = everyoneJoinCount;
	}

	public int getJoinCountType() {
		return joinCountType;
	}

	public void setJoinCountType(int joinCountType) {
		this.joinCountType = joinCountType;
	}

	public String getPassTypeIds() {
		return passTypeIds;
	}

	public void setPassTypeIds(String passTypeIds) {
		this.passTypeIds = passTypeIds;
	}

	public String getPassTypeIdsLogic() {
		return passTypeIdsLogic;
	}

	public void setPassTypeIdsLogic(String passTypeIdsLogic) {
		this.passTypeIdsLogic = passTypeIdsLogic;
	}

	public int getBuyAmount() {
		return buyAmount;
	}

	public void setBuyAmount(int buyAmount) {
		this.buyAmount = buyAmount;
	}

	public BigDecimal getAfterTaxBonus() {
		return afterTaxBonus;
	}

	public void setAfterTaxBonus(BigDecimal afterTaxBonus) {
		this.afterTaxBonus = afterTaxBonus;
	}

	public String getCountRelevanceGranTypeIds() {
		return countRelevanceGranTypeIds;
	}

	public void setCountRelevanceGranTypeIds(String countRelevanceGranTypeIds) {
		this.countRelevanceGranTypeIds = countRelevanceGranTypeIds;
	}

	public Integer getShow() {
		return show;
	}

	public void setShow(Integer show) {
		this.show = show;
	}

}

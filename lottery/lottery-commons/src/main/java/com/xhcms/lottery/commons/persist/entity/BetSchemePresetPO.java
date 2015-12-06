package com.xhcms.lottery.commons.persist.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.hibernate.annotations.Formula;

import com.xhcms.lottery.commons.lang.Channel;
import com.xhcms.lottery.commons.lang.Partner;

@Entity
@Table(name = "lt_bet_scheme_preset")
public class BetSchemePresetPO implements Serializable{
	private static final long serialVersionUID = 6861608300564486230L;
	
	public BetSchemePresetPO(){
	}
	
	@Id
	private Long id;

	@Column(nullable= false, name = "lottery_id")
    private String lotteryId;
	
	/**
	 * 玩法
	 */
	@Column(nullable = false, name = "play_id", updatable = false)
	private String playId;

	@Column(nullable = false, name = "pass_type_ids", updatable = false)
	private String passTypeIds;
	
	/**
	 * 方案发起人
	 */
	@Column(nullable = false, name = "sponsor_id", updatable = false)
	private Long sponsorId;
	
	@Column(nullable = false, updatable = false)
	private String sponsor;
	
	/**
	 * 方案类型
	 */
	@Column(name = "type", updatable = false)
	private int type;

	@Column(nullable = false, name = "created_time", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdTime;

	@Column(nullable = false, name = "offtime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date offtime;

	@Column(name = "total_amount", updatable = false)
	private int totalAmount;
	
	@Column(name = "share_ratio", updatable = false)
	private int shareRatio;

	@Column(name = "buy_amount", updatable = false)
	private int buyAmount;

	@Column(name = "floor_amount", updatable = false)
	private int floorAmount;

	@Column(name = "purchased_amount")
	private int purchasedAmount;

	@Column(name = "bet_note", updatable = false)
	private int betNote;

	@Column(name = "match_number", updatable = false)
	private int matchNumber;

	@Column(updatable = false)
	private int multiple;

	@Column(name = "privacy", updatable = false)
	private int privacy;

	@Column(name = "win_note")
	private int winNote;

    @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "award_time")
	private Date awardTime;
	
	@Column(nullable = false, name = "max_bonus", updatable = false)
    private BigDecimal maxBonus;
	
	@Column(name = "expect_bonus")
    private BigDecimal expectBonus;
	
	@Column(name = "pre_tax_bonus")
	private BigDecimal preTaxBonus;

	@Column(nullable = false, name = "after_tax_bonus")
	private BigDecimal afterTaxBonus;

	@Column(name = "ticket_note")
	private int ticketNote;

	@Column(name = "cancel_note")
	private int cancelNote;
	
	@Column(name = "ticket_count", updatable = false)
	private int ticketCount;
	
	@Column(name = "partner_count")
	private int partnerCount;
	
	@Column(name = "sale_status")
    private int saleStatus;
    
    private int status;
    
	@Version
	private int version;

	@Column(name = "is_show_scheme")
	private int showScheme;

	@Column(nullable = false, name = "followed_scheme_id", updatable = false,columnDefinition="Long default -1")
	private Long followedSchemeId;
	
	@Column(name = "following_count")
	private int followingCount;
	
	@Column(name = "follow_total_amount")
	private int followTotalAmount;

	@Column(name = "followed_ratio")
	private int followedRatio;
	
	@Column(name = "follow_scheme_privacy")
	private int followSchemePrivacy;
	
	@Column
	private int recommendation;

	/**
	 * 回报率字段
	 */
	@Formula(value="max_bonus/total_amount")
	private float returnRate;
	
	/**
	 * 合买购买进度
	 */
	@Formula(value="purchased_amount/total_amount")
	private float groupBuyProcessPer;
	
	/**
	 * 保底比例
	 */
	@Formula(value="floor_amount/total_amount")
	private float floorAmountPer;
	
	/**
	 * 总进度
	 */
	@Formula(value="purchased_amount/total_amount + floor_amount/total_amount")
	private float totalProcessPer;
	
	@Formula(value="after_tax_bonus/total_amount")
	private float actualReturnRate;
	/**
	 * 表示来自客户端还是网站
	 */
	@Column(nullable = false, name = "channel")
	private String channel=Channel.WWW;

	/**
	 * 合作方
	 */
	@Column(nullable = false, name = "partner")
	private String partner=Partner.DAVCAI_WWW;
	
	@Column(name = "check_status")
	private int checkStatus;
	
	public int getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(int checkStatus) {
		this.checkStatus = checkStatus;
	}

	public Long getId() {
		return id;
	}

	public String getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(String lotteryId) {
		this.lotteryId = lotteryId;
	}

	public String getPlayId() {
		return playId;
	}

	public void setPlayId(String playId) {
		this.playId = playId;
	}

	public String getPassTypeIds() {
		return passTypeIds;
	}

	public void setPassTypeIds(String passTypeIds) {
		this.passTypeIds = passTypeIds;
	}

	public Long getSponsorId() {
		return sponsorId;
	}

	public void setSponsorId(Long sponsorId) {
		this.sponsorId = sponsorId;
	}

	public String getSponsor() {
		return sponsor;
	}

	public void setSponsor(String sponsor) {
		this.sponsor = sponsor;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Date getOfftime() {
		return offtime;
	}

	public void setOfftime(Date offtime) {
		this.offtime = offtime;
	}

	public int getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(int totalAmount) {
		this.totalAmount = totalAmount;
	}

	public int getShareRatio() {
		return shareRatio;
	}

	public void setShareRatio(int shareRatio) {
		this.shareRatio = shareRatio;
	}

	public int getBuyAmount() {
		return buyAmount;
	}

	public void setBuyAmount(int buyAmount) {
		this.buyAmount = buyAmount;
	}

	public int getFloorAmount() {
		return floorAmount;
	}

	public void setFloorAmount(int floorAmount) {
		this.floorAmount = floorAmount;
	}

	public int getPurchasedAmount() {
		return purchasedAmount;
	}

	public void setPurchasedAmount(int purchasedAmount) {
		this.purchasedAmount = purchasedAmount;
	}

	public int getBetNote() {
		return betNote;
	}

	public void setBetNote(int betNote) {
		this.betNote = betNote;
	}

	public int getMatchNumber() {
		return matchNumber;
	}

	public void setMatchNumber(int matchNumber) {
		this.matchNumber = matchNumber;
	}

	public int getMultiple() {
		return multiple;
	}

	public void setMultiple(int multiple) {
		this.multiple = multiple;
	}

	public int getPrivacy() {
		return privacy;
	}

	public void setPrivacy(int privacy) {
		this.privacy = privacy;
	}

	public int getWinNote() {
		return winNote;
	}

	public void setWinNote(int winNote) {
		this.winNote = winNote;
	}

	public Date getAwardTime() {
		return awardTime;
	}

	public void setAwardTime(Date awardTime) {
		this.awardTime = awardTime;
	}

	public BigDecimal getMaxBonus() {
		return maxBonus;
	}

	public void setMaxBonus(BigDecimal maxBonus) {
		this.maxBonus = maxBonus;
	}

	public BigDecimal getExpectBonus() {
		return expectBonus;
	}

	public void setExpectBonus(BigDecimal expectBonus) {
		this.expectBonus = expectBonus;
	}

	public BigDecimal getPreTaxBonus() {
		return preTaxBonus;
	}

	public void setPreTaxBonus(BigDecimal preTaxBonus) {
		this.preTaxBonus = preTaxBonus;
	}

	public BigDecimal getAfterTaxBonus() {
		return afterTaxBonus;
	}

	public void setAfterTaxBonus(BigDecimal afterTaxBonus) {
		this.afterTaxBonus = afterTaxBonus;
	}

	public int getTicketNote() {
		return ticketNote;
	}

	public void setTicketNote(int ticketNote) {
		this.ticketNote = ticketNote;
	}

	public int getCancelNote() {
		return cancelNote;
	}

	public void setCancelNote(int cancelNote) {
		this.cancelNote = cancelNote;
	}

	public int getTicketCount() {
		return ticketCount;
	}

	public void setTicketCount(int ticketCount) {
		this.ticketCount = ticketCount;
	}

	public int getPartnerCount() {
		return partnerCount;
	}

	public void setPartnerCount(int partnerCount) {
		this.partnerCount = partnerCount;
	}

	public int getSaleStatus() {
		return saleStatus;
	}

	public void setSaleStatus(int saleStatus) {
		this.saleStatus = saleStatus;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public int getShowScheme() {
		return showScheme;
	}

	public void setShowScheme(int showScheme) {
		this.showScheme = showScheme;
	}

	public Long getFollowedSchemeId() {
		return followedSchemeId;
	}

	public void setFollowedSchemeId(Long followedSchemeId) {
		this.followedSchemeId = followedSchemeId;
	}

	public int getFollowingCount() {
		return followingCount;
	}

	public void setFollowingCount(int followingCount) {
		this.followingCount = followingCount;
	}

	public int getFollowTotalAmount() {
		return followTotalAmount;
	}

	public void setFollowTotalAmount(int followTotalAmount) {
		this.followTotalAmount = followTotalAmount;
	}

	public int getFollowedRatio() {
		return followedRatio;
	}

	public void setFollowedRatio(int followedRatio) {
		this.followedRatio = followedRatio;
	}

	public int getFollowSchemePrivacy() {
		return followSchemePrivacy;
	}

	public void setFollowSchemePrivacy(int followSchemePrivacy) {
		this.followSchemePrivacy = followSchemePrivacy;
	}

	public int getRecommendation() {
		return recommendation;
	}

	public void setRecommendation(int recommendation) {
		this.recommendation = recommendation;
	}

	public float getReturnRate() {
		return returnRate;
	}

	public void setReturnRate(float returnRate) {
		this.returnRate = returnRate;
	}

	public float getGroupBuyProcessPer() {
		return groupBuyProcessPer;
	}

	public void setGroupBuyProcessPer(float groupBuyProcessPer) {
		this.groupBuyProcessPer = groupBuyProcessPer;
	}

	public float getFloorAmountPer() {
		return floorAmountPer;
	}

	public void setFloorAmountPer(float floorAmountPer) {
		this.floorAmountPer = floorAmountPer;
	}

	public float getTotalProcessPer() {
		return totalProcessPer;
	}

	public void setTotalProcessPer(float totalProcessPer) {
		this.totalProcessPer = totalProcessPer;
	}

	public float getActualReturnRate() {
		return actualReturnRate;
	}

	public void setActualReturnRate(float actualReturnRate) {
		this.actualReturnRate = actualReturnRate;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getPartner() {
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}

	public void setId(Long id) {
		this.id = id;
	}
}

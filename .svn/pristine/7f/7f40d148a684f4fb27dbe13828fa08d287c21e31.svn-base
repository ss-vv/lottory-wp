package com.davcai.data.statistic.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.annotations.Formula;

import com.xhcms.lottery.commons.lang.Channel;
import com.xhcms.lottery.commons.lang.Partner;

public class BetScheme {
	public BetScheme(){
		this.followedSchemeId=-1L;
	}

	private Long id;

    private String lotteryId;
	
	/**
	 * 玩法
	 */
	private String playId;

	private String passTypeIds;
	
	/**
	 * 方案发起人
	 */
	private Long sponsorId;
	
	private String sponsor;
	
	/**
	 * 方案类型
	 */
	private int type;

	private Date createdTime;

	private Date offtime;

	private int totalAmount;
	
	private int shareRatio;

	private int buyAmount;

	private int floorAmount;

	private int purchasedAmount;

	private int betNote;

	private int matchNumber;

	private int multiple;

	private int privacy;

	private int winNote;

	private Date awardTime;
	
    private BigDecimal maxBonus;
	
    private BigDecimal expectBonus;
	
	private BigDecimal preTaxBonus;

	private BigDecimal afterTaxBonus;

	private int ticketNote;

	private int cancelNote;
	
	private int ticketCount;
	
	private int partnerCount;
	
    private int saleStatus;
    
    private int status;
    
	private int version;

	private int showScheme;

	private Long followedSchemeId;
	
	private int followingCount;
	
	private int followTotalAmount;

	private int followedRatio;
	
	private int followSchemePrivacy;
	
	private int recommendation;

	/**
	 * 回报率字段
	 */
	private float returnRate;
	
	/**
	 * 合买购买进度
	 */
	private float groupBuyProcessPer;
	
	/**
	 * 保底比例
	 */
	private float floorAmountPer;
	
	/**
	 * 总进度
	 */
	private float totalProcessPer;
	
	private float actualReturnRate;
	/**
	 * 表示来自客户端还是网站
	 */
	private String channel=Channel.WWW;
	
	/**
	 * 是否预兑奖 0 否 1 是
	 */
	private int isPresetAward;

	/**
	 * 是否发不过晒单
	 */
	private int isPublishShow;
	
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

	/**
	 * 合作方
	 */
	private String partner=Partner.DAVCAI_WWW;
	
	/**
	 * 方案公开时间
	 */
	private Date publicTime;
	
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Date getAwardTime() {
        return awardTime;
    }

    public void setAwardTime(Date awardTime) {
        this.awardTime = awardTime;
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

	public Long getFollowedSchemeId() {
		return followedSchemeId;
	}

	public void setFollowedSchemeId(Long followedSchemeId) {
		this.followedSchemeId = followedSchemeId;
	}

	public int getRecommendation() {
		return recommendation;
	}

	public void setRecommendation(int recommendation) {
		this.recommendation = recommendation;
	}

	public int getShowScheme() {
		return showScheme;
	}

	public void setShowScheme(int showScheme) {
		this.showScheme = showScheme;
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

	public float getReturnRate() {
		return returnRate;
	}

	public void setReturnRate(float returnRate) {
		this.returnRate = returnRate;
	}

	public float getActualReturnRate() {
		return actualReturnRate;
	}

	public void setActualReturnRate(float actualReturnRate) {
		this.actualReturnRate = actualReturnRate;
	}
	
	public Date getPublicTime() {
		return publicTime;
	}

	public void setPublicTime(Date publicTime) {
		this.publicTime = publicTime;
	}

	public String toString(){
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	public int getIsPresetAward() {
		return isPresetAward;
	}

	public void setIsPresetAward(int isPresetAward) {
		this.isPresetAward = isPresetAward;
	}

	public int getIsPublishShow() {
		return isPublishShow;
	}

	public void setIsPublishShow(int isPublishShow) {
		this.isPublishShow = isPublishShow;
	}

}

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
import javax.persistence.Version;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "lt_ticket")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TicketPO implements Serializable {

    private static final long serialVersionUID = -8314644574776562323L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "platform_id")
    private Long platformId = new Long(0L);
    
    @Column(name = "lottery_platform_id")
    private String lotteryPlatformId ;

    @Column(nullable = false, name = "scheme_id", updatable = false)
    private Long schemeId;

    @Column(nullable = false, name = "play_id", updatable = false)
    private String playId;
    
    @Column(nullable = false, name = "pass_type_id", updatable = false)
    private String passTypeId;
    
    @Column(nullable = false, updatable = false)
    private String code;

    @Column(name="actual_code")
    private String actualCode;
    
    private String odds;
    
    @Column(name="actual_odds")
    private String actualOdds;
    
    @Column(updatable = false)
    private int note;
    
    @Column(updatable = false)
    private int money;

    @Column(updatable = false)
    private int multiple;
    
    @Column(name = "expect_bonus", updatable = false)
    private BigDecimal expectBonus;

    @Column(name = "pre_tax_bonus")
    private BigDecimal preTaxBonus;
    
    @Column(name = "after_tax_bonus")
    private BigDecimal afterTaxBonus;

    
    private String issue;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_time", nullable = false, updatable = false)
    private Date createdTime;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "final_state_time")
    private Date FinalStateTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ticket_succ_time")
    private Date ticketSuccTime; 
    
	private String number;
    
    private String message;
    
    private int status;
    
    @Column(name="actual_status", nullable = false)
    private int actualStatus = 0;
    
    /**
     * 是否预兑奖
     * 0 否
     * 1 是
     */
    @Column(name="preset_award")
    private int isPresetAward;
    
    @Version
    private Integer version;
    
    @Column(name="platform_bet_code")
    private String platformBetCode;
    
    @Column(name="davcai_odds")
    private String davcaiOdds;
    
    @Column(name="min_match_playing_time",nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date minMatchPlayingTime;//票包含的比赛中，最早的开赛时间

	public int getIsPresetAward() {
		return isPresetAward;
	}

	public void setIsPresetAward(int isPresetAward) {
		this.isPresetAward = isPresetAward;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSchemeId() {
        return schemeId;
    }

    public void setSchemeId(Long schemeId) {
        this.schemeId = schemeId;
    }

    public String getPlayId() {
        return playId;
    }

    public void setPlayId(String playId) {
        this.playId = playId;
    }

    public String getPassTypeId() {
        return passTypeId;
    }

    public void setPassTypeId(String passTypeId) {
        this.passTypeId = passTypeId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getOdds() {
        return odds;
    }

    public void setOdds(String odds) {
        this.odds = odds;
    }

    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getMultiple() {
        return multiple;
    }

    public void setMultiple(int multiple) {
        this.multiple = multiple;
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

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

	public Long getPlatformId() {
		return platformId;
	}

	public void setPlatformId(Long platformId) {
		this.platformId = platformId;
	}

	public String getActualCode() {
		return actualCode;
	}

	public void setActualCode(String actualCode) {
		this.actualCode = actualCode;
	}

	public String getActualOdds() {
		return actualOdds;
	}

	public void setActualOdds(String actualOdds) {
		this.actualOdds = actualOdds;
	}

	public int getActualStatus() {
		return actualStatus;
	}

	public void setActualStatus(int actualStatus) {
		this.actualStatus = actualStatus;
	}

	 public Date getFinalStateTime() {
		return FinalStateTime;
	}

	public void setFinalStateTime(Date finalStateTime) {
		FinalStateTime = finalStateTime;
	}

	public String getLotteryPlatformId() {
		return lotteryPlatformId;
	}

	public void setLotteryPlatformId(String lotteryPlatformId) {
		this.lotteryPlatformId = lotteryPlatformId;
	}

	public String getPlatformBetCode() {
		return platformBetCode;
	}

	public void setPlatformBetCode(String platformBetCode) {
		this.platformBetCode = platformBetCode;
	}

	public String getDavcaiOdds() {
		return davcaiOdds;
	}

	public void setDavcaiOdds(String davcaiOdds) {
		this.davcaiOdds = davcaiOdds;
	}

	public Date getTicketSuccTime() {
		return ticketSuccTime;
	}

	public void setTicketSuccTime(Date ticketSuccTime) {
		this.ticketSuccTime = ticketSuccTime;
	}

	public Date getMinMatchPlayingTime() {
		return minMatchPlayingTime;
	}

	public void setMinMatchPlayingTime(Date minMatchPlayingTime) {
		this.minMatchPlayingTime = minMatchPlayingTime;
	}
}

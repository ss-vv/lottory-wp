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
import javax.persistence.Transient;
import javax.persistence.Version;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "lt_ticket_preset")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TicketPresetPO implements Serializable {

	private static final long serialVersionUID = -3084972335200855271L;

	@Id
    private Long id;

    @Column(nullable = false, name = "platform_id")
    private Long platformId = new Long(0L);

    @Column(nullable = false, name = "scheme_id", updatable = false)
    private Long schemeId;

    @Column(nullable = false, name = "play_id", updatable = false)
    private String playId;
    
    @Column(nullable = false, name = "pass_type_id", updatable = false)
    private String passTypeId;
    
    @Column(nullable = false, updatable = false)
    private String code;

    @Column(name="actual_code", nullable = false, updatable = false)
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

	private String number;
    
    private String message;
    
    private int status;
    
    @Column(name="actual_status", nullable = false)
    private int actualStatus = 0;
    
    @Version
    private Integer version;

    @Column(name = "check_status")
	private int checkStatus;
	
    @Transient
    private int winNotes;
    
	public int getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(int checkStatus) {
		this.checkStatus = checkStatus;
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
    	if(null == afterTaxBonus){
    		return BigDecimal.ZERO;
    	}
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

	public int getWinNotes() {
		return winNotes;
	}

	public void setWinNotes(int winNotes) {
		this.winNotes = winNotes;
	}
	
	public String toString(){
		return ReflectionToStringBuilder.toString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
}

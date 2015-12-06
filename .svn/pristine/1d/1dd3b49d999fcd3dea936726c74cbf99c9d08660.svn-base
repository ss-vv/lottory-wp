package com.xhcms.lottery.commons.persist.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "lt_bet_partner")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BetPartnerPO implements Serializable {

	private static final long serialVersionUID = -7098949959141578962L;

	public BetPartnerPO(){
		this.commission=new BigDecimal(0);
	}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "scheme_id", nullable = false)
	private BetSchemePO scheme;

	@Column(nullable = false, name = "user_id", updatable = false)
	private Long userId;
	
	@Column(nullable = false, updatable = false)
	private String username;

	@Column(nullable = false, name = "bet_amount", updatable = false)
	private int betAmount;

	@Column(name = "win_amount")
	private BigDecimal winAmount;

	@Column(nullable = false, name = "created_time", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdTime;

	@Column(name = "commission")
	private BigDecimal commission;
	
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BetSchemePO getScheme() {
        return scheme;
    }

    public void setScheme(BetSchemePO scheme) {
        this.scheme = scheme;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public int getBetAmount() {
        return betAmount;
    }

    public void setBetAmount(int betAmount) {
        this.betAmount = betAmount;
    }

    public BigDecimal getWinAmount() {
        return winAmount;
    }

    public void setWinAmount(BigDecimal winAmount) {
        this.winAmount = winAmount;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

	public BigDecimal getCommission() {
		return commission;
	}

	public void setCommission(BigDecimal commission) {
		this.commission = commission;
	}
	
}

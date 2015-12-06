package com.xhcms.lottery.commons.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class BetPartner implements Serializable {

    private static final long serialVersionUID = -3272723070148265038L;

    private Long id;

    private Long schemeId;

    private BetScheme betScheme;

    private Long userId;
    
    private String username;

    private int betAmount;

    private BigDecimal winAmount;

    private Date createdTime;
    
    private BigDecimal commission;

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

    public BetScheme getBetScheme() {
        return betScheme;
    }

    public void setBetScheme(BetScheme betScheme) {
        this.betScheme = betScheme;
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

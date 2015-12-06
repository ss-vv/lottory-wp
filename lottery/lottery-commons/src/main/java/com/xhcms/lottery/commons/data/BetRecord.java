package com.xhcms.lottery.commons.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class BetRecord implements Serializable{

    private static final long serialVersionUID = 7439124494762293575L;

    private long id;
    
    private String lotteryId;
    
    private String playId;
    
    private String issue;
    
    private long sponsorId;
    
    private String sponsor;
    /**进度*/
    private int progress;
    
    private int status;
    
    private int type;
    
    private int saleStatus;
    
    private int showScheme;
    
    private int isPublishShow;
    
    private int betAmount;
    
    private BigDecimal winAmount = BigDecimal.ZERO;
    
    private Date createdTime;
    
    private String playName;
    
    private String shareUrl;
    
    private PMWeekWinnersRecord record;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPlayId() {
        return playId;
    }

    public void setPlayId(String playId) {
        this.playId = playId;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public long getSponsorId() {
        return sponsorId;
    }

    public void setSponsorId(long sponsorId) {
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

    public int getSaleStatus() {
        return saleStatus;
    }

    public void setSaleStatus(int saleStatus) {
        this.saleStatus = saleStatus;
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

    public String getLotteryId() {
        return lotteryId;
    }

    public void setLotteryId(String lotteryId) {
        this.lotteryId = lotteryId;
    }

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getShowScheme() {
		return showScheme;
	}

	public void setShowScheme(int showScheme) {
		this.showScheme = showScheme;
	}

	public int getProgress() {
		return progress;
	}

	public void setProgress(int progress) {
		this.progress = progress;
	}

	
	public int getIsPublishShow() {
		return isPublishShow;
	}

	
	public void setIsPublishShow(int isPublishShow) {
		this.isPublishShow = isPublishShow;
	}

	public String getPlayName() {
		return playName;
	}

	public void setPlayName(String playName) {
		this.playName = playName;
	}

	public String getShareUrl() {
		return shareUrl;
	}

	public void setShareUrl(String shareUrl) {
		this.shareUrl = shareUrl;
	}

	public PMWeekWinnersRecord getRecord() {
		return record;
	}

	public void setRecord(PMWeekWinnersRecord record) {
		this.record = record;
	}
    
    
    
}

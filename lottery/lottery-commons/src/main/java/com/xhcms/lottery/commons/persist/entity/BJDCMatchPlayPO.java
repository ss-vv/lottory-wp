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

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 北单赛事玩法赔率信息
 */
@Entity
@Table(name = "bjdc_match_play")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class BJDCMatchPlayPO implements Serializable {

    private static final long serialVersionUID = 562501281359572198L;

    @Id
    private String id;
    
    @Column(nullable = false, name = "issue_number")
	private String issueNumber;

    @Column(nullable = false, name = "match_id")
    private Long matchId;

    @Column(nullable = false, name = "play_id")
    private String playId;

    @Column(nullable = false)
    private String options;

    @Column(nullable = false)
    private String odds;
    
    @Column(name="final_odds")
    private String finalOdds;
    
    @Column(name="concede_points")
    private BigDecimal concedePoints;

    @Column(name = "win_option")
    private String winOption;

    @Column(name="created_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdTime;
	
	@Column(name="update_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateTime;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getMatchId() {
        return matchId;
    }

    public void setMatchId(Long matchId) {
        this.matchId = matchId;
    }

    public String getPlayId() {
        return playId;
    }

    public void setPlayId(String playId) {
        this.playId = playId;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public String getOdds() {
        return odds;
    }

    public void setOdds(String odds) {
        this.odds = odds;
    }
    
    public String getWinOption() {
        return winOption;
    }
    
    public void setWinOption(String winOption) {
        this.winOption = winOption;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

	public String getIssueNumber() {
		return issueNumber;
	}

	public void setIssueNumber(String issueNumber) {
		this.issueNumber = issueNumber;
	}

	public String getFinalOdds() {
		return finalOdds;
	}

	public void setFinalOdds(String finalOdds) {
		this.finalOdds = finalOdds;
	}

	public BigDecimal getConcedePoints() {
		return concedePoints;
	}

	public void setConcedePoints(BigDecimal concedePoints) {
		this.concedePoints = concedePoints;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}

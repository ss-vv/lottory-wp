package com.xhcms.lottery.commons.data;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * <p>Title: 投注方案关联赛事VO</p>
 * @author Wang Lei
 */
public class BetMatch implements Serializable {

    private static final long serialVersionUID = -5613047871285644639L;

    private Long id;

    private Long schemeId;

    private Long matchId;

    private String code;
    
    private String cnCode;
    
    private String odds;

    private Date entrustDeadline;
    
    private Date playingTime;
    
    /** 胆码 */
    protected boolean seed;
    
    /**默认比分（让球、预设总分）*/
    protected float defaultScore;
    
    protected String playId;
    private String playName;
    
    /**注释*/
	private String annotation;
	
	private boolean matchWin;
	
	private String betOptions;
	
	private String result;
    
	/**
	 * 竞彩官网赛事ID，主要给“安瑞智赢”出票接口使用
	 */
	private Long jcOfficialMatchId;
	
    public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getBetOptions() {
		return betOptions;
	}

	public void setBetOptions(String betOptions) {
		this.betOptions = betOptions;
	}

	public boolean isMatchWin() {
		return matchWin;
	}

	public void setMatchWin(boolean matchWin) {
		this.matchWin = matchWin;
	}

	public String getPlayId() {
		return playId;
	}

	public void setPlayId(String playId) {
		this.playId = playId;
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

    public Long getMatchId() {
        return matchId;
    }

    public void setMatchId(Long matchId) {
        this.matchId = matchId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCnCode() {
        return cnCode;
    }

    public void setCnCode(String cnCode) {
        this.cnCode = cnCode;
    }

    public String getOdds() {
        return odds;
    }

    public void setOdds(String odds) {
        this.odds = odds;
    }

    public Date getEntrustDeadline() {
        return entrustDeadline;
    }

    public void setEntrustDeadline(Date entrustDeadline) {
        this.entrustDeadline = entrustDeadline;
    }

	public boolean isSeed() {
		return seed;
	}

	public void setSeed(boolean seed) {
		this.seed = seed;
	}

	public float getDefaultScore() {
		return defaultScore;
	}

	public void setDefaultScore(float defaultScore) {
		this.defaultScore = defaultScore;
	}
	
	public String toString(){
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	public String getAnnotation() {
		return annotation;
	}

	public void setAnnotation(String annotation) {
		this.annotation = annotation;
	}

	public String getPlayName() {
		return playName;
	}

	public void setPlayName(String playName) {
		this.playName = playName;
	}

	public Long getJcOfficialMatchId() {
		return jcOfficialMatchId;
	}

	public void setJcOfficialMatchId(Long jcOfficialMatchId) {
		this.jcOfficialMatchId = jcOfficialMatchId;
	}

	public Date getPlayingTime() {
		return playingTime;
	}

	public void setPlayingTime(Date playingTime) {
		this.playingTime = playingTime;
	}
}

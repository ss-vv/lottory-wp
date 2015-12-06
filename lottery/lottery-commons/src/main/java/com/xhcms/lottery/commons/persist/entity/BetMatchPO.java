package com.xhcms.lottery.commons.persist.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 
 * @author Wang Lei
 *
 */
@Entity
@Table(name = "lt_bet_match")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BetMatchPO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, name = "scheme_id")
	private Long schemeId;

	@Column(nullable = false, name = "match_id")
	private Long matchId;

	@Column(nullable = false)
	private String code;
	
	private String odds;
	
	private BigDecimal bonus;

	/**胆码*/
	private boolean seed;
	
	@Column(name="play_id")
	private String playId;		// 玩法id, 主要是给“混合过关”玩法用。
	
	@Column(name="concede")
	private Float defaultScore = 0.0f;	// 预设分值、让分值
	
	/**注释*/
	private String annotation;

	public String getPlayId() {
		return playId;
	}

	public void setPlayId(String playId) {
		this.playId = playId;
	}

	public Float getDefaultScore() {
		if (defaultScore == null){
			defaultScore = 0.0f;
		}
		return defaultScore;
	}

	public void setDefaultScore(Float defaultScore) {
		this.defaultScore = defaultScore;
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

    public String getOdds() {
        return odds;
    }

    public void setOdds(String odds) {
        this.odds = odds;
    }

    public BigDecimal getBonus() {
        return bonus;
    }

    public void setBonus(BigDecimal bonus) {
        this.bonus = bonus;
    }

	public boolean isSeed() {
		return seed;
	}

	public void setSeed(boolean seed) {
		this.seed = seed;
	}

	public String getAnnotation() {
		return annotation;
	}

	public void setAnnotation(String annotation) {
		this.annotation = annotation;
	}
    
    
    
}

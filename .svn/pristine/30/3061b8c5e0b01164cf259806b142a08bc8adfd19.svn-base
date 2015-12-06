package com.xhcms.lottery.commons.persist.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *	传统足彩投注内容实体
 *	@author Wang Lei
 */
@Entity
@Table(name = "lt_ct_bet_content")
public class CTBetContentPO {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
    @Column(name = "lottery_id", nullable = false)
	private String lotteryId;
    
    @Column(name = "play_id", nullable = false)
    private String playId;
    
    @Column(name = "scheme_id", nullable = false)
    private Long schemeId;
    
    @Column(name = "issue_id", nullable = false)
    private Long issueId;
    
    @Column(name = "issue_number", nullable = false)
	private String issueNumber;
    
    @Column(name = "seed", nullable = true)
    private String seed;
    
    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "choose_type", nullable = false)
    private int chooseType;

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

	public Long getSchemeId() {
		return schemeId;
	}

	public void setSchemeId(Long schemeId) {
		this.schemeId = schemeId;
	}

	public Long getIssueId() {
		return issueId;
	}

	public void setIssueId(Long issueId) {
		this.issueId = issueId;
	}

	public String getIssueNumber() {
		return issueNumber;
	}

	public void setIssueNumber(String issueNumber) {
		this.issueNumber = issueNumber;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getChooseType() {
		return chooseType;
	}

	public void setChooseType(int chooseType) {
		this.chooseType = chooseType;
	}

	public String getSeed() {
		return seed;
	}

	public void setSeed(String seed) {
		this.seed = seed;
	}
	
}

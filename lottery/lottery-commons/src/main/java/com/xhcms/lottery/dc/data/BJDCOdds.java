package com.xhcms.lottery.dc.data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 北京单场赔率
 * @author  haohao wu
 *
 */
public class BJDCOdds {

	private long id;
	private String issue_number;
	private String match_id;
	private long play_id;
	private String options;
	private String odds;
	private String final_odds;
	private BigDecimal condede_points;
	private String win_option;
	private Date created_time;
	private Date update_time;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getIssue_number() {
		return issue_number;
	}
	public void setIssue_number(String issue_number) {
		this.issue_number = issue_number;
	}
	public String getMatch_id() {
		return match_id;
	}
	public void setMatch_id(String match_id) {
		this.match_id = match_id;
	}
	public long getPlay_id() {
		return play_id;
	}
	public void setPlay_id(long play_id) {
		this.play_id = play_id;
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
	public String getFinal_odds() {
		return final_odds;
	}
	public void setFinal_odds(String final_odds) {
		this.final_odds = final_odds;
	}
	public BigDecimal getCondede_points() {
		return condede_points;
	}
	public void setCondede_points(BigDecimal condede_points) {
		this.condede_points = condede_points;
	}
	public String getWin_option() {
		return win_option;
	}
	public void setWin_option(String win_option) {
		this.win_option = win_option;
	}
	public Date getCreated_time() {
		return created_time;
	}
	public void setCreated_time(Date created_time) {
		this.created_time = created_time;
	}
	public Date getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}
	
	
	
	
}

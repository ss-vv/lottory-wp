/**
 * 
 */
package com.xhcms.lottery.dc.data;

/**
 * 赔率装载对象
 * 
 * @author langhsu
 * 
 */
public class Bonus {
	private String playId;
	private long matchId;
	private double bonus;	// 单关的开奖赔率
	private String option;	// 中奖选项
	
	public Bonus(String playId, long matchId, double bonus, String option) {
		this.playId = playId;
		this.matchId = matchId;
		this.bonus = bonus;
		this.option = option;
	}
	
	public String getId() {
		return matchId + playId;
	}
	
	public String getPlayId() {
		return playId;
	}

	public void setPlayId(String playId) {
		this.playId = playId;
	}

	public String getOption() {
		return option;
	}

	public long getMatchId() {
		return matchId;
	}

	public void setMatchId(long matchId) {
		this.matchId = matchId;
	}

	public void setOption(String option) {
		this.option = option;
	}

	public double getBonus() {
		return bonus;
	}

	public void setBonus(double bonus) {
		this.bonus = bonus;
	}

}

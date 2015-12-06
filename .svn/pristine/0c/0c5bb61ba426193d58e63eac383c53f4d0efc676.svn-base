package com.xhcms.lottery.commons.data.bonus;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 一组 MatchOddsPair 构成的票。
 * 
 * @author Yang Bo
 */
public class MatchOddsTicket {
	private String passType;
	private String originalPassType;	// 投注时的过关类型
	private List<MatchOddsPair> matchOddsPairs = new LinkedList<MatchOddsPair>();
	private BigDecimal minBonus;	// 单倍最小奖金
	private BigDecimal maxBonus;	// 单倍最大奖金
	
	public String getPassType() {
		return passType;
	}
	public void setPassType(String passType) {
		this.passType = passType;
	}
	public List<MatchOddsPair> getMatchOddsPairs() {
		return matchOddsPairs;
	}
	public void setMatchOddsPairs(List<MatchOddsPair> matchOddsPairs) {
		this.matchOddsPairs = matchOddsPairs;
	}
	public BigDecimal getMinBonus() {
		return minBonus;
	}
	public void setMinBonus(BigDecimal minBonus) {
		this.minBonus = minBonus;
	}
	public BigDecimal getMaxBonus() {
		return maxBonus;
	}
	public void setMaxBonus(BigDecimal maxBonus) {
		this.maxBonus = maxBonus;
	}

	public String toString(){
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
	public String getOriginalPassType() {
		return originalPassType;
	}
	public void setOriginalPassType(String originalPassType) {
		this.originalPassType = originalPassType;
	}
}

package com.xhcms.lottery.commons.data.bonus;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 一场比赛的场次和选项的赔率。
 * 
 * @author Yang Bo
 */
public class MatchOddsPair {
	// 场次
	private String match;
	
	// 选项的赔率
	private List<BigDecimal> odds = new LinkedList<BigDecimal>();
	
	public String getMatch() {
		return match;
	}
	public void setMatch(String match) {
		this.match = match;
	}
	public List<BigDecimal> getOdds() {
		return odds;
	}
	public void setOdds(List<BigDecimal> odds) {
		this.odds = odds;
	}
	@Override
	public int hashCode() {
		return this.match.hashCode();
	}
	/** 只要比赛场次相同，就认为是相同的 MatchOddsPair */
	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof MatchOddsPair)){
			return false;
		}
		MatchOddsPair mop = (MatchOddsPair) obj;
		return this.match.equals(mop.getMatch());
	}
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}

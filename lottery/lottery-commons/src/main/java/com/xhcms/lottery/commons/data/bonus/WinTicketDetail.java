package com.xhcms.lottery.commons.data.bonus;

import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 中奖票详情.
 * 
 * @author Yang Bo
 *
 */
public class WinTicketDetail {

	// 所属过关类型, 格式: 2@1 
	private String passType;
	
	// 过关的比赛选项
	private List<WinMatch> winMatches;
	
	public List<WinMatch> getWinMatches() {
		return winMatches;
	}
	public void setWinMatches(List<WinMatch> winMatches) {
		this.winMatches = winMatches;
	}
	public String getPassType() {
		return passType;
	}
	public void setPassType(String passType) {
		this.passType = passType;
	}

	public String toString(){
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}

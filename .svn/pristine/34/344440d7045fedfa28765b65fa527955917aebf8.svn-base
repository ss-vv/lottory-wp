package com.xhcms.lottery.commons.utils.internal;

import java.util.Date;
import java.util.List;

/**
 * 
 * @desc 推算不同彩种的期号
 * @createTime 2013-8-8
 * @author lei.li@unison.net.cn
 * @version 1.0
 */
public interface IssueNumberStrategy {

	/**
	 * 根据当前在售期，推算指定彩种的下一期的期号
	 * @param currOnSaleIssue	当前在售期的期号
	 * @param startTime 期开始时间
	 * @param index	下一期的索引
	 * @return
	 */
	public String nextIssueNumber(String currOnSaleIssue, 
			Date startTime, int index);
	
	/**
	 * 根据彩种ID和当前在售期号顺延推算出指定期数
	 * @param currOnSaleIssue	当前在售期的期号
	 * @param postponeIssueSize	需要顺延推算的期数（不包含当前在售期）
	 * @param startTime	期开始时间 
	 * @return	期数集合
	 */
	public List<String> moreIssueNumbers(String currOnSaleIssue, 
			int postponeIssueSize, Date startTime);
}
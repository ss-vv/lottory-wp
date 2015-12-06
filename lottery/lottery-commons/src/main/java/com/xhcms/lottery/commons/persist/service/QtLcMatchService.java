/**
 * 
 */
package com.xhcms.lottery.commons.persist.service;

/**
 * 
 * @author lei.li@davcai.com
 */
public interface QtLcMatchService {

	/**
	 * 根据大V彩赛事ID获取球探赛事ID
	 * @param lcMatchId
	 * @return
	 */
	long findQTMatchId(String lcMatchId);
	
	/**
	 * 根据球探赛事ID获取大V彩赛事ID,当结果列表有多个时，取比赛时间最近的
	 * @param lotteryName 彩种名称
	 * @param lcMatchId
	 * @return
	 */
	long findLCMatchId(Long qtMatchId, String lotteryName);
	
	
	/**
	 * 更新足球预派奖比分
	 * @param qtMatchId
	 */
	void updateFBPresetScore(String qtMatchId, String homeHalfScore, 
			String guestHalfScore, String homeScore, String guestScore);
	
	/**
	 * 更新篮球预派奖比分
	 * @param qtMatchId
	 */
	void updateBBPresetScore(Long qtMatchId, int homeScore, int guestScore);
}

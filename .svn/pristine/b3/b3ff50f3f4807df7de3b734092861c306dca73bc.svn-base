package com.xhcms.lottery.commons.persist.dao;


import java.sql.Date;
import java.util.List;

import com.xhcms.commons.persist.Dao;

import com.xhcms.lottery.commons.persist.entity.BetSchemeWithIssueInfoPO;

/**
 * 
 * <p>Title: 投注方案</p>
 * <p>Description: </p>
 * <p>Copyright：Copyright (c) 2011</p>
 * <p>Company：XingHui Spirit (Beijing) Technical Co.,Ltd.</p>
 * 
 * @author 陈岩
 * @version 1.0.0
 */
public interface BetSchemeWithIssueInfoDao extends Dao<BetSchemeWithIssueInfoPO> {

	

    
	/**
	 * 返回在指定的彩票id列表里，
	 * 创建时间大于from,状态为
	 * status,且当前时间在对应
	 * 的有效的期信息的startTime
	 * 和ZMcloseTime之间的方案列表
	 * @param ticketAllowBuy
	 * @param date
	 * @param targetLotteryIdList
	 * @return
	 */
	List<BetSchemeWithIssueInfoPO> findByStatusWithCurrentTime(
			int status, Date from,
			List<String> targetLotteryIdList);
	
	
	/**
	 * 返回在指定的彩票id列表里，
	 * 创建时间大于from,状态为
	 * status,且指定时间在对应
	 * 的有效的期信息的startTime
	 * 和ZMcloseTime之间的方案列表
	 * @param status
	 * @param from 创建时间大于 from
	 * @param targetLotteryIdList 指定彩种
	 * @param targetTime 指定的“当前时间”。
	 * @return
	 */
	List<BetSchemeWithIssueInfoPO> findByStatusWithSpecifiedTime(
			int status, Date from, List<String> targetLotteryIdList,
			java.util.Date targetTime);
    
}

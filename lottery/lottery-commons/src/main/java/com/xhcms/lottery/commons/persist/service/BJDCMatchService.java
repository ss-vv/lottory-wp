package com.xhcms.lottery.commons.persist.service;

import java.util.List;
import com.xhcms.lottery.commons.data.BJDCMatchsViewResult;
import com.xhcms.lottery.commons.persist.entity.MatchPlay;

/**
 * 北京单场赛事数据接口 
 * @date 2014-7-30 上午11:44:30
 * @author lei.li@unison.net.cn
 * @version 2.0
 */
public interface BJDCMatchService {
	
	/**
	 * 获取指定玩法的在售北单赛事
	 * @param playId
	 * @param issueNumber
	 * @return
	 */
	List<MatchPlay> listBDOnSale(String playId, String issueNumber);
	
	List<MatchPlay> listBDMatchs(String playId, String issueNumber);
	
	/**
	 * @param issueNumber
	 * 用于页面赛程展现；如果入参issueNumber为null，则取当前在售期；
	 * @param lotteryId
	 * @param playId
	 * @return
	 */
	BJDCMatchsViewResult findMatchs(String issueNumber, String lotteryId, String playId);
}
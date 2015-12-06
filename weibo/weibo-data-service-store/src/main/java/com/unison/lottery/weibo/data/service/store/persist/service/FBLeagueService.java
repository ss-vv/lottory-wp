package com.unison.lottery.weibo.data.service.store.persist.service;

import com.unison.lottery.weibo.data.service.store.persist.entity.FBLeaguePO;


/**
 * 提供足球联赛相关的服务
 * @desc
 * @author lei.li@unison.net.cn
 * @createTime 2014-2-20
 * @version 1.0
 */
public interface FBLeagueService {
	
	/**
	 * 返回联赛ID对应的足球联赛、杯赛PO
	 * @param leagueId
	 * @return
	 */
	FBLeaguePO currMatchSeason(long leagueId);
	
	FBLeaguePO findLeagueBy(String lcMatchId);
	
}
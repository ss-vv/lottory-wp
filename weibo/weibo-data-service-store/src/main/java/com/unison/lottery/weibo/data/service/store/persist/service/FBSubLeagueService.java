package com.unison.lottery.weibo.data.service.store.persist.service;

import com.unison.lottery.weibo.data.service.store.persist.entity.FBSubLeaguePO;


/**
 * 提供足球子联赛相关的服务
 * @desc
 * @author lei.li@unison.net.cn
 * @createTime 2014-2-20
 * @version 1.0
 */
public interface FBSubLeagueService {
	
	/**
	 * 返回足球子联赛类型
	 * @param leagueId
	 * @return
	 */
	FBSubLeaguePO getSubLeague(long leagueId, String season);
	
	
	
}
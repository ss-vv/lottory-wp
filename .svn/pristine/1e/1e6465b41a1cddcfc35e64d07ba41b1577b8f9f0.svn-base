package com.unison.weibo.admin.service;

import java.util.List;

import com.unison.lottery.weibo.data.PageRequest;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.BasketBallLeagueInfoPO;
import com.xhcms.commons.persist.Dao;

public abstract interface BasketBallService extends Dao<BasketBallLeagueInfoPO>{
	
	/**
	 * 查询所有，分页
	 * @param pageRequest
	 * @return
	 */
	public abstract List<BasketBallLeagueInfoPO> findAll(PageRequest pageRequest);
	
	/**
	 * 根据ID查询
	 * @param id
	 * @return
	 */
	public abstract BasketBallLeagueInfoPO findLagueByID(String id);
	
	public abstract void basketballleagueEdit(String id,String key,String newData);
}

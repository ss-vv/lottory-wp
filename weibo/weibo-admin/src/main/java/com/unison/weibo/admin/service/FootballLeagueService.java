package com.unison.weibo.admin.service;

import java.util.List;

import com.unison.lottery.weibo.data.PageRequest;
import com.unison.lottery.weibo.data.PageResult;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.LeagueInfoPO;
import com.xhcms.commons.persist.Dao;
import com.xhcms.commons.persist.hibernate.DaoImpl;


public abstract interface FootballLeagueService extends Dao<LeagueInfoPO> {
	
	/**
	 * 查询所有，分页
	 * @param pageRequest
	 * @return
	 */
	public abstract List<LeagueInfoPO> findAll(PageRequest pageRequest);
	
	/**
	 * 根据ID查询
	 * @param id
	 * @return
	 */
	public abstract LeagueInfoPO findLagueByID(int id);
	
	public abstract void footballleagueEdit(int id,String key,String newData);
	
}

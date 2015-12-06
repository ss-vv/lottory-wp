package com.unison.weibo.admin.service;

import java.util.List;

import com.xhcms.commons.persist.Dao;
import com.xhcms.lottery.commons.persist.entity.ActivityPO;

/**
 * 
 * @author Next
 *
 */
public abstract interface ActivityDaoWeiboAdmin extends Dao<ActivityPO> {
	
	/**
	 * 下架
	 * @param paramInteger
	 * @param paramList
	 */
	public abstract void updatePutOffListVersion(Integer paramInteger, List<Long> paramList);
	/**
	 * 下移一条记录
	 * @param paramInteger
	 * @param parList
	 */
	public abstract void updatePutUpListVersuib(Integer paramInteger,List<Long> parList);
	/**
	 * 上移一条记录
	 * @param paramInteger
	 * @param parList
	 */
	public abstract void updatePutDownListVersuib(Integer paramInteger,List<Long> parList);
	/**
	 * 置顶功能
	 * @param paramInteger
	 * @param parList
	 */
	public abstract void updatePutUpUpListVersuib(Integer paramInteger,List<Long> parList);
	/**
	 * 查询所有
	 * @return
	 */
	public abstract List<ActivityPO> findAll();
}

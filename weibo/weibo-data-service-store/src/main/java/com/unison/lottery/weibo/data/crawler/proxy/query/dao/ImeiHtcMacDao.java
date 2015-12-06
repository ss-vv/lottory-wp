package com.unison.lottery.weibo.data.crawler.proxy.query.dao;

import java.util.List;

import com.unison.lottery.weibo.data.crawler.proxy.query.dao.entity.ImeiMacHtcPO;


/**
 * imei和mac地址
 * @author 彭保星
 *
 * @since 2014年10月27日上午10:54:10
 */
public interface ImeiHtcMacDao {
	public List<ImeiMacHtcPO> findAll();
}

package com.unison.lottery.weibo.data.crawler.proxy.query.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.unison.lottery.weibo.data.crawler.proxy.query.dao.ImeiHtcMacDao;
import com.unison.lottery.weibo.data.crawler.proxy.query.dao.entity.ImeiMacHtcPO;
import com.unison.lottery.weibo.data.service.store.persist.dao.impl.BasicDaoImpl;
import com.xhcms.commons.persist.hibernate.DaoImpl;

/**
 * @author 彭保星
 *
 * @since 2014年10月27日上午10:54:58
 */
@Repository
public class ImeiHtcMacDaoImpl extends BasicDaoImpl<ImeiMacHtcPO> implements ImeiHtcMacDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2873254312556762221L;

	public ImeiHtcMacDaoImpl() {
		super(ImeiMacHtcPO.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<ImeiMacHtcPO> findAll() {
		Criteria criteria = createCriteria();
		return criteria.addOrder(Order.asc("id")).list();
	}
	
	
}

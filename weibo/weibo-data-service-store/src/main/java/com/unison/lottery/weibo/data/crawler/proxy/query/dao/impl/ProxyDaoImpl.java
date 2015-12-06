package com.unison.lottery.weibo.data.crawler.proxy.query.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.unison.lottery.weibo.data.crawler.proxy.query.dao.ProxyDao;
import com.unison.lottery.weibo.data.crawler.proxy.query.dao.entity.ProxyPO;
import com.unison.lottery.weibo.data.service.store.persist.dao.impl.BasicDaoImpl;
import com.xhcms.commons.persist.hibernate.DaoImpl;


/**
 * @author 彭保星
 *
 * @since 2014年10月27日上午9:26:41
 */
@Repository
public class ProxyDaoImpl extends DaoImpl<ProxyPO> implements ProxyDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = -993005919884985789L;

	public ProxyDaoImpl() {
		super(ProxyPO.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<ProxyPO> findAll() {
		Criteria criteria = createCriteria();
		criteria = criteria.addOrder(Order.asc("id"));
		return criteria.list();
	}

}

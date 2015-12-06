package com.unison.lottery.weibo.data.crawler.proxy.query.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.unison.lottery.weibo.data.crawler.proxy.query.dao.UserAgentDao;
import com.unison.lottery.weibo.data.crawler.proxy.query.dao.entity.UserAgentPO;
import com.unison.lottery.weibo.data.service.store.persist.dao.impl.BasicDaoImpl;

/**
 * @author 彭保星
 *
 * @since 2014年10月27日下午2:44:33
 */
@Repository
public class UserAgentDaoImpl extends BasicDaoImpl<UserAgentPO> implements UserAgentDao{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3004514307570716945L;

	public UserAgentDaoImpl() {
		super(UserAgentPO.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<UserAgentPO> findAll() {
		Criteria criteria = createCriteria();
		return criteria.addOrder(Order.asc("id")).list();
	}
	

}

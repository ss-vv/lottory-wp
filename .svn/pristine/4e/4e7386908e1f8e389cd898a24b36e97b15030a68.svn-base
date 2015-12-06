package com.unison.lottery.weibo.data.service.store.persist.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.unison.lottery.weibo.data.service.store.persist.dao.BasicDao;
import com.xhcms.commons.persist.hibernate.DaoImpl;

/**
 * 扩展星汇的 DaoImpl.
 * 
 * @author Yang Bo
 */
@SuppressWarnings("serial" )
public abstract class BasicDaoImpl<PO> extends DaoImpl<PO> implements BasicDao<PO>{

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	public BasicDaoImpl(Class<PO> poClass){
		super(poClass);
	}
	
	@Override
	public void flushAndEvict(Object po) {
		try{
			session().flush();
		}catch(Exception e){
			logger.error("不能保存数据: \n{}", po, e);
		}
		session().evict(po);
	}

}

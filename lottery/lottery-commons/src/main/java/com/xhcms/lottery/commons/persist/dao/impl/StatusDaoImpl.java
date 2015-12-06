package com.xhcms.lottery.commons.persist.dao.impl;

import java.util.List;

import com.xhcms.commons.persist.hibernate.DaoImpl;
import com.xhcms.lottery.commons.persist.dao.StatusDao;
import com.xhcms.lottery.commons.persist.entity.ReturnStatusPO;




public class StatusDaoImpl extends DaoImpl<ReturnStatusPO> implements StatusDao{

	 /**
	 * 
	 */
	private static final long serialVersionUID = 221164103768811668L;


	public StatusDaoImpl() {
	        super(ReturnStatusPO.class);
	 }
	 
	 
	

}

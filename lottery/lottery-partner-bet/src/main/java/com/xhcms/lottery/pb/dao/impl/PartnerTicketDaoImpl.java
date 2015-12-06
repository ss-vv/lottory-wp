package com.xhcms.lottery.pb.dao.impl;


import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.xhcms.commons.persist.hibernate.DaoImpl;
import com.xhcms.lottery.pb.dao.PartnerTicketDao;
import com.xhcms.lottery.pb.po.PartnerBetPO;
import com.xhcms.lottery.pb.po.PartnerTicketPO;

@Repository
public class PartnerTicketDaoImpl extends DaoImpl<PartnerTicketPO> implements PartnerTicketDao{
	private static final long serialVersionUID = 1L;
	public PartnerTicketDaoImpl(){
		super(PartnerTicketPO.class);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PartnerTicketPO> listByStatus(int length,int status,long userId) {
		return createQuery("select t from PartnerTicketPO t,PartnerBetPO b"
				+ " where t.schemeId = b.schemeId and b.userId = ? and t.status= ?")
				.setMaxResults(length).setLong(0, userId).setInteger(1, status).list();
	}

	@Override
	public void updateTicketStatus(long ticketId, int drawTicketNoticeSuccess) {
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public PartnerTicketPO findByTicketId(long ticketId) {
		List<PartnerTicketPO> p = createQuery("select t from PartnerTicketPO t"
				+ " where t.ticketId = ?").setLong(0, ticketId).list();
		return p.size() > 0 ? p.get(0):null;
	}
}

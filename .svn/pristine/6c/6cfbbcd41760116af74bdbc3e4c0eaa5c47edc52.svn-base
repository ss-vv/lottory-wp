package com.xhcms.lottery.pb.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.xhcms.commons.persist.hibernate.DaoImpl;
import com.xhcms.lottery.pb.dao.PartnerBetDao;
import com.xhcms.lottery.pb.po.PartnerBetPO;

@Repository
public class PartnerBetDaoImpl extends DaoImpl<PartnerBetPO> implements PartnerBetDao{
	private static final long serialVersionUID = 1L;
	public PartnerBetDaoImpl(){
		super(PartnerBetPO.class);
	}
	
	@Override
	public PartnerBetPO findPartnerBetPOByUuid(String uuid) {
		Criteria c = createCriteria();
		c.add(Restrictions.eq("uuid", uuid));
		return (PartnerBetPO)c.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PartnerBetPO> listByStatus(int length,int status) {
		Criteria c = createCriteria();
		c.add(Restrictions.eq("status", status));
		c.setMaxResults(length);
		return c.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PartnerBetPO> listByStatus(int length,
			ArrayList<Integer> statuses) {
		Criteria c = createCriteria();
		c.add(Restrictions.in("status", statuses));
		c.setMaxResults(length);
		return c.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PartnerBetPO> listByStatus(int length,
			ArrayList<Integer> statuses,String userId) {
		Criteria c = createCriteria();
		c.add(Restrictions.and(Restrictions.eq("userId", userId),
				Restrictions.in("status", statuses)));
		c.setMaxResults(length);
		return c.list();
	}

	@Override
	public void updateTicketStatus(Long ticketId, int status) {
		 createQuery("update PartnerBetPO set status=:s where ticketId = :tId")
         .setInteger("s", status).setLong("tId", ticketId).executeUpdate();
	}
	
}

package com.xhcms.lottery.commons.persist.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

import com.xhcms.commons.persist.hibernate.DaoImpl;
import com.xhcms.lottery.commons.data.RemoteTicket;
import com.xhcms.lottery.commons.persist.dao.TicketRemoteDao;
import com.xhcms.lottery.commons.persist.entity.TicketRemotePO;

public class TicketRemoteDaoImpl extends DaoImpl<TicketRemotePO> implements TicketRemoteDao{

	private static final long serialVersionUID = 2121111962928299718L;
	public TicketRemoteDaoImpl(){
		super(TicketRemotePO.class);
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<String> findRemoteTicketByStatus(String[] status) {
		Criteria c = createCriteria()
				.setProjection(Property.forName("orderId"))
				.add(Restrictions.in("status", status))
				.addOrder(Order.asc("createdTime"));
				//.setFirstResult(0).setMaxResults(50);
		return c.list();
	}
	@Override
	public void updateTicketStatus(RemoteTicket rt) {
		createQuery("update TicketRemotePO set status=:status,message=:msg where orderId=:orderId")
		.setParameter("status", rt.getError()).setParameter("msg", rt.getMessage()).setParameter("orderId", rt.getOrderId())
		.executeUpdate();
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<TicketRemotePO> findTicketByOrderIds(List<String> orderIds) {
		Criteria c = createCriteria().add(Restrictions.in("orderId", orderIds));
		return c.list();
	}
}

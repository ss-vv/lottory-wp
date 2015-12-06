package com.xhcms.lottery.mc.persist.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.xhcms.lottery.commons.data.RemoteTicket;
import com.xhcms.lottery.commons.persist.dao.TicketRemoteDao;
import com.xhcms.lottery.commons.persist.entity.TicketRemotePO;
import com.xhcms.lottery.mc.persist.service.RemoteTicketService;
@Transactional
public class RemoteTicketServiceImpl implements RemoteTicketService {

	@Autowired
	private TicketRemoteDao ticketRemoteDao;
	@Override
	public void updateRemoteTicketStatus(List<RemoteTicket> tickets) {
		if(tickets!=null && tickets.size()>0){
			for(RemoteTicket t:tickets){
				ticketRemoteDao.updateTicketStatus(t);
			}
		}
	}
	@Override
	public Map<Long, TicketRemotePO> findRemoteTicketWithOrderIds(
			List<RemoteTicket> tickets) {
		Map<Long,TicketRemotePO> tMap=new HashMap<Long,TicketRemotePO>();
		List<String> orderIds=new ArrayList<String>();
		for(RemoteTicket t:tickets){
			orderIds.add(t.getOrderId());
		}
		List<TicketRemotePO> tPO=ticketRemoteDao.findTicketByOrderIds(orderIds);
		if(tPO!=null && tPO.size()>0){
			for(TicketRemotePO po:tPO){
				tMap.put(po.getTicketId(), po);
			}
		}
		return tMap;
	}

}

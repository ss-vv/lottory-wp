package com.xhcms.lottery.commons.persist.dao;

import java.util.List;

import com.xhcms.commons.persist.Dao;
import com.xhcms.lottery.commons.data.RemoteTicket;
import com.xhcms.lottery.commons.persist.entity.TicketRemotePO;

public interface TicketRemoteDao extends Dao<TicketRemotePO>{

	public List<String> findRemoteTicketByStatus(String []status);
	
	public void updateTicketStatus(RemoteTicket rt);
	
	public List<TicketRemotePO> findTicketByOrderIds(List<String> orderIds);
}

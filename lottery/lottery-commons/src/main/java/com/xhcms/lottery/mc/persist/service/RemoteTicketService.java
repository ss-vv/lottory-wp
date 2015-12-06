package com.xhcms.lottery.mc.persist.service;

import java.util.List;
import java.util.Map;

import com.xhcms.lottery.commons.data.RemoteTicket;
import com.xhcms.lottery.commons.persist.entity.TicketRemotePO;

public interface RemoteTicketService {
 
	public void updateRemoteTicketStatus(List<RemoteTicket> tickets);
	
	public Map<Long,TicketRemotePO> findRemoteTicketWithOrderIds(List<RemoteTicket> tickets);
}

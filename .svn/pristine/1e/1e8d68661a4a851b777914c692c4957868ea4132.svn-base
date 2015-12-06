package com.xhcms.lottery.pb.dao;


import java.util.List;

import com.xhcms.commons.persist.Dao;
import com.xhcms.lottery.pb.po.PartnerTicketPO;

public interface PartnerTicketDao extends Dao<PartnerTicketPO> {
	public List<PartnerTicketPO> listByStatus(int length,int status,long userId);

	public void updateTicketStatus(long ticketId, int drawTicketNoticeSuccess);
	
	public PartnerTicketPO findByTicketId(long ticketId);
}

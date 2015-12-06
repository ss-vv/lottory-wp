package com.xhcms.lottery.commons.persist.dao;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.xhcms.commons.lang.Paging;
import com.xhcms.commons.persist.Dao;
import com.xhcms.lottery.commons.data.Ticket;
import com.xhcms.lottery.commons.persist.entity.PrintableTicketPO;

public interface PrintableTicketDao extends Dao<PrintableTicketPO>{

	List<Object> findWithPage(Paging paging, Date from, Date to, int state,
			String lotteryId, Long schemeId, Long ticketId,String playId, String lotteryPlatformId);

	List<PrintableTicketPO> findByIds(List<Long> printableTicketIds, String lotteryPlatformId, int status);

	List<Object> findTicketByFileId(long fileId);

	List<PrintableTicketPO> findByTicketIds(Set<Long> ticketIds);
}

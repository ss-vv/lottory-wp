package com.xhcms.lottery.admin.persist.service;

import java.util.List;

import com.xhcms.lottery.commons.data.PrintableTicket;

public interface PrintableFileService {

	String export(List<Long> printableTicketIds, String lotteryId, String playId, String lotteryPlatformId, String exportToShitidian);

	List<PrintableTicket> findTicketByFileId(long fileId);
}

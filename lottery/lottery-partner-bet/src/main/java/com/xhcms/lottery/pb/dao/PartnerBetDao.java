package com.xhcms.lottery.pb.dao;

import java.util.ArrayList;
import java.util.List;

import com.xhcms.commons.persist.Dao;
import com.xhcms.lottery.pb.po.PartnerBetPO;

public interface PartnerBetDao extends Dao<PartnerBetPO> {

	public PartnerBetPO findPartnerBetPOByUuid(String uuid);

	public List<PartnerBetPO> listByStatus(int length, int status);

	public List<PartnerBetPO> listByStatus(int length,
			ArrayList<Integer> statuses);

	public void updateTicketStatus(Long ticketId, int status);

	public List<PartnerBetPO> listByStatus(int pushTicketMaxSize,
			ArrayList<Integer> statuses, String userId);
}

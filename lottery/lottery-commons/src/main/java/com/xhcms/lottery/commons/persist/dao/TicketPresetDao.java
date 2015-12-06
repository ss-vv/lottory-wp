package com.xhcms.lottery.commons.persist.dao;

import java.util.Collection;
import java.util.List;

import com.xhcms.commons.persist.Dao;
import com.xhcms.lottery.commons.persist.entity.TicketPresetPO;

public interface TicketPresetDao extends Dao<TicketPresetPO> {

	public void updateStatusByScheme(Collection<Long> sids, int oldStatus, int newStatus, String message);

	public List<TicketPresetPO> findByScheme(Long schemeId, int status);
}

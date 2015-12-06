package com.xhcms.lottery.commons.persist.service;

import java.util.Date;
import java.util.List;

import com.xhcms.lottery.commons.data.Ticket;

public interface AwardService {

	List<Ticket> findByCreatTime(String playId, Date createdTime, boolean isawarded);
	
	void award(List<Long> ids);
}

package com.unison.lottery.api.protocol.status.service;

import java.util.List;

import com.xhcms.lottery.commons.persist.entity.ReturnStatusPO;

public interface IStatusService {

	List<ReturnStatusPO> loadAllStatus();

}

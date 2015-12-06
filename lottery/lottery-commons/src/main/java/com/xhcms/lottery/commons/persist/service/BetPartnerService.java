package com.xhcms.lottery.commons.persist.service;

import java.util.List;

import com.xhcms.lottery.commons.data.BetPartner;
import com.xhcms.lottery.commons.data.BetRecord;

public interface BetPartnerService {

	List<BetRecord> list(long schemeId);
	
	BetPartner findById(long betRecordId);
	
	/**
	 * 查找合买方案发起人的投注记录ID
	 * @param schemeId
	 * @return
	 */
	BetPartner findGroupSponsorRecord(long schemeId, long sponsorId);
}

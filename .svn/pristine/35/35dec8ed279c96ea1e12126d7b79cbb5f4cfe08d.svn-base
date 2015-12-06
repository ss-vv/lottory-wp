package com.xhcms.lottery.admin.persist.service;

import java.util.List;

import com.davcai.lottery.platform.util.model.NameAndValue;
import com.davcai.lottery.platform.util.model.LotteryPlatformPriority;

public interface LotteryPlatformPriorityService {

	List<LotteryPlatformPriority> listShiTiDianPriorityList(String lotteryId, String lotteryPlatformAliasName);

	
	void updatePriority(Long id, int priority, int priorityOfBigTicket);


	List<NameAndValue> listShiTiDianAliasNameList();


	List<NameAndValue> listShiTiDianLotteryNameList();


	boolean batchUpdatePriority(List<Long> selectedIds,
			List<Integer> selectedPrioritys,
			List<Integer> selectedPriorityOfBigTickets);

}

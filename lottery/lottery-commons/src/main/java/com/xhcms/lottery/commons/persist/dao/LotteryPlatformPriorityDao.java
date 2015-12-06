package com.xhcms.lottery.commons.persist.dao;

import java.util.List;

import com.xhcms.commons.persist.Dao;
import com.xhcms.lottery.commons.persist.entity.LotteryPlatformPriorityPO;

public interface LotteryPlatformPriorityDao extends Dao<LotteryPlatformPriorityPO>{

	List<LotteryPlatformPriorityPO> findByLotteryInterfaceNameAndLotteryId(
			String lotteryInterfaceName, String lotteryId);

	LotteryPlatformPriorityPO findOneByInterfaceName(String lotteryInterfaceName, String lotteryId);

	List<LotteryPlatformPriorityPO> listShiTiDianPriorityList(
			List<String> shiTiDianLotteryPlatformIdList,
			List<String> shiTiDianLotteryIdList, String lotteryId, String lotteryPlatformAliasName);

	void updatePriorityAndPriorityOfBigTicket(Long id, int priority,
			int priorityOfBigTicket);

	List<Object[]> listShiTiDianAliasNameList(
			List<String> shiTiDianLotteryPlatformIdList);

}

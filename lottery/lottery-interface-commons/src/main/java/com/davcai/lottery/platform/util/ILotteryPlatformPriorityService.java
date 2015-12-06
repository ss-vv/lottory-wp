package com.davcai.lottery.platform.util;

import java.util.List;

import com.davcai.lottery.platform.constants.LotteryInterfaceName;
import com.davcai.lottery.platform.util.model.LotteryPlatformPriority;
import com.xhcms.lottery.lang.LotteryId;

public interface ILotteryPlatformPriorityService {

	List<LotteryPlatformPriority> loadAll(LotteryInterfaceName lotteryInterfaceName, LotteryId lotteryId);
	LotteryPlatformPriority findOneByInterfaceName(LotteryInterfaceName lotteryInterfaceName, String lotteryId);

}

package com.davcai.lottery.platform.util;

import java.util.List;
import java.util.Map;

import com.davcai.lottery.platform.util.model.LotteryPlatformPriority;

public interface ILotteryPlatformAllocator<T> {

	/**
	 * 将列表按照权重分配。
	 * 
	 * @param tickets
	 * @return
	 */
	public abstract Map<String, List<T>> allocateByPriority(List<T> tickets);

//	public abstract Map<String, Integer> computeShouldArrangeNumber(List<T> priorityList, int totalTicketNumber);

}
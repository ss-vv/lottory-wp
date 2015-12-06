package com.unison.lottery.pm.service;

import java.util.Date;
import java.util.List;

public interface PMGrantVoucherRecordService {

	/**
	 * 竞彩足球每人按日投注记录
	 * @param day
	 * @return
	 */
	List<Object[]> findUserJCZQBetByDate(Date day);

}

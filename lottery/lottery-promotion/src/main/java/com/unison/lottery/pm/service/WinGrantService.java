package com.unison.lottery.pm.service;

import java.util.List;

/**
 * 活动赠款记录表service
 * @author Wang Lei
 *
 */
public interface WinGrantService {

	/**
	 * 根据活动id和状态，返回记录id集合
	 * @param PMid
	 * @param status
	 * @return
	 */
	public List<Long> findIdsByPMIDandStatus(Long PMid, Integer status);

}

package com.davcai.lottery.service;

import java.util.List;
import java.util.Map;

import com.unison.lottery.api.redEnvalope.model.EnvalopeGrabHistory;
import com.unison.lottery.api.redEnvalope.model.RedEnvalope;
import com.unison.lottery.weibo.data.service.store.persist.entity.RedEnvalopePO;

public interface RedEnvalopeService {
	/**
	 * 增加红包
	 * @param redEnvalopeAmount 红包金额
	 * @param envalopeNum 红包数量
	 * @param uid 用户id
	 * @param redArray 每个小红包的金额集合
	 * @return
	 */
	public long addRedEnvalope(Long redEnvalopeAmount, Integer envalopeNum, Long uid, long[] redArray);

	public RedEnvalope findRedEnvalopeById(Long envalopeId);

	public void deleteRedEnvalopeById(long envalopeId);

	/**
	 * 更新红包的已被抢金额
	 * @param redEnvalope
	 * @param smallRedEnvalopeAmount
	 * @param userId 
	 */
	public void updateRedEnvalope(RedEnvalope redEnvalope,
			Long smallRedEnvalopeAmount, Long userId);

	/**
	 * 
	 * @param envalopeId
	 * @param grabAmount 
	 * @param userId 
	 * @return
	 */
	public Map<String, Object> findRedEnvalopeRecordById(Long envalopeId, Long userId, String grabAmount);

	/**
	 * 查询 所有已失效的红包
	 * @return
	 */
	public List<RedEnvalopePO> findAllInvalidEvalope();

	/**
	 * 更新红包
	 * @param redEnvalopePO
	 */
	public void updateRedEnvalope(RedEnvalopePO redEnvalopePO);
}

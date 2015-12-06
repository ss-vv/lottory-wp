package com.unison.lottery.weibo.common.nosql;

/**
 * 红包redis
 * @author baoxing.peng
 *
 */
public interface RedEnvalopeRedisDao {

	void addEnvalopeToRedis(long envalopeId, Long uid, long[] redArray);

	Long grabRedAmountFromRedis(Long ltUserId, Long envalopeId, Long long1);
	
}

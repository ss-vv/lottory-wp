package com.unison.lottery.weibo.common.nosql.impl;

import com.unison.lottery.weibo.common.nosql.RedEnvalopeRedisDao;

public class RedEnvalopeRedisDaoImpl extends BaseDaoImpl<String> implements RedEnvalopeRedisDao{
	private final String tryGetHongBaoScript = 
			"if redis.call('hexists', KEYS[2], KEYS[3]) ~= 0 then\n"
			+ "return nil\n"
			+ "else\n"
			+ "local hongBao = redis.call('rpop', KEYS[1]);\n"
//			+ "print('hongBao:', hongBao);\n"
			+ "if hongBao then\n"
			+ "redis.call('hset', KEYS[2], KEYS[3],KEYS[3]);\n"
			+ "return hongBao;\n"
			+ "end\n"
			+ "end\n"
			+ "return nil";
	@Override
	public void addEnvalopeToRedis(long envalopeId, Long uid, long[] redArray) {
		String key = Keys.getRedEnvalopeListKey(String.valueOf(uid), String.valueOf(envalopeId));
		for(int i=0;i<redArray.length;i++){
			lpush(key, String.valueOf(redArray[i]));
		}
	}

	@Override
	public Long grabRedAmountFromRedis(Long ltUserId, Long envalopeId,Long userId) {
		String ltUserIdStr = String.valueOf(ltUserId);
		String envalopeIdStr = String.valueOf(envalopeId);
		String key = Keys.getRedEnvalopeListKey(ltUserIdStr, envalopeIdStr);
		//抢红包的用户set key
		String userGrabKey = Keys.getUserGrabSetKey(ltUserIdStr, envalopeIdStr);
		Object object = eval(tryGetHongBaoScript, 3, key,userGrabKey,String.valueOf(userId));
		if (object != null) {
			return Long.valueOf((String) object);
		}else {
			//已经取完了
			if(llen(key) == 0){
				return 0L;
			}else{
				return -1L;
			}
		}
	}
}

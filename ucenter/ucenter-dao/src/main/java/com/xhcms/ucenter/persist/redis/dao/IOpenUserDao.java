package com.xhcms.ucenter.persist.redis.dao;

/**
 * @Description: 访问redis数据库中的user数据 
 * @author 江浩翔   
 * @date 2013-11-15 上午10:38:25 
 * @version V1.0
 */
public interface IOpenUserDao {
	/**
	 * 使用第三方uid查询大V彩用户uid，如果未查到，返回null
	 * @param openUid
	 * @param authSrc
	 * @return
	 */
	String getLotteryUsername(String openUid,String authSrc);
}

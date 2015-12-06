package com.unison.lottery.weibo.common.cache;

import com.xhcms.lottery.commons.data.BetScheme;

/**
 * @desc 方案缓存服务
 * @createTime 2014-7-16
 * @author lei.li@unison.net.cn
 * @version 1.0
 */
public interface SchemeCache {
	
	boolean isOpenQueryCache();
	
	/**
     * 缓存给定方案对象
     * @param schemeCacheKey 缓存的KEY
     * @param scheme	缓存的方案对象
     */
    void setSchemeCache(String schemeCacheKey, BetScheme scheme);
    
    /**
     * 获取方案的缓存对象
     * @param schemeCacheKey
     * @return
     */
    BetScheme getSchemeCache(String schemeCacheKey);
    
    /**
     * 更新方案缓存
     * @param schemeCacheKey
     * @param betScheme
     */
    void updateSchemeCache(String schemeCacheKey, BetScheme betScheme);
}
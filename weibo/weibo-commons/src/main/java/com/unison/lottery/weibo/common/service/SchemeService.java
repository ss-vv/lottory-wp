package com.unison.lottery.weibo.common.service;

import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.persist.entity.BetSchemePO;

/**
 * 方案的service服务
 * @desc
 * @author lei.li@unison.net.cn
 * @createTime 2014-1-14
 * @version 1.0
 */
public interface SchemeService {

	/**
	 * 查询指定方案ID的方案信息
	 * @param schemeId
	 * @return
	 */
	BetSchemePO getScheme(long schemeId);
	
    /**
     * 带有缓存的方案查询
     * @param schemeId
     * @param userId
     * @param displayMode	-1为代购不晒(跟单)模式,0为晒单模式,1为合买模式
     * @return
     */
    BetScheme viewSchemeCache(Long schemeId, Long userId,int displayMode);
    
    /**
     * 更新实单方案缓存
     * @param schemeId
     * @param displayMode -1为代购不晒(跟单)模式,0为晒单模式,1为合买模式
     * @return
     */
    boolean updateRealSchemeCache(Long schemeId, int displayMode);
}
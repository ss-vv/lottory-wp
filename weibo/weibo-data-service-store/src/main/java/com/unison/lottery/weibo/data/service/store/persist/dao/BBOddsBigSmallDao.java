package com.unison.lottery.weibo.data.service.store.persist.dao;

import java.util.List;
import com.unison.lottery.weibo.data.service.store.persist.entity.BBOddsBigSmallPO;

/**
 * @desc 篮球大小球赔率数据库操作
 * @author lei.li@unison.net.cn
 * @createTime 2014-3-12
 * @version 1.0
 */
public interface BBOddsBigSmallDao extends BasicDao<BBOddsBigSmallPO>{

	BBOddsBigSmallPO findBy(long matchId, long corpId);
	
	List<BBOddsBigSmallPO> findByMatchId(long matchId);
}
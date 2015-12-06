package com.unison.lottery.weibo.data.service.store.persist.dao;

import java.util.List;
import com.unison.lottery.weibo.data.service.store.persist.entity.BBConcedeScoreOddsPO;

/**
 * @desc 篮球让分赔率数据库操作
 * @author lei.li@unison.net.cn
 * @createTime 2014-3-12
 * @version 1.0
 */
public interface BBConcedeScoreOddsDao extends BasicDao<BBConcedeScoreOddsPO>{

	BBConcedeScoreOddsPO findBy(long matchId, long corpId);
	
	List<BBConcedeScoreOddsPO> findByMatchId(long matchId);
}
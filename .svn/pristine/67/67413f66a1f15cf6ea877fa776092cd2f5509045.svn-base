package com.unison.lottery.weibo.data.service.store.persist.service;

import java.util.List;

import com.unison.lottery.weibo.data.vo.FightHistoryVO;
import com.unison.lottery.weibo.data.vo.FutureMatchVO;
import com.unison.lottery.weibo.data.vo.RecentMatchVO;

/**
 * @Description: 赛事信息查询服务接口（交战历史、近期赛事、未来赛事）
 * @author haoxiang.jiang@unison.net.cn 
 * @date 2014-2-14 上午11:45:40 
 * @version V1.0
 */
public interface MatchInfoService {
	/**
	 * 获取交战历史赛事
	 * @param homeTeamId 
	 * @param guestTeamId
	 * @param size 获取记录条数
	 * @return
	 */
	List<FightHistoryVO> getFightHistoryMatchs(long homeTeamId,long guestTeamId, int size);
	
	/**
	 * 获取近期赛事
	 * @param teamId
	 * @param size 获取记录条数
	 * @return
	 */
	List<RecentMatchVO> getRecentMatchs(long teamId, int size);
	
	/**
	 * 获取未来赛事
	 * @param teamId 
	 * @param size 获取记录条数
	 * @return
	 */
	List<FutureMatchVO> getFutureMatchs(long teamId, int size);
}

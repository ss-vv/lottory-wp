package com.unison.lottery.weibo.data.service.store.persist.service;

import java.util.Date;
import java.util.List;
import com.unison.lottery.weibo.data.service.store.data.MatchResultStats;
import com.unison.lottery.weibo.data.vo.FightHistoryVO;
import com.unison.lottery.weibo.data.vo.FutureMatchVO;
import com.unison.lottery.weibo.data.vo.RecentMatchVO;

/**
 * @desc 篮球赛事信息查询服务接口（交战历史、近期赛事、未来赛事）
 * @author lei.li@unison.net.cn
 * @createTime 2014-3-17
 * @version 1.0
 */
public interface BBMatchInfoService {

	/**
	 * 获取交战历史赛事
	 * @param from
	 * @param to
	 * @param size 获取记录条数
	 * @param homeTeamId
	 * @param guestTeamId
	 * 
	 * @return
	 */
	List<FightHistoryVO> getFightHistoryMatchs(long homeTeamId,
			long guestTeamId, Date from, Date to, int size);
	
	/**
	 * 查询球队的近期赛事
	 * @param teamId
	 * @param size
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
	
	/**
	 * 近期赛事赛果统计
	 * @param teamId
	 * @param size
	 * @return
	 */
	MatchResultStats getRecentMatchsStats(long teamId, int size);
	
	/**
	 * 篮球两队交战历史赛果统计,返回的是主队的“胜平负”
	 * @param homeTeamId
	 * @param guestTeamId
	 * @param from
	 * @param to
	 * @param size
	 * @return
	 */
	MatchResultStats getFightHistoryMatchsStats(long homeTeamId, long guestTeamId, 
			Date from, Date to, int size);
}
package com.unison.lottery.weibo.data.service.store.persist.service;

import java.util.List;
import com.unison.lottery.weibo.data.service.store.data.FBAsianOddResult;
import com.unison.lottery.weibo.data.service.store.data.FBAsianOddVO;
import com.unison.lottery.weibo.data.service.store.data.FBEuropeOddVO;
import com.unison.lottery.weibo.data.service.store.data.FBMatchData;
import com.unison.lottery.weibo.data.service.store.data.FBMatchScore;

/**
 * @desc 赛事数据服务：查询比分、欧赔、亚盘数据
 * @author lei.li@unison.net.cn
 * @createTime 2014-2-11
 * @version 1.0
 */
public interface MatchDataService {

	/**
	 * 根据赛事ID获取竞足赛事比分结果
	 * @param lcMatchId
	 * @return
	 */
	public FBMatchScore getFBMatch(String lclcMatchId);

	/**
	 * 获取最近几场比赛的赛果列表
	 * @param lclcMatchId
	 * @param count	最新几场比赛
	 */
	public FBMatchData getFBLatestMatchResult(String lclcMatchId, int count);
	
	/**
	 * 获取足球对战赛事的欧赔指数
	 * @param lcMatchId
	 * @return
	 */
	List<FBEuropeOddVO> fbEuropeOddList(String lcMatchId);
	
	/**
	 * 获取对战赛事的亚盘水位
	 * @param lcMatchId
	 * @return
	 */
	List<FBAsianOddVO> fbAsianOddList(String lcMatchId);
	
	/**
	 * 足球对战赛事亚盘水位，包含主客队名称
	 * @param lcMatchId
	 * @return
	 */
	FBAsianOddResult fbAsianOddResult(String lcMatchId);
}
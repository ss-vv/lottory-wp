package com.unison.lottery.weibo.web.service;

import java.util.List;

import com.unison.lottery.weibo.data.vo.MatchRecommendVo;

public interface MatchRecommendService {

	/**
	 * flag 为是否取已比赛完的推荐
	 * @param jqzc
	 * @param jclq
	 * @param bjdc
	 * @param flag
	 */
	void getMatchRecommend(List<MatchRecommendVo> jqzc,List<MatchRecommendVo> jclq,List<MatchRecommendVo> bjdc,boolean flag);
}

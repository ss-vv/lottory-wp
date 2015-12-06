package com.xhcms.lottery.commons.persist.service;

import java.util.List;

import com.xhcms.commons.lang.Paging;
import com.xhcms.lottery.commons.data.CTFBMatch;
import com.xhcms.lottery.commons.data.CTFBMatchPreset;
import com.xhcms.lottery.commons.data.ctfb.CTMatchInfo;

public interface CTMatchService {
	/**
	 * 获取指定玩法和期号的传统足球赛事
	 * @param playId
	 * @param issueNumber
	 * @return
	 */
	List<CTFBMatch> listCTFB(String playId,String issueNumber, Paging paging);
	
	/**
	 * 获取指定玩法的期号、赛事和当前在售的期信息列表，封装在一起返回<br>
	 * 1，playId为必填。<br>
	 * 2，如果targetIssueNumber为空，则默认当前在售的最近的期号。
	 * @param playId
	 * @param targetIssueNumber
	 * @return
	 */
	CTMatchInfo getCTMatchInfo(String playId,String targetIssueNumber);
	
	
	CTMatchInfo getCTIssueInfo(String playId, String targetIssueNumber);
	/**
	 * 
	 * @param issue
	 * @return
	 */
	List<CTFBMatchPreset> getCTMatchByIssue(String issue,String playId);
	/**
	 * 预设传统足球比分
	 * @param id
	 * @param halfScore
	 * @param score
	 */
	void updateCTMatchScore(String id,String halfScore,String score,String status);
	
}

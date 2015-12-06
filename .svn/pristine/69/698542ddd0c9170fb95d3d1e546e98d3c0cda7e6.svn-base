package com.xhcms.lottery.admin.persist.service;

import java.util.Date;
import java.util.List;

import com.xhcms.commons.lang.Paging;
import com.xhcms.lottery.commons.data.CTFBMatch;

public interface MatchService {

    /**
     * 查询足球赛事
     * @param paging
     * @param status
     * @param from
     * @param to
     */
    void listFBMatch(Paging paging, int status, Date from, Date to, int matchResult);

    /**修改足球赛事状态
     * @param mid
     * @param status
     */
    void updateFBMatch(Long mid, int status);
    
    /**
     * 查询篮球赛事
     * @param paging
     * @param status
     * @param from
     * @param to
     */
    void listBBMatch(Paging paging, int status, Date from, Date to,
			int matchResult);
    
    /**修改篮球赛事状态
     * @param mid
     * @param status
     */
    void updateBBMatch(Long mid, int status);
    
    /**
     * 根据期号查询传统足彩赛事信息
     * @param issueNumber
     * @return
     */
    List<CTFBMatch> findByIssueNumberAndPlayId(String issueNumber, String playId);

	void updateBBMatchScore(Long id, String finalScorePreset);

	void updateFBMatchScore(Long id, String halfScorePreset, String scorePreset);

}

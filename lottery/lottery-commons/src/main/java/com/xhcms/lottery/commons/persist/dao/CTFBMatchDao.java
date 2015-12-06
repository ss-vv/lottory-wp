package com.xhcms.lottery.commons.persist.dao;

import java.util.Date;
import java.util.List;

import com.xhcms.commons.lang.Paging;
import com.xhcms.commons.persist.Dao;
import com.xhcms.lottery.commons.persist.entity.CTFBMatchPO;

/**
 * 传统足球
 * @author Wang Lei
 *
 */
public interface CTFBMatchDao extends Dao<CTFBMatchPO>{
	
	List<CTFBMatchPO> findOnSale(String playId);
	
	List<CTFBMatchPO> findMAXIssueMatchsByPlayId(String playId);
	
	List<CTFBMatchPO> findByIssueNoAndPlayId(String issueNumber, String playId);
	
	List<CTFBMatchPO> findByIssueNoAndPlayId(String issueNumber, String playId, Paging paging);
	
	List<CTFBMatchPO> find(Paging paging, int status, Date from, Date to);
	
	List<CTFBMatchPO> find(Paging paging, int status, Date from, Date to,String playId);

	List<Object[]> queryCTFBMatchCountByStatus(int status);

	List<String> queryCTFBLeaguesByStatus(int status);
	
	Integer findMatchCount();
	
    List<CTFBMatchPO> findCTFBMatchByIssue(String issue,String playId);
    
    List<Object> findCTFBMatchByIssue_(String issue,String playId);
    
    CTFBMatchPO findCTFBMatchById(String id);
    
    void updateCTFBMatchScore(CTFBMatchPO ctfb);
    Long countHaveMatchResultByIssueNumberAndPlayId(String issueNumber, String playId);
    
}

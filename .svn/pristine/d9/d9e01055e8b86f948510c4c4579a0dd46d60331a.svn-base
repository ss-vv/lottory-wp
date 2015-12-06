package com.xhcms.lottery.commons.persist.dao;

import java.util.List;

import com.xhcms.commons.persist.Dao;
import com.xhcms.lottery.commons.data.ctfb.CTBetContent;
import com.xhcms.lottery.commons.persist.entity.CTBetContentPO;

/**
 * 传统足球投注内容dao
 * @author yonglizhu,lei.wang
 *
 */
public interface CTBetContentDao extends Dao<CTBetContentPO>{
	List<CTBetContentPO> findCtBetContent(Long schemeId);
	
	/**
	 * 	 * 大数据批量插入
	 * @author Wang Lei
	 * @param cTBetContents
	 * @param schemeId
	 * @param issueId
	 */
	void batchInserts(List<CTBetContent> cTBetContents,Long schemeId,Long issueId);

	List<Long> findBetSchemeIdsByIssueNumberAndPlayId(String issueNumber,
			String playId); 
}

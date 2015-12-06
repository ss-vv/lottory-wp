package com.xhcms.lottery.commons.persist.dao;

import com.xhcms.commons.persist.Dao;
import com.xhcms.lottery.commons.persist.entity.CGJIssueInfoPO;

public interface CGJIssueInfoDao extends Dao<CGJIssueInfoPO> {

	CGJIssueInfoPO find(String playType, String season);
}
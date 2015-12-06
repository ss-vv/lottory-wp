package com.xhcms.lottery.commons.persist.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.xhcms.lottery.commons.data.CTFBMatch;
import com.xhcms.lottery.commons.persist.dao.CTFBMatchDao;
import com.xhcms.lottery.commons.persist.service.CTFBMatchBaseService;
import com.xhcms.lottery.utils.POUtils;

/**
 * 传统足彩赛事基础类
 * @author Wang Lei
 *
 */
public class CTFBMatchBaseServiceImpl implements CTFBMatchBaseService {
	@Autowired
	CTFBMatchDao cTFBMatchDao;

	@Override
	@Transactional(readOnly=true)
	public List<CTFBMatch> findByIssueNoAndPlayId(String issueNumber, String playId) {
		return POUtils.copyBeans(cTFBMatchDao.findByIssueNoAndPlayId(issueNumber, playId), CTFBMatch.class);
	}
}

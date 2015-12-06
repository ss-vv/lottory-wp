package com.xhcms.lottery.dc.persist.persister;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.xhcms.lottery.commons.data.IssueInfo;
import com.xhcms.lottery.commons.persist.service.IssueService;
import com.xhcms.lottery.dc.core.Persister;

public class UpdateIssueinfoLCStatusPersisterImpl implements Persister<IssueInfo>{
	
	private Logger logger=LoggerFactory.getLogger(getClass());
	
	@Autowired
	private IssueService issueService;

	@Override
	@Transactional
	public void persist(List<IssueInfo> issueinfos) {
		logger.debug("开始执行更新高频彩期lc_status状态数据入库任务...");
		issueService.updateHFLCStatus();
		
		logger.debug("开始执行更新福彩期lc_status状态数据入库任务...");
		issueService.updateWFLCStatus();
	}

	public IssueService getIssueService() {
		return issueService;
	}

	public void setIssueService(IssueService issueService) {
		this.issueService = issueService;
	}
}
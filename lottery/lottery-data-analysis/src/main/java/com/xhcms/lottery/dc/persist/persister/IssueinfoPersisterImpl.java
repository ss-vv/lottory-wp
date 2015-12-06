package com.xhcms.lottery.dc.persist.persister;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.xhcms.lottery.commons.data.IssueInfo;
import com.xhcms.lottery.commons.persist.service.IssueService;
import com.xhcms.lottery.dc.core.Persister;
import com.xhcms.lottery.utils.IssueStartTimeCalculator;

public class IssueinfoPersisterImpl implements Persister<IssueInfo>{
	private Logger logger=LoggerFactory.getLogger(getClass());
	
	@Autowired
	private IssueService issueService;

	@Override
	@Transactional
	public void persist(List<IssueInfo> issueinfos) {
		logger.debug("开始执行期查询数据入库任务...");
		
		if(null!=issueinfos&&!issueinfos.isEmpty()){
			logger.debug("有需处理数据");
			logger.info("playId="+issueinfos.get(0).getPlayId());
			wrapIssue(issueinfos);
			issueService.saveOrUpdate(issueinfos);
		}
		else{
			logger.debug("没有需处理的数据");
		}
	}

	private void wrapIssue(List<IssueInfo> issueinfos) {
		Calendar c = Calendar.getInstance();
		//对于双色球，如果接口方面没有给出开始时间，则加入自定义开始时间
		if(null != issueinfos && issueinfos.size() > 0) {
			int size = issueinfos.size();
			for(int i = 0; i < size; i++) {
				IssueInfo issue = issueinfos.get(i);
				if(null == issue.getStartTime()) {
					Date closeTime = issue.getCloseTime();
					String lotteryId = issue.getLotteryId();
					Date startTime = IssueStartTimeCalculator.getStartTime(closeTime, lotteryId);
					c.setTime(startTime);
					issueinfos.get(i).setStartTime(c.getTime());
				}
			}
		}
	}

	public IssueService getIssueService() {
		return issueService;
	}

	public void setIssueService(IssueService issueService) {
		this.issueService = issueService;
	}
}

package com.xhcms.lottery.dc.feed.web.action.ssq;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;
import com.xhcms.lottery.commons.data.IssueInfo;
import com.xhcms.lottery.commons.data.ssq.SSQResult;
import com.xhcms.lottery.commons.persist.service.IssueService;
import com.xhcms.lottery.lang.Constants;

/**
 * 开奖结果。
 * 
 * @author Yang Bo
 */
public class AjaxResults extends ActionSupport {
	private static final long serialVersionUID = -2341590313913042456L;
	
	// in ==============
	private int max = 20;	// 默认最大20条
	
	// out =============
	private List<SSQResult> data;
	
	// service =========
	@Autowired
	private IssueService issueService;

	
	public String execute(){
		// (未开售=0, 销售中=1，已截止=2,已开奖=3，已派奖=4)
		String[] status = new String[]{"3", "4"};
		String lotteryId = Constants.SSQ;
		String stopTime = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		List<IssueInfo> issues = issueService.findBBallotRecordsOfWF(lotteryId, status, stopTime, max);
		data = issueService.computeSSQResult(issues);
		return SUCCESS;
	}

	public List<SSQResult> getData() {
		return data;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}
}

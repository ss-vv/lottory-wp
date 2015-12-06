package com.unison.lottery.wap.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.lottery.commons.data.IssueInfo;
import com.xhcms.lottery.commons.persist.service.IssueService;
import com.xhcms.ucenter.action.BaseAction;

/**	开奖信息查询
 * @author lei.li
 * @version 1.0
 */
public class JX11BallotRecordAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	@Autowired
	private IssueService issueService;
	
	private String stopTime;
	
	private List<IssueInfo> list;
	
	private int maxResults;
	
	public String jx11Ballot() {
		String[] status = new String[]{"3", "4"};
		String lotteryId = "JX11";
		if(null == stopTime) {
			stopTime = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		}
		list = issueService.findBBallotRecords(lotteryId, status, stopTime, maxResults);
		return SUCCESS;
	}

	public List<IssueInfo> getList() {
		return list;
	}
	
	public void setList(List<IssueInfo> list) {
		this.list = list;
	}

	public String getStopTime() {
		return stopTime;
	}

	public void setStopTime(String stopTime) {
		this.stopTime = stopTime;
	}
}

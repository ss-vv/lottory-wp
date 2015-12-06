package com.unison.lottery.wap.action.ssq;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.lottery.commons.data.IssueInfo;
import com.xhcms.lottery.commons.persist.service.IssueService;
import com.xhcms.lottery.lang.Constants;
import com.xhcms.ucenter.action.BaseAction;

/**	双色球开奖信息查询
 * @author lei.li
 * @version 1.0
 */
public class SSQBallotRecordAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	@Autowired
	private IssueService issueService;
	
	private String stopTime;
	
	private List<IssueInfo> list;
	
	private int maxResults;
	
	private String lotteryId;
	
	private String issueNumber;
	
	private IssueInfo issue;
	
	private String redBall;
	
	private String blueBall;
	
	private List<Map<String, String>> bonusList;
	
	public String ssqBallot() {
		String[] status = new String[]{"3", "4"};
		String lotteryId = Constants.SSQ;
		if(null == stopTime) {
			stopTime = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		}
		list = issueService.findBBallotRecordsOfWF(lotteryId, status, stopTime, maxResults);
		return SUCCESS;
	}

	public String ssqBallotDetail() {
		if(Constants.SSQ.equals(lotteryId)) {
			if(StringUtils.isBlank(issueNumber)) {
				issue = issueService.findLatestBallotIssue(lotteryId, new Integer[]{Constants.ISSUE_STATUS_SOLD, Constants.ISSUE_STATUS_AWARD});
			} else {
				issue = issueService.findBBallotDetail(lotteryId, issueNumber);
			}
			if(null != issue && StringUtils.isBlank(issue.getIssueNumber())) {
				issue = null;
			}
			if(null != issue) {
				issueNumber = issue.getIssueNumber();
				String bonusCode = issue.getBonusCode();
				if(StringUtils.isNotBlank(bonusCode)) {
					int sep = bonusCode.indexOf("|");
					redBall = bonusCode.substring(0, sep - 1);
					blueBall = bonusCode.substring(sep + 2);
				}
				String bonusInfo = issue.getBonusInfo();
				if(StringUtils.isNotBlank(bonusInfo)) {
					bonusList = parseBonusInfo(issue.getBonusInfo());
				}
			}
		}
		return SUCCESS;
	}
	
	List<Map<String, String>> parseBonusInfo(String bonusInfo) {
		List<Map<String, String>> bonusList = new ArrayList<Map<String,String>>();
		String[] level = bonusInfo.split(";");
		for(String s : level) {
			Map<String, String> m = new HashMap<String, String>();
			int first = s.indexOf(" ");
			int last = s.lastIndexOf(" ");
			String note = s.substring(first+1, last-1);
			String bonus = s.substring(last+1);
			m.put("note", note);
			m.put("bonus", bonus);
			bonusList.add(m);
		}
		return bonusList;
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

	public void setLotteryId(String lotteryId) {
		this.lotteryId = lotteryId;
	}

	public void setIssueNumber(String issueNumber) {
		this.issueNumber = issueNumber;
	}

	public String getIssueNumber() {
		return issueNumber;
	}
	
	public IssueInfo getIssue() {
		return issue;
	}

	public List<Map<String, String>> getBonusList() {
		return bonusList;
	}

	public String getRedBall() {
		return redBall;
	}

	public String getBlueBall() {
		return blueBall;
	}
}

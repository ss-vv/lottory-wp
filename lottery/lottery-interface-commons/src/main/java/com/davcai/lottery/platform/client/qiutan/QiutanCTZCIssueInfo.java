package com.davcai.lottery.platform.client.qiutan;

import java.util.List;

import com.davcai.lottery.platform.client.LotteryPlatformBaseResponse;

/**
 * 球探传统足彩期信息
 * @author haoxiang.jiang@davcai.com
 * @date 2015年4月13日 下午5:49:57
 */
public class QiutanCTZCIssueInfo extends LotteryPlatformBaseResponse{
	
	private String issueid;
	private String issuenum;
	private String m_stoptime;
	private String pre_issuenum;//上一期期号
	private String pre_bonusinfo;//上一期中奖号码
	private List<QiutanCTZCMatch> ctzcMatchs;
	public String getIssueid() {
		return issueid;
	}
	public void setIssueid(String issueid) {
		this.issueid = issueid;
	}
	public String getIssuenum() {
		return issuenum;
	}
	public void setIssuenum(String issuenum) {
		this.issuenum = issuenum;
	}
	public String getM_stoptime() {
		return m_stoptime;
	}
	public void setM_stoptime(String m_stoptime) {
		this.m_stoptime = m_stoptime;
	}
	public List<QiutanCTZCMatch> getCtzcMatchs() {
		return ctzcMatchs;
	}
	public void setCtzcMatchs(List<QiutanCTZCMatch> ctzcMatchs) {
		this.ctzcMatchs = ctzcMatchs;
	}
	public String getPre_issuenum() {
		return pre_issuenum;
	}
	public void setPre_issuenum(String pre_issuenum) {
		this.pre_issuenum = pre_issuenum;
	}
	public String getPre_bonusinfo() {
		return pre_bonusinfo;
	}
	public void setPre_bonusinfo(String pre_bonusinfo) {
		this.pre_bonusinfo = pre_bonusinfo;
	}
	
}

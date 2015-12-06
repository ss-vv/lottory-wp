package com.xhcms.lottery.commons.data;

import java.io.Serializable;
import java.util.List;
import com.xhcms.lottery.commons.data.IssueInfo;
import com.xhcms.lottery.commons.persist.entity.MatchPlay;

public class BJDCMatchsViewResult implements Serializable {

	private static final long serialVersionUID = -2758520174886995155L;

	private List<IssueInfo> issues;

	private List<MatchPlay> matchs;
	
	/**当前期号*/
	private String currIssue;
	
	private boolean onSale;

	public List<IssueInfo> getIssues() {
		return issues;
	}

	public void setIssues(List<IssueInfo> issues) {
		this.issues = issues;
	}

	public List<MatchPlay> getMatchs() {
		return matchs;
	}

	public void setMatchs(List<MatchPlay> matchs) {
		this.matchs = matchs;
	}

	public String getCurrIssue() {
		return currIssue;
	}

	public void setCurrIssue(String currIssue) {
		this.currIssue = currIssue;
	}

	public boolean isOnSale() {
		return onSale;
	}

	public void setOnSale(boolean onSale) {
		this.onSale = onSale;
	}
}
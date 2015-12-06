package com.unison.lottery.mc.uni.parser;

import java.util.List;
import com.xhcms.lottery.commons.data.IssueInfo;


public class QueryIssueResponseParserStatus extends ParserStatus{
	
	private List<IssueInfo> issueinfos;

	public List<IssueInfo> getIssueinfos() {
		return issueinfos;
	}

	public void setIssueinfos(List<IssueInfo> issueinfos) {
		this.issueinfos = issueinfos;
	}
}

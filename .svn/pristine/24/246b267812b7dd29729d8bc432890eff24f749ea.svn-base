package com.xhcms.lottery.dc.feed.web.action.matchplay;

import org.springframework.beans.factory.annotation.Autowired;
import com.opensymphony.xwork2.Action;
import com.xhcms.commons.lang.Data;
import com.xhcms.lottery.dc.feed.persist.service.CTMatchService;
import com.xhcms.lottery.lang.Constants;
import com.xhcms.lottery.commons.persist.service.MatchService;

public class AjaxListAction implements Action{
	@Autowired
	private MatchService matchService;
	@Autowired
	private CTMatchService cTMatchService;

	private Data data;
	private String playId;
	private String issueNumber;
	
	@Override
	public String execute() {
		if(Constants.PLAY_05_ZC_2.equals(playId)){
			data = Data.success(matchService.listMixFBOnSale(playId));
		}else{
			data = Data.success(matchService.listFBOnSale(playId));
		}
		return SUCCESS;
	}
	
	public String basketball() {
	    if(Constants.PLAY_10_LC_2.equals(playId)){
			data = Data.success(matchService.listMixBBOnSale(playId));
		}else{
			data = Data.success(matchService.listBBOnSale(playId));
		}
	    return SUCCESS;
	}
	
	/*** 获取传统足彩在售赛事列表	 */
	public String ctFootball() {
		data = Data.success(cTMatchService.getCTMatchInfo(playId, issueNumber));
		return SUCCESS;
	}

	public Data getData() {
        return data;
    }

    public void setPlayId(String playId) {
		this.playId = playId;
	}

	public String getIssueNumber() {
		return issueNumber;
	}

	public void setIssueNumber(String issueNumber) {
		this.issueNumber = issueNumber;
	}
	
}

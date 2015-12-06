package com.xhcms.lottery.admin.web.action.match;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.lottery.admin.web.action.BaseListAction;
import com.xhcms.lottery.commons.data.CTFBMatch;
import com.xhcms.lottery.commons.data.CTFBMatchPreset;
import com.xhcms.lottery.commons.persist.service.CTMatchService;
import com.xhcms.lottery.commons.persist.service.IssueService;
import com.xhcms.lottery.lang.LotteryId;
import com.xhcms.lottery.lang.PlayType;
/**
 * 预处理传统足球赛果列表
 * @author haohao
 *
 */
public class CTFBMatchForPresetResultAction extends BaseListAction{

	private static final long serialVersionUID = -6268683463507389819L;
	@Autowired
	private IssueService issueService;
	@Autowired
	private CTMatchService ctMatchService;
	private List<CTFBMatch> ctfbMatchList;
	private List<CTFBMatchPreset>  presetList;
	private List<Long> issueList;
	private String issue;
	private Date now=new Date();
	private String beginIssueNumber="15036";//从没有设置赛果的期开始查
	private String playId;//玩法
	public String execute(){
		issueList=issueService.findCTZTIssueInfoList(LotteryId.CTZC,beginIssueNumber,0);
		if(StringUtils.isBlank(playId)){
			playId=PlayType.CTZC_14.getShortPlayStr();
		}
		if(StringUtils.isNotBlank(issue)){
			presetList=ctMatchService.getCTMatchByIssue(issue,playId);
		}else{
			if(!issueList.isEmpty() && issueList.size()>0){
				presetList=ctMatchService.getCTMatchByIssue(issueList.get(0)+"",playId);
			}
		}
		return SUCCESS;
	}
	public List<Long> getIssueList() {
		return issueList;
	}
	public void setIssueList(List<Long> issueList) {
		this.issueList = issueList;
	}
	public IssueService getIssueService() {
		return issueService;
	}
	public void setIssueService(IssueService issueService) {
		this.issueService = issueService;
	}
	public CTMatchService getCtMatchService() {
		return ctMatchService;
	}
	public void setCtMatchService(CTMatchService ctMatchService) {
		this.ctMatchService = ctMatchService;
	}
	public List<CTFBMatch> getCtfbMatchList() {
		return ctfbMatchList;
	}
	public void setCtfbMatchList(List<CTFBMatch> ctfbMatchList) {
		this.ctfbMatchList = ctfbMatchList;
	}
	public String getIssue() {
		return issue;
	}
	public void setIssue(String issue) {
		this.issue = issue;
	}
	public Date getNow() {
		return now;
	}
	public void setNow(Date now) {
		this.now = now;
	}
	public String getPlayId() {
		return playId;
	}
	public void setPlayId(String playId) {
		this.playId = playId;
	}
	public List<CTFBMatchPreset> getPresetList() {
		return presetList;
	}
	public void setPresetList(List<CTFBMatchPreset> presetList) {
		this.presetList = presetList;
	}
	
}

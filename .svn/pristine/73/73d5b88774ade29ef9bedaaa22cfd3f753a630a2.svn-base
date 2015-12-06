package com.unison.lottery.wap.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.Cookie;

import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.wap.utils.ChannelCookieGenerator;
import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.data.IssueInfo;
import com.xhcms.lottery.commons.persist.service.BetException;
import com.xhcms.lottery.commons.persist.service.BetSchemeService;
import com.xhcms.lottery.commons.persist.service.IssueService;
import com.xhcms.ucenter.action.BaseAction;

public class IndexAction extends BaseAction {
	private static final long serialVersionUID = -3955035997869421877L;
	public static final String CHANNEL_COOKIE_NAME = "_cid_davcai_";
	public static final  int REMEMBERME_MAX_AGE = 7889231;
	
	@Autowired
    private ChannelCookieGenerator channelCookieGenerator;
	
	//渠道ID
	private String cid;
	
	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}
	
 	@Autowired
 	private IssueService issueService;
 	
 	@Autowired
 	private BetSchemeService betSchemeService;
	
	private IssueInfo issueInfo;
	
 	//最近5期开奖号码
 	private List<IssueInfo> ballotRecordList;
 	
 	//最近5个中奖方案
 	private List<BetScheme> newestBetSchemes;
 	
	//任|直|组选几个数
	private Integer selectNum;

	//选择类型：0 手选；1胆拖；2机选
	private Integer selectType;
	
	//投注倍数
	private String times = "1";

	@Override
	public String execute() {
		try {
			//取得期信息
			if(selectType == null) {
				selectType = 0;
			}
			if(selectNum == null) {
				selectNum = 5;
			}
			
			//取得期信息
			issueInfo = issueService.getCurrentSalingIssueForShow("JX11", new Date());
			
			String lotteryId = "JX11";
			//取得最新5期开奖号码
			String[] status = new String[]{"3", "4"};
			int maxResults = 5;
			String stopTime = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			ballotRecordList = issueService.findBBallotRecords(lotteryId, status, stopTime, maxResults);
			
			//取得最新5个中奖用户
			newestBetSchemes = betSchemeService.findNewestWinScheme(lotteryId, maxResults);
		} catch (BetException e) {
			addActionError(getText("error."+e.getErrorCode()));
		}
		
		//cookie中记录渠道
		if(cid != null ){
			Cookie[] cookies = request.getCookies();
			if(cookies !=null){
				for (Cookie cookie : cookies) {
					if(cookie.getName().equals(CHANNEL_COOKIE_NAME)&&cookie.getValue()!=null){
						if(!cookie.getValue().equals(cid)){
							channelCookieGenerator.removeCookie(response);
							channelCookieGenerator.addCookie(request, response, cid);
						}
					}else{
						channelCookieGenerator.addCookie(request, response, cid);
					}
				}
			}else{
				channelCookieGenerator.addCookie(request, response, cid);
			}
		}
		
		return SUCCESS;
	}
	
	
	public IssueInfo getIssueInfo() {
		return issueInfo;
	}

	public void setIssueInfo(IssueInfo issueInfo) {
		this.issueInfo = issueInfo;
	}
	
	public Integer getSelectNum() {
		return selectNum;
	}
	
	public void setSelectNum(Integer selectNum) {
		this.selectNum = selectNum;
	}
	
	public Integer getSelectType() {
		return selectType;
	}

	public void setSelectType(Integer selectType) {
		this.selectType = selectType;
	}
	
	public String getTimes() {
		return times;
	}

	public void setTimes(String times) {
		this.times = times;
	}
	
	public List<IssueInfo> getBallotRecordList() {
		return ballotRecordList;
	}

	public void setBallotRecordList(List<IssueInfo> ballotRecordList) {
		this.ballotRecordList = ballotRecordList;
	}
	
	public List<BetScheme> getNewestBetSchemes() {
		return newestBetSchemes;
	}

	public void setNewestBetSchemes(List<BetScheme> newestBetSchemes) {
		this.newestBetSchemes = newestBetSchemes;
	}
}

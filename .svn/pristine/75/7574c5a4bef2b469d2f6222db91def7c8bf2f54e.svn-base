package com.unison.lottery.wap.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.exception.XHRuntimeException;
import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.data.ChooseType;
import com.xhcms.lottery.commons.data.IssueInfo;
import com.xhcms.lottery.commons.data.DigitalBetRequest;
import com.xhcms.lottery.commons.data.User;
import com.xhcms.lottery.commons.lang.Channel;
import com.xhcms.lottery.commons.lang.Partner;
import com.xhcms.lottery.commons.persist.service.BetException;
import com.xhcms.lottery.commons.persist.service.DigitalBetService;
import com.xhcms.lottery.commons.persist.service.IssueService;
import com.xhcms.lottery.lang.PlayType;
import com.xhcms.ucenter.action.BaseAction;

public class BuyJX11s5BetAction extends BaseAction{
	private static final long serialVersionUID = 7861315462015477126L;
	
	@Autowired
	private IssueService issueService;
	
	@Autowired
	private DigitalBetService digitalBetService;
	
	private String lotteryId;
	private Integer playId;
	private Integer selectNum;
	private String playName;
	private Integer selectType;
	private Integer times;
	private String 	content;
	private Integer betCount;	// 注数
	private List<String> resultList = new ArrayList<String>();
	private IssueInfo issueInfo;


	public IssueInfo getIssueInfo() {
		return issueInfo;
	}

	public void setIssueInfo(IssueInfo issueInfo) {
		this.issueInfo = issueInfo;
	}

	public Integer getBetCount() {
		return betCount;
	}

	public void setBetCount(Integer betCount) {
		this.betCount = betCount;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(String lotteryId) {
		this.lotteryId = lotteryId;
	}

	public Integer getPlayId() {
		return playId;
	}

	public void setPlayId(Integer playId) {
		this.playId = playId;
	}

	public Integer getSelectNum() {
		return selectNum;
	}

	public void setSelectNum(Integer selectNum) {
		this.selectNum = selectNum;
	}

	public String getPlayName() {
		return playName;
	}

	public void setPlayName(String playName) {
		this.playName = playName;
	}

	public Integer getSelectType() {
		return selectType;
	}

	public void setSelectType(Integer selectType) {
		this.selectType = selectType;
	}

	public Integer getTimes() {
		return times;
	}

	public void setTimes(Integer times) {
		this.times = times;
	}

	@Override
	public String execute() {
		
		try {
			issueInfo = issueService.getCurrentSalingIssueForShow(lotteryId, new Date());
		} catch (BetException e) {
			addActionError(getText("error."+e.getErrorCode()));
			return INPUT;
		}
		
		if(!resultList.isEmpty()){
			if( playId != null && selectNum !=null && selectType!=null){
				DigitalBetRequest betContent = createBetContent();
				try {
					User user = getSelf();
					if(user != null){
						betContent.setUserId(user.getId());
						BetScheme scheme = this.digitalBetService.bet(betContent);
						request.setAttribute("scheme", scheme);
						request.setAttribute("issueNumber", issueInfo.getIssueNumber());
					}
					else{
						return INPUT;
					}
				} catch (BetException e) {
					addActionError(getText("error."+e.getErrorCode()));
					return INPUT;
				} catch (XHRuntimeException xe){
					addActionError(getText("error." + xe.getCode()));
					return INPUT;
				}
				return SUCCESS;
			}
		}
		return INPUT;
	}

	private DigitalBetRequest createBetContent() {
		DigitalBetRequest betRequest = new DigitalBetRequest();
		betRequest.setPlayType(PlayType.valueOfPlayId(playId));
		betRequest.setTotalNotesCount(betCount);
		betRequest.setMultiple(times);
		betRequest.setMoney(betCount * times * 2);
		betRequest.setLotteryId(lotteryId);
		betRequest.setIssue(issueInfo.getIssueNumber());
		
		betRequest.setBetContents(resultList);
		betRequest.setChooseType(ChooseType.valueOfIndex(selectType));
		betRequest.setChannel(Channel.WAP);
		betRequest.setPartner(Partner.DAVCAI);
		return betRequest;
	}


	public List<String> getResultList() {
		return resultList;
	}

	public void setResultList(List<String> resultList) {
		this.resultList = resultList;
	}
}


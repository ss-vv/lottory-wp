package com.xhcms.lottery.admin.web.action.preset;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.commons.lang.Paging;
import com.xhcms.lottery.admin.web.action.BaseListAction;
import com.xhcms.lottery.commons.data.IssueInfo;
import com.xhcms.lottery.commons.persist.service.IssueService;
import com.xhcms.lottery.lang.LotteryId;
import com.xhcms.lottery.lang.PlayType;

public class IssueListAction extends BaseListAction {
	private static final long serialVersionUID = -2838062419897513893L;
	
	@Autowired
	IssueService issueService;
	
	private int maxResult;
	private List<IssueInfo> issueInfos;
	
	private String lotteryId;
	private String playId;
	private String issueNumber;
	private int pageSize=20;
	private int pageNo=1;
	private float yidengjiang;
	private float erdengjiang;
	private float renjiu;
	
	private boolean setBonusResult;
	
	public String setCTZCBonus() {
		setBonusResult=false;
		float bonus1=0;
		float bonus2=0;
		if(renjiu > 0){
			bonus1 = renjiu;
		} else {
			bonus1 = yidengjiang;
			bonus2 = erdengjiang;
		}
		setBonusResult = issueService.updateCTZCPresetBonus(issueNumber,playId,bonus1,bonus2);
		return SUCCESS;
	}
	@SuppressWarnings("unchecked")
	public String ctzc() {
		lotteryId = LotteryId.CTZC.name();
		PlayType playType;
		if(StringUtils.isNotBlank(playId)){
			playType=PlayType.valueOfLcPlayId(playId);
		} else {
			playType=null;
		}
		paging = new Paging();
		paging.setPageNo(pageNo);
		paging.setMaxResults(pageSize);
		paging.setCount(true);
		paging = issueService.findByPage(LotteryId.get(lotteryId),playType,issueNumber,paging);
		issueInfos = (List<IssueInfo>) paging.getResults();
		return SUCCESS;
	}
	
	public String execute() {
		return SUCCESS;
	}

	public int getMaxResult() {
		return maxResult;
	}

	public void setMaxResult(int maxResult) {
		this.maxResult = maxResult;
	}

	public Paging getPaging() {
		return paging;
	}
	public List<IssueInfo> getIssueInfos() {
		return issueInfos;
	}

	public void setPaging(Paging paging) {
		this.paging = paging;
	}

	public String getPlayId() {
		return playId;
	}

	public void setPlayId(String playId) {
		this.playId = playId;
	}

	public String getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(String lotteryId) {
		this.lotteryId = lotteryId;
	}

	public String getIssueNumber() {
		return issueNumber;
	}

	public void setIssueNumber(String issueNumber) {
		this.issueNumber = issueNumber;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public boolean isSetBonusResult() {
		return setBonusResult;
	}

	public void setSetBonusResult(boolean setBonusResult) {
		this.setBonusResult = setBonusResult;
	}
	public float getYidengjiang() {
		return yidengjiang;
	}
	public void setYidengjiang(float yidengjiang) {
		this.yidengjiang = yidengjiang;
	}
	public float getErdengjiang() {
		return erdengjiang;
	}
	public void setErdengjiang(float erdengjiang) {
		this.erdengjiang = erdengjiang;
	}
	public float getRenjiu() {
		return renjiu;
	}
	public void setRenjiu(float renjiu) {
		this.renjiu = renjiu;
	}
}

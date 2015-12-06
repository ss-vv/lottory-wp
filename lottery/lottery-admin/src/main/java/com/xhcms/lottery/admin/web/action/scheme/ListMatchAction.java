package com.xhcms.lottery.admin.web.action.scheme;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.xhcms.lottery.admin.persist.service.BetSchemeService;
import com.xhcms.lottery.admin.web.action.BaseAction;
import com.xhcms.lottery.commons.data.BetMatch;
import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.data.CTFBMatch;
import com.xhcms.lottery.commons.data.IssueInfo;
import com.xhcms.lottery.commons.data.ctfb.CTBetContent;
import com.xhcms.lottery.commons.persist.service.CGJTeamService;
import com.xhcms.lottery.commons.persist.service.CTBetService;
import com.xhcms.lottery.commons.persist.service.CTFBMatchBaseService;
import com.xhcms.lottery.commons.persist.service.IssueService;
import com.xhcms.lottery.dc.data.CGJTeam;
import com.xhcms.lottery.lang.Constants;
import com.xhcms.lottery.lang.PlayType;

public class ListMatchAction extends BaseAction {

    private static final long serialVersionUID = 128142404801446862L;

    @Autowired
    private BetSchemeService betSchemeService;
    
    @Autowired
    private CTBetService cTBetService;
    
    @Autowired
    private CTFBMatchBaseService cTFBMatchBaseService;
    
    @Autowired
    private IssueService issueService;
    
    private List<BetMatch> matchs;
    private List<CTFBMatch> cTFBMatchs;
    private List<CTBetContent> cTBetContents;
    private IssueInfo issueInfo;
    private Long id;
    private String playId;
    private String lotteryId;
    private int chooseType;
    private BetScheme betScheme;
    
    @Autowired
    private CGJTeamService cgjTeamService;

    @Override
    public String execute() {
    	String ret = null;
    	betScheme = betSchemeService.getScheme(id);
    	lotteryId = betScheme.getLotteryId();
    	if(Constants.JCZQ.equals(lotteryId) || Constants.JCLQ.equals(lotteryId)) {
    		if(loadMatchs()) {
    			matchs = betSchemeService.listMatch(id);
    		}
    		ret = SUCCESS;
    	} else if(Constants.CTZC.equals(lotteryId)) {
    		//取得投注内容
    		cTBetContents = cTBetService.findCtBetContent(id);
    		if(null != cTBetContents && cTBetContents.size() > 0) {
    			//取得选择方式：手选，机选
    			chooseType = cTBetContents.get(0).getChooseType();
    			//取得方案赛事
    			String issueNumber = cTBetContents.get(0).getIssueNumber();
    			cTFBMatchs = cTFBMatchBaseService.findByIssueNoAndPlayId(issueNumber, betScheme.getPlayId());
    			//取得期信息
    			Long issueId = cTBetContents.get(0).getIssueId();
    			issueInfo = issueService.findById(issueId);
    		}
    		ret = "ctmatch";
    	}
    	betScheme.setMatchs(matchs);
        return ret;
    }
    
    private boolean loadMatchs() {
    	boolean result = PlayType.JCSJBGJ.getShortPlayStr().equals(betScheme.getPlayId());
    	List<CGJTeam> cgjTeams = cgjTeamService.listTeamsBySchemeId(betScheme.getId());
    	betScheme.setCgjTeams(cgjTeams);
    	return !result;
    }
    
    public IssueInfo getIssueInfo() {
		return issueInfo;
	}

	public void setIssueInfo(IssueInfo issueInfo) {
		this.issueInfo = issueInfo;
	}

	public int getChooseType() {
		return chooseType;
	}

	public void setChooseType(int chooseType) {
		this.chooseType = chooseType;
	}

	public List<BetMatch> getMatchs() {
        return matchs;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public List<CTBetContent> getCTBetContents() {
		return cTBetContents;
	}

	public void setCTBetContents(List<CTBetContent> cTBetContents) {
		this.cTBetContents = cTBetContents;
	}

	public List<CTFBMatch> getCTFBMatchs() {
		return cTFBMatchs;
	}

	public void setCTFBMatchs(List<CTFBMatch> cTFBMatchs) {
		this.cTFBMatchs = cTFBMatchs;
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

	public BetScheme getScheme() {
		return betScheme;
	}
}

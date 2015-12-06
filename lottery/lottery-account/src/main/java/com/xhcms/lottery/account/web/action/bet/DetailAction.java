package com.xhcms.lottery.account.web.action.bet;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.weibo.common.service.SchemeService;
import com.xhcms.exception.XHRuntimeException;
import com.xhcms.lottery.account.service.AccountQueryService;
import com.xhcms.lottery.account.web.action.BaseAction;
import com.xhcms.lottery.commons.data.BetMatch;
import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.data.IssueInfo;
import com.xhcms.lottery.commons.data.PlayMatch;
import com.xhcms.lottery.commons.data.Ticket;
import com.xhcms.lottery.commons.persist.service.AccountService;
import com.xhcms.lottery.commons.persist.service.BetSchemeService;
import com.xhcms.lottery.commons.persist.service.CGJTeamService;
import com.xhcms.lottery.commons.persist.service.IssueService;
import com.xhcms.lottery.dc.data.CGJTeam;
import com.xhcms.lottery.lang.AppCode;
import com.xhcms.lottery.lang.Constants;
import com.xhcms.lottery.lang.EntityType;
import com.xhcms.lottery.lang.PlayType;
import com.xhcms.lottery.utils.CombineBetMatchUtil;

/**
 * <p>Title: 方案详情</p>
 * <p>Description: </p>
 * <p>Copyright：Copyright (c) 2011</p>
 * <p>Company：XingHui Spirit (Beijing) Technical Co.,Ltd.</p>
 * 
 * @author jiajiancheng
 * @version 1.0.0
 */
public class DetailAction extends BaseAction {

    private static final long serialVersionUID = 6282404139035946071L;

    private Long id;
    private BigDecimal sponsorAward;
    private BetScheme scheme;
    private BigDecimal sumBetAmount;
    private BigDecimal sumBonus;
    private List<Ticket> tickets;
    private Map<String, PlayMatch> matches;
    private IssueInfo issueInfo;
    
    @Autowired
    private AccountService accountService;
    
    @Autowired
    private AccountQueryService accountQueryService;

    @Autowired
    private BetSchemeService betSchemeService;
    @Autowired
    private SchemeService schemeService;
    
    @Autowired
    private IssueService issueService;
    
    @Autowired
    private CGJTeamService cgjTeamService;
    
    public String execute() {
        if (id == null) {
            throw new XHRuntimeException(AppCode.SCHEME_NOT_EXIST);
        }
        Long userId = getUserId();
        
        scheme = schemeService.viewSchemeCache(id, userId, EntityType.DISPLAY_ALONE);
        
        if(PlayType.JCSJBGJ.getShortPlayStr().equals(scheme.getPlayId())) {
        	List<CGJTeam> cgjTeamList = cgjTeamService.listTeamsBySchemeId(id);
    		scheme.setCgjTeams(cgjTeamList);
        }
        scheme.setPassTypeIds(scheme.getPassTypeIds().replace(',', ' ').replaceAll("@", "串"));
        combinationInfo();
        
        sponsorAward = accountService.getAccount(scheme.getSponsorId()).getTotalAward();
        sponsorAward = sponsorAward==null?new BigDecimal(0.00):sponsorAward;
        
        BigDecimal[] sums = accountQueryService.sumBonus(id, userId);
        sumBetAmount = sums[0];
        sumBonus = sums[1];
        
        tickets = betSchemeService.listTicket(id, userId,EntityType.DISPLAY_ALONE);
        
        //解析出票数据中的odds 
        parseResultOdds(tickets,matches);
        //将同一赛事不同玩法合并
        scheme = CombineBetMatchUtil.combine(scheme);
        return SUCCESS;
    }
    
    /**组合需要的信息*/
    private void combinationInfo(){
    	if(scheme.getLotteryId().equals(Constants.CTZC)){
    		issueInfo = issueService.findByIssueAndPlayId(scheme.getPlayId(), scheme.getCtBetContents().get(0).getIssueNumber());
    	}else if(scheme.getLotteryId().equals(Constants.SSQ)){
    		issueInfo = issueService.findByIssue(scheme.getLotteryId(), scheme.getDigitalBetContents().get(0).getIssueNumber());
    	}else{
    		matches = new HashMap<String, PlayMatch>();
            List<BetMatch> mlist = scheme.getMatchs();
    		if (mlist != null && mlist.size() > 0) {
    			for (BetMatch m : mlist) {
    				matches.put(m.getCode(), (PlayMatch) m);
    			}
    		}
    	}
    }
    
    public BetScheme getScheme() {
        return scheme;
    }

    public BigDecimal getSponsorAward() {
        return sponsorAward;
    }

    public BigDecimal getSumBetAmount() {
        return sumBetAmount;
    }

    public BigDecimal getSumBonus() {
        return sumBonus;
    }

    public void setId(Long id) {
        this.id = id;
    }

	public List<Ticket> getTickets() {
		return tickets;
	}

	public Map<String, PlayMatch> getMatches() {
		return matches;
	}

	public IssueInfo getIssueInfo() {
		return issueInfo;
	}

	public void setIssueInfo(IssueInfo issueInfo) {
		this.issueInfo = issueInfo;
	}

}

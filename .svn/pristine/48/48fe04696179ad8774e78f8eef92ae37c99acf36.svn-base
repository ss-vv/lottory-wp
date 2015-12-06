package com.unison.lottery.wap.action;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.lottery.commons.data.Account;
import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.data.DigitalBetContent;
import com.xhcms.lottery.commons.data.IssueInfo;
import com.xhcms.lottery.commons.data.User;
import com.xhcms.lottery.commons.persist.service.AccountQueryService;
import com.xhcms.lottery.commons.persist.service.AccountService;
import com.xhcms.lottery.commons.persist.service.BetSchemeService;
import com.xhcms.lottery.commons.persist.service.IssueService;
import com.xhcms.lottery.commons.persist.service.DigitalBetService;
import com.xhcms.lottery.lang.EntityType;
import com.xhcms.ucenter.action.BaseAction;
import com.xhcms.ucenter.lang.Constant;
/**
 * <p>Title: 查看账户明细</p>
 * <p>Description: </p>
 * <p>Copyright：Copyright (c) 2011</p>
 * 
 * @author peng.qi
 * 
 */
public class SchemeDetailAction extends BaseAction {


	private static final long serialVersionUID = 8460728659218780939L;

	@Autowired
    private AccountService accountService;
	
	@Autowired
	private AccountQueryService accountQueryService;
	
	@Autowired
	private BetSchemeService schemeService;
	
	@Autowired
	private DigitalBetService digitalBetService;
	
	@Autowired
	private IssueService issueService;

    private Account account;
    
    private Long schemeId;
    
    private List<DigitalBetContent> jx11betContents;
    
    private BetScheme scheme;
    
	private BigDecimal sumBetAmount;
    
    private BigDecimal sumBonus;
    
    private BigDecimal sponsorAward;
    
    private IssueInfo issueInfo;
    
	public IssueInfo getIssueInfo() {
		return issueInfo;
	}

	public void setIssueInfo(IssueInfo issueInfo) {
		this.issueInfo = issueInfo;
	}

	public BigDecimal getSponsorAward() {
		return sponsorAward;
	}

	public void setSponsorAward(BigDecimal sponsorAward) {
		this.sponsorAward = sponsorAward;
	}

	public BetScheme getScheme() {
		return scheme;
	}

	public void setScheme(BetScheme scheme) {
		this.scheme = scheme;
	}
    
    public Long getSchemeId() {
		return schemeId;
	}

	public void setSchemeId(Long schemeId) {
		this.schemeId = schemeId;
	}


	public BigDecimal getSumBetAmount() {
		return sumBetAmount;
	}

	public void setSumBetAmount(BigDecimal sumBetAmount) {
		this.sumBetAmount = sumBetAmount;
	}

	public BigDecimal getSumBonus() {
		return sumBonus;
	}

	public void setSumBonus(BigDecimal sumBonus) {
		this.sumBonus = sumBonus;
	}



	/**
     * 账户明细
     */
    @Override
    public String execute() {
    	User user  = (User)request.getSession().getAttribute(Constant.USER_KEY);
        if(user!=null){
        	account = accountService.getAccount(user.getId());
        	jx11betContents = digitalBetService.findHfBetContent(schemeId);
        	
        	scheme = schemeService.viewScheme(schemeId, EntityType.DISPLAY_ALONE);
        	scheme.setPassTypeIds(scheme.getPassTypeIds().replace(',', ' ').replaceAll("@", "串"));
        	for (DigitalBetContent betContent : jx11betContents) {
        		issueInfo = issueService.findByIssue(scheme.getLotteryId(), betContent.getIssueNumber());
        		break;
        	}
            
            sponsorAward = accountService.getAccount(scheme.getSponsorId()).getTotalAward();
            sponsorAward = sponsorAward==null?new BigDecimal(0.00):sponsorAward;
            
            
        	
            
        	return SUCCESS;
        }else{
        	return LOGIN;
        }
    }

    public List<DigitalBetContent> getJx11betContents() {
		return jx11betContents;
	}

	public void setJx11betContents(List<DigitalBetContent> jx11betContents) {
		this.jx11betContents = jx11betContents;
	}


    public Account getAccount() {
        return account;
    }
    
    public void setAccount(Account account) {
		this.account = account;
	}

}

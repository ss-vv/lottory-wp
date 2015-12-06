package com.xhcms.lottery.admin.web.action.scheme;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.xwork.ArrayUtils;
import org.apache.commons.lang.xwork.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.xhcms.exception.XHRuntimeException;
import com.xhcms.lottery.admin.web.action.BaseAction;
import com.xhcms.lottery.commons.data.BetMatch;
import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.data.IssueInfo;
import com.xhcms.lottery.commons.data.PlayMatch;
import com.xhcms.lottery.commons.data.Ticket;
import com.xhcms.lottery.commons.persist.service.AccountQueryService;
import com.xhcms.lottery.commons.persist.service.AccountService;
import com.xhcms.lottery.commons.persist.service.BetSchemeService;
import com.xhcms.lottery.commons.persist.service.IssueService;
import com.xhcms.lottery.lang.AppCode;
import com.xhcms.lottery.lang.Constants;
import com.xhcms.lottery.lang.EntityType;
import com.xhcms.ucenter.sso.client.UserProfile;
import com.xhcms.ucenter.sso.client.UserProfileThreadContextHolder;

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
    private IssueService issueService;
    
    public UserProfile getUser(){
        return UserProfileThreadContextHolder.getUserProfile();
    }
    
    public long getUserId(){
        return getUser().getId();
    }
    
    public String execute() {
        if (id == null) {
            throw new XHRuntimeException(AppCode.SCHEME_NOT_EXIST);
        }
        Long userId = 1676L;//getUserId();
        scheme = betSchemeService.viewScheme(id, userId,EntityType.DISPLAY_ALONE);
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
        
        return SUCCESS;
    }
    
    /**
	 * 解析出票数据中的odds
	 * 1.700-1.700-1.750-1.750@173.5B152.5B190.5B174.5
	 * @param tickets
	 */
	protected void parseResultOdds(List<Ticket> tickets,Map<String, PlayMatch> matches) {
		if(tickets == null || tickets.size() == 0 || matches == null || matches.size() == 0) {
			return;
		}
		for (Ticket t : tickets) {
			String codes = t.getCode();
			String odds = t.getOdds();
			String defaultScore = "";

			String codeArr[] = ArrayUtils.EMPTY_STRING_ARRAY;
			String oddArr[] = ArrayUtils.EMPTY_STRING_ARRAY;
			String scoreArr[] = ArrayUtils.EMPTY_STRING_ARRAY;
			if (StringUtils.isNotEmpty(codes)) {
				codeArr = codes.split("-");
				if (StringUtils.isNotEmpty(odds)) {
					int scoreIndex = odds.indexOf('@');
					if (scoreIndex > 0) {
						defaultScore = odds.substring(scoreIndex + 1);
						odds = odds.substring(0, scoreIndex);
					}
					oddArr = odds.replace('A', ' ').split("-");
				}
				if (StringUtils.isNotEmpty(defaultScore)) {
					scoreArr = defaultScore.split("B");
				}
			}

			List<PlayMatch> ms = new ArrayList<PlayMatch>(codeArr.length);
			for (int i = 0; i < codeArr.length; i++) {
				String tcode = codeArr[i];
				PlayMatch pm = new PlayMatch();

				String code = tcode.substring(0, 4);
				PlayMatch bm = matches.get(code);
				pm.setBetCode(tcode.substring(4));
				pm.setCnCode(bm.getCnCode());

				pm.setCode(code);
				if (!ArrayUtils.isEmpty(oddArr)) {
					pm.setResultOdd(oddArr[i]);
				}
				if (!ArrayUtils.isEmpty(scoreArr)) {
					pm.setConcedePoints(scoreArr[i]);
					bm.setConcedePoints(scoreArr[i]);
					computationResult(t.getPlayId(),bm);
					pm.setResult(bm.getResult());
				}
				ms.add(pm);
			}
			t.setMatches(ms);
		}
	}
    
	/**
     * 计算正确的投注结果<br/>
     * 篮球让球过关和大小分过关是固定赔率，需要用出票时的分值计算赛果
     * @param play_id
     * @param bm
     * @return 
     */
    private void computationResult(String play_id,PlayMatch bm){
    	String result = bm.getResult();
    	if(StringUtils.isBlank(result)){
    		return;
    	}
    	float defaultScore = Float.parseFloat(bm.getConcedePoints());
    	String[] scores = bm.getScore().split(":");
    	float homeTeamScore = Float.parseFloat(scores[1]);
    	float guestTeamScore = Float.parseFloat(scores[0]);
    	
    	if(play_id.equals(Constants.PLAY_06_LC_2)){
    		String homeWin = "1";
        	String guestWin = "2";
    		homeTeamScore = (homeTeamScore + defaultScore);
    		result = homeTeamScore > guestTeamScore ? homeWin : guestWin;
    	}else if(play_id.equals(Constants.PLAY_09_LC_2)){
    		String big = "1";
        	String small = "2";
    		result = (homeTeamScore + guestTeamScore) > defaultScore ? big : small;
    	}
    	bm.setResult(result);
    }
	
    /**组合需要的信息*/
    private void combinationInfo(){
    	if(scheme.getLotteryId().equals(Constants.CTZC)){
    		issueInfo = issueService.findByIssueAndPlayId(scheme.getPlayId(), scheme.getCtBetContents().get(0).getIssueNumber());
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

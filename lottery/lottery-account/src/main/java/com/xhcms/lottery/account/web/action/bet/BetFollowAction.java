package com.xhcms.lottery.account.web.action.bet;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.weibo.common.service.SchemeService;
import com.xhcms.exception.XHRuntimeException;
import com.xhcms.lottery.account.service.AccountQueryService;
import com.xhcms.lottery.account.web.action.BaseAction;
import com.xhcms.lottery.commons.data.BetMatch;
import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.data.PlayMatch;
import com.xhcms.lottery.commons.data.Ticket;
import com.xhcms.lottery.commons.persist.service.AccountService;
import com.xhcms.lottery.commons.persist.service.BetSchemeService;
import com.xhcms.lottery.commons.persist.service.CGJTeamService;
import com.xhcms.lottery.dc.data.CGJTeam;
import com.xhcms.lottery.lang.AppCode;
import com.xhcms.lottery.lang.EntityType;
import com.xhcms.lottery.lang.PlayType;
import com.xhcms.lottery.utils.CombineBetMatchUtil;

/**
 * <p>Title: 跟单确认页，查看晒单详情</p>
 * @author wang lei
 * @version 1.0.0
 */
public class BetFollowAction extends BaseAction {

    private static final long serialVersionUID = 5896092198258232555L;

    private Long id;
    private BigDecimal sponsorAward;
    private BetScheme scheme;
    private BigDecimal sumBetAmount;
    private BigDecimal sumBonus;
    private List<Ticket> tickets;
    private Map<String, PlayMatch> matches;
    private int offtime=0;//0为未截止 
    private String copyURL;
    private String ticket;	// 单点登录的ticket，需要重定向以便在url中去掉。
    
    public String getTicket() {
		return ticket;
	}


	public void setTicket(String ticket) {
		this.ticket = ticket;
	}


	@Autowired
    private AccountService accountService;
    
    @Autowired
    private AccountQueryService accountQueryService;

    @Autowired
    private SchemeService schemeService;
    @Autowired
    private BetSchemeService betSchemeService;
    
    @Autowired
    private CGJTeamService cgjTeamService;
    
    public String execute() {
        if (id == null) {
            throw new XHRuntimeException(AppCode.SCHEME_NOT_EXIST);
        }
        if (StringUtils.isNotBlank(ticket)){
        	return "betView";
        }
        Long userId = getUserId();
        String Username = getUsername();
        
        scheme = schemeService.viewSchemeCache(id, userId, EntityType.DISPLAY_SHOW);
        
        if(null == scheme || scheme.getId() <= 0) {
        	return SUCCESS;
        }
        
        if(PlayType.JCSJBGJ.getShortPlayStr().equals(scheme.getPlayId())) {
    		List<CGJTeam> cgjTeamList = cgjTeamService.listTeamsBySchemeId(id);
    		scheme.setCgjTeams(cgjTeamList);
        }
        
        scheme.setPassTypeIds(scheme.getPassTypeIds().replace(',', ' ').replaceAll("@", "串"));
        matches = new HashMap<String, PlayMatch>();
        List<BetMatch> mlist = scheme.getMatchs();
		if (mlist != null && mlist.size() > 0) {
			for (BetMatch m : mlist) {
				matches.put(m.getCode(), (PlayMatch) m);
			}
		}
        
        sponsorAward = accountService.getAccount(scheme.getSponsorId()).getTotalAward();
        sponsorAward = sponsorAward==null?new BigDecimal(0.00):sponsorAward;
        
        BigDecimal[] sums = accountQueryService.sumBonus(id, scheme.getSponsorId());
        sumBetAmount = sums[0];
        sumBonus = sums[1];

        //判断是否停售
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String now = df.format(new Date());
        String off = df.format(scheme.getOfftime());
        Date nowDate;
        Date offtimeDate;
		try {
			nowDate = df.parse(now);
			offtimeDate = df.parse(off);
			if(nowDate.getTime()>offtimeDate.getTime()){
				offtime=1;
	        }
		} catch (ParseException e) {
			e.printStackTrace();
		}
		//生成页面URL地址
		copyURL=getRequestURL(request);
		
        //取得方案对应的出票信息
		tickets = betSchemeService.listTicket(id, userId,EntityType.DISPLAY_SHOW);
        
		//解析出票数据中的odds 
        parseResultOdds(tickets,matches);
        
        //只有自己的方案才能看出票拆分
        if(!Username.equals(scheme.getSponsor()) && tickets != null){
        	tickets.clear();
        }
        //将同一赛事不同玩法合并
		scheme = CombineBetMatchUtil.combine(scheme);
        return SUCCESS;
    }

	/**
	 * 获取访问链接
	 * @param request
	 * @return
	 */
	private String getRequestURL(HttpServletRequest request) {  
        if (request == null) {  
            return "";  
        }  
        String url = "";  
        url = request.getContextPath();  
        url = url + request.getServletPath();
        if (!org.apache.commons.lang.StringUtils.isEmpty(request.getQueryString())) {  
            url = url + "?" + request.getQueryString();  
        }
        String serverName="http://"+request.getServerName();
        serverName+=url;
        return serverName;  
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
    public Long getId() {
        return this.id;
    }

	public List<Ticket> getTickets() {
		return tickets;
	}

	public Map<String, PlayMatch> getMatches() {
		return matches;
	}


	public int getOfftime() {
		return offtime;
	}


	public void setOfftime(int offtime) {
		this.offtime = offtime;
	}


	public String getCopyURL() {
		return copyURL;
	}


	public void setCopyURL(String copyURL) {
		this.copyURL = copyURL;
	}

	
    
}

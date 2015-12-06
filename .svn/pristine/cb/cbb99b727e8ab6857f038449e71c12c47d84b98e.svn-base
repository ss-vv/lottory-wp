package com.xhcms.lottery.account.web.action.bet;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
import com.xhcms.lottery.commons.data.DigitalBetContent;
import com.xhcms.lottery.commons.data.IssueInfo;
import com.xhcms.lottery.commons.data.PlayMatch;
import com.xhcms.lottery.commons.data.Ticket;
import com.xhcms.lottery.commons.data.ctfb.CTBetContent;
import com.xhcms.lottery.commons.persist.service.AccountService;
import com.xhcms.lottery.commons.persist.service.BetSchemeService;
import com.xhcms.lottery.commons.persist.service.CGJTeamService;
import com.xhcms.lottery.commons.persist.service.IssueService;
import com.xhcms.lottery.conf.SystemConf;
import com.xhcms.lottery.dc.data.CGJTeam;
import com.xhcms.lottery.lang.AppCode;
import com.xhcms.lottery.lang.EntityStatus;
import com.xhcms.lottery.lang.EntityType;
import com.xhcms.lottery.lang.PlayType;
import com.xhcms.lottery.utils.CombineBetMatchUtil;

/**
 * <p>Title: 合买确认页，查看合买详情</p>
 * @author wang lei
 * @version 1.0.0
 */
public class BetGroupbuyAction extends BaseAction {

    private static final long serialVersionUID = 5896092198258232555L;

    private Long id;
    private BigDecimal sponsorAward;
    private BetScheme scheme;
    private BigDecimal sumBetAmount;
    private BigDecimal sumBonus;
    private List<Ticket> tickets;
    private Map<String, PlayMatch> matches;
    private int offtime=0;//0为未截止 
    private Long deadline=0L;//距离合买截止时间 单位：秒
    private int aheadTime; // 距离合买截止时间提前的秒钟 单位：秒钟
    private String copyURL;
    private String ticket;	// 单点登录的ticket，需要重定向以便在url中去掉。
    private IssueInfo issueInfo;
    
    @Autowired
    private CGJTeamService cgjTeamService;

    @Autowired
    private IssueService issueService;
	@Autowired
    private AccountService accountService;
    
    @Autowired
    private AccountQueryService accountQueryService;

    @Autowired
    private SchemeService schemeService;
    @Autowired
    private BetSchemeService  betSchemeService;
    @Autowired
    private SystemConf systemConf;
    
    public String execute() {
        if (id == null) {
            throw new XHRuntimeException(AppCode.SCHEME_NOT_EXIST);
        }
        if (StringUtils.isNotBlank(ticket)){
        	return "betView";
        }
        Long userId = getUserId();
        scheme = schemeService.viewSchemeCache(id, userId, EntityType.DISPLAY_GROUPBUY);
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
		List<CTBetContent> ctBetContents = scheme.getCtBetContents();
        if(ctBetContents != null && !ctBetContents.isEmpty()){
        	issueInfo = issueService.findByIssueAndPlayId(scheme.getPlayId(), scheme.getCtBetContents().get(0).getIssueNumber());
        }
        
        List<DigitalBetContent> hfBetContent = scheme.getDigitalBetContents();
        if(null != hfBetContent && !hfBetContent.isEmpty()) {
        	issueInfo = issueService.findByIssue(scheme.getLotteryId(), scheme.getDigitalBetContents().get(0).getIssueNumber());
        }
		
        sponsorAward = accountService.getAccount(scheme.getSponsorId()).getTotalAward();
        sponsorAward = sponsorAward==null?new BigDecimal(0.00):sponsorAward;
        
        BigDecimal[] sums = accountQueryService.sumBonus(id, scheme.getSponsorId());
        sumBetAmount = sums[0];
        sumBonus = sums[1];
        
        tickets = betSchemeService.listTicket(id, userId,EntityType.DISPLAY_GROUPBUY);
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
		//判断方案状态，如果不等于未出票，可出票,已请求出票,出票成功则不能合买
		if(scheme.getStatus()!=EntityStatus.TICKET_INIT  &&
			scheme.getStatus()!=EntityStatus.TICKET_ALLOW_BUY &&
			scheme.getStatus()!=EntityStatus.TICKET_REQUIRED  &&
			scheme.getStatus()!=EntityStatus.TICKET_BUY_SUCCESS){
			offtime=1;
		}
		if(scheme.getSaleStatus()!=EntityStatus.SCHEME_ON_SALE){//判断方案状态，如果不等于可参与，则不能合买
			offtime=1;
		}
		//生成页面URL地址
		copyURL=getRequestURL(request);
		
		//解析出票数据中的odds 
        parseResultOdds(tickets,matches);
        
        //计算合买截止时间
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(scheme.getOfftime());
        aheadTime = systemConf.getIntegerValueByKey(SystemConf.BETTIME.GROUPBUY_DEFAULT_AHEAD_SECOND);
        calendar.add(Calendar.SECOND, - aheadTime);
        scheme.setOfftime(calendar.getTime());
        //计算合买剩余时间，秒
        if(offtime==0){
            Calendar calendarNow=Calendar.getInstance();
            calendarNow.setTime(new Date());
            deadline=(calendar.getTime().getTime()-calendarNow.getTime().getTime())/1000;
            if(deadline<=0L){
            	offtime=1;
            }
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


	public Long getDeadline() {
		return deadline;
	}


	public void setDeadline(Long deadline) {
		this.deadline = deadline;
	}

    public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public IssueInfo getIssueInfo() {
		return issueInfo;
	}

	public void setIssueInfo(IssueInfo issueInfo) {
		this.issueInfo = issueInfo;
	}
	
    
}

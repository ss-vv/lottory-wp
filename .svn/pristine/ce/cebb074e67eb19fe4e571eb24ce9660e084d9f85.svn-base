package com.xhcms.lottery.account.web.action.bet;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.unison.lottery.weibo.common.service.TagService;
import com.xhcms.commons.lang.Data;
import com.xhcms.lottery.account.service.BetSchemeService;
import com.xhcms.lottery.account.service.RealSchemeCacheUpdate;
import com.xhcms.lottery.account.web.action.BaseAction;
import com.xhcms.lottery.commons.data.Account;
import com.xhcms.lottery.commons.data.Bet;
import com.xhcms.lottery.commons.data.BetMatch;
import com.xhcms.lottery.commons.data.BetResult;
import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.data.Ticket;
import com.xhcms.lottery.commons.data.repeat.RepeatBetContent;
import com.xhcms.lottery.commons.data.repeat.RepeatPlan;
import com.xhcms.lottery.commons.data.repeat.RepeatPlanIssues;
import com.xhcms.lottery.commons.data.repeat.RepeatRequest;
import com.xhcms.lottery.commons.data.repeat.RepeatRequestParse;
import com.xhcms.lottery.commons.data.repeat.SSQRepeatRequestParse;
import com.xhcms.lottery.commons.persist.entity.BetSchemePO;
import com.xhcms.lottery.commons.persist.service.AccountService;
import com.xhcms.lottery.commons.persist.service.BetException;
import com.xhcms.lottery.commons.persist.service.BetSchemeBaseService;
import com.xhcms.lottery.commons.persist.service.DigitalBetService;
import com.xhcms.lottery.commons.persist.service.IssueService;
import com.xhcms.lottery.commons.persist.service.MessageService;
import com.xhcms.lottery.commons.util.BJDCPlayIdConvert;
import com.xhcms.lottery.commons.util.BetResolver;
import com.xhcms.lottery.commons.utils.internal.IssueNumberStrategy;
import com.xhcms.lottery.commons.utils.internal.impl.SSQIssueNumberStrategyImpl;
import com.xhcms.lottery.conf.SystemConf;
import com.xhcms.lottery.lang.AppCode;
import com.xhcms.lottery.lang.Constants;
import com.xhcms.lottery.lang.EntityType;
import com.xhcms.lottery.lang.LotteryId;
import com.xhcms.lottery.lang.RepeatPlanStatus;
import com.xhcms.lottery.lang.RepeatPlanStopReasonType;
import com.xhcms.lottery.mc.persist.service.TicketService;
import com.xhcms.lottery.service.RepeatService;

/**
 * <p>Title: 投注</p>
 * <p>Description: </p>
 * <p>Copyright：Copyright (c) 2011</p>
 * <p>Company：XingHui Spirit (Beijing) Technical Co.,Ltd.</p>
 * 
 * @author jiajiancheng, Bob Yang
 * @version 1.0.0
 */
public class BetConfirmInWeiboAction extends BaseAction {

    private static final long serialVersionUID = 5896092198258232555L;
    private Logger logger = LoggerFactory.getLogger(getClass());
    
    private int privacy;
    
    private int followSchemePrivacy;

	private Integer type;

    private int buyAmount;
    
    private int multiple;

    private int floorAmount;

    private Account account;

    private Long schemeId;
    
    private BetScheme scheme;
    
    private int showScheme;
    
    private int followedRatio;
    
    private int groupbuyRatio;

    private int aheadTime; // 默认距离合买截止时间提前的秒钟 单位：秒钟
    
    private int repeatType;//追号类型(不追号、追号、追号套餐)
    
    private int profitStandardStop;//追号盈利达标停止标准
    
    private int profitStandardStopMeal;//追号套餐盈利停止标准
    
    private String stopType;	//追号停止类型
    
    private String stopTypeMeal;//追号套餐停止类型
    
    private int repeatPrivacy;//追号保密设置
    
    private int mealPrivacy;//追号套餐保密设置
    
    private int mealType;//追号套餐类型
    
    private String betNoteList;//追号期信息对应的投注倍数
    
    private RepeatRequestParse repeatRequestParse;
    
    private String followRealWeiboContent;
    private String repost;
    
    @Autowired
    private RepeatService repeatService;
    
    // 前一步的action名，可以是 bet, bet_ssq 用来重定向到不同的投注action，如: bet.do、bet_ssq.do等。
    private String preAction;
    
    @Autowired
    private AccountService accountService;

	@Autowired
    private BetSchemeService betSchemeService;
	
	@Autowired
	private DigitalBetService digitalBetService;

    @Autowired
    private BetResolver betResolver;
    @Autowired
    private SystemConf systemConf;
    
    @Autowired
    private IssueService issueService;
    
    private BetResult betResult;
    
    @Autowired
	@Qualifier("betSchemeService")
	private BetSchemeBaseService betSchemeBaseService;
    
    @Autowired
    private TicketService ticketService;
    
    @Autowired
    private RealSchemeCacheUpdate realSchemeCacheUpdate;
    @Autowired
    MessageService messageService;
    @Autowired
    private TagService tagService;
    
    public String execute() {
    	betResult = new BetResult();
    	betResult.setData(Data.failure(null));
    	
    	scheme = (BetScheme) request.getSession(true).getAttribute(Constants.BET_IN_SESSION);
    	LotteryId schemeLotteryId = null;
    	if (scheme != null) {
    		schemeLotteryId = LotteryId.valueOf(scheme.getLotteryId());
    	}
    	//如果type为空，表示用户是没有登录进行了赛事的投注，登录成功之后再次进入投注确认页。
    	if(!this.cheackParameter(type, schemeId, multiple, buyAmount)){
    		if (schemeLotteryId == LotteryId.SSQ){
    			betResult.getData().setData("请登录");
    			return "betViewSSQ";
    		}else{
    			betResult.getData().setData("请登录");
    			return "betView";
    		}
    	}
    	// 注意，不能放在重定向前清空session中的投注方案对象，否则 bet_ssq.do 不能正确执行。
		request.getSession().removeAttribute(Constants.BET_IN_SESSION);
        if(schemeId!=null){
        	//跟单/合买时
        	if(type==EntityType.BET_TYPE_FOLLOW || type==EntityType.BET_TYPE_JION_PARTNER){
        		scheme=betSchemeService.getSchemeById(schemeId);
        		schemeLotteryId = LotteryId.valueOf(scheme.getLotteryId());
        		scheme.setIsPublishShow(EntityType.DONT_PUBLISH_SHOW);
        	}
        	if(type==EntityType.BET_TYPE_FOLLOW && scheme!=null){
        		scheme.setMultiple(multiple);
        	}
        }
        if (scheme == null) {
            addActionError(getText("bet.session.Null"));
            return ERROR;
        }
        // 截止时间
        Date offtime=this.checkAllDeadline(scheme);
        if (new Date().after(offtime)) {
            addActionError(getText("bet.after.Offtime"));
            betResult.getData().setData("方案包含截至赛事，方案已过期");
            return INPUT;
        }
        if(type==EntityType.BET_TYPE_ALONE || type ==EntityType.BET_TYPE_PARTNER){
        	if (schemeLotteryId != LotteryId.SSQ){
        		scheme.setPassTypeIds("," + scheme.getPassTypeIds().replace(' ', ',').replaceAll("串", "@") + ",");
        	}
        	//跟单方案保密
        	scheme.setFollowSchemePrivacy(followSchemePrivacy);
        
	        scheme.setSponsorId(getUser().getId());
	        scheme.setAfterTaxBonus(Constants.ZERO);
	        scheme.setExpectBonus(Constants.ZERO);
	        scheme.setPreTaxBonus(Constants.ZERO);
	        //是否晒单
	        scheme.setShowScheme(showScheme);
	        //跟单提成比例
	        scheme.setFollowedRatio(followedRatio);
	        //当type等于跟单时，此id为被跟单方案id。
	        //默认值-1，表示此单没有跟别单。当type等于跟单时，此id为被跟单方案id。
	        scheme.setFollowedSchemeId(new Long(-1));
	        //此方案的用户是自动推荐用户，则此方案为推荐方案，否则不是推荐方案
	        if(betSchemeService.isRecommendUser(scheme)){
	        	scheme.setRecommendation(1);
	        }else{
	        	scheme.setRecommendation(0);
	        }
        }
        if(Constants.BJDC.equals(scheme.getLotteryId())) {
        	List<Ticket> originSchemeTicket = ticketService.listTicketsOfScheme(scheme.getId());
        	Ticket ticket = originSchemeTicket.get(0);
        	String issue = ticket.getIssue();
        	
        	String issueOnSale = issueService.findOnsale(LotteryId.BJDC.name(), 
        			BJDCPlayIdConvert.convertQueryPlayId(scheme.getPlayId()));
        	if(!issue.equals(issueOnSale)) {
        		addActionError(getText("bet.after.Offtime"));
        		return INPUT;
        	} else {
        		scheme.setIssueNumber(issue);
        	}
        }
        
        
        int resultCode = 0;
        if (type == EntityType.BET_TYPE_ALONE) {
        	Bet bet = betResolver.resolve(scheme);
            // 代购
            scheme.setBuyAmount(bet.getNote() * 2);
            scheme.setTotalAmount(bet.getNote() * 2);
            resultCode = aloneBuyScheme(getUserId(), scheme, bet.getTickets());
        } else if (type == EntityType.BET_TYPE_PARTNER) {
        	Bet bet = betResolver.resolve(scheme);
            // 合买
            int total = scheme.getBetNote() * 2;
            if (buyAmount > total) {
                addActionError(getText("bet.buy.lt.total"));
            }
            if (floorAmount > total) {
                addActionError(getText("bet.floor.lt.total"));
            }
            if ((buyAmount + floorAmount) > total) {
                addActionError(getText("bet.buyandfloor.lt.total"));
            }
            if(hasActionErrors()){
            	return INPUT;
            }
            scheme.setType(type);
            scheme.setPrivacy(privacy);
            scheme.setBuyAmount(buyAmount);
            scheme.setFloorAmount(floorAmount);
            scheme.setShareRatio(groupbuyRatio);
            scheme.setPurchasedAmount(buyAmount);
            BetResult br = betSchemeService.promoteBet(getUserId(), scheme, bet.getTickets());
            resultCode = br.getAppCode();
            scheme.setBetRecordId(br.getBetPartner().getId());
            if(resultCode <= 0)
            	betSchemeService.postWeibo(scheme);
        } else if(type == EntityType.BET_TYPE_FOLLOW){
        	//跟单
        	Bet bet = betResolver.resolve(scheme);
        	//设置跟单id
        	scheme.setFollowedSchemeId(scheme.getId());
        	//id设为0，代表插入新数据
        	scheme.setId(0);
        	//最大金额
            scheme.setMaxBonus(new BigDecimal(bet.getMaxBonus()));
            //注数
            scheme.setBetNote(bet.getNote());
            
        	//跟单不能被晒单
            scheme.setShowScheme(EntityType.DONT_SHOW_SCHEME);
            //跟单提成为0
            scheme.setFollowedRatio(0);
            //跟单不能被推荐
            scheme.setRecommendation(0);
            //认购金额
        	scheme.setBuyAmount(bet.getNote() * 2);
        	//总金额
            scheme.setTotalAmount(bet.getNote() * 2);
           	resultCode = betSchemeService.betFollow(getUserId(), 
           			scheme, bet.getTickets());
        } else if(type == EntityType.BET_TYPE_JION_PARTNER){
        	BetResult br = betSchemeService.purchase(getUserId(), schemeId, buyAmount);
        	resultCode = br.getAppCode();
        	scheme.setBetRecordId(br.getBetPartner().getId());
        	betResult.setProgress(br.getProgress());
        	betResult.setSchemeId(schemeId);
        	betResult.setSaleStatus(br.getSaleStatus());
        }
        betResult.setAppCode(resultCode);
        if (resultCode > 0) {
            addActionError(getErrorText(resultCode));
            if(AppCode.SCHEME_BET_FAIL == resultCode) {
            	betResult.getData().setData("方案已满员");
            } else {
            	betResult.getData().setData("投注失败");
            }
            return INPUT;
        } else {
        	betSchemeBaseService.sendBetMessage(scheme, getUserId());
        	boolean isFollowScheme = (EntityType.BETTING_FOLLOW == type);
        	realSchemeCacheUpdate.betSuccessUpdateSchemeCache(scheme, isFollowScheme);
        }
        schemeId = scheme.getId();
        account = accountService.getAccount(getUserId());
        
        if(LotteryId.SSQ.name().equals(scheme.getLotteryId())) {
        	parseRepeatActivity();
        }
        betResult.setData(Data.success("恭喜您方案订单提交成功！请注意查看出票状态"));
        
        //跟单且在微博跟单中选中 “同时转发”，且微博内容
        if(type == EntityType.BET_TYPE_FOLLOW && repost != null && 
        		null != followRealWeiboContent){
        	betSchemeBaseService.publishShowScheme(scheme, getUserId(), followRealWeiboContent);
        } else if(type == EntityType.BET_TYPE_JION_PARTNER && repost != null && 
        		null != followRealWeiboContent) {
        	betSchemeBaseService.publishGroupBuyWeibo(schemeId, scheme.getBetRecordId(), 
        			getUserId(), buyAmount, followRealWeiboContent, scheme.getSponsorId());
        }
        
        if(type == EntityType.BET_TYPE_JION_PARTNER) {
        	BetSchemePO po = betSchemeBaseService.getSchemePOById(schemeId);
        	tagService.updateGroupProgressByGroupScheme(schemeId, po.getTotalAmount(), 
        			po.getPurchasedAmount());
        }
        return SUCCESS;
    }
    
    //解析用户追号行为
    private void parseRepeatActivity() {
    	repeatRequestParse = new SSQRepeatRequestParse();
    	IssueNumberStrategy issueNumberStrategy = new SSQIssueNumberStrategyImpl();
    	repeatRequestParse.setIssueService(issueService);
    	repeatRequestParse.setIssueNumberStrategy(issueNumberStrategy);
    	
    	RepeatRequest repeatRequest = new RepeatRequest();
    	repeatRequest.setLotteryId(scheme.getLotteryId());
		repeatRequest.setPlayId(scheme.getPlayId());
		repeatRequest.setSponsorId(scheme.getSponsorId());
		repeatRequest.setSuite(mealType);
    	repeatRequest.setRepeatType(repeatType);
    	repeatRequest.setBetNoteList(betNoteList);
    	repeatRequest.setPrivacy(repeatPrivacy);
    	repeatRequest.setMealPrivacy(mealPrivacy);
    	repeatRequest.setStopTypeMeal(stopTypeMeal);
    	repeatRequest.setStopType(stopType);
    	repeatRequest.setBonusForStop(profitStandardStop);
    	repeatRequest.setBonusForStopMeal(profitStandardStopMeal);
    	repeatRequest.setStopedReason(RepeatPlanStopReasonType.NO_STOP.getType());
		repeatRequest.setStatus(RepeatPlanStatus.EXECUTE.getType());
		repeatRequest.setIssueNumber(scheme.getIssueNumber());
		
		
    	RepeatPlan repeatPlan = repeatRequestParse.parseToRepeatPlan(scheme, repeatRequest);
    	List<RepeatPlanIssues> repeatIssuePlanList = repeatRequestParse.parseToRepeatIssuePlan(repeatRequest);
    	List<RepeatBetContent> repeatBetContentList = repeatRequestParse.parseToOriginalBetContent(scheme);
    	
    	if(null != repeatPlan && null != repeatIssuePlanList &&
    			repeatIssuePlanList.size() > 0 &&
    			null != repeatBetContentList &&
    			repeatBetContentList.size() > 0) {    		
    		repeatService.repeatCode(repeatPlan, repeatIssuePlanList, repeatBetContentList);
    	}
    }
    
    /**
     * 代购投注，准备出票。<br/>
     * 一个 proxy 方法，封装对不同彩种的投注。调用竞彩 BetSchemeService 或者 DigitalBetService 实现。
     * 
     * @param userId 要投注的用户id
     * @param scheme 投注方案对象
     * @param tickets 方案对应的票
     * @return 0：投注成功，可出票；其他值认购失败
     */
    private int aloneBuyScheme(long userId, BetScheme scheme, List<Ticket> tickets) {
    	LotteryId schemeLotteryId = LotteryId.valueOf(scheme.getLotteryId());
    	switch(schemeLotteryId){
    		case SSQ:
    			try{
    				digitalBetService.betConfirm(scheme);
    				return 0;
    			}catch(BetException be){
    				logger.error("投注失败!", be);
    				addActionError(be.getMessage());
    				return -1;
    			}
    		default:
    			return betSchemeService.bet(userId, scheme, tickets);
    	}
	}

	/**
     * 检查所有下注的停售时间
     * @param scheme
     * @return
     */
    private Date checkAllDeadline(BetScheme scheme){
    	Date offtime;
    	LotteryId schemeLotteryId = LotteryId.valueOf(scheme.getLotteryId());
    	if(type==EntityType.BET_TYPE_FOLLOW){
        	offtime = scheme.getOfftime();
        }else if(type==EntityType.BET_TYPE_PARTNER || type==EntityType.BET_TYPE_JION_PARTNER){
	        Calendar calendar=Calendar.getInstance();
	        calendar.setTime(scheme.getOfftime());
	        aheadTime = systemConf.getIntegerValueByKey(SystemConf.BETTIME.GROUPBUY_DEFAULT_AHEAD_SECOND);
	        calendar.add(Calendar.SECOND, - aheadTime);
        	offtime = calendar.getTime();
        }else if(schemeLotteryId==LotteryId.CTZC || schemeLotteryId==LotteryId.SSQ){
        	// 对于双色球来说，方案截止时间是“大V彩停止投注时间”。
    		offtime = scheme.getOfftime();
    	}else{
        	offtime = minEntrustDeadline(scheme.getMatchs());
        }
        return offtime;
    }
    
    /**
     * @param matches 比赛列表
     * @return 最先到期的比赛时间
     */
    private Date minEntrustDeadline(List<BetMatch> matches) {
        Date t = null;
        for (BetMatch match : matches) {
            Date deadline = match.getEntrustDeadline();
            if (t == null || t.after(deadline)) {
                t = deadline;
            }
        }
        return t;
    }

    /**
     * 检查参数是否正确
     * @param type
     * @param schemeId
     * @param multiple
     * @param buyAmount
     * @return
     */
    private boolean cheackParameter(Integer type,Long schemeId,int multiple,int buyAmount){
    	if(type==null)
    		return false;
    	switch(type){
        case EntityType.BET_TYPE_ALONE:
            return true;
        case EntityType.BET_TYPE_PARTNER:
        	return true;
        case EntityType.BET_TYPE_FOLLOW:
            return  schemeId!=null && multiple>0;
        case EntityType.BET_TYPE_JION_PARTNER:
        	return  schemeId!=null && buyAmount>0;
        default:
            return false;
    	}
    }
    
    public Account getAccount() {
        return account;
    }

    public Long getSchemeId() {
        return schemeId;
    }

    public void setSchemeId(Long schemeId) {
        this.schemeId = schemeId;
    }
    
    public void setPrivacy(int privacy) {
        this.privacy = privacy;
    }

    public void setType(Integer type) {
        this.type = type;
    }
    
    public Integer getType() {
        return type;
    }

    public void setBuyAmount(int buyAmount) {
        this.buyAmount = buyAmount;
    }

    public void setFloorAmount(int floorAmount) {
        this.floorAmount = floorAmount;
    }

    public BetScheme getScheme() {
        return scheme;
    }

	public int getShowScheme() {
		return showScheme;
	}

	public void setShowScheme(int showScheme) {
		this.showScheme = showScheme;
	}

	public int getFollowSchemePrivacy() {
		return followSchemePrivacy;
	}

	public void setFollowSchemePrivacy(int followSchemePrivacy) {
		this.followSchemePrivacy = followSchemePrivacy;
	}
	
    public int getFollowedRatio() {
		return followedRatio;
	}

	public void setFollowedRatio(int followedRatio) {
		this.followedRatio = followedRatio;
	}

	public int getMultiple() {
		return multiple;
	}

	public void setMultiple(int multiple) {
		this.multiple = multiple;
	}

	public int getGroupbuyRatio() {
		return groupbuyRatio;
	}

	public void setGroupbuyRatio(int groupbuyRatio) {
		this.groupbuyRatio = groupbuyRatio;
	}

	public int getAheadTime() {
		return aheadTime;
	}

	public void setAheadTime(int aheadTime) {
		this.aheadTime = aheadTime;
	}

	public String getPreAction() {
		return preAction;
	}

	public void setPreAction(String preAction) {
		this.preAction = preAction;
	}

	public void setRepeat_type(int repeat_type) {
		this.repeatType = repeat_type;
	}

	public void setProfitStandardStop(int profitStandardStop) {
		this.profitStandardStop = profitStandardStop;
	}

	public void setStop_type(String stop_type) {
		this.stopType = stop_type;
	}

	public void setRepeatPrivacy(int repeatPrivacy) {
		this.repeatPrivacy = repeatPrivacy;
	}

	public void setBetNoteList(String betNoteList) {
		this.betNoteList = betNoteList;
	}

	public void setMealType(int mealType) {
		this.mealType = mealType;
	}
	
	public void setMealPrivacy(int mealPrivacy) {
		this.mealPrivacy = mealPrivacy;
	}
	
	public void setStop_type_meal(String stop_type_meal) {
		this.stopTypeMeal = stop_type_meal;
	}
	
	public void setProfitStandardStopMeal(int profitStandardStopMeal) {
		this.profitStandardStopMeal = profitStandardStopMeal;
	}

	public BetResult getData() {
		return betResult;
	}

	public void setFollowRealWeiboContent(String followRealWeiboContent) {
		this.followRealWeiboContent = followRealWeiboContent;
	}

	public void setRepost(String repost) {
		this.repost = repost;
	}
}

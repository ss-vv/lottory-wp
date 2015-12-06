package com.xhcms.lottery.account.web.action.bet;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.weibo.common.service.SchemeService;
import com.unison.lottery.weibo.lang.Constant;
import com.xhcms.exception.XHRuntimeException;
import com.xhcms.lottery.account.service.BetSchemeService;
import com.xhcms.lottery.account.web.action.BaseAction;
import com.xhcms.lottery.commons.data.Account;
import com.xhcms.lottery.commons.data.Bet;
import com.xhcms.lottery.commons.data.BetMatch;
import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.data.CTBetRequest;
import com.xhcms.lottery.commons.data.CTFBMatch;
import com.xhcms.lottery.commons.data.ChooseType;
import com.xhcms.lottery.commons.data.IssueInfo;
import com.xhcms.lottery.commons.data.Play;
import com.xhcms.lottery.commons.data.ctfb.CTBetContent;
import com.xhcms.lottery.commons.persist.service.BetOptionService;
import com.xhcms.lottery.commons.persist.service.CTFBMatchBaseService;
import com.xhcms.lottery.commons.persist.service.CheckMatchService;
import com.xhcms.lottery.commons.persist.service.IssueService;
import com.xhcms.lottery.commons.persist.service.MatchService;
import com.xhcms.lottery.commons.persist.service.PlayService;
import com.xhcms.lottery.commons.util.BJDCPlayIdConvert;
import com.xhcms.lottery.commons.util.BetMatchsResolver;
import com.xhcms.lottery.commons.util.BetResolver;
import com.xhcms.lottery.lang.Constants;
import com.xhcms.lottery.lang.LotteryId;
import com.xhcms.lottery.lang.PlayType;
import com.xhcms.lottery.utils.CombineBetMatchUtil;

/**
 * <p>Title: 投注确认页，查看用户投注详情</p>
 * <p>Description: </p>
 * <p>Copyright：Copyright (c) 2011</p>
 * <p>Company：XingHui Spirit (Beijing) Technical Co.,Ltd.</p>
 * 
 * @author jiajiancheng
 * @version 1.0.0
 */
public class BetAction extends BaseAction {

    private static final long serialVersionUID = 5896092198258232555L;
    private Logger log = LoggerFactory.getLogger(getClass());
    private String playId;

    private String passTypes;

    /**
     * 赛事ID-赛事编号和选项.
     * -true表示为胆，-sf表示混合过关方式为胜负
     * 26937-300131-true-spf,26938-300231,26939-300331,26940-300431
     */
    private String matchs;
    private int showScheme;
    private int multiple;
    private int buyAmount;
    private int type;
    
    //晒单
    private int followedRatio;
    private int followSchemePrivacy;
    
    //合买
    private int privacy;
    private int groupbuyRatio;
    private int floorAmount;
    
    private String issueNumber;
    private Account account;
    private BetScheme schemeView;
    private int chooseType;
    private IssueInfo issueInfo = null;
    
    @Autowired
    private BetSchemeService betSchemeService;
    @Autowired
    private BetResolver betResolver;
    @Autowired
    private PlayService playService;
    @Autowired
    private IssueService issueService;
    @Autowired
    private SchemeService schemeService;
    @Autowired
    private CTFBMatchBaseService cTFBMatchBaseService;
    @Autowired
    private BetMatchsResolver betMatchsResolver;
    
    private String matchAnnotation;
    
    
    
    @Autowired
	private MatchService matchService;

    public String executeConfirm() {
    	logBet();
    	String result = INPUT;
    	try {
			result = execute();
			if (SUCCESS.equals(result)) {//只有在确认成功之后才进行数据转换
				schemeView = CombineBetMatchUtil.combine(schemeView);
				//如果是新版投注返回新版确认页面（篮球和足球）
				if (Constants.JCZQ.equals(schemeView.getLotteryId())
						|| Constants.JCLQ.equals(schemeView.getLotteryId())) {
					result = "newBetConfirm";
				}
			}
		} catch (Exception e) {
			result = ERROR;
			log.error("new bet error:", e);
		}
		return result;
    }
    
    private boolean stop = true;
    @Autowired
	private CheckMatchService checkMatchService;
    @Autowired
	private BetOptionService betOptionService;
    
	public String execute() {
		if(stop){
			return ERROR;
		}
		if(new StopBet().isStop()) {
    		return ERROR;
    	}
    	
    	BetScheme betSession = (BetScheme) request.getSession().getAttribute(Constants.BET_IN_SESSION);
    	
    	//如果不为空，说明用户是在未登录状态下提交的投注，登录之后重新返回投注确认页。
    	if(isGet() && betSession != null){
    		schemeView = betSession;
            // 确认赛事信息
    		if(!schemeView.getLotteryId().equals(Constants.CTZC)){
    			betSchemeService.confirmScheme(schemeView);
			}
    		return SUCCESS;
    	}
        if (playId == null) {
            addActionError(getText("bet.playId.Null"));
            return INPUT;
        }
        if (passTypes == null) {
            addActionError(getText("bet.passTypes.Null"));
            return INPUT;
        }
        if (matchs == null) {
            addActionError(getText("bet.matchs.Null"));
            return INPUT;
        }
        if (multiple <= 0) {
            addActionError(getText("bet.multiple.Null"));
            return INPUT;
        }
        if(!checkMatchAnnotation(matchAnnotation)) {
        	addActionError(getText("bet.annotation.OverMaxLength"));
        	return INPUT;
        }

        BetScheme scheme = new BetScheme();
        Play play = playService.get(playId);
        if (play != null) {
            scheme.setLotteryId(play.getLotteryId());
        }
        scheme.setMatchAnnotation(matchAnnotation);
        
        if(Constants.CTZC.equals(scheme.getLotteryId())){
        	if(StringUtils.isBlank(issueNumber)){
            	addActionError(getText("bet.issueNumber.Null"));
            	return INPUT;
            }else{
            	issueInfo = issueService.findByIssueAndPlayId(playId,issueNumber);
            }
        } else if(Constants.BJDC.equals(scheme.getLotteryId())) {
        	if(StringUtils.isBlank(issueNumber)) {
        		addActionError(getText("bet.issueNumber.Null"));
        		return INPUT;
        	}
        	String issueOnSale = issueService.findOnsale(LotteryId.BJDC.name(), 
        			BJDCPlayIdConvert.convertQueryPlayId(playId));
        	if(!issueNumber.equals(issueOnSale)) {
        		addActionError(getText("bet.after.Offtime"));
        		return INPUT;
        	}
        }
        
        scheme.setIssueNumber(issueNumber);
        scheme.setType(type);
        scheme.setPlayId(playId);
        scheme.setMultiple(multiple);
        scheme.setFollowSchemePrivacy(followSchemePrivacy);
        scheme.setFollowedRatio(followedRatio);
        
        scheme.setPrivacy(followSchemePrivacy);
        scheme.setBuyAmount(buyAmount);
        scheme.setFloorAmount(floorAmount);
        scheme.setShareRatio(followedRatio);
        scheme.setPurchasedAmount(buyAmount);
        
        String result = produceMatch(scheme);
        if(StringUtils.isNotBlank(result)){
        	return result;
        }
        log.debug("解析出方案投注赛事信息：{}", scheme.getMatchs());
        
        // 过关方式
        List<String> pts = new ArrayList<String>();
        String[] passTypeArr = passTypes.split(",");
        for (int i = 0; i < passTypeArr.length; i++) {
            pts.add(passTypeArr[i]);
        }
        scheme.setPassTypes(pts);
        scheme.setPassTypeIds("," + passTypes + ",");
        scheme.setPassTypeIds(passTypes.replace(',', ' ').replaceAll("@", "串"));

        Bet bet = betResolver.resolve(scheme);

        scheme.setCreatedTime(new Date());
        betSchemeService.computeMinAndMaxBonus(scheme,bet);
       
       
        scheme.setTotalAmount(bet.getNote() * 2);
        scheme.setBetNote(bet.getNote());

        request.getSession(true).setAttribute(Constants.BET_IN_SESSION, scheme);

        schemeView = new BetScheme();
        
        BeanUtils.copyProperties(scheme, schemeView);
        if(!scheme.getLotteryId().equals(Constants.CTZC)){
        	betSchemeService.confirmScheme(schemeView);
        }
        return SUCCESS;
    }

    private String produceMatch(BetScheme scheme){
    	Date offtime = new Date();
    	if(scheme.getLotteryId().equals(Constants.CTZC)){
    		// 截止时间
            offtime = issueInfo.getStopTimeForUser();
            if (new Date().after(offtime)) {
                addActionError(getText("bet.after.Offtime"));
                return INPUT;
            }
            scheme.setOfftime(offtime);
    		CTBetRequest ctBetRequest = new CTBetRequest();
    		String[] matchArr = matchs.split("\\|");
    		Pattern p = Pattern.compile(";");
    		List<CTBetContent> matchList = new ArrayList<CTBetContent>(matchArr.length);
    		for (int i = 0; i < matchArr.length; i++) {
                String match = matchArr[i];
                String[] m = p.split(match);
                if(StringUtils.isNotBlank(match)){
                    CTBetContent ctc = new CTBetContent();
                    ctc.setIssueNumber(issueNumber);
                    ctc.setLotteryId(scheme.getLotteryId());
                    ctc.setPlayId(scheme.getPlayId());
                    ctc.setCode(m[1]);
                    ctc.setChooseType(chooseType);
                    if(m.length>2){
                    	ctc.setSeed(m[2]);
                    }
                    matchList.add(ctc);
                    
                    ctBetRequest.setBetContents(Arrays.asList(matchArr));
                    ctBetRequest.setIssue(issueNumber);
                    ctBetRequest.setLotteryId(scheme.getLotteryId());
                    ctBetRequest.setMultiple(multiple);
                    ctBetRequest.setChooseType(ChooseType.valueOfIndex(chooseType));
                    ctBetRequest.setUserId(getUser().getId());
                    ctBetRequest.setPlayType(PlayType.valueOfLcPlayId(scheme.getPlayId()));
                }
                scheme.setCtBetContents(matchList);
                scheme.setCtBetRequest(ctBetRequest);
            }
            // 准备赛事信息
            if(chooseType == ChooseType.MANUAL.getIndex()) {
            	List<CTFBMatch> ctfbMatchs = cTFBMatchBaseService.findByIssueNoAndPlayId(scheme.getCtBetRequest().getIssue(), scheme.getPlayId());
            	scheme.setCtFBMatchs(ctfbMatchs);
            }
    	}else{
            List<BetMatch> betMatchs = new ArrayList<BetMatch>();
            if(StringUtils.isNotBlank(playId)) {
            	boolean oldVersionPlayId = playId.indexOf("ZC_1") > 0 || 
            			playId.indexOf("ZC_2") > 0 ||
            			playId.indexOf("LC_1") > 0 || 
            			playId.indexOf("LC_2") > 0;
            	if(oldVersionPlayId) {	//注意是一场赛事单玩法投注（包括单关和过关）
            		betMatchs = betMatchsResolver.splitOneMatchOnePlay(matchs, playId);
            	} if(betMatchsResolver.isOneMatchSingleHHBet(playId, matchs)) {
            		betMatchs = betMatchsResolver.splitOneMatchMutiPlayHH(playId, matchs);
            		scheme.setPlayId(playId);
            	} else if(PlayType.isOneMatchMutiPlayMixBet(playId)) {//包含一场赛事多玩法投注
                	betMatchs = betMatchsResolver.splitOneMatchMutiPlayHH(playId, matchs);
                	scheme.setPlayId(playId);
            	}
            }
            betOptionService.combinBetOptions(playId, betMatchs,true);
            
            int code = checkMatchService.checkMatch(playId, betMatchs);
            if (code != 0) {
                throw new XHRuntimeException(getErrorText(code));
            }
            scheme.setMatchNumber(betMatchs.size());
            scheme.setMatchs(betMatchs);
			Map<Long, BigDecimal> matchScoreMap=matchService.findDefaultScoreByMatchIdAndLotteryId(scheme.getMatchs(), scheme.getLotteryId());
			scheme.setMatchScoreMap(matchScoreMap);
            // 截止时间
            offtime = minEntrustDeadline(betMatchs);
            if (new Date().after(offtime)) {
                addActionError(getText("bet.after.Offtime"));
                return INPUT;
            }
    	}
    	scheme.setOfftime(offtime);
        return null;
    }
    

    
    private boolean checkMatchAnnotation(String annotation) {
    	boolean result = true;
    	if(StringUtils.isNotBlank(matchAnnotation)) {
    		String[] anno = matchAnnotation.split(",");
    		if(anno.length > 0) {
    			for(String cont : anno) {
    				String[] match = cont.split("-");
    				if(match.length == 2 && match[1].length() > 
    						Constant.WeiboContentLength.ANNOTATION) {
    					result = false;
    					break;
    				}
    			}
    		}
    	}
    	return result;
    }

    private void logBet() {
    	log.debug("========接收投注参数===========");
    	log.debug("type={}", type);
    	log.debug("matchs={}", matchs);
    	log.debug("playId={}", playId);
    	log.debug("multiple={}", multiple);
    	log.debug("buyAmount={}", buyAmount);
    	
    	//晒单
    	log.debug("followedRatio={}", followedRatio);
    	log.debug("followSchemePrivacy={}", followSchemePrivacy);
        
        //合买
    	log.debug("privacy={}", privacy);
    	log.debug("groupbuyRatio={}", groupbuyRatio);
    	log.debug("privacy={}", privacy);
    	log.debug("floorAmount={}", floorAmount);
    	log.debug("==============================");
	}
    
    /**
     * 停售时间
     * @param matches
     * @return 
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

    public void setPlayId(String playId) {
        this.playId = playId;
    }

    public void setPassTypes(String passTypes) {
        this.passTypes = passTypes;
    }

    public void setMatchs(String matchs) {
    	if(StringUtils.isNotBlank(matchs)) {
    		matchs = matchs.trim();
    	}
        this.matchs = matchs;
    }

    public void setMultiple(int multiple) {
        this.multiple = multiple;
    }

    public Account getAccount() {
        return account;
    }

    public BetScheme getSchemeView() {
		return schemeView;
	}

	public String getPlayId() {
        return playId;
    }

    public String getPassTypes() {
        return passTypes;
    }

    public String getMatchs() {
        return matchs;
    }

    public int getMultiple() {
        return multiple;
    }

	public String getIssueNumber() {
		return issueNumber;
	}

	public void setIssueNumber(String issueNumber) {
		this.issueNumber = issueNumber;
	}

	public int getChooseType() {
		return chooseType;
	}

	public void setChooseType(int chooseType) {
		this.chooseType = chooseType;
	}

	public String getMatchAnnotation() {
		return matchAnnotation;
	}

	public void setMatchAnnotation(String matchAnnotation) {
		this.matchAnnotation = matchAnnotation;
	}
	
	public void setAnnotations(String annotations) {
		this.matchAnnotation = annotations;
	}

	public void setBuyAmount(int buyAmount) {
		this.buyAmount = buyAmount;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getShowScheme() {
		return showScheme;
	}

	public void setShowScheme(int showScheme) {
		this.showScheme = showScheme;
	}

	public int getBuyAmount() {
		return buyAmount;
	}

	public int getFollowedRatio() {
		return followedRatio;
	}

	public void setFollowedRatio(int followedRatio) {
		this.followedRatio = followedRatio;
	}

	public int getFollowSchemePrivacy() {
		return followSchemePrivacy;
	}

	public void setFollowSchemePrivacy(int followSchemePrivacy) {
		this.followSchemePrivacy = followSchemePrivacy;
	}

	public int getPrivacy() {
		return privacy;
	}

	public void setPrivacy(int privacy) {
		this.privacy = privacy;
	}

	public int getGroupbuyRatio() {
		return groupbuyRatio;
	}

	public void setGroupbuyRatio(int groupbuyRatio) {
		this.groupbuyRatio = groupbuyRatio;
	}

	public int getFloorAmount() {
		return floorAmount;
	}

	public void setFloorAmount(int floorAmount) {
		this.floorAmount = floorAmount;
	}
}

/**
 * 
 */
package com.xhcms.lottery.commons.persist.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.xhcms.commons.mq.MessageSender;
import com.xhcms.exception.XHRuntimeException;
import com.xhcms.lottery.commons.data.BetMatch;
import com.xhcms.lottery.commons.data.BetPartner;
import com.xhcms.lottery.commons.data.BetResult;
import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.data.DigitalBetContent;
import com.xhcms.lottery.commons.data.Ticket;
import com.xhcms.lottery.commons.data.ctfb.CTBetContent;
import com.xhcms.lottery.commons.data.weibo.BetMessage;
import com.xhcms.lottery.commons.data.weibo.SchemeHandle;
import com.xhcms.lottery.commons.lang.OddAndActualOdd;
import com.xhcms.lottery.commons.persist.dao.BBMatchDao;
import com.xhcms.lottery.commons.persist.dao.BBMatchPlayDao;
import com.xhcms.lottery.commons.persist.dao.BJDCMatchDao;
import com.xhcms.lottery.commons.persist.dao.BJDCMatchPlayDao;
import com.xhcms.lottery.commons.persist.dao.BetMatchDao;
import com.xhcms.lottery.commons.persist.dao.BetPartnerDao;
import com.xhcms.lottery.commons.persist.dao.BetSchemeDao;
import com.xhcms.lottery.commons.persist.dao.BetSchemePresetDao;
import com.xhcms.lottery.commons.persist.dao.BetSwitchDao;
import com.xhcms.lottery.commons.persist.dao.CGJTeamDao;
import com.xhcms.lottery.commons.persist.dao.CTBetContentDao;
import com.xhcms.lottery.commons.persist.dao.CTFBMatchDao;
import com.xhcms.lottery.commons.persist.dao.CustomMadeAvaiableSchemeDao;
import com.xhcms.lottery.commons.persist.dao.FBMatchDao;
import com.xhcms.lottery.commons.persist.dao.FBMatchPlayDao;
import com.xhcms.lottery.commons.persist.dao.HfBetContentDao;
import com.xhcms.lottery.commons.persist.dao.IssueInfoDao;
import com.xhcms.lottery.commons.persist.dao.OrderDao;
import com.xhcms.lottery.commons.persist.dao.PlayDao;
import com.xhcms.lottery.commons.persist.dao.PrintableTicketDao;
import com.xhcms.lottery.commons.persist.dao.RecommendUserDao;
import com.xhcms.lottery.commons.persist.dao.TicketDao;
import com.xhcms.lottery.commons.persist.dao.UserDao;
import com.xhcms.lottery.commons.persist.dao.UserScoreDao;
import com.xhcms.lottery.commons.persist.entity.BBMatchPO;
import com.xhcms.lottery.commons.persist.entity.BBMatchPlayPO;
import com.xhcms.lottery.commons.persist.entity.BJDCMatchPlayPO;
import com.xhcms.lottery.commons.persist.entity.BetMatchPO;
import com.xhcms.lottery.commons.persist.entity.BetPartnerPO;
import com.xhcms.lottery.commons.persist.entity.BetSchemePO;
import com.xhcms.lottery.commons.persist.entity.CGJTeamPO;
import com.xhcms.lottery.commons.persist.entity.CustomMadeAvaiableSchemePO;
import com.xhcms.lottery.commons.persist.entity.FBMatchPO;
import com.xhcms.lottery.commons.persist.entity.FBMatchPlayPO;
import com.xhcms.lottery.commons.persist.entity.HfBetContentPO;
import com.xhcms.lottery.commons.persist.entity.IssueInfoPO;
import com.xhcms.lottery.commons.persist.entity.PlayPO;
import com.xhcms.lottery.commons.persist.entity.PrintableTicketPO;
import com.xhcms.lottery.commons.persist.entity.TicketPO;
import com.xhcms.lottery.commons.persist.entity.UserPO;
import com.xhcms.lottery.commons.persist.service.AccountService;
import com.xhcms.lottery.commons.persist.service.BetSchemeBaseService;
import com.xhcms.lottery.commons.persist.service.MatchPlatformService;
import com.xhcms.lottery.commons.persist.service.MatchService;
import com.xhcms.lottery.commons.persist.service.PhantomService;
import com.xhcms.lottery.commons.util.BetResolver;
import com.xhcms.lottery.lang.AppCode;
import com.xhcms.lottery.lang.Constants;
import com.xhcms.lottery.lang.EntityStatus;
import com.xhcms.lottery.lang.EntityType;
import com.xhcms.lottery.lang.LotteryId;
import com.xhcms.lottery.lang.PlayType;
import com.xhcms.lottery.lang.SchemeDisplayMode;
import com.xhcms.lottery.utils.POUtils;
import com.xhcms.lottery.utils.ResultTool;
import com.xhcms.lottery.utils.Ticket2ShiTiDianCodeTool;

/**
 * @author Bean.Long
 *
 */
public class BetSchemeBaseServiceImpl implements BetSchemeBaseService {

	@Autowired
	protected MatchService matchService;
    @Autowired
    protected BetSchemeDao betSchemeDao;

    @Autowired
    protected OrderDao orderDao;
    
    @Autowired
    protected BetPartnerDao betPartnerDao;

    @Autowired
    protected BetMatchDao betMatchDao;

    @Autowired
    protected FBMatchDao fbMatchDao;
    
    @Autowired
    protected FBMatchPlayDao fbMatchPlayDao;
    
    @Autowired
    protected BBMatchDao bbMatchDao;
    
    @Autowired
    protected BBMatchPlayDao bbMatchPlayDao;

    @Autowired
    protected TicketDao ticketDao;

    @Autowired
    protected PlayDao playDao;
    
    @Autowired
    protected UserDao userDao;
    
    @Autowired
    protected RecommendUserDao recommendUserDao;
    
    @Autowired
    protected AccountService accountService;

    @Autowired
    protected UserScoreDao userScoreDao;
    
    @Autowired
    protected CustomMadeAvaiableSchemeDao customMadeAvaiableSchemeDao;
    @Autowired
    protected PhantomService pservice;
    @Autowired
    protected BetResolver betResolver;
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected IssueInfoDao issueInfoDao;
    @Autowired
    protected CTFBMatchDao cTFBMatchDao;
    @Autowired
    protected CTBetContentDao cTBetContentDao;
    @Autowired
    protected BetSchemePresetDao betSchemePresetDao;
    
	@Autowired
	protected HfBetContentDao hfBetContentDao;
	@Autowired
    private MessageSender messageSender;
	
	@Autowired
	private CGJTeamDao cgjTeamDao; 
	@Autowired
	protected BJDCMatchPlayDao bjdcMatchPlayDao;
	@Autowired
	protected BJDCMatchDao bjdcMatchDao;
	
	@Autowired
	private MatchPlatformService matchPlatformService;

	@Autowired
	private BetSwitchDao betSwitchDao;
	
	@Autowired
	private PrintableTicketDao printTicketDao;
	
	
	@Transactional
	@Override
	public boolean updateBetSchemePublishShow(long schemeId) {
		BetSchemePO po = getSchemePOById(schemeId);
		if(null != po) {
			po.setIsPublishShow(EntityType.PUBLISH_SHOW);
			if(po.getType() == EntityStatus.BETSCHEME_TYPE_BUY) {
				po.setShowScheme(EntityType.SHOW_SCHEME);
			}
			return true;
		} else {
			return false;
		}
	}
	
	@Transactional
	@Override
	public boolean isCanSendShowScheme(long schemeId) {
		BetSchemePO po = getSchemePOById(schemeId);
		if(null != po && po.getIsPublishShow() == EntityType.DONT_PUBLISH_SHOW &&
				po.getShowScheme() == EntityType.DONT_SHOW_SCHEME) {
			return true;
		}
		return false;
	}
	
	@Transactional
	@Override
	public boolean publishShowScheme(BetSchemePO po, Long loginUserId) {
		if(null == po || po.getId() <= 0) return false;
		
		SchemeHandle schemeHandle = new SchemeHandle();
		schemeHandle.setSchemeId(po.getId());
		schemeHandle.setLoginUserId(loginUserId);
		schemeHandle.setBetRecordShowScheme(true);
		schemeHandle.setBuyAmount(po.getBuyAmount());
		
		//如果方案已经被晒过
		if(po.getIsPublishShow() == EntityType.PUBLISH_SHOW || 
				po.getShowScheme() == EntityType.SHOW_SCHEME) {
			schemeHandle.setAlreadyShow(true);
		}
		
		logger.info("\n用户ID={}，申请晒单方案ID={}, 请求消息={}", 
				new Object[]{loginUserId, po.getId(), schemeHandle});
		messageSender.send(schemeHandle);
		
		return true;
	}
	@Transactional
	@Override
	public void publishShowScheme(BetScheme betScheme, Long loginUserId,String followWeiboContent) {
		if(null == betScheme || betScheme.getId() <= 0) return;
		if(isCanSendShowScheme(betScheme.getId())) {
			SchemeHandle schemeHandle = new SchemeHandle();
			schemeHandle.setSchemeId(betScheme.getId());
			schemeHandle.setBetRecordId(betScheme.getBetRecordId());
			schemeHandle.setLoginUserId(loginUserId);
			schemeHandle.setBetRecordShowScheme(true);
			schemeHandle.setWeiboContent(followWeiboContent);
			schemeHandle.setBuyAmount(betScheme.getBuyAmount());
			messageSender.send(schemeHandle);
		} else {
			logger.info("用户ID={}，申请晒单ID={}，条件失败！是否为晒单方案={}, 是否已晒过单={}",
					new Object[]{loginUserId, betScheme.getId(), 
					betScheme.getShowScheme(), betScheme.getIsPublishShow()});
		}
	}
	
	@Override
	public void publishGroupBuyWeibo(long schemeId, long betRecordId,
			Long loginUserId, int buyAmount, String weiboContent, long sponsorId) {
		SchemeHandle schemeHandle = new SchemeHandle();
		schemeHandle.setSchemeId(schemeId);
		schemeHandle.setBetRecordId(betRecordId);
		schemeHandle.setLoginUserId(loginUserId);
		schemeHandle.setBetRecordShowScheme(true);
		schemeHandle.setWeiboContent(weiboContent);
		schemeHandle.setBuyAmount(buyAmount);
		schemeHandle.setSponsorId(sponsorId);
		messageSender.send(schemeHandle);
	}
	
	@Override
	@Transactional
	public BetSchemePO getSchemePOById(Long schemeId) {
		BetSchemePO schemePO = betSchemeDao.get(schemeId);
		return schemePO;
	}
	
    @Override
    @Transactional
    public BetScheme getSchemeById(Long schemeId){
    	BetSchemePO schemePO=betSchemeDao.get(schemeId);
    	if(null==schemePO)
    		return null;
    	BetScheme scheme=new BetScheme();
    	BeanUtils.copyProperties(schemePO,scheme);
    	//获取赛事信息
    	List<BetMatchPO> bmsPOs = betMatchDao.findBySchemeId(schemeId);
    	Set<Long> idSet = new HashSet<Long>(bmsPOs.size());
    	for(BetMatchPO bm:bmsPOs){
    		idSet.add(bm.getMatchId());
    	}
    	HashMap<Long, Date> mMap = new HashMap<Long, Date>(idSet.size());
    	HashMap<Long, Date> playingTimeMap = new HashMap<Long, Date>(idSet.size());
    	if(scheme.getLotteryId().equals(Constants.JCZQ)){
    		List<FBMatchPO> fbms = fbMatchDao.find(idSet);
    		for(FBMatchPO mp: fbms){
                mMap.put(mp.getId(), mp.getEntrustDeadline());
                playingTimeMap.put(mp.getId(), mp.getPlayingTime());
            }
    	}else if(scheme.getLotteryId().equals(Constants.JCLQ)){
    		List<BBMatchPO> bbms = bbMatchDao.find(idSet);
    		for(BBMatchPO mp: bbms){
                mMap.put(mp.getId(), mp.getEntrustDeadline());
                playingTimeMap.put(mp.getId(), mp.getPlayingTime());
            }
    	}
    	List<BetMatch> bms = POUtils.copyBeans(bmsPOs, BetMatch.class); 
    	for(BetMatch bm:bms){
    		bm.setEntrustDeadline(mMap.get(bm.getMatchId()));
    		bm.setPlayingTime(playingTimeMap.get(bm.getMatchId()));
    		
    	}
    	
    	scheme.setMatchs(bms);
    	// 过关方式
        List<String> pts = new ArrayList<String>();
        String[] passTypeArr = schemePO.getPassTypeIds().split(",");
        for (int i = 0; i < passTypeArr.length; i++) {
        	if(!passTypeArr[i].equals(","))
        		pts.add(passTypeArr[i]);
        }
        scheme.setPassTypes(pts);
        
    	return scheme;
    }
    
    
    

    @Transactional
    @Override
    public int checkMatchAndFillScheme(BetScheme betScheme, String playId) {
    	if(PlayType.JCSJBGJ.getShortPlayStr().equals(playId)) {
    		List<BetMatch> matchs = betScheme.getMatchs();
        	Set<Long> teamIdSet = new HashSet<Long>();
        	if(null != matchs && matchs.size() > 0) {
        		String code = matchs.get(0).getCode();
        		if(StringUtils.isNotBlank(code)) {
        			String[] codeSet = code.split(",");
        			for(int i=0; i<codeSet.length; i++) {
        				teamIdSet.add(Long.parseLong(codeSet[i]));
        			}
        		}
        	}
        	List<CGJTeamPO> cgjTeamList = cgjTeamDao.findByTeamIdSet(teamIdSet, playId);
        	if(cgjTeamList.size() != teamIdSet.size()){
                return AppCode.INVALID_BET_CODE;
            }
        	if(cgjTeamList != null) {
        		Date entrustDeadline = minEntrustDeadline(cgjTeamList);
        		matchs.get(0).setEntrustDeadline(entrustDeadline);
        		betScheme.setOfftime(entrustDeadline);
        		StringBuffer odds = new StringBuffer();
        		StringBuffer betCode = new StringBuffer();
        		
        		BigDecimal maxBonus = new BigDecimal(2);
        		BigDecimal maxOdd = cgjTeamList.get(0).getOdds();
        		for(CGJTeamPO cgjTeam : cgjTeamList) {
        			if(cgjTeam.getOdds().doubleValue() > maxOdd.doubleValue()) {
        				maxOdd = cgjTeam.getOdds();
        			}
        			odds.append(cgjTeam.getOdds()).append(",");
        			long teamId = cgjTeam.getTeamId();
        			betCode.append(composeCgjBetContent(teamId, playId)).append(",");
        		}
        		maxBonus = maxOdd.multiply(new BigDecimal(2))
        				.multiply(new BigDecimal(betScheme.getMultiple()));
        		betScheme.setMaxBonus(maxBonus);
        		betScheme.setBetNote(cgjTeamList.size() * betScheme.getMultiple());
        		betScheme.setTotalAmount(betScheme.getBetNote() * 2);
        		
        		if(odds.length() > 2) {
        			String oddStr = odds.substring(0, odds.length()-1);
        			matchs.get(0).setOdds(oddStr);
        		} else {
        			return AppCode.INVALID_BET_CODE;
        		}
        		if(betCode.length() > 2) {
        			String betCodeStr = betCode.substring(0, betCode.length()-1);
        			matchs.get(0).setCode(betCodeStr);
        		} else {
        			return AppCode.INVALID_BET_CODE;
        		}
        	}
        }
    	return 0;
    }
    
    private String composeCgjBetContent(long teamId, String playId) {
    	String teamIdStr = "";
    	if(PlayType.JCSJBGJ.getShortPlayStr().equals(playId)) {
    		String msg = "玩法=%s,对应投注内容=%s,非法.";
    		if(teamId <= 0) {
    			throw new XHRuntimeException(String.format(msg, playId, teamId));
    		} else if(teamId < 10) {
    			teamIdStr = "0" + teamId;
    		} else if(teamId <= 32) {
    			teamIdStr = "" + teamId;
    		} else {
    			throw new XHRuntimeException(String.format(msg, playId, teamId));
    		}
    	}
    	return teamIdStr;
    }
    
    /**
	 * 计算方案的截止投注时间
	 * @param matches
	 * @return
	 */
	private Date minEntrustDeadline(List<CGJTeamPO> matches) {
        Date t = null;
        for (CGJTeamPO match : matches) {
            Date offtime = match.getOfftime();
            if (t == null || t.after(offtime)) {
                t = offtime;
            }
        }
        return t;
    }
    
    /**
     * 查找所有的 MatchPlay, 对混合过关模式要特殊处理。
     * @param playId 玩法id
     * @param matchs 比赛
     * @return 所有的 MatchPlay 对象。
     */
    private List<FBMatchPlayPO> findFBMatchPlay(String playId,
			List<BetMatch> matchs, Set<Long> idSet) {
    	PlayType playType = PlayType.valueOfLcPlayId(playId);
    	if (playType == PlayType.JCZQ_HH || playType == PlayType.JCZQ_FH) {
    		Set<FBMatchPlayPO> fps = new HashSet<FBMatchPlayPO>();
    		for (BetMatch m : matchs) {
    			HashSet<Long> ids = new HashSet<Long>();
    			ids.add(m.getMatchId());
    			fps.addAll(fbMatchPlayDao.find(m.getPlayId(), ids));
    		}
    		ArrayList<FBMatchPlayPO> list = new ArrayList<FBMatchPlayPO>();
    		list.addAll(fps);
    		return list;
    	}else{
    		return fbMatchPlayDao.find(playId, idSet);
    	}
	}
     
    private List<BJDCMatchPlayPO> findBJDCMatchPlay(String playId,Set<Long> idSet){
    	//PlayType playType = PlayType.valueOfLcPlayId(playId);
    	return bjdcMatchPlayDao.find(playId, idSet);
    }
    
    private List<BBMatchPlayPO> findBBMatchPlay(String playId, List<BetMatch> matchs, Set<Long> idSet){
    	PlayType playType = PlayType.valueOfLcPlayId(playId);
    	if (playType == PlayType.JCLQ_HH || playType == PlayType.JCLQ_FH){
    		List<BBMatchPlayPO> fps = new LinkedList<BBMatchPlayPO>();
    		for (BetMatch m : matchs){
    			HashSet<Long> ids = new HashSet<Long>();
    			ids.add(m.getMatchId());
    			fps.addAll(bbMatchPlayDao.find(m.getPlayId(), ids));
    		}
    		return fps;
    	}else{
    		return bbMatchPlayDao.find(playId, idSet);
    	}
    }

    /**
     * 创建方案到数据库中
     * @param userId
     * @param scheme
     * @param tickets
     */
    @Transactional(propagation=Propagation.REQUIRES_NEW)
    private void createBet(Long userId, BetScheme scheme, List<Ticket> tickets){
    	logger.debug("in com.xhcms.lottery.commons.persist.service.impl.BetSchemeBaseServiceImpl.createBet(Long, BetScheme, List<Ticket>)");
    	
	    Date createdTime = new Date();
	    UserPO upo = userDao.get(userId);
	    // 保存投注单
	    BetSchemePO spo = new BetSchemePO();
	    BeanUtils.copyProperties(scheme, spo);
	    spo.setSponsorId(userId);
	    spo.setSponsor(upo.getUsername());
	    spo.setType(EntityType.BETTING_ALONE);
	    spo.setCreatedTime(createdTime);
	    spo.setPrivacy(EntityStatus.PRIVACY_SECRET);
	    spo.setWinNote(0);
	    spo.setTicketNote(0);
	    spo.setCancelNote(0);
	    spo.setTicketCount(tickets.size());
	    spo.setSaleStatus(EntityStatus.SCHEME_STOP);
	    if(null == spo.getMachineOfftime()){
	    	spo.setMachineOfftime(matchService.computeMachineOfftime(scheme.getMatchs(),scheme.getLotteryId()));
	    }
	    logger.debug("pservice.updateSchemeStatus(spo) start");
	    pservice.updateSchemeStatus(spo);
	    logger.debug("pservice.updateSchemeStatus(spo)");
	    spo.setPartnerCount(1);
	    spo.setLotteryId(playDao.get(scheme.getPlayId()).getLotteryId());
	    /**传统足彩暂不支持晒单*/
	    if(spo.getLotteryId().equals(Constants.CTZC)){
	    	spo.setShowScheme(Constants.NOT_SHOW_SCHEME);
	    } else {
	    	calculateSchemePublicTime(spo, scheme.getMatchs());
	    }
	    betSchemeDao.save(spo);
	    logger.debug("betSchemeDao.save(spo)");
	    scheme.setId(spo.getId());
	    
	    updateJcBetMatchPlayIdAndDefaultScore(scheme, spo.getPlayId());

	    boolean canAuto = pservice.canAutoTicketSuccess(scheme.getPlayId(), scheme.getSponsorId(), 
	    		scheme.getType(), scheme.getLotteryId());
	    
	    // 保存彩票信息
	    for(Ticket t: tickets){
	    	TicketPO ticketPO = copyToTicketPO(t, spo.getId(), createdTime);
	    	if(canAuto) {
	    		ticketPO.setStatus(EntityStatus.TICKET_BUY_SUCCESS);
	    		pservice.updateOddsOfSpecialScheme(ticketPO, scheme);
	    		t.setDavcaiOdds(ticketPO.getDavcaiOdds());
	    	} else {
	    		OddAndActualOdd oddsObj = pservice.convert(ticketPO, scheme);
	        	ticketPO.setDavcaiOdds(oddsObj.getOdds());
	        	t.setDavcaiOdds(oddsObj.getOdds());
	    	}
	    	ticketDao.save(ticketPO);
	    	
	    	convertPrintTicket(spo.getLotteryId(), ticketPO.getId(), t);
	    }

	    // 保存方案所选赛事
	    if(scheme.getLotteryId().equals(Constants.CTZC)){
	    	IssueInfoPO issueInfo = issueInfoDao.findByPlayIdAndIssueNumber(scheme.getPlayId(), scheme.getCtBetRequest().getIssue());
	    	saveCTBetContent(spo.getId(),scheme.getCtBetContents(),issueInfo.getId());
	    } else {
	    	saveBetMatch(spo.getId(), scheme.getMatchs(), scheme.getMatchAnnotation());
	    }

	    // 保存投注人信息
	    BetPartnerPO ppo = new BetPartnerPO();
	    ppo.setScheme(spo);
	    ppo.setUserId(userId);
	    ppo.setUsername(upo.getUsername());
	    ppo.setBetAmount(scheme.getTotalAmount());
	    ppo.setCreatedTime(createdTime);
	    betPartnerDao.save(ppo);

	    // 冻结资金
	    BigDecimal frozenAmount = new BigDecimal(scheme.getTotalAmount());
	    accountService.betFrozen(scheme.getSponsorId(), frozenAmount, ppo.getId());

	    scheme.setId(spo.getId());
    }
    
    private void convertPrintTicket(String lotteryId, long ticketId, Ticket t) {
    	if(!LotteryId.JCZQ.name().equals(lotteryId) && 
    		!LotteryId.JCLQ.name().equals(lotteryId) &&
    		!LotteryId.CTZC.name().equals(lotteryId)) {
    		logger.info("ignore ticketId={},lotteryId is null.", ticketId);
    		return;
    	}
    	String printTicket = Ticket2ShiTiDianCodeTool.convert(t);
    	if(StringUtils.isNotBlank(printTicket)) {
    		PrintableTicketPO pto = new PrintableTicketPO();
    		pto.setTicketId(ticketId);
    		pto.setPrintBetContent(printTicket);
    		printTicketDao.save(pto);
    	} else {
			logger.error("ticket ID={} not convert print ticket!", ticketId);
		}
    }
    
    private void updateJcBetMatchPlayIdAndDefaultScore(BetScheme scheme, String playId) {
	    List<BetMatch> betMatchs = scheme.getMatchs();
    	if(null != betMatchs && betMatchs.size() > 0) {
    		for(int i=0; i<betMatchs.size(); i++) {
    			BetMatch betMatch = betMatchs.get(i);
    			if(StringUtils.isBlank(betMatch.getPlayId())) {
    				betMatch.setPlayId(playId);
    			}
    			String lotteryId = PlayType.getLotteryIdByPlayId(playId);
    			if(LotteryId.JCLQ.name().equals(lotteryId)) {
    				Float defaultScore = bbMatchPlayDao.findByMatchIdAndPlayId(""+betMatch.getMatchId(), betMatch.getPlayId());
    				if(null != defaultScore) {
    					betMatchs.get(i).setDefaultScore(defaultScore);
    				}
    			}
    		}
    	}
    }
    
	@Override
    @Transactional
    public int bet(Long userId, BetScheme scheme, List<Ticket> tickets) {
		logger.debug("in com.xhcms.lottery.commons.persist.service.impl.BetSchemeBaseServiceImpl.bet(Long, BetScheme, List<Ticket>)");
		logger.debug("userId:{}",userId);
		logger.debug("scheme:{}",scheme);
		logger.debug("tickets:{}",tickets);
	    if(scheme.getTotalAmount() <= 0 || scheme.getBuyAmount() <= 0 || 
            scheme.getBuyAmount() > scheme.getTotalAmount()){
             return AppCode.INVALID_AMOUNT;
	    }
        createBet(userId, scheme, tickets);
        
    	pservice.onBetScheme(scheme);
        
        //发出一个创建方案的消息
        notifyNewBetScheme(scheme);
        
        // 发送发微博请求
        postWeibo(scheme);
        
        return 0;
    }
	
	@Override
	public void postWeibo(BetScheme scheme){
		logger.debug("准备发送实单微博：{}", scheme.getId());
		if(scheme.getShowScheme() != EntityType.SHOW_SCHEME ){
			return;
		}
		logger.info("异步发送实单微博，schemeId={}, betRecordId={}", 
				scheme.getId(), scheme.getBetRecordId());
		SchemeHandle schemeHandle = new SchemeHandle(scheme.getId());
		schemeHandle.setBetRecordId(scheme.getBetRecordId());
		schemeHandle.setShowSchemeSlogan(scheme.getShowSchemeSlogan());
		messageSender.send(schemeHandle);
	}
    
	/**
	 * 计算方案公开时间(服务器时间>=这个时间方案即公开)
	 * @param spo
	 * @param matches
	 */
	private void calculateSchemePublicTime(BetSchemePO spo, List<BetMatch> matches) {
    	Date publicTime = maxEntrustDeadline(matches);
    	spo.setPublicTime(publicTime);
	}
	
	protected Date maxEntrustDeadline(List<BetMatch> matches) {
        Date t = null;
        for (BetMatch match : matches) {
            Date deadline = match.getEntrustDeadline();
            if (t == null || deadline.after(t)) {
                t = deadline;
            }
        }
        return t;
    }
	
    protected TicketPO copyToTicketPO(Ticket t, Long schemeId, Date createdTime) {
    	TicketPO tpo = new TicketPO();
        tpo.setSchemeId(schemeId);
        tpo.setPlayId(t.getPlayId());
        tpo.setPassTypeId(t.getPassTypeId());
        tpo.setCode(t.getCode());
        tpo.setActualCode(t.getActualCode());
        tpo.setNote(t.getNote());
        tpo.setMultiple(t.getMultiple());
        tpo.setMoney(t.getMoney());
        tpo.setIssue(t.getIssue());
        tpo.setStatus(EntityStatus.TICKET_INIT);
        tpo.setCreatedTime(createdTime);
        tpo.setPlatformBetCode(t.platformBet());
        tpo.setExpectBonus(t.getExpectBonus());
        tpo.setMinMatchPlayingTime(t.getMinMatchPlayingTime());
		return tpo;
	}
    
	@Override
    @Transactional
    public BetResult promoteBet(Long userId, BetScheme scheme, List<Ticket> tickets) {
		BetResult betResult = new BetResult();
        if(scheme.getTotalAmount() <= 0 || scheme.getBuyAmount() <= 0
                || scheme.getBuyAmount() > scheme.getTotalAmount() || scheme.getFloorAmount() < 0 ||
                (scheme.getBuyAmount()+scheme.getFloorAmount())>scheme.getTotalAmount()){
        	betResult.setAppCode(AppCode.INVALID_AMOUNT);
            return betResult;
        }
        
        Date createdTime = new Date();
        betResult.setAppCode(0);
        
        UserPO upo = userDao.get(userId);
        // 保存投注单
        BetSchemePO spo = new BetSchemePO();
        BeanUtils.copyProperties(scheme, spo);
        spo.setSponsorId(userId);
        spo.setSponsor(upo.getUsername());
        spo.setType(EntityType.BETTING_PARTNER);
        spo.setCreatedTime(createdTime);
        spo.setWinNote(0);
        spo.setTicketNote(0);
        spo.setCancelNote(0);
        spo.setTicketCount(tickets.size());
        spo.setSaleStatus(scheme.getBuyAmount() < scheme.getTotalAmount() ? EntityStatus.SCHEME_ON_SALE : EntityStatus.SCHEME_STOP);
        spo.setPartnerCount(1);
        
        // 投注人认购资金和保底资金低于总投注金额，不出票
        PlayPO play = playDao.get(scheme.getPlayId());
        int floorAmount = play.getFloorRatio() * scheme.getTotalAmount() / 100;// 网站保底金额 
        if(scheme.getBuyAmount() + scheme.getFloorAmount() + floorAmount < scheme.getTotalAmount()){
            spo.setStatus(EntityStatus.TICKET_INIT);
        }else{
            betResult.setAppCode(-1);
            spo.setStatus(EntityStatus.TICKET_ALLOW_BUY);
        }
        /**传统足彩暂不支持晒单*/
        if(spo.getLotteryId().equals(Constants.CTZC)){
        	spo.setShowScheme(Constants.NOT_SHOW_SCHEME);
        } else {
        	calculateSchemePublicTime(spo, scheme.getMatchs());
        }
        betSchemeDao.save(spo);
        
        updateJcBetMatchPlayIdAndDefaultScore(scheme, spo.getPlayId());
        
        // 保存彩票信息
        for(Ticket t: tickets){
        	TicketPO ticketPO = copyToTicketPO(t, spo.getId(), createdTime);
        	ticketDao.save(ticketPO);
        	
        	OddAndActualOdd oddsObj = pservice.convert(ticketPO, scheme);
        	if(null != oddsObj) {
        		ticketPO.setDavcaiOdds(oddsObj.getOdds());
        		t.setDavcaiOdds(oddsObj.getOdds());
        	}
        	
        	convertPrintTicket(spo.getLotteryId(), ticketPO.getId(), t);
        }
        // 保存方案所选赛事
        LotteryId schemeLotteryId = LotteryId.valueOf(scheme.getLotteryId());
        switch(schemeLotteryId){
        	case CTZC:
        		IssueInfoPO issueInfo = issueInfoDao.findByPlayIdAndIssueNumber(scheme.getPlayId(), scheme.getCtBetRequest().getIssue());
        		saveCTBetContent(spo.getId(),scheme.getCtBetContents(),issueInfo.getId());
        		break;
        	case SSQ:
        		issueInfo = issueInfoDao.findByLotteryIdAndIssueNumber(scheme.getLotteryId(), scheme.getIssueNumber());
        		if(issueInfo==null){
        			logger.error("不能查到双色球期信息: playid={}, issue_num={}", 
        					scheme.getPlayId(), scheme.getIssueNumber());
        		}
        		scheme.setId(spo.getId());
        		saveDigitalBetContent(scheme, issueInfo);
        		break;
        	default:
        	saveBetMatch(spo.getId(), scheme.getMatchs(), scheme.getMatchAnnotation());
        }
        
        // 保存投注人信息
        BetPartnerPO ppo = new BetPartnerPO();
        ppo.setScheme(spo);
        ppo.setUserId(userId);
        ppo.setUsername(upo.getUsername());
        ppo.setBetAmount(scheme.getBuyAmount());
        ppo.setCreatedTime(createdTime);
        ppo.setWinAmount(new BigDecimal(0));
        betPartnerDao.save(ppo);
        
        // 冻结资金
        accountService.betFrozen(scheme.getSponsorId(), new BigDecimal(scheme.getBuyAmount()), ppo.getId());
        if(scheme.getFloorAmount()>0)
        	accountService.betFloorFrozen(scheme.getSponsorId(), new BigDecimal(scheme.getFloorAmount()), spo.getId());
        scheme.setId(spo.getId());
        
        BetPartner betPartner = new BetPartner();
        BeanUtils.copyProperties(ppo, betPartner);
        betResult.setBetPartner(betPartner);
        
        notifyNewBetScheme(scheme);
        
        return betResult;
    }

    @Override
    @Transactional
    public BetResult purchase(Long userId, Long schemeId, int buyAmount) {
    	BetResult betResult = new BetResult();
        BetSchemePO po = betSchemeDao.get(schemeId);
        if(po == null){
        	betResult.setAppCode(AppCode.SCHEME_NOT_EXIST);
        	return betResult;
        }
        if(buyAmount <= 0){
        	betResult.setAppCode(AppCode.INVALID_AMOUNT);
        	return betResult;
        }
        
        // 方案类型或状态不合法
        if(po.getType() != EntityType.BETTING_PARTNER ||  (po.getStatus()!=EntityStatus.TICKET_INIT && po.getStatus()!=EntityStatus.TICKET_ALLOW_BUY  &&
        		po.getStatus()!=EntityStatus.TICKET_REQUIRED && po.getStatus()!=EntityStatus.TICKET_BUY_SUCCESS )){
            
            betResult.setAppCode(AppCode.SCHEME_BET_FAIL);
        	return betResult;
        }
        //方案已满员
        if(po.getSaleStatus() != EntityStatus.SCHEME_ON_SALE){
        	 betResult.setAppCode(AppCode.BET_GROUPBUY_FULL);
         	return betResult;
        }
        
        // 可跟单金额
        int surplusAmount = po.getTotalAmount() - po.getPurchasedAmount();
        // 投注金额超过可跟单金额
        if (surplusAmount < buyAmount) {
            betResult.setAppCode(AppCode.SCHEME_BET_AMOUNT_EXCEED);
        	return betResult;
        }
        
        UserPO upo = userDao.get(userId);
        BetPartnerPO ppo = new BetPartnerPO();
        ppo.setScheme(po);
        ppo.setUserId(userId);
        ppo.setUsername(upo.getUsername());
        ppo.setBetAmount(buyAmount);
        ppo.setCreatedTime(new Date());
        ppo.setWinAmount(Constants.ZERO);
        betPartnerDao.save(ppo);
        
        // 冻结资金
        accountService.betFrozen(userId, new BigDecimal(buyAmount), ppo.getId());
        
        po.setPartnerCount(po.getPartnerCount()  + 1);
        po.setPurchasedAmount(po.getPurchasedAmount() + buyAmount);
        if (surplusAmount == buyAmount) {
            // TODO:增加网站的保底金额
            po.setSaleStatus(EntityStatus.SCHEME_STOP);
        }
        BetPartner betPartner = new BetPartner();
        BeanUtils.copyProperties(ppo, betPartner);
        betResult.setBetPartner(betPartner);
        int progress = ResultTool.progress(po.getStatus(), po.getSaleStatus(), 
        		po.getPurchasedAmount(), po.getTotalAmount());
        betResult.setProgress(progress);
        betResult.setSchemeId(po.getId());
        betResult.setSaleStatus(po.getSaleStatus());
        
        // 判断是否可出票
        if(po.getStatus() == EntityStatus.TICKET_INIT){
            PlayPO play = playDao.get(po.getPlayId());
            int floorAmount = play.getFloorRatio() * po.getTotalAmount() / 100;// 网站保底金额 
            if(floorAmount + buyAmount + po.getFloorAmount() >= surplusAmount){
                po.setStatus(EntityStatus.TICKET_ALLOW_BUY);
                betResult.setAppCode(-1);
                return betResult;
            }
        }
        betResult.setAppCode(0);
        
        return betResult;
    }


	/**
	 * 跟单投注. 双色球不支持跟单。
	 */
    @Override
    @Transactional
	public int betFollow(Long userId, BetScheme s, List<Ticket> tickets) {
		if(s.getTotalAmount() <= 0 || s.getBuyAmount() <= 0 || s.getBuyAmount() > s.getTotalAmount()){
            return AppCode.INVALID_AMOUNT;
        }
		LotteryId schemeLotteryId = LotteryId.valueOf(s.getLotteryId());
		if (schemeLotteryId == LotteryId.SSQ){
			return AppCode.UNSUPPORTED_LOTTERY_ID;
		}
        Date createdTime = new Date();
        
        UserPO upo = userDao.get(userId);
        
        // 保存投注单
        BetSchemePO spo = new BetSchemePO();
        BeanUtils.copyProperties(s, spo);
        spo.setSponsorId(userId);
        spo.setFollowingCount(0);//清空跟单人数
        spo.setSponsor(upo.getUsername());
        spo.setType(EntityType.BETTING_FOLLOW);//跟单
        spo.setCreatedTime(createdTime);
        spo.setWinNote(0);
        spo.setTicketNote(0);
        spo.setFloorAmount(0);
        spo.setCancelNote(0);
        spo.setTicketCount(tickets.size());
        spo.setSaleStatus(EntityStatus.SCHEME_STOP);
        pservice.updateSchemeStatus(spo);
        spo.setPartnerCount(0);
        spo.setFollowSchemePrivacy(s.getFollowSchemePrivacy()); //跟单方案权限为继承晒单方案权限
        
        spo.setLotteryId(playDao.get(s.getPlayId()).getLotteryId());
        betSchemeDao.save(spo);
        s.setId(spo.getId());
        
        BetScheme bs = new BetScheme();
    	BeanUtils.copyProperties(spo, bs);
    	bs.setMatchs(s.getMatchs());
    	
    	updateJcBetMatchPlayIdAndDefaultScore(s, spo.getPlayId());
    	
    	boolean canAuto = pservice.canAutoTicketSuccess(bs.getPlayId(), bs.getSponsorId(), 
	    		bs.getType(), bs.getLotteryId());
    	
        // 保存彩票信息
        for(Ticket t: tickets){
        	TicketPO ticketPO = copyToTicketPO(t, spo.getId(), createdTime);
        	pservice.updateVirtualSchemeTicketStatusSucc(ticketPO, bs);
        	if(canAuto) {
	    		ticketPO.setStatus(EntityStatus.TICKET_BUY_SUCCESS);
	    		t.setDavcaiOdds(ticketPO.getDavcaiOdds());
	    		pservice.updateOddsOfSpecialScheme(ticketPO, bs);
	    	} else {
	    		OddAndActualOdd oddsObj = pservice.convert(ticketPO, s);
	        	ticketPO.setDavcaiOdds(oddsObj.getOdds());
	        	t.setDavcaiOdds(oddsObj.getOdds());
			}
        	ticketDao.save(ticketPO);
        	
        	convertPrintTicket(spo.getLotteryId(), ticketPO.getId(), t);
        }
        
        // 保存方案所选赛事
        saveBetMatch(spo.getId(), s.getMatchs(), s.getMatchAnnotation());
        
        // 保存投注人信息
        BetPartnerPO ppo = new BetPartnerPO();
        ppo.setScheme(spo);
        ppo.setUserId(userId);
        ppo.setUsername(upo.getUsername());
        ppo.setBetAmount(s.getTotalAmount());
        ppo.setCreatedTime(createdTime);
        betPartnerDao.save(ppo);
        
        // 冻结资金
        accountService.betFrozen(userId, new BigDecimal(s.getTotalAmount()), ppo.getId());
        
        //添加跟单人数
        this.addFollowCount(spo.getFollowedSchemeId());
        //添加跟单金额
        this.addFollowAmount(spo.getFollowedSchemeId(), spo.getTotalAmount());
        
        
        BeanUtils.copyProperties(spo, bs);
        pservice.onBetScheme(bs);
        
        return 0;
	}
    
	/** 
     * 拷贝一个方案到某用户名下去投注，直接拷贝原始方案的票信息，不再调用 BetResolver 拆票。
     * @param userId 被投注的用户
     * @param srcScheme 要拷贝的方案
     * @param initSchemeStatus 新方案的状态
     * @param 拷贝的倍数
     * @return null 如果拷贝失败
     **/
    public BetScheme copySchemeToUser(Long userId, BetScheme srcScheme, int multiple) {
    	BetScheme scheme = new BetScheme();
    	List<BetMatchPO> srcBetMatches = betMatchDao.findBySchemeId(srcScheme.getId());
    	LinkedList<BetMatch> betMatchesCopy = new LinkedList<BetMatch>();
    	for (BetMatchPO sbm : srcBetMatches){
    		BetMatch bm = new BetMatch();
    		BeanUtils.copyProperties(sbm, bm);
    		bm.setSchemeId(0L);
    		betMatchesCopy.add(bm);
    	}
    	BeanUtils.copyProperties(srcScheme, scheme);
    	scheme.setId(0L);
    	scheme.setShowScheme(Constants.NOT_SHOW_SCHEME);
    	scheme.setFollowedSchemeId(0L);
    	scheme.setSponsorId(userId);
    	UserPO user = userDao.get(userId);
    	scheme.setSponsor(user.getUsername());
        scheme.setMatchNumber(betMatchesCopy.size());
        scheme.setMatchs(betMatchesCopy);
        scheme.setMultiple(multiple);
        scheme.setCreatedTime(new Date());
        
    	List<TicketPO> srcTickets = ticketDao.findByScheme(srcScheme.getId(), -1);
    	List<Ticket> ticketCopy = copyTickets(srcTickets, multiple);
    	int totalAmount = 0; // 方案总金额
    	int buyAmount = 0;	 // 购买金额
    	int betNote = 0;	 // 方案注数, 注意，不能看原始方案倍数，而要看每张票自己的倍数。
    	
    	for (Ticket tc : ticketCopy){
    		totalAmount += tc.getMoney();
    		betNote += tc.getNote();
    	}
    	buyAmount = totalAmount;
        
        double maxBonus = (srcScheme.getMaxBonus().doubleValue() / srcScheme.getMultiple() ) * multiple;
        double expectBonus = (srcScheme.getExpectBonus().doubleValue() / srcScheme.getMultiple()) * multiple;
        scheme.setMaxBonus(new BigDecimal(maxBonus));
        scheme.setExpectBonus(new BigDecimal(expectBonus));

        scheme.setTotalAmount(totalAmount);
        scheme.setBuyAmount(buyAmount);
        scheme.setBetNote(betNote);

        scheme.setPreTaxBonus(BigDecimal.ZERO);
        scheme.setAfterTaxBonus(BigDecimal.ZERO);
        int ret = bet(userId, scheme, ticketCopy);
        if (ret != 0){
        	logger.error("copySchemeToUser failed! code: "+ret);
        	return null;
        }
        return scheme;
    }
	
    /**
     * 拷贝一份指定倍数的票。
     * @param srcTickets
     * @param multiple 
     * @return
     */
    protected List<Ticket> copyTickets(List<TicketPO> srcTickets, int multiple) {
		LinkedList<Ticket> copy = new LinkedList<Ticket>();
		for (TicketPO tp : srcTickets){
			Ticket t = new Ticket();
			int mult = tp.getMultiple();
			BeanUtils.copyProperties(tp, t);
			t.setMultiple(multiple);
			t.setMoney(multiple * tp.getMoney()/mult);
			t.setNote(multiple * tp.getNote()/mult);
			copy.add(t);
		}
		return copy;
	}

	// XXX:增加新玩法需要增加对应的解析模式
    private Pattern getCodePattern(String playId){
        //让球胜平负、不让球胜平负
        if( playId.startsWith(Constants.PLAY_01_ZC) || 
        		playId.startsWith(Constants.PLAY_80_ZC) ){
            return Pattern.compile("\\d{4}([310]{1,3})");
        }
        //比分
        if(playId.startsWith(Constants.PLAY_02_ZC)){
            return Pattern.compile("\\d{4}(\\d{2,62})");
        }
        //总进球数
        if(playId.startsWith(Constants.PLAY_03_ZC)){
            return Pattern.compile("\\d{4}([01234567]{1,8})");
        }
        //半场胜平负
        if(playId.startsWith(Constants.PLAY_04_ZC)){
            return Pattern.compile("\\d{4}([310]{2,18})");
        }
        // 篮球
        // 让分胜负、胜负、大小分
        if (playId.startsWith(Constants.PLAY_06_LC) || playId.startsWith(Constants.PLAY_07_LC)|| playId.startsWith(Constants.PLAY_09_LC)) {
            return Pattern.compile("\\d{4}([12]{1,2})");
        }
        // 胜分差
        if (playId.startsWith(Constants.PLAY_08_LC)) {
            return Pattern.compile("\\d{4}([0-6]{2,24})");
        }
        //北京单场
        //胜平负
        if(playId.startsWith(Constants.PLAY_01_BD_SPF)){
        	
        	 return Pattern.compile("\\d{3}([310]{1,3})");
        }
        //进球数
        if(playId.startsWith(Constants.PLAY_02_BD_JQS)){
        	
        	 return Pattern.compile("\\d{3}([012345678]{1,8})");
        }
        //上下单双--
        if(playId.startsWith(Constants.PLAY_03_BD_SXDS)){
        	 //01 02 11 12   "\\d{3}(01?02?11?12?)"  \\d{3}([01|02|11|12]{1,4})
        	return Pattern.compile("\\d{3}([01|02|11|12]{1,8})");
        }
        //比分
        if(playId.startsWith(Constants.PLAY_04_BD_BF)){
        	
        	  return Pattern.compile("\\d{3}(\\d{2,62})");
        }
        //半全场
        if(playId.startsWith(Constants.PLAY_05_BD_BQC)){
        	
        	 return Pattern.compile("\\d{3}([310]{2,18})");
        }
        
        //胜负
        if(playId.startsWith(Constants.PLAY_06_BD_SF)){
        	
        	return Pattern.compile("\\d{3}([30]{1,2})");
        }

        return null;
    }
    

    /**
     * 添加跟单人数
     * @param schemeId
     */
    @Transactional
    private void addFollowCount(Long schemeId){
    	BetSchemePO schemePO=betSchemeDao.get(schemeId);
    	schemePO.setFollowingCount(schemePO.getFollowingCount()+1);
    }
    
    /**
     * 添加跟单金额
     * @param schemeId
     */
    @Transactional
    private void addFollowAmount(Long schemeId, int followAmount){
    	BetSchemePO schemePO=betSchemeDao.get(schemeId);
    	schemePO.setFollowTotalAmount(schemePO.getFollowTotalAmount()+followAmount);
    }
    
    private void saveBetMatch(Long schemeId, List<BetMatch> matchs, String annotation){
    	Map<String, String> map = parseMatchAnnotation(annotation);
        for(BetMatch bm: matchs){
            BetMatchPO po = new BetMatchPO();
            po.setMatchId(bm.getMatchId());
            po.setSchemeId(schemeId);
            po.setCode(bm.getCode());
            po.setOdds(bm.getOdds());
            po.setPlayId(bm.getPlayId());
            po.setDefaultScore(bm.getDefaultScore());
            //胆码
            po.setSeed(bm.isSeed());
            po.setAnnotation(map.get(bm.getMatchId().toString()));
            
            betMatchDao.save(po);
        }
    }
    
    private Map<String, String> parseMatchAnnotation(String matchAnnotation) {
    	Map<String, String> map = new HashMap<String, String>();
    	if(StringUtils.isNotBlank(matchAnnotation)) {
    		String[] anno = matchAnnotation.split(",");
    		if(anno.length > 0) {
    			for(String cont : anno) {
    				String[] match = cont.split("-");
    				if(match.length == 2) {
    					map.put(match[0], match[1]);
    				}
    			}
    		}
    	}
    	return map;
    }
    
    private void saveCTBetContent(Long schemeId, List<CTBetContent> cTBetContents, Long issueId){
    	cTBetContentDao.batchInserts(cTBetContents,schemeId,issueId);
    }
    
	private void notifyNewBetScheme(BetScheme betScheme) {
		//生成可能的定制方案
		CustomMadeAvaiableSchemePO po = customMadeAvaiableSchemeDao.get(betScheme.getId());
		if(po == null) {
			po = new CustomMadeAvaiableSchemePO();
			po.setId(betScheme.getId());
			po.setCreateTime(Calendar.getInstance().getTime());
			po.setStatus(EntityStatus.CUSTOMMADE_STATUS_NO);
			customMadeAvaiableSchemeDao.save(po);
		}
	}
	
	/**
	 * 保存数字彩投注内容
	 * @param scheme
	 * @param issueInfo
	 */
	protected void saveDigitalBetContent(BetScheme scheme, IssueInfoPO issueInfo) {
		List<DigitalBetContent> betContents = scheme.getDigitalBetContents();
		for (DigitalBetContent betContent : betContents){
			HfBetContentPO betPO = new HfBetContentPO();
			BeanUtils.copyProperties(betContent, betPO);
			betPO.setSchemeId(scheme.getId());
			betPO.setIssueId(issueInfo.getId());
			hfBetContentDao.save(betPO);
		}
	}

	@Transactional
	@Override
	public Map<Long, List<Long>> queryPresetSchemeList(String lotteryId) {
		List<Object> schemeIds = findProfessionalSchemeTicketSuccess(lotteryId);
		Map<Long, List<Long>> map = null;
		if(LotteryId.JCZQ.name().equals(lotteryId)) {
			map = findFBPresetMatchIds(schemeIds);
		} else if(LotteryId.JCLQ.name().equals(lotteryId)) {
			map = findBBPresetMatchIds(schemeIds);
		}
		return map;
	}
	
	@Transactional
	@Override
	public List<Object> findProfessionalSchemeTicketSuccess(String lotteryId) {
		List<Object> list = betSchemeDao.findProfessionalSchemeTicketSuccess(lotteryId);
		return list;
	}
	
	@Transactional
	@Override
	public Map<Long, List<Long>> findFBPresetMatchIds(List<Object> schemeIds) {
		Map<Long, List<Long>> map = new HashMap<Long, List<Long>>();
		if(null != schemeIds && schemeIds.size() > 0) {
			for(Object obj : schemeIds) {
				Long schemeId = Long.valueOf(obj.toString());
				List<Long> matchIds = betSchemeDao.findFBPresetMatchIds(schemeId);
				if(null != matchIds && matchIds.size() > 0) {
					map.put(schemeId, matchIds);
				}
			}
		}
		return map;
	}
	
	@Override
	public Map<Long, List<Long>> findBBPresetMatchIds(List<Object> schemeIds) {
		Map<Long, List<Long>> map = new HashMap<Long, List<Long>>();
		if(null != schemeIds && schemeIds.size() > 0) {
			for(Object obj : schemeIds) {
				Long schemeId = Long.valueOf(obj.toString());
				List<Long> matchIds = betSchemeDao.findBBPresetMatchIds(schemeId);
				if(null != matchIds && matchIds.size() > 0) {
					map.put(schemeId, matchIds);
				}
			}
		}
		return map;
	}

	@Override
	public void sendBetMessage(BetScheme betScheme, long loginUserId) {
		if(loginUserId <= 0) {
			logger.error("登录用户无效，无法发送消息:{}", betScheme);
			return;
		}
		BetMessage betMsg = new BetMessage();
		betMsg.setUserId(loginUserId);
		betMsg.setSchemeId(betScheme.getId());
		betMsg.setLotteryId(betScheme.getLotteryId());
		betMsg.setSchemeCreateTime(betScheme.getCreatedTime().getTime());
		betMsg.setBetRecordId(betScheme.getBetRecordId());
		
		int schemeType = betScheme.getType();
		int isShowScheme = betScheme.getShowScheme();
		int isPublishShow = betScheme.getIsPublishShow();
		int displayMode = SchemeDisplayMode.getDisplayMode(schemeType, isShowScheme, isPublishShow);
		betMsg.setDisplayMode(displayMode);
		
		logger.info("发送投注记录行为消息:{}", betMsg);
		messageSender.send(betMsg);
	}

	@Override
	@Transactional(readOnly=true)
	public boolean canBet(String channel, String lotteryId) {
		boolean result=false;
		if(StringUtils.isNotBlank(channel)&&StringUtils.isNotBlank(lotteryId)){
			Long count = betSwitchDao.countCanBet(channel,lotteryId);
			if(null!=count&&count>0){
				result=true;
			}
		}
		return result;
	}
	
}

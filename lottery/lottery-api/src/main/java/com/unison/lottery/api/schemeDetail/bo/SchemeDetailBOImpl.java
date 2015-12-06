package com.unison.lottery.api.schemeDetail.bo;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map; 
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.tools.generic.MathTool;
import org.springframework.beans.factory.annotation.Autowired;

import com.davcai.lottery.service.MatchNameService;
import com.unison.lottery.api.lang.LotteryUtil;
import com.unison.lottery.api.protocol.common.Constants;
import com.unison.lottery.api.protocol.common.SystemStatusKeyNames;
import com.unison.lottery.api.protocol.response.model.SchemeDetailResponse;
import com.unison.lottery.api.protocol.response.model.SchemeTicketResponse;
import com.unison.lottery.api.protocol.status.IStatusRepository;
import com.unison.lottery.api.protocol.status.ReturnStatus;
import com.unison.lottery.api.schemeDetail.data.CTSchemeDetailData;
import com.xhcms.lottery.commons.data.BetMatch;
import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.data.CTFBMatch;
import com.xhcms.lottery.commons.data.DigitalBetContent;
import com.xhcms.lottery.commons.data.IssueInfo;
import com.xhcms.lottery.commons.data.PlayMatch;
import com.xhcms.lottery.commons.data.Ticket;
import com.xhcms.lottery.commons.data.User;
import com.xhcms.lottery.commons.data.ctfb.CTBetContent;
import com.xhcms.lottery.commons.persist.entity.CTFBMatchPO;
import com.xhcms.lottery.commons.persist.service.AccountQueryService;
import com.xhcms.lottery.commons.persist.service.AccountService;
import com.xhcms.lottery.commons.persist.service.BetSchemeService;
import com.xhcms.lottery.commons.persist.service.IssueService;
import com.xhcms.lottery.commons.utils.CTFBMatchResultUtil;
import com.xhcms.lottery.lang.EntityStatus;
import com.xhcms.lottery.lang.LotteryId;
import com.xhcms.lottery.lang.PlayType;
import com.xhcms.lottery.mc.persist.service.TicketService;
import com.xhcms.lottery.utils.NumberUtils;
import com.xhcms.lottery.utils.ResultTool;
import com.xhcms.ucenter.persistent.service.IUserService;

public class SchemeDetailBOImpl implements SchemeDetailBO {
    @Autowired
    private BetSchemeService betSchemeService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private AccountQueryService accountQueryService;
	@Autowired
	private IStatusRepository statusRepository;
	@Autowired
	private TicketService ticketService;
	@Autowired
	private IssueService issueService;
	@Autowired
	private MatchNameService matchNameService;
	@Autowired
	private IUserService userService;
	
	ReturnStatus succStatus;
	
	ReturnStatus failStatus;
	
	ReturnStatus outdateStatus;
	
	ReturnStatus invalidStatus;
	
	@Autowired
	private CTFBMatchResultUtil ctfbMatchResultUtil;
	
	@Override
	public SchemeDetailResponse schemeDetail(Long userId,
			Map<String, String> paramMap) {
		BetScheme scheme = null;		
		succStatus = statusRepository.getSystemStatusBySystemKey(SystemStatusKeyNames.SchemeDetail.SUCC);
		failStatus = statusRepository.getSystemStatusBySystemKey(SystemStatusKeyNames.SchemeDetail.FAIL);
		outdateStatus = statusRepository.getSystemStatusBySystemKey(SystemStatusKeyNames.SchemeDetail.OUT_DATE);
		invalidStatus = statusRepository.getSystemStatusBySystemKey(SystemStatusKeyNames.SchemeDetail.INVALID);
		
		SchemeDetailResponse response = new SchemeDetailResponse();
		Long schemeId = Long.parseLong(paramMap.get(Constants.SCHEME_ID));
		String displayMode = paramMap.get(Constants.DISPLAY_MODE);
		try{
			scheme = betSchemeService.viewScheme(schemeId, Integer.parseInt(displayMode));
		} catch(Exception e){
			e.printStackTrace();
			response.setReturnStatus(invalidStatus);
			return response;
		}
		
		if(null != scheme) {
			String lotteryId = scheme.getLotteryId();
			if(com.xhcms.lottery.lang.Constants.CQSS.equals(lotteryId) 
					|| com.xhcms.lottery.lang.Constants.JX11.equals(lotteryId)) {
				schemeDetailForHF(response,displayMode,scheme,schemeId,userId,lotteryId);
			} else if(com.xhcms.lottery.lang.Constants.CTZC.equals(lotteryId)) {//传统足彩方案详情
				schemeDetailForCTZC(response,displayMode,scheme,schemeId,userId,lotteryId);
			} else if(com.xhcms.lottery.lang.Constants.SSQ.equals(lotteryId)) {
				schemeDetailForSSQ(response,displayMode,scheme,schemeId,userId,lotteryId);
			} else {
				schemeDetailForBBAndFB(response,displayMode,scheme,schemeId,userId,lotteryId);
			}
		}
		return response;
	}
	

	private SchemeDetailResponse schemeDetailForSSQ(SchemeDetailResponse response, String displayMode, BetScheme scheme, Long schemeId, Long userId, String lotteryId) {
		BigDecimal sponsorAward = accountService.getAccount(scheme.getSponsorId()).getTotalAward();
        sponsorAward = sponsorAward==null?new BigDecimal(0.00):sponsorAward;
        BigDecimal[] sums = accountQueryService.sumBonus(schemeId, userId);
        BigDecimal sumBetAmount = sums[0];
        BigDecimal sumBonus = sums[1];
        
        Map<String,Object> resultMap = new HashMap<String, Object>();
        resultMap.put(Constants.SCHEME, scheme);
        resultMap.put(Constants.SPONSOR_AWARD, sponsorAward);
        resultMap.put(Constants.SUM_BET_AMOUNT, sumBetAmount);
        resultMap.put(Constants.SUM_BONUS, sumBonus);
        resultMap.put(Constants.DISPLAY_MODE, displayMode);
        
        if(null != scheme.getDigitalBetContents()) {
        	String issueNumber = scheme.getDigitalBetContents().get(0).getIssueNumber();
        	IssueInfo issue = issueService.findByIssue(lotteryId, issueNumber);
        	if(null != issue) {
        		resultMap.put(Constants.BONUS_CODE, issue.getBonusCode());
        	}
        }
        List<DigitalBetContent> list = scheme.getDigitalBetContents();
        if(null != list && list.size() > 0) {
        	resultMap.put(Constants.DIGITAL_BET_CONTENT, list);
        }
        User sponsor = userService.getUser(scheme.getSponsorId());
        if(sponsor != null){
            resultMap.put("sponsor", sponsor);//方案发起人
        }
        response.setResultMap(resultMap);
        response.setReturnStatus(succStatus);
		return response;
	}

	/**
	 * 构建传统足彩方案详情数据
	 * @param response
	 * @param displayMode 
	 */
	private void schemeDetailForCTZC(SchemeDetailResponse response, String displayMode,BetScheme scheme, Long schemeId, Long userId, String lotteryId) {
		BigDecimal sponsorAward = accountService.getAccount(scheme.getSponsorId()).getTotalAward();
        sponsorAward = (sponsorAward == null ? new BigDecimal(0.00) : sponsorAward);
        BigDecimal[] sums = accountQueryService.sumBonus(schemeId, userId);
        BigDecimal sumBetAmount = sums[0];
        BigDecimal sumBonus = sums[1];
        
        Map<String,Object> resultMap = new HashMap<String, Object>();
        resultMap.put(Constants.SCHEME, scheme);
        resultMap.put(Constants.SPONSOR_AWARD, sponsorAward);
        resultMap.put(Constants.SUM_BET_AMOUNT, sumBetAmount);
        resultMap.put(Constants.SUM_BONUS, sumBonus);
        resultMap.put(Constants.DISPLAY_MODE, displayMode);
        List<CTSchemeDetailData> ctSchemeDetailDataList = new ArrayList<CTSchemeDetailData>();
        List<CTBetContent> ctBetContentList = scheme.getCtBetContents();//投注内容
        if(null != ctBetContentList) {
        	//构建传统足彩方案赛事详情数据
        	List<CTBetContent> betContentList = scheme.getCtBetContents();
        	ctSchemeDetailDataList = getCTSchemeTicket(scheme, betContentList.get(0));
        }
        resultMap.put(Constants.CTFB_SCHEMEDETAIL, ctSchemeDetailDataList);
        User sponsor = userService.getUser(scheme.getSponsorId());
        if(sponsor != null){
            resultMap.put("sponsor", sponsor);//方案发起人
        }
        response.setResultMap(resultMap);
        response.setReturnStatus(succStatus);
	}

	private String getMatchResultByPlayId(String playId, String cont, String suffix) {
		if(StringUtils.isNotBlank(cont)) {
			if(PlayType.CTZC_JQ.getShortPlayStr().equals(playId)) {
				return suffix + "(" + cont + ")";
			} else if(PlayType.CTZC_BQ.getShortPlayStr().equals(playId)) {
				return suffix + "(" + getNameConvertContent(cont) + ")";
			}
		}
		return cont;
	}
	
	private String getBetContentByPlayId(String playId, String cont, String suffix) {
		if(StringUtils.isNotBlank(cont)) {
			if(PlayType.CTZC_JQ.getShortPlayStr().equals(playId)) {
				List<String> list = Arrays.asList(cont.split(""));
				StringBuffer buf = new StringBuffer();
				for(int i = 0; i < list.size(); i++) {
					String ch = list.get(i);
					if(StringUtils.isNotBlank(ch)) {
						if("3".equals(ch)) {
							ch = ch + "+";
						}
						buf.append(ch);
						if(i != list.size() - 1) {
							buf.append(",");
						}
					}
				}
				return suffix + "(" + buf.toString() + ")";
			} else if(PlayType.CTZC_BQ.getShortPlayStr().equals(playId)) {
				return suffix + "(" + getNameConvertContent(cont) + ")";
			}
		}
		return cont;
	}
	
	
	private String getNameConvertContent(String cont) {
		char[] chars = cont.toCharArray();
		int size = chars.length;
		StringBuffer buf = new StringBuffer();
		
		for(int i = 0; i < size; i++) {
			switch (chars[i]) {
			case '0':
				cont = "负";
				break;
			case '1':
				cont = "平";
				break;
			case '3':
				cont = "胜";
				break;
			default:
				break;
			}
			buf.append(cont);
			if(i != size - 1) {
				buf.append(",");
			}
		}
		return buf.toString();
	}
	
	public void schemeDetailForHF(SchemeDetailResponse response, String displayMode, BetScheme scheme, Long schemeId, Long userId, String lotteryId) {
		BigDecimal sponsorAward = accountService.getAccount(scheme.getSponsorId()).getTotalAward();
		
        sponsorAward = sponsorAward==null?new BigDecimal(0.00):sponsorAward;
        BigDecimal[] sums = accountQueryService.sumBonus(schemeId, userId);
        BigDecimal sumBetAmount = sums[0];
        BigDecimal sumBonus = sums[1];
        
        Map<String,Object> resultMap = new HashMap<String, Object>();
        resultMap.put(Constants.SCHEME, scheme);
        resultMap.put(Constants.SPONSOR_AWARD, sponsorAward);
        resultMap.put(Constants.SUM_BET_AMOUNT, sumBetAmount);
        resultMap.put(Constants.SUM_BONUS, sumBonus);
        resultMap.put(Constants.DISPLAY_MODE, displayMode);
        if(null != scheme.getDigitalBetContents()) {
        	String issueNumber = scheme.getDigitalBetContents().get(0).getIssueNumber();
        	PlayType playType = PlayType.valueOfLcPlayId(scheme.getPlayId());
			scheme.setPlayId(playType.name());
        	IssueInfo issue = issueService.findByIssue(lotteryId, issueNumber);
        	if(null != issue) {
        		resultMap.put(Constants.BONUS_CODE, issue.getBonusCode());
        	}
        }
        
        User sponsor = userService.getUser(scheme.getSponsorId());
        if(sponsor != null){
            resultMap.put("sponsor", sponsor);//方案发起人
        }
        response.setResultMap(resultMap);
        response.setReturnStatus(succStatus);
	}
	
	public SchemeDetailResponse schemeDetailForBBAndFB(SchemeDetailResponse response, String displayMode, BetScheme scheme, Long schemeId, Long userId, String lotteryId) {
		scheme.setPassTypeIds(scheme.getPassTypeIds().replace(',', ' ').replaceAll("@", "串"));
        
        BigDecimal sponsorAward = accountService.getAccount(scheme.getSponsorId()).getTotalAward();
        sponsorAward = sponsorAward==null?new BigDecimal(0.00):sponsorAward;
        
        BigDecimal[] sums = accountQueryService.sumBonus(schemeId, userId);
        BigDecimal sumBetAmount = sums[0];
        BigDecimal sumBonus = sums[1];
        
        Map<String,Object> resultMap = new HashMap<String, Object>();
        if(scheme.getType()==com.xhcms.lottery.lang.Constants.TYPE_FOLLOW){
        	if(scheme.getStatus()==EntityStatus.TICKET_NOT_AWARD || scheme.getStatus() ==EntityStatus.TICKET_AWARDED ){
        		BigDecimal totalBonus = scheme.getAfterTaxBonus();
        		//当税后奖金大于本金时才给晒单人佣金
        		if(totalBonus.compareTo(new BigDecimal(scheme.getTotalAmount()))==1){
        			BigDecimal ratio = NumberUtils.percent(scheme.getFollowedRatio());
        			BigDecimal commission = totalBonus.multiply(ratio).setScale(2, RoundingMode.DOWN);
        			resultMap.put(Constants.SPONSOR_COMMISSION, commission);
        		}
        	}
        }
        User sponsor = userService.getUser(scheme.getSponsorId());
        if(sponsor != null){
            resultMap.put("sponsor", sponsor);//方案发起人
        }
        if(new Date().getTime() < scheme.getOfftime().getTime()){
        	if(scheme.getStatus() != EntityStatus.TICKET_BUY_FAIL){
        		scheme.setStatus(EntityStatus.SCHEME_SALE);
        	}
        }
        for (BetMatch betMatch:scheme.getMatchs()) {
        	if(betMatch instanceof PlayMatch){
        		PlayMatch p = (PlayMatch)betMatch;
        		String[] ts = p.getName().split("VS");
        		if(LotteryId.JCLQ.name().equals(PlayType.getLotteryIdByPlayId(p.getPlayId()))){
        			p.setHomeName(matchNameService.getTeamShortNameByLeagueIdAndTeamName(p.getLeagueName(), ts[1]));
            		p.setGuestName(matchNameService.getTeamShortNameByLeagueIdAndTeamName(p.getLeagueName(), ts[0]));
            		p.setName(p.getGuestName() + " VS " + p.getHomeName());
        		}
        	}
		}
        resultMap.put(Constants.SCHEME, scheme);
        resultMap.put(Constants.SPONSOR_AWARD, sponsorAward);
        resultMap.put(Constants.SUM_BET_AMOUNT, sumBetAmount);
        resultMap.put(Constants.SUM_BONUS, sumBonus);
        resultMap.put(Constants.DISPLAY_MODE, displayMode);
        resultMap.put(Constants.USER_ID, userId);
        response.setResultMap(resultMap);
        response.setReturnStatus(succStatus);
		return response;
	}
	

	/**
	 * 出票信息明细查询
	 */
	@Override
	public SchemeTicketResponse schemeTicket(Long userId,
			Map<String, String> paramMap) {
		
		Long schemeId = Long.parseLong(paramMap.get(Constants.SCHEME_ID));
		String displayMode = paramMap.get(Constants.DISPLAY_MODE);
		SchemeTicketResponse response = new SchemeTicketResponse();
		ReturnStatus succStatus = statusRepository
				.getSystemStatusBySystemKey(SystemStatusKeyNames.SchemeTicket.SUCC);
		//ReturnStatus failStatus = statusRepository.getSystemStatusBySystemKey(SystemStatusKeyNames.SchemeTicket.FAIL);
		ReturnStatus invalidStatus=statusRepository.getSystemStatusBySystemKey(SystemStatusKeyNames.SchemeDetail.INVALID);
		BetScheme scheme;
		try{
			 scheme = betSchemeService.viewScheme(schemeId, Integer.parseInt(displayMode));
		}catch (Throwable t) {
			response.setReturnStatus(invalidStatus);
			return response;
		}
		Map<String,Object> resultMap = new HashMap<String, Object>();
		String lotteryId = scheme.getLotteryId();
		
		List<Ticket> tickets = betSchemeService.listTicket(schemeId, userId,
				Integer.parseInt(displayMode));
		resultMap.put(Constants.TICKET, tickets);
		
		resultMap.put(Constants.TOTAL_AMOUNT, scheme.getTotalAmount());

		HashMap<String, BetMatch> matches = new HashMap<String, BetMatch>();
		if(LotteryUtil.isUsedParseMatch(lotteryId)) {
			List<BetMatch> mlist = scheme.getMatchs();
			resultMap.put(Constants.JC_BET_MATCH, mlist);
			if (mlist != null && mlist.size() > 0) {
				for (BetMatch m : mlist) {
					matches.put(m.getCode(), m);
				}
			}
		}
		if(LotteryUtil.isUsedParseOdd(lotteryId)) {
			// 解析出票数据中的odds
			if (isZC_MIX(scheme)) {
				HashMap<String,PlayMatch> playMatchs = new HashMap<String,PlayMatch>();
				List<BetMatch> mlist = scheme.getMatchs();
				if (mlist != null && mlist.size() > 0) {
					for (BetMatch m : mlist) {
						playMatchs.put(m.getCode(), (PlayMatch)m);
					}
				}
				ticketService.updatePlayMatchByPlatformOdds(tickets, playMatchs);
			} else if (isLC_MIX(scheme)) {
				HashMap<String,PlayMatch> playMatchs = new HashMap<String,PlayMatch>();
				List<BetMatch> mlist = scheme.getMatchs();
				if (mlist != null && mlist.size() > 0) {
					for (BetMatch m : mlist) {
						playMatchs.put(m.getCode(), (PlayMatch)m);
					}
				}
				ticketService.updatePlayMatchByPlatformOdds(tickets, playMatchs);
			} else {
				parseResultOdds(tickets, matches);
			}
		}
		resultMap.put(Constants.MATCHES, matches);
		
		if(com.xhcms.lottery.lang.Constants.CQSS.equals(lotteryId)) {
			PlayType playType = PlayType.valueOfLcPlayId(scheme.getPlayId());
			scheme.setPlayId(playType.name());
		} else if(com.xhcms.lottery.lang.Constants.CTZC.equals(lotteryId)) {
			List<CTBetContent> ctBetConntents = scheme.getCtBetContents();
			List<CTFBMatch> betMatchs = scheme.getCtFBMatchs();
			if(null != ctBetConntents && ctBetConntents.size() > 0 
					&& null != betMatchs && betMatchs.size() > 0) {
				CTBetContent ctBetContent = ctBetConntents.get(0);
				List<CTSchemeDetailData> list = getCTSchemeTicket(scheme, ctBetContent);
				
				Map<String, List<CTSchemeDetailData>> splitTicketMatchs = 
						splitTicketGetMatch(tickets, list);
				
				resultMap.put(Constants.MATCH_DATA, list);
				resultMap.put(Constants.SPLIT_MATCH_DATA, splitTicketMatchs);
			}
		}
		resultMap.put(Constants.SCHEME, scheme);
		response.setResultMap(resultMap);
		response.setReturnStatus(succStatus);
		return response;
	}
	
	private boolean isZC_MIX(BetScheme scheme) {
		return  com.xhcms.lottery.lang.Constants.PLAY_05_ZC_2.equals(scheme.getPlayId()) || 
				com.xhcms.lottery.lang.Constants.PLAY_555_ZCFH_2.equals(scheme.getPlayId()) ||
				com.xhcms.lottery.lang.Constants.PLAY_555_ZCFH_1.equals(scheme.getPlayId());
	}
	
	private boolean isLC_MIX(BetScheme scheme) {
		return  com.xhcms.lottery.lang.Constants.PLAY_10_LC_2.equals(scheme.getPlayId()) || 
				com.xhcms.lottery.lang.Constants.PLAY_666_LCFH_2.equals(scheme.getPlayId()) ||
				com.xhcms.lottery.lang.Constants.PLAY_666_LCFH_1.equals(scheme.getPlayId());
	}

	//传统足彩根据票的信息和投注内容拆分对应的赛事数据
	public Map<String, List<CTSchemeDetailData>> splitTicketGetMatch(List<Ticket> tickets, 
			List<CTSchemeDetailData> list) {
		Map<String, List<CTSchemeDetailData>> splitTicketMatchs = 
				new HashMap<String, List<CTSchemeDetailData>>();
		if(null != tickets && tickets.size() > 0
				&& null != list && list.size() > 0) {
			Pattern pattern = Pattern.compile("[0-9]");
			int size = tickets.size();
			for(int i = 0; i < size; i++) {
				Ticket ticket = tickets.get(i);
				String actualCode = ticket.getActualCode();
				if(StringUtils.isNotBlank(actualCode)) {
					String[] actualCodeArr = actualCode.split(",");
					int len = actualCodeArr.length;
					List<CTSchemeDetailData> ctList = new ArrayList<CTSchemeDetailData>();
					for(int j = 0; j < len; j++) {
						String code = actualCodeArr[j];
						Matcher matcher = pattern.matcher(code);
						if(matcher.matches()) {
							ctList.add(list.get(j));
						}
					}
					splitTicketMatchs.put(String.valueOf(ticket.getId()), ctList);
				}
				
			}
		}
		return splitTicketMatchs;
	}
	
	//构建传统足彩出票明细
	private List<CTSchemeDetailData> getCTSchemeTicket(BetScheme scheme, CTBetContent ctBetContent) {
		List<CTSchemeDetailData> ctSchemeDetailDataList = new ArrayList<CTSchemeDetailData>();
		String playId = ctBetContent.getPlayId();
		String code = ctBetContent.getCode();
		IssueInfo issue = issueService.findById(ctBetContent.getIssueId());
		String issueNumber = issue.getIssueNumber();
		String bonusCode = "";
		if(null != issue) {
			bonusCode = issue.getBonusCode();
		}
	
		
		List<CTFBMatch> ctfbMatchList = scheme.getCtFBMatchs();//投注对应的赛事
		if(null != ctfbMatchList && ctfbMatchList.size() > 0) {
			List<CTFBMatchPO> ctfbMatchPOList=makeCTFBMatchPOList(ctfbMatchList);
			List<String> matchResultList = ctfbMatchResultUtil.makeMatchResultList(ctfbMatchPOList,playId);//赛果列表
			bonusCode = makeBonusCodeIfBlank(playId, bonusCode, matchResultList);
			//赛事信息
			for(CTFBMatch ctfbMatch : ctfbMatchList) {
				CTSchemeDetailData ctSchemeDetailData = new CTSchemeDetailData();
				ctSchemeDetailData.setMatchId(ctfbMatch.getMatchId());
				ctSchemeDetailData.setPlayingTime(ctfbMatch.getPlayingTime());
				ctSchemeDetailData.setHomeTeamName(ctfbMatch.getHomeTeamName());
				ctSchemeDetailData.setGuestTeamName(ctfbMatch.getGuestTeamName());
				ctSchemeDetailData.setMatchName(ctfbMatch.getHomeTeamName() + "VS" + ctfbMatch.getGuestTeamName());
				ctSchemeDetailData.setIssueNumber(issueNumber);
				
				MathTool mathTool = new MathTool();
				if(StringUtils.isNotBlank(playId)) {
					if(PlayType.CTZC_14.getShortPlayStr().equals(playId) ||
							PlayType.CTZC_R9.getShortPlayStr().equals(playId)) {
						//解析赛果
						if(StringUtils.isNotBlank(bonusCode)) {
							int nu = (Integer) mathTool.sub(ctfbMatch.getMatchId(),1);
							char ch = bonusCode.charAt(nu);
							String result = ResultTool.cn(scheme.getPlayId(), String.valueOf(ch), ctfbMatch.getOdds());
							ctSchemeDetailData.setMatchResult(result);
						}
						//解析用户投注内容
						String v = code.split(",")[(Integer) mathTool.sub(ctfbMatch.getMatchId(), 1)];//用户投注项
						String betContent = ResultTool.cn(playId, v, ctfbMatch.getOdds());
						ctSchemeDetailData.setBetContent(betContent);
						if(null!=matchResultList&&!matchResultList.isEmpty()){
							String currentMatchResult = matchResultList.get(ctfbMatch.getMatchId().intValue()-1);
							if(StringUtils.equals(v,currentMatchResult)||StringUtils.equals("*", currentMatchResult)){
								ctSchemeDetailData.setPass(true);
							}
						}
						
						
					} else if(PlayType.CTZC_BQ.getShortPlayStr().equals(playId) ||
							PlayType.CTZC_JQ.getShortPlayStr().equals(playId)) {
						String suffix = "";
						String end = "";
						if(PlayType.CTZC_BQ.getShortPlayStr().equals(playId)) {
							suffix = "半";
							end = "全";
						} else if(PlayType.CTZC_JQ.getShortPlayStr().equals(playId)) {
							suffix = "主";
							end = "客";
							
						}
						//解析赛果
						if(StringUtils.isNotBlank(bonusCode)) {
							int indexOfFirst = (Integer) mathTool.sub(mathTool.mul(ctfbMatch.getMatchId(),2),2);
							char bonusCodeAtFirst = bonusCode.charAt(indexOfFirst);
							
							int indexOfSecond = (Integer) mathTool.add(mathTool.sub(mathTool.mul(ctfbMatch.getMatchId(), 2), 2), 1);
							char bonusCodeAtSecond = bonusCode.charAt(indexOfSecond);
							ctSchemeDetailData.setMatchResult(getMatchResultByPlayId(playId, String.valueOf(bonusCodeAtFirst), suffix) + ";" + 
									getMatchResultByPlayId(playId, String.valueOf(bonusCodeAtSecond), end));
						}
						//解析用户投注内容
						int mul = (Integer) mathTool.mul(ctfbMatch.getMatchId(),2);
						int sub = (Integer) mathTool.sub(mul, 2);
						String v = code.split(",")[sub];//上半场投注项
						
						int add = (Integer) mathTool.add(mathTool.sub(mathTool.mul(ctfbMatch.getMatchId(), 2), 2), 1);
						String vAll = code.split(",")[add];//下半场投注项
						ctSchemeDetailData.setBetContent(getBetContentByPlayId(playId, v, suffix) + ";" + 
								getBetContentByPlayId(playId, vAll, end));
						if(null!=matchResultList&&!matchResultList.isEmpty()){
							String currentFirstMatchResult=matchResultList.get(sub);
							boolean firstHalfMatchPass=StringUtils.equals(v,currentFirstMatchResult)||StringUtils.equals("*", currentFirstMatchResult);
							String currentSecondMatchResult=matchResultList.get(add);
							boolean secondHalfMatchPass=StringUtils.equals(vAll,currentSecondMatchResult)||StringUtils.equals("*", currentSecondMatchResult);
							ctSchemeDetailData.setPass(firstHalfMatchPass&&secondHalfMatchPass);
						}
						
					}
				}
				ctSchemeDetailDataList.add(ctSchemeDetailData);
			}
		}
		return ctSchemeDetailDataList;
	}


	private String makeBonusCodeIfBlank(String playId, String bonusCode, List<String> matchResultList){
		if(StringUtils.isBlank(bonusCode)){//期的中奖号码为空，需要自己生成
			
			bonusCode=StringUtils.join(matchResultList, "");
		}
		return bonusCode;
	}

	private List<CTFBMatchPO> makeCTFBMatchPOList(List<CTFBMatch> ctfbMatchList) {
		List<CTFBMatchPO> result=new ArrayList<CTFBMatchPO>();
		for(CTFBMatch ctfbMatch:ctfbMatchList){
			CTFBMatchPO po=new CTFBMatchPO();
			try {
				BeanUtils.copyProperties(po, ctfbMatch);
				result.add(po);
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}


	protected void parseResultOdds(List<Ticket> tickets,Map<String, BetMatch> matches) {
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
				PlayMatch bm = (PlayMatch)matches.get(code);
				pm.setBetCode(tcode.substring(4));
				pm.setCnCode(matches.get(code).getCnCode());

				pm.setCode(code);
				if (!ArrayUtils.isEmpty(oddArr)) {
					pm.setResultOdd(oddArr[i]);
				}
				if (!ArrayUtils.isEmpty(scoreArr)) {
					pm.setConcedePoints(scoreArr[i]);
					bm.setDefaultScore(Float.parseFloat(scoreArr[i]));
					pm.setDefaultScore(Float.parseFloat(scoreArr[i]));
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
    	
    	if(play_id.equals(com.xhcms.lottery.lang.Constants.PLAY_06_LC_2)){
    		String homeWin = "1";
        	String guestWin = "2";
    		homeTeamScore = (homeTeamScore + defaultScore);
    		result = homeTeamScore > guestTeamScore ? homeWin : guestWin;
    	}else if(play_id.equals(com.xhcms.lottery.lang.Constants.PLAY_09_LC_2)){
    		String big = "1";
        	String small = "2";
    		result = (homeTeamScore + guestTeamScore) > defaultScore ? big : small;
    	}
    	bm.setResult(result);
    }
}

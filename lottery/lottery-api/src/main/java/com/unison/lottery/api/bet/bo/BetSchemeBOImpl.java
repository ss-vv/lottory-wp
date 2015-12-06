package com.unison.lottery.api.bet.bo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.api.bet.parse.HFBetContentParse;
import com.unison.lottery.api.bet.parse.MXNParser;
import com.unison.lottery.api.bet.parse.ParsePassTypeResult;
import com.unison.lottery.api.bet.parse.PlayMaxPassCountCheck;
import com.unison.lottery.api.bet.parse.PlayTypeParse;
import com.unison.lottery.api.bet.parse.SupportClientPassTypeMN;
import com.unison.lottery.api.lang.IssueInfoUtil;
import com.unison.lottery.api.login.hx.httpclient.apidemo.HX_sendMassage;
import com.unison.lottery.api.model.SchemePrivacy;
import com.unison.lottery.api.protocol.common.Constants;
import com.unison.lottery.api.protocol.common.SystemStatusKeyNames;
import com.unison.lottery.api.protocol.response.model.BetSchemeResponse;
import com.unison.lottery.api.protocol.status.IStatusRepository;
import com.unison.lottery.api.protocol.status.ReturnStatus;
import com.unison.lottery.api.util.SchemePrivacyUtil;
import com.unison.lottery.api.vGroup.data.GroupSecretkey;
import com.unison.lottery.api.vGroupPublishScheme.data.PublishSchemeClient;
import com.xhcms.exception.XHRuntimeException;
import com.xhcms.lottery.commons.data.Account;
import com.xhcms.lottery.commons.data.Bet;
import com.xhcms.lottery.commons.data.BetMatch;
import com.xhcms.lottery.commons.data.BetResult;
import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.data.CTBetRequest;
import com.xhcms.lottery.commons.data.CTFBMatch;
import com.xhcms.lottery.commons.data.ChooseType;
import com.xhcms.lottery.commons.data.DigitalBetRequest;
import com.xhcms.lottery.commons.data.IssueInfo;
import com.xhcms.lottery.commons.data.PublishBetScheme;
import com.xhcms.lottery.commons.data.User;
import com.xhcms.lottery.commons.data.ctfb.CTBetContent;
import com.xhcms.lottery.commons.exception.JXRuntimeException;
import com.xhcms.lottery.commons.lang.Channel;
import com.xhcms.lottery.commons.lang.Partner;
import com.xhcms.lottery.commons.persist.dao.BetMatchDao;
import com.xhcms.lottery.commons.persist.entity.BetMatchPO;
import com.xhcms.lottery.commons.persist.entity.IssueInfoPO;
import com.xhcms.lottery.commons.persist.service.AccountService;
import com.xhcms.lottery.commons.persist.service.BetException;
import com.xhcms.lottery.commons.persist.service.BetOptionService;
import com.xhcms.lottery.commons.persist.service.BetSchemeBaseService;
import com.xhcms.lottery.commons.persist.service.BetSchemeService;
import com.xhcms.lottery.commons.persist.service.CTFBMatchBaseService;
import com.xhcms.lottery.commons.persist.service.CheckMatchService;
import com.xhcms.lottery.commons.persist.service.DigitalBetService;
import com.xhcms.lottery.commons.persist.service.IssueService;
import com.xhcms.lottery.commons.persist.service.MatchService;
import com.xhcms.lottery.commons.persist.service.PublishBetSchemeService;
import com.xhcms.lottery.commons.util.BetResolver;
import com.xhcms.lottery.conf.SystemConf;
import com.xhcms.lottery.lang.AppCode;
import com.xhcms.lottery.lang.EntityStatus;
import com.xhcms.lottery.lang.EntityType;
import com.xhcms.lottery.lang.PlayType;
import com.xhcms.lottery.lang.SchemeDisplayMode;
import com.xhcms.lottery.utils.DES;
import com.xhcms.ucenter.persistent.service.IUserService;

public class BetSchemeBOImpl implements BetSchemeBO {
	// <methodRequest name="betScheme" validId="用户身份识别码(md5)"
	// clientVersion="客户端版本号" channel="渠道号" deviceId="sim卡序列号" modelName="机型信息"
	// issueNumber="彩票期号（11选5才有）" lotteryId="JCZQ|JCLQ|JX11" playId="玩法id"
	// betContent="投注内容,形式为：matchId-options 201211047029-31,201211047030-10"
	// schemeId="当跟单或合买时才有"
	// multiple="倍数" betNote="注数" passType="竞彩才有该字段，多个过关方式用逗号分隔。3串1例如：3@1"
	// betType="0代沟|1合买|2跟单"
	// isShow="是否晒单" showType="晒胆类型：0完全公开|1跟单公开|2销售截止公开|3开奖公开"
	// followedRatio="提成,晒单才有">
	// </methodRequest>
	//
	// <response name="betScheme" status="2000230|2000231" desc="响应成功|响应失败|期号错误"
	// currentIssueNumber="当前期号" currentSalingIssueCountdownTime="当前销售期倒计时秒数"
	// userBalance="当前投注用户余额">
	// <betSchemes ( 生成的方案id集合 )>
	// <betScheme id="方案id"
	// issueNumber="2011088(期号，投注请求支持多期投注， 可以是当前销售期和未来期次（未来20期），同时也可以跨期投注，合作商可以利用该接口完成追号业务。详见-期号格式)"
	// passType="P1_1(该张彩票的投注方式,详见-betType值的设置)" betMoney="8(该张彩票的金额,最大为20000)"
	// betUnits="1(该彩票的注数 （不计倍投）" statusCode="000(状态码)"
	// message="接收成功(对该彩票的交易结果的说明)"
	// palmid="2208(彩票平台序列号)" detailmessage=" 交易出错时的详细说明" />
	// </betSchemes>
	//
	// </response>


	

	@Autowired
	private AccountService accountService;

	@Autowired
	private BetSchemeService betSchemeService;

	@Autowired
	private BetResolver betResolver;

	@Autowired
	private IStatusRepository statusRepository;

	@Autowired
	private DigitalBetService digitalBetService;





	@Autowired
	private IssueService issueService;
	
	@Autowired
	private CTFBMatchBaseService cTFBMatchBaseService;
	
	@Autowired
	private PublishBetSchemeService publishBetSchemeService;

	@Autowired
	private BetMatchDao betMatchDao;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private BetSchemeBaseService betSchemeBaseService;
	@Autowired
    private SystemConf systemConf;
	
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	ReturnStatus succStatus;
	ReturnStatus failStatus;
	ReturnStatus errorStatus;
	ReturnStatus outDateStatus;
	ReturnStatus balanceNotEnough;
	ReturnStatus accountFrozen;
	ReturnStatus invalidAmount;
	ReturnStatus betIssueClose;
	ReturnStatus betClose;

	@Autowired
	private MatchService matchService;

	@Autowired
	private SchemePrivacyUtil schemePrivacyUtil;

	private ReturnStatus groupBuyFullStatus;

	private ReturnStatus lessThanTotalAmountLimitStatus;
	private ReturnStatus alonePassTypeLimitWithTime;
	private ReturnStatus groupPassTypeLimitWithTime;

	@Autowired
	private CheckMatchService checkMatchService;
	
	@Autowired
	private BetOptionService betOptionService;

	


	@Override
	public BetSchemeResponse bet(Long userId, Map<String, String> paramMap,
			String partner) {
		succStatus = statusRepository
				.getSystemStatusBySystemKey(SystemStatusKeyNames.BetScheme.SUCC);
		failStatus = statusRepository
				.getSystemStatusBySystemKey(SystemStatusKeyNames.BetScheme.FAIL);
		errorStatus = statusRepository
				.getSystemStatusBySystemKey(SystemStatusKeyNames.BetScheme.ERROR);
		outDateStatus = statusRepository
				.getSystemStatusBySystemKey(SystemStatusKeyNames.BetScheme.OUT_DATE);
		balanceNotEnough = statusRepository
				.getSystemStatusBySystemKey(SystemStatusKeyNames.BetScheme.BALANCE_NOT_ENOUGH);
		accountFrozen = statusRepository
				.getSystemStatusBySystemKey(SystemStatusKeyNames.BetScheme.ACCOUNT_FROZEN);
		invalidAmount = statusRepository
				.getSystemStatusBySystemKey(SystemStatusKeyNames.BetScheme.BET_SCHEME_INVALID_AMOUNT);
		betIssueClose = statusRepository
				.getSystemStatusBySystemKey(SystemStatusKeyNames.BetScheme.BET_ISSUE_CLOSE);
		betClose = statusRepository
				.getSystemStatusBySystemKey(SystemStatusKeyNames.BetScheme.BET_CLOSE);
		groupBuyFullStatus=statusRepository
				.getSystemStatusBySystemKey(SystemStatusKeyNames.BetScheme.BET_GROUPBUY_FULL);
		lessThanTotalAmountLimitStatus=statusRepository
				.getSystemStatusBySystemKey(SystemStatusKeyNames.BetScheme.BET_LESS_THAN_TOTAL_AMOUNT_LIMIT);
		alonePassTypeLimitWithTime =statusRepository
				.getSystemStatusBySystemKey(SystemStatusKeyNames.BetScheme.ALONE_PASSTYPE_LIMIT_TIME);
		groupPassTypeLimitWithTime =statusRepository
				.getSystemStatusBySystemKey(SystemStatusKeyNames.BetScheme.GROUP_PASSTYPE_LIMIT_TIME);

		try {
			partner = DES.decryptDES(partner, GroupSecretkey.secretKey, "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		String lotteryId = paramMap.get(Constants.LOTTERY_ID);
		if (StringUtils.isBlank(partner)) {
			partner = Partner.DAVCAI_WWW;
		}
		BetSchemeResponse response = new BetSchemeResponse();
		if(betSchemeBaseService.canBet(Channel.MOBILE_CLIENT,lotteryId)){//检查是否允许该channel该彩种可以投注
			if (com.xhcms.lottery.lang.Constants.JCLQ.equals(lotteryId)
					|| com.xhcms.lottery.lang.Constants.JCZQ.equals(lotteryId)
					|| com.xhcms.lottery.lang.Constants.CTZC.equals(lotteryId)) {
				betJCAndCTZC(response, userId, partner, paramMap, lotteryId);
			} else if (com.xhcms.lottery.lang.Constants.CQSS.equals(lotteryId)
					|| com.xhcms.lottery.lang.Constants.JX11.equals(lotteryId)) {
				betHF(lotteryId, response, partner, userId, paramMap);
			} else if (com.xhcms.lottery.lang.Constants.SSQ.equals(lotteryId)) {
				betSSQ(lotteryId, response, partner, userId, paramMap);
			}
		}
		else{
			response.setReturnStatus(betClose);
		}
		
		return response;
	}

	private BetSchemeResponse betJCAndCTZC(BetSchemeResponse response, Long userId,
			String partner, Map<String, String> paramMap, String lotteryId) {
		BetScheme scheme = null;
		String schemeId = paramMap.get(Constants.SCHEME_ID);
		String playId = paramMap.get(Constants.PLAY_ID);
		String betType = paramMap.get(Constants.BET_TYPE);
		Integer type =makeType(betType);
		if(null==type){
			response.setReturnStatus(failStatus);
			return response;
		}
	    
		
		String multiple = paramMap.get(Constants.MUTIPLE);
		String betContent = paramMap.get(Constants.BET_CONTENT);
		String passType = paramMap.get(Constants.PASS_TYPE);
		String buyAmount = paramMap.get(Constants.BUY_AMOUNT);
		String floorAmount = paramMap.get(Constants.FLOOR_AMOUNT);
		String followedRatio = paramMap.get(Constants.FOLLOWED_RATIO); 
		String issueNumber = paramMap.get(Constants.ISSUE_NUMBER);
		int chooseType = 0;
		if (schemeId != null) {
			scheme = betSchemeService.getSchemeById(Long.parseLong(schemeId));
			String passTypeIds = scheme.getPassTypeIds();
			if(StringUtils.isNotBlank(passTypeIds)) {
				passType = MXNParser.filter(passTypeIds);
			}
			betOptionService.combinBetOptions(playId, scheme.getMatchs(),false);
			Map<Long, BigDecimal> matchScoreMap=matchService.findDefaultScoreByMatchIdAndLotteryId(scheme.getMatchs(), lotteryId);
			scheme.setMatchScoreMap(matchScoreMap);
			
			
		}
		
		//校验客户端传递的串关是否支持
		boolean isSupportPassType = PlayMaxPassCountCheck.check(lotteryId, playId, passType, betContent, betType);
		if(!isSupportPassType) {
			response.setReturnStatus(failStatus);
			return response;
		}
		//尝试转换m@n，并对转换之后的结果检验是否支持
		ParsePassTypeResult convertResult = SupportClientPassTypeMN.translator(lotteryId, 
				playId, betContent, passType,betType, schemeId);
		if(convertResult.isConvertSuccess()) {
			//如果转换为m@n之后不被玩法支持则使用原始串关内容
			isSupportPassType = PlayMaxPassCountCheck.check(lotteryId, playId, convertResult.getConvertPassType(), betContent, betType);
			if(!isSupportPassType) {
				convertResult.setConvertSuccess(false);
			} else {
				passType = convertResult.getConvertPassType();
			}
		}
		
		if (schemeId != null) {//参与跟单或合买
			Account account = accountService.getAccount(userId);
			if(null==account||null==scheme){//检查userId和schemeId的有效性
				response.setReturnStatus(errorStatus);
				return response;
			}
			if(account != null){
				Map<String, Object> resultMap = new HashMap<String, Object>();
				resultMap.put(Constants.ACCOUNT, account);
			}
			// 截止时间
			BetMatch minOfftimeMatch = matchService.computeMinEntrustDeadlineMatch(scheme.getMatchs(), scheme.getLotteryId());
			Date offtime = minOfftimeMatch.getEntrustDeadline();
			if (new Date().after(offtime)) {
				response.setReturnStatus(outDateStatus);
				return response;
			}
			
			if(!convertResult.isConvertSuccess()) {
				boolean isTimeLimited = betPassTypeLimitedByTime(response, userId, betContent,
						passType, betType, minOfftimeMatch);
				if(isTimeLimited) {
					return response;
				}
			}
			
			// 参与跟单
			if (Integer.valueOf(betType) == EntityType.BET_TYPE_FOLLOW) {
				scheme.setMultiple(Integer.parseInt(multiple));
			} else if (Integer.valueOf(betType) == EntityType.BET_TYPE_JION_PARTNER) {//参与合买
				try{
					BetResult result = betSchemeBaseService.purchase(Long.valueOf(userId), Long.valueOf(schemeId), Integer.valueOf(buyAmount));
					if(result.getAppCode() > 0){
						logger.info("投注失败状态appCode:{}",result.getAppCode());
						if(AppCode.BET_GROUPBUY_FULL==result.getAppCode()){
							response.setReturnStatus(groupBuyFullStatus);
						}
						else {
							response.setReturnStatus(failStatus);
						}
						
						return response;
					}
				}catch(Throwable t){
					t.printStackTrace();
					if (t instanceof XHRuntimeException) {
						XHRuntimeException ex = (XHRuntimeException)t;
						switch (ex.getCode()) {
						case AppCode.ACCOUNT_FROZED:
							response.setReturnStatus(accountFrozen);
							break;
						case AppCode.INVALID_AMOUNT:
							response.setReturnStatus(invalidAmount);
							break;
						case AppCode.INSUFFICIENT_BALANCE:
							response.setReturnStatus(balanceNotEnough);
							break;

						default:
							break;
						}
					} else {
						response.setReturnStatus(failStatus);
					}

					return response;
				}
				
			} else {
				response.setReturnStatus(errorStatus);
				return response;
			}
			

		} else {//发起合买、发起晒单或发起普通代购
			scheme = new BetScheme();
			scheme.setLotteryId(lotteryId);
			scheme.setPlayId(playId);
			scheme.setCreatedTime(new Date());
			if(com.xhcms.lottery.lang.Constants.JCLQ.equals(lotteryId)
					|| com.xhcms.lottery.lang.Constants.JCZQ.equals(lotteryId)){//是竞彩
				String[] matchArr = betContent.split(",");
				Pattern p = Pattern.compile("-");
				List<BetMatch> matchList = matchService.makeMatchList(matchArr, p);
				betOptionService.combinBetOptions(playId, matchList,false);
				int code = checkMatchService.checkMatch(playId, matchList);
				if (code != 0) {
					response.setReturnStatus(failStatus);
					return response;
				}
				scheme.setMatchNumber(matchList.size());
				scheme.setMatchs(matchList);
				scheme.setMultiple(Integer.parseInt(multiple));
				Map<Long, BigDecimal> matchScoreMap=matchService.findDefaultScoreByMatchIdAndLotteryId(matchList, lotteryId);
				scheme.setMatchScoreMap(matchScoreMap);
				// 截止时间
				BetMatch minOfftimeMatch = matchService.computeMinEntrustDeadlineMatch(scheme.getMatchs(), scheme.getLotteryId());
				Date offtime = minOfftimeMatch.getEntrustDeadline();
				if (null==offtime||new Date().after(offtime)) {
					response.setReturnStatus(outDateStatus);
					return response;
				}
				scheme.setOfftime(offtime);
				if(!convertResult.isConvertSuccess()) {
					boolean isTimeLimited = betPassTypeLimitedByTime(response, userId, betContent,
							passType, betType, minOfftimeMatch);
					if(isTimeLimited) {
						return response;
					} else {
						scheme.setOfftime(minOfftimeMatch.getEntrustDeadline());
					}
				}
				Date machineOfftime = this.computeMachineOfftime(scheme, type);
				scheme.setMachineOfftime(machineOfftime);
			}
			else{//传统足彩
				scheme.setMultiple(Integer.parseInt(multiple));
				IssueInfo issueInfo = issueService.findByIssueAndPlayId(playId,issueNumber);
				Date newStopTimeForUser=issueService.computeNewStopTimeForUser(issueInfo);
				if (null==newStopTimeForUser||new Date().after(newStopTimeForUser)) {
					response.setReturnStatus(outDateStatus);
					return response;
				}
		        scheme.setOfftime(newStopTimeForUser);
				scheme.setMachineOfftime(issueInfo.getCloseTime());
		        produceMatch(scheme, issueInfo, betContent, chooseType, userId);
			}
			
			// 过关方式
			List<String> pts = new ArrayList<String>();
			String[] passTypeArr = passType.split(",");
			for (int i = 0; i < passTypeArr.length; i++) {
				pts.add(passTypeArr[i]);
			}

			scheme.setPassTypes(pts);
			scheme.setPassTypeIds("," + passType + ",");
		}

		Bet bet = betResolver.resolve(scheme);
		betSchemeService.computeMinAndMaxBonus(scheme, bet);
		scheme.setTotalAmount(bet.getNote() * 2);
		scheme.setBetNote(bet.getNote());
		scheme.setChannel(Channel.MOBILE_CLIENT);
		scheme.setPartner(partner);
		if (Integer.valueOf(betType) == EntityType.BET_TYPE_ALONE ||
				Integer.valueOf(betType) == EntityType.BET_TYPE_PARTNER) {//当发起代购或晒单下注或者发起合买时
			scheme.setPassTypeIds("," + passType + ",");
			scheme.setSponsorId(userId);
			scheme.setAfterTaxBonus(Constants.ZERO);
			scheme.setExpectBonus(Constants.ZERO);
			scheme.setPreTaxBonus(Constants.ZERO);
			// 是否晒单
			String isShow = paramMap.get(Constants.IS_SHOW);
			if (StringUtils.isNotBlank(isShow)) {
				scheme.setShowScheme((Integer.parseInt(isShow)));
			}
			//设置保密级别
			String showType = (String) paramMap.get(Constants.SHOW_TYPE);
			logger.debug("showType---->" + showType);
			if(StringUtils.isBlank(showType)){
				showType="0";
			}
			int showTypeNumber=Integer
					.parseInt(showType);
			SchemePrivacy schemePrivacy=schemePrivacyUtil.showTypeTranslateToSchemePrivacy(showTypeNumber);
			if(null!=schemePrivacy){
				// 跟单方案保密
				if(Integer.valueOf(betType) == EntityType.BET_TYPE_ALONE){
					if(schemePrivacy.getFollowSchemePrivacy()>=0){
						scheme.setFollowSchemePrivacy(schemePrivacy.getFollowSchemePrivacy());
					}
					else{
						response.setReturnStatus(failStatus);
						return response;
					}
				}
				else if(Integer.valueOf(betType) == EntityType.BET_TYPE_PARTNER){//是发起合买
					if(schemePrivacy.getPrivacy()>=0){
						scheme.setPrivacy(schemePrivacy.getPrivacy());
					}
					else{
						response.setReturnStatus(failStatus);
						return response;
					}
					
				}
				
			}
			// 跟单提成比例
			String followRatio = (String) paramMap
					.get(Constants.FOLLOWED_RATIO);
			if (followRatio != null && !followRatio.isEmpty()) {
				scheme.setFollowedRatio(Integer.parseInt(followRatio));
			}
			// 当type等于参与跟单时，此id为被跟单方案id。
			// 默认值-1，表示此单没有跟别单。当type等于参与跟单时，此id为被跟单方案id。
			scheme.setFollowedSchemeId(new Long(-1));
			// 此方案的用户是自动推荐用户，则此方案为推荐方案，否则不是推荐方案
			if (betSchemeService.isRecommendUser(scheme)) {
				scheme.setRecommendation(1);
			} else {
				scheme.setRecommendation(0);
			}
		}

		int result = 0;
		if (Integer.valueOf(betType) == EntityType.BET_TYPE_ALONE || 
				Integer.valueOf(betType) ==EntityType.BET_TYPE_PARTNER) {//发起合买或代购
			scheme.setTotalAmount(bet.getNote() * 2);
			if(lessThanTotalAmoutLimit(scheme)){
				response.setReturnStatus(lessThanTotalAmountLimitStatus);
				return response;
			}
			try {
				if(Integer.valueOf(betType) ==EntityType.BET_TYPE_PARTNER && 
						StringUtils.isNotBlank(floorAmount) && StringUtils.isNotBlank(buyAmount)){ //发起合买的时候
					scheme.setBuyAmount(Integer.valueOf(buyAmount));
					scheme.setPurchasedAmount(Integer.valueOf(buyAmount));
					scheme.setFloorAmount(Integer.valueOf(floorAmount));
					scheme.setShareRatio(Integer.valueOf(followedRatio));
					BetResult br = betSchemeService.promoteBet(userId, scheme, bet.getTickets());
					result = br.getAppCode();
				} else {//认购 和 晒单
					scheme.setBuyAmount(bet.getNote() * 2);
					if(StringUtils.isNotBlank(followedRatio)){
						scheme.setFollowedRatio(Integer.valueOf(followedRatio));
					}
					result = betSchemeService.bet(userId, scheme, bet.getTickets());
				}

			} catch (Throwable e) {
				if (e instanceof XHRuntimeException) {
					XHRuntimeException ex = (XHRuntimeException) e;
					switch (ex.getCode()) {
					case AppCode.ACCOUNT_FROZED:
						response.setReturnStatus(accountFrozen);
						break;
					case AppCode.INVALID_AMOUNT:
						response.setReturnStatus(invalidAmount);
						break;
					case AppCode.INSUFFICIENT_BALANCE:
						response.setReturnStatus(balanceNotEnough);
						break;

					default:
						break;
					}
				} else {
					response.setReturnStatus(failStatus);
				}

				return response;
			}
		} else if (Integer.valueOf(betType) == EntityType.BET_TYPE_FOLLOW) {//参与跟单
			// 设置跟单id
			scheme.setFollowedSchemeId(scheme.getId());
			// id设为0，代表插入新数据
			scheme.setId(0);
			// 最大金额
			scheme.setMaxBonus(new BigDecimal(bet.getMaxBonus()));
			// 注数
			scheme.setBetNote(bet.getNote());

			// 跟单不能被晒单
			scheme.setShowScheme(EntityType.DONT_SHOW_SCHEME);
			// 跟单提成为0
			scheme.setFollowedRatio(0);
			// 跟单不能被推荐
			scheme.setRecommendation(0);
			// 认购金额
			scheme.setBuyAmount(bet.getNote() * 2);
			// 总金额
			scheme.setTotalAmount(bet.getNote() * 2);
			if(lessThanTotalAmoutLimit(scheme)){
				response.setReturnStatus(lessThanTotalAmountLimitStatus);
				return response;
			}
			try{
				result = betSchemeService.betFollow(userId, scheme,
						bet.getTickets());
			} catch (Throwable e) {
				if (e instanceof XHRuntimeException) {
					XHRuntimeException ex = (XHRuntimeException) e;
					switch (ex.getCode()) {
					case AppCode.ACCOUNT_FROZED:
						response.setReturnStatus(accountFrozen);
						break;
					case AppCode.INVALID_AMOUNT:
						response.setReturnStatus(invalidAmount);
						break;
					case AppCode.INSUFFICIENT_BALANCE:
						response.setReturnStatus(balanceNotEnough);
						break;

					default:
						break;
					}
				} else {
					response.setReturnStatus(failStatus);
				}
				return response;
			}
			
		}

		scheme.setType(type);
		if (result > 0) {
			response.setReturnStatus(failStatus);
		} else {

			response.setReturnStatus(succStatus);
			Account account = accountService.getAccount(userId);

			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put(Constants.SCHEME, scheme);
			resultMap.put(Constants.ACCOUNT, account);
			resultMap.put("displayMode", SchemeDisplayMode.getDisplayMode(scheme.getType(), scheme.getShowScheme(), scheme.getIsPublishShow()));
			// resultMap.put(Constants.TICKET, bet.getTickets());
			response.setResultMap(resultMap);

		}
		//发此方案到群里面
		User user = userService.getUser(userId);
		if(!com.xhcms.lottery.lang.Constants.CTZC.equals(lotteryId) &&  StringUtils.equals(partner.split("[+]")[1], String.valueOf(user.getId()))){
			PublishSchemeClient publishSchemeClient = makePublish2GroupParams(partner, scheme, user);
			String jsonStr = PublishSchemeClient.toJsonString(publishSchemeClient);
			//发到特定的群里
			HX_sendMassage hx_sendMassage = new HX_sendMassage();
			String groupid = partner.split("[+]")[0];
			if(hx_sendMassage.sendMsg2Group(groupid, jsonStr)){
				PublishBetScheme publishBetScheme = makePublishSchemeParams(userId, scheme.getId(), groupid, user);
				List<BetMatchPO> betMatchPOs = betMatchDao.findBySchemeId(scheme.getId());
				boolean isResult = publishBetSchemeService.savePublishScheme(publishBetScheme, betMatchPOs);
				if(isResult){
					response.setReturnStatus(succStatus);
				} else {
					logger.info("用户userId:{}的方案schemeId:{}消息已发成功，但保存失败...", userId,schemeId);
				}
			} else {
				logger.info("用户userId:{}的方案schemeId:{}消息失败...");
			}
		}
		return response;
		
	}


	/**
	 * 投注的串关方式是否被限制
	 * <pre>非m@n串关方式限时投注</pre>
	 * <pre>如果投注的串关方式不支持m@n转换则”根据设定的时间仅支持m串1和m串n过关方式“做处理<pre>
	 * @param minOfftimeMatch
	 * @return
	 */
	private boolean betPassTypeLimitedByTime(BetSchemeResponse response, Long userId,
			String betContent, String passType, String betType, BetMatch minOfftimeMatch) {
		boolean isBeforeWithDeadline = false;
		if(Integer.valueOf(betType) == EntityType.BET_TYPE_PARTNER) {
			response.setReturnStatus(groupPassTypeLimitWithTime);
		} else {
			String passTypeTimeLimit = systemConf.findValueByKey(
					SystemConf.CLOSETIME.BET_FOR_PASSTYPE_CLOSE_TIME);
			isBeforeWithDeadline = SupportClientPassTypeMN.passTypeBeforeTimeLimit(passTypeTimeLimit, minOfftimeMatch.getEntrustDeadline());
			if(!isBeforeWithDeadline) {
				logger.info("用户投注被限制(passType_MN):用户ID={},投注串关={},betContent={}", 
						new Object[]{userId, passType, betContent});
				response.setReturnStatus(alonePassTypeLimitWithTime);
			}
			//修改方案截止时间
			Calendar cal = Calendar.getInstance();
			cal.setTime(minOfftimeMatch.getEntrustDeadline());
			int beforeLimitMinute = Integer.parseInt(passTypeTimeLimit);
			cal.set(Calendar.MINUTE, cal.get(Calendar.MINUTE) - beforeLimitMinute);
			minOfftimeMatch.setEntrustDeadline(cal.getTime());
		}
		
		return !isBeforeWithDeadline;
	}

	/**
	 * 检查方案总金额是否小于阙值
	 * @param scheme
	 * @return
	 */
	private boolean lessThanTotalAmoutLimit(BetScheme scheme) {
		boolean result=false;
		int totalAmountLimit=betSchemeService.findTotalAmountLimit();
		if(scheme.getTotalAmount()<totalAmountLimit){
			result=true;
		}
		return result;
	}


	private Integer makeType(String betType) {
		Integer type=null;
		
		if(betType.equals(String.valueOf(EntityType.BET_TYPE_ALONE))) {
			type = EntityType.BETTING_ALONE;
		} else if(betType.equals(String.valueOf(EntityType.BET_TYPE_PARTNER)) || 
				betType.equals(String.valueOf(EntityType.BET_TYPE_JION_PARTNER))) {
			type = EntityType.BETTING_PARTNER;
		} else if(betType.equals(String.valueOf(EntityType.BET_TYPE_FOLLOW))) {
			type = EntityType.BETTING_FOLLOW;
		}
		return type;
	}

	public void betHF(String lotteryId, BetSchemeResponse response, String partner, long userId, Map<String, String> paramMap) {
		DigitalBetRequest betRequst = null;
		if (com.xhcms.lottery.lang.Constants.CQSS.equals(lotteryId) ||
				com.xhcms.lottery.lang.Constants.JX11.equals(lotteryId)) {
			betRequst = new DigitalBetRequest();
			betRequst.setChannel(Channel.MOBILE_CLIENT);
			betRequst.setPartner(partner);
		} else {
			throw new RuntimeException("Unsupport LotteryId Bet.");
		}

		try {
			String playId = paramMap.get(Constants.PLAY_ID);
			String multiple = paramMap.get(Constants.MUTIPLE);
			String betContent = paramMap.get(Constants.BET_CONTENT);
			Integer betNote = Integer
					.parseInt(paramMap.get(Constants.BET_NOTE));
			Integer betType = Integer
					.parseInt(paramMap.get(Constants.BET_TYPE));
			if(null!=betType&&betType!=EntityType.BETTING_ALONE){
				response.setReturnStatus(failStatus);
				return ;
			}
			String issueNumber = paramMap.get(Constants.ISSUE_NUMBER);

			betRequst.setUserId(userId);
			betRequst.setLotteryId(lotteryId);
			List<PlayType> playTypeList = PlayTypeParse.getPlayTypeList(lotteryId, playId);
			if (playTypeList.size() > 0) {
				betRequst.setPlayType(playTypeList.get(0));
			}
			betRequst.setPlayTypeList(playTypeList);
			betRequst.setBetContents(HFBetContentParse
					.getBetContentList(betContent));
			betRequst.setMultiple(Integer.parseInt(multiple));
			betRequst.setBetNote(betNote);
			betRequst.setBetType(betType);
			betRequst.setIssue(issueNumber);
			
			IssueInfo issue = issueService
					.findByIssue(
							lotteryId,
							issueNumber,
							new Integer[] { com.xhcms.lottery.lang.Constants.ISSUE_STATUS_SALE });
			if (null == issue) {
				response.setReturnStatus(betIssueClose);
			} else {
				Map<String, Object> resultMap = new HashMap<String, Object>();
				BetScheme betScheme = null;
				if (com.xhcms.lottery.lang.Constants.CQSS.equals(lotteryId)) {
					betScheme = digitalBetService.bet(betRequst);
				} else if (com.xhcms.lottery.lang.Constants.JX11
						.equals(lotteryId)) {
					//投注的生成方式
					List<String> betList = betRequst.getBetContents();
					if(betList.get(0).startsWith("(")) {
						betRequst.setChooseType(ChooseType.DAN);
					} else {
						betRequst.setChooseType(ChooseType.MANUAL);
					}
					betScheme = digitalBetService.bet(betRequst);
				}
				resultMap.put(Constants.SCHEME, betScheme);

				IssueInfoPO issueInfo = issueService
						.getCurrentSalingIssueByLotteryId(lotteryId, new Date());
				Account account = accountService.getAccount(userId);
				if (null != issueInfo) {
					resultMap.put(Constants.COUNT_DOWN_TIME,
							Long.toString(IssueInfoUtil.getCurrentIssueCountDown(issueInfo)));
					resultMap.put(Constants.ISSUE_NUMBER,
							issueInfo.getIssueNumber());
				}
				resultMap.put("displayMode", SchemeDisplayMode.getDisplayMode(betScheme.getType(), betScheme.getShowScheme(), betScheme.getIsPublishShow()));
				resultMap.put(Constants.ACCOUNT, account);
				response.setResultMap(resultMap);
				response.setReturnStatus(succStatus);
			}
		} catch (Throwable e) {
			int code = 0;
			if (e instanceof XHRuntimeException) {
				code = ((XHRuntimeException) e).getCode();
			}
			if (e instanceof JXRuntimeException) {
				code = ((JXRuntimeException) e).getErrorCode();
			}
			if (e instanceof BetException) {
				code = ((BetException) e).getErrorCode();
			}
			switch (code) {
			case AppCode.ACCOUNT_FROZED:
				response.setReturnStatus(accountFrozen);
				break;
			case AppCode.INVALID_AMOUNT:
				response.setReturnStatus(invalidAmount);
				break;
			case AppCode.INSUFFICIENT_BALANCE:
				response.setReturnStatus(balanceNotEnough);
				break;
			case AppCode.INVALID_ISSUE_OFFTIME:
				response.setReturnStatus(betIssueClose);
				break;
			default:
				response.setReturnStatus(failStatus);
				break;
			}
			logger.error("lotteryId :{}, 投注发生异常：betRequest:{}, 异常信息：{}",
					new Object[] { lotteryId, betRequst, e.getMessage() });
		}
	}

	private PublishBetScheme makePublishSchemeParams(Long userId,
			Long schemeId, String groupid, User user) {
		PublishBetScheme publishBetScheme = new PublishBetScheme();
		publishBetScheme.setBetSchemeId(schemeId);
		publishBetScheme.setGroupid(groupid);
		publishBetScheme.setUserId(Long.valueOf(userId));
		publishBetScheme.setNickName(user.getNickName());
		publishBetScheme.setImageUrl(user.getHeadImageURL());
		return publishBetScheme;
	}

	private PublishSchemeClient makePublish2GroupParams(String channel, BetScheme scheme, User user) {
		PublishSchemeClient publishScheme = new PublishSchemeClient();
		publishScheme.userId = scheme.getSponsorId();
		publishScheme.nickName = user.getNickName();
		publishScheme.rateOfReturn = scheme.getReturnRation();
		publishScheme.betAmount = scheme.getTotalAmount();
		publishScheme.maxBonus = scheme.getMaxBonus().setScale(2, BigDecimal.ROUND_HALF_UP);
		publishScheme.lotteryId = scheme.getLotteryId();
		publishScheme.bounus = scheme.getAfterTaxBonus();
		publishScheme.betType = scheme.getType();
		publishScheme.schemeId = scheme.getId();
		publishScheme.imageUrl = user.getHeadImageURL();
		String[] strs = channel.split("[+]");
		logger.info("环信groupId: {} , 大V id : {} , clientVersion : {}" , strs[0], strs[1], strs[2]);
//		if(StringUtils.equals(String.valueOf(scheme.getSponsorId()), strs[1])){
			publishScheme.isDaV = true;
//		} else {
//			publishScheme.isDaV = false;
//		}
		publishScheme.displayMode = SchemeDisplayMode.getDisplayMode(scheme.getType(), scheme.getShowScheme(), scheme.getIsPublishShow());
		if(scheme.getStatus() == EntityStatus.TICKET_NOT_AWARD || scheme.getStatus() == EntityStatus.TICKET_AWARDED){
			publishScheme.afterTaxBonus = scheme.getAfterTaxBonus() + "";//中奖金额
		}
		if(new Date().getTime() < scheme.getOfftime().getTime()){
        	if(scheme.getStatus() != EntityStatus.TICKET_BUY_FAIL){
        		scheme.setStatus(EntityStatus.SCHEME_SALE);
        	}
	    }
		publishScheme.betSchemeStatus = scheme.getStatus();
		publishScheme.singlePrice = scheme.getTotalAmount() / scheme.getMultiple();
		return publishScheme;
	
	}

	public void betSSQ(String lotteryId, BetSchemeResponse response, String partner, long userId, Map<String, String> paramMap) {
		DigitalBetRequest betRequst = null;
		if (com.xhcms.lottery.lang.Constants.SSQ.equals(lotteryId)) {
			betRequst = new DigitalBetRequest();
			betRequst.setChannel(Channel.MOBILE_CLIENT);
			betRequst.setPartner(partner);
		} else {
			throw new RuntimeException("Unsupport LotteryId Bet.");
		}

		try {
			String multiple = paramMap.get(Constants.MUTIPLE);
			String betContent = paramMap.get(Constants.BET_CONTENT);
			String issueNumber = paramMap.get(Constants.ISSUE_NUMBER);
			Integer betType = Integer
					.parseInt(paramMap.get(Constants.BET_TYPE));
			if(null!=betType&&betType!=EntityType.BETTING_ALONE){
				response.setReturnStatus(failStatus);
				return ;
			}
			betRequst.setMultiple(Integer.parseInt(multiple));
			betRequst.setLotteryId(lotteryId);
			String[] bets = betContent.split(Constants.CLIENT_CONTENT_SPLIT);
			LinkedList<String> contents = new LinkedList<String>();
			List<PlayType> playTypes = new LinkedList<PlayType>();
			for (String bet : bets){
				if(StringUtils.isNotBlank(bet)){
					contents.add(bet);
					playTypes.add(digitalBetService.deduceSSQPlayType(bet));
				}
			}
			betRequst.setBetContents(contents);
			betRequst.setPlayTypeList(playTypes);
			if (playTypes.size() > 0) {
				betRequst.setPlayType(playTypes.get(0));
			}
			betRequst.setIssue(issueNumber);
			// TODO：应该去掉，这个参数没有什么作用了。
			betRequst.setChooseType(ChooseType.valueOfIndex(EntityType.HAND_PICK));
			
			betRequst.setUserId(userId);
			betRequst.setBetType(betType);
			
			IssueInfo issue = issueService
					.findByIssue(
							lotteryId,
							issueNumber,
							new Integer[] { com.xhcms.lottery.lang.Constants.ISSUE_STATUS_SALE });
			if (null == issue) {
				response.setReturnStatus(betIssueClose);
			} else {
				try{
					Map<String, Object> resultMap = new HashMap<String, Object>();
					BetScheme betScheme = null;
					if (com.xhcms.lottery.lang.Constants.SSQ.equals(lotteryId)) {
						betScheme = digitalBetService.bet(betRequst);
					}
					resultMap.put(Constants.SCHEME, betScheme);
	
					IssueInfoPO issueInfo = issueService
							.getCurrentSalingIssueByLotteryId(lotteryId, new Date());
					Account account = accountService.getAccount(userId);
					if (null != issueInfo) {
						resultMap.put(Constants.COUNT_DOWN_TIME,
								Long.toString(IssueInfoUtil.getCurrentIssueCountDown(issueInfo)));
						resultMap.put(Constants.ISSUE_NUMBER,
								issueInfo.getIssueNumber());
					}
					resultMap.put("displayMode", SchemeDisplayMode.getDisplayMode(betScheme.getType(), betScheme.getShowScheme(), betScheme.getIsPublishShow()));
					resultMap.put(Constants.ACCOUNT, account);
					response.setResultMap(resultMap);
					response.setReturnStatus(succStatus);
				}catch(Exception e) {
					response.setReturnStatus(failStatus);
					e.printStackTrace();
				}
			}
		} catch (Throwable e) {
			int code = 0;
			if (e instanceof XHRuntimeException) {
				code = ((XHRuntimeException) e).getCode();
			}
			if (e instanceof JXRuntimeException) {
				code = ((JXRuntimeException) e).getErrorCode();
			}
			if (e instanceof BetException) {
				code = ((BetException) e).getErrorCode();
			}
			switch (code) {
			case AppCode.ACCOUNT_FROZED:
				response.setReturnStatus(accountFrozen);
				break;
			case AppCode.INVALID_AMOUNT:
				response.setReturnStatus(invalidAmount);
				break;
			case AppCode.INSUFFICIENT_BALANCE:
				response.setReturnStatus(balanceNotEnough);
				break;
			case AppCode.INVALID_ISSUE_OFFTIME:
				response.setReturnStatus(betIssueClose);
				break;
			default:
				response.setReturnStatus(failStatus);
				break;
			}
			logger.error("lotteryId :{}, 投注发生异常：betRequest:{}, 异常信息：{}",
					new Object[] { lotteryId, betRequst, e.getMessage() });
		}
	}
	
	

	private void produceMatch(BetScheme scheme, IssueInfo issueInfo, String matchs, int chooseType, long userId){
//    	Date offtime = new Date();
    	if(scheme.getLotteryId().equals(com.xhcms.lottery.lang.Constants.CTZC)){
//            scheme.setOfftime(offtime);
    		CTBetRequest ctBetRequest = new CTBetRequest();
    		String[] matchArr = matchs.split("\\|");
    		Pattern p = Pattern.compile(";");
    		List<CTBetContent> matchList = new ArrayList<CTBetContent>(matchArr.length);
    		for (int i = 0; i < matchArr.length; i++) {
                String match = matchArr[i];
                String[] m = p.split(match);
                if(StringUtils.isNotBlank(match)){
                    CTBetContent ctc = new CTBetContent();
                    ctc.setIssueNumber(issueInfo.getIssueNumber());
                    ctc.setLotteryId(scheme.getLotteryId());
                    ctc.setPlayId(scheme.getPlayId());
                    ctc.setCode(m[1]);
                    ctc.setChooseType(chooseType);
                    if(m.length>2){
                    	ctc.setSeed(m[2]);
                    }
                    matchList.add(ctc);
                    
                    ctBetRequest.setBetContents(Arrays.asList(matchArr));
                    ctBetRequest.setIssue(issueInfo.getIssueNumber());
                    ctBetRequest.setLotteryId(scheme.getLotteryId());
                    ctBetRequest.setMultiple(scheme.getMultiple());
                    ctBetRequest.setChooseType(ChooseType.valueOfIndex(chooseType));
                    ctBetRequest.setUserId(userId);
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
    	}
    }
	
	/**
	 * 计算彩机截止时间 
	 * @param scheme
	 * @return
	 */
	private Date computeMachineOfftime(BetScheme scheme, int type) {
		return matchService.computeMachineOfftime(scheme.getMatchs(),scheme.getLotteryId());
	}
}

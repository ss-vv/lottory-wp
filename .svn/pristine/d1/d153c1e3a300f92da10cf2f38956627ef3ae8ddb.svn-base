package com.unison.lottery.api.protocol.response.xml.parser.methodparse;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap; 
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.api.lang.DateUtil;
import com.unison.lottery.api.protocol.common.Constants;
import com.unison.lottery.api.protocol.common.SystemStatusKeyNames;
import com.unison.lottery.api.protocol.common.VONames;
import com.unison.lottery.api.protocol.common.model.Item;
import com.unison.lottery.api.protocol.common.model.Result;
import com.unison.lottery.api.protocol.response.model.IHaveReturnStatus;
import com.unison.lottery.api.protocol.response.model.Response;
import com.unison.lottery.api.protocol.response.model.SchemeDetailResponse;
import com.unison.lottery.api.schemeDetail.data.CTSchemeDetailData;
import com.xhcms.lottery.commons.data.BetMatch;
import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.data.DigitalBetContent;
import com.xhcms.lottery.commons.data.PlayMatch;
import com.xhcms.lottery.commons.data.User;
import com.xhcms.lottery.commons.persist.entity.BBMatchPlayPO;
import com.xhcms.lottery.commons.persist.service.BetSchemeService;
import com.xhcms.lottery.commons.persist.service.MatchService;
import com.xhcms.lottery.commons.utils.BetMatchComparator;
import com.xhcms.lottery.lang.EntityStatus;
import com.xhcms.lottery.lang.EntityType;
import com.xhcms.lottery.lang.MixPlayType;
import com.xhcms.lottery.lang.PlayType;
import com.xhcms.lottery.utils.ResultTool;

public class SchemeDetailMethodResponseParser extends
		AbstractMethodResponseParser {
	@Autowired
	private MatchService mathcService;
	
	@Autowired
	private BetSchemeService betSchemeService;
	
	@Override
	protected IHaveReturnStatus getResponseFromHttpRequest(
			HttpServletRequest httpRequest) {
		return (IHaveReturnStatus) httpRequest.getAttribute(VONames.SCHEME_DETAIL_VO_NAME);
	}
	
	@Override
	protected void handle(Response resultResponse,
			Object responseFromHttpRequest) {
		SchemeDetailResponse schemeDetailResponse=(SchemeDetailResponse) responseFromHttpRequest;
		
		if(null!=schemeDetailResponse&&schemeDetailResponse.getResultMap()!=null){
			Map<String,Object> resultMap = schemeDetailResponse.getResultMap();
			BetScheme scheme = (BetScheme)resultMap.get(Constants.SCHEME);
			User sponsor = (User)resultMap.get("sponsor");//这里的user对象是方案发起人
			Long userId=(Long)resultMap.get(Constants.USER_ID);//这里的user对象是登陆用户
			resultResponse.result=new Result();
			if(sponsor != null){
				resultResponse.result.imageUrl = sponsor.getHeadImageURL();
				resultResponse.result.nickName = sponsor.getNickName();
			}
			//总中奖金额
			resultResponse.result.sponsorAward = normalBigDecimal((BigDecimal)resultMap.get(Constants.SPONSOR_AWARD));
			
			resultResponse.result.sponsor = scheme.getSponsor();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			resultResponse.result.schemeCreateTime = dateFormat.format(scheme.getCreatedTime());
			
			resultResponse.result.playId = scheme.getPlayId();
			resultResponse.result.betNote = scheme.getBetNote();
			resultResponse.result.multiple = scheme.getMultiple();
			resultResponse.result.betType = scheme.getType();
			//我的认购金额
			resultResponse.result.sumBetAmount = normalBigDecimal((BigDecimal)resultMap.get(Constants.SUM_BET_AMOUNT));
			resultResponse.result.schemeStatus = scheme.getStatus();
			//方案总额
			resultResponse.result.totalAmount = scheme.getTotalAmount();
			//理论最高奖金
			resultResponse.result.maxBonus = scheme.getMaxBonus().setScale(2, BigDecimal.ROUND_HALF_UP);
			//税后奖金
			resultResponse.result.taxBonus =normalBigDecimal( scheme.getAfterTaxBonus());
			//进度
			double progressDouble = (scheme.getPurchasedAmount()*1.0)/scheme.getTotalAmount();
			BigDecimal progress=new BigDecimal(progressDouble).setScale(2, RoundingMode.DOWN);
			resultResponse.result.progress = progress.doubleValue();
			//合买的保底金额
			resultResponse.result.floorAmount = scheme.getFloorAmount()*1.0 / scheme.getTotalAmount();
			//合买的剩余
			resultResponse.result.surplus = scheme.getTotalAmount() - scheme.getPurchasedAmount(); //剩余份数 = 总金额 - 认购金额
			//合买的提成
			resultResponse.result.pushMoney = scheme.getShareRatio();
			//合买的总额
			resultResponse.result.price = scheme.getTotalAmount();
			//截止时间
			resultResponse.result.offtime = dateFormat.format(scheme.getOfftime());;
			//实际派奖
			resultResponse.result.sumBonus = normalBigDecimal((BigDecimal)resultMap.get(Constants.SUM_BONUS));
			
			if(scheme.getShowScheme()==1){
				
				resultResponse.result.followRatio = scheme.getFollowedRatio();
			}
			//保密级别
			resultResponse.result.privacy = makePrivacy(scheme);
			
			
			//给发起人的佣金 
			if(resultMap.get(Constants.SPONSOR_COMMISSION)!=null){
				resultResponse.result.sponsorCommission = (BigDecimal)resultMap.get(Constants.SPONSOR_COMMISSION);
				resultResponse.result.sponsorCommission =resultResponse.result.sponsorCommission.setScale(2, RoundingMode.DOWN);//保留小数点2位，后面的截断
			}
			
			String lotteryId = scheme.getLotteryId();
			resultResponse.result.lotteryId= lotteryId;
			if(com.xhcms.lottery.lang.Constants.CQSS.equals(lotteryId) 
					|| com.xhcms.lottery.lang.Constants.JX11.equals(lotteryId)) {//高频彩
				handleWithHFLottery(resultResponse, resultMap, scheme);
			} else if(com.xhcms.lottery.lang.Constants.CTZC.equals(lotteryId)) {//传统足彩
				handleWithCTZC(resultResponse, resultMap,scheme);
			}  else if(com.xhcms.lottery.lang.Constants.SSQ.equals(lotteryId)) {//双色球
				handleWithSSQ(resultResponse, resultMap);
			} else {
				
				String displayMode=(String) resultMap.get(Constants.DISPLAY_MODE);
				handleWithJC(resultResponse, scheme, dateFormat,userId,displayMode);
			}
		}
	}

	private BigDecimal normalBigDecimal(BigDecimal bigDecimal) {
		if(null!=bigDecimal){
			return bigDecimal.setScale(2, RoundingMode.DOWN);
		}
		return null;
	}

	private Integer makePrivacy(BetScheme scheme) {
		Integer result = null;
		if(scheme.getType()==EntityType.BETTING_PARTNER){//合买
			int privacy = scheme.getPrivacy();
			if(privacy==2){
				result=5;
			}
			else if(privacy==3){
				result=6;
			}
			else if(privacy==0){
				result=privacy;
			}
		}
		else if(scheme.getType()==EntityType.BET_TYPE_ALONE){//代购或者发起跟单
			if(scheme.getShowScheme()==1){//发起跟单并晒单时，showScheme为1
				int followSchemePrivacy = scheme.getFollowSchemePrivacy();
				if(followSchemePrivacy==2||followSchemePrivacy==0||followSchemePrivacy==3){
					result=followSchemePrivacy;
				}
				
			}
		}
		return result;
	}

	private void handleWithJC(Response resultResponse, BetScheme scheme,
			SimpleDateFormat dateFormat, Long userId, String displayMode) {
		resultResponse.result.passType = scheme.getPassTypeIds();
		resultResponse.result.item = new ArrayList<Item>();
		if(null != scheme.getMatchs()&&!scheme.getMatchs().isEmpty()) {
			List<BetMatch> matchesToHandle = makeMatchesToHandle(scheme);
			boolean needKeepSecret=betSchemeService.needKeepSecret(userId, Integer.parseInt(displayMode), scheme);
			for (BetMatch betMatch : matchesToHandle) {
				
				handleWithOneBetMatch(resultResponse, scheme, dateFormat,
						betMatch,displayMode, needKeepSecret);
			}
		}
	}

	private List<BetMatch> makeMatchesToHandle(BetScheme scheme) {
		List<BetMatch> matchesToHandle=null;
		if(isFHPlayId(scheme)){//是复合玩法，一个比赛会对应多个玩法，这里要做个整合，把比赛列表中属于同一个比赛的多个玩法的betContent合并到一个betcontent里,形成一个比赛对象
			matchesToHandle=compositeFHMatches(scheme.getMatchs());
		}
		else{
			matchesToHandle=scheme.getMatchs();
		}
		sortBetMatch(matchesToHandle);
		
		return matchesToHandle;
	}

	private void sortBetMatch(List<BetMatch> matchesToHandle) {
		if(null!=matchesToHandle&&!matchesToHandle.isEmpty()){
			BetMatchComparator<BetMatch> betMatchComparator = new BetMatchComparator<BetMatch>();
			Collections.sort(matchesToHandle, betMatchComparator);
		}
	}

	private List<BetMatch> compositeFHMatches(List<BetMatch> matchs) {
		List<BetMatch> result=null;
		Map<Long,BetMatch> map=new HashMap<Long,BetMatch>();
		for(BetMatch match:matchs){
			Long matchId=match.getMatchId();
			if(map.containsKey(matchId)){
				PlayMatch resultMatch = (PlayMatch) map.get(matchId);
				String betContent4MixFH = resultMatch.getBetContent4MixFH();
				String newBetContent4MixFh=ResultTool.cn4Client(match.getPlayId(),((PlayMatch)match) .getBetCode(), match.getOdds(),((PlayMatch)match).getConcedePoints(),((PlayMatch)match).getDefaultScore());
				if(StringUtils.isNotBlank(betContent4MixFH)&&StringUtils.isNotBlank(newBetContent4MixFh)){
					((PlayMatch)resultMatch).setBetContent4MixFH(betContent4MixFH+","+newBetContent4MixFh);
					
				}
				boolean isMatchWin=resultMatch.isMatchWin();
				resultMatch.setMatchWin(isMatchWin||match.isMatchWin());//针对多种玩法，只要有一种玩法是过关的，则整个比赛就是过关的
			}
			else{
				BetMatch resultMatch=new PlayMatch();
				try {
					String newBetContent4MixFh=ResultTool.cn4Client(match.getPlayId(), ((PlayMatch)match).getBetCode(), match.getOdds(),((PlayMatch)match).getConcedePoints(),((PlayMatch)match).getDefaultScore());
					((PlayMatch)match).setBetContent4MixFH(newBetContent4MixFh);
					BeanUtils.copyProperties(resultMatch, match);
					map.put(matchId, resultMatch);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
				
			}
		}
		if(!map.isEmpty()){
			result=new ArrayList<BetMatch>();
			for(BetMatch resultMatche:map.values()){
				result.add(resultMatche);
			}
			
		}
		
		return result;
	}

	private boolean isFHPlayId(BetScheme scheme) {
		return PlayType.isFHMixBet(scheme.getPlayId());
	}

	private void handleWithOneBetMatch(Response resultResponse,
			BetScheme scheme, SimpleDateFormat dateFormat, BetMatch betMatch, String displayMode, boolean needKeepSecret) {
		Item playMatchItem = new Item();
		PlayMatch playMatch = (PlayMatch)betMatch;
		playMatchItem.matchCode = playMatch.getCnCode();
		playMatchItem.name= playMatch.getName();
		//让球数
//		if(scheme.getLotteryId().equals("JCZQ")){
//			int concedePoints=Float.valueOf(playMatch.getConcedePoints()).intValue();
//			if(!StringUtils.equals("0.0", playMatch.getConcedePoints()) && concedePoints>0){
//				playMatchItem.concedePoints = "+"+concedePoints;
//			}else{
//				playMatchItem.concedePoints = ""+concedePoints;
//			}
//		}else if(scheme.getLotteryId().equals("JCLQ")){
			
//			if(scheme.getStatus() == EntityStatus.TICKET_BUY_SUCCESS ||  scheme.getStatus() == EntityStatus.TICKET_NOT_WIN || scheme.getStatus() == EntityStatus.TICKET_NOT_AWARD || scheme.getStatus() == EntityStatus.TICKET_AWARDED || scheme.getStatus() == EntityStatus.TICKET_SCHEME_FLOW){
//				if(playMatch.getDefaultScore()>0){
//					playMatchItem.defaultScore = "+"+String.valueOf(playMatch.getDefaultScore());
//				}else{
//					playMatchItem.defaultScore = String.valueOf(playMatch.getDefaultScore());
//				}
//			}
			
//			Collection<String> playIds = new HashSet<String>();
//			playIds.add(playMatch.getPlayId());
//			List<BBMatchPlayPO> bbPlayList = mathcService.findBBMatchPlayPOByMatchIdAndPlayIds(playMatch.getMatchId(), playIds);
//			if(bbPlayList.size() > 0) {
//				if (com.xhcms.lottery.lang.Constants.PLAY_09_LC_2.equals(playMatch.getPlayId())) {
//		    		playMatchItem.concedePointsDXF = String.valueOf(bbPlayList.get(0).getDefaultScore());
//				} else {
//					playMatchItem.concedePoints = String.valueOf(bbPlayList.get(0).getDefaultScore());
//				}	
//			}
//		}
		
		
		//全场比分
		playMatchItem.score = playMatch.getScore();
		//赛果
//		playMatchItem.matchResult = ResultTool.cn(scheme.getPlayId(), playMatch.getResult(), null);
	
		//投注内容
		if (com.xhcms.lottery.lang.Constants.PLAY_05_ZC_2.equals(scheme.getPlayId())) {
			playMatchItem.betContent = ResultTool.cn(playMatch.getPlayId(), playMatch.getBetCode(), playMatch.getOdds());
			PlayType playType = PlayType.valueOfLcPlayId(playMatch.getPlayId());
			MixPlayType mixPlayType = MixPlayType.valueOfPlayType(playType);
			playMatchItem.playId = mixPlayType.getName().toLowerCase();
		} else if (com.xhcms.lottery.lang.Constants.PLAY_10_LC_2.equals(scheme.getPlayId())){
			playMatchItem.betContent = ResultTool.cn(playMatch.getPlayId(), playMatch.getBetCode(), playMatch.getOdds());
			PlayType playType = PlayType.valueOfLcPlayId(playMatch.getPlayId());
			MixPlayType mixPlayType = MixPlayType.valueOfPlayType(playType);
			playMatchItem.playId = mixPlayType.getName().toLowerCase();
		} else {
			if(isFHPlayId(scheme)){
				playMatchItem.betContent =playMatch.getBetContent4MixFH();
			}else{
				playMatchItem.betContent = ResultTool.cn4Client(scheme.getPlayId(), playMatch.getBetCode(), playMatch.getOdds(),playMatch.getConcedePoints(),playMatch.getDefaultScore());
			}
			
		}
		
		if(needKeepSecret){
			playMatchItem.betContent="保密";
		}
		//开赛时间
		playMatchItem.playingTime = playMatch.getPlayingTime() == null ? null : dateFormat.format(playMatch.getPlayingTime());
		if (canSetIsPass(scheme)) {//如果方案状态处于出票成功、中奖未派奖、已派奖和未中奖状态，才需要显示是否过关
			playMatchItem.isPass = playMatch.isMatchWin();
		}
		
		resultResponse.result.item.add(playMatchItem);
	}

	private boolean canSetIsPass(BetScheme scheme) {
		return scheme.getStatus() == EntityStatus.TICKET_BUY_SUCCESS
				|| scheme.getStatus() == EntityStatus.TICKET_NOT_AWARD
				|| scheme.getStatus() == EntityStatus.TICKET_AWARDED
				||scheme.getStatus() ==EntityStatus.TICKET_NOT_WIN;
	}

	private void handleWithSSQ(Response resultResponse,
			Map<String, Object> resultMap) {
		resultResponse.result.item = new ArrayList<Item>();
		Object betObj = resultMap.get(Constants.DIGITAL_BET_CONTENT);
		if(null != betObj) {
			@SuppressWarnings("unchecked")
			List<DigitalBetContent> list = (List<DigitalBetContent>) betObj;
			if(list.size() > 0) {
				resultResponse.result.issueNumber = list.get(0).getIssueNumber();
			}
			String bonusCode = String.valueOf(resultMap.get(Constants.BONUS_CODE));
			for(DigitalBetContent bet : list) {
				Item item = new Item();
				item.betContent = bet.getCode();
				item.betNote = String.valueOf(bet.getNote());
				item.money = bet.getMoney();
				item.playId = bet.getPlayId();
				item.matchResult = bonusCode;
				
				resultResponse.result.item.add(item);
			}
		}
	}

	private void handleWithCTZC(Response resultResponse,
			Map<String, Object> resultMap, BetScheme scheme) {
		resultResponse.result.item = new ArrayList<Item>();
		@SuppressWarnings("unchecked")
		List<CTSchemeDetailData> ctSchemeDetailDataList = (List<CTSchemeDetailData>) resultMap.get(Constants.CTFB_SCHEMEDETAIL);
		for(CTSchemeDetailData detailData : ctSchemeDetailDataList) {
			Item item = new Item();
			resultResponse.result.issueNumber = detailData.getIssueNumber();
			item.name = detailData.getMatchName();
			if(StringUtils.isNotBlank(detailData.getBetContent())) {
				item.betContent = detailData.getBetContent().trim();
			}
			if(StringUtils.isNotBlank(detailData.getMatchResult())) {
				item.matchResult = detailData.getMatchResult().trim();
			}
			item.matchCode = String.valueOf(detailData.getMatchId());
			item.playingTime = DateUtil.format(detailData.getPlayingTime(), null);
			if (canSetIsPass(scheme)){
				item.isPass=detailData.isPass();
			}
			resultResponse.result.item.add(item);
		}
	}

	private void handleWithHFLottery(Response resultResponse,
			Map<String, Object> resultMap, BetScheme scheme) {
		String bonusCode = String.valueOf(resultMap.get(Constants.BONUS_CODE));
		resultResponse.result.matchResult = bonusCode;
		List<DigitalBetContent> betContents = scheme.getDigitalBetContents();
		resultResponse.result.item = new ArrayList<Item>();
		for(DigitalBetContent hfBetContent : betContents) {
			Item item = new Item();
			resultResponse.result.issueNumber = hfBetContent.getIssueNumber();
			item.betContent = hfBetContent.getCode();
			item.betNote = hfBetContent.getNote() + "";
			item.money = hfBetContent.getMoney();
			item.playId = hfBetContent.getPlayId();
			resultResponse.result.item.add(item);
		}
	}
	
	@Override
	protected String getDefaultReturnStatusKeyName() {
		return SystemStatusKeyNames.SchemeDetail.FAIL;
	}

}

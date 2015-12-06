package com.xhcms.lottery.commons.persist.service.impl;

import java.math.BigDecimal; 
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.xhcms.lottery.commons.data.BetMatch;
import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.data.PlayMatch;
import com.xhcms.lottery.commons.persist.service.AccountQueryService;
import com.xhcms.lottery.commons.persist.service.AccountService;
import com.xhcms.lottery.commons.persist.service.Scheme2ViewService;
import com.xhcms.lottery.lang.EntityStatus;
import com.xhcms.lottery.lang.EntityType;
import com.xhcms.lottery.lang.LotteryId;
import com.xhcms.lottery.lang.PlayType;
import com.xhcms.lottery.utils.ResultTool;
import com.xhcms.lottery.utils.StatusTool;

public class Scheme2ViewServiceImpl implements Scheme2ViewService {

	@Autowired
    private AccountQueryService accountQueryService;
	
	@Autowired
    private AccountService accountService;
	
	protected BetScheme convertMatchs(BetScheme betScheme) {
		boolean betCodeNull = false;
		String playId = betScheme.getPlayId();
		boolean isMixPlay = false;
		if(playId.startsWith(PlayType.JCZQ_HH.getShortPlayStr()) ||
				playId.startsWith(PlayType.JCLQ_HH.getShortPlayStr())) {
			isMixPlay = true;
		} else if(playId.startsWith(PlayType.JCZQ_SPF.getShortPlayStr()) ||
				playId.startsWith(PlayType.JCLQ_RFSF.getShortPlayStr())) {
			betScheme.setShowConcede(true);
		}
		
		List<BetMatch> betMatchs = betScheme.getMatchs();
		if (null != betMatchs && betMatchs.size() > 0) {
			for (int i = 0; i < betMatchs.size(); i++) {
				PlayMatch playMatch = (PlayMatch) betMatchs.get(i);
				String betCode = playMatch.getBetCode();
				
				PlayMatch fillPlayMatch = fillBetOptions(betScheme, playMatch);
				betScheme.getMatchs().set(i, fillPlayMatch);
				
				// 过关方式
				if(StringUtils.isNotBlank(betScheme.getPassTypeIds())) {
					betScheme.setPassTypeIds(betScheme.getPassTypeIds()
							.replace(',', ' ').replaceAll("@", "串"));
				}
				if(StringUtils.isBlank(playMatch.getConcedePoints()) ||
						playMatch.getConcedePoints().startsWith("0.0")) {
					playMatch.setConcedePoints("");
				}

				// 选择比赛的玩法
				playMatch.setPlayName(StatusTool.getPlayName(playMatch
						.getPlayId()));

				// 调用拆分主客队逻辑
				playMatch = autowirePlayName(playMatch);
				betScheme.getMatchs().set(i, playMatch);

				/**是否是混合投注**/
				if(isMixPlay) {
					String mPlayId = playMatch.getPlayId();
					if(mPlayId.indexOf(PlayType.JCZQ_SPF.getShortPlayStr()) == 0 ||
							mPlayId.indexOf(PlayType.JCLQ_RFSF.getShortPlayStr()) == 0) {
						betScheme.setShowConcede(true);
					}
				}
				
				if (StringUtils.isBlank(betCode)) {
					betCodeNull = true;
				}
			}

			// 保密状态
			String privacyMsg = "";
			if (betCodeNull) {
				if (betScheme.getType() == EntityStatus.BETSCHEME_TYPE_GROUPBUY) {
					privacyMsg = StatusTool.privacy(betScheme.getPrivacy());
				} else {
					privacyMsg = StatusTool.privacy(betScheme
							.getFollowSchemePrivacy());
				}
				betScheme.setPrivacyMsg(privacyMsg);
			}

			// 方案奖金
			BigDecimal sponsorAward = accountService.getAccount(
					betScheme.getSponsorId()).getTotalAward();
			sponsorAward = (sponsorAward == null) ? new BigDecimal(0.00)
					: sponsorAward;
			if(EntityType.BETTING_PARTNER == betScheme.getType()) {
				betScheme.setSumBonus(betScheme.getAfterTaxBonus());
			} else {
				BigDecimal[] sums = accountQueryService.sumBonus(
						betScheme.getId(), betScheme.getSponsorId());
				if (null != sums && sums.length >= 2) {
					betScheme.setSumBonus(sums[1]);
				}
			}
			betScheme.setProgress(ResultTool.progress(betScheme));
		}
		return betScheme;
	}
	
	@Transactional
	@Override
	public BetScheme scheme2View(BetScheme betScheme, boolean isRealScheme) {
		if (null != betScheme && betScheme.getId() > 0) {
			List<BetMatch> betMatchs = betScheme.getMatchs();
			betScheme.setPlayName(StatusTool.getPlayName(betScheme.getPlayId()));
			
			if(isRealScheme) {
				betScheme = convertMatchs(betScheme);
			} else {
				if (null != betMatchs && betMatchs.size() > 0) {
					String ratio = calculateHitPercent(betScheme);
					betScheme.setRatio(ratio);
					
					for (int i = 0; i < betMatchs.size(); i++) {
						PlayMatch playMatch = (PlayMatch) betScheme.getMatchs().get(i);
						playMatch = autowirePlayName(playMatch);
						
						PlayMatch fillPlayMatch = fillBetOptions(betScheme, playMatch);
						betScheme.getMatchs().set(i, fillPlayMatch);
					}
				}
			}
		}
		return betScheme;
	}

	protected PlayMatch autowirePlayName(PlayMatch playMatch) {
		String[] nameList = splitMatchName(playMatch.getName());
		if (2 <= nameList.length) {
			playMatch.setHomeName(nameList[0]);
			playMatch.setGuestName(nameList[1]);
		}
		return playMatch;
	}
	
	// 拆分主客队
	protected String[] splitMatchName(String matchName) {
		String[] nameList = new String[2];
		if (StringUtils.isNotBlank(matchName)) {
			String[] names = matchName.split("VS");
			if (2 <= names.length) {
				nameList[0] = names[0];
				nameList[1] = names[1];
			}
		}
		return nameList;
	}
	
	protected PlayMatch fillBetOptions(BetScheme betScheme, PlayMatch playMatch) {
		// 判断比赛是否过关
		boolean isMatchWin = ResultTool.isMatchWin(playMatch,betScheme);
		playMatch.setMatchWin(isMatchWin);

		// 计算方案投注选项
		String betOptions = null;
		String playId = playMatch.getPlayId();
		String betCode = playMatch.getBetCode();
		String odds = playMatch.getOdds();
		String lotteryId = betScheme.getLotteryId();
		if (LotteryId.JCZQ.name().equals(lotteryId)
				|| LotteryId.JCLQ.name().equals(lotteryId)
				&& StringUtils.isNotBlank(playMatch.getBetCode())) {
			if (isMatchWin) {
				playMatch.setMatchWin(isMatchWin);
				betOptions = ResultTool.cn(playId, betCode, null);//页面显示赔率格式改变，使用“选项@赔率”，不需要“选项(赔率)”
			} else {
				betOptions = ResultTool.cn(playId, betCode, null);
			}
		} else if (LotteryId.BJDC.name().equals(
				betScheme.getLotteryId())) {
			 betOptions = ResultTool.cn(playId, betCode, null);
		}
		playMatch.setBetOptions(betOptions);

		// 填充赛果
		if (playMatch.getStatus() == EntityStatus.MATCH_OVER) {
			String resultView = null;
			if (LotteryId.BJDC.name().equals(
					betScheme.getLotteryId())) {
				 resultView = ResultTool.cn(playId, playMatch.getResult(), playMatch.getOdds());
			} else {
				//新版方案，一场比赛有多玩法，不能使用result判断赛果
				//resultView = ResultTool.cn(playId, playMatch.getResult(), null);
			}
			playMatch.setResultView(resultView);
		}
		return playMatch;
	}
	
	public String calculateHitPercent(BetScheme betScheme) {
		int totalCount = betScheme.getMatchs().size();
		int guodanCount = 0;
		int overCount = 0;
		for (BetMatch m : betScheme.getMatchs()) {
			PlayMatch p = (PlayMatch)m;
			if(ResultTool.isMatchWin(p, betScheme)){
				guodanCount ++;
			}
			if(p.getStatus() == 5){
				overCount ++;
			}
		}
		if(overCount == totalCount){
			int ratio = guodanCount * 100 / totalCount;
			return ratio+"%";
		}
		return "";
	}
}

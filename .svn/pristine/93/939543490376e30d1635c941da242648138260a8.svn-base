package com.unison.lottery.weibo.web.action.pc.ajax;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;
import com.unison.lottery.weibo.data.MatchFloatCard;
import com.unison.lottery.weibo.data.service.store.data.BBMatchScore;
import com.unison.lottery.weibo.data.service.store.data.FBMatchScore;
import com.unison.lottery.weibo.data.service.store.persist.service.BBMatchDataService;
import com.unison.lottery.weibo.data.service.store.persist.service.MatchDataService;
import com.unison.lottery.weibo.utils.LotteryIdMatchData;
import com.unison.lottery.weibo.web.action.BaseAction;
import com.unison.lottery.weibo.web.service.MatchFloatCardService;
import com.xhcms.lottery.lang.LotteryId;

public class MatchFloatCardAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	private Logger log = LoggerFactory.getLogger(getClass());
	private String lotteryType;
	private String matchId;
	private MatchFloatCard matchFloatCard;
	
	@Autowired
	private MatchDataService matchDataService;
	
	@Autowired
	private BBMatchDataService bbMatchDataService;
	
	@Autowired
	private MatchFloatCardService matchFloatCardService;
	public String execute(){
		matchFloatCard = new MatchFloatCard(); 
		if(StringUtils.isBlank(matchId) || StringUtils.isBlank(lotteryType)){
			return SUCCESS;
		}
		matchFloatCard.setMatchId(Long.parseLong(matchId));
		matchFloatCard.setLotteryType(lotteryType);
		try {
			if(LotteryIdMatchData.isZC(lotteryType)) {
				FBMatchScore fbMatchScore = matchDataService.getFBMatch(matchId);
				BeanUtils.copyProperties(fbMatchScore, matchFloatCard);
			} else if(LotteryIdMatchData.isLC(lotteryType)) {
				BBMatchScore bbMatchScore = bbMatchDataService.getBBMatch(matchId);
				BeanUtils.copyProperties(bbMatchScore, matchFloatCard);
				matchFloatCard.setMatchStatus(bbMatchScore.getMatchState());
			}
		} catch (Exception e) {
			log.error("查询赛事比分结果异常!", e);
			e.printStackTrace();
		}
		matchFloatCardService.addCardOddInfo(matchFloatCard);
		matchFloatCardService.addCardFavorCountInfo(matchFloatCard,getUserLaicaiWeiboId()+"");
		matchFloatCardService.addCardRealAndRecCountInfo(matchFloatCard);
		return SUCCESS;
	}

	public String getLotteryType() {
		return lotteryType;
	}

	public void setLotteryType(String lotteryType) {
		this.lotteryType = lotteryType;
	}

	public String getMatchId() {
		return matchId;
	}

	public void setMatchId(String matchId) {
		this.matchId = matchId;
	}

	public MatchFloatCard getMatchFloatCard() {
		return matchFloatCard;
	}

	public void setMatchFloatCard(MatchFloatCard matchFloatCard) {
		this.matchFloatCard = matchFloatCard;
	}
}

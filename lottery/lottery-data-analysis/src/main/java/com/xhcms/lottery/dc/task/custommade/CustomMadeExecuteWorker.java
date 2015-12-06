/**
 * 
 */
package com.xhcms.lottery.dc.task.custommade;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.commons.job.Job;
import com.xhcms.lottery.commons.data.Bet;
import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.data.CustomMade;
import com.xhcms.lottery.commons.data.CustomMadeAvaiableScheme;
import com.xhcms.lottery.commons.persist.service.BetSchemeBaseService;
import com.xhcms.lottery.commons.util.BetResolver;
import com.xhcms.lottery.dc.persist.service.CustomMadeService;
import com.xhcms.lottery.lang.EntityStatus;
import com.xhcms.lottery.lang.EntityType;

/**
 * @author Bean.Long
 * 定制检查执行器
 * 检查是否有符合条件的合买和晒单可以
 */
public class CustomMadeExecuteWorker extends Job {
	
	
	/**
	 * 记录处理结果的内部类
	 * @author u9
	 *
	 */
	private class CustomMadeProcessResult {
		 int custFollow ;//处理的跟单计数
		 int custGroupBuy ;//处理的合买计数
		
	}

	private static Logger logger = LoggerFactory.getLogger(CustomMadeExecuteWorker.class);
	
	@Autowired
	private BetSchemeBaseService betSchemeBaseService;
	@Autowired
	private BetResolver betResolver;
	
	@Autowired
	private CustomMadeService customMadeService;
	
	@Override
	protected void execute() throws Exception {
		logger.info("start execute CustomMadeExecuteWorker!");
		try {
			excuteCustomMadeTask();
		}catch(Throwable exp) {
			logger.error(exp.getMessage(), exp);
		}
		logger.info("end execute CustomMadeExecuteWorker!");
	}
	
	private void excuteCustomMadeTask() {
		List<CustomMadeAvaiableScheme> availableSchemes = customMadeService.listCustomMadeAvaiableSchemes();
		if (availableSchemes.size() == 0) {
			return;
		}	
		for (CustomMadeAvaiableScheme availableScheme : availableSchemes) {
			
			handleOneCustomMadeAvaiableScheme(availableScheme);			
		}
	}

	private void handleOneCustomMadeAvaiableScheme(
			CustomMadeAvaiableScheme availableScheme) {
		BetScheme origalBetScheme = betSchemeBaseService.getSchemeById(availableScheme.getId());
		if(null==origalBetScheme){
			logger.error("origalBetScheme {} can't find!",availableScheme.getId());
			return;
		}
		
		if (notValidBetScheme(origalBetScheme)) {//方案不合法，无法处理，需要标记一下，下次不再处理
			updateCustomMadeStatusAndLog(availableScheme, origalBetScheme,
					"Scheme {} can't been coustommade!");
			return;
		}
		
		Date currTime = Calendar.getInstance().getTime();
		if (origalBetScheme.getOfftime().before(currTime)) {//方案过期，不再处理
			updateCustomMadeStatusAndLog(availableScheme, origalBetScheme,
					"Scheme {} is offtime!");
			return;
		}
		
		//找到所有跟从origalBetScheme发起者的定制方案
		List<CustomMade> customMades = customMadeService.listAllCustomMades(origalBetScheme.getSponsorId());
		
		logger.debug("Check user({})'s custom size:{}", origalBetScheme.getSponsorId(), customMades.size());
		
		//处理跟从origalBetScheme发起者的定制方案
		CustomMadeProcessResult processResult = processCustomMadeSchemes(
				availableScheme, origalBetScheme, customMades);
		logger.debug("Custom Scheme {}, follow:{}, group:{}", new Object[] {
				origalBetScheme.getId(), processResult.custFollow,
				processResult.custGroupBuy });
		
	}

	private CustomMadeProcessResult processCustomMadeSchemes(
			CustomMadeAvaiableScheme availableScheme,
			BetScheme origalBetScheme, List<CustomMade> customMades) {
		CustomMadeProcessResult processResult=new CustomMadeProcessResult();
		
		// 检查是否可以跟单或者合买
		for (CustomMade customMade : customMades) {
			handleOneCustomMade(origalBetScheme, processResult, customMade);
		}
		customMadeService.updateCustomMadeStatus(availableScheme.getId(), EntityStatus.CUSTOMMADE_STATUS_YES);
		return processResult;
	}

	private void handleOneCustomMade(BetScheme origalBetScheme,
			CustomMadeProcessResult processResult, CustomMade customMade) {
		logger.debug("handleOneCustomMade scheme({}), cunstomMade({})", origalBetScheme.getId(), customMade.getId());
		// 时间检查
		if (createdTimeIsInValid(origalBetScheme, customMade)) {
			logger.debug("Time is expired!");
			return;
		}
		
		//玩法检查
		if(playIdsIsEmpty(customMade)) {
			logger.debug("not plays expired!");
			return;
		}

		//检查playId
		if(!isPlay(customMade, origalBetScheme)) {
			logger.info("No custom plays:origialSchemeId{},userId={}", origalBetScheme.getId(), customMade.getUserId());
			return;
		} 
		
		// 跟单检查,防止级联，即过滤掉非原创方案
		if (origalBetScheme.getFollowedSchemeId() != null
				&& origalBetScheme.getFollowedSchemeId() > 0){
			logger.info("No custom for non-original scheme: {}", origalBetScheme.getId());
			return;
		}
			
		
		// 当日购买金额和最大参与次数检查
		if ((origalBetScheme.getType() == EntityStatus.BETSCHEME_TYPE_BUY ||
				origalBetScheme.getType() == EntityStatus.BETSCHEME_TYPE_GROUPBUY)
				&& origalBetScheme.getShowScheme() == EntityType.SHOW_SCHEME
				&& customMade.isFollowBuy()) {
			
			// 参与跟单，初始化方案信息
			BetScheme betScheme = initBetScheme(origalBetScheme,
					customMade);

			Bet bet = betResolver.resolve(betScheme);

			betScheme.setMaxBonus(new BigDecimal(bet.getMaxBonus()));
			betScheme.setBetNote(bet.getNote());
			betScheme.setBuyAmount(bet.getNote() * 2);
			betScheme.setTotalAmount(bet.getNote() * 2);

			if (customMadeService.checkCustomMadeDaily(customMade.getUserId(), 
					customMade.getFollowedUser().getId(), customMade.getMaxMoney(), 
					bet.getNote()*2, customMade.getMaxFollowCount())) {
				try {
					customMadeService.betFollow(customMade, betScheme, bet);
					processResult.custFollow++;
				} catch(Throwable e) {
					logger.error(e.getMessage(), e);
				}
			} else {
				logger.info("Exceed Follow Count or money: origialSchemeId={}, userId={}", origalBetScheme.getId(), customMade.getUserId());
			}	
		} 
			
		//参与合买
		if (origalBetScheme.getType() == EntityStatus.BETSCHEME_TYPE_GROUPBUY
				&& customMade.isGroupBuy()) {
			// 检查合买方案的进度和剩余金额
			int surplusAmount = origalBetScheme.getTotalAmount()
					- origalBetScheme.getPurchasedAmount();
			if (surplusAmount == 0){
				logger.info("Group buy surplusAmount is: {}", surplusAmount);
				return;
			}
				

			int buyMoney = computeBuyMoney(customMade, surplusAmount);
			
			if (!customMadeService.checkCustomMadeCount(customMade.getUserId(), customMade.getFollowedUser().getId(),
					customMade.getMaxFollowCount())) {
				logger.info("group buy Exceed  Count:origialSchemeId = {},userId={}, follow count={}", origalBetScheme.getId(), customMade.getUserId());
				return;
			}
			
			int buy = customMadeService.checkCustomMadeSum(customMade.getUserId(), customMade.getFollowedUser().getId(),
					customMade.getMaxMoney(), buyMoney);
		
			if(buy  > 0)  {
				// 参与合买
				logger.debug("buy money > 0");
				try {
					customMadeService.purchase(customMade, origalBetScheme.getId(), buy);
					processResult.custGroupBuy++;
				} catch (Throwable exp) {
					logger.error("CustomMade Groupbuy shceme Failed!", exp);
				}
			} else {
				logger.info("group buy money:origialSchemeId{},userId={}, follow count={}", origalBetScheme.getId(), customMade.getUserId());
				return;
			}
		}
	}

	private int computeBuyMoney(CustomMade customMade, int surplusAmount) {
		int buyMoney;
		if (customMade.getGroupMoney() <= surplusAmount) {
			buyMoney = customMade.getGroupMoney();
		} else {
			buyMoney = surplusAmount;
		}
		return buyMoney;
	}

	private BetScheme initBetScheme(BetScheme origalBetScheme,
			CustomMade customMade) {
		BetScheme betScheme = new BetScheme();

		BeanUtils.copyProperties(origalBetScheme, betScheme,
						new String[] { "matchs", "passTypes",
								"userScores", "followSchemes",
								"groupbuyPartners", "play" });

		betScheme.setPassTypes(origalBetScheme.getPassTypes());
		betScheme.setMatchs(origalBetScheme.getMatchs());

		betScheme.setSponsor(customMade.getUsername());
		betScheme.setSponsorId(customMade.getUserId());
		betScheme.setFollowedSchemeId(origalBetScheme.getId());
		betScheme.setId(0);
		betScheme.setShowScheme(EntityType.DONT_SHOW_SCHEME);
		betScheme.setFollowedRatio(0);
		betScheme.setRecommendation(0);
		
		betScheme.setMultiple(customMade.getFollowMultiple());
		return betScheme;
	}

	private boolean isPlay(CustomMade customMade, BetScheme origalBetScheme) {
		boolean isPlay = false;
		String[] playIds = customMade.getPlayIds().split(",");
		if(null!=playIds){
			for(int i = 0; i < playIds.length; i++) {
				if(origalBetScheme.getPlayId().equals(playIds[i])) {
					isPlay = true;
					break;
				}
			}
		}
		return isPlay;
	}

	private boolean playIdsIsEmpty(CustomMade customMade) {
		return customMade.getPlayIds() == null || StringUtils.isEmpty(customMade.getPlayIds());
	}

	private boolean createdTimeIsInValid(BetScheme origalBetScheme,
			CustomMade customMade) {
		return origalBetScheme.getCreatedTime().before(
				customMade.getStartTime())
				|| origalBetScheme.getCreatedTime().after(
						customMade.getEndTime());
	}

	private void updateCustomMadeStatusAndLog(
			CustomMadeAvaiableScheme availableScheme, BetScheme origalBetScheme,String logTemplate) {
		customMadeService.updateCustomMadeStatus(
				availableScheme.getId(),
				EntityStatus.CUSTOMMADE_STATUS_YES);
		logger.debug(logTemplate,
				origalBetScheme.getId());
	}

	private boolean notValidBetScheme(BetScheme origalBetScheme) {
		return !(origalBetScheme.getType() == EntityStatus.BETSCHEME_TYPE_BUY && origalBetScheme
						.getShowScheme() == EntityType.SHOW_SCHEME)
				&& origalBetScheme.getType() != EntityStatus.BETSCHEME_TYPE_GROUPBUY;
	}
}

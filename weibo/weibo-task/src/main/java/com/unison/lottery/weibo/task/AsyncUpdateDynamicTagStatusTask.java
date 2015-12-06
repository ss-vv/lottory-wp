package com.unison.lottery.weibo.task;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.weibo.common.redis.RedisConstant;
import com.unison.lottery.weibo.common.service.TagService;
import com.unison.lottery.weibo.data.WeiboTag;
import com.unison.lottery.weibo.lang.SpecialTag;
import com.unison.lottery.weibo.lang.WeiboType;
import com.unison.thrift.client.bet.BetSchemeClient;
import com.unison.thrift.client.bet.RecoSchemeClient;
import com.unison.thrift.scheme.service.gen.BetSchemeData;
import com.xhcms.commons.job.Job;
import com.xhcms.lottery.commons.data.BetMatch;
import com.xhcms.lottery.commons.data.BetPartner;
import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.data.PlayMatch;
import com.xhcms.lottery.commons.persist.service.BetPartnerService;
import com.xhcms.lottery.commons.persist.service.BetSchemeRecService;
import com.xhcms.lottery.lang.EntityType;
import com.xhcms.lottery.utils.ResultTool;

public class AsyncUpdateDynamicTagStatusTask extends Job {

	private static final Logger log = LoggerFactory
			.getLogger(AsyncUpdateDynamicTagStatusTask.class);

	@Autowired
	private TagService tagService;
	@Autowired
	private BetSchemeClient betSchemeClient;
	@Autowired
	private RecoSchemeClient recoSchemeClient;
	@Autowired
	BetSchemeRecService betSchemeRecService;
	@Autowired
	BetPartnerService betPartnerService;
	
	public AsyncUpdateDynamicTagStatusTask() {
		log.debug("execute AsyncUpdateDynamicTagStatus task...");
	}

	@Override
	protected void execute() {
		try {
			updateDynamicTagStatus();
		} catch (Exception e) {
			log.error("AsyncUpdateDynamicTagStatus error:", e);
			e.printStackTrace();
		}
	}

	protected void updateDynamicTagStatus() {
		log.info("准备更新动态标签列表！");
		
		Set<String> tagIdList = tagService.findDynamicTagList(RedisConstant.N_INFINIT, RedisConstant.INFINIT);
		if(null != tagIdList && tagIdList.size() > 0) {
			for(String tagId : tagIdList) {
				WeiboTag weiboTag = tagService.getById(tagId);
				if(!weiboTag.isFinalStatus() && 
						( weiboTag.getSchemeId() > 0 || weiboTag.getRecSchemeId() > 0)) {
					log.info("准备更新动态标签={},weiboType={}", weiboTag,weiboTag.getWeiboType());
					if(WeiboType.RECOMMEND.getType().equals(weiboTag.getWeiboType())){
						BetSchemeData betScheme = recoSchemeClient.viewScheme(weiboTag.getRecSchemeId());
						if(null != betScheme && betScheme.getSchemeId() > 0) {
							updateTagForRecoScheme(betScheme, tagId);
						}
					} else {
						if(WeiboType.GROUP.getType().equals(weiboTag.getWeiboType())){
							BetSchemeData betScheme = betSchemeClient.getSchemeById(weiboTag.getSchemeId());
							if(null != betScheme && betScheme.getSchemeId() > 0) {
								updateTagForGroupBuySchemeProgress(betScheme, tagId);
							}
						}
						
						BetSchemeData betScheme = betSchemeClient.getSchemeById(weiboTag.getSchemeId());
						if(null != betScheme && betScheme.getSchemeId() > 0) {
							int schemeStatus = betScheme.getStatus();
							int type = betScheme.getType();
							long offtime = betScheme.getOfftime();
							double bonus = 0.0d;
							if(betScheme.getType() == EntityType.BETTING_PARTNER) {
								BetPartner bp = betPartnerService.findById(weiboTag.getBetRecordId());
								if(null != bp && bp.getWinAmount() != null) {
									bonus = bp.getWinAmount().doubleValue();
								}
							} else {
								bonus = betScheme.getBonus();
							}
							tagService.updateTagForSchemeStatus(schemeStatus, type, offtime, bonus, tagId);
						}
					}
				} else {
					tagService.removeTagToDynamicList(tagId);
				}
			}
		}
	}
	
	private void updateTagForGroupBuySchemeProgress(BetSchemeData betScheme,
			String tagId) {
		double totalAmount = betScheme.getTotalAmount();
		double purchasedAmount = betScheme.getPurchasedAmount();
		tagService.updateGroupProgress(Long.valueOf(tagId), totalAmount, purchasedAmount);
	}

	/**
	 * 更新与推荐相关的标签状态
	 * @param betScheme
	 * @param tagId
	 */
	protected void updateTagForRecoScheme(BetSchemeData betScheme, String tagId) {
		BetScheme scheme = betSchemeRecService.viewRecScheme(betScheme.getSchemeId());
		
		if(null == scheme || scheme.getMatchs() == null) {
			log.debug("找不到方案对象，betScheme={}", betScheme);
			return;
		}
		
		int totalCount = scheme.getMatchs().size();
		int guodanCount = 0;
		int overCount = 0;
		for (BetMatch m : scheme.getMatchs()) {
			PlayMatch p = (PlayMatch)m;
			if(ResultTool.isMatchWin(p, scheme)){
				guodanCount ++;
			}
			if(p.getStatus() == 5){
				overCount ++;
			}
		}
		if(overCount == totalCount){
			int guodanlv = guodanCount * 100 / totalCount;
			Map<String, String> hash = new HashMap<String, String>();
			hash.put("name", SpecialTag.REC_HIT_RATE.getTagName());
			hash.put("value", ":" + guodanlv + "%");
			hash.put("isFinalStatus", "true");
			tagService.editTag(tagId, hash);
		}
	}
}
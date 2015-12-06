package com.unison.lottery.weibo.common.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unison.lottery.weibo.common.nosql.TagDao;
import com.unison.lottery.weibo.common.nosql.impl.Keys;
import com.unison.lottery.weibo.common.service.SchemeToWeiboService;
import com.unison.lottery.weibo.common.service.TagService;
import com.unison.lottery.weibo.data.WeiboTag;
import com.unison.lottery.weibo.lang.SpecialTag;
import com.unison.lottery.weibo.lang.WeiboType;
import com.xhcms.lottery.commons.data.BetPartner;
import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.lang.EntityStatus;
import com.xhcms.lottery.lang.LotteryId;

@Service
public class TagServiceImpl implements TagService {

	private Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private TagDao tagDao;
	@Autowired
    private SchemeToWeiboService schemeToWeiboService;
	
	@Override
	public WeiboTag getById(String tagId) {
		WeiboTag tag = tagDao.hashGet(Keys.tag(tagId));
		return tag;
	}
	
	@Override
	public WeiboTag exists(WeiboTag weiboTag) {
		if(null != weiboTag) {
			WeiboTag tag = tagDao.findByName(weiboTag.getName());
			if(null != tag && tag.getId() > 0) {
				return tag;
			}
		}
		return null;
	}

	@Override
	public WeiboTag addTag(WeiboTag weiboTag) {
		if(null == weiboTag || StringUtils.isBlank(weiboTag.getName())) {
			log.error("标签名称为空!无法加入标签；WeiboTag={}", weiboTag);
			return null;
		}
		WeiboTag findTag = exists(weiboTag);
		if(null == findTag) {
			tagDao.create(weiboTag);
			log.info("加入标签：ID={}, 名称={}, 是否动态={}, 方案ID={}", new Object[]{
					weiboTag.getId(), weiboTag.getName(), 
					weiboTag.isDynamic(), weiboTag.getSchemeId()
			});
			if(!weiboTag.isDynamic()) {
				String tagNameKey = Keys.tagNameKey(weiboTag.getName());
				tagDao.set(tagNameKey, weiboTag.getId()+"");
				log.info("创建标签名称索引：名称={}, 标签ID={}", new Object[]{
						weiboTag.getName(), weiboTag.getId()});
			} else {
				log.info("加入到动态标签列表, 标签={}.", new Object[]{weiboTag});
				tagDao.addTagToDynamicList(weiboTag);
			}
		} else {
			weiboTag = findTag;
			log.info("标签已存在！名称={}", weiboTag.getName());
		}
		return weiboTag;
	}

	@Override
	public WeiboTag createTagByLottery(String lotteryId) {
		long now = new Date().getTime();
		String color = SpecialTag.COLOR;
		String bgColor = "";
		
		WeiboTag lotteryTag = new WeiboTag();
		lotteryTag.setIndex(0);
		String lotteryName = "";
		if(LotteryId.JCZQ.name().equals(lotteryId)) {
			lotteryName = SpecialTag.JCZQ.getTagName();
			bgColor = SpecialTag.JCZQ.getBgcolor();
		} else if(LotteryId.JCLQ.name().equals(lotteryId)) {
			lotteryName = SpecialTag.JCLQ.getTagName();
			bgColor = SpecialTag.JCLQ.getBgcolor();
		} else if(LotteryId.CTZC.name().equals(lotteryId)) {
			lotteryName = SpecialTag.CTZC.getTagName();
		} else if(LotteryId.BJDC.name().equals(lotteryId)) {
			lotteryName = SpecialTag.BJDC.getTagName();
			bgColor = SpecialTag.BJDC.getBgcolor();
		}
		
		lotteryTag.setName(lotteryName);
		lotteryTag.setColor(color);
		lotteryTag.setBgcolor(bgColor);
		lotteryTag.setCreateTime(now);
		
		return addTag(lotteryTag);
	}

	@Override
	public WeiboTag createTagByWeiboType(String type) {
		log.info("根据微博类型={}，创建标签", type);
		
		WeiboTag schemeTypeTag = new WeiboTag();
		String tagName = WeiboType.getByValue(type);
		long now = new Date().getTime();
		String color = SpecialTag.COLOR;
		String bgColor = null;
		if(WeiboType.SHOW_SCHEME.getType().equals(type)) {
			bgColor = SpecialTag.SHOW.getBgcolor();
		} else if(WeiboType.GROUP.getType().equals(type)) {
			bgColor = SpecialTag.GROUP.getBgcolor();
		} else if(WeiboType.RECOMMEND.getType().equals(type)) {
			bgColor = SpecialTag.RECOMMEND.getBgcolor();
		} else if(WeiboType.FOLLOW.getType().equals(type)) {
			bgColor = SpecialTag.FOLLOW.getBgcolor();
		}
		
		schemeTypeTag.setIndex(1);
		schemeTypeTag.setName(tagName);
		schemeTypeTag.setColor(color);
		schemeTypeTag.setBgcolor(bgColor);
		schemeTypeTag.setCreateTime(now);
		
		return addTag(schemeTypeTag);
	}

	@Override
	public WeiboTag createTagBySchemeStatus(String weiboType, BetPartner betPartner) {
		BetScheme bs = betPartner.getBetScheme();
		long betRecordId = 0L;
		if(!WeiboType.RECOMMEND.getType().equals(weiboType) &&
				betPartner.getId() != null && betPartner.getId() > 0) {
			betRecordId = betPartner.getId();
		}
		long schemeId = bs.getId();
		long offtime = 0;
		if(bs.getOfftime() != null) {
			offtime = bs.getOfftime().getTime();
		}
		int status = bs.getStatus();
		double totalAmount = bs.getTotalAmount();
		double purchasedAmount = bs.getPurchasedAmount();
		double bonus = 0;
		if(WeiboType.GROUP.getName().equals(weiboType)) {
			bonus = betPartner.getWinAmount().doubleValue();
		} else {
			bonus = bs.getAfterTaxBonus().doubleValue();
		}
		log.info("根据方案信息创建标签：方案ID={}, 投注记录ID={}, 停售时间={}, "
				+ "方案状态={}, 奖金={}", new Object[]{
				schemeId, betRecordId, new Date(offtime), status, bonus
		});
		long now = new Date().getTime();
		String color = SpecialTag.COLOR;
		
		WeiboTag schemeStatusTag = new WeiboTag();
		
		schemeStatusTag.setColor(color);
		schemeStatusTag.setCreateTime(now);
		schemeStatusTag.setIndex(2);
		schemeStatusTag.setWeiboType(weiboType);
		schemeStatusTag.setBetRecordId(betRecordId);
		if(now >= offtime && offtime > 0) {
			switch (status) {
			case EntityStatus.TICKET_INIT:
			case EntityStatus.TICKET_ALLOW_BUY:
			case EntityStatus.TICKET_REQUIRED:
			case EntityStatus.TICKET_BUY_SUCCESS:
			case EntityStatus.TICKET_BUY_FAIL:
				schemeStatusTag.setSchemeId(schemeId);
				schemeStatusTag.setName(SpecialTag.END.getTagName());
				schemeStatusTag.setBgcolor(SpecialTag.END.getBgcolor());
				schemeStatusTag.setDynamic(true);
				schemeStatusTag.setFinalStatus(false);
				break;
			case EntityStatus.TICKET_NOT_WIN:
				schemeStatusTag.setName(SpecialTag.NOT_WIN.getTagName());
				schemeStatusTag.setBgcolor(SpecialTag.NOT_WIN.getBgcolor());
				schemeStatusTag.setFinalStatus(true);
				break;
			case EntityStatus.TICKET_NOT_AWARD:
			case EntityStatus.TICKET_AWARDED:
				schemeStatusTag.setName("奖金:" + bonus + "元");
				schemeStatusTag.setBgcolor(SpecialTag.AWARDED.getBgcolor());
				schemeStatusTag.setFinalStatus(true);
				break;
			case EntityStatus.TICKET_SCHEME_FLOW:
				schemeStatusTag.setName(SpecialTag.FLOW.getTagName());
				schemeStatusTag.setBgcolor(SpecialTag.FLOW.getBgcolor());
				schemeStatusTag.setFinalStatus(true);
				break;
			case EntityStatus.TICKET_SCHEME_CANCEL:
				schemeStatusTag.setName(SpecialTag.CANCEL.getTagName());
				schemeStatusTag.setBgcolor(SpecialTag.CANCEL.getBgcolor());
				schemeStatusTag.setFinalStatus(true);
				break;
			}
		} else {
			if(WeiboType.GROUP.getType().equals(weiboType)) {
				schemeStatusTag.setName(SpecialTag.PROGRESS.getTagName());
				schemeStatusTag.setSchemeId(schemeId);
				schemeStatusTag.setBgcolor(SpecialTag.PROGRESS.getBgcolor());
				schemeStatusTag.setDynamic(true);
				schemeStatusTag.setFinalStatus(false);
			} else if(WeiboType.SHOW_SCHEME.getType().equals(weiboType) ||
					WeiboType.FOLLOW.getType().equals(weiboType) ) {
				schemeStatusTag.setName(SpecialTag.SALING.getTagName());
				schemeStatusTag.setSchemeId(schemeId);
				schemeStatusTag.setBgcolor(SpecialTag.SALING.getBgcolor());
				schemeStatusTag.setDynamic(true);
			} else if(WeiboType.RECOMMEND.getType().equals(weiboType)) {
				schemeStatusTag.setName("过单率");
				schemeStatusTag.setRecSchemeId(schemeId);
				schemeStatusTag.setBgcolor(SpecialTag.REC_HIT_RATE.getBgcolor());
				schemeStatusTag.setDynamic(true);
			}
		}
		
		WeiboTag tag = addTag(schemeStatusTag);
		if(WeiboType.GROUP.getType().equals(weiboType)) {
			updateGroupProgress(tag.getId(), totalAmount, purchasedAmount);
		}
		return tag;
	}
	
	@Override
	public Set<String> findDynamicTagList(String min, String max) {
		return tagDao.findDynamicTagList(min, max);
	}

	@Override
	public Long removeTagToDynamicList(String tagId) {
		return tagDao.removeTagToDynamicList(tagId);
	}

	@Override
	public String editTag(String tagId, Map<String, String> hash) {
		if(StringUtils.isNotBlank(tagId)) {
			log.info("更新标签ID={}, 准备更新的字段={}", tagId, hash);
			String rs = tagDao.hmset(Keys.tag(tagId), hash);
			
			if("true".equals(hash.get("isFinalStatus"))) {
				removeTagToDynamicList(tagId);
			}
			return rs;
		}
		return null;
	}

	@Override
	public List<WeiboTag> getTagListByWeiboId(long weiboId) {
		return tagDao.getTagListByWeiboId(weiboId);
	}

	@Override
	public void updateWeiboTag(long postId, String sourceTagName, SpecialTag destTag) {
		List<WeiboTag> weiboTags = this.getTagListByWeiboId(postId);
		if(null != weiboTags && weiboTags.size() > 0 && 
				StringUtils.isNotBlank(sourceTagName)) {
			for(WeiboTag weiboTag : weiboTags) {
				Map<String, String> hash = new HashMap<String, String>();
				if(sourceTagName.equals(weiboTag.getName())) {
					hash.put("name", destTag.getTagName());
					if(StringUtils.isNotBlank(weiboTag.getValue())) {
						hash.put("value", weiboTag.getValue());
					}
					hash.put("bgcolor", destTag.getBgcolor());
					hash.put("isFinalStatus", weiboTag.isFinalStatus()+"");
					this.editTag(weiboTag.getId()+"", hash);
					break;
				}
			}
		}
	}

	@Override
	public void updateGroupProgress(long tagId, double totalAmount,
			double purchasedAmount) {
		BigDecimal purchased = new BigDecimal(purchasedAmount).multiply(new BigDecimal(100));
		BigDecimal progress = purchased.divide(new BigDecimal(totalAmount), RoundingMode.DOWN);
		Map<String, String> hash = new HashMap<String, String>();
		hash.put("name", SpecialTag.PROGRESS.getTagName());
		hash.put("value", progress + "%");
		hash.put("isFinalStatus", "false");
		hash.put("isDynamic", "true");
		String result = editTag(tagId + "", hash);
		log.info("更新进度标签ID:{}, hash={}, 更新结果:{}", new Object[]{tagId, hash, result});
	}

	@Override
	public void updateGroupProgressByGroupScheme(long schemeId, double totalAmount,
			double purchasedAmount) {
		long postId = schemeToWeiboService.getWeiboIdByShowSchemeId(schemeId);
		List<WeiboTag> tags = schemeToWeiboService.getWeiboTagByPostId(postId);
    	if(null != tags && tags.size() > 0) {
    		for(WeiboTag weiboTag : tags) {
    			String tagName = weiboTag.getName();
    			if(StringUtils.isNotBlank(tagName) && 
    					tagName.indexOf(SpecialTag.PROGRESS.getTagName()) >= 0) {
    				this.updateGroupProgress(weiboTag.getId(), totalAmount, purchasedAmount);
    			}
    		}
    	}
	}

	@Override
	public void updateTagForSchemeStatus(int schemeStatus, int type,
			long offtime, double bonus, String tagId) {
		Map<String, String> hash = new HashMap<String, String>();
		int status = schemeStatus;
		long now = new Date().getTime();
		if(now >= offtime) {
			switch (status) {
			case EntityStatus.TICKET_INIT:
			case EntityStatus.TICKET_ALLOW_BUY:
			case EntityStatus.TICKET_REQUIRED:
			case EntityStatus.TICKET_BUY_SUCCESS:
			case EntityStatus.TICKET_BUY_FAIL:
				hash.put("name", SpecialTag.END.getTagName());
				hash.put("value", "");
				break;
			case EntityStatus.TICKET_NOT_WIN:
				hash.put("name", SpecialTag.NOT_WIN.getTagName());
				hash.put("value", "");
				hash.put("isFinalStatus", "true");
				break;
			case EntityStatus.TICKET_NOT_AWARD:
			case EntityStatus.TICKET_AWARDED:
				hash.put("name", SpecialTag.AWARDED.getTagName());
				hash.put("value", ": " + bonus + "元");
				hash.put("bgcolor", SpecialTag.AWARDED.getBgcolor());
				hash.put("isFinalStatus", "true");
				break;
			case EntityStatus.TICKET_SCHEME_FLOW:
				hash.put("name", SpecialTag.FLOW.getTagName());
				hash.put("value", "");
				hash.put("isFinalStatus", "true");
				break;
			case EntityStatus.TICKET_SCHEME_CANCEL:
				hash.put("name", SpecialTag.CANCEL.getTagName());
				hash.put("value", "");
				hash.put("isFinalStatus", "true");
				break;
			}
		}
		
		if(hash.size() > 0) {
			this.editTag(tagId, hash);
		}
	}
}
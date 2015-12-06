package com.unison.lottery.pm.service.impl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.unison.lottery.pm.data.PromotionWinResult;
import com.unison.lottery.pm.entity.WinGrantPO;
import com.unison.lottery.pm.lang.WinGrant;
import com.unison.lottery.pm.persist.PMBetSchemeDao;
import com.unison.lottery.pm.persist.WinGrantDao;
import com.unison.lottery.pm.service.PromotionService;
import com.xhcms.commons.lang.Paging;
import com.xhcms.lottery.commons.data.Promotion;
import com.xhcms.lottery.commons.persist.dao.BetSchemeDao;
import com.xhcms.lottery.commons.persist.dao.GrantDao;
import com.xhcms.lottery.commons.persist.dao.PMPromotionDao;
import com.xhcms.lottery.commons.persist.dao.PromotionDetailDao;
import com.xhcms.lottery.commons.persist.dao.VoucherUserDao;
import com.xhcms.lottery.commons.persist.entity.BetSchemePO;
import com.xhcms.lottery.commons.persist.entity.GrantPO;
import com.xhcms.lottery.commons.persist.entity.PromotionDetailPO;
import com.xhcms.lottery.commons.persist.entity.PromotionPO;
import com.xhcms.lottery.commons.persist.service.VoucherUserService;
import com.xhcms.lottery.lang.Constants;
import com.xhcms.lottery.lang.EntityStatus;
import com.xhcms.lottery.lang.VoucherGrant;

/**
 * 活动管理service
 * @author Wang Lei
 * 
 */
public class PromotionServiceImpl implements PromotionService {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	VoucherUserDao voucherUserDao;
	@Autowired
	VoucherUserService voucherUserService;
	@Autowired
	PMBetSchemeDao pMBetSchemeDao;
	@Autowired
	WinGrantDao winGrantDao;
	@Autowired
	PMPromotionDao pMPromotionDao;
	@Autowired
	GrantDao grantDao;
	@Autowired
	BetSchemeDao betSchemeDao;
	@Autowired
	PromotionDetailDao promotionDetailDao;
	
	@Override
	@Transactional(readOnly=true)
	public List<Long> findBasketballPromotionSchemeIds(Long promotionId){
		if(promotionId==null){
			throw new RuntimeException("查询篮彩奖上奖活动条件方案失败！活动ID="+promotionId+"不存在！");
		}
		PromotionPO  promotionPO=pMPromotionDao.get(promotionId);
		if(promotionPO ==null){
			throw new RuntimeException("查询篮彩奖上奖活动条件方案失败！活动不存在！");
		}
		return pMBetSchemeDao.findBasketballPromotionSchemes(promotionPO, EntityStatus.TICKET_AWARDED);
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<Long> findFootballPromotionSchemeIds(Long promotionId){
		if(promotionId==null){
			throw new RuntimeException("查询足彩奖上奖活动条件方案失败！活动ID="+promotionId+"不存在！");
		}
		PromotionPO  promotionPO=pMPromotionDao.get(promotionId);
		if(promotionPO ==null){
			throw new RuntimeException("查询足彩奖上奖活动条件方案失败！活动不存在！");
		}
		return pMBetSchemeDao.findFootballPromotionSchemes(promotionPO, EntityStatus.TICKET_AWARDED);
	}

	@Override
	@Transactional(readOnly=true)
	public List<Long> findNotWin2C1PromotionSchemeIds(Long promotionId) {
		if(promotionId==null){
			throw new RuntimeException("查询活动信息失败！活动ID="+promotionId+"不存在！");
		}
		PromotionPO  promotionPO=pMPromotionDao.get(promotionId);
		if(promotionPO ==null){
			throw new RuntimeException("查询活动信息失败失败！活动不存在！");
		}
		return pMBetSchemeDao.findPromotionSchemeIds(promotionPO, EntityStatus.TICKET_NOT_WIN,",2@1,",100,null);
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<Long> findWin2C1PromotionSchemeIds(Long promotionId) {
		if(promotionId==null){
			throw new RuntimeException("查询活动信息失败！活动ID="+promotionId+"不存在！");
		}
		PromotionPO  promotionPO=pMPromotionDao.get(promotionId);
		if(promotionPO ==null){
			throw new RuntimeException("查询活动信息失败失败！活动不存在！");
		}
		return pMBetSchemeDao.findPromotionSchemeIds(promotionPO, EntityStatus.TICKET_AWARDED,",2@1,",null,100);
	}
	
	@Override
	@Transactional
	/**
	 * 奖上奖活动条件方案，插入活动子表。
	 */
	public void insertWingrantFromPromotion(Long schemeId,PromotionPO  promotionPO) {
		if(schemeId!=null){
			BetSchemePO schemePO = betSchemeDao.get(schemeId);
			winGrantDao.save(schemeCopyToWingrant(schemePO,promotionPO));
		}
	}
	
	/**
	 * 组合活动子表数据
	 * @param scheme
	 * @param promotionPO
	 * @return
	 */
	private WinGrantPO schemeCopyToWingrant(BetSchemePO scheme,PromotionPO  promotionPO){
		WinGrantPO wingrant=new WinGrantPO();
		wingrant.setUserId(scheme.getSponsorId());
		wingrant.setUsername(scheme.getSponsor());
		wingrant.setScheme(scheme);
		Date date=new Date();
		wingrant.setGrantTime(date);
		wingrant.setOperatorId(WinGrant.operatorId);
		wingrant.setOperatorTime(date);
		wingrant.setStatus(WinGrant.submitStatus.unSubmit);
		wingrant.setPromotionId(promotionPO.getId());
		wingrant.setGranttypeId(promotionPO.getGrantTypeId());
		wingrant.setAmount(calculateGrantAmount(scheme, promotionPO));
		return wingrant;
	}
	
	/**
	 * 生成活动赠款记录
	 * @param scheme
	 * @param promotion
	 * @return
	 */
	private WinGrantPO schemeCopyToWingrant(BetSchemePO scheme,Promotion  promotion){
		WinGrantPO wingrant=new WinGrantPO();
		wingrant.setUserId(scheme.getSponsorId());
		wingrant.setUsername(scheme.getSponsor());
		wingrant.setScheme(scheme);
		Date date=new Date();
		wingrant.setGrantTime(date);
		wingrant.setOperatorId(WinGrant.operatorId);
		wingrant.setOperatorTime(date);
		wingrant.setStatus(WinGrant.submitStatus.unSubmit);
		wingrant.setPromotionId(promotion.getId());
		wingrant.setGranttypeId(promotion.getGrantTypeId());
		wingrant.setAmount(calculateGrantAmount(scheme, promotion));
		return wingrant;
	}
	
	/**
	 * 匹配活动赠款逻辑
	 * @param scheme
	 * @param promotion
	 * @return
	 */
	private BigDecimal calculateGrantAmount(BetSchemePO scheme,Promotion  promotion){
		if(null == scheme){
			logger.error("方案详情不能为空！");
			throw new RuntimeException("方案详情不能为空！");
		}
		Long promotionId = promotion.getId();
		List<PromotionDetailPO> pdPOs = promotionDetailDao.findListByPromotionId(promotionId);
		if(null == pdPOs || pdPOs.isEmpty()){
			logger.error("未找到活动详情！活动id="+promotionId);
			throw new RuntimeException("未找到活动详情！活动id="+promotionId);
		}
		BigDecimal buyAmount = new BigDecimal(promotion.getBuyAmount());
		BigDecimal money = buyAmount.compareTo(BigDecimal.ZERO)==0?scheme.getAfterTaxBonus():new BigDecimal(scheme.getBuyAmount());
		for(PromotionDetailPO pdPO:pdPOs){
			// 匹配活动详情表的过关方式
			boolean pass = checkPassType(scheme.getPassTypeIds(),pdPO.getPassTypeIdsLogic(),pdPO.getPassTypeIds());
			// 匹配最小金额（大于等于）
			boolean minRange = money.compareTo(pdPO.getMinValue())==0 || money.compareTo(pdPO.getMinValue())==1;
			// 是否不限最大金额
			boolean maxRange = pdPO.getMaxValue()==null ? true : (money.compareTo(pdPO.getMaxValue())==-1);
			
			// 符合规则 计算赠款
			if(pass && maxRange &&  minRange ) {
				BigDecimal grant = new BigDecimal(0);
				if(null == pdPO.getGrant()){
					grant = money.multiply(new BigDecimal(pdPO.getPercent()*0.01));
				}else{
					grant = pdPO.getGrant();
				}
				return grant;
			}
		}
		logger.error("方案内容未匹配！活动id="+promotionId+" 方案id="+scheme.getId());
		throw new RuntimeException("方案内容未匹配！活动id="+promotionId+" 方案id="+scheme.getId());
	}
	
	private boolean checkPassType(String passTypes,String logic,String passTypeIds){
		if(StringUtils.isBlank(passTypes) || StringUtils.isBlank(logic)  || StringUtils.isBlank(passTypeIds)){
			return false;
		}
		logic = logic.trim().toUpperCase(); passTypeIds = passTypeIds.trim();
		if("EQ".equals(logic) && passTypes.equals(passTypeIds)){
			return true;
		}else if("NE".equals(logic) && !passTypes.equals(passTypeIds)){
			return true;
		}else if("IN".equals(logic)){
			throw new RuntimeException("没有匹配IN逻辑！");
		}else{
			return false;
		}
	}
	
	/**
	 * 计算赠款
	 * @param scheme
	 * @param promotionPO
	 * @return
	 */
	private BigDecimal calculateGrantAmount(BetSchemePO scheme,PromotionPO  promotionPO ){
		BigDecimal bonus=scheme.getAfterTaxBonus();
		int buyAmount=scheme.getBuyAmount();
		if(promotionPO.getId()==WinGrant.promotion.jclc_2012_8 || promotionPO.getId()==WinGrant.promotion.jczc_2012_8
				|| promotionPO.getId()==WinGrant.promotion.jczc_2012_11_2C1_Win || promotionPO.getId()==WinGrant.promotion.jclc_2012_11_2C1_Win ){
			if(bonus.compareTo(new BigDecimal(100))>=0 && bonus.compareTo(new BigDecimal(500))<0){
				return new BigDecimal(8);
			}else if(bonus.compareTo(new BigDecimal(500))>=0 && bonus.compareTo(new BigDecimal(1000))<0){
				return new BigDecimal(28);
			}else if(bonus.compareTo(new BigDecimal(1000))>=0 && bonus.compareTo(new BigDecimal(5000))<0){
				return new BigDecimal(88);
			}else if(bonus.compareTo(new BigDecimal(5000))>=0 && bonus.compareTo(new BigDecimal(10000))<0){
				return new BigDecimal(288);
			}else if(bonus.compareTo(new BigDecimal(10000))>=0 && bonus.compareTo(new BigDecimal(50000))<0){
				return new BigDecimal(888);
			}else if(bonus.compareTo(new BigDecimal(50000))>=0 && bonus.compareTo(new BigDecimal(100000))<0){
				return new BigDecimal(2888);
			}else if(bonus.compareTo(new BigDecimal(100000))>=0){
				return new BigDecimal(8888);
			}else{
				throw new RuntimeException("winGrant Error! schemeId="+scheme.getId()+" AfterTaxBonus="+bonus);
			}
		}else if(promotionPO.getId() == WinGrant.promotion.jczc_2012_10_11_2C1_notWin || promotionPO.getId() == WinGrant.promotion.jclc_2012_11_2C1_notWin
				|| promotionPO.getId() == WinGrant.promotion.jczc_2012_12_2C1_notWin || promotionPO.getId() == WinGrant.promotion.jclc_2012_12_2C1_notWin){
			if(buyAmount>=100 && buyAmount<500){
				return new BigDecimal(buyAmount).multiply(new BigDecimal(0.04));
			}else if(buyAmount>=500 && buyAmount<1000){
				return new BigDecimal(buyAmount).multiply(new BigDecimal(0.06));
			}else if(buyAmount>=1000 && buyAmount<5000){
				return new BigDecimal(buyAmount).multiply(new BigDecimal(0.08));
			}else if(buyAmount>=5000 && buyAmount<10000){
				return new BigDecimal(buyAmount).multiply(new BigDecimal(0.10));
			}else if(buyAmount>=10000){
				return new BigDecimal(buyAmount).multiply(new BigDecimal(0.12));
			}else{
				throw new RuntimeException("winGrant Error! schemeId="+scheme.getId()+" buyAmount="+buyAmount);
			}
		}
		else{
			return bonus.multiply(WinGrant.grantAmount);
		}
	}

	@Override
	@Transactional(readOnly=true)
	public List<PromotionWinResult> findPromotionWinTOP(int top,Long pomotionId) {
		return winGrantDao.findPromotionWinTOP(top,pomotionId);
	}

	@Override
	@Transactional(readOnly=true)
	public PromotionPO getPromotionById(Long promotionId) {
		PromotionPO promotionPO = pMPromotionDao.get(promotionId);
		return promotionPO;
	}
	
	@Override
	@Transactional(readOnly=true)
	public Promotion getPromotionVOById(Long promotionId) {
		Promotion promotion =  null;
		PromotionPO promotionPO = pMPromotionDao.get(promotionId);
		if(null != promotionPO){
			promotion = new Promotion();
			BeanUtils.copyProperties(promotionPO, promotion);
		}
		return promotion;
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<WinGrantPO> listWinGrantPO(Paging paging, Date from, Date to,
			int status,WinGrantPO winGrant,String lottertId) {
		List<Long> ls=pMPromotionDao.getPromotionIdsByLotteryId(lottertId);
		HashSet<Long> set = new HashSet<Long>(ls);
		return winGrantDao.findWinGrantList(paging, status, from, to,winGrant,set.isEmpty()==true?null:set);
	}

	@Override
	@Transactional
	public void  sponsorGrants(Collection<Long> ids, int operatorid,String operatorName) {
		List<WinGrantPO> winGrantList=winGrantDao.find(ids);
		for(WinGrantPO winGrant:winGrantList){
			// 获取活动信息
			PromotionPO ppo = pMPromotionDao.get(winGrant.getPromotionId());
			// 检查用户是否到达最大活动次数
			boolean allowInsert=allowInsert(ppo,winGrant.getUserId());
			//活动子表当前状态是未处理并且允许插入为true时才向赠款表插入数据
			if(winGrant.getStatus()==WinGrant.submitStatus.unSubmit){
				insertGrant(winGrant, operatorid, operatorName, ppo,allowInsert);
			}
		}
	}
	
	private boolean allowInsert(PromotionPO ppo,Long userId){
		switch(ppo.getJoinCountType()){
		case Constants.NOT_COMPUTE:
			return true;
		case Constants.EVERYDAY:
			Calendar startTime = Calendar.getInstance();
			Calendar endTime = Calendar.getInstance();
			makeDate(startTime, endTime);
			Set<Long> grantTypes = new HashSet<Long>();
			for(String pmId:ppo.getCountRelevanceGranTypeIds().split(",")){
				grantTypes.add(Long.parseLong(pmId));
			}
			int grantCount=grantDao.getGrantCountByGrantTypeIdsAndTime(grantTypes,userId ,startTime.getTime(), endTime.getTime());
			if(grantCount>0){
				System.err.println("");
			}
			return ppo.getEveryoneJoinCount()>grantCount;
		case Constants.EVERYMONTH:
			throw new RuntimeException("没有赠款按月计数逻辑！");
		default:
			return true;
		}
	}
	
	private boolean allowCreate(PromotionPO ppo,Long userId){
		switch(ppo.getJoinCountType()){
		case Constants.NOT_COMPUTE:
			return true;
		case Constants.EVERYDAY:
			Calendar startTime = Calendar.getInstance();
			Calendar endTime = Calendar.getInstance();
			makeDate(startTime, endTime);
			Set<Long> grantTypes = new HashSet<Long>();
			for(String pmId:ppo.getCountRelevanceGranTypeIds().split(",")){
				grantTypes.add(Long.parseLong(pmId));
			}
			int grantCount=voucherUserDao.getVoucherCountByGrantTypeIdsAndTime(grantTypes,userId ,startTime.getTime(), endTime.getTime());
			if(grantCount>0){
				System.err.println("");
			}
			return ppo.getEveryoneJoinCount()>grantCount;
		case Constants.EVERYMONTH:
			throw new RuntimeException("没有赠款按月计数逻辑！");
		default:
			return true;
		}
	}
	
	@Override
	@Transactional
	public void autoSponsorAndCreateVouchers(Long winGrantId, PromotionPO ppo){
		WinGrantPO winGrant=winGrantDao.get(winGrantId);

		// 检查用户是否到达最大活动次数
		boolean allowInsert=allowCreate(ppo,winGrant.getUserId());
		//活动子表当前状态是未处理并且允许插入为true时才赠送优惠劵
		if(winGrant.getStatus()==WinGrant.submitStatus.unSubmit){
			winGrant.setStatus(allowInsert==true?WinGrant.submitStatus.submited:WinGrant.submitStatus.submited_out_count);
			winGrant.setOperatorId(1);
			winGrant.setOperatorName("root");
			winGrant.setOperatorTime(new Date());
			winGrantDao.update(winGrant);
			if(allowInsert){
				voucherUserService.createVoucher(winGrant.getUserId(), 30L, VoucherGrant.voucherGrantMap.get(winGrant.getAmount().intValue()), ppo.getGrantTypeId());
			}
		}
	}
	
	private void insertGrant(WinGrantPO winGrant,int operatorid,String operatorName,PromotionPO ppo,boolean allowInsert){
		winGrant.setStatus(allowInsert==true?WinGrant.submitStatus.submited:WinGrant.submitStatus.submited_out_count);
		winGrant.setOperatorId(operatorid);
		winGrant.setOperatorName(operatorName);
		winGrant.setOperatorTime(new Date());
		winGrantDao.update(winGrant);
		if(allowInsert){
			GrantPO grant= new GrantPO();
			grant.setUserId(winGrant.getUserId());
			grant.setOrderId(winGrant.getScheme().getId());
			grant.setAmount(winGrant.getAmount());
			grant.setCreatedTime(new Date());
			grant.setOperatorId(operatorid);
			grant.setStatus(EntityStatus.GRANT_INIT);
			
			grant.setNote(ppo.getName());
			grant.setGrantTypeId(ppo.getGrantTypeId());
		
			grantDao.save(grant);
		}
	}

	private void makeDate(Calendar startTime,Calendar endTime){
		Date now = new Date();
		startTime.setTime(now);
		startTime.set(Calendar.HOUR_OF_DAY,0);
		startTime.set(Calendar.MINUTE,0);
		startTime.set(Calendar.SECOND,0);
		endTime.setTime(now);
		endTime.set(Calendar.HOUR_OF_DAY,23);
		endTime.set(Calendar.MINUTE,59);
		endTime.set(Calendar.SECOND,59);
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<PromotionWinResult> findWinTOP(int top, Date startTime,
			Date endTime, String lotteryId,int status) {
		return pMBetSchemeDao.findWinTOP(top, startTime, endTime, lotteryId,status);
	}

	@Override
	@Transactional(readOnly=true)
	public List<Long> findSchemeIdsByPromotion(Promotion promotion) {
		return pMBetSchemeDao.findSchemeIdsByPromtion(promotion);
	}

	@Override
	@Transactional
	public void insertWingrantFromPromotion(Long schemeId, Promotion promotion) {
		if(schemeId!=null){
			BetSchemePO schemePO = betSchemeDao.get(schemeId);
			winGrantDao.save(schemeCopyToWingrant(schemePO,promotion));
		}
	}

}

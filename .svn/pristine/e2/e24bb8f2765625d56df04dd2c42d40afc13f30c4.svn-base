package com.unison.lottery.pm.persist.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.unison.lottery.pm.data.PromotionWinResult;
import com.unison.lottery.pm.persist.PMBetSchemeDao;
import com.xhcms.lottery.commons.data.Promotion;
import com.xhcms.lottery.commons.persist.dao.impl.BetSchemeDaoImpl;
import com.xhcms.lottery.commons.persist.entity.PromotionPO;
import com.xhcms.lottery.lang.Constants;
import com.xhcms.lottery.lang.EntityStatus;
import com.xhcms.lottery.lang.EntityType;
import com.xhcms.lottery.utils.TextTool;

/**
 * @author Wang Lei
 * 
 */
public class PMBetSchemeDaoImpl extends BetSchemeDaoImpl implements
		PMBetSchemeDao  {

	private static final long serialVersionUID = 1669753507606979068L;
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Long> findFootballPromotionSchemes(PromotionPO promotion, int status){
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			logger.info(promotion.getName()+"查询时间范围:{}至:{} 。",sdf.format(promotion.getStartTime()),sdf.format(promotion.getEndTime()));
			List<Long> ids=createQuery("select s.id from WinGrantPO as w RIGHT JOIN w.scheme as s where" +
					" s.createdTime>? AND s.createdTime<? AND s.status=? AND s.playId IN (" +
					"'" + Constants.PLAY_01_ZC_2 +"'," +
					"'" + Constants.PLAY_02_ZC_2 + "'," +
					"'" + Constants.PLAY_03_ZC_2 + "'," +
					"'" + Constants.PLAY_04_ZC_2 +"') AND" +
					" s.type in ("+EntityType.BETTING_ALONE + ","+ EntityType.BETTING_FOLLOW +") AND s.afterTaxBonus>=100 AND w.id IS NULL")
					.setTimestamp(0, promotion.getStartTime()).setTimestamp(1, promotion.getEndTime()).setInteger(2, status).list();
			logger.info(promotion.getName()+"查询结果用户数："+ids.size());
			return ids;
		} catch (Exception e) {
			logger.error("查找符合足彩奖上奖活动条件的方案失败。", e);
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Long> findBasketballPromotionSchemes(PromotionPO promotion, int status){
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			logger.info(promotion.getName()+"查询时间范围:{}至:{} 。",sdf.format(promotion.getStartTime()),sdf.format(promotion.getEndTime()));
			List<Long> ids=createQuery("select s.id from WinGrantPO as w RIGHT JOIN w.scheme as s where" +
					" s.createdTime>? AND s.createdTime<? AND s.status=? AND s.playId IN (" +
					"'" + Constants.PLAY_06_LC_2 +"'," +
					"'" + Constants.PLAY_07_LC_2 + "'," +
					"'" + Constants.PLAY_08_LC_2 + "'," +
					"'" + Constants.PLAY_09_LC_2 +"') AND" +
					" s.type in ("+EntityType.BETTING_ALONE + ","+ EntityType.BETTING_FOLLOW +") AND s.afterTaxBonus>=100 AND w.id IS NULL")
					.setTimestamp(0, promotion.getStartTime()).setTimestamp(1, promotion.getEndTime()).setInteger(2, status).list();
			logger.info(promotion.getName()+"查询结果用户数："+ids.size());
			return ids;
		} catch (Exception e) {
			logger.error("查找符合篮彩奖上奖活动条件的方案失败。", e);
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Long> findPromotionSchemeIds(PromotionPO promotion,
			int status,String passTypeIds,Integer buyAmount,Integer afterTaxBonus) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			logger.info(promotion.getName()+"查询时间范围:{}至:{} 。",sdf.format(promotion.getStartTime()),sdf.format(promotion.getEndTime()));
			String hql = "select s.id from WinGrantPO as w RIGHT JOIN w.scheme as s where" +
					" s.createdTime>? AND s.createdTime<? AND s.status=? ";
			if(StringUtils.isNotBlank(passTypeIds)){
				hql+=" AND s.passTypeIds='" + passTypeIds + "'";
			}
			if(buyAmount!=null){
				hql+=" AND s.buyAmount>=" + buyAmount;
			}
			if(afterTaxBonus!=null){
				hql+=" AND s.afterTaxBonus>=" + afterTaxBonus;
			}
			hql += " AND s.playId IN (" + getplayIdsByLotteryId(promotion.getLotteryId())  +") AND" +
					" s.type in ("+EntityType.BETTING_ALONE + ","+ EntityType.BETTING_FOLLOW +") AND w.id IS NULL";
			
			List<Long> ids=createQuery(hql)
					.setTimestamp(0, promotion.getStartTime()).setTimestamp(1, promotion.getEndTime()).setInteger(2, status).list();
			logger.info(promotion.getName()+"查询结果用户数："+ids.size());
			return ids;
		} catch (Exception e) {
			logger.error("查找符合活动条件的方案失败。", e);
			return null;
		}
	}
	
	private String getplayIdsByLotteryId(String lotteryId){
		String playIds="";
		if(StringUtils.isBlank(lotteryId)){
			return playIds;
		}
		if(lotteryId.equals(Constants.JCZQ)){
			playIds = 
					"'" + Constants.PLAY_01_ZC_2 +"'," +
					"'" + Constants.PLAY_02_ZC_2 + "'," +
					"'" + Constants.PLAY_03_ZC_2 + "'," +
					"'" + Constants.PLAY_04_ZC_2 +"'";
		}else if(lotteryId.equals(Constants.JCLQ)){
			playIds = 
					"'" + Constants.PLAY_06_LC_2 +"'," +
					"'" + Constants.PLAY_07_LC_2 + "'," +
					"'" + Constants.PLAY_08_LC_2 + "'," +
					"'" + Constants.PLAY_09_LC_2 +"'";
		}
		return playIds;
	}
	
	@SuppressWarnings("unchecked")
	public List<Long> findPromotionSchemeIds(PromotionPO promotion,int status){
		Criteria c = createCriteria()
                .setProjection(Property.forName("id"))
                .add(Restrictions.eq("saleStatus", EntityStatus.SCHEME_ON_SALE))
                .add(Restrictions.eq("type", EntityType.BETTING_PARTNER));
//        if(from != null){
//            c.add(Restrictions.ge("offtime", from));
//        }
//        if(to != null){
//            c.add(Restrictions.le("offtime", to));
//        }
        return c.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PromotionWinResult> findWinTOP(int top, Date startTime,
			Date endTime, String lotteryId,int status) {
		if(StringUtils.isBlank(lotteryId)){
			logger.info("findWinTOP Faild! lotteryId is NULL!");
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String hql="SELECT  new com.unison.lottery.pm.data.PromotionWinResult(p.username as username,SUM(p.winAmount) as totalAfterTaxBonus,p.userId as uid) " +
				"FROM BetSchemePO as s,BetPartnerPO as p WHERE s.id=p.scheme AND s.lotteryId=:lotteryId AND s.status=:status " ;
		if(startTime != null && endTime != null){
			hql += " and s.createdTime between "+ sdf.format(startTime) +" and " + sdf.format(endTime); 
		}else if(startTime != null && endTime == null){
			hql += " and s.createdTime between "+ sdf.format(startTime) +" and " + sdf.format(new Date()); 
		}else if(startTime == null && endTime != null){
			hql += " and s.offtime<=" + sdf.format(new Date()); 
		}
		hql += " GROUP BY p.userId ORDER BY totalAfterTaxBonus DESC";
		
		return createQuery(hql).setParameter("lotteryId", lotteryId).setParameter("status", status<0?0:status).setMaxResults(top).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Long> findSchemeIdsByPromtion(Promotion promotion) {
		if(promotion == null){
			logger.info("findPromotionSchemeIDs Faild! Promotion is NULL!");
			return null;
		}
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			logger.info(promotion.getName()+"查询时间范围:{}至:{} 。",sdf.format(promotion.getStartTime()),sdf.format(promotion.getEndTime()));
			String hql = "select s.id from WinGrantPO as w RIGHT JOIN w.scheme as s where" +
					" s.createdTime>:createdTime AND s.createdTime<:createdTimeMax AND s.status=:status ";
			if(StringUtils.isNotBlank(promotion.getLotteryId())){
				hql+=" AND s.lotteryId ='" + promotion.getLotteryId() +"'";
			}
			if(StringUtils.isNotBlank(promotion.getPassTypeIds()) &&
					StringUtils.isNotBlank(promotion.getPassTypeIdsLogic())){
				hql+=" AND s.passTypeIds " + TextTool.IDLConvert(promotion.getPassTypeIdsLogic()) +"'" + promotion.getPassTypeIds() + "'";
			}
			if(promotion.getBuyAmount() != 0){
				hql+=" AND s.buyAmount>=" + promotion.getBuyAmount();
			}
			if(null != promotion.getAfterTaxBonus()){
				hql+=" AND s.afterTaxBonus>=" + promotion.getAfterTaxBonus();
			}
			if(null != promotion.getAfterTaxBonusMax() && 
					null != promotion.getAfterTaxBonus() && 
					promotion.getAfterTaxBonusMax().compareTo(promotion.getAfterTaxBonus())==1){
				hql+=" AND s.afterTaxBonus<" + promotion.getAfterTaxBonusMax();
			}
			if(null != promotion.getShow() && promotion.getShow() > -1){
				hql+=" AND s.showScheme>=" + promotion.getShow();
			}
			if(StringUtils.isNotBlank(promotion.getPlayIds())){
				String[] playIds = promotion.getPlayIds().split(",");
				if(playIds.length>1){
					hql += " AND s.playId IN (" + StringUtils.join(playIds,",")  +")";
				}else{
					hql += " AND s.playId ="+playIds[0];
				}
			}
			if(StringUtils.isNotBlank(promotion.getSchemeTypes())){
				String[] schemeTypes = promotion.getSchemeTypes().split(",");
				if(schemeTypes.length>1){
					hql += " AND s.type in ("+StringUtils.join(schemeTypes,",") +")";
				}else{
					hql += " AND s.type ="+schemeTypes[0];
				}
				
			}
			hql += " AND w.id IS NULL";
			
			String[] status = promotion.getSchemeStatus().split(",");
			Set<Integer>  intStatus = new HashSet<Integer>();
			for(int i=0;i<status.length;i++){
				intStatus.add(Integer.parseInt(status[i]));
			}
			Query query = createQuery(hql)
					.setParameter("createdTime", promotion.getStartTime())
					.setParameter("createdTimeMax", promotion.getEndTime())
					.setParameterList("status", intStatus);
			
			List<Long> ids=query.list();
			logger.info(promotion.getName()+"查询结果用户数："+ids.size());
			return ids;
		} catch (Exception e) {
			logger.error("查找符合活动条件的方案失败。", e);
			return null;
		}
	}
}

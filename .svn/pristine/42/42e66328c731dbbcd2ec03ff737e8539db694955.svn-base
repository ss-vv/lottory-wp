package com.unison.lottery.pm.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.unison.lottery.pm.data.OrderColorGrant;
import com.unison.lottery.pm.data.RechargeResult;
import com.unison.lottery.pm.data.VoucherForOrderColorType;
import com.unison.lottery.pm.lang.RechargeGrant;
import com.unison.lottery.pm.lang.WinGrant;
import com.unison.lottery.pm.persist.PMGrantAccountDao;
import com.unison.lottery.pm.persist.PMRechargeGrantDao;
import com.unison.lottery.pm.service.RechargeGrantService;
import com.unison.lottery.pm.utils.Constants;
import com.xhcms.lottery.commons.persist.dao.BetSchemeDao;
import com.xhcms.lottery.commons.persist.dao.GrantDao;
import com.xhcms.lottery.commons.persist.dao.PMGrantVoucherDao;
import com.xhcms.lottery.commons.persist.dao.PMPromotionDao;
import com.xhcms.lottery.commons.persist.dao.VoucherPMDao;
import com.xhcms.lottery.commons.persist.entity.GrantPO;
import com.xhcms.lottery.commons.persist.entity.PMGrantVoucherPO;
import com.xhcms.lottery.commons.persist.entity.PromotionPO;
import com.xhcms.lottery.commons.persist.entity.UserPO;
import com.xhcms.lottery.commons.persist.entity.VoucherPMPO;
import com.xhcms.lottery.commons.persist.service.AccountService;
import com.xhcms.lottery.commons.persist.service.VoucherPMService;
import com.xhcms.lottery.commons.persist.service.VoucherUserService;
import com.xhcms.lottery.lang.EntityStatus;
import com.xhcms.lottery.lang.VoucherGrant;

public class RechargeGrantServiceImpl implements RechargeGrantService {
	protected Logger log = LoggerFactory.getLogger(getClass());
	
	protected Logger orderColorGrantLog = LoggerFactory.getLogger(OrderColorGrant.class);
	
	@Autowired
	private PMRechargeGrantDao pMRechargeGrantDao;
	@Autowired
	private PMGrantAccountDao pMGrantAccountDao;
	@Autowired
	private GrantDao grantDao;
	@Autowired
	private PMPromotionDao pMPromotionDao;
	@Autowired
	private AccountService accountService;
	@Autowired
	private VoucherUserService voucherUserService;
	@Autowired
	private BetSchemeDao betSchemeDao;
	@Autowired
	private VoucherPMDao voucherPMDao;
	@Autowired
	private PMGrantVoucherDao pMGrantVoucherDao;
	
	@Autowired
	private VoucherPMService voucherPMService;
	 
	/**
	 * 用户首次充值大于等于50送10元
	 */
	@Override
	@Transactional
	public void rechargeGrant(Long promotionId) {
		try {
			PromotionPO promotionPO = pMPromotionDao.get(promotionId);
			Date from = promotionPO.getStartTime();
			Date to = promotionPO.getEndTime();
			log.info("promotion information：promotionName={},startTime={},endTime={}",
					new Object[] { promotionPO.getName(),
							promotionPO.getStartTime(),
							promotionPO.getEndTime() });
			// 取得满足条件的用户id
			List<Long> ids = this.getUserIdsFromUserInfo(from, to, promotionPO.getId());
			log.info("In the active period of time, bind the mobile phone and validation of mailbox, and did not grant the user number：ids={}", ids.size());
			if (ids != null && ids.size() > 0) {
				// 取得首次充值金额大于等于50的用户id
				ids = pMGrantAccountDao.getUserListFromRecharge(ids, from, to);
				log.info("In the active period of time, the first value is greater than or equal to 50 yuan of the number of users：userNum={}", ids.size());
				if (ids != null && ids.size() > 0) {
					// 根据用户id取得用户信息列表
					List<UserPO> userList = pMRechargeGrantDao
							.getUserListById(ids);
					log.info("Meet all the conditions of the number of users：userSize={},用户id：ids={}",
							new Object[] { ids.size(), ids });
					if (userList != null && userList.size() > 0) {
						// 插入赠款记录表，并返回赠款记录id
						List<Long> grantIds = pMRechargeGrantDao.insertGrant(
								userList, promotionPO);
						// 插入赠款活动记录表
						pMRechargeGrantDao.insertRechargeGrant(userList,
								promotionPO);
						// 审批、赠款
						this.batchPass(grantIds, RechargeGrant.auditId);
					}
				}
			}
		} catch (Exception e) {
			log.error("register recharge error", e);
		}

	}

	/**
	 * 取得符合如下条件的用户信息list 1、注册时间在2012-06-08 00:00:00和2012-07-02 00:00:00之间 2、绑定手机
	 * 3、验证邮箱 4、本活动没有赠款
	 */
	private List<Long> getUserIdsFromUserInfo(Date from, Date to, Long promId) {
		List<Long> ids = new ArrayList<Long>();
		List<UserPO> userList = pMRechargeGrantDao.getUserListFromUserInfo(
				from, to, promId);
		if (userList != null && userList.size() > 0) {
			for (UserPO user : userList) {
				ids.add(user.getId());
			}
		}

		return ids;
	}

	// 审批、赠款
	private void batchPass(List<Long> ids, int auditId) {
		if (ids == null || ids.size() <= 0) {
			return;
		}
		List<GrantPO> gras = grantDao.find(ids);

		for (GrantPO g : gras) {
			// 判断是否未审核
			if (g.getStatus() == EntityStatus.GRANT_INIT) {
				g.setStatus(EntityStatus.GRANT_AUDIT);
				// TODO 转换为赠送彩金劵代码
				voucherUserService.createVoucher(g.getUserId(), 30L, VoucherGrant.voucherGrantMap.get(g.getAmount().intValue()), g.getGrantTypeId());
				
				//accountService.grant(auditId, g.getUserId(), g.getAmount(),g.getNote());
			}
		}
	}
	
	@Override
	@Transactional
	public void registGrant(Long promotionId) {
		PromotionPO promotionPO = pMPromotionDao.get(promotionId);
		Date from = promotionPO.getStartTime();
		Date to = promotionPO.getEndTime();
		log.info("promotion information：promotionName={},startTime={},endTime={}",
				new Object[] { promotionPO.getName(),
						promotionPO.getStartTime(),
						promotionPO.getEndTime() });
		
		// 取得满足条件的用户
		List<UserPO> userPOs = pMRechargeGrantDao.getNewRegistUser(from, to, promotionPO.getId());
		log.info("regist user num={}" + userPOs.size());
		if (userPOs != null && userPOs.size() > 0) {
			// 插入赠款记录表，并返回赠款记录id
			List<Long> grantIds = pMRechargeGrantDao.insertGrant(
					userPOs, promotionPO);
			// 插入充值赠款活动记录表
			pMRechargeGrantDao.insertRechargeGrant(userPOs,
					promotionPO);
			// 审批、赠款
			this.batchPass(grantIds, RechargeGrant.auditId);
		}	
	}
	
	@Override
	@Transactional
	public void newUserGrantVoucher(Long promotionId) {
		VoucherPMPO voupm = voucherPMService.get(promotionId);
		Date from = voupm.getPmBeginTime();
		Date to = voupm.getPmEndTime();
		log.info("promotion information：promotionName={},startTime={},endTime={}",
				new Object[] { voupm.getName(),	from, to });
		
		// 取得满足条件的用户
		Long grantTypeId = voupm.getGrantType().getId();
		List<UserPO> userPOs = pMRechargeGrantDao.getNewUserForGrantVoucher(from, to, grantTypeId);
		
		if(null != userPOs && !userPOs.isEmpty()) {
			log.info("活动一赠送充值优惠券新用户数 num={}", userPOs.size());
			for(UserPO po : userPOs) {
				if(promotionId == 5) {
					//8月活动，每人赠送充值优惠券（充50送10）
					voucherUserService.createVoucher(po.getId(), 31L, "CZS_50_10_WEB", 47L);
				} else if(promotionId == 7) {
					//9月活动，每人赠送充值优惠券（充20送10）
					voucherUserService.createVoucher(po.getId(), 30L, "CZS_20_10", 50L);
				} else {
					log.error("活动id出错！", promotionId);
				}
			}
		}
		
	}
	
	@Override
	@Transactional
	public void clientGrantVoucher(Long promotionId) {
		VoucherPMPO voupm = voucherPMService.get(promotionId);
		Date from = voupm.getPmBeginTime();
		Date to = voupm.getPmEndTime();
		log.info("promotion information：promotionName={},startTime={},endTime={}",
				new Object[] { voupm.getName(),	from, to });
		
		// 取得满足条件的用户
		Long grantTypeId = voupm.getGrantType().getId();
		List<UserPO> userPOs = pMRechargeGrantDao.getNewUserForGrantVoucher(null, null, grantTypeId);
		
		if(null != userPOs && !userPOs.isEmpty()) {
			log.info("活动二赠送充值优惠券用户数 num={}", userPOs.size());
			for(UserPO po : userPOs) {
				//每人赠送充值优惠券
				voucherUserService.createVoucher(po.getId(), 31L, "CZS_20_10_CLIENT", 48L);
			}
		}
	}
	
	/**是否对用户继续进行现金券的赠送*/
	private boolean isContinueGrant(int historyGrantCnt) {
		if(historyGrantCnt >= Constants.MAX_GRANT_COUNT) {
			return false;
		}
		return true;
	}
	
	//根据用户注册渠道生成优惠券类型
	private String getVoucherId(String pid, int grantAmount) {
		String voucherId = null;
		grantAmount = grantAmount / 100;
		if(Constants.ORDER_RING_CHANNEL_CLIENT.equals(pid)) {
			voucherId = VoucherForOrderColorType.voucherGrantMapForClient.get(grantAmount);
		} else {
			voucherId = VoucherForOrderColorType.voucherGrantMapForALl.get(grantAmount);
		}
		return voucherId;
	}
	
	@Override
	@Transactional
	public void orderColorRingGrant(Long promotionId) {
		PromotionPO promotionPO = pMPromotionDao.get(promotionId);
		orderColorGrantLog.info("promotion information：promotionName={}",promotionPO.getName());
		List<OrderColorGrant> mobileList = pMRechargeGrantDao.getNoGrantMobileList(0, 200);
		Map<String, Long> mobileUserIdMap = new HashMap<String, Long>();
		
		if(null != mobileList && mobileList.size() > 0) {
			orderColorGrantLog.info("查询出准备赠送彩金的手机号码列表：{}", mobileList);
			long startTime = System.currentTimeMillis();
			//赠送现金优惠券
			for(OrderColorGrant colorGrant : mobileList) {
				String pid = colorGrant.getPid();
				String voucherId = getVoucherId(pid, colorGrant.getGrantAmount());
				if(StringUtils.isBlank(voucherId)) {
					orderColorGrantLog.error("优惠券无法赠送：根据赠送金额无法匹配优惠券类型，{}", colorGrant);
				} else {
					boolean isAddFilter = true;
					//缓存本次遍历的大V彩用户ID
					Long userId = mobileUserIdMap.get(colorGrant.getMobile());
					if(null != userId && !userId.equals(colorGrant.getUserId())) {
						isAddFilter = false;
						orderColorGrantLog.info("检查发现有多个用户ID={},对应同一个手机号={}，\n后续彩票帐号不再继续赠送，\n 详细赠送彩金记录={}",
								new Object[]{new Long[]{userId, colorGrant.getUserId()}, 
								colorGrant.getMobile(), colorGrant});
					} else {
						int historyGrantCnt = pMRechargeGrantDao.findGrantedCountByMobile(colorGrant);
						if(isContinueGrant(historyGrantCnt)) {
							pMRechargeGrantDao.updateColorRingGrantStatus(colorGrant.getTradeNo());
							voucherUserService.createVoucher(colorGrant.getUserId(), 30L, 
									voucherId, 
									Constants.ORDER_RING_GRANT);
						} else {
							pMRechargeGrantDao.updateColorRingGrantStatus(colorGrant.getTradeNo(), 
									WinGrant.GrantStatus.granted_out_count + "");
							orderColorGrantLog.info("手机号总的彩票现金券获赠次数大于15次,不再继续赠送；详情：{}", colorGrant);
						}
						pMRechargeGrantDao.updateColorRingGrantUserId(colorGrant.getTradeNo(), colorGrant.getUserId());
					}
					if(isAddFilter) {
						mobileUserIdMap.put(colorGrant.getMobile(), colorGrant.getUserId());
					}
				}
			}
			long endTime = System.currentTimeMillis();
			orderColorGrantLog.info("duration times(millis second)={}", (endTime - startTime));
		}
	}
	
	@Override
	@Transactional
	public void betReturnCashCoupon(Long promotionId) {
		try {
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        Calendar cal = Calendar.getInstance();
	        cal.add(Calendar.DATE, -1);
	        String startTime = sdf.format(cal.getTime()) + " 00:00:00";
	        String endTime = sdf.format(cal.getTime()) + " 23:59:59";
	        log.info("startTime={}", startTime);
	        log.info("endTime={}", endTime);
	        
			VoucherPMPO voucherPM = voucherPMDao.get(promotionId);
			
	        Date start = null;
	        Date end = null;
	        Date pmStart = null;
	        Date pmEnd = null;
			try {
				start = sdf2.parse(startTime);
				end = sdf2.parse(endTime);
				
				if(null != voucherPM) {
					pmStart = voucherPM.getPmBeginTime();
					pmEnd = voucherPM.getPmEndTime();
				} else {
					log.error("取不到该活动！！！");
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
	        if(start.compareTo(pmStart) >= 0 && end.compareTo(pmEnd) <= 0){
	        	//log.info("赠送时间在活动时间范围内！");
	    		// 取得满足条件的用户
	    		List<Object[]> bets = betSchemeDao.findBetPassSuccessNote(start, end);
	    		if(null != bets && bets.size() > 0) {
	    			for(int i=0; i<bets.size(); i++) {
		    			Object[] obj = bets.get(i);
		    			grantCashCoupon(obj, voucherPM, cal);

	    			}
	    		}
	        }
		}catch(Exception e) {
			e.printStackTrace();
		}
        
	}
	
	@Transactional
	private void grantCashCoupon(Object[] obj, VoucherPMPO voucherPM, Calendar cal) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String startStr = sdf.format(cal.getTime());
		Long weekNum = new Long(startStr.substring(startStr.length()-2, startStr.length()));
		
		Long granttypeId = voucherPM.getGrantType().getId();
		Long voucherPmId = voucherPM.getId();
		
		Long userId = new Long(obj[0].toString());
		Integer sumAmount = new Integer(obj[1].toString())*2;
		
		PMGrantVoucherPO grantVoucherPO = pMGrantVoucherDao.find(userId, voucherPmId, weekNum);
		if(null == grantVoucherPO) {
			//优惠券id
			String voucherId = getVoucherId(sumAmount);
			try{
				if(null != voucherId) {
	    			PMGrantVoucherPO pmgvPO = new PMGrantVoucherPO();
	    			pmgvPO.setUserId(userId);	    			
	    			pmgvPO.setVoucherPmId(voucherPmId);
	    			pmgvPO.setGranttypeId(granttypeId.intValue());
	    			pmgvPO.setVoucherType("grant");
	    			pmgvPO.setVoucherId(voucherId);
	    			pmgvPO.setPmWeek(weekNum);
	    			pmgvPO.setCreatedTime(new Date());
	    			pMGrantVoucherDao.save(pmgvPO);
	    			
	    			//插入lt_voucher_user
	    			voucherUserService.createVoucher(userId, 30L, voucherId, granttypeId);
				}
			} catch(Exception e) {
				e.printStackTrace();
				log.error(
						"九月购彩返彩金活动赠送出错，userId={}, pmId={}, weekNum={}",
						new Object[] { userId,
								voucherPM.getId(), weekNum });
			}
		}
	}
	
	private String getVoucherId(int sumAmount) {
		String voucherId = null;
		if(sumAmount >= 10000) {
			voucherId = "SCJ_500";
		} else if(sumAmount >= 5000) {
			voucherId = "SCJ_200";
		} else if(sumAmount >= 1000) {
			voucherId = "SCJ_50";
		} else if(sumAmount >= 100) {
			voucherId = "SCJ_10";
		} else if(sumAmount >= 10) {
			voucherId = "SCJ_3";
		} else {
			voucherId = null;
			//log.info("用户"+userId+"投注金额不符合赠送条件！");
		}
		
		return voucherId;
	}
	
}

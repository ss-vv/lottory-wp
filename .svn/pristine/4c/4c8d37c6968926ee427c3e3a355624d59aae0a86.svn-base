package com.unison.lottery.pm.persist.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;

import com.unison.lottery.pm.data.CountResult;
import com.unison.lottery.pm.data.OrderColorGrant;
import com.unison.lottery.pm.data.RechargeResult;
import com.unison.lottery.pm.entity.RechargeGrantPO;
import com.unison.lottery.pm.entity.WapRechargeGrantPO;
import com.unison.lottery.pm.lang.RechargeGrant;
import com.unison.lottery.pm.persist.PMRechargeGrantDao;
import com.unison.lottery.pm.utils.DateUtil;
import com.xhcms.lottery.commons.persist.dao.impl.UserDaoImpl;
import com.xhcms.lottery.commons.persist.entity.GrantPO;
import com.xhcms.lottery.commons.persist.entity.PromotionPO;
import com.xhcms.lottery.commons.persist.entity.UserPO;

/**
 * @author yongli zhu
 * 
 */
public class PMRechargeGrantDaoImpl extends UserDaoImpl implements
		PMRechargeGrantDao {
	private static final long serialVersionUID = -8203302237333957344L;

	@Override
	public List<UserPO> getUserListFromUserInfo(Date from, Date to, Long promId) {
		List<UserPO> userList = createQuery(
				" from UserPO u where u.createdTime>=? and u.createdTime<=? and u.verifyStatus in('3') " +
			    " and not exists (from RechargeGrantPO g where u.id=g.userId and g.promotionId="+promId+" ) " +
				" and not exists (from RechargeGrantPO g where u.idNumber=g.idNumber and g.promotionId="+promId+" ) " +
			    " and not exists (from RechargeGrantPO g where u.email=g.email and g.promotionId="+promId+" ) " +
				" and not exists (from RechargeGrantPO g where u.mobile=g.mobile and g.promotionId="+promId+" ) " +
				" and (u.ip is null or u.ip not in (select g.ip from RechargeGrantPO g where g.promotionId="+promId+" group by g.ip having count(g.id)>=3 ) )")
				.setTimestamp(0, from).setTimestamp(1, to).list();

		return userList;
	}
	
	@Override
	public List<UserPO> getNewRegistUser(Date from, Date to, Long promId) {
		int ipLimitNum = 20;
		List<UserPO> userList = createQuery(
				" from UserPO u where u.createdTime>=? and u.createdTime<=? and u.verifyStatus in ('2','3') " +
			    " and not exists (from RechargeGrantPO g where u.id=g.userId and g.promotionId="+promId+" ) " +
				" and not exists (from RechargeGrantPO g where u.idNumber=g.idNumber and g.promotionId="+promId+" ) " +
			    " and not exists (from RechargeGrantPO g where u.email=g.email and g.promotionId="+promId+" ) " +
				" and not exists (from RechargeGrantPO g where u.mobile=g.mobile and g.promotionId="+promId+" ) " +
			    " and (u.ip is null or  not exists (select g.ip from RechargeGrantPO g where g.promotionId="+promId+" group by g.ip having count(g.id)>=? and g.ip = u.ip) )")
				.setTimestamp(0, from).setTimestamp(1, to).setInteger(2, ipLimitNum).list();
		
		return userList;
	}
	
	@Override
	public List<UserPO> getNewUserForGrantVoucher(Date from, Date to, Long grantTypeId) {
		
		String hql = " from UserPO u where u.status=0 and u.verifyStatus in ('2','3') ";
		if(null != from && null != to) {
			hql = hql + " and u.createdTime>=? and u.createdTime<=? ";
		}
		hql = hql + " and not exists (from VoucherUserExtendPO v where u.id=v.userId and v.grantType=? ) ";
		
		Query query = createQuery(hql);
		
		if(null != from && null != to) {
			query.setTimestamp(0, from).setTimestamp(1, to).setLong(2, grantTypeId);
		} else {
			query.setLong(0, grantTypeId);
		}
		
		List<UserPO> userList = query.list();
			
		return userList;
	}

	// 根据用户id取得用户信息列表
	@Override
	public List<UserPO> getUserListById(Collection<Long> ids) {

		return createCriteria().add(Restrictions.in("id", ids)).list();
	}

	@Override
	public List<Long> insertGrant(List<UserPO> userList,PromotionPO promotionPO) {
		List<Long> grantIds = new ArrayList<Long>();
		if (userList != null && userList.size() > 0) {
			for (UserPO user : userList) {
				// 插入赠款记录表
				GrantPO grantPO = new GrantPO();
				grantPO.setUserId(user.getId());
				grantPO.setAmount(new BigDecimal(3.0));
				grantPO.setCreatedTime(new Date());
				grantPO.setOperatorId(RechargeGrant.operatorId);
				grantPO.setNote(promotionPO.getName());
				grantPO.setStatus(RechargeGrant.auditStatus);
				grantPO.setGrantTypeId(promotionPO.getGrantTypeId());
				
				save(grantPO);
				grantIds.add(grantPO.getId());
			}
		}
		return grantIds;
	}

	@Override
	public void insertRechargeGrant(List<UserPO> userList,PromotionPO promotionPO) {
		if (userList != null && userList.size() > 0) {
			for (UserPO user : userList) {
				// 插入赠款活动表
				RechargeGrantPO rechargeGrantPO = new RechargeGrantPO();
				rechargeGrantPO.setUserId(user.getId());
				rechargeGrantPO.setUsername(user.getUsername());
				rechargeGrantPO.setPromotionId(promotionPO.getId());
				rechargeGrantPO.setGrantTypeId(promotionPO.getGrantTypeId());
				rechargeGrantPO.setAmount(new BigDecimal(3.0));
				rechargeGrantPO.setGrantTime(new Date());
				rechargeGrantPO.setOperatorTime(new Date());
				rechargeGrantPO.setIdNumber(user.getIdNumber());
				rechargeGrantPO.setEmail(user.getEmail());
				rechargeGrantPO.setMobile(user.getMobile());
				rechargeGrantPO.setIp(user.getIp());

				getSessionFactory().getCurrentSession().save(rechargeGrantPO);
			}
		}
	}
	
	@Override
	public UserPO getVerifyMobileById(Long userId, Date from, Date to) {
		UserPO userPO = (UserPO)createQuery(
				" from UserPO u where u.createdTime>=? and u.createdTime<=? and u.verifyStatus in('2','3') and u.id=?" +
			    " and not exists (from WapRechargeGrantPO g where u.id=g.userId) " +
				" and not exists (from WapRechargeGrantPO g where u.mobile=g.mobile) ")
				.setTimestamp(0, from).setTimestamp(1, to)
				.setLong(2, userId).uniqueResult();

		return userPO;
	}
	
	@Override
	public Long insertWapGrant(UserPO userPO, PromotionPO promotionPO, BigDecimal grantAmount) {
		// 插入赠款记录表
		GrantPO grantPO = new GrantPO();
		grantPO.setUserId(userPO.getId());
		grantPO.setAmount(grantAmount);
		grantPO.setCreatedTime(new Date());
		grantPO.setOperatorId(RechargeGrant.operatorId);
		grantPO.setAuditTime(new Date());
		grantPO.setNote(promotionPO.getName());
		grantPO.setStatus(RechargeGrant.auditStatus);
		grantPO.setGrantTypeId(promotionPO.getGrantTypeId());
		
		save(grantPO);
		return grantPO.getId();
	}
	
	@Override
	public void insertWapRechargeGrant(UserPO userPO, PromotionPO promotionPO, BigDecimal grantAmount) {
		// 插入wap赠款活动表
		WapRechargeGrantPO waprgPO = new WapRechargeGrantPO();
		waprgPO.setUserId(userPO.getId());
		waprgPO.setUsername(userPO.getUsername());
		waprgPO.setPromotionId(promotionPO.getId());
		waprgPO.setGrantTypeId(promotionPO.getGrantTypeId());
		waprgPO.setAmount(grantAmount);
		waprgPO.setGrantTime(new Date());
		waprgPO.setOperatorTime(new Date());
		waprgPO.setMobile(userPO.getMobile());
		
		save(waprgPO);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CountResult> countRegistNum(String sdata) {
		String sql = "select new com.unison.lottery.pm.data.CountResult(mobile,count(id) as registNum) from UserPO where mobile in (" + sdata +
				") group by mobile";
		return (List<CountResult>)this.createQuery(sql).list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Long> getUserIdByMobile(String mobile) {
		return (List<Long>)this.createQuery("select id from UserPO where mobile=?").setString(0, mobile).list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<RechargeResult> getUserFromColorRingGrant() {
		List<RechargeResult> results = createQuery(
				" select new com.unison.lottery.pm.data.RechargeResult(u.id as userId, p.tradeNo as tradeNo, p.grantAmount as grantAmount) " +
				" from UserPO u, PmColorRingGrantPO p where u.mobile=p.mobile and u.verifyStatus in ('2','3') " +
				" and p.status='0' ").list();

		return results;
	}
	
	@Override
	public List<Long> insertColorRingGrant(List<RechargeResult> results, PromotionPO promotionPO) {
		List<Long> grantIds = new ArrayList<Long>();
		if (results != null && results.size() > 0) {
			for (RechargeResult rs : results) {
				// 插入赠款记录表
				GrantPO grantPO = new GrantPO();
				grantPO.setUserId(rs.getUserId());
				BigDecimal amount = new BigDecimal(rs.getGrantAmount()).divide(new BigDecimal(100));
				grantPO.setAmount(amount);
				grantPO.setCreatedTime(new Date());
				grantPO.setOperatorId(RechargeGrant.operatorId);
				grantPO.setNote(promotionPO.getName());
				grantPO.setStatus(RechargeGrant.auditStatus);
				grantPO.setGrantTypeId(promotionPO.getGrantTypeId());
				
				save(grantPO);
				grantIds.add(grantPO.getId());
			}
		}
		
		return grantIds;
	}
	
	@Override
	public void updateColorRingGrantStatus(List<String> tradeNoList) {
		Date date = new Date();
		this.createQuery("update PmColorRingGrantPO set status='1', updateTime=:updateTime where tradeNo in (:tradeNos)")
        .setTimestamp("updateTime", date).setParameterList("tradeNos", tradeNoList).executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OrderColorGrant> getNoGrantMobileList(int firstIndex, int maxResults) {
		StringBuffer sql = new StringBuffer();
		sql.append("select new com.unison.lottery.pm.data.OrderColorGrant")
		.append("(g.mobile as mobile, u.id as userId, g.tradeNo as tradeNo, g.grantAmount as grantAmount,")
		.append("u.pid as pid, g.orderTime as orderTime) ")
		.append("from PmColorRingGrantPO g, UserPO u ")
		.append("where g.mobile = u.mobile and u.verifyStatus in (2,3) and g.status = 0 ")
		.append("order by u.createdTime desc,g.orderTime asc ");
		
		Query query = createQuery(sql.toString());
		query.setFirstResult(firstIndex);
		query.setMaxResults(maxResults);
		return query.list();
	}

	@Override
	public int findGrantedCountByMobile(OrderColorGrant orderColorGrant) {
		String dateStr = DateUtil.format(orderColorGrant.getOrderTime());
		
		//查看此号码在指定日期历史赠送的次数
		StringBuffer sql = new StringBuffer();
		sql.append("select count(g.mobile) cnt from pm_colorring_grant g ")
		.append("where g.status = 1 and g.mobile = ? and date_format(g.order_time, '%Y-%m-%d') = ?");
		SQLQuery query = createSQLQuery(sql.toString());
		query.setParameter(0, orderColorGrant.getMobile());
		query.setParameter(1, dateStr);
		Object objRs =  query.uniqueResult();
		int historyMobileGrantCnt = Integer.parseInt(String.valueOf(objRs));
		int historyDiffMobileGrantCnt = findHistoryGrantAndMobileIsDiff(orderColorGrant, dateStr);
		
		int result = historyMobileGrantCnt + historyDiffMobileGrantCnt;
		return result;
	}

	/**
	 * 查询是否存在与当前手机号不同但对应同一个用户的历史赠送记录
	 * @param orderColorGrant
	 * @return
	 */
	private int findHistoryGrantAndMobileIsDiff(OrderColorGrant orderColorGrant, String dateStr) {
		StringBuffer sql = new StringBuffer();
		sql.append("select count(g.mobile) ")
		.append("from pm_colorring_grant g, lt_user u ")
		.append("where u.id = ? and g.mobile <> ? ")
		.append("and g.status = 1 and g.user_id = u.id and date_format(g.order_time, '%Y-%m-%d') = ? ");
		SQLQuery query = createSQLQuery(sql.toString());
		query.setParameter(0, orderColorGrant.getUserId());
		query.setParameter(1, orderColorGrant.getMobile());
		query.setParameter(2, dateStr);
		Object objRs =  query.uniqueResult();
		int historyMobileGrantCnt = Integer.parseInt(String.valueOf(objRs));
		return historyMobileGrantCnt;
	}

	@Override
	public void updateColorRingGrantUserId(String tradeNo, Long userId) {
		this.createQuery("update PmColorRingGrantPO set userId=:userId, updateTime=now() where tradeNo = :tradeNo")
		.setParameter("userId", userId)
        .setParameter("tradeNo", tradeNo).executeUpdate();
	}

	@Override
	public void updateColorRingGrantStatus(String tradeNo) {
		this.createQuery("update PmColorRingGrantPO set status='1' where tradeNo = :tradeNo")
        .setParameter("tradeNo", tradeNo).executeUpdate();
	}
	
	@Override
	public void updateColorRingGrantStatus(String tradeNo, String status) {
		this.createQuery("update PmColorRingGrantPO set status=:status where tradeNo = :tradeNo")
		.setParameter("status", status)
		.setParameter("tradeNo", tradeNo)
		.executeUpdate();
	}
}

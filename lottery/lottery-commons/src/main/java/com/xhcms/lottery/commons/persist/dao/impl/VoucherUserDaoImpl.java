package com.xhcms.lottery.commons.persist.dao.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.impl.CriteriaImpl;

import com.xhcms.commons.lang.Assert;
import com.xhcms.commons.lang.Paging;
import com.xhcms.commons.persist.hibernate.DaoImpl;
import com.xhcms.lottery.commons.persist.dao.VoucherUserDao;
import com.xhcms.lottery.commons.persist.entity.VoucherTypePO;
import com.xhcms.lottery.commons.persist.entity.VoucherUserExtendPO;
import com.xhcms.lottery.commons.persist.entity.VoucherUserPO;
import com.xhcms.lottery.lang.AppCode;
import com.xhcms.lottery.lang.VoucherType;
import com.xhcms.lottery.lang.VoucherUserStatus;
import com.xhcms.lottery.lang.VoucherUserViewStatus;

/**
 * 优惠卷dao
 * @author Wang Lei
 *
 */
@SuppressWarnings("unchecked")
public class VoucherUserDaoImpl extends DaoImpl<VoucherUserExtendPO> implements VoucherUserDao {
	
	private static final long serialVersionUID = 1L;

	public VoucherUserDaoImpl(){
		super(VoucherUserExtendPO.class);
	}
	
	/** 返回锁定超时时间 */
	private Date getLockTimeOutTime(){
		Calendar calendarLock=Calendar.getInstance();
        calendarLock.setTime(new Date());
        calendarLock.add(Calendar.MINUTE, -10);
        return calendarLock.getTime();
	}
	
	/** 可用的优惠卷状态 */
	private LogicalExpression validStatus(){
        Junction junction = Restrictions.conjunction();
		junction.add(Restrictions.eq("status", VoucherUserStatus.LOCKED.getValue()));
		junction.add(Restrictions.lt("lockTime", getLockTimeOutTime()));
		Junction junction2 = Restrictions.conjunction();
		junction2.add(Restrictions.eq("status", VoucherUserStatus.UNUSED.getValue()));
		return Restrictions.or(junction2, junction);
	}
	
	@Override
	public List<VoucherUserPO> listValidRechargeByUserid(Long userId){
		Criteria c = createCriteria();
		validCriteria(c);
		c.add(Restrictions.eq("userId", userId));
		c.addOrder(Order.desc("id"));
		c.createCriteria("voucher").add(Restrictions.eq("type", VoucherType.recharge.name()));
		return c.list();
	}
	
	/**
	 * 根据充值流水id获得可以使用的优惠劵
	 * @param rechargeId
	 * @return
	 */
	@Override
	public VoucherUserPO getCanUseByRechargeId(Long rechargeId){
		Assert.notNull(rechargeId, AppCode.RECHARGE_NOT_EXIST);
		Criteria c = createCriteria();
		c.add(Restrictions.eq("rechargeId", rechargeId));
		c.add(Restrictions.eq("status", VoucherUserStatus.LOCKED.getValue()));
		c.add(Restrictions.gt("lockTime", getLockTimeOutTime()));
		return (VoucherUserPO) c.uniqueResult();
	}
	
	/**  未使用的优惠卷 */
	@Override
	public List<VoucherUserPO> listValidByUserid(Paging paging, Long userId){
		Criteria c = createCriteria();
		validCriteria(c);
		c.add(Restrictions.eq("userId", userId));
		c.addOrder(Order.desc("id"));
		return (List<VoucherUserPO>) findByCriteria(c, paging);
	}
	
	private void validCriteria(Criteria c){
		c.add(Restrictions.gt("deadTime", new Date()));
		c.add(validStatus());
	}
	
	/**  已使用的优惠卷 */
	@Override
	public List<VoucherUserPO> listUsedByUserid(Paging paging, Long userId){
		Criteria c = createCriteria();
		usedCriteria(c);
		c.add(Restrictions.eq("userId", userId));
		c.addOrder(Order.desc("id"));
		return (List<VoucherUserPO>) findByCriteria(c, paging);
	}
	
	private void usedCriteria(Criteria c){
		Junction junction = Restrictions.conjunction();
		junction.add(Restrictions.eq("status", VoucherUserStatus.LOCKED.getValue()));
		junction.add(Restrictions.gt("lockTime", getLockTimeOutTime()));
		c.add(Restrictions.or(junction, Restrictions.eq("status", VoucherUserStatus.USED.getValue())));
	}
	
	/**  已过期的优惠卷 */
	@Override
	public List<VoucherUserPO> listExpireByUserid(Paging paging, Long userId){
		Criteria c = createCriteria();
		expireCriteria(c);
		c.add(Restrictions.eq("userId", userId));
		c.addOrder(Order.desc("id"));
		return (List<VoucherUserPO>) findByCriteria(c, paging);
	}
	
	/**  已过期的优惠卷 */
	private void expireCriteria(Criteria c){
		c.add(Restrictions.lt("deadTime", new Date()));
		c.add(validStatus());
	}
	
	/**  使用赠款类型id获取用户优惠卷列表 */
	@Override
	public List<String> listUserVoucherByGrant(Long granttypeId, Long userId){
		Criteria c = createCriteria();
		c.add(Restrictions.eq("userId", userId));
		c.createCriteria("grantType").add(Restrictions.eq("id", granttypeId));
		c.setProjection(Projections.property("voucher.id"));
		return c.list();
	}
	
	@Override
	public int getVoucherCountByGrantTypeIdsAndTime(Set<Long> grantTypeIds,Long userId,
			Date startTime, Date endTime) {
		int count=0;
		Criteria criteria = createCriteria();
		if(grantTypeIds == null || grantTypeIds.isEmpty() || null == userId){
			return count;
		}
		if(null != startTime){
			criteria.add(Restrictions.ge("createTime", startTime));
		}
		if(null != endTime){
			criteria.add(Restrictions.le("createTime", endTime));
		}
		criteria.setProjection(Projections.rowCount());
		criteria.add(Restrictions.eq("userId", userId));
		criteria.add(Restrictions.in("grantType.id", grantTypeIds));
		Object obj = criteria.uniqueResult();
		if(null != obj){
			count=((Long) obj).intValue();
		}
		return count;
	}
	
	@Override
	public List<VoucherUserExtendPO> list(Paging paging, String voucherUserViewStatus,String username ,Long id,String voucherId ,Long granttypeId){
		Criteria c = getSessionFactory().getCurrentSession().createCriteria(VoucherUserExtendPO.class);
		if(StringUtils.isNotBlank(voucherUserViewStatus)){
			if(voucherUserViewStatus.equals(VoucherUserViewStatus.UNUSED.name())){
				validCriteria(c);
			}else if(voucherUserViewStatus.equals(VoucherUserViewStatus.USED.name())){
				usedCriteria(c);
			}else if(voucherUserViewStatus.equals(VoucherUserViewStatus.EXPIRE.name())){
				expireCriteria(c);
			}
		}
		if( id != null){
			c.add(Restrictions.eq("id", id));
		}
		if( granttypeId != null){
			c.add(Restrictions.eq("grantType.id", granttypeId));
		}
		if(StringUtils.isNotBlank(voucherId)){
			c.add(Restrictions.eq("voucher.id", voucherId));
		}
		
		c.addOrder(Order.desc("id"));
		
		if(StringUtils.isNotBlank(username)){
			c.createCriteria("user").add(Restrictions.eq("username", username));
		}
		
		return (List<VoucherUserExtendPO>) findByCriteria(c, paging);
	}
	
	@Override
	public List<VoucherUserExtendPO> list(Paging paging, String voucherUserViewStatus,VoucherUserExtendPO voucher){
		Criteria c = getSessionFactory().getCurrentSession().createCriteria(VoucherUserExtendPO.class);
		if(StringUtils.isNotBlank(voucherUserViewStatus)){
			if(voucherUserViewStatus.equals(VoucherUserViewStatus.UNUSED.name())){
				validCriteria(c);
			}else if(voucherUserViewStatus.equals(VoucherUserViewStatus.USED.name())){
				usedCriteria(c);
			}else if(voucherUserViewStatus.equals(VoucherUserViewStatus.EXPIRE.name())){
				expireCriteria(c);
			}
		}
		if( voucher != null){
			if(voucher.getId() != null){
				c.add(Restrictions.eq("id", voucher.getId()));
			}
			if(voucher.getGrantType() != null && voucher.getGrantType().getId() != null){
				c.add(Restrictions.eq("grantType.id", voucher.getGrantType().getId()));
			}
			if(voucher.getVoucher() != null && StringUtils.isNotBlank(voucher.getVoucher().getId())){
				c.add(Restrictions.eq("voucher.id", voucher.getVoucher().getId()));
			}
		}
		c.addOrder(Order.desc("id"));
		
		if(voucher != null && voucher.getVoucher() != null && StringUtils.isNotBlank(voucher.getVoucher().getType())){
			c.createCriteria("voucher").add(Restrictions.eq("type", voucher.getVoucher().getType()));
			//TODO null 或者 
		}
		if(voucher != null && voucher.getUser() != null){
			Criteria  cc = c.createCriteria("user");
			if(StringUtils.isNotBlank(voucher.getUser().getUsername())){
				cc.add(Restrictions.eq("username", voucher.getUser().getUsername()));
			}
			if(null != voucher.getUser().getId()){
				cc.add(Restrictions.eq("id", voucher.getUser().getId()));
			}
		}
		return (List<VoucherUserExtendPO>) findByCriteria(c, paging);
	}
	
	@Override
	public List<VoucherTypePO> loadAllVoucherType(){
		Criteria c = getSessionFactory().getCurrentSession().createCriteria(VoucherTypePO.class);
		return c.list();
	}
	
	public List<?> findByCriteria(Criteria criteria, Paging paging) {
		Projection projection = ((CriteriaImpl)criteria).getProjection();
        if (paging.isCount()) {
            int totalCount = ((Number) criteria.setProjection(Projections.rowCount())
            			.uniqueResult()).intValue();
            paging.setTotalCount(totalCount);
        }
    	criteria.setProjection(projection);
        if (projection == null) {
            criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
        }
        List<?> results = criteria.setMaxResults(paging.getMaxResults())
				.setFirstResult(paging.getFirstResult())
				.list();
		return results;
	}
}
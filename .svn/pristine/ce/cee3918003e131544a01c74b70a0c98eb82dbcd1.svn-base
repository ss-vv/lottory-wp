package com.xhcms.lottery.commons.persist.dao.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.xhcms.commons.lang.Paging; 
import com.xhcms.commons.persist.hibernate.DaoImpl;
import com.xhcms.lottery.commons.persist.dao.BetPartnerDao;
import com.xhcms.lottery.commons.persist.entity.BetPartnerPO;

public class BetPartnerDaoImpl extends DaoImpl<BetPartnerPO> implements BetPartnerDao {

    private static final long serialVersionUID = -4107191533986896420L;

    public BetPartnerDaoImpl() {
        super(BetPartnerPO.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    public BetPartnerPO get(Long schemeId, Long userId) {
        Criteria c = createCriteria();
        c.setFetchMode("scheme", FetchMode.SELECT);
        c.add(Restrictions.eq("scheme.id", schemeId));
        c.add(Restrictions.eq("userId", userId));
		List<BetPartnerPO> list=c.list();
        return list.size()>0?(BetPartnerPO)list.get(0):null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<BetPartnerPO> findBySchemeId(long schemeId) {
        Criteria c = createCriteria();
        c.setFetchMode("scheme", FetchMode.SELECT);
        c.add(Restrictions.eq("scheme.id", schemeId));
        return c.list();
    }
    
    @SuppressWarnings("unchecked")
	@Override
    public BetPartnerPO findGroupSponsorRecord(long schemeId,
    		long sponsorId) {
    	Criteria c = createCriteria();
        c.setFetchMode("scheme", FetchMode.SELECT);
        c.add(Restrictions.eq("scheme.id", schemeId));
        c.add(Restrictions.eq("userId", sponsorId));
        c.addOrder(Order.asc("createdTime"));
        List<BetPartnerPO> list = c.list();
        if(null != list && list.size() > 0) {
        	return list.get(0);
        }
        return null;
    }

    @Override
    public List<BetPartnerPO> find(String lotteryId, Long userId, Date startDate, Date endDate, int status, Paging paging) {
    	return this.find(lotteryId, userId, startDate, endDate, status, paging, -1, -1);
    }
    
	@Override
	public List<BetPartnerPO> find(String lotteryId, Long userId,
			Date startDate, Date endDate, int status, Paging paging, int type,
			int showScheme) {
		PagingQuery<BetPartnerPO> pq = pagingQuery(paging);
        
        this.addScheme(pq, lotteryId, null , null, status, type, showScheme, null);
        
        if (userId != null && userId > 0) {
            pq.add(Restrictions.eq("userId", userId));
        }
        if (startDate != null) {
            pq.add(Restrictions.ge("createdTime", startDate));
        }
        if (endDate != null) {
            pq.add(Restrictions.lt("createdTime", endDate));
        }
        
        pq.desc("id");
        return pq.list();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<BetPartnerPO> find(String lotteryId, Long userId,
			Date startDate, Date endDate, int status, int firstResult,int maxResult, int type,
			int showScheme) {
		Criteria c = createCriteria();
		
		this.addScheme(c, lotteryId, null , null, status, type, showScheme, null);
		
		if (userId != null && userId > 0) {
			c.add(Restrictions.eq("userId", userId));
		}
		if (startDate != null) {
			c.add(Restrictions.ge("createdTime", startDate));
		}
		if (endDate != null) {
			c.add(Restrictions.lt("createdTime", endDate));
		}
		
		c.setFirstResult(firstResult).setMaxResults(maxResult).addOrder(Order.desc("id"));
		return c.list();
	}
	
	private void addScheme(Criteria c,String lotteryId,
			Date startDate, Date endDate, int status, int type,int showScheme,Long sponsorId){
		if(c!=null &&(startDate!=null ||endDate!=null|| lotteryId != null || status != -1 || type != -1 || showScheme != -1 || sponsorId!=null)){
			c.createAlias("scheme", "s");
			if(sponsorId != null){
				c.add(Restrictions.eq("s.sponsorId", sponsorId));
			}
			if(lotteryId != null){
				c.add(Restrictions.eq("s.lotteryId", lotteryId));
			}
			if(status != -1){
				c.add(Restrictions.eq("s.status", status));
			}
			if(type != -1){
				c.add(Restrictions.eq("s.type", type));
			}
			if(showScheme != -1){
				c.add(Restrictions.eq("s.showScheme", showScheme));
			}
			if (startDate != null) {
				c.add(Restrictions.ge("s.createdTime", startDate));
			}
			if (endDate != null) {
				c.add(Restrictions.lt("s.createdTime", endDate));
			}
		}
	}
	
	private void addScheme(PagingQuery<BetPartnerPO> pq,String lotteryId,
			Date startDate, Date endDate, int status, int type,int showScheme,Long sponsorId){
		if(pq!=null &&(startDate!=null ||endDate!=null|| lotteryId != null || status != -1 || type != -1 || showScheme != -1 || sponsorId!=null)){
            pq.alias("scheme", "s");
            if(sponsorId != null){
            	pq.add(Restrictions.eq("s.sponsorId", sponsorId));
            }
            if(lotteryId != null){
                pq.add(Restrictions.eq("s.lotteryId", lotteryId));
            }
            if(status != -1){
                pq.add(Restrictions.eq("s.status", status));
            }
            if(type != -1){
            	pq.add(Restrictions.eq("s.type", type));
            }
            if(showScheme != -1){
            	pq.add(Restrictions.eq("s.showScheme", showScheme));
            }
            if (startDate != null) {
                pq.add(Restrictions.ge("s.createdTime", startDate));
            }
            if (endDate != null) {
                pq.add(Restrictions.lt("s.createdTime", endDate));
            }
		}
	}
    @Override
    public Object[] sumBonus(Long schemeId, Long userId) {
        return (Object[])createQuery("select sum(betAmount), sum(winAmount) from BetPartnerPO where scheme.id = ? and userId = ?")
                .setLong(0, schemeId).setLong(1, userId).uniqueResult();
    }
    
    @Override
    public List<BetPartnerPO> find(Long userId,String lotteryId,int duration,int status, Paging paging,int type,int showScheme){
    	return find(userId, new String[]{lotteryId}, duration, status, paging, type, showScheme);
    }
    
    @Override
    public List<BetPartnerPO> find(Long userId,String[] lotteryId,int duration,int status, Paging paging,int type,int showScheme){
    	PagingQuery<BetPartnerPO> pq = pagingQuery(paging);
    	this.addScheme(pq, lotteryId, null , null, status, type, showScheme, null);
    	Calendar beginCalendar = Calendar.getInstance();
    	Calendar endCalendar = Calendar.getInstance();
    	Date begin = null;
    	Date end = null;
    	switch (duration) {
			case 0:
				beginCalendar.set(beginCalendar.get(Calendar.YEAR), beginCalendar.get(Calendar.MONTH), beginCalendar.get(Calendar.DATE), 0, 0, 0);
				endCalendar.set(endCalendar.get(Calendar.YEAR), endCalendar.get(Calendar.MONTH), endCalendar.get(Calendar.DATE), 23, 59, 59);
				break;
	
			case 1:
				beginCalendar.add(Calendar.DAY_OF_YEAR, -7);
				endCalendar.set(endCalendar.get(Calendar.YEAR), endCalendar.get(Calendar.MONTH), endCalendar.get(Calendar.DATE), 23, 59, 59);
				break;
			
			case 2:
				beginCalendar.add(Calendar.DAY_OF_YEAR, -30);
				endCalendar.set(endCalendar.get(Calendar.YEAR), endCalendar.get(Calendar.MONTH), endCalendar.get(Calendar.DATE), 23, 59, 59);
				break;
				
			default:
				break;
		}
		begin = beginCalendar.getTime();
		
    	end =endCalendar.getTime();
    	
    	if(duration == -1) {
    		begin = null;
    		end = null;
    	}
        
        if (userId != null && userId > 0) {
        	pq.add(Restrictions.eq("userId", userId));
        }
        if (begin != null) {
        	pq.add(Restrictions.ge("createdTime", begin));
        }
        if (end != null) {
        	pq.add(Restrictions.lt("createdTime", end));
        }
        pq.desc("createdTime");
    	return pq.list();
    }
    
    private void addScheme(PagingQuery<BetPartnerPO> pq,String[] lotteryId,
			Date startDate, Date endDate, int status, int type,int showScheme,Long sponsorId){
		if(pq!=null &&(startDate!=null ||endDate!=null|| lotteryId != null || status != -1 || type != -1 || showScheme != -1 || sponsorId!=null)){
            pq.alias("scheme", "s");
            if(sponsorId != null){
            	pq.add(Restrictions.eq("s.sponsorId", sponsorId));
            }
            if(lotteryId != null){
                pq.add(Restrictions.in("s.lotteryId", lotteryId));
            }
            if(status != -1){
                pq.add(Restrictions.eq("s.status", status));
            }
            if(type != -1){
            	pq.add(Restrictions.eq("s.type", type));
            }
            if(showScheme != -1){
            	pq.add(Restrictions.eq("s.showScheme", showScheme));
            }
            if (startDate != null) {
                pq.add(Restrictions.ge("s.createdTime", startDate));
            }
            if (endDate != null) {
                pq.add(Restrictions.lt("s.createdTime", endDate));
            }
		}
	}

	@Override
	public BetPartnerPO findById(long betRecordId) {
		Criteria criteria = createCriteria();
		criteria.add(Restrictions.eq("id", betRecordId));
		return (BetPartnerPO) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BetPartnerPO> findPartnersWithPager(Long betSchemeID,
			int startPosition) {
		Criteria c = createCriteria();
        c.setFetchMode("scheme", FetchMode.SELECT);
        c.add(Restrictions.eq("scheme.id", betSchemeID));
        return c.list();
	}
}

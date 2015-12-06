package com.xhcms.lottery.commons.persist.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.xhcms.commons.lang.Paging;
import com.xhcms.commons.persist.hibernate.DaoImpl;
import com.xhcms.lottery.commons.persist.dao.AdvertisementDao;
import com.xhcms.lottery.commons.persist.entity.AdvertisementPO;
@Repository
public class AdvertisementDaoImpl extends DaoImpl<AdvertisementPO> implements AdvertisementDao{

	private static final long serialVersionUID = 4205461451428844770L;

	
	public AdvertisementDaoImpl() {
		super(AdvertisementPO.class);

	}


	@SuppressWarnings("unchecked")
	@Override
	public List<AdvertisementPO> getAllAd(Paging p) {
		
		Criteria c=createCriteria();
		c.addOrder(Order.desc("updateTime"));
		c.setFirstResult((p.getPageNo()-1)*p.getMaxResults());
		c.setMaxResults(p.getMaxResults());
		return c.list();
	}


	@Override
	public Integer getCountAd() {
	      Object o= createQuery("select count(id) from AdvertisementPO").uniqueResult();
		return o!=null?Integer.parseInt(o.toString()):0;
	}


	@Override
	public void updateAd(Long id, Integer status) {
		
		createSQLQuery("update advertisement set status=:status,update_time=:time where id=:id")
		             .setParameter("status", status)
		             .setParameter("time", new Date())
		             .setParameter("id", id).executeUpdate();
		
	}


	@Override
	public AdvertisementPO getAdById(Long id) {
          
		return this.get(id);
	}


	@Override
	public void deleteAd(Object obj) {
		this.delete(obj);	
	}


	@Override
	public void updateAd(AdvertisementPO ad) {
		this.session().clear();
		this.update(ad);
		
	}


	@Override
	@SuppressWarnings("unchecked")
	public AdvertisementPO getDisplayAd() {
		 Criteria c=createCriteria();
		 c.add(Restrictions.eq("status", 0));
		 c.addOrder(Order.desc("updateTime"));
		 List<AdvertisementPO> ad=c.list();
		return ad!=null&&ad.size()>0?ad.get(0):null;
	}

}

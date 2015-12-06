package com.xhcms.lottery.commons.persist.dao.impl;

import java.io.Serializable;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder.In;

import org.hibernate.Criteria;
import org.hibernate.Session; 
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import com.xhcms.commons.persist.hibernate.DaoImpl;
import com.xhcms.lottery.commons.persist.dao.PublishBetSchemeDao;
import com.xhcms.lottery.commons.persist.entity.HX_userPO;
import com.xhcms.lottery.commons.persist.entity.PublishBetSchemePO;

@Transactional
public class PublishBetSchemeDaoImpl extends DaoImpl<PublishBetSchemePO> implements PublishBetSchemeDao {

	private static final long serialVersionUID = 1L;

	public PublishBetSchemeDaoImpl() {
        super(PublishBetSchemePO.class);
    }
	
	@Override
	public String savePublishScheme(PublishBetSchemePO publishBetSchemePO) {
		if(publishBetSchemePO != null){
			Session session = getSessionFactory().getCurrentSession();
			Serializable id = session.save(publishBetSchemePO);
			return id.toString();
		}
		return null;
	}

	@Override
	public PublishBetSchemePO getPublishBetSchemePOByBetSchemeId(
			long betSchemeId) {
		Criteria c = createCriteria();
		c.add(Restrictions.eq("betSchemeId", betSchemeId));
		return (PublishBetSchemePO) c.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PublishBetSchemePO> getPublishBetSchemePOByUserId(long userId) {
		Criteria c = createCriteria();
		c.add(Restrictions.eq("userId", userId));
		return c.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PublishBetSchemePO> getPublishBetSchemePOByUserIdAndPages(long userId, String pages, int maxResult,String groupid) {
		int frist = Integer.valueOf(pages);
		String hql = "from PublishBetSchemePO p where p.userId=:userId and p.groupid=:groupid group by p.betSchemeId order by p.createDate desc";
		List<PublishBetSchemePO> pos = createQuery(hql).setParameter("userId", userId).setParameter("groupid", groupid).setFirstResult(frist).setMaxResults(maxResult).list();
		return pos;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<PublishBetSchemePO> getPubSchemeIsOthersWithPages(long userId, long daVId,String pages,int maxResult,String groupid) {
		int frist = Integer.valueOf(pages);
		String hql = "from PublishBetSchemePO p where p.userId not in (:userId,:daVId) and p.groupid=:groupid group by p.betSchemeId order by  p.createDate desc";
		List<PublishBetSchemePO> pos = createQuery(hql).setParameter("userId", userId).setParameter("daVId", daVId).setParameter("groupid", groupid).setFirstResult(frist).setMaxResults(maxResult).list();
		return pos;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PublishBetSchemePO> getPublishBetSchemePOByGroupId(
			String groupid) {
		Criteria c = createCriteria();
		c.add(Restrictions.eq("groupid", groupid));
		return c.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PublishBetSchemePO> getPublishBetSchemePOByMatchId(
			long matchId) {
		Criteria c = createCriteria();
		c.add(Restrictions.eq("matchId", matchId));
		return c.list();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<PublishBetSchemePO> getPublishBetSchemePOsBySchemeId(
			long betSchemeId) {
		Criteria c = createCriteria();
		c.add(Restrictions.eq("betSchemeId", betSchemeId));
		return c.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PublishBetSchemePO> getPublishBetSchemePOByOtherParams(
			long userId, String groupid, long betSchemeId) {
		Criteria c = createCriteria();
		c.add(Restrictions.eq("userId", userId));
		c.add(Restrictions.eq("groupid", groupid));
		c.add(Restrictions.eq("betSchemeId", betSchemeId));
		return c.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public PublishBetSchemePO getPublishSchemeByMathId(long matchId, long schemeId, String groupid) {
		Criteria c = createCriteria();
		c.add(Restrictions.eq("matchId", matchId));
		c.add(Restrictions.eq("groupid", groupid));
		c.add(Restrictions.eq("betSchemeId", schemeId));
		List<PublishBetSchemePO> pos = c.list();
		if(pos != null && !pos.isEmpty()){
			return pos.get(0);
		}
		return null;
	}

	@Override
	public PublishBetSchemePO findById(Long id) {
		return get(id);
	}

	
}

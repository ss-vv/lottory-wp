package com.xhcms.lottery.commons.persist.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import com.xhcms.commons.persist.hibernate.DaoImpl;
import com.xhcms.lottery.commons.persist.dao.GoldSchemeDao;
import com.xhcms.lottery.commons.persist.entity.GoldSchemePO;

public class GoldSchemeDaoImpl extends DaoImpl<GoldSchemePO> implements GoldSchemeDao {

	 public GoldSchemeDaoImpl() {
	        super(GoldSchemePO.class);
	    }
	@Override
	@Transactional
	public long addGoldScheme(GoldSchemePO goldScheme) {
		Session session = getSessionFactory().getCurrentSession();
		Serializable id = session.save(goldScheme);
		return Long.parseLong(id.toString());
	}

	@Override
	@Transactional
	public void deteleGoldSchemeByGoldSchemeId(String goldSchemeId) {
		deleteById(goldSchemeId);
	}

	@Override
	@Transactional
	public void deleteGoldSchemeById(String id) {
		deleteById(id);
		
	}

	@Override
	@Transactional
	public void updateGoldScheme(GoldSchemePO goldScheme) {
		createQuery("update GoldSchemePO set goldSchemeId=:goldSchemeId,createtime=:createtime where id=:id")
		.setString("goldSchemeId", goldScheme.getGoldSchemeId()).setDate("createtime", goldScheme.getCreatetime()).setLong("id", goldScheme.getId()).executeUpdate();

	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<GoldSchemePO> findAllGoldScheme() {
	
		String hql="from GoldSchemePO where 1=1";
		return createQuery(hql).list();
	}

	@Override
	@Transactional
	public GoldSchemePO findGoldSchemeByGoldSchemeId(String goldSchemeId) {
		return (GoldSchemePO) createCriteria().add(Restrictions.eq("goldSchemeId", goldSchemeId)).uniqueResult();
		
	}

	@Override
	@Transactional
	public GoldSchemePO findGoldSchemeById(String id) {
		return  (GoldSchemePO) createCriteria().add(Restrictions.eq("id", id)).uniqueResult();
		
	}

}

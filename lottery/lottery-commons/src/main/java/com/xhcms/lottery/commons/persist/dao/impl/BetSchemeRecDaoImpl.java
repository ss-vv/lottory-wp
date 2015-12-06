package com.xhcms.lottery.commons.persist.dao.impl;

import java.util.Collection;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.xhcms.commons.persist.hibernate.DaoImpl;
import com.xhcms.lottery.commons.persist.dao.BetSchemeRecDao;
import com.xhcms.lottery.commons.persist.entity.BetSchemeRecPO;

@Repository
@SuppressWarnings("unchecked")
public class BetSchemeRecDaoImpl extends DaoImpl<BetSchemeRecPO> implements BetSchemeRecDao {

    private static final long serialVersionUID = -793097112883708694L;

    public BetSchemeRecDaoImpl() {
        super(BetSchemeRecPO.class);
    }

	@Override
	public List<BetSchemeRecPO> findBySchemaIds(Collection<Long> ids) {
		return createCriteria().add(Restrictions.in("id", ids)).list();
	}

	@Override
	public String getPlayType(Long id) {
		Object o = createQuery("select passTypeIds from BetSchemeRecPO r where r.id = :id ")
		.setParameter("id", id).uniqueResult();
		return (String) o;
	}

	@Override
	public List<BetSchemeRecPO> findBySchemaId(Long id) {

		return createCriteria().add(Restrictions.eq("id", id)).list();
	}
	@Override
	public String getSponsorById(Long id) {
		Object o = createQuery("select sponsor from BetSchemeRecPO b where b.id=:id").setParameter("id", id).uniqueResult();
		return o!=null?(String)o:"";
	}
}
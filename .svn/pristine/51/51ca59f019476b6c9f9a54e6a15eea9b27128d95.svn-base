package com.xhcms.lottery.commons.persist.dao.impl;

import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.LockMode;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.xhcms.commons.lang.Paging;
import com.xhcms.commons.persist.hibernate.DaoImpl;
import com.xhcms.lottery.commons.persist.dao.AccountDao;
import com.xhcms.lottery.commons.persist.entity.AccountPO;

public class AccountDaoImpl extends DaoImpl<AccountPO> implements AccountDao {

    private static final long serialVersionUID = -8866968344416859253L;

    public AccountDaoImpl() {
        super(AccountPO.class);
    }
    
    @SuppressWarnings("unchecked")
	@Override
    public List<Long> listIds(){
    	return createQuery("select id from AccountPO").list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<AccountPO> findByIds(Collection<Long> ids) {
        return createQuery("from AccountPO where id in (:ids)").setParameterList("ids", ids).list();
    }

    @Override
    public List<AccountPO> find(Paging paging, String username) {
        PagingQuery<AccountPO> pq = pagingQuery(paging);
        if(StringUtils.isNotBlank(username)){
            pq.add(Restrictions.like("username", username, MatchMode.ANYWHERE));
        }
        return pq.asc("id").list();
    }

	@Override
	public AccountPO get(Long ltUserId, LockMode upgrade) {
		// TODO Auto-generated method stub
		return (AccountPO) session().get(AccountPO.class, ltUserId,upgrade);
	}

}

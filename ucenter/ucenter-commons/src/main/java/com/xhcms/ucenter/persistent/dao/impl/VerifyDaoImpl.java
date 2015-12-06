package com.xhcms.ucenter.persistent.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.xhcms.commons.lang.Paging;
import com.xhcms.commons.persist.hibernate.DaoImpl;
import com.xhcms.ucenter.lang.UCEntityType;
import com.xhcms.ucenter.persistent.dao.IVerifyDao;
import com.xhcms.ucenter.persistent.entity.VerifyPO;

/**
 * Title:
 * 
 * @author jiajiancheng
 * @version 1.0
 */
public class VerifyDaoImpl extends DaoImpl<VerifyPO> implements IVerifyDao {

	private static final long serialVersionUID = -8926472682353741253L;

	public VerifyDaoImpl() {
		super(VerifyPO.class);
	}

	@Override
	public VerifyPO get(long uid, int type) {
		Criteria c = createCriteria();
		c.add(Restrictions.eq("uid", uid));
		c.add(Restrictions.eq("type", type));
		return (VerifyPO) c.uniqueResult();
	}

    @Override
    public List<VerifyPO> findByRealnameStatus(Paging paging, int realnameStatus) {
        PagingQuery<VerifyPO> pq = pagingQuery(paging);
        pq.add(Restrictions.eq("realnameStatus", realnameStatus));
        return pq.list();
    }

    @Override
    public List<VerifyPO> findByIdnumberStatus(Paging paging, int idnumberStatus) {
        PagingQuery<VerifyPO> pq = pagingQuery(paging);
        pq.add(Restrictions.eq("idnumberStatus", idnumberStatus));
        return pq.list();
    }
    @Override
	public VerifyPO findPhoneNumberByVerifyCode(String verifyCode) {
		Criteria c = createCriteria();
		c.add(Restrictions.eq("code", verifyCode));
		c.add(Restrictions.eq("type", UCEntityType.VERIFY_MOBILE));
		return (VerifyPO) c.uniqueResult();
	}

	@Override
	public VerifyPO findByVerifyCode(String verifyCode) {
		Criteria c = createCriteria();
		c.add(Restrictions.eq("code", verifyCode));
		return (VerifyPO) c.uniqueResult();
	}

	@Override
	public VerifyPO get(String target, int type) {
		Criteria c = createCriteria();
		c.add(Restrictions.eq("target", target));
		c.add(Restrictions.eq("type", type));
		return (VerifyPO) c.uniqueResult();
	}
    
    

}

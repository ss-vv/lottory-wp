package com.xhcms.lottery.commons.persist.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Restrictions;

import com.xhcms.commons.persist.hibernate.DaoImpl;
import com.xhcms.lottery.commons.persist.dao.PlayDao;
import com.xhcms.lottery.commons.persist.entity.PlayPO;

public class PlayDaoImpl extends DaoImpl<PlayPO> implements PlayDao {

    private static final long serialVersionUID = 7590894274703421041L;

    public PlayDaoImpl() {
        super(PlayPO.class);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<PlayPO> findPlay(){
        return createCriteria().list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<PlayPO> find(String lotteryId) {
        Criteria c = createCriteria();
        if (StringUtils.isNotBlank(lotteryId)) {
            c.add(Restrictions.eq("lotteryId", lotteryId));
        }
        return c.list();
    }

    @Override
    public PlayPO getWithPassType(String id) {
        Criteria c = createCriteria();
        c.add(Restrictions.eq("id", id));
        c.setFetchMode("passTypes", FetchMode.JOIN);
        return (PlayPO) c.uniqueResult();
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<PlayPO> findCustomMadePlay(String lotteryId) {
        Criteria c = createCriteria();
        if (StringUtils.isNotBlank(lotteryId)) {
            c.add(Restrictions.ne("lotteryId", lotteryId));
        }
        return c.list();
    }
}

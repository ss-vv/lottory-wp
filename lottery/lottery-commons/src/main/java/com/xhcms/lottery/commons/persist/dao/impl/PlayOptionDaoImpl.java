package com.xhcms.lottery.commons.persist.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.xhcms.commons.persist.hibernate.DaoImpl;
import com.xhcms.lottery.commons.persist.dao.PlayOptionDao;
import com.xhcms.lottery.commons.persist.entity.PlayOptionPO;

public class PlayOptionDaoImpl extends DaoImpl<PlayOptionPO> implements PlayOptionDao {

    private static final long serialVersionUID = 7291797507301807781L;

    public PlayOptionDaoImpl() {
        super(PlayOptionPO.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<PlayOptionPO> find(String playId) {
        Criteria c = createCriteria();
        if (playId != null) {
            c.add(Restrictions.eq("playId", playId));
        }
        return c.list();
    }

}

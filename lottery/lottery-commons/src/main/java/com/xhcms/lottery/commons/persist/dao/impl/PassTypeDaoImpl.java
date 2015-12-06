package com.xhcms.lottery.commons.persist.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;

import com.xhcms.commons.persist.hibernate.DaoImpl;
import com.xhcms.lottery.commons.persist.dao.PassTypeDao;
import com.xhcms.lottery.commons.persist.entity.PassTypePO;

public class PassTypeDaoImpl extends DaoImpl<PassTypePO> implements PassTypeDao {

    private static final long serialVersionUID = -8055578276272878556L;

    public PassTypeDaoImpl() {
        super(PassTypePO.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<PassTypePO> list() {
        Criteria c = createCriteria();
        c.addOrder(Order.asc("index"));
        return c.list();
    }

}

package com.xhcms.lottery.commons.persist.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.xhcms.commons.lang.Paging;
import com.xhcms.lottery.commons.data.Order;
import com.xhcms.lottery.commons.persist.dao.OrderDao;
import com.xhcms.lottery.commons.persist.dao.UserDao;
import com.xhcms.lottery.commons.persist.entity.OrderPO;
import com.xhcms.lottery.commons.persist.entity.UserPO;
import com.xhcms.lottery.commons.persist.service.OrderService;

public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private UserDao userDao;

    @Override
    @Transactional(readOnly = true)
    public void listOrder(Paging paging, String username, int type, Date from, Date to) {
        List<OrderPO> data = orderDao.list(paging, username, type, from, to);
        List<Order> results = new ArrayList<Order>(data.size());

        HashMap<Long, UserPO> users = new HashMap<Long, UserPO>();
        for (OrderPO po : data) {
            Order o = new Order();
            BeanUtils.copyProperties(po, o);
            
            users.put(po.getUserId(), null);
            results.add(o);
        }

        if(users.size() > 0){
            for (UserPO upo : userDao.find(users.keySet())) {
                users.put(upo.getId(), upo);
            }
    
            for (Order o : results) {
                UserPO upo = users.get(o.getUserId());
                if (upo != null) {
                    o.setRealname(upo.getRealname());
                }
            }
        }
        paging.setResults(results);
    }

    @Override
    @Transactional(readOnly = true)
    public Order getOrder(Long id) {
        OrderPO po = orderDao.get(id);
        Order o = new Order();
        if(po != null){
            BeanUtils.copyProperties(po, o);
        }
        return o;
    }

	@Override
	public OrderPO getByRelated(int type, Long relatedId,Long userId) {
		return orderDao.getByTypeAndRelated(type, relatedId,userId);
	}

}

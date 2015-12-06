package com.xhcms.lottery.account.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.xhcms.commons.lang.Paging;
import com.xhcms.lottery.account.service.AccountQueryService;
import com.xhcms.lottery.commons.data.Order;
import com.xhcms.lottery.commons.data.Recharge;
import com.xhcms.lottery.commons.data.Withdraw;
import com.xhcms.lottery.commons.persist.dao.BetPartnerDao;
import com.xhcms.lottery.commons.persist.dao.OrderDao;
import com.xhcms.lottery.commons.persist.dao.RechargeDao;
import com.xhcms.lottery.commons.persist.dao.WithdrawDao;
import com.xhcms.lottery.commons.persist.entity.OrderPO;
import com.xhcms.lottery.commons.persist.entity.RechargePO;
import com.xhcms.lottery.commons.persist.entity.WithdrawPO;
import com.xhcms.lottery.commons.persist.service.GetPresetSchemeService;
import com.xhcms.lottery.utils.PagingUtils;

@Transactional
public class AccountQueryServiceImpl implements AccountQueryService {
    @Autowired
    private BetPartnerDao betPartnerDao;
    @Autowired
    private WithdrawDao withdrawDao;
    @Autowired
    private GetPresetSchemeService getPresetSchemeService;
    @Autowired
    private RechargeDao rechargeDao;
    @Autowired
    private OrderDao orderDao;

    
    @Override
    @Transactional(readOnly = true)
    public BigDecimal[] sumBonus(Long schemeId, Long userId) {
        BigDecimal[] sums = { BigDecimal.ZERO, BigDecimal.ZERO };
        Object[] o = betPartnerDao.sumBonus(schemeId, userId);

        if (o != null) {
            for (int i = 0; i < 2; i++) {
                if (o[i] != null) {
                    sums[i] = new BigDecimal(o[i].toString());
                }
            }
        }
        return sums;
    }

    @Override
    @Transactional(readOnly = true)
    public void listWithdraw(Long userId, Date begin, Date end, int status, Paging paging) {
        if(end != null){
            end = DateUtils.addDays(end, 1);
        }
        List<WithdrawPO> list = withdrawDao.find(userId, null, begin, end, status, paging);
        List<Withdraw> results = new ArrayList<Withdraw>(list.size());
        Withdraw wh = null;
        for (WithdrawPO po : list) {
        	wh = new Withdraw();
            BeanUtils.copyProperties(po, wh);
            results.add(wh);
        }
        paging.setResults(results);
    }

    @Override
    @Transactional(readOnly = true)
    public void listRecharge(Long userId, Date begin, Date end, int status, Paging paging) {
        if(end != null){
            end = DateUtils.addDays(end, 1);
        }
        List<RechargePO> list = rechargeDao.find(userId, null, begin, end, status, paging);
        List<Recharge> results = new ArrayList<Recharge>();
        Recharge rh = null;
        for (RechargePO po : list) {
            rh = new Recharge();
            BeanUtils.copyProperties(po, rh);
            results.add(rh);
        }
        paging.setResults(results);
    }
    
	@Override
	@Transactional(readOnly = true)
	public void listJournal(Paging paging, Long userId,int type, Date from, Date to) {
		List<OrderPO> orderPOs=orderDao.list(paging, userId, type, from, to);
		if(orderPOs!=null)
			PagingUtils.makeCopyAndSetPaging(orderPOs, paging, Order.class);
	}

}

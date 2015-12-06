package com.xhcms.lottery.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.xhcms.lottery.commons.data.Recharge;
import com.xhcms.lottery.commons.data.Win;
import com.xhcms.lottery.commons.data.Withdraw;
import com.xhcms.lottery.commons.persist.dao.RechargeDao;
import com.xhcms.lottery.commons.persist.dao.WinDao;
import com.xhcms.lottery.commons.persist.dao.WithdrawDao;
import com.xhcms.lottery.commons.persist.entity.RechargePO;
import com.xhcms.lottery.commons.persist.entity.WinPO;
import com.xhcms.lottery.commons.persist.entity.WithdrawPO;
import com.xhcms.lottery.service.UserDetailQueryService;

public class UserDetailQueryServiceImpl implements UserDetailQueryService {

	@Autowired
	private WithdrawDao withdrawDao;
	
	@Autowired
	private RechargeDao rechargeDao;

	@Autowired
	private WinDao winDao;

	@Override
	@Transactional(readOnly = true)
	public List<Withdraw> listWithdraw(Long userId, int firstResult) {
		List<WithdrawPO> list = withdrawDao.findWithPage(userId,firstResult);
        List<Withdraw> results = new ArrayList<Withdraw>(list.size());
        Withdraw wh = null;
        for (WithdrawPO po : list) {
        	wh = new Withdraw();
            BeanUtils.copyProperties(po, wh);
            results.add(wh);
        }
        return results;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Recharge> listRecharge(Long userId, int firstResult) {
		List<RechargePO> list = rechargeDao.findWithPage(userId,firstResult);
        List<Recharge> results = new ArrayList<Recharge>();
        Recharge rh = null;
        for (RechargePO po : list) {
            rh = new Recharge();
            BeanUtils.copyProperties(po, rh);
            results.add(rh);
        }
        return results;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Win> listWin(Long userId, int firstResult) {
		List<WinPO> list = winDao.findWithPage(userId, firstResult);
		List<Win> rets = new ArrayList<Win>(list.size());
		Win win = null;
		for (WinPO po : list) {
			win = new Win();
			BeanUtils.copyProperties(po, win);
			rets.add(win);
		}
		return rets;
	}

}

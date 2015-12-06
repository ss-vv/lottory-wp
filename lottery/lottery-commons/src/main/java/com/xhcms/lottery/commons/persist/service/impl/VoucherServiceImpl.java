package com.xhcms.lottery.commons.persist.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.xhcms.commons.lang.Paging;
import com.xhcms.lottery.commons.data.Voucher;
import com.xhcms.lottery.commons.persist.dao.VoucherDao;
import com.xhcms.lottery.commons.persist.entity.VoucherPO;
import com.xhcms.lottery.commons.persist.service.VoucherService;
import com.xhcms.lottery.utils.POUtils;

/**
 * 优惠劵类型
 * @author Wang Lei
 *
 */
public class VoucherServiceImpl implements VoucherService {
	@Autowired
	private VoucherDao voucherDao;

	@Override
	@Transactional(readOnly=true)
	public Map<String, VoucherPO> listByIds(Collection<String> ids) {
		List<Object[]> vouchers =voucherDao.listByIds(ids);
		Map<String, VoucherPO> voucherMaps = new HashMap<String, VoucherPO>();
		for(Object[]  obj : vouchers){
			voucherMaps.put(obj[0].toString(), (VoucherPO)obj[1]);
		}
		return voucherMaps;
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<Voucher> list(Paging paging, Voucher voucher){
		List<Voucher> list = new ArrayList<Voucher>();
		List<VoucherPO> listPO = voucherDao.list(paging, voucher);
		if(listPO != null && !listPO.isEmpty()){
			list = POUtils.copyBeans(listPO , Voucher.class);
		}
		paging.setResults(list);
		return list;
	}

	@Override
	@Transactional(readOnly=true)
	public List<Voucher> loadAll(){
		return POUtils.copyBeans(voucherDao.list(),Voucher.class);
	}
}

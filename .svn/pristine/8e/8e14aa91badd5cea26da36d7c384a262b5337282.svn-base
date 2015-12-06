package com.xhcms.lottery.commons.persist.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.xhcms.commons.lang.Paging;
import com.xhcms.lottery.commons.data.Voucher;
import com.xhcms.lottery.commons.persist.entity.VoucherPO;

public interface VoucherService {
	
	public Map<String, VoucherPO> listByIds(Collection<String> ids);

	List<Voucher> loadAll();

	List<Voucher> list(Paging paging, Voucher voucher);
}

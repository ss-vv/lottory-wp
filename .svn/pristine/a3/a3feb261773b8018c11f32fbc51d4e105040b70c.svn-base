package com.xhcms.lottery.commons.persist.dao;

import java.util.Collection;
import java.util.List;

import com.xhcms.commons.lang.Paging;
import com.xhcms.commons.persist.Dao;
import com.xhcms.lottery.commons.data.Voucher;
import com.xhcms.lottery.commons.persist.entity.VoucherPO;

public interface VoucherDao extends Dao<VoucherPO>{

	List<Object[]> listByIds(Collection<String> ids);

	List<VoucherPO> list(Paging paging, Voucher voucher);

}

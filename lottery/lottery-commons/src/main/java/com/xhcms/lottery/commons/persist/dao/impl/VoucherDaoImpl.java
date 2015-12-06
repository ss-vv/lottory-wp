package com.xhcms.lottery.commons.persist.dao.impl;

import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Restrictions;

import com.xhcms.commons.lang.Paging;
import com.xhcms.commons.persist.hibernate.DaoImpl;
import com.xhcms.lottery.commons.data.Voucher;
import com.xhcms.lottery.commons.persist.dao.VoucherDao;
import com.xhcms.lottery.commons.persist.entity.VoucherPO;

/***
 * 优惠详情dao
 * @author Wang Lei
 *
 */
@SuppressWarnings("unchecked")
public class VoucherDaoImpl extends DaoImpl<VoucherPO> implements VoucherDao {
	private static final long serialVersionUID = 1L;

	VoucherDaoImpl(){
		super(VoucherPO.class);
	}
	
	/**
	 * 使用id集合返回优惠劵list
	 */
	@Override
	public List<Object[]> listByIds(Collection<String> ids){
		return createQuery("select vcp.id,vcp from VoucherPO as vcp where id in (:ids)").setParameterList("ids", ids).list();
	}
	
	/***
	 * 分页查询优惠劵
	 * @param paging
	 * @param voucher
	 * @return
	 */
	@Override
	public List<VoucherPO> list(Paging paging, Voucher voucher){
		PagingQuery<VoucherPO> q = pagingQuery(paging);
		if(voucher != null){
			if(StringUtils.isNotBlank(voucher.getType())){
				q.add(Restrictions.eq("type", voucher.getType()));
			}
		}
		return q.list();
	}
}

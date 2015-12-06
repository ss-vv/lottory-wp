package com.xhcms.lottery.commons.persist.dao.impl;


import java.util.Date;
import java.util.List;

import org.hibernate.Query;

import com.xhcms.commons.persist.hibernate.DaoImpl;
import com.xhcms.lottery.commons.persist.dao.PrintableFileDao;
import com.xhcms.lottery.commons.persist.entity.PrintableFilePO;

@SuppressWarnings("deprecation")
public class PrintableFileDaoImpl extends DaoImpl<PrintableFilePO> implements PrintableFileDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3913835433138300324L;

	@SuppressWarnings("unchecked")
	@Override
	public List<PrintableFilePO> findByDate(String lotteryPlatformId, Date time) {
		String hql = "select a from PrintableFilePO a "
				+ " where lotteryPlatformId=:lotteryPlatformId "
				+ " 	and createTime >= :from" 
				+ " order by a.createTime desc";
		Query query = createQuery(hql);
		query.setDate("from", time);
		query.setString("lotteryPlatformId", lotteryPlatformId);
		return query.list();
	}
	
	@Override
	public PrintableFilePO findById(long fileId) {
		String hql = "select a from PrintableFilePO a "
				+ " where id=:fileId ";
		Query query = createQuery(hql);
		query.setLong("fileId", fileId);
		return (PrintableFilePO) query.uniqueResult();
	}
}

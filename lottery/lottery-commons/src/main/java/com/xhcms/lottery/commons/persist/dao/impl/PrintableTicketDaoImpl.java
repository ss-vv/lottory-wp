package com.xhcms.lottery.commons.persist.dao.impl;


import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;

import com.xhcms.commons.lang.Paging;
import com.xhcms.commons.persist.hibernate.DaoImpl;
import com.xhcms.lottery.commons.persist.dao.PrintableTicketDao;
import com.xhcms.lottery.commons.persist.entity.PrintableTicketPO;
import com.xhcms.lottery.lang.Constants;
import com.xhcms.lottery.lang.EntityStatus;
import com.xhcms.lottery.lang.LotteryId;

@SuppressWarnings("deprecation")
public class PrintableTicketDaoImpl extends DaoImpl<PrintableTicketPO> implements PrintableTicketDao {

	private static final long serialVersionUID = -4923681616770876760L;

	@Override
	public List<Object> findWithPage(Paging paging, Date begin, Date end,
			int status, String lotteryId, Long schemeId, Long ticketId,String playId, String lotteryPlatformId) {
		if(null!=paging&&status!=-1&&StringUtils.isNotBlank(lotteryId)&&StringUtils.isNotBlank(playId)){
			String hql = "from TicketPO a ,PrintableTicketPO b ,BetSchemePO c "
					+ "where "
					+ "a.id=b.ticketId "
					+ " and a.schemeId=c.id "
					+ "and a.status=:status "
					+ " and c.lotteryId=:lotteryId"
					+ " and a.lotteryPlatformId=:lotteryPlatformId";
			StringBuilder critea = makeCritea(begin, end, lotteryId, schemeId,
					ticketId,playId);
	        String order=" order by c.machineOfftime asc,a.money desc,a.id asc ";
	        if(EntityStatus.TICKET_EXPORTED==status){
	        	order= " order by a.id asc ";
	        }
			hql=hql+critea+order;
			
			Query query = createQuery(hql);
			makeParameters(begin, end, status, schemeId, ticketId, playId,
					query,lotteryId, lotteryPlatformId);
			query.setFirstResult(paging.getFirstResult());
			query.setMaxResults(paging.getMaxResults());
			Long totalCount=0l;
			if(paging.isCount()){
				
				String countHql="select count(*)  "+hql;
				Query countQuery = createQuery(countHql);
				makeParameters(begin, end, status, schemeId, ticketId, playId,
						countQuery,lotteryId, lotteryPlatformId);
				totalCount=(Long) countQuery.uniqueResult();
				paging.setTotalCount(totalCount.intValue());
			}
			List result=null;
			if (totalCount > paging.getFirstResult()) {
				result = query.list();
				paging.setResults(result);
			}
			return result;
		}
		return null;
	}

	private void makeParameters(Date begin, Date end, int status,
			Long schemeId, Long ticketId, String playId, Query query,String lotteryId, String lotteryPlatformId) {
		query.setParameter("status", status);
		query.setParameter("lotteryId", lotteryId);
		query.setParameter("lotteryPlatformId", lotteryPlatformId);
		if(!StringUtils.equals(playId, Constants.ALL_PLAY_ID)){
			query.setParameter("playId", playId);
		}
		
		if(null != ticketId && ticketId > 0) {
			query.setParameter("ticketId", ticketId);
		}
		if(null != schemeId && schemeId > 0) {
			query.setParameter("schemeId", schemeId);
		}
		if (begin != null) {
			query.setParameter("begin", begin);
			
		}
		if (end != null) {
			query.setParameter("end", end);
			
		}
		
	}

	private StringBuilder makeCritea(Date begin, Date end, String lotteryId,
			Long schemeId, Long ticketId, String playId) {
		StringBuilder critea=new StringBuilder();
		if(null != ticketId && ticketId > 0) {
			critea.append(" and a.id=:ticketId ");
		}
		if(null != schemeId && schemeId > 0) {
			critea.append(" and a.schemeId=:schemeId ");
		}
		if (begin != null) {
			critea.append(" and a.createdTime>=:begin ");
		}
		if (end != null) {
			critea.append(" and a.createdTime<=:end ");
		}
		
		if(lotteryId.equals(LotteryId.JCZQ.name())) {
			critea.append(" and a.odds is not null ");
		} else if(lotteryId.equals(LotteryId.JCLQ.name())) {
			critea.append(" and a.odds is not null ");
		}
		if(!StringUtils.equals(playId, Constants.ALL_PLAY_ID)){
			critea.append("and a.playId = :playId ") ;
		}
		
		return critea;
	}

	@Override
	public List<PrintableTicketPO> findByIds(List<Long> printableTicketIds, String lotteryPlatformId, int status) {
		String andStatus="";
		if(status > 0){
			andStatus = " and lt.status="+status;
		}
		String hql="select pt from PrintableTicketPO pt,TicketPO lt "
				+ "where pt.ticketId=lt.id " + andStatus
				+ " and lt.lotteryPlatformId=:lotteryPlatformId "
				+ " and pt.ticketId in (:printableTicketIds)";
		Query query = createQuery(hql);
		query.setParameterList("printableTicketIds", printableTicketIds);
		query.setString("lotteryPlatformId", lotteryPlatformId);
		return query.list();
	}

	@Override
	public List<Object> findTicketByFileId(long fileId) {
		String hql = "select lt,pt from PrintableTicketPO pt,TicketPO lt,PrintableTicketFilePO ptf "
				+ " where pt.ticketId=lt.id and ptf.printableTicketId=pt.id "
				+ " and ptf.fileId=:fileId"
				+ " order by ptf.id asc";
		Query query = createQuery(hql);
		query.setLong("fileId", fileId);
		return query.list();
	}

	@Override
	public List<PrintableTicketPO> findByTicketIds(Set<Long> ticketIds) {
		String hql="from PrintableTicketPO "
				+ "where "
				+ "ticketId in (:ticketIds)";
		Query query = createQuery(hql);
		query.setParameterList("ticketIds", ticketIds);
		return query.list();
	}
}
package com.xhcms.lottery.commons.persist.dao.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

import com.xhcms.commons.lang.Paging;
import com.xhcms.commons.persist.hibernate.DaoImpl;
import com.xhcms.lottery.commons.persist.dao.TicketDao;
import com.xhcms.lottery.commons.persist.entity.PrintableFilePO;
import com.xhcms.lottery.commons.persist.entity.TicketPO;
import com.xhcms.lottery.lang.EntityStatus;
import com.xhcms.lottery.lang.LotteryId;
import com.xhcms.lottery.lang.LotteryPlatformId;
import com.xhcms.lottery.lang.PlayType;
import com.xhcms.lottery.lang.PresetAward;

public class TicketDaoImpl extends DaoImpl<TicketPO> implements TicketDao {

    private static final int _15_DAY = 15;
	private static final long serialVersionUID = -8660228214655379590L;

    public TicketDaoImpl() {
        super(TicketPO.class);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<TicketPO> findByScheme(Long schemeId, int status) {
        Criteria c = createCriteria().add(Restrictions.eq("schemeId", schemeId));
        if (status != -1) {
            c.add(Restrictions.eq("status", status));
        }
        return c.list();
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<TicketPO> findByScheme(Long schemeId, List<Integer> status) {
    	Criteria c = createCriteria().add(Restrictions.eq("schemeId", schemeId));
    	if (null != status && status.size() > 0) {
    		c.add(Restrictions.in("status", status));
    	} else {
    		return null;
    	}
    	return c.list();
    }

    @Override
    public List<TicketPO> find(List<Long> ids, Date startDate, Date endDate, int status, Paging paging) {
        PagingQuery<TicketPO> pq = pagingQuery(paging);
        if (ids != null && ids.size() > 0) {
            pq.add(Restrictions.in("schemeId", ids));
        }
        if (startDate != null) {
            pq.add(Restrictions.ge("requestTime", startDate));
        }
        if (endDate != null) {
            pq.add(Restrictions.le("requestTime", endDate));
        }
        if (status != -1) {
            pq.add(Restrictions.le("status", status));
        }
        return pq.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Long> findByStatus(int status, Date from, Date to) {
        Criteria c = createCriteria()
                .setProjection(Property.forName("id"))
                .add(Restrictions.eq("status", status));
        if(from != null){
            c.add(Restrictions.ge("createdTime", from));
        }
        if(to != null){
            c.add(Restrictions.le("createdTime", to));
        }
        return c.list();
    }
    
    @Override
    public void updateStatusByPresetTicket(Long schemeId, int oldStatus, int newStatus, String message) {
        createQuery("update TicketPO set status=:newStatus, message=:msg, FinalStateTime=current_timestamp() where status=:oldStatus and schemeId=:schemeId and isPresetAward=" + PresetAward.Is_PresetAward.getValue())
            .setInteger("newStatus", newStatus).setString("msg", message)
            .setLong("schemeId", schemeId)
            .setInteger("oldStatus", oldStatus)
            .executeUpdate();
    }
    
    @SuppressWarnings("unchecked")
	@Override
    public List<Long> findByStatusTest(int status, Date from, Date to) {
    	List<Long> ids=createQuery("select t.id from TicketPO as t,BetSchemePO as s where t.schemeId=s.id and s.status=? and s.saleStatus=?" +
    			" and s.createdTime>? ")
    			.setInteger(0, status).setInteger(1, EntityStatus.SCHEME_SETTLEMENT).setTimestamp(2, from).list();
         return ids;
    }
    
    @Override
    public void updateStatus(Collection<Long> ids, int oldStatus, int newStatus, String message) {
        createQuery("update TicketPO set status=:newStatus, message=:msg where status=:oldStatus and id in (:ids)")
            .setInteger("newStatus", newStatus).setString("msg", message)
            .setInteger("oldStatus", oldStatus).setParameterList("ids", ids)
            .executeUpdate();
    }
    
    @Override
    public void updateLotteryPlatformId(List<Long> printableTicketIds, String exportToShitidian) {
    	createQuery("update TicketPO set lotteryPlatformId=:exportToShitidian  where id in (:ids)")
    	.setParameter("exportToShitidian", exportToShitidian).setParameterList("ids", printableTicketIds)
    	.executeUpdate();
    }
    
    @Override
    public void updateStatusByScheme(Collection<Long> sids, int oldStatus, int newStatus, String message) {
        createQuery("update TicketPO set status=:newStatus, message=:msg,FinalStateTime=:finalTime where status=:oldStatus and schemeId in (:sids)")
        .setInteger("newStatus", newStatus).setString("msg", message)
        .setTimestamp("finalTime", new Date())
        .setInteger("oldStatus", oldStatus).setParameterList("sids", sids)
        .executeUpdate();
    }
    
    @Override
    public void cancelTicketsBySchemeIds(int oldStatus,Collection<Long> sids) {
    	createQuery("update TicketPO set status=:newStatus, message=:msg,FinalStateTime=:finalTime " +
    			"where status=:oldStatus and schemeId in (:sids)")
    	.setInteger("newStatus", EntityStatus.TICKET_SCHEME_CANCEL).setString("msg", "撤单")
    	.setInteger("oldStatus", oldStatus).setParameterList("sids", sids)
    	.setTimestamp("finalTime", new Date())
    	.executeUpdate();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<TicketPO> find(Collection<Long> ids) {
        return createQuery("from TicketPO where id in (:ids)").setParameterList("ids", ids).list();
    }
    @SuppressWarnings("unchecked")
    @Override
    public TicketPO findById(Long id) {
    	List<TicketPO> ts = createQuery("from TicketPO where id = :id").setLong("id", id).list();
    	return ts.size() > 0 ? ts.get(0) : null;
    }

//    @SuppressWarnings("unchecked")
//    @Override
//    public List<Object[]> countSchemeTicket(Long schemeId) {
//        return createQuery("select status,sum(money) from TicketPO where schemeId=? group by status")
//                .setLong(0, schemeId).list();
//    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<Object[]> sumBoughtNote(Collection<Long> schemes) {
        return createQuery("select schemeId,status,sum(note) from TicketPO where (status=5100 or status=5101) and schemeId in (:schemes) group by schemeId, status")
                .setParameterList("schemes", schemes).list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Object[]> sumPrized(Collection<Long> schemes) {
        return createQuery("select schemeId,status,sum(note),sum(preTaxBonus),sum(afterTaxBonus) from TicketPO where (status=5101 or status=5202 or status=5203) and schemeId in (:schemes) group by schemeId, status")
                .setParameterList("schemes", schemes).list();
    }

    /**
     * 列出15天内没有获取中奖信息,并且lotteryId在指定列表中的ticket。按照 playId 和 创建日期分组。
     */
	@SuppressWarnings("unchecked")
	@Override
	public List<TicketPO> listNotPrizeAndLotteryIdInTargetListTicketGroupByPlayIdAndDate(List<String> targetLotteryIdList) {
        Date from = new Date(System.currentTimeMillis() - 86400000L * _15_DAY);
        String hql = "select t from TicketPO as t,PlayPO as p " +
        		"where " +
        		"t.status=:status " +
        		"and t.createdTime>:from " +
        		"and p.lotteryId in(:targetLotteryIds) " +
        		"and t.playId = p.id " +
        		"group by t.playId,DATE_FORMAT(t.createdTime, '%Y-%m-%d')";
        return createQuery(hql)
        		.setParameter("status", EntityStatus.TICKET_BUY_SUCCESS)
        		.setParameter("from", from)
        		.setParameterList("targetLotteryIds", targetLotteryIdList)
        		.list();
	}
	
	/**
     * 列出15天内没有获取中奖信息,并且playid在指定列表中的ticket。按照 playId 和期号分组。
     */
	@SuppressWarnings("unchecked")
	@Override
	public List<TicketPO> listNotPrizeAndLotteryIdInTargetListTicketGroupByPlayIdAndIssue(List<String> targetLotteryIdList) {
        Date from = new Date(System.currentTimeMillis() - 86400000L * _15_DAY);
        String hql = "select t from TicketPO as t,PlayPO as p " +
        		"where " +
        		"t.status=:status " +
        		"and t.createdTime>:from " +
        		"and p.lotteryId in(:targetLotteryIds) " +
        		"and t.playId = p.id " +
        		"group by t.playId,t.issue";
        return createQuery(hql)
        		.setParameter("status", EntityStatus.TICKET_BUY_SUCCESS)
        		.setParameter("from", from)
        		.setParameterList("targetLotteryIds", targetLotteryIdList)
        		.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TicketPO> listNotPrizeAndLotteryIdInTargetListTicket(
			List<String> targetLotteryIds) {
		Date from = new Date(System.currentTimeMillis() - 86400000L * _15_DAY);//15天之内
        String hql = "select t from TicketPO as t,PlayPO as p " +
    		"where t.status=:status and t.createdTime>:from " +
    		"and p.lotteryId in(:targetLotteryIds) and t.playId = p.id";
        return createQuery(hql)
    		.setParameter("status", EntityStatus.TICKET_BUY_SUCCESS)
    		.setParameter("from", from)
    		.setParameterList("targetLotteryIds", targetLotteryIds)
    		.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TicketPO> find(String playId, Date createdTime, List<Integer> status) {
		return (List<TicketPO>)createQuery(
						" select t from TicketPO as t " +
						" where " +
						" t.playId=:playId " +
						" and date(t.createdTime)=date(:createdTime) " +
						" and t.status in(:status) "
						)
				.setString("playId", playId)
				.setDate("createdTime", createdTime)
				.setParameterList("status", status).list();
	}

	private List<String> hhPlayIDs = Arrays.asList(new String[]{
			PlayType.JCZQ_HH.getPlayIdWithPass(false),
			PlayType.JCLQ_HH.getPlayIdWithPass(false)});
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TicketPO> listNotPrizedHHTickets() {
		Date from = new Date(System.currentTimeMillis() - 86400000L * _15_DAY);
        String hql = "select t from TicketPO as t " +
        		"where " +
        		"t.status=:status " +
        		"and t.createdTime>:from " +
        		"and t.playId in(:hhPlayIDs)" +
        		"group by t.playId,DATE_FORMAT(t.createdTime, '%Y-%m-%d')";
        return createQuery(hql)
        		.setParameter("status", EntityStatus.TICKET_BUY_SUCCESS)
        		.setParameter("from", from)
        		.setParameterList("hhPlayIDs", hhPlayIDs)
        		.list();
        }

	@Override
	public void updateTicketStatusAndLotteryPlatformIdAndActualCode(Long id,
			int status, int actualStatus, String message,
			String lotteryPlatformId, String actualCode,String number,String odds) {
		String hql=null;
		if(status==-1){
			hql="update TicketPO "
					+ "set actualStatus=:actualStatus ,"
					+ "message=:message,lotteryPlatformId=:lotteryPlatformId,"
					+ "actualCode=:actualCode, "
					+ "number=:number, "
					+ "odds=:odds  "
					+ "where "
					+ "id=:id";
			Query query = createQuery(hql);
			query.setParameter("actualStatus", actualStatus);
			query.setParameter("message", message);
			query.setParameter("lotteryPlatformId", lotteryPlatformId);
			query.setParameter("actualCode", actualCode);
			query.setParameter("id", id);
			query.setParameter("number", number);
			query.setParameter("odds", odds);
			query.executeUpdate();
		}
		else{
			hql="update TicketPO "
					+ "set actualStatus=:actualStatus ,"
					+ "message=:message,lotteryPlatformId=:lotteryPlatformId,"
					+ "actualCode=:actualCode, "
					+ "status=:status, "
					+ "number=:number,  "
					+ "odds=:odds  "
					+ "where "
					+ "id=:id";
			Query query = createQuery(hql);
			query.setParameter("actualStatus", actualStatus);
			query.setParameter("message", message);
			query.setParameter("lotteryPlatformId", lotteryPlatformId);
			query.setParameter("actualCode", actualCode);
			query.setParameter("status", status);
			query.setParameter("id", id);
			query.setParameter("number", number);
			query.setParameter("odds", odds);
			query.executeUpdate();
		}
		
		
	}

	/**
	 * 列出尊奥15天内没有获取中奖信息,并且lotteryId在指定列表中的ticket。按照 playId 和 创建日期分组。
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<TicketPO> listZunAoNotPrizeAndLotteryIdInTargetListTicketGroupByPlayIdAndDate(
			List<String> targetLotteryIdList) {
		Date from = new Date(System.currentTimeMillis() - 86400000L * _15_DAY);
        String hql = "select t from TicketPO as t,PlayPO as p " +
        		"where " +
        		"t.status=:status " +
        		"and t.createdTime>:from " +
        		"and p.lotteryId in(:targetLotteryIds) " +
        		"and t.playId = p.id "
        		+ "and "
        		+ "("
        		+ "t.lotteryPlatformId =:lotteryPlatformId  "
        		+ "or t.lotteryPlatformId is null"
        		+ ") " +
        		"group by t.playId,DATE_FORMAT(t.createdTime, '%Y-%m-%d')";
        return createQuery(hql)
        		.setParameter("status", EntityStatus.TICKET_BUY_SUCCESS)
        		.setParameter("from", from)
        		.setParameterList("targetLotteryIds", targetLotteryIdList)
        		.setParameter("lotteryPlatformId", LotteryPlatformId.ZUN_AO)
        		.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TicketPO> listZunAoNotPrizedHHTickets() {
		Date from = new Date(System.currentTimeMillis() - 86400000L * _15_DAY);
        String hql = "select t from TicketPO as t " +
        		"where " +
        		"t.status=:status " +
        		"and t.createdTime>:from " +
        		"and t.playId in(:hhPlayIDs) " 
        		+ "and "
        		+ "("
        		+ "t.lotteryPlatformId =:lotteryPlatformId  "
        		+ "or t.lotteryPlatformId is null"
        		+ ") " +
        		"group by t.playId,DATE_FORMAT(t.createdTime, '%Y-%m-%d')";
        return createQuery(hql)
        		.setParameter("status", EntityStatus.TICKET_BUY_SUCCESS)
        		.setParameter("from", from)
        		.setParameterList("hhPlayIDs", hhPlayIDs)
        		.setParameter("lotteryPlatformId", LotteryPlatformId.ZUN_AO)
        		.list();
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<TicketPO> listNotPrizeTicketWithTargetLotteryPlatformId(
			String lotteryPlatformId) {
		Date from = new Date(System.currentTimeMillis() - 86400000L * _15_DAY);
        String hql = "select t from TicketPO as t " +
        		"where " +
        		"t.status=:status " +
        		"and t.createdTime>:from " 
        		+ "and "
        		+ "t.lotteryPlatformId =:lotteryPlatformId  ";
        return createQuery(hql)
        		.setParameter("status", EntityStatus.TICKET_BUY_SUCCESS)
        		.setParameter("from", from)
        		.setParameter("lotteryPlatformId", lotteryPlatformId)
        		.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Long> findZunAoTicketByStatus(int status,
			Date from, Date to) {
		Criteria c = createCriteria()
				.setProjection(Property.forName("id"))
				.add(Restrictions.eq("status", status))
				.add(Restrictions.or(Restrictions.isNull("lotteryPlatformId"),
						Restrictions.eq("lotteryPlatformId",
								LotteryPlatformId.ZUN_AO)));
		if (from != null) {
			c.add(Restrictions.ge("createdTime", from));
		}
		if (to != null) {
			c.add(Restrictions.le("createdTime", to));
		}
		return c.list();
	}


	@Override
	public Object[] findShiTiDianFBMinEntrustDeadLine(List<String> availableShiTiDian) {
		String sql="select min(minDeadline),sum(money) as totalMoney,count(*) as total  "
				+ "from ( "
						+ "select a.id,a.money,b.entrust_deadline as minDeadline "
						+ "from lt_ticket a ,fb_match b,lt_bet_match c "
						+ "where "
						+"a.scheme_id=c.scheme_id "
						+"and b.id=c.match_id "
						+"and a.status=:status "
						+"and a.lottery_platform_id in (:availableShiTiDian) "
						+"and a.play_id in (:playIds) "
						+ "and a.odds is not null "
						+ "group by a.id,a.money "
						+ "having b.entrust_deadline<=min(b.entrust_deadline) "
				+ ") d";
		Query query=createSQLQuery(sql);
		query.setParameterList("availableShiTiDian", availableShiTiDian);
		List<String> playIds=makePlayIds(LotteryId.JCZQ.toString());
		query.setParameterList("playIds", playIds);
		query.setParameter("status", EntityStatus.TICKET_READY_FOR_HANDWORK);
		return  (Object[]) query.uniqueResult();
	}

	@Override
	public Object[] findShiTiDianBBMinEntrustDeadLine(List<String> availableShiTiDian) {
		String sql="select min(minDeadline),sum(money) as totalMoney,count(*) as total "
				+ "from ( "
						+ "select a.id,a.money,b.entrust_deadline as minDeadline "
						+ "from lt_ticket a ,bb_match b,lt_bet_match c "
						+ "where "
						+"a.scheme_id=c.scheme_id "
						+"and b.id=c.match_id "
						+"and a.status=:status "
						+"and a.lottery_platform_id in (:availableShiTiDian) "
						+"and a.play_id in (:playIds) "
						+ "and a.odds is not null "
						+ "group by a.id,a.money "
						+ "having b.entrust_deadline<=min(b.entrust_deadline) "
				+ ") d";
		Query query=createSQLQuery(sql);
		query.setParameterList("availableShiTiDian", availableShiTiDian);
		List<String> playIds=makePlayIds(LotteryId.JCLQ.toString());
		query.setParameterList("playIds", playIds);
		query.setParameter("status", EntityStatus.TICKET_READY_FOR_HANDWORK);
		return (Object[]) query.uniqueResult();
	}

	@Override
	public Object[] findShiTiDianCtfbMinEntrustDeadLine(List<String> availableShiTiDian) {
		String sql="select min(minDeadline),sum(money) as totalMoney,count(*) as total "
				+ "from ( "
						+ "select a.id,a.money,b.entrust_deadline as minDeadline "
						+ "from lt_ticket a ,ctfb_match b,lt_ct_bet_content c "
						+ "where "
						+"a.scheme_id=c.scheme_id "
						+"and b.issue_number=c.issue_number "
						+ "and b.play_id=c.play_id "
						+"and a.status=:status "
						+"and a.lottery_platform_id in (:availableShiTiDian) "
						+"and a.play_id in (:playIds) "
						+ "group by a.id,a.money "
						+ "having b.entrust_deadline<=min(b.entrust_deadline) "
				+ ") d";
		Query query=createSQLQuery(sql);
		query.setParameterList("availableShiTiDian", availableShiTiDian);
		List<String> playIds=makePlayIds(LotteryId.CTZC.toString());
		query.setParameterList("playIds", playIds);
		query.setParameter("status", EntityStatus.TICKET_READY_FOR_HANDWORK);
		return (Object[]) query.uniqueResult();
	}
	
	@Override
	public List<TicketPO> find(Paging paging, Date begin, Date end, int status, String lotteryId, Long schemeId, Long ticketId) { 
		PagingQuery<TicketPO> pq = pagingQuery(paging);
		if(null != ticketId && ticketId > 0) {
			pq.add(Restrictions.eq("id", ticketId));
		}
		if(null != schemeId && schemeId > 0) {
			pq.add(Restrictions.eq("schemeId", schemeId));
		}
		if (begin != null) {
            pq.add(Restrictions.ge("createdTime", begin));
        }
        if (end != null) {
            pq.add(Restrictions.le("createdTime", end));
        }
        if (status != -1) {
            pq.add(Restrictions.eq("status", status));
        }
        pq.add(Restrictions.eq("lotteryPlatformId", LotteryPlatformId.CHANGCHUN_SHI_TI_DIAN));
        
        if(StringUtils.isBlank(lotteryId)) {
        	return null;
        } else {
        	List<String> values = new ArrayList<String>();
        	if(lotteryId.equals(LotteryId.JCZQ.name())) {
        		pq.add(Restrictions.isNotNull("odds"));
        		
        		values.add("01_ZC_1");values.add("01_ZC_2");
        		values.add("80_ZC_1");values.add("80_ZC_2");
        		values.add("02_ZC_1");values.add("02_ZC_2");
        		values.add("03_ZC_1");values.add("03_ZC_2");
        		values.add("04_ZC_1");values.add("04_ZC_2");
        		values.add("05_ZC_2");
        	} else if(lotteryId.equals(LotteryId.JCLQ.name())) {
        		pq.add(Restrictions.isNotNull("odds"));
        		
        		values.add("06_LC_1");values.add("06_LC_2");
        		values.add("07_LC_1");values.add("07_LC_2");
        		values.add("08_LC_1");values.add("08_LC_2");
        		values.add("09_LC_1");values.add("09_LC_2");
        		values.add("10_LC_2");
        	} else if(lotteryId.equals(LotteryId.CTZC.name())) {
        		values.add("24_ZC_14");values.add("25_ZC_R9");
        		values.add("26_ZC_BQ");values.add("27_ZC_JQ");
        	}
        	pq.add(Restrictions.in("playId", values));
        }
        pq.desc("money");
        pq.asc("createdTime");
        return pq.list();
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public List<Object> findShiTiDianTicketsAndOrderByDeadline(
			Date begin, Date end, int status, String lotteryId, Long schemeId,
			Long ticketId, String lotteryPlatformId) {
		
		if(status!=-1&&StringUtils.isNotBlank(lotteryId)){
			List<String> playIds=new ArrayList<String>();
			String hql = "from TicketPO a ,BetSchemePO b "
					+ "where "
					+ "a.schemeId=b.id "
					+ "and a.lotteryPlatformId=:lotteryPlatformId "
					+ "and a.status=:status "
					+ "and a.playId in (:playIds) ";
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
        		
        		playIds.add("01_ZC_1");playIds.add("01_ZC_2");
        		playIds.add("80_ZC_1");playIds.add("80_ZC_2");
        		playIds.add("02_ZC_1");playIds.add("02_ZC_2");
        		playIds.add("03_ZC_1");playIds.add("03_ZC_2");
        		playIds.add("04_ZC_1");playIds.add("04_ZC_2");
        		playIds.add("05_ZC_2");
        	} else if(lotteryId.equals(LotteryId.JCLQ.name())) {
        		critea.append(" and a.odds is not null ");
        		
        		playIds.add("06_LC_1");playIds.add("06_LC_2");
        		playIds.add("07_LC_1");playIds.add("07_LC_2");
        		playIds.add("08_LC_1");playIds.add("08_LC_2");
        		playIds.add("09_LC_1");playIds.add("09_LC_2");
        		playIds.add("10_LC_2");
        	} else if(lotteryId.equals(LotteryId.CTZC.name())) {
        		playIds.add("24_ZC_14");playIds.add("25_ZC_R9");
        		playIds.add("26_ZC_BQ");playIds.add("27_ZC_JQ");
        	}
	        
	        String order=" order by b.offtime asc,a.money desc ";
			hql=hql+critea+order;
			
			Query query = createQuery(hql);
			makeParameters(begin, end, status, schemeId, ticketId, playIds,
					query, lotteryPlatformId);
			return query.list();
		}
		return null;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<Object> findShiTiDianTicketsAndOrderByDeadlineWithPaging(
			Paging paging, Date begin, Date end, int status, String lotteryId,
			Long schemeId, Long ticketId, String lotteryPlatformId) {
		if(null!=paging&&status!=-1&&StringUtils.isNotBlank(lotteryId)){
			List<String> playIds = makePlayIds(lotteryId);
			String hql = "from TicketPO a ,BetSchemePO b "
					+ "where "
					+ "a.schemeId=b.id "
					+ "and a.lotteryPlatformId=:lotteryPlatformId "
					+ "and a.status=:status "
					+ "and a.playId in (:playIds) ";
			StringBuilder critea = makeCritea(begin, end, lotteryId, schemeId,
					ticketId);
	        
	        String order=" order by b.offtime asc,a.money desc ";
			hql=hql+critea+order;
			
			Query query = createQuery(hql);
			makeParameters(begin, end, status, schemeId, ticketId, playIds,
					query, lotteryPlatformId);
			query.setFirstResult(paging.getFirstResult());
			query.setMaxResults(paging.getMaxResults());
			Long totalCount=0l;
			if(paging.isCount()){
				
				String countHql="select count(*)  "+hql;
				Query countQuery = createQuery(countHql);
				makeParameters(begin, end, status, schemeId, ticketId, playIds,
						countQuery, lotteryPlatformId);
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

	private StringBuilder makeCritea(Date begin, Date end, String lotteryId,
			Long schemeId, Long ticketId) {
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
		return critea;
	}

	private List<String> makePlayIds(String lotteryId) {
		List<String> playIds=new ArrayList<String>();
		if (lotteryId.equals(LotteryId.JCZQ.name())) {

			playIds.add("01_ZC_1");
			playIds.add("01_ZC_2");
			playIds.add("80_ZC_1");
			playIds.add("80_ZC_2");
			playIds.add("02_ZC_1");
			playIds.add("02_ZC_2");
			playIds.add("03_ZC_1");
			playIds.add("03_ZC_2");
			playIds.add("04_ZC_1");
			playIds.add("04_ZC_2");
			playIds.add("05_ZC_2");
		} else if (lotteryId.equals(LotteryId.JCLQ.name())) {

			playIds.add("06_LC_1");
			playIds.add("06_LC_2");
			playIds.add("07_LC_1");
			playIds.add("07_LC_2");
			playIds.add("08_LC_1");
			playIds.add("08_LC_2");
			playIds.add("09_LC_1");
			playIds.add("09_LC_2");
			playIds.add("10_LC_2");
		} else if (lotteryId.equals(LotteryId.CTZC.name())) {
			playIds.add("24_ZC_14");
			playIds.add("25_ZC_R9");
			playIds.add("26_ZC_BQ");
			playIds.add("27_ZC_JQ");
		}
		return playIds;
	}

	private void makeParameters(Date begin, Date end, int status,
			Long schemeId, Long ticketId, List<String> playIds, Query query, String lotteryPlatformId) {
		query.setParameter("lotteryPlatformId", lotteryPlatformId);
		query.setParameter("status", status);
		query.setParameterList("playIds", playIds);
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

	@SuppressWarnings("unchecked")
	@Override
	public List<PrintableFilePO> findHighSpeedPrintFileByTime(Date from,Date to,String lotteryPlatformId){
		String hql = "select a from PrintableFilePO a "
				+ " where lotteryPlatformId=:lotteryPlatformId "
				+ " 	and createTime >= :from and createTime <= :to" 
				+ " order by a.createTime desc";
		Query query = createQuery(hql);
		query.setString("lotteryPlatformId", lotteryPlatformId);
		query.setDate("from", from);
		query.setDate("to", to);
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PrintableFilePO> findHighSpeedPrintFilePagingByTicketId(
			Paging paging, Long ticketId, String lotteryPlatformId) {
		if(null!=paging){//&&ticketId!=null
			String hql = " from PrintableFilePO a where lotteryPlatformId=:lotteryPlatformId"
					+ " order by a.createTime desc";
			//where a.ticketId=?
			Query query = createQuery("select a " + hql);
			query.setString("lotteryPlatformId", lotteryPlatformId);
			//query.setLong(0, ticketId);
			query.setFirstResult(paging.getFirstResult());
			query.setMaxResults(paging.getMaxResults());
			Long totalCount = 1L;
			if(paging.isCount()){
				
				String countHql="select count(*)  "+hql;
				Query countQuery = createQuery(countHql);//.setLong(0, ticketId);
				countQuery.setString("lotteryPlatformId", lotteryPlatformId);
				totalCount = (Long) countQuery.uniqueResult();
				paging.setTotalCount(totalCount.intValue());
			}
			List<PrintableFilePO> result=null;
			if (totalCount > paging.getFirstResult()) {
				result = query.list();
				paging.setResults(result);
			}
			return result;
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findTicketWithOfftime(List<Long> ptIds) {
		String hql = "select a.id,b.offtime,b.machineOfftime from TicketPO a ,BetSchemePO b where a.schemeId=b.id and a.id in(:ticketIds)";
		Query query = createQuery(hql);
		query.setParameterList("ticketIds", ptIds);
		return query.list();
	}

	@Override
	public void updateTicketOdds(Long id, String odds) {
		String hql="update TicketPO set odds=:odds where id=:id";
		Query query = createQuery(hql);
		query.setParameter("id", id);
		query.setParameter("odds", odds);
		query.executeUpdate();
	}
	
	@Override
	public List<TicketPO> findBySchemeWithLock(Long schemeId, Integer status) {
		Criteria c = createCriteria().add(Restrictions.eq("schemeId", schemeId));
		c.setLockMode(LockMode.PESSIMISTIC_WRITE);
        if (status != -1) {
            c.add(Restrictions.eq("status", status));
        }
        return c.list();
	}
}

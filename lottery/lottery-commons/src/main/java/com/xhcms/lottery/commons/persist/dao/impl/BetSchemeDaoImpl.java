package com.xhcms.lottery.commons.persist.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria; 
import org.hibernate.SQLQuery;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.impl.CriteriaImpl;
import org.springframework.stereotype.Repository;

import com.xhcms.commons.lang.Paging;
import com.xhcms.commons.persist.hibernate.DaoImpl;
import com.xhcms.lottery.commons.persist.dao.BetSchemeDao;
import com.xhcms.lottery.commons.persist.entity.BetSchemePO;
import com.xhcms.lottery.lang.Constants;
import com.xhcms.lottery.lang.EntityStatus;
import com.xhcms.lottery.lang.EntityType;
import com.xhcms.lottery.lang.LotteryId;

/**
 * 投注方案
 * 
 * @author jiajiancheng
 * 
 */
@Repository
@SuppressWarnings("unchecked")
public class BetSchemeDaoImpl extends DaoImpl<BetSchemePO> implements
		BetSchemeDao {

	private static final long serialVersionUID = -793097112883708694L;

	public BetSchemeDaoImpl() {
		super(BetSchemePO.class);
	}

	@Override
	public List<Long> findDeadlinesGroupbuySchemeIds(Date from, Date to) {
		Criteria c = createCriteria()
				.setProjection(Property.forName("id"))
				.add(Restrictions.eq("saleStatus", EntityStatus.SCHEME_ON_SALE))
				.add(Restrictions.eq("type", EntityType.BETTING_PARTNER));
		if (from != null) {
			c.add(Restrictions.ge("offtime", from));
		}
		if (to != null) {
			c.add(Restrictions.le("offtime", to));
		}
		return c.list();
	}

	@Override
	public void forceGroupBuyFlow(Long schemeId) {
		BetSchemePO p = get(schemeId);
		p.setSaleStatus(EntityStatus.SCHEME_STOP);
		p.setStatus(EntityStatus.TICKET_SCHEME_FLOW);
		p.setCancelNote(p.getBetNote());
		update(p);
	}

	@Override
	public List<BetSchemePO> find(Paging paging, Date from, Date to,
			int status, String lotteryId, Long schemeId, String sponsor,
			String playId, String passType) {
		PagingQuery<BetSchemePO> pq = pagingQuery(paging);
		pq.add(Restrictions.ge("createdTime", from)).add(
				Restrictions.lt("createdTime", to));
		addCommonRestrictions(pq, status, lotteryId, schemeId, sponsor, playId,
				passType, -1);
		return pq.desc("id").list();
	}

	private PagingQuery<BetSchemePO> addCommonRestrictions(
			PagingQuery<BetSchemePO> pq, int status, String lotteryId,
			Long schemeId, String sponsor, String playId, String passType,
			int type) {
		if (status != -1) {
			pq.add(Restrictions.eq("status", status));
		}
		if (StringUtils.isNotBlank(lotteryId)) {
			pq.add(Restrictions.eq("lotteryId", lotteryId));
		}
		if (type != -1) {
			pq.add(Restrictions.eq("type", type));
		}
		if (schemeId != null) {
			pq.add(Restrictions.eq("id", schemeId));
		}
		if (StringUtils.isNotBlank(sponsor)) {
			pq.add(Restrictions.like("sponsor", sponsor, MatchMode.ANYWHERE));
		}
		if (StringUtils.isNotBlank(playId)) {
			pq.add(Restrictions.eq("playId", playId));
		}
		if (StringUtils.isNotBlank(passType)) {
			pq.add(Restrictions.like("passTypeIds", "," + passType + ",",
					MatchMode.ANYWHERE));
		}
		return pq;
	}

	@Override
	public List<Long> findBySaleStatus(int saleStatus, Date from, Date to) {
		Criteria c = createCriteria().setProjection(Property.forName("id"))
				.add(Restrictions.eq("saleStatus", saleStatus));
		if (from != null) {
			c.add(Restrictions.ge("createdTime", from));
		}
		if (to != null) {
			c.add(Restrictions.le("createdTime", to));
		}
		return c.list();
	}

	@Override
	public List<BetSchemePO> find(Collection<Long> ids) {
		return createCriteria().add(Restrictions.in("id", ids)).list();
	}

	@Override
	public List<BetSchemePO> findByStatus(int status, Date from) {
		Criteria c = createCriteria();
		if (status != -1) {
			c.add(Restrictions.eq("status", status));
		}
		if (from != null) {
			c.add(Restrictions.ge("createdTime", from));
		}
		return c.list();
	}

	@Override
	public List<BetSchemePO> findFollowSchemes(Long followedSchemeId) {
		return createCriteria().add(
				Restrictions.eq("followedSchemeId", followedSchemeId)).list();
	}

	@Override
	public List<BetSchemePO> findFollowSchemesByUser(Long followedSchemeId,
			Long userid) {
		List<BetSchemePO> betSchemePO = createCriteria()
				.add(Restrictions.eq("followedSchemeId", followedSchemeId))
				.add(Restrictions.eq("sponsorId", userid))
				.add(Restrictions.eq("type", EntityType.BETTING_FOLLOW)).list();
		return betSchemePO.size() > 0 ? betSchemePO : null;
	}

	@Override
	public List<BetSchemePO> findOnSaleShowingScheme(Paging paging, int status,
			String lotteryId, Long schemeId, String sponsor, String playId,
			String passType) {

		PagingQuery<BetSchemePO> pq = pagingQuery(paging);
		addCommonRestrictions(pq, status, lotteryId, schemeId, sponsor, playId,
				passType, -1);
		addOnSaleRestriction(pq);
		addShowingRestriction(pq);
		return pq.desc("createdTime").list();
	}

	@Override
	public List<BetSchemePO> findOnGroupbuyScheme(Paging paging, int status,
			String lotteryId, Long schemeId, String sponsor, String playId,
			String passType) {
		PagingQuery<BetSchemePO> pg = pagingQuery(paging);
		addCommonRestrictions(pg, status, lotteryId, schemeId, sponsor, playId,
				passType, Constants.TYPE_GROUP);
		return pg.desc("createdTime").list();
	}

	@Override
	public List<BetSchemePO> findLaunchShowingScheme(Paging paging, int status,
			String lotteryId, Long schemeId, String sponsor, String playId,
			String passType, Date starttime, Date offtime) {
		PagingQuery<BetSchemePO> pq = pagingQuery(paging);
		addCommonRestrictions(pq, status, lotteryId, schemeId, sponsor, playId,
				passType, -1);
		addShowingRestriction(pq);
		addStartEndTimeRestriction(pq, starttime, offtime);
		return pq.desc("createdTime").list();
	}

	@Override
	public List<BetSchemePO> findLaunchShowingScheme(Paging paging, int status,
			String lotteryId, Long schemeId, Long sponsorId, String playId,
			String passType, Date starttime, Date offtime) {
		PagingQuery<BetSchemePO> pq = pagingQuery(paging);
		addCommonRestrictions(pq, status, lotteryId, schemeId, null, playId,
				passType, -1);
		pq.add(Restrictions.eq("sponsorId", sponsorId));
		addShowingRestriction(pq);
		addStartEndTimeRestriction(pq, starttime, offtime);
		return pq.desc("createdTime").list();
	}

	// 时间范围约束
	private void addStartEndTimeRestriction(PagingQuery<BetSchemePO> pq,
			Date starttime, Date offtime) {
		if (pq == null) {
			return;
		}
		if (starttime != null)
			pq.add(Restrictions.gt("createdTime", starttime));
		if (offtime != null)
			pq.add(Restrictions.lt("createdTime", offtime));
	}

	// 在售约束
	private void addOnSaleRestriction(PagingQuery<BetSchemePO> pq) {
		pq.add(Restrictions.gt("offtime", new Date()));
	}

	// 晒单约束
	private void addShowingRestriction(PagingQuery<BetSchemePO> pq) {
		pq.add(Restrictions.eq("showScheme", Constants.SHOW_SCHEME));
	}

	@Override
	public List<BetSchemePO> findRecommended(Paging paging, boolean isOnSale) {
		PagingQuery<BetSchemePO> pq = pagingQuery(paging);
		addRecommendRestriction(pq);
		addShowingRestriction(pq);
		if (isOnSale) {
			addOnSaleRestriction(pq);
		}
		return pq.desc("createdTime").list();
	}

	@Override
	public List<BetSchemePO> findRecommended(Paging paging, boolean isOnSale,
			int type) {
		PagingQuery<BetSchemePO> pg = pagingQuery(paging);
		addRecommendRestriction(pg);
		if (isOnSale) {
			addOnSaleRestriction(pg);
		}
		pg.add(Restrictions.eq("type", type));

		return pg.desc("createdTime").list();
	}

	private void addRecommendRestriction(PagingQuery<BetSchemePO> pq) {
		pq.add(Restrictions.eq("recommendation", Constants.RECOMMEND));
	}

	@Override
	public List<BetSchemePO> findMyJoinFollowScheme(Paging paging, int status,
			String lotteryId, Long schemeId, Long sponsorId, String playId,
			String passType, Date from, Date to) {
		int count = 0;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String countStr = "select count(*) ";
		String selectStr = "select s ";
		String hqlStr = "from BetSchemePO  s,BetSchemePO b "
				+ "where b.showScheme="
				+ EntityType.DONT_SHOW_SCHEME
				+ " and "
				+ "b.sponsorId=? and b.followedSchemeId!=-1 and  b.followedSchemeId=s.id";
		String endStr = " group by b.followedSchemeId order by b.createdTime desc ";

		if (status != -1)
			hqlStr += " and b.status=" + status;
		if (StringUtils.isNotBlank(lotteryId))
			hqlStr += " and b.lotteryId='" + lotteryId + "'";
		if (schemeId != null)
			hqlStr += " and b.id=" + schemeId;
		if (StringUtils.isNotBlank(playId))
			hqlStr += " and b.playId='" + playId + "'";
		if (StringUtils.isNotBlank(passType))
			hqlStr += " and b.passTypeIds like '%" + passType + "%'";
		if (from != null)
			hqlStr += " and b.createdTime>'" + df.format(from) + "'";
		if (to != null)
			hqlStr += " and b.createdTime<'" + df.format(to) + "'";

		List<?> ls = createQuery(countStr + hqlStr + endStr).setLong(0,
				sponsorId).list();
		if (ls.size() > 0)
			count = Integer.valueOf(ls.get(0).toString());

		List<BetSchemePO> betSchemePOs = createQuery(
				selectStr + hqlStr + endStr).setLong(0, sponsorId)
				.setFirstResult(paging.getFirstResult())
				.setMaxResults(paging.getMaxResults()).list();
		paging.setTotalCount(count);
		return betSchemePOs.size() > 0 ? betSchemePOs : null;
	}

	@Override
	public List<BetSchemePO> getWinSchemeList() {
		return createCriteria()
				.add(Restrictions.or(Restrictions.eq("showScheme", 1),
						Restrictions.eq("type", 1)))
				.add(Restrictions.eq("status", 12)).list();
	}

	@Override
	public List<Object[]> findBetSchemeListByPartnerIds(Set<Long> ids) {
		String hql = "select s.sponsor,s.followedSchemeId,p.id,s.id from BetSchemePO as s,BetPartnerPO as p where p.scheme=s.id and p.id in (:ids) order by p.id desc";
		List<Object[]> betSchemePOs = createQuery(hql).setParameterList("ids",
				ids).list();
		return betSchemePOs.size() > 0 ? betSchemePOs : null;
	}

	@Override
	public List<Object[]> findCommissionOutList(Paging paging, Long userId,
			Date from, Date to) {
		int count = 0;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String countStr = "select count(*) ";
		String selectStr = "select s.id,p.id,p.createdTime,s.followedSchemeId ";
		String hql = " from BetSchemePO as s,BetPartnerPO as p where p.scheme=s.id and s.sponsorId=?"
				+ " AND s.status="
				+ EntityStatus.TICKET_AWARDED
				+ " and s.type<>"
				+ EntityType.BETTING_PARTNER
				+ " and p.commission>0";
		String endStr = " order by p.id desc";
		if (from != null)
			hql += " and p.createdTime>'" + df.format(from) + "'";
		if (to != null)
			hql += " and p.createdTime<'" + df.format(to) + "'";

		count = Integer.parseInt(createQuery(countStr + hql + endStr)
				.setLong(0, userId).uniqueResult().toString());

		List<Object[]> Objects = createQuery(selectStr + hql + endStr)
				.setLong(0, userId).setFirstResult(paging.getFirstResult())
				.setMaxResults(paging.getMaxResults()).list();
		paging.setTotalCount(count);
		return Objects.size() > 0 ? Objects : null;
	}

	@Override
	public List<BetSchemePO> findMyJoinGroupbuyScheme(Paging paging,
			int status, String lotteryId, Long userId, String playId,
			String passType, Date from, Date to) {
		int count = 0;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String countStr = "select count(*) ";
		String selectStr = "select s ";
		String hqlStr = "from BetSchemePO as  s,BetPartnerPO as  p "
				+ "where s.type=" + EntityType.BETTING_PARTNER
				+ " and s.id=p.scheme and p.userId=?";
		String endStr = " order by p.id desc";
		if (status != -1)
			hqlStr += " and s.status=" + status;
		if (StringUtils.isNotBlank(lotteryId))
			hqlStr += " and s.lotteryId='" + lotteryId + "'";
		if (StringUtils.isNotBlank(playId))
			hqlStr += " and s.playId='" + playId + "'";
		if (StringUtils.isNotBlank(passType))
			hqlStr += " and s.pass_type_ids like '%" + passType + "%'";
		if (from != null)
			hqlStr += " and p.createdTime>'" + df.format(from) + "'";
		if (to != null)
			hqlStr += " and p.createdTime<'" + df.format(to) + "'";

		count = Integer.parseInt(createQuery(countStr + hqlStr + endStr)
				.setLong(0, userId).uniqueResult().toString());
		List<BetSchemePO> betSchemePOs = createQuery(
				selectStr + hqlStr + endStr).setLong(0, userId)
				.setFirstResult(paging.getFirstResult())
				.setMaxResults(paging.getMaxResults()).list();
		paging.setTotalCount(count);
		return betSchemePOs.size() > 0 ? betSchemePOs : null;
	}

	@Override
	public List<BetSchemePO> findLaunchGroupbuyScheme(Paging paging,
			int status, String lotteryId, Long sponsorId, String playId,
			String passType, Date from, Date to) {
		PagingQuery<BetSchemePO> pq = pagingQuery(paging);
		this.addCommonRestrictions(pq, status, lotteryId, null, null, playId,
				passType, EntityType.BETTING_PARTNER);
		this.addStartEndTimeRestriction(pq, from, to);
		pq.add(Restrictions.eq("sponsorId", sponsorId));
		List<BetSchemePO> betSchemePOs = pq.desc("id").list();
		return betSchemePOs.size() > 0 ? betSchemePOs : null;
	}

	@Override
	public List<?> findBetSchemeByDetachCriteria(
			DetachedCriteria detachedCriteria, Paging paging) {

		Criteria criteria = detachedCriteria
				.getExecutableCriteria(getSessionFactory().getCurrentSession());
		Projection projection = ((CriteriaImpl) criteria).getProjection();
		if (paging.isCount()) {
			int totalCount = ((Number) criteria.setProjection(
					Projections.rowCount()).uniqueResult()).intValue();
			paging.setTotalCount(totalCount);
		}

		criteria.setProjection(projection);
		if (projection == null) {
			criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		}

		List<?> results = criteria.setMaxResults(paging.getMaxResults())
				.setFirstResult(paging.getFirstResult()).list();

		return results;
	}

	@Override
	public List<BetSchemePO> findByStatusAndLotteryIdList(int status,
			java.sql.Date from, List<String> targetLotteryIdList) {
		Criteria c = createCriteria();
		if (status != -1) {
			c.add(Restrictions.eq("status", status));
		}
		if (from != null) {
			c.add(Restrictions.ge("createdTime", from));
		}
		if (null != targetLotteryIdList && !targetLotteryIdList.isEmpty()) {
			c.add(Restrictions.in("lotteryId", targetLotteryIdList));
		}
		// 不出混合过关方式的票
		// c.add(Restrictions.ne("playId",
		// PlayType.JCZQ_HH.getPlayIdWithPass(false)));
		// c.add(Restrictions.ne("playId",
		// PlayType.JCLQ_HH.getPlayIdWithPass(false)));
		return c.list();
	}

	@Override
	public List<BetSchemePO> findNewestWinScheme(String lotteryId, int status,
			int maxResults) {
		Criteria c = createCriteria();
		if (StringUtils.isNotBlank(lotteryId)) {
			c.add(Restrictions.eq("lotteryId", lotteryId));
		}
		if (status > 0) {
			c.add(Restrictions.eq("status", status));
		}
		if (maxResults > 0) {
			c.setFirstResult(0).setMaxResults(maxResults);
		}

		return c.addOrder(Order.desc("createdTime")).list();
	}

	@Override
	public List<BetSchemePO> findFollowSchemesWithPager(Long id,
			int startPosition) {
		return createCriteria().add(Restrictions.eq("followedSchemeId", id))
				.add(Restrictions.eq("type", 2)).list(); 
		//.setFirstResult(startPosition).setMaxResults(20) 查询所有
	}

	@Override
	public List<BetSchemePO> queryShowingScheme(String schemeType,
			Integer firstResult, Integer pageMaxResult) {
		Criteria c = createCriteria();

		c.add(Restrictions.eq("showScheme", Constants.SHOW_SCHEME));
		c.add(Restrictions.eq("type", Constants.TYPE_BUY));
		c.add(Restrictions.gt("offtime", new Date()));
//		c.add(Restrictions.ne("playId", Constants.PLAY_05_ZC_2));
//		c.add(Restrictions.ne("playId", Constants.PLAY_10_LC_2));
//		c.add(Restrictions.ne("playId", Constants.PLAY_80_ZC_1));
//		c.add(Restrictions.ne("playId", Constants.PLAY_80_ZC_2));

		if (schemeType.equals("JCZQ") || schemeType.equals("JCLQ")) {
			c.add(Restrictions.eq("lotteryId", schemeType));
		} else if(schemeType.equals("HOT")){
			c.add(Restrictions.eq("recommendation", Constants.RECOMMEND));
		}
		c.add(Restrictions.ne("lotteryId", LotteryId.BJDC.toString()));
		c.add(Restrictions.ne("lotteryId", LotteryId.BDSF.toString()));
		c.setFirstResult(firstResult);
		c.setMaxResults(pageMaxResult);
		addOrder4ShowingGroupbuy(c);
		return c.list();
	}

	@Override
	public List<Object[]> findBetSuccessNote(Long userId, Date start, Date end) {
		String hql = " select s.sponsorId,sum(s.ticketNote) as sucNote from BetSchemePO s "
				+ " where s.type in ("
				+ Constants.TYPE_BUY
				+ ","
				+ Constants.TYPE_FOLLOW
				+ ") "
				+ " and date(s.createdTime)>=? and date(s.createdTime)<=? "
				+ " and s.saleStatus="
				+ EntityStatus.SCHEME_SETTLEMENT
				+ " and s.lotteryId in ('"
				+ Constants.JCZQ
				+ "','"
				+ Constants.JCLQ + "') ";
		if (null != userId) {
			hql = hql + " and s.sponsorId=" + userId;
		}
		hql = hql + " group by s.sponsorId ";

		List<Object[]> betSchemePOs = createQuery(hql).setDate(0, start)
				.setDate(1, end).list();

		return betSchemePOs.size() > 0 ? betSchemePOs : null;
	}

	@Override
	public List<Object[]> findBetPassSuccessNote(Date start, Date end) {
		String hql = " select s.sponsorId,sum(s.ticketNote) as sucNote from BetSchemePO s "
				+ " where s.type in ("
				+ Constants.TYPE_BUY
				+ ","
				+ Constants.TYPE_FOLLOW
				+ ") "
				+ " and date(s.createdTime)>=? and date(s.createdTime)<=? "
				+ " and s.saleStatus="
				+ EntityStatus.SCHEME_SETTLEMENT
				+ " and s.lotteryId in ('"
				+ Constants.JCZQ
				+ "','"
				+ Constants.JCLQ
				+ "') "
				+ " and s.playId in ('"
				+ Constants.PLAY_01_ZC_2
				+ "','"
				+ Constants.PLAY_02_ZC_2
				+ "','"
				+ Constants.PLAY_03_ZC_2
				+ "','"
				+ Constants.PLAY_04_ZC_2
				+ "','"
				+ Constants.PLAY_06_LC_2
				+ "','"
				+ Constants.PLAY_07_LC_2
				+ "','"
				+ Constants.PLAY_08_LC_2
				+ "','"
				+ Constants.PLAY_09_LC_2
				+ "','"
				+ Constants.PLAY_05_ZC_2
				+ "','"
				+ Constants.PLAY_10_LC_2
				+ "','"
				+ Constants.PLAY_80_ZC_2
				+ "') " + " group by s.sponsorId ";

		List<Object[]> betSchemePOs = createQuery(hql).setDate(0, start)
				.setDate(1, end).list();

		return betSchemePOs.size() > 0 ? betSchemePOs : null;
	}

	@Override
	public List<BetSchemePO> queryShowingSchemeAll(String schemeType,
			Integer firstResult, Integer pageMaxResult) {
		Criteria c = createCriteria();

		c.add(Restrictions.eq("showScheme", Constants.SHOW_SCHEME));
		c.add(Restrictions.eq("type", Constants.TYPE_BUY));
		c.add(Restrictions.gt("offtime", new Date()));

		if (schemeType.equals("JCZQ") || schemeType.equals("JCLQ")) {
			c.add(Restrictions.eq("lotteryId", schemeType));
		} else if(schemeType.equals("HOT")){
			c.add(Restrictions.eq("recommendation", Constants.RECOMMEND));
		}
		c.add(Restrictions.ne("lotteryId", LotteryId.BJDC.toString()));
		c.add(Restrictions.ne("lotteryId", LotteryId.BDSF.toString()));
		c.setFirstResult(firstResult);
		c.setMaxResults(pageMaxResult);
		addOrder4ShowingGroupbuy(c);
		return c.list();
	}

	@Override
	public List<BetSchemePO> findByStatus(int status, Date from, Date to) {
		Criteria c = createCriteria();
		if (status != -1) {
			c.add(Restrictions.eq("status", status));
		}
		if (from != null) {
			c.add(Restrictions.ge("createdTime", from));
		}
		if (to != null) {
			c.add(Restrictions.le("createdTime", to));
		}
		return c.list();
	}
	
	@Override
	public List<BetSchemePO> findByStatusDesc(int status, Date from, Date to) {
		Criteria c = createCriteria();
		if (status != -1) {
			c.add(Restrictions.eq("status", status));
		}
		if (from != null) {
			c.add(Restrictions.ge("createdTime", from));
		}
		if (to != null) {
			c.add(Restrictions.le("createdTime", to));
		}
		c.addOrder(Order.asc("createdTime"));
		return c.list();
	}

	@Override
	public List<BetSchemePO> findByStatusAndNotSaleStatus(int status,
			int saleStatus, Date from, Date to) {
		Criteria c = createCriteria();
		if (status != -1) {
			c.add(Restrictions.eq("status", status));
		}
		if (saleStatus != -1) {
			c.add(Restrictions.ne("saleStatus", saleStatus));
		}
		if (from != null) {
			c.add(Restrictions.ge("createdTime", from));
		}
		if (to != null) {
			c.add(Restrictions.le("createdTime", to));
		}
		return c.list();
	}

	@Override
	public List<BetSchemePO> findBySponsorAndShowScheme(Long sponsorId,
			boolean isShowScheme, Date from, Date to) {
		Criteria c = createCriteria();
		if (sponsorId > 0) {
			c.add(Restrictions.eq("sponsorId", sponsorId));
		} else {
			return null;
		}
		if(isShowScheme){
			c.add(Restrictions.eq("showScheme", 1));
		}
		
		if (from != null) {
			c.add(Restrictions.ge("createdTime", from));
		}
		if (to != null) {
			c.add(Restrictions.le("createdTime", to));
		}
		return c.list();
	}

	@Override
	public List<BetSchemePO> findScheme(int publishShow, int type, Date from,
			int status,int maxResults) {
		Criteria c = createCriteria();
		c.add(Restrictions.eq("isPublishShow", publishShow));
		c.add(Restrictions.eq("type", type));
		c.add(Restrictions.eq("status", status));
		if (null != from) {
			c.add(Restrictions.ge("createdTime", from));
		}
		if (maxResults > 0) {
			c.setFirstResult(0).setMaxResults(maxResults);
		}
		return c.addOrder(Order.desc("afterTaxBonus")).list();
	}

	@Override
	public List<Object> findProfessionalSchemeTicketSuccess(String lotteryId) {
		StringBuilder sb = new StringBuilder();
		sb.append("select distinct bs.id from lt_bet_scheme bs, lt_ticket tk ")
		.append("where bs.status = 5100 and now() > bs.public_time and bs.preset_award = 0 ")
		.append("and bs.sponsor_id in (select uid from db_lottery.lt_pu where ut=1) ")
		.append("and bs.id = tk.scheme_id and tk.status = 5100 and bs.lottery_id = ? ")
		.append("order by bs.created_time asc");
		
		SQLQuery sqlQuery = createSQLQuery(sb.toString());
		sqlQuery.setParameter(0, lotteryId);
		
		return sqlQuery.list();
	}
	
	@Override
	public List<Long> findFBPresetMatchIds(long schemeId) {
		StringBuilder sb = new StringBuilder();
		
		sb.append("select bm.match_id from lt_bet_match bm, fb_match fm ")
		.append("where bm.scheme_id = ? and bm.match_id = fm.id ")
		.append("and fm.status in (4, 5) and fm.half_score_preset <> '' ")
		.append("and fm.score_preset <> '' and now() > fm.playing_time ");
		
		SQLQuery sqlQuery = createSQLQuery(sb.toString());
		sqlQuery.setParameter(0, schemeId);
		return sqlQuery.list();
	}

	@Override
	public List<Long> findBBPresetMatchIds(long schemeId) {
		StringBuilder sb = new StringBuilder();
		
		sb.append("select lbm.match_id from lt_bet_match lbm, bb_match bbm ")
		.append("where lbm.scheme_id = ? and lbm.match_id = bbm.id ")
		.append("and bbm.status in (4, 5) and bbm.final_score_preset <> '' ")
		.append(" and now() > bbm.playing_time");

		SQLQuery sqlQuery = createSQLQuery(sb.toString());
		sqlQuery.setParameter(0, schemeId);
		return sqlQuery.list();
	}
	
	public List<Object> getLastFourSchemeId() {
		String sql="select id from lt_bet_scheme where is_show_scheme=1 and status=5100 and is_publish_show=1 order by created_time desc limit 0,4";
		return createSQLQuery(sql).list();
	}

	@Override
	public Integer getShowBetShemeNum(String beginTime,String endTime) {
	     String sql="select count(*) from lt_bet_scheme s where s.is_show_scheme=1 and s.created_time between :beg and :end";
		 return Integer.parseInt(createSQLQuery(sql).setParameter("beg", beginTime).setParameter("end",endTime).uniqueResult().toString());
	}
    //晒单助人50场
	@Override
	public List<Object[]> findMatch50ShowSchemeWin() {
		String sql=" select s.sponsor_id,count(s.sponsor_id) spnum, "
				  +"  sum(t.total_money+s.after_tax_bonus)  money, t.num from" 
				  +" (select l.followed_scheme_id,count(l.followed_scheme_id) num,sum(l.after_tax_bonus) total_money"
				  +"    from lt_bet_scheme l where"
				  +"     l.`status` in('5203','12') and l.type=2 and "
				  +"    l.is_publish_show=1 and l.after_tax_bonus> l.total_amount"
				  +"     group by l.followed_scheme_id order by l.created_time desc limit 0,50"
				  + " ) t ,lt_bet_scheme s "
				  +" where s.id=t.followed_scheme_id "
				  +" group by s.sponsor_id order by money desc limit 0,5";
		return createSQLQuery(sql).list();
	}

	@Override
	public List<Object[]> findMatch50FollowSchemeWin() {
		String sql="   select w1.sponsor_id,w1.win_follow,w1.money,w2.all_follow   from "
					+"   (select count(l.followed_scheme_id) win_follow,"
					+"	   sum(l.after_tax_bonus) money,l.sponsor_id from lt_bet_scheme l "
					+"	   where l.`type`=2 and l.is_publish_show=1 and l.`status` in('5203','12')"
					+"     and l.after_tax_bonus> l.total_amount "
					+"	   group by l.sponsor_id order by l.created_time desc limit 0,50 "
					+"	 ) w1,"
					+"   (select count(s.followed_scheme_id) all_follow,s.sponsor_id from "
					+"	   lt_bet_scheme s"
					+"     where s.`type`=2 and s.is_publish_show=1" 
					+"	   and s.`status` in('5203','5202','12')"
					+"     group by s.sponsor_id order by s.created_time desc limit 0,50"
					+"	)  w2 "
					+"	where w1.sponsor_id=w2.sponsor_id order by w1.money desc limit 0,5";
		return createSQLQuery(sql).list();
	}

	@Override
	public List<Object[]> findDay7ShowSchemeWinLiLv() {
		String sql="select  m.sid,m.q,n.w ,n.w/m.q as lilv from"
				+ " (select t.sid,count(t.sid) as q from "
                  +"   (select l.sponsor_id sid,l.`status` lstatus,l.id schemeid from lt_bet_scheme l where l.type=0 "
                  +"     and l.is_show_scheme=1 and l.`status` in ('5202','5203','12') and "
                  +"     l.created_time > concat(DATE_SUB(curdate(), INTERVAL 7 DAY), ' 00:00:00')"
                  +"     and l.created_time<=concat(DATE_SUB(curdate(), INTERVAL 1 DAY), ' 23:59:59') order by sid ) t"
                  +"     group by t.sid"
                  + "   ) m,"
                  + "   (select t.sid,count(t.sid) as w from"
                  +"      (select l.sponsor_id sid,l.`status` lstatus,l.id schemeid from lt_bet_scheme l where l.type=0" 
                  +"      and l.is_show_scheme=1 and l.`status` in ('5202','5203','12')"
                  +"      and l.created_time > concat(DATE_SUB(curdate(), INTERVAL 7 DAY), ' 00:00:00')"
                  +"      and l.created_time<=concat(DATE_SUB(curdate(), INTERVAL 1 DAY), ' 23:59:59') order by sid "
                  + "     ) t  where t.lstatus=12 group by t.sid"
                  + "   ) n where m.sid=n.sid  order by lilv desc,sid asc limit 0,5";
		return  createSQLQuery(sql).list();
	}

	@Override
	public List<Object[]> findMatch50ShowSchemeWinLilv() {
		String sql="select  m.sid,m.q,n.w ,n.w/m.q as lilv from (select t.sid,count(t.sid) as q from"
                  +" (select l.sponsor_id sid,l.`status` lstatus,l.id schemeid from lt_bet_scheme l where l.type=0 "
                  +" and l.is_show_scheme=1 and l.`status` in ('5202','5203','12') order by l.created_time desc limit 50 ) t"
                  +" group by t.sid) m,(select t.sid,count(t.sid) as w from"
                  +" (select l.sponsor_id sid,l.`status` lstatus,l.id schemeid from lt_bet_scheme l where l.type=0 "
                  +" and l.is_show_scheme=1 and l.`status` in ('5202','5203','12') order by l.created_time desc limit 50  ) t"
                  +" where t.lstatus=12 group by t.sid) n where m.sid=n.sid  order by lilv desc,m.q desc limit 0,5";
		return createSQLQuery(sql).list();
	}

	@Override
	public List<Object[]> findMath50FollowSchemeWinLiLv() {
		 String sql="select  m.sid,m.q,n.w ,n.w/m.q as lilv from"
                   +" (select t.sid,count(t.sid) as q from"
                   +"   (select l.sponsor_id sid,l.`status` lstatus,l.id schemeid from lt_bet_scheme l where l.type=2"
                   +"    and l.is_publish_show=1 and l.`status` in ('5202','5203','12')"
                   +"    order by l.created_time desc limit 50 "
                   +"    ) t group by t.sid"
                   +"  ) m,"
                   +" (select t.sid,count(t.sid) as w from"
                   +"   (select l.sponsor_id sid,l.`status` lstatus,l.id schemeid from lt_bet_scheme l where l.type=2"
                   +"    and l.is_publish_show=1 and l.`status` in ('5202','5203','12')"
                   +"    order by l.created_time desc limit 50 "
                   +"   ) t"
                   +"   where t.lstatus=12 group by t.sid"
                   +" ) n  "
                   +" where m.sid=n.sid  order by lilv desc,m.q desc limit 0,5";
		return createSQLQuery(sql).list();
	}

	@Override
	public List<Object[]> findDay7FollowSchemeWinLiLv() {
		String sql="select  m.sid,m.q,n.w ,n.w/m.q as lilv from "
				  + " (select t.sid,count(t.sid) as q from "
                  +"    (select l.sponsor_id sid,l.`status` lstatus,l.id schemeid from lt_bet_scheme l where l.type=2 "
                  +"     and l.is_publish_show=1 and l.`status` in ('5202','5203','12') and "
                  +"     l.created_time > concat(DATE_SUB(curdate(), INTERVAL 7 DAY), ' 00:00:00')"
                  +"     and l.created_time<=concat(DATE_SUB(curdate(), INTERVAL 1 DAY), ' 23:59:59') order by sid "
                  + "    ) t group by t.sid"
                  +"   ) m,"
                  + " (select t.sid,count(t.sid) as w from"
                  +"    (select l.sponsor_id sid,l.`status` lstatus,l.id schemeid from lt_bet_scheme l where l.type=2"
                  +"    and l.is_publish_show=1 and l.`status` in ('5202','5203','12')"
                  +"    and l.created_time > concat(DATE_SUB(curdate(), INTERVAL 7 DAY), ' 00:00:00')"
                  +"    and l.created_time<=concat(DATE_SUB(curdate(), INTERVAL 1 DAY), ' 23:59:59') order by sid "
                  + "   ) t"
                  +"    where t.lstatus=12 group by t.sid"
                  + "  ) n where m.sid=n.sid  order by lilv desc,sid asc limit 0,5";
		return createSQLQuery(sql).list();
	}

	@Override
	public List<BetSchemePO> findYesterdayScheme(Date beginTime, Date endTime) {
		Criteria cr=createCriteria();
		cr.add(Restrictions.gt("createdTime", beginTime));
		cr.add(Restrictions.le("createdTime", endTime));
		cr.add(Restrictions.in("status", new Integer[]{5203,12}));
		cr.addOrder(Order.asc("createdTime"));
		return cr.list();
	}

	@Override
	public List<Object[]> findBetRecord(String lotteryId, long userId, int count) {
		String sql = new String("select id, type, is_show_scheme, is_publish_show, created_time from lt_bet_scheme bs "+
			"where bs.sponsor_id = ? and lottery_id = ? "+
			"order by bs.created_time desc "+
			"limit 0, ?");
		
		SQLQuery sqlQuery = createSQLQuery(sql);
		sqlQuery.setLong(0, userId);
		sqlQuery.setString(1, lotteryId);
		sqlQuery.setInteger(2, count);
		
		return sqlQuery.list();
	}
	
	@Override
	public List<Object[]> queryShowingSchemeBy(String fromDate,
			String followCountSort, String buyAmountSort,
			String rateOfReturnSort, String timeSort,String lottery) {
		String sql = "";
		String lotteryQuery = "";
		if(!"ALL".equals(lottery)){
			lotteryQuery = " and lottery_id='" + lottery + "' ";
		}
		if(null != rateOfReturnSort && ("asc".equals(rateOfReturnSort) || "desc".equals(rateOfReturnSort))){//按照回报率排序
			sql = " select s.id,s.max_bonus/s.buy_amount rateOfReturn from lt_bet_scheme s "+ 
				  " where s.created_time >= '"+fromDate+"' and s.is_show_scheme = 1 "+ lotteryQuery +
				  " order by rateOfReturn "+rateOfReturnSort+";";
		} else if(null != timeSort && ("asc".equals(timeSort) || "desc".equals(timeSort))){
			sql = " select s.id,s.created_time from lt_bet_scheme s " + 
				  " where s.created_time > '"+fromDate+"' and s.is_show_scheme = 1"+ lotteryQuery +
				  " order by s.created_time "+timeSort+";";
		} else if(null != buyAmountSort && ("asc".equals(buyAmountSort) || "desc".equals(buyAmountSort))){
			sql = " select s.id,s.buy_amount from lt_bet_scheme s " + 
				  " where s.created_time > '"+fromDate+"' and s.is_show_scheme = 1"+ lotteryQuery +
				  " order by s.buy_amount "+buyAmountSort+";";
		} else if(null != followCountSort && ("asc".equals(followCountSort) || "desc".equals(followCountSort))){
			sql = " select s.id,s.following_count from lt_bet_scheme s " + 
				  " where s.created_time > '"+fromDate+"' and s.is_show_scheme = 1"+ lotteryQuery +
				  " order by s.following_count "+followCountSort+";";
		}
		if(StringUtils.isNotBlank(sql)){
			return createSQLQuery(sql).list();
		} else {
			return null;
		}
	}

	@Override
	public List<BetSchemePO> queryShowingGroupbuy(String schemeType,
			int firstResult, int pageMaxResult) {
		Criteria c = createCriteria();

		prepareCriteria4ShowingGroupbuy(schemeType, c);
		c.setFirstResult(firstResult);
		c.setMaxResults(pageMaxResult);
		addOrder4ShowingGroupbuy(c);
		return c.list();
	}

	private void addOrder4ShowingGroupbuy(Criteria c) {
		c.addOrder(org.hibernate.criterion.Order.desc("createdTime"));
		c.addOrder(org.hibernate.criterion.Order.desc("followingCount"));
		c.addOrder(org.hibernate.criterion.Order.asc("offtime"));
		c.addOrder(org.hibernate.criterion.Order.desc("totalAmount"));
	}

	private void prepareCriteria4ShowingGroupbuy(String schemeType, Criteria c) {
		c.add(Restrictions.eq("type", Constants.TYPE_GROUP));
		c.add(Restrictions.gt("offtime", new Date()));

		if (schemeType.equals("JCZQ") || schemeType.equals("JCLQ")) {
			c.add(Restrictions.eq("lotteryId", schemeType));
		} else if(schemeType.equals("HOT")){
			c.add(Restrictions.eq("recommendation", Constants.RECOMMEND));
		}
		c.add(Restrictions.ne("lotteryId", LotteryId.BJDC.toString()));
		c.add(Restrictions.ne("lotteryId", LotteryId.BDSF.toString()));
	}

	@Override
	public List<BetSchemePO> findOrderByOfftimeAsc(Paging paging, Date from,
			Date to, int status, String lotteryId, Long schemeId,
			String sponsor, String playId, String passType) {
		PagingQuery<BetSchemePO> pq = pagingQuery(paging);
		pq.add(Restrictions.ge("createdTime", from)).add(
				Restrictions.lt("createdTime", to));
		addCommonRestrictions(pq, status, lotteryId, schemeId, sponsor, playId,
				passType, -1);
	
		return pq.desc("newCreatedTime").asc("offtime").list();
	}

	@Override
	public List<BetSchemePO> queryShowingGroupbuyWithoutPage(String schemeType) {
		Criteria c = createCriteria();
		prepareCriteria4ShowingGroupbuy(schemeType, c);
		addOrder4ShowingGroupbuy(c);
		return c.list();
	}
}

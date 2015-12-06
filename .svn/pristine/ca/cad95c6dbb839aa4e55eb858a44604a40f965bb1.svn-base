package com.xhcms.lottery.commons.persist.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.jdbc.Work;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xhcms.commons.lang.Paging;
import com.xhcms.commons.persist.hibernate.DaoImpl;
import com.xhcms.lottery.commons.data.IssueInfo;
import com.xhcms.lottery.commons.persist.dao.IssueInfoDao;
import com.xhcms.lottery.commons.persist.entity.IssueInfoPO;
import com.xhcms.lottery.lang.Constants;
import com.xhcms.lottery.lang.LotteryId;
import com.xhcms.lottery.lang.PlayType;

@SuppressWarnings("unchecked")
public class IssueinfoDaoImpl extends DaoImpl<IssueInfoPO> implements
		IssueInfoDao {
	private static final long serialVersionUID = 4582112329281257477L;
	private Logger logger = LoggerFactory.getLogger(getClass());

	public IssueinfoDaoImpl() {
		super(IssueInfoPO.class);
	}

	@Override
	public IssueInfoPO findByLotteryIdAndIssueNumber(String lotteryId,
			String issueNumber) {

		Criteria c = createCriteria();
		if (StringUtils.isBlank(lotteryId) || StringUtils.isBlank(issueNumber)) {
			return null;
		}
		c.add(Restrictions.eq("lotteryId", lotteryId)).add(
				Restrictions.eq("issueNumber", issueNumber));
		return (IssueInfoPO) c.uniqueResult();
	}

	@Override
	public List<IssueInfoPO> findListByLotteryIdAndIssueNumber(
			List<IssueInfo> issueinfos) {
		if (null != issueinfos && !issueinfos.isEmpty()) {
			Criteria c = createCriteria();
			Disjunction conjunction = Restrictions.disjunction();

			for (IssueInfo issueinfo : issueinfos) {

				Criterion andCriterion = createAndCriterion(issueinfo);
				String playId = issueinfo.getPlayId();

				if (!Constants.JX11.equals(issueinfo.getLotteryId())
						&& StringUtils.isNotBlank(playId)) {
					c.add(Restrictions.eq("playId", playId));
				}

				if (null != andCriterion) {
					conjunction.add(andCriterion);
				}
			}
			if (null != conjunction) {
				c.add(conjunction);
				return c.list();
			}
		}
		return null;
	}

	private Criterion createAndCriterion(IssueInfo issueinfo) {
		String lotteryId = issueinfo.getLotteryId();
		String issueNumber = issueinfo.getIssueNumber();

		if (StringUtils.isBlank(lotteryId) || StringUtils.isBlank(issueNumber)) {
			return null;
		}
		return Restrictions.and(Restrictions.eq("lotteryId", lotteryId),
				Restrictions.eq("issueNumber", issueNumber));
	}

	@Override
	public void batchSaveOrUpdate(List<IssueInfoPO> issueinfoPOsShouldUpdate,
			List<IssueInfoPO> issueinfoPOsShouldInsert) {

		batchUpdate(issueinfoPOsShouldUpdate);
		batchSave(issueinfoPOsShouldInsert);
	}

	private void batchSave(final List<IssueInfoPO> issueinfoPOsShouldInsert) {
		if (null != issueinfoPOsShouldInsert
				&& !issueinfoPOsShouldInsert.isEmpty()) {
			logger.debug("需要新增{}条数据", issueinfoPOsShouldInsert.size());
			this.session().doWork(new Work() {
				@Override
				public void execute(Connection conn) throws SQLException {
					String sql = "insert into lt_issueinfo("
							+ "start_time,stop_time,stop_time_for_user,close_time,status,bonus_code,"
							+ "bonus_info,lottery_id,issue_number,zm_close_time,play_id, prize_time, " +
							"created_time, update_time,is_valid)  values("
							+ "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
					PreparedStatement stmt = conn.prepareStatement(sql);
					int i = 0;
					for (IssueInfoPO issueinfoPO : issueinfoPOsShouldInsert) {
						stmt.setTimestamp(1,
								initDateTimeProperty4JDBC(issueinfoPO
										.getStartTime()));
						stmt.setTimestamp(2,
								initDateTimeProperty4JDBC(issueinfoPO
										.getStopTime()));
						stmt.setTimestamp(3,
								initDateTimeProperty4JDBC(issueinfoPO
										.getStopTimeForUser()));
						stmt.setTimestamp(4,
								initDateTimeProperty4JDBC(issueinfoPO
										.getCloseTime()));
						stmt.setInt(5, issueinfoPO.getStatus());
						stmt.setString(6, issueinfoPO.getBonusCode());
						stmt.setString(7, issueinfoPO.getBonusInfo());
						stmt.setString(8, issueinfoPO.getLotteryId());
						stmt.setString(9, issueinfoPO.getIssueNumber());
						stmt.setTimestamp(10,
								initDateTimeProperty4JDBC(issueinfoPO
										.getZMCloseTime()));
						stmt.setString(11, issueinfoPO.getPlayId());
						stmt.setTimestamp(12,
								initDateTimeProperty4JDBC(issueinfoPO
										.getPrizeTime()));
						
						Timestamp now = initDateTimeProperty4JDBC(new Date());
						stmt.setTimestamp(13, now);
						stmt.setTimestamp(14, now);
						stmt.setBoolean(15, issueinfoPO.getValid());
						stmt.addBatch();

						if (++i % 20 == 0) {
							stmt.executeBatch();
						}
					}
					if (i % 20 != 0) {
						stmt.executeBatch();
					}
				}

				private Timestamp initDateTimeProperty4JDBC(java.util.Date date) {
					return null == date ? null : new java.sql.Timestamp(date
							.getTime());
				}
			});
		} else {
			logger.debug("需要新增0条数据");
		}

	}

	private void batchUpdate(final List<IssueInfoPO> issueinfoPOsShouldUpdate) {
		if (null != issueinfoPOsShouldUpdate
				&& !issueinfoPOsShouldUpdate.isEmpty()) {
			logger.debug("需要更新{}条数据", issueinfoPOsShouldUpdate.size());
			this.session().doWork(new Work() {
				@Override
				public void execute(Connection conn) throws SQLException {
					String sql = "update lt_issueinfo set start_time=?, stop_time=?," +
							"stop_time_for_user=?,close_time=?,status=?,bonus_code=?," +
							"bonus_info=?,zm_close_time=?,prize_time=?,update_time=? " +
							"where id=?";
					PreparedStatement stmt = conn.prepareStatement(sql);
					int i = 0;
					for (IssueInfoPO issueinfoPO : issueinfoPOsShouldUpdate) {
						stmt.setTimestamp(1,
								initDateTimeProperty4JDBC(issueinfoPO
										.getStartTime()));
						stmt.setTimestamp(2,
								initDateTimeProperty4JDBC(issueinfoPO
										.getStopTime()));
						stmt.setTimestamp(3,
								initDateTimeProperty4JDBC(issueinfoPO
										.getStopTimeForUser()));
						stmt.setTimestamp(4,
								initDateTimeProperty4JDBC(issueinfoPO
										.getCloseTime()));
						stmt.setInt(5, issueinfoPO.getStatus());
						stmt.setString(6, issueinfoPO.getBonusCode());
						stmt.setString(7, issueinfoPO.getBonusInfo());
						stmt.setTimestamp(8,
								initDateTimeProperty4JDBC(issueinfoPO
										.getZMCloseTime()));
						stmt.setTimestamp(9,
								initDateTimeProperty4JDBC(issueinfoPO
										.getPrizeTime()));
						
						Timestamp now = initDateTimeProperty4JDBC(new Date());
						stmt.setTimestamp(10, now);
						
						stmt.setLong(11, issueinfoPO.getId());
						stmt.addBatch();

						if (++i % 20 == 0) {
							stmt.executeBatch();
						}
					}
					if (i % 20 != 0) {
						stmt.executeBatch();
					}
					logger.debug("更新结束！");
				}

				private Timestamp initDateTimeProperty4JDBC(java.util.Date date) {
					return null == date ? null : new java.sql.Timestamp(date
							.getTime());
				}
			});
		}

		else {
			logger.debug("需要更新0条数据");
		}
	}

	@Override
	public void updateLCStatusFromZMStatus(List<String> lotteryIds) {
		if (null != lotteryIds && !lotteryIds.isEmpty()) {
			Query query = this.createQuery(
					"update IssueInfoPO " + "set LCStatus=status "
							+ "where lotteryId in (:lotteryIds)")
					.setParameterList("lotteryIds", lotteryIds);
			query.executeUpdate();
		}

	}

	@Override
	public void batchUpdateLCStatus2Saling(List<IssueInfoPO> salingIssueList) {
		String id4Update = constructIdList4Update(salingIssueList);
		if (StringUtils.isNotBlank(id4Update)) {
			Query query = this.createQuery("update IssueInfoPO set LCStatus=1 "
					+ "where " + "id in (" + id4Update + ")");
			query.executeUpdate();
		}
	}

	private String constructIdList4Update(List<IssueInfoPO> salingIssueList) {
		String result = null;
		if (null != salingIssueList && !salingIssueList.isEmpty()) {
			List<String> ids = new ArrayList<String>();
			for (IssueInfoPO po : salingIssueList) {
				ids.add(Long.toString(po.getId()));
			}
			result = StringUtils.join(ids, ",");

		}
		return result;
	}

	@Override
	public List<String> findDistinctLotteryId() {
		Query query = this
				.createQuery("select  distinct lotteryId from IssueInfoPO ");

		return query.list();
	}

	@Override
	public IssueInfoPO findValidSalingTimeByLotteryId(String lotteryId,
			Date date) {
		IssueInfoPO issueinfoPO = null;

		Query query = this
				.createQuery(
						"select  min(startTime)  ,max(closeTime) from IssueInfoPO "
								+ "where " + "lotteryId=:lotteryId "
								+ "and valid=true "
								+ "and date(startTime)=date(:now) ")
				.setString("lotteryId", lotteryId).setDate("now", date);
		Object result = query.uniqueResult();

		Object[] resultArray = (Object[]) result;
		if (null != resultArray) {
			issueinfoPO = new IssueInfoPO();
			issueinfoPO.setStartTime((Date) resultArray[0]);
			issueinfoPO.setCloseTime((Date) resultArray[1]);
		}
		return issueinfoPO;
	}

	@Override
	public IssueInfoPO findValidIssueByLotteryIdBetweenStartTimeAndStopTimeForUser(
			java.util.Date now, String lotteryId) {
		Timestamp timestamp = new Timestamp(now.getTime());
		Query query = this
				.createQuery(
						"from IssueInfoPO "
								+ "where "
								+ "lotteryId=:lotteryId "
								+ "and valid=true "
								+ "and (:now between startTime and stopTimeForUser ) ")
				.setString("lotteryId", lotteryId)
				.setTimestamp("now", timestamp);
		return (IssueInfoPO) query.uniqueResult();
	}

	@Override
	public List<IssueInfoPO> findValidOnSaleIssueListByPlayId(
			java.util.Date now, String playId) {
		if (now == null) {
			now = new Date();
		}
		Timestamp timestamp = new Timestamp(now.getTime());
		Criteria c = createCriteria();
		c.add(Restrictions.eq("valid", true));
		c.add(Restrictions.gt("stopTimeForUser", timestamp));
		if (StringUtils.isNotBlank(playId)) {
			c.add(Restrictions.eq("playId", playId));
		}
		c.addOrder(Order.asc("stopTimeForUser"));
		return c.list();
	}

	@Override
	public List<IssueInfoPO> findIssuesBetweenStopTimeForUser(String playId,
			Date beginStopTime, Date endStopTime) {
		Criteria c = createCriteria();
		if (StringUtils.isBlank(playId)) {
			return c.list();
		}
		if (beginStopTime == null) {
			beginStopTime = new Date();
		}
		if (endStopTime != null) {
			Timestamp endTimestamp = new Timestamp(endStopTime.getTime());
			c.add(Restrictions.le("stopTimeForUser", endTimestamp));
		}

		Timestamp beginTimestamp = new Timestamp(beginStopTime.getTime());
		c.add(Restrictions.eq("valid", true));
		c.add(Restrictions.eq("playId", playId));
		c.add(Restrictions.gt("stopTimeForUser", beginTimestamp));
		c.addOrder(Order.asc("stopTimeForUser"));
		return c.list();
	}

	@Override
	public IssueInfoPO findBeforeIssueInfoByPlayId(java.util.Date now,
			String playId) {
		Query query = this
				.createQuery(
						"from IssueInfoPO " + "where " + "playId=:playId "
								+ "and valid=true "
								+ "order by issueNumber desc")
				.setString("playId", playId).setFirstResult(0).setMaxResults(1);
		return (IssueInfoPO) query.uniqueResult();
	}

	@Override
	public IssueInfoPO findValidIssueRecentlyByLotteryId(java.util.Date now,
			String lotteryId) {
		Timestamp timestamp = new Timestamp(now.getTime());
		Query query = this
				.createQuery(
						"from IssueInfoPO " + "where "
								+ "lotteryId=:lotteryId " + "and valid=true "
								+ "and startTime>=:now "
								+ "order by startTime asc").setFirstResult(0)
				.setMaxResults(1).setString("lotteryId", lotteryId)
				.setTimestamp("now", timestamp);
		return (IssueInfoPO) query.uniqueResult();
	}

	@Override
	public List<String> findValidDistinctLotteryIdInExpectedListByDate(
			Date date, List<String> expectedLotteryIdList) {
		Query query = createQuery(
				"select  distinct lotteryId from IssueInfoPO " + "where "
						+ "date(startTime)=date(:now) " + "and valid=true "
						+ "and lotteryId in (:expectedLotteryIdList)").setDate(
				"now", date).setParameterList("expectedLotteryIdList",
				expectedLotteryIdList);

		return query.list();
	}

	@Override
	public List<String> findValidLotteryIdOfWF(Date date,
			List<String> expectedLotteryIdList) {
		Query query = createQuery(
				"select distinct lotteryId from IssueInfoPO " + "where "
						+ ":now between startTime and closeTime "
						+ "and valid=true "
						+ "and lotteryId in (:expectedLotteryIdList)")
				.setTimestamp("now", date).setParameterList(
						"expectedLotteryIdList", expectedLotteryIdList);
		return query.list();
	}

	@Override
	public IssueInfoPO findValidBuyTicketTimeByLotteryId(String lotteryId,
			Date targetDate) {
		IssueInfoPO issueinfoPO = null;

		Query query = this
				.createQuery(
						"select  min(startTime)  ,max(ZMCloseTime) from IssueInfoPO "
								+ "where " + "lotteryId=:lotteryId "
								+ "and valid=true "
								+ "and date(startTime)=date(:now) ")
				.setString("lotteryId", lotteryId).setDate("now", targetDate);
		Object result = query.uniqueResult();

		Object[] resultArray = (Object[]) result;
		if (null != resultArray) {
			issueinfoPO = new IssueInfoPO();
			issueinfoPO.setStartTime((Date) resultArray[0]);
			issueinfoPO.setZMCloseTime((Date) resultArray[1]);
		}
		return issueinfoPO;
	}

	@Override
	public List<IssueInfoPO> getCurrentSalingIssue(Paging paging,
			String lotteryId, Date from, Date to, int status) {
		PagingQuery<IssueInfoPO> q = pagingQuery(paging);
		if (status != -1) {
			q.add(Restrictions.eq("status", status));
		}
		if (null != lotteryId && !"".equals(lotteryId)) {
			q.add(Restrictions.eq("lotteryId", lotteryId));
		}
		if (null != from) {
			q.add(Restrictions.gt("startTime", from));
		}
		if (null != to) {
			q.add(Restrictions.lt("startTime", to));
		}
		return q.desc("id").list();
	}

	@Override
	public List<IssueInfoPO> findIssue(Paging paging, String lotteryId,
			Date from, Date to, int status) {
		PagingQuery<IssueInfoPO> q = pagingQuery(paging);
		if (status != -1) {
			q.add(Restrictions.eq("status", status));
		}
		if (null != lotteryId && !"".equals(lotteryId)) {
			q.add(Restrictions.eq("lotteryId", lotteryId));
		}
		if (null != from) {
			//q.add(Restrictions.gt("closeTime", from));
			q.add(Restrictions.gt("createdTime", from));
		}
		if (null != to) {
			//q.add(Restrictions.lt("closeTime", to));
			q.add(Restrictions.lt("createdTime", to));
		}
		return q.desc("id").list();
	}

	@Override
	public List<IssueInfo> findBBallotRecords(String lotteryId,
			String[] status, String date, int maxResults) {

		StringBuffer sql = new StringBuffer();
		sql.append("from IssueInfoPO where ")
				.append("lotteryId = :lotteryId and date(startTime) = :date ")
				.append("and status in(:status_first, :status_second) ")
				.append("order by stopTime desc ");

		Query query = this.createQuery(String.valueOf(sql))
				.setString("lotteryId", lotteryId).setString("date", date)
				.setString("status_first", status[0])
				.setString("status_second", status[1]);
		if (0 < maxResults) {
			query.setFirstResult(0).setMaxResults(maxResults);
		}
		return query.list();
	}

	@Override
	public List<IssueInfoPO> findBBallotRecordsOfWF(String lotteryId,
			String[] status, String closeTime, int maxResults) {
		StringBuffer sql = new StringBuffer();
		sql.append("from IssueInfoPO where ").append("lotteryId = :lotteryId ")
				.append("and date(closeTime) <= date(:closeTime) ")
				.append("and status in(:status_first, :status_second) ")
				.append("order by closeTime desc ");

		Query query = this.createQuery(String.valueOf(sql))
				.setString("lotteryId", lotteryId)
				.setString("closeTime", closeTime)
				.setString("status_first", status[0])
				.setString("status_second", status[1]);
		if (0 < maxResults) {
			query.setFirstResult(0).setMaxResults(maxResults);
		}
		return query.list();
	}

	@Override
	public IssueInfoPO findValidSalingTimeForShowByLotteryId(String lotteryId,
			Date targetDate) {
		IssueInfoPO issueinfoPO = null;
		Query query = this
				.createQuery(
						"select  min(startTime)  ,max(stopTimeForUser) from IssueInfoPO "
								+ "where " + "lotteryId=:lotteryId "
								+ "and valid=true "
								+ "and date(startTime)=date(:now) ")
				.setString("lotteryId", lotteryId).setDate("now", targetDate);
		Object result = query.uniqueResult();

		Object[] resultArray = (Object[]) result;
		if (null != resultArray) {
			issueinfoPO = new IssueInfoPO();
			issueinfoPO.setStartTime((Date) resultArray[0]);
			issueinfoPO.setStopTimeForUser((Date) resultArray[1]);
		}
		return issueinfoPO;
	}

	@Override
	public IssueInfoPO findCurrentSalingIssueForShowByLotteryId(
			String lotteryId, Date targetDate) {
		Timestamp timestamp = new Timestamp(targetDate.getTime());
		Query query = this
				.createQuery(
						"from IssueInfoPO " + "where "
								+ "lotteryId=:lotteryId " + "and valid=true "
								+ "and LCStatus=:lcStatus "
								+ "and date(startTime)=date(:now) "
								+ "order by startTime desc ").setFirstResult(0)
				.setMaxResults(1).setString("lotteryId", lotteryId)
				.setTimestamp("now", timestamp).setInteger("lcStatus", 1);
		return (IssueInfoPO) query.uniqueResult();
	}

	@Override
	public IssueInfoPO findFirstByLotteryId(String lotteryId, Date targetDate) {
		Timestamp timestamp = new Timestamp(targetDate.getTime());
		Query query = this
				.createQuery(
						"from IssueInfoPO " + "where "
								+ "lotteryId=:lotteryId "
								+ "and date(startTime)=date(:now) "
								+ "and issueNumber like '%01'")

				.setString("lotteryId", lotteryId)
				.setTimestamp("now", timestamp);
		return (IssueInfoPO) query.uniqueResult();
	}

	@Override
	public boolean haveIssueInfoInTargetDate(String lotteryId, Date targetDate) {
		boolean result = false;
		Query query = this
				.createQuery(
						"select  count(*) from IssueInfoPO " + "where "
								+ "lotteryId=:lotteryId "
								+ "and date(startTime)=date(:now) ")
				.setString("lotteryId", lotteryId).setDate("now", targetDate);
		Object resultFromDB = query.uniqueResult();

		long count = (Long) resultFromDB;

		if (count > 0) {
			result = true;
		}

		return result;
	}

	@Override
	public IssueInfoPO findByPlayIdAndIssueNumber(String playId,
			String issueNumber) {

		Criteria c = createCriteria();
		if (StringUtils.isBlank(playId) || StringUtils.isBlank(issueNumber)) {
			return null;
		}
		c.add(Restrictions.eq("playId", playId)).add(
				Restrictions.eq("issueNumber", issueNumber));
		return (IssueInfoPO) c.uniqueResult();

	}

	@Override
	public List<IssueInfoPO> findOldIssuesNotEqualStatus(String playId,
			int status) {
		Criteria c = createCriteria();
		c.add(Restrictions.eq("playId", playId));
		c.add(Restrictions.ne("status", status));
		c.add(Restrictions.lt("closeTime", new Date()));

		return c.list();
	}

	@Override
	public List<IssueInfoPO> findOldIssuesByLottery(String lotteryId, int status) {
		Criteria c = createCriteria();
		c.add(Restrictions.eq("lotteryId", lotteryId));
		c.add(Restrictions.ne("status", status));
		c.add(Restrictions.lt("closeTime", new Date()));

		return c.list();
	}

	@Override
	public List<IssueInfoPO> findIssuesByPlayIdAndStatus(String playId,
			int status) {
		Criteria c = createCriteria();
		c.add(Restrictions.eq("playId", playId));
		c.add(Restrictions.eq("status", status));
		c.add(Restrictions.eq("valid", true));

		return c.list();
	}

	@Override
	public String findOnSaleIssueBy(String lotteryId, String playId, int status) {
		Query query = createQuery("select max(issueNumber) from IssueInfoPO "
				+ "where lotteryId = ? and playId = ? and status = ? and valid = true");
		query.setParameter(0, lotteryId);
		query.setParameter(1, playId);
		query.setParameter(2, status);
		return (String) query.uniqueResult();
	}

	@Override
	public IssueInfoPO findMAXIssueInfoByPlayId(String playId) {
		Criteria c = createCriteria();
		c.add(Restrictions.eq("playId", playId));
		c.addOrder(Order.desc("issueNumber"));
		c.setFirstResult(0).setMaxResults(1);
		Object obj = c.uniqueResult();
		return null == obj ? null : (IssueInfoPO) obj;
	}

	@Override
	public IssueInfoPO findLatestBallotIssue(String lotteryId, Integer[] status) {
		return (IssueInfoPO) createCriteria()
				.add(Restrictions.in("status", status))
				.add(Restrictions.eq("lotteryId", lotteryId))
				.addOrder(Order.desc("startTime")).setFirstResult(0)
				.setMaxResults(1).uniqueResult();
	}

	@Override
	public List<IssueInfoPO> findBBallotRecords(Paging paging,
			String lotteryId, Integer[] status, String date) {

		StringBuffer sql = new StringBuffer();
		sql.append("from IssueInfoPO where ")
				.append("lotteryId = :lotteryId and date(startTime) = :date ")
				.append("and status in(:status_first, :status_second) ")
				.append("order by stopTime desc ");

		Query query = this.createQuery(String.valueOf(sql))
				.setString("lotteryId", lotteryId).setString("date", date)
				.setInteger("status_first", status[0])
				.setInteger("status_second", status[1]);

		StringBuffer sqlCount = new StringBuffer();
		sqlCount.append("select count(*) from IssueInfoPO where ")
				.append("lotteryId = :lotteryId and date(startTime) = :date ")
				.append("and status in(:status_first, :status_second) ");
		Query queryCount = this.createQuery(String.valueOf(sqlCount))
				.setString("lotteryId", lotteryId).setString("date", date)
				.setInteger("status_first", status[0])
				.setInteger("status_second", status[1]);
		Object o = queryCount.uniqueResult();

		int totalCount = 0;
		if (null != o) {
			totalCount = Integer.parseInt(String.valueOf(o));
		}
		paging.setTotalCount(totalCount);
		if (0 < paging.getMaxResults()) {
			query.setFirstResult(paging.getFirstResult()).setMaxResults(
					paging.getMaxResults());
		}
		return query.list();
	}

	@Override
	public List<IssueInfoPO> findBBallotRecords(Paging paging,
			String lotteryId, Integer[] status) {

		StringBuffer sql = new StringBuffer();
		sql.append("from IssueInfoPO where ").append("lotteryId = :lotteryId ")
				.append("and status in(:status_first, :status_second) ")
				.append("order by stopTime desc ");

		Query query = this.createQuery(String.valueOf(sql))
				.setString("lotteryId", lotteryId)
				.setInteger("status_first", status[0])
				.setInteger("status_second", status[1]);

		StringBuffer sqlCount = new StringBuffer();
		sqlCount.append("select count(*) from IssueInfoPO where ")
				.append("lotteryId = :lotteryId ")
				.append("and status in(:status_first, :status_second) ");
		Query queryCount = this.createQuery(String.valueOf(sqlCount))
				.setString("lotteryId", lotteryId)
				.setInteger("status_first", status[0])
				.setInteger("status_second", status[1]);
		Object o = queryCount.uniqueResult();

		int totalCount = 0;
		if (null != o) {
			totalCount = Integer.parseInt(String.valueOf(o));
		}
		paging.setTotalCount(totalCount);
		if (0 < paging.getMaxResults()) {
			query.setFirstResult(paging.getFirstResult()).setMaxResults(
					paging.getMaxResults());
		}
		return query.list();
	}

	@Override
	public List<IssueInfoPO> getSalingIssueFromCurrent(String lotteryId,
			Date now, Integer[] status, int maxResults) {
		Criteria criteria = createCriteria();
		criteria.setMaxResults(maxResults);
		if (status.length > 0) {
			criteria.add(Restrictions.in("LCStatus", status));
		}
		if (null != lotteryId && !"".equals(lotteryId)) {
			criteria.add(Restrictions.eq("lotteryId", lotteryId));
		}
		if (null != now) {
			criteria.add(Restrictions.gt("stopTimeForUser", now));
		}
		criteria.addOrder(Order.asc("startTime"));
		return criteria.list();
	}

	@Override
	public IssueInfoPO findByIssue(String lotteryId, String issue,
			Integer[] status) {
		Timestamp timestamp = new Timestamp(new Date().getTime());
		Criteria c = createCriteria();
		if (StringUtils.isBlank(lotteryId) || StringUtils.isBlank(issue)) {
			return null;
		}
		c.add(Restrictions.eq("lotteryId", lotteryId))
				.add(Restrictions.eq("issueNumber", issue))
				.add(Restrictions.in("LCStatus", status))
				.add(Restrictions.gt("stopTimeForUser", timestamp));
		return (IssueInfoPO) c.uniqueResult();
	}

	@Override
	public IssueInfoPO findCurrentSalingValid(String lotteryId, Date targetDate) {
		Query query = this
				.createQuery(
						"from IssueInfoPO " + "where "
								+ "lotteryId=:lotteryId " + "and valid=true "
								+ "and LCStatus=:lcStatus "
								+ "and :now between startTime and closeTime")
				.setString("lotteryId", lotteryId).setInteger("lcStatus", 1)
				.setTimestamp("now", targetDate);
		return (IssueInfoPO) query.uniqueResult();
	}

	@Override
	public IssueInfoPO findCurrentOnSaling(String lotteryId, Date targetDate) {
		Query query = this
				.createQuery(
						"from IssueInfoPO "
								+ "where "
								+ "lotteryId=:lotteryId "
								+ "and valid=true "
								+ "and LCStatus=:lcStatus "
								+ "and :now between startTime and stopTimeForUser")
				.setString("lotteryId", lotteryId).setInteger("lcStatus", 1)
				.setTimestamp("now", targetDate);
		return (IssueInfoPO) query.uniqueResult();
	}

	@Override
	public IssueInfoPO findBBallotDetail(String lotteryId, String issueNumber) {
		Criteria c = createCriteria();
		if (StringUtils.isBlank(lotteryId) || StringUtils.isBlank(issueNumber)) {
			return null;
		}
		c.add(Restrictions.eq("lotteryId", lotteryId))
				.add(Restrictions.eq("issueNumber", issueNumber))
				.add(Restrictions.in("status", new Integer[] {
						Constants.ISSUE_STATUS_SOLD,
						Constants.ISSUE_STATUS_AWARD }));
		return (IssueInfoPO) c.uniqueResult();
	}

	@Override
	public IssueInfoPO findLatestBallotIssue(String lotteryId,
			Integer[] status, String playId) {
		Criteria c = createCriteria();
		c.add(Restrictions.in("status", status))
				.add(Restrictions.eq("lotteryId", lotteryId))
				.add(Restrictions.eq("playId", playId))
				.addOrder(Order.desc("prizeTime"));
		c.setFirstResult(0);
		c.setMaxResults(1);
		return (IssueInfoPO) c.uniqueResult();
	}

	@Override
	public List<IssueInfoPO> findBBallotRecordsOfPlayId(Paging paging,
			String lotteryId, Integer[] status, String playId) {
		StringBuffer sql = new StringBuffer();
		sql.append("from IssueInfoPO where ")
				.append("lotteryId = :lotteryId and playId = :playId ")
				.append("and status in(:status_first, :status_second) ")
				.append("order by stopTime desc ");

		Query query = this.createQuery(String.valueOf(sql))
				.setString("lotteryId", lotteryId).setString("playId", playId)
				.setInteger("status_first", status[0])
				.setInteger("status_second", status[1]);

		StringBuffer sqlCount = new StringBuffer();
		sqlCount.append("select count(*) from IssueInfoPO where ")
				.append("lotteryId = :lotteryId and playId = :playId ")
				.append("and status in(:status_first, :status_second) ");
		Query queryCount = this.createQuery(String.valueOf(sqlCount))
				.setString("lotteryId", lotteryId).setString("playId", playId)
				.setInteger("status_first", status[0])
				.setInteger("status_second", status[1]);
		Object o = queryCount.uniqueResult();

		int totalCount = 0;
		if (null != o) {
			totalCount = Integer.parseInt(String.valueOf(o));
		}
		paging.setTotalCount(totalCount);
		if (0 < paging.getMaxResults()) {
			query.setFirstResult(paging.getFirstResult()).setMaxResults(
					paging.getMaxResults());
		}
		return query.list();
	}

	@Override
	public List<IssueInfoPO> findbyplayId(String playId, int size) {
		Criteria c = createCriteria();
		if (StringUtils.isBlank(playId)) {
			return null;
		}
		c.add(Restrictions.eq("valid", true));
		c.add(Restrictions.eq("playId", playId));
		c.addOrder(Order.desc("stopTimeForUser"));
		c.setFirstResult(0).setMaxResults(size);
		return c.list();
	}
	
	@Override
	public Integer isHaveSSQ() {
	
		String sql="select count(*) from lt_issueinfo info where info.status=1 and lottery_id='SSQ'";
		return Integer.parseInt(createSQLQuery(sql).uniqueResult().toString());
	}
	
	@Override
	public List<IssueInfoPO> findByPage(LotteryId lotteryId, PlayType playType,
			String issueNumber, Paging paging) {
		if(null == lotteryId){
			logger.info("查询期信息未传lotteryId参数!");
			return null;
		}
		String hql = "from IssueInfoPO a where lotteryId=:lotteryId ";
		hql = makeHql(playType, issueNumber, hql);
		Query query = createQuery(hql);
		setParameter(lotteryId, playType, issueNumber, query);
		query.setFirstResult(paging.getFirstResult());
		query.setMaxResults(paging.getMaxResults());
		Long totalCount=0l;
		if(paging.isCount()){
			String countHql = "select count(*)  "+hql;
			Query countQuery = createQuery(countHql);
			setParameter(lotteryId, playType, issueNumber, countQuery);
			totalCount=(Long) countQuery.uniqueResult();
			paging.setTotalCount(totalCount.intValue());
		}
		List<IssueInfoPO> result=new ArrayList<IssueInfoPO>();
		result = query.list();
		paging.setResults(result);
		return result;
	}

	private String makeHql(PlayType playType, String issueNumber, String hql) {
		if(null != playType){
			hql += " and playId=:playType ";
		}
		if(StringUtils.isNotBlank(issueNumber)){
			hql += " and issueNumber=:issueNumber ";
		}
		hql +=" order by issueNumber desc ";
		return hql;
	}

	private void setParameter(LotteryId lotteryId, PlayType playType,
			String issueNumber, Query query) {
		query.setParameter("lotteryId", lotteryId.name());
		if(null != playType){
			query.setParameter("playType", playType.getShortPlayStr());
		}
		if(StringUtils.isNotBlank(issueNumber)){
			query.setParameter("issueNumber",issueNumber);
		}
	}

	@Override
	public List<Long> findIssueInfoListByLotteryId(LotteryId lotteryId,String issueNumber,
			int maxResult) {
		String sql="select distinct issue.issue_number from lt_issueinfo issue where issue.lottery_id=:lotteryId ";
		if(StringUtils.isNotBlank(issueNumber)){
			sql+=" and issue.issue_number>=:issueNumber";
		}
		sql+=" order by issue.created_time desc";
		if(maxResult>0){
			sql+=" limit :result";
		}
		SQLQuery query=this.createSQLQuery(sql);
		query.setParameter("lotteryId", lotteryId.name());
		if(StringUtils.isNotBlank(issueNumber)){
			query.setParameter("issueNumber", issueNumber);
		}
		if(maxResult>0){
			query.setParameter("result", maxResult);
		}
		return (List<Long>)query.list();
	}

	public List<IssueInfoPO> findByIssueNumber(String issueNumber) {
		String hql="from IssueInfoPO"
				+ " where "
				+ " issueNumber=:issueNumber";
		Query query = createQuery(hql);
		query.setParameter("issueNumber", issueNumber);
		return query.list();
	}
	public Integer findIssueCountByLotteryIdAndIssueNumber(String lotteryId,String issueNumber,boolean valid){
		Query query=this.createQuery("select count(*) from IssueInfoPO lt where lt.lotteryId=:lotteryId and lt.issueNumber=:issueNumber and lt.valid=:valid");
		query.setParameter("lotteryId", lotteryId);
		query.setParameter("issueNumber", issueNumber);
		query.setParameter("valid", valid);
		Object obj=query.uniqueResult();
		if(obj!=null){
			return Integer.parseInt(obj.toString());
		}
		return 0;
		
	}
	public Integer isExistsCTZCIssueInfo(String lotteryId,String issueNumber){
		Query query=this.createQuery("select count(*) from IssueInfoPO lt where lt.lotteryId=:lotteryId and lt.issueNumber=:issueNumber");
		query.setParameter("lotteryId", lotteryId);
		query.setParameter("issueNumber", issueNumber);
		Object obj=query.uniqueResult();
		if(obj!=null){
			return Integer.parseInt(obj.toString());
		}
		return 0;
	}
	
	public IssueInfoPO findIssueByIssueNumberAndPlayId(String issueNumber,String playId) {
		String hql="from IssueInfoPO"
				+ " where "
				+ " issueNumber=:issueNumber and playId=:playId";
		Query query = createQuery(hql);
		query.setParameter("issueNumber", issueNumber);
		query.setParameter("playId", playId);
		return (IssueInfoPO) query.uniqueResult();
	}
}

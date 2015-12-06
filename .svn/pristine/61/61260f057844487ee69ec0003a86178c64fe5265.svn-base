package com.davcai.lottery.dao.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.davcai.lottery.dao.JCZQAnalyseDao;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.FbLeagueScorePO;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.FbLeagueScoreRulePO;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.FbMatchAsiaOuOddsInfoPO;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.FbMatchBaseInfoPO;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.FbMatchOpOddsInfoPO;
import com.xhcms.commons.persist.hibernate.DaoImpl;

@Repository
public class JCZQAnalyseDaoImpl extends DaoImpl<FbMatchBaseInfoPO> implements
		JCZQAnalyseDao {

	@Override
	@Transactional
	public Object[] findFbMatchInfoByDavcaiMatchId(String daVCaiMatchId) {
		
		Object[] matchBaseInfoPO = (Object[]) createQuery(
				"select a,b.homeTeamName,b.guestTeamName from FbMatchBaseInfoPO a,FBMatchPO b where a.jingcaiId=b.cnCode and a.matchTime=b.playingTime and b.id=?")
				.setString(0, daVCaiMatchId).uniqueResult();
		return matchBaseInfoPO;
	}

	@Override
	@Transactional
	public List<FbMatchBaseInfoPO> queryFbMatchAgainstLatest_20(
			String homeTeamId, String guestTeamId, Integer latestNum) {
		@SuppressWarnings("unchecked")
		List<FbMatchBaseInfoPO> matchBaseInfoPOs = createQuery(
				"select new FbMatchBaseInfoPO(a.leagueId,a.jingcaiId,a.homeTeamId,a.guestTeamId,a.homeTeamScore,a.guestTeamScore,a.homeTeamHalfScore,a.guestTeamHalfScore,a.matchTime,b.colour,b.chineseName) from FbMatchBaseInfoPO a,LeagueInfoPO b where ((homeTeamId=? and guestTeamId=?)  "
						+ "or (guestTeamId=? and homeTeamId=?)) and a.leagueId=b.leagueId "
						+ " and matchStatus=? order by matchTime desc")
				.setString(0, homeTeamId).setString(1, guestTeamId)
				.setString(2, homeTeamId).setString(3, guestTeamId)
				.setInteger(4, -1).setMaxResults(latestNum).list();
		return matchBaseInfoPOs;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<FbMatchBaseInfoPO> queryFbTeamMatchRecord_latest(String teamId,
			int rencentNum) {
		List<FbMatchBaseInfoPO> matchBaseInfoPOs = createQuery(
				"select new FbMatchBaseInfoPO(a.leagueId,a.jingcaiId,a.homeTeamId,a.guestTeamId,a.homeTeamScore,a.guestTeamScore,a.homeTeamHalfScore,a.guestTeamHalfScore,a.matchTime,b.colour,b.chineseName) from FbMatchBaseInfoPO a,LeagueInfoPO b where (homeTeamId=? or guestTeamId=?)  "
						+ " and a.leagueId=b.leagueId "
						+ " and matchStatus=? order by matchTime desc")
				.setString(0, teamId).setString(1, teamId).setInteger(2, -1)
				.setMaxResults(rencentNum).list();
		return matchBaseInfoPOs;
	}

	@Override
	@Transactional
	public List<FbLeagueScorePO> queryLeagueOverallStandings(String leagueId) {

		return createQuery(
				"select a from FbLeagueScorePO a where leagueId=? and seasonName in(select max(seasonName) from FbLeagueSeasonBasePO where leagueId=?) order by rank asc")
				.setString(0, leagueId).setString(1, leagueId).list();
	}

	@Override
	@Transactional
	public List<FbLeagueScoreRulePO> queryLeagueOverallStandingsRule(
			String leagueId) {
		return createQuery(
				"select a from FbLeagueScoreRulePO a where leagueId=? and seasonName in(select max(seasonName) from FbLeagueSeasonBasePO where leagueId=?) and subLeagueId is null order by ruleNum asc ")
				.setString(0, leagueId).setString(1, leagueId).list();
	}

	@Override
	@Transactional
	public String queryNowSeasonByLeagueId(String leagueId) {
		// TODO Auto-generated method stub
		return (String) createQuery(
				"select max(seasonName) from FbLeagueSeasonBasePO where leagueId=?")
				.setString(0, leagueId).uniqueResult();
	}

	@Override
	@Transactional
	public Set<String> queryQtFbTeamByLeagueId(
			String leagueId, int qtsource, String seasonName) {
		List<Object[]> arrays = null;
		Set<String> teamId = new HashSet<String>();
		try {
			Query query = createQuery("select homeTeamId,guestTeamId from FbMatchBaseInfoPO where leagueId=? and season=? "
					+ "and matchStatus=-1 and source=? ");
			query.setString(0, leagueId).setString(1, seasonName)
					.setInteger(2, qtsource);
			arrays = query.list();
		} catch (HibernateException e) {
			throw e;
		}
		for(Object[] each:arrays){
			teamId.add((String) each[0]);
			teamId.add((String) each[1]);
		}
		teamId.remove(null);
		return teamId;
		
	}

	@Override
	@Transactional
	public Long queryFbMatchIdByDavcaiId(String daVCaiMatchId) {
		return (Long) createQuery(
				"select a.id from FbMatchBaseInfoPO a,FBMatchPO b where a.jingcaiId=b.cnCode and a.matchTime=b.playingTime and b.id=?")
				.setString(0, daVCaiMatchId).uniqueResult();
	}

	@Override
	@Transactional
	public List<Object[]> queryFbMatchEuroOddsByMatchId(Long matchId,String corpId) {
		String hql = "select a,b.corpName from FbMatchOpOddsInfoPO a,FbLotteryCorpBaseInfoPO b where a.bsId=? and a.euroOdds is not null and a.corpId=b.euroId";
		if(corpId!=null){
			hql += " and a.corpId=?";
		}
		Query query = createQuery(hql).setLong(0, matchId);
		if(corpId!=null){
			query.setString(1, corpId);
		}
		return query.list();
		
	}

	@Override
	@Transactional
	public List<Object[]> queryFbMatchAsianOddsById(
			Long matchId,String order) {
		return createQuery("SELECT a,b.corpName from FbMatchAsiaOuOddsInfoPO a,FbLotteryCorpBaseInfoPO b "
				+ "where a.corpId=b.corpId and a.timestamp=(select "+order+"(timestamp) "
				+ " from FbMatchAsiaOuOddsInfoPO where bsId=? and a.corpId=corpId and oddsType=1) and a.bsId=? and oddsType=1 ").setLong(0, matchId).setLong(1,matchId).list();
	}

	@Override
	@Transactional
	public List<FbMatchAsiaOuOddsInfoPO> queryFbMatchAsianOddsByIdAndCorpId(Long matchId,
			Integer corpId) {
		
		return createQuery("from  FbMatchAsiaOuOddsInfoPO"
				+ " where bsId=? and corpId=? and oddsType=1 order by timestamp desc").setLong(0, matchId).setInteger(1, corpId).list();
	}

	@Override
	@Transactional
	public List<Object[]> queryFbMatchByPlayingTime(Date from, Date to) {
		String toSql = "";
		if(to!=null){
			toSql = " and b.matchTime<:to";
		}
		Query query  = createQuery("select b.id,a.code,a.playingTime,b.homeTeamPosition,b.guestTeamPosition from FBMatchPO a,FbMatchBaseInfoPO b "
				+ "where a.playingTime=b.matchTime and a.cnCode=b.jingcaiId and b.matchTime>=:from" + toSql)
				
				.setParameter("from", from);
		if(StringUtils.isNotBlank(toSql)){
			query.setParameter("to", to);
		}
		return query.list();
	}

	@Override
	@Transactional
	public List<FbMatchOpOddsInfoPO> queryFbOpOddsByBsIdsAndCorpIds(
			String euroCorpId, String bsIds) {
		List<FbMatchOpOddsInfoPO> fbMatchOpOddsInfoPOs = createQuery(
				"select new FbMatchOpOddsInfoPO(a.bsId,a.corpId,a.euroOdds,a.changeTime) from FbMatchOpOddsInfoPO a"
				+ " where a.bsId in("+bsIds+") "
				+ " and corpId in("+ euroCorpId+") ").list();
		return fbMatchOpOddsInfoPOs;
	}

	@Override
	@Transactional
	public List<FbMatchAsiaOuOddsInfoPO> queryFbMatchAsianOrOuOddsByBsIdsAndCorpIds(
			String asianOuCorpId, String bsIds, int oddsType, int maxNum) {
		List<FbMatchAsiaOuOddsInfoPO> fbMatchAsiaOuOddsInfoPOs = createQuery(
				"select new FbMatchAsiaOuOddsInfoPO(a.bsId,a.corpId,a.HomeWinOdds,a.GuestWinOdds,a.handicap,a.oddsType,a.timestamp) from FbMatchAsiaOuOddsInfoPO a"
				+ " where a.oddsType=? and a.bsId"
				+ " in("+ bsIds+") and a.corpId in("+asianOuCorpId+")"
				+ "  and "+maxNum+">(select count(id) "
				+ " from FbMatchAsiaOuOddsInfoPO where bsId=a.bsId and corpId=a.corpId"
				+ " and oddsType=a.oddsType and timestamp>a.timestamp) order by bsId,corpId,timestamp desc").setInteger(0, oddsType).list();
		return fbMatchAsiaOuOddsInfoPOs;
	}

	@Override
	@Transactional
	public List<FbMatchAsiaOuOddsInfoPO> queryFbMatchInitAsianOrOuOddsByBsIdsAndCorpIds(
			String bsIds, String asianOuCorpId, int oddsType) {
		List<FbMatchAsiaOuOddsInfoPO> fbMatchAsiaOuOddsInfoPOs = createQuery(
				"select new FbMatchAsiaOuOddsInfoPO(a.bsId,a.corpId,a.HomeWinOdds,a.GuestWinOdds,a.handicap,a.oddsType,a.timestamp) from FbMatchAsiaOuOddsInfoPO a"
				+ " where a.oddsType=? and a.bsId"
				+ " in("+ bsIds+") and a.corpId in("+asianOuCorpId+")"
				+ "  and a.timestamp=(select min(timestamp) "
				+ " from FbMatchAsiaOuOddsInfoPO where bsId=a.bsId and corpId=a.corpId"
				+ " and oddsType=a.oddsType) ").setInteger(0, oddsType).list();
		return fbMatchAsiaOuOddsInfoPOs;
	}

	@Override
	@Transactional
	public List<Object[]> queryFbMatchAllInfoByPlayingTime(Date from,
			Date to,String leagueShortNames) {
		String hql = "select b,a.chineseName from LeagueInfoPO a,FbMatchBaseInfoPO b "
				+ "where a.leagueId=b.leagueId and b.jingcaiId is not null ";
				
		if(StringUtils.isNotBlank(leagueShortNames)){
			hql += " and a.chineseName in("+leagueShortNames+")";
		}
		hql += " and isNow='1' and b.matchStatus=0 and (b.createTime>=:createTime or b.updateTime>=:updateTime) ";
		Query query  = createQuery(
				 hql);
		query.setParameter("updateTime", from);
		query.setParameter("createTime", from);
		
		return query.list();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getZQbifen(Date date, Date to, String leagueShortName) {
		String toSql = "";
		String toLeague = "";
		Date yesterday = DateUtils.addHours(DateUtils.addDays(date, -1), 12);
		Date yesTo = DateUtils.addHours(DateUtils.addDays(date, 1), 6);
		if(to!=null){
			toSql = " and ( b.createTime<:to or b.updateTime<:to )";
		}
		if(StringUtils.isNotBlank(leagueShortName)){
			toLeague = " and a.chineseName in (:leagueShortName)";
		}
		Query query  = createQuery("select b,a.chineseName from LeagueInfoPO a,FbMatchBaseInfoPO b "
				+ "where b.matchStatus = '-1' and a.leagueId=b.leagueId and b.jingcaiId <> null and b.matchTime>=:yesterDay and b.matchTime<=:yesTo and ( b.createTime>=:date or b.updateTime>=:date ) and isNow='1'" + toSql + toLeague +" order by b.matchTime DESC , b.jingcaiId")
				.setParameter("date", date).setParameter("yesterDay", yesterday).setParameter("yesTo", yesTo);
		Query query1  = createQuery("select b,a.chineseName from LeagueInfoPO a,FbMatchBaseInfoPO b "
				+ "where b.matchStatus <> '-1' and a.leagueId=b.leagueId and b.jingcaiId <> null and b.matchTime>=:yesterDay and b.matchTime<=:yesTo and ( b.createTime>=:date or b.updateTime>=:date ) and isNow='1'" + toSql + toLeague +" order by b.matchTime , b.jingcaiId")
				.setParameter("date", date).setParameter("yesterDay", yesterday).setParameter("yesTo", yesTo);
		if(StringUtils.isNotBlank(toSql)){
			query.setParameter("to", to);
			query1.setParameter("to", to);
		}
		if(StringUtils.isNotBlank(toLeague)){
			query.setParameterList("leagueShortName", makeLeagueShortName(leagueShortName));
			query1.setParameterList("leagueShortName", makeLeagueShortName(leagueShortName));
		}
		List<Object[]> datas = new ArrayList<Object[]>();
		List<Object[]> overLists = query.list();
		List<Object[]> noLists = query1.list();
		if(overLists != null && !overLists.isEmpty()){
			datas.addAll(overLists);
		}
		if(noLists != null && !noLists.isEmpty()){
			datas.addAll(noLists);
		}
		return datas;
	}

	private Set<String> makeLeagueShortName(String leagueShortName) {
		Map<String, String> map = new HashMap<String, String>();
		if(StringUtils.isNotBlank(leagueShortName)){
			for(String value : leagueShortName.split(",")){
				if(StringUtils.isBlank(map.get(value))){
					map.put(value, value);
				}
			}
		}
		Set<String> values = map.keySet();
		return values;
	}

	@Override
	@Transactional
	public List<FbMatchAsiaOuOddsInfoPO> queryFbMatchAsianOrOuOddsByMatchIdAndCorpId(
			Long matchId, String corpId, String oddsType) {
		List<FbMatchAsiaOuOddsInfoPO> fbMatchAsiaOuOddsInfoPOs = createQuery(
				"select new FbMatchAsiaOuOddsInfoPO(a.HomeWinOdds,a.GuestWinOdds,a.handicap,a.timestamp) from FbMatchAsiaOuOddsInfoPO a"
				+ " where a.oddsType=? and a.bsId=? and a.corpId=? order by timestamp desc").setInteger(0, Integer.valueOf(oddsType))
				.setInteger(1, Integer.valueOf(matchId.toString())).setString(2, corpId).list();
		return fbMatchAsiaOuOddsInfoPOs;
	}

	@Override
	@Transactional
	public Date queryfbMatchTimeById(Long matchId) {
		Date objects = (Date) createQuery("select matchTime from FbMatchBaseInfoPO where id=?").setLong(0, matchId).uniqueResult();
		return objects;
	}

	@Override
	@Transactional
	public List<String> queryAllLeagueShortNameInDays(Date from,Date to) {
		String hql = "select a.chineseName from LeagueInfoPO a,FbMatchBaseInfoPO b "
				+ "where a.leagueId=b.leagueId and b.jingcaiId is not null"
				+ " and isNow='1' and b.matchStatus=0 and (b.createTime>=:createTime or b.updateTime>=:updateTime) "
				+ "order by b.matchTime asc";
		Query query  = createQuery(
				 hql);
		query.setParameter("updateTime", from);
		query.setParameter("createTime", from);
		
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findFbMatchInfoByDavcaiMatchId(Collection<Long> ids, int matchTimeInterval) {
		String sql = "select fm.id, " +
			"concat(qm.homeTeamScore, ':', qm.guestTeamScore) as score," +
			"concat(qm.homeTeamHalfScore, ':', qm.guestTeamHalfScore) as halfScore " +
			"from fb_match fm, md_qt_match_base qm " +
			"where fm.cn_code = qm.jingcaiId " +
			"and fm.playing_time between date_add(qm.matchTime,interval :negativeInterval minute) " +
			"and date_add(qm.matchTime,interval :matchTimeInterval minute) " +
			"and (qm.matchStatus = -1 or fm.status = 4) and fm.id in(:ids)";
		SQLQuery sqlQuery = createSQLQuery(sql);
		sqlQuery.setParameterList("ids", ids);
		sqlQuery.setParameter("negativeInterval", -matchTimeInterval);
		sqlQuery.setParameter("matchTimeInterval", matchTimeInterval);
		
		return sqlQuery.list();
	}

	@Override
	@Transactional
	public List<Object[]> getZQResoureData(Date now, Date from, Date to) {
		String toFrom = "",fromTo = "" , toTo = "";
		Date nTo = DateUtils.addMilliseconds(DateUtils.addMinutes(DateUtils.addHours(now, 23), 59), 59);
		if(from != null){
			toFrom = " and ( b.matchTime>:from)";
		}
		if(to != null){
			toTo = " and ( b.matchTime<:to )";
		}
		if(to != null && from !=null){
			fromTo = " and ( b.matchTime>:from or b.matchTime<:to ) ";
		}
		Query query  = createQuery("select b,a.chineseName from LeagueInfoPO a,FbMatchBaseInfoPO b "
				+ "where a.leagueId=b.leagueId and b.jingcaiId <> null and ( b.matchTime>=:now and b.matchTime<=:nTo )" + toFrom + toTo + fromTo + " order by b.matchTime DESC")
				.setParameter("now", now).setParameter("nTo", nTo);
		if(StringUtils.isNotBlank(toFrom)){
			query.setParameter("from", now);
		}
		if(StringUtils.isNotBlank(toTo)){
			query.setParameter("to", to);
		}
		if(StringUtils.isNotBlank(fromTo)){
			query.setParameter("from", now).setParameter("to", to);
		}
		return query.list();
	}
	
	@Override
	@Transactional
	public Object[] getZQResoureDataById(String detailId){
		long id = Long.parseLong(detailId);
		Query query  = createQuery("select b,a.chineseName from LeagueInfoPO a,FbMatchBaseInfoPO b "
				+ "where a.leagueId=b.leagueId and b.id=:detailId").setParameter("detailId", id);
		List<Object[]> lists= query.list();
		if(lists != null && lists.size() > 0){
			return lists.get(0);
		}
		return null;
	}

	@Override
	@Transactional
	public FbMatchBaseInfoPO findFbById(long id) {
		Query query  = createQuery("select b from LeagueInfoPO a,FbMatchBaseInfoPO b "
				+ "where a.leagueId=b.leagueId and b.id=:detailId").setParameter("detailId", id);
		List<FbMatchBaseInfoPO> lists= query.list();
		if(lists != null && lists.size() > 0){
			return lists.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object[]> getZQResoureData(Date from, Date to) {
		String toFrom = "",fromTo = "" , toTo = "";
		Date nto = DateUtils.addMilliseconds(DateUtils.addMinutes(DateUtils.addHours(to, 23), 59), 59);
//		if(from != null){
//			toFrom = " and ( b.matchTime>:from)";
//		}
//		if(to != null){
//			toTo = " and ( b.matchTime<:to )";
//		}
		if(to != null && from !=null){
			fromTo = " and ( b.matchTime>:from and b.matchTime<:to ) ";
		}
		Query query  = createQuery("select b,a.chineseName from LeagueInfoPO a,FbMatchBaseInfoPO b "
				+ "where a.leagueId=b.leagueId and b.jingcaiId <> null " + toFrom + toTo + fromTo + " order by b.matchTime DESC");
//		if(StringUtils.isNotBlank(toFrom)){
//			query.setParameter("from", from);
//		}
//		if(StringUtils.isNotBlank(toTo)){
//			query.setParameter("to", to);
//		}
		if(StringUtils.isNotBlank(fromTo)){
			query.setParameter("from", from).setParameter("to", nto);
		}
		return query.list();
	
	}
}

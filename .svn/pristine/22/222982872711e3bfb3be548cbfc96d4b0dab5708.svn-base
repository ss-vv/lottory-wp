package com.davcai.lottery.dao.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.davcai.lottery.dao.JCLQAnalyseDao;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.BasketBallLeagueScorePO;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.BasketBallMatchPO;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.BasketMatchAsiaOuOddsInfoPO;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.BasketMatchOpOddsInfoPO;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.FbMatchBaseInfoPO;
import com.xhcms.commons.persist.hibernate.DaoImpl;
import com.xhcms.lottery.commons.data.BasketBallMatchData;
/**
 *
 * @author baoxing.peng@davcai.com
 *
 * @since 2015年3月2日 下午5:21:52
 */
@Repository
public class JCLQAnalyseDaoImpl extends DaoImpl<BasketBallMatchPO> implements JCLQAnalyseDao {

	@Override
	@Transactional
	public List<BasketBallMatchPO> queryBbMatchAgainstLatest_20(
			
			String homeTeamId, String guestTeamId, Integer latestNum){
		@SuppressWarnings("unchecked")
		List<BasketBallMatchPO> bbMatchPOs = createQuery("select new BasketBallMatchPO("
				+ "a.name,a.matchTime,a.homeTeam,a.guestTeam,a.homeScore,a.guestScore,a.letBallNum,color,a.leagueId)"
				+ " from BasketBallMatchPO a where ((a.homeTeam=? and a.guestTeam=?) "
				+ " or (a.homeTeam=? and a.guestTeam=?)) and a.matchState=-1 and a.source=1 order by matchTime desc limit "+latestNum)
				.setString(0, homeTeamId).setString(1, guestTeamId).setString(2, guestTeamId).setString(3, homeTeamId)
				.setMaxResults(latestNum).list();
		return bbMatchPOs;
	}

	@Override
	@Transactional
	public Object[] findBbMatchInfoByDavcaiMatchId(String daVCaiMatchId) {
		Object[] objects = (Object[]) createQuery("select a,b.homeTeamName,b.guestTeamName from BasketBallMatchPO a,BBMatchPO b "
				+ "where b.id=? and a.jingcaiId=b.cnCode and a.matchTime=b.playingTime").setString(0, daVCaiMatchId).uniqueResult();
		return objects;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<BasketBallLeagueScorePO> queryLeagueOverallStandings(String leagueId,
			String subLeagueId) {
		subLeagueId = (subLeagueId==null||subLeagueId.equals("-1"))?"subLeuageId is null":"subLeagueId="+subLeagueId;
		String hql = "select a from BasketBallLeagueScorePO a where leagueId=? and season in(select max(seasonName) "
				+ "from BasketBallLeagueSeasonPO where leagueId=?) and (subLeagueId is null or subLeagueId=?)order by rank asc";
		return createQuery(hql).setString(0, leagueId).setString(1, leagueId).setString(2, "0").list();
	}

	@Override
	@Transactional
	public List<Object[]> queryBbMatchByPlayingTime(Date from, Date to) {
		String toSql = "";
		if(to!=null){
			toSql = " and a.playingTime<:to";
		}
		Query query  = createQuery("select b.id,a.code,a.playingTime from BBMatchPO a,BasketBallMatchPO b where a.playingTime=b.matchTime and a.cnCode=b.jingcaiId and a.playingTime>=:from" + toSql)
				.setParameter("from", from);
		if(StringUtils.isNotBlank(toSql)){
			query.setParameter("to", to);
		}
		return query.list();
	}

	@Override
	@Transactional
	public List<BasketMatchOpOddsInfoPO> queryBbOpOddsByBsIdsAndCorpIds(
			String corpNames, String bsIds,Integer maxNum) {
		@SuppressWarnings("unchecked")
		List<BasketMatchOpOddsInfoPO> basketMatchOpOddsInfoPOs = createQuery(
				"select new BasketMatchOpOddsInfoPO(a.bsId,a.corpId,a.HomeWinOdds,a.GuestWinOdds,a.timestamp) from BasketMatchOpOddsInfoPO a"
				+ " where a.bsId in("+bsIds+") "
				+ " and corpId in("+ corpNames+") and "+maxNum+">(select count(id) "
				+ " from BasketMatchOpOddsInfoPO where bsId=a.bsId and corpId=a.corpId and timestamp>a.timestamp) order by "
				+ " bsId,corpId,timestamp desc  ").list();
		return basketMatchOpOddsInfoPOs;
	}

	@Override
	@Transactional
	public List<BasketMatchAsiaOuOddsInfoPO> queryBbMatchAsianOrOuOddsByBsIdsAndCorpIds(
			String corpNames, String bsIds,int type,Integer maxNum) { 
		@SuppressWarnings("unchecked")
		List<BasketMatchAsiaOuOddsInfoPO> basketMatchAsianOrOuOddsInfoPOs = createQuery(
				"select new BasketMatchAsiaOuOddsInfoPO(a.bsId,a.corpId,a.HomeWinOdds,a.GuestWinOdds,a.handicapOrScore,a.oddsType,a.timestamp) from BasketMatchAsiaOuOddsInfoPO a"
				+ " where corpId is not null and a.bsId"
				+ " in("+ bsIds+") and a.corpId in("+corpNames+")"
				+ " and a.oddsType=? and "+maxNum+">(select count(id) "
				+ " from BasketMatchAsiaOuOddsInfoPO where bsId=a.bsId and corpId=a.corpId and corpId is not null "
				+ " and oddsType=a.oddsType and timestamp>a.timestamp) order by bsId,corpId,timestamp desc").setInteger(0, type).list();
		return basketMatchAsianOrOuOddsInfoPOs;
	}

	@Override
	@Transactional
	public List<Object[]> queryBbMatchAllInfoByPlayingTime(Date from, Date to,String leagueShortNames) {
		String hql = "select b,a.shortName from BasketBallLeagueInfoPO a,BasketBallMatchPO b "
				+ "where b.jingcaiId is not null and a.leagueId=b.leagueId and b.matchState=0 ";
		if(StringUtils.isNotBlank(leagueShortNames)){
			hql += " and a.shortName in("+ leagueShortNames +") ";
		}
		hql += " and (b.createTime>=:createTime or b.updateTime>=:updateTime) and b.isNow='1' order by b.matchTime asc";
		Query query  = createQuery(hql);
		query.setParameter("createTime", from);
		query.setParameter("updateTime", from);
		return query.list();
	}

	@Override
	@Transactional
	public List<BasketMatchOpOddsInfoPO> queryBbOpInitOddsByBsIdsAndCorpIds(
			String bbOpCoprNames, String matchIds) {
		@SuppressWarnings("unchecked")
		List<BasketMatchOpOddsInfoPO> basketMatchOpOddsInfoPOs = createQuery(
				"select new BasketMatchOpOddsInfoPO(a.bsId,a.corpId,a.HomeWinOdds,a.GuestWinOdds,a.timestamp) from BasketMatchOpOddsInfoPO a"
				+ " where a.bsId in("+matchIds+") "
				+ " and corpId in("+ bbOpCoprNames+") and a.timestamp=(select min(timestamp) "
				+ " from BasketMatchOpOddsInfoPO where bsId=a.bsId and corpId=a.corpId) ").list();
		return basketMatchOpOddsInfoPOs;
	}

	@Override
	@Transactional
	public List<BasketMatchAsiaOuOddsInfoPO> queryBbMatchAsianOrOuInitOddsByBsIdsAndCorpIds(
			String bbasianoucorpnames, String matchIds, int type) {
		@SuppressWarnings("unchecked")
		List<BasketMatchAsiaOuOddsInfoPO> basketMatchAsianOrOuOddsInfoPOs = createQuery(
				"select new BasketMatchAsiaOuOddsInfoPO(a.bsId,a.corpId,a.HomeWinOdds,a.GuestWinOdds,a.handicapOrScore,a.oddsType,a.timestamp) from BasketMatchAsiaOuOddsInfoPO a"
				+ " where a.bsId"
				+ " in("+ matchIds+") and a.corpId in("+bbasianoucorpnames+")"
				+ " and a.oddsType=? and timestamp=(select min(timestamp) "
				+ " from BasketMatchAsiaOuOddsInfoPO where bsId=a.bsId and corpId=a.corpId"
				+ " and oddsType=a.oddsType) ").setInteger(0, type).list();
		return basketMatchAsianOrOuOddsInfoPOs;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getBBbifen(Date date, Date to, String leagueShortName) {
		String toSql = "";
		String toLeague = "";
		Date yesterday = DateUtils.addHours(DateUtils.addDays(date, -1), 12);
		Date yesTo = DateUtils.addHours(DateUtils.addDays(date, 1), 13);
		if(to!=null){
			toSql = " and ( b.createTime<:to or b.updateTime<:to )";
		}
		if(StringUtils.isNotBlank(leagueShortName)){
			toLeague = " and a.shortName in (:leagueShortName)";
		}
		Query query  = createQuery("select b,a.shortName,a.color from BasketBallLeagueInfoPO a,BasketBallMatchPO b "
				+ "where b.matchState = '-1' and a.leagueId=b.leagueId and b.jingcaiId <> null and b.matchTime>=:yesterDay and b.matchTime<=:yesTo and ( b.createTime>=:date or b.updateTime>=:date ) and isNow='1'" + toSql + toLeague +" order by b.matchTime DESC, b.jingcaiId )")
				.setParameter("date", date).setParameter("yesterDay", yesterday).setParameter("yesTo", yesTo);
		Query query1  = createQuery("select b,a.shortName,a.color from BasketBallLeagueInfoPO a,BasketBallMatchPO b "
				+ "where b.matchState <> '-1' and a.leagueId=b.leagueId and b.jingcaiId <> null and b.matchTime>=:yesterDay and b.matchTime<=:yesTo and ( b.createTime>=:date or b.updateTime>=:date ) and isNow='1'" + toSql + toLeague +" order by b.matchTime, b.jingcaiId )")
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
	public List<BasketMatchOpOddsInfoPO> queryBbOpAllOddsByBsIdAndCorpId(
			String corpName, Long matchId) {
		return createQuery(
				"select new BasketMatchOpOddsInfoPO(a.HomeWinOdds,a.GuestWinOdds,a.timestamp) from BasketMatchOpOddsInfoPO a"
				+ " where a.bsId=? and a.corpId=? order by timestamp desc").setLong(0, matchId).setString(1, corpName).list();
	}

	@Override
	@Transactional
	public List<BasketMatchAsiaOuOddsInfoPO> queryBbAsianOrOuAllOdds(
			Long matchId, String corpName, String oddsType) {
		return createQuery(
				"select new BasketMatchAsiaOuOddsInfoPO(a.HomeWinOdds,a.GuestWinOdds,a.handicapOrScore,a.timestamp) from BasketMatchAsiaOuOddsInfoPO a"
				+ " where a.bsId=? and a.corpId=? and a.oddsType=? order by timestamp desc")
				.setLong(0, matchId).setString(1, corpName).setInteger(2, Integer.valueOf(oddsType)).list();
	}

	@Override
	@Transactional
	public Date queryBbMatchTimeById(Long matchId) {
		return (Date) createQuery("select matchTime from BasketBallMatchPO where id=?").setLong(0, matchId).uniqueResult();
	}

	@Override
	@Transactional
	public List<String> queryAllLeagueShortNameInDays(Date from, Date to) {
		String hql = "select a.shortName from BasketBallLeagueInfoPO a,BasketBallMatchPO b "
				+ "where b.jingcaiId is not null and a.leagueId=b.leagueId and b.matchState=0 and (b.createTime>=:createTime or b.updateTime>=:updateTime) and b.isNow='1' order by b.matchTime asc";
		Query query  = createQuery(hql);
		query.setParameter("createTime", from);
		query.setParameter("updateTime", from);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findBbMatchInfoByDavcaiMatchId(Collection<Long> ids, int matchTimeInterval) {
		String sql = "select bm.id, concat(qm.guestScore, ':', qm.homeScore) as score " +
				"from bb_match bm, md_bb_match_base qm " +
				"where bm.cn_code = qm.jingcaiId " +
				"and bm.playing_time between date_add(qm.matchTime,interval :negativeInterval minute) " +
				"and date_add(qm.matchTime,interval :matchTimeInterval minute) " +
				"and (qm.matchState = -1 or bm.status = 4) and bm.id in(:ids)";
			SQLQuery sqlQuery = createSQLQuery(sql);
			sqlQuery.setParameterList("ids", ids);
			sqlQuery.setParameter("negativeInterval", -matchTimeInterval);
			sqlQuery.setParameter("matchTimeInterval", matchTimeInterval);
			
			return sqlQuery.list();
	}

	@Override
	@Transactional
	public boolean saveFbMatchBaseInfoPO(FbMatchBaseInfoPO newFoBaseInfoPO) {
		boolean result = false;
		saveOrUpdate(newFoBaseInfoPO);
		result = true;
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object[]> getBBResoureData(Date now, Date from, Date to) {
		String toFrom = "",fromTo = "" , toTo = "";
		Date nTo = DateUtils.addMilliseconds(DateUtils.addMinutes(DateUtils.addHours(now, 23), 59), 59);
		if(from != null){
			toFrom = " and ( b.matchTime>:from)";
		}
//		if(to != null){
//			toTo = " and ( b.matchTime<:to )";
//		}
		if(to != null && from !=null){
			fromTo = " and ( b.matchTime>:from or b.matchTime<:to ) ";
		}
		Query query  = createQuery("select b,a.shortName,a.color from BasketBallLeagueInfoPO a,BasketBallMatchPO b "
				+ "where a.leagueId=b.leagueId and b.jingcaiId <> null and ( b.matchTime>=:now and b.matchTime<=:nTo )" + toFrom + toTo + fromTo + " order by b.matchTime DESC")
				.setParameter("now", now).setParameter("nTo", nTo);
		if(StringUtils.isNotBlank(toFrom)){
			query.setParameter("from", from);
		}
//		if(StringUtils.isNotBlank(toTo)){
//			query.setParameter("to", to);
//		}
		if(StringUtils.isNotBlank(fromTo)){
			query.setParameter("from", from).setParameter("to", to);
		}
		return query.list();
	
	}

	@Override
	@Transactional
	public Object[] getBBResoureData(String dtailId) {
		Query query  = createQuery("select b,a.shortName,a.color from BasketBallLeagueInfoPO a,BasketBallMatchPO b "
				+ "where a.leagueId=b.leagueId and b.jingcaiId <> null and b.id=:dtailId").setParameter("dtailId", Long.parseLong(dtailId));
		return (Object[]) (query.list() != null ? query.list().get(0) : null);
	}

	@Override
	@Transactional
	public boolean saveBbMatchBaseInfoPO(BasketBallMatchData newBbBaseInfoPO) {
		boolean result = false;
		saveOrUpdate(newBbBaseInfoPO.getBasketBallMatchPO());
		result = true;
		return result;
	}

	@Override
	@Transactional
	public List<Object[]> getLQResoureData(Date from, Date to) {
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
		Query query  = createQuery("select b,a.shortName,a.color from BasketBallLeagueInfoPO a,BasketBallMatchPO b "
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

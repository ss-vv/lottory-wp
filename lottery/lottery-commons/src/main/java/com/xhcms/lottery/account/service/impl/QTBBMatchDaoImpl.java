package com.xhcms.lottery.account.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.SessionFactoryUtils;

import com.unison.lottery.weibo.data.service.store.persist.entity.BBMatchInfoPO;
import com.xhcms.commons.persist.hibernate.DaoImpl;
import com.xhcms.lottery.account.service.QTBBMatchDao;

public class QTBBMatchDaoImpl extends DaoImpl<BBMatchInfoPO> implements QTBBMatchDao{
	private static final long serialVersionUID = 1L;

	public QTBBMatchDaoImpl() {
		 super(BBMatchInfoPO.class);
	}

	@Override
	public BBMatchInfoPO findById(long qtMatchId) {
		if(qtMatchId > 0) {
			Query qs = createQuery("from BBMatchInfoPO as m where m.id = :id");
			qs.setParameter("id", qtMatchId);
			return (BBMatchInfoPO) qs.uniqueResult();
		}
		return null;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Object[]> fillBBMatchResult() {
		//String sql="select lc.lcMatchId,md.name,md.homeTeam,md.guestTeam,md.homeScore,md.guestScore from md_bb_match md,md_qt_lc_matchId lc where md.matchState= -1 and md.id=lc.qiuTanWangMatchId order by md.matchTime desc limit 0,19";
		String sql="select bm.id,bm.name,bm.homeTeam,bm.guestTeam,bm.homeScore,bm.guestScore "
				 + " from md_bb_match_base bm,md_bb_league_base bl where" 
				 +" bm.leagueId=bl.leagueId and bm.matchState=-1 and bm.jingcaiId is not null "
				 + "order by bm.matchTime desc limit 19";
		return createSQLQuery(sql).list();
		
		//return createQuery("from BBMatchInfoPO where matchState=:ms order by matchTime desc").setInteger("ms", -1).setFirstResult(0).setMaxResults(19).list();
	}

	@Override
	public String getTeamColor(Long id) {
		//String sql="select md.color from md_qt_match md where id=(select m.qiuTanWangMatchId from md_qt_lc_matchId m where m.lcMatchId=?)";
		String sql="select l.color from md_bb_league_base l "
				+ " where l.leagueId="
				+ "("
				+ "   select leagueId from md_bb_match_base mb,"
                + "    ("
                + "      select cn_code,playing_time from bb_match where id=?"
                + "    ) t "
                + "    where"
                + "    t.cn_code=mb.jingcaiId and t.playing_time =mb.matchTime"
                + ")";
		Object o=createSQLQuery(sql).setParameter(0, id).uniqueResult();
		return o==null?"green":o.toString();
	}

	@Override
	public BBMatchInfoPO findByJingCaiIdAndMatchTime(String cnCode,
			Date playingTime) {
		if(StringUtils.isNotBlank(cnCode)&&null!=playingTime){
			Query qs = createQuery("from BBMatchInfoPO as m where m.jingCaiId=:jingCaiId and matchTime=:matchTime");
			qs.setParameter("jingCaiId", cnCode);
			qs.setParameter("matchTime", playingTime);
			return (BBMatchInfoPO) qs.uniqueResult();
		}
		return null;
	}
}

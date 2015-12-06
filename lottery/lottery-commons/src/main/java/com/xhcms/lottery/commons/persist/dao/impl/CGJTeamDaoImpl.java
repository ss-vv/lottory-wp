package com.xhcms.lottery.commons.persist.dao.impl;

import java.util.Collection;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import com.xhcms.commons.persist.hibernate.DaoImpl;
import com.xhcms.lottery.commons.lang.CGJConstant;
import com.xhcms.lottery.commons.persist.dao.CGJTeamDao;
import com.xhcms.lottery.commons.persist.entity.CGJTeamPO;

public class CGJTeamDaoImpl extends DaoImpl<CGJTeamPO> implements CGJTeamDao {

    private static final long serialVersionUID = -8866968344416859253L;

    public CGJTeamDaoImpl() {
        super(CGJTeamPO.class);
    }

	@Override
	public CGJTeamPO find(String playType, String season, String teamName, String matchName) {
		if(StringUtils.isNotBlank(playType) && StringUtils.isNotBlank(season) &&
				StringUtils.isNotBlank(teamName) && StringUtils.isNotBlank(matchName)) {
			Criteria c = createCriteria();
			c.add(Restrictions.eq("playType", playType));
			c.add(Restrictions.eq("season", season));
			c.add(Restrictions.eq("teamName", teamName));
			c.add(Restrictions.eq("name", matchName));
			Object obj = c.uniqueResult();
			if(null != obj) {
				return (CGJTeamPO) obj;
			}
		}
		return null;
	}

	@Override
	public int updateCgjTeamCurrentSeason(String year, String playType) {
		int rs = 0;
		if(StringUtils.isNotBlank(year) && StringUtils.isNotBlank(playType)) {
			StringBuffer buf = new StringBuffer();
			buf.append("update cgj_teams c set c.current_season = ? ")
			.append("where c.season <> ? and c.play_type = ?");
			
			SQLQuery sqlQuery = createSQLQuery(buf.toString());
			sqlQuery.setParameter(0, CGJConstant.NOT_CURRENT_SEASON);
			sqlQuery.setParameter(1, year);
			sqlQuery.setParameter(2, playType);
			
			rs = sqlQuery.executeUpdate();
		}
		return rs;
	}

	@Override
	public CGJTeamPO findById(Long id) {
		if(id > 0) {
			Criteria c = createCriteria();
			c.add(Restrictions.eq("id", id));
			Object obj = c.uniqueResult();
			if(null != obj) {
				return (CGJTeamPO) obj;
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CGJTeamPO> findByTeamIdSet(Collection<Long> teamIdSet, String playId) {
		if(null != teamIdSet && teamIdSet.size() > 0 && StringUtils.isNotBlank(playId)) {
			Criteria c = createCriteria();
			c.add(Restrictions.in("teamId", teamIdSet));
			c.add(Restrictions.eq("currentSeason", CGJConstant.YES_CURRENT_SEASON));
			c.add(Restrictions.eq("playType", playId));
			c.addOrder(Order.asc("teamId"));
			return c.list();
		}
		return null;
	}

	@Override
	public CGJTeamPO findByTeamId(Long teamId, String playId) {
		if(teamId > 0 && StringUtils.isNotBlank(playId)) {
			Criteria c = createCriteria();
			c.add(Restrictions.eq("teamId", teamId));
			c.add(Restrictions.eq("currentSeason", CGJConstant.YES_CURRENT_SEASON));
			c.add(Restrictions.eq("playType", playId));
			c.addOrder(Order.asc("teamId"));
			Object result = c.uniqueResult();
			if(null != result)
				return (CGJTeamPO) result; 
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CGJTeamPO> listTeamsCurrentSeason(String playType) {
		if(StringUtils.isNotBlank(playType)) {
			Criteria c = createCriteria();
			c.add(Restrictions.eq("playType", playType));
			c.add(Restrictions.eq("currentSeason", CGJConstant.YES_CURRENT_SEASON));
			c.addOrder(Order.asc("groupName"));
			c.addOrder(Order.asc("odds"));
			return c.list();
		}
		return null;
	}
}
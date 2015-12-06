package com.xhcms.lottery.commons.persist.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import com.xhcms.commons.lang.Paging;
import com.xhcms.commons.persist.hibernate.DaoImpl;
import com.xhcms.lottery.commons.persist.dao.MatchColorDao;
import com.xhcms.lottery.commons.persist.entity.MatchColorPO;

public class MatchColorDaoImpl extends DaoImpl<MatchColorPO> implements MatchColorDao{

	private static final long serialVersionUID = 6922346527053538801L;

	public MatchColorDaoImpl(){
		super(MatchColorPO.class);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MatchColorPO> findBBMatchColorListByStatus(String playId,int status){
		String hql="select c from MatchColorPO as  c,PlayPO as p, BBMatchPO m " +
				" where c.lotteryId=p.lotteryId and m.longLeagueName=c.leagueName and " +
				"m.status=:status and p.id=:playid group by m.longLeagueName";
		return createQuery(hql).setParameter("status", status).setParameter("playid", playId).list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MatchColorPO> findFBMatchColorListByStatus(String playId,int status){
		String hql="select c from MatchColorPO as  c,PlayPO as p, FBMatchPO m " +
				" where c.lotteryId=p.lotteryId and m.longLeagueName=c.leagueName and " +
				"m.status=:status and p.id=:playid group by m.longLeagueName";
		return createQuery(hql).setParameter("status", status).setParameter("playid", playId).list();
	}

	@Override
	public List<MatchColorPO> listColoured(String lotteryId, Paging paging) {
		PagingQuery<MatchColorPO> pq=pagingQuery(paging);
		if(StringUtils.isNotBlank(lotteryId)){
			pq.add(Restrictions.eq("lotteryId", lotteryId));
		}
		return pq.asc("leagueName").list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String[]> listNoColorFBMatchs(Paging paging) {
		String countStr="select count(DISTINCT m.longLeagueName) from FBMatchPO as m " +
				"where not exists(select c.leagueName from MatchColorPO  as c where m.longLeagueName=c.leagueName )";
		String hql="select m.id,m.longLeagueName from FBMatchPO as m where not exists" +
				"(select c.leagueName from MatchColorPO  as c where m.longLeagueName=c.leagueName ) " +
				"group by m.longLeagueName order by m.longLeagueName asc";
		
		int count=0;
		List<?> ls=createQuery(countStr).list();
		if(ls.size()>0)
			count=Integer.valueOf(ls.get(0).toString());
		
		paging.setTotalCount(count);
		return createQuery(hql)
				.setFirstResult(paging.getFirstResult())
				.setMaxResults(paging.getMaxResults()).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String[]> listNoColorBBMatchs(Paging paging) {
		String countStr="select count(DISTINCT m.longLeagueName) from BBMatchPO as m " +
				"where not exists(select c.leagueName from MatchColorPO  as c where m.longLeagueName=c.leagueName )";
		String hql="select m.id,m.longLeagueName from BBMatchPO as m where not exists" +
				"(select c.leagueName from MatchColorPO  as c where m.longLeagueName=c.leagueName ) " +
				"group by m.longLeagueName order by m.longLeagueName asc";
		
		int count=0;
		List<?> ls=createQuery(countStr).list();
		if(ls.size()>0)
			count=Integer.valueOf(ls.get(0).toString());
		
		paging.setTotalCount(count);
		return createQuery(hql)
				.setFirstResult(paging.getFirstResult())
				.setMaxResults(paging.getMaxResults()).list();
	}

	@SuppressWarnings("unchecked" )
	@Override
	public Map<String,String> listColorsByLeagueNames(Set<String> leagueNames) {
		List<MatchColorPO> list = createQuery("from MatchColorPO where leagueShortName in (:leagueNames)")
				.setParameterList("leagueNames", leagueNames).list();
		Map<String,String> maps = new HashMap<String, String>();
		for(MatchColorPO mcp:list){
			maps.put(mcp.getLeagueShortName(), mcp.getColor());
		}
		return maps;
	}

	@Override
	public Map<String, String> loadLeagueNameColorMap() {

		Criteria criteria = createCriteria();
		List<MatchColorPO> matchColorList = criteria.list();
		Map<String,String> map = new HashMap<String, String>();
		for (MatchColorPO matchColorPO : matchColorList) {
			map.put(matchColorPO.getLeagueName(), matchColorPO.getColor());
		}
		return map;
	}
	
	@Override
	public Map<String,String> loadLeagueNameShortName(){
		Criteria criteria = createCriteria();
		List<MatchColorPO> matchColorList = criteria.list();
		Map<String,String> map = new HashMap<String, String>();
		for (MatchColorPO matchColorPO : matchColorList) {
			map.put(matchColorPO.getLeagueName(), matchColorPO.getLeagueShortName());
		}
		return map;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, String> listColorsByLeagueShortNames(
			Set<String> leagueShortNames) {
		List<MatchColorPO> list = createQuery("from MatchColorPO where leagueShortName in (:leagueShortName)")
				.setParameterList("leagueShortName", leagueShortNames).list();
		Map<String,String> maps = new HashMap<String, String>();
		for(MatchColorPO mcp:list){
			maps.put(mcp.getLeagueShortName(), mcp.getColor());
		}
		return maps;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String findLongByShort(String leagueShortName) {
		List<MatchColorPO> list = createQuery("from MatchColorPO where leagueShortName=:leagueShortName")
				.setParameter("leagueShortName", leagueShortName).list();
		if(list.size() > 0){
			return ((MatchColorPO)list.get(0)).getLeagueName();
		} else {
			return null;
		}
	}
}

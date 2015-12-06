package com.xhcms.lottery.commons.persist.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.xhcms.commons.lang.Paging;
import com.xhcms.commons.persist.Dao;
import com.xhcms.lottery.commons.persist.entity.MatchColorPO;

public interface MatchColorDao extends Dao<MatchColorPO>{
	/**
	 * 查询在售篮球赛事对应的赛事颜色
	 * @param playId
	 * @param status
	 * @return
	 */
	public List<MatchColorPO> findBBMatchColorListByStatus(String playId,int status);
	
	/**
	 * 查询在售足球赛事对应的赛事颜色
	 * @param playId
	 * @param status
	 * @return
	 */
	public List<MatchColorPO> findFBMatchColorListByStatus(String playId,int status);
	
	/**
	 * 根据竞彩种类分页返回有颜色的联赛列表
	 * @param lotteryId
	 * @param paging
	 * @return
	 */
	public List<MatchColorPO> listColoured(String lotteryId,Paging paging);
	
	/**
	 * 分页返回无颜色的足球联赛列表
	 * @param paging
	 * @return
	 */
	public List<String[]>  listNoColorFBMatchs(Paging paging);
	
	/**
	 * 分页返回无颜色的篮球联赛列表
	 * @param paging
	 * @return
	 */
	public List<String[]> listNoColorBBMatchs(Paging paging);
	
	/**
	 * 根据联赛名称集合返回对应颜色集合
	 */
	Map<String,String> listColorsByLeagueNames(Set<String> leagueNames);
	
	/**
	 * 根据联赛短名称集合返回对应颜色集合
	 * @param leagueNames
	 * @return
	 */
	Map<String,String> listColorsByLeagueShortNames(Set<String> leagueShortNames);
	
	/**
	 * 
	 * 
	 * @return
	 */
	public Map<String,String> loadLeagueNameColorMap();
	
	
	public Map<String,String> loadLeagueNameShortName();

	public String findLongByShort(String sclass);
	
}

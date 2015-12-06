package com.unison.weibo.admin.service.impl;


import java.io.Serializable;
import java.sql.Blob;
import java.sql.Clob;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;




























import org.hibernate.Query;
import org.springframework.transaction.annotation.Transactional;




























import com.unison.lottery.weibo.data.PageRequest;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.LeagueInfoPO;
import com.unison.weibo.admin.service.FootballLeagueService;
import com.xhcms.commons.persist.hibernate.DaoImpl;

public class FootballServiceImpl extends DaoImpl<LeagueInfoPO> implements FootballLeagueService{


	
	private static final long serialVersionUID = 1L;
	
	
//	@Transactional
//	@Override
//	public List<LeagueInfoPO> findAll() {
//			
//				List<LeagueInfoPO> list = createQuery("select lea.id,lea.leagueId,lea.source,lea.processStatus,lea.colour,lea.chineseName,"
//						+ "lea.chineseNameAll,lea.traditionalName,lea.traditionalNameAll,"
//						+ "lea.englishName,lea.englishNameAll,lea.type,lea.sumRound,lea.currRound,"
//						+ "lea.currMatchSeason,lea.countryId,lea.country,lea.areaId,"
//						+ "lea.importance,lea.logoUrl,lea.ruleInfo,lea.updateTime  from LeagueInfoPO as lea").list();
//				System.out.println("查出了 "+list.size());
//				return list;
//			
//	}
	String sql_count = "select count(id) from LeagueInfoPO";
	
	@Transactional
	@Override
	public List<LeagueInfoPO> findAll(PageRequest pageRequest) {
		Long count =  (long) createQuery(sql_count).uniqueResult();
		pageRequest.setRecordCount(count.intValue());
		List<Object[]> list = createQuery(sqll).setFirstResult(pageRequest.getOffset()).setMaxResults(pageRequest.getPageSize()).list();
	    List<LeagueInfoPO> resultList = new ArrayList<LeagueInfoPO>();
	    LeagueInfoPO e;
	    for(Object[] obj : list)
	    {
	    	e = new LeagueInfoPO();
	    	e.setId((Integer) obj[0]);
	    	e.setLeagueId((String) obj[1]);
	    	e.setSource((Integer) obj[2]);
	    	e.setProcessStatus((Integer) obj[3]);
	    	e.setColour((String) obj[4]);
	    	e.setChineseName((String) obj[5]);
	    	e.setChineseNameAll((String) obj[6]);
	    	e.setTraditionalName((String) obj[7]);
	    	e.setTraditionalNameAll((String) obj[8]);
	    	e.setEnglishName((String) obj[9]);
	    	e.setEnglishNameAll((String) obj[10]);
	    	e.setType((Integer) obj[11]);
	    	e.setSumRound((Integer) obj[12]);
	    	e.setCurrRound((Integer) obj[13]);
	    	e.setCurrMatchSeason((String) obj[14]);
	    	e.setCountryId((String) obj[15]);
	    	e.setCountry((String) obj[16]);
	    	e.setAreaId((Integer) obj[17]);
	    	e.setImportance((Integer) obj[18]);
	    	e.setLogoUrl((String) obj[19]);
	    	e.setRuleInfo((String) obj[20]);
	    	e.setUpdateTime((Date) obj[21]);
	    	//组装为对象
	    	resultList.add(e);
	    	e = null;
	    }
	    
        return resultList;
	    
	}
	
	
	/**
	 * 对象不匹配 去除 documentPath 字段
	 */

	String sqll = "select id,leagueId,source,processStatus,colour,chineseName,"
			+ "chineseNameAll,traditionalName,traditionalNameAll,"
			+ "englishName,englishNameAll,type,sumRound,currRound,"
			+ "currMatchSeason,countryId,country,areaId,"
			+ "importance,logoUrl,ruleInfo,updateTime from LeagueInfoPO ";

	String sql_id = sqll + "as lea where lea.id=:id order by lea.id asc";
	
	@Transactional
	@Override
	public LeagueInfoPO findLagueByID(int id) {
		List<Object[]>  objs = createQuery(sql_id).setParameter("id", id).list();
		LeagueInfoPO e = null;
		if(objs!= null && objs.size()>0)
		{
			Object[] obj = objs.get(0);
	    	e = new LeagueInfoPO();
	    	e.setId((Integer) obj[0]);
	    	e.setLeagueId((String) obj[1]);
	    	e.setSource((Integer) obj[2]);
	    	e.setProcessStatus((Integer) obj[3]);
	    	e.setColour((String) obj[4]);
	    	e.setChineseName((String) obj[5]);
	    	e.setChineseNameAll((String) obj[6]);
	    	e.setTraditionalName((String) obj[7]);
	    	e.setTraditionalNameAll((String) obj[8]);
	    	e.setEnglishName((String) obj[9]);
	    	e.setEnglishNameAll((String) obj[10]);
	    	e.setType((Integer) obj[11]);
	    	e.setSumRound((Integer) obj[12]);
	    	e.setCurrRound((Integer) obj[13]);
	    	e.setCurrMatchSeason((String) obj[14]);
	    	e.setCountryId((String) obj[15]);
	    	e.setCountry((String) obj[16]);
	    	e.setAreaId((Integer) obj[17]);
	    	e.setImportance((Integer) obj[18]);
	    	e.setLogoUrl((String) obj[19]);
	    	e.setRuleInfo((String) obj[20]);
	    	e.setUpdateTime((Date) obj[21]);
		}
		
	    
		return e;
	}

	/**
	 * 修改足球联赛
	 */
	@Transactional
	@Override
	public void footballleagueEdit(int id,String key, String newdata) {
		key.replace(" ", "");
		createQuery("update LeagueInfoPO set "+ key +" =:newdata where id=:id")
		.setParameter("newdata", newdata).setParameter("id", id).executeUpdate();
		
	}

	
	
	

}

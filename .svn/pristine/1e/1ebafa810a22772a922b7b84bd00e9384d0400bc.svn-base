package com.unison.weibo.admin.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.transaction.annotation.Transactional;

import com.unison.lottery.weibo.data.PageRequest;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.BasketBallLeagueInfoPO;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.LeagueInfoPO;
import com.unison.weibo.admin.service.BasketBallService;
import com.xhcms.commons.persist.hibernate.DaoImpl;


public class BasketBallServiceImpl extends DaoImpl<BasketBallLeagueInfoPO> implements BasketBallService{

	/**
	 * 对象和数据库字段不匹配
	 */
	String sqll ="select leagueId, source, processStatus,color,shortName,chineseName,"
			+ "traditionalName,englishName,type,currMatchSeason,"
			+ "countryId, country,currYear,currMonth, sclassKind, sclassTime, logo,"
			+ "createTime, updateTime from BasketBallLeagueInfoPO ";
	String sql = sqll + "as lea order by lea.leagueId asc ";
	String count_sql = "select count(leagueId) from BasketBallLeagueInfoPO";
	@Transactional
	@Override
	public List<BasketBallLeagueInfoPO> findAll(PageRequest pageRequest) {
		Long count =  (long) createQuery(count_sql).uniqueResult();
		pageRequest.setRecordCount(count.intValue());
		List<Object[]> list = createQuery(sql).setFirstResult(pageRequest.getOffset()).setMaxResults(pageRequest.getPageSize()).list();
	    List<BasketBallLeagueInfoPO> resultList = new ArrayList<BasketBallLeagueInfoPO>();
	    BasketBallLeagueInfoPO e;
	    
	    for(Object[] obj : list)
	    {
	    	e = new BasketBallLeagueInfoPO();
	    	
	    	e.setLeagueId((String) obj[0]);
	    	e.setSource((Integer) obj[1]);
	    	e.setProcessStatus((Integer) obj[2]);
	    	e.setColor((String) obj[3]);
	    	e.setShortName((String) obj[4]);
	    	e.setChineseName((String) obj[5]);
	    	e.setTraditionalName((String) obj[6]);
	    	e.setEnglishName((String) obj[7]);
	    	e.setType((Integer) obj[8]);
	    	e.setCurrMatchSeason((String) obj[9]);
	    	e.setCountryId((String) obj[10]);
	    	e.setCountry((String) obj[11]);
	    	e.setCurrYear((String) obj[12]);
	    	e.setCurrMonth((String) obj[13]);
	    	e.setSclassKind((Integer) obj[14]);
	    	e.setSclassTime((String) obj[15]);
	    	e.setLogo((String) obj[16]);
	    	e.setCreateTime((Date) obj[17]);
	    	e.setUpdateTime((Date) obj[18]);
	    	
	    	//组装为对象
	    	resultList.add(e);
	    	e = null;
	    }
	    //System.out.println("篮球查出了"+pageRequest.getRecordCount()+"第一页"+resultList.size());
        return resultList;
	}

	String sql_id = sqll + "as lea where lea.leagueId=:id ";
	@Transactional
	@Override
	public BasketBallLeagueInfoPO findLagueByID(String id) {
		List<Object[]>  objs = createQuery(sql_id).setParameter("id", id).list();
		BasketBallLeagueInfoPO e = null;
		if(objs!= null && objs.size()>0)
		{
			e = new BasketBallLeagueInfoPO();
			Object[] obj = objs.get(0);
			
	    	e.setLeagueId((String) obj[0]);
	    	e.setSource((Integer) obj[1]);
	    	e.setProcessStatus((Integer) obj[2]);
	    	e.setColor((String) obj[3]);
	    	e.setShortName((String) obj[4]);
	    	e.setChineseName((String) obj[5]);
	    	e.setTraditionalName((String) obj[6]);
	    	e.setEnglishName((String) obj[7]);
	    	e.setType((Integer) obj[8]);
	    	e.setCurrMatchSeason((String) obj[9]);
	    	e.setCountryId((String) obj[10]);
	    	e.setCountry((String) obj[11]);
	    	e.setCurrYear((String) obj[12]);
	    	e.setCurrMonth((String) obj[13]);
	    	e.setSclassKind((Integer) obj[14]);
	    	e.setSclassTime((String) obj[15]);
	    	e.setLogo((String) obj[16]);
	    	e.setCreateTime((Date) obj[17]);
	    	e.setUpdateTime((Date) obj[18]);
		}
		return e;
	}

	@Transactional
	@Override
	public void basketballleagueEdit(String leagueId, String key, String newData) {
		//System.out.println(leagueId +" "+key+" "+newData);
		key.replace(" ", "");
		createQuery("update BasketBallLeagueInfoPO set "+ key +" =:newdata where leagueId=:leagueId")
		.setParameter("newdata", newData).setParameter("leagueId", leagueId).executeUpdate();
	}

}

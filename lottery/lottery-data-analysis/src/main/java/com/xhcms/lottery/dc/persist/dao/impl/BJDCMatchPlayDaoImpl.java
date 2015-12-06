package com.xhcms.lottery.dc.persist.dao.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.xhcms.commons.persist.hibernate.DaoImpl;
import com.xhcms.lottery.commons.persist.entity.BJDCMatchPlayPO;
import com.xhcms.lottery.dc.data.BDMatch;
import com.xhcms.lottery.dc.data.BDResult;
import com.xhcms.lottery.dc.persist.dao.BJDCMatchPlayDao;
import com.xhcms.lottery.lang.Constants;


public class BJDCMatchPlayDaoImpl extends DaoImpl<BJDCMatchPlayPO> implements
		BJDCMatchPlayDao {

	@Override
	@SuppressWarnings("unchecked")
	public Map<String, BDMatch> getBDMatchMap(List<BDResult> rs,String typeId) {
		  
		  Map<String, BDMatch> bdmap=null;
		  if(rs!=null&&rs.size()>0){
			  bdmap=new HashMap<String,BDMatch>();
			  for(BDResult bd:rs){
				  List list=createSQLQuery("select id,match_id,concede_points from bjdc_match_play where id=?")
						  .setParameter(0, bd.getMatchId()+typeId).list(); 
						  
				  
				  if(list!=null&&list.size()>0){
					   
						
						 for(int i=0;i<list.size();i++){
							 Object []obj=(Object[])list.get(i);
							 BDMatch bm=new BDMatch();
							 bm.setMatchId(Long.parseLong(obj[1].toString()));
							 bm.setConcedePoints(obj[2]==null?0:BigDecimal.valueOf(Double.parseDouble(obj[2].toString())).intValue());
							 bdmap.put(obj[0].toString(), bm);
							 
						 }
						 
					 }
			  }
			
			  
		  }else{
			  
			  bdmap=new HashMap<String,BDMatch>();
		  }
		
		
	    /*List<BJDCMatchPlayPO> bdpo=createQuery("from BJDCMatchPlayPO where issueNumber= ? and playId='91_BJDC_SPF'")
			  .setString(0, issueId).list();*/
	   /* List list=createSQLQuery("select id,match_id,concede_points from bjdc_match_play where issue_number=:issue and play_id='91_BJDC_SPF'")
	    .setParameter("issue", issueId).list();
			 
	 if(list!=null&&list.size()>0){
		   
		 bdmap=new HashMap<String,BDMatch>();
		 for(int i=0;i<list.size();i++){
			 Object []obj=(Object[])list.get(i);
			 BDMatch bm=new BDMatch();
			 bm.setMatchId(Long.parseLong(obj[1].toString()));
			 bm.setConcedePoints(Integer.parseInt(obj[1].toString()));
			 bdmap.put(obj[0].toString(), bm);
			 
		 }
		 
	 }else{
		 
		 return new HashMap<String,BDMatch>();
	 }
		 */
		      return bdmap;
	}


}

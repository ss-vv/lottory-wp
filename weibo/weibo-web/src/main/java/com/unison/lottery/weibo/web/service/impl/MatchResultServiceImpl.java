package com.unison.lottery.weibo.web.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.unison.lottery.weibo.data.service.store.persist.dao.QTMatchDao;
import com.unison.lottery.weibo.data.vo.MDBBMatchResultVo;
import com.unison.lottery.weibo.data.vo.MDFBMatchResultVo;
import com.unison.lottery.weibo.web.service.MatchResultService;
import com.xhcms.lottery.account.service.QTBBMatchDao;

@Service
public class MatchResultServiceImpl implements MatchResultService {

	@Autowired
	private QTBBMatchDao qtBBMatchDao;
	@Autowired
	private QTMatchDao qtMatchDao;
	
	@Override
	@Transactional
	public void FillMatchResult(List<MDFBMatchResultVo> fb,
			List<MDBBMatchResultVo> bb){
		
		    fillBBMatchResult(bb);
		    fillFBMatchResult(fb);
		
	}
	/**
	 * 查询填充篮球球探比赛结果
	 * @param bb
	 */
	private void fillBBMatchResult(List<MDBBMatchResultVo> bb){
		
	      List<Object[]> bbMatchResult=qtBBMatchDao.fillBBMatchResult();
	      if(bbMatchResult!=null&&bbMatchResult.size()>0){
	    	  
	    	  for(int i=0;i<bbMatchResult.size();i++){
	    		  MDBBMatchResultVo bbres=new MDBBMatchResultVo();
	    		  Object[] obj=bbMatchResult.get(i);
	    		      if(obj[0]!=null){
	    		    	  bbres.setId(obj[0].toString()); 
	    		      }
	    			 if(obj[1]!=null){
	    				 
	    				  bbres.setName(obj[1].toString()); 
	    			 }
	    			 if(obj[2]!=null){
	    				 
	    				 bbres.setHomeTeam(obj[2].toString()); 
	    			 }
	    			 if(obj[3]!=null){
	    				 
	    				 bbres.setGuestTeam(obj[3].toString()); 
	    			 }
	    			 if(obj[4]!=null){
	    				 
	    				  bbres.setHomeScore(Integer.parseInt(obj[4].toString())); 
	    			 }
	    		     if(obj[5]!=null){
	    		    	
	    		    	 bbres.setGuestScore(Integer.parseInt(obj[5].toString()));
	    		    	 
	    		     }
	    			 

	    		  bb.add(bbres);
	    	  }
	    	  
	      }
	}
	/**
	 * 查询填充足球球探比赛结果
	 * @param fb
	 */
	private void fillFBMatchResult(List<MDFBMatchResultVo> fb){
		
		List<Object[]> fbMatchResult=qtMatchDao.findQTFBMatchResult();
		if(fbMatchResult!=null&&fbMatchResult.size()>0){
			
			 for(int i=0;i<fbMatchResult.size();i++){
				 
				 MDFBMatchResultVo fbres=new MDFBMatchResultVo();
				 Object obj[]=fbMatchResult.get(i);
				 if(obj[0]!=null&&!"".equals(obj[0])){
					 
					 fbres.setId(obj[0].toString());
				 }
				 if(obj[1]!=null){
					 
					 fbres.setLeagueShortName(String.valueOf(obj[1]));
				 }
				 if(obj[2]!=null){
					 String homeTeam=String.valueOf(obj[2]);
					 homeTeam=homeTeam.length()>4?homeTeam.substring(0, 4):homeTeam;
					 fbres.setHomeTeamName(homeTeam);
				 }
				 if(obj[3]!=null){
					 String guestTeam=String.valueOf(obj[3]);
					 guestTeam=guestTeam.length()>4?guestTeam.substring(0, 4):guestTeam;
					 fbres.setGuestTeamName(guestTeam);
				 }
				 if(obj[4]!=null){
					 
					 fbres.setScore(String.valueOf(obj[4]));
				 }
				 if(obj[5]!=null&&!"".equals(obj[5])){
					 
					 fbres.setHomeTeamPosition(Integer.parseInt(obj[5].toString())); 
				 }
				 if(obj[6]!=null&&!"".equals(obj[6])){
					 
					 fbres.setGuestTeamPosition(Integer.parseInt(obj[6].toString()));
				 }
				
				 fb.add(fbres);
				 
			 }
			
		}
	}

	
}

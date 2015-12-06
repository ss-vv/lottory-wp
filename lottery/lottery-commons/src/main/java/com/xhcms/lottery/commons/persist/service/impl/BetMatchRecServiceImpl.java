package com.xhcms.lottery.commons.persist.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xhcms.commons.lang.Paging;
import com.xhcms.lottery.commons.data.BetMatchRecVo;
import com.xhcms.lottery.commons.persist.dao.BBMatchPlayDao;
import com.xhcms.lottery.commons.persist.dao.BJDCMatchPlayDao;
import com.xhcms.lottery.commons.persist.dao.BetMatchRecDao;
import com.xhcms.lottery.commons.persist.dao.BetSchemeRecDao;
import com.xhcms.lottery.commons.persist.dao.LotteryHomeRecommendDao;
import com.xhcms.lottery.commons.persist.entity.BetMatchRecPO;
import com.xhcms.lottery.commons.persist.service.BetMatchRecService;

@Service
public class BetMatchRecServiceImpl implements BetMatchRecService {

	@Autowired
	private BetMatchRecDao betMatchRecDao;
	@Autowired
	private BetSchemeRecDao betSchemeRecDao;
	@Autowired
	private BBMatchPlayDao bbMatchPlayDao;
	@Autowired
	private BJDCMatchPlayDao bjdcMatchPlayDao;
	@Autowired
	private LotteryHomeRecommendDao lotteryHomeRecommendDao;
	
	@Transactional
	@Override
	public List<BetMatchRecPO> findBySchemeId(long schemeId) {
		return betMatchRecDao.findBySchemeId(schemeId);
	}

	@Transactional
	@Override
	public BetMatchRecPO getById(long id) {
		return betMatchRecDao.get(id);
	}
	
	@Transactional
	@Override
	public Paging getBetMatchRec(Paging p,String matchType) {
		
		
		  String types[]={"fb","bb","bjdc"};
		  String fb[]={"01_ZC_1","01_ZC_2","80_ZC_1","80_ZC_2"};
		  String bb[]={"06_LC_1","06_LC_2","07_LC_1","07_LC_2"};
		  String bjdc[]={"91_BJDC_SPF","96_BJDC_SF"};
		  if(types[0].equals(matchType)){
			  Integer count=betMatchRecDao.getMatchCount(types[0], fb);
			  p.setTotalCount(count);
			  List<Object[]> fbMatchs=betMatchRecDao.getMatchRecList(types[0],fb,p);
			  matchData(fbMatchs,types[0],p);
		  }else if(types[1].equals(matchType)){
			  Integer count=betMatchRecDao.getMatchCount(types[1], bb);
			  p.setTotalCount(count);
			  List<Object[]> bbMatchs=betMatchRecDao.getMatchRecList(types[0],bb,p);
			  matchData(bbMatchs,types[1],p);
		  }else if(types[2].equals(p)){
			  Integer count=betMatchRecDao.getMatchCount(types[2],bjdc);
			  p.setTotalCount(count);
			  List<Object[]> bjdcMatchs=betMatchRecDao.getMatchRecList(types[0],bjdc,p);
			  matchData(bjdcMatchs,types[2],p); 
			  
		  }

		  return p;
	}
	
	private void matchData(List<Object[]> obj,String type,Paging p){
		List<BetMatchRecVo> matches=new ArrayList<BetMatchRecVo>();
		
		  if(obj!=null&&obj.size()>0){
			  for(int i=0;i<obj.size();i++){
				  BetMatchRecVo betMatch=new BetMatchRecVo();
				      Object []matchs=(Object[])obj.get(i);
				      if(matchs[0]!=null){
				    	  
				    	  betMatch.setId(Long.parseLong(matchs[0].toString())); 
				    	  Integer isOn=lotteryHomeRecommendDao.isOn(Long.parseLong(matchs[0].toString()));
				    	  betMatch.setIsOn(isOn);
				      }
				      if(matchs[1]!=null){
				    	  
				    	  betMatch.setSchemeId(Long.parseLong(matchs[1].toString()));
				    	  String spon=getSponsor(Long.parseLong(matchs[1].toString()));
				    	  betMatch.setSponsor(spon);
				      }
				      if(matchs[2]!=null){
				    	  
				    	  betMatch.setCode(matchs[2].toString());
				      }
				      if(matchs[3]!=null){
				    	  
				    	  betMatch.setPlayId(matchs[3].toString());
				      }
				      if(matchs[4]!=null){
				    	  
				    	  betMatch.setAnnotation(matchs[4].toString());
				      }
				      if(matchs[5]!=null){
				    	  
				    	  betMatch.setCnCode(matchs[5].toString());
				      }
				      if(matchs[6]!=null){
				    	  
				    	  betMatch.setHomeTeamName(matchs[6].toString());
				      }
				      if(matchs[7]!=null){
				    	  
				    	  betMatch.setGuestTeamName(matchs[7].toString());
				      }
				      if(matchs[8]!=null){
				    	  
				    	  betMatch.setLeagueName(matchs[8].toString());
				      }
				      if(matchs[9]!=null){
				    	  
				    	  betMatch.setPlayingTime(StrToDate(matchs[9].toString()));
				      }
				      if(matchs[10]!=null){
				    	  
				    	  betMatch.setEntrustDeadline(StrToDate(matchs[10].toString()));
				      }
				      if(matchs[11]!=null){
				    	  
				    	  betMatch.setMatchId(Long.parseLong(matchs[11].toString()));
				      }
				      if("fb".equals(type)&&matchs[12]!=null){
				    	  
				    	  betMatch.setConcedePoints(matchs[12].toString());
				      }else if("bb".equals(type)&&matchs[11]!=null){
				    	  String id=matchs[11].toString()+matchs[3].toString();
				    	  String score=bbMatchPlayDao.getPointsById(id);
				    	   betMatch.setConcedePoints(score);
				      }else if("bjdc".equals(type)&&matchs[11]!=null){
				    	  String id=matchs[11].toString()+matchs[3].toString();
				    	  String score=bjdcMatchPlayDao.getPoints(id);
				    	  betMatch.setConcedePoints(score);
				      }
				      matches.add(betMatch);
				      
			  }
			  p.setResults(matches);
			  
		  }
		
	}
	private String getSponsor(Long id){
		
		return betSchemeRecDao.getSponsorById(id);
	}
	
	@Override
	public void addRecommendMatch(List<Integer> id) {
		List<BetMatchRecPO> betMatchPo=new ArrayList<BetMatchRecPO>();
		if(id!=null&&id.size()>0){
			
			for(int i=0;i<id.size();i++){
				
				betMatchPo.add(getById(id.get(i)));
			}
			
		}
	}
	private Date StrToDate(String timestamp){
		Date d=null;
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			d=sdf.parse(timestamp);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return d;
	}

	public BetMatchRecDao getBetMatchRecDao() {
		return betMatchRecDao;
	}

	public void setBetMatchRecDao(BetMatchRecDao betMatchRecDao) {
		this.betMatchRecDao = betMatchRecDao;
	}

	public BetSchemeRecDao getBetSchemeRecDao() {
		return betSchemeRecDao;
	}

	public void setBetSchemeRecDao(BetSchemeRecDao betSchemeRecDao) {
		this.betSchemeRecDao = betSchemeRecDao;
	}

	public BBMatchPlayDao getBbMatchPlayDao() {
		return bbMatchPlayDao;
	}

	public void setBbMatchPlayDao(BBMatchPlayDao bbMatchPlayDao) {
		this.bbMatchPlayDao = bbMatchPlayDao;
	}

	public BJDCMatchPlayDao getBjdcMatchPlayDao() {
		return bjdcMatchPlayDao;
	}

	public void setBjdcMatchPlayDao(BJDCMatchPlayDao bjdcMatchPlayDao) {
		this.bjdcMatchPlayDao = bjdcMatchPlayDao;
	}

	public LotteryHomeRecommendDao getLotteryHomeRecommendDao() {
		return lotteryHomeRecommendDao;
	}

	public void setLotteryHomeRecommendDao(
			LotteryHomeRecommendDao lotteryHomeRecommendDao) {
		this.lotteryHomeRecommendDao = lotteryHomeRecommendDao;
	}

	
	
	
}
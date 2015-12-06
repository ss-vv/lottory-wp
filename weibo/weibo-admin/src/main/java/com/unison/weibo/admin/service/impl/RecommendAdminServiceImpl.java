package com.unison.weibo.admin.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unison.weibo.admin.MatchType;
import com.unison.weibo.admin.service.RecommendAdminService;
import com.xhcms.commons.lang.Paging;
import com.xhcms.lottery.commons.data.LtLotteryHomeRecommendVo;
import com.xhcms.lottery.commons.persist.dao.BBMatchDao;
import com.xhcms.lottery.commons.persist.dao.BBMatchPlayDao;
import com.xhcms.lottery.commons.persist.dao.BJDCMatchDao;
import com.xhcms.lottery.commons.persist.dao.BJDCMatchPlayDao;
import com.xhcms.lottery.commons.persist.dao.BetSchemeRecDao;
import com.xhcms.lottery.commons.persist.dao.FBMatchDao;
import com.xhcms.lottery.commons.persist.dao.LotteryHomeRecommendDao;
import com.xhcms.lottery.commons.persist.entity.BBMatchPO;
import com.xhcms.lottery.commons.persist.entity.BJDCMatchPO;
import com.xhcms.lottery.commons.persist.entity.FBMatchPO;
import com.xhcms.lottery.lang.LotteryId;

@Service
public class RecommendAdminServiceImpl implements RecommendAdminService{
	
    @Autowired
	private LotteryHomeRecommendDao lotteryHomeRecommendDao;
    @Autowired
    private FBMatchDao fbMatchDao;
    @Autowired
    private BBMatchPlayDao bbMatchPlayDao;
    @Autowired
    private BJDCMatchPlayDao bjdcMatchPlayDao;
    @Autowired
    private BetSchemeRecDao betSchemeRecDao;
    @Autowired
    private BBMatchDao bbMatchDao;
    @Autowired
    private BJDCMatchDao bjdcMatchDao;
    
    @Transactional
	@Override
	public Paging getRecommendMatch(Paging p, String matchType) {
		// TODO Auto-generated method stub
    	String mType="";
    	if(StringUtils.isNotBlank(matchType)){
    		if(MatchType.fb.name().equals(matchType)){
    			mType=LotteryId.JCZQ.name();		
    		}else if(MatchType.bb.name().equals(matchType)){
	            mType=LotteryId.JCLQ.name();
    		}else if(MatchType.bjdc.name().equals(matchType)){
    			mType=LotteryId.BJDC.name();
    		}
    		
    	}
		Integer count=lotteryHomeRecommendDao.getLtLotteryHomeRecommendCount(mType);
		p.setTotalCount(count);
		List<Object[]> relist=lotteryHomeRecommendDao.getLtLotteryHomeRecommendBy(p, mType);
		List<LtLotteryHomeRecommendVo> list=fillData(relist);
		getMatchInfo(list,p);
		return p;
	}
	

	private List<LtLotteryHomeRecommendVo> fillData(List<Object[]> obj){
		
		List<LtLotteryHomeRecommendVo> ltvo=new ArrayList<LtLotteryHomeRecommendVo>();
		if(obj!=null&&obj.size()>0){
			
			for(int i=0;i<obj.size();i++){
				LtLotteryHomeRecommendVo vo=new LtLotteryHomeRecommendVo();
				Object o[]=obj.get(i);
				if(o[0]!=null){
					
					vo.setReId(Long.parseLong(o[0].toString()));
				}
				if(o[1]!=null){
					
					vo.setReLotteryId(o[1].toString());
				}
				if(o[2]!=null){
					
					vo.setReBetMatchId(Long.parseLong(o[2].toString()));
				}
				if(o[3]!=null){
					
					vo.setReStatus(Integer.parseInt(o[3].toString()));
				}
				if(o[4]!=null){
					
					vo.setReCreatedTime(StrToDate(o[4].toString()));
				}
				if(o[5]!=null){
					
					vo.setReUpdateTime(StrToDate(o[5].toString()));
				}
				if(o[6]!=null){
					
					vo.setSchemeId(Long.parseLong(o[6].toString()));
				}
				if(o[7]!=null){
					
					vo.setMatchId(Long.parseLong(o[7].toString()));
				}
				if(o[8]!=null){
					
					vo.setCode(o[8].toString());
				}
				if(o[9]!=null){
					
					vo.setPlayId(o[9].toString());
				}
				if(o[10]!=null){
					vo.setAnnotation(o[10].toString());
					
				}
				if(o[11]!=null){
					
					vo.setReDeadlineTime(StrToDate(o[11].toString()));
				}
				ltvo.add(vo);
				
			}
		}
		return ltvo;
		
	}
	private void getMatchInfo(List<LtLotteryHomeRecommendVo> list,Paging p){
		if(list!=null&&list.size()>0){
			
			for(int i=0;i<list.size();i++){
				LtLotteryHomeRecommendVo v=list.get(i);
				if(LotteryId.JCZQ.name().equals(v.getReLotteryId())){
					FBMatchPO fb=fbMatchDao.get(v.getMatchId());
					if(fb!=null){
						
						v.setHomeTeamName(fb.getHomeTeamName());
						v.setGuestTeamName(fb.getGuestTeamName());
						v.setPlayingTime(fb.getPlayingTime());
						v.setLeagueName(fb.getLeagueName());
						v.setConcedePoints(fb.getConcedePoints()+"");
					}
				}else if(LotteryId.JCLQ.name().equals(v.getReLotteryId())){
					BBMatchPO bb=bbMatchDao.get(v.getMatchId());
					if(bb!=null){
						v.setHomeTeamName(bb.getHomeTeamName());
						v.setGuestTeamName(bb.getGuestTeamName());
						v.setPlayingTime(bb.getPlayingTime());
						v.setLeagueName(bb.getLeagueName());
					}
					String points=bbMatchPlayDao.getPointsById(v.getMatchId()+v.getPlayId());
					v.setConcedePoints(points+"");
				}else if(LotteryId.BJDC.name().equals(v.getReLotteryId())||LotteryId.BDSF.name().equals(v.getReLotteryId())){
					BJDCMatchPO bjdc=bjdcMatchDao.get(v.getMatchId());
					 if(bjdc!=null){
						    v.setHomeTeamName(bjdc.getHomeTeamName());
							v.setGuestTeamName(bjdc.getGuestTeamName());
							v.setPlayingTime(bjdc.getPlayingTime());
							v.setLeagueName(bjdc.getLeagueName());
						 
					 }
					String points=bjdcMatchPlayDao.getPoints(v.getMatchId()+v.getPlayId());
					v.setConcedePoints(points+"");
				}
				
				String sponsor=betSchemeRecDao.getSponsorById(v.getSchemeId());
				v.setSponsor(sponsor);
				
			}
			p.setResults(list);
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
	@Transactional
	@Override
	public void putOnRecommend(List<Long> id) {
		if(id!=null&&id.size()>0){
			
			for(int i=0;i<id.size();i++){
				
				lotteryHomeRecommendDao.updateStatus(id.get(i),0);
			}
		}
		
	}
	@Transactional
	@Override
	public void getOffRecommend(List<Long> id) {
		// TODO Auto-generated method stub
            if(id!=null&&id.size()>0){
			
			for(int i=0;i<id.size();i++){
				
				lotteryHomeRecommendDao.updateStatus(id.get(i),-1);
			}
		}
		
	}

}

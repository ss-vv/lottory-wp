package com.xhcms.lottery.commons.persist.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.davcai.lottery.dao.JCLQAnalyseDao;
import com.davcai.lottery.dao.JCZQAnalyseDao;
import com.davcai.lottery.service.MatchNameService;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.BasketBallMatchPO;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.FbMatchBaseInfoPO;
import com.xhcms.commons.lang.Paging;
import com.xhcms.lottery.commons.data.BBMatchPlay;
import com.xhcms.lottery.commons.data.BasketBallMatchData;
import com.xhcms.lottery.commons.data.BetMatch;
import com.xhcms.lottery.commons.data.CTFBMatch;
import com.xhcms.lottery.commons.data.FBMatchPlay;
import com.xhcms.lottery.commons.data.FootBallMatchData;
import com.xhcms.lottery.commons.data.OpenSaleTime;
import com.xhcms.lottery.commons.persist.dao.BBMatchDao;
import com.xhcms.lottery.commons.persist.dao.BBMatchPlayDao;
import com.xhcms.lottery.commons.persist.dao.CTFBMatchDao;
import com.xhcms.lottery.commons.persist.dao.FBMatchDao;
import com.xhcms.lottery.commons.persist.dao.FBMatchPlayDao;
import com.xhcms.lottery.commons.persist.dao.LotteryOpenSaleDao;
import com.xhcms.lottery.commons.persist.dao.MatchColorDao;
import com.xhcms.lottery.commons.persist.dao.SystemConfDao;
import com.xhcms.lottery.commons.persist.entity.BBMatchPO;
import com.xhcms.lottery.commons.persist.entity.BBMatchPlayPO;
import com.xhcms.lottery.commons.persist.entity.BBMatchResult;
import com.xhcms.lottery.commons.persist.entity.CTFBMatchPO;
import com.xhcms.lottery.commons.persist.entity.FBMatchPO;
import com.xhcms.lottery.commons.persist.entity.FBMatchPlayPO;
import com.xhcms.lottery.commons.persist.entity.LotteryOpenSalePO;
import com.xhcms.lottery.commons.persist.entity.MatchPlay;
import com.xhcms.lottery.commons.persist.entity.MixMatchPlay;
import com.xhcms.lottery.commons.persist.entity.QTMatchPO;
import com.xhcms.lottery.commons.persist.entity.SingalMatchPlay;
import com.xhcms.lottery.commons.persist.service.MatchService;
import com.xhcms.lottery.commons.util.OpenSaleTimeUtil;
import com.xhcms.lottery.commons.utils.MatchPlayComparator;
import com.xhcms.lottery.conf.LeagueColorConf;
import com.xhcms.lottery.conf.SystemConf;
import com.xhcms.lottery.lang.Constants;
import com.xhcms.lottery.lang.EntityStatus;
import com.xhcms.lottery.lang.LotteryId;
import com.xhcms.lottery.lang.MixPlayType;
import com.xhcms.lottery.lang.PlayType;

public class MatchServiceImpl implements MatchService {
    @Autowired
    private FBMatchDao fbMatchDao;
    @Autowired
    private FBMatchPlayDao fbMatchPlayDao;
    @Autowired
    private BBMatchDao bbMatchDao;
    @Autowired
    private BBMatchPlayDao bbMatchPlayDao;
    @Autowired
    private MatchNameService matchNameService;
    @Autowired
    private MatchColorDao matchColorDao;
    
    @Autowired
    private CTFBMatchDao ctfbMatchDao;
    
    @Autowired
    private LotteryOpenSaleDao lotteryOpenSaleDao;
    
    @Autowired
    private SystemConfDao systemConfDao;
    
    @Autowired
 	private LeagueColorConf initLeagueColor;
    
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private JCZQAnalyseDao jCZQAnalyseDao;
    
    @Autowired
    private JCLQAnalyseDao jCLQAnalyseDao;
    
    @Override
  	public List<BetMatch> makeMatchList(String[] matchArr, Pattern p) {
  		List<BetMatch> matchList = new ArrayList<BetMatch>(matchArr.length);
  		for (int i = 0; i < matchArr.length; i++) {
  			String match = matchArr[i];
  			String[] m = p.split(match);
  			BetMatch bm = new BetMatch();
  			bm.setMatchId(Long.parseLong(m[0]));
  			bm.setCode(m[1]);
  			//----江浩翔 2013-06-28 添加混合过关玩法投注----begin----
  			if(m.length > 2) {
  				bm.setSeed(Boolean.parseBoolean(m[2]));  //加胆码
  			}
  			if(m.length > 3){ //为混合过关方式 
  		        MixPlayType mp = MixPlayType.valueOfPlayName(m[3]);
  		        bm.setPlayId(mp.getPlayId());
  		    }
  			//----江浩翔 2013-06-28 添加混合过关玩法投注----end----
  			matchList.add(bm);
  		}
  		return matchList;
  	}
    
    @Override
    @Transactional
    public List<CTFBMatch> findByIssueNumberAndPlayId(String issueNumber, String playId) {
    	List<CTFBMatchPO> data = ctfbMatchDao.findByIssueNoAndPlayId(issueNumber, playId);
    	List<CTFBMatch> results = new ArrayList<CTFBMatch>(data.size());
    	for(CTFBMatchPO po : data) {
    		CTFBMatch m = new CTFBMatch();
    		BeanUtils.copyProperties(po, m);
            results.add(m);
    	}
    	return results;
    }
    
    private Comparator<Long> comparator = new Comparator<Long>() {
        @Override
        public int compare(Long x, Long y) {
            if (x > y) {
                return -1;
            }
            if (x < y) {
                return 1;
            }
            return 0;
        }
    };
	
    
    @Override
    @Transactional(readOnly = true)
    public List<MixMatchPlay> listMixFBOnSale(String playId) {
    	List<MixMatchPlay> results = new ArrayList<MixMatchPlay>(); 
    	if(StringUtils.isBlank(playId) || !Constants.PLAY_05_ZC_2.equals(playId) ){
    		return results;
    	}
    	HashSet<String> leagueNames = new HashSet<String>();
    	LinkedHashMap<Long,String> leagueMatchIdsCodes = new LinkedHashMap<Long,String>(); 
    	LinkedHashMap<String, MatchPlay> mps01 = mapsFBOnSale(Constants.PLAY_01_ZC_2, leagueMatchIdsCodes);
    	LinkedHashMap<String, MatchPlay> mps02 = mapsFBOnSale(Constants.PLAY_02_ZC_2, leagueMatchIdsCodes);
    	LinkedHashMap<String, MatchPlay> mps03 = mapsFBOnSale(Constants.PLAY_03_ZC_2, leagueMatchIdsCodes);
    	LinkedHashMap<String, MatchPlay> mps04 = mapsFBOnSale(Constants.PLAY_04_ZC_2, leagueMatchIdsCodes);
    	LinkedHashMap<String, MatchPlay> mps05 = mapsFBOnSale(Constants.PLAY_80_ZC_2, leagueMatchIdsCodes);
    	Object[] matchIds = leagueMatchIdsCodes.keySet().toArray();
		Arrays.sort(matchIds);
		
		for(Object mid:matchIds){
			List<String> notOpenSalePlays = new ArrayList<String>();
			MixMatchPlay mmp = new MixMatchPlay();
			String code = leagueMatchIdsCodes.get(mid);
			MatchPlay mp = mps02.get(code) == null ? null : (MatchPlay)mps02.get(code);
			if(mp == null){
				MixMatchPlay mpCopy = new MixMatchPlay();
				if(mps01.get(code) != null){
					BeanUtils.copyProperties(mps01.get(code), mpCopy);
				}else if(mps03.get(code) != null){
					BeanUtils.copyProperties(mps03.get(code), mpCopy);
				}else if(mps04.get(code) != null){
					BeanUtils.copyProperties(mps04.get(code), mpCopy);
				}else{
					continue;
				}
				mp = mpCopy;
				mp.setOdds("0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0");
				mp.setOptions(PlayType.getOptionByPlayId(Constants.PLAY_02_ZC));
				mp.setPriorOdds("0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0");
				mp.setDefaultScore(0);
				notOpenSalePlays.add(MixPlayType.BF.name());
			}
			BeanUtils.copyProperties(mp, mmp);
			MatchPlay mp01 = mps01.get(code);
			MatchPlay mp03 = mps03.get(code);
			MatchPlay mp04 = mps04.get(code);
			MatchPlay mp05 = mps05.get(code);
			
			mmp.setOdds_80ZC2( mp05 == null ? "0,0,0" : mp05.getOdds());
			mmp.setOptions_80ZC2( mp05 == null ? PlayType.getOptionByPlayId(Constants.PLAY_01_ZC) : mp05.getOptions());
			mmp.setPriorOdds_80ZC2( mp05 == null ? "0,0,0" : mp05.getPriorOdds());
			mmp.setDefaultScore(0);
			if(mp05 == null){
				notOpenSalePlays.add(MixPlayType.BRQSPF.name());
			}
			
			mmp.setOdds_01ZC2( mp01 == null ? "0,0,0" : mp01.getOdds());
			mmp.setOptions_01ZC2( mp01 == null ? PlayType.getOptionByPlayId(Constants.PLAY_01_ZC) : mp01.getOptions());
			mmp.setPriorOdds_01ZC2( mp01 == null ? "0,0,0" : mp01.getPriorOdds());
			mmp.setDefaultScore( mp01 == null ? 0 : mp01.getDefaultScore());
			if(mp01 == null){
				notOpenSalePlays.add(MixPlayType.SPF.name());
			}
			
			mmp.setOdds_03ZC2( mp03 == null ? "0,0,0,0,0,0,0,0" : mp03.getOdds());
			mmp.setOptions_03ZC2( mp03 == null ? PlayType.getOptionByPlayId(Constants.PLAY_03_ZC) : mp03.getOptions());
			mmp.setPriorOdds_03ZC2( mp03 == null ? "0,0,0,0,0,0,0,0"  : mp03.getPriorOdds());
			if(mp03 == null){
				notOpenSalePlays.add(MixPlayType.JQS.name());
			}
				
			mmp.setOdds_04ZC2( mp04 == null ? "0,0,0,0,0,0,0,0,0" : mp04.getOdds());
			mmp.setOptions_04ZC2( mp04 == null ? PlayType.getOptionByPlayId(Constants.PLAY_04_ZC) : mp04.getOptions());
			mmp.setPriorOdds_04ZC2( mp04 == null ? "0,0,0,0,0,0,0,0,0" : mp04.getPriorOdds());
			if(mp04 == null){
				notOpenSalePlays.add(MixPlayType.BQC.name());
			}
			if(!notOpenSalePlays.isEmpty()){
				mmp.setNotOpenSalePlays(notOpenSalePlays.toString().replace("[", "").replace("]", ""));
			}
			leagueNames.add(mmp.getLeagueName());
			results.add(mmp);
		}
		
		if(leagueNames != null && leagueNames.size()>0){
	        Map<String, String> colors = matchColorDao.listColorsByLeagueNames(leagueNames);
	        for(MatchPlay mp:results){
	        	mp.setColor(colors.get(mp.getLeagueName()));
	        }
		}
    	return results; 
    }
    
    private List<MixMatchPlay> listHHBBMatchByTime(Date from ,Date to) {
    	List<MixMatchPlay> results = new ArrayList<MixMatchPlay>(); 
    	HashSet<String> leagueNames = new HashSet<String>();
    	LinkedHashMap<Long ,String> leagueMatchIdsCodes = new LinkedHashMap<Long,String>(); 
    	LinkedHashMap<String,MatchPlay> mps01 = mapsBBByTime(Constants.PLAY_06_LC_2, leagueMatchIdsCodes,from,to);
    	LinkedHashMap<String,MatchPlay> mps02 = mapsBBByTime(Constants.PLAY_07_LC_2, leagueMatchIdsCodes,from,to);
    	LinkedHashMap<String,MatchPlay> mps03 = mapsBBByTime(Constants.PLAY_08_LC_2, leagueMatchIdsCodes,from,to);
    	LinkedHashMap<String,MatchPlay> mps04 = mapsBBByTime(Constants.PLAY_09_LC_2, leagueMatchIdsCodes,from,to);
    	LinkedHashMap<String,MatchPlay> mps05 = mapsBBByTime(Constants.PLAY_06_LC_1, leagueMatchIdsCodes,from,to);
    	LinkedHashMap<String,MatchPlay> mps06 = mapsBBByTime(Constants.PLAY_07_LC_1, leagueMatchIdsCodes,from,to);
    	LinkedHashMap<String,MatchPlay> mps07 = mapsBBByTime(Constants.PLAY_08_LC_1, leagueMatchIdsCodes,from,to);
    	LinkedHashMap<String,MatchPlay> mps08 = mapsBBByTime(Constants.PLAY_09_LC_1, leagueMatchIdsCodes,from,to);
    	
    	Object[] matchIds = leagueMatchIdsCodes.keySet().toArray();
    	Arrays.sort(matchIds);
    	for(Object mid:matchIds){
    		List<String> notOpenSalePlays = new ArrayList<String>();
    		//初始化混合赛事-并添加赛事数据-开始
    		MixMatchPlay mmp = new MixMatchPlay();
    		String code = leagueMatchIdsCodes.get(mid);
			if(mps01.get(code) != null){
				BeanUtils.copyProperties(mps01.get(code), mmp);
			}else if(mps02.get(code) != null){
				BeanUtils.copyProperties(mps02.get(code), mmp);
			}else if(mps03.get(code) != null){
				BeanUtils.copyProperties(mps03.get(code), mmp);
			}else if(mps04.get(code) != null){
				BeanUtils.copyProperties(mps04.get(code), mmp);
			}else{
				continue;
			}
			mmp.setOdds(null);
			mmp.setOptions(null);
			mmp.setPriorOdds(null);
			mmp.setDefaultScore(0);
			//初始化混合赛事-并添加赛事数据-结束
			
    		MatchPlay mp01 = mps01.get(code);
    		MatchPlay mp02 = mps02.get(code);
    		MatchPlay mp03 = mps03.get(code);
    		MatchPlay mp04 = mps04.get(code);
    		MatchPlay mp05 = mps05.get(code);
    		MatchPlay mp06 = mps06.get(code);
    		MatchPlay mp07 = mps07.get(code);
    		MatchPlay mp08 = mps08.get(code);
    		if(mp01 == null){
    			notOpenSalePlays.add(MixPlayType.RFSF.name());
    		} else {
	    		mmp.setOdds_06LC2(mp01.getOdds());
	    		mmp.setOptions_06LC2(mp01.getOptions());
	    		mmp.setPriorOdds_06LC2(mp01.getPriorOdds());
	    		mmp.setDefaultScore(mp01.getDefaultScore());
	    		if(mp05 != null){
	    			mmp.setSupport06LC2SinglePass(true);
	    		}
    		}

    		if(mp02 == null){
    			notOpenSalePlays.add(MixPlayType.SF.name());
    		} else {
    			mmp.setOdds_07LC2(mp02.getOdds());
    			mmp.setOptions_07LC2(mp02.getOptions());
    			mmp.setPriorOdds_07LC2(mp02.getPriorOdds());
    			if(mp06 != null){
	    			mmp.setSupport07LC2SinglePass(true);
	    		}
    		}
    		
    		if(mp03 == null){
    			notOpenSalePlays.add(MixPlayType.FC.name());
    		} else {
    			mmp.setOdds_08LC2(mp03.getOdds());
        		mmp.setOptions_08LC2(mp03.getOptions());
        		mmp.setPriorOdds_08LC2(mp03.getPriorOdds());
        		if(mp07 != null){
	    			mmp.setSupport08LC2SinglePass(true);
	    		}
    		}
    		
    		if(mp04 == null){
    			notOpenSalePlays.add(MixPlayType.DXF.name());
    		} else {
    			mmp.setOdds_09LC2(mp04.getOdds());
        		mmp.setOptions_09LC2(mp04.getOptions());
        		mmp.setPriorOdds_09LC2(mp04.getPriorOdds());
        		mmp.setDefaultScore_09LC2(mp04.getDefaultScore());
        		if(mp08 != null){
	    			mmp.setSupport09LC2SinglePass(true);
	    		}
    		}
    		
    		if(!notOpenSalePlays.isEmpty()){
    			mmp.setNotOpenSalePlays(notOpenSalePlays.toString().replace("[", "").replace("]", ""));
    		}
    		results.add(mmp);
    		leagueNames.add(mmp.getLeagueName());
    	}
    	
    	if(leagueNames != null && leagueNames.size()>0){
    		Map<String, String> colors = matchColorDao.listColorsByLeagueNames(leagueNames);
    		for(MatchPlay mp:results){
    			mp.setColor(colors.get(mp.getLeagueName()));
    		}
    	}
    	return results;
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<MixMatchPlay> listMixBBOnSale(String playId) {
    	List<MixMatchPlay> results = new ArrayList<MixMatchPlay>(); 
    	if(StringUtils.isBlank(playId) || !Constants.PLAY_10_LC_2.equals(playId)){
    		return results;
    	}
    	
    	HashSet<String> leagueNames = new HashSet<String>();
    	
    	LinkedHashMap<Long ,String> leagueMatchIdsCodes = new LinkedHashMap<Long,String>(); 
		LinkedHashMap<String,MatchPlay> mps01 = mapsBBOnSale(Constants.PLAY_06_LC_2, leagueMatchIdsCodes);
		LinkedHashMap<String,MatchPlay> mps02 = mapsBBOnSale(Constants.PLAY_07_LC_2, leagueMatchIdsCodes);
		LinkedHashMap<String,MatchPlay> mps03 = mapsBBOnSale(Constants.PLAY_08_LC_2, leagueMatchIdsCodes);
		LinkedHashMap<String,MatchPlay> mps04 = mapsBBOnSale(Constants.PLAY_09_LC_2, leagueMatchIdsCodes);
		Object[] matchIds = leagueMatchIdsCodes.keySet().toArray();
		Arrays.sort(matchIds);
		
		for(Object mid:matchIds){
			List<String> notOpenSalePlays = new ArrayList<String>();
			MixMatchPlay mmp = new MixMatchPlay();
			String code = leagueMatchIdsCodes.get(mid);
			MatchPlay mp = mps02.get(code) == null ? null : (MatchPlay)mps02.get(code);
			if(mp == null){
				MixMatchPlay mpCopy = new MixMatchPlay();
				if(mps01.get(code) != null){
					BeanUtils.copyProperties(mps01.get(code), mpCopy);
				}else if(mps03.get(code) != null){
					BeanUtils.copyProperties(mps03.get(code), mpCopy);
				}else if(mps04.get(code) != null){
					BeanUtils.copyProperties(mps04.get(code), mpCopy);
				}else{
					continue;
				}
				mp = mpCopy;
				mp.setOdds("0,0");
				mp.setOptions(PlayType.getOptionByPlayId(Constants.PLAY_07_LC));
				mp.setPriorOdds(null);
				mp.setDefaultScore(0);
				notOpenSalePlays.add(MixPlayType.SF.name());
			}
			BeanUtils.copyProperties(mp, mmp);
			MatchPlay mp06 = mps01.get(code);
			MatchPlay mp08 = mps03.get(code);
			MatchPlay mp09 = mps04.get(code);
			
			mmp.setOdds_06LC2( mp06 == null ? "0,0" : mp06.getOdds());
			mmp.setOptions_06LC2( mp06 == null ? PlayType.getOptionByPlayId(Constants.PLAY_06_LC) : mp06.getOptions());
			mmp.setPriorOdds_06LC2( mp06 == null ? null : mp06.getPriorOdds());
			mmp.setDefaultScore( mp06 == null ? 0 : mp06.getDefaultScore());
			if(mp06 == null){
				notOpenSalePlays.add(MixPlayType.RFSF.name());
			}
			
			mmp.setOdds_08LC2( mp08 == null ? "0,0,0,0,0,0,0,0,0,0,0,0" : mp08.getOdds());
			mmp.setOptions_08LC2( mp08 == null ? PlayType.getOptionByPlayId(Constants.PLAY_08_LC) : mp08.getOptions());
			mmp.setPriorOdds_08LC2( mp08 == null ? null : mp08.getPriorOdds());
			if(mp08 == null){
				notOpenSalePlays.add(MixPlayType.FC.name());
			}
				
			mmp.setOdds_09LC2( mp09 == null ? "0,0" : mp09.getOdds());
			mmp.setOptions_09LC2( mp09 == null ? PlayType.getOptionByPlayId(Constants.PLAY_09_LC) : mp09.getOptions());
			mmp.setPriorOdds_09LC2( mp09 == null ? null : mp09.getPriorOdds());
			mmp.setDefaultScore_09LC2( mp09 == null ? 1 : mp09.getDefaultScore());
			if(mp09 == null){
				notOpenSalePlays.add(MixPlayType.DXF.name());
			}
			if(!notOpenSalePlays.isEmpty()){
				mmp.setNotOpenSalePlays(notOpenSalePlays.toString().replace("[", "").replace("]", ""));
			}
			results.add(mmp);
			
			leagueNames.add(mmp.getLeagueName());
		}
		
		if(leagueNames != null && leagueNames.size()>0){
	        Map<String, String> colors = matchColorDao.listColorsByLeagueNames(leagueNames);
	        for(MatchPlay mp:results){
	        	mp.setColor(colors.get(mp.getLeagueName()));
	        }
		}
		
    	return results;
    }

    @Override
    @Transactional(readOnly = true)
    public List<MixMatchPlay> listFBInLast2Days() {
    	Calendar c = Calendar.getInstance();
    	c.add(Calendar.DATE, -1);
    	c.set(Calendar.HOUR_OF_DAY, 12);
    	c.set(Calendar.MINUTE, 0);
    	c.set(Calendar.SECOND, 0);
    	c.set(Calendar.MILLISECOND, 0);
    	Date from = c.getTime();
    	return listHHFBMatchByTime(from,null);
    }

	private List<MixMatchPlay> listHHFBMatchByTime(Date from,Date to) {
		List<MixMatchPlay> results = new ArrayList<MixMatchPlay>(); 
    	HashSet<String> leagueNames = new HashSet<String>();
    	LinkedHashMap<Long,String> leagueMatchIdsCodes = new LinkedHashMap<Long,String>();

    	LinkedHashMap<String, MatchPlay> mps01 = mapsFBByTime(Constants.PLAY_01_ZC_2, leagueMatchIdsCodes,from,to);
    	LinkedHashMap<String, MatchPlay> mps02 = mapsFBByTime(Constants.PLAY_02_ZC_2, leagueMatchIdsCodes,from,to);
    	LinkedHashMap<String, MatchPlay> mps03 = mapsFBByTime(Constants.PLAY_03_ZC_2, leagueMatchIdsCodes,from,to);
    	LinkedHashMap<String, MatchPlay> mps04 = mapsFBByTime(Constants.PLAY_04_ZC_2, leagueMatchIdsCodes,from,to);
    	LinkedHashMap<String, MatchPlay> mps05 = mapsFBByTime(Constants.PLAY_80_ZC_2, leagueMatchIdsCodes,from,to);
    	
    	LinkedHashMap<String, MatchPlay> mps06 = mapsFBByTime(Constants.PLAY_01_ZC_1, leagueMatchIdsCodes,from,to);
    	LinkedHashMap<String, MatchPlay> mps07 = mapsFBByTime(Constants.PLAY_02_ZC_1, leagueMatchIdsCodes,from,to);
    	LinkedHashMap<String, MatchPlay> mps08 = mapsFBByTime(Constants.PLAY_03_ZC_1, leagueMatchIdsCodes,from,to);
    	LinkedHashMap<String, MatchPlay> mps09 = mapsFBByTime(Constants.PLAY_04_ZC_1, leagueMatchIdsCodes,from,to);
    	LinkedHashMap<String, MatchPlay> mps10 = mapsFBByTime(Constants.PLAY_80_ZC_1, leagueMatchIdsCodes,from,to);
    	
    	Object[] matchIds = leagueMatchIdsCodes.keySet().toArray();
		Arrays.sort(matchIds);
		
		for(Object mid:matchIds){
			List<String> notOpenSalePlays = new ArrayList<String>();
			MixMatchPlay mmp = new MixMatchPlay();
			String code = leagueMatchIdsCodes.get(mid);
			MatchPlay mp = mps02.get(code) == null ? null : mps02.get(code);
			if(mp == null){
				MixMatchPlay mpCopy = new MixMatchPlay();
				if(mps01.get(code) != null){
					BeanUtils.copyProperties(mps01.get(code), mpCopy);
				}else if(mps03.get(code) != null){
					BeanUtils.copyProperties(mps03.get(code), mpCopy);
				}else if(mps04.get(code) != null){
					BeanUtils.copyProperties(mps04.get(code), mpCopy);
				}else if(mps05.get(code) != null){
					BeanUtils.copyProperties(mps05.get(code), mpCopy);
				}else{
					continue;
				}
				mp = mpCopy;
				mp.setOdds("0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0");
				mp.setOptions(PlayType.getOptionByPlayId(Constants.PLAY_02_ZC));
				mp.setPriorOdds("0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0");
				mp.setDefaultScore(0);
				notOpenSalePlays.add(MixPlayType.BF.name());
			}
			BeanUtils.copyProperties(mp, mmp);
			MatchPlay mp01 = mps01.get(code);
			MatchPlay mp02 = mps02.get(code);
			MatchPlay mp03 = mps03.get(code);
			MatchPlay mp04 = mps04.get(code);
			MatchPlay mp05 = mps05.get(code);

			MatchPlay mp06 = mps06.get(code);
			MatchPlay mp07 = mps07.get(code);
			MatchPlay mp08 = mps08.get(code);
			MatchPlay mp09 = mps09.get(code);
			MatchPlay mp10 = mps10.get(code);
			
			mmp.setOdds_80ZC2( mp05 == null ? "0,0,0" : mp05.getOdds());
			mmp.setOptions_80ZC2( mp05 == null ? PlayType.getOptionByPlayId(Constants.PLAY_01_ZC) : mp05.getOptions());
			mmp.setPriorOdds_80ZC2( mp05 == null ? "0,0,0" : mp05.getPriorOdds());
			mmp.setDefaultScore(0);
			if(mp05 == null){
				notOpenSalePlays.add(MixPlayType.BRQSPF.name());
			} else {
				if(mp10 != null){
					mmp.setSupport80ZC2SinglePass(true);
				}
			}
			
			mmp.setOdds_01ZC2( mp01 == null ? "0,0,0" : mp01.getOdds());
			mmp.setOptions_01ZC2( mp01 == null ? PlayType.getOptionByPlayId(Constants.PLAY_01_ZC) : mp01.getOptions());
			mmp.setPriorOdds_01ZC2( mp01 == null ? "0,0,0" : mp01.getPriorOdds());
			mmp.setDefaultScore( mp01 == null ? 0 : mp01.getDefaultScore());
			if(mp01 == null){
				notOpenSalePlays.add(MixPlayType.SPF.name());
			} else {
				if(mp06 != null){
					mmp.setSupport01ZC2SinglePass(true);
				}
			}
			
			if(mp02 != null && mp07 != null){
				mmp.setSupport02ZC2SinglePass(true);
			}
			
			mmp.setOdds_03ZC2( mp03 == null ? "0,0,0,0,0,0,0,0" : mp03.getOdds());
			mmp.setOptions_03ZC2( mp03 == null ? PlayType.getOptionByPlayId(Constants.PLAY_03_ZC) : mp03.getOptions());
			mmp.setPriorOdds_03ZC2( mp03 == null ? "0,0,0,0,0,0,0,0"  : mp03.getPriorOdds());
			if(mp03 == null){
				notOpenSalePlays.add(MixPlayType.JQS.name());
			} else {
				if(mp08 != null){
					mmp.setSupport03ZC2SinglePass(true);
				}
			}
				
			mmp.setOdds_04ZC2( mp04 == null ? "0,0,0,0,0,0,0,0,0" : mp04.getOdds());
			mmp.setOptions_04ZC2( mp04 == null ? PlayType.getOptionByPlayId(Constants.PLAY_04_ZC) : mp04.getOptions());
			mmp.setPriorOdds_04ZC2( mp04 == null ? "0,0,0,0,0,0,0,0,0" : mp04.getPriorOdds());
			if(mp04 == null){
				notOpenSalePlays.add(MixPlayType.BQC.name());
			} else {
				if(mp09 != null){
					mmp.setSupport04ZC2SinglePass(true);
				}
			}
			
			if(!notOpenSalePlays.isEmpty()){
				mmp.setNotOpenSalePlays(notOpenSalePlays.toString().replace("[", "").replace("]", ""));
			}
			leagueNames.add(mmp.getLeagueName());
			results.add(mmp);
		}
		
		if(leagueNames != null && leagueNames.size()>0){
	        Map<String, String> colors = matchColorDao.listColorsByLeagueNames(leagueNames);
	        for(MatchPlay mp:results){
	        	mp.setColor(colors.get(mp.getLeagueName()));
	        }
		}
    	return results;
	}
    @Override
    @Transactional(readOnly = true)
    public List<MatchPlay> listFBOnSale(String playId) {
        List<Object[]> data;
        if(playId.indexOf("ZC_1") != -1){
        	data = fbMatchPlayDao.findByStatusForSinglePass(playId, EntityStatus.MATCH_ON_SALE); 
        } else {
        	data = fbMatchPlayDao.findByStatus(playId, EntityStatus.MATCH_ON_SALE);
        }
        List<MatchPlay> results = new ArrayList<MatchPlay>(data.size());
        Map<String,String> leagueNames = new HashMap<String, String>();//联赛名称集合
        Integer beforeCloseMinute=systemConfDao.findIntValueByKey(SystemConf.KEY_BEFORE_CLOSE_MINUTE);
    	List<LotteryOpenSalePO> openAndEndTimes=this.lotteryOpenSaleDao.findOpenAndEndTime();
        for (Object[] d : data) {
            MatchPlay mp = new MatchPlay();
            FBMatchPlayPO p = (FBMatchPlayPO) d[0];
           
            BeanUtils.copyProperties(d[1], mp);
            mp.setDefaultScore(((FBMatchPO)d[1]).getConcedePoints());
            leagueNames.put(mp.getLeagueName(), mp.getLeagueName());
            
            mp.setPlayId(p.getPlayId());
            mp.setOptions(p.getOptions());
            mp.setOdds(p.getOdds());
            mp.setPriorOdds(p.getPriorOdds());
            mp.setEntrustDeadline(makeEntrustDeadline(beforeCloseMinute,mp.getEntrustDeadline(),openAndEndTimes));
            results.add(mp);
        }
        
        Map<String, String> colors = new HashMap<String, String>();
        if(null != leagueNames && leagueNames.size() > 0) {
        	colors = matchColorDao.listColorsByLeagueNames(leagueNames.keySet());
        }
        for(MatchPlay mp:results){
        	mp.setColor(colors.get(mp.getLeagueName()));
        }

        return results;
    }
    
    @Override
    @Transactional(readOnly = true)
    public LinkedHashMap<String,MatchPlay> mapsFBOnSale(String playId, LinkedHashMap<Long ,String> leagueMatchIdsCodes) {
    	List<Object[]> data = fbMatchPlayDao.findByStatus(playId, EntityStatus.MATCH_ON_SALE);
    	LinkedHashMap<String,MatchPlay> results = new LinkedHashMap<String,MatchPlay>(data.size());
    	Integer beforeCloseMinute=systemConfDao.findIntValueByKey(SystemConf.KEY_BEFORE_CLOSE_MINUTE);
    	List<LotteryOpenSalePO> openAndEndTimes=this.lotteryOpenSaleDao.findOpenAndEndTime();
    	for (Object[] d : data) {
    		MatchPlay mp = new MatchPlay();
    		FBMatchPlayPO p = (FBMatchPlayPO) d[0];
    		BeanUtils.copyProperties(d[1], mp);
    		mp.setDefaultScore(((FBMatchPO)d[1]).getConcedePoints());
    		if(leagueMatchIdsCodes != null){
    			leagueMatchIdsCodes.put( mp.getId(), mp.getCode());
    		}
    		mp.setPlayId(p.getPlayId());
    		mp.setOptions(p.getOptions());
    		mp.setOdds(p.getOdds());
    		mp.setPriorOdds(p.getPriorOdds());
    		mp.setEntrustDeadline(makeEntrustDeadline(beforeCloseMinute,mp.getEntrustDeadline(),openAndEndTimes));
    		results.put(mp.getCode(),mp);
    	}
    	return results;
    }
    @Override
    @Transactional(readOnly = true)
    public LinkedHashMap<String,MatchPlay> mapsFBByTime(String playId, LinkedHashMap<Long ,String> leagueMatchIdsCodes,Date from,Date to) {
    	List<Object[]> data;
    	if(playId.indexOf("ZC_1") != -1){
    		data = fbMatchPlayDao.findByPlayingtimeForSinglePass(playId,from,to);
    	} else {
    		data = fbMatchPlayDao.findByPlayingtime(playId,from,to);
    	}
    	LinkedHashMap<String,MatchPlay> results = new LinkedHashMap<String,MatchPlay>(data.size());
    	Integer beforeCloseMinute=systemConfDao.findIntValueByKey(SystemConf.KEY_BEFORE_CLOSE_MINUTE);
    	List<LotteryOpenSalePO> openAndEndTimes=this.lotteryOpenSaleDao.findOpenAndEndTime();
    	for (Object[] d : data) {
    		MatchPlay mp = new MatchPlay();
    		FBMatchPlayPO p = (FBMatchPlayPO) d[0];
    		BeanUtils.copyProperties(d[1], mp);
    		mp.setDefaultScore(((FBMatchPO)d[1]).getConcedePoints());
    		if(leagueMatchIdsCodes != null){
    			leagueMatchIdsCodes.put( mp.getId(), mp.getCode());
    		}
    		mp.setPlayId(p.getPlayId());
    		mp.setOptions(p.getOptions());
    		mp.setOdds(p.getOdds());
    		mp.setPriorOdds(p.getPriorOdds());
    		mp.setEntrustDeadline(makeEntrustDeadline(beforeCloseMinute,mp.getEntrustDeadline(),openAndEndTimes));
    		results.put(mp.getCode(),mp);
    	}
    	return results;
    }

    @Override
    @Transactional(readOnly = true)
    public void listFBMatch(Paging paging, Date from, Date to) {
        List<FBMatchPO> data = fbMatchDao.find(paging, EntityStatus.MATCH_OVER, from, to);
        
        List<QTMatchPO> results = new ArrayList<QTMatchPO>(data.size());
		Map<Long, QTMatchPO> mrMap = new TreeMap<Long, QTMatchPO>(comparator);
		Map<String,String> leagueNames = new HashMap<String, String>();//联赛名称集合
        for (FBMatchPO po : data) {
            QTMatchPO m = new QTMatchPO();
            BeanUtils.copyProperties(po, m);
            
            results.add(m);
            mrMap.put(m.getId(), m);
            
            //将联赛名称去重保存
            if(leagueNames.get(m.getLeagueName())==null){
            	leagueNames.put(m.getLeagueName(), m.getLeagueName());
            }
        }
        
        //取得联赛对应颜色
        Set<String> keySet = leagueNames.keySet();
        if(null != keySet && !keySet.isEmpty()){
        	Map<String, String> colors = matchColorDao.listColorsByLeagueNames(keySet);
            for(QTMatchPO result:results){
            	result.setColor(colors.get(result.getLeagueName()));
            }
        }
        
        for (FBMatchPlayPO po : fbMatchPlayDao.findByMatchId(mrMap.keySet())) {
            QTMatchPO mr = mrMap.get(po.getMatchId());
            if (mr != null) {
                FBMatchPlay mp = new FBMatchPlay();
            	BeanUtils.copyProperties(po, mp);
            	mr.addPlay(mp);
            }
        };
        paging.setResults(results);
    }

    @Override
    @Transactional(readOnly = true)
    public LinkedHashMap<String,MatchPlay> mapsBBByTime(String playId, LinkedHashMap<Long,String> leagueMatchIdsCodes,Date from ,Date to) {
    	List<Object[]> data;
    	if(playId.indexOf("LC_1") != -1){
    		data = bbMatchPlayDao.findByPlayingtimeForSinglePass(playId, from, to);
    	} else {
    		data = bbMatchPlayDao.findByPlayingtime(playId, from, to);
    	}
    	LinkedHashMap<String,MatchPlay>  results = new LinkedHashMap<String,MatchPlay>(data.size());
    	mapsBB(leagueMatchIdsCodes, data, results);
    	return results;
    }

	private void mapsBB(LinkedHashMap<Long, String> leagueMatchIdsCodes,
			List<Object[]> data, LinkedHashMap<String, MatchPlay> results) {
		for (Object[] d : data) {
    		MatchPlay mp = new MatchPlay();
    		BBMatchPlayPO p = (BBMatchPlayPO) d[0];
    		BBMatchPO m = (BBMatchPO) d[1];
    		BeanUtils.copyProperties(m, mp);
    		if(leagueMatchIdsCodes != null){
    			leagueMatchIdsCodes.put(mp.getId(), mp.getCode());	
    		}
    		mp.setPlayId(p.getPlayId());
    		mp.setOptions(p.getOptions());
    		mp.setOdds(p.getOdds());
    		mp.setPriorOdds(p.getPriorOdds());
    		mp.setDefaultScore(p.getDefaultScore());
    		mp.setScore(m.getFinalScore());
    		results.put(mp.getCode(),mp);
    	}
	}
    @Override
    @Transactional(readOnly = true)
    public LinkedHashMap<String,MatchPlay> mapsBBOnSale(String playId, LinkedHashMap<Long,String> leagueMatchIdsCodes) {
    	List<Object[]> data = bbMatchPlayDao.findByStatus(playId, EntityStatus.MATCH_ON_SALE);
    	LinkedHashMap<String,MatchPlay>  results = new LinkedHashMap<String,MatchPlay>(data.size());
    	mapsBB(leagueMatchIdsCodes, data, results);
    	return results;
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<MatchPlay> listBBOnSale(String playId) {
        List<Object[]> data;
        if(playId.indexOf("LC_1") != -1){
        	data = bbMatchPlayDao.findByStatusForSinglePass(playId, EntityStatus.MATCH_ON_SALE);
        } else {
        	data = bbMatchPlayDao.findByStatus(playId, EntityStatus.MATCH_ON_SALE);
        }
        List<MatchPlay> results = new ArrayList<MatchPlay>(data.size());
        Map<String,String> leagueNames = new HashMap<String, String>();//联赛名称集合
        Integer beforeCloseMinute=systemConfDao.findIntValueByKey(SystemConf.KEY_BEFORE_CLOSE_MINUTE);
    	List<LotteryOpenSalePO> openAndEndTimes=this.lotteryOpenSaleDao.findOpenAndEndTime();
        for (Object[] d : data) {
            MatchPlay mp = new MatchPlay();
            BBMatchPlayPO p = (BBMatchPlayPO) d[0];
            BBMatchPO m = (BBMatchPO) d[1];
            
            BeanUtils.copyProperties(m, mp);
            leagueNames.put(mp.getLeagueName(), mp.getLeagueName()); 
            mp.setPlayId(p.getPlayId());
            mp.setOptions(p.getOptions());
            mp.setOdds(p.getOdds());
            mp.setPriorOdds(p.getPriorOdds());
            mp.setDefaultScore(p.getDefaultScore());
            mp.setEntrustDeadline(makeEntrustDeadline(beforeCloseMinute,mp.getEntrustDeadline(),openAndEndTimes));
            results.add(mp);
        }

        Map<String, String> colors = new HashMap<String, String>();
        if(leagueNames != null && leagueNames.size() > 0) {
        	colors = matchColorDao.listColorsByLeagueNames(leagueNames.keySet());
        }
        for(MatchPlay mp:results){
        	mp.setColor(colors.get(mp.getLeagueName()));
        }
        
        return results;
    }

    @Override
    @Transactional(readOnly = true)
    public void listBBMatch(Paging paging, Date from, Date to) {
        List<BBMatchPO> data = bbMatchDao.find(paging, EntityStatus.MATCH_OVER, from, to);
        List<BBMatchResult> results = new ArrayList<BBMatchResult>(data.size());
		Map<Long, BBMatchResult> mrMap = new TreeMap<Long, BBMatchResult>(comparator);
		Map<String,String> leagueNames = new HashMap<String, String>();//联赛名称集合
		
        for (BBMatchPO po : data) {
            BBMatchResult m = new BBMatchResult();
            BeanUtils.copyProperties(po, m);
            
            results.add(m);
            mrMap.put(m.getId(), m);
            
          //将联赛名称去重保存
            if(leagueNames.get(m.getLeagueName())==null){
            	leagueNames.put(m.getLeagueName(), m.getLeagueName());
            }
        }
        
        //取得联赛对应颜色
        Set<String> keySet = leagueNames.keySet();
        if(null != keySet && !keySet.isEmpty()){
        	Map<String, String> colors = matchColorDao.listColorsByLeagueNames(leagueNames.keySet());
        	for(BBMatchResult result:results){
        		result.setColor(colors.get(result.getLeagueName()));
        	}
        }
        
        for (BBMatchPlayPO po : bbMatchPlayDao.findByMatchId(mrMap.keySet())) {
            BBMatchResult mr = mrMap.get(po.getMatchId());
            if (mr != null) {
                BBMatchPlay mp = new BBMatchPlay();
                BeanUtils.copyProperties(po, mp);
                mr.addPlay(mp);
            }
        };
        paging.setResults(new ArrayList<BBMatchResult>(mrMap.values()));
    }

	@Override
	@Transactional(readOnly = true)
	public List<Object[]> queryFBMatchCountByStatus(int status) {
		return fbMatchDao.queryFBMatchCountByStatus(status);
	}

	@Override
	@Transactional(readOnly = true)
	public List<String> queryFBLeaguesByStatus(int status) {
		return fbMatchDao.queryFBLeaguesByStatus(status);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Object[]> queryBBMatchCountByStatus(int status) {
		return bbMatchDao.queryBBMatchCountByStatus(status);
	}

	@Override
	@Transactional(readOnly = true)
	public List<String> queryBBLeaguesByStatus(int status) {
		return bbMatchDao.queryBBLeaguesByStatus(status);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Object[]> queryFBMatchByPlayIdAndStatusAndLeagueName(
			String playId, int status, List<String> leagues,Integer firstResult, Integer pageMaxResult) {
		return fbMatchPlayDao.findMatchByPlayIdAndStatusAndLeagueName(playId, status, leagues,firstResult,pageMaxResult);
	}


	@Override
	@Transactional(readOnly = true)
	public List<Object[]> queryBBMatchByPlayIdAndStatusAndLeagueName(
			String playId, int status, List<String> leagues,Integer firstResult,Integer pageMaxResult) {
		return bbMatchPlayDao.findMatchByPlayIdAndStatusAndLeagueName(playId, status, leagues,firstResult,pageMaxResult);
	}

	@Override
	@Transactional(readOnly = true)
	public Map<String, List<Object[]>> listLotteryInfo() {
		List<Object[]> lotteryInfoFBList = fbMatchDao.queryLotteryInfoForFB();
		List<Object[]> lotteryInfoBBList = bbMatchDao.queryLotteryInfoForBB();
		Map<String, List<Object[]>> m = new HashMap<String, List<Object[]>>();
		m.put(Constants.JCZQ, lotteryInfoFBList);
		m.put(Constants.JCLQ, lotteryInfoBBList);
		return m;
	}
	//------------------------------  混合赛事查询 start---------------------------------
	@Transactional(readOnly = true)
    public LinkedHashMap<String,MatchPlay> mapsFBOnSale(String playId, 
    		LinkedHashMap<Long ,String> leagueMatchIdsCodes, List<String> leagueList, Integer firstResult,Integer pageMaxResult,Date minMatchPlayingTime) {
		Integer beforeCloseMinute = computeBeforeCloseMinute();
		List<LotteryOpenSalePO> openAndEndTimes=this.lotteryOpenSaleDao.findOpenAndEndTime();
		if (null != openAndEndTimes
				&& !openAndEndTimes.isEmpty()
				) {

			List<Object[]> data = fbMatchPlayDao.findByStatusAndAfterDeadLine(playId, EntityStatus.MATCH_ON_SALE,leagueList,firstResult,pageMaxResult,minMatchPlayingTime);
    	LinkedHashMap<String,MatchPlay> results = new LinkedHashMap<String,MatchPlay>(data.size());
    	
    	
	    	for (Object[] d : data) {
	    		MatchPlay mp = new MatchPlay();
	    		FBMatchPlayPO fbMatchPlayPO = (FBMatchPlayPO) d[0];
	    		FBMatchPO fbMatchPO=(FBMatchPO) d[1];
	    		BeanUtils.copyProperties(fbMatchPO, mp);
	    		mp.setDefaultScore(fbMatchPO.getConcedePoints());
	    		if(leagueMatchIdsCodes != null){
	    			leagueMatchIdsCodes.put( mp.getId(), mp.getCode());
	    		}
	    		
	    		mp.setPlayId(fbMatchPlayPO.getPlayId());
	    		mp.setOptions(fbMatchPlayPO.getOptions());
	    		mp.setOdds(fbMatchPlayPO.getOdds());
	    		mp.setPriorOdds(fbMatchPlayPO.getPriorOdds());
	    		
	    		mp.setEntrustDeadline(makeEntrustDeadline(beforeCloseMinute,mp.getPlayingTime(),openAndEndTimes));
	    		results.put(mp.getCode(),mp);
	    	}
    	return results;
			

		}
		return null;
		
    }
	/**
	 * 计算比赛的新截止时间；
	 * 
	 * @param beforeCloseMinute 系统设置的提前截止分钟数
	 * @param playingTime  原比赛开始时间
	 * @param openAndEndTimes 今日投注开始和结束时间以及明日投注开始时间
	 * @return
	 */
	private Date makeEntrustDeadline(
			Integer beforeCloseMinute, Date playingTime, List<LotteryOpenSalePO> openAndEndTimes) {
		
		Date result=null;
		if(null!=beforeCloseMinute&&null!=playingTime&&null!=openAndEndTimes){
			OpenSaleTime openSaleTime = OpenSaleTimeUtil.transferToOpenSaleTime(openAndEndTimes, playingTime);//根据比赛的开赛时间计算
			result = makeEntrustTime(beforeCloseMinute, playingTime, result,openSaleTime);
		}
		return result;
	}
	
	private Date makeMachineEntrustDeadline(
			Integer beforeCloseMinute, Date playingTime, List<LotteryOpenSalePO> openAndEndTimes) {
		Date result=null;
		if(null!=beforeCloseMinute&&null!=playingTime&&null!=openAndEndTimes){
			OpenSaleTime openSaleTime = OpenSaleTimeUtil.transferToMachineOpenSaleTime(openAndEndTimes, playingTime);//根据比赛的开赛时间计算
			result = makeEntrustTime(beforeCloseMinute, playingTime, result,openSaleTime);
		}
		return result;
	}

	private Date makeEntrustTime(Integer beforeCloseMinute, Date playingTime,
			Date result, OpenSaleTime openSaleTime) {
		Date newPlayingTime=DateUtils.addMinutes(playingTime, -1*beforeCloseMinute);
		if(newPlayingTime.compareTo(openSaleTime.getTodayOpenDateTime())<=0){
			if(newPlayingTime.compareTo(openSaleTime.getYesterdayEndDateTime())<=0){//比赛开赛时间提前1小时后，也早于昨日结束时间
				result=newPlayingTime;
			}else{
				result=openSaleTime.getYesterdayEndDateTime();//该比赛截止时间应为昨天最晚投注时间
			}
		}
		else if(newPlayingTime.compareTo(openSaleTime.getTodayOpenDateTime())>0
				&&newPlayingTime.compareTo(openSaleTime.getTodayEndDateTime())<0){//比赛开赛时间提前1小时后，在今天投注开始和结束时间之间
			result=newPlayingTime;
		}
		else if(newPlayingTime.compareTo(openSaleTime.getTodayEndDateTime())>=0
				&&newPlayingTime.compareTo(openSaleTime.getTomorrowOpenDateTime())<=0){//比赛开赛时间提前1小时后，晚于今天结束时间，早于明天开始时间
			result=openSaleTime.getTodayEndDateTime();
		}
		else {
			result=newPlayingTime;
		}
		return result;
	}

	private Date makeTodayBetTime(Date lotteryBetEndTime) {
		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTime(lotteryBetEndTime);
		Date todayBetEndTime=new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(todayBetEndTime);
		calendar.set(Calendar.HOUR, calendar2.get(Calendar.HOUR));
		calendar.set(Calendar.MINUTE, calendar2.get(Calendar.MINUTE));
		calendar.set(Calendar.SECOND, calendar2.get(Calendar.SECOND));
		todayBetEndTime=calendar.getTime();
		return todayBetEndTime;
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<Map<String, String>> getFBMatchResultMaps(
			List<Object[]> matchAndPlayObjs) {
		List<Map<String,String>> matches = new ArrayList<Map<String, String>>();
		Integer beforeCloseMinute = computeBeforeCloseMinute();
		List<LotteryOpenSalePO> openAndEndTimes=this.lotteryOpenSaleDao.findOpenAndEndTime();
		for (Object[] objects : matchAndPlayObjs) {
			Map<String,String> resultMap = new HashMap<String, String>();
			FBMatchPO matchPO = (FBMatchPO) objects[0];
			
			resultMap.put(Constants.MATCH_ID, String.valueOf(matchPO.getId()));
			resultMap.put(Constants.MATCH_CODE, matchPO.getCode());
			resultMap.put(Constants.LEAGUE_SHORT_NAME, matchPO.getLeagueName());
			resultMap.put(Constants.LEAGUE_COLOR, initLeagueColor.getColorMap()
					.get(matchPO.getLongLeagueName()));
			Date newDeadLine=this.makeEntrustDeadline(beforeCloseMinute, matchPO.getPlayingTime(), openAndEndTimes);
			resultMap.put(Constants.MATCH_OFF_TIME, formatEntrustDeadline(newDeadLine));//避免客户端改动，将比赛截止时间赋值为“大V彩销售截止时间”
			resultMap.put(Constants.ENTRUST_DEADLINE, formatEntrustDeadline(newDeadLine));
			resultMap.put(Constants.HOST_NAME, matchPO.getHomeTeamName());
			resultMap.put(Constants.GUEST_NAME, matchPO.getGuestTeamName());
			resultMap.put(Constants.CONCEDE_POINTS, String.valueOf(matchPO.getConcedePoints()));
			System.out.println(matchPO.getOfftime().toString());
			
			FBMatchPlayPO matchPlayPO = (FBMatchPlayPO) objects[1];
			resultMap.put(Constants.ODDS, matchPlayPO.getOdds());
			resultMap.put(Constants.OPTIONS, matchPlayPO.getOptions());
			
			matches.add(resultMap);
		}
		return matches;
	}

	@Override
	public String formatEntrustDeadline(Date entrustDeadline) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SS");
		return sdf.format( entrustDeadline);
	}

	@Override
	@Transactional(readOnly = true)
	public List<MixMatchPlay> listMixFBOnSaleForPageAndLeagueName(
			String playId, List<String> leagueList, Integer firstResult,Integer pageMaxResult,Date minMatchPlayingTime) {
		List<MixMatchPlay> results = new ArrayList<MixMatchPlay>(); 
    	if(StringUtils.isBlank(playId) || !Constants.PLAY_555_ZCFH_2.equals(playId) ){
    		return results;
    	}
    	HashSet<String> leagueNames = new HashSet<String>();
    	 
    	//先分页查询所有可投注的比赛id和code，形成matchIdAndCodes
    	LinkedHashMap<Long,String> matchIdAndCodes=makeFBMatchIdAndCodes(leagueList,firstResult,pageMaxResult,minMatchPlayingTime);
    	//再根据比赛id查到所有可投注的玩法列表，形成玩法id为键，map<比赛id,MatchPlay>为值的map
    	Map<String,Map<Long,MatchPlay>> playIdAndMapOfMatchIdAndMatchPlay=makeFBPlayIdAndMapOfMatchIdAndMatchPlay(matchIdAndCodes);
    	
    	Map<Long, MatchPlay> mps01 = mapsOnSale(Constants.PLAY_01_ZC_2, playIdAndMapOfMatchIdAndMatchPlay);
    	Map<Long, MatchPlay> mps02 = mapsOnSale(Constants.PLAY_02_ZC_2, playIdAndMapOfMatchIdAndMatchPlay);
    	Map<Long, MatchPlay> mps03 = mapsOnSale(Constants.PLAY_03_ZC_2, playIdAndMapOfMatchIdAndMatchPlay);
    	Map<Long, MatchPlay> mps04 = mapsOnSale(Constants.PLAY_04_ZC_2, playIdAndMapOfMatchIdAndMatchPlay);
    	Map<Long, MatchPlay> mps05 = mapsOnSale(Constants.PLAY_80_ZC_2, playIdAndMapOfMatchIdAndMatchPlay);
    	Object[] matchIds = matchIdAndCodes.keySet().toArray();
		Arrays.sort(matchIds);
		
		for(Object mid:matchIds){
			List<String> notOpenSalePlays = new ArrayList<String>();
			MixMatchPlay mmp = new MixMatchPlay();
			Long matchId = (Long) mid;
			MatchPlay mp = mps02.get(matchId) == null ? null : (MatchPlay)mps02.get(matchId);
			if(mp == null){
				MixMatchPlay mpCopy = new MixMatchPlay();
				if(mps01.get(matchId) != null){
					BeanUtils.copyProperties(mps01.get(matchId), mpCopy);
				}else if(mps03.get(matchId) != null){
					BeanUtils.copyProperties(mps03.get(matchId), mpCopy);
				}else if(mps04.get(matchId) != null){
					BeanUtils.copyProperties(mps04.get(matchId), mpCopy);
				}else{
					continue;
				}
				mp = mpCopy;
				mp.setOdds("0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0");
				mp.setOptions(PlayType.getOptionByPlayId(Constants.PLAY_02_ZC));
				mp.setPriorOdds("0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0");
				mp.setDefaultScore(0);
				notOpenSalePlays.add(MixPlayType.BF.name());
			}
			BeanUtils.copyProperties(mp, mmp);
			MatchPlay mp01 = mps01.get(matchId);
			MatchPlay mp03 = mps03.get(matchId);
			MatchPlay mp04 = mps04.get(matchId);
			MatchPlay mp05 = mps05.get(matchId);
			
			mmp.setOdds_80ZC2( mp05 == null ? "0,0,0" : mp05.getOdds());
			mmp.setOptions_80ZC2( mp05 == null ? PlayType.getOptionByPlayId(Constants.PLAY_01_ZC) : mp05.getOptions());
			mmp.setPriorOdds_80ZC2( mp05 == null ? "0,0,0" : mp05.getPriorOdds());
			mmp.setDefaultScore(0);
			if(mp05 == null){
				notOpenSalePlays.add(MixPlayType.BRQSPF.name());
			}
			
			mmp.setOdds_01ZC2( mp01 == null ? "0,0,0" : mp01.getOdds());
			mmp.setOptions_01ZC2( mp01 == null ? PlayType.getOptionByPlayId(Constants.PLAY_01_ZC) : mp01.getOptions());
			mmp.setPriorOdds_01ZC2( mp01 == null ? "0,0,0" : mp01.getPriorOdds());
			mmp.setDefaultScore( mp01 == null ? 0 : mp01.getDefaultScore());
			if(mp01 == null){
				notOpenSalePlays.add(MixPlayType.SPF.name());
			}
			
			mmp.setOdds_03ZC2( mp03 == null ? "0,0,0,0,0,0,0,0" : mp03.getOdds());
			mmp.setOptions_03ZC2( mp03 == null ? PlayType.getOptionByPlayId(Constants.PLAY_03_ZC) : mp03.getOptions());
			mmp.setPriorOdds_03ZC2( mp03 == null ? "0,0,0,0,0,0,0,0"  : mp03.getPriorOdds());
			if(mp03 == null){
				notOpenSalePlays.add(MixPlayType.JQS.name());
			}
				
			mmp.setOdds_04ZC2( mp04 == null ? "0,0,0,0,0,0,0,0,0" : mp04.getOdds());
			mmp.setOptions_04ZC2( mp04 == null ? PlayType.getOptionByPlayId(Constants.PLAY_04_ZC) : mp04.getOptions());
			mmp.setPriorOdds_04ZC2( mp04 == null ? "0,0,0,0,0,0,0,0,0" : mp04.getPriorOdds());
			if(mp04 == null){
				notOpenSalePlays.add(MixPlayType.BQC.name());
			}
			if(!notOpenSalePlays.isEmpty()){
				mmp.setNotOpenSalePlays(notOpenSalePlays.toString().replace("[", "").replace("]", ""));
			}
			leagueNames.add(mmp.getLeagueName());
			results.add(mmp);
		}
		
		if(leagueNames != null && leagueNames.size()>0){
	        Map<String, String> colors = matchColorDao.listColorsByLeagueNames(leagueNames);
	        for(MatchPlay mp:results){
	        	mp.setColor(colors.get(mp.getLeagueName()));
	        }
		}
		sortMixMatchPlayList(results);
		
    	return results; 
	}

	private void sortMixMatchPlayList(List<MixMatchPlay> results) {
		 MatchPlayComparator<MixMatchPlay> matchPlayerComparator = new MatchPlayComparator<MixMatchPlay>();
		Collections.sort(results, matchPlayerComparator);
	}
	private Map<Long, MatchPlay> mapsOnSale(String playId,
			Map<String, Map<Long, MatchPlay>> playIdAndMapOfMatchIdAndMatchPlay) {
		if(null!=playIdAndMapOfMatchIdAndMatchPlay&&!playIdAndMapOfMatchIdAndMatchPlay.isEmpty()&&playIdAndMapOfMatchIdAndMatchPlay.containsKey(playId)){
			return playIdAndMapOfMatchIdAndMatchPlay.get(playId);
		}
		return new HashMap<Long, MatchPlay>();
	}

	/**
	 * 再根据比赛id查到所有可投注的玩法列表，形成玩法id为键，map<比赛id,MatchPlay>为值的map
	 * @param matchIdAndCodes
	 * @return
	 */
	private Map<String, Map<Long, MatchPlay>> makeFBPlayIdAndMapOfMatchIdAndMatchPlay(
			LinkedHashMap<Long, String> matchIdAndCodes) {
		Map<String, Map<Long, MatchPlay>> result=null;
		
		Integer beforeCloseMinute = computeBeforeCloseMinute();
		List<LotteryOpenSalePO> openAndEndTimes=this.lotteryOpenSaleDao.findOpenAndEndTime();
		Set<Long> matchIdSet = matchIdAndCodes.keySet();
		List<Object[]> matchAndMatchPlays=this.fbMatchPlayDao.findMatchAndMatchPlayByMatchId(matchIdSet);
		if(null!=openAndEndTimes&&!openAndEndTimes.isEmpty()){
			if(null!=matchAndMatchPlays&&!matchAndMatchPlays.isEmpty()){
				result=new HashMap<String, Map<Long, MatchPlay>>();
				for(Object[] matchAndMatchPlay:matchAndMatchPlays){
					FBMatchPlayPO fbMatchPlayPO = (FBMatchPlayPO) matchAndMatchPlay[0];
		    		FBMatchPO fbMatchPO=(FBMatchPO) matchAndMatchPlay[1];
		    		String playId=fbMatchPlayPO.getPlayId();
		    		Long matchId=fbMatchPO.getId();
					MatchPlay matchPlay=new MatchPlay();
					BeanUtils.copyProperties(fbMatchPO, matchPlay);
					matchPlay.setDefaultScore(fbMatchPO.getConcedePoints());
		    		
					matchPlay.setPlayId(fbMatchPlayPO.getPlayId());
					matchPlay.setOptions(fbMatchPlayPO.getOptions());
					matchPlay.setOdds(fbMatchPlayPO.getOdds());
					matchPlay.setPriorOdds(fbMatchPlayPO.getPriorOdds());
		    		
					matchPlay.setEntrustDeadline(makeEntrustDeadline(beforeCloseMinute,matchPlay.getPlayingTime(),openAndEndTimes));
					Map<Long, MatchPlay> matchIdAndMatchPlay=null;
		    		if(result.containsKey(playId)){
		    			matchIdAndMatchPlay = result.get(playId);
		    			if(null!=matchIdAndMatchPlay){
		    				
							matchIdAndMatchPlay.put(matchId, matchPlay);
		    			}
		    			
		    		}
		    		else{
		    			matchIdAndMatchPlay=new HashMap<Long,MatchPlay>();
		    			matchIdAndMatchPlay.put(matchId, matchPlay);
						result.put(playId, matchIdAndMatchPlay);
		    		}
				}
			}
		}
		
		return result;
	}

	private LinkedHashMap<Long, String> makeFBMatchIdAndCodes(
			List<String> leagueList, Integer firstResult,
			Integer pageMaxResult, Date minMatchPlayingTime) {
		LinkedHashMap<Long, String> matchIdAndCodes = new LinkedHashMap<Long,String>(); 
		List<Object[]> list=this.fbMatchDao.queryIdAndCodesByStatusAndLeagueListAndAfterDeadlineWithPage(EntityStatus.MATCH_ON_SALE, leagueList,minMatchPlayingTime,firstResult,pageMaxResult);
		if(null!=list&&!list.isEmpty()){
			for(Object[] idAndCode:list){
				Long id=(Long) idAndCode[0];
				String code=(String) idAndCode[1];
				matchIdAndCodes.put(id, code);
			}
		}
		return matchIdAndCodes;
	}

	@Transactional(readOnly = true)
    public LinkedHashMap<String,MatchPlay> mapsBBOnSale(String playId,
    		LinkedHashMap<Long,String> leagueMatchIdsCodes, List<String> leagueList, Integer firstResult,Integer pageMaxResult,Date minMatchPlayingTime) {
		Integer beforeCloseMinute = computeBeforeCloseMinute();
		List<LotteryOpenSalePO> openAndEndTimes=this.lotteryOpenSaleDao.findOpenAndEndTime();
    	List<Object[]> data = bbMatchPlayDao.findByStatusAndAfterDeadline(playId, EntityStatus.MATCH_ON_SALE,leagueList,firstResult,pageMaxResult,minMatchPlayingTime);
    	LinkedHashMap<String,MatchPlay>  results = new LinkedHashMap<String,MatchPlay>(data.size());
    	
    	for (Object[] d : data) {
    		MatchPlay mp = new MatchPlay();
    		BBMatchPlayPO p = (BBMatchPlayPO) d[0];
    		BBMatchPO m = (BBMatchPO) d[1];
    		
    		BeanUtils.copyProperties(m, mp);
    		if(leagueMatchIdsCodes != null){
    			leagueMatchIdsCodes.put(mp.getId(), mp.getCode());	
    		}
    		
    		mp.setPlayId(p.getPlayId());
    		mp.setOptions(p.getOptions());
    		mp.setOdds(p.getOdds());
    		mp.setPriorOdds(p.getPriorOdds());
    		mp.setDefaultScore(p.getDefaultScore());
    		mp.setEntrustDeadline(makeEntrustDeadline(beforeCloseMinute,mp.getPlayingTime(),openAndEndTimes));
    		results.put(mp.getCode(),mp);
    	}
    	
    	return results;
    }
	
	@Transactional(readOnly = true)
	public LinkedHashMap<String,MatchPlay> mapsSingalBBOnSale(String playId,
			LinkedHashMap<Long,String> leagueMatchIdsCodes, List<String> leagueList, Integer firstResult,Integer pageMaxResult, Date minMatchPlayingTime) {
		Integer beforeCloseMinute = computeBeforeCloseMinute();
		List<LotteryOpenSalePO> openAndEndTimes=this.lotteryOpenSaleDao.findOpenAndEndTime();
		List<Object[]> data = bbMatchPlayDao.findSignalsByStatusAndAfterDeadline(playId, EntityStatus.MATCH_ON_SALE,leagueList,firstResult,pageMaxResult,minMatchPlayingTime);
		LinkedHashMap<String,MatchPlay>  results = new LinkedHashMap<String,MatchPlay>(data.size());
		
		for (Object[] d : data) {
			MatchPlay mp = new MatchPlay();
			BBMatchPlayPO p = (BBMatchPlayPO) d[0];
			BBMatchPO m = (BBMatchPO) d[1];
			
			BeanUtils.copyProperties(m, mp);
			if(leagueMatchIdsCodes != null){
				leagueMatchIdsCodes.put(mp.getId(), mp.getCode());	
			}
			
			mp.setPlayId(p.getPlayId());
			mp.setOptions(p.getOptions());
			mp.setOdds(p.getOdds());
			mp.setPriorOdds(p.getPriorOdds());
			mp.setDefaultScore(p.getDefaultScore());
			mp.setEntrustDeadline(makeEntrustDeadline(beforeCloseMinute,mp.getPlayingTime(),openAndEndTimes));
			results.put(mp.getCode(),mp);
		}
		
		return results;
	}
	@Override
	@Transactional(readOnly = true)
	public List<MixMatchPlay> listMixBBOnSaleForPageAndLeagueName(
			String playId, List<String> leagueList, Integer firstResult,Integer pageMaxResult,Date minMatchPlayingTime) {
		List<MixMatchPlay> results = new ArrayList<MixMatchPlay>(); 
    	if(StringUtils.isBlank(playId) || !Constants.PLAY_666_LCFH_2.equals(playId)){
    		return results;
    	}
    	//先分页查询所有可投注的比赛id和code，形成matchIdAndCodes
    	LinkedHashMap<Long,String> matchIdAndCodes=makeBBMatchIdAndCodes(leagueList,firstResult,pageMaxResult,minMatchPlayingTime);
    	//再根据比赛id查到所有可投注的玩法列表，形成玩法id为键，map<比赛id,MatchPlay>为值的map
    	Map<String,Map<Long,MatchPlay>> playIdAndMapOfMatchIdAndMatchPlay=makeBBPlayIdAndMapOfMatchIdAndMatchPlay(matchIdAndCodes);
		Map<Long,MatchPlay> mps01 = mapsOnSale(Constants.PLAY_06_LC_2, playIdAndMapOfMatchIdAndMatchPlay);
		Map<Long,MatchPlay> mps02 = mapsOnSale(Constants.PLAY_07_LC_2, playIdAndMapOfMatchIdAndMatchPlay);
		Map<Long,MatchPlay> mps03 = mapsOnSale(Constants.PLAY_08_LC_2, playIdAndMapOfMatchIdAndMatchPlay);
		Map<Long,MatchPlay> mps04 = mapsOnSale(Constants.PLAY_09_LC_2, playIdAndMapOfMatchIdAndMatchPlay);
		Object[] matchIds = matchIdAndCodes.keySet().toArray();
		Arrays.sort(matchIds);
		
		for(Object mid:matchIds){
			List<String> notOpenSalePlays = new ArrayList<String>();
			MixMatchPlay mmp = new MixMatchPlay();
			Long matchId = (Long) mid;
			MatchPlay mp = mps02.get(matchId) == null ? null : (MatchPlay)mps02.get(matchId);
			if(mp == null){
				MixMatchPlay mpCopy = new MixMatchPlay();
				if(mps01.get(matchId) != null){
					BeanUtils.copyProperties(mps01.get(matchId), mpCopy);
				}else if(mps03.get(matchId) != null){
					BeanUtils.copyProperties(mps03.get(matchId), mpCopy);
				}else if(mps04.get(matchId) != null){
					BeanUtils.copyProperties(mps04.get(matchId), mpCopy);
				}else{
					continue;
				}
				mp = mpCopy;
				mp.setOdds("0,0");
				mp.setOptions(PlayType.getOptionByPlayId(Constants.PLAY_07_LC));
				mp.setPriorOdds(null);
				mp.setDefaultScore(0);
				notOpenSalePlays.add(MixPlayType.SF.name());
			}
			BeanUtils.copyProperties(mp, mmp);
			MatchPlay mp06 = mps01.get(matchId);
			MatchPlay mp08 = mps03.get(matchId);
			MatchPlay mp09 = mps04.get(matchId);
			
			mmp.setOdds_06LC2( mp06 == null ? "0,0" : mp06.getOdds());
			mmp.setOptions_06LC2( mp06 == null ? PlayType.getOptionByPlayId(Constants.PLAY_06_LC) : mp06.getOptions());
			mmp.setPriorOdds_06LC2( mp06 == null ? null : mp06.getPriorOdds());
			mmp.setDefaultScore( mp06 == null ? 0 : mp06.getDefaultScore());
			if(mp06 == null){
				notOpenSalePlays.add(MixPlayType.RFSF.name());
			}
			
			mmp.setOdds_08LC2( mp08 == null ? "0,0,0,0,0,0,0,0,0,0,0,0" : mp08.getOdds());
			mmp.setOptions_08LC2( mp08 == null ? PlayType.getOptionByPlayId(Constants.PLAY_08_LC) : mp08.getOptions());
			mmp.setPriorOdds_08LC2( mp08 == null ? null : mp08.getPriorOdds());
			if(mp08 == null){
				notOpenSalePlays.add(MixPlayType.FC.name());
			}
				
			mmp.setOdds_09LC2( mp09 == null ? "0,0" : mp09.getOdds());
			mmp.setOptions_09LC2( mp09 == null ? PlayType.getOptionByPlayId(Constants.PLAY_09_LC) : mp09.getOptions());
			mmp.setPriorOdds_09LC2( mp09 == null ? null : mp09.getPriorOdds());
			mmp.setDefaultScore_09LC2( mp09 == null ? 1 : mp09.getDefaultScore());
			if(mp09 == null){
				notOpenSalePlays.add(MixPlayType.DXF.name());
			}
			if(!notOpenSalePlays.isEmpty()){
				mmp.setNotOpenSalePlays(notOpenSalePlays.toString().replace("[", "").replace("]", ""));
			}
			results.add(mmp);
		}
		sortMixMatchPlayList(results);
    	return results;
	}
	//------------------------------  混合赛事查询  end---------------------------------



	private Map<String, Map<Long, MatchPlay>> makeBBPlayIdAndMapOfMatchIdAndMatchPlay(
			LinkedHashMap<Long, String> matchIdAndCodes) {
		Map<String, Map<Long, MatchPlay>> result=null;
		
		Integer beforeCloseMinute = computeBeforeCloseMinute();
		List<LotteryOpenSalePO> openAndEndTimes=this.lotteryOpenSaleDao.findOpenAndEndTime();
		Set<Long> matchIdSet = matchIdAndCodes.keySet();
		List<Object[]> matchAndMatchPlays=this.bbMatchPlayDao.findMatchAndMatchPlayByMatchId(matchIdSet);
		if(null!=openAndEndTimes&&!openAndEndTimes.isEmpty()){
			if(null!=matchAndMatchPlays&&!matchAndMatchPlays.isEmpty()){
				result=new HashMap<String, Map<Long, MatchPlay>>();
				for(Object[] matchAndMatchPlay:matchAndMatchPlays){
					BBMatchPlayPO bbMatchPlayPO = (BBMatchPlayPO) matchAndMatchPlay[0];
		    		BBMatchPO bbMatchPO=(BBMatchPO) matchAndMatchPlay[1];
		    		String playId=bbMatchPlayPO.getPlayId();
		    		Long matchId=bbMatchPO.getId();
					MatchPlay matchPlay=new MatchPlay();
					BeanUtils.copyProperties(bbMatchPO, matchPlay);
					matchPlay.setDefaultScore(bbMatchPlayPO.getDefaultScore());
		    		
					matchPlay.setPlayId(bbMatchPlayPO.getPlayId());
					matchPlay.setOptions(bbMatchPlayPO.getOptions());
					matchPlay.setOdds(bbMatchPlayPO.getOdds());
					matchPlay.setPriorOdds(bbMatchPlayPO.getPriorOdds());
		    		
					matchPlay.setEntrustDeadline(makeEntrustDeadline(beforeCloseMinute,matchPlay.getPlayingTime(),openAndEndTimes));
					Map<Long, MatchPlay> matchIdAndMatchPlay=null;
		    		if(result.containsKey(playId)){
		    			matchIdAndMatchPlay = result.get(playId);
		    			if(null!=matchIdAndMatchPlay){
		    				
							matchIdAndMatchPlay.put(matchId, matchPlay);
		    			}
		    			
		    		}
		    		else{
		    			matchIdAndMatchPlay=new HashMap<Long,MatchPlay>();
		    			matchIdAndMatchPlay.put(matchId, matchPlay);
						result.put(playId, matchIdAndMatchPlay);
		    		}
				}
			}
		}
		
		return result;
	}

	private LinkedHashMap<Long, String> makeBBMatchIdAndCodes(
			List<String> leagueList, Integer firstResult,
			Integer pageMaxResult, Date minMatchPlayingTime) {
		LinkedHashMap<Long, String> matchIdAndCodes = new LinkedHashMap<Long,String>(); 
		List<Object[]> list=this.bbMatchDao.queryIdAndCodesByStatusAndLeagueListAndAfterDeadlineWithPage(EntityStatus.MATCH_ON_SALE, leagueList,minMatchPlayingTime,firstResult,pageMaxResult);
		if(null!=list&&!list.isEmpty()){
			for(Object[] idAndCode:list){
				Long id=(Long) idAndCode[0];
				String code=(String) idAndCode[1];
				matchIdAndCodes.put(id, code);
			}
		}
		return matchIdAndCodes;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Object[]> queryCTFBMatchCountByStatus(int status) {
		return ctfbMatchDao.queryCTFBMatchCountByStatus(status);
	}

	@Override
	@Transactional(readOnly = true)
	public List<String> queryCTFBLeaguesByStatus(int status) {
		return ctfbMatchDao.queryCTFBLeaguesByStatus(status);
	}
	
	//根据赛事Id和玩法Id查询篮球赛事玩法
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<BBMatchPlayPO> findBBMatchPlayPOByMatchIdAndPlayIds(Long matchId, Collection<String> playIds) {
		if(matchId == null || playIds == null || playIds.size() < 1){
			return Collections.EMPTY_LIST;
		}
		return bbMatchPlayDao.findByMatchIdAndPlayIds(matchId, playIds);
	}

	@Transactional
	@Override
	public Float findByMatchIdAndPlayId(String matchId, String playId) {
		return bbMatchPlayDao.findByMatchIdAndPlayId(matchId, playId);
	}

	@Transactional
	@Override
	public void updateScoreToFBMatchPreset(long lcMatchId, String scorePreset, String halfScorePreset) {
		if(StringUtils.isNotBlank(halfScorePreset) && 
				StringUtils.isNotBlank(scorePreset) && lcMatchId > 0) {
			fbMatchDao.updatePresetScore(lcMatchId, scorePreset, halfScorePreset);
		}
	}
	
	@Transactional
	@Override
	public void updateScoreToBBMatchPreset(long lcMatchId, String finalScorePreset) {
		if(StringUtils.isNotBlank(finalScorePreset) && lcMatchId > 0) {
			bbMatchDao.updatePresetScore(lcMatchId, finalScorePreset);
		}
	}

	@Transactional
	@Override
	public List<SingalMatchPlay> listSingalFBOnSaleForPageAndLeagueName(
			String playId, List<String> leagueList, int firstResult, int pageMaxResult,Date minMatchPlayingTime) {

		List<SingalMatchPlay> results = new ArrayList<SingalMatchPlay>(); 
    	if(StringUtils.isBlank(playId)){
    		return results;
    	}
    	HashSet<String> leagueNames = new HashSet<String>();
    	//先分页查询所有可投注的比赛id和code，形成matchIdAndCodes
    	LinkedHashMap<Long,String> matchIdAndCodes=makeFBMatchIdAndCodes(leagueList,firstResult,pageMaxResult,minMatchPlayingTime);
    	//再根据比赛id查到所有可投注的玩法列表，形成玩法id为键，map<比赛id,MatchPlay>为值的map
    	Map<String,Map<Long,MatchPlay>> playIdAndMapOfMatchIdAndMatchPlay=makeFBSinglePlayIdAndMapOfMatchIdAndMatchPlay(matchIdAndCodes);
    	Map<Long, MatchPlay> mps01 = mapsOnSale(Constants.PLAY_01_ZC_1, playIdAndMapOfMatchIdAndMatchPlay);
    	Map<Long, MatchPlay> mps02 = mapsOnSale(Constants.PLAY_02_ZC_1, playIdAndMapOfMatchIdAndMatchPlay);
    	Map<Long, MatchPlay> mps03 = mapsOnSale(Constants.PLAY_03_ZC_1, playIdAndMapOfMatchIdAndMatchPlay);
    	Map<Long, MatchPlay> mps04 = mapsOnSale(Constants.PLAY_04_ZC_1, playIdAndMapOfMatchIdAndMatchPlay);
    	Map<Long, MatchPlay> mps05 = mapsOnSale(Constants.PLAY_80_ZC_1, playIdAndMapOfMatchIdAndMatchPlay);
    	Object[] matchIds = matchIdAndCodes.keySet().toArray();
		Arrays.sort(matchIds);
		
		for(Object mid:matchIds){
			List<String> notOpenSalePlays = new ArrayList<String>();
			SingalMatchPlay mmp = new SingalMatchPlay();
			Long matchId = (Long) mid;
			MatchPlay mp = mps02.get(matchId) == null ? null : (MatchPlay)mps02.get(matchId);
			if(mp == null){
				MixMatchPlay mpCopy = new MixMatchPlay();
				if(mps01.get(matchId) != null){
					BeanUtils.copyProperties(mps01.get(matchId), mpCopy);
				}else if(mps03.get(matchId) != null){
					BeanUtils.copyProperties(mps03.get(matchId), mpCopy);
				}else if(mps04.get(matchId) != null){
					BeanUtils.copyProperties(mps04.get(matchId), mpCopy);
				}else{
					continue;
				}
				mp = mpCopy;
				mp.setOdds("0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0");
				mp.setOptions(PlayType.getOptionByPlayId(Constants.PLAY_02_ZC));
				mp.setPriorOdds("0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0");
				mp.setDefaultScore(0);
				notOpenSalePlays.add(MixPlayType.BF.name());
			}
			BeanUtils.copyProperties(mp, mmp);
			MatchPlay mp01 = mps01.get(matchId);
			MatchPlay mp03 = mps03.get(matchId);
			MatchPlay mp04 = mps04.get(matchId);
			MatchPlay mp05 = mps05.get(matchId);
			
			mmp.setOdds_80ZC1( mp05 == null ? "0,0,0" : mp05.getOdds());
			mmp.setOptions_80ZC1( mp05 == null ? PlayType.getOptionByPlayId(Constants.PLAY_01_ZC) : mp05.getOptions());
			mmp.setPriorOdds_80ZC1( mp05 == null ? "0,0,0" : mp05.getPriorOdds());
			mmp.setDefaultScore(0);
			if(mp05 == null){
				notOpenSalePlays.add(MixPlayType.BRQSPF.name());
			}
			
			mmp.setOdds_01ZC1( mp01 == null ? "0,0,0" : mp01.getOdds());
			mmp.setOptions_01ZC1( mp01 == null ? PlayType.getOptionByPlayId(Constants.PLAY_01_ZC) : mp01.getOptions());
			mmp.setPriorOdds_01ZC1( mp01 == null ? "0,0,0" : mp01.getPriorOdds());
			mmp.setDefaultScore( mp01 == null ? 0 : mp01.getDefaultScore());
			if(mp01 == null){
				notOpenSalePlays.add(MixPlayType.SPF.name());
			}
			
			mmp.setOdds_03ZC1( mp03 == null ? "0,0,0,0,0,0,0,0" : mp03.getOdds());
			mmp.setOptions_03ZC1( mp03 == null ? PlayType.getOptionByPlayId(Constants.PLAY_03_ZC) : mp03.getOptions());
			mmp.setPriorOdds_03ZC1( mp03 == null ? "0,0,0,0,0,0,0,0"  : mp03.getPriorOdds());
			if(mp03 == null){
				notOpenSalePlays.add(MixPlayType.JQS.name());
			}
				
			mmp.setOdds_04ZC1( mp04 == null ? "0,0,0,0,0,0,0,0,0" : mp04.getOdds());
			mmp.setOptions_04ZC1( mp04 == null ? PlayType.getOptionByPlayId(Constants.PLAY_04_ZC) : mp04.getOptions());
			mmp.setPriorOdds_04ZC1( mp04 == null ? "0,0,0,0,0,0,0,0,0" : mp04.getPriorOdds());
			if(mp04 == null){
				notOpenSalePlays.add(MixPlayType.BQC.name());
			}
			if(!notOpenSalePlays.isEmpty()){
				mmp.setNotOpenSalePlays(notOpenSalePlays.toString().replace("[", "").replace("]", ""));
			}
			leagueNames.add(mmp.getLeagueName());
			results.add(mmp);
		}
		
		if(leagueNames != null && leagueNames.size()>0){
	        Map<String, String> colors = matchColorDao.listColorsByLeagueNames(leagueNames);
	        for(MatchPlay mp:results){
	        	mp.setColor(colors.get(mp.getLeagueName()));
	        }
		}
		sortSingleMatchPlayList(results);
    	return results; 
	
	}

	private void sortSingleMatchPlayList(List<SingalMatchPlay> results) {
		 MatchPlayComparator<SingalMatchPlay> matchPlayerComparator = new MatchPlayComparator<SingalMatchPlay>();
		 Collections.sort(results, matchPlayerComparator);
		
	}

	private Map<String, Map<Long, MatchPlay>> makeFBSinglePlayIdAndMapOfMatchIdAndMatchPlay(
			LinkedHashMap<Long, String> matchIdAndCodes) {
		Map<String, Map<Long, MatchPlay>> result=null;
		
		Integer beforeCloseMinute = computeBeforeCloseMinute();
		List<LotteryOpenSalePO> openAndEndTimes=this.lotteryOpenSaleDao.findOpenAndEndTime();
		Set<Long> matchIdSet = matchIdAndCodes.keySet();
		List<Object[]> matchAndMatchPlays=this.fbMatchPlayDao.findSingleMatchAndMatchPlayByMatchId(matchIdSet);
		if(null!=openAndEndTimes&&!openAndEndTimes.isEmpty()){
			if(null!=matchAndMatchPlays&&!matchAndMatchPlays.isEmpty()){
				result=new HashMap<String, Map<Long, MatchPlay>>();
				for(Object[] matchAndMatchPlay:matchAndMatchPlays){
					FBMatchPlayPO fbMatchPlayPO = (FBMatchPlayPO) matchAndMatchPlay[0];
		    		FBMatchPO fbMatchPO=(FBMatchPO) matchAndMatchPlay[1];
		    		String playId=fbMatchPlayPO.getPlayId();
		    		Long matchId=fbMatchPO.getId();
					MatchPlay matchPlay=new MatchPlay();
					BeanUtils.copyProperties(fbMatchPO, matchPlay);
					matchPlay.setDefaultScore(fbMatchPO.getConcedePoints());
		    		
					matchPlay.setPlayId(fbMatchPlayPO.getPlayId());
					matchPlay.setOptions(fbMatchPlayPO.getOptions());
					matchPlay.setOdds(fbMatchPlayPO.getOdds());
					matchPlay.setPriorOdds(fbMatchPlayPO.getPriorOdds());
		    		
					matchPlay.setEntrustDeadline(makeEntrustDeadline(beforeCloseMinute,matchPlay.getPlayingTime(),openAndEndTimes));
					Map<Long, MatchPlay> matchIdAndMatchPlay=null;
		    		if(result.containsKey(playId)){
		    			matchIdAndMatchPlay = result.get(playId);
		    			if(null!=matchIdAndMatchPlay){
		    				
							matchIdAndMatchPlay.put(matchId, matchPlay);
		    			}
		    			
		    		}
		    		else{
		    			matchIdAndMatchPlay=new HashMap<Long,MatchPlay>();
		    			matchIdAndMatchPlay.put(matchId, matchPlay);
						result.put(playId, matchIdAndMatchPlay);
		    		}
				}
			}
		}
		
		return result;
	}

	

	private LinkedHashMap<String, MatchPlay> mapsSingleFBOnSale(
			String playId, LinkedHashMap<Long, String> leagueMatchIdsCodes,
			List<String> leagueList, int firstResult, int pageMaxResult,
			Date minMatchPlayingTime) {
		Integer beforeCloseMinute = computeBeforeCloseMinute();
		List<LotteryOpenSalePO> openAndEndTimes=this.lotteryOpenSaleDao.findOpenAndEndTime();
		if (null != openAndEndTimes
				&& !openAndEndTimes.isEmpty()
				) {

			List<Object[]> data = fbMatchPlayDao.findSingleByStatusAndAfterDeadLine(playId, EntityStatus.MATCH_ON_SALE,leagueList,firstResult,pageMaxResult,minMatchPlayingTime);
	    	LinkedHashMap<String,MatchPlay> results = new LinkedHashMap<String,MatchPlay>(data.size());
	    	
	    	for (Object[] d : data) {
	    		MatchPlay mp = new MatchPlay();
	    		FBMatchPlayPO p = (FBMatchPlayPO) d[0];
	    		
	    		BeanUtils.copyProperties(d[1], mp);
	    		mp.setDefaultScore(((FBMatchPO)d[1]).getConcedePoints());
	    		if(leagueMatchIdsCodes != null){
	    			leagueMatchIdsCodes.put( mp.getId(), mp.getCode());
	    		}
	    		
	    		mp.setPlayId(p.getPlayId());
	    		mp.setOptions(p.getOptions());
	    		mp.setOdds(p.getOdds());
	    		mp.setPriorOdds(p.getPriorOdds());
	    		
	    		mp.setEntrustDeadline(makeEntrustDeadline(beforeCloseMinute,mp.getPlayingTime(),openAndEndTimes));
	    		results.put(mp.getCode(),mp);
	    	}
	    	return results;
			

		}
		return null;
	}

	@Transactional
	@Override
	public List<SingalMatchPlay> listSingalBBOnSaleForPageAndLeagueName(
			String playId, List<String> leagueList, int firstResult, int pageMaxResult,Date minMatchPlayingTime) {
		List<SingalMatchPlay> results = new ArrayList<SingalMatchPlay>(); 
    	if(StringUtils.isBlank(playId)){
    		return results;
    	}
    	//先分页查询所有可投注的比赛id和code，形成matchIdAndCodes
    	LinkedHashMap<Long,String> matchIdAndCodes=makeBBMatchIdAndCodes(leagueList,firstResult,pageMaxResult,minMatchPlayingTime);
    	//再根据比赛id查到所有可投注的玩法列表，形成玩法id为键，map<比赛id,MatchPlay>为值的map
    	Map<String,Map<Long,MatchPlay>> playIdAndMapOfMatchIdAndMatchPlay=makeBBSinglePlayIdAndMapOfMatchIdAndMatchPlay(matchIdAndCodes);
		Map<Long,MatchPlay> mps01 = mapsOnSale(Constants.PLAY_06_LC_1, playIdAndMapOfMatchIdAndMatchPlay);
		Map<Long,MatchPlay> mps02 = mapsOnSale(Constants.PLAY_07_LC_1, playIdAndMapOfMatchIdAndMatchPlay);
		Map<Long,MatchPlay> mps03 = mapsOnSale(Constants.PLAY_08_LC_1, playIdAndMapOfMatchIdAndMatchPlay);
		Map<Long,MatchPlay> mps04 = mapsOnSale(Constants.PLAY_09_LC_1, playIdAndMapOfMatchIdAndMatchPlay);
		Object[] matchIds = matchIdAndCodes.keySet().toArray();
		Arrays.sort(matchIds);
		
		for(Object mid:matchIds){
			List<String> notOpenSalePlays = new ArrayList<String>();
			SingalMatchPlay mmp = new SingalMatchPlay();
			Long matchId = (Long) mid;
			MatchPlay mp = mps02.get(matchId) == null ? null : (MatchPlay)mps02.get(matchId);
			if(mp == null){
				MixMatchPlay mpCopy = new MixMatchPlay();
				if(mps01.get(matchId) != null){
					BeanUtils.copyProperties(mps01.get(matchId), mpCopy);
				}else if(mps03.get(matchId) != null){
					BeanUtils.copyProperties(mps03.get(matchId), mpCopy);
				}else if(mps04.get(matchId) != null){
					BeanUtils.copyProperties(mps04.get(matchId), mpCopy);
				}else{
					continue;
				}
				mp = mpCopy;
				mp.setOdds("0,0");
				mp.setOptions(PlayType.getOptionByPlayId(Constants.PLAY_07_LC));
				mp.setPriorOdds(null);
				mp.setDefaultScore(0);
				notOpenSalePlays.add(MixPlayType.SF.name());
			}
			BeanUtils.copyProperties(mp, mmp);
			MatchPlay mp06 = mps01.get(matchId);
			MatchPlay mp08 = mps03.get(matchId);
			MatchPlay mp09 = mps04.get(matchId);
			
			mmp.setOdds_06LC1( mp06 == null ? "0,0" : mp06.getOdds());
			mmp.setOptions_06LC1( mp06 == null ? PlayType.getOptionByPlayId(Constants.PLAY_06_LC) : mp06.getOptions());
			mmp.setPriorOdds_06LC1( mp06 == null ? null : mp06.getPriorOdds());
			mmp.setDefaultScore( mp06 == null ? 0 : mp06.getDefaultScore());
			if(mp06 == null){
				notOpenSalePlays.add(MixPlayType.RFSF.name());
			}
			
			mmp.setOdds_08LC1( mp08 == null ? "0,0,0,0,0,0,0,0,0,0,0,0" : mp08.getOdds());
			mmp.setOptions_08LC1( mp08 == null ? PlayType.getOptionByPlayId(Constants.PLAY_08_LC) : mp08.getOptions());
			mmp.setPriorOdds_08LC1( mp08 == null ? null : mp08.getPriorOdds());
			if(mp08 == null){
				notOpenSalePlays.add(MixPlayType.FC.name());
			}
				
			mmp.setOdds_09LC1( mp09 == null ? "0,0" : mp09.getOdds());
			mmp.setOptions_09LC1( mp09 == null ? PlayType.getOptionByPlayId(Constants.PLAY_09_LC) : mp09.getOptions());
			mmp.setPriorOdds_09LC1( mp09 == null ? null : mp09.getPriorOdds());
			mmp.setDefaultScore_09LC1( mp09 == null ? 1 : mp09.getDefaultScore());
			if(mp09 == null){
				notOpenSalePlays.add(MixPlayType.DXF.name());
			}
			if(!notOpenSalePlays.isEmpty()){
				mmp.setNotOpenSalePlays(notOpenSalePlays.toString().replace("[", "").replace("]", ""));
			}
			results.add(mmp);
		}
		sortSingleMatchPlayList(results);
    	return results;
	}

	private Map<String, Map<Long, MatchPlay>> makeBBSinglePlayIdAndMapOfMatchIdAndMatchPlay(
			LinkedHashMap<Long, String> matchIdAndCodes) {
		Map<String, Map<Long, MatchPlay>> result=null;
		
		Integer beforeCloseMinute = computeBeforeCloseMinute();
		List<LotteryOpenSalePO> openAndEndTimes=this.lotteryOpenSaleDao.findOpenAndEndTime();
		Set<Long> matchIdSet = matchIdAndCodes.keySet();
		List<Object[]> matchAndMatchPlays=this.bbMatchPlayDao.findSingleMatchAndMatchPlayByMatchId(matchIdSet);
		if(null!=openAndEndTimes&&!openAndEndTimes.isEmpty()){
			if(null!=matchAndMatchPlays&&!matchAndMatchPlays.isEmpty()){
				result=new HashMap<String, Map<Long, MatchPlay>>();
				for(Object[] matchAndMatchPlay:matchAndMatchPlays){
					BBMatchPlayPO bbMatchPlayPO = (BBMatchPlayPO) matchAndMatchPlay[0];
		    		BBMatchPO bbMatchPO=(BBMatchPO) matchAndMatchPlay[1];
		    		String playId=bbMatchPlayPO.getPlayId();
		    		Long matchId=bbMatchPO.getId();
					MatchPlay matchPlay=new MatchPlay();
					BeanUtils.copyProperties(bbMatchPO, matchPlay);
					
		    		
					matchPlay.setPlayId(bbMatchPlayPO.getPlayId());
					matchPlay.setOptions(bbMatchPlayPO.getOptions());
					matchPlay.setOdds(bbMatchPlayPO.getOdds());
					matchPlay.setPriorOdds(bbMatchPlayPO.getPriorOdds());
					matchPlay.setDefaultScore(bbMatchPlayPO.getDefaultScore());
		    		
					matchPlay.setEntrustDeadline(makeEntrustDeadline(beforeCloseMinute,matchPlay.getPlayingTime(),openAndEndTimes));
					Map<Long, MatchPlay> matchIdAndMatchPlay=null;
		    		if(result.containsKey(playId)){
		    			matchIdAndMatchPlay = result.get(playId);
		    			if(null!=matchIdAndMatchPlay){
		    				
							matchIdAndMatchPlay.put(matchId, matchPlay);
		    			}
		    			
		    		}
		    		else{
		    			matchIdAndMatchPlay=new HashMap<Long,MatchPlay>();
		    			matchIdAndMatchPlay.put(matchId, matchPlay);
						result.put(playId, matchIdAndMatchPlay);
		    		}
				}
			}
		}
		
		return result;
	}

	@Override
	@Transactional(readOnly=true)
	public boolean canBetJC(LotteryId lotteryId) {
		boolean result=false;
		if(lotteryId.equals(LotteryId.JCLQ)||lotteryId.equals(LotteryId.JCZQ)){
			int dayOfWeek=Calendar.getInstance().get(Calendar.DAY_OF_WEEK );
			Long count=lotteryOpenSaleDao.countByLotteryIdAndDayOfWeek(lotteryId,dayOfWeek);
			if(null!=count&&count==1){
				result=true;
			}
		}
		return result;
	}

	@Override
	@Transactional(readOnly=true)
	public List<Object[]> queryJCFBMatchCountByStatusAndAfterDeadLine(int status,Date minMatchPlayingTime) {
		
		return fbMatchDao.queryFBMatchCountByStatusAndAfterDeadLine(status,minMatchPlayingTime);
	}

	private Integer computeBeforeCloseMinute() {
		Integer beforeCloseMinute=systemConfDao.findIntValueByKey(SystemConf.KEY_BEFORE_CLOSE_MINUTE);
		return beforeCloseMinute;
	}

	@Override
	@Transactional(readOnly = true)
	public List<String> queryJCFBLeaguesByStatusAndAfterDeadLine(int status,Date minMatchPlayingTime) {
		return fbMatchDao.queryFBLeaguesByStatusAndAfterDeadLine(status,minMatchPlayingTime);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Object[]> queryJCBBMatchCountByStatusAndAfterDeadLine(
			int status,Date minMatchiPlayingTime) {
		return bbMatchDao.queryBBMatchCountByStatusAndAfterDeadLine(status,minMatchiPlayingTime);
	}

	@Override
	@Transactional(readOnly = true)
	public List<String> queryJCBBLeaguesByStatusAndAfterDeadLine(int status,Date minMatchiPlayingTime) {
		return bbMatchDao.queryBBLeaguesByStatusAndAfterDeadLine(status,minMatchiPlayingTime);
	}

	@Override
	@Transactional(readOnly=true)
	public List<Object[]> queryJCFBMatchByPlayIdAndStatusAndLeagueNameAndAfterDeadLine(
			String playId, int status, List<String> leagueList,
			int firstResult, int pagingMaxResult,Date minMatchPlayingTime) {
		
		
		return fbMatchPlayDao.findMatchByPlayIdAndStatusAndLeagueNameAndAfterDeadLine(playId, status, leagueList, firstResult, pagingMaxResult,minMatchPlayingTime);
	}


	@Override
	@Transactional(readOnly=true)
	public List<Object[]> queryJCBBMatchByPlayIdAndStatusAndLeagueNameAndAfterDeadLine(
			String playId, int status, List<String> leagues,
			int firstResult, int pageMaxResult,Date minMatchPlayingTime) {
		return bbMatchPlayDao.findMatchByPlayIdAndStatusAndLeagueNameAndAfterDeadLine(playId, status, leagues,firstResult,pageMaxResult,minMatchPlayingTime);
	}

	@Override
	@Transactional(readOnly=true)
	public List<Map<String, String>> getBBMatchResultMaps(
			List<Object[]> matchAndPlayObjs) {
		List<Map<String,String>> matches = new ArrayList<Map<String, String>>();
		Integer beforeCloseMinute = computeBeforeCloseMinute();
		List<LotteryOpenSalePO> openAndEndTimes=this.lotteryOpenSaleDao.findOpenAndEndTime();
		for (Object[] objects : matchAndPlayObjs) {
			Map<String,String> resultMap = new HashMap<String, String>();
			BBMatchPO matchPO = (BBMatchPO) objects[0];
			resultMap.put(Constants.MATCH_ID, String.valueOf(matchPO.getId()));
			resultMap.put(Constants.MATCH_CODE, matchPO.getCode());
			resultMap.put(Constants.LEAGUE_SHORT_NAME, matchPO.getLeagueName());
			resultMap.put(Constants.LEAGUE_COLOR, initLeagueColor.getColorMap()
					.get(matchPO.getLongLeagueName()));
			Date newDeadLine=this.makeEntrustDeadline(beforeCloseMinute, matchPO.getPlayingTime(),openAndEndTimes);
			resultMap.put(Constants.MATCH_OFF_TIME, formatEntrustDeadline(newDeadLine)); //避免客户端改动，将比赛截止时间赋值为“大V彩销售截止时间”
			resultMap.put(Constants.ENTRUST_DEADLINE, formatEntrustDeadline(newDeadLine));
			resultMap.put(Constants.HOST_NAME, matchNameService.getTeamShortNameByLeagueIdAndTeamName(matchPO.getLeagueName(), matchPO.getHomeTeamName()));
			resultMap.put(Constants.GUEST_NAME, matchNameService.getTeamShortNameByLeagueIdAndTeamName(matchPO.getLeagueName(),matchPO.getGuestTeamName()));
			
			System.out.println(matchPO.getOfftime().toString());
			
			BBMatchPlayPO matchPlayPO = (BBMatchPlayPO) objects[1];
			resultMap.put(Constants.ODDS, matchPlayPO.getOdds());
			resultMap.put(Constants.OPTIONS, matchPlayPO.getOptions());
			resultMap.put(Constants.CONCEDE_POINTS, String.valueOf(matchPlayPO.getDefaultScore()));
			
			matches.add(resultMap);
		}
		return matches;
	}

	@Override
	public Date computeNewEntrustDeadlineByLotteryId(Date entrustDeadline,
			String lotteryId,Integer beforeCloseMinute) {
		Date result=null;
		if(null!=entrustDeadline&&StringUtils.isNotBlank(lotteryId)&&null!=beforeCloseMinute&&beforeCloseMinute>0){
			if(StringUtils.equals(lotteryId, LotteryId.CTZC.toString())){
				result=DateUtils.addMinutes(entrustDeadline, -1*beforeCloseMinute);
			}else if(StringUtils.equals(lotteryId, LotteryId.JCZQ.toString())||StringUtils.equals(lotteryId, LotteryId.JCLQ.toString())){
				result=DateUtils.addMinutes(entrustDeadline, -1*(beforeCloseMinute-15));
			}
			else if(StringUtils.equals(lotteryId, LotteryId.BJDC.toString())||StringUtils.equals(lotteryId, LotteryId.BDSF.toString())){
				result=DateUtils.addMinutes(entrustDeadline, -1*(beforeCloseMinute-10));
			}
		}
		return result;
	}

	@Override
	@Transactional(readOnly=true)
	public Date computeMinEntrustDeadline(List<BetMatch> matchs,
			String lotteryId) {
		BetMatch bm = calcMinEntrustDeadline(matchs, lotteryId);
		if(null != bm) {
			return bm.getEntrustDeadline();
		}
		return null;
	}

	private BetMatch calcMinEntrustDeadline(List<BetMatch> matchs, String lotteryId) {
		BetMatch bm = null;
		Date t = null;
		Date newDeadline = null;
		if(null!=matchs&&!matchs.isEmpty()&&StringUtils.isNotBlank(lotteryId)){
			Integer beforeCloseMinute=systemConfDao.findIntValueByKey(SystemConf.KEY_BEFORE_CLOSE_MINUTE);
			List<LotteryOpenSalePO> openAndEndTimes=this.lotteryOpenSaleDao.findOpenAndEndTime();
			for (BetMatch match : matchs) {
				newDeadline = this.makeEntrustDeadline(beforeCloseMinute, match.getPlayingTime(), openAndEndTimes);
				if (t == null || t.after(newDeadline)) {
					t = newDeadline;
					bm = match;
				}
			}
		}
		if(null != bm) {
			bm.setEntrustDeadline(t);
		}
		return bm;
	}
	
	@Transactional
	@Override
	public BetMatch computeMinEntrustDeadlineMatch(List<BetMatch> matchs,
			String lotteryId) {
		BetMatch bm = calcMinEntrustDeadline(matchs, lotteryId);
		return bm;
	}

	@Override
	@Transactional
	public Date computeMinMatchPlayingTime() {
		Date minMatchPlayingTime=null;
		List<LotteryOpenSalePO> openAndEndTimes=this.lotteryOpenSaleDao.findOpenAndEndTime();
		Integer beforeCloseMinute=systemConfDao.findIntValueByKey(SystemConf.KEY_BEFORE_CLOSE_MINUTE);
		if(null!=openAndEndTimes&&!openAndEndTimes.isEmpty()){
			
				Date now=new Date();
				OpenSaleTime openSaleTime=OpenSaleTimeUtil.transferToOpenSaleTime(openAndEndTimes, now);
				if(null!=openSaleTime){
					if(now.compareTo(openSaleTime.getTodayOpenDateTime())<=0){//比当天投注开始时间早
						minMatchPlayingTime=DateUtils.addMinutes(openSaleTime.getTodayOpenDateTime(), beforeCloseMinute);
					}
					else if(now.compareTo(openSaleTime.getTodayOpenDateTime())>0&&now.compareTo(openSaleTime.getTodayEndDateTime())<0){//在当天投注开始时间和结束时间之间
						minMatchPlayingTime=DateUtils.addMinutes(now, beforeCloseMinute);
					}
					else if(now.compareTo(openSaleTime.getTodayEndDateTime())>=0&&now.compareTo(openSaleTime.getTomorrowOpenDateTime())<=0){//在当天投注结束时间和明天投注开始时间之间
						minMatchPlayingTime=DateUtils.addMinutes(openSaleTime.getTomorrowOpenDateTime(), beforeCloseMinute);
					}
				}
				
			
		}
		return minMatchPlayingTime;
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<MixMatchPlay> listFBInLastDays(String time) {
		if(StringUtils.isBlank(time)){
			return new ArrayList<MixMatchPlay>();
		} else {
			ArrayList<Date> a = getFromAndTo(time,false);
			return listHHFBMatchByTime(a.get(0),a.get(1));
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<MixMatchPlay> listBBInLastDays(String time) {
		ArrayList<Date> a = getFromAndTo(time,false);
		return listHHBBMatchByTime(a.get(0),a.get(1));
	}
	
	private ArrayList<Date> getFromAndTo(String time,Boolean flag){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		if(null == time){
			time = simpleDateFormat.format(new Date());
		}
		Calendar c = Calendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY, 0);
    	c.set(Calendar.MINUTE, 0);
    	c.set(Calendar.SECOND, 0);
    	c.set(Calendar.MILLISECOND, 0);
    	Date today = c.getTime();
    	c.set(Calendar.HOUR_OF_DAY, 12);
		Date from;
		Date to;
		try {
			Date date = simpleDateFormat.parse(time);
			if(date.after(today) || date.equals(today)){
				Date date1 = new Date();
				c.setTime(date1);
				c.set(Calendar.HOUR_OF_DAY, 12);
				c.set(Calendar.MINUTE, 0);
				c.set(Calendar.SECOND, 0);
				c.set(Calendar.MILLISECOND, 0);
				c.add(Calendar.DATE, -1);
				from = c.getTime();
		    	to = null;
			} else {
				c.setTime(date);
				c.set(Calendar.HOUR_OF_DAY, 12);
		    	c.set(Calendar.MINUTE, 0);
		    	c.set(Calendar.SECOND, 0);
		    	c.set(Calendar.MILLISECOND, 0);
		    	from = c.getTime();
		    	c.add(Calendar.DATE, 1);
		    	to = c.getTime();
			}
			if(flag){
				from = date;
				to = null;
			}
		} catch (ParseException e) {
			logger.info("查询投注赛程错误，非法的事件格式：{},要求的时间格式：yyyy-MM-dd",time);
			logger.info("使用当前时间作为默认查询时间");
			c.add(Calendar.DATE, -1);
			from = c.getTime();
			to = null;
		}
		ArrayList<Date> d = new ArrayList<Date>();
		d.add(from);
		d.add(to);
		return d;
	}
	
	@Override
	@Transactional
	public List<FootBallMatchData> getZQbifenData(String matchType,
			String subType, String leagueShortName) {
		ArrayList<Date> dates= getFromAndTo(null,true);
		List<Object[]> ftDatas = jCZQAnalyseDao.getZQbifen(dates.get(0), dates.get(1), leagueShortName);
		if(ftDatas != null && !ftDatas.isEmpty()){
			List<FootBallMatchData> list = new ArrayList<FootBallMatchData>();
			Map<String,String> leagueNames = new HashMap<String, String>();//联赛名称集合
			FootBallMatchData foot = null;
			//取得联赛对应颜色
			for(Object[] objects : ftDatas){
				leagueNames.put((String)objects[1], (String)objects[1]);
			}
			Set<String> keySet = leagueNames.keySet();
			Map<String, String> colors = matchColorDao.listColorsByLeagueNames(keySet);
			for(Object[] objects : ftDatas){
				foot = new FootBallMatchData();
				foot.setFoBaseInfoPO((FbMatchBaseInfoPO)objects[0]);
				foot.setLeagueShortName((String)objects[1]);
				foot.setColor(colors.get((String)objects[1]) != null ? colors.get((String)objects[1]) : "#000000");
				list.add(foot);
			}
			return list;
		}
		return null;
	}
	
	@Override
	@Transactional
	public List<BasketBallMatchData> getLQbifenData(String matchType, String subType,
			String leagueShortName) {
		ArrayList<Date> dates= getFromAndTo(null,true);
		List<Object[]> btDatas = jCLQAnalyseDao.getBBbifen(dates.get(0), dates.get(1), leagueShortName);
		if(btDatas != null && !btDatas.isEmpty()){
			List<BasketBallMatchData> list = new ArrayList<BasketBallMatchData>();
			BasketBallMatchData bas = null;
			//取得联赛对应颜色
			for(Object[] objects : btDatas){
				bas = new BasketBallMatchData();
				bas.setBasketBallMatchPO((BasketBallMatchPO)objects[0]);
				bas.setLeagueShortName((String)objects[1]);
				bas.setColor((String)objects[2]);
				list.add(bas);
			}
			return list;
		}
		return null;
	}
	
	@Override
	@Transactional(readOnly=true)
	public Date computeMachineOfftime(List<BetMatch> matchs,
			String lotteryId) {
		if(null!=matchs&&!matchs.isEmpty()&&StringUtils.isNotBlank(lotteryId)){
			List<LotteryOpenSalePO> openAndEndTimes=this.lotteryOpenSaleDao.findOpenAndEndTime();
			Date t=null;
			Date newDeadline=null;
			for (BetMatch match : matchs) {
				newDeadline = this.makeMachineEntrustDeadline(0, match.getPlayingTime(), openAndEndTimes);
				if (t == null || t.after(newDeadline)) {
					t = newDeadline;
				}
			}
			return t;
		}
		return null;
	}
	
	@Override
	@Transactional
	public List<FootBallMatchData> findZQSameDayFromQT(Date from, Date to) {
		ArrayList<Date> dates= getFromAndTo(null,true);
		List<Object[]> ftDatas = jCZQAnalyseDao.getZQResoureData(dates.get(0),from , to);
		if(ftDatas != null && ftDatas.size() > 0){
			List<FootBallMatchData> list = new ArrayList<FootBallMatchData>(30);
			Map<String,String> leagueNames = new HashMap<String, String>(30);//联赛名称集合
			FootBallMatchData foot = null;
			//取得联赛对应颜色
			for(Object[] objects : ftDatas){
				leagueNames.put((String)objects[1], (String)objects[1]);
			}
			Set<String> keySet = leagueNames.keySet();
			Map<String, String> colors = matchColorDao.listColorsByLeagueNames(keySet);
			for(Object[] objects : ftDatas){
				foot = new FootBallMatchData();
				foot.setFoBaseInfoPO((FbMatchBaseInfoPO)objects[0]);
				foot.setLeagueShortName((String)objects[1]);
				foot.setColor(colors.get((String)objects[1]) != null ? colors.get((String)objects[1]) : "#000000");
				list.add(foot);
			}
			return list;
		}
		return null;
	}

	@Override
	@Transactional
	public FootBallMatchData findZQByQT(String detailId) {
		Object[] ftDatas = jCZQAnalyseDao.getZQResoureDataById(detailId);
		if(ftDatas != null){
			Map<String,String> leagueNames = new HashMap<String, String>();//联赛名称集合
			FootBallMatchData foot = new FootBallMatchData();
			//取得联赛对应颜色
			leagueNames.put((String)ftDatas[1], (String)ftDatas[1]);
			Set<String> keySet = leagueNames.keySet();
			Map<String, String> colors = matchColorDao.listColorsByLeagueNames(keySet);
			foot.setFoBaseInfoPO((FbMatchBaseInfoPO)ftDatas[0]);
			foot.setLeagueShortName((String)ftDatas[1]);
			foot.setColor(colors.get((String)ftDatas[1]) != null ? colors.get((String)ftDatas[1]) : "#000000");
			return foot;
		}
		return null;
	}

	@Override
	@Transactional
	public boolean updateZQByQT(FbMatchBaseInfoPO foBaseInfoPO) {
		FbMatchBaseInfoPO newFoBaseInfoPO = jCZQAnalyseDao.findFbById(foBaseInfoPO.getId());
		newFoBaseInfoPO.setGuestTeamName(foBaseInfoPO.getGuestTeamName());
		newFoBaseInfoPO.setHomeTeamName(foBaseInfoPO.getHomeTeamName());
		newFoBaseInfoPO.setMatchTime(foBaseInfoPO.getMatchTime());
		newFoBaseInfoPO.setMatchStatus(foBaseInfoPO.getMatchStatus());
		newFoBaseInfoPO.setLiveContent(foBaseInfoPO.getLiveContent());
		if(jCLQAnalyseDao.saveFbMatchBaseInfoPO(newFoBaseInfoPO)){
			return true;
		}
		return false;
	}

	@Override
	@Transactional
	public List<FootBallMatchData> findZQFromQT(Date from, Date to) {
		List<Object[]> ftDatas = jCZQAnalyseDao.getZQResoureData(from , to);
		if(ftDatas != null && ftDatas.size() > 0){
			List<FootBallMatchData> list = new ArrayList<FootBallMatchData>(30);
			Map<String,String> leagueNames = new HashMap<String, String>(30);//联赛名称集合
			FootBallMatchData foot = null;
			//取得联赛对应颜色
			for(Object[] objects : ftDatas){
				leagueNames.put((String)objects[1], (String)objects[1]);
			}
			Set<String> keySet = leagueNames.keySet();
			Map<String, String> colors = matchColorDao.listColorsByLeagueNames(keySet);
			for(Object[] objects : ftDatas){
				foot = new FootBallMatchData();
				foot.setFoBaseInfoPO((FbMatchBaseInfoPO)objects[0]);
				foot.setLeagueShortName((String)objects[1]);
				foot.setColor(colors.get((String)objects[1]) != null ? colors.get((String)objects[1]) : "#000000");
				list.add(foot);
			}
			return list;
		}
		return null;
	}

	@Override
	@Transactional
	public List<BasketBallMatchData> findLQSameDayFromQT(Date from, Date to) {
		ArrayList<Date> dates= getFromAndTo(null,true);
		List<Object[]> btDatas = jCLQAnalyseDao.getBBResoureData(dates.get(0), from , to );
		if(btDatas != null && !btDatas.isEmpty()){
			List<BasketBallMatchData> list = new ArrayList<BasketBallMatchData>();
			BasketBallMatchData bas = null;
			//取得联赛对应颜色
			for(Object[] objects : btDatas){
				bas = new BasketBallMatchData();
				bas.setBasketBallMatchPO((BasketBallMatchPO)objects[0]);
				bas.setLeagueShortName((String)objects[1]);
				bas.setColor((String)objects[2]);
				list.add(bas);
			}
			return list;
		}
		return null;
	}

	@Override
	@Transactional
	public BasketBallMatchData findLQByQT(String dtailId) {
		Object[] btDatas = jCLQAnalyseDao.getBBResoureData(dtailId);
		BasketBallMatchData bas =  new BasketBallMatchData();
		//取得联赛对应颜色
		bas.setBasketBallMatchPO((BasketBallMatchPO)btDatas[0]);
		bas.setLeagueShortName((String)btDatas[1]);
		bas.setColor((String)btDatas[2]);
		return bas;
	}

	@Override
	@Transactional
	public boolean updateLQByQT(BasketBallMatchPO basketBallMatchPO) {
		BasketBallMatchData newBbBaseInfoPO = findLQByQT(basketBallMatchPO.getId()+"");
		newBbBaseInfoPO.getBasketBallMatchPO().setGuestTeam(basketBallMatchPO.getGuestTeam());
		newBbBaseInfoPO.getBasketBallMatchPO().setHomeTeam(basketBallMatchPO.getHomeTeam());
		newBbBaseInfoPO.getBasketBallMatchPO().setMatchTime(basketBallMatchPO.getMatchTime());
		newBbBaseInfoPO.getBasketBallMatchPO().setMatchState(basketBallMatchPO.getMatchState());
		newBbBaseInfoPO.getBasketBallMatchPO().setExplainContent(basketBallMatchPO.getExplainContent());
		if(jCLQAnalyseDao.saveBbMatchBaseInfoPO(newBbBaseInfoPO)){
			return true;
		}
		return false;
	}

	@Override
	@Transactional
	public List<BasketBallMatchData> findLQFromQT(Date from, Date to) {
		List<Object[]> btDatas = jCLQAnalyseDao.getLQResoureData(from , to);
		if(btDatas != null && btDatas.size() > 0){
			List<BasketBallMatchData> list = new ArrayList<BasketBallMatchData>(30);
			Map<String,String> leagueNames = new HashMap<String, String>(30);//联赛名称集合
			BasketBallMatchData basket = null;
			//取得联赛对应颜色
			for(Object[] objects : btDatas){
				leagueNames.put((String)objects[1], (String)objects[1]);
			}
			Set<String> keySet = leagueNames.keySet();
			Map<String, String> colors = matchColorDao.listColorsByLeagueNames(keySet);
			for(Object[] objects : btDatas){
				basket = new BasketBallMatchData();
				basket.setBasketBallMatchPO((BasketBallMatchPO)objects[0]);
				basket.setLeagueShortName((String)objects[1]);
				basket.setColor(colors.get((String)objects[1]) != null ? colors.get((String)objects[1]) : "#000000");
				list.add(basket);
			}
			return list;
		}
		return null;
	}

	@Override
	@Transactional(readOnly=true)
	public Map<Long, BigDecimal> findDefaultScoreByMatchIdAndLotteryId(
			List<BetMatch> matchList, String lotteryId) {
		Map<Long, BigDecimal> result=null;
		if(null!=matchList&&!matchList.isEmpty()&&StringUtils.isNotBlank(lotteryId)){
			Set<Long> matchIdSet=makeMatchIdSet(matchList);
			result=new HashMap<Long, BigDecimal>();
			if(StringUtils.equals(lotteryId, LotteryId.JCZQ.toString())){
				List<FBMatchPO> fbMatchList = fbMatchDao.find(matchIdSet);
				if(null!=fbMatchList&&!fbMatchList.isEmpty()){
					for(FBMatchPO fbMatch:fbMatchList){
						result.put(fbMatch.getId(), new BigDecimal(fbMatch.getConcedePoints()));
					}
				}
			}else if(StringUtils.equals(lotteryId, LotteryId.JCLQ.toString())){
				List<BBMatchPlayPO> bbMatchPlayList = bbMatchPlayDao.find(Constants.PLAY_06_LC_2, matchIdSet);
				if(null!=bbMatchPlayList&&!bbMatchPlayList.isEmpty()){
					for(BBMatchPlayPO bbMatchPlay:bbMatchPlayList){
						result.put(bbMatchPlay.getMatchId(), new BigDecimal(bbMatchPlay.getDefaultScore()));
					}
				}
			}
		}
		return result;
	}

	private Set<Long> makeMatchIdSet(List<BetMatch> matchList) {
		Set<Long> result=null;
		if(null!=matchList&&!matchList.isEmpty()){
			result=new HashSet<Long>();
			for(BetMatch match:matchList){
				result.add(match.getMatchId());
			}
		}
		return result;
	}
}

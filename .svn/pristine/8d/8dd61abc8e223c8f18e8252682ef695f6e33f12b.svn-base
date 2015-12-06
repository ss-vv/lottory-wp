package com.unison.lottery.weibo.web.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unison.lottery.weibo.data.service.store.persist.dao.QTMatchDao;
import com.unison.lottery.weibo.web.service.MatchScheduleService;
import com.xhcms.lottery.commons.data.IssueInfo;
import com.xhcms.lottery.commons.data.MatchSelector;
import com.xhcms.lottery.commons.lang.MatchConstant;
import com.xhcms.lottery.commons.persist.dao.BBMatchPlayDao;
import com.xhcms.lottery.commons.persist.dao.BJDCMatchPlayDao;
import com.xhcms.lottery.commons.persist.dao.FBMatchPlayDao;
import com.xhcms.lottery.commons.persist.dao.MatchColorDao;
import com.xhcms.lottery.commons.persist.entity.BBMatchPO;
import com.xhcms.lottery.commons.persist.entity.BBMatchPlayPO;
import com.xhcms.lottery.commons.persist.entity.BJDCMatchPO;
import com.xhcms.lottery.commons.persist.entity.BJDCMatchPlayPO;
import com.xhcms.lottery.commons.persist.entity.FBMatchPO;
import com.xhcms.lottery.commons.persist.entity.FBMatchPlayPO;
import com.xhcms.lottery.commons.persist.entity.MatchPlay;
import com.xhcms.lottery.commons.persist.service.IssueService;
import com.xhcms.lottery.lang.Constants;
import com.xhcms.lottery.lang.EntityStatus;
import com.xhcms.lottery.lang.LotteryId;

@Service
public class MatchScheduleServiceImpl implements MatchScheduleService {
	
	Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private IssueService issueService;
	
	@Autowired
	private QTMatchDao qTMatchDao;
	
	@Autowired
    private MatchColorDao matchColorDao;
	
	@Autowired
    private FBMatchPlayDao fbMatchPlayDao;
	
	@Autowired
	private BBMatchPlayDao bbMatchPlayDao;
	
	@Autowired
	private BJDCMatchPlayDao bjdcMatchPlayDao;
	
	private List<Object[]> qtMatchPositionList;
	
	//返回大V彩赛事对应球队的排名
	protected Object[] findMatchPositionByLcMatch(String lcMatchId) {
		Object[] result = null;
		if(null != qtMatchPositionList) {
			for(Object[] match : qtMatchPositionList) {
				if(lcMatchId.equals(match[0])) {
					result = match;
					break;
				}
			}
		}
        return result;
	}
	
	/**
	 * 拿到所有大V彩赛事ID
	 * @param data	每个元素的数组对象第1个元素是<p>FBMatchPO</p>
	 * @return
	 */
	protected List<String> parseJCZQLcMatchId(List<Object[]> data) {
		List<String> lcMatchIdList = new ArrayList<String>();
		if(null != data && data.size() > 0) {
			for(Object[] result : data) {
				FBMatchPO fbMatch = (FBMatchPO) result[1];
				lcMatchIdList.add(fbMatch.getId()+"");
			}
		}
		return lcMatchIdList;
	}
	protected List<String> parseJCLQLcMatchId(List<Object[]> data) {
		List<String> lcMatchIdList = new ArrayList<String>();
		if(null != data && data.size() > 0) {
			for(Object[] result : data) {
				BBMatchPO bbMatch = (BBMatchPO) result[1];
				lcMatchIdList.add(bbMatch.getId()+"");
			}
		}
		return lcMatchIdList;
	}
	protected List<String> parseBJDCLcMatchId(List<Object[]> data) {
		List<String> lcMatchIdList = new ArrayList<String>();
		if(null != data && data.size() > 0) {
			for(Object[] result : data) {
				BJDCMatchPO bjdcMatch = (BJDCMatchPO) result[1];
				lcMatchIdList.add(bjdcMatch.getId()+"");
			}
		}
		return lcMatchIdList;
	}
	
	/**
	 * 返回组合后的赛事信息数据
	 * @param data 赛事信息的二维数组，第一个对象是FBMatchPlayPO对象，第二个是
	 * FBMatchPO对象
	 * @return
	 */
	protected Map<String, Object> composeFBMatchPlay(List<Object[]> data) {
		if(null == data) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
        List<MatchPlay> results = new ArrayList<MatchPlay>(data.size());
        Map<String,String> leagueNames = new HashMap<String, String>();
        Set<Long> fbMatchIdSet = new HashSet<Long>();
        
        //根据大V彩赛事ID，关联球探赛事数据获取球队排名
        List<String> lcMatchIdList = parseJCZQLcMatchId(data);
        log.debug("\n++++查询大V彩赛事球队的排名, \\\n\t大V彩赛事ID集合={}", lcMatchIdList);
        qtMatchPositionList = qTMatchDao.findQTFBMatchPosition(lcMatchIdList);
        
        Map<String,Integer> leagueMatchCount = new HashMap<String, Integer>();
        
        for (Object[] d : data) {
            MatchPlay mp = new MatchPlay();
            FBMatchPlayPO p = (FBMatchPlayPO) d[0];
            FBMatchPO fbMatchPO = (FBMatchPO)d[1];
            if(fbMatchIdSet.contains(fbMatchPO.getId())) {
            	continue;
            } else {
            	fbMatchIdSet.add(fbMatchPO.getId());
            }
            
            BeanUtils.copyProperties(fbMatchPO, mp);
            mp.setDefaultScore((fbMatchPO).getConcedePoints());
            
            leagueNames.put(mp.getLeagueName(), mp.getLeagueName());
            mp.setPlayId(p.getPlayId());
            mp.setOptions(p.getOptions());
            mp.setOdds(p.getOdds());
            mp.setPriorOdds(p.getPriorOdds());
            mp.setMatchId(fbMatchPO.getId()+"");
            
            Object[] qtMatch = findMatchPositionByLcMatch(p.getMatchId()+"");
            if(null != qtMatch) {
            	String score = calFBScore(String.valueOf(qtMatch[5]), String.valueOf(qtMatch[4]));
            	
            	Object homePosition = (null == qtMatch[1]) ? "" : qtMatch[1];
            	Object guestPosition = (null == qtMatch[2]) ? "" : qtMatch[2];
            	mp.setHomeTeamPosition(String.valueOf(homePosition));
            	mp.setGuestTeamPosition(String.valueOf(guestPosition));
            	mp.setQtMatchStatus(Integer.valueOf(String.valueOf(qtMatch[3])));
            	mp.setScore(score);
            } else {
            	mp.setHomeTeamPosition("");
            	mp.setGuestTeamPosition("");
            	log.warn("通过大V彩赛事ID={}，没有取到球队排名和赛事比分数据.", p.getMatchId());
            }
            if(mp.getStatus() == EntityStatus.MATCH_ON_SALE) {
            	mp.setStopMatch(false);
            } else {
            	mp.setStopMatch(true);
            }
            
            String leagueName = fbMatchPO.getLeagueName();
            Integer countMatch = 0;
            if((countMatch = leagueMatchCount.get(leagueName)) != null) {
            	leagueMatchCount.put(leagueName, countMatch+1);
            } else {
            	leagueMatchCount.put(leagueName, 1);
            }
            
            results.add(mp);
        }
        fbMatchIdSet = null;
        
        //赛事对应的场次数
        map.put(MatchConstant.LEAGUES, leagueMatchCount);
        
        Map<String, String> colors = new HashMap<String, String>();
        if(null != leagueNames && leagueNames.size() > 0) {
        	colors = matchColorDao.listColorsByLeagueNames(leagueNames.keySet());
        }
        for(MatchPlay mp:results){
        	mp.setColor(colors.get(mp.getLeagueName()));
        }
        
        map.put(MatchConstant.MATCHS, results);
        return map;
	}
	protected Map<String, Object> composeBJDCMatchPlay(List<Object[]> data) {
		if(null == data) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		List<MatchPlay> results = new ArrayList<MatchPlay>(data.size());
		Map<String,String> leagueNames = new HashMap<String, String>();
		Set<Long> bjdcMatchIdSet = new HashSet<Long>();
		
//		根据大V彩赛事ID，关联球探赛事数据获取球队排名
		List<String> lcMatchIdList = parseBJDCLcMatchId(data);
		qtMatchPositionList = qTMatchDao.findQTFBMatchPosition(lcMatchIdList);
		
		Map<String,Integer> leagueMatchCount = new HashMap<String, Integer>();
		
		for (Object[] d : data) {
			MatchPlay mp = new MatchPlay();
			BJDCMatchPlayPO p = (BJDCMatchPlayPO) d[0];
			BJDCMatchPO bjdcMatchPO = (BJDCMatchPO)d[1];
			if(bjdcMatchIdSet.contains(bjdcMatchPO.getId())) {
				continue;
			} else {
				bjdcMatchIdSet.add(bjdcMatchPO.getId());
			}
			
			BeanUtils.copyProperties(bjdcMatchPO, mp);
			mp.setDefaultScore(p.getConcedePoints().floatValue());
			
			leagueNames.put(mp.getLeagueName(), mp.getLeagueName());
			mp.setPlayId(p.getPlayId());
			mp.setOptions(p.getOptions());
			mp.setOdds(p.getOdds());
			mp.setMatchId(bjdcMatchPO.getId()+"");
			
			Object[] qtMatch = findMatchPositionByLcMatch(p.getMatchId()+"");
			if(null != qtMatch) {
				String score = calFBScore(String.valueOf(qtMatch[5]), String.valueOf(qtMatch[4]));
				
				Object homePosition = (null == qtMatch[1]) ? "" : qtMatch[1];
				Object guestPosition = (null == qtMatch[2]) ? "" : qtMatch[2];
				mp.setHomeTeamPosition(String.valueOf(homePosition));
				mp.setGuestTeamPosition(String.valueOf(guestPosition));
				mp.setQtMatchStatus(Integer.valueOf(String.valueOf(qtMatch[3])));
				mp.setScore(score);
			} else {
				mp.setHomeTeamPosition("");
				mp.setGuestTeamPosition("");
				log.warn("通过大V彩赛事ID={}，没有取到球队排名和赛事比分数据.", p.getMatchId());
			}
			if(mp.getStatus() == EntityStatus.MATCH_ON_SALE) {
				mp.setStopMatch(false);
			} else {
				mp.setStopMatch(true);
			}
			
			String leagueName = bjdcMatchPO.getLeagueName();
			Integer countMatch = 0;
			if((countMatch = leagueMatchCount.get(leagueName)) != null) {
				leagueMatchCount.put(leagueName, countMatch+1);
			} else {
				leagueMatchCount.put(leagueName, 1);
			}
			
			results.add(mp);
		}
		bjdcMatchIdSet = null;
		
		//赛事对应的场次数
		map.put(MatchConstant.LEAGUES, leagueMatchCount);
		
		Map<String, String> colors = new HashMap<String, String>();
		if(null != leagueNames && leagueNames.size() > 0) {
			colors = matchColorDao.listColorsByLeagueNames(leagueNames.keySet());
		}
		for(MatchPlay mp:results){
			mp.setColor(colors.get(mp.getLeagueName()));
		}
		
		map.put(MatchConstant.MATCHS, results);
		List<IssueInfo> issues = issueService.findByPlayId(Constants.PLAY_01_BD_SPF, 15);
		map.put("issues", issues);
		return map;
	}

	protected String calFBScore(String isTurn, String score) {
    	if(StringUtils.isNotBlank(isTurn) &&
    			isTurn.equals("True") && StringUtils.isNotBlank(score)) {
    		List<String> list = Arrays.asList(score.split(":"));
    		Collections.reverse(list);
    		if(list.size() == 2) {
    			return (list.get(0) + ":" + list.get(1));
    		}
    	}
    	return score;
	}
	
	protected Map<String, Object> composeBBMatchPlay(List<Object[]> data) {
		if(null == data) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		
        List<MatchPlay> results = new ArrayList<MatchPlay>(data.size());
        Map<String,String> leagueNames = new HashMap<String, String>();
        Set<Long> bbMatchIdSet = new HashSet<Long>();
        
        //根据大V彩赛事ID，关联球探赛事数据获取球队排名
        List<String> lcMatchIdList = parseJCLQLcMatchId(data);
        log.debug("\n++++查询大V彩赛事球队的排名, \\\n\t大V彩赛事ID集合={}", lcMatchIdList);
        qtMatchPositionList = qTMatchDao.findQTBBMatchPosition(lcMatchIdList);
        Map<String,Integer> leagueMatchCount = new HashMap<String, Integer>();
        for (Object[] d : data) {
        	MatchPlay mp = new MatchPlay();
            BBMatchPlayPO p = (BBMatchPlayPO) d[0];
            BBMatchPO bbMatchPO = (BBMatchPO) d[1];
            
            if(bbMatchIdSet.contains(bbMatchPO.getId())) {
            	continue;
            } else {
            	bbMatchIdSet.add(bbMatchPO.getId());
            }
            
            BeanUtils.copyProperties(bbMatchPO, mp);
            leagueNames.put(mp.getLeagueName(), mp.getLeagueName()); 
            mp.setPlayId(p.getPlayId());
            mp.setOptions(p.getOptions());
            mp.setOdds(p.getOdds());
            mp.setPriorOdds(p.getPriorOdds());
            mp.setDefaultScore(p.getDefaultScore());
            mp.setMatchId(bbMatchPO.getId()+"");
            
            Object[] qtMatch = findMatchPositionByLcMatch(p.getMatchId()+"");
            if(null != qtMatch) {
            	mp.setQtMatchStatus(Integer.valueOf(String.valueOf(qtMatch[1])));
            	
            	String isTurn = String.valueOf(qtMatch[4]);
            	if(StringUtils.isNotBlank(isTurn) && isTurn.equals("True")) {
            		mp.setScore(String.valueOf(qtMatch[2])+":"+String.valueOf(qtMatch[3]));
            	} else {
            		mp.setScore(String.valueOf(qtMatch[3])+":"+String.valueOf(qtMatch[2]));
            	}
            	
            	//篮球排名暂时没有抓到库中
            	mp.setHomeTeamPosition("");
            	mp.setGuestTeamPosition("");
            } else {
            	mp.setHomeTeamPosition("");
            	mp.setGuestTeamPosition("");
            	log.warn("通过大V彩赛事ID={}，没有取到球队排名和赛事比分数据.", p.getMatchId());
            }
            if(mp.getStatus() == EntityStatus.MATCH_ON_SALE) {
            	mp.setStopMatch(false);
            } else {
            	mp.setStopMatch(true);
            }
            
            String leagueName = bbMatchPO.getLeagueName();
            Integer countMatch = 0;
            if((countMatch = leagueMatchCount.get(leagueName)) != null) {
            	leagueMatchCount.put(leagueName, countMatch+1);
            } else {
            	leagueMatchCount.put(leagueName, 1);
            }
            
            results.add(mp);
        }
        bbMatchIdSet = null;
        
        //赛事对应的场次数
        map.put(MatchConstant.LEAGUES, leagueMatchCount);
        
        Map<String, String> colors = new HashMap<String, String>();
        if(null != leagueNames && leagueNames.size() > 0) {
        	colors = matchColorDao.listColorsByLeagueNames(leagueNames.keySet());
        }
        for(MatchPlay mp:results){
        	mp.setColor(colors.get(mp.getLeagueName()));
        }
        
        map.put(MatchConstant.MATCHS, results);
		
		return map;
	}
	
	@Transactional(readOnly=true)
	@Override
	public Map<String, Object> findFBMatchs(MatchSelector selector) {
		if(null == selector) {
			return null;
		}
		long begin = System.currentTimeMillis();
		List<Object[]> data = fbMatchPlayDao.findMatchBySelector(
				selector.getPlayId(), 
				selector.isShowStopSell(), 
				selector.getLeagueList(), 
				selector.getPlayingTime());
		
		//如果是当天则查询在售赛程
		if(DateUtils.isSameDay(selector.getPlayingTime(), new Date())) {
			List<Object[]> onSaleMatchs = fbMatchPlayDao.findByStatus(selector.getPlayId(), 
					EntityStatus.MATCH_ON_SALE);
			data.addAll(onSaleMatchs);
		}
		
		long end = System.currentTimeMillis();
		log.debug("\n++++查询竞彩足球赛事, 耗时(毫秒)：{}", (end-begin));
		
		begin = System.currentTimeMillis();
		Map<String, Object> results = composeFBMatchPlay(data);
		end = System.currentTimeMillis();
		log.debug("\n++++组合竞彩足球赛事, 耗时(毫秒)：{}", (end-begin));
		
		return results;
	}

	@Transactional(readOnly=true)
	@Override
	public Map<String, Object> findBBMatchs(MatchSelector selector) {
		if(null == selector) {
			return null;
		}
		long begin = System.currentTimeMillis();
		List<Object[]> data = bbMatchPlayDao.findMatchBySelector(
				selector.getPlayId(), 
				selector.isShowStopSell(), 
				selector.getLeagueList(), 
				selector.getPlayingTime());
		
		//如果是当天则查询在售赛程
		if(DateUtils.isSameDay(selector.getPlayingTime(), new Date())) {
			List<Object[]> onSaleMatchs = bbMatchPlayDao.findByStatus(selector.getPlayId(), 
					EntityStatus.MATCH_ON_SALE);
			data.addAll(onSaleMatchs);
		}
		
		long end = System.currentTimeMillis();
		log.debug("\n++++查询竞彩篮球赛事, 耗时(毫秒)：{}", (end-begin));
		
		begin = System.currentTimeMillis();
		Map<String, Object> results = composeBBMatchPlay(data);
		end = System.currentTimeMillis();
		log.debug("\n++++组合竞彩篮球赛事, 耗时(毫秒)：{}", (end-begin));

		return results;
	}

	@Override
	@Transactional(readOnly=true)
	public Map<String, Object> findBJDCMatchs(MatchSelector selector) {
		if(null == selector) {
			return null;
		}
		if(StringUtils.isBlank(selector.getPlayId())){
			selector.setPlayId(Constants.PLAY_01_BD_SPF);
		}
		if(StringUtils.isBlank(selector.getIssueNum())){
			selector.setIssueNum(issueService.findOnsale(LotteryId.BJDC.name(), selector.getPlayId()));
		} 
		List<Object[]> data = bjdcMatchPlayDao.findBy(selector.getIssueNum(),selector.getPlayId());
		Map<String, Object> results = composeBJDCMatchPlay(data);
		return results;
	}
}
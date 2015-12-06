package com.xhcms.lottery.commons.persist.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xhcms.lottery.commons.data.BetMatch;
import com.xhcms.lottery.commons.data.MatchPlatform;
import com.xhcms.lottery.commons.persist.dao.MatchPlatformDao;
import com.xhcms.lottery.commons.persist.entity.MatchPlatformPO;
import com.xhcms.lottery.commons.persist.service.MatchPlatformService;
import com.xhcms.lottery.dc.data.Match;
import com.xhcms.lottery.lang.LotteryId;
import com.xhcms.lottery.lang.LotteryPlatformId;

@Service
public class MatchPlatformServiceImpl implements MatchPlatformService {
	@Autowired
	private MatchPlatformDao matchPlatformDao;
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Override
	@Transactional
	public void save(List<Match> matches,String platformId,LotteryId lotteryId){
		List<MatchPlatform> matchPlatforms = new ArrayList<MatchPlatform>();
		for (Match match : matches) {
			if(null != matchPlatformDao.getMatchPlatformPO(lotteryId.name(), platformId, match.getMatchId())){
				continue;
			}
			MatchPlatform m = new MatchPlatform();
			m.setPlatformId(platformId);
			m.setLotteryId(lotteryId.name());
			m.setMatchId(match.getMatchId());
			if(LotteryPlatformId.AN_RUI_ZHI_YING.equals(platformId)){
				m.setPlatformMatchId(match.getAnruiMatchId());
			} else {
				//其他平台补充
				continue;
			}
			matchPlatforms.add(m);
			logger.info("添加赛事平台数据：{}",m.toString());
		}
		matchPlatformDao.save(matchPlatforms);
	}
	
	@Transactional
	@Override
	public Map<Long, Long> findJcOfficial(String lotteryId, Set<Long> idSet,
			String lotteryPlatformId) {
		Map<Long, Long> map = new HashMap<Long, Long>();
		if(StringUtils.isBlank(lotteryId) || StringUtils.isBlank(lotteryPlatformId) ||
				null == idSet || idSet.size() == 0) {
			return map;
		}
		List<MatchPlatformPO> result = matchPlatformDao.findJcOfficial(lotteryId, idSet, lotteryPlatformId);
		if(null != result && result.size() > 0) {
			for(MatchPlatformPO po : result) {
				long matchId = po.getMatchId();
				long jcOfficial = po.getPlatformMatchId();
				if(matchId > 0 && jcOfficial > 0) {
					map.put(matchId, jcOfficial);
				}
			}
		}
		return map;
	}

	@Transactional
	@Override
	public Map<Long, Long> jcOfficialMatchIdList(String playId,
			List<BetMatch> matchs) {
		Map<Long, Long> jcOfficialMap = new HashMap<Long, Long>();
    	Set<Long> idSet = new HashSet<Long>(matchs.size());
        for(BetMatch m: matchs){
            idSet.add(m.getMatchId());
        }
    	if(null != matchs && matchs.size() > 0) {
    		String lotteryId = null;
    		if(playId.indexOf("ZC") > 0) {
    			lotteryId = LotteryId.JCZQ.name();
    		} else if(playId.indexOf("LC") > 0) {
    			lotteryId = LotteryId.JCLQ.name();
    		}
    		jcOfficialMap = this.findJcOfficial(lotteryId, idSet, 
    				LotteryPlatformId.AN_RUI_ZHI_YING);
    		if(idSet.size() != jcOfficialMap.size()) {
    			logger.debug("根据大V彩赛事ID查询竞彩官网赛事ID, 找不到对应关系:大V彩赛事ID={}, 竞彩官网赛事ID={}",
    					new Object[]{matchs, jcOfficialMap.values()});
    			return new HashMap<Long, Long>();
    		}
    	}
    	if(jcOfficialMap.size() > 0) {
    		for(BetMatch bm : matchs) {
    			long lcMatchId = bm.getMatchId();
    			Long jcOfficialMatchId = jcOfficialMap.get(lcMatchId);
    			bm.setJcOfficialMatchId(jcOfficialMatchId);
    		}
    	}
    	return jcOfficialMap;
	}
	
}

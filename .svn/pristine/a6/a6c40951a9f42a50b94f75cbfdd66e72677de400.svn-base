package com.xhcms.lottery.admin.persist.service.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.davcai.lottery.service.ZMJoinQtMatchService;
import com.xhcms.commons.lang.Paging;
import com.xhcms.lottery.admin.persist.service.MatchService;
import com.xhcms.lottery.commons.data.BBMatch;
import com.xhcms.lottery.commons.data.CTFBMatch;
import com.xhcms.lottery.commons.data.FBMatch;
import com.xhcms.lottery.commons.persist.dao.BBMatchDao;
import com.xhcms.lottery.commons.persist.dao.CTFBMatchDao;
import com.xhcms.lottery.commons.persist.dao.FBMatchDao;
import com.xhcms.lottery.commons.persist.dao.SystemConfDao;
import com.xhcms.lottery.commons.persist.entity.BBMatchPO;
import com.xhcms.lottery.commons.persist.entity.CTFBMatchPO;
import com.xhcms.lottery.commons.persist.entity.FBMatchPO;
import com.xhcms.lottery.conf.SystemConf;
import com.xhcms.lottery.lang.EntityStatus;
import com.xhcms.lottery.lang.LotteryId;
import com.xhcms.lottery.lang.PresetCheckStatus;

public class MatchServiceImpl implements MatchService {

	private Logger log = LoggerFactory.getLogger(getClass());
	
    @Autowired
    private FBMatchDao fbMatchDao;
    
    @Autowired
    private BBMatchDao bbMatchDao;
    
    @Autowired
    private CTFBMatchDao cTFBMatchDao;
    
    @Autowired
    private ZMJoinQtMatchService zmJoinQtMatchService;
    
    @Autowired
    private SystemConfDao systemConfDao;
    
    @Override
    @Transactional
    public void listFBMatch(Paging paging, int status, Date from, Date to, int matchResult) {
        List<FBMatchPO> data = fbMatchDao.find(paging, status, from, to, matchResult);
        List<FBMatch> results = new ArrayList<FBMatch>(data.size());
        List<Long> matchIds = new ArrayList<Long>();
        List<Object[]> matchDatas = new ArrayList<Object[]>();
        for(FBMatchPO po : data) {
        	matchIds.add(po.getId());
        }
        if(matchIds.size() > 0) {
        	matchDatas = zmJoinQtMatchService.findMatchInfoByDavcaiMatchId(matchIds, LotteryId.JCZQ);
        }
        for (FBMatchPO po : data) {
            FBMatch m = new FBMatch();
            BeanUtils.copyProperties(po, m);
            fillScoreData(m, matchDatas, LotteryId.JCZQ);
            results.add(m);
        }
        paging.setResults(results);
    }

    private void fillScoreData(Object match, List<Object[]> matchDatas, LotteryId lotteryId) {
    	if(null != matchDatas && matchDatas.size() > 0) {
    		for(Object[] obj : matchDatas) {
    			if(LotteryId.JCZQ.equals(lotteryId)) {
    				if(null != obj && obj.length == 3) {
    					FBMatch m = (FBMatch) match;
    					BigInteger matchId = (BigInteger) obj[0];
    					String score = (String) obj[1];
    					String halfScore = (String) obj[2];
    					if(null != matchId && null != m.getId() &&
    							matchId.longValue() == m.getId().longValue()) {
    						m.setCheckScore(score);
    						m.setCheckHalfScore(halfScore);
    						break;
    					}
    				}
    			} else if(LotteryId.JCLQ.equals(lotteryId)) {
    				if(null != obj && obj.length == 2) {
    					BBMatch m = (BBMatch) match;
    					BigInteger matchId = (BigInteger) obj[0];
    					String score = (String) obj[1];
    					if(null != matchId && null != m.getId() &&
    							matchId.longValue() == m.getId().longValue()) {
    						m.setCheckScore(score);
    						break;
    					}
    				}
    			}
    		}
    	}
    }
    
    @Override
    @Transactional
    public void listBBMatch(Paging paging, int status, Date from, Date to, int matchResult) {
        List<BBMatchPO> data = bbMatchDao.find(paging, status, from, to, matchResult);
        List<BBMatch> results = new ArrayList<BBMatch>(data.size());
        List<Long> matchIds = new ArrayList<Long>();
        List<Object[]> matchDatas = new ArrayList<Object[]>();
        for(BBMatchPO po : data) {
        	matchIds.add(po.getId());
        }
        if(matchIds.size() > 0) {
        	matchDatas = zmJoinQtMatchService.findMatchInfoByDavcaiMatchId(matchIds, LotteryId.JCLQ);
        }
        for (BBMatchPO po : data) {
            BBMatch m = new BBMatch();
            BeanUtils.copyProperties(po, m);
            fillScoreData(m, matchDatas, LotteryId.JCLQ);
            results.add(m);
        }
        paging.setResults(results);
    }

    @Override
    @Transactional
    public void updateFBMatch(Long mid, int status) {
        FBMatchPO po = fbMatchDao.get(mid);
        if(po == null){
        	return;
        }
        po.setStatus(status);
    }

    @Override
    @Transactional
    public void updateBBMatch(Long mid, int status) {
        BBMatchPO po = bbMatchDao.get(mid);
        if(po == null){
        	return;
        }
        po.setStatus(status);
    }
    
    @Override
    @Transactional
    public List<CTFBMatch> findByIssueNumberAndPlayId(String issueNumber, String playId) {
    	List<CTFBMatchPO> data = cTFBMatchDao.findByIssueNoAndPlayId(issueNumber, playId);
    	List<CTFBMatch> results = new ArrayList<CTFBMatch>(data.size());
    	for(CTFBMatchPO po : data) {
    		CTFBMatch m = new CTFBMatch();
    		BeanUtils.copyProperties(po, m);
            results.add(m);
    	}
    	return results;
    }

	@Override
	@Transactional
	public void updateBBMatchScore(Long id, String finalScorePreset) {
		log.info("修改竞彩篮球赛事比分,ID={},finalScorePreset={}", 
				new Object[]{id, finalScorePreset});
		BBMatchPO po = bbMatchDao.get(id);
        if(po == null){
        	return;
        }
        if(StringUtils.isNotBlank(finalScorePreset) &&
        		PresetCheckStatus.NOT_PRESET.getValue() == po.getCheckStatus() && (EntityStatus.MATCH_OVER == po.getStatus() || EntityStatus.MATCH_STOP_SELLING == po.getStatus())){
        	String finalScore = finalScorePreset.replace("：", ":").trim();
        	po.setFinalScorePreset(finalScore);
        	po.setFinalScore(finalScore);
        }
	}
	
	@Override
	@Transactional
	public void updateFBMatchScore(Long id, String halfScorePreset, String scorePreset) {
		log.info("修改竞彩足球赛事比分,ID={},halfScorePreset={},scorePreset={}", 
				new Object[]{id, halfScorePreset, scorePreset});
		FBMatchPO po = fbMatchDao.get(id);
        if(po == null){
        	return;
        }
        if(StringUtils.isNotBlank(halfScorePreset) && StringUtils.isNotBlank(scorePreset) &&
        		PresetCheckStatus.NOT_PRESET.getValue() == po.getCheckStatus() && (EntityStatus.MATCH_OVER == po.getStatus() || EntityStatus.MATCH_STOP_SELLING == po.getStatus())){
        	String halfScore = halfScorePreset.replace("：", ":").trim();
        	String score = scorePreset.replace("：", ":").trim();
        	po.setHalfScorePreset(halfScore);
        	po.setScorePreset(score);
        	
        	po.setHalfScore(halfScore);
        	po.setScore(score);
        }
	}
	
}

package com.davcai.lottery.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.davcai.lottery.dao.JCLQAnalyseDao;
import com.davcai.lottery.dao.JCZQAnalyseDao;
import com.davcai.lottery.service.ZMJoinQtMatchService;
import com.xhcms.lottery.commons.persist.dao.SystemConfDao;
import com.xhcms.lottery.conf.SystemConf;
import com.xhcms.lottery.lang.LotteryId;

public class ZMJoinQtMatchServiceImpl implements ZMJoinQtMatchService {

	@Autowired
    private JCZQAnalyseDao jczqAnalyseDao;
    
    @Autowired
    private JCLQAnalyseDao jclqAnalyseDao;
	
    @Autowired
    private SystemConfDao systemConfDao;

    @Transactional
	@Override
	public List<Object[]> findMatchInfoByDavcaiMatchId(Collection<Long> ids,
			LotteryId lotteryId) {
		List<Object[]> matchDatas = new ArrayList<Object[]>();
		Integer intervalForQtMatchJoin = systemConfDao.findIntValueByKey(SystemConf.FETCH_MATCH.INTERVAL_FOR_QT_MATCH_JOIN);
    	if(null == intervalForQtMatchJoin || intervalForQtMatchJoin < 1) {
    		intervalForQtMatchJoin = 60;
    	}
    	
		if(lotteryId == LotteryId.JCZQ) {
			matchDatas = jczqAnalyseDao.findFbMatchInfoByDavcaiMatchId(ids, intervalForQtMatchJoin);
		} else if(lotteryId == LotteryId.JCLQ) {
			matchDatas = jclqAnalyseDao.findBbMatchInfoByDavcaiMatchId(ids, intervalForQtMatchJoin);
		}
		
		return matchDatas;
	}
}

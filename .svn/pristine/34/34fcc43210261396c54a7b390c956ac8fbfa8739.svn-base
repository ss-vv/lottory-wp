package com.unison.lottery.weibo.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.unison.lottery.weibo.common.nosql.LotteryBetDao;
import com.unison.lottery.weibo.common.nosql.impl.Keys;
import com.unison.lottery.weibo.service.LotteryBetTaskService;
import com.xhcms.lottery.commons.persist.dao.BBMatchDao;
import com.xhcms.lottery.commons.persist.dao.BJDCMatchDao;
import com.xhcms.lottery.commons.persist.dao.CTFBMatchDao;
import com.xhcms.lottery.commons.persist.dao.FBMatchDao;
import com.xhcms.lottery.commons.persist.dao.IssueInfoDao;
@Service
public class LotteryBetTaskImpl implements LotteryBetTaskService{

	private Logger log=LoggerFactory.getLogger(LotteryBetTaskImpl.class);
	@Autowired
	private FBMatchDao fbMatchDao;
	@Autowired
	private BBMatchDao bbMatchDao;
	@Autowired
	private BJDCMatchDao bjdcMatchDao;
	@Autowired
	private CTFBMatchDao ctfbMatchDao;
	@Autowired
	private IssueInfoDao issueInfoDao;
	@Autowired
	private LotteryBetDao lotteryBetDao;

	private static Boolean jczq;
	private static Boolean jclq;
	private static Boolean bjdc;
	private static Boolean ctzq;
	private static Boolean ssq;
	@Transactional
	@Override
	public void isHaveMatch() {
		isHaveJCZQ();
		isHaveJCLQ();
		isHaveBJDC();
		isHaveCTZQ();
		isHaveSSQ();
	}
    @Transactional
	@Override
	public void isHaveJCZQ() {
		
		int count=fbMatchDao.findMatchCount();
		if(count>0){
			
			jczq=true;
		}else{
			jczq=false;
		}
	}
    @Transactional
	@Override
	public void isHaveJCLQ() {
		int count=bbMatchDao.findMatchCount();
		if(count>0){
			jclq=true;
		}else{
			jclq=false;
		}
	}
    @Transactional
	@Override
	public void isHaveBJDC() {
		int count=bjdcMatchDao.findMatchCount();
		if(count>0){
			bjdc=true;
		}else{
		    bjdc=false;	
		}
		
	}
    @Transactional
	@Override
	public void isHaveCTZQ() {
		int count =ctfbMatchDao.findMatchCount();
		if(count>0){
			ctzq=true;
		}else{
			ctzq=false;
		}
		
	}
    @Transactional
	@Override
	public void isHaveSSQ() {
		int count=issueInfoDao.isHaveSSQ();
		if(count>0){
			ssq=true;
		}else{
			ssq=false;
		}
		
	}
    @Transactional
    @Override
	public void initBetNum() {
		
    	isHaveMatch();
		lotteryBetDao.set(Keys.getJCZQKey(), "0");
		lotteryBetDao.set(Keys.getJCLQKey(), "0");
		lotteryBetDao.set(Keys.getBJDCKey(), "0");
		lotteryBetDao.set(Keys.getCTZQKey(), "0");
		lotteryBetDao.set(Keys.getSSQKey(), "0");
		log.info("...................init");
		
	}

	public static Boolean getJczq() {
		return jczq;
	}

	public static Boolean getJclq() {
		return jclq;
	}

	public static Boolean getBjdc() {
		return bjdc;
	}

	public static Boolean getCtzq() {
		return ctzq;
	}

	public static Boolean getSsq() {
		return ssq;
	}
	

	

}

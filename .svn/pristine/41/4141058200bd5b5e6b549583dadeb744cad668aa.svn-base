package com.xhcms.lottery.commons.persist.dao.impl;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.xhcms.lottery.commons.data.IssueInfo;
import com.xhcms.lottery.commons.persist.DbUnitTestIssueBase;
import com.xhcms.lottery.commons.persist.dao.IssueInfoDao;
import com.xhcms.lottery.commons.persist.entity.IssueInfoPO;
import com.xhcms.lottery.lang.PlayType;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test-issue-spring.xml")
public class IssueinfoDaoImplTest extends DbUnitTestIssueBase {

	@Autowired
	IssueInfoDao issueinfoDao;
	
	@Transactional
	@Test
	public void testFindListByLotteryIdAndIssueNumber() {
		
		List<IssueInfo> issueinfos=new ArrayList<IssueInfo>();
		IssueInfo issueinfo1=new IssueInfo();
		issueinfo1.setIssueNumber("12090501");
		issueinfo1.setLotteryId("JX11");
		issueinfos.add(issueinfo1);
		
		
		IssueInfo issueinfo2=new IssueInfo();
		issueinfo2.setIssueNumber("12090502");
		issueinfo2.setLotteryId("JX11");
		issueinfos.add(issueinfo2);
		
		IssueInfo issueinfo3=new IssueInfo();
		issueinfo3.setIssueNumber("12090507");
		issueinfo3.setLotteryId("JX11");
		issueinfos.add(issueinfo3);
		
		IssueInfo issueinfo4=new IssueInfo();
		issueinfo4.setIssueNumber("");
		issueinfo4.setLotteryId("JX11");
		issueinfos.add(issueinfo4);
		
		List<IssueInfoPO> results=issueinfoDao.findListByLotteryIdAndIssueNumber(issueinfos);
		System.out.println("results="+results);
		if(null!=results&&!results.isEmpty()){
			for(IssueInfoPO po:results){
				System.out.println("po="+po);
			}
		}
		
	}
	
	
	@Transactional
	@Test
	@Rollback(value=true)
	public void testBatchSaveOrUpdate() {
		
		List<IssueInfoPO> issueinfoPOsShouldUpdate=new ArrayList<IssueInfoPO>();
		IssueInfoPO issueinfoPO1=new IssueInfoPO();
		issueinfoPO1.setIssueNumber("12090501");
		issueinfoPO1.setLotteryId("JX11");
		issueinfoPO1.setBonusCode("01 02 03 04 05");
		issueinfoPO1.setBonusInfo("");
		issueinfoPO1.setCloseTime(new Date());
		issueinfoPO1.setId(1L);
		issueinfoPO1.setStartTime(new Date());
		issueinfoPO1.setStatus(3);
		issueinfoPO1.setStopTime(new Date());
		issueinfoPO1.setStopTimeForUser(new Date());
		issueinfoPO1.setPlayId(PlayType.UNKNOWN.getShortPlayStr());
		issueinfoPOsShouldUpdate.add(issueinfoPO1);
		
		IssueInfoPO issueinfoPO2=new IssueInfoPO();
		issueinfoPO2.setIssueNumber("12090507");
		issueinfoPO2.setLotteryId("JX11");
		issueinfoPO2.setBonusCode("01 02 03 04 05");
		issueinfoPO2.setBonusInfo("1");
		issueinfoPO2.setCloseTime(new Date());
		issueinfoPO2.setId(5L);
		issueinfoPO2.setStartTime(new Date());
		issueinfoPO2.setStatus(4);
		issueinfoPO2.setStopTime(new Date());
		issueinfoPO2.setStopTimeForUser(new Date());
		issueinfoPO2.setPlayId(PlayType.UNKNOWN.getShortPlayStr());
		issueinfoPOsShouldUpdate.add(issueinfoPO2);
		
		List<IssueInfoPO> issueinfoPOsShouldInsert=new ArrayList<IssueInfoPO>();
		IssueInfoPO issueinfoPO3=new IssueInfoPO();
		issueinfoPO3.setIssueNumber("12090502");
		issueinfoPO3.setLotteryId("JX11");
		issueinfoPO3.setBonusCode("01 11 03 04 05");
		issueinfoPO3.setBonusInfo("2");
		issueinfoPO3.setCloseTime(new Date());
		issueinfoPO3.setStartTime(new Date());
		issueinfoPO3.setStatus(2);
		issueinfoPO3.setStopTime(new Date());
		issueinfoPO3.setStopTimeForUser(new Date());
		issueinfoPO3.setPlayId(PlayType.UNKNOWN.getShortPlayStr());
		issueinfoPOsShouldInsert.add(issueinfoPO3);
		
		
		
		issueinfoDao.batchSaveOrUpdate(issueinfoPOsShouldUpdate, issueinfoPOsShouldInsert);
		
		
	}
	
	@Transactional
	@Test
	@Rollback(value=false)
	public void testFindValidSalingTimeByLotteryId() {
		Calendar calendar=Calendar.getInstance();
		calendar.set(2012, 8, 13);
		Date targetDate=calendar.getTime();
		IssueInfoPO  validSalingTime =issueinfoDao.findValidSalingTimeByLotteryId("JX11",targetDate);
		assertTrue(null!=validSalingTime);
		System.out.println("validSalingTime="+validSalingTime);
	}
	
	@Transactional
	@Test
	@Rollback(value=false)
	public void testFindValidIssueByLotteryIdBetweenStartTimeAndCloseTime(){
		Calendar calendar=Calendar.getInstance();
		calendar.set(2012, 8, 13, 10, 5, 10);
		Date targetDate=calendar.getTime();
		IssueInfoPO  po =issueinfoDao.findValidIssueByLotteryIdBetweenStartTimeAndStopTimeForUser(targetDate, "JX11");
		assertTrue(null!=po);
		System.out.println("po="+po);
	}
	
	@Transactional
	@Test
	@Rollback(value=false)
	public void testUpdateLCStatusFromZMStatus(){
		List<String> targetLotteryIdList=new ArrayList<String>();
		targetLotteryIdList.add("JX11");
		issueinfoDao.updateLCStatusFromZMStatus(targetLotteryIdList);
	}
	
	@Transactional
	@Test
	@Rollback(value=false)
	public void testFindDistinctLotteryId(){
		Calendar calendar=Calendar.getInstance();
		calendar.set(2012, 8, 13, 10, 5, 10);
		Date targetDate=calendar.getTime();
		List<String> targetLotteryIdList=new ArrayList<String>();
		targetLotteryIdList.add("JX11");
		List<String> lotteryIds=issueinfoDao.findValidDistinctLotteryIdInExpectedListByDate(targetDate,targetLotteryIdList);
		assertTrue(!lotteryIds.isEmpty());
		for(String lotteryId:lotteryIds){
			System.out.println("lotteryId="+lotteryId);
		}
		
	}
	
	@Transactional
	@Test
	@Rollback(value=false)
	public void testFindValidIssueRecentlyByLotteryId(){
		Calendar calendar=Calendar.getInstance();
		calendar.set(2012, 8, 13, 10, 10, 1);
		Date targetDate=calendar.getTime();
		IssueInfoPO  po =issueinfoDao.findValidIssueRecentlyByLotteryId(targetDate,  "JX11");
		System.out.println("po="+po);
		assertTrue(null!=po);
		assertTrue(po.getIssueNumber().equals("12091308"));
		
	}
	
	
	@Transactional
	@Test
	@Rollback(value=false)
	public void testBatchUpdateLCStatus(){
		
		List<IssueInfoPO> salingIssueList=new ArrayList<IssueInfoPO>();
		IssueInfoPO po1=new IssueInfoPO();
		po1.setId(231L);
		salingIssueList.add(po1);
		IssueInfoPO po2=new IssueInfoPO();
		po2.setId(258L);
		salingIssueList.add(po2);
		issueinfoDao.batchUpdateLCStatus2Saling(salingIssueList);
		
		
	}
	
}

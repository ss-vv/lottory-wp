package com.xhcms.lottery.commons.persist.service.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xhcms.lottery.commons.data.IssueInfo;
import com.xhcms.lottery.commons.persist.DbUnitTestIssueBase;
import com.xhcms.lottery.commons.persist.entity.IssueInfoPO;
import com.xhcms.lottery.commons.persist.service.BetException;
import com.xhcms.lottery.commons.persist.service.IssueService;
import com.xhcms.lottery.lang.AppCode;
import com.xhcms.lottery.lang.Constants;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test-issue-spring.xml")
public class IssueServiceImplTest extends DbUnitTestIssueBase {
    
	@Autowired
	private IssueService issueService;
	private final String issue = "12091355";
	private final String lotteryId = Constants.JX11;
	
	@Test
	public void findByIssueHasResult() {
		IssueInfo issueInfo = issueService.findByIssue(lotteryId, issue);
		assertNotNull(issueInfo);
	}
	
	@Test
	public void whenBeforeValidSaleTimeThenReturnTheFirstValidIssueInCurrentDate(){
		Calendar calendar=Calendar.getInstance();
		calendar.set(2012, 8, 13, 8, 5, 10);//2012-09-13 08:05:10
		Date targetDate=calendar.getTime();
		IssueInfoPO po=issueService.getCurrentSalingIssueByLotteryId(lotteryId, targetDate);
		assertTrue(po.getIssueNumber().equals("12091301"));
		
	}
	
	@Test
	public void whenAfterValidSaleTimeThenReturnTheFirstValidIssueInNextDate(){
		Calendar calendar=Calendar.getInstance();
		calendar.set(2012, 8, 13, 22, 5, 10);//2012-09-13 22:05:10
		Date targetDate=calendar.getTime();
		IssueInfoPO po=issueService.getCurrentSalingIssueByLotteryId(lotteryId, targetDate);
		assertTrue(po.getIssueNumber().equals("12091401"));
		
	}
	
	/**
	 * 当前时间在某一有效期信息的开始和结束时间内，则该期为当前有效期
	 */
	@Test
	public void whenCurrentTimeBetweenStartTimeAndStopTimeForUserThenReturnIssueInfoThatHaveSameStartTimeAndStopTimeForUser(){
		Calendar calendar=Calendar.getInstance();
		calendar.set(2012, 8, 13, 21, 25, 10);//2012-09-13 21:25:10
		Date targetDate=calendar.getTime();
		IssueInfoPO po=issueService.getCurrentSalingIssueByLotteryId(lotteryId, targetDate);
		System.out.println("po="+po);
		assertTrue(null!=po);
		assertTrue(po.getIssueNumber().equals("12091375"));
		
	}
	/**
	 * 当前时间大于某一有效期信息a的结束时间，小于另一有效期b的开始时间,
	 * 则返回b作为有效期
	 */
	@Test	
	public void whenCurrentTimeAfterOneValidIssueinfoStopTimeForUserAndBeforeAnotherValidIssueinfoStartTimeThenReturnTheAnotherIssueInfo(){
		Calendar calendar=Calendar.getInstance();
		calendar.set(2012, 8, 13, 21, 29, 51);//2012-09-13 21:29:51
		Date targetDate=calendar.getTime();
		IssueInfoPO po=issueService.getCurrentSalingIssueByLotteryId(lotteryId, targetDate);
		System.out.println("po="+po);
		assertTrue(null!=po);
		assertTrue(po.getIssueNumber().equals("12091376"));
		
	}
	
	/**
	 * 当前时间不在任何有效的期信息的开始和结束时间段内
	 * 返回当前时间之后离的最近的有效期作为当前有效期
	 */
	@Test	
	public void whenCurrentTimeNotBetweenAllValidStartTimeAndCloseTimeThenReturnNextValidIssueInfo(){
		Calendar calendar=Calendar.getInstance();
		calendar.set(2012, 8, 13, 21, 17, 51);//2012-09-13 21:17:51
		Date targetDate=calendar.getTime();
		IssueInfoPO po=issueService.getCurrentSalingIssueByLotteryId(lotteryId, targetDate);
		System.out.println("po="+po);
		assertTrue(null!=po);
		assertTrue(po.getIssueNumber().equals("12091375"));
		
	}
	
	/**
	 * 得到某个时间点的所有有效期
	 */
	@Test	
	public void testFindAllValidSalingIssueinfoByTime(){
		Calendar calendar=Calendar.getInstance();
		calendar.set(2012, 8, 13, 10, 1, 1);//2012-09-13 10:01:01
		Date targetDate=calendar.getTime();
		List<IssueInfoPO> poList=issueService.getAllHFCurrentSalingIssue(targetDate);
		
		assertTrue(null!=poList);
		for(IssueInfoPO po:poList){
			System.out.println("po="+po);
		}
		
		
	}
	
	/**
	 * 如果指定时间没有超过当天最晚的StopTimeForUser，
	 * 则返回有效，并且LCStatus=1的期信息，
	 * @throws BetException 
	 * 
	 * 
	 */
	@Test
	public void whenTargetDateBeforeStopTimeForUserThenReturnIssueThatLCStatusEqualsOneAndIsValid() throws BetException{
		Calendar calendar=Calendar.getInstance();
		calendar.set(2012, 8, 13, 21, 55, 0);//2012-09-13 21:55:00
		Date targetDate=calendar.getTime();
		
		IssueInfo issueInfo = null;
		try {
			issueInfo = issueService.getCurrentSalingIssueForShow(lotteryId, targetDate);
			assertTrue(issueInfo.getIssueNumber().equals("12091378"));
		}
		catch (BetException be) {
			assertTrue(be.getErrorCode() == AppCode.CURRENT_SALING_ISSUE_FOR_SHOW_NOT_FOUND);
		}
		
		if (issueInfo == null) {
			System.out.println("没有当前销售期!");
		}
		else {
			System.out.println("issueInfo = " + issueInfo);
		}
	}
	
	/**
	 * 如果指定时间没有超过当天最晚的StopTimeForUser，
	 * 并且找不到有效并且LCStatus=1的期信息，则抛出 
	 * errorCode为AppCode.CURRENT_SALING_ISSUE_FOR_SHOW_NOT_FOUND的异常
	 */
	@Test
	public void whenTargetDateBeforeStopTimeForUserAndHaveAnyIssueThatLCStatusEqualsOneAndIsValidThenThrowsException(){
		Calendar calendar=Calendar.getInstance();
		calendar.set(2012, 8, 15, 10, 55, 0);//2012-09-15 10:55:00
		Date targetDate=calendar.getTime();
		try{
			issueService.getCurrentSalingIssueForShow(lotteryId, targetDate);
		}
		catch(BetException be){
			assertTrue(be.getErrorCode()==AppCode.CURRENT_SALING_ISSUE_FOR_SHOW_NOT_FOUND);
		}
		
		
		
	}
	
	/**
	 * 如果指定时间超过当天最晚的StopTimeForUser，
	 * 根据这个彩种指定时间当天第一期的信息推断出第二天第
	 * 	一期的期号和相应的时间信息
	 * @throws BetException 
	 */
	
	@Test
	public void whenTargetDateAfterStopTimeForUserThenReturnIssueThatIssueNumberIsConstructedByFirstIssueThatStartTimeAndTargetDateIsSameDay() throws BetException {
		Calendar calendar=Calendar.getInstance();
		calendar.set(2012, 8, 13, 22, 0, 0);//2012-09-13 22:00:00
		Date targetDate=calendar.getTime();
		
		calendar.set(2012, 8, 14, 9, 0, 5);//2012-09-14 09:00:05
		Date expectedStartTime=calendar.getTime();
		System.out.println("expectedStartTime="+expectedStartTime);
		
		calendar.set(2012, 8, 14, 9, 6, 50);//2012-09-14 09:06:50
		Date expectedStopTime=calendar.getTime();
		System.out.println("expectedStopTime="+expectedStopTime);
		
		IssueInfo issueInfo = null;
		try {
			issueInfo = issueService.getCurrentSalingIssueForShow(lotteryId, targetDate);
			assertTrue(issueInfo.getIssueNumber().equals("12091401"));
		}
		catch (BetException be) {
			assertTrue(be.getErrorCode() == AppCode.CURRENT_SALING_ISSUE_FOR_SHOW_NOT_FOUND);
		}

		if (issueInfo == null) {
			System.out.println("没有当前销售期!");
		}
		else {
			System.out.println("issueInfo = " + issueInfo);
		}

		//assertTrue(issueInfo.getStartTime().equals(expectedStartTime));
		//assertTrue(issueInfo.getStopTime().equals(expectedStopTime));
	}
	
	/**
	 * 如果指定时间超过当天最晚的StopTimeForUser，
	 * 但是没有找到当天的第一期信息，
	 * 则抛出 
	 * errorCode为AppCode.CURRENT_SALING_ISSUE_FOR_SHOW_NOT_FOUND的异常
	 */
	@Test
	public void whenTargetDateAfterStopTimeForUserAndHaveNoFirstIssueInThatDayThenThrowsException(){
		Calendar calendar=Calendar.getInstance();
		calendar.set(2012, 8, 12, 10, 55, 0);//2012-09-12 10:55:00
		Date targetDate=calendar.getTime();
		try{
			issueService.getCurrentSalingIssueForShow(lotteryId, targetDate);
		}
		catch(BetException be){
			assertTrue(be.getErrorCode()==AppCode.CURRENT_SALING_ISSUE_FOR_SHOW_NOT_FOUND);
		}
	}
	
	/**
	 * 如果在指定时间当天没有期信息，
	 * 则抛出 
	 * errorCode为AppCode.CURRENT_SALING_ISSUE_FOR_SHOW_NOT_FOUND的异常
	 */
	@Test
	public void whenHaveNoIssueInTargetDateThenThrowsException(){
		Calendar calendar=Calendar.getInstance();
		calendar.set(2012, 8, 26, 10, 55, 0);//2012-09-12 10:55:00
		Date targetDate=calendar.getTime();
		try{
			issueService.getCurrentSalingIssueForShow(lotteryId, targetDate);
		}
		catch(BetException be){
			assertTrue(be.getErrorCode()==AppCode.CURRENT_SALING_ISSUE_FOR_SHOW_NOT_FOUND);
		}
	}
	
	
}

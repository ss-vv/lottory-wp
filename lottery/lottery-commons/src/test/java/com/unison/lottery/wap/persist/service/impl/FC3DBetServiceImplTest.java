package com.unison.lottery.wap.persist.service.impl;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.data.DigitalBetRequest;
import com.xhcms.lottery.commons.data.IssueInfo;
import com.xhcms.lottery.commons.persist.service.BetException;
import com.xhcms.lottery.commons.persist.service.DigitalBetService;
import com.xhcms.lottery.commons.persist.service.IssueService;
import com.xhcms.lottery.lang.Constants;
import com.xhcms.lottery.lang.LotteryId;
import com.xhcms.lottery.lang.PlayType;

/**
 * @desc 福彩3D投注功能测试用例
 * @author lei.li@lai310.com
 * @createTime 2014-9-3
 * @version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "/test-bet-service-spring.xml")
public class FC3DBetServiceImplTest extends BetServiceTestBase {

	@Autowired
	private DigitalBetService betService;

	private DigitalBetRequest betRequest;

	private long userId;

	private String issueNumber;

	@Autowired
	private IssueService issueService;

	@Before
	public void setup() throws Exception {
		if (StringUtils.isBlank(issueNumber)) {
			IssueInfo issue = issueService.getCurrentOnSalingIssue(
					LotteryId.FC3D.name(), new Date());
			issueNumber = issue.getIssueNumber();
		}
		userId = 1676;
		betRequest = new DigitalBetRequest();
		betRequest.setUserId(userId);
		betRequest.setLotteryId(Constants.FC3D);
		betRequest.setIssue(issueNumber);
		betRequest.setMultiple(1);
	}

	/** 每种玩法投注功能测试 */
	/** 直选单式 */
	@Test
	public void testBetOfFC3D_ZXDS() throws Exception {
		betRequest.setPlayType(PlayType.FC3D_ZXDS);
		List<String> list = new ArrayList<String>();
		list.add("3,3,3;1,2,9;3,3,4;3,3,4;3,2,4;1,3,7;3,2,6");
		list.add("3,3,1");
//		list.add("3,3,10");
		betDoWork(list);
	}

	/** 直选复式 */
	@Test
	public void testBetOfFC3D_ZXFS() throws Exception {
		betRequest.setPlayType(PlayType.FC3D_ZXFS);
		List<String> list = new ArrayList<String>();
		list.add("02,01,01;0123456789,2,13");
		list.add("01,2,3");
		// list.add("1,2,3");
		betDoWork(list);
	}

	/** 直选和值 */
	@Test
	public void testBetOfFC3D_ZXHZ() throws Exception {
		betRequest.setPlayType(PlayType.FC3D_ZXHZ);
		List<String> list = new ArrayList<String>();
		list.add("1;11;27;21");
		betDoWork(list);
	}

	/** 组选单式 */
	@Test
	public void testBetOfFC3D_ZX_DS() throws Exception {
		betRequest.setPlayType(PlayType.FC3D_ZX_DS);
		List<String> list = new ArrayList<String>();
		list.add("3,8,9;3,3,8");
		betDoWork(list);
	}

	/** 组三复式 */
	@Test
	public void testBetOfFC3D_Z3FS() throws Exception {
		betRequest.setPlayType(PlayType.FC3D_Z3FS);
		List<String> list = new ArrayList<String>();
		list.add("0,1,2,3,4,5,6,7,8,9;1,2,3");
		list.add("0,1");
		betDoWork(list);
	}

	/** 组三和值 */
	@Test
	public void testBetOfFC3D_Z3HZ() throws Exception {
		betRequest.setPlayType(PlayType.FC3D_Z3HZ);
		List<String> list = new ArrayList<String>();
		list.add("1;3;26");
		betDoWork(list);
	}

	/** 组六复式 */
	@Test
	public void testBetOfFC3D_Z6FS() throws Exception {
		betRequest.setPlayType(PlayType.FC3D_Z6FS);
		List<String> list = new ArrayList<String>();
		list.add("0,1,2,3,4,5,6,7,8,9");
		betDoWork(list);
	}

	/** 组六和值 */
	@Test
	public void testBetOfFC3D_Z6HZ() throws Exception {
		betRequest.setPlayType(PlayType.FC3D_Z6HZ);
		List<String> list = new ArrayList<String>();
		list.add("3;4;5");
		betDoWork(list);
	}

	/** 单选包号 */
	@Test
	public void testBetOfFC3D_DXBH() throws Exception {
		betRequest.setPlayType(PlayType.FC3D_DXBH);
		List<String> list = new ArrayList<String>();
		list.add("0,9");
//		 list.add("1,1;1,9");
		 list.add("0,1,2,3,4,5,6,7,8,9");
		betDoWork(list);
	}

	public void betDoWork(List<String> list) {
		for (String betCode : list) {
			String[] betContent = new String[] { betCode };
			betRequest.setBetContents(Arrays.asList(betContent));
			whenBetThenSaveBetScheme();
		}
	}

	// 保存投注单
	public void whenBetThenSaveBetScheme() throws BetException {
		BetScheme betScheme = betService.bet(betRequest);
		BetScheme loadedScheme = betService.getSchemeById(betScheme.getId());
		assertNotNull(loadedScheme);
	}
}
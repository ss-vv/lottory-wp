package com.unison.lottery.wap.persist.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xhcms.lottery.commons.data.Account;
import com.xhcms.lottery.commons.data.BetPartner;
import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.data.ChooseType;
import com.xhcms.lottery.commons.data.DigitalBetContent;
import com.xhcms.lottery.commons.data.IssueInfo;
import com.xhcms.lottery.commons.data.DigitalBetRequest;
import com.xhcms.lottery.commons.data.Ticket;
import com.xhcms.lottery.commons.persist.service.AccountService;
import com.xhcms.lottery.commons.persist.service.BetException;
import com.xhcms.lottery.commons.persist.service.IssueService;
import com.xhcms.lottery.commons.persist.service.DigitalBetService;
import com.xhcms.lottery.lang.Constants;
import com.xhcms.lottery.lang.PlayType;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value="/test-bet-service-spring.xml")
public class JX11BetServiceImplTest extends BetServiceTestBase {

	@Autowired
	private DigitalBetService betService;

	@Autowired
	private AccountService accountService;
	
	@Autowired
	private IssueService issueService;
		
	private DigitalBetRequest betRequest;
	private long userId;

	private String issueNumber;
	
	@Before
	public void setup() throws Exception{
		super.setup();
		userId = 213;
		issueNumber = "12091359";
		modifyIssueCloseTime();
		betRequest = new DigitalBetRequest();
		betRequest.setUserId(userId);
		betRequest.setLotteryId(Constants.JX11);
		betRequest.setPlayType(PlayType.JX11_R1);
		betRequest.setChooseType(ChooseType.MANUAL);
		betRequest.setIssue(issueNumber);
		betRequest.setBetContents(Arrays.asList(new String[]{"7,8,9"}));
		betRequest.setMultiple(3);
		betRequest.setTotalNotesCount(3);	// 前1任选只能一票一号。
		betRequest.setMoney(3*3*2);
	}
	
	private void modifyIssueCloseTime() {
		IssueInfo issue = issueService.findByIssue(Constants.JX11, issueNumber);
		issue.setCloseTime(org.apache.commons.lang.time.DateUtils.addHours(new Date(), 1));
		issueService.saveOrUpdate(Arrays.asList(new IssueInfo[]{issue}));
	}

	// 保存投注单
	@Test
	public void whenBetThenSaveBetScheme() throws BetException {
		BetScheme betScheme = betService.bet(betRequest);
		BetScheme loadedScheme = betService.getSchemeById(betScheme.getId());
		assertNotNull(loadedScheme);
	}
	
	// 保存彩票信息
	@Test
	public void whenBetThenSaveTickets() throws BetException {
		BetScheme betScheme = betService.bet(betRequest);
		List<Ticket> tickets = betService.listTicketsOfScheme(betScheme.getId());
		assertEquals(3, tickets.size());
	}
	
	@Test
	public void whenBetThenSaveTicketsWithIssueNumber() throws BetException {
		BetScheme betScheme = betService.bet(betRequest);
		List<Ticket> tickets = betService.listTicketsOfScheme(betScheme.getId());
		assertEquals(3, tickets.size());
		assertFalse(StringUtils.isEmpty(tickets.get(0).getIssue()));
	}
	
	// 保存方案所选投注内容
	@Test
	public void whenBetThenSaveBetContent() throws BetException {
		BetScheme betScheme = betService.bet(betRequest);
		List<DigitalBetContent> betOptions = betService.findHfBetContent(betScheme.getId());
		assertEquals(1, betOptions.size());
		DigitalBetContent opt = betOptions.get(0);
		assertEquals("should store choose type.", 0, opt.getChooseType());
		assertTrue("should store issue id.", 0 != opt.getIssueId());
		assertEquals("should store issue number.", issueNumber, opt.getIssueNumber());
	}

	// 保存投注人信息
	@Test
	public void whenBetThenSaveBetPartner() throws BetException {
		BetScheme betScheme = betService.bet(betRequest);
		List<BetPartner> partners = betService.findBetPartners(betScheme.getId());
		assertEquals(1, partners.size());
	}
	
	// 冻结资金
	@Test
	public void whenBetThenFreezeMoney() throws BetException {
		BetScheme betScheme = betService.bet(betRequest);
		Account account = accountService.getAccount(userId);
		int totalMoney = betScheme.getTotalAmount();
		if (account.getGrant().compareTo(BigDecimal.ZERO)>0){
			assertTrue(account.getFrozenGrant().compareTo(BigDecimal.valueOf(totalMoney))==0);
		}else{
			assertTrue(account.getFrozenFund().compareTo(BigDecimal.valueOf(totalMoney))==0);
		}
	}

}

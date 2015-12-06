package com.xhcms.lottery.commons.utils.internal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.xhcms.lottery.commons.data.Bet;
import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.data.CTBetRequest;
import com.xhcms.lottery.commons.data.ChooseType;
import com.xhcms.lottery.commons.data.Ticket;
import com.xhcms.lottery.commons.data.ctfb.CTBetContent;
import com.xhcms.lottery.commons.persist.entity.IssueInfoPO;
import com.xhcms.lottery.commons.persist.service.BetException;
import com.xhcms.lottery.commons.persist.service.impl.CTBetServiceImpl;
import com.xhcms.lottery.lang.Constants;
import com.xhcms.lottery.lang.PlayType;

/**
 * 传统足彩的投注策略器。
 * 
 * @author Yang Bo
 */
public class CTBetStrategyTest {

	private BetScheme scheme;
	private long userId;
	private CTBetServiceImpl service = new CTBetServiceImpl();
	private CTBetStrategy betStrategy = new CTBetStrategy();

	@Before
	public void setup() throws BetException {
		userId = 77;
	}
	
	@Test
	public void testMatch() {
		assertTrue(betStrategy.match(PlayType.CTZC_14.getPlayId()));
		assertTrue(betStrategy.match(PlayType.CTZC_R9.getPlayId()));
		assertTrue(betStrategy.match(PlayType.CTZC_BQ.getPlayId()));
		assertTrue(betStrategy.match(PlayType.CTZC_JQ.getPlayId()));
	}

	@Test
	public void testSF14ManualNotes() throws BetException{
		setupScheme(PlayType.CTZC_14, 
				ChooseType.MANUAL, new String[]{
				"310,3,1,0,3,1,0,3,1,0,3,1,0,30"});
		Bet bet = betStrategy.resolve(scheme);
		assertBetNotesTickets(bet, 6, 1);
	}

	private void assertBetNotesTickets(Bet bet, int notes, int tickets) {
		assertEquals("expected notes is (" + notes + ") but is (" + bet.getNote() + ")", 
				notes, bet.getNote());
		int actualTickets = bet.getTickets().size();
		assertEquals("expected tickets is (" + tickets + ") but is (" + actualTickets + ")",
				tickets, actualTickets);
		List<Ticket> ticketList = bet.getTickets();
		for (Ticket ticket : ticketList){
			assertEquals(ticket.getNote()*2, ticket.getMoney());
			assertTrue("单票不能超过2万元", ticket.getMoney()<=Constants.MAX_MONEY_PER_TICKET);
		}
	}

	private void setupScheme(PlayType playType, ChooseType chooseType, String[] betOptions) throws BetException {
		IssueInfoPO issueInfo = makeIssueInfo();
		CTBetRequest betContent = makeBetContent(playType, chooseType, betOptions);
		scheme = service.makeBetScheme(userId, betContent, issueInfo);
	}

	private void setupScheme(PlayType playType, ChooseType chooseType, String[] betOptions, String dan) throws BetException {
		setupScheme(playType, chooseType, betOptions);
		for (CTBetContent bt : scheme.getCtBetContents()){
			bt.setSeed(dan);
		}
	}
	
	private IssueInfoPO makeIssueInfo() {
		IssueInfoPO issueInfo = new IssueInfoPO();
		issueInfo.setCloseTime(new Date());
		return issueInfo;
	}

	/**
	 * 只是设置closeTime
	 * @param betOptions 投注选项，比如: 01,02;03,04
	 * @param playType 玩法类型
	 * @param chooseType 选择类型
	 * 
	 * @return
	 */
	private CTBetRequest makeBetContent(PlayType playType, ChooseType chooseType, String[] betOptions) {
		CTBetRequest request = new CTBetRequest();
		request.setLotteryId(Constants.CTZC);
		request.setPlayType(playType);
		request.setChooseType(chooseType);
		request.setIssue("12091371");
		request.setBetContents(Arrays.asList(betOptions));
		request.setMultiple(1);
		request.setTotalNotesCount(1);
		request.setMoney(2);
		return request;
	}

	// ------------- 拆票测试 ---------------
	
	/**
	 * 一票最多5注
	 */
	@Test
	public void oneTicketMax5Notes() throws BetException{
		setupScheme(PlayType.CTZC_14, 
				ChooseType.MACHINE, 
				new String[]{
				"3,3,1,0,3,1,0,3,1,0,3,1,0,3",
				"3,3,1,0,3,1,0,3,1,0,3,1,0,3",
				"3,3,1,0,3,1,0,3,1,0,3,1,0,3",
				"3,3,1,0,3,1,0,3,1,0,3,1,0,3",
				"3,3,1,0,3,1,0,3,1,0,3,1,0,3",
				"3,3,1,0,3,1,0,3,1,0,3,1,0,3",
				});
		Bet bet = betStrategy.resolve(scheme);
		assertBetNotesTickets(bet, 6, 2);
		
		assertEquals("3,3,1,0,3,1,0,3,1,0,3,1,0,3;3,3,1,0,3,1,0,3,1,0,3,1,0,3;3,3,1,0,3,1,0,3,1,0,3,1,0,3;3,3,1,0,3,1,0,3,1,0,3,1,0,3;3,3,1,0,3,1,0,3,1,0,3,1,0,3", 
				bet.getTickets().get(0).getCode());
		assertEquals("3,3,1,0,3,1,0,3,1,0,3,1,0,3", 
				bet.getTickets().get(1).getCode());
		Ticket ticket = bet.getTickets().get(0);
		assertTrue(ticket.getCode().equals(ticket.getActualCode()));
		
		setupScheme(PlayType.CTZC_14, 
				ChooseType.MACHINE, 
				new String[]{
				"3,3,1,0,3,1,0,3,1,0,3,1,0,3",
				"3,3,1,0,3,1,0,3,1,0,3,1,0,3",
				"3,3,1,0,3,1,0,3,1,0,3,1,0,3",
				"3,3,1,0,3,1,0,3,1,0,3,1,0,3",
				"3,3,1,0,3,1,0,3,1,0,3,1,0,3",
				});
		bet = betStrategy.resolve(scheme);
		assertBetNotesTickets(bet, 5, 1);

		setupScheme(PlayType.CTZC_R9, 
				ChooseType.MACHINE, 
				new String[]{
				"3,3,1,0,3,1,0,3,1,-,-,-,-,-",
				"3,3,1,0,3,1,0,3,1,-,-,-,-,-",
				"3,3,1,0,3,1,0,3,1,-,-,-,-,-",
				"3,3,1,0,3,1,0,3,1,-,-,-,-,-",
				"3,3,1,0,3,1,0,3,1,-,-,-,-,-",
				"3,3,1,0,3,1,0,3,1,-,-,-,-,-",
				"3,3,1,0,3,1,0,3,1,-,-,-,-,-",
				"3,3,1,0,3,1,0,3,1,-,-,-,-,-",
				"3,3,1,0,3,1,0,3,1,-,-,-,-,-",
				"3,3,1,0,3,1,0,3,1,-,-,-,-,-",
				"3,3,1,0,3,1,0,3,1,-,-,-,-,-",
				"3,3,1,0,3,1,0,3,1,-,-,-,-,-",
				});
		bet = betStrategy.resolve(scheme);
		assertBetNotesTickets(bet, 12, 3);

		setupScheme(PlayType.CTZC_JQ, 
				ChooseType.MACHINE, 
				new String[]{
				"3,3,1,0,3,1,0,3",
				"3,3,1,0,3,1,0,3",
				"3,3,1,0,3,1,0,3",
				"3,3,1,0,3,1,0,3",
				"3,3,1,0,3,1,0,3",
				"3,3,1,0,3,1,0,3",
				"3,3,1,0,3,1,0,3",
				"3,3,1,0,3,1,0,3",
				"3,3,1,0,3,1,0,3",
				"3,3,1,0,3,1,0,3",
				"3,3,1,0,3,1,0,3",
				"3,3,1,0,3,1,0,3",
				"3,3,1,0,3,1,0,3",
				"3,3,1,0,3,1,0,3",
				"3,3,1,0,3,1,0,3",
				"3,3,1,0,3,1,0,3",
				});
		bet = betStrategy.resolve(scheme);
		assertBetNotesTickets(bet, 16, 4);
	}
	
	/**
	 * 6场半全单式，最多4注
	 * @throws BetException 
	 */
	@Test
	public void oneTicketMax4NotesFor6CBQ() throws BetException{
		setupScheme(PlayType.CTZC_BQ, 
				ChooseType.MACHINE, 
				new String[]{
				"3,3,1,0,3,1,0,3,1,0,3,1",
				"3,3,1,0,3,1,0,3,1,0,3,1",
				"3,3,1,0,3,1,0,3,1,0,3,1",
				"3,3,1,0,3,1,0,3,1,0,3,1",
				"3,3,1,0,3,1,0,3,1,0,3,1",
				});
		Bet bet = betStrategy.resolve(scheme);
		assertBetNotesTickets(bet, 5, 2);

		setupScheme(PlayType.CTZC_BQ, 
				ChooseType.MACHINE, 
				new String[]{
				"3,3,1,0,3,1,0,3,1,0,3,1",
				"3,3,1,0,3,1,0,3,1,0,3,1",
				"3,3,1,0,3,1,0,3,1,0,3,1",
				"3,3,1,0,3,1,0,3,1,0,3,1",
				});
		bet = betStrategy.resolve(scheme);
		assertBetNotesTickets(bet, 4, 1);
	}
	
	/**
	 * 每票的投注金额都不能超过：2万，要考虑倍数。
	 * 拆票方法：
	 * 1、如果单倍小于2万，那么只需要减少倍数。
	 * 2、如果单倍大于2万，那么先拆成单倍N票，再对每张票拆分，得到M票，总票数为N*M。
	 * @throws BetException 
	 */
	@Test
	public void oneTicketMax20K() throws BetException {
		setupScheme(PlayType.CTZC_14, 
				ChooseType.MANUAL, new String[]{
				"310,310,310,310,310,310,310,310,310,310,0,0,0,0"});
		Bet bet = betStrategy.resolve(scheme);
		assertBetNotesTickets(bet, (int)Math.pow(3, 10), 9);
	}
	
	@Test
	public void r9Dan() throws BetException{
		setupScheme(PlayType.CTZC_R9, 
				ChooseType.MANUAL, new String[]{
				"310,310,310,1,1,0,3,3,3,3,0,0,0,0"},
				"0,1");
		Bet bet = betStrategy.resolve(scheme);
		assertBetNotesTickets(bet, 15444, 792);
		List<Ticket> ts = bet.getTickets();
		for (Ticket t : ts){
			String[] p = t.getCode().split(",");
			int count = 0;
			for (String c : p){
				if (!c.equals("-")){
					count++;
				}
			}
			assertEquals(9, count);	// 只能是9个选项
		}
		
		setupScheme(PlayType.CTZC_R9, 
				ChooseType.MANUAL, new String[]{
				"310,310,310,310,1,0,310,310,310,3,310,310,310,0"},
				"0,1,2,3,6,7,8,10");
		bet = betStrategy.resolve(scheme);
		assertBetNotesTickets(bet, 65610, 10);
	}
	
	/**
	 * 多倍单式。
	 */
	@Test
	public void multipleSingle() throws BetException{
		setupScheme(PlayType.CTZC_14, 
				ChooseType.MACHINE, new String[]{
				"3,3,3,1,1,0,3,3,3,3,0,0,0,0",
				"3,3,3,1,1,0,3,3,3,3,0,0,0,0",
				"3,3,3,1,1,0,3,3,3,3,0,0,0,0",
				"3,3,3,1,1,0,3,3,3,3,0,0,0,0",
				"3,3,3,1,1,0,3,3,3,3,0,0,0,0",
				});
		scheme.setMultiple(2);
		Bet bet = betStrategy.resolve(scheme);
		assertBetNotesTickets(bet, 10, 1);
		assertEquals(20, bet.getNote()*2);
	}
	
	/**
	 * 任九10场8个胆，计算钱数
	 * @throws BetException 
	 */
	@Test
	public void R910C8D() throws BetException{
		System.out.println("------------------------");
		setupScheme(PlayType.CTZC_R9, 
				ChooseType.MANUAL, new String[]{
				"310,310,310,310,310,310,310,310,310,310,-,-,-,-",
				}, "0,1,2,3,4,5,6,7");
		scheme.setMultiple(1);
		Bet bet = betStrategy.resolve(scheme);
		assertBetNotesTickets(bet, 39366, 6);
		assertEquals(39366*2, bet.getNote()*2);

		System.out.println("------------------------");
		setupScheme(PlayType.CTZC_R9, 
				ChooseType.MANUAL, new String[]{
				"310,310,310,310,310,310,310,310,310,310,-,-,-,-",
				}, "0,1,2,3,4,5,6,7");
		scheme.setMultiple(2);
		bet = betStrategy.resolve(scheme);
		assertBetNotesTickets(bet, 39366*2, 12);
		assertEquals(39366*2*2, bet.getNote()*2);
		
		System.out.println("------------------------");
		setupScheme(PlayType.CTZC_R9, 
				ChooseType.MANUAL, new String[]{
				"310,310,310,310,310,310,310,310,310,310,-,-,-,-",
				}, "0,1,2,3,4,5,6,7");
		scheme.setMultiple(3);
		bet = betStrategy.resolve(scheme);
		assertBetNotesTickets(bet, 39366*3, 18);
		assertEquals(39366*3*2, bet.getNote()*2);
	}
	
	/**
	 * 任九10场8个胆，倒序，计算钱数
	 * @throws BetException 
	 */
	@Test
	public void R910C8DReverse() throws BetException {
		setupScheme(PlayType.CTZC_R9, 
				ChooseType.MANUAL, new String[]{
				"-,-,-,-,310,310,310,310,310,310,310,310,310,310",
				}, "6,7,8,9,10,11,12,13");
		scheme.setMultiple(1);
		Bet bet = betStrategy.resolve(scheme);
		assertBetNotesTickets(bet, 39366, 6);
		assertEquals(39366*2, bet.getNote()*2);
	}
	
	/**
	 * Test JQ
	 * @throws BetException 
	 */
	@Test
	public void jq() throws BetException{
		setupScheme(PlayType.CTZC_JQ, 
				ChooseType.MANUAL, new String[]{
				"0123,0123,0123,0123,0123,0123,0123,0123",
				}, "");
		scheme.setMultiple(3);
		Bet bet = betStrategy.resolve(scheme);
		assertBetNotesTickets(bet, 196608, 28);
		assertEquals(196608*2, bet.getNote()*2);
	}
	
	@Test
	public void jq2() throws BetException{
		setupScheme(PlayType.CTZC_JQ, 
				ChooseType.MANUAL, new String[]{
				"3,03,012,0123,013,01,3,0",
				}, "");
		scheme.setMultiple(5);
		Bet bet = betStrategy.resolve(scheme);
		assertBetNotesTickets(bet, 720, 1);
		assertEquals(720*2, bet.getNote()*2);
	}
}

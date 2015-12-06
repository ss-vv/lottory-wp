package com.xhcms.lottery.commons.utils.internal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.laicai.util.ComputerUtils;
import com.xhcms.lottery.commons.data.Bet;
import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.data.DigitalBetRequest;
import com.xhcms.lottery.commons.data.Ticket;
import com.xhcms.lottery.commons.persist.entity.IssueInfoPO;
import com.xhcms.lottery.commons.persist.service.BetException;
import com.xhcms.lottery.commons.persist.service.impl.DigitalBetServiceImpl;
import com.xhcms.lottery.lang.LotteryId;
import com.xhcms.lottery.lang.PlayType;

/**
 * 
 * @author zhangdebin, 杨波
 *
 */
public class SSQBetStrategyTest {
	private SSQBetStrategy betStrategy;
	private BetScheme scheme;
	private long userId;
	private DigitalBetServiceImpl service = new DigitalBetServiceImpl();

	@Before
	public void setup() throws BetException {
		userId = 77;
		betStrategy = new SSQBetStrategy();
	}

	/**
	 * 只是设置closeTime
	 * @param betOptions 投注选项，比如: 01,02|03,04
	 * @param playType 玩法类型
	 * @param chooseType 选择类型
	 * 
	 * @return
	 */
	private DigitalBetRequest makeBetRequest(PlayType playType, String[] betOptions) {
		DigitalBetRequest request = new DigitalBetRequest();
		request.setUserId(userId);
		request.setLotteryId(LotteryId.SSQ.name());
		request.setPlayType(playType);
		request.setIssue("2013011");
		request.setBetContents(Arrays.asList(betOptions));
		request.setMultiple(1);
		request.setTotalNotesCount(1);
		request.setMoney(2);
		return request;
	}

	private IssueInfoPO makeIssueInfo() {
		IssueInfoPO issueInfo = new IssueInfoPO();
		issueInfo.setCloseTime(new Date());
		return issueInfo;
	}

	private void setupScheme(PlayType playType, String[] betOptions) throws BetException {
		IssueInfoPO issueInfo = makeIssueInfo();
		DigitalBetRequest betRequest = makeBetRequest(playType, betOptions);
		scheme = service.makeBetScheme(betRequest, issueInfo);
	}

	@Test
	public void testShouldMatch() {
		for(PlayType playType : PlayType.values()) {
			String playId = null;
			LotteryId lotteryId = playType.getLotteryId();
			if (lotteryId == LotteryId.JCLQ || lotteryId == LotteryId.JCZQ){
				playId = playType.getPlayIdWithPass(true);
				assertFalse(betStrategy.match(playId));
				playId = playType.getPlayIdWithPass(false);
				assertFalse(betStrategy.match(playId));
			}else{
				playId = playType.getPlayId();
				if (lotteryId==LotteryId.SSQ){
					assertTrue(betStrategy.match(playId));
				}else{
					assertFalse(betStrategy.match(playId));
				}
			}
		}
	}
	
	@Test
	public void testSplitToMultiNotes(){
		SSQBetStrategy cs = new SSQBetStrategy();
		List<String> ss = cs.splitToMultiNotes("01,02,03,04,05,06|01;01,02,03,04,05,06|02;01,02,03,04,05,06|03;01,02,03,04,05,06|04;01,02,03,04,05,06|05;01,02,03,04,05,06|06; 01,02,03,04,05,06|07; 01,02,03,04,05,06|08");
		assertEquals(2, ss.size());
		assertEquals("01,02,03,04,05,06|01;01,02,03,04,05,06|02;01,02,03,04,05,06|03;01,02,03,04,05,06|04;01,02,03,04,05,06|05", ss.get(0));
		assertEquals("01,02,03,04,05,06|06;01,02,03,04,05,06|07;01,02,03,04,05,06|08", ss.get(1));
	}

	private void assertBetNotesTicketsBonus(Bet bet, int notes, int tickets, int bonus) {
		assertEquals("expected notes is (" + notes + ") but is (" + bet.getNote() + ")", 
				notes, bet.getNote());
		int actualTickets = bet.getTickets().size();
		assertEquals("expected tickets is (" + tickets + ") but is (" + actualTickets + ")",
				tickets, actualTickets);
	}
	
	/**
	 * 单式
	 * @throws BetException 
	 */
	@Test
	public void test_DanShi() throws BetException {
		setupScheme(PlayType.SSQ_DS, new String[]{"01,02,03,04,05,06|01;01,02,03,04,05,06|02;01,02,03,04,05,06|03;01,02,03,04,05,06|04;01,02,03,04,05,06|05;01,02,03,04,05,06|06; 01,02,03,04,05,06|07; 01,02,03,04,05,06|08"});
		Bet bet = betStrategy.resolve(scheme);
		assertBetNotesTicketsBonus(bet, 8, 2, 0);
		
		assertEquals(5, bet.getTickets().get(0).getNote());
		assertEquals(10, bet.getTickets().get(0).getMoney());
	}
	
	/**
	 * 单式, 倍投
	 * @throws BetException 
	 */
	@Test
	public void test_DanShi_MultiBet() throws BetException {
		setupScheme(PlayType.SSQ_DS, new String[]{"01,02,03,04,05,06|01;01,02,03,04,05,06|02;01,02,03,04,05,06|03;01,02,03,04,05,06|04;01,02,03,04,05,06|05;01,02,03,04,05,06|06; 01,02,03,04,05,06|07; 01,02,03,04,05,06|08", "02,03,04,05,06,07|09"});
		Bet bet = betStrategy.resolve(scheme);
		assertBetNotesTicketsBonus(bet, 9, 3, 0);
		
		assertEquals(1, bet.getTickets().get(2).getNote());
		assertEquals(2, bet.getTickets().get(2).getMoney());
	}

	/**
	 * 复式
	 * @throws BetException 
	 */
	@Test
	public void test_FuShi() throws BetException{
		assertEquals(ComputerUtils.combination(13, 6), 1716);
		
		setupScheme(PlayType.SSQ_FS, new String[]{"01,02,03,04,05,06,07,08|01,02,03"});
		Bet bet = betStrategy.resolve(scheme);
		assertBetNotesTicketsBonus(bet, 28 * 3, 1, 0);
		assertEquals(1, bet.getTickets().size());
		Ticket ticket = bet.getTickets().get(0);
		assertEquals("01,02,03,04,05,06,07,08|01,02,03", ticket.getActualCode());
		
		setupScheme(PlayType.SSQ_FS, new String[]{"01,02,03,04,05,06,07,08,09,10,31,32|01"});
		bet = betStrategy.resolve(scheme);
		assertBetNotesTicketsBonus(bet, 924 * 1, 1, 0);
		assertEquals(1, bet.getTickets().size());
		ticket = bet.getTickets().get(0);
		assertEquals("01,02,03,04,05,06,07,08,09,10,31,32|01", ticket.getActualCode());
		
		setupScheme(PlayType.SSQ_FS, new String[]{"01,02,03,04,05,06,07,08,09,10,31,32,33|01,02,03"});
		bet = betStrategy.resolve(scheme);
		assertBetNotesTicketsBonus(bet, 1716 * 3, 1, 0);
		assertEquals(1, bet.getTickets().size());
		ticket = bet.getTickets().get(0);
		assertEquals("01,02,03,04,05,06,07,08,09,10,31,32,33|01,02,03", ticket.getActualCode());
	}
	
	@Test
	public void test_FuShi_multi() throws BetException{
		setupScheme(PlayType.SSQ_FS, new String[]{"01,02,03,04,05,06,07,08|01,02,03", "01,02,03,04,05,06,07,08|01,02,03,04"});
		Bet bet = betStrategy.resolve(scheme);
		assertBetNotesTicketsBonus(bet, 28 * 3 + 28 * 4, 2, 0);
		assertEquals(2, bet.getTickets().size());
		Ticket ticket = bet.getTickets().get(1);
		assertEquals("01,02,03,04,05,06,07,08|01,02,03,04", ticket.getActualCode());
		assertEquals(PlayType.SSQ_FS.getPlayId(), ticket.getPlayId());
	}
	
	/**
	 * 胆拖
	 */
	@Test
	public void test_DanTuo() throws BetException{
		setupScheme(PlayType.SSQ_DT, new String[]{"01,02@03,04,05,06,07,08|01,02,03"});
		Bet bet = betStrategy.resolve(scheme);
		assertBetNotesTicketsBonus(bet, 45, 1, 0);
		
		Ticket ticket = bet.getTickets().get(0);
		assertEquals("(01,02),03,04,05,06,07,08|01,02,03", ticket.getActualCode());
		assertEquals(PlayType.SSQ_DT.getPlayId(), ticket.getPlayId());
	}

	@Test
	public void test_DanTuo_multi() throws BetException{
		setupScheme(PlayType.SSQ_DT, new String[]{"01,02@03,04,05,06,07,08|01,02,03", "01,02,03@04,05,06,07,08,09,10,11|01,02,03,04,05"});
		Bet bet = betStrategy.resolve(scheme);
		assertBetNotesTicketsBonus(bet, 45 + 56 * 5, 2, 0);
		
		Ticket ticket = bet.getTickets().get(1);
		assertEquals("(01,02,03),04,05,06,07,08,09,10,11|01,02,03,04,05", ticket.getActualCode());
		assertEquals(PlayType.SSQ_DT.getPlayId(), ticket.getPlayId());
	}
	
	/**
	 * 大于99倍的拆票
	 */
	@Test
	public void shouldSplitMultiple() throws BetException{
		setupScheme(PlayType.SSQ_DS, new String[]{"01,02,03,04,05,06|01;01,02,03,04,05,06|02;01,02,03,04,05,06|03;01,02,03,04,05,06|04;01,02,03,04,05,06|05;01,02,03,04,05,06|06; 01,02,03,04,05,06|07; 01,02,03,04,05,06|08"});
		scheme.setMultiple(100);
		Bet bet = betStrategy.resolve(scheme);
		assertBetNotesTicketsBonus(bet, 800, 4, 0);

		assertEquals(5 * 50, bet.getTickets().get(0).getNote());
		assertEquals(5 * 50 * 2, bet.getTickets().get(0).getMoney());
	}
	
	/**
	 * 单张票的金额较大，导致不能用99倍组合新票的情况。
	 */
	@Test
	public void shouldSplitMultipleLargeTicket() throws BetException{
		setupScheme(PlayType.SSQ_FS, new String[] { "01,02,03,04,05,06,07,08|01,02,03" });
		scheme.setMultiple(100);
		Bet bet = betStrategy.resolve(scheme);
		assertBetNotesTicketsBonus(bet, 84 * 100, 2, 0);

		assertEquals(84 * 50, bet.getTickets().get(0).getNote());
		assertEquals(84 * 50 * 2, bet.getTickets().get(0).getMoney());
	}
	
	/**
	 * 胆拖变单式或复式。
	 */
	@Test
	public void testDanTuo2DS(){
		setupScheme(PlayType.SSQ_DT, new String[]{"01@02,03,04,05,06|01"});
		Bet bet = betStrategy.resolve(scheme);
		Ticket ticket = bet.getTickets().get(0);
		assertEquals("01,02,03,04,05,06|01", ticket.getActualCode());
		assertEquals(PlayType.SSQ_DS.getPlayId(), ticket.getPlayId());
	}
	
	/**
	 * 胆拖变单式或复式。
	 */
	@Test
	public void testDanTuo2FS(){
		setupScheme(PlayType.SSQ_DT, new String[]{"01@02,03,04,05,06|01,02"});
		Bet bet = betStrategy.resolve(scheme);
		Ticket ticket = bet.getTickets().get(0);
		assertEquals("01,02,03,04,05,06|01,02", ticket.getActualCode());
		assertEquals(PlayType.SSQ_FS.getPlayId(), ticket.getPlayId());
	}
}

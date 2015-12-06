package com.xhcms.lottery.commons.utils.internal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

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
 * @author Yang Bo
 *
 */
public class CQSSBetStrategyTest {
	private CQSSBetStrategy betStrategy;
	private BetScheme scheme;
	private long userId;
	private DigitalBetServiceImpl service = new DigitalBetServiceImpl();

	@Before
	public void setup() throws BetException {
		userId = 77;
		betStrategy = new CQSSBetStrategy();
	}

	/**
	 * 只是设置closeTime
	 * @param betOptions 投注选项，比如: 01,02|03,04
	 * @param playType 玩法类型
	 * @param chooseType 选择类型
	 */
	private DigitalBetRequest makeBetRequest(PlayType playType, String[] betOptions) {
		DigitalBetRequest request = new DigitalBetRequest();
		request.setUserId(userId);
		request.setLotteryId(LotteryId.CQSS.name());
		request.setPlayType(playType);
		request.setIssue("12091371");
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
				if (lotteryId==LotteryId.CQSS){
					assertTrue(betStrategy.match(playId));
				}else{
					assertFalse(betStrategy.match(playId));
				}
			}
		}
	}
	
	@Test
	public void testSplitToMultiNotes(){
		CQSSBetStrategy cs = new CQSSBetStrategy();
		List<String> ss = cs.splitToMultiNotes("1;2;3;4;5;6;7;8");
		assertEquals(2, ss.size());
		assertEquals("1;2;3;4;5", ss.get(0));
		assertEquals("6;7;8", ss.get(1));
	}

	private void assertBetNotesTicketsBonus(Bet bet, int notes, int tickets, int bonus) {
		assertEquals("expected notes is (" + notes + ") but is (" + bet.getNote() + ")", 
				notes, bet.getNote());
		int actualTickets = bet.getTickets().size();
		assertEquals("expected tickets is (" + tickets + ") but is (" + actualTickets + ")",
				tickets, actualTickets);
	}
	
	/**
	 * 一星
	 * @throws BetException 
	 */
	@Test
	public void test_1X() throws BetException {
		setupScheme(PlayType.CQSS_1X_DS, new String[]{"0;1;2;3;4;5;6"});
		Bet bet = betStrategy.resolve(scheme);
		assertBetNotesTicketsBonus(bet, 7, 2, 0);
		
		assertEquals(5, bet.getTickets().get(0).getNote());
		assertEquals(10, bet.getTickets().get(0).getMoney());
	}
	
	@Test
	public void test_1X_MultiBet() throws BetException {
		setupScheme(PlayType.CQSS_1X_DS, new String[]{"0;1;2;3;4;5;6", "0"});
		Bet bet = betStrategy.resolve(scheme);
		assertBetNotesTicketsBonus(bet, 8, 3, 0);
		
		assertEquals(1, bet.getTickets().get(2).getNote());
		assertEquals(2, bet.getTickets().get(2).getMoney());
	}
	
	/**
	 * 五星
	 */
	@Test
	public void test_5XTX() throws BetException{
		setupScheme(PlayType.CQSS_5X_TX, new String[]{"123,123,123,123,123"});
		Bet bet = betStrategy.resolve(scheme);
		assertBetNotesTicketsBonus(bet, 3*3*3*3*3, 3*3*3*3*3/5+1, 0);
		
		Ticket ticket = bet.getTickets().get(0);
		assertEquals(5, ticket.getNote());
		assertEquals(10, ticket.getMoney());
		assertEquals("11111;11112;11113;21111;21112", ticket.getActualCode());
	}
	
	@Test
	public void test_5XTX_multi() throws BetException{
		setupScheme(PlayType.CQSS_5X_TX, new String[]{"123,123,123,123, 123", "12345"});
		Bet bet = betStrategy.resolve(scheme);
		assertBetNotesTicketsBonus(bet, 3*3*3*3*3+1, 3*3*3*3*3/5+2, 0);
		
		Ticket ticket = bet.getTickets().get(1);
		assertEquals(5, ticket.getNote());
		assertEquals(10, ticket.getMoney());
		assertEquals("21113;31111;31112;31113;12111", ticket.getActualCode());
	}
	
	@Test
	public void test_5XFS() throws BetException{
		setupScheme(PlayType.CQSS_5X_FS, new String[]{"34567890,0,123,1234,12345"});
		Bet bet = betStrategy.resolve(scheme);
		assertBetNotesTicketsBonus(bet, 480, 1, 0);
		assertEquals(1, bet.getTickets().size());
		Ticket ticket = bet.getTickets().get(0);
		assertEquals("34567890,0,123,1234,12345", ticket.getActualCode());
	}
	
	@Test
	public void test_5XFS_multi() throws BetException{
		setupScheme(PlayType.CQSS_5X_FS, new String[]{"34567890,0,123,1234,12345", "1,2,3,4,5"});
		Bet bet = betStrategy.resolve(scheme);
		assertBetNotesTicketsBonus(bet, 481, 2, 0);
		assertEquals(2, bet.getTickets().size());
		Ticket ticket = bet.getTickets().get(1);
		assertEquals("12345", ticket.getActualCode());
		assertEquals(PlayType.CQSS_5X_DS.getPlayId(), ticket.getPlayId());
	}
	
	/**
	 * 三星
	 */
	@Test
	public void test_3XFS() throws BetException{
		setupScheme(PlayType.CQSS_3X_FS, new String[]{"0123456789,01234,56789"});
		Bet bet = betStrategy.resolve(scheme);
		assertBetNotesTicketsBonus(bet, 250, 1, 0);
		
		Ticket ticket = bet.getTickets().get(0);
		assertEquals("0123456789,01234,56789", ticket.getActualCode());
		assertEquals(PlayType.CQSS_3X_FS.getPlayId(), ticket.getPlayId());
	}

	@Test
	public void test_3XFS_multi() throws BetException{
		setupScheme(PlayType.CQSS_3X_FS, new String[]{"0123456789,01234,56789", "0,1,9"});
		Bet bet = betStrategy.resolve(scheme);
		assertBetNotesTicketsBonus(bet, 251, 2, 0);
		
		Ticket ticket = bet.getTickets().get(1);
		assertEquals("019", ticket.getActualCode());
		assertEquals(PlayType.CQSS_3X_DS.getPlayId(), ticket.getPlayId());
	}
	
	@Test
	public void test_3XHZ() throws BetException{
		setupScheme(PlayType.CQSS_3X_HZ, new String[]{"12;23", "0"});
		Bet bet = betStrategy.resolve(scheme);
		assertBetNotesTicketsBonus(bet, 73+15+1, 3, 0);
		
		Ticket ticket = bet.getTickets().get(1);
		assertEquals("23", ticket.getActualCode());
		assertEquals(PlayType.CQSS_3X_HZ.getPlayId(), ticket.getPlayId());
		assertEquals(15, ticket.getNote());
	}
	
	@Test
	public void test_3XZSFS() throws BetException{
		setupScheme(PlayType.CQSS_3X_Z3_FS, new String[]{"12", "123", "0123456789"});
		Bet bet = betStrategy.resolve(scheme);
		assertBetNotesTicketsBonus(bet, 2+6+90, 3, 0);
		
		Ticket ticket = bet.getTickets().get(1);
		assertEquals("123", ticket.getActualCode());
		assertEquals(PlayType.CQSS_3X_Z3_FS.getPlayId(), ticket.getPlayId());
		assertEquals(6, ticket.getNote());
	}
	
	@Test
	public void test_3XZSDS() throws BetException{
		setupScheme(PlayType.CQSS_3X_Z3_DS, new String[]{"1,2", "12,34"});
		Bet bet = betStrategy.resolve(scheme);
		assertBetNotesTicketsBonus(bet, 1+4, 5, 0);
		
		Ticket ticket = bet.getTickets().get(1);
		assertEquals("113", ticket.getActualCode());
		assertEquals(PlayType.CQSS_3X_Z3_DS.getPlayId(), ticket.getPlayId());
		assertEquals(1, ticket.getNote());
		
		setupScheme(PlayType.CQSS_3X_Z3_DS, new String[]{"12,234"});
		bet = betStrategy.resolve(scheme);
		assertBetNotesTicketsBonus(bet, 6-1, 5, 0);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void test_3XZSDS_wrongFormat() throws BetException{
		setupScheme(PlayType.CQSS_3X_Z3_DS, new String[]{"112"});
		betStrategy.resolve(scheme);
	}

	/**
	 * 三星组六复式
	 */
	@Test
	public void test_3XZ6FS() throws BetException{
		setupScheme(PlayType.CQSS_3X_Z6_FS, new String[]{"1234", "123"});
		Bet bet = betStrategy.resolve(scheme);
		assertBetNotesTicketsBonus(bet, 4+1, 2, 0);
		
		Ticket ticket = bet.getTickets().get(1);
		assertEquals("123", ticket.getActualCode());
		assertEquals(PlayType.CQSS_3X_Z6_DS.getPlayId(), ticket.getPlayId());
		assertEquals(1, ticket.getNote());
	}

	/**
	 * 二星直选复式
	 */
	@Test
	public void test_2XFS() throws BetException{
		setupScheme(PlayType.CQSS_2X_FS, new String[]{"12,29", "1,2"});
		Bet bet = betStrategy.resolve(scheme);
		assertBetNotesTicketsBonus(bet, 4+1, 2, 0);
		
		Ticket ticket = bet.getTickets().get(1);
		assertEquals("12", ticket.getActualCode());
		assertEquals(PlayType.CQSS_2X_DS.getPlayId(), ticket.getPlayId());
		assertEquals(1, ticket.getNote());
	}

	/**
	 * 二星直选单式
	 */
	@Test
	public void test_2XDS() throws BetException{
		setupScheme(PlayType.CQSS_2X_DS, new String[]{"12;34", "56"});
		Bet bet = betStrategy.resolve(scheme);
		assertBetNotesTicketsBonus(bet, 2+1, 2, 0);
		
		Ticket ticket = bet.getTickets().get(0);
		assertEquals("12;34", ticket.getActualCode());
		assertEquals(PlayType.CQSS_2X_DS.getPlayId(), ticket.getPlayId());
		assertEquals(2, ticket.getNote());
	}

	/**
	 * 二星和值
	 */
	@Test
	public void test_2XHZ() throws BetException{
		setupScheme(PlayType.CQSS_2X_HZ, new String[]{"18;17;15;0;", "0;"});
		Bet bet = betStrategy.resolve(scheme);
		assertBetNotesTicketsBonus(bet, 1+2+4+1+1, 5, 0);
		
		Ticket ticket = bet.getTickets().get(0);
		assertEquals("18", ticket.getActualCode());
		assertEquals(PlayType.CQSS_2X_HZ.getPlayId(), ticket.getPlayId());
		assertEquals(1, ticket.getNote());
	}
	
	/**
	 * 二星组选组合
	 */
	@Test
	public void test_2XZXZH() throws BetException{
		setupScheme(PlayType.CQSS_2X_ZX_ZH, new String[]{"1234567890", "90"});
		Bet bet = betStrategy.resolve(scheme);
		assertBetNotesTicketsBonus(bet, 45+1, 2, 0);
		
		Ticket ticket = bet.getTickets().get(1);
		assertEquals("90", ticket.getActualCode());
		assertEquals(PlayType.CQSS_2X_ZX_DS.getPlayId(), ticket.getPlayId());
		assertEquals(1, ticket.getNote());
	}
	
	/**
	 * 大小单双
	 */
	@Test
	public void test_DXDS() throws BetException{
		setupScheme(PlayType.CQSS_DXDS, new String[]{"1234,1234", "13"});
		Bet bet = betStrategy.resolve(scheme);
		assertBetNotesTicketsBonus(bet, 16+1, 4, 0);
		
		Ticket ticket = bet.getTickets().get(0);
		assertEquals("11;12;13;14;21", ticket.getActualCode());
		assertEquals(PlayType.CQSS_DXDS.getPlayId(), ticket.getPlayId());
		assertEquals(5, ticket.getNote());
	}
	
	/**
	 * 大于99倍的拆票
	 */
	@Test
	public void shouldSplitMultiple() throws BetException{
		setupScheme(PlayType.CQSS_1X_DS, new String[]{"0;1;2;3;4;5;6"});
		scheme.setMultiple(100);
		Bet bet = betStrategy.resolve(scheme);
		assertBetNotesTicketsBonus(bet, 700, 4, 0);
		
		assertEquals(5*99, bet.getTickets().get(0).getNote());
		assertEquals(5*99*2, bet.getTickets().get(0).getMoney());
	}
	
	/**
	 * 单张票的金额较大，导致不能用99倍组合新票的情况。
	 */
	@Test
	public void shouldSplitMultipleLargeTicket() throws BetException{
		setupScheme(PlayType.CQSS_5X_FS, new String[]{"0123456789,6123,7,8,9"});
		scheme.setMultiple(100);
		Bet bet = betStrategy.resolve(scheme);
		assertBetNotesTicketsBonus(bet, 40*100, 2, 0);
		
		assertEquals(40*99, bet.getTickets().get(0).getNote());
		assertEquals(40*99*2, bet.getTickets().get(0).getMoney());
		
		setupScheme(PlayType.CQSS_5X_FS, new String[]{"0123456789,6012345789,78,8,9"});
		scheme.setMultiple(100);
		bet = betStrategy.resolve(scheme);
		assertBetNotesTicketsBonus(bet, 200*100, 2, 0);
		
		assertEquals(50, bet.getTickets().get(0).getMultiple());
		assertEquals(10000, bet.getTickets().get(0).getNote());
		assertEquals(20000, bet.getTickets().get(0).getMoney());

		assertEquals(50, bet.getTickets().get(0).getMultiple());
		assertEquals(10000, bet.getTickets().get(1).getNote());
		assertEquals(20000, bet.getTickets().get(1).getMoney());
		
		setupScheme(PlayType.CQSS_5X_FS, new String[]{"0123456789,601234578,78,8,9"});
		scheme.setMultiple(100);
		bet = betStrategy.resolve(scheme);
		assertBetNotesTicketsBonus(bet, 180*100, 2, 0);
		
		assertEquals(55, bet.getTickets().get(0).getMultiple());
		assertEquals(55*180, bet.getTickets().get(0).getNote());
		assertEquals(55*180*2, bet.getTickets().get(0).getMoney());

		assertEquals(45, bet.getTickets().get(1).getMultiple());
		assertEquals(45*180, bet.getTickets().get(1).getNote());
		assertEquals(45*180*2, bet.getTickets().get(1).getMoney());
	}
}

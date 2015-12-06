package com.xhcms.lottery.commons.utils.internal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.xhcms.lottery.commons.data.Bet;
import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.data.ChooseType;
import com.xhcms.lottery.commons.data.DigitalBetRequest;
import com.xhcms.lottery.commons.data.Ticket;
import com.xhcms.lottery.commons.persist.entity.IssueInfoPO;
import com.xhcms.lottery.commons.persist.service.BetException;
import com.xhcms.lottery.commons.persist.service.impl.DigitalBetServiceImpl;
import com.xhcms.lottery.lang.Constants;
import com.xhcms.lottery.lang.PlayType;

/**
 * 测试江西11选5拆票逻辑。
 * 
 * @author Yang Bo
 */
public class JX11BetStrategyTest {

	private JX11BetStrategy jx11BetStrategy;
	private BetScheme scheme;
	private long userId;
	private DigitalBetServiceImpl service = new DigitalBetServiceImpl();

	@Before
	public void setup() throws BetException {
		userId = 77;
		jx11BetStrategy = new JX11BetStrategy();
	}

	/**
	 * 只是设置closeTime
	 * @param betOptions 投注选项，比如: 01,02|03,04
	 * @param playType 玩法类型
	 * @param chooseType 选择类型
	 * 
	 * @return
	 */
	private DigitalBetRequest makeBetRequest(PlayType playType, ChooseType chooseType, String[] betOptions) {
		DigitalBetRequest request = new DigitalBetRequest();
		request.setUserId(userId);
		request.setLotteryId(Constants.JX11);
		request.setPlayType(playType);
		request.setChooseType(chooseType);
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
	
	/*
	 * D2、D3 单选、复选标志是code最末尾是否添加分号“;”，有分号为单选，没有为复选。
	 */
	@Test
	public void D2D3ManualShouldNotEndWithSemicolon() throws BetException{
		setupScheme(PlayType.JX11_D3, ChooseType.MANUAL, new String[]{"01|02,03|04,05|06"});
		Bet bet = jx11BetStrategy.resolve(scheme);
		assertFalse("D3手选，code串末尾应该没有分号", bet.getTickets().get(0).getCode().endsWith(";"));
		
		setupScheme(PlayType.JX11_D2, ChooseType.MANUAL, new String[]{"01|02,03|04"});
		bet = jx11BetStrategy.resolve(scheme);
		assertFalse("D2手选，code串末尾应该没有分号", bet.getTickets().get(0).getCode().endsWith(";"));
	}

	@Test
	public void D2D3MachineShouldEndWithSemicolon() throws BetException{
		setupScheme(PlayType.JX11_D3, ChooseType.MACHINE, new String[]{"01|02|03","04|05|06"});
		Bet bet = jx11BetStrategy.resolve(scheme);
		assertTrue("D2,D3机选，code串末尾应该有分号", bet.getTickets().get(0).getCode().endsWith(";"));

		setupScheme(PlayType.JX11_D2, ChooseType.MACHINE, new String[]{"02|03","04|05"});
		bet = jx11BetStrategy.resolve(scheme);
		assertTrue("D2机选，code串末尾应该有分号", bet.getTickets().get(0).getCode().endsWith(";"));
	}
	
	@Test
	public void D2D3CompositionToSingle() throws BetException {
		setupScheme(PlayType.JX11_D3, ChooseType.MANUAL, new String[]{"01|02|03"});
		Bet bet = jx11BetStrategy.resolve(scheme);
		assertTrue("D3手选，code串没有逗号则变为单式", bet.getTickets().get(0).getCode().endsWith(";"));

		setupScheme(PlayType.JX11_D2, ChooseType.MANUAL, new String[]{"02|03"});
		bet = jx11BetStrategy.resolve(scheme);
		assertTrue("D2手选，code串没有逗号则变为单式", bet.getTickets().get(0).getCode().endsWith(";"));
	}
	
	@Test
	public void D3ManualShouldBe1Note() throws BetException{
		setupScheme(PlayType.JX11_D3, ChooseType.MANUAL, new String[]{"02,03|03,04|02,03"});
		Bet bet = jx11BetStrategy.resolve(scheme);
		assertBetNotesTicketsBonus(bet, 2, 1, 1170*2);
	}
	
	@Test
	public void D3ManualAllowRepeat() throws BetException {
		setupScheme(PlayType.JX11_D3, ChooseType.MANUAL, new String[]{"02,03,04|02,03|03,04"});
		Bet bet = jx11BetStrategy.resolve(scheme);
		assertBetNotesTicketsBonus(bet, 3, 1, 1170*3);
	}

	@Test
	public void D2ManualAllowRepeat() throws BetException {
		setupScheme(PlayType.JX11_D2, ChooseType.MANUAL, new String[]{"02,03,04|02,03"});
		Bet bet = jx11BetStrategy.resolve(scheme);
		assertBetNotesTicketsBonus(bet, 4, 1, 130*4);
	}

	@Test(expected=IllegalArgumentException.class)
	public void D3ManualBadOptions() throws BetException {
		setupScheme(PlayType.JX11_D3, ChooseType.MANUAL, new String[]{"02,03|02,03|02,03"});
		jx11BetStrategy.resolve(scheme);
	}
	
	@Test
	public void shouldMatchJX11() {
		assertTrue(jx11BetStrategy.match(PlayType.JX11_R1.getPlayId()));
		assertTrue(jx11BetStrategy.match(PlayType.JX11_R2.getPlayId()));
		assertTrue(jx11BetStrategy.match(PlayType.JX11_R8.getPlayId()));
		assertTrue(jx11BetStrategy.match(PlayType.JX11_D2.getPlayId()));
		assertTrue(jx11BetStrategy.match(PlayType.JX11_D3.getPlayId()));
		assertTrue(jx11BetStrategy.match(PlayType.JX11_G2.getPlayId()));
		assertTrue(jx11BetStrategy.match(PlayType.JX11_G2.getPlayId()));

		assertFalse(jx11BetStrategy.match(PlayType.JCZQ_SPF
				.getPlayIdWithPass(true)));
		assertFalse(jx11BetStrategy.match(PlayType.JCLQ_RFSF
				.getPlayIdWithPass(false)));
	}

	@Test
	public void whenR1ManualWith1OptionThen1Note() throws BetException {
		setupScheme(PlayType.JX11_R1, ChooseType.MANUAL, new String[]{"01"});
		Bet bet = jx11BetStrategy.resolve(scheme);
		assertBetNotesTicketsBonus(bet, 1, 1, 13);

		assertEquals(1, bet.getTickets().get(0).getNote());
		assertEquals(2, bet.getTickets().get(0).getMoney());
	}

	private void setupScheme(PlayType playType, ChooseType chooseType, String[] betOptions) throws BetException {
		IssueInfoPO issueInfo = makeIssueInfo();
		DigitalBetRequest betContent = makeBetRequest(playType, chooseType, betOptions);
		scheme = service.makeBetScheme(betContent, issueInfo);
	}

	@Test
	public void whenR1ManualWith3DigitsThen3Notes() throws BetException {
		setupScheme(PlayType.JX11_R1, ChooseType.MANUAL, new String[]{"01,02,03"});
		Bet bet = jx11BetStrategy.resolve(scheme);
		assertBetNotesTicketsBonus(bet, 3, 3, 13*3);
		
		assertEquals(1, bet.getTickets().get(0).getNote());
		assertEquals(2, bet.getTickets().get(0).getMoney());
	}

	private void assertBetNotesTicketsBonus(Bet bet, int notes, int tickets, int bonus) {
		assertEquals("expected notes is (" + notes + ") but is (" + bet.getNote() + ")", 
				notes, bet.getNote());
		int actualTickets = bet.getTickets().size();
		assertEquals("expected tickets is (" + tickets + ") but is (" + actualTickets + ")",
				tickets, actualTickets);
		double acutalBonus = bet.getMaxBonus();
		assertTrue("expected bonus is (" + bonus + ") but is (" + acutalBonus + ")",
				Double.compare(bonus,acutalBonus)==0);
	}
 
	@Test
	public void testR1MacineWith5Digits() throws BetException{
		setupScheme(PlayType.JX11_R1, ChooseType.MACHINE, new String[]{"01","02","03", "11", "10"});
		Bet bet = jx11BetStrategy.resolve(scheme);
		assertBetNotesTicketsBonus(bet, 5, 5, 13*5);
	}
	
	@Test
	public void testR2Dan() throws BetException{
		setupScheme(PlayType.JX11_R2, ChooseType.DAN, new String[]{"(01)02,03,04,05,06,07,08,09,10,11"});
		Bet bet = jx11BetStrategy.resolve(scheme);
		assertBetNotesTicketsBonus(bet, 10, 1, 6*10);
		
		setupScheme(PlayType.JX11_R2, ChooseType.DAN, new String[]{"(08)01,02"});
		bet = jx11BetStrategy.resolve(scheme);
		assertBetNotesTicketsBonus(bet, 2, 1, 6*2);
	}
	
	@Test
	public void testR2MachineWith5Options() throws BetException{
		setupScheme(PlayType.JX11_R2, ChooseType.MACHINE, new String[]{
				"03,06",
				"05,08",
				"02,07",
				"03,08",
				"02,06"});
		Bet bet = jx11BetStrategy.resolve(scheme);
		assertBetNotesTicketsBonus(bet, 5, 1, 6*5);
	}

	@Test
	public void testR2MachineWith1Options() throws BetException{
		setupScheme(PlayType.JX11_R2, ChooseType.MACHINE, new String[]{
				"02,03"});
		Bet bet = jx11BetStrategy.resolve(scheme);
		assertBetNotesTicketsBonus(bet, 1, 1, 6*1);
	}

	@Test
	public void testR2MachineWith6Options() throws BetException{
		setupScheme(PlayType.JX11_R2, ChooseType.MACHINE, new String[]{
				"03,06",
				"03,11",
				"05,08",
				"02,07",
				"03,08",
				"02,06"});
		Bet bet = jx11BetStrategy.resolve(scheme);
		assertBetNotesTicketsBonus(bet, 6, 2, 6*6);
	}

	@Test
	public void testR2MachineWith10Options() throws BetException{
		setupScheme(PlayType.JX11_R2, ChooseType.MACHINE, new String[]{
				"04,06",
				"01,03",
				"08,11",
				"03,04",
				"02,05",
				"07,11",
				"05,08",
				"02,03",
				"05,06",
				"05,09"
				});
		Bet bet = jx11BetStrategy.resolve(scheme);
		assertBetNotesTicketsBonus(bet, 10, 2, 6*10);
	}
	
	@Test
	public void testR3MachineWith1Options() throws BetException{
		setupScheme(PlayType.JX11_R3, ChooseType.MACHINE, new String[]{
				"07,09,10",
				});
		Bet bet = jx11BetStrategy.resolve(scheme);
		assertBetNotesTicketsBonus(bet, 1, 1, 19);
	}

	@Test
	public void testR3MachineWith5Options() throws BetException{
		setupScheme(PlayType.JX11_R3, ChooseType.MACHINE, new String[]{
				"07,09,10",
				"07,09,10",
				"07,09,10",
				"07,09,10",
				"07,09,10",
				});
		Bet bet = jx11BetStrategy.resolve(scheme);
		assertBetNotesTicketsBonus(bet, 5, 1, 19*5);
	}
	
	@Test
	public void testR3MachineWith6Options() throws BetException{
		setupScheme(PlayType.JX11_R3, ChooseType.MACHINE, new String[]{
				"07,09,10",
				"07,09,10",
				"07,09,10",
				"07,09,10",
				"07,09,10",
				"07,09,10",
				});
		Bet bet = jx11BetStrategy.resolve(scheme);
		assertBetNotesTicketsBonus(bet, 6, 2, 19*6);
	}

	@Test
	public void testR3MachineWith10Options() throws BetException{
		setupScheme(PlayType.JX11_R3, ChooseType.MACHINE, new String[]{
				"07,09,10",
				"07,09,10",
				"07,09,10",
				"07,09,10",
				"07,09,10",
				"07,09,10",
				"07,09,10",
				"07,09,10",
				"07,09,10",
				"07,09,10",
				});
		Bet bet = jx11BetStrategy.resolve(scheme);
		assertBetNotesTicketsBonus(bet, 10, 2, 19*10);
	}
	
	@Test
	public void testR3MachineWith21Options() throws BetException{
		setupScheme(PlayType.JX11_R3, ChooseType.MACHINE, new String[]{
				"07,09,10",
				"07,09,10",
				"07,09,10",
				"07,09,10",
				"07,09,10",
				"07,09,10",
				"07,09,10",
				"07,09,10",
				"07,09,10",
				"07,09,10",
				"07,09,10",
				"07,09,10",
				"07,09,10",
				"07,09,10",
				"07,09,10",
				"07,09,10",
				"07,09,10",
				"07,09,10",
				"07,09,10",
				"07,09,10",
				"07,09,10",
				});
		Bet bet = jx11BetStrategy.resolve(scheme);
		assertBetNotesTicketsBonus(bet, 21, 5, 19*21);
	}

	@Test
	public void testR3Manual() throws BetException{
		setupScheme(PlayType.JX11_R3, ChooseType.MANUAL, new String[]{
				"01,02,03,04,05,06,07,08,09,10,11",
				});
		Bet bet = jx11BetStrategy.resolve(scheme);
		assertBetNotesTicketsBonus(bet, 165, 1, 19*165);
	}
	
	@Test
	public void testR3Dan() throws BetException{
		setupScheme(PlayType.JX11_R3, ChooseType.DAN, new String[]{
				"(01)02,03,04,05,06,07,08,09,10,11",
				});
		Bet bet = jx11BetStrategy.resolve(scheme);
		assertBetNotesTicketsBonus(bet, 45, 1, 19*45);
	}
	
	@Test
	public void testR3DanWith2First() throws BetException{
		setupScheme(PlayType.JX11_R3, ChooseType.DAN, new String[]{
				"(06,07)01,02,05,08",
				});
		Bet bet = jx11BetStrategy.resolve(scheme);
		assertBetNotesTicketsBonus(bet, 4, 1, 19*4);
	}

	/**
	 * 很特殊的任选8玩法，要拆分为单式送过去。
	 */
	@Test
	public void testR8Manual() throws BetException{
		setupScheme(PlayType.JX11_R8, ChooseType.MANUAL, new String[]{
				"01,02,03,04,05,06,07,08,09,10,11",
				});
		Bet bet = jx11BetStrategy.resolve(scheme);
		assertBetNotesTicketsBonus(bet, 165, 33, 9*165);
	}

	@Test
	public void testR8ManualWith8Digits() throws BetException{
		String option = "01,03,04,05,06,11,12,13";
		setupScheme(PlayType.JX11_R8, ChooseType.MANUAL, new String[]{
				option,
				});
		Bet bet = jx11BetStrategy.resolve(scheme);
		assertBetNotesTicketsBonus(bet, 1, 1, 9*1);
		assertEquals(option, bet.getTickets().get(0).getCode());
		assertNoRepeatBetCode(bet);
	}

	private void assertNoRepeatBetCode(Bet bet) {
		Set<String> betSet = new LinkedHashSet<String>();
		for (Ticket t : bet.getTickets()){
			String code = t.getCode();
			String[] opts = code.split(";");
			for (String opt : opts){
				String[] digits = opt.split(",");
				Arrays.sort(digits);
				StringBuilder b = new StringBuilder();
				for (String d: digits){
					b.append(d).append(",");
				}
				String sorted = b.toString();
				if (betSet.contains(sorted)){
					Assert.fail("Find duplicated bet option: " + opt);
				}
				betSet.add(sorted);
			}
		}
	}

	@Test
	public void testR8ManualWith9Digits() throws BetException{
		String option = "01,03,04,05,06,09,11,12,13";
		setupScheme(PlayType.JX11_R8, ChooseType.MANUAL, new String[]{
				option,
				});
		Bet bet = jx11BetStrategy.resolve(scheme);
		assertBetNotesTicketsBonus(bet, 9, 2, 9*9);
		assertNoRepeatBetCode(bet);
	}

	@Test
	public void testR8Dan() throws BetException{
		String option = "(01,02,03)04,05,06,07,08,09,10,11";
		setupScheme(PlayType.JX11_R8, ChooseType.DAN, new String[]{
				option,
				});
		Bet bet = jx11BetStrategy.resolve(scheme);
		assertBetNotesTicketsBonus(bet, 56, 12, 9*56);
		assertNoRepeatBetCode(bet);
	}
	
	@Test
	public void testD2Manual() throws BetException{
		setupScheme(PlayType.JX11_D2, ChooseType.MANUAL, new String[]{
				"01,02,03,04,06,07,08,09,10,11|01,03,05,07,09,11",
				});
		Bet bet = jx11BetStrategy.resolve(scheme);
		assertBetNotesTicketsBonus(bet, 55, 1, 130*55);
	}

	@Test
	public void testD2MachineWith1Option() throws BetException{
		setupScheme(PlayType.JX11_D2, ChooseType.MACHINE, new String[]{
				"07|06",
				});
		Bet bet = jx11BetStrategy.resolve(scheme);
		assertBetNotesTicketsBonus(bet, 1, 1, 130*1);
	}

	@Test
	public void testD2MachineWith5Option() throws BetException{
		setupScheme(PlayType.JX11_D2, ChooseType.MACHINE, new String[]{
				"07|06",
				"07|06",
				"07|06",
				"07|06",
				"07|06",
				});
		Bet bet = jx11BetStrategy.resolve(scheme);
		assertBetNotesTicketsBonus(bet, 5, 1, 130*5);
	}

	@Test
	public void testD2MachineWith10Option() throws BetException{
		setupScheme(PlayType.JX11_D2, ChooseType.MACHINE, new String[]{
				"07|06",
				"07|06",
				"07|06",
				"07|06",
				"07|06",
				"07|06",
				"07|06",
				"07|06",
				"07|06",
				"07|06",
				});
		Bet bet = jx11BetStrategy.resolve(scheme);
		assertBetNotesTicketsBonus(bet, 10, 2, 130*10);
	}

	@Test
	public void testD3Manual() throws BetException{
		setupScheme(PlayType.JX11_D3, ChooseType.MANUAL, new String[]{
				"01,03|02,04|01,04,05,09,11",
				});
		Bet bet = jx11BetStrategy.resolve(scheme);
		assertBetNotesTicketsBonus(bet, 16, 1, 1170*16);
	}

	@Test
	public void testD3Machine() throws BetException{
		setupScheme(PlayType.JX11_D3, ChooseType.MACHINE, new String[]{
				"08|10|11",
				"02|10|07",
				"11|02|08",
				"07|03|09",
				"02|01|10",
				"11|10|08",
				"10|05|07",
				"07|03|02",
				"08|11|10",
				"08|09|02",
				"11|05|02",
				"06|01|09",
				"08|02|06",
				"06|04|01",
				"08|01|02",
				"02|07|11",
				"08|02|07",
				"07|01|11",
				"03|06|01",                                              
				"08|10|05",
				"11|03|07",                                               
				"01|07|02",                                           
				"10|09|04",                                      
				"02|01|05",
				"02|05|07",
				"10|08|01",
				"07|05|01",                                
				"04|06|05",
				"10|03|11",                                
				"04|07|09",                                
				"05|04|06",                                
				"10|07|02",
				"07|06|10",
				});
		Bet bet = jx11BetStrategy.resolve(scheme);
		assertBetNotesTicketsBonus(bet, 33, 7, 1170*33);
	}
	
	@Test
	public void testG2Manual() throws BetException{
		setupScheme(PlayType.JX11_G2, ChooseType.MANUAL, new String[]{
				"01,02,03,04,05,06,07,08,09,10,11",
				});
		Bet bet = jx11BetStrategy.resolve(scheme);
		assertBetNotesTicketsBonus(bet, 55, 1, 65*55);
	}
	
	@Test
	public void testG2Machine() throws BetException{
		setupScheme(PlayType.JX11_G2, ChooseType.MACHINE, new String[]{
				"05,09",
				"09,11",
				"04,10",
				"03,08",
				"04,10",
				"04,07",
				});
		Bet bet = jx11BetStrategy.resolve(scheme);
		assertBetNotesTicketsBonus(bet, 6, 2, 65*6);
	}

	@Test
	public void testG2Dan() throws BetException{
		setupScheme(PlayType.JX11_G2, ChooseType.DAN, new String[]{
				"(01)03,05",
				});
		Bet bet = jx11BetStrategy.resolve(scheme);
		assertBetNotesTicketsBonus(bet, 2, 1, 65*2);
	}

	@Test
	public void testG3Manual() throws BetException{
		setupScheme(PlayType.JX11_G3, ChooseType.MANUAL, new String[]{
				"01,03,07",
				});
		Bet bet = jx11BetStrategy.resolve(scheme);
		assertBetNotesTicketsBonus(bet, 1, 1, 195*1);
	}
	
	@Test
	public void testG3Machine() throws BetException{
		setupScheme(PlayType.JX11_G3, ChooseType.MACHINE, new String[]{
				"01,03,07",
				"01,03,07",
				"01,03,07",
				"01,03,07",
				"01,03,07",
				"01,03,07",
				});
		Bet bet = jx11BetStrategy.resolve(scheme);
		assertBetNotesTicketsBonus(bet, 6, 2, 195*6);
	}
	
	@Test
	public void testG3Dan() throws BetException{
		setupScheme(PlayType.JX11_G3, ChooseType.DAN, new String[]{
				"(01,02)03,04,05,06,07,08,09,10,11",
				});
		Bet bet = jx11BetStrategy.resolve(scheme);
		assertBetNotesTicketsBonus(bet, 9, 1, 195*9);
	}
}

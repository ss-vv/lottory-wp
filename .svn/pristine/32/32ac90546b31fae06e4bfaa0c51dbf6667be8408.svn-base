package com.xhcms.lottery.commons.data.ctfb;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.xhcms.lottery.commons.data.ChooseType;
import com.xhcms.lottery.commons.persist.service.BetException;
import com.xhcms.lottery.lang.Constants;
import com.xhcms.lottery.lang.PlayType;

/**
 * 测试传统足彩投注项类。
 * 
 * @author Yang Bo
 */
public class CTBetOptionTest {

	@Test
	public void test14SF() throws BetException {
		CTBetOption opt = new CTBetOption(PlayType.CTZC_14, ChooseType.MANUAL, 
				"310,3,1,0,3,1,0,3,1,0,3,1,0,30", null);
		assertEquals(6, opt.getNotes());
		assertEquals(12, opt.getMoney());
		
		opt = new CTBetOption(PlayType.CTZC_14, ChooseType.MANUAL, 
				"310,3,1,0,3, 310, 013,3,1,0,3,1,0,30", null);
		assertEquals(54, opt.getNotes());
		assertEquals(108, opt.getMoney());
		assertEquals(Constants.ZM_BETTYPE_CTZC_FS, opt.getBetType());
	}

	@Test(expected=BetException.class)
	public void test14SFException() throws BetException {
		new CTBetOption(PlayType.CTZC_14, ChooseType.MANUAL, 
				"310,3,12,0,3,1,0,3,1,0,3,1,0,30", null);
	}
	
	@Test
	public void shouldBeDS() throws BetException{
		CTBetOption opt = new CTBetOption(PlayType.CTZC_14, ChooseType.MANUAL, 
				"3,3,1,0,3,1,0,3,1,0,3,1,0,0", null);
		assertEquals(Constants.ZM_BETTYPE_CTZC_DS, opt.getBetType());
	}
	
	@Test
	public void testR9Notes() throws BetException{
		CTBetOption opt = new CTBetOption(PlayType.CTZC_R9, ChooseType.MANUAL, 
				"310,3,1,0,3,1,0,3,1,0,3,1,0,0", null);
		assertEquals(Constants.ZM_BETTYPE_CTZC_FS, opt.getBetType());
		assertEquals(4576, opt.getNotes());
		
		opt = new CTBetOption(PlayType.CTZC_R9, ChooseType.MANUAL, "0,3,1,0,3,1,0,3,1,0,3,1,0,0", null);
		assertEquals(Constants.ZM_BETTYPE_CTZC_DS, opt.getBetType());
		assertEquals(2002, opt.getNotes());

		opt = new CTBetOption(PlayType.CTZC_R9, ChooseType.MANUAL, "-,-,310,310,10,1,0,3,1,0,3,-,-,-", null);
		assertEquals(Constants.ZM_BETTYPE_CTZC_FS, opt.getBetType());
		assertEquals(18, opt.getNotes());

		opt = new CTBetOption(PlayType.CTZC_R9, ChooseType.MANUAL, "-,-,310,310,10,1,0,3,1,0,3,30,-,-", null);
		assertEquals(Constants.ZM_BETTYPE_CTZC_FS, opt.getBetType());
		assertEquals(276, opt.getNotes());
		
		opt = new CTBetOption(PlayType.CTZC_R9, ChooseType.MANUAL, "310,310,310,310,310,310,310,310,310,310,310,310,310,310", null);
		assertEquals(Constants.ZM_BETTYPE_CTZC_FS, opt.getBetType());
		assertEquals(39405366, opt.getNotes());
	}
	
	@Test
	public void testR9Dan() throws BetException{
		CTBetOption opt = new CTBetOption(PlayType.CTZC_R9, ChooseType.MANUAL, 
				"310,013,130,0,3,1,0,3,1,0,3,1,0,0", "0,1");
		assertEquals(Constants.ZM_BETTYPE_CTZC_FS, opt.getBetType());
		assertEquals(15444, opt.getNotes());
	}
	
	@Test
	public void testR9Notes2() throws BetException {
		CTBetOption opt = new CTBetOption(PlayType.CTZC_R9, ChooseType.MANUAL, "-,-,310,310,3,1,0,3,1,0,3,-,-,-", null);
		assertEquals(Constants.ZM_BETTYPE_CTZC_FS, opt.getBetType());
		assertEquals(9, opt.getNotes());
	}
	
	@Test
	public void testR9Reverse() throws BetException {
		CTBetOption opt = new CTBetOption(PlayType.CTZC_R9, ChooseType.MANUAL, "-,-,-,-,-,310,310,3,1,0,3,1,0,3", null);
		assertEquals(Constants.ZM_BETTYPE_CTZC_FS, opt.getBetType());
		assertEquals(9, opt.getNotes());
	}
	
	@Test
	public void testR9Reverse10C8D() throws BetException{
		CTBetOption opt = new CTBetOption(PlayType.CTZC_R9, ChooseType.MANUAL, "-,-,-,-,310,310,310,310,310,310,310,310,310,310", 
				"6,7,8,9,10,11,12,13");
		assertEquals(Constants.ZM_BETTYPE_CTZC_FS, opt.getBetType());
		assertEquals(39366, opt.getNotes());
		
		opt = new CTBetOption(PlayType.CTZC_R9, ChooseType.MANUAL, 
				"-,-,-,-,3,3,3,310,310,310,310,310,310,310", 
				"6,7,8,9,10,11,12,13");
		assertEquals(Constants.ZM_BETTYPE_CTZC_FS, opt.getBetType());
		assertEquals(4374, opt.getNotes());
	}
	
	@Test
	public void test1() throws BetException{
		CTBetOption opt = new CTBetOption(PlayType.CTZC_R9, 
				ChooseType.MANUAL, 
				"-,-,-,-,3,3,3,310,310,310,310,310,310,310", 
				"6,7,8,9,10,11,12,13");
		assertEquals(Constants.ZM_BETTYPE_CTZC_FS, opt.getBetType());
		assertEquals(4374, opt.getNotes());
	}
	
	@Test
	public void test2() throws BetException{
		CTBetOption opt = new CTBetOption(PlayType.CTZC_R9, 
				ChooseType.MANUAL, 
				"-,-,-,-,-,310,310,310,310,310,310,310,310,310", 
				"6,7,8,9,10,11,12,13");
		assertEquals(19683, opt.getNotes());
	}
	
	@Test
	public void test3() throws BetException{
		CTBetOption opt = new CTBetOption(PlayType.CTZC_R9, 
				ChooseType.MANUAL, 
				"-,-,-,-,310,310,310,310,310,310,310,310,310,310", 
				"6,7,8,9,10,11,12,13");
		assertEquals(39366, opt.getNotes());
	}

	@Test
	public void test4() throws BetException{
		CTBetOption opt = new CTBetOption(PlayType.CTZC_R9, 
				ChooseType.MANUAL, 
				"-,-,-,-,3,3,10,310,310,310,310,310,310,310", 
				"6,7,8,9,10,11,12,13");
		assertEquals(8748, opt.getNotes());
	}
	
	@Test(expected=BetException.class)
	public void R9MustHave9Selection() throws BetException {
		new CTBetOption(PlayType.CTZC_R9, ChooseType.MANUAL, 
				"310,3,1,0,3,1,0,-,-,-,-,-,-,-", "");
	}
	
	@Test
	public void testBQNotes() throws BetException{
		CTBetOption opt = new CTBetOption(PlayType.CTZC_BQ, ChooseType.MANUAL, 
				"310,310,3,1,0,3,1,0,3,1,1,1", "");
		assertEquals(Constants.ZM_BETTYPE_CTZC_FS, opt.getBetType());
		assertEquals(9, opt.getNotes());
		
		opt = new CTBetOption(PlayType.CTZC_BQ, ChooseType.MANUAL, 
				"310,310,310,310,310,310,310,310,310,310,310,310", "");
		assertEquals(Constants.ZM_BETTYPE_CTZC_FS, opt.getBetType());
		assertEquals(531441, opt.getNotes());
	}
	
	@Test
	public void jqNotes() throws BetException{
		CTBetOption opt = new CTBetOption(PlayType.CTZC_JQ, ChooseType.MANUAL, 
				"0123,0123,0123,0123,0123,0123,0123,0123", "");
		assertEquals(Constants.ZM_BETTYPE_CTZC_FS, opt.getBetType());
		assertEquals(65536, opt.getNotes());
		opt = new CTBetOption(PlayType.CTZC_JQ, ChooseType.MANUAL, 
				"3,0123,0123,0123,0123,0123,0,0", "");
		assertEquals(Constants.ZM_BETTYPE_CTZC_FS, opt.getBetType());
		assertEquals(1024, opt.getNotes());
	}
}

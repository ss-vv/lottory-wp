package com.xhcms.lottery.commons.data.jx11;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.xhcms.lottery.commons.data.BetOption;
import com.xhcms.lottery.commons.data.ChooseType;
import com.xhcms.lottery.commons.persist.service.BetException;
import com.xhcms.lottery.lang.PlayType;

/**
 * 测试 JX11BetOption.<br/>
 * Manual 手选，Machine 机选，Dan 胆拖
 * 
 * @author Yang Bo
 */
public class JX11BetOptionTest {

	/**
	 * 测试 前1，手选 的各种情况。
	 */
	@Test
	public void testR1Manual() throws BetException {
		BetOption option = new JX11BetOption(PlayType.JX11_R1, ChooseType.MANUAL, "01");
		assertEquals(1, option.getNotes());
		assertEquals(2, option.getMoney());

		option = new JX11BetOption(PlayType.JX11_R1, ChooseType.MANUAL, "01,02");
		assertEquals(2, option.getNotes());
		assertEquals(4, option.getMoney());

		option = new JX11BetOption(PlayType.JX11_R1, ChooseType.MANUAL, "01,02,03,04,05,06,07,08,09,10,11");
		assertEquals(11, option.getNotes());
		assertEquals(22, option.getMoney());
	}

	/**
	 * 测试 前1，机选 的各种情况。
	 */
	@Test
	public void testR1Machine() throws BetException {
		BetOption option = new JX11BetOption(PlayType.JX11_R1, ChooseType.MACHINE, "06");
		assertEquals(1, option.getNotes());
		assertEquals(2, option.getMoney());
	}
	
	@Test(expected=BetException.class)
	public void R1MachineShouldHas1Digit() throws BetException{
		new JX11BetOption(PlayType.JX11_R1, ChooseType.MACHINE, "01,02");
	}

	@Test(expected=BetException.class)
	public void R1MachineShouldNotHasSemicolon() throws BetException{
		new JX11BetOption(PlayType.JX11_R1, ChooseType.MACHINE, "01;02");
	}

	/**
	 * 测试 任选2，手选 的各种情况。
	 */
	@Test
	public void testR2Manual() throws BetException {
		BetOption option = new JX11BetOption(PlayType.JX11_R2, ChooseType.MANUAL, "01,02,03,04,05,06,07,08,09,10,11");
		assertEquals(55, option.getNotes());
		assertEquals(110, option.getMoney());

		option = new JX11BetOption(PlayType.JX11_R2, ChooseType.MANUAL, "01,02,03,04,05,06,07,08,09,10");
		assertEquals(45, option.getNotes());
		assertEquals(90, option.getMoney());

		option = new JX11BetOption(PlayType.JX11_R2, ChooseType.MANUAL, "01,02,03,04,05,06,07,08,09");
		assertEquals(36, option.getNotes());
		assertEquals(72, option.getMoney());
		
		option = new JX11BetOption(PlayType.JX11_R2, ChooseType.MANUAL, "01,02");
		assertEquals(1, option.getNotes());
		assertEquals(2, option.getMoney());
	}
	
	@Test(expected= BetException.class)
	public void whenR2ManualHas1DigitThenException() throws BetException{
		new JX11BetOption(PlayType.JX11_R2, ChooseType.MANUAL, "01");
	}
	
	@Test
	public void testR2Dan() throws BetException{
		BetOption option = new JX11BetOption(PlayType.JX11_R2, ChooseType.DAN, "(01)02,03,04,05,06,07,08,09,10,11");
		assertEquals(10, option.getNotes());
		assertEquals(20, option.getMoney());
	}
	
	@Test
	public void testR3Manual() throws BetException{
		BetOption option = new JX11BetOption(PlayType.JX11_R3, ChooseType.MANUAL, "01,02,03,04,05,06,07,08,09,10,11");
		assertEquals(165, option.getNotes());
		assertEquals(330, option.getMoney());
		
		option = new JX11BetOption(PlayType.JX11_R3, ChooseType.MANUAL, "04,09,10,11");
		assertEquals(4, option.getNotes());
		assertEquals(8, option.getMoney());

		option = new JX11BetOption(PlayType.JX11_R3, ChooseType.MANUAL, "04,09,10");
		assertEquals(1, option.getNotes());
		assertEquals(2, option.getMoney());
	}
	
	@Test(expected= BetException.class)
	public void whenR3ManualHas2DigitThenException() throws BetException{
		new JX11BetOption(PlayType.JX11_R3, ChooseType.MANUAL, "01,02");
	}
	
	@Test
	public void testR3Dan() throws BetException{
		BetOption option = new JX11BetOption(PlayType.JX11_R3, ChooseType.DAN, "(01)02,03,04,05,06,07,08,09,10,11");
		assertEquals(45, option.getNotes());
		assertEquals(90, option.getMoney());
		
		option = new JX11BetOption(PlayType.JX11_R3, ChooseType.DAN, "(08,10)01,02,03,04,05,06,07,09,11");
		assertEquals(9, option.getNotes());
		assertEquals(18, option.getMoney());
	}
	
	@Test(expected=BetException.class)
	public void R3DanShouldHaveAtLeast4Digits() throws BetException{
		new JX11BetOption(PlayType.JX11_R3, ChooseType.DAN, "(01)02,03");
	}
	
	@Test
	public void testR3Machine() throws BetException{
		BetOption option = new JX11BetOption(PlayType.JX11_R3, ChooseType.MACHINE, "04,05,06");
		assertEquals(1, option.getNotes());
		assertEquals(2, option.getMoney());
	}
	
	@Test(expected= BetException.class)
	public void whenR3MachineHas4DigitsThenException() throws BetException{
		new JX11BetOption(PlayType.JX11_R3, ChooseType.MACHINE, "01,02,03,04");
	}
	
	@Test
	public void testR4Manual() throws BetException{
		BetOption option = new JX11BetOption(PlayType.JX11_R4, ChooseType.MANUAL, "01,02,03,04,05,06,07,08,09,10,11");
		assertEquals(330, option.getNotes());
		assertEquals(660, option.getMoney());
	}

	@Test
	public void testR4Dan() throws BetException{
		BetOption option = new JX11BetOption(PlayType.JX11_R4, ChooseType.DAN, "(01,02)05,06,07,08");
		assertEquals(6, option.getNotes());
		assertEquals(12, option.getMoney());
	}
	
	@Test
	public void testR4Machine() throws BetException{
		BetOption option = new JX11BetOption(PlayType.JX11_R4, ChooseType.MACHINE, "01,03,04,08");
		assertEquals(1, option.getNotes());
		assertEquals(2, option.getMoney());
	}
	
	@Test(expected=BetException.class)
	public void R4MachineShouldNotContainSemicolon() throws BetException{
		new JX11BetOption(PlayType.JX11_R4, ChooseType.MACHINE, "01,03,04,08;05,09");
	}
	
	@Test
	public void testD2Manual() throws BetException{
		BetOption option = new JX11BetOption(PlayType.JX11_D2, ChooseType.MANUAL, "01,02,03,04,05,06,07,08,09,10,11|01,02,03,04,05,06,07,08,09,10,11");
		assertEquals(110, option.getNotes());
		assertEquals(220, option.getMoney());
		
		option = new JX11BetOption(PlayType.JX11_D2, ChooseType.MANUAL, "01|11");
		assertEquals(1, option.getNotes());
		assertEquals(2, option.getMoney());
	}

	@Test
	public void testD3Manual() throws BetException{
		BetOption option = new JX11BetOption(PlayType.JX11_D3, ChooseType.MANUAL, "01,02,03,04,05,06,07,08,09,10,11|01,02,03,04,05,06,07,08,09,10,11|01,02,03,04,05,06,07,08,09,10,11");
		assertEquals(990, option.getNotes());
		assertEquals(1980, option.getMoney());
		
		option = new JX11BetOption(PlayType.JX11_D3, ChooseType.MANUAL, "09|02|01");
		assertEquals(1, option.getNotes());
		assertEquals(2, option.getMoney());
	}
	
	@Test(expected=BetException.class)
	public void whenD3HasAllDuplicatedDigitsThenException() throws BetException{
		new JX11BetOption(PlayType.JX11_D3, ChooseType.MANUAL, "01,09|01,09|01,09");
	}
	
	@Test
	public void testD2Machine() throws BetException{
		BetOption option = new JX11BetOption(PlayType.JX11_D2, ChooseType.MACHINE, "04|11");
		assertEquals(1, option.getNotes());
		assertEquals(2, option.getMoney());
	}
	
	@Test
	public void testD3Machine() throws BetException{
		BetOption option = new JX11BetOption(PlayType.JX11_D3, ChooseType.MACHINE, "04|11|03");
		assertEquals(1, option.getNotes());
		assertEquals(2, option.getMoney());
	}
	
	@Test
	public void testG2Manual() throws BetException{
		BetOption option = new JX11BetOption(PlayType.JX11_G2, ChooseType.MANUAL, "01,02,03,04,05,06,07,08,09,10,11");
		assertEquals(55, option.getNotes());
		assertEquals(110, option.getMoney());
		
		option = new JX11BetOption(PlayType.JX11_G2, ChooseType.MANUAL, "02,04,06,07,09,10,11");
		assertEquals(21, option.getNotes());
		assertEquals(42, option.getMoney());
	}

	@Test
	public void testG2Dan() throws BetException{
		BetOption option = new JX11BetOption(PlayType.JX11_G2, ChooseType.DAN, "(01)02,03,04,05,06,07,08,09,10,11");
		assertEquals(10, option.getNotes());
		assertEquals(20, option.getMoney());
		
		option = new JX11BetOption(PlayType.JX11_G2, ChooseType.DAN, "(01)02,03");
		assertEquals(2, option.getNotes());
		assertEquals(4, option.getMoney());
	}
	
	@Test(expected=BetException.class)
	public void whenG2HasOnly2DigitsThenException() throws BetException{
		new JX11BetOption(PlayType.JX11_G2, ChooseType.DAN, "(01)02");
	}

	@Test
	public void testG2Machine() throws BetException{
		BetOption option = new JX11BetOption(PlayType.JX11_G2, ChooseType.MACHINE, "06,07");
		assertEquals(1, option.getNotes());
		assertEquals(2, option.getMoney());
	}
	
	@Test
	public void testG3Manual() throws BetException{
		BetOption option = new JX11BetOption(PlayType.JX11_G3, ChooseType.MANUAL, "01,02,03,04,05,06,07,08,09,10,11");
		assertEquals(165, option.getNotes());
		assertEquals(330, option.getMoney());
		
		option = new JX11BetOption(PlayType.JX11_G3, ChooseType.MANUAL, "09,10,11");
		assertEquals(1, option.getNotes());
		assertEquals(2, option.getMoney());
	}

	@Test
	public void testG3Dan() throws BetException{
		BetOption option = new JX11BetOption(PlayType.JX11_G3, ChooseType.DAN, "(01,02)03,04,05,06,07,08,09,10,11");
		assertEquals(9, option.getNotes());
		assertEquals(18, option.getMoney());

		option = new JX11BetOption(PlayType.JX11_G3, ChooseType.DAN, "(01,02)03,04");
		assertEquals(2, option.getNotes());
		assertEquals(4, option.getMoney());
	}

	@Test(expected=BetException.class)
	public void whenG3HasOnly3DigitsThenException() throws BetException{
		new JX11BetOption(PlayType.JX11_G3, ChooseType.DAN, "(01)02,03");
	}
	
	@Test
	public void testG3Machine() throws BetException{
		BetOption option = new JX11BetOption(PlayType.JX11_G3, ChooseType.MACHINE, "01,06,07");
		assertEquals(1, option.getNotes());
		assertEquals(2, option.getMoney());
	}
}

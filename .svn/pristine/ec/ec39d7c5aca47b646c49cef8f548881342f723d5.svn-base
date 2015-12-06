package com.xhcms.lottery.commons.utils.internal;

import org.junit.Test;

import com.xhcms.lottery.lang.PlayType;
import static org.junit.Assert.assertEquals;
import com.xhcms.lottery.commons.data.ssq.SSQBetOption;
import com.xhcms.lottery.commons.persist.service.BetException;

/**
 * 双色球订单注数计算器测试。
 * 
 * @author zhangdebin
 */

public class SSQBetOptionTest {

	/**
	 * 双色球单式投注测试
	 * 
	 * @throws BetException
	 */
	@Test
	public void test_DanShi() throws BetException {

		SSQBetOption ssqBetOption;

		ssqBetOption = new SSQBetOption(PlayType.SSQ_DS, "01,02,03,04,05,06|01");
		assertEquals(1, ssqBetOption.getNotes());

		ssqBetOption = new SSQBetOption(PlayType.SSQ_DS, "01,02,03,04,05,06|01;01,02,03,04,05,07|02;01,02,03,04,05,16|07");
		assertEquals(3, ssqBetOption.getNotes());
	}

	/**
	 * 双色球复式投注测试
	 * 
	 * @throws BetException
	 */
	@Test
	public void test_FuShi() throws BetException {

		SSQBetOption ssqBetOption;

		ssqBetOption = new SSQBetOption(PlayType.SSQ_FS, "01,02,03,04,05,06,07|01");
		assertEquals(7, ssqBetOption.getNotes());

		ssqBetOption = new SSQBetOption(PlayType.SSQ_FS, "01,02,03,04,05,06,07,08|01");
		assertEquals(28, ssqBetOption.getNotes());

		ssqBetOption = new SSQBetOption(PlayType.SSQ_FS, "01,02,03,04,05,06,07,08,09|01,02,03");
		assertEquals(252, ssqBetOption.getNotes());
	}

	/**
	 * 双色球胆拖投注测试
	 * 
	 * @throws BetException
	 */
	@Test
	public void test_DanTuo() throws BetException {

		SSQBetOption ssqBetOption;

		ssqBetOption = new SSQBetOption(PlayType.SSQ_DT, "(01,02,03),04,05,06,07,08|07,08");
		assertEquals(20, ssqBetOption.getNotes());

		ssqBetOption = new SSQBetOption(PlayType.SSQ_DT, "(01,02,03,04),05,06,07,08|01");
		assertEquals(6, ssqBetOption.getNotes());

		ssqBetOption = new SSQBetOption(PlayType.SSQ_DT, "(01,02),03,04,05,06,07,08,09|01,02,03");
		assertEquals(105, ssqBetOption.getNotes());
	}
}
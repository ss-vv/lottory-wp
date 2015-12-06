package com.xhcms.lottery.commons.utils.internal;

import java.util.Map;

import org.junit.Test;

import com.xhcms.lottery.lang.PlayType;
import static org.junit.Assert.assertEquals;
import com.xhcms.lottery.commons.data.cqss.CQSSBetOption;
import com.xhcms.lottery.commons.persist.service.BetException;

/**
 * 重庆时时彩订单注数计算器测试。
 * 
 * @author zhangdebin
 */

public class CQSSBetOptionTest {

	/**
	 * 大小单双测试
	 * 
	 * @throws BetException
	 */
	@Test
	public void test_1X_DXDS() throws BetException {

		CQSSBetOption cqSSCBetOption;

		cqSSCBetOption = new CQSSBetOption(PlayType.CQSS_DXDS, "12;23;34");
		assertEquals(3, cqSSCBetOption.getNotes());
	}

	/**
	 * 五星直选单式测试
	 * 
	 * @throws BetException
	 */
	@Test
	public void test_5X_DS() throws BetException {

		CQSSBetOption cqSSCBetOption;

		cqSSCBetOption = new CQSSBetOption(PlayType.CQSS_5X_DS, "1,0,3,1,5");
		assertEquals(1, cqSSCBetOption.getNotes());
	}

	/**
	 * 二星直选复式测试
	 * 
	 * @throws BetException
	 */
	@Test
	public void test_2X_FS() throws BetException {

		CQSSBetOption cqSSCBetOption;

		cqSSCBetOption = new CQSSBetOption(PlayType.CQSS_2X_FS, "03456789,123");
		assertEquals(24, cqSSCBetOption.getNotes());

		cqSSCBetOption = new CQSSBetOption(PlayType.CQSS_2X_FS, "12,234");
		assertEquals(6, cqSSCBetOption.getNotes());
	}

	/**
	 * 二星组选组合(二星组选复式)
	 * 
	 * @throws BetException
	 */
	@Test
	public void test_2X_ZX_ZH() throws BetException {

		CQSSBetOption cqSSCBetOption;

		cqSSCBetOption = new CQSSBetOption(PlayType.CQSS_2X_ZX_ZH, "123");
		assertEquals(3, cqSSCBetOption.getNotes());

		cqSSCBetOption = new CQSSBetOption(PlayType.CQSS_2X_ZX_ZH, "2345");
		assertEquals(6, cqSSCBetOption.getNotes());
	}

	/**
	 * 三星直选复式测试
	 * 
	 * @throws BetException
	 */
	@Test
	public void test_3X_FS() throws BetException {

		CQSSBetOption cqSSCBetOption;

		cqSSCBetOption = new CQSSBetOption(PlayType.CQSS_3X_FS, "03456789,123,456");
		assertEquals(72, cqSSCBetOption.getNotes());

		cqSSCBetOption = new CQSSBetOption(PlayType.CQSS_3X_FS, "12,234,456");
		assertEquals(18, cqSSCBetOption.getNotes());
	}

	/**
	 * 五星直选复式测试
	 * 
	 * @throws BetException
	 */
	@Test
	public void test_5X_FS() throws BetException {

		CQSSBetOption cqSSCBetOption;

		cqSSCBetOption = new CQSSBetOption(PlayType.CQSS_5X_FS, "34567890,0,123,1234,12345");
		assertEquals(480, cqSSCBetOption.getNotes());

		cqSSCBetOption = new CQSSBetOption(PlayType.CQSS_5X_FS, "12,234,9,1,3");
		assertEquals(6, cqSSCBetOption.getNotes());
	}

	/**
	 * 二星直选和值测试
	 * 
	 * @throws BetException
	 */
	@Test
	public void test_2X_HZ() throws BetException {
		java.util.Map<Integer, Integer> x2ZhiXuanMap = new java.util.HashMap<Integer, Integer>();
		x2ZhiXuanMap.put(0, 1);
		x2ZhiXuanMap.put(1, 2);
		x2ZhiXuanMap.put(2, 3);
		x2ZhiXuanMap.put(3, 4);
		x2ZhiXuanMap.put(4, 5);
		x2ZhiXuanMap.put(5, 6);
		x2ZhiXuanMap.put(6, 7);
		x2ZhiXuanMap.put(7, 8);
		x2ZhiXuanMap.put(8, 9);
		x2ZhiXuanMap.put(9, 10);
		x2ZhiXuanMap.put(10, 9);
		x2ZhiXuanMap.put(11, 8);
		x2ZhiXuanMap.put(12, 7);
		x2ZhiXuanMap.put(13, 6);
		x2ZhiXuanMap.put(14, 5);
		x2ZhiXuanMap.put(15, 4);
		x2ZhiXuanMap.put(16, 3);
		x2ZhiXuanMap.put(17, 2);
		x2ZhiXuanMap.put(18, 1);

		CQSSBetOption cqSSCBetOption;
		for (Map.Entry<Integer, Integer> entry : x2ZhiXuanMap.entrySet()) {
			Integer key = entry.getKey();
			Integer value = entry.getValue();

			cqSSCBetOption = new CQSSBetOption(PlayType.CQSS_2X_HZ, String.valueOf(key));
			assertEquals(value.intValue(), cqSSCBetOption.getNotes());
		}

		cqSSCBetOption = new CQSSBetOption(PlayType.CQSS_2X_HZ, "0");
		assertEquals(1, cqSSCBetOption.getNotes());

		cqSSCBetOption = new CQSSBetOption(PlayType.CQSS_2X_HZ, "5");
		assertEquals(6, cqSSCBetOption.getNotes());

		cqSSCBetOption = new CQSSBetOption(PlayType.CQSS_2X_HZ, "8");
		assertEquals(9, cqSSCBetOption.getNotes());

		cqSSCBetOption = new CQSSBetOption(PlayType.CQSS_2X_HZ, "9");
		assertEquals(10, cqSSCBetOption.getNotes());

		cqSSCBetOption = new CQSSBetOption(PlayType.CQSS_2X_HZ, "10");
		assertEquals(9, cqSSCBetOption.getNotes());

		cqSSCBetOption = new CQSSBetOption(PlayType.CQSS_2X_HZ, "18");
		assertEquals(1, cqSSCBetOption.getNotes());

		cqSSCBetOption = new CQSSBetOption(PlayType.CQSS_2X_HZ, "0;5;8;9;10");
		assertEquals(35, cqSSCBetOption.getNotes());
	}

	/**
	 * 二星组选和值测试
	 * 
	 * @throws BetException
	 */
	@Test
	public void test_2X_ZX_HZ() throws BetException {

		java.util.Map<Integer, Integer> x2ZuXuanMap = new java.util.HashMap<Integer, Integer>();
		x2ZuXuanMap.put(0, 1);
		x2ZuXuanMap.put(1, 1);
		x2ZuXuanMap.put(2, 2);
		x2ZuXuanMap.put(3, 2);
		x2ZuXuanMap.put(4, 3);
		x2ZuXuanMap.put(5, 3);
		x2ZuXuanMap.put(6, 4);
		x2ZuXuanMap.put(7, 4);
		x2ZuXuanMap.put(8, 5);
		x2ZuXuanMap.put(9, 5);
		x2ZuXuanMap.put(10, 5);
		x2ZuXuanMap.put(11, 4);
		x2ZuXuanMap.put(12, 4);
		x2ZuXuanMap.put(13, 3);
		x2ZuXuanMap.put(14, 3);
		x2ZuXuanMap.put(15, 2);
		x2ZuXuanMap.put(16, 2);
		x2ZuXuanMap.put(17, 1);
		x2ZuXuanMap.put(18, 1);

		CQSSBetOption cqSSCBetOption;
		for (Map.Entry<Integer, Integer> entry : x2ZuXuanMap.entrySet()) {
			Integer key = entry.getKey();
			Integer value = entry.getValue();

			cqSSCBetOption = new CQSSBetOption(PlayType.CQSS_2X_ZX_HZ, String.valueOf(key));
			assertEquals(value.intValue(), cqSSCBetOption.getNotes());
		}

		cqSSCBetOption = new CQSSBetOption(PlayType.CQSS_2X_ZX_HZ, "1");
		assertEquals(1, cqSSCBetOption.getNotes());

		cqSSCBetOption = new CQSSBetOption(PlayType.CQSS_2X_ZX_HZ, "6");
		assertEquals(4, cqSSCBetOption.getNotes());

		cqSSCBetOption = new CQSSBetOption(PlayType.CQSS_2X_ZX_HZ, "8");
		assertEquals(5, cqSSCBetOption.getNotes());

		cqSSCBetOption = new CQSSBetOption(PlayType.CQSS_2X_ZX_HZ, "9");
		assertEquals(5, cqSSCBetOption.getNotes());

		cqSSCBetOption = new CQSSBetOption(PlayType.CQSS_2X_ZX_HZ, "10");
		assertEquals(5, cqSSCBetOption.getNotes());

		cqSSCBetOption = new CQSSBetOption(PlayType.CQSS_2X_ZX_HZ, "18");
		assertEquals(1, cqSSCBetOption.getNotes());

		cqSSCBetOption = new CQSSBetOption(PlayType.CQSS_2X_ZX_HZ, "0;6;8;9;10");
		assertEquals(20, cqSSCBetOption.getNotes());
	}

	/**
	 * 三星直选和值测试
	 * 
	 * @throws BetException
	 */
	@Test
	public void test_3X_HZ() throws BetException {

		java.util.Map<Integer, Integer> x3ZhiXuanMap = new java.util.HashMap<Integer, Integer>();
		x3ZhiXuanMap.put(0, 1);
		x3ZhiXuanMap.put(1, 3);
		x3ZhiXuanMap.put(2, 6);
		x3ZhiXuanMap.put(3, 10);
		x3ZhiXuanMap.put(4, 15);
		x3ZhiXuanMap.put(5, 21);
		x3ZhiXuanMap.put(6, 28);
		x3ZhiXuanMap.put(7, 36);
		x3ZhiXuanMap.put(8, 45);
		x3ZhiXuanMap.put(9, 55);
		x3ZhiXuanMap.put(10, 63);
		x3ZhiXuanMap.put(11, 69);
		x3ZhiXuanMap.put(12, 73);
		x3ZhiXuanMap.put(13, 75);
		x3ZhiXuanMap.put(14, 75);
		x3ZhiXuanMap.put(15, 73);
		x3ZhiXuanMap.put(16, 69);
		x3ZhiXuanMap.put(17, 63);
		x3ZhiXuanMap.put(18, 55);
		x3ZhiXuanMap.put(19, 45);
		x3ZhiXuanMap.put(20, 36);
		x3ZhiXuanMap.put(21, 28);
		x3ZhiXuanMap.put(22, 21);
		x3ZhiXuanMap.put(23, 15);
		x3ZhiXuanMap.put(24, 10);
		x3ZhiXuanMap.put(25, 6);
		x3ZhiXuanMap.put(26, 3);
		x3ZhiXuanMap.put(27, 1);

		CQSSBetOption cqSSCBetOption;
		for (Map.Entry<Integer, Integer> entry : x3ZhiXuanMap.entrySet()) {
			Integer key = entry.getKey();
			Integer value = entry.getValue();

			cqSSCBetOption = new CQSSBetOption(PlayType.CQSS_3X_HZ, String.valueOf(key));
			assertEquals(value.intValue(), cqSSCBetOption.getNotes());
		}

		cqSSCBetOption = new CQSSBetOption(PlayType.CQSS_3X_HZ, "12");
		assertEquals(73, cqSSCBetOption.getNotes());

		cqSSCBetOption = new CQSSBetOption(PlayType.CQSS_3X_HZ, "1;6;10;16");
		assertEquals(163, cqSSCBetOption.getNotes());

		cqSSCBetOption = new CQSSBetOption(PlayType.CQSS_3X_HZ, "0;5;8;9;10;12;14;23");
		assertEquals(348, cqSSCBetOption.getNotes());
	}
}
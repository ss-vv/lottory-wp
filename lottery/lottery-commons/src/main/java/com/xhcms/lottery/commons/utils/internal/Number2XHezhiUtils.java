package com.xhcms.lottery.commons.utils.internal;

public class Number2XHezhiUtils
{
	private static java.util.Map<Integer, String> zhiXuanHezhiMap;
	private static java.util.Map<Integer, String> zuXuanHezhiMap;

	private static java.util.Map<Integer, Integer> zhiXuanStakesMap;
	private static java.util.Map<Integer, Integer> zuXuanStakesMap;
	static
	{
		zuXuanHezhiMap = new java.util.HashMap<Integer, String>();
		zhiXuanHezhiMap = new java.util.HashMap<Integer, String>();

		zhiXuanHezhiMap.put(0,  "00");
		zhiXuanHezhiMap.put(1,  "01,10");
		zhiXuanHezhiMap.put(2,  "02,11,20");
		zhiXuanHezhiMap.put(3,  "03,12,21,30");
		zhiXuanHezhiMap.put(4,  "04,13,22,31,40");
		zhiXuanHezhiMap.put(5,  "05,14,23,32,41,50");
		zhiXuanHezhiMap.put(6,  "06,15,24,33,42,51,60");
		zhiXuanHezhiMap.put(7,  "07,16,25,34,43,52,61,70");
		zhiXuanHezhiMap.put(8,  "08,17,26,35,44,53,62,71,80");
		zhiXuanHezhiMap.put(9,  "09,18,27,36,45,54,63,72,81,90");
		zhiXuanHezhiMap.put(10, "19,28,37,46,55,64,73,82,91");
		zhiXuanHezhiMap.put(11, "29,38,47,56,65,74,83,92");
		zhiXuanHezhiMap.put(12, "39,48,57,66,75,84,93");
		zhiXuanHezhiMap.put(13, "49,58,67,76,85,94");
		zhiXuanHezhiMap.put(14, "59,68,77,86,95");
		zhiXuanHezhiMap.put(15, "69,78,87,96");
		zhiXuanHezhiMap.put(16, "79,88,97");
		zhiXuanHezhiMap.put(17, "89,98");
		zhiXuanHezhiMap.put(18, "99");
		
		zuXuanHezhiMap.put(0, "00");
		zuXuanHezhiMap.put(1, "01");
		zuXuanHezhiMap.put(2, "02,11");
		zuXuanHezhiMap.put(3, "03,12");
		zuXuanHezhiMap.put(4, "04,13,22");
		zuXuanHezhiMap.put(5, "05,14,23");
		zuXuanHezhiMap.put(6, "06,15,24,33");
		zuXuanHezhiMap.put(7, "07,16,25,34");
		zuXuanHezhiMap.put(8, "08,17,26,35,44");
		zuXuanHezhiMap.put(9, "09,18,27,36,45");
		zuXuanHezhiMap.put(10, "19,28,37,46,55");
		zuXuanHezhiMap.put(11, "29,38,47,56");
		zuXuanHezhiMap.put(12, "39,48,57,66");
		zuXuanHezhiMap.put(13, "49,58,67");
		zuXuanHezhiMap.put(14, "59,68,77");
		zuXuanHezhiMap.put(15, "69,78");
		zuXuanHezhiMap.put(16, "79,88");
		zuXuanHezhiMap.put(17, "89");
		zuXuanHezhiMap.put(18, "99");

		zhiXuanStakesMap = new java.util.HashMap<Integer, Integer>();
		for (Integer tmpHezhiKey : zhiXuanHezhiMap.keySet())
		{
			String tmpNumberMapItem = zhiXuanHezhiMap.get(tmpHezhiKey);
			if (tmpNumberMapItem != null)
			{
				zhiXuanStakesMap.put(tmpHezhiKey, tmpNumberMapItem.split(",").length);
			}
		}

		zuXuanStakesMap = new java.util.HashMap<Integer, Integer>();
		for (Integer tmpHezhiKey : zuXuanHezhiMap.keySet())
		{
			String tmpNumberMapItem = zuXuanHezhiMap.get(tmpHezhiKey);
			if (tmpNumberMapItem != null)
			{
				zuXuanStakesMap.put(tmpHezhiKey, tmpNumberMapItem.split(",").length);
			}
		}
	}

	public static int getZhiXuanStakeCount(String zhiXuanHe)
	{
		try
		{
			return getZhiXuanStakeCount(Integer.parseInt(zhiXuanHe));
		}
		catch (NumberFormatException exception)
		{
			return 0;
		}
	}
	
	public static int getZhiXuanStakeCount(int zhiXuanHe)
	{
		Integer tmpZhiXuanHe = Integer.valueOf(zhiXuanHe);
		if (!zhiXuanStakesMap.containsKey(tmpZhiXuanHe))
		{
			return 0;
		}
		else
		{
			return zhiXuanStakesMap.get(tmpZhiXuanHe);
		}
	}

	public static int getZuXuanStakeCount(String zuXuanHe)
	{
		try
		{
			return getZuXuanStakeCount(Integer.parseInt(zuXuanHe));
		}
		catch (NumberFormatException exception)
		{
			return 0;
		}
	}
	
	public static int getZuXuanStakeCount(int zuXuanHe)
	{
		Integer tmpZuXuanHe = Integer.valueOf(zuXuanHe);
		if (!zuXuanStakesMap.containsKey(tmpZuXuanHe))
		{
			return 0;
		}
		else
		{
			return zuXuanStakesMap.get(tmpZuXuanHe);
		}
	}

	public static void main(String[] args)
	{
		for (int i = 0; i <= 18; i++)
		{
			System.out.println("直选: " + i + " - " + getZhiXuanStakeCount(i + ""));
		}

		System.out.println("直选:   " + " - " + getZhiXuanStakeCount(""));
		System.out.println("直选: qq" + " - " + getZhiXuanStakeCount("qq"));

		System.out.println("\r\n");
		for (int i = 1; i <= 17; i++)
		{
			System.out.println("组选: " + i + " - " + getZuXuanStakeCount(i + ""));
		}
	}
}
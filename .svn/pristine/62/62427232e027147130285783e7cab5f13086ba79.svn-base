package com.xhcms.lottery.commons.utils.internal;

public class Number3XHezhiUtils
{
	private static java.util.Map<Integer, String> zhiXuanHezhiMap;
	private static java.util.Map<Integer, String> zuXuan3HezhiMap;
	private static java.util.Map<Integer, String> zuXuan6HezhiMap;

	private static java.util.Map<Integer, Integer> zhiXuanStakesMap;
	private static java.util.Map<Integer, Integer> zuXuan3StakesMap;
	private static java.util.Map<Integer, Integer> zuXuan6StakesMap;

	static
	{
		zhiXuanHezhiMap = new java.util.HashMap<Integer, String>();
		zuXuan3HezhiMap = new java.util.HashMap<Integer, String>();
		zuXuan6HezhiMap = new java.util.HashMap<Integer, String>();
		
		zhiXuanHezhiMap.put(0,  "000");
		zhiXuanHezhiMap.put(1,  "001,010,100");
		zhiXuanHezhiMap.put(2,  "002,011,020,101,110,200");
		zhiXuanHezhiMap.put(3,  "003,012,021,030,102,111,120,201,210,300");
		zhiXuanHezhiMap.put(4,  "004,013,022,031,040,103,112,121,130,202,211,220,301,310,400");
		zhiXuanHezhiMap.put(5,  "005,014,023,032,041,050,104,113,122,131,140,203,212,221,230,302,311,320,401,410,500");
		zhiXuanHezhiMap.put(6,  "006,015,024,033,042,051,060,105,114,123,132,141,150,204,213,222,231,240,303,312,321,330,402,411,420,501,510,600");
		zhiXuanHezhiMap.put(7,  "007,016,025,034,043,052,061,070,106,115,124,133,142,151,160,205,214,223,232,241,250,304,313,322,331,340,403,412,421,430,502,511,520,601,610,700");
		zhiXuanHezhiMap.put(8,  "008,017,026,035,044,053,062,071,080,107,116,125,134,143,152,161,170,206,215,224,233,242,251,260,305,314,323,332,341,350,404,413,422,431,440,503,512,521,530,602,611,620,701,710,800");
		zhiXuanHezhiMap.put(9,  "009,018,027,036,045,054,063,072,081,090,108,117,126,135,144,153,162,171,180,207,216,225,234,243,252,261,270,306,315,324,333,342,351,360,405,414,423,432,441,450,504,513,522,531,540,603,612,621,630,702,711,720,801,810,900");
		zhiXuanHezhiMap.put(10, "019,028,037,046,055,064,073,082,091,109,118,127,136,145,154,163,172,181,190,208,217,226,235,244,253,262,271,280,307,316,325,334,343,352,361,370,406,415,424,433,442,451,460,505,514,523,532,541,550,604,613,622,631,640,703,712,721,730,802,811,820,901,910");
		zhiXuanHezhiMap.put(11, "029,038,047,056,065,074,083,092,119,128,137,146,155,164,173,182,191,209,218,227,236,245,254,263,272,281,290,308,317,326,335,344,353,362,371,380,407,416,425,434,443,452,461,470,506,515,524,533,542,551,560,605,614,623,632,641,650,704,713,722,731,740,803,812,821,830,902,911,920");
		zhiXuanHezhiMap.put(12, "039,048,057,066,075,084,093,129,138,147,156,165,174,183,192,219,228,237,246,255,264,273,282,291,309,318,327,336,345,354,363,372,381,390,408,417,426,435,444,453,462,471,480,507,516,525,534,543,552,561,570,606,615,624,633,642,651,660,705,714,723,732,741,750,804,813,822,831,840,903,912,921,930");
		zhiXuanHezhiMap.put(13, "049,058,067,076,085,094,139,148,157,166,175,184,193,229,238,247,256,265,274,283,292,319,328,337,346,355,364,373,382,391,409,418,427,436,445,454,463,472,481,490,508,517,526,535,544,553,562,571,580,607,616,625,634,643,652,661,670,706,715,724,733,742,751,760,805,814,823,832,841,850,904,913,922,931,940");
		zhiXuanHezhiMap.put(14, "059,068,077,086,095,149,158,167,176,185,194,239,248,257,266,275,284,293,329,338,347,356,365,374,383,392,419,428,437,446,455,464,473,482,491,509,518,527,536,545,554,563,572,581,590,608,617,626,635,644,653,662,671,680,707,716,725,734,743,752,761,770,806,815,824,833,842,851,860,905,914,923,932,941,950");
		zhiXuanHezhiMap.put(15, "069,078,087,096,159,168,177,186,195,249,258,267,276,285,294,339,348,357,366,375,384,393,429,438,447,456,465,474,483,492,519,528,537,546,555,564,573,582,591,609,618,627,636,645,654,663,672,681,690,708,717,726,735,744,753,762,771,780,807,816,825,834,843,852,861,870,906,915,924,933,942,951,960");
		zhiXuanHezhiMap.put(16, "079,088,097,169,178,187,196,259,268,277,286,295,349,358,367,376,385,394,439,448,457,466,475,484,493,529,538,547,556,565,574,583,592,619,628,637,646,655,664,673,682,691,709,718,727,736,745,754,763,772,781,790,808,817,826,835,844,853,862,871,880,907,916,925,934,943,952,961,970");
		zhiXuanHezhiMap.put(17, "089,098,179,188,197,269,278,287,296,359,368,377,386,395,449,458,467,476,485,494,539,548,557,566,575,584,593,629,638,647,656,665,674,683,692,719,728,737,746,755,764,773,782,791,809,818,827,836,845,854,863,872,881,890,908,917,926,935,944,953,962,971,980");
		zhiXuanHezhiMap.put(18, "099,189,198,279,288,297,369,378,387,396,459,468,477,486,495,549,558,567,576,585,594,639,648,657,666,675,684,693,729,738,747,756,765,774,783,792,819,828,837,846,855,864,873,882,891,909,918,927,936,945,954,963,972,981,990");
		zhiXuanHezhiMap.put(19, "199,289,298,379,388,397,469,478,487,496,559,568,577,586,595,649,658,667,676,685,694,739,748,757,766,775,784,793,829,838,847,856,865,874,883,892,919,928,937,946,955,964,973,982,991");
		zhiXuanHezhiMap.put(20, "299,389,398,479,488,497,569,578,587,596,659,668,677,686,695,749,758,767,776,785,794,839,848,857,866,875,884,893,929,938,947,956,965,974,983,992");
		zhiXuanHezhiMap.put(21, "399,489,498,579,588,597,669,678,687,696,759,768,777,786,795,849,858,867,876,885,894,939,948,957,966,975,984,993");
		zhiXuanHezhiMap.put(22, "499,589,598,679,688,697,769,778,787,796,859,868,877,886,895,949,958,967,976,985,994");
		zhiXuanHezhiMap.put(23, "599,689,698,779,788,797,869,878,887,896,959,968,977,986,995");
		zhiXuanHezhiMap.put(24, "699,789,798,879,888,897,969,978,987,996");
		zhiXuanHezhiMap.put(25, "799,889,898,979,988,997");
		zhiXuanHezhiMap.put(26, "899,989,998");
		zhiXuanHezhiMap.put(27, "999");

		zuXuan3HezhiMap.put(1,  "001");
		zuXuan3HezhiMap.put(2,  "002,011");
		zuXuan3HezhiMap.put(3,  "003");
		zuXuan3HezhiMap.put(4,  "004,022,112");
		zuXuan3HezhiMap.put(5,  "005,113,122");
		zuXuan3HezhiMap.put(6,  "006,033,114");
		zuXuan3HezhiMap.put(7,  "007,115,133,223");
		zuXuan3HezhiMap.put(8,  "008,044,116,224,233");
		zuXuan3HezhiMap.put(9,  "009,117,144,225");
		zuXuan3HezhiMap.put(10, "055,118,226,244,334");
		zuXuan3HezhiMap.put(11, "119,155,227,335,344");
		zuXuan3HezhiMap.put(12, "066,228,255,336");
		zuXuan3HezhiMap.put(13, "166,229,337,355,445");
		zuXuan3HezhiMap.put(14, "077,266,338,446,455");
		zuXuan3HezhiMap.put(15, "177,339,366,447");
		zuXuan3HezhiMap.put(16, "088,277,448,466,556");
		zuXuan3HezhiMap.put(17, "188,377,449,557,566");
		zuXuan3HezhiMap.put(18, "099,288,477,558");
		zuXuan3HezhiMap.put(19, "199,388,559,577,667");
		zuXuan3HezhiMap.put(20, "299,488,668,677");
		zuXuan3HezhiMap.put(21, "399,588,669");
		zuXuan3HezhiMap.put(22, "499,688,778");
		zuXuan3HezhiMap.put(23, "599,779,788");
		zuXuan3HezhiMap.put(24, "699");
		zuXuan3HezhiMap.put(25, "799,889");
		zuXuan3HezhiMap.put(26, "899");

		zuXuan6HezhiMap.put(3,  "012");
		zuXuan6HezhiMap.put(4,  "013");
		zuXuan6HezhiMap.put(5,  "014,023");
		zuXuan6HezhiMap.put(6,  "015,024,123");
		zuXuan6HezhiMap.put(7,  "016,025,034,124");
		zuXuan6HezhiMap.put(8,  "017,026,035,125,134");
		zuXuan6HezhiMap.put(9,  "018,027,036,045,126,135,234");
		zuXuan6HezhiMap.put(10, "019,028,037,046,127,136,145,235");
		zuXuan6HezhiMap.put(11, "029,038,047,056,128,137,146,236,245");
		zuXuan6HezhiMap.put(12, "039,048,057,129,138,147,156,237,246,345");
		zuXuan6HezhiMap.put(13, "049,058,067,139,148,157,238,247,256,346");
		zuXuan6HezhiMap.put(14, "059,068,149,158,167,239,248,257,347,356");
		zuXuan6HezhiMap.put(15, "069,078,159,168,249,258,267,348,357,456");
		zuXuan6HezhiMap.put(16, "079,169,178,259,268,349,358,367,457");
		zuXuan6HezhiMap.put(17, "089,179,269,278,359,368,458,467");
		zuXuan6HezhiMap.put(18, "189,279,369,378,459,468,567");
		zuXuan6HezhiMap.put(19, "289,379,469,478,568");
		zuXuan6HezhiMap.put(20, "389,479,569,578");
		zuXuan6HezhiMap.put(21, "489,579,678");
		zuXuan6HezhiMap.put(22, "589,679");
		zuXuan6HezhiMap.put(23, "689");
		zuXuan6HezhiMap.put(24, "789");
		
		zhiXuanStakesMap = new java.util.HashMap<Integer, Integer>();
		for (Integer tmpHezhiKey : zhiXuanHezhiMap.keySet())
		{
			String tmpNumberMapItem = zhiXuanHezhiMap.get(tmpHezhiKey);
			if (tmpNumberMapItem != null)
			{
				zhiXuanStakesMap.put(tmpHezhiKey, tmpNumberMapItem.split(",").length);
			}
		}

		zuXuan3StakesMap = new java.util.HashMap<Integer, Integer>();
		for (Integer tmpHezhiKey : zuXuan3HezhiMap.keySet())
		{
			String tmpNumberMapItem = zuXuan3HezhiMap.get(tmpHezhiKey);
			if (tmpNumberMapItem != null)
			{
				zuXuan3StakesMap.put(tmpHezhiKey, tmpNumberMapItem.split(",").length);
			}
		}

		zuXuan6StakesMap = new java.util.HashMap<Integer, Integer>();
		for (Integer tmpHezhiKey : zuXuan6HezhiMap.keySet())
		{
			String tmpNumberMapItem = zuXuan6HezhiMap.get(tmpHezhiKey);
			if (tmpNumberMapItem != null)
			{
				zuXuan6StakesMap.put(tmpHezhiKey, tmpNumberMapItem.split(",").length);
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

	public static int getZuXuan3StakeCount(String zuXuan3He)
	{
		try
		{
			return getZuXuan3StakeCount(Integer.parseInt(zuXuan3He));
		}
		catch (NumberFormatException exception)
		{
			return 0;
		}
	}
	
	public static int getZuXuan3StakeCount(int zuXuan3He)
	{
		Integer tmpZuXuan3He = Integer.valueOf(zuXuan3He);
		if (!zuXuan3StakesMap.containsKey(tmpZuXuan3He))
		{
			return 0;
		}
		else
		{
			return zuXuan3StakesMap.get(tmpZuXuan3He);
		}
	}

	public static int getZuXuan6StakeCount(String zuXuan6He)
	{
		try
		{
			return getZuXuan6StakeCount(Integer.parseInt(zuXuan6He));
		}
		catch (NumberFormatException exception)
		{
			return 0;
		}
	}
	
	public static int getZuXuan6StakeCount(int zuXuan6He)
	{
		Integer tmpZuXuan6He = Integer.valueOf(zuXuan6He);
		if (!zuXuan6StakesMap.containsKey(tmpZuXuan6He))
		{
			return 0;
		}
		else
		{
			return zuXuan6StakesMap.get(tmpZuXuan6He);
		}
	}
	
	public static void main(String[] args)
	{
		for (int i = 0; i <= 27; i++)
		{
			System.out.println("直选: " + i + " - " + getZhiXuanStakeCount(i));
		}
		
		System.out.println("直选:   " + " - " + getZhiXuanStakeCount(""));
		System.out.println("直选: qq" + " - " + getZhiXuanStakeCount("qq"));		

		System.out.println("\r\n");
		for (int i = 1; i <= 26; i++)
		{
			System.out.println("组选3: " + i + " - " + getZuXuan6StakeCount(i));
		}

		System.out.println("\r\n");
		for (int i = 3; i <= 24; i++)
		{
			System.out.println("组选6: " + i + " - " + getZuXuan6StakeCount(i));
		}
	}
}
package com.xhcms.lottery.commons.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xhcms.lottery.commons.data.BetMatch;

/**
 * 胆码的组合
 * @author lei.li@davcai.com
 */
public class CombinSeed {

	private static final Logger log = LoggerFactory.getLogger(CombinSeed.class);
	
	public static String arrayToString(int[] arr) {
		String code = "";
		for(int j=0; j<arr.length; j++) {
			code = code + "." + arr[j];
		}
		return code.substring(1);
	}
	
	/**
     * 字典法计算排列组合
     * @param cursor 参与组合的对象列表的游标数组
     * @param n 组合的下标 选择赛事场次数
     * @param m 组合的上标 串关数
     */
    public static void combinate(int[] cursor, int n, int m) {
        int i = 0, MAX = m - 1, max = n - MAX;
        int[] c = new int[m];
        while (c[0] < max) {
            for (i++; i < m; i++) {
                c[i] = c[i - 1] + 1;
            }

            while (c[MAX] < n) {
                c[MAX]++;
            }

            i = MAX - 1;
            while (i >= 0) {
                if (c[i] + 1 < c[i + 1]) {
                    c[i]++;
                    break;
                }
                i--;
            }
        }
    }
	
	/**
	 * 按ID分组
	 * @param matchs
	 * @return
	 * {0=[0, 1, 2], 1=[3, 4], 2=[5, 6]}
	 * {0=[0, 1, 2], 1=[3, 4], 2=[5, 6], 3=[7,8]}
	 */
	public static Map<Long, List<Long>> groupById(List<BetMatch> matchs, List<Long> seedIndex) {
		Map<Long, List<Long>> m = new HashMap<Long, List<Long>>();
    	if(null != matchs && matchs.size() > 0) {
    		long tmid = 0;
    		boolean isSeed = false;
    		List<Long> matchIds = new ArrayList<Long>();
    		for(int index = 0; index < matchs.size(); index++) {
    			Long matchId = matchs.get(index).getMatchId();
    			if(tmid == 0) {
    				tmid = matchId;
    				matchIds.add(Long.valueOf(index));
    			} else if(matchId == tmid) {
    				matchIds.add(Long.valueOf(index));
    			} else {
    				m.put(tmid - 1, matchIds);
    				tmid = matchId;
    				matchIds = new ArrayList<Long>();
    				matchIds.add(Long.valueOf(index));
    			}
    			if(index == matchs.size() - 1 && matchIds != null && matchIds.size() > 0) {
    				m.put(tmid - 1, matchIds);
    			}
    			
    			//计算胆的数量
    			if(index == 0 || tmid == matchId) {
    				isSeed = matchs.get(index).isSeed();
    				if(isSeed) {
    					seedIndex.add(tmid - 1);
    				}
    			}
    		}
    	}
    	log.debug("group by id:{}", m);
    	return m;
	}
	
	public static void combinId(Map<Long, List<Long>> m) {
		int[] a = new int[m.keySet().size()];
		Set<Long> set = m.keySet();
		Iterator<Long> iter = set.iterator();
		int i = 0;
		while(iter.hasNext()) {
			if(i < a.length) {
				a[i] = Integer.valueOf(iter.next()+"");
				i++;
			}
		}
		combinate(a, m.keySet().size(), 2);
	}
	
	
	public static int[][] calcSeedIndex(Map<Long, List<Long>> m, List<Long> seedIndex) {
		if(m.size() > 0) {
    		Set<Long> mks = m.keySet();
    		int[][] seeds = new int[mks.size()][];
    		int i = 0;
    		Iterator<Long> iter = mks.iterator();
    		while(iter.hasNext()) {
    			Long key = iter.next();
    			if(seedIndex.contains(Long.valueOf(i))) {
    				List<Long> list = m.get(key);
    				seeds[i] = new int[list.size()];
    				for(int j=0; j<list.size(); j++) {
    					seeds[i][j] = list.get(j).intValue();
    				}
    			}
    			i++;
    		}
    		return seeds;
    	}
		return null;
	}
	
	public static void main(String[] args) {
		List<Long> seedIndex = new ArrayList<Long>();
		Map<Long, List<Long>> m = groupById(gtMatchs(), seedIndex);
		int[][] seeds = calcSeedIndex(m, seedIndex);
		int[] cursor = new int[]{0,1};
		cursor = new int[]{1,3,5};
		boolean result = checkSeedSelect(cursor, seeds);
		
		String optIndex = Arrays.toString(cursor);
		String ret = String.format("选项:%s,胆的数量:%s,胆选校验结果:%s", optIndex, seedIndex.size(), result);
		
		System.out.println(ret);
	}
	
	static List<BetMatch> gtMatchs() {
		List<BetMatch> matchs = new ArrayList<BetMatch>();
		//第一场比赛
		BetMatch bm1 = new BetMatch();
		bm1.setSeed(true);
		bm1.setMatchId(201502097038L);
		BetMatch bm2 = new BetMatch();
		bm2.setMatchId(201502097038L);
		bm2.setSeed(true);
		BetMatch bm3 = new BetMatch();
		bm3.setMatchId(201502097038L);
		bm3.setSeed(true);
		
		//第二场比赛
		BetMatch bm4 = new BetMatch();
		bm4.setMatchId(201502097039L);
		bm4.setSeed(true);
		BetMatch bm5 = new BetMatch();
		bm5.setMatchId(201502097039L);
		bm5.setSeed(true);
		
		//第三场比赛
		BetMatch bm6 = new BetMatch();
		bm6.setMatchId(201502097040L);
//		bm6.setSeed(true);
		BetMatch bm7 = new BetMatch();
		bm7.setMatchId(201502097040L);
//		bm7.setSeed(true);
		
		//第四场比赛
		BetMatch bm8 = new BetMatch();
		bm8.setMatchId(201502097041L);
		BetMatch bm9 = new BetMatch();
		bm9.setMatchId(201502097041L);
		
		matchs.add(bm1);
		matchs.add(bm2);
		matchs.add(bm3);
		
		matchs.add(bm4);
		matchs.add(bm5);
		
		matchs.add(bm6);
		matchs.add(bm7);
		
		matchs.add(bm8);
		matchs.add(bm9);
		
		return matchs;
	}	
	
	public static boolean checkSeedSelect(int[] cursor, int[][] seeds) {
//		cursor = new int[]{0,1};
//		seeds[0][0] = 0;
//		seeds[0][1] = 1;
//		seeds[0][2] = 2;
//		
//		seeds[1][0] = 3;
//		seeds[1][1] = 4;
//		
//		seeds[2][0] = 5;
//		seeds[3][1] = 6;
		
		boolean result = true;
		int seedSize = seeds.length;
		for(int i=0; i<seedSize; i++) {
			if(null != seeds[i] && seeds[i].length > 1) {//同一场比赛选了多个玩法的设胆情况
				boolean a = false;
				for(int j=0; j<seeds[i].length; j++) {
					for(int m=0; m<cursor.length; m++) {
						if(cursor[m] == seeds[i][j]) {
							a = true;//有一个胆在选项里面就行
							break;
						}
					}
				}
				if(!a){
					result = false;
					break;
				}
			} else if(null != seeds[i] && seeds[i].length == 1) {//同一场比赛只选了一个玩法设胆情况
				for(int m=0; m<cursor.length; m++) {
					if(cursor[m] != seeds[i][0]) {
						result = false;
						break;
					}
				}
			}
		}
		return result;
	}
	
}

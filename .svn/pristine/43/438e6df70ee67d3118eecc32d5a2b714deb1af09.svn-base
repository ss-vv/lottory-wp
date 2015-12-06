package com.xhcms.lottery.commons.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xhcms.lottery.commons.data.BetMatch;

/**
 * 计算投注选项的组合结果，排除无效的投注组合方式
 * @author lei.li@davcai.com
 */
public class DigitalCombination {

	private static final Logger log = LoggerFactory.getLogger(DigitalCombination.class);
	
	private List<String> excludeIndex = new ArrayList<String>();
	private List<String> includeIndex = new ArrayList<String>();
	private int type;
	public DigitalCombination() {
		
	}
	public DigitalCombination(int type) {
		this.type = type;
	}

	public void combine(List<Integer> list) {
		if (null != list && list.size() > 0) {
			int[] arr = new int[list.size()];
			for (int i = 0; i < list.size(); i++) {
				arr[i] = list.get(i);
			}
			combine(arr);
		}
	}

	public void combine(int[] a) {
		if (null == a || a.length == 0)
			return;
		int[] b = new int[a.length];
		getCombination(a, 0, b, 0);
	}

	private void getCombination(int[] a, int begin, int b[], int index) {
		if (index >= a.length) return;
		for (int i = begin; i < a.length; i++) {
			b[index] = a[i];
			saveResult(b, index);
			getCombination(a, i + 1, b, index + 1);
		}
	}

	private void saveResult(int[] b, int index) {
		String code = "";
		for (int i = 0; i < index + 1; i++) {
			if(i != 0) {
				code = code + "." + b[i];
			} else {
				code = code + b[i];
			}
		}
		boolean isContain = false;
		for(int elt : b) {
			if(String.valueOf(elt).equals(code)) {
				isContain = true;
				break;
			}
		}
		if(code.length() > 1 && !isContain) {
			if(type == 0) {
				excludeIndex.add(code);
			} else if(type == 1) {
				includeIndex.add(code);
			}
		}
	}

	public List<String> getExcludeIndex() {
		return excludeIndex;
	}
	
	public List<String> getIncludeIndex() {
		return includeIndex;
	}

	public void setType(int type) {
		this.type = type;
	}
	public static DigitalCombination calcElementIndex(List<BetMatch> matchs) {
		List<String> excludeIndex = new ArrayList<String>();
		DigitalCombination digitalCom = new DigitalCombination();
    	if(null != matchs && matchs.size() > 0) {
    		Set<Long> matchIdSet = new HashSet<Long>();
    		List<Long> matchIdList = new ArrayList<Long>();
    		
            for(int i=0;i<matchs.size();i++){
            	if(matchs.get(i).isSeed()){
            		digitalCom.includeIndex.add(""+i);
            	}
            }
    		for(int index = 0; index < matchs.size(); index++) {
    			Long matchId = matchs.get(index).getMatchId();
    			matchIdList.add(matchId);
    			matchIdSet.add(matchId);
    		}
    		digitalCom.setType(0);
    		Iterator<Long> iter = matchIdSet.iterator();
    		while(iter.hasNext()) {
    			List<Integer> matchIdPostion = new ArrayList<Integer>();
    			long matchId = iter.next();
    			for(int i = 0; i<matchIdList.size(); i++) {
    				if(matchId == matchIdList.get(i)) {
    					matchIdPostion.add(i);
    				}
    			}
    			if(matchIdPostion.size() > 1) {
    				digitalCom.combine(matchIdPostion);
    				excludeIndex.addAll(digitalCom.getExcludeIndex());
    			}
    		}
    	}
    	return digitalCom;
	}
	
	public static boolean mustContain(List<String> includeIndex, int[] arr) {
		boolean result = true;
		if(null != includeIndex && includeIndex.size() > 0) {
			String val = arrayToString(arr);
			for(String elt : includeIndex) {
				boolean startWith = val.startsWith(elt + ".");
				boolean endsWith = val.endsWith("." + elt);
				boolean middel = val.indexOf("." + elt + ".") >= 0 ? true : false;
				if(startWith || endsWith || middel) {
					result = false;
					break;
				}
			}
		}
		return result;
	}
	
	/**
	 * 包含即排除
	 * @param excludeIndex
	 * @param arr
	 * @return
	 */
	public static boolean isContainOrStartWith(List<String> excludeIndex, int[] arr) {
		boolean result = false;
		if(null != excludeIndex && excludeIndex.size() > 0) {
			String val = arrayToString(arr);
			if(excludeIndex.contains(val)) {
				result = true;
			} else {
				for(String elt : excludeIndex) {
					if(val.indexOf(elt+".") >= 0 || val.indexOf("."+elt) >= 0) {
						result = true;
						break;
					}
				}
			}
		}
		return result;
	}
	
	public static boolean isSkipCombin(List<BetMatch> matchs, 
			List<String> excludeIndex,
			List<String> includeIndex, int[] arr, Set<Integer> seedIndexs) {
		String convert = arrayToString(arr);
		log.debug("select match index={}", convert);
		
		boolean result = false;
		result = isContainOrStartWith(excludeIndex, arr);
		if(!result && null != includeIndex && includeIndex.size() > 0) {
			result = mustContain(includeIndex, arr);
		}
		if(!result && null != seedIndexs && seedIndexs.size() > 0) {
			List<Long> seedIndex = new ArrayList<Long>();
			Map<Long, List<Long>> m = CombinSeed.groupById(matchs, seedIndex);
			int[][] seeds = CombinSeed.calcSeedIndex(m, seedIndex);
			result = !CombinSeed.checkSeedSelect(arr, seeds);
		}
		return result;
	}
	
	public static String arrayToString(int[] arr) {
		String code = "";
		for(int j=0; j<arr.length; j++) {
			code = code + "." + arr[j];
		}
		return code.substring(1);
	}
	
	public static void main(String[] args) {
		testCombin();
	}
	
	static void testCombin() {
		DigitalCombination comb = new DigitalCombination();
		long beginTime = System.currentTimeMillis();
		int[] a = { 1, 2, 3, 10};
		comb.combine(a);
		long endTime = System.currentTimeMillis();
		System.out.println("run time=" + (endTime - beginTime));
		System.out.println(comb.excludeIndex);
	}
	
}

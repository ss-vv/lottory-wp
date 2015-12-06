package com.xhcms.lottery.commons.data.ctfb;

import java.util.LinkedList;

import org.apache.commons.lang.StringUtils;

import com.laicai.util.Visitor;
import com.xhcms.lottery.commons.persist.service.BetException;

/**
 * 任9玩法，遍历组合的访问器。只计算注数。
 * 
 * @author yangbo
 */
class R9Visitor implements Visitor {

	protected int[] selCountArray;
	protected int[] selIndexArray;
	protected int totalNotes;
	protected int[] danIndex;
	
	/**
	 * @param selCountArray 每场比赛的选项个数，比如：0-未选,3-选了3个选项。 
	 * @param dan 胆，做胆的赛事下标，从0开始。
	 * @throws BetException 如果胆的个数超过8
	 */
	public R9Visitor(int[] selCountArray, int[] selIndexArray, String dan) throws BetException{
		this.selCountArray = selCountArray;
		this.selIndexArray = selIndexArray;
		splitDan(dan);
	}
	
	private void splitDan(String dan) throws BetException {
		if (StringUtils.isNotBlank(dan)){
			String[] dp = dan.split(",");
			if (dp.length>8){
				throw new BetException("R9 dan count must less than 9, now is: " + dp.length);
			}
			LinkedList<Integer> darray = new LinkedList<Integer>();
			for (String d : dp){
				darray.add(Integer.parseInt(d));
			}
			danIndex = new int[darray.size()];
			for (int i=0; i<danIndex.length; i++){
				danIndex[i] = darray.get(i);
			}
		}
	}

	@Override
	public void visit(int[] combination) {
		if (!containsAllDan(combination)){
			return;
		}
//		printArray("包含所有胆的赛事：", selIndexArray, combination);
		long t = 1;
		for (int idx : combination) {
			t *= selCountArray[idx];
		}
		totalNotes += t;
		visitR9(combination);
	}

	/**
	 * 子类可以重载本方法，实现其他功能，如生成所有的9场比赛选择。
	 * @param combination
	 */
	protected void visitR9(int[] combination) {
	}

	@SuppressWarnings("unused")
	private void printArray(String msg, int[] selIndexArray2, int[] combination) {
		System.out.println(msg);
		System.out.print("[");
		for (int i=0; i<combination.length; i++){
			System.out.print(selIndexArray2[combination[i]]+",");
		}
		System.out.print("]\n");
	}

	/**
	 * @param combination 被选中赛事的下标数组，长度是固定的9
	 * @return true 如果 combination 包含所有的 dan；否则返回false
	 */
	private boolean containsAllDan(int[] combination) {
		if (danIndex != null && danIndex.length > 0){
			for (int danSub : danIndex){
				boolean find = false;
				for (int mIndex : combination){
					if (selIndexArray[mIndex] == danSub){
						find = true;
						break;
					}
				}
				if (!find) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * 总注数。
	 */
	@Override
	public int getTotal() {
		return totalNotes;
	}
}
package com.xhcms.lottery.commons.utils.internal;

import java.util.LinkedList;
import java.util.List;

import com.laicai.util.Visitor;

/**
 * 生成江西11选5的投注数字组合串。
 *
 * @author yangbo
 */
public class JX11CombinVistor implements Visitor {
	private int total;
	
	// 数字选项组合列表
	private LinkedList<String> digitsOptions = new LinkedList<String>();
	private String[] digits;
	
	/**
	 * @param digits 用来产生组合的数字串。
	 */
	public JX11CombinVistor(String[] digits) {
		this.digits = digits;
	}

	/**
	 * @combination 一个组合，从 0 开始的数字。
	 */
	@Override
	public void visit(int[] combination) {
		StringBuilder builder = new StringBuilder();
		for (int n : combination) {
			if (builder.length()>0){
				builder.append(",");
			}
			builder.append(digits[n]);
		}
		digitsOptions.add(builder.toString());
		total++;
	}

	@Override
	public int getTotal() {
		return total;
	}

	public List<String> getDigitsOptions() {
		return digitsOptions;
	}

}

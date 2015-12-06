package com.laicai.util;

/**
 * 访问组合生成器产生一个组合。
 *
 * @author yangbo
 */
public interface Visitor {
	/**
	 * @param combination 元素是被选中元素的下标，从0开始。
	 */
	void visit(int[] combination);
	
	/**
	 * @return 总的组合数量。
	 */
	int getTotal();
}

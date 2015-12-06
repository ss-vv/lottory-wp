package com.xhcms.lottery.commons.data.ctfb;

import java.util.LinkedList;
import java.util.List;

import com.xhcms.lottery.commons.persist.service.BetException;

/**
 * 展开任九所有可能赛事选择的访问器。
 * 
 * @author yangbo
 */
public class ExpandingR9Visitor extends R9Visitor {

	private List<String> expandedCodes;
	private String[] origBetGroup;	// 原始投注内容，如：310,310,-,10,310,-,10,310,-,10,310,-,-,-
	
	private final String NONE = "-";
	private final String SEP  = ",";
	private final int MAX_MACHTES = 14;
	
	
	public ExpandingR9Visitor(int[] selCountArray, int[] selIndexArray, 
			String dan, String[] betGroup) throws BetException {
		super(selCountArray, selIndexArray, dan);
		this.origBetGroup = betGroup;
		this.expandedCodes = new LinkedList<String>();
	}

	@Override
	protected void visitR9(int[] combination) {
		String[] betGroup = new String[MAX_MACHTES];
		for (int i=0; i<betGroup.length; i++){
			betGroup[i] = NONE;
		}
		for (int c : combination) {
			int idxOfMatch = selIndexArray[c];
			betGroup[idxOfMatch] = origBetGroup[idxOfMatch];
		}
		StringBuilder b = new StringBuilder(betGroup[0]);
		for (int i=1; i<betGroup.length; i++){
			b.append(SEP).append(betGroup[i]);
		}
		expandedCodes.add(b.toString());
	}

	public List<String> getExpandedCodes(){
		return this.expandedCodes;
	}
}

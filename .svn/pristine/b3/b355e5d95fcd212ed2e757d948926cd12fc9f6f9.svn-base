package com.xhcms.lottery.commons.data.ctfb;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.laicai.util.Combination;
import com.xhcms.lottery.commons.data.BetOption;
import com.xhcms.lottery.commons.data.ChooseType;
import com.xhcms.lottery.commons.persist.service.BetException;
import com.xhcms.lottery.lang.AppCode;
import com.xhcms.lottery.lang.Constants;
import com.xhcms.lottery.lang.PlayType;

/**
 * 传统足球投注选项。
 * 
 * @author Yang Bo
 */
public class CTBetOption extends BetOption {

	private static final String SEP = ",";
	
	public CTBetOption(PlayType playType, ChooseType chooseType, String option, String dan)
			throws BetException {
		super(playType, chooseType, option, dan);
	}

	@Override
	protected void computeNotes() throws BetException {
		switch(playType){
		case CTZC_14:
			computeNotesForAllSelectedType(14, "[310]{1,3}");
			break;
		case CTZC_R9:
			computeR9Notes();
			break;
		case CTZC_BQ:
			computeNotesForAllSelectedType(6*2, "[310]{1,3}");
			break;
		case CTZC_JQ:
			computeNotesForAllSelectedType(4*2, "[0123]{1,4}");
			break;
		default:
			throw new BetException("Unsupported playType: " + playType);
		}
	}

	/**
	 * 计算任9玩法注数
	 * @throws BetException 有效选择小于9.
	 */
	private void computeR9Notes() throws BetException {
		int[][] selCountAndIndexArray = createSelCountAndIndexArray();
		int[] selCountArray = selCountAndIndexArray[0];
		int[] selIndexArray = selCountAndIndexArray[1];
		int validCount = r9ValidCount();
		R9Visitor visitor = new R9Visitor(selCountArray, selIndexArray, this.dan);
		Combination.generateWithAlgorithmL(validCount , 9, visitor);
		this.notes = visitor.getTotal();
	}

	public int[][] createSelCountAndIndexArray() throws BetException {
		String[] items = this.option.split(SEP);
		int validCount = r9ValidCount(items);
		if (validCount < 9){
			throw new BetException("R9 selected matches less than 9: " + this.option, AppCode.INVALID_BET_CODE);
		}
		int[] selCountArray = new int[validCount];
		int[] selIndexArray = new int[validCount];
		for (int i=0,j=0; i<items.length; i++){
			String it = items[i].trim();
			if (is310(it)) {
				selIndexArray[j] = i;				// 有效场次下标
				selCountArray[j++] = it.length();	// 选项数
			}
		}
		return new int[][]{selCountArray, selIndexArray};
	}

	/** 有效选择的个数 */
	private int r9ValidCount(String[] items) throws BetException {
		if (items.length != 14){
			throw new BetException("R9 option items count is not 14. " + ReflectionToStringBuilder.toString(items), AppCode.INVALID_BET_CODE);
		}
		int selCount = 0;
		for (int i=0; i<items.length; i++) {
			String it = items[i].trim();
			if (is310(it)){
				selCount++;
			}
		}
		return selCount;
	}

	public int r9ValidCount() throws BetException{
		return r9ValidCount(this.option.split(SEP));
	}
	
	private Pattern pt310 = Pattern.compile("[310]{1,3}");
	private boolean is310(String s){
		if (pt310==null){
			pt310 = Pattern.compile("[310]{1,3}");
		}
		Matcher m = pt310.matcher(s);
		return m.matches();
	}
	
	/**
	 * 计算14场胜负的选项注数。
	 * 选项格式如：310,3,1,0,3,1,0,3,1,0,3,1,0,30
	 * @throws BetException 如果格式错误
	 */
	private void computeNotesForAllSelectedType(int matchCount, String validOption) throws BetException {
		String[] items = this.option.split(SEP);
		if (items.length != matchCount){
			throw new BetException("select count must be "+matchCount+": " + this.option, AppCode.INVALID_BET_CODE);
		}
		Pattern p = Pattern.compile(validOption);
		notes = 1;
		for (String select : items) {
			String selectTrimed = select.trim();
			Matcher m = p.matcher(selectTrimed);
			if (!m.matches()){
				throw new BetException("code invalid: " + this.option, AppCode.INVALID_BET_CODE);
			}
			notes *= selectTrimed.length();
		}
	}

	/**
	 * 传统足彩，4种玩法都是一样的单复式规则。
	 * 单式：包含分号“;”
	 * 复式：选项单元中的选项串字符个数大于1。
	 */
	@Override
	protected void computeBetType() {
		this.betType = Constants.ZM_BETTYPE_CTZC_DS;
		if (this.option.contains(";")){
			return;
		}
		String[] units = this.option.split(SEP);
		for (String u : units) {
			if (u.trim().length()>1){
				this.betType = Constants.ZM_BETTYPE_CTZC_FS;
				return;
			}
		}
	}
	
}

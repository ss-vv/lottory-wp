package com.xhcms.lottery.commons.data.jx11;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.laicai.util.ComputerUtils;
import com.xhcms.lottery.commons.data.BetOption;
import com.xhcms.lottery.commons.data.ChooseType;
import com.xhcms.lottery.commons.persist.service.BetException;
import com.xhcms.lottery.lang.AppCode;
import com.xhcms.lottery.lang.PlayType;

/**
 * 江西11选5，投注选项。可用来计算注数。<br/>
 * 投注选项的格式同《中民接口协议》中的定义。
 * 倍数应该放在包含 JX11BetOption 的投注方案中。
 * 
 * @author Yang Bo
 */
public class JX11BetOption extends BetOption {

	/**
	 * 构造并解析投注选项。
	 * @param playType
	 * @param option
	 */
	public JX11BetOption(PlayType playType, ChooseType chooseType, String option)
		throws BetException {
		super(playType, chooseType, option);
	}

	/**
	 * <li>任选8不支持手选复式和胆托。所以被拆分为单式，并合并到一张票中了，用分号隔开。
	 * <li>直选不支持胆拖。
	 * @throws BetException 格式错误
	 */
	protected void computeNotes() throws BetException {
		int digits = option.split(COMMA).length;
		int choose = getChooseCountFromPlayType();
		switch(chooseType) {
			case MACHINE:	// 单式
				if (playType == PlayType.JX11_R1){	// 前一只能一票一注，不能有分号
					notes = 1;
				} else {
					// 支持一票多注
					notes = option.split(SEMICOLON).length;
				}
				// 校验数字个数是否合法
				if(playType == PlayType.JX11_R1){
					assertNoSemiColon(option);
					assertNoComma(option);
				}else{
					for (String oneNote : option.split(SEMICOLON)){
						if (playType.isJX11AnyChoose()){
							digits = oneNote.split(COMMA).length;
							assertDigitsEquals(choose, digits);
						} else if(playType == PlayType.JX11_D2 || playType == PlayType.JX11_D3){
							digits = oneNote.split(BAR_REGEXP).length;
							assertDigitsEquals(choose, digits);
						}
					}
				}
				break;
			case MANUAL:	// 手选, 即复式
				if (playType == PlayType.JX11_R1) {
					// 前一，一数字就是一注
					notes = option.split(COMMA).length;
				}else if (playType == PlayType.JX11_R8){
					// R8 是被拆分为单式，然后又合并到一起的
					notes = option.split(SEMICOLON).length;
				}else if (playType.isJX11AnyChoose()) {
					assertDigitsLargerThen(digits, choose-1);
					// 任选N，是组合数
					notes = (int)ComputerUtils.combination(digits, choose);
				}else if (playType == PlayType.JX11_D2 || playType == PlayType.JX11_D3) {
					List<Digits> digitsPerIndex = parseDigitsPerIndex();
					notes = countNotesOfDirectChoose(digitsPerIndex);
				}else if (playType == PlayType.JX11_G2 || playType == PlayType.JX11_G3) {
					notes = (int)ComputerUtils.combination(digits, choose);
				}else{
					throw new IllegalStateException("Should not reach here, playType: " + 
							playType + ", chooseType: " + chooseType);
				}
				break;
			case DAN: // 胆拖
				if (playType == PlayType.JX11_R8){
					// R8 是被拆分为单式，然后又合并到一起的
					notes = option.split(SEMICOLON).length;
				}else{
					String danRemoved = removeDan(option);
					String dan = getDan(option);
					int digitsRemoveDan = danRemoved.split(COMMA).length;
					int danDigits = dan.split(COMMA).length;
					assertDigitsLargerThen(digitsRemoveDan+danDigits, choose);
					notes = (int)ComputerUtils.combination(digitsRemoveDan, choose-danDigits);
				}
				break;
		}
	}
	
	private void assertDigitsEquals(int expect, int actual) throws BetException {
		if (actual != expect) {
			throw new BetException("Bet option should has "+expect+" digits, but actual is: " + actual);
		}
	}

	private void assertNoSemiColon(String optionStr) throws BetException {
		if (optionStr.contains(SEMICOLON)){
			throw new BetException("Bet option should not contains ';'. option: ("+
					option + ")" + ". playType: " + playType);
		}
	}

	private void assertNoComma(String oneNote) throws BetException {
		if (oneNote.contains(COMMA)){
			throw new BetException("Bet option should not contains ','. option: ("+
					option + ")" + ". playType: " + playType);
		}
	}

	/**
	 *  从 PlayType 计算出“任选N”的N值。
	 */
	private int getChooseCountFromPlayType(){
		int choose = 0;
		if (playType == PlayType.JX11_R1){
			return 1;
		} else if (playType.isJX11AnyChoose()){
			choose = playType.toInt() - 10;
			if (choose <2 || choose > 8) {
				throw new IllegalStateException("JX11 any choose has wrong playType: " + playType);
			}
		} else if (playType == PlayType.JX11_G2 || playType == PlayType.JX11_G3){
			choose = playType.toInt() - 19;
			if (choose <2 || choose > 3) {
				throw new IllegalStateException("JX11 G2,G3 has wrong playType: " + playType);
			}
		} else if (playType == PlayType.JX11_D2 || playType == PlayType.JX11_D3){
			choose = playType.toInt() - 17;
			if (choose <2 || choose > 3) {
				throw new IllegalStateException("JX11 D2,D3 has wrong playType: " + playType);
			}
		}
		return choose;
	}

	private void assertDigitsLargerThen(int digits, int count) throws BetException {
		if (digits<=count) {
			throw new BetException("Digits count should larger then " + count);
		}
	}

	/**
	 *  去掉胆，比如："(01,06)02,03,04,05" 变为 "02,03,04,05"。
	 */
	private String removeDan(String optionStr) {
		return option.replaceAll("\\(.+\\)", "");
	}

	private String getDan(String optionStr) {
		Pattern pt = Pattern.compile("\\((.+)\\).*");
		Matcher matcher = pt.matcher(optionStr);
		if (matcher.matches()){
			return matcher.group(1);
		}
		return StringUtils.EMPTY;
	}

	/**
	 *  解析前2、前3直选的选项，例如：1,2,3|2,3,4
	 */
	private List<Digits> parseDigitsPerIndex() {
		String[] digitsStrings = option.split(BAR_REGEXP);
		Digits[] digits = new Digits[digitsStrings.length];
		int i = 0;
		for (String digitsStr : digitsStrings) {
			digits[i++] = new Digits(digitsStr.split(COMMA));
		}
		return Arrays.asList(digits);
	}
	
	/**
	 * 计算直选的注数。
	 * 展开所有可能投注选择方式，去掉重复，去掉位数不够的注，得到的总注数就是直选的注数。
	 * @throws BetException 如果有一个部分是和其他部分全部重叠了，如：01,02|03,04|01,02
	 * 但下面的选择是允许的，因为直选要考虑位置：01,02|03,04|02,03。
	 * 抛出异常的格式为：new BetException("All Direct Choose are duplcated!", AppCode.INVALID_BET_CODE);
	 */
	private int countNotesOfDirectChoose(List<Digits> digitsList) throws BetException {
		List<int[]> digits = new LinkedList<int[]>();
		for (Digits ds : digitsList) {
			digits.add(ds.getNumbers());
		}
		List<int[]> combinations = com.laicai.util.Combination.listCombination(digits);
		Iterator<int[]> it = combinations.iterator();
		while(it.hasNext()){
			int[] nums = it.next();
			if (!isValidChooseForDirect(nums)){
				it.remove();
			}
		}
		int notes = combinations.size();
		if (notes == 0){
			throw new BetException("All Direct Choose are duplcated!", AppCode.INVALID_BET_CODE);
		}
		return notes;
	}
	
	/**
	 * 有重复的数字就是无效的。
	 * @param nums
	 * @return true 无重复数字，是有效的；false 有重复数字，是无效的。
	 */
	private boolean isValidChooseForDirect(int[] nums) {
		for(int pos=0; pos<nums.length-1; pos++){
			int cur = nums[pos];
			for(int i=pos+1; i<nums.length; i++){
				if (cur == nums[i]){
					return false;
				}
			}
		}
		return true;
	}

	@Override
	protected void computeBetType() {
		// TODO: 重构计算 BetType 的逻辑到这里。
	}
}

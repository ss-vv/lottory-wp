package com.xhcms.lottery.commons.utils.internal;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.xhcms.lottery.commons.data.BetOption;
import com.xhcms.lottery.commons.exception.JXRuntimeException;
import com.xhcms.lottery.commons.persist.service.BetException;
import com.xhcms.lottery.lang.PlayType;

/**
 * 出票内容格式验证器。确保投注内容格式满足出票平台约束。
 */
public class FC3DBetCodeValidator extends BetCodeValidator{
	
	private static java.util.Map<PlayType, Pattern> playTypePatterns = new java.util.HashMap<PlayType, Pattern>();
	private String contentNotRepeat = "^(?![0-9]*?([0-9])[0-9]*?\\1)[0-9]{1,10}$";
	
	static {
		
		String pattern_FC3D_ZXDS = "([0-9],){2}[0-9]|(([0-9],){2}[0-9];)+([0-9],){2}[0-9]";
		
		//已经检测重复号问题
		String pattern_FC3D_ZXFS = "([0-9]{1,10},){1,9}[0-9]{1,10}";
		
		//已经检测重复号问题
		String pattern_FC3D_Z3FS = "([0-9],){1,9}[0-9]";//最少2个号
		
		//已经检测重复号问题
		String pattern_FC3D_Z6FS = "([0-9],){2,9}[0-9]";//最少3个号
		
		String pattern_FC3D_ZX_DS = "([0-9],){2}[0-9]";		//组选单式0~9，最少3个号
		
		String pattern_FC3D_ZXHZ = "[0-9]|1[0-9]|2[0-7]";	//选项（0-27）
		
		String pattern_FC3D_Z3HZ = "[1-9]|1[0-9]|2[0-6]";	//选项（1~26）
		
		String pattern_FC3D_Z6HZ = "[3-9]|1[0-9]|2[0-4]";	//选项（3~24）
		String pattern_FC3D_DXBH = "([0-9],){1,9}[0-9]";	//选项（0-9），最少2个号
		
		playTypePatterns.put(PlayType.FC3D_ZXDS, Pattern.compile(pattern_FC3D_ZXDS)); 	// 福彩3D直选单式
		playTypePatterns.put(PlayType.FC3D_ZXFS, Pattern.compile(pattern_FC3D_ZXFS)); 	// 福彩3D直选复式
		playTypePatterns.put(PlayType.FC3D_ZXHZ, Pattern.compile(pattern_FC3D_ZXHZ)); 	// 福彩3D直选和值
		playTypePatterns.put(PlayType.FC3D_ZX_DS, Pattern.compile(pattern_FC3D_ZX_DS)); //福彩3D组选单式（组三和组六单式通用）
		playTypePatterns.put(PlayType.FC3D_Z3FS, Pattern.compile(pattern_FC3D_Z3FS)); 	// 福彩3D组三复式
		playTypePatterns.put(PlayType.FC3D_Z3HZ, Pattern.compile(pattern_FC3D_Z3HZ)); 	// 福彩3D组三和值
		playTypePatterns.put(PlayType.FC3D_Z6FS, Pattern.compile(pattern_FC3D_Z6FS)); 	// 福彩3D组六复式
		playTypePatterns.put(PlayType.FC3D_Z6HZ, Pattern.compile(pattern_FC3D_Z6HZ)); 	// 福彩3D组六和值
		playTypePatterns.put(PlayType.FC3D_DXBH, Pattern.compile(pattern_FC3D_DXBH)); 	// 福彩3D单选包号
	}
	
	/**
	 * 对投注的号码进行格式验证。
	 * 
	 * @param playType, 投注玩法
	 * @param betCode, 投注号码
	 * @throws BetException, 投注格式不符合要求。
	 * @return
	 */
	@Override
	public void valid(String betCode, PlayType playType) throws BetException {
		if(StringUtils.isBlank(betCode) || null == playType) {
			throw new JXRuntimeException("check betCode and playType have null value.");
		}
		boolean checkResult =  false;
		
		Pattern pattern = null;
		pattern = Pattern.compile(contentNotRepeat);
		if(playType == PlayType.FC3D_Z3FS || 
				playType == PlayType.FC3D_Z6FS || 
				playType == PlayType.FC3D_DXBH) {
			String replaceRes = betCode.replace(BetOption.COMMA, "");
			Matcher res = pattern.matcher(replaceRes);
			if(res.matches()) {
				checkResult = true;
			}
		} else if(playType == PlayType.FC3D_ZXFS) {
			String[] bets = betCode.split(BetOption.COMMA);
			int validLen = 0;
			for(String bet : bets) {
				validLen += bet.length();
				boolean rs = pattern.matcher(bet).matches();
				if(!rs) {
					checkResult = false;
					break;
				} else {
					checkResult = true;
				}
			}
			if(validLen <= 3) {
				throw new JXRuntimeException("直选复式投注格式错误:betCode="+betCode+";长度错误");
			}
		} else {
			checkResult = true;
		}
		if (checkResult) {
			pattern = playTypePatterns.get(playType);
			Matcher matcher = pattern.matcher(betCode);
			checkResult = matcher.matches();
			
			switch (playType) {
			case FC3D_ZXDS:
			case FC3D_ZXFS:
			case FC3D_ZXHZ:
			case FC3D_ZX_DS:
			case FC3D_Z3FS:
			case FC3D_Z3HZ:
			case FC3D_Z6FS:
			case FC3D_Z6HZ:
			case FC3D_DXBH:
				break;
			}
		}
		if (!checkResult) {
			throw new BetException("Invalid bet content: " + betCode + " || pattern: " + pattern.pattern());
		}
	}
}

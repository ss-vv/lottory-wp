package com.unison.lottery.wap.persist.service.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Test;

import com.xhcms.lottery.commons.utils.internal.BetCodeValidator;
import com.xhcms.lottery.commons.utils.internal.FC3DBetCodeValidator;
import com.xhcms.lottery.lang.PlayType;

/**
 * @desc 福彩3D投注格式测试
 * @author lei.li@lai310.com
 * @createTime 2014-9-3
 * @version 1.0
 */
public class FC3DBetFormatTest {
	String patternNotRepeat = "^(?![0-9]*?([0-9])[0-9]*?\\1)[0-9]{1,10}$";
	
	@Test
	public void testNotRepat() {
//		CharSequence betCode = "1234567890";
		CharSequence betCode = "26";
		Pattern pattern = Pattern.compile(patternNotRepeat);
		Matcher matcher = pattern.matcher(betCode);
		boolean checkResult = matcher.matches();
		Assert.assertTrue(checkResult);
	}
	
	@Test
	public void testRegExp() {
		BetCodeValidator codeValidator = new FC3DBetCodeValidator();
		codeValidator.valid("0,9", PlayType.FC3D_Z3FS);
	}
}
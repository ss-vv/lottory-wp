package com.unison.lottery.weibo.common.service.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.Assert;
import org.junit.Test;

public class WeiboParameterParserTest {

	/**
	 * 解析中奖喜报方案信息
	 */
	@Test
	public void winningNewTest() {
		String matchPattern = "((\\#中奖喜报)\\((\\d+)\\)#)";
		String content = "~~~~~#中奖喜报(60496)#微博内容~~~~~#中奖喜报(79227)#微博内容";
		Pattern pattern = Pattern.compile(matchPattern);
		Matcher matcher = pattern.matcher(content);
		if (matcher.find()) {
			System.out.println("解析出中奖喜报开头内容：" + matcher.group(1));
			System.out.println("解析出中奖喜报方案ID：" + matcher.group(3));
		} else {
			Assert.fail("无法解析中奖喜报微博内容，喜报格式无效.");
		}
	}
}

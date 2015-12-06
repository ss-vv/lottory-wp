package com.unison.lottery.wap.persist.service.impl;

import java.util.Arrays;
import java.util.List;

import com.unison.test.DbUnitTestBase;

/**
 * 投注服务测试基础类。负责提供 DBUnit 功能。
 * 
 * @author Yang Bo
 */
public class BetServiceTestBase extends DbUnitTestBase {

	@Override
	public List<String> getDataSetNames() {
		String[] tables = new String[]{
				"/dbunit/lt_bet_scheme.xml",
				"/dbunit/lt_bet_partner.xml",
				"/dbunit/lt_hf_bet_content.xml",
				"/dbunit/lt_ticket.xml",
				"/dbunit/lt_play.xml",
				"/dbunit/lt_lottery.xml",
				"/dbunit/lt_issueinfo.xml",
				"/dbunit/lt_user.xml"
		};
		return Arrays.asList(tables);
	}

	@Override
	public List<String> getDataSetNames2() {
		String[] tables = new String[]{
				"/dbunit/lt_account.xml",
		};
		return Arrays.asList(tables);
	}

}

package com.xhcms.lottery.commons.persist.service;

import java.util.Arrays;
import java.util.List;

import com.unison.test.DbUnitTestBase;

public class DbUnitFollowServiceTestBase extends DbUnitTestBase {

	@Override
	public List<String> getDataSetNames() {
		return Arrays.asList(new String[]{
				"/dbunit/lt_user.xml",
				});
	}

	@Override
	public List<String> getDataSetNames2() {
		return null;
	}

}

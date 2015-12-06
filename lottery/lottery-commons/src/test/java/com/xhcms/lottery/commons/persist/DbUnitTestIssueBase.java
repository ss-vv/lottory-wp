package com.xhcms.lottery.commons.persist;

import java.util.Arrays;
import java.util.List;

import com.unison.test.DbUnitTestBase;

public class DbUnitTestIssueBase extends DbUnitTestBase {

	@Override
	public List<String> getDataSetNames() {
		return Arrays.asList(new String[]{
				"/dbunit/lt_issueinfo.xml",
				});
	}

	@Override
	public List<String> getDataSetNames2() {
		return null;
	}

}

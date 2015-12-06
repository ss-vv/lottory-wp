package com.unison.lottery.api.getverifycode.bo;

import java.util.Arrays;
import java.util.List;

import com.unison.test.DbUnitTestBase;

public class GetVerifyCodeBOTestBase extends DbUnitTestBase{

	@Override
	public List<String> getDataSetNames() {
		String[] tables = new String[]{
				"/dbunit/lt_user.xml",
				"/dbunit/lt_return_status.xml",
				"/dbunit/lt_user_validid.xml"
		};
		return Arrays.asList(tables);
	}

	@Override
	public List<String> getDataSetNames2() {
		String[] tables = new String[]{
				"/dbunit/lt_account.xml"
		};
		return Arrays.asList(tables);
	}

}

package com.unison.lottery.api.protocol.status;

import java.util.Arrays;
import java.util.List;

import com.unison.test.DbUnitTestBase;

public class StatusRepositoryTestBase extends DbUnitTestBase{

	@Override
	public List<String> getDataSetNames() {
		String[] tables = new String[]{
				"/dbunit/lt_return_status.xml"
		};
		return Arrays.asList(tables);
	}

	@Override
	public List<String> getDataSetNames2() {
		// TODO Auto-generated method stub
		return null;
	}

}

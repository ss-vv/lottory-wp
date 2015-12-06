package com.xhcms.lottery.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import com.xhcms.commons.lang.Paging;
import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.persist.entity.BetSchemePO;

public class PagingUtilsTest {
	
	@Test
	public void testMakeCopyAndSetPaging() {
		List<BetSchemePO> schemes = new LinkedList<BetSchemePO>();
		BetSchemePO scheme = new BetSchemePO();
		scheme.setSponsor("yangbo");
		scheme.setId(0L);
		scheme.setSponsorId(0L);
		schemes.add(scheme);
		Paging paging = new Paging();
		PagingUtils.makeCopyAndSetPaging(schemes, paging, BetScheme.class);
		assertNotNull(paging.getResults());
		BetScheme data = (BetScheme) paging.getResults().get(0);
		assertEquals(data.getSponsor(), "yangbo");
		System.out.println(paging);
	}

	@Test
	public void testMakeDataCopy() {
	}

}

package com.xhcms.lottery.commons.tools;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Test;

public class PagerTest {

	@Test
	public void testEdges(){
		// <1>,2,3,4
		Pager pager = Pager.paging(1, 4, 2);
		LinkedList<Integer> pages = pager.getPages();
		assertEquals(pages.size(), 4);
		assertEquals(pages.get(0), Integer.valueOf(1));
		assertEquals(pages.get(1), Integer.valueOf(2));
		assertEquals(pages.get(2), Integer.valueOf(3));
		assertEquals(pages.get(3), Integer.valueOf(4));
		
		// 1,2,3,<4>
		pager = Pager.paging(4, 4, 2);
		pages = pager.getPages();
		assertEquals(pages.size(), 4);
		assertEquals(pages.get(0), Integer.valueOf(1));
		assertEquals(pages.get(1), Integer.valueOf(2));
		assertEquals(pages.get(2), Integer.valueOf(3));
		assertEquals(pages.get(3), Integer.valueOf(4));
		
		// 1,2,<3>
		pager = Pager.paging(3, 3, 2);
		pages = pager.getPages();
		assertEquals(pages.size(), 3);
		assertEquals(pages.get(0), Integer.valueOf(1));
		assertEquals(pages.get(1), Integer.valueOf(2));
		assertEquals(pages.get(2), Integer.valueOf(3));
		
		// 1,<2>,3
		pager = Pager.paging(2, 3, 2);
		pages = pager.getPages();
		assertEquals(pages.size(), 3);
		assertEquals(pages.get(0), Integer.valueOf(1));
		assertEquals(pages.get(1), Integer.valueOf(2));
		assertEquals(pages.get(2), Integer.valueOf(3));
		
		// <1>
		pager = Pager.paging(1, 1, 2);
		pages = pager.getPages();
		assertEquals(pages.size(), 1);
		assertEquals(pages.get(0), Integer.valueOf(1));
	}
	
	@Test
	public void testErrorParams(){
		Pager pager = Pager.paging(1, 0, 2);
		LinkedList<Integer> pages = pager.getPages();
		assertEquals(pages.size(), 0);
	}
	
	@Test
	public void testPaging() {
		// <1>,2,3,...,10
		Pager pager = Pager.paging(1, 10, 2);
		LinkedList<Integer> pages = pager.getPages();
		assertEquals(pages.size(), 5);
		assertEquals(pages.get(0), Integer.valueOf(1));
		assertEquals(pages.get(1), Integer.valueOf(2));
		assertEquals(pages.get(2), Integer.valueOf(3));
		assertEquals(pages.get(3), Integer.valueOf(-1));
		assertEquals(pages.get(4), Integer.valueOf(10));

		// 1,<2>,3,4,...,10
		pager = Pager.paging(2, 10, 2);
		pages = pager.getPages();
		assertEquals(pages.size(), 6);
		assertEquals(pages.get(0), Integer.valueOf(1));
		assertEquals(pages.get(1), Integer.valueOf(2));
		assertEquals(pages.get(2), Integer.valueOf(3));
		assertEquals(pages.get(3), Integer.valueOf(4));
		assertEquals(pages.get(4), Integer.valueOf(-1));
		assertEquals(pages.get(5), Integer.valueOf(10));
		
		// 1,2,<3>,4,5,...,10
		pager = Pager.paging(3, 10, 2);
		pages = pager.getPages();
		assertEquals(pages.size(), 7);
		assertEquals(pages.get(0), Integer.valueOf(1));
		assertEquals(pages.get(1), Integer.valueOf(2));
		assertEquals(pages.get(2), Integer.valueOf(3));
		assertEquals(pages.get(3), Integer.valueOf(4));
		assertEquals(pages.get(4), Integer.valueOf(5));
		assertEquals(pages.get(5), Integer.valueOf(-1));
		assertEquals(pages.get(6), Integer.valueOf(10));
		
		// 1,2,3,<4>,5,6,...,10
		pager = Pager.paging(4, 10, 2);
		pages = pager.getPages();
		assertEquals(pages.size(), 8);
		assertEquals(pages.get(0), Integer.valueOf(1));
		assertEquals(pages.get(1), Integer.valueOf(2));
		assertEquals(pages.get(2), Integer.valueOf(3));
		assertEquals(pages.get(3), Integer.valueOf(4));
		assertEquals(pages.get(4), Integer.valueOf(5));
		assertEquals(pages.get(5), Integer.valueOf(6));
		assertEquals(pages.get(6), Integer.valueOf(-1));
		assertEquals(pages.get(7), Integer.valueOf(10));
		
		// 1,...,3,4,<5>,6,7,...,10
		pager = Pager.paging(5, 10, 2);
		pages = pager.getPages();
		assertEquals(pages.size(), 9);
		assertEquals(pages.get(0), Integer.valueOf(1));
		assertEquals(pages.get(1), Integer.valueOf(-1));
		assertEquals(pages.get(2), Integer.valueOf(3));
		assertEquals(pages.get(3), Integer.valueOf(4));
		assertEquals(pages.get(4), Integer.valueOf(5));
		assertEquals(pages.get(5), Integer.valueOf(6));
		assertEquals(pages.get(6), Integer.valueOf(7));
		assertEquals(pages.get(7), Integer.valueOf(-1));
		assertEquals(pages.get(8), Integer.valueOf(10));

		// 1,...,4,5,<6>,7,8,...,10
		pager = Pager.paging(6, 10, 2);
		pages = pager.getPages();
		assertEquals(pages.size(), 9);
		assertEquals(pages.get(0), Integer.valueOf(1));
		assertEquals(pages.get(1), Integer.valueOf(-1));
		assertEquals(pages.get(2), Integer.valueOf(4));
		assertEquals(pages.get(3), Integer.valueOf(5));
		assertEquals(pages.get(4), Integer.valueOf(6));
		assertEquals(pages.get(5), Integer.valueOf(7));
		assertEquals(pages.get(6), Integer.valueOf(8));
		assertEquals(pages.get(7), Integer.valueOf(-1));
		assertEquals(pages.get(8), Integer.valueOf(10));

		// 1,...,5,6,<7>,8,9,10
		pager = Pager.paging(7, 10, 2);
		pages = pager.getPages();
		assertEquals(pages.size(), 8);
		assertEquals(pages.get(0), Integer.valueOf(1));
		assertEquals(pages.get(1), Integer.valueOf(-1));
		assertEquals(pages.get(2), Integer.valueOf(5));
		assertEquals(pages.get(3), Integer.valueOf(6));
		assertEquals(pages.get(4), Integer.valueOf(7));
		assertEquals(pages.get(5), Integer.valueOf(8));
		assertEquals(pages.get(6), Integer.valueOf(9));
		assertEquals(pages.get(7), Integer.valueOf(10));
		
		// 1,...,6,7,<8>,9,10
		pager = Pager.paging(8, 10, 2);
		pages = pager.getPages();
		assertEquals(pages.size(), 7);
		assertEquals(pages.get(0), Integer.valueOf(1));
		assertEquals(pages.get(1), Integer.valueOf(-1));
		assertEquals(pages.get(2), Integer.valueOf(6));
		assertEquals(pages.get(3), Integer.valueOf(7));
		assertEquals(pages.get(4), Integer.valueOf(8));
		assertEquals(pages.get(5), Integer.valueOf(9));
		assertEquals(pages.get(6), Integer.valueOf(10));
		
		// 1,...,7,8,<9>,10
		pager = Pager.paging(9, 10, 2);
		pages = pager.getPages();
		assertEquals(pages.size(), 6);
		assertEquals(pages.get(0), Integer.valueOf(1));
		assertEquals(pages.get(1), Integer.valueOf(-1));
		assertEquals(pages.get(2), Integer.valueOf(7));
		assertEquals(pages.get(3), Integer.valueOf(8));
		assertEquals(pages.get(4), Integer.valueOf(9));
		assertEquals(pages.get(5), Integer.valueOf(10));
		
		// 1,...,8,9,<10>
		pager = Pager.paging(10, 10, 2);
		pages = pager.getPages();
		assertEquals(pages.size(), 5);
		assertEquals(pages.get(0), Integer.valueOf(1));
		assertEquals(pages.get(1), Integer.valueOf(-1));
		assertEquals(pages.get(2), Integer.valueOf(8));
		assertEquals(pages.get(3), Integer.valueOf(9));
		assertEquals(pages.get(4), Integer.valueOf(10));
	}

}

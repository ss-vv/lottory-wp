package com.xhcms.lottery.commons.persist.service;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.xhcms.commons.lang.Paging;
import com.xhcms.lottery.commons.data.FollowWinList;
import com.xhcms.lottery.commons.data.ShowWinList;
import com.xhcms.lottery.commons.persist.dao.BetSchemeDao;
import com.xhcms.lottery.commons.persist.dao.FollowWinListDao;
import com.xhcms.lottery.commons.persist.dao.ShowWinListDao;
import com.xhcms.lottery.commons.persist.entity.BetSchemePO;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test-follow-spring.xml")
public class WinListServiceTest {

	@Autowired
	private WinListService winListService;
	@Autowired
	private ShowWinListDao showWinListDao;
	@Autowired
	private FollowWinListDao followWinListDao;
	@Autowired
	private BetSchemeDao betSchemeDao;
	
	@Test
	public void testShowWinList() {
		Paging paging = new Paging();
		paging.setPageNo(1);
		paging.setMaxResults(20);
		try{
		winListService.findShowWinList(paging, "JCZQ");
		}catch(Exception e){
			e.printStackTrace();
		}
		
		List<ShowWinList> showList = (List<ShowWinList>)paging.getResults();
		for (ShowWinList showWinList:showList) {
			System.out.println(showWinList.getId());
		}
		
	}
	
	@Test
	public void testFollowWinList() {
		Paging paging = new Paging();
		paging.setPageNo(1);
		paging.setMaxResults(20);
		winListService.findFollowWinList(paging, "JCZQ");
		
		List<FollowWinList> followList = (List<FollowWinList>)paging.getResults();
		for (FollowWinList followWinList:followList) {
			System.out.println(followWinList.getId());
		}
	}
	
	@Test
	@Transactional
	public void testCountWinList() {
		List l = new ArrayList();
		l.add(86395L);
		List<BetSchemePO> pl = betSchemeDao.find(l);
		for(BetSchemePO betSchemePO:pl) {
			winListService.countWinList(betSchemePO);
		}
	}

}

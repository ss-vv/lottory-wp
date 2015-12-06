package com.xhcms.lottery.commons.persist.service;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xhcms.commons.lang.Paging;
import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.data.Play;
import com.xhcms.lottery.commons.data.UserScore;
import com.xhcms.lottery.commons.persist.dao.ShowSchemeDao;
import com.xhcms.lottery.commons.persist.entity.BetSchemeWithAssocPO;
import com.xhcms.lottery.lang.Constants;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test-follow-spring.xml")
public class ShowSchemeServiceTest {

	@Autowired
	private ShowSchemeService showSchemeService;
	@Autowired
	private WinListService winListService;
	@Autowired
	private ShowSchemeDao showSchemeDao;
	
	
/*	@Test
	public void testFindShowSchemes(){
		Paging paging = new Paging();
		ShowSchemeQueryCondition conc = new ShowSchemeQueryCondition();
		conc.setLotteryId("JCZQ");
		conc.setRecommend(false);
		//conc.setOrderColumn("followedRatio");
		showSchemeService.findOnSaleShowingSchemes(paging, conc);
		System.out.println(paging.getResults().size());
		

	}*/
	
	@Test
	public void testFindShowSchemesByCondition(){
		Paging paging = new Paging();
		ShowFollowQueryCondition con = new ShowFollowQueryCondition();
		con.setCurDate("now");
		con.setStatus(-1);
		con.setUserId(616);
		showSchemeService.findShowSchemesByCondition(paging, con);
		for(BetScheme betSchemes:(List<BetScheme>)paging.getResults()){
			System.out.println(betSchemes.getId());
		}

	}
	
	@Test
	public void testFindFollowSchemesByCondition(){
		Paging paging = new Paging();
		ShowFollowQueryCondition con = new ShowFollowQueryCondition();
		con.setCurDate("");
		con.setStatus(12);
		con.setUserId(616);
		showSchemeService.findFollowSchemesByCondition(paging,con);
		for(BetScheme betSchemes:(List<BetScheme>)paging.getResults()){
			System.out.println(betSchemes.getId());
		}
	}


}

package com.xhcms.lottery.dc.persist.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.xhcms.lottery.commons.data.IssueInfo;
import com.xhcms.lottery.commons.data.Ticket;
import com.xhcms.lottery.commons.persist.entity.IssueInfoPO;
import com.xhcms.lottery.dc.core.Persister;
import com.xhcms.lottery.dc.persist.persister.IssueinfoPersisterImpl;
import com.xhcms.lottery.dc.persist.service.TicketService;
import com.xhcms.lottery.lang.PlayType;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/spring-service.xml","/spring-db.xml"})
public class IssueinfoPersisterImplTest {

	@Autowired
	private Persister<IssueInfo> issueinfoPersisterImpl;
	
	@Transactional
	@Test
	@Rollback(value=true)
	public void testBatchSaveOrUpdate() {
		List<IssueInfo> issueinfos=new ArrayList<IssueInfo>();
		Date now=new Date();
		//未在hibernate托管下的vo
		IssueInfo issueinfo1=new IssueInfo();
		issueinfo1.setBonusCode("01 02 03 04 05");
		issueinfo1.setCloseTime(now);
		issueinfo1.setIssueNumber("12091003-test");
		issueinfo1.setLotteryId("JX11");
		issueinfo1.setStartTime(now);
		issueinfo1.setStatus(1);
		issueinfo1.setStopTime(now);
		issueinfo1.setPlayId(PlayType.UNKNOWN.getShortPlayStr());
		issueinfos.add(issueinfo1);
		
		//在hibernate托管下的vo
		IssueInfo issueinfo2=new IssueInfo();
		
		issueinfo2.setBonusCode("01 02 03 04 06");
		issueinfo2.setCloseTime(now);
		issueinfo2.setIssueNumber("12090501");
		issueinfo2.setLotteryId("JX11");
		issueinfo2.setStartTime(now);
		issueinfo2.setStatus(2);
		issueinfo2.setStopTime(now);
		issueinfo2.setPlayId(PlayType.UNKNOWN.getShortPlayStr());
		issueinfos.add(issueinfo2);
		
		issueinfoPersisterImpl.persist(issueinfos);
	}

}

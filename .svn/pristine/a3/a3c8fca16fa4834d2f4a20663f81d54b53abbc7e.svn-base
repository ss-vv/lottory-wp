package com.xhcms.lottery.dc.persist.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xhcms.lottery.commons.data.IssueInfo;
import com.xhcms.lottery.commons.data.Ticket;
import com.xhcms.lottery.commons.persist.entity.IssueInfoPO;
import com.xhcms.lottery.dc.core.Persister;
import com.xhcms.lottery.dc.persist.persister.IssueinfoPersisterImpl;
import com.xhcms.lottery.dc.persist.service.TicketService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/spring-service.xml","/spring-db.xml"})
public class UpdateLCStatusIssueinfoPersisterImplTest {

	@Autowired
	private Persister<IssueInfo> updateIssueinfoLCStatusPersisterImpl;
	
	@Test
	public void test() {
		
		
		List<IssueInfo> issueinfos=Collections.emptyList();
		updateIssueinfoLCStatusPersisterImpl.persist(issueinfos);
	}

}

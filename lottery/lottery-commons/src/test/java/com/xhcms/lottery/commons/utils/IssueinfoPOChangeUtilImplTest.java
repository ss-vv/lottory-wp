package com.xhcms.lottery.commons.utils;

import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xhcms.lottery.commons.data.IssueInfo;
import com.xhcms.lottery.commons.data.UpdateAndInsertList;
import com.xhcms.lottery.commons.persist.DbUnitTestIssueBase;
import com.xhcms.lottery.commons.persist.entity.IssueInfoPO;
import com.xhcms.lottery.commons.util.IssueinfoPOChangeUtil;
import com.xhcms.lottery.lang.Constants;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test-issue-spring.xml")
public class IssueinfoPOChangeUtilImplTest extends DbUnitTestIssueBase {
	
	@Autowired
	private IssueinfoPOChangeUtil issueinfoPOChangeUtilService;

	private List<IssueInfo> issueinfos=new ArrayList<IssueInfo>();
	
	@Before
	public void prepare(){
		// 应该进update
		IssueInfo vo1=new IssueInfo();
		vo1.setIssueNumber("12090501");
		vo1.setLotteryId(Constants.JX11);
		vo1.setBonusCode("01 02 03 04 06");
		issueinfos.add(vo1);
		
		IssueInfo vo2=new IssueInfo();
		vo2.setIssueNumber("12090508");
		vo2.setLotteryId(Constants.JX11);
		vo2.setBonusCode("01 02 03 04 08");
		issueinfos.add(vo2);
		
		IssueInfo vo3=new IssueInfo();
		vo3.setIssueNumber("12090502");
		vo3.setLotteryId(Constants.JX11);
		vo3.setBonusCode("01 02 03 04 05");
		issueinfos.add(vo3);
	}
	
	@Test
	public void whenIssueNotExistThenInsert(){
		UpdateAndInsertList updateAndInsertList = 
				issueinfoPOChangeUtilService.split2UpdateAndInsertList(issueinfos);
		List<IssueInfoPO> insertList = updateAndInsertList.getIssueinfoPOsShouldInsert();
		
		assertFalse( insertList.isEmpty() );
	}
	
	@Test
	public void whenIssueExistThenUpdate() {
		UpdateAndInsertList updateAndInsertList = 
				issueinfoPOChangeUtilService.split2UpdateAndInsertList(issueinfos);
		List<IssueInfoPO> updateList = updateAndInsertList.getIssueinfoPOsShouldUpdate();
		
		assertFalse(updateList.isEmpty());
	}
}

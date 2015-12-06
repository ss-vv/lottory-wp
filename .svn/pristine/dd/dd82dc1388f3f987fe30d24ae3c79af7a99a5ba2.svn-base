package com.xhcms.lottery.dc.task.ticket;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;





/**
 * 测试竞彩出票任务
 * @author Yang Bo
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/spring-task.xml","/spring-test.xml","/spring-db.xml","/spring-service.xml","/spring-activemq.xml"})
public class BuyJCTicketTaskTest {

	@Autowired
	private BuyJCTicketTask task;
	
	@Test
	public void testExecute() {
		try {
			task.execute();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
}

package com.unison.lottery.api.lotteryInfo.bo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.unison.lottery.api.model.User;
import com.unison.lottery.api.protocol.response.model.BonusResultResponse;
import com.unison.lottery.api.protocol.status.ClientStatusCode;
import com.unison.lottery.api.protocol.status.ReturnStatus;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "/test-lotteryInfo-service-spring.xml")
public class LotteryInfoBOTest {

	@Autowired
	private LotteryInfoBO lotteryInfoBO;
	
	@Test
	public void queryLotteryInfo() throws Exception {
		User user = new User();
		user.setId("1676");
		
		BonusResultResponse bonusResultResponse = lotteryInfoBO.queryLotteryInfo(user);

		assertTrue(null != bonusResultResponse);
		ReturnStatus returnStatus = bonusResultResponse.getReturnStatus();
		assertTrue(null != returnStatus);
		assertEquals(ClientStatusCode.LotteryInfo.SUCC,
				returnStatus.getStatusCodeForClient());

	}
}
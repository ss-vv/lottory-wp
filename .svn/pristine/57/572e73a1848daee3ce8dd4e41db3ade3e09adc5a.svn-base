package com.davcai.lottery.platform.client.util;

import static org.junit.Assert.*;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import com.davcai.lottery.platform.client.anruizhiying.constants.AnRuiZhiYingLotteryId;
import com.davcai.lottery.platform.client.anruizhiying.constants.AnRuiZhiYingWareId;
import com.davcai.lottery.platform.client.anruizhiying.constants.AnRuiZhiYingWareIssue;
import com.davcai.lottery.platform.client.anruizhiying.model.AnRuiZhiYingParams;
import com.davcai.lottery.platform.client.anruizhiying.util.DavCaiPlayIdToAnRuiZhiYingParamsUtil;
import com.xhcms.lottery.lang.Constants;

public class DavCaiPlayIdToAnRuiZhiYingParamsUtilTest {
	@Test
	public void testTranslate(){
			
		
		AnRuiZhiYingParams params=DavCaiPlayIdToAnRuiZhiYingParamsUtil.translate(Constants.PLAY_08_LC_2);
		assertTrue(StringUtils.equals(params.getLotteryId(), AnRuiZhiYingLotteryId.JL_SFC));
		assertTrue(StringUtils.equals(params.getWareId(), AnRuiZhiYingWareId.JL_SFC));
		assertTrue(StringUtils.equals(params.getWareIssue(), AnRuiZhiYingWareIssue.JC));
		
		params=DavCaiPlayIdToAnRuiZhiYingParamsUtil.translate(Constants.PLAY_08_LC_1);
		assertTrue(StringUtils.equals(params.getLotteryId(), AnRuiZhiYingLotteryId.JL_SFC));
		assertTrue(StringUtils.equals(params.getWareId(), AnRuiZhiYingWareId.JL_SFC));
		assertTrue(StringUtils.equals(params.getWareIssue(), AnRuiZhiYingWareIssue.JC));	
		
		params=DavCaiPlayIdToAnRuiZhiYingParamsUtil.translate(Constants.PLAY_01_BD_SPF);
		assertTrue(StringUtils.equals(params.getLotteryId(), AnRuiZhiYingLotteryId.BD_SPF));
		assertTrue(StringUtils.equals(params.getWareId(), AnRuiZhiYingWareId.AUTO));
		assertTrue(StringUtils.equals(params.getWareIssue(), AnRuiZhiYingWareIssue.UNDETERMINED));
		
		params=DavCaiPlayIdToAnRuiZhiYingParamsUtil.translate(Constants.PLAY_01_ZC_1);
		assertTrue(StringUtils.equals(params.getLotteryId(), AnRuiZhiYingLotteryId.JZ_RQSPF));
		assertTrue(StringUtils.equals(params.getWareId(), AnRuiZhiYingWareId.JZ_RQSPF));
		assertTrue(StringUtils.equals(params.getWareIssue(), AnRuiZhiYingWareIssue.JC));
		
		params=DavCaiPlayIdToAnRuiZhiYingParamsUtil.translate(Constants.PLAY_01_ZC_2);
		assertTrue(StringUtils.equals(params.getLotteryId(), AnRuiZhiYingLotteryId.JZ_RQSPF));
		assertTrue(StringUtils.equals(params.getWareId(), AnRuiZhiYingWareId.JZ_RQSPF));
		assertTrue(StringUtils.equals(params.getWareIssue(), AnRuiZhiYingWareIssue.JC));
		
		params=DavCaiPlayIdToAnRuiZhiYingParamsUtil.translate(Constants.PLAY_02_BD_JQS);
		assertTrue(StringUtils.equals(params.getLotteryId(), AnRuiZhiYingLotteryId.BD_JQS));
		assertTrue(StringUtils.equals(params.getWareId(), AnRuiZhiYingWareId.AUTO));
		assertTrue(StringUtils.equals(params.getWareIssue(), AnRuiZhiYingWareIssue.UNDETERMINED));
		
		params=DavCaiPlayIdToAnRuiZhiYingParamsUtil.translate(Constants.PLAY_02_ZC_1);
		assertTrue(StringUtils.equals(params.getLotteryId(), AnRuiZhiYingLotteryId.JZ_BF));
		assertTrue(StringUtils.equals(params.getWareId(), AnRuiZhiYingWareId.JZ_BF));
		assertTrue(StringUtils.equals(params.getWareIssue(), AnRuiZhiYingWareIssue.JC));
		
		params=DavCaiPlayIdToAnRuiZhiYingParamsUtil.translate(Constants.PLAY_02_ZC_2);
		assertTrue(StringUtils.equals(params.getLotteryId(), AnRuiZhiYingLotteryId.JZ_BF));
		assertTrue(StringUtils.equals(params.getWareId(), AnRuiZhiYingWareId.JZ_BF));
		assertTrue(StringUtils.equals(params.getWareIssue(), AnRuiZhiYingWareIssue.JC));
		
		params=DavCaiPlayIdToAnRuiZhiYingParamsUtil.translate(Constants.PLAY_03_BD_SXDS);
		assertTrue(StringUtils.equals(params.getLotteryId(), AnRuiZhiYingLotteryId.BD_SXDS));
		assertTrue(StringUtils.equals(params.getWareId(), AnRuiZhiYingWareId.AUTO));
		assertTrue(StringUtils.equals(params.getWareIssue(), AnRuiZhiYingWareIssue.UNDETERMINED));
		
		params=DavCaiPlayIdToAnRuiZhiYingParamsUtil.translate(Constants.PLAY_03_ZC_1);
		assertTrue(StringUtils.equals(params.getLotteryId(), AnRuiZhiYingLotteryId.JZ_JQS));
		assertTrue(StringUtils.equals(params.getWareId(), AnRuiZhiYingWareId.JZ_JQS));
		assertTrue(StringUtils.equals(params.getWareIssue(), AnRuiZhiYingWareIssue.JC));
		
		params=DavCaiPlayIdToAnRuiZhiYingParamsUtil.translate(Constants.PLAY_03_ZC_2);
		assertTrue(StringUtils.equals(params.getLotteryId(), AnRuiZhiYingLotteryId.JZ_JQS));
		assertTrue(StringUtils.equals(params.getWareId(), AnRuiZhiYingWareId.JZ_JQS));
		assertTrue(StringUtils.equals(params.getWareIssue(), AnRuiZhiYingWareIssue.JC));
		
		params=DavCaiPlayIdToAnRuiZhiYingParamsUtil.translate(Constants.PLAY_04_BD_BF);
		assertTrue(StringUtils.equals(params.getLotteryId(), AnRuiZhiYingLotteryId.BD_BF));
		assertTrue(StringUtils.equals(params.getWareId(), AnRuiZhiYingWareId.AUTO));
		assertTrue(StringUtils.equals(params.getWareIssue(), AnRuiZhiYingWareIssue.UNDETERMINED));
		
		params=DavCaiPlayIdToAnRuiZhiYingParamsUtil.translate(Constants.PLAY_04_ZC_1);
		assertTrue(StringUtils.equals(params.getLotteryId(), AnRuiZhiYingLotteryId.JZ_BQC));
		assertTrue(StringUtils.equals(params.getWareId(), AnRuiZhiYingWareId.JZ_BQC));
		assertTrue(StringUtils.equals(params.getWareIssue(), AnRuiZhiYingWareIssue.JC));
		
		params=DavCaiPlayIdToAnRuiZhiYingParamsUtil.translate(Constants.PLAY_04_ZC_2);
		assertTrue(StringUtils.equals(params.getLotteryId(), AnRuiZhiYingLotteryId.JZ_BQC));
		assertTrue(StringUtils.equals(params.getWareId(), AnRuiZhiYingWareId.JZ_BQC));
		assertTrue(StringUtils.equals(params.getWareIssue(), AnRuiZhiYingWareIssue.JC));
		
		params=DavCaiPlayIdToAnRuiZhiYingParamsUtil.translate(Constants.PLAY_05_BD_BQC);
		assertTrue(StringUtils.equals(params.getLotteryId(), AnRuiZhiYingLotteryId.BD_BQC));
		assertTrue(StringUtils.equals(params.getWareId(), AnRuiZhiYingWareId.AUTO));
		assertTrue(StringUtils.equals(params.getWareIssue(), AnRuiZhiYingWareIssue.UNDETERMINED));
		
		params=DavCaiPlayIdToAnRuiZhiYingParamsUtil.translate(Constants.PLAY_05_ZC_2);
		assertTrue(StringUtils.equals(params.getLotteryId(), AnRuiZhiYingLotteryId.JZ_HH));
		assertTrue(StringUtils.equals(params.getWareId(), AnRuiZhiYingWareId.JZ_HH));
		assertTrue(StringUtils.equals(params.getWareIssue(), AnRuiZhiYingWareIssue.JC));
		
		params=DavCaiPlayIdToAnRuiZhiYingParamsUtil.translate(Constants.PLAY_06_BD_SF);
		assertTrue(StringUtils.equals(params.getLotteryId(), AnRuiZhiYingLotteryId.BD_SF));
		assertTrue(StringUtils.equals(params.getWareId(), AnRuiZhiYingWareId.AUTO));
		assertTrue(StringUtils.equals(params.getWareIssue(), AnRuiZhiYingWareIssue.UNDETERMINED));
		
		params=DavCaiPlayIdToAnRuiZhiYingParamsUtil.translate(Constants.PLAY_06_LC_1);
		assertTrue(StringUtils.equals(params.getLotteryId(), AnRuiZhiYingLotteryId.JL_RFSF));
		assertTrue(StringUtils.equals(params.getWareId(), AnRuiZhiYingWareId.JL_RFSF));
		assertTrue(StringUtils.equals(params.getWareIssue(), AnRuiZhiYingWareIssue.JC));
		
		params=DavCaiPlayIdToAnRuiZhiYingParamsUtil.translate(Constants.PLAY_06_LC_2);
		assertTrue(StringUtils.equals(params.getLotteryId(), AnRuiZhiYingLotteryId.JL_RFSF));
		assertTrue(StringUtils.equals(params.getWareId(), AnRuiZhiYingWareId.JL_RFSF));
		assertTrue(StringUtils.equals(params.getWareIssue(), AnRuiZhiYingWareIssue.JC));
		
		params=DavCaiPlayIdToAnRuiZhiYingParamsUtil.translate(Constants.PLAY_07_LC_1);
		assertTrue(StringUtils.equals(params.getLotteryId(), AnRuiZhiYingLotteryId.JL_SF));
		assertTrue(StringUtils.equals(params.getWareId(), AnRuiZhiYingWareId.JL_SF));
		assertTrue(StringUtils.equals(params.getWareIssue(), AnRuiZhiYingWareIssue.JC));
		
		params=DavCaiPlayIdToAnRuiZhiYingParamsUtil.translate(Constants.PLAY_07_LC_2);
		assertTrue(StringUtils.equals(params.getLotteryId(), AnRuiZhiYingLotteryId.JL_SF));
		assertTrue(StringUtils.equals(params.getWareId(), AnRuiZhiYingWareId.JL_SF));
		assertTrue(StringUtils.equals(params.getWareIssue(), AnRuiZhiYingWareIssue.JC));
		
		params=DavCaiPlayIdToAnRuiZhiYingParamsUtil.translate(Constants.PLAY_09_LC_1);
		assertTrue(StringUtils.equals(params.getLotteryId(), AnRuiZhiYingLotteryId.JL_DXF));
		assertTrue(StringUtils.equals(params.getWareId(), AnRuiZhiYingWareId.JL_DXF));
		assertTrue(StringUtils.equals(params.getWareIssue(), AnRuiZhiYingWareIssue.JC));
		
		params=DavCaiPlayIdToAnRuiZhiYingParamsUtil.translate(Constants.PLAY_09_LC_2);
		assertTrue(StringUtils.equals(params.getLotteryId(), AnRuiZhiYingLotteryId.JL_DXF));
		assertTrue(StringUtils.equals(params.getWareId(), AnRuiZhiYingWareId.JL_DXF));
		assertTrue(StringUtils.equals(params.getWareIssue(), AnRuiZhiYingWareIssue.JC));
		
		params=DavCaiPlayIdToAnRuiZhiYingParamsUtil.translate(Constants.PLAY_10_LC_2);
		assertTrue(StringUtils.equals(params.getLotteryId(), AnRuiZhiYingLotteryId.JL_HH));
		assertTrue(StringUtils.equals(params.getWareId(), AnRuiZhiYingWareId.JL_HH));
		assertTrue(StringUtils.equals(params.getWareIssue(), AnRuiZhiYingWareIssue.JC));
		
		
	}

}

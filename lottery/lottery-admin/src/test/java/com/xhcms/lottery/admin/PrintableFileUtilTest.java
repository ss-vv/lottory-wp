package com.xhcms.lottery.admin;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.xhcms.lottery.admin.util.PrintableFileUtil;
import com.xhcms.lottery.commons.data.PrintableFile;
import com.xhcms.lottery.lang.LotteryPlatformId;

public class PrintableFileUtilTest {
	
	@Test
	public void testCreateFile(){
		PrintableFileUtil util=new PrintableFileUtil();
		util.setDataStoreDirPath("/data/weibo/images");
		util.setDownloadUrlPre("http://images-test.davcai.com/export");
		List<String> printBetContents = new ArrayList<String>();
		printBetContents.add("51111,竞彩足球胜平负,T,(51)(5001>3),单关,1,2");
		printBetContents.add("59999,竞彩足球胜平负,T,(59)(5002>51-3)(5003>56-3)(5004>52-102-0),3串1,1,2");
		Date now=new Date();
		List<Long> printableTicketIds = new ArrayList<Long>();
		printableTicketIds.add(994L);
		printableTicketIds.add(1723574L);
		String lotteryId = "JCZQ";
		String playId = "05_ZC_2";
		PrintableFile file = util.createFile(printBetContents, now, printableTicketIds, lotteryId, playId, LotteryPlatformId.CHANGCHUN_SHI_TI_DIAN);
		System.out.println("file="+file);
		assertTrue(null!=file);
	}

}

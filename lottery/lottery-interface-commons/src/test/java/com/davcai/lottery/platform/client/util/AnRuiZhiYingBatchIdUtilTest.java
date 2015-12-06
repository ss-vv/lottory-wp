package com.davcai.lottery.platform.client.util;

import static org.junit.Assert.*;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import com.davcai.lottery.platform.client.anruizhiying.util.AnRuiZhiYingBatchIdUtil;

public class AnRuiZhiYingBatchIdUtilTest {
	
	@Test
	public void testGetBatchId(){
		
		Date date=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String dateStr = sdf.format(date);
		System.out.println("dateStr="+dateStr);
		BigInteger batchIdMin=new BigInteger(dateStr+"000");
		BigInteger batchIdMax=new BigInteger(dateStr+"999");
		BigInteger batchId=null;
		for(int i=0;i<2000;i++){
			batchId=AnRuiZhiYingBatchIdUtil.getBatchId(date);
			System.out.println(batchId);
			assertTrue(batchId.compareTo(batchIdMin)>=0&&batchId.compareTo(batchIdMax)<=0);
		}
		
		
	}

}

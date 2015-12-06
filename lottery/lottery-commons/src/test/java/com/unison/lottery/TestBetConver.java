package com.unison.lottery;

import com.xhcms.lottery.commons.data.Ticket;
import com.xhcms.lottery.utils.Ticket2ShiTiDianCodeTool;

public class TestBetConver {

	public static void main(String[] args) {
		Ticket t=new Ticket();
		t.setPlayId("05_ZC_2");
		t.setPassTypeId("2@1");
		t.setCode("50010:JQS-500201:BQC");
		t.setMultiple(1);
		String str=Ticket2ShiTiDianCodeTool.convert(t);
		System.out.println(str);
		
		
	}

}

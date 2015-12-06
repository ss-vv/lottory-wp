package com.unison.lottery.api.framework.log;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.unison.lottery.api.framework.log.LogManager;

public class LogManagerTest {
	
	@Test
	public void testFilterSecurityProperty(){
		String logStr=" oldPassword=\"1\"  newPassword=\"test\"  value=\"aa\"";
		LogManager logManager=new LogManager();
		List<String> propertyList=new ArrayList<String>();
		propertyList.add("newPassword");
		propertyList.add("oldPassword");
		propertyList.add("password");
		logManager.setPropertiesShouldEncodeByMD5(propertyList);
		logStr=logManager.filterSecurityProperty(logStr);
		System.out.println("logStr="+logStr);
		
	}

}

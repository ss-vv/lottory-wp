package com.xhcms.lottery.utils;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Test;

import com.xhcms.lottery.commons.persist.entity.CTFBMatchPO;
import com.xhcms.lottery.commons.utils.CTFBMatchResultUtil;
import com.xhcms.lottery.lang.EntityStatus;

public class CTFBMatchResultUtilTest {
	
	private CTFBMatchResultUtil ctfbMatchResultUtil ;
	@Before
	public void setUp(){
		ctfbMatchResultUtil=new CTFBMatchResultUtil();
	}
	
	@Test
	public void testMakeMatchResultParameterIsNUll(){
		List<CTFBMatchPO> ctfbMatchs=null;
		String playId=null;
		List<String> result = ctfbMatchResultUtil.makeMatchResultList(ctfbMatchs, playId);
	    assertTrue(null==result);
	}
	
	
	@Test
	public void testMakeMatchResultParameterIsEmpty(){
		List<CTFBMatchPO> ctfbMatchs=new ArrayList<CTFBMatchPO>();
		String playId=null;
		List<String> result = ctfbMatchResultUtil.makeMatchResultList(ctfbMatchs, playId);
	    assertTrue(null==result);
	    
	    ctfbMatchs=null;
		playId="";
		result = ctfbMatchResultUtil.makeMatchResultList(ctfbMatchs, playId);
	    assertTrue(null==result);
	}
	
	
	@Test
	public void testMakeMatchResultList14CSF(){
		List<CTFBMatchPO> ctfbMatchs=new ArrayList<CTFBMatchPO>();
		CTFBMatchPO ctfbMatch1=new CTFBMatchPO();
		ctfbMatch1.setScore("2:0");
		CTFBMatchPO ctfbMatch2=new CTFBMatchPO();
		ctfbMatch2.setScore("0:0");
		CTFBMatchPO ctfbMatch3=new CTFBMatchPO();
		ctfbMatch3.setScore("0:1");
		CTFBMatchPO ctfbMatch4=new CTFBMatchPO();
		ctfbMatch4.setStatus(EntityStatus.MATCH_CANCEL);//设为取消状态
		ctfbMatch4.setScore("0:1");
		ctfbMatchs.add(ctfbMatch1);
		ctfbMatchs.add(ctfbMatch2);
		ctfbMatchs.add(ctfbMatch3);
		ctfbMatchs.add(ctfbMatch4);
		String playId="24_ZC_14";
		List<String> result = ctfbMatchResultUtil.makeMatchResultList(ctfbMatchs, playId);
	    assertTrue(null!=result&&result.size()==3);
	    assertTrue(StringUtils.equals(result.get(0), "3"));
	    assertTrue(StringUtils.equals(result.get(1), "1"));
	    assertTrue(StringUtils.equals(result.get(2), "0"));
	    assertTrue(StringUtils.equals(result.get(3), "*"));
	}
	
	@Test
	public void testMakeMatchResultParameterIsBlank(){
		CTFBMatchPO ctfbMatch=null;
		String playId=null;
		String result = ctfbMatchResultUtil.makeMatchResult(ctfbMatch, playId);
		assertTrue(null==result);
		
		ctfbMatch=null;
		playId="";
		result = ctfbMatchResultUtil.makeMatchResult(ctfbMatch, playId);
		assertTrue(null==result);
		
		ctfbMatch=new CTFBMatchPO();
		playId="24_ZC_14";
		result = ctfbMatchResultUtil.makeMatchResult(ctfbMatch, playId);
		assertTrue(null==result);
		
	}
	
	@Test
	public void testMakeMatchResult14CSF(){
		CTFBMatchPO ctfbMatch=new CTFBMatchPO();
		ctfbMatch.setScore("2:0");
		String playId="24_ZC_14";
		String result = ctfbMatchResultUtil.makeMatchResult(ctfbMatch, playId);
		assertTrue(StringUtils.equals(result, "3"));
		
		  
	
		ctfbMatch=new CTFBMatchPO();
		ctfbMatch.setScore("0:0");
		result = ctfbMatchResultUtil.makeMatchResult(ctfbMatch, playId);
		assertTrue(StringUtils.equals(result, "1"));
		
		
		ctfbMatch=new CTFBMatchPO();
		ctfbMatch.setScore("0:1");
		result = ctfbMatchResultUtil.makeMatchResult(ctfbMatch, playId);
		assertTrue(StringUtils.equals(result, "0"));
		
		
		ctfbMatch=new CTFBMatchPO();
		ctfbMatch.setStatus(EntityStatus.MATCH_CANCEL);//设为取消状态
		ctfbMatch.setScore("0:1");
		assertTrue(StringUtils.equals(result, "*"));
		
	}
	

}

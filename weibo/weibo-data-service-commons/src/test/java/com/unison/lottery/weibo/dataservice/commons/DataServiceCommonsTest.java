package com.unison.lottery.weibo.dataservice.commons;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.script.Bindings;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import sun.org.mozilla.javascript.internal.NativeArray;

import com.unison.lottery.weibo.dataservice.commons.constants.DataInterfaceName;
import com.unison.lottery.weibo.dataservice.commons.download.DowloadService;
import com.unison.lottery.weibo.dataservice.commons.model.BFResultModel;
import com.unison.lottery.weibo.dataservice.commons.model.BFXmlByIdModel;
import com.unison.lottery.weibo.dataservice.commons.model.BJEuroreOddsModel;
import com.unison.lottery.weibo.dataservice.commons.model.DataInterfaceResponse;
import com.unison.lottery.weibo.dataservice.commons.model.LeagueModel;
import com.unison.lottery.weibo.dataservice.commons.model.MatchidModel;
import com.unison.lottery.weibo.dataservice.commons.model.TeamModel;
import com.unison.lottery.weibo.dataservice.commons.model.TechnicModel;
import com.unison.lottery.weibo.dataservice.commons.parse.ParseService;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class DataServiceCommonsTest 
    extends TestCase
{
	
	private DowloadService downloadService;
	
	private ApplicationContext ctx;

	private ParseService parseService;
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public DataServiceCommonsTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( DataServiceCommonsTest.class );
    }

    /**
     * 
     */
    public void testDownloadDataToString()
    {
    	
//    	DowloadService downloadService=new DownloadServiceImpl();
    	DataInterfaceResponse result=downloadService.downloadToString(DataInterfaceName.BJ_EUROPE_ODDS);
//        System.out.println("result="+result);
    	assertTrue( null!=result&&result.isSucc() );
    	
    }
    
    public void testDownloadDataToFile()
    {
    	
    	DataInterfaceResponse result=downloadService.downloadToFile(DataInterfaceName.BJ_EUROPE_ODDS);
    	System.out.println("result file="+result.getResponseFile());
    	assertTrue( null!=result
        		&&result.isSucc()
        		&&null!=result.getResponseFile()
        		&&result.getResponseFile().exists());
    }
    
    public void testDownloadDataToFileWithExtendParams()
    {
    	
    	Map<String,String> extendParams=new HashMap<String,String>();
    	extendParams.put("date", "2014-01-14");
		DataInterfaceResponse result=downloadService.downloadToFileWithExtendParams(DataInterfaceName.BJ_EUROPE_ODDS,extendParams);
    	System.out.println("result file="+result.getResponseFile());
    	assertTrue( null!=result
        		&&result.isSucc()
        		&&null!=result.getResponseFile()
        		&&result.getResponseFile().exists());
    }
    
    public void testDownloadDataToStringWithExtendParams()
    {
    	
    	Map<String,String> extendParams=new HashMap<String,String>();
    	extendParams.put("date", "2014-01-14");
		DataInterfaceResponse result=downloadService.downloadToStringWithExtendParams(DataInterfaceName.BJ_EUROPE_ODDS,extendParams);
    	
    	assertTrue( null!=result
        		&&result.isSucc());
    }
    
    public void testParseJSFromString(){
    	String js=null;
		parseService.parseJSFromUTF8String(js);
    	
    }
    
    public void testParseJSFromFile(){
    	File jsFile=new File("D:\\dataInterface\\BFData\\1389681124217.txt");
		Bindings bindings = parseService.parseJSFromFile(jsFile,DataInterfaceName.BFData);
    	assertTrue(null!=bindings);
    	//assertTrue(null!=bindings.get("A"));
    	Object a = bindings.get("A");
    	
    	NativeArray  nativeArray=(NativeArray) a;
    	List<List<String>> content=parseService.nativeArrayToListOfStringList(nativeArray);
    	if(null!=content&&!content.isEmpty()){
    		for(List<String> contentItem:content){
    			if(null!=contentItem&&!contentItem.isEmpty()){
    				for(String item:contentItem){
    					System.out.print("item="+item+",");
    				}
    			}
    			System.out.println();
    		}
    	}
    	
    	String matchdate = (String) bindings.get("matchdate");
    	System.out.println("matchdate="+matchdate);
    	
    	Double matchcount =  (Double) bindings.get("matchcount");
    	System.out.println("matchcount="+matchcount);
    	
    	NativeArray c=(NativeArray) bindings.get("C");
    	List<List<Object>> result=parseService.nativeArrayToListOfObjectList(c);
    	
    	
    	if(null!=result&&!result.isEmpty()){
    		for(List<Object> contentItem:result){
    			if(null!=contentItem&&!contentItem.isEmpty()){
    				for(Object item:contentItem){
    					System.out.print("item="+item+",");
    				}
    			}
    			System.out.println();
    		}
    	}
    	
    	jsFile=new File("D:\\dataInterface\\BFDetail\\1389682506513.txt");
		bindings = parseService.parseJSFromFile(jsFile,DataInterfaceName.BFDetail);
    	assertTrue(null!=bindings);
    	NativeArray rq = (NativeArray) bindings.get("rq");
    	List<String> strList=parseService.nativeArrayToListOfString(rq);
    	if(null!=strList&&!strList.isEmpty()){
    		for(String str:strList){
    			System.out.println("str="+str);
    		}
    	}
    }
    
    
  
    
    
    public void testParseXmlFromFileWithDom(){
    	File xmlFile=new File("F:\\dataInterface\\ChangeOdds\\1389940014248.txt");
		List<List<List<String>>> result=parseService.parseXmlFromFileWithDom(xmlFile,DataInterfaceName.ChangeOdds);
		for(List<List<String>> list:result){
			for(List<String> strList:list){
				for(String str:strList){
					System.out.println(str);
				}
			}
		}
    }
    
    
    public void testParseTextFromString(){
    	String str=null;
		List<List<List<String>>> result=parseService.parseTextFromUTF8String(str);
    }
    
    public void testParseTextFromFile(){
    	File file=new File("D:\\dataInterface\\CurrentDayOdds\\1389679680932.txt");
    	List<List<List<String>>> result=parseService.parseTextFromFile(file,DataInterfaceName.CurrentDayOdds);
    	if(null!=result&&!result.isEmpty()){
    		for(List<List<String>> secondResult:result){
    			if(null!=secondResult&&!secondResult.isEmpty()){
    				for(List<String> thirdResult:secondResult){
    					if(null!=thirdResult&&!thirdResult.isEmpty()){
    						for(String item:thirdResult){
    							System.out.print("item:"+item+",");
    						}
    					}
    					System.out.println();
    				}
    			}
    			System.out.println("==============================================");
    		}
    	}
    }
    
    
    
 
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		ctx = new ClassPathXmlApplicationContext(
				"dataServiceTest.xml");
		downloadService=(DowloadService) ctx.getBean("downloadService");
		parseService=(ParseService) ctx.getBean("parseService");
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		ctx = null;
		downloadService=null;
		parseService=null;
	}
	
	//百家欧赔
	public void testParseTextFromBJ_EUROPE_ODDSFile(){
		    	File xmlFile=new File("F:\\dataInterface\\BJ_EUROPE_ODDS\\1390201444391.txt");
				Object result=parseService.parseXmlFromFileWithJAXB(xmlFile,DataInterfaceName.BJ_EUROPE_ODDS);
				BJEuroreOddsModel bfModel= (BJEuroreOddsModel) result;
				System.out.println(bfModel.toString());
				
	}
	
	//赛程赛果 
	 public void testParseTextFromBFResultFile(){
	    	File xmlFile=new File("F:\\dataInterface\\BFResult\\1389872044338.txt");
			Object result=parseService.parseXmlFromFileWithJAXB(xmlFile,DataInterfaceName.BFResult);
			BFResultModel bfModel= (BFResultModel) result;
			System.out.println(bfModel.toString());
			
	 }

	 //联赛杯赛
	 public void testParseTextFromLeague(){
	    	File xmlFile=new File("F:\\dataInterface\\League\\1389872582592.txt");
			Object result=parseService.parseXmlFromFileWithJAXB(xmlFile,DataInterfaceName.League);
			LeagueModel bfModel= (LeagueModel) result;
			System.out.println(bfModel.toString());
			
	 }
	 
	 //球队资料    
	 public void testParseTextFromTeam(){
	    	File xmlFile=new File("F:\\dataInterface\\Team\\1389873146430.txt");
			Object result=parseService.parseXmlFromFileWithJAXB(xmlFile,DataInterfaceName.Team);
			TeamModel bfModel= (TeamModel) result;
			System.out.println(bfModel.toString());
			
	 }
	 
	 //彩票赛事与球探网的关联表   
	 public void testParseTextFromMatchid(){
	    	File xmlFile=new File("F:\\dataInterface\\Matchid\\1389873206522.txt");
			Object result=parseService.parseXmlFromFileWithJAXB(xmlFile,DataInterfaceName.Matchid);
			MatchidModel bfModel= (MatchidModel) result;
			System.out.println(bfModel.toString());
			
	 }
	 
	//一天内比赛的技术统计 
		 public void testParseTextFromTechnic(){
		    	File xmlFile=new File("F:\\dataInterface\\Technic\\1389873237118.txt");
				Object result=parseService.parseXmlFromFileWithJAXB(xmlFile,DataInterfaceName.Technic);
				TechnicModel bfModel= (TechnicModel) result;
				System.out.println(bfModel.toString());
				
		 }
		 
		//按赛程ID查该比赛的数据 
		 public void testParseTextFromBF_XMLByID(){
		    	File xmlFile=new File("F:\\dataInterface\\BF_XMLByID\\1389873268116.txt");
				Object result=parseService.parseXmlFromFileWithJAXB(xmlFile,DataInterfaceName.BF_XMLByID);
				BFXmlByIdModel bfModel= (BFXmlByIdModel) result;
				System.out.println(bfModel.toString());
				
		 }
		 
		 
		
	
	
}

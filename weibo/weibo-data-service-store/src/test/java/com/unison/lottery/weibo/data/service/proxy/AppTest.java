package com.unison.lottery.weibo.data.service.proxy;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.unison.lottery.weibo.data.crawler.proxy.ProxyChooser;
import com.unison.lottery.weibo.data.crawler.proxy.ProxyHealthChecker;
import com.unison.lottery.weibo.data.crawler.proxy.UserChooser;
import com.unison.lottery.weibo.data.crawler.proxy.query.dao.entity.ImeiMacHtcPO;
import com.unison.lottery.weibo.data.crawler.proxy.query.dao.entity.ProxyPO;
import com.unison.lottery.weibo.dataservice.commons.crawler.constants.CrawlerInterfaceName;

/**
 * Unit test for simple App.
 */
public class AppTest {
	private ApplicationContext ctx;
	
	private UserChooser userChooser;

	private ProxyChooser proxyChooser;

	private ProxyHealthChecker proxyHealthChecker;
	
	@Before
	public void init() throws Exception {

		ctx = new ClassPathXmlApplicationContext(
				"classpath:app-test-config.xml");
		// ctx.refresh();
		userChooser = ctx.getBean(UserChooser.class);
		proxyChooser = ctx.getBean(ProxyChooser.class);
		proxyHealthChecker = ctx.getBean(ProxyHealthChecker.class);
	}
    /**
     * 
     */
	@Test
    public void testUserChooser()
    {
    	
    	ImeiMacHtcPO user=userChooser.randomChooseOne();
    	
        assertTrue( null!=user );
        System.out.println(user);
    }
    
    @Test
    public void testProxyChooser()
    {
    	
    	ProxyPO proxy=proxyChooser.randomChooseOne();
        assertTrue( null!=proxy );
        System.out.println(proxy);
    }
    @Test
    public void testProxyHealthChecker()
    {
    	ProxyPO proxy=new ProxyPO();
    	proxy.setIp("118.145.17.144");
    	proxy.setPort("80");
    	boolean checkResult=proxyHealthChecker.check(proxy,"",new HashMap<String, String>());
        assertFalse( checkResult );
        proxy=new ProxyPO();
    	proxy.setIp("221.130.29.184");
    	proxy.setPort("8888");
    	checkResult=proxyHealthChecker.check(proxy,"",new HashMap<String, String>());
    	assertTrue( checkResult );
       
    }
    @Test
    public void testChooseRandomHealthProxy()
    {
    	ProxyPO proxy=proxyChooser.randomChooseHealthOne(CrawlerInterfaceName.InfoIndex,new HashMap<String, String>());
    	
        assertNotNull( proxy );
       System.out.println(proxy);
       
    }
    
   
    
  
}

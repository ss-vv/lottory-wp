package com.xhcms.lottery.mc.ticket;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.xhcms.lottery.commons.data.Ticket;
import com.xhcms.lottery.commons.persist.dao.BetSchemeDao;
import com.xhcms.lottery.commons.persist.dao.TicketDao;
import com.xhcms.lottery.commons.persist.entity.BetSchemePO;
import com.xhcms.lottery.commons.persist.entity.TicketPO;
import com.xhcms.lottery.mc.persist.service.TicketService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring-db.xml", "/spring-activemq.xml", "/spring-service.xml"})
public class TicketServiceTest {

	@Autowired
    private TicketService ticketService;
    @Autowired
    private TicketDao ticketDao;
    @Autowired
    private BetSchemeDao betSchemeDao;
    Set<Long> ids=new HashSet<Long>();
	
	@Before
	public void setup(){
    	ids.add(new Long(36055));
	}
    
    @Test
    @Ignore
    @Transactional
    @Rollback(value=false)
    public void testAaveticket(){
    	List<BetSchemePO> bsps = betSchemeDao.find(ids);
    	for(BetSchemePO bsp:bsps){
    		if(bsp!=null){
	        	bsp.setStatus(5100);//出票成功
	        	bsp.setTicketNote(bsp.getBetNote());
	        	bsp.setSaleStatus(2);
	        	betSchemeDao.save(bsp);
	        	List<TicketPO> ticketPOs=ticketDao.findByScheme(bsp.getId(), 0);
	        	if(ticketPOs==null || ticketPOs.size()==0){
	        		ticketPOs=ticketDao.findByScheme(bsp.getId(), 5203);
	        	}
	        	if(ticketPOs==null || ticketPOs.size()==0){
	        		ticketPOs=ticketDao.findByScheme(bsp.getId(), 5100);
	        	}
	        	for(TicketPO t:ticketPOs){
	        		t.setStatus(5100);//出票成功
	        		ticketDao.save(t);
	        	}
    		}
    	}
    }
    
    @Test
    @Ignore
    @Transactional
    public void testPrize() {
    	Map<Long, Ticket> tickets=new HashMap<Long, Ticket>();
    	List<BetSchemePO> bsps = betSchemeDao.find(ids);
    	for(BetSchemePO bsp:bsps){
        	List<TicketPO> ticketPOs=ticketDao.findByScheme(bsp.getId(), 5100);
        	Ticket ticket;
        	for(TicketPO t:ticketPOs){
        		ticket=new Ticket();
        		BeanUtils.copyProperties(t, ticket);
        		ticket.setPreTaxBonus(new BigDecimal(t.getMoney()).multiply(new BigDecimal(2)));//2.6倍奖金
        		ticket.setAfterTaxBonus(t.getPreTaxBonus());
        		ticket.setMessage("中奖未派奖");
        		ticket.setNumber("20112995518127307032");
        		ticket.setStatus(5203);
        		tickets.put(t.getId(), ticket);
        	}
    	}
    	ticketService.prize(tickets);
    }

    @Transactional(value="txManager")
    @Test
    public void testSumBoughtNote(){
    	Long schemeId = 87960L;
    	List<Long> schemes = new LinkedList<Long>();
    	schemes.add(schemeId);
    	List<Object[]> data = ticketDao.sumBoughtNote(schemes);
    	System.out.println("data: " + data);
    }
    
    @Test
    @Transactional(value="txManager")
    @Ignore
    public void testCheck(){
    	Map<Long, Ticket> ticketMap = new HashMap<Long, Ticket>();
    	TicketPO ticketPO = ticketDao.get(371927L);
    	Ticket ticket = new Ticket();
    	BeanUtils.copyProperties(ticketPO, ticket);
    	ticket.setPlayId("06_LC");
    	ticket.setStatus(5101);
    	ticketMap.put(371927L, ticket);
    	ticketService.check(ticketMap);
    }
}

package com.xhcms.lottery.commons.persist.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration("/test-db-spring.xml")
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    private static final Long USER = 1L;

    @Test
    public void test(){}
    
//    @Test
//    public void testListOrder() {
//        Calendar startC = Calendar.getInstance();
//        Date startDate = startC.getTime();
//        startC.add(Calendar.DAY_OF_YEAR, 1);
//        Date endDate = startC.getTime();
//        Paging paging = new Paging();
//        orderService.listOrder (USER, 0, startDate, endDate, paging);
//        System.out.println(paging.getResults());
//    }

}

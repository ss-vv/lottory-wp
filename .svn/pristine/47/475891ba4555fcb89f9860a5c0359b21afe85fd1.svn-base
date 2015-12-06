package com.xhcms.lottery.commons.persist.service;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.lottery.commons.data.Account;
import com.xhcms.lottery.lang.Constants;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration("/test-db-spring.xml")
public class AccountServiceTest {

    @Autowired
    private AccountService accountService;
    
    private static final Long USER = 1L;

    @Test
    public void test(){}
    
//    @Test
    @Ignore
    public void testRecharge() {
//        accountService.applyForRecharge(1, USER, new BigDecimal("54323.00"), new BigDecimal("2.00"), "充值54323.00，手续费2.00，实际充值54321.00");
//        assertAccount(new BigDecimal("54321.00"), Constants.ZERO, Constants.ZERO, Constants.ZERO, Constants.ZERO);
    }
    
//    @Test
//    @Ignore
//    public void testWithdraw() {
//        accountService.withdraw(USER, new BigDecimal("998.00"), new BigDecimal("2.00"), "提现998.00，手续费2.00");
//        assertAccount(Constants.ZERO, Constants.ZERO, new BigDecimal("1000.00"), new BigDecimal("1000.00"), Constants.ZERO);
//    }
    
//    @Test
//    @Ignore
//    public void testDevide() {
//        accountService.devide(1, USER, new BigDecimal("1200.00"), new BigDecimal("200.00"), "派奖1200.00，个税200.00");
//        assertAccount(Constants.ZERO, Constants.ZERO, new BigDecimal("1000"), new BigDecimal("1000"), Constants.ZERO);
//    }
    
//    @Test
    @Ignore
    public void testGrant() {
        accountService.grant(1, USER, new BigDecimal("12345.00"), "赠款12345.00");
        assertAccount(Constants.ZERO, Constants.ZERO, new BigDecimal("12345"), Constants.ZERO, new BigDecimal("12345.00"));
    }
    
//    @Test
//    @Ignore
//    public void testFrozen() {
//        accountService.frozen(USER, new BigDecimal("29876.54"), "投注下单冻结资金29876.54");
//        assertAccount(new BigDecimal("36789.46"), Constants.ZERO, new BigDecimal("29876.54"), new BigDecimal("17531.54"), new BigDecimal("12345"));
//    }
    
//    @Test
//  @Ignore
//  public void testDeduct() {
//      accountService.deduct(USER, new BigDecimal("11111.11"), "扣款11111.11");
//      assertAccount(new BigDecimal("36789.46"), Constants.ZERO, new BigDecimal("18765.43"), new BigDecimal("17531.54"), new BigDecimal("1233.89"));
//  }
    
//    @Test
//    @Ignore
//    public void testReturnDeduct() {
//        accountService.returnDeduct(1, USER, new BigDecimal("2345.67"), "扣款返还2345.67");
//        assertAccount(new BigDecimal("39135.13"), Constants.ZERO, new BigDecimal("16419.76"), new BigDecimal("15185.87"), new BigDecimal("1233.89"));
//    }
    
    private void assertAccount(BigDecimal fund, BigDecimal grant, BigDecimal frozen, BigDecimal frozenFund, BigDecimal frozenGrant){
        Account a = accountService.getAccount(USER);
        
        Assert.assertTrue("充值余额", (0 == fund.compareTo(a.getFund())));
        Assert.assertTrue("赠款余额", (0 == grant.compareTo(a.getGrant())));
        Assert.assertTrue("冻结总金额", (0 == frozen.compareTo(a.getFrozenFund().add(a.getFrozenGrant()))));
        Assert.assertTrue("冻结充值金额", (0 == frozenFund.compareTo(a.getFrozenFund())));
        Assert.assertTrue("冻结赠款金额", (0 == frozenGrant.compareTo(a.getFrozenGrant())));
        Assert.assertTrue("可投注金额", (0 == a.getFree().compareTo(a.getFund().add(a.getGrant()))));
    }
}

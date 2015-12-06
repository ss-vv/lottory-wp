package com.xhcms.lottery.account.web.action.yeepay;

import java.io.InputStream;
import java.math.BigDecimal;

import org.apache.activemq.util.ByteArrayInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.Action;
import com.xhcms.lottery.account.util.YeePay;
import com.xhcms.lottery.account.web.YeePayContext;
import com.xhcms.lottery.commons.data.VoucherUser;
import com.xhcms.lottery.commons.persist.service.AccountService;
import com.xhcms.lottery.commons.persist.service.VoucherUserService;

public class CallbackAction implements Action {

    private static final Logger log = LoggerFactory.getLogger(CallbackAction.class);

    @Autowired
    private YeePayContext yeePayContext;
    
    @Autowired
    private VoucherUserService voucherUserService;
    @Autowired
    private AccountService accountService;
    private String p1_MerId;
    private String r0_Cmd;
    private String r1_Code;
    private String r2_TrxId;
    private String r3_Amt;
    private String r4_Cur;
    private String r6_Order;
    private String r7_Uid;
    private String r8_MP;
    private String r9_BType;
    private String hmac;

    private InputStream inputStream;
    
    @Override
    public String execute() {
        try {
            if (YeePay.verifyHmac(hmac, p1_MerId, r0_Cmd, r1_Code, r2_TrxId, r3_Amt, r4_Cur, "", r6_Order, r7_Uid,
                    r8_MP, r9_BType, yeePayContext.getKeyValue())) {
                if ("1".equals(r1_Code)) {
                	if(!accountService.isDealWithTransactionResult(new Long(r6_Order))){
                		try{
                			Long uId = Long.parseLong(r8_MP);
                			Long rId = Long.parseLong(r6_Order);
                			BigDecimal ramount = new BigDecimal(r3_Amt);
                			VoucherUser vu = new VoucherUser();
                			try {
                				// 使用优惠劵,并获取优惠劵信息
                				vu = voucherUserService.useWebRechargeVoucher(rId, uId);
							} catch (Exception e) {
								e.printStackTrace();
								log.error("用户ID:"+uId+"  流水id:"+rId+" msg:"+e.getMessage());
							}
                			// 如果取得优惠劵信息说明使用成功！
							if (vu != null && vu.getId() != null) {
								// 充值到赠款
								accountService.rechargeSuccess(rId);
								accountService.grant(0, uId, ramount, vu.getGrantType().getName());
							} else {
								// 正常充值
								accountService.comfirmRecharge(0, uId, ramount, rId, r2_TrxId);
							}
                		}catch(Exception e) {
                			e.printStackTrace();
                			log.info("充值返还彩金活动易宝异步回调出错！");
                		}
                	}                  
                }
            } else {
                log.warn("Recharge is unsuccessful, orderId=" + r6_Order + ", trxId=" + r2_TrxId);
            }
        } catch (Throwable t) {
            log.warn(t.getClass().getName() + " : " + t.getMessage());
        } finally {
            inputStream = new ByteArrayInputStream("success".getBytes());
        }

        return Action.SUCCESS;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setP1_MerId(String p1_MerId) {
        this.p1_MerId = p1_MerId;
    }

    public void setR0_Cmd(String r0_Cmd) {
        this.r0_Cmd = r0_Cmd;
    }

    public void setR1_Code(String r1_Code) {
        this.r1_Code = r1_Code;
    }

    public void setR2_TrxId(String r2_TrxId) {
        this.r2_TrxId = r2_TrxId;
    }

    public void setR3_Amt(String r3_Amt) {
        this.r3_Amt = r3_Amt;
    }

    public void setR4_Cur(String r4_Cur) {
        this.r4_Cur = r4_Cur;
    }

    public void setR6_Order(String r6_Order) {
        this.r6_Order = r6_Order;
    }

    public void setR7_Uid(String r7_Uid) {
        this.r7_Uid = r7_Uid;
    }

    public void setR8_MP(String r8_MP) {
        this.r8_MP = r8_MP;
    }

    public void setR9_BType(String r9_BType) {
        this.r9_BType = r9_BType;
    }

    public void setHmac(String hmac) {
        this.hmac = hmac;
    }

}

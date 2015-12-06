package com.xhcms.lottery.account.web.action.yeepay;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.lottery.account.util.YeePay;
import com.xhcms.lottery.account.web.PromotionContext;
import com.xhcms.lottery.account.web.YeePayContext;
import com.xhcms.lottery.account.web.action.BaseAction;
import com.xhcms.lottery.commons.data.Account;
import com.xhcms.lottery.commons.persist.service.AccountService;
import com.xhcms.lottery.commons.persist.service.PMRechargeRedeemedService;
import com.xhcms.lottery.commons.persist.service.VoucherUserService;
import com.xhcms.ucenter.sso.client.UserProfile;

/**
 * 
 * 
 * <p>Title: 充值</p>
 * <p>Description: </p>
 * <p>Copyright：Copyright (c) 2011</p>
 * <p>Company：XingHui Spirit (Beijing) Technical Co.,Ltd.</p>
 * 
 * @author wanged
 * @version 1.0.0
 */
public class PayAction extends BaseAction implements ServletRequestAware {
	private static final Logger log = LoggerFactory.getLogger(PayAction.class);
	
    private static final long serialVersionUID = 8030414964917400749L;
    private static final String NEED_RESP = "1";
    private static final String SAF = "0";
    private static final String CMD = "Buy";
    private static final String CUR = "CNY";

    @Autowired
    private YeePayContext yeePayContext;

    @Autowired
    private AccountService accountService;
    @Autowired
    private VoucherUserService voucherUserService;
    @Autowired
    private PMRechargeRedeemedService pMRechargeRedeemedService;
    
    @Autowired
    private PromotionContext promotionContext;
    
    private Account account;

	private HttpServletRequest request;
    private BigDecimal amount;
    public Long voucherId; // 优惠卷id
    public boolean isUseVoucher; // 是否使用优惠劵
	private String frpId;
    private String memberId;
    private String order;
    private String amt;
    private String callback;
    private String url;
    private String mp;
    private String hmac;

    @Override
    public String execute() {
        UserProfile user = getUser();
        if(user.getId() == 0){
            return LOGIN;
        }
        
        if(amount.compareTo(new BigDecimal(2.0)) == -1) {
        	account = accountService.getAccount(user.getId());
        	addActionError(getText("recharge.amount.little"));
        	return INPUT;
        }
        
        url = yeePayContext.getUrl();
        amt = amount.toString();
        memberId = encode(yeePayContext.getMemberId());
        callback = encode(yeePayContext.getCallback());
        mp = encode(String.valueOf(user.getId()));
        // 检查优惠劵是否有效
        if(isUseVoucher){
        	voucherUserService.validWebVoucherRV(voucherId, getUserId(), amount);
        }
        order = String.valueOf(accountService.applyForRecharge(user.getId(), amount, BigDecimal.ZERO, BigDecimal.ZERO, frpId,
                request.getRemoteAddr()));
        // 锁定优惠劵
        if(isUseVoucher){
        	voucherUserService.lockingRechargeVoucher(Long.valueOf(order), getUserId(), voucherId, amount);
        }

        hmac = YeePay.getHmac(CMD, yeePayContext.getMemberId(), order, amt, CUR, "", "", "",
                yeePayContext.getCallback(), SAF, encode(mp), frpId, NEED_RESP, yeePayContext.getKeyValue());

        return SUCCESS;
    }

    private String encode(String v) {
        try {
            return new String(v.getBytes("utf-8"), "gbk");
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setFrpId(String frpId) {
        this.frpId = frpId.toUpperCase();
    }

    public String getFrpId() {
        return frpId;
    }

    public String getMemberId() {
        return memberId;
    }

    public String getOrder() {
        return order;
    }

    public String getAmt() {
        return amt;
    }

    public String getCallback() {
        return callback;
    }

    public String getUrl() {
        return url;
    }

    public String getMp() {
        return mp;
    }

    public String getHmac() {
        return hmac;
    }

    @Override
    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }
    
    public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
}

package com.xhcms.lottery.account.web.action.alipay;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.lottery.account.util.alipay.AlipaySubmit;
import com.xhcms.lottery.account.web.AliPayContext;
import com.xhcms.lottery.account.web.action.BaseAction;
import com.xhcms.lottery.commons.data.Account;
import com.xhcms.lottery.commons.persist.service.AccountService;
import com.xhcms.lottery.commons.persist.service.VoucherUserService;
import com.xhcms.ucenter.sso.client.UserProfile;

/**
 * <p>Title: 充值</p>
 * 
 * @author yongli zhu, lei.wang
 */
public class PayAction extends BaseAction implements ServletRequestAware {

	private static final long serialVersionUID = 7607788669490353395L;
	private static final Logger log = LoggerFactory.getLogger(PayAction.class);

    @Autowired
    private AccountService accountService;
    
    @Autowired
    private AliPayContext aliPayContext;
    
    @Autowired
    private VoucherUserService voucherUserService;
    
    private Account account;
    public Long voucherId; // 优惠卷id
    public boolean isUseVoucher; // 是否使用优惠劵
    private BigDecimal amount;
	private String frpId;
    private String order;
    private String amt;
    private String mp;
    private String mysign;
    private String url;
    private String exterInvokeIp;
    private String partner;
    private String returnUrl;
    private String notifyUrl;
    private String signType;
    private String sellerEmail;
    private String paymethod;
    
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
        
		//获取用户的IP地址，作为防钓鱼的一种方法
		String clientIp = request.getHeader("x-forwarded-for");
		if ((clientIp == null) || (clientIp.length() == 0)
				|| ("unknown".equalsIgnoreCase(clientIp))) {
			clientIp = request.getHeader("Proxy-Client-IP");
		}
		if ((clientIp == null) || (clientIp.length() == 0)
				|| ("unknown".equalsIgnoreCase(clientIp))) {
			clientIp = request.getHeader("WL-Proxy-Client-IP");
		}
		if ((clientIp == null) || (clientIp.length() == 0)
				|| ("unknown".equalsIgnoreCase(clientIp))) {
			clientIp = request.getRemoteAddr();
		}
        //goto.vm 页面取值
        url = aliPayContext.getUrl();
        exterInvokeIp = clientIp;
        partner = aliPayContext.getPartner();
        returnUrl = aliPayContext.getReturnUrl();
        notifyUrl = aliPayContext.getNotifyUrl();      
        
        log.debug("returnUrl=" + returnUrl + ", Notify Url:" + notifyUrl);
        
        signType = aliPayContext.getSignType();
        sellerEmail = aliPayContext.getSellerEmail();       
        amt = amount.toString();
        mp = String.valueOf(user.getId());
        log.info("user id={}", mp);
        // 检查优惠劵是否有效
        if(isUseVoucher){
        	voucherUserService.validWebVoucherRV(voucherId, getUserId(), amount);
        }
        order = String.valueOf(accountService.alipayApplyForRecharge(user.getId(), amount, BigDecimal.ZERO, BigDecimal.ZERO, frpId,
        		exterInvokeIp));
        // 锁定优惠劵
        if(isUseVoucher){
        	voucherUserService.lockingRechargeVoucher(Long.valueOf(order), getUserId(), voucherId, amount);
        }
        
        log.info("order id={}",order);
		Map<String, String> sParaTemp = new HashMap<String, String>();  
		sParaTemp.put("partner", partner);
		sParaTemp.put("out_trade_no", order);
		if(null!=frpId && frpId.equals("ALIPAY")){
			sParaTemp.put("defaultbank", "");//支付宝
			sParaTemp.put("paymethod", "");
			frpId = "";
			paymethod = "";
		}else{
			sParaTemp.put("defaultbank", frpId);//银行编码
			sParaTemp.put("paymethod", "bankPay");
			paymethod = "bankPay";
		}		
		sParaTemp.put("exter_invoke_ip", exterInvokeIp);
		sParaTemp.put("extra_common_param", mp);
		sParaTemp.put("total_fee", amt);
    	sParaTemp.put("return_url", returnUrl);        
        sParaTemp.put("notify_url", notifyUrl);
        sParaTemp.put("seller_email",sellerEmail);
        
       
        String alikey = aliPayContext.getKey();
		mysign = AlipaySubmit.create_direct_pay_by_user(sParaTemp,alikey);
		log.info("mysign={}",mysign);
        return SUCCESS;
    }



	public String getUrl() {
		return url;
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

    public String getOrder() {
        return order;
    }

    public String getAmt() {
        return amt;
    }

    public String getMp() {
        return mp;
    }

    public String getMysign() {
        return mysign;
    }
    
	public String getPartner() {
		return partner;
	}

	public String getExterInvokeIp() {
		return exterInvokeIp;
	}

	public String getReturnUrl() {
		return returnUrl;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public String getSignType() {
		return signType;
	}
	
	public String getSellerEmail() {
		return sellerEmail;
	}

	public void setSellerEmail(String sellerEmail) {
		this.sellerEmail = sellerEmail;
	}

	public String getPaymethod() {
		return paymethod;
	}

	public void setPaymethod(String paymethod) {
		this.paymethod = paymethod;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
}

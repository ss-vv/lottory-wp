package com.xhcms.lottery.account.web.action.yeepay;

import java.io.InputStream;
import java.math.BigDecimal;

import org.apache.activemq.util.ByteArrayInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.lottery.account.util.yeepay.DigestUtil;
import com.xhcms.lottery.account.web.YeePayContext;
import com.xhcms.lottery.account.web.action.BaseAction;
import com.xhcms.lottery.commons.data.Recharge;
import com.xhcms.lottery.commons.data.VoucherUser;
import com.xhcms.lottery.commons.persist.service.AccountService;
import com.xhcms.lottery.commons.persist.service.VoucherUserService;

public class ClientCardNotifyAction extends BaseAction {

	private static final long serialVersionUID = 3134042797785516390L;
	
	private static final Logger log = LoggerFactory.getLogger(ClientCardNotifyAction.class);
    @Autowired
    private VoucherUserService voucherUserService;
	@Autowired
    private AccountService accountService;
	
    @Autowired
	private YeePayContext yeePayContext;
    
    private InputStream inputStream;

	@Override
	public String execute() {			
		// 业务类型
		String r0_Cmd = formatString(request.getParameter("r0_Cmd"));
		// 支付结果
		String r1_Code = formatString(request.getParameter("r1_Code"));
		// 商户编号
		String p1_MerId = formatString(request.getParameter("p1_MerId"));
		// 商户订单号
		String p2_Order = formatString(request.getParameter("p2_Order"));
		// 成功金额
		String p3_Amt = formatString(request.getParameter("p3_Amt"));
		// 支付方式
		String p4_FrpId = formatString(request.getParameter("p4_FrpId"));
		// 卡序列号组
		String p5_CardNo = formatString(request.getParameter("p5_CardNo"));
		// 确认金额组
		String p6_confirmAmount = formatString(request.getParameter("p6_confirmAmount"));
		// 实际金额组
		String p7_realAmount = formatString(request.getParameter("p7_realAmount"));
		// 卡状态组
		String p8_cardStatus = formatString(request.getParameter("p8_cardStatus"));
		// 扩展信息
		String p9_MP = formatString(request.getParameter("p9_MP"));
		// 支付余额 注：此项仅为订单成功,并且需要订单较验时才会有值。失败订单的余额返部返回原卡密中
		String pb_BalanceAmt = formatString(request.getParameter("pb_BalanceAmt"));
		// 余额卡号  注：此项仅为订单成功,并且需要订单较验时才会有值
		String pc_BalanceAct = formatString(request.getParameter("pc_BalanceAct"));
		// 签名数据
		String hmac	= formatString(request.getParameter("hmac"));
		
		log.info("r0_Cmd={}" + r0_Cmd);
		log.info("r1_Code={}" + r1_Code);
		log.info("p2_Order={}" + p2_Order);
		log.info("p3_Amt={}" + p3_Amt);
		log.info("p4_FrpId={}" + p4_FrpId);
		log.info("p8_cardStatus={}" + p8_cardStatus);
		log.info("p9_MP={}" + p9_MP);
		
		String newHmac = DigestUtil.getHmac(new String[] {  r0_Cmd, 
			   	r1_Code,
			   	p1_MerId,
			   	p2_Order,
			   	p3_Amt,
			   	p4_FrpId,
			   	p5_CardNo,
			   	p6_confirmAmount,
			   	p7_realAmount,
			   	p8_cardStatus,
			   	p9_MP,
			   	pb_BalanceAmt,
			   	pc_BalanceAct}, yeePayContext.getKeyValue());
		Long rechageId = new Long(p2_Order);
		if (hmac.equals(newHmac)) {
			if (r1_Code.equals("1")) {
	        	//根据交易状态处理业务逻辑
	        	//判断该笔订单是否已经做过处理
	        	if(!accountService.isDealWithTransactionResult(rechageId)){
	        		Recharge recharge = accountService.getRecharge(rechageId);
	        		Long userId = recharge.getUserId();
	        		// use voucher
	        		VoucherUser vu = new VoucherUser();
        			try {
        				// 使用优惠劵,并获取优惠劵信息
        				vu = voucherUserService.useClientRechargeVoucher(rechageId, userId);
					} catch (Exception e) {
						e.printStackTrace();
						log.error("用户ID:"+userId+"  流水id:"+rechageId+" msg:{}",e);
					}
        			// 如果取得优惠劵信息说明使用成功！
					if (vu != null && vu.getId() != null) {
						// 充值到赠款
						accountService.rechargeSuccess(rechageId);
						accountService.grant(0, userId, new BigDecimal(p3_Amt), vu.getGrantType().getName());
					} else {
						// 正常充值
						accountService.comfirmRecharge(0, Long.parseLong(p9_MP), new BigDecimal(p3_Amt), rechageId, "0");
					}
	        		
	        		log.info("client card yeepay notify success!");
	        	}
	        	//当交易状态成功，处理业务逻辑成功。回写success
	        	inputStream = new ByteArrayInputStream("success".getBytes());
			} else {
				//充值失败
				String cardStatus = convertCardStatus(Integer.parseInt(p8_cardStatus));
				accountService.updateRecharge(rechageId, cardStatus);
				inputStream = new ByteArrayInputStream("fail".getBytes());
				log.info("client card yeepay notify fail !");
			}
		} else {
			String errorMessage = "交易签名被篡改!";
			log.error(errorMessage);
			throw new RuntimeException(errorMessage);
		}
		
		return SUCCESS;
	}
	
	private String formatString(String text){ 
		if(text == null) {
			return ""; 
		}
		return text;
	}

	public InputStream getInputStream() {
		return inputStream;
	}
	
	private String convertCardStatus(int cardStatus) {
		String cardStatusStr = null;
		switch(cardStatus){
			case 0:
				cardStatusStr = "销卡成功，订单成功！"; 
				break;
			case 1:
				cardStatusStr = "销卡成功，订单失败！"; 
				break;
			case 7:
				cardStatusStr = "卡号卡密或卡面额不符合规则！"; 
				break;
			case 1002:
				cardStatusStr = "本张卡密您提交过于频繁，请您稍后再试！"; 
				break;		
			case 1003:
				cardStatusStr = "不支持的卡类型（比如电信地方卡）！"; 
				break;	
			case 1004:
				cardStatusStr = "密码错误或充值卡无效！"; 
				break;	
			case 1006:
				cardStatusStr = "充值卡无效！"; 
				break;	
			case 1007:
				cardStatusStr = "卡内余额不足！"; 
				break;	
			case 1008:
				cardStatusStr = "余额卡过期（有效期1个月）！"; 
				break;	
			case 1010:
				cardStatusStr = "此卡正在处理中！"; 
				break;	
			case 10000:
				cardStatusStr = "未知错误！"; 
				break;	
			case 2005:
				cardStatusStr = "此卡已使用！"; 
				break;	
			case 2006:
				cardStatusStr = "卡密在系统处理中！"; 
				break;	
			case 2007:
				cardStatusStr = "该卡为假卡！"; 
				break;	
			case 2008:
				cardStatusStr = "该卡种正在维护！"; 
				break;	
			case 2009:
				cardStatusStr = "浙江省移动维护！"; 
				break;	
			case 2010:
				cardStatusStr = "江苏省移动维护！"; 
				break;	
			case 2011:
				cardStatusStr = "福建省移动维护！"; 
				break;	
			case 2012:
				cardStatusStr = "辽宁省移动维护！"; 
				break;	
			case 2013:
				cardStatusStr = "该卡已被锁定！"; 
				break;	
			case 2014:
				cardStatusStr = "系统繁忙，请稍后再试！"; 
				break;	
			default:
				cardStatusStr = "未知错误！";
				break;
		
		}
		return cardStatusStr;
	}
}

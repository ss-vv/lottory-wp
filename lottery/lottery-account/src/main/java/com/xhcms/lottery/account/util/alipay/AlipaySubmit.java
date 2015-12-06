package com.xhcms.lottery.account.util.alipay;

import java.util.Map;

/* *
 *类名：AlipaySubmit
 *功能：支付宝各接口请求提交类
 *详细：构造支付宝各接口表单HTML文本，获取远程HTTP数据
 *版本：3.2
 *日期：2011-03-17
 *说明：
 */

public class AlipaySubmit {
	
    /**
     * 构造纯网关接口
     * @param sParaTemp 请求参数集合
     * @return 表单提交HTML信息
     */
    public static String create_direct_pay_by_user(Map<String, String> sParaTemp, String alikey) {

    	//增加基本配置
    	sParaTemp.put("service", "create_direct_pay_by_user");
    	sParaTemp.put("_input_charset", "UTF-8");
    	sParaTemp.put("subject", "大V彩会员充值");    	
    	sParaTemp.put("payment_type", "1"); 

        return AlipaySubmit.buildRequestPara(sParaTemp, alikey);
    }

    /**
     * 生成要请求给支付宝的参数数组
     * @param sParaTemp 请求前的参数数组
     * @return 要请求的参数数组
     */
    private static String buildRequestPara(Map<String, String> sParaTemp, String alikey) {
        //除去数组中的空值和签名参数
        Map<String, String> sPara = AlipayCore.paraFilter(sParaTemp);
        //生成签名结果
        AlipayCore alipayCore = new AlipayCore();
        String mysign = alipayCore.buildMysign(sPara, alikey);

        return mysign;
    }
 
}

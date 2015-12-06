package com.xhcms.lottery.account.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class YeePay {
    
    /**
     * 返回校验hmac方法
     * 
     * @param hmac
     * @param merId 商户编号
     * @param cmd 业务类型
     * @param code 支付结果
     * @param trxId 易宝支付交易流水号
     * @param amt 支付金额
     * @param cur 交易币种
     * @param pid 商品名称
     * @param order 商户订单号
     * @param uid 易宝支付会员ID
     * @param mp 商户扩展信息
     * @param bType 交易结果返回类型
     * @param keyValue 交易结果返回类型
     * @return
     */
    public static boolean verifyHmac(String hmac, String merId, String cmd, String code, String trxId, String amt,
            String cur, String pid, String order, String uid, String mp, String bType, String keyValue) {

        StringBuilder buf = new StringBuilder();

        buf.append(merId);// 商户编号
        buf.append(cmd);// 业务类型
        buf.append(code);// 支付结果
        buf.append(trxId);// 易宝支付交易流水号
        buf.append(amt); // 支付金额
        buf.append(cur); // 交易币种
        buf.append(pid); // 商品名称
        buf.append(order);// 商户订单号
        buf.append(uid);// 易宝支付会员ID
        buf.append(mp);// 商户扩展信息
        buf.append(bType);// 交易结果返回类型
        String mac = hmacSign(buf.toString(), keyValue);

        if (hmac.equals(mac)) {
            return (true);
        }
        return (false);
    }

    /**
     * 生成hmac方法
     * 
     * @param cmd 业务类型
     * @param merId 商户编号
     * @param order 商户订单号
     * @param amt 支付金额
     * @param cur 交易币种
     * @param pid 商品名称
     * @param pcat商品种类
     * @param pdesc 商品描述
     * @param url 商户接收支付成功数据的地址
     * @param saf 送货地址
     * @param mp 商户扩展信息
     * @param frpId 银行编码
     * @param needResponse 应答机制
     * @param keyValue 商户密钥
     * @return
     */
    public static String getHmac(String cmd, String merId, String order, String amt, String cur,
            String pid, String pcat, String pdesc, String url, String saf, String mp, String frpId,
            String needResponse, String keyValue) {
        StringBuilder buf = new StringBuilder();
        
        buf.append(cmd).append(merId).append(order).append(amt).append(cur);
        buf.append(pid).append(pcat).append(pdesc).append(url).append(saf);
        buf.append(mp).append(frpId).append(needResponse);

        return hmacSign(buf.toString(), keyValue);
    }

    private static String hmacSign(String aValue, String aKey) {
        byte k_ipad[] = new byte[64];
        byte k_opad[] = new byte[64];
        byte keyb[];
        byte value[];
        try {
            keyb = aKey.getBytes("UTF-8");
            value = aValue.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            keyb = aKey.getBytes();
            value = aValue.getBytes();
        }

        Arrays.fill(k_ipad, keyb.length, 64, (byte) 54);
        Arrays.fill(k_opad, keyb.length, 64, (byte) 92);
        for (int i = 0; i < keyb.length; i++) {
            k_ipad[i] = (byte) (keyb[i] ^ 0x36);
            k_opad[i] = (byte) (keyb[i] ^ 0x5c);
        }

        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
        md.update(k_ipad);
        md.update(value);
        byte dg[] = md.digest();
        md.reset();
        md.update(k_opad);
        md.update(dg, 0, 16);
        dg = md.digest();
        return toHex(dg);
    }

    private static String toHex(byte input[]) {
        if (input == null) {
            return null;
        }

        StringBuilder buf = new StringBuilder(input.length * 2);
        for (int i = 0; i < input.length; i++) {
            int c = input[i] & 0xff;
            if (c < 16) {
                buf.append('0');
            }
            buf.append(Integer.toString(c, 16));
        }

        return buf.toString();
    }

}

package com.xhcms.ucenter.persist.redis.dao;

/**
 * @Description:获取Redis keys 
 * @author 江浩翔   
 * @date 2013-11-15 上午10:49:38 
 * @version V1.0
 */
public class Keys {
	private static final String SINA_WEIBO_UID = "sinaWeiboUid:%s";
	private static final String WEIXIN_OPEN_UID = "weixinOpenUid:%s";
	private static final String QQ_WEIBO_UID = "qqWeiboUid:%s";
	private static final String QQ_CONNECT_UID = "qqConnectUid:%s";
	private static final String USER = "user:%d";
	private static final String ALIPAY_UID="alipay_uid:%s";
	
	/**
	 * 构建新浪微博Uid，通过该key获取大V彩微博用户的id 
	 * @param sinaWeiboUid
	 * @return
	 */
	public static String getSinaWeiboUidKey(String sinaWeiboUid){
		return String.format(SINA_WEIBO_UID, sinaWeiboUid);
	}
	/**
	 * 构建微信Uid，通过该key获取大V彩微博用户的id 
	 * @param weixinOpenUid
	 * @return
	 */
	public static String getWeixinUidKey(String weixinOpenUid){
		return String.format(WEIXIN_OPEN_UID, weixinOpenUid);
	}
	
	/**
	 * 构建腾讯微博Uid，通过该key获取大V彩微博用户的id 
	 * @param sinaWeiboUid
	 * @return
	 */
	public static String getQQWeiboUidKey(String qqWeiboUid){
		return String.format(QQ_WEIBO_UID, qqWeiboUid);
	}
	public static String getQQConnectUidKey(String qqConnectUid){
		return String.format(QQ_CONNECT_UID, qqConnectUid);
	}
	
	/**
	 * 构建用户key
	 * @param uid
	 * @return
	 */
	public static String getUserKey(long uid){
		return String.format(USER, uid);
	}
	/**
	 * 构建阿里key
	 * @param alipayUid
	 * @return
	 */
	public static String getAlipayUidKay(String alipayUid){
		
		return String.format(ALIPAY_UID, alipayUid);
	}
}

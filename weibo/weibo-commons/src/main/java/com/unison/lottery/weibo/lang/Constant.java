package com.unison.lottery.weibo.lang;


/**
 * weibo常量类
 * 
 * @author Wang Lei
 */
public interface Constant {
	/**  登录用户信息 */
	public static class User{
		/** 用户信息 */
		public static final String USER = "user";
	}
	/** 关系*/
	public static class Relationship{
		/**关注的人*/
		public static final String FOLLOWING = "following";
		/**粉丝*/
		public static final String FOLLOWER = "follower";
	}
	/** 处理结果*/
	public static class Result {
		public static final String result = "result";
	}
	
	/** 登录状态*/
	public static class LoginState {
		public static final String UNLOGIN = "UNLOGIN";
		public static final String LOGINING = "LOGINING";
		public static final String LOGINING_BY_SINA_NOT_BIND = "LOGINING_BY_SINA_NOT_BIND";
		public static final String LOGINING_BY_QQ_NOT_BIND = "LOGINING_BY_QQ_NOT_BIND";
	}
	
	/** 结果信息 */
	public static class ResultMessage{
		public static final String ERROR = "错误！";
		public static final String PUBLISH_FAIL_LOGIN = "请先登录再发主帖！";
		public static final String PUBLISH_FAIL = "发送失败！";
		public static final String PUBLISH_UPDATE_FAIL = "修改失败！";
		public static final String FORWARD_FAIL_POST = "错误！主帖不存在或被删除！";
		public static final String DELETE_FAIL = "出错！删除失败！";
	}
	
	/** 删除替换内容 */
	public static class DeleteContent{
		public static final String USER_DELETED = "此主帖已被作者删除！";
		public static final String ADMIN_DELETED = "此主帖已被管理员删除！";
	}
	
	/**	账户相关标识*/
	public static class AccountCode {
		/** 注册结果相关*/
		public static class RegistResultCode {
			/** 注册成功*/
			public static final String REGIST_SUCCESS="REGIST_SUCCESS";
			/** 注册用户名不唯一*/
			public static final String REGIST_USERNAME_NOT_UNIQUE="REGIST_USERNAME_NOT_UNIQUE";
			/** 注册昵称不唯一*/
			public static final String REGIST_NICKNAME_NOT_UNIQUE="REGIST_NICKNAME_NOT_UNIQUE";
			/** 注册失败*/
			public static final String REGIST_ERROR="REGIST_ERROR";
		}
		
		public static class LoginResultCode {
			/** 登录成功*/
			public static final String LOGIN_SUCCESS="LOGIN_SUCCESS";
			/** 使用新浪主帖登录成功*/
			public static final String LOGIN_SUCCESS_BY_SINAWEIBO="LOGIN_SUCCESS_BY_SINAWEIB";
			/** 使用新浪主帖登录成功 ,未绑定大V彩帐号*/
			public static final String LOGIN_SUCCESS_BY_SINAWEIBO_NOT_BIND="LOGIN_SUCCESS_BY_SINAWEIBO_NOT_BIND";
			/** 使用腾讯主帖登录成功*/
			public static final String LOGIN_SUCCESS_BY_QQWEIBO="LOGIN_SUCCESS_BY_QQWEIBO";
			/** 使用腾讯主帖登录成功 ,未绑定大V彩帐号*/
			public static final String LOGIN_SUCCESS_BY_QQWEIBO_NOT_BIND="LOGIN_SUCCESS_BY_QQWEIBO_NOT_BIND";
			/** 登录失败,用户名或密码错误*/
			public static final String LOGIN_ERROR="LOGIN_ERROR";
			/** 登录失败,新浪主帖授权超期*/
			public static final String LOGIN_ERROR_BY_SINAWEIBO_EXPIRE="LOGIN_ERROR_BY_SINAWEIBO_EXPIRE";
			/** 登录失败,腾讯主帖授权超期*/
			public static final String LOGIN_ERROR_BY_QQWEIBO_EXPIRE="LOGIN_ERROR_BY_SINAWEIBO_EXPIRE";
		}
		
		public static class UpdateResultCode {
			public static final String UPDATE_SUCCESS = "UPDATE_SUCCESS";
			public static final String UPDATE_ERROR = "UPDATE_ERROR";
		} 
		
		public static class FollowResultCode {
			public static final String FOLLOW_SUCCESS = "FOLLOW_SUCCESS";
			public static final String FOLLOW_ERROR = "FOLLOW_ERROR";
		}
	}
	/** jedis map to Data Object，需要转换的时间属性*/
	public static class NeedConvertTimeField {
		/** WeiboUser 类的weiboUserCreateTime属性*/
		public static final String	WeiboUser_weiboUserCreateTime = "weiboUserCreateTime";
	}

	/** 大V彩主帖注册渠道编号*/
	public static final String WeiboChannel = "LaicaiWeibo";
	
	public static class SolrConfig{
		//public static final String SOLR_URL= "http://58.83.235.132:28080/solr/collection1/";
		//public static final String SOLR_URL= "http://localhost:9090/solr/collection1/";
		
		//public static final String SOLR_URL= "http://58.83.235.135:28080/solr/collection1/";
		public static final String SOLR_ID = "id";
		public static final String SEARCH_TYPE_USER = "userType";
		public static final String SEARCH_TYPE_WEIBO = "weiboType";
		public static final String SEARCH_TYPE_JCLQ_MATCH = "JCLQ";
		public static final String SEARCH_TYPE_JCZQ_MATCH = "JCZQ";
		public static final String SEARCH_TYPE_CTZC_MATCH = "CTZC";
		public static final String SEARCH_TYPE_BJDC_MATCH = "BJDC";
	}
	
	public static final String TOPIC_IMAGE_PREFIX = "http://images.davcai.com/topic/";
	public static final String APP_IMAGE_PREFIX = "http://images.davcai.com/ad/client/";
	/**
	 * 首页广告的图片服务器IP
	 */
	
	//public static final String AD_IMAGE_PREFIX="http://images.lai310.com/ad/";
	public static final String AD_IMAGE_PREFIX="http://images.davcai.com/ad/";
	/** 特殊主帖标记串 */
	public static class Mark {
		public static final String RECOMMEND = "#推荐#";
		public static final String ANNOUNCEMENT = "#公告#";
		public static final String NEWS = "#新闻#";
	}
	
	/** 大V彩晒单消息模板 */
	public static class LaiCaiShowSchemeTmp {
		public static final String ALONE = "我发起了{lottery}晒单。<a href=\"http://trade.davcai.com/ac/follow.do?id={schemeId}\" target=\"_blank\">查看晒单</a><br/>该方案包含以下赛事：";
		public static final String GROUP_BUY = "我发起了{lottery}合买。<a href=\"http://trade.davcai.com/ac/groupbuy.do?id={schemeId}\" target=\"_blank\">查看晒单</a><br/>该方案包含以下赛事：";
	}
	
	/** 球探网赛事状态 
	 * 比赛状态,0:未开,1:上半场,2:中场,3:下半场,-11:待定,-12:腰斩,-13:中断,-14:推迟,-1:完场
	 * */
	public static class QTMatchStatus {
		/** 0:未开 */
		public static final int  NOT_START= 0;
		/** 上半场 */
		public static final int FIRST_HALF =1;
		/** 中场 */
		public static final int HALF_TIME =2;
		/** 下半场 */
		public static final int SECOND_HALF =3;
		/** 待定 */
		public static final int UNDETERMINED =-11;
		/** 腰斩 */
		public static final int HALVED =-12;
		/** 中断 */
		public static final int UNTERRUPT =-13;
		/** 推迟 */
		public static final int DELAYED =-14;
		/** 完场 */
		public static final int END =-1;
	}
	
	/**
	 * 主帖来源 
	 */
	public static class WeiboFrom{
		public static final String LC_SD = "大V彩实单";
	}
	
	/**
	 * 主帖内容长度
	 */
	public static class WeiboContentLength{
		public static final int FORWARD = 1000;
		public static final int POST = 1000+7;
		public static final int ANNOTATION = 1000;
	}
}

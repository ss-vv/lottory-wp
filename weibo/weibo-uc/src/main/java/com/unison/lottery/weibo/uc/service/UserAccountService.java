package com.unison.lottery.weibo.uc.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.unison.lottery.weibo.data.WeiboUser;
import com.unison.lottery.weibo.uc.data.AccountDealResult;
import com.unison.lottery.weibo.uc.data.SinaWeiboLoginModel;
import com.unison.lottery.weibo.uc.data.WeixinTokenModel;
import com.xhcms.lottery.commons.persist.entity.UserPO;
import com.xhcms.ucenter.persistent.service.exception.LoginNameNotFoundException;
import com.xhcms.ucenter.persistent.service.exception.LoginNameOrPasswordBlankException;
import com.xhcms.ucenter.persistent.service.exception.PasswordWrongException;

/**
 * @Description:用户账户服务接口
 * @author 江浩翔
 * @date 2013-10-16 下午1:59:48
 * @version V1.0
 */
public interface UserAccountService {
	/**
	 * 注册大V彩账号
	 * @param weiboUser
	 * @return
	 */
	AccountDealResult regist(WeiboUser weiboUser) ;
	
	/**
	 * 注册系统特殊账号，如：“竞彩足球”。供管理系统使用。
	 * @param weiboUser
	 * @return
	 */
	AccountDealResult registSpecialUser(WeiboUser weiboUser) ;
	
	/**
	 * 使用大V彩帐号登录
	 * @param weiboUser
	 * @return
	 * @throws LoginNameOrPasswordBlankException 
	 * @throws PasswordWrongException 
	 * @throws LoginNameNotFoundException 
	 */
	AccountDealResult login(WeiboUser weiboUser) throws LoginNameNotFoundException, PasswordWrongException, LoginNameOrPasswordBlankException;

	/**
	 * 通过新浪微博帐号登录
	 * @param sinaWeiboLoginModel
	 * @return
	 */
	AccountDealResult loginBySinaWeibo(SinaWeiboLoginModel sinaWeiboLoginModel) ;
	
	/**
	 * 更新帐号全部信息，调用前请确保 weiboUser 信息完整
	 * @param weiboUser
	 * @return
	 */
	AccountDealResult updateAccountAllInfo(WeiboUser weiboUser) ;
	
	/**
	 * 判断是否绑定了新浪微博
	 * @param sinaWeiboUid
	 * @return
	 * @ 
	 */
	AccountDealResult isBindSinaWeibo(String sinaWeiboUid) ;
	
	/**
	 * 判断是否绑定了腾讯微博
	 * @param qqWeiboUid
	 * @return
	 */
	AccountDealResult isBindQQWeibo(String qqWeiboUid) ;
	
	/**
	 * 更新昵称
	 * @param weiboUser
	 * @return
	 */
	AccountDealResult updateNickName(WeiboUser weiboUser);
	/**
	 * 更新头像
	 * @param weiboUser
	 * @return
	 */
	AccountDealResult updateHeadImage(WeiboUser weiboUser);
	
	/**
	 * 检查用户名是否唯一
	 * @param username
	 * @return true-唯一;false-不唯一
	 */
	boolean isUserNameUnique(String username);
	
	/**
	 * 检查昵称是否唯一
	 * @param username
	 * @return true-唯一;false-不唯一
	 */
	boolean isNicknameUnique(String nickname);
	/**
	 * 检查身份证号码是否唯一
	 * @param idcard
	 * @return true-唯一;false-不唯一
	 */
	boolean isIdCardUnique(String idcard);
	
	/**
	 * 绑定新浪微博
	 * @param weiboUser
	 * @param sinaWeiboLoginModel
	 * @return
	 */
	AccountDealResult bindSinaWeibo(WeiboUser weiboUser,SinaWeiboLoginModel sinaWeiboLoginModel);
	
	/**
	 * 绑定QQ
	 * @param weiboUser
	 * @param qqUserInfo
	 * @return
	 */
	AccountDealResult bindQQ(WeiboUser weiboUser,WeiboUser qqUserInfo);
	
	/**
	 * 根据彩票uid获取用户信息
	 * @param uids 彩票uid
	 * @return
	 */
	List<WeiboUser> findWeiboUserByLotteryUids(String[] uids);
	
	/**
	 * 根据彩票uid获取用户信息
	 * @param uid 彩票uid
	 * @return
	 */
	WeiboUser findWeiboUserByLotteryUid(String uid);
	
	WeiboUser findWeiboUserByLotteryUidsNotLoadUser(String uid);
	
	/**
	 * 根据微博uid获取用户信息
	 * @param uids 微博uid
	 * @return
	 */
	List<WeiboUser> findWeiboUserByWeiboUids(String[] uids);
	
	/**
	 * 根据微博uid获取用户信息
	 * @param uid 微博uid
	 * @return
	 */
	WeiboUser findWeiboUserByWeiboUid(String uid);
	
	/**
	 * 用昵称集合 获得大V彩微博用户id集合
	 * @param nicknames
	 * @return
	 */
	String[] findWeiboUserIdsByNickNames(Set<String> nicknames);
	
	WeiboUser findWeiboUserByNickName(String nickName);
	
	/**
	 * 用昵称查微博用户id
	 * @param nickName 昵称
	 * @return 微博用户id
	 */
	String findWeiboUserIdByNickName(String nickName);
	
	/**
	 * 用昵称集合 获得微博用户集合
	 * @param nicknames
	 * @return
	 */
	List<WeiboUser> findWeiboUserByNickNames(Set<String> nicknames);
	
	/**
	 * 添加用户信息（使用原有大V彩帐号第一次登陆大V彩微博，补充 昵称）
	 * @param weiboUser
	 * @return
	 */
	AccountDealResult addUserInfo(WeiboUser weiboUser);

	/**
	 * 更新用户信息
	 * @param weiboUser
	 * @return
	 */
	AccountDealResult updateUserInfo(WeiboUser weiboUser);

	Map<Long, WeiboUser> findWeiboUserByWeiboUids(Set<Long> uids);
	
	String getWeiboUidByLotteryUid(String lotteryUid);
	
	AccountDealResult cancelSinaWeiboBind(WeiboUser weiboUser);
	
	AccountDealResult cancelQQBind(WeiboUser weiboUser);
	
	WeiboUser getWeiboUserByEmail(String email);

	boolean isEmailUnique(String email);

	boolean isMobileUnique(String mobile);
	
	WeiboUser findWeiboUserByUsername(String username);

	AccountDealResult bindWeixin(WeiboUser weiboUser, WeixinTokenModel weixinTokenModel);

	AccountDealResult cancelWeixinBind(WeiboUser user);

	AccountDealResult cancelAlipayBind(WeiboUser user);

	WeiboUser getWeiboUserByUserId(Long id);
	
	/**
	 * 更新用户第三方信息
	 * @param src  "weixin","qq","weibo"
	 * @param userPO
	 */
	void updateOpenInfo(String src,UserPO userPO);
}

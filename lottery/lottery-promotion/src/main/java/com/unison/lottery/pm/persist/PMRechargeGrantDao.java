
package com.unison.lottery.pm.persist;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.unison.lottery.pm.data.CountResult;
import com.unison.lottery.pm.data.OrderColorGrant;
import com.unison.lottery.pm.data.RechargeResult;
import com.xhcms.lottery.commons.persist.dao.UserDao;
import com.xhcms.lottery.commons.persist.entity.PromotionPO;
import com.xhcms.lottery.commons.persist.entity.UserPO;

/**
 * @author yongli zhu
 *
 */
public interface PMRechargeGrantDao extends UserDao {
	public List<UserPO> getUserListFromUserInfo(Date from, Date to, Long promId);
	
	public List<UserPO> getNewRegistUser(Date from, Date to, Long promId);
	
	public List<UserPO> getNewUserForGrantVoucher(Date from, Date to, Long grantTypeId);
	
	public List<UserPO> getUserListById(Collection<Long> ids);
	
	public List<Long> insertGrant(List<UserPO> userList,PromotionPO promotionPO);
	
	public void insertRechargeGrant(List<UserPO> userList,PromotionPO promotionPO);
	
	public UserPO getVerifyMobileById(Long userId, Date from, Date to);
	
	public Long insertWapGrant(UserPO userPO, PromotionPO promotionPO, BigDecimal grantAmount);
	
	public void insertWapRechargeGrant(UserPO userPO, PromotionPO promotionPO, BigDecimal grantAmount);
		
	public List<CountResult> countRegistNum(String sdata);
	
	public List<Long> getUserIdByMobile(String mobile);
	
	/**
	 * 取得符合赠送购彩金条件的用户信息
	 * @return
	 */
	public List<RechargeResult> getUserFromColorRingGrant();
	
	/**
	 * 获取“订彩铃送彩金活动”没有进行赠送的手机号用户
	 * @return
	 */
	public List<OrderColorGrant> getNoGrantMobileList(int firstIndex, int maxResults);
	
	/**
	 * 查询指定日期的手机号“订彩铃送彩金活动”已经赠送的次数
	 * @param mobile
	 * @return
	 */
	public int findGrantedCountByMobile(OrderColorGrant orderColorGrant);
	
	/**
	 * 订购彩铃赠送彩金活动
	 * @param userList
	 * @param promotionPO
	 * @return
	 */
	public List<Long> insertColorRingGrant(List<RechargeResult> results, PromotionPO promotionPO);
	
	/**
	 * 更新订购彩铃赠送彩金状态
	 * @param results
	 */
	public void updateColorRingGrantStatus(List<String> tradeNoList);
	
	/**
	* 更新订购彩铃赠送彩金状态
	* @param results
	*/
	public void updateColorRingGrantStatus(String tradeNo);
	
	/**
	 * 更新订购彩铃赠送彩金状态
	 * @param results
	 */
	public void updateColorRingGrantStatus(String tradeNo, String grantStatus);
	
	/**
	 * 更新订购彩铃赠送彩金的赠送人ID
	 * @param tradeNo
	 */
	public void updateColorRingGrantUserId(String tradeNo, Long userId);
}

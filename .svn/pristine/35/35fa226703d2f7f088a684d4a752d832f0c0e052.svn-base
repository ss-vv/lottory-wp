package com.xhcms.lottery.commons.persist.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.xhcms.commons.lang.Assert;
import com.xhcms.commons.lang.Paging;
import com.xhcms.lottery.commons.data.Account;
import com.xhcms.lottery.commons.data.MayPMVoucher;
import com.xhcms.lottery.commons.data.VoucherUser;
import com.xhcms.lottery.commons.persist.dao.BetSchemeDao;
import com.xhcms.lottery.commons.persist.dao.PMGrantVoucherByDayDao;
import com.xhcms.lottery.commons.persist.dao.PMGrantVoucherDao;
import com.xhcms.lottery.commons.persist.dao.UserDao;
import com.xhcms.lottery.commons.persist.dao.VoucherDao;
import com.xhcms.lottery.commons.persist.dao.VoucherUserDao;
import com.xhcms.lottery.commons.persist.entity.GrantTypePO;
import com.xhcms.lottery.commons.persist.entity.PMGrantVoucherByDayPO;
import com.xhcms.lottery.commons.persist.entity.PMGrantVoucherPO;
import com.xhcms.lottery.commons.persist.entity.UserPO;
import com.xhcms.lottery.commons.persist.entity.VoucherPMPO;
import com.xhcms.lottery.commons.persist.entity.VoucherPO;
import com.xhcms.lottery.commons.persist.entity.VoucherTypePO;
import com.xhcms.lottery.commons.persist.entity.VoucherUserExtendPO;
import com.xhcms.lottery.commons.persist.entity.VoucherUserPO;
import com.xhcms.lottery.commons.persist.service.GrantService;
import com.xhcms.lottery.commons.persist.service.VoucherUserService;
import com.xhcms.lottery.lang.AppCode;
import com.xhcms.lottery.lang.ValidTimeType;
import com.xhcms.lottery.lang.VoucherLimit;
import com.xhcms.lottery.lang.VoucherType;
import com.xhcms.lottery.lang.VoucherUserStatus;
import com.xhcms.lottery.utils.POUtils;

/**
 * 用户优惠卷service
 * @author Wang Lei
 *
 */
public class VoucherUserServiceImpl implements VoucherUserService {
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
    private GrantService grantBaseService;
	@Autowired
	private VoucherUserDao voucherUserDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private BetSchemeDao betSchemeDao;
	@Autowired
	private PMGrantVoucherDao pMGrantVoucherDao;
	@Autowired
	private VoucherDao voucherDao;
	@Autowired
	private PMGrantVoucherByDayDao pMGrantVoucherByDayDao;
	
	/**
	 * 分页获取有效优惠卷
	 */
	@Override
	@Transactional
	public Paging listValidByUserid(Paging paging, Long userId){
		List<VoucherUserPO> vpos = voucherUserDao.listValidByUserid(paging, userId);
		paging.setResults(POUtils.copyBeans(vpos, VoucherUser.class));
		return paging;
	}
	
	@Override
	@Transactional
	public List<VoucherUser> listValidRechargeByUserid(Long userId){
		List<VoucherUserPO> vpos = voucherUserDao.listValidRechargeByUserid(userId);
		return POUtils.copyBeans(vpos, VoucherUser.class);
	}
	
	/** 分页获取已使用优惠卷 */
	@Override
	@Transactional
	public Paging listUsedByUserid(Paging paging, Long userId){
		List<VoucherUserPO> vpos = voucherUserDao.listUsedByUserid(paging, userId);
		paging.setResults(POUtils.copyBeans(vpos, VoucherUser.class));
		return paging;
	}
	
	/** 查询已过期优惠劵 */
	@Override
	@Transactional
	public Paging listExpireByUserid(Paging paging, Long userId){
		List<VoucherUserPO> vpos = voucherUserDao.listExpireByUserid(paging, userId);
		paging.setResults(POUtils.copyBeans(vpos, VoucherUser.class));
		return paging;
	}
	
	/** 使用Web赠款优惠劵 */
	@Override
	@Transactional
	public void useWebGrantVoucher(Long voucherId, Long userId){
		this.useGrantVoucher(voucherId, userId, VoucherLimit.web.name());
	}
	
	/** 使用Web充值优惠劵 */
	@Override
	@Transactional
	public VoucherUser useWebRechargeVoucher(Long rechargeId, Long userId){
		return this.useRechargeVoucher(rechargeId, userId, VoucherLimit.web.name());
	}
	
	/** 使用Client赠款优惠劵 */
	@Override
	@Transactional
	public void useClientGrantVoucher(Long voucherId, Long userId){
		this.useGrantVoucher(voucherId, userId, VoucherLimit.client.name());
	}
	
	/** 使用Client充值优惠劵 */
	@Override
	@Transactional
	public VoucherUser useClientRechargeVoucher(Long rechargeId, Long userId){
		return this.useRechargeVoucher(rechargeId, userId, VoucherLimit.client.name());
	}
	
	@Transactional
	private void useGrantVoucher(Long voucherId, Long userId, String limit){
		logger.info("useGrantVoucher voucherId:"+voucherId+"  userId:"+userId);
		VoucherUserPO vupo = loadVoucherPO(voucherId); // 根据优惠id返回优惠劵
		isCanBeUseGrant(vupo, userId); // 检查优惠劵状态是否可用
		checkLimit(vupo, limit); // 检查优惠劵可用范围
		useVoucher(vupo); // 使用优惠劵
		grantBaseService.autoGrant(userId, vupo.getVoucher().getGrant(), vupo.getGrantType().getId(), vupo.getGrantType().getName());	// 赠款
	}
	
	@Transactional
	private VoucherUser useRechargeVoucher(Long rechargeId, Long userId, String limit){
		logger.info("useRechargeVoucher rechargeId:"+rechargeId+"  userId:"+userId);
		VoucherUserPO vupo = loadCanUseByRecharge(rechargeId); // 根据流水号返回优惠劵
		isCanBeUseRV(vupo, userId , limit); // 检查优惠劵状态是否可用
		useVoucher(vupo); // 使用优惠劵
		grantBaseService.autoGrant(userId, vupo.getVoucher().getGrant(), vupo.getGrantType().getId(), vupo.getGrantType().getName()); 	// 赠款
		logger.info("grant uId="+userId+"  amount="+vupo.getVoucher().getGrant() +" note="+vupo.getGrantType().getName());
		VoucherUser vu = new VoucherUser();
		BeanUtils.copyProperties(vupo, vu);
		return vu;
	}

	/** 锁定充值优惠劵 */
	@Override
	@Transactional
	public void lockingRechargeVoucher(Long rechargeId, Long userId, Long voucherId, BigDecimal price){
		VoucherUserPO vupo = loadVoucherPO(voucherId);
		isValidAndNotUsedRV(vupo, userId, price); // 检查充值优惠劵是否未使用
		vupo.setRechargeId(rechargeId); // 写入充值流水号
		lockingVoucher(vupo); // 锁定优惠劵
	}
	
	@Transactional
	private void lockingVoucher(VoucherUserPO vupo){
		vupo.setLockTime(new Date());
		vupo.setStatus(VoucherUserStatus.LOCKED.getValue());
	}
	
	@Transactional
	private void useVoucher(VoucherUserPO vupo){
		logger.info("useVoucher vuId="+vupo.getId());
		vupo.setServiceTime(new Date());
		vupo.setStatus(VoucherUserStatus.USED.getValue());
		voucherUserDao.save(vupo);
	}
	
	@Transactional(readOnly=true)
	private void isValidAndNotUsedRV(VoucherUserPO vupo, Long userId, BigDecimal reahangePrice) {
		checkPrice(vupo.getVoucher().getPrice(), reahangePrice); // 检查充值金额是否符合优惠劵额度
		checkUser(vupo.getUserId(), userId); // 检查此优惠劵是否属于该用户
		checkValidStatus(vupo); // 检查优惠劵状态是否是有效
		Assert.isTrue(vupo.getDeadTime().after(new Date()), AppCode.INVALID_VOUCHER_EXPIRE); // 检查优惠劵有效期
		checkType(vupo.getVoucher().getType(), VoucherType.recharge.name());  // 检查优惠劵类型
	}
	
	/** 检查网站充值优惠劵是否有效并未使用 */
	@Override
	@Transactional(readOnly=true)
	public void validWebVoucherRV(Long voucherId, Long userId, BigDecimal reahangePrice){
		VoucherUserPO vupo = loadVoucherPO(voucherId);
		checkLimit(vupo, VoucherLimit.web.name());
		isValidAndNotUsedRV(vupo, userId, reahangePrice);
	}
	
	/** 检查网站彩金优惠劵是否有效并未使用 */
	@Override
	@Transactional(readOnly=true)
	public void validWebVoucher(Long voucherId, Long userId, BigDecimal reahangePrice){
		VoucherUserPO vupo = loadVoucherPO(voucherId);
		checkLimit(vupo, VoucherLimit.web.name());
		isCanBeUseGrant(vupo, userId);
	}
	
	/** 检查客户端充值优惠劵是否有效并未使用 */
	@Override
	@Transactional(readOnly=true)
	public void validClientVoucherRV(Long voucherId, Long userId, BigDecimal reahangePrice){
		VoucherUserPO vupo = loadVoucherPO(voucherId);
		checkLimit(vupo, VoucherLimit.client.name());
		isValidAndNotUsedRV(vupo, userId, reahangePrice);
	}
	
	/** 检查客户端彩金优惠劵是否有效并未使用 */
	@Override
	@Transactional(readOnly=true)
	public void validClientVoucher(Long voucherId, Long userId, BigDecimal reahangePrice){
		VoucherUserPO vupo = loadVoucherPO(voucherId);
		checkLimit(vupo, VoucherLimit.client.name());
		isCanBeUseGrant(vupo, userId);
	}
	
	@Transactional(readOnly=true)
	private void isCanBeUseRV(VoucherUserPO vupo, Long userId, String limit){
		checkLimit(vupo, limit);
		Assert.isTrue(vupo.getLockTime().after(getLockTimeOutTime()), AppCode.INVALID_VOUCHER_EXPIRE); // 检查优惠劵有效期
		checkUser(vupo.getUserId(), userId); // 检查此优惠劵是否属于该用户
		checkStatus(vupo.getStatus(), VoucherUserStatus.LOCKED.getValue()); // 检查优惠劵状态是否是有效
		checkType(vupo.getVoucher().getType(), VoucherType.recharge.name());  // 检查优惠劵类型
	}
	
	/** 检查赠款劵是否符合真正使用条件 */
	@Transactional(readOnly=true)
	private void isCanBeUseGrant(VoucherUserPO vupo, Long userId){
		Assert.isTrue(vupo.getDeadTime().after(new Date()), AppCode.INVALID_VOUCHER_EXPIRE); // 检查优惠劵有效期
		checkUser(vupo.getUserId(), userId); // 检查此优惠劵是否属于该用户
		checkStatus(vupo.getStatus(), VoucherUserStatus.UNUSED.getValue()); // 检查优惠劵状态是否是有效
		checkType(vupo.getVoucher().getType(), VoucherType.grant.name());  // 检查优惠劵类型
	}
	
	@Transactional(readOnly=true)
	private VoucherUserPO loadVoucherPO(Long voucherId){
		Assert.notNull(voucherId, AppCode.INVALID_VOUCHER_ID);
		VoucherUserPO vupo = voucherUserDao.get(voucherId);
		Assert.notNull(vupo, AppCode.VOUCHER_INEXISTENCE);
		return vupo;
	}
	
	@Transactional(readOnly=true)
	private VoucherUserPO loadCanUseByRecharge(Long rechargeId){
		Assert.notNull(rechargeId, AppCode.RECHARGE_VOUCHER_INEXISTENCE);
		VoucherUserPO vupo = voucherUserDao.getCanUseByRechargeId(rechargeId);
		Assert.notNull(vupo, AppCode.RECHARGE_VOUCHER_INEXISTENCE);
		return vupo;
	}

	@Transactional(readOnly=true)
	private void checkPrice(BigDecimal price, BigDecimal reahangePrice){
		Assert.le(price, reahangePrice, AppCode.INVALID_VOUCHER_PRICE);
	}
	
	@Transactional(readOnly=true)
	private void checkType(String type, String targetType){
		Assert.eq(type, targetType, AppCode.INVALID_VOUCHER_TYPE);
	}
	
	/**检查使用范围*/
	private void checkLimit(VoucherUserPO vus, String limit){
		Assert.isTrue(StringUtils.isNotBlank(limit), AppCode.INVALID_VOUCHER_LIMIT);
		if(StringUtils.isBlank(vus.getVoucher().getLimit())){
			return;
		}
		Assert.eq(vus.getVoucher().getLimit(), limit, AppCode.INCONFORMITY_VOUCHER_LIMIT);
	}
	
	@Transactional(readOnly=true)
	private void checkValidStatus(VoucherUserPO vus){
		Assert.isTrue(vus.getStatus()==VoucherUserStatus.UNUSED.getValue() ||
				(vus.getStatus()==VoucherUserStatus.LOCKED.getValue() && getLockTimeOutTime().after(vus.getLockTime()) ),
				AppCode.INVALID_VOUCHER_STATUS);
	}
	
	@Transactional(readOnly=true)
	private void checkStatus(Integer status, Integer targetStatus){
		Assert.eq(status, targetStatus, AppCode.INVALID_VOUCHER_STATUS);
	}
	
	@Transactional(readOnly=true)
	private void checkUser(Long userId, Long targetUserId){
		Assert.eq(userId, targetUserId, AppCode.INVALID_VOUCHER_USER);
	}
	

	@Override
	@Transactional
	public void createVoucher(String username, Long validDay, String voucherId, Long granttypeId){
		Assert.notNull(username, AppCode.SCHEME_UNUSUAL_OPERATE);
		UserPO user = userDao.getUserByUsername(username);
		Assert.notNull(user, AppCode.INVALID_VOUCHER_USER);
		createVoucher(user.getId(), validDay, voucherId, granttypeId);
	}
	
	/**
	 * 生成优惠劵
	 */
	@Override
	@Transactional
	public void createVoucher(Long userId, Long validDay, String voucherId, Long granttypeId){
		Assert.notNull(validDay, AppCode.SCHEME_UNUSUAL_OPERATE);
		Assert.notNull(voucherId, AppCode.SCHEME_UNUSUAL_OPERATE);
		Assert.notNull(granttypeId, AppCode.SCHEME_UNUSUAL_OPERATE);
		VoucherUserExtendPO voucherUserPO = new VoucherUserExtendPO();
		voucherUserPO.setUserId(userId);
		VoucherPO voucher = new VoucherPO();
		voucher.setId(voucherId);
		voucherUserPO.setVoucher(voucher);
		GrantTypePO grantType = new GrantTypePO();
		grantType.setId(granttypeId);
		voucherUserPO.setGrantType(grantType);
		voucherUserPO.setCreateTime(new Date());
		voucherUserPO.setStatus(VoucherUserStatus.UNUSED.getValue());
		// 生成优惠劵有效期
		createLimitTime(voucherUserPO, validDay);
		voucherUserDao.save(voucherUserPO);
		logger.info("createVoucher id="+voucherUserPO.getId());
	}
	
	/**
	 * 根据活动自动生成充值优惠卷
	 */
	@Override
	@Transactional
	public int createRechargeVoucher( VoucherPMPO voupm, Account ac, Map<String,VoucherPO> voucherMaps){
		BigDecimal totalbet = ac.getTotalBet();
		int count=0;
		// 查询已有活动对应的优惠劵数
		List<String> existingVous = voucherUserDao.listUserVoucherByGrant(voupm.getGrantType().getId(), ac.getId());
		// 匹配应有的优惠卷数
		Set<String> voucherIds = computerVoucher(totalbet);
		// 计算实际该发的优惠卷
		for(String vid:voucherIds){
			// 如果有优惠卷不存在已有列表中 则新建优惠劵
			if(!existingVous.contains(vid)){ 
				VoucherUserExtendPO voucherUserPO = new VoucherUserExtendPO();
				voucherUserPO.setVoucher(voucherMaps.get(vid));
				voucherUserPO.setGrantType(voupm.getGrantType());
				voucherUserPO.setUserId(ac.getId());
				voucherUserPO.setCreateTime(new Date());
				voucherUserPO.setStatus(VoucherUserStatus.UNUSED.getValue());
				// 生成优惠卷有效期
				createLimitTimeByVouPM(voucherUserPO, voupm);
				voucherUserDao.save(voucherUserPO);
				logger.info("createVoucher id="+voucherUserPO.getId());
				count++;
			}
		}
		return count;
	}
	
	/***  计算应该得到的优惠劵数 */
	private Set<String> computerVoucher(BigDecimal totalbet){
		Set<String> voucherIds = new HashSet<String>();
		if(totalbet.compareTo(new BigDecimal(999999)) == 1 ){
			voucherIds.add("CZS_50000_5058");
		}
		if(totalbet.compareTo(new BigDecimal(99999)) == 1 ){
			voucherIds.add("CZS_10000_1000");
		}
		if(totalbet.compareTo(new BigDecimal(19999)) == 1 ){
			voucherIds.add("CZS_5000_500");
		}
		if(totalbet.compareTo(new BigDecimal(4999)) == 1 ){
			voucherIds.add("CZS_1000_100");
		}
		if(totalbet.compareTo(new BigDecimal(-1)) == 1 ){
			voucherIds.add("CZS_100_8");
		}
		return voucherIds;
	}
	
	/***  生成优惠卷有效期 */
	private void createLimitTimeByVouPM(VoucherUserPO vupo,VoucherPMPO voupm){
		if( voupm.getValidTimeType().equals(ValidTimeType.fromCreate.name())){
			Date now = new Date();
			Calendar deadTime=Calendar.getInstance();
			deadTime.setTime(now);
			deadTime.add(Calendar.DAY_OF_MONTH, + voupm.getFromCreateday());
			vupo.setEffectTime(now);
			vupo.setDeadTime(deadTime.getTime());
		}else if(voupm.getValidTimeType().equals(ValidTimeType.limit.name())){
			vupo.setEffectTime(voupm.getEffectTime());
			vupo.setDeadTime(voupm.getDeadTime());
		}
	}
	
	/***  生成优惠卷有效期 */
	private void createLimitTime(VoucherUserPO vupo,Long validDay){
		Assert.notNull(validDay, AppCode.SCHEME_UNUSUAL_OPERATE);
		Assert.gt(validDay, 0, AppCode.SCHEME_UNUSUAL_OPERATE);
		Date now = new Date();
		Calendar deadTime=Calendar.getInstance();
		deadTime.setTime(now);
		deadTime.add(Calendar.DAY_OF_MONTH, + validDay.intValue());
		vupo.setEffectTime(now);
		vupo.setDeadTime(deadTime.getTime());
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<VoucherUserExtendPO> list(Paging paging, String voucherUserViewStatus,String username,Long id,String voucherId,Long granttypeId){
		List<VoucherUserExtendPO> vues = voucherUserDao.list(paging, voucherUserViewStatus, username, id, voucherId, granttypeId);
		paging.setResults(vues);
		return vues;
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<VoucherUserExtendPO> list(Paging paging, String voucherUserViewStatus , VoucherUserExtendPO voucher){
		List<VoucherUserExtendPO> vues = voucherUserDao.list(paging, voucherUserViewStatus, voucher);
		paging.setResults(vues);
		return vues;
	}
	
	/** 返回锁定超时时间 */
	private Date getLockTimeOutTime(){
		Calendar calendarLock=Calendar.getInstance();
        calendarLock.setTime(new Date());
        calendarLock.add(Calendar.MINUTE, -10);
        return calendarLock.getTime();
	}
	
	@Override
	@Transactional(readOnly=true)
	public MayPMVoucher getMayPMVoucherGrant(Long userId) {
		MayPMVoucher mayPMVoucher = new MayPMVoucher();
		//第一周
		String startStr = "2013-07-01 00:00:00";
		String endStr = "2013-07-07 23:59:59";
		Date start = getDateFromStr(startStr);
		Date end = getDateFromStr(endStr);
		Long pmId = 4L;
		Long pmWeek = 1L;
		List<Object[]> bets = betSchemeDao.findBetSuccessNote(userId, start, end);
		if(null != bets && bets.size() == 1) {
			Object[] obj = bets.get(0);
			Integer sumAmount = new Integer(obj[1].toString())*2;
			mayPMVoucher.setFwAmount(sumAmount);
			PMGrantVoucherPO pMGrantVoucherPO = pMGrantVoucherDao.find(userId, pmId, pmWeek);
			if(null != pMGrantVoucherPO) {
				String voucherId = pMGrantVoucherPO.getVoucherId();
				VoucherPO voucherPO = voucherDao.get(voucherId);
				mayPMVoucher.setFwVoucher(voucherPO.getName());
			}
		}
		//第二周
		startStr = "2013-07-08 00:00:00";
		endStr = "2013-07-14 23:59:59";
		start = getDateFromStr(startStr);
		end = getDateFromStr(endStr);
		pmWeek = 2L;
		bets = betSchemeDao.findBetSuccessNote(userId, start, end);
		if(null != bets && bets.size() == 1) {
			Object[] obj = bets.get(0);
			Integer sumAmount = new Integer(obj[1].toString())*2;
			mayPMVoucher.setSwAmount(sumAmount);
			PMGrantVoucherPO pMGrantVoucherPO = pMGrantVoucherDao.find(userId, pmId, pmWeek);
			if(null != pMGrantVoucherPO) {
				String voucherId = pMGrantVoucherPO.getVoucherId();
				VoucherPO voucherPO = voucherDao.get(voucherId);
				mayPMVoucher.setSwVoucher(voucherPO.getName());
			}
		}
	
		return mayPMVoucher;
	}
	
	private Date getDateFromStr(String dateStr) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return date;
	}
	
	@Override
	@Transactional
	public void createVoucerAndRecord(Long userId , String voucherId , Long granttypeId, Date day){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		this.createVoucher(userId, 31L, voucherId, granttypeId);
		PMGrantVoucherByDayPO grantRecord = new PMGrantVoucherByDayPO();
		grantRecord.setId(sdf.format(day)+"_"+userId);
		pMGrantVoucherByDayDao.save(grantRecord);
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<VoucherTypePO> listAllVoucherType(){
		return voucherUserDao.loadAllVoucherType();
	}
}

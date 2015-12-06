package com.xhcms.lottery.commons.persist.dao.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.xhcms.commons.lang.Paging;
import com.xhcms.commons.persist.hibernate.DaoImpl;
import com.xhcms.lottery.commons.persist.dao.UserDao;
import com.xhcms.lottery.commons.persist.entity.UserPO;
import com.xhcms.lottery.lang.EntityStatus;
import com.xhcms.lottery.utils.DateUtils;

/**
 * @author jiajiancheng
 * 
 */
public class UserDaoImpl extends DaoImpl<UserPO> implements UserDao {

    private static final long serialVersionUID = 9144023387209973966L;

    public UserDaoImpl() {
        super(UserPO.class);
    }

    @Override
    public UserPO getUserByUsername(String username) {
        return (UserPO) createCriteria().add(Restrictions.eq("username", username)).uniqueResult();
    }

    @Override
    public UserPO getUserByEmail(String email) {
        return (UserPO) createCriteria().add(Restrictions.eq("email", email)).uniqueResult();
    }

    
    
    
    @SuppressWarnings("unchecked")
	private List<UserPO> getUserByMobileAndValidStatus(String mobile,
			int validStatus, Set<Integer> bindMobileStatus) {
		return createCriteria().add(Restrictions.eq("mobile", mobile))
				.add(Restrictions.eq("status", validStatus))
				.add(Restrictions.in("verifyStatus", bindMobileStatus)).list();
	}
    
    @Override
    public UserPO getUserByVerifyedMobile(String mobile) {
    	Set<Integer> statusSet = new HashSet<Integer>();
    	statusSet.add(EntityStatus.VERIFY_MOBILE);
    	statusSet.add(EntityStatus.VERIFY_EMAIL_MOBILE);
    	return (UserPO) createCriteria().add(Restrictions.eq("mobile", mobile)).
    			add(Restrictions.in("verifyStatus", statusSet)).uniqueResult();
    }

	@Override
	public UserPO findById(Long id) {
		List<Long> l = new ArrayList<Long>();
		l.add(id);
		List<UserPO> u = this.find(l);
		if(u.size() > 0){
			return u.get(0);
		} else {
			return null;
		}
	}

    @SuppressWarnings("unchecked")
    @Override
    public List<UserPO> find(Collection<Long> ids) {
        return createCriteria().add(Restrictions.in("id", ids)).list();
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public UserPO findByOpenUid(String openUid, String field){
    	List<UserPO> list = createCriteria().add(Restrictions.eq(field, openUid)).list();
    	if(list.size() > 0){
    		return list.get(0);
    	} else {
    		return null;
    	}
    }
	@Override
	public List<UserPO> find(Paging paging, Date from, Date to, int status, String username, String ip, String idNumber) {
		PagingQuery<UserPO> q = pagingQuery(paging);
		
		if(from != null){
		    q.add(Restrictions.ge("createdTime", from));
		}
		if(to != null){
		    q.add(Restrictions.lt("createdTime", to));
		}
		if(status != -1){
		    q.add(Restrictions.eq("status", status));
		}
		if(StringUtils.isNotBlank(username)){
		    q.add(Restrictions.like("username", username, MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotBlank(ip)){
		    q.add(Restrictions.like("ip", ip, MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotBlank(idNumber)){
		    q.add(Restrictions.like("idNumber", idNumber, MatchMode.ANYWHERE));
		}
		
		return q.list();
	}
	
	@Override
	public List<UserPO> find(Paging paging, Date from, Date to, int status, String username, String ip, String idNumber, String mobile, String nickName) {
		PagingQuery<UserPO> q = pagingQuery(paging);
		
		if(from != null){
		    q.add(Restrictions.ge("createdTime", from));
		}
		if(to != null){
		    q.add(Restrictions.lt("createdTime", to));
		}
		if(status != -1){
		    q.add(Restrictions.eq("status", status));
		}
		if(StringUtils.isNotBlank(username)){
		    q.add(Restrictions.like("username", username, MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotBlank(ip)){
		    q.add(Restrictions.like("ip", ip, MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotBlank(idNumber)){
		    q.add(Restrictions.like("idNumber", idNumber, MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotBlank(mobile)){
		    q.add(Restrictions.like("mobile", mobile, MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotBlank(nickName)){
			q.add(Restrictions.like("nickName", nickName, MatchMode.ANYWHERE));
		}
		
		return q.list();
	}

	@Override
	public void updateQQInfo(long id, String uid,String token){
		createQuery("update UserPO set qqConnectUid=:uid,qqConnectToken=:token where id=:id")
			.setString("uid", uid).setString("token", token).setLong("id", id).executeUpdate();
	}
	
	@Override
	public void updateSinaInfo(long id, String uid,String token){
		createQuery("update UserPO set sinaWeiboUid=:uid,sinaWeiboToken=:token where id=:id")
			.setString("uid", uid).setString("token", token).setLong("id", id).executeUpdate();
	}
	
	@Override
	public void updateAlipayInfo(long id, String uid,String token){
		createQuery("update UserPO set alipayUid=:uid,alipayToken=:token where id=:id")
			.setString("uid", uid).setString("token", token).setLong("id", id).executeUpdate();	
	}
	
	@Override
	public void updateWeixinInfo(long id, String uid,String token){
		createQuery("update UserPO set weixinPCUid=:uid,weixinToken=:token where id=:id")
			.setString("uid", uid).setString("token", token).setLong("id", id).executeUpdate();
	}
	
	@Override
	public void updateWeixinInfo(long id, String weixinUid,String token,
			String weixinUnionId,String weixinPCUid){
		createQuery("update UserPO set weixinUid=:weixinUid,"
				+ " weixinToken=:token,"
				+ " weixinUnionId=:weixinUnionId,"
				+ " weixinPCUid=:weixinPCUid where id=:id")
		.setString("weixinUid", weixinUid)
		.setString("token", token)
		.setString("weixinUnionId", weixinUnionId)
		.setString("weixinPCUid", weixinPCUid)
		.setLong("id", id)
		.executeUpdate();
	}
	
	@Override
	public void updateHeadImageUrl(long id, String url){
		createQuery("update UserPO set headImageURL=:url where id=:id")
    		.setString("url", url).setLong("id", id).executeUpdate();
	}
	
	@Override
	public void updateNickname(long id, String nickname){
		createQuery("update UserPO set nickName=:s where id=:id")
        	.setString("s", nickname).setLong("id", id).executeUpdate();
	}
	
    @Override
    public void updateStatus(Collection<Long> ids, int status) {
        if(status == EntityStatus.NORMAL || status == EntityStatus.LOCKED){
            createQuery("update UserPO set status=:s where id in (:ids)")
                .setInteger("s", status).setParameterList("ids", ids).executeUpdate();
        }        
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<UserPO> getValidUsersByIdNumber(String idnumber) {
		return  createCriteria()
				.add(Restrictions.eq("idNumber", idnumber))
				.add(Restrictions.eq("status", EntityStatus.USER_VALID))
				.list();
	}

	@Override
	public List<UserPO> getValidUsersByBindedMobile(String mobile) {
		Set<Integer> bindMobileStatus=new HashSet<Integer>();
		bindMobileStatus.add(EntityStatus.VERIFY_MOBILE);
		bindMobileStatus.add(EntityStatus.VERIFY_EMAIL_MOBILE);
		return this.getUserByMobileAndValidStatus(mobile, EntityStatus.USER_VALID,bindMobileStatus);
	}

	@Override
	public List<UserPO> getValidUsersByBindedEmail(String email) {
		Set<Integer> bindEmailStatus=new HashSet<Integer>();
		bindEmailStatus.add(EntityStatus.VERIFY_EMAIL);
		bindEmailStatus.add(EntityStatus.VERIFY_EMAIL_MOBILE);
		return this.getUserByEmailAndValidStatus(email, EntityStatus.USER_VALID, bindEmailStatus);
	}

	@SuppressWarnings("unchecked")
	private List<UserPO> getUserByEmailAndValidStatus(String email,
			int userValid, Set<Integer> bindEmailStatus) {
		return createCriteria().add(Restrictions.eq("email", email))
				.add(Restrictions.eq("status", userValid))
				.add(Restrictions.in("verifyStatus", bindEmailStatus)).list();
	}

	@Override
	public void updateLastLoginStatus(UserPO user) {
		if(null!=user){
			String hql="update UserPO " +
					"set lastLoginTime=:lastLoginTime ,loginNumber=loginNumber+1 " +
					"where id=:id";
			Date now=new Date();
			Timestamp timestamp=new Timestamp(now.getTime());
			createQuery(hql)
			.setParameter("lastLoginTime", timestamp)
			.setParameter("id", user.getId())
			.executeUpdate();
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean isExistByNickName(String nickName) {
		if(StringUtils.isNotBlank(nickName)){
			String hql="from UserPO u where u.nickName = '" + nickName + "'";
			List<UserPO> userPOs = createQuery(hql).list();
			if(userPOs != null && !userPOs.isEmpty()){
				return true;
			}
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public UserPO getUserByUidAndType(String uid, String type) {
		Criteria c = null;
		if(StringUtils.equals(type, "0")){
			 c = createCriteria().add(Restrictions.eq("sinaWeiboUid", uid));
		}else if (StringUtils.equals(type, "1")) {
			 c = createCriteria().add(Restrictions.eq("weixinUid", uid));
		}else if (StringUtils.equals(type, "2")) {
			 c = createCriteria().add(Restrictions.eq("qqConnectUid", uid));
		}
		List<UserPO> userPOs = c.list();
		if(userPOs != null && !userPOs.isEmpty()){
			return userPOs.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public UserPO getUserByPhoneNumberAndStatus0(String phoneNumber) {
		List<UserPO> udPos = createCriteria().add(Restrictions.eq("mobile", phoneNumber))
				.add(Restrictions.eq("status", EntityStatus.USER_VALID)).list();
		if(udPos != null && !udPos.isEmpty()){
			return udPos.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserPO> findUsersByIds(List<Long> userIds) {
		Criteria c = createCriteria();
		if (null != userIds && !userIds.isEmpty()) {
			c.add(Restrictions.in("id", userIds));
		}
		
		return c.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserPO> findByFieldIsNotNull(String field) {
		String hql = "from UserPO where "+field+" is not null ";
		return createQuery(hql).list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findNewRegUserGroupByPid(Date from, Date to) {
		String sql = 
		"select date_format(created_time, '%Y-%m-%d') as '日期', " + 
		"count(id) as '新注册用户数', pid "+
		"from lt_user "+
		"where " +
		"	 date_format(created_time, '%Y-%m-%d %H-%i-%s') " +
		"	 between ? and ? " +
		"group by date_format(created_time, '%Y-%m-%d'), pid " +
		"order by created_time desc";
		
		SQLQuery sqlQuery = createSQLQuery(sql);
		
		String fromDate = DateUtils.format(from);
		String toDate = DateUtils.format(to);
		
		sqlQuery.setParameter(0, fromDate);
		sqlQuery.setParameter(1, toDate);
		
		return sqlQuery.list();
	}
	@Override
	public void updateUserAllow(long id,int allow){
		this.createQuery("update UserPO u set u.allowSeeImportantContentInClient=:allow where u.id=:id")
		.setParameter("allow", allow).setParameter("id", id).executeUpdate();
	}
}

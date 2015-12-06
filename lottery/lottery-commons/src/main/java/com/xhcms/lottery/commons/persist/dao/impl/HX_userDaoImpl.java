package com.xhcms.lottery.commons.persist.dao.impl;

import java.io.Serializable;
import java.util.List;


import org.apache.commons.lang.StringUtils;
import org.hibernate.Session; 
import org.springframework.transaction.annotation.Transactional;

import com.xhcms.commons.persist.hibernate.DaoImpl;
import com.xhcms.lottery.commons.persist.dao.HX_userDao;
import com.xhcms.lottery.commons.persist.entity.HX_userPO;

@Transactional
public class HX_userDaoImpl extends DaoImpl<HX_userPO> implements HX_userDao {
 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public HX_userDaoImpl() {
        super(HX_userPO.class);
    }

	@Override
    @SuppressWarnings("unchecked")
	public HX_userPO findHx_userByUserPOId(String userId) {
		if(StringUtils.isNotBlank(userId)){
			List<HX_userPO> HXuserPOs = createQuery("From HX_userPO  p where p.userId = " + userId).list();
			if(HXuserPOs != null && !HXuserPOs.isEmpty()){
				return HXuserPOs.get(0);
			}
		}
		return null;
	}
    

	@Override
	public Long createHX_user(HX_userPO hx_userPO) {
		Session session = getSessionFactory().getCurrentSession();
		Serializable id = session.save(hx_userPO);
		return Long.parseLong(id.toString());
	}

	@Override
	@SuppressWarnings("unchecked")
	public HX_userPO findHx_userByUserPOName(String username) {
		if(StringUtils.isNotBlank(username)){
			List<HX_userPO> HXuserPOs = createQuery("From HX_userPO  p where p.hx_username = '" + username + "'").list();
			if(HXuserPOs != null && !HXuserPOs.isEmpty()){
				return HXuserPOs.get(0);
			}
		}
		return null;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<HX_userPO> findHx_userByUserPONames(List<String> usernames) {
		if(usernames != null && !usernames.isEmpty()){
			String hql = "from HX_userPO  p where p.hx_username IN (:usernames)";
			List<HX_userPO> HXuserPOs = createQuery(hql).setParameterList("usernames", usernames).list();
			if(HXuserPOs != null && !HXuserPOs.isEmpty()){
				return HXuserPOs;
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public HX_userPO findHx_userByNickName(String name) {
		if(StringUtils.isNotBlank(name)){
			List<HX_userPO> HXuserPOs = createQuery("From HX_userPO  p where p.nickname =  '" + name + "'").list();
			if(HXuserPOs != null && !HXuserPOs.isEmpty()){
				return HXuserPOs.get(0);
			}
		}
		return null;
	}

	@Override
	public void updateHX_user(HX_userPO hx_userPO) {
		String  hql =  "update HX_userPO p set p.nickname=:nickname,p.hx_username=:hx_username,p.hx_password =:hx_password  where p.userId=:userId";
		createQuery(hql).setParameter("nickname", hx_userPO.getNickname())
			.setParameter("hx_username", hx_userPO.getHx_username())
			.setParameter("hx_password", hx_userPO.getHx_password())
			.setParameter("userId", hx_userPO.getUserId()).executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object[] findHxUserByUsername(String useranme) {
		String  sql =  "select u.nickName,u.headImageURL,u.id  from HX_userPO p , UserPO u where p.userId = u.id and p.hx_username=:hx_username";
		List<Object[]> objects = createQuery(sql).setParameter("hx_username", useranme).list();
		return objects != null && !objects.isEmpty() ? objects.get(0) : null;
	}
}

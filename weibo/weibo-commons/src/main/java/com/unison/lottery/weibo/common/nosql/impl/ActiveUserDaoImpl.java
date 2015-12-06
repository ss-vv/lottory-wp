package com.unison.lottery.weibo.common.nosql.impl;

import org.springframework.stereotype.Repository;
import com.unison.lottery.weibo.common.nosql.ActiveUserDao;
import com.unison.lottery.weibo.data.WeiboMsg;
@Repository
public class ActiveUserDaoImpl extends BaseDaoImpl<WeiboMsg> implements ActiveUserDao{

}

package com.unison.lottery.weibo.data.service.store.persist.dao;

import com.xhcms.commons.persist.Dao;

/**
 * 扩展星汇的 DAO 接口
 * 
 * @author Yang Bo
 * @param <PO>
 */
public interface BasicDao<PO> extends Dao<PO> {
	/**
	 * flush session 并且将 po 从session中去掉，避免"different object with the same identifier value was already associated with the session."问题。 
	 * @param po 要从session去掉的 PO 对象。
	 */
	void flushAndEvict(Object po);
}

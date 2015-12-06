package com.xhcms.lottery.admin.persist.service;

import java.util.List;

import com.xhcms.lottery.commons.data.PassType;

/**
 * <p>Title: 过关方式管理</p>
 * <p>Description: </p>
 * <p>Copyright：Copyright (c) 2011</p>
 * <p>Company：XingHui Spirit (Beijing) Technical Co.,Ltd.</p>
 * 
 * @author jiajiancheng
 * @version 1.0.0
 */ 
public interface PassTypeService {

    List<PassType> list();
    
    void modify(String playId, String[] passTypeIds);

}

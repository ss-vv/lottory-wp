package com.xhcms.lottery.commons.persist.service;

import java.util.Collection;
import java.util.Date;

import com.xhcms.commons.lang.Paging;
import com.xhcms.lottery.commons.data.IdCard;

/**
 * <p>Title: 真实姓名、身份证号修改、提款密码找回</p>
 * <p>Description: </p>
 * <p>Copyright：Copyright (c) 2011</p>
 * <p>Company：XingHui Spirit (Beijing) Technical Co.,Ltd.</p>
 * 
 * @author jiajiancheng
 * @version 1.0.0
 */
public interface IdCardService {

    /**
     * 添加新身份证记录
     * @param idCard
     */
    void add(IdCard idCard);

    /**
     * 查询上传的身份证列表
     * @param paging
     * @param username 
     * @param status
     * @param type
     * @param from
     * @param to
     * @return
     */
    void list(Paging paging, String username, int status, int type, Date from, Date to);

    /**
     * 通过
     * @param id
     * @param adminId
     * @param admin
     */
    void pass(Collection<Long> id, Long adminId, String admin);

    /**
     * 未通过
     * @param id
     * @param adminId
     * @param admin
     */
    void unPass(Collection<Long> id, Long adminId, String admin);

    IdCard get(Long id);




}

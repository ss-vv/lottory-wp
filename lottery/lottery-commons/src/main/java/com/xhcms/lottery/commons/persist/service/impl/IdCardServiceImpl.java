package com.xhcms.lottery.commons.persist.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.xhcms.commons.lang.Paging;
import com.xhcms.lottery.commons.data.IdCard;
import com.xhcms.lottery.commons.persist.dao.IdCardDao;
import com.xhcms.lottery.commons.persist.entity.IdCardPO;
import com.xhcms.lottery.commons.persist.service.IdCardService;
import com.xhcms.lottery.lang.EntityStatus;

public class IdCardServiceImpl implements IdCardService {

    @Autowired
    private IdCardDao idCardDao;

    @Override
    @Transactional
    public void add(IdCard idCard) {
        IdCardPO po = new IdCardPO();
        BeanUtils.copyProperties(idCard, po);
        idCardDao.save(po);
    }

    @Override
    @Transactional(readOnly = true)
    public void list(Paging paging, String username, int status, int type, Date from, Date to) {
        List<IdCardPO> list = idCardDao.find(paging, username, status, type, from, to);
        List<IdCard> result = new ArrayList<IdCard>();
        for (IdCardPO po : list) {
            result.add(toVO(po));
        }
        paging.setResults(result);
    }

    @Override
    @Transactional
    public void pass(Collection<Long> id, Long adminId, String admin) {
        idCardDao.updateStatus(id, EntityStatus.ID_CARD_PASS, adminId, admin);
    }

    @Override
    @Transactional
    public void unPass(Collection<Long> id, Long adminId, String admin) {
        idCardDao.updateStatus(id, EntityStatus.ID_CARD_UNPASS, adminId, admin);
    }

    @Override
    @Transactional
    public IdCard get(Long id) {
        IdCardPO po = idCardDao.get(id);
        return toVO(po);
    }

    private IdCard toVO(IdCardPO po) {
        IdCard ic = new IdCard();
        BeanUtils.copyProperties(po, ic);
        return ic;
    }

}

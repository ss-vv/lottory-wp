package com.xhcms.lottery.admin.persist.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.xhcms.lottery.admin.persist.service.PassTypeService;
import com.xhcms.lottery.commons.data.PassType;
import com.xhcms.lottery.commons.persist.dao.PassTypeDao;
import com.xhcms.lottery.commons.persist.dao.PlayDao;
import com.xhcms.lottery.commons.persist.entity.PassTypePO;
import com.xhcms.lottery.commons.persist.entity.PlayPO;

public class PassTypeServiceImpl implements PassTypeService {

    @Autowired
    private PassTypeDao passTypeDao;

    @Autowired
    private PlayDao playDao;

    @Override
    @Transactional(readOnly = true)
    public List<PassType> list() {
        List<PassTypePO> result = passTypeDao.list();
        List<PassType> list = new ArrayList<PassType>(result.size());
        for (PassTypePO po : result) {
            list.add(toVO(po));
        }
        return list;
    }

    @Override
    @Transactional
    public void modify(String playId, String[] passTypeIds) {
        PlayPO play = playDao.getWithPassType(playId);
        if (play != null) {
            List<PassTypePO> types = new ArrayList<PassTypePO>(passTypeIds.length);
            for (String id : passTypeIds) {
                PassTypePO po = new PassTypePO();
                po.setId(id);
                types.add(po);
            }
            play.setPassTypes(types);
        }
    }

    private PassType toVO(PassTypePO po) {
        PassType pt = new PassType();
        BeanUtils.copyProperties(po, pt);
        return pt;
    }

}

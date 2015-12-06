package com.xhcms.lottery.admin.persist.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.xhcms.lottery.admin.persist.service.PlayOptionService;
import com.xhcms.lottery.commons.data.PlayOption;
import com.xhcms.lottery.commons.persist.dao.PlayOptionDao;
import com.xhcms.lottery.commons.persist.entity.PlayOptionPO;

public class PlayOptionServiceImpl implements PlayOptionService {

    private static final String[] IGNORE = new String[] { "id", "playId" };

    @Autowired
    private PlayOptionDao playOptionDao;

    @Override
    @Transactional(readOnly = true)
    public List<PlayOption> list(String playId) {
        List<PlayOptionPO> result = playOptionDao.find(playId);
        List<PlayOption> list = new ArrayList<PlayOption>();
        for (PlayOptionPO po : result) {
            list.add(toOption(po));
        }
        return list;
    }

    @Override
    @Transactional
    public void add(PlayOption option) {
        PlayOptionPO po = playOptionDao.get(option.getId());
        if (po == null) {
            po = new PlayOptionPO();
            BeanUtils.copyProperties(option, po);
            playOptionDao.save(po);
        } else {
            BeanUtils.copyProperties(option, po, IGNORE);
        }
    }

    @Override
    @Transactional
    public void remove(Long playOptionId) {
        playOptionDao.deleteById(playOptionId);
    }

    private PlayOption toOption(PlayOptionPO po) {
        PlayOption o = new PlayOption();
        BeanUtils.copyProperties(po, o);
        if (o.getNote() == null) {
            o.setNote("");
        }
        return o;
    }

}

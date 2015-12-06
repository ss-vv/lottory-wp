package com.xhcms.lottery.admin.persist.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.xhcms.lottery.admin.persist.service.PlayService;
import com.xhcms.lottery.commons.data.Play;
import com.xhcms.lottery.commons.persist.dao.PlayDao;
import com.xhcms.lottery.commons.persist.entity.PlayPO;

public class PlayServiceImpl implements PlayService {

    @Autowired
    private PlayDao playDao;

    @Override
    @Transactional(readOnly = true)
    public List<Play> list(String lottery) {

        List<PlayPO> list = playDao.find(lottery);
        List<Play> result = new ArrayList<Play>(list.size());
        for (PlayPO po : list) {
            result.add(toPlay(po));
        }
        return result;
    }

    @Override
    @Transactional
    public void add(Play play) {
        PlayPO po = playDao.get(play.getId());
        if (po == null) {
            po = new PlayPO();
            BeanUtils.copyProperties(play, po);
            playDao.save(po);
        } else {
            BeanUtils.copyProperties(play, po, new String[] { "passTypes" });
        }
    }

    @Override
    @Transactional
    public void remove(String id) {
        playDao.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Play getWithPassType(String playId) {
        PlayPO playPO = playDao.getWithPassType(playId);
        Play play = new Play();
        if (playPO != null) {
            BeanUtils.copyProperties(playPO, play);
        }
        return play;
    }

    private Play toPlay(PlayPO po) {
        Play play = new Play();
        BeanUtils.copyProperties(po, play, new String[] { "passTypes" });
        return play;
    }

}

/**
 * 
 */
package com.xhcms.lottery.commons.persist.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.xhcms.lottery.commons.data.PassType;
import com.xhcms.lottery.commons.data.Play;
import com.xhcms.lottery.commons.persist.dao.PlayDao;
import com.xhcms.lottery.commons.persist.entity.PassTypePO;
import com.xhcms.lottery.commons.persist.entity.PlayPO;
import com.xhcms.lottery.commons.persist.service.PlayService;

/**
 * @author Bean.Long
 *
 */
public class PlayServiceImpl implements PlayService {
	@Autowired
	private PlayDao playDao;

    @Override
    @Transactional(readOnly = true)
    public Play get(String playId) {
        PlayPO po = playDao.get(playId);
        Play p = new Play();
        if (po != null) {
            BeanUtils.copyProperties(po, p);
        }
        return p;
    }

	@Override
	@Transactional(readOnly = true)
	public List<Play> listAllPlays() {
		List<PlayPO> playPOs = playDao.list();
		
		List<Play> plays = new ArrayList<Play>();
		for(PlayPO po : playPOs) {
			Play play = new Play();
			BeanUtils.copyProperties(po, play);
			plays.add(play);
		}
		
		return plays;
	}
	

    @Override
    @Transactional(readOnly = true)
    public List<Play> listPlay(String lotteryId) {
        List<Play> list = new ArrayList<Play>();
        List<PlayPO> result = playDao.find(lotteryId);
        for (PlayPO po : result) {
            Play play = new Play();
            BeanUtils.copyProperties(po, play);
            list.add(play);
        }
        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PassType> listPassType(String playId) {
        PlayPO ppo = playDao.getWithPassType(playId);
        List<PassType> results = new ArrayList<PassType>(64);
        if (ppo != null) {
            List<PassTypePO> pos = ppo.getPassTypes();
            for (PassTypePO po : pos) {
                PassType pt = new PassType();
                pt.setId(po.getId());
                pt.setName(po.getName());
                pt.setNote(po.getNote());
                results.add(pt);
            }
        }
        return results;
    }
    
	@Override
	@Transactional(readOnly = true)
    public List<Play> listCustomMadePlays() {
		//查询定制合买跟单玩法，排除江西11选5的玩法
		String lotteryId = "JX11";
		List<PlayPO> playPOs = playDao.findCustomMadePlay(lotteryId);
		
		List<Play> plays = new ArrayList<Play>();
		for(PlayPO po : playPOs) {
			Play play = new Play();
			BeanUtils.copyProperties(po, play);
			plays.add(play);
		}
		
		return plays;
    }
}

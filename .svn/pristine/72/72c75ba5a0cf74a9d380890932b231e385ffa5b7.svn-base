package com.xhcms.lottery.commons.persist.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xhcms.lottery.commons.data.BetPartner;
import com.xhcms.lottery.commons.data.BetRecord;
import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.persist.dao.BetPartnerDao;
import com.xhcms.lottery.commons.persist.dao.BetSchemeDao;
import com.xhcms.lottery.commons.persist.entity.BetPartnerPO;
import com.xhcms.lottery.commons.persist.entity.BetSchemePO;
import com.xhcms.lottery.commons.persist.service.BetPartnerService;
import com.xhcms.lottery.utils.ResultTool;

@Service
public class BetPartnerServiceImpl implements BetPartnerService {

	@Autowired
	private BetPartnerDao betParnterDao;
	@Autowired
	private BetSchemeDao betSchemeDao;

	@Transactional(readOnly = true)
	@Override
	public List<BetRecord> list(long schemeId) {
		List<BetRecord> results = new ArrayList<BetRecord>();

		List<BetPartnerPO> betPartnerPOs = betParnterDao
				.findBySchemeId(schemeId);
		if (null != betPartnerPOs && betPartnerPOs.size() > 0) {
			for (BetPartnerPO po : betPartnerPOs) {
				BetRecord br = new BetRecord();
				BetSchemePO spo = betSchemeDao.get(schemeId);

				br.setId(spo.getId());
				br.setBetAmount(po.getBetAmount());
				br.setCreatedTime(po.getCreatedTime());
				br.setPlayId(spo.getPlayId());
				br.setSaleStatus(spo.getSaleStatus());
				br.setSponsorId(spo.getSponsorId());
				br.setSponsor(spo.getSponsor());
				br.setLotteryId(spo.getLotteryId());
				br.setStatus(spo.getStatus());
				br.setIsPublishShow(spo.getIsPublishShow());
				if (po.getWinAmount() != null) {
					br.setWinAmount(po.getWinAmount());
				}
				br.setType(spo.getType());
				br.setShowScheme(spo.getShowScheme());

				BetScheme s = new BetScheme();
				BeanUtils.copyProperties(spo, s);
				br.setProgress(ResultTool.progress(s));

				results.add(br);
			}
		}
		return results;
	}

	@Transactional
	@Override
	public BetPartner findById(long betRecordId) {
		BetPartner bp = new BetPartner();
		BetPartnerPO po = betParnterDao.findById(betRecordId);
		if(null != po) {
			BetScheme bs = new BetScheme();
			BeanUtils.copyProperties(po, bp);
			BeanUtils.copyProperties(po.getScheme(), bs);
			bp.setSchemeId(bs.getId());
			bp.setBetScheme(bs);
		}
		return bp;
	}
	
	@Transactional
	@Override
	public BetPartner findGroupSponsorRecord(long schemeId, long sponsorId) {
		BetPartner bp = null;
		BetPartnerPO po = betParnterDao.findGroupSponsorRecord(schemeId, sponsorId);
		if(null != po && po.getId() > 0) {
			bp = new BetPartner();
			BeanUtils.copyProperties(po, bp);
		}
		return bp;
	}
}
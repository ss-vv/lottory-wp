package com.xhcms.lottery.commons.persist.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.xhcms.exception.XHRuntimeException;
import com.xhcms.lottery.commons.data.BetMatch;
import com.xhcms.lottery.commons.persist.service.BetOptionService;
import com.xhcms.lottery.lang.AppCode;
import com.xhcms.lottery.utils.ResultTool;

public class BetOptionServiceImpl implements BetOptionService {

	@Override
	public void combinBetOptions(String playId, List<BetMatch> matchList,boolean needOdds) {
		for(BetMatch bm : matchList) {//投注格式：10023表示周一002场选项是“胜”
    		if(StringUtils.isNotBlank(bm.getCode()) && bm.getCode().length() > 4) {
    			String betCode = bm.getCode().substring(4);
    			//主要是兼容旧版的投注格式，旧的普通投注格式是不带玩法的：201502031002-100231-false,201502031003-10033-false
    			//新的普通投注格式是带有玩法标示的：201502031002-100231-false-brqspf,201502031003-10033-false-brqspf
    			//简单说就是旧版普通投注赛事串后不会有玩法，新版投注格式有，此处为了兼容
    			String betPlayId = "";
    			if(StringUtils.isBlank(bm.getPlayId())) {
    				betPlayId = playId;
    			} else {
    				betPlayId = bm.getPlayId();
    			}
    			String betOptions = ResultTool.cn(betPlayId, betCode,needOdds? bm.getOdds():null);
    			bm.setBetOptions(betOptions);
    		} else {
    			throw new XHRuntimeException(AppCode.INVALID_BET_CODE);
    		}
    	}

	}

}

package com.unison.lottery.weibo.web.service;

import java.util.Date;
import java.util.List;

import com.unison.lottery.weibo.data.PageResult;
import com.unison.lottery.weibo.data.service.store.data.QTMatchVO;
import com.unison.lottery.weibo.data.service.store.persist.entity.QTMatchPO;

public interface ScoreService {

	PageResult<QTMatchVO> listCTZCscore(String issueNumber);

	PageResult<QTMatchVO> listJCZQscore(Date date);

}

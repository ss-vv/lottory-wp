package com.unison.lottery.weibo.common.service;

import java.util.List;
import java.util.Map;

/**
 * 各种timeline服务
 * @author Wang Lei
 *
 */
public interface TimelineLastPosService {

	Map<String, String> loadAll(List<Long> uids);
}

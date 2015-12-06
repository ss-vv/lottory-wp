package com.davcai.lottery.service;

import java.util.Collection;
import java.util.List;

import com.xhcms.lottery.lang.LotteryId;

/**
 * 提供从尊奥赛程关联球探赛事数据的接口：
 * 接口定义原因：球探赛事数据的开赛时间和竞彩官网开赛时间有时间间隔导致无法进行数据关联
 * 解决方案是：使用cn_code和开始时间进行关联，由于球探开赛时间和官网有偏差，使用前后15分钟（lt_system_conf表的数据库配置）区间的方式进行模糊匹配；
 * 可以通过扩大区间值的方式增大查询范围
 * 
 * @author lei.li@davcai.com
 * @time   2015年4月21日 上午10:37:06
 */
public interface ZMJoinQtMatchService {

	/**
	 * 1.支持通过大V彩赛事ID关联球探数据，彩种支持：竞彩足球、竞彩篮球
	 * 
	 * @param ids 大V彩赛事ID
	 * @param lotteryId	彩种枚举值,对应"LotteryId"枚举类
	 * @return
	 */
	List<Object[]> findMatchInfoByDavcaiMatchId(Collection<Long> ids, 
			LotteryId lotteryId);
    
}
package com.xhcms.lottery.commons.util;

import java.util.List;

import com.xhcms.lottery.commons.data.IssueInfo;
import com.xhcms.lottery.commons.data.UpdateAndInsertList;

public interface IssueinfoPOChangeUtil {

	/**
	 * 将issueinfos分成需要更新的IssueinfoPO和需要新增的IssueinfoPO。
	 * 并会计算"大V彩停止出票时间"即 stop_time_for_user。
	 * 
	 * @param issueinfos 期信息集合
	 * @return 拆开的需要被更新或插入的期信息。
	 */
	public abstract UpdateAndInsertList split2UpdateAndInsertList(
			List<IssueInfo> issueinfos);

}
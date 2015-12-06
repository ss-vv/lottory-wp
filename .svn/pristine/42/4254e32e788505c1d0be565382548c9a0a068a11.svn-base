package com.unison.lottery.weibo.common.nosql;

import java.util.List;

import com.unison.lottery.weibo.data.Top5Recommend;

public interface Top5RecommendDao extends BaseDao<Top5Recommend>{

	/**
	 * 将Top5Recommend列表加入redis，redis中的存储结构是keyOfList（列表对应的key）==>ObjectKeys(每一列表项对象对应的key),
	 * 每一个列表项对象对应的key，在redis中都对应一个hashmap存储列表项对象的各属性。
	 * 
	 * 将以第一项的topType和dimension作为初始条件生成redis列表需要的keyOfList，然后
	 * 将每一个列表项对象都生成对应的ObjectKey，将列表项对象的属性存入该
	 * ObjectKey对应的hashMap中，最后
	 * 将所有列表项生成的ObjectKey都插入该列表keyOfList对应的列表中
	 * @param top5Recommend 同样排行榜类型和维度的Top5Recommend对象列表
	 */
	public void addList(List<Top5Recommend> top5Recommend);
}

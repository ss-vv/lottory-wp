package com.xhcms.lottery.lang;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * 操作Map的工具。
 * @author Yang Bo
 *
 */
public class MapUtils {
	/**
	 * 创建一个新Map，翻转 key、value。即原来map的value为key，key为value。
	 * @param orig 原始Map，要求 value 没有重复的。
	 * @return 翻转了 key、value 的新Map。
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map reverse(Map orig) {
		Set vs = new HashSet();
		vs.addAll(orig.values());
		Set ks = orig.keySet();
		if (ks.size() != vs.size()){
			throw new IllegalArgumentException("Values of original Map is not unique.");
		}
		Map m = new HashMap();
		for (Object e : orig.entrySet()){
			Entry ent = (Entry)e;
			m.put(ent.getValue(), ent.getKey());
		}
		return m;
	}
}

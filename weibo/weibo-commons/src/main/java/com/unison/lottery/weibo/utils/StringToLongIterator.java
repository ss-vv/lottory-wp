package com.unison.lottery.weibo.utils;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * String类型的 Iterator 包裹类。取到的值都是 Long 型的。
 * 
 * @author Yang Bo
 */
public class StringToLongIterator implements Iterator<Long> {

	private Iterator<String> strIterator;

	public StringToLongIterator(Iterator<String> strIterator){
		this.strIterator = strIterator;
	}
	
	@Override
	public boolean hasNext() {
		return strIterator.hasNext();
	}

	@Override
	public Long next() {
		String num = strIterator.next();
		if (num != null){
			return Long.parseLong(num);
		}
		throw new NoSuchElementException("strIterator.next() return null!");
	}

	@Override
	public void remove() {
		strIterator.remove();
	}
	
	public static StringToLongIterator instance(Iterator<String> strIterator){
		return new StringToLongIterator(strIterator);
	}
}

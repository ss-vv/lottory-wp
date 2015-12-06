package com.unison.lottery.newsextract.util;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Crawler {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	/** 种子 */
	private String seed;
	
	/** 文档对象*/
	private Document doc;
	
	/** 选择器，有顺序，将按照顺序一级一级进一步筛选*/
	private List<String> selectors = new ArrayList<String>();
	
	public Crawler(String seed){
		this.seed = seed;
	}

	public Crawler(String seed,List<String> selectors){
		this.seed = seed;
		this.selectors = selectors;
	}
	
	public void setSeed(String seed) {
		this.seed = seed;
	}

	public void setSelectors(List<String> selectors) {
		this.selectors = selectors;
	}
	
	public void addSelector(String selector){
		if(null == selectors){
			selectors = new ArrayList<String>();
		}
		this.selectors.add(selector);
	} 
	
	/**
	 * 创建一个新链接，并根据selectors 筛选，返回元素集合
	 * @return
	 * @throws Exception 
	 */
	public Elements getElements() throws Exception{
		if(selectors.size() < 1){
			return null;
		}
		doc = Jsoup.connect(seed).get();
		return doSelect();
	}
	
	/**
	 * 如果对象 doc 不为空，直接从doc进行筛选，并返回元素集合
	 * 		否则，创建一个新连接并根据selectors进行筛选，返回元素集合
	 * @return
	 * @throws Exception 
	 */
	public Elements getElementsWithCache() throws Exception{
		if(null != doc){
			return doSelect();
		} else {
			return getElements();
		}
	}
	
	private Elements doSelect(){
		if(selectors.size() < 1){
			return null;
		}
		Elements elements = doc.select(selectors.get(0));
		for (int i = 1; i < selectors.size(); i++) {
			elements = elements.select(selectors.get(i));
		}
		return elements;
	}
	
	public void delSelector(){
		this.selectors = new ArrayList<String>();
	}
}

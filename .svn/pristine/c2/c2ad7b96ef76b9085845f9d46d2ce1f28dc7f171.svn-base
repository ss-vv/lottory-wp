package com.xhcms.lottery.commons.tools;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.LinkedList;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 分页工具
 * 
 * @author Yang Bo
 */
public class Pager {

	private static Logger logger = LoggerFactory.getLogger(Pager.class);
	
	private LinkedList<Integer> pages = new LinkedList<Integer>();

	/**
	 * 在现有GET URL地址基础上过滤一个参数，然后返回URL参数串。
	 * 编码用UTF8.
	 * @param request 请求对象
	 * @param paramName 要被过滤的参数
	 * @return 去掉"paramName"的请求参数串
	 */
	public static String filterUrl(HttpServletRequest request, String paramName){
		StringBuffer buf = new StringBuffer();
		Enumeration<String> names = request.getParameterNames();
		while(names.hasMoreElements()){
			String name = names.nextElement();
			String value = request.getParameter(name);
			if (!name.equals(paramName)){
				if (buf.length()>0){
					buf.append("&");
				}
				try {
					buf.append(name).append("=").append(URLEncoder.encode(value, "utf8"));
				} catch (UnsupportedEncodingException e) {
					logger.error("can not url encode: {}", name+"="+value, e);
				}
			}
		}
		return buf.toString();
	}
	
	/**
	 * 创建分页对象
	 * @param currentPage 当前页号，从1开始。
	 * @param totalPages 总页数
	 * @param width 当前页左右两边各有多少个按钮，例如：width=2，那么就是"...1,2,当前页,4,5"的布局。
	 * 一般是2.
	 * @return 分页对象
	 */
	public static Pager paging(int currentPage, int totalPages, int width) {
		Pager pager = new Pager();
		if (totalPages <= 0){
			return pager;
		}
		int index = currentPage;
		int remains = width+1;
		while(index > 0){
			pager.pages.push(index);
			index--;
			remains--;
			if (remains == 0) {
				break;
			}
		}
		if (remains == 0 && index > 1){
			pager.pages.push(-1);	// ... 标志页号
			pager.pages.push(1);
		}else{
			if (pager.pages.getFirst()!=1){
				pager.pages.push(1);
			}
		}
		index = currentPage+1;
		remains = width;
		while(index <= totalPages){
			pager.pages.add(index);
			index++;
			remains--;
			if (remains == 0) {
				break;
			}
		}
		if (remains == 0 && index < totalPages){
			pager.pages.add(-1);	// ... 标志页号
			pager.pages.add(totalPages);
		}else{
			if (pager.pages.getLast() != totalPages){
				pager.pages.add(totalPages);
			}
		}
		return pager;
	}

	public LinkedList<Integer> getPages() {
		return pages;
	}
}

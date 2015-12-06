/*
 * <p>Copyright：Copyright (c) 2011</p>
 * <p>Company：XingHui Spirit (Beijing) Technical Co.,Ltd.</p>
 */
package com.xhcms.lottery.admin.web.menu;

import java.util.LinkedList;
import java.util.List;

/**
 * @author hdzhong
 * @version 0.0.1
 */
public class Menu implements Cloneable {
	List<MenuItem> menuItems ;
	String name;
	String alias;
	Long right;

	public Menu() {
		menuItems = new LinkedList<MenuItem>();
	}

	public List<MenuItem> getMenuItems() {
		return menuItems;
	}

	public void setMenuItems(List<MenuItem> menuItems) {
		this.menuItems = menuItems;
	}

	public void addMenuItem(MenuItem menuItem){
		menuItems.add(menuItem);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public Long getRight() {
		return right;
	}

	public void setRight(Long right) {
		this.right = right;
	}

	@Override
	public String toString() {
		return "menu: "+name + "(" + alias + ")";
	}

	@Override
	public Object clone() {
		Menu clone = null;
		try {
			clone = (Menu)super.clone();
		} catch (CloneNotSupportedException e) {
			throw new InternalError();
		}
		
		clone.menuItems = new LinkedList<MenuItem>();
		for(MenuItem item :menuItems){
			clone.menuItems.add((MenuItem)item.clone());
		}
		
		return clone;
	}
	
}

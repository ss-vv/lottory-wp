/*
 * <p>Copyright：Copyright (c) 2011</p>
 * <p>Company：XingHui Spirit (Beijing) Technical Co.,Ltd.</p>
 */
package com.xhcms.lottery.admin.web.menu;

/**
 * @author hdzhong
 * @version 0.0.1
 */
public class MenuItem implements Cloneable {
	Menu subMenu;
	String name;
	String alias;
	String url;
	String target="_window";
	boolean service = false;
	boolean unique = false;
	String script = null;
	String option = null;
	Long right;
	
	
	public MenuItem() {
	}

	public Menu getSubMenu() {
		return subMenu;
	}

	public void setSubMenu(Menu subMenu) {
		this.subMenu = subMenu;
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
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public boolean isUnique() {
		return unique;
	}

	public void setUnique(boolean unique) {
		this.unique = unique;
	}

	public boolean isService() {
		return service;
	}

	public void setService(boolean service) {
		this.service = service;
	}

	public String getScript() {
		return script;
	}

	public void setScript(String script) {
		this.script = script;
	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}

	public Long getRight() {
		return right;
	}

	public void setRight(Long right) {
		this.right = right;
	}

	@Override
	public String toString() {
		return "item: "+name + "(" + alias + ")";
	}

	@Override
	public Object clone() {
		MenuItem clone = null;
		try {
			clone = (MenuItem) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new InternalError();
		}
		if(subMenu != null){
			clone.subMenu = (Menu)subMenu.clone();
		}
		
		return clone;
	}
	
	
}

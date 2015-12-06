package com.xhcms.lottery.admin.web.action.menu;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.lottery.admin.data.Admin;
import com.xhcms.lottery.admin.web.action.BaseAction;
import com.xhcms.lottery.admin.web.menu.Menu;
import com.xhcms.lottery.admin.web.menu.MenuItem;
import com.xhcms.lottery.admin.web.menu.MenuManager;

public class MenuAction extends BaseAction {
	private static final long serialVersionUID = 5807440862358922668L;

	@Autowired
	private MenuManager menuManager;

	private List<Menu> menus;

	// jsonout
	protected String jsonRoot = null;
	protected String jsonExcludes = null;
	protected boolean jsonIgnoreHierarchy = true;

	public List<Menu> getMenus() {
		return menus;
	}

	@Override
	public String execute() throws Exception {
		menus = menuManager.getCloneMenus();
		Admin admin = getAdmin();
		if(null != admin.getUsername() && !"root".equals(admin.getUsername())){
			for(Menu menu:menus) {
				if(menu.getName().equals("base.admin")) {
					List<MenuItem> menuItems = menu.getMenuItems();
					Iterator<MenuItem> iter = menuItems.iterator();
					while(iter.hasNext()){
						MenuItem menuItem = iter.next();
						if(menuItem.getName().equals("admin.createadmin")) {
							iter.remove();
						}
					}
				}
			}
		}
		return super.execute();
	}

	public String getJsonRoot() {
		return jsonRoot;
	}

	public String getJsonExcludes() {
		return jsonExcludes;
	}

	public boolean isJsonIgnoreHierarchy() {
		return jsonIgnoreHierarchy;
	}

}

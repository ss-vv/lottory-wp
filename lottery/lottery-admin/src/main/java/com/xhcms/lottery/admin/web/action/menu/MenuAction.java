package com.xhcms.lottery.admin.web.action.menu;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.lottery.admin.shiro.service.ShiroService;
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
		Subject subject = ShiroService.subject();
		menus = menuManager.getCloneMenus();
		if(null != subject && subject.isAuthenticated() &&
				null != menus && menus.size() > 0){
			List<Menu> result = new ArrayList<Menu>();
			for(Menu menu : menus) {
				List<MenuItem> menuItems = menu.getMenuItems();
				Iterator<MenuItem> iter = menuItems.iterator();
				while(iter.hasNext()){
					MenuItem menuItem = iter.next();
					boolean isPermitted = subject.isPermitted(menuItem.getName());
					if(!isPermitted) {
					  iter.remove();
					}
				}
				if(menuItems.size() > 0) {
					result.add(menu);
				}
			}
			menus = result;
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

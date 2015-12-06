Jet().$package(function(J){
	var $ = jQuery, $E = Jooe;
	var IWindow = xhcms.ui.IWindow, Window = xhcms.ui.Window;
	var Menu = xhcms.ui.Menu, MenuItem = xhcms.ui.MenuItem;
	var Layout = xhcms.desk.Layout;
	var LauncherManager = xhcms.desk.LauncherManager,
		TaskManager = xhcms.desk.TaskManager,
		MenuManager = xhcms.desk.MenuManager;
	
//	$E.on(window, "beforeunload", function(e){
//		var msg = "你确定要离开管理后台？";
//		if ($.browser.safari || $.browser.chrome)
//			return msg;
//		else if ($.browser.msie > 0)
//			window.event.result = msg;
//		else
//			e.result = msg;
//	});
	
	var layout = new Layout();
	
	/**
	 * load global menu
	 */
	var loadMenu = function(){
		var url = "menu.do";
		$.ajax({
		    type: "GET",
		    url: url,
		    cache: false,
		    success: function(menus){
				layout.addMenus($.parseJSON(menus));
		    }
		});
	};
	
	$(function(){
		layout.hideStartingCover();
		loadMenu();
	});

	window.layout = layout;
});
(function(window){
	if(typeof window.xhOpenWindow != "undefined"){
		return;
	}
	/**
	 * 	xhOpenWindow(url, {
	 *		id: 'site_'+id,
	 *		title: '站点管理列表',
	 *		height: 600,
	 *		width: 960,
	 *		x: 200,
	 *		y: 100
	 *	});
	 * 
	 * @param url 目标url
	 * @param option 窗口参数，参见样例
	 * @param callback 打开新窗口后回调该函数；例如callback(win, true)，其中第一个参数为打开的窗口，第二个参数为false时表示有window.open打开的窗口。
	 * @returns 返回false值；返回true值请参考fallback参数。
	 */
	window.xhOpenWindow = function(url, option, callback){
		option = option || {};
		option.windowId = option.id;
		if(!(/^https{0,1}:\/\//.test(url))){
			url = window.location.href.substring(0, window.location.href.lastIndexOf("/")) + "/" +url;
		}
		var win, xh = true;
		try{
			win = window.parent.layout.openWindow(url, option);
		}catch(e){
			var opt = 'toolbar=no, menubar=no,scrollbars=yes, status=no';
			opt += ',height=' + (option.height||600);
			opt += ',width=' + (option.width||900);
			opt += ',top=' + (option.y||100);
			opt += ',left=' + (option.x||200);
			win = window.open(url, option.id||null, opt);
			xh = false;
		}
		if(callback){
			callback.apply(context, [win, xh]);
		}

		return false;
	};
	
	window.xhCloseWindow = function(id){
		try{
			if(id){
				window.parent.layout.closeWindow(id);
			}else{
				window.parent.layout.closeWindow(window);
			}
		}catch(e){
			alert(e);
		}
	};
	
	window.xhInvokeService = function(name, params, callback){
		return window.parent.layout.invokeService(name, params, callback);
	};
	
	try{
		document.onclick = function(e){
			try{
				window.parent.layout.clickWindow(window);
			}catch(e){
			}
		};
	}catch(e){
	}
})(window);


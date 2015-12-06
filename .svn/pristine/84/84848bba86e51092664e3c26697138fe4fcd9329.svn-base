/**	
 * 基于Jet引擎的web桌面扩展
 *
 * @version	1.0
 * @author	Danny Zhong
 * @description 
 * 
 */

/**	
 * @description
 * Package: jet.xhcms.desk
 *
 * Need package:
 * jet.base.js
 * 
 */

/**
 * xhcms.desk模块包
 */
Jet().$package("xhcms.desk", function(J){
	var $ = jQuery, $E = Jooe;
	var MaskLayer = xhcms.ui.MaskLayer;
	var Panel = xhcms.ui.Panel;

	var PanelManager = function(){};
	PanelManager.prototype = {
		init : function() {
			this.panelList = [];
		},
		createPanel : function(option) {
			option = option || {};
			var x = new Panel(option);
			this.panelList[option.id] = x;
			J.out("createPanel:" + option.name, "layout");
			return x;
		},
		getPanel : function(id) {
			return this.panelList[id];
		}
	};
	PanelManager = Jooe.extend(PanelManager.prototype);
	/**
	 * @super Jooe
	 * @constructor
	 */
	var Layout = function(){};
	Layout.prototype = {
		layers: null,
		init: function(option){
			var context = this;
			
			this.layers = [ 10, 1E5, 2E5, 3E5, 4E5 ];
			this.observer = {
				onClickDesktop : function() {
					$E.trigger(context, Layout.EVENT.ClickDesktop, context.getDesktop());
				},
				onFocusDesktop : function() {
					$E.trigger(context, Layout.EVENT.DesktopFocus);
				},
				onBlurDesktop : function() {
					$E.trigger(context, Layout.EVENT.DesktopBlur);
				},
				onWindowResize : function() {
					context.onResize();
				}
			};
			
			for(var i in Layout.Addons){
				this.loadAddons(i, Layout.Addons[i]);
			}
			this.width = 320;
			this.height = 100;
			this.bindDom();
			//J.browser.firefox ? setTimeout(DesktopNotifier.onWindowResize, 100)
					//: DesktopNotifier.onWindowResize();
			setTimeout(function() {
				context.onResize();
			}, 100);
		},
		loadAddons: function(name, addons, option){
			option = option || {};
			option.layout = this;
			this[name] = new addons(option);
			return this[name];
		},
		bindDom: function() {
			var p = $("#mainPanel")[0], x = $("#topBar")[0], 
				z = $("#toolBar")[0];
			
			this.areas = {};
			this.body = document.body;
			this.splash = $("#startingCover")[0];
			this._title = document.title;
			this._titleTimer = null;
			this.desktop = $("#desktop")[0];
			this.launcherBar = $("#launcherBar")[0];
			this.areas.topArea = x;
			this.areas.bottomArea = z;
			this.areas.mainArea = p;
			this.areas.leftArea = null;
			this.areas.rightArea = null;
			
			$(x).height(32);
			$(x).css("overflow", "visible");

			this.panelManager.createPanel({
				id : "desktop",
				name : "desktop",
				container : this.getBody(),
				body : this.desktop,
				html : ""
			});
			this.panelManager.createPanel({
				id : "topBar",
				name : "topBar",
				container : x,
				body : x,
				html : ""
			});
			
			$E.on(window, "resize", this.observer.onWindowResize);
			$E.on(this.desktop, "click", this.observer.onClickDesktop);
			if ("onfocusin" in document) {
				$E.on(document, "focusin", this.observer.onFocusDesktop);
				$E.on(document, "focusout", this.observer.onBlurDesktop);
			} else {
				$E.on(window, "focus", this.observer.onFocusDesktop);
				$E.on(window, "blur", this.observer.onBlurDesktop);
			}
			this.trigger(Layout.EVENT.Inited, [this]);
		},
		onResize: function() {
			var p = $(window).width(), x = $(window).height();
			if ($.browser.msie) {
				p = p % 2 + p;
				x = x % 2 + x;
			}
			this.clientWidth = p;
			this.clientHeight = x;
			var z = false;
			if (p >= this.width) {
				$(this.body).css("overflowX", "hidden");
				$(this.desktop).css("width", "");
				this.desktopWidth = p;
			} else {
				z = true;
				$(this.body).css("overflowX", "auto");
				$(this.desktop).css("width", this.width + "px");
				this.desktopWidth = this.width;
			}
			if (x >= this.height) {
				$(this.body).css("overflowY", "hidden");
				$(this.desktop).css("height", "");
				this.desktopHeight = x;
			} else {
				z = true;
				$(this.body).css("overflowY", "auto");
				$(this.desktop).css("height", this.height + "px");
				this.desktopHeight = this.height;
			}
			z ? $(this.desktop).css("position", "absolute") : $(this.desktop).css("position", "static");
//			J.out("DesktopResize, width:"+desktopWidth+", height:"+desktopHeight);
			$(this.body).css("height", this.desktopHeight + "px");
			if (this.splash) {
				$(this.splash).css( "width", this.desktopWidth + "px");
				$(this.splash).css("height", this.desktopHeight + "px");
			}
			$(this.launcherBar).css( "width", (this.desktopWidth - 34 - 150) + "px");
			
			this.trigger(Layout.EVENT.DesktopResize);
		},
		getTopZIndex: function(layer){
			if (typeof layer == "undefined" || !this.layers[layer])
				layer = 0;
			return this.layers[layer]++;
		},
		getArea: function(area) {
			return this.areas[area + "Area"];
		},
		getAreaWidth: function(area) {
			if (area = this.getArea(area))
				return $(area).innerWidth();
			return 0;
		},
		getAreaHeight: function(area) {
			if (area = this.getArea(area))
				return $(area).innerHeight();
			return 0;
		},
		getAvailableWidth: function() {
			return this.getDesktopWidth()
					- this.getAreaWidth("left")
					- this.getAreaWidth("right");
		},
		getAvailableHeight: function() {
			return this.getDesktopHeight()
					- this.getAreaHeight("top")
					- this.getAreaHeight("bottom");
		},
		setDesktopWidth: function(width) {
			return this.desktopWidth = width;
		},
		setDesktopHeight: function(height) {
			return this.desktopHeight = height;
		},
		getDesktopWidth: function() {
			return this.desktopWidth;
		},
		getDesktopHeight: function() {
			return this.desktopHeight;
		},
		getClientWidth: function() {
			return this.clientWidth = this.clientWidth || $(window).width();
		},
		getClientHeight: function() {
			return this.clientHeight = this.clientHeight || $(window).height();
		},
		getDesktop: function() {
			return this.panelManager.getPanel("desktop");
		},
		getBody: function() {
			return this.body;
		},
		getMaskLayer: function() {
			this.mask || (this.mask = MaskLayer({
				appendTo : this.getDesktop().body,
				zIndex : 1,
				opacity : 0.5
			}));
			this.mask.reset();
			return this.mask;
		},
		getPanel: function(id) {
			return this.panelManager.getPanel(id);
		},
		getThemeManager: function() {
		},
		showDesktop: function() {
			var p = [], x;
			x = this.getWindowManager();
			for ( var z = x.getCurrentWindow(), F = x.getWindowList(), G = 0; G < F.length; G++) {
				x = F[G];
				if (x.isShow && x.isShow()) {
					x.min();
					p.push(x);
				}
			}
			if (p.length > 0) {
				this.windows = p;
				this.currentWindow = z;
			} else {
				this.currentWindow && this.currentWindow.setCurrent();
				for (G = 0; G < this.windows.length; G++)
					this.windows[G].show();
			}
		},
		setTitle: function(newTitle, option) {
			option.roll = option.roll || false;
			option.speed = option.speed || 500;
			if (option.roll) {
				if (!(newTitle.length < 1)) {
					this._title = document.title;
					this._titleTimer && clearInterval(this._titleTimer);
					this._titleTimer = setInterval(function() {
						document.title = newTitle;
						newTitle = newTitle.substr(1) + newTitle.charAt(0);
					}, option.speed);
				}
			} else {
				this._title = document.title;
				document.title = newTitle;
			}
		},
		resetTitle: function() {
			if (this._titleTimer) {
				clearInterval(this._titleTimer);
				this._titleTimer = null;
			}
			document.title = this._title;
		},
		messagebox: function(html, option) {
			option = option || {};
			option.innerHtml = html;
			return (new xhcms.ui.MessageBox(option)).Window;
		},
		alert: function(html, acceptFunc, option) {
			option = option || {};
			option.onAccept = acceptFunc;
			option.innerHtml = html;
			return (new xhcms.ui.MessageBox.Alert(option)).Window;
		},
		confirm: function(html, acceptFunc, option) {
			option = option || {};
			option.onAccept = acceptFunc;
			option.innerHtml = html;
			return (new xhcms.ui.MessageBox.Confirm(option)).Window;
		},
		hideStartingCover: function() {
			$(this.splash).hide(500);
		},
		hideDesktop: function() {
			$(this.desktop).hide(1000);
		}
	};
	//declare Jooe Class
	Layout = Jooe.extend(Layout.prototype);
	//layout event
	Layout.EVENT = {
			Inited: "Inited",
			ClickDesktop: "ClickDesktop",
			DesktopFocus: "DesktopFocus",
			DesktopBlur: "DesktopBlur",
			DesktopResize: "DesktopResize",
			CreateWindow: "CreateWindow"
		};
	Layout.Addons = {
		panelManager: PanelManager
	};
	
	this.Layout = Layout;
});
/**
 * xhcms.ui.WindowManager 模块
 */
Jet().$package("xhcms.desk", function(J){
	var $ = jQuery, $E = Jooe;
	var IWindow = xhcms.ui.IWindow, Window = xhcms.ui.Window, 
		Widget = xhcms.ui.Widget, Layout = xhcms.desk.Layout;
	
	var WindowManager = function(){};
	WindowManager.prototype = {
		_currentWindow : null,
		_isDragProxy : false,
		_isGlobalProxy : false,
		_useGlobalProxySetting : false,
		init : function(option) {
			var context = this;
			this._windowArr = [];
			this._id2Window = {};			
			this._windowType = {};
			
			option = option || {};
			/**
			 * @type Layout
			 */
			this.layout = option.layout || window.layout;
			
			this.registerWindow("Window", xhcms.ui.Window);
			this.registerWindow("Widget", xhcms.ui.Widget);
			
			this.observer = {
				onLayoutInited: function(e){
					context.onLayoutInited(e);
				},
				onDesktopResize: function(e) {
					context.onDesktopResize(e);
				}
			};
			this._windowObserver = {
				onWindowSetCenter : function() {
					var width = context.layout.getAvailableWidth(), 
						height = context.layout.getAvailableHeight(), 
						top = context.layout.getAreaHeight("top");
					width = width > this._width ? (width - this._width) / 2	: 0;
					height = height > this._height ? (height - this._height) / 2 : 0;
					height = height < top ? top : height;
					this.setXY(width, height);
				},
				onWindowSetCurrent : function(e) {
					if (context.getCurrentWindow() != this) {
						context.setCurrentWindow(this);
						context.setWindowZIndex(this);
					}
				},
				onWindowDestroy : function(e) {
					context.removeWindow(this);
				},
				onWindowMax : function() {
					var width = context.layout.getAvailableWidth(), 
						height = context.layout.getAvailableHeight();
					this.setXY(0, context.layout.getAreaHeight("top"));
					this.setWidth(width);
					this.setHeight(height);
				},
				onWindowRestore : function() {
				},
				onWindowFullscreen : function(e) {
					var width = context.layout.getClientWidth(), 
						height = context.layout.getClientHeight();
					this.setXY(0, 0);
					this.setWidth(width);
					this.setHeight(height);
					var tip = null;
					this.setZIndexLevel(3);
					context.setWindowZIndex(this);
					tip = $("#fullscreen_tip")[0];
					if (!tip) {
						tip = $('<div id="fullscreen_tip" class = "fullscreen_tip"></div>');
						tip.appendTo(document.body);
						tip = tip[0];
					}
					$(tip).css("zIndex", context.layout.getTopZIndex(3));
					$(tip).show();
					setTimeout(function() {
						$(tip).hide();
					}, 3E3);
				},
				onWindowRestoreFull : function(e) {
					this.setZIndexLevel(0);
					context.setWindowZIndex(this);
				},
				onWindowPin : function(e) {
					this.setZIndexLevel(1);
					context.setWindowZIndex(this);
				},
				onWindowPinOff : function(e) {
					this.setZIndexLevel(0);
					context.setWindowZIndex(this);
				}
			};
			$E.addObserver(this.layout, Layout.EVENT.Inited, this.observer.onLayoutInited);
			$E.addObserver(this.layout, Layout.EVENT.DesktopResize, this.observer.onDesktopResize);
			
			this.extendLayout();
		},
		extendLayout: function(){
			//extend layout
			Jooe.extend(this.layout, {
				getWindowManager: function() {
					return this.windowManager;
				},
				createWindow: function(wndClass, option){
					option = option || {};
					var win = this.getWindowManager().createWindow(wndClass, option);
					this.trigger(Layout.EVENT.CreateWindow, [win, option]);
					
					return win;
				},
				clickWindow: function(win){
					var context = this;
					$("iframe", this.getBody()).each(function(){
						if(this.contentWindow == win){
							var id = $(this).attr("windowId");
							if(typeof id != "undefined"){
								var w = context.windowManager.getWindow(id);
								if(context.getWindowManager().getCurrentWindow() !== w){
									w.setCurrent();
								}
							}
						}
					});
					setTimeout(function(){
						$(document).trigger("click");
					},0);
				},
				closeWindow: function(win){
					var context = this;
					var id = win;
					//try to find the special window
					if(typeof win == 'object'){
						$("iframe", this.getBody()).each(function(){
							if(this.contentWindow == win){
								id = $(this).attr("windowId");
							}
						});
					}
					
					if(typeof id != "undefined"){
						var w = context.windowManager.getWindow(id);
						w.close();
					}
				},
				
				openWindow: function(url, option, wndClass){
					var win = null;
					var html = '<div class="content_area"><iframe frameborder="no" scrolling="auto" hidefocus="" allowtransparency="true" style="width: 100%; height: 100%; overflow: auto;" src="'
						+url+'"></iframe></div>';
					option = option || {};
					if(option.windowId){
						win = this.windowManager.getWindow(option.windowId);
						if(win){
							win.setHtml(html);
							$("iframe", win.body).attr("windowId", win.getId());
							setTimeout(function(){
								win.setCurrent();
							}, 5);
							return win;
						}
					}
					
					//open popup window
					var opt = {
							title: '',
							dragable: true,
							dragProxy: true,
							resize: true,
							hasCloseButton: true,
							hasRefreshButton: true,
							hasHomeButton: true,
							hasMaxButton: true,
							hasRestoreButton: true,
							hasMinButton: true,
							isSetCentered: true,
							modeSwitch: true
					};
					if(option != null)
						$.extend(opt, option);
					wndClass = wndClass || "Window";
					win = this.createWindow(wndClass, opt);
					win.setHtml(html);
					$("iframe", win.body).attr("windowId", win.getId());
					win.addObserver(Window.EVENT.ClickRefreshButton, function(){
						//try refresh current content
						try{
							$("iframe", this.body)[0].contentWindow.location.reload();
						}catch(e){
							this.setHtml(html);
							$("iframe", this.body).attr("windowId", win.getId());
						}
					});
					win.addObserver(Window.EVENT.ClickHomeButton, function(){
						this.setHtml(html);
						$("iframe", this.body).attr("windowId", win.getId());
					});
					return win;
				}
			});
		},
		onLayoutInited: function(e){
			this._defaultContainer = this.layout.getDesktop().body;
		},
		onDesktopResize: function(e) {
			var wnd;
			for ( var i in this._windowArr) {
				wnd = this._windowArr[i];
				var status = wnd.getBoxStatus();
				status == IWindow.STATUS.MAX 
							|| status == IWindow.STATUS.FULLSCREEN 
								? this.adjustSize(wnd) : this.adjustPosition(wnd);
			}
		},
		createWindow : function(type, option) {
			var windowClass = this._windowType[type];
			option.level = option.level ? parseInt(option.level) : 0;
			option.dragProxy = option.dragProxy || this.getWindowDragProxy();
			option.zIndex = option.zIndex || this.layout.getTopZIndex();
			option.topMargin = option.topMargin	|| this.layout.getAreaHeight("top");
			option.bottomMargin = option.bottomMargin || this.layout.getAreaHeight("bottom");
			
			if (windowClass) {
				if (!option.appendTo && this._defaultContainer)
					option.appendTo = this._defaultContainer;
				
				var wnd = new windowClass(option);
				wnd.setZIndexLevel(option.level);
				if(option.maxWidth){
					wnd.setWidth(this.layout.getAvailableWidth());
				}
				if(option.maxHeight){
					wnd.setHeight(this.layout.getAvailableHeight());
				}
				if (!option.x && !option.y) {
					option = this.getDefaultPosition(wnd, 0, 0);
					wnd.setXY(option.x, option.y);
				} else
					this.adjustPosition(wnd);
				
				this.addWindow(wnd);
				
				option = wnd.option;
				setTimeout(function(){
					option.isSetCurrent ? wnd.setCurrent() : wnd.setNotCurrent();
				},5);
				option.isSetCentered && wnd.setWindowCentered();
				switch (option.defaultMode) {
				case IWindow.STATUS.MAX:
					wnd.max();
					break;
				case IWindow.STATUS.RESTORE:
					wnd.restore();
					break;
				case IWindow.STATUS.MIN:
					wnd.min();
					break;
				}
				return wnd;
			} else
				throw new Error('WindowManager: class "' + type + '" has not register!');
		},
		registerWindow : function(type, windowClass) {
			this._windowType[type] = windowClass;
		},
		addWindow : function(wnd) {
			this._addObserversToWindow(wnd);
			this._windowArr.push(wnd);
			this._id2Window[wnd.getId()] = wnd;
		},
		removeWindow : function(wnd) {
			wnd == this.getCurrentWindow() && this.setCurrentWindow(null);
			var index = $.inArray(wnd, this._windowArr);
			if(index > -1){
				this._windowArr.splice(index, 1);
			}
			this._id2Window[wnd.getId()] = null;
			delete this._id2Window[wnd.getId()];
		},
		getWindow : function(id) {
			return this._id2Window[id];
		},
		getWindowList : function() {
			return this._windowArr;
		},
		setCurrentWindow : function(wnd) {
			wnd	&& this._currentWindow
					&& this._currentWindow != wnd
					&& this._currentWindow.setNotCurrent();
			this._currentWindow = wnd;
		},
		getCurrentWindow : function() {
			return this._currentWindow;
		},
		getTopZIndex : function(level) {
			return this.layout.getTopZIndex(level || 0);
		},
		setWindowZIndex : function(wnd) {
			var index = this.getTopZIndex(wnd.getZIndexLevel() || 0);
			wnd.setZIndex(index);
		},
		getWindowZIndexLevel : function(wnd) {
			return wnd.getZIndexLevel();
		},
		setWindowZIndexLevel : function(wnd, level) {
			wnd.setZIndexLevel(level);
		},
		/**
		 * 
		 * @param {WindowManager} wnd
		 */
		adjustPosition : function(wnd) {
			var width = this.layout.getClientWidth(), 
				height = this.layout.getClientHeight(), 
				top = this.layout.getAreaHeight("top"), 
				bottom = this.layout.getAreaHeight("bottom"), 
				x = wnd.getX() || 0, 
				y = wnd.getY() || 0;
			if (x + wnd._width > width) {
				width = width - wnd._width;
				x = width < 0 ? 0 : width;
				wnd.setX(x);
			}
			if (y + wnd._height > height - top - bottom) {
				height = height - wnd._height - bottom;
				y = height < top ? top : height;
				wnd.setY(y);
			}
		},
		adjustSize : function(wnd, x, y) {
			x = x || 0;
			y = y || 0;
			var width = this.layout.getClientWidth(), 
				height = this.layout.getClientHeight(), 
				top = 0;
			if (wnd.getBoxStatus() == IWindow.STATUS.MAX) {
				width = this.layout.getAvailableWidth();
				height = this.layout.getAvailableHeight();
				top = this.layout.getAreaHeight("top");
			}
			if (wnd.windowType == "window")
				wnd.adjustSize(x, y, width, height, 0, top);
		},
		getDefaultPosition : function(wnd, offsetX, offsetY) {
			offsetX = offsetX || 0;
			offsetY = offsetY || 0;
			var width = wnd.option.clientWidth || this.layout.getAvailableWidth(), 
				height = wnd.option.clientHeight || this.layout.getAvailableHeight();
			this.layout.getAreaWidth("left");
			this.layout.getAreaWidth("right");
			var top = this.layout.getAreaHeight("top");
			this.layout.getAreaHeight("bottom");
			var w = width - wnd._width, h = height - wnd._height;
			w2 = w > 0 ? w / 2 : 0;
			h2 = h > 0 ? h / 2 : 0;
			var y = wnd.getId();
			y = y < 0 ? 0 : y;
			var x = (w2 + y * 25) % w + offsetX;
			y = (h2 + y * 25) % h + offsetY;
			x = x > 0 ? x : 0;
			y = y > 0 ? y : 0;
			x = x + parseInt(wnd._width) >= width ? 0 : x;
			y = y + parseInt(wnd._height) >= height ? 0 : y;
			y += top;
			J.out("w:" + w + ", h:" + h + ", w2:"
					+ w2 + ", h2:" + h2 + ", offsetX:"
					+ offsetX + ", offsetY:" + offsetY);
			return {
				x : x,
				y : y
			};
		},
		getWindowDragProxy : function() {
			return this._isDragProxy;
		},
		setGlobalDragProxyEnabled : function(d, g) {
			this._useGlobalProxySetting = d;
			this._isGlobalProxy = !!g;
		},
		getGlobalDragProxyEnabled : function() {
			return {
				useGlobal : this._useGlobalProxySetting,
				isGlobalProxy : this._isGlobalProxy
			};
		},
		_addObserversToWindow : function(wnd) {
			$E.addObserver(wnd, IWindow.EVENT.SetCenter, this, 
							this._windowObserver.onWindowSetCenter);
			$E.addObserver(wnd, IWindow.EVENT.SetCurrent, this,
							this._windowObserver.onWindowSetCurrent);
			$E.addObserver(wnd, IWindow.EVENT.Destroy, this,
							this._windowObserver.onWindowDestroy);
			if (wnd.windowType == "window") {
				$E.addObserver(wnd, IWindow.EVENT.Max, this,
							this._windowObserver.onWindowMax);
				$E.addObserver(wnd, IWindow.EVENT.Fullscreen, this,
							this._windowObserver.onWindowFullscreen);
				$E.addObserver(wnd, IWindow.EVENT.Restorefull, this,
							this._windowObserver.onWindowRestoreFull);
			}
			if (wnd.windowType == "widget") {
				$E.addObserver(wnd, Widget.EVENT.ClickPinUpButton, this,
							this._windowObserver.onWindowPin);
				$E.addObserver(wnd, Widget.EVENT.ClickPinDownButton, this,
							this._windowObserver.onWindowPinOff);
			}
		},
		_removeObserversFromWindow : function(wnd) {
			$E.removeObserver(wnd, IWindow.EVENT.SetCenter);
			$E.removeObserver(wnd, IWindow.EVENT.SetCurrent);
			$E.removeObserver(wnd, IWindow.EVENT.Destroy);
			$E.removeObserver(wnd, IWindow.EVENT.Max);
			$E.removeObserver(wnd, IWindow.EVENT.Fullscreen);
		}
	};
	
	WindowManager = Jooe.extend(WindowManager.prototype);
	Layout.Addons.windowManager = WindowManager;
	this.WindowManager = WindowManager;
});

/**
 * Service, DefaultService, ServiceManager
 */
Jet().$package("xhcms.desk", function(J){
	var $ = jQuery, $E = Jooe;
	var IWindow = xhcms.ui.IWindow,
		Window = xhcms.ui.Window,
		Layout = xhcms.desk.Layout;
	
	//service super class
	var Service = function(){};
	Service.prototype = {
		init: function(option){
			option = option || {};
			this.option = option;
		},
		execute: function(params, callback){
			throw "execute does not implement a required interface(Error in Service.execute())"; 
		}
	};
	Service = Jooe.extend(Service.prototype);
	
	//default service
	var DefaultService = function(){};
	DefaultService.prototype = {
		init: function(option){
			this._super.init(option);
			this._url = option.url;
			this._script = option.script;
			this._target = option.target;
			this._unique = option.unique;
			this._option = option.option;
			this._title = option.title;
			this.layout = option.layout || window.layout;
		},
		execute: function(params, callback){
			var context = this;
			
			if(this._script != null){
				//script
				return eval(this._script);
			}else{
				if(this._url != null){
					//add the params to the url
					var url = this._url;
					if(params != null){
						var query = '';
						for(var i in params){
							query += (query == '' ?'':'&') + i + '=' + params[i];
						}
						if(query.length > 0){
							url += ( query.indexOf('?') >= 0 ? "&" : "?" )+query;
						}
					}
					
					if(this._target == "_window" || this._target == "_widget"){
						if(this._unique && this._win != null){
							if(!(/^https{0,1}:\/\//.test(url))){
								url = window.location.href.substring(0, window.location.href.lastIndexOf("/")) + "/" +url;
							}
							if(url != $("iframe", this._win.body)[0].contentWindow.location.href){
								$("iframe", this._win.body)[0].contentWindow.location.href = url;
							}
							setTimeout(function(){
								context._win.setCurrent();
							}, 5);
							return this._win;
						}
						var wndClass = "Window";
						if(this._target == "_widget")
							wndClass = "Widget";
						var option = {title: this._title};
						$.extend(option, eval('('+this._option+')'));
						var win = this.layout.openWindow(url, option, wndClass);
						
						if(this._unique){
							this._win = win;
							win.addObserver(IWindow.EVENT.Destroy, function(){
								context._win = null;
								delete context._win;
							});
						}
						if(callback){
							win.addObserver(Window.EVENT.ClickOkButton, function(){
								win.removeObserver(Window.EVENT.ClickOkButton, callback);
								//TODO add fetch data from the window before invoke callback
								var data = null;
								data = $("iframe", win.body)[0].contentWindow.xhFetchData();
								callback.apply(this, [data]);
							});
						}
						return win;
					}else{
						//open new window
						return window.open(url, this._target, this._option);
					}
				}
			}
			return true;
		}
	};
	
	DefaultService = Service.extend(DefaultService.prototype);
	//services manager
	var ServiceManager = function(){};
	ServiceManager.prototype = {
		_services: null,
		init: function(option){
			var context = this;
			this.layout = option.layout;
			this._services = {};
			//extend layout
			Jooe.extend(this.layout, {
				getServiceManager: function(){
					return this.serviceManager;
				},
				registerService: function(name, service) {
					return this.serviceManager.register(name, service);
				},
				invokeService: function(name, params, callback){
					return this.serviceManager.invoke(name, params, callback);
				},
				getServices: function(){
					this.serviceManager.getServices();
				}
			});
		},
		register: function(name, service){
			this._services[name] = service;
		},
		invoke: function(name, params, callback){
			if(this._services[name]){
				return this._services[name].execute(params, callback);
			}
			return false;
		},
		getServices: function(){
			return this._services;
		}
	};
	ServiceManager = Jooe.extend(ServiceManager.prototype);
	ServiceManager.TARGET = {
		Window: "_window",
		Widget: "_widget",
		Blank: "_blank"
	};
	
	Layout.Addons.serviceManager = ServiceManager;
	
	this.Service = Service;
	this.DefaultService = DefaultService;
	this.ServiceManager = ServiceManager;
});

/**
 * LauncherManager, LauncherbarManager TaskbarManager, QuickbarManager, StatusbarManager, MenuManager 模块
 */
Jet().$package("xhcms.desk", function(J) {
	var $ = jQuery, $E = Jooe;
	var RichButton = xhcms.ui.RichButton;
	var IWindow = xhcms.ui.IWindow;
	var Menu = xhcms.ui.Menu, MenuItem = xhcms.ui.MenuItem;
	var Layout = xhcms.desk.Layout;
	
	var DefaultService = xhcms.desk.DefaultService;
	
	/**
	 * @super RichButton
	 * @constructor
	 */
	var Launcher = function(){};
	Launcher.prototype = {
		init: function(option){
			this._super.init(option);
		}
	};
	Launcher = RichButton.extend(Launcher.prototype);
	Launcher.EVENT = {
		Close: "Close"
	};
	$.extend(Launcher.EVENT, RichButton.EVENT);
	
	var LauncherManager = function(){};
	LauncherManager.prototype = {
		init : function(option) {
			var context = this;
			this.LauncherList = {};
			this.layout = option.layout;
			this.observer = {
				onLayoutInited: function(e){
					context.onLayoutInited(e);
				},
				onCreateWindow: function(e, win, option){
					context.onCreateWindow(e, win, option);
				}
			};
			$E.addObserver(this.layout, Layout.EVENT.Inited, this.observer.onLayoutInited);
			$E.addObserver(this.layout, Layout.EVENT.CreateWindow, this.observer.onCreateWindow);			
		},
		getTemplate : function(id, option){
			var template = '<div class="icons" style="background:url('
					+ option.icon+') no-repeat 0 0 "></div><div class="appName">'+option.appName+'</div>';
			
			return template;
		},
		addLauncher : function(option) {
			var context = this;
			option = option || {};
			option.text = option.text || "";
			var opt = {
				appName: ""
			};
			$.extend(opt, option);
			
			
			var launcher = new Launcher(opt);			
			launcher.setHtml(context.getTemplate(launcher.getId(), option));
			
			var name = "ui_launcher_" + launcher.getId();
			this.LauncherList[name] = launcher;	
			launcher.addObserver(Launcher.EVENT.Destroy, function(){
				context.LauncherList[name] = null;
				delete context.LauncherList[name];
			});
			
			return launcher;
		},
		/**
		 * @param {Launcher} launcher
		 */
		removeLauncher : function(launcher){
			launcher.destroy();
		},
		onLayoutInited: function(e){
			
		},
		onCreateWindow: function(e, win, option){
			
		}
	};
	LauncherManager = Jooe.extend(LauncherManager.prototype);
	
	var LauncherbarManager = function(){};
	LauncherbarManager.prototype = {
		init: function(option){
			this._super.init(option);
			//extend layout
			Jooe.extend(this.layout, {
				addLauncher: function(option) {
					return this.launcherbarManager.addLauncher(option);
				},
				removeLauncher: function(launcher){
					this.launcherbarManager.removeLauncher(launcher);
				}
			});
		},
		onLayoutInited: function(e){
			this._container = $('#appList')[0];
		},
		addLauncher : function(option) {
			option.appendTo = this._container;
			return this._super.addLauncher(option);
		}
	};
	LauncherbarManager = LauncherManager.extend(LauncherbarManager.prototype);
	
	var QuickbarManager = function(){};
	QuickbarManager.prototype = {
		init: function(option){
			this._super.init(option);
			//extend layout
			Jooe.extend(this.layout, {
				addQuickButton: function(option) {
					return this.quickbarManager.addLauncher(option);
				},
				removeQuickButton: function(launcher){
					 this.quickbarManager.removeLauncher(launcher);
				}
			});
		},
		onLayoutInited: function(e){
			this._container = $('#quickList')[0];
		},
		addLauncher : function(option) {
			option.appendTo = this._container;
			return this._super.addLauncher(option);
		},
		onCreateWindow: function(e, win, option){
			
		}
	};
	QuickbarManager = LauncherManager.extend(QuickbarManager.prototype);
	
	var StatusbarManager = function(){};
	StatusbarManager.prototype = {
		init: function(option){
			this._super.init(option);
		},
		onLayoutInited: function(e){
			this._container = $('#statusList')[0];
		},
		addLauncher : function(option) {
			option.appendTo = this._container;
			return this._super.addLauncher(optoin);
		},
		onCreateWindow: function(e, win, option){
			var context = this;
			if(option.useStatuspanel && option.useStatuspanel == true){
				var btn = this.addLauncher({
					title: option.title,
					icon: option.icon
				});
				btn.attachEvent({
					"click":function(e){
						if(win.isShow()){
							if(win.getWindowFlags() & IWindow.CONST.WINDOW_FLAG_CURRENT){
								win.min();
							}else{
								win.setCurrent();
							}
						}else{
							win.show();						
							win.setCurrent();
						}
					}
				});
				
				$E.addObserver(win, IWindow.EVENT.Destroy, function(e){
					context.removeLauncher(btn);
				});
			}
		}
	};
	StatusbarManager = LauncherManager.extend(StatusbarManager.prototype);
	
	var TaskbarManager = function(){};
	TaskbarManager.prototype = {
		_currentTask : null,
		init : function(option) {
			this._super.init(option);
		},
		onLayoutInited: function(e){
			this._container = $('#taskList')[0];
		},
		addLauncher : function(option){
			var context = this;
			option = option || {};
			option.appendTo = this._container;
			var opt = {
				title: ""
			};
			$.extend(opt, option);
			
			var launcher = this._super.addLauncher(opt);
			
			launcher.attachEvent({
				click : function(e){context.setCurrentTask(launcher);}
			});
			var closeButton = $('#'+this.getCloseButtonId(launcher.getId()))[0];
			$E.on(closeButton, "click", function(e){
				e.stopPropagation();
				$E.trigger(launcher, Launcher.EVENT.Close, launcher);
			});
			$E.on(closeButton, "mouseover", function(){
				$(closeButton).addClass("task_closebutton_hover");
			});
			$E.on(closeButton, "mouseout", function(){
				$(closeButton).removeClass("task_closebutton_hover");
			});
			this.setCurrentTask(launcher);
			return launcher;
		},
		removeLauncher : function(launcher){
			//unhook event
			var closeButton = $('#'+this.getCloseButtonId(launcher.getId()))[0];
			$E.off(closeButton, "click");
			$E.off(closeButton, "mouseover");
			$E.off(closeButton, "mouseout");
			this._super.removeLauncher(launcher);
		},
		getTemplate : function(id, option){
			var template = '<span class="fl"><img src="'+ (option.icon ? option.icon : './xhdesk/images/img/logo.png')
					+ '" /><span class="taskName">'
					+ option.title +'</span></span><span id="'+this.getCloseButtonId(id)+'" class="icons"></span>';
			
			return template;
		},
		getCloseButtonId : function(id){
			return 'task_closebutton_'+id;
		},
		setCurrentTask : function(task){
			task	&& this._currentTask
				&& this._currentTask != task
				&& this._currentTask.setNotHighlight();
			
			task.setHighlight();
			this._currentTask = task;
		},
		setNotCurrentTask : function(){
			this._currentTask
				&& this._currentTask.setNotHighlight();
			
			this._currentTask = null;
		},
		onCreateWindow: function(e, win, option){
			var context = this;
			if((option.useStatuspanel && option.useStatuspanel == true)
					|| (option.useQuickpanel && option.useQuickpanel == true)){
				this.setNotCurrentTask();
				$E.addObserver(win, IWindow.EVENT.SetCurrent, function(e){context.setNotCurrentTask();});
			}else{
				var taskBtn = this.addLauncher({
					title: option.title,
					icon: option.icon
				});
				taskBtn.attachEvent({
					"click":function(e){
						if(win.isShow()){
							if(win.getWindowFlags() & IWindow.CONST.WINDOW_FLAG_CURRENT){
								win.min();
							}else{
								win.setCurrent();
							}
						}else{
							win.show();						
							win.setCurrent();
						}
					}
				});
				
				$E.addObserver(win, IWindow.EVENT.Destroy, function(e){
					context.removeLauncher(taskBtn);
				});
				$E.addObserver(taskBtn, Launcher.EVENT.Close, function(e){
					win.close();
				});
				$E.addObserver(win, IWindow.EVENT.SetCurrent, function(e){context.setCurrentTask(taskBtn);});			
			}
		}
	};
	TaskbarManager = LauncherManager.extend(TaskbarManager.prototype);
	
	var MenuManager = function(){};
	MenuManager.prototype = {
		init : function(option) {
			this._super.init(option);
			this._menus = {};
			this._items = {};
			
			this.extendLayout();
		},
		extendLayout: function(){
			//extend layout
			Jooe.extend(this.layout, {
				jsonToMenu: function(menuJson){
					//menu
					var menu = new Menu({
						name: menuJson.name,
						text: menuJson.alias,
			    		appendTo : this.getDesktop().body,
			    		noCatchMouseUp : true
			    	});
					//items
					for(var i=0; i < menuJson.menuItems.length; i++){
						var item = menuJson.menuItems[i];
						var menuItem = menu.addItem({
							name: item.name,
							text: item.alias,
							data: {
								title: item.alias,
								url: item.url,
								target: item.target,
								service: item.service,
								unique: item.unique,
								script: item.script,
								option: item.option
							}
				    	});
						if(item.subMenu != null){
							menuItem.addSubMenu(this.jsonToMenu(item.subMenu));
						}
					}
					return menu;
				},
				addMenus: function(menus){
					for(var i=0; i < menus.length; i++){
						var menu = this.jsonToMenu(menus[i]);
						
				    	this.addMenu(menu);
			    	}
				},
				/**
				 * @param menu xhcms.ui.Menu
				 */
				addMenu: function(menu){
					return this.menuManager.addMenu(menu);
				},
				/**
				 * @param menu xhcms.ui.Menu
				 */
				removeMenu: function(menu){
					return this.menuManager.removeMenu(menu);
				},
				triggerMenu: function(name){
					this.menuManager.triggerMenu(name);
				},
				getMenu: function(name){
					return this.menuManager.getMenu(name);
				},
				getMenuItem: function(name){
					return this.menuManager.getMenuItem(name);
				},
				bindMenuItem: function(item){
					this.menuManager._bindItem(item);
				},
				unbindMenuItem: function(item){
					this.menuManager._unbindItem(item);
				}
			});
		},
		addLauncher: function(option, menu){
			if(menu == null)
				throw "the second argument MUST be xhcms.ui.Menu";
			
			var launcher = this._super.addLauncher(option);
			this.addMenu(menu, launcher);
			return launcher;
		},
		removeLauncher: function(launcher){
			this.removeMenu(launcher._menu);
		},
		_bindItem: function(item){
			this._items[item.getName()] = item;
			//create service
			var service = new DefaultService(item.getData());
			item._service = service;
			//register as layout service
			if(item.getData().service){
				this.layout.registerService(item.getName(), service);
			}
			//click event
			item.addObserver(MenuItem.EVENT.Click, function(e){
				this._service.execute();
			});
			//sub menu
			if(item.getSubMenu() != null){
				this._bindMenu(item.getSubMenu());
			}
		},
		_unbindItem: function(item){
			this._items[item.getName()] = null;
			delete this._items[item.getName()];
			if(item.getSubMenu() != null){
				this._unbindMenu(item.getSubMenu());
			}
		},
		/**
		 * bind MenuItem actions
		 * @param menu
		 */
		_bindMenu: function(menu){
			this._menus[menu.getName()] = menu;
			var items = menu.getItems();
			for(var i = 0; i < items.length; i++){
				var item = items[i];
				this._bindItem(item);
			}
		},
		_unbindMenu: function(menu){
			this._menus[menu.getName()] = null;
			delete this._menus[menu.getName()];
			var items = menu.getItems();
			for(var i = 0; i < items.length; i++){
				var item = items[i];
				this._unbindItem(item);
			}
		},
		addMenu : function(menu, launcher){
			if(launcher == null){
				var option = {
						title: menu.getText(),
						appendTo: $("#menuList")[0]
				};
				launcher = this._super.addLauncher(option);
			}
			
			
			//bind menu item actions
			this._bindMenu(menu);
			
			//bind click event of launcher
			launcher.attachEvent({click:function(){
				var xy = launcher.getClientXY();
				menu.setXY(xy.x + 5, xy.y + launcher.getHeight() + 8);
				menu.show();
				return false;
			}});
			//attach launcher, menu each other
			launcher._menu = menu;
			menu._launcher = launcher;
			return launcher;
		},
		removeMenu : function(menu){
			var launcher = menu._launcher;
			//detach
			launcher._menu = null;
			menu._launcher = null;
			
			//remove menu and items
			this._unbindMenu(menu);
			menu.destroy();
			
			this._super.removeLauncher(launcher);
		},
		getTemplate : function(id, option){
			var template = '<span class="lcr_l"></span><span class="lcr_c" id="'+this.getTitleId(id)+'">'
					+ option.title +'</span><span class="lcr_l"></span>';
			
			return template;
		},
		getTitleId : function(id){
			return 'menu_title_'+id;
		},
		triggerMenu: function(name){
			if(this._items[name]){
				this._items[name].trigger(MenuItem.EVENT.Click);
			}
		},
		getMenu: function(name){
			return this._menus[name];
		},
		getMenuItem: function(name){
			return this._items[name];
		}
	};
	
	MenuManager = LauncherManager.extend(MenuManager.prototype);
	
	Layout.Addons.quickbarManager = QuickbarManager;
	Layout.Addons.statusbarManager = StatusbarManager;
	Layout.Addons.launcherbarManager = LauncherbarManager;
	Layout.Addons.taskbarManager = TaskbarManager;
	Layout.Addons.menuManager = MenuManager;
	
	this.LauncherManager = LauncherManager;
	this.LauncherbarManager = LauncherbarManager;
	this.StatusbarManager = StatusbarManager;
	this.QuickbarManager = QuickbarManager;
	this.TaskbarManager = TaskbarManager;
	this.MenuManager = MenuManager;
});



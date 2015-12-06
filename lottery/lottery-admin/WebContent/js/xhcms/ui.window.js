/**
 * xhcms.ui.IWindow
 */
Jet().$package("xhcms.ui", function(J){
	var $ = jQuery, $E = Jooe, _globalDragProxy=null;
	var Drag = this.Drag;
	var Resize = this.Resize;
	var _id = 0;
	var newId = function(){
		return _id++;
	};
	
	var createDragProxy = function() {
		var mask = $('<div class="dragMask"></div>');
		var proxy = $('<div class="dragProxy"></div>');
		proxy.appendTo(mask);
		mask.appendTo(document.body);
		return {
			maskEl : mask[0],
			proxyEl : proxy[0]
		};
	};
	var getDragProxy = function() {
		if (!_globalDragProxy) {
			_globalDragProxy = createDragProxy();
		}
		return _globalDragProxy;
	};
	
	var IWindow = function(){};
	IWindow.prototype = {
		_zIndex : 1,
		_inBorder : 5,
		_outBorder : 5,
		_windowFlag : 0,
		init : function(option) {
			option = this.parseOption(option);
			this.setPrivateProperty();
			if (typeof this._windowId == "undefined") {
				this._windowId = newId();
			}
			
			this.createDom();
			$(this._window_outer).css("zIndex", this.option.zIndex);
			this.createEvent();
		},
		parseOption : function() {
			throw "parseOption does not implement a required interface(Error in IWindow.parseOption())";
		},
		setPrivateProperty : function() {
			throw "setPrivateProperty does not implement a required interface(Error in IWindow.setPrivateProperty())";
		},
		getTemplate : function() {
			throw "getTemplate does not implement a required interface(Error in IWindow.getTemplate())";
		},
		initObserver : function() {
			var context = this;
			this.observer = {
				onMousedown : function() {
					$(context._dragProxy.proxyEl).css("left", context.getX() + context._outBorder + "px");
					$(context._dragProxy.proxyEl).css("top", context.getY() + context._outBorder + "px");
					$(context._dragProxy.proxyEl).css("width", context._width - context._outBorder * 2 + "px");
					$(context._dragProxy.proxyEl).css("height", context._height - context._outBorder * 2 + "px");
					$(context._dragProxy.maskEl).css("zIndex", 60000);
					$(context._dragProxy.proxyEl).css("zIndex", 60002);
					$(context._dragProxy.maskEl).show();
				},
				onMove : function(e, xy) {
					context._x = xy.x;
					context._y = xy.y;
				},
				onDragStart : function(e, wh) {
					context.trigger(IWindow.EVENT.DragStart, wh);
				},
				onDragEnd : function(e, wh) {
					context.trigger(IWindow.EVENT.DragEnd, wh);
				},
				onResize : function(e, wh) {
					if (wh.width) {
						context.setWidth(wh.width);
					}
					if (wh.height) {
						context.setHeight(wh.height);
					}
					context.trigger(IWindow.EVENT.Resize, context.getBodySize());
				},
				onDragProxyEnd : function(e, xy) {
					if (xy) {
						$(context._dragProxy.maskEl).css("zIndex", '');
						$(context._dragProxy.maskEl).hide();
						context.setXY(xy.x - context._outBorder, xy.y - context._outBorder);
					}
				},
				onDragProxyResizeEnd : function(e, rect) {
					$(context._dragProxy.maskEl).css("zIndex", '');
					$(context._dragProxy.maskEl).hide();
					context.setXY(rect.x - context._outBorder, rect.y - context._outBorder);
					var p = 1.1;
					var delay = 200;
					var loop = 5;
					var i = 0;
					var doStick = function() {
						i++;
						var r = context.getBodySize();
						var width = rect.width - r.width;
						var height = rect.height - r.height;
						context.setWidth(r.width + width * p + context._outBorder * 2);
						context.setHeight(r.height + height * p + context._outBorder * 2);
						context.trigger(IWindow.EVENT.Resize, context.getBodySize());
						if (i < loop && (width >= 5 || width <= -5)) {
							J.out("setting timeout " + width + " " + height + " " + i + " mostStick:" + loop, "IWindow");
							setTimeout(doStick, delay);
						} else {
							context.setWidth(rect.width + context._outBorder * 2);
							context.setHeight(rect.height + context._outBorder * 2);
							context.trigger(IWindow.EVENT.Resize, context.getBodySize());
						}
					};
					context.setWidth(rect.width + context._outBorder * 2);
					context.setHeight(rect.height + context._outBorder * 2);
					context.trigger(IWindow.EVENT.Resize, context.getBodySize());
				}
			};
		},
		initDomReference : function() {
			throw "initDomReference does not implement a required interface(Error in IWindow.initDomReference())";
		},
		initButtons : function() {
			throw "initButtons does not implement a required interface(Error in IWindow.initButtons())";
		},
		createEvent : function() {
			throw "createEvent does not implement a required interface(Error in IWindow.createEvent())";
		},
		getAppId : function() {
			return this._appId;
		},
		getWindowFlags : function() {
			return this._windowFlag;
		},
		setWindowFlags : function(flag) {
			this._windowFlag = flag;
		},
		createDom : function() {
			J.out("CreateDom", "IWindow");
			var id = this._windowId;
			this.getId = function() {
				return id;
			};
			
			var tmpl = this.getTemplate();
			if ($.browser.msie) {
				tmpl += '<iframe width="100%" height="100%" class="window_bg_iframe"></iframe>';
			}
			this.container = $('<div id="appWindow_"' + id +'" class="window"></div>');
			this.container.html(tmpl);
			this.container.appendTo(this.option.appendTo);
			this.container = this.container[0];//save the dom node
			
			this.initDomReference();
			J.out("initDomReference", "IWindow");
			this.initObserver();
			J.out("initObserver", "IWindow");
			this.initButtons();
			J.out("initButtons", "IWindow");
			this.setWidth(this._width);
			this.setHeight(this._height);
			if (this.option.x != null && this.option.y != null) {
				this.setXY(this.option.x, this.option.y);
			}
			this.setTitle(this.option.title);
			if (this.option.html) {
				this.setHtml(this.option.html);
			}
		},
		setTitle : function(title) {
			$(this._title).text(title);
		},
		setTitleHtml : function(title) {
			$(this._title).html(title);
		},
		showCloseButton : function() {
			this._closeButton.show();
		},
		showFullButton : function() {
			this._fullButton.show();
		},
		showMaxButton : function() {
			if (this._restoreButton) {
				this._restoreButton.hide();
			}
			this._maxButton.show();
		},
		showRestoreButton : function() {
			if (this._maxButton) {
				this._maxButton.hide();
			}
			this._restoreButton.show();
		},
		showMinButton : function() {
			this._minButton.show();
		},
		showRefreshButton : function() {
			this._refreshButton.show();
		},
		showPinUpButton : function() {
			this._pinDownButton.hide();
			this._pinUpButton.show();
		},
		showPinDownButton : function() {
			this._pinUpButton.hide();
			this._pinDownButton.show();
		},
		showControlButton : function(button) {
			$(this._controlArea).show();
			$(this.body).css("bottom", "26px");
			button.show();
		},
		showControlArea : function() {
			$(this._controlArea).show();
			$(this.body).css("bottom", "26px");
		},
		hideControlArea : function() {
			$(this._controlArea).hide();
			$(this.body).css("bottom", "0");
		},
		show : function() {
			$(this.container).show();
			J.out(">>>> Window: show", "IWindow");
			this.trigger(IWindow.EVENT.Show, this.getBodySize());
			this._isShow = true;
		},
		hide : function() {
			$(this.container).hide();
			this.trigger(IWindow.EVENT.Hide);
			this._isShow = false;
		},
		isShow : function() {
			return this._isShow;
		},
		toggleShow : function() {
			if (this.isShow()) {
				this.hide();
			} else {
				this.show();
			}
		},
		setCurrent : function() {
			this.setCurrentWithoutFocus();
			this.focus();
		},
		setNotCurrent : function() {
			this.setWindowFlags(this.getWindowFlags()
					& ~IWindow.CONST.WINDOW_FLAG_CURRENT
					| IWindow.CONST.WINDOW_FLAG_NOT_CURRENT);
			this.setStyleNotCurrent();
			this.trigger(IWindow.EVENT.SetNotCurrent);
		},
		setCurrentWithoutFocus : function() {
			if (!(this.getWindowFlags() & IWindow.CONST.WINDOW_FLAG_CURRENT)) {
				this.setStyleCurrent();
				this.show();
				this.setWindowFlags(this.getWindowFlags() | IWindow.CONST.WINDOW_FLAG_CURRENT);
			}
			this.trigger(IWindow.EVENT.SetCurrent);
		},
		setStyleCurrent : function() {
			$(this.container).addClass("window_current");
		},
		setStyleNotCurrent : function() {
			if (!this.container) {
				return;
			}
			$(this.container).removeClass("window_current");
		},
		focus : function() {
			this.trigger(IWindow.EVENT.Focus);
		},
		setBoxStatus : function(status) {
			this._status = status;
		},
		getBoxStatus : function() {
			return this._status;
		},
		adjustSize : function(marginWidth, marginHeight, clientWidth, clientHeight, x, y) {
			var status = this.getBoxStatus();
			if (x != null && y != null) {
				this.setXY(x, y);
			}
			if (status == IWindow.STATUS.MAX) {
				this.setWidth(clientWidth - x);
				this.setHeight(clientHeight - y);
				this.trigger(IWindow.EVENT.Resize, this.getBodySize());
			} else {
				if (status == IWindow.STATUS.FULLSCREEN) {
					this.setWidth(clientWidth);
					this.setHeight(clientHeight);
					this.trigger(IWindow.EVENT.Resize, this.getBodySize());
				}
			}
		},
		max : function() {
			if (!(this.getWindowFlags() & IWindow.CONST.WINDOW_FLAG_FULLSCREEN)) {
				this._restoreX = this._x;
				this._restoreY = this._y;
			}
			var status = this.getBoxStatus();
			this.setDisableDrag();
			this.setWindowFlags(this.getWindowFlags()
					& ~IWindow.CONST.WINDOW_FLAG_NORMAL
					| IWindow.CONST.WINDOW_FLAG_MAX);
			this.setBoxStatus(IWindow.STATUS.MAX);
			this.showRestoreButton();
			this._fullButton.show();
			this._restorefullButton.hide();
			this.trigger(IWindow.EVENT.Max, status);
			this.trigger(IWindow.EVENT.Resize, this.getBodySize());
		},
		fullscreen : function() {
			if (this.getWindowFlags()
					& IWindow.CONST.WINDOW_FLAG_NORMAL) {
				this._restoreX = this._x;
				this._restoreY = this._y;
			}
			this.setWindowFlags(this.getWindowFlags()
					| IWindow.CONST.WINDOW_FLAG_FULLSCREEN);
			this.setBoxStatus(IWindow.STATUS.FULLSCREEN);
			this._maxButton.hide();
			this._restoreButton.hide();
			this._fullButton.hide();
			this._restorefullButton.show();
			this.trigger(IWindow.EVENT.Fullscreen, this.getBodySize());
			this.trigger(IWindow.EVENT.Resize, this.getBodySize());
		},
		restorefull : function() {
			if (this.getWindowFlags()& IWindow.CONST.WINDOW_FLAG_NORMAL) {
				this.restore();
			} else {
				this.max();
			}
			this.setWindowFlags(this.getWindowFlags() & ~IWindow.CONST.WINDOW_FLAG_FULLSCREEN);
			this.trigger(IWindow.EVENT.Restorefull, this.getBoxStatus());
			this.trigger(IWindow.EVENT.Resize, this.getBodySize());
		},
		min : function() {
			var status = this.getBoxStatus();
			this.setWindowFlags(this.getWindowFlags()
					& ~IWindow.CONST.WINDOW_FLAG_CURRENT
					| IWindow.CONST.WINDOW_FLAG_NOT_CURRENT
					| IWindow.CONST.WINDOW_FLAG_MIN);
			if (!this.option.flashMode) {
				this.hide();
			}
			var newStatus = status || IWindow.STATUS.MIN;
			this.setBoxStatus(newStatus);
			this._isShow = false;
			this.trigger(IWindow.EVENT.Min);
			this.trigger(IWindow.EVENT.Resize, this.getBodySize());
		},
		restore : function() {
			this.setWindowFlags(this.getWindowFlags()
					& ~IWindow.CONST.WINDOW_FLAG_MAX
					| IWindow.CONST.WINDOW_FLAG_NORMAL);
			this.setXY(this._restoreX, this._restoreY);
			if (this._restoreWidth < 0) {
				this._restoreWidth = 0;
			}
			if (this._restoreHeight < 0) {
				this._restoreHeight = 0;
			}
			this.setWidth(this._restoreWidth);
			this.setHeight(this._restoreHeight);
			if (this._dragController) {
				this._dragController.lock();
			}
			this.setEnableDrag();
			if (this.option.hasMaxButton) {
				this.showMaxButton();
				this._fullButton.show();
				this._restorefullButton.hide();
			}
			this.setBoxStatus(IWindow.STATUS.RESTORE);
			this.trigger(IWindow.EVENT.Restore);
			this.trigger(IWindow.EVENT.Resize, this.getBodySize());
		},
		setZIndexLevel : function(zIndexLevel) {
			this.zIndexLevel = zIndexLevel;
		},
		getZIndexLevel : function() {
			return this.zIndexLevel;
		},
		setWidth : function(width) {
			$(this.container).width(width);
			$(this.body).width(width - 16);
			
			this._width = width;
			if (this.getBoxStatus() !== IWindow.STATUS.MAX
					&& this.getBoxStatus() !== IWindow.STATUS.FULLSCREEN) {
				this._restoreWidth = width;
			}
		},
		getWidth : function() {
			return this._width;
		},
		getHeight : function() {
			return this._height;
		},
		setHeight : function(height) {
			$(this.container).height(height);
			$(this._window_outer).height(height - 20);
			var j = 24;
			if ($.browser.msie && $.browser.versionNumber < 7) {
				j = 25;
			}
			if (this.option.hasOkButton) {
				this._bodyHeight = height - 47 - j;
			} else {
				this._bodyHeight = height - 20 - j;
			}
			$(this.body).height(this._bodyHeight);
			this._height = height;
			if (this.getBoxStatus() !== IWindow.STATUS.MAX
					&& this.getBoxStatus() !== IWindow.STATUS.FULLSCREEN) {
				this._restoreHeight = height;
			}
			this.trigger(IWindow.EVENT.SetNewHeight, height);
		},
		getBodyHeight : function() {
			return this._bodyHeight;
		},
		getZIndex : function() {
			return this._zIndex;
		},
		setZIndex : function(zIndex) {
			$(this.container).css("zIndex", zIndex);
			$(this._window_inner).css("zIndex", zIndex);
			this._zIndex = zIndex;
		},
		setXY : function(x, y) {
			if (x || x === 0) {
				this.setX(x);
			}
			if (y || y === 0) {
				this.setY(y);
			}
		},
		setX : function(x) {
			this._x = x;
			$(this.container).css("left", x + "px");
		},
		setY : function(y) {
			this._y = y;
			$(this.container).css("top", y + "px");
		},
		getX : function() {
			return parseInt($(this.container).css("left"));
		},
		getRestoreX : function() {
			return this._restoreX;
		},
		getRestoreY : function() {
			return this._restoreY;
		},
		getLeft : function() {
			return this._x;
		},
		getY : function(i) {
			return parseInt($(this.container).css("top"));
		},
		setLeft : function(left) {
			$(this.container).css("left", left + "px");
			$(this.container).css("right", "");
		},
		setTop : function(top) {
			$(this.container).css("top", top + "px");
			$(this.container).css("bottom", "");
		},
		setRight : function(right) {
			$(this.container).css("right", right + "px");
			$(this.container).css("left", "");
		},
		setBottom : function(bottom) {
			$(this.container).css("bottom", bottom + "px");
			$(this.container).css("top", "");
		},
		setWindowCentered : function() {
			this.trigger(IWindow.EVENT.SetCenter);
		},
		setWindowCenteredRelative : function(win) {
			var x = win.getX()
					+ ((win.getWidth() - this._width) / 2);
			this.setX(x);
		},
		enableDrag : function() {
			this.option.dragable = true;
			if (this.getBoxStatus() !== IWindow.STATUS.MAX
					&& this.getBoxStatus() !== IWindow.STATUS.FULLSCREEN) {
				this.setEnableDrag();
			}
		},
		disableDrag : function() {
			this.option.dragable = false;
			this.setDisableDrag();
		},
		enableDragProxy : function() {
			this.option.dragProxy = true;
		},
		disableDragProxy : function() {
			this.option.dragProxy = false;
		},
		setEnableDrag : function() {
			if (this.option.dragable) {
				if (this._dragController) {
					if (this.option.dragProxy) {
						$E.on(this.container, "mousedown", this.observer.onMousedown);
					}
					this._dragController.unlock();
				} else {
					if (this.option.dragProxy) {
						this._dragProxy = getDragProxy();
						$E.on(this.container, "mousedown", this.observer.onMousedown);
						this._dragController = new Drag(
								this.container,
								this._dragProxy.proxyEl,
								{
									isLimited : true,
									leftMargin : this._leftMargin + this._outBorder,
									topMargin : this._topMargin + this._outBorder,
									rightMargin : this._rightMargin + this._outBorder,
									bottomMargin : this._bottomMargin + this._outBorder
								});
						$E.addObserver(this._dragController, Drag.EVENT.End, this.observer.onDragProxyEnd);
					} else {
						this._dragController = new Drag(
								this.container,
								this.container,
								{
									isLimited : true,
									leftMargin : this._leftMargin,
									topMargin : this._topMargin,
									rightMargin : this._rightMargin,
									bottomMargin : this._bottomMargin
								});
						$E.addObserver(this._dragController, Drag.EVENT.Move, this.observer.onMove);
					}
					$E.addObserver(this._dragController, Drag.EVENT.Start, this.observer.onDragStart);
					$E.addObserver(this._dragController, Drag.EVENT.End, this.observer.onDragEnd);
				}
				this.setEnableResize();
			}
		},
		setDisableDrag : function() {
			if (this._dragController) {
				this._dragController.lock();
				if (this.option.dragProxy) {
					$E.off(this.container, "mousedown", this.observer.onMousedown);
				}
			}
			this.setDisableResize();
		},
		enableResize : function() {
			this.option.resize = true;
			if (this.getBoxStatus() !== IWindow.STATUS.MAX) {
				this.setEnableResize();
			}
		},
		disableResize : function() {
			this.option.dragable = false;
			this.setDisableResize();
		},
		setEnableResize : function() {
			if (this.option.resize) {
				if (this._resizeController) {
					if (this.option.dragProxy) {
						$E.addObserver(this._resizeController, Resize.EVENT.Mousedown, this.observer.onMousedown);
					}
					this._resizeController.show();
				} else {
					if (this.option.dragProxy) {
						this._dragProxy = getDragProxy();
						this._resizeController = new Resize(
								this._window_inner,
								this._dragProxy.proxyEl,
								{
									isLimited : true,
									leftMargin : this._leftMargin,
									topMargin : this._topMargin,
									rightMargin : this._rightMargin,
									bottomMargin : this._bottomMargin,
									minWidth : this._minWidth,
									minHeight : this._minHeight,
									dragProxy : this._dragProxy
								});
						$E.addObserver(this._resizeController, Resize.EVENT.Mousedown, this.observer.onMousedown);
						$E.addObserver(this._resizeController, Resize.EVENT.End, this.observer.onDragProxyResizeEnd);
					} else {
						this._resizeController = new Resize(
								this._window_inner,
								this.container,
								{
									isLimited : true,
									leftMargin : this._leftMargin,
									topMargin : this._topMargin,
									rightMargin : this._rightMargin,
									bottomMargin : this._bottomMargin,
									minWidth : this._minWidth,
									minHeight : this._minHeight
								});
						$E.addObserver(this._resizeController, Resize.EVENT.Resize, this.observer.onResize);
					}
					$E.addObserver(this._resizeController, Resize.EVENT.Mousedown, this.observer.onDragStart);
					$E.addObserver(this._resizeController, Resize.EVENT.End, this.observer.onDragEnd);
				}
			}
		},
		setDisableResize : function() {
			if (this._resizeController) {
				this._resizeController.hide();
				if (this.option.dragProxy) {
					$E.removeObserver(this._resizeController, Resize.EVENT.Mousedown, this.observer.onMousedown);
				}
			}
		},
		setLimite : function(i) {
			i = i || {};
			if (this.isLimited) {
				this._leftMargin = i.leftMargin;
				this._topMargin = i.topMargin;
				this._rightMargin = i.rightMargin;
				this._bottomMargin = i.bottomMargin;
			}
		},
		setHtml : function(html) {
			this.html = html;
			$(this.body).html(html);
		},
		append : function(i) {
			$(this.body).append(i);
		},
		getWindowId: function(){
			return this._windowId;
		},
		getSize : function() {
			return {
				width : $(this.container).innerWidth(),
				height : $(this.container).innerHeight()
			};
		},
		getBodySize : function() {
			return {
				width : $(this.body).width(),
				height : $(this.body).height()
			};
		},
		getSelfDomObj : function() {
			return this.container;
		},
		onClose: function(e, data){
			if(!e.isDefaultPrevented()){
				this.destroy();
			}
		},
		close : function() {
			$("#fullscreen_tip").hide();
			this.trigger(IWindow.EVENT.Close, [this]);
		},
		destroy : function() {
			this.trigger(IWindow.EVENT.Destroy, [this]);

			$(this.container).remove();
			for ( var i in this) {
				if (this.hasOwnProperty(i)) {
					delete this[i];
				}
			}
		}
	};
	IWindow = Jooe.extend(IWindow.prototype);
	IWindow.EVENT = {
		DragStart: "DragStart",
		DragEnd: "DragEnd",
		Resize: "Resize",
		Show: "Show",
		Hide: "Hide",
		SetCenter: "SetCenter",
		SetNotCurrent: "SetNotCurrent",
		SetCurrent: "SetCurrent",
		Focus: "Focus",
		Max: "Max",
		Fullscreen: "Fullscreen",
		Restorefull: "Restorefull",
		Min: "Min",
		Restore: "Restore",
		SetNewHeight: "SetNewHeight",
		Close: "Close",
		Destroy: "Destroy",
		CloseWindow: "CloseWindow"
	};
	IWindow.STATUS = {
		MIN: "MIN",
		MAX: "MAX",
		RESTORE: "RESTORE",
		FULLSCREEN: "FULLSCREEN"
	};
	IWindow.CONST = {
		WINDOW_FLAG_MIN : 1,
		WINDOW_FLAG_NORMAL : 2,
		WINDOW_FLAG_MAX : 4,
		WINDOW_FLAG_CURRENT : 8,
		WINDOW_FLAG_NOT_CURRENT : 16,
		WINDOW_FLAG_FULLSCREEN : 32
	};
	
	this.IWindow = IWindow;
});

/**
 * xhcms.ui.Window
 */
Jet().$package("xhcms.ui", function(J) {
	var $ = jQuery, $E = Jooe;
	var IWindow = this.IWindow;
	var Window = function(){};
	Window.prototype = {
		init : function(option) {
			this.windowType = "window";

			this._super.init(option);
		},
		parseOption : function(option) {
			option = option || {};
			option.type = option.type || "default";
			option.ieOnly = typeof option.ieOnly == "undefined" ? false : option.ieOnly;
			option.loginLevel = typeof option.loginLevel == "undefined" ? 1 : option.loginLevel;
			option.isTask = typeof option.isTask == "undefined" ? true	: option.isTask;
			option.width = option.width || 600;
			option.height = option.height || 450;
			option.minWidth = option.minWidth || 180;
			option.minHeight = option.minHeight || 100;
			option.zIndex = typeof option.zIndex != "undefined" ? option.zIndex : "99999";
			option.title = typeof option.title != "undefined" ? option.title : "窗口";
			option.html = option.html || "";
			option.modeSwitch = (option.modeSwitch == true) ? true : false;
			option.isSetCurrent = option.isSetCurrent ? option.isSetCurrent : true;
			option.defaultMode = option.defaultMode ? option.defaultMode : IWindow.STATUS.RESTORE;
			option.dragable = (option.dragable == true) ? true : false;
			option.resize = (option.resize == true) ? true : false;
			option.dragProxy = (option.dragProxy == true) ? true : false;
			option.isFixedZIndex = (option.isFixedZIndex == true) ? true : false;
			option.isSetCentered = (option.isSetCentered == true) ? true : false;
			option.hasCloseButton = (option.hasCloseButton == true) ? true : false;
			option.hasMaxButton = (option.hasMaxButton == true) ? true : false;
			option.hasRestoreButton = (option.hasRestoreButton == true) ? true : false;
			option.hasMinButton = (option.hasMinButton == true) ? true : false;
			option.hasRefreshButton = (option.hasRefreshButton == true) ? true : false;
			option.hasPinUpButton = (option.hasPinUpButton == true) ? true : false;
			option.hasPinDownButton = (option.hasPinDownButton == true) ? true : false;
			option.hasOkButton = (option.hasOkButton == true) ? true : false;
			option.hasCancelButton = (option.hasCancelButton == true) ? true : false;
			option.hasPreviousButton = (option.hasPreviousButton == true) ? true : false;
			option.hasNextButton = (option.hasNextButton == true) ? true : false;
			option.leftMargin = option.leftMargin || 0;
			option.topMargin = option.topMargin || 0;
			option.rightMargin = option.rightMargin || 0;
			option.bottomMargin = option.bottomMargin || 0;
			option.doubleClickModeSwitch = (option.doubleClickModeSwitch === false) ? false	: true;
			option.appendTo = option.appendTo;
			this.option = option;
			return option;
		},
		setPrivateProperty : function() {
			this.type = this.option.type;
			this._width = this.option.width;
			this._height = this.option.height;
			this._x = this.option.x;
			this._y = this.option.y;
			this._restoreWidth = this.option.width;
			this._restoreHeight = this.option.height;
			this._minWidth = this.option.minWidth;
			this._minHeight = this.option.minHeight;
			this._leftMargin = this.option.leftMargin;
			this._topMargin = this.option.topMargin;
			this._rightMargin = this.option.rightMargin;
			this._bottomMargin = this.option.bottomMargin;
			this._appId = this.option.appId;
			this._windowId = this.option.windowId;
		},
		getTemplate : function() {
			var id = this.getId();
			var template = '<div id="window_outer_'	+ id + '" class="win_outer">'
					+ '<div id="window_inner_' + id + '" class="win_inner" >'
					+ '<div class="win_bg"><div class="win_bg_c"></div><div class="win_bg_t"></div><div class="win_bg_r"></div><div class="win_bg_b"></div><div class="win_bg_l"></div><div class="win_bg_rt"></div><div class="win_bg_rb"></div><div class="win_bg_lb"></div><div class="win_bg_lt"></div></div>'
					+ '<div class="win_ctt"><div id="window_titleBar_' + id	+ '" class="win_ttBar">'
					+ '<div id="window_titleButtonBar_' + id + '" class="win_btnBox"></div>'
					+ '<div id="window_title_' + id +'" class="win_tt"></div></div>'
					+ '<div id="window_body_' + id + '" class="win_body"></div>' 
					+ '<div id="window_controlArea_'+ id + '" class="window_controlArea"></div>'
					+ '</div></div></div>';
			return template;
		},
		initObserver : function() {
			var context = this;
			this._super.initObserver();
			$.extend(this.observer,	{
				onCloseButtonClick : function(e) {
					context.close();
					e.preventDefault();
					e.stopPropagation();
				},
				stopPropagation : function(e) {
					e.stopPropagation();
				},
				onMaxButtonClick : function(e) {
					e.preventDefault();
					if (context.option.modeSwitch) {
						context.max();
					}
				},
				onRestorefullButtonClick : function(e) {
					e.preventDefault();
					if (context.option.modeSwitch) {
						context.restorefull();
					}
				},
				onFullButtonClick : function(e) {
					e.preventDefault();
					if (context.option.modeSwitch) {
						context.fullscreen();
					}
				},
				onRestoreButtonClick : function(e) {
					e.preventDefault();
					if (context.option.modeSwitch) {
						context.restore();
					}
				},
				onMinButtonClick : function(e) {
					e.preventDefault();
					if (context.option.modeSwitch) {
						context.min();
					}
				},
				onRefreshButtonClick : function(e) {
					e.preventDefault();
					context.trigger(Window.EVENT.ClickRefreshButton);
				},
				onHomeButtonClick : function(e) {
					e.preventDefault();
					context.trigger(Window.EVENT.ClickHomeButton);
				},
				onPinUpButtonClick : function(e) {
					e.preventDefault();
					context.trigger(Window.EVENT.ClickPinUpButton);
					context.showPinDownButton();
				},
				onPinDownButtonClick : function(f) {
					f.preventDefault();
					context.trigger(Window.EVENT.ClickPinDownButton);
					context.showPinUpButton();
				},
				onOkButtonClick : function(e) {
					e.preventDefault();
					context.trigger(Window.EVENT.ClickOkButton);
				},
				onCancelButtonClick : function(f) {
					f.preventDefault();
					context.trigger(Window.EVENT.ClickCancelButton);
				},
				onPreviousButtonClick : function(f) {
					f.preventDefault();
					context.trigger(Window.EVENT.ClickPreviousButton);
				},
				onNextButtonClick : function(f) {
					f.preventDefault();
					context.trigger(Window.EVENT.ClickNextButton);
				},
				onMouseoverWindow : function(f) {
					f.stopPropagation();
					context.trigger(Window.EVENT.MouseoverWindow);
				},
				onMouseoutWindow : function(f) {
					f.stopPropagation();
					context.trigger(Window.EVENT.MouseoutWindow);
				},
				onMousedownWindow : function(f) {
					if (context) {
						context.setCurrent();
					}
				},
				onKeyDownWindow : function(f) {
				},
				onTitleBarDblClick : function(f) {
					if (context.option.doubleClickModeSwitch) {
						f.preventDefault();
						context.trigger(Window.EVENT.DblClickTitleBar);
						if (context.option.modeSwitch) {
							if (context.getBoxStatus() === IWindow.STATUS.MAX) {
								context.restore();
							} else {
								if (context.getBoxStatus() === IWindow.STATUS.RESTORE) {
									context.max();
								}
							}
						}
					}
				},
				setCurrent : function(f) {
					context.setCurrent();
				},
				stopPropagationAndSetCurrent : function(f) {
					f.stopPropagation();
					context.setCurrent();
				},
				stopPropagationAndSetCurrentWithoutFocus : function(f) {
					f.stopPropagation();
					context.setCurrentWithoutFocus();
				}
			});
		},
		ClickCancelButton: function(){
			var context = this;
			setTimeout(function() {
				context.close();
			}, 0);
		},
		ClickOkButton: function(){
			var context = this;
			setTimeout(function() {
				context.close();
			}, 0);
		},
		initDomReference : function() {
			var id = this.getId();
			this._titleBar = $("#window_titleBar_" + id)[0];
			this._titleButtonBar = $("#window_titleButtonBar_" + id)[0];
			this._controlArea = $("#window_controlArea_" + id)[0];
			this._title = $("#window_title_" + id)[0];
			this.body = $("#window_body_" + id)[0];
			this._window_outer = $("#window_outer_" + id)[0];
			this._window_inner = $("#window_inner_" + id)[0];
		},
		initButtons : function() {
			var context = this;
			var Button = xhcms.ui.Button;
			var g = this._titleButtonBar;
			if (this.option.hasCloseButton) {
				this._closeButton = new Button(
						{
							appendTo : g,
							className : "window_action_button window_close",
							isStopPropagation : true,
							title : "关闭",
							event : {
								click : context.observer.onCloseButtonClick
							}
						});
				this._closeButton.show();
			}
			if (this.option.hasMaxButton) {
				this._maxButton = new Button(
						{
							appendTo : g,
							className : "window_action_button window_max",
							isStopPropagation : true,
							title : "最大化",
							event : {
								click : context.observer.onMaxButtonClick,
								mousedown : context.observer.setCurrent
							}
						});
				this._maxButton.show();
				this._restoreButton = new Button(
						{
							appendTo : g,
							className : "window_action_button window_restore",
							isStopPropagation : true,
							title : "还原",
							event : {
								click : context.observer.onRestoreButtonClick,
								mousedown : context.observer.setCurrent
							}
						});
				this._restoreButton.hide();
			}
			if (this.option.hasMinButton) {
				this._minButton = new Button(
						{
							appendTo : g,
							className : "window_action_button window_min",
							isStopPropagation : true,
							title : "最小化",
							event : {
								click : context.observer.onMinButtonClick
							}
						});
				this._minButton.show();
			}
			if (this.option.hasMaxButton) {
				this._fullButton = new Button(
						{
							appendTo : g,
							className : "window_action_button window_fullscreen",
							isStopPropagation : true,
							title : "全屏",
							event : {
								click : context.observer.onFullButtonClick,
								mousedown : context.observer.setCurrent
							}
						});
				this._restorefullButton = new Button(
						{
							appendTo : g,
							className : "window_action_button window_restore_full",
							isStopPropagation : true,
							title : "退出全屏",
							event : {
								click : context.observer.onRestorefullButtonClick,
								mousedown : context.observer.setCurrent
							}
						});
				this._restorefullButton.hide();
			}
			if (this.option.hasRefreshButton) {
				this._refreshButton = new Button(
						{
							appendTo : g,
							className : "window_action_button window_refresh",
							isStopPropagation : true,
							title : "刷新",
							event : {
								click : context.observer.onRefreshButtonClick,
								mousedown : context.observer.setCurrent
							}
						});
				this._refreshButton.show();
			}
			if (this.option.hasHomeButton) {
				this._homeButton = new Button(
						{
							appendTo : g,
							className : "window_action_button window_home",
							isStopPropagation : true,
							title : "主页",
							event : {
								click : context.observer.onHomeButtonClick,
								mousedown : context.observer.setCurrent
							}
						});
				this._homeButton.show();
			}
			if (this.option.hasPinUpButton) {
				this._pinUpButton = new Button(
						{
							appendTo : g,
							className : "window_action_button window_pinUp",
							isStopPropagation : true,
							title : "置顶",
							event : {
								click : context.observer.onPinUpButtonClick,
								mousedown : context.observer.setCurrent
							}
						});
				this._pinDownButton = new Button(
						{
							appendTo : g,
							className : "window_action_button window_pinDown",
							isStopPropagation : true,
							title : "浮动",
							event : {
								click : context.observer.onPinDownButtonClick,
								mousedown : context.observer.setCurrent
							}
						});
				this._pinUpButton.show();
				this._pinDownButton.hide();
			}
			g = this._controlArea;
			$(this._controlArea).hide();
			if (this.option.hasCancelButton) {
				this._cancelButton = new Button(
						{
							appendTo : g,
							className : "window_button window_cancel",
							isStopPropagation : true,
							text : "取消",
							event : {
								click : context.observer.onCancelButtonClick
							}
						});
				this.showControlButton(this._cancelButton);
			}
			if (this.option.hasOkButton) {
				this._okButton = new Button(
						{
							appendTo : g,
							className : "window_button window_ok",
							isStopPropagation : true,
							text : "确定",
							event : {
								click : context.observer.onOkButtonClick
							}
						});
				this.showControlButton(this._okButton);
			}
			if (this.option.hasNextButton) {
				this._nextButton = new Button(
						{
							appendTo : g,
							className : "window_button window_next",
							isStopPropagation : true,
							text : "下一步",
							event : {
								click : context.observer.onNextButtonClick
							}
						});
				this.showControlButton(this._nextButton);
			}
			if (this.option.hasPreviousButton) {
				this._previousButton = new Button(
						{
							appendTo : g,
							className : "window_button window_previous",
							isStopPropagation : true,
							text : "上一步",
							event : {
								click : context.observer.onPreviousButtonClick
							}
						});
				this.showControlButton(this._previousButton);
			}
		},
		createEvent : function() {
			if (this.option.dragProxy) {
				this.enableDragProxy();
			}
			if (this.option.dragable) {
				this.enableDrag();
			}
			if (this.option.resize) {
				this.enableResize();
			}
			$E.on(this.container, "mousedown",
					this.observer.onMousedownWindow);
			$E.on(this.container, "keydown",
					this.observer.onKeyDownWindow);
			$E.on(this.body, "mousedown",
					this.observer.stopPropagationAndSetCurrent);
			$E.on(this._titleBar, "dblclick",
					this.observer.onTitleBarDblClick);
			this.addObserver(IWindow.EVENT.CloseWindow, this.close);
		}
	};
	Window = IWindow.extend(Window.prototype);
	Window.EVENT = {
		ClickHomeButton: "ClickHomeButton",
		ClickRefreshButton: "ClickRefreshButton",
		ClickPinUpButton: "ClickPinUpButton",
		ClickPinDownButton: "ClickPinDownButton",
		ClickOkButton: "ClickOkButton",
		ClickCancelButton: "ClickCancelButton",
		ClickPreviousButton: "ClickPreviousButton",
		ClickNextButton: "ClickNextButton",
		MouseoverWindow: "MouseoverWindow",
		MouseoutWindow: "MouseoutWindow",
		DblClickTitleBar: "DblClickTitleBar"
	};
	Window.EVENT = $.extend(Window.EVENT, IWindow.EVENT);
	
	this.Window = Window;
});

/**
 * xhcms.ui.Widget
 */
Jet().$package("xhcms.ui", function(J) {
	var $ = jQuery, $E = Jooe;
	var IWindow = this.IWindow;
	var Widget = function(){};
	Widget.prototype = {
		init : function(option) {
			this.windowType = "widget";
			this._super.init(option);
			if (this.option.hasPinUpButton) {
				this.trigger(Widget.EVENT.ClickPinUpButton);
			}
		},
		parseOption : function(option) {
			option = option || {};
			option.isTask = typeof option.isTask == "undefined" ? true : option.isTask;
			option.windowMode = option.windowMode || "single";
			option.width = option.width > 0 ? option.width : 0;
			option.height = option.height > 0 ? option.height : 0;
			option.dragable = (option.dragable === false) ? false : true;
			option.title = option.title || "";
			option.pinUpStyle = option.pinUpStyle || "default-class";
			option.pinDownStyle = option.pinDownStyle || "default-class";
			option.closeStyle = option.closeStyle || "default-class";
			option.hasCloseButton = (option.hasCloseButton === true) ? true : false;
			option.hasMinButton = (option.hasMinButton === true) ? true : false;
			option.hasRefreshButton = (option.hasRefreshButton === true) ? true : false;
			option.hasPinUpButton = (option.hasPinUpButton === true) ? true : false;
			option.hasPinDownButton = (option.hasPinDownButton === true) ? true : false;
			option.isFix = option.isFix || false;
			this._x = option.x;
			this._y = option.y;
			this._width = option.width;
			option.x = option.x > 0 ? parseInt(option.x) : 0;
			option.y = option.y > 0 ? parseInt(option.y) : 0;
			option.isSetCurrent = typeof option.isSetCurrent == "undefined" ? true : option.isSetCurrent;
			option.leftMargin = option.leftMargin || 0;
			option.topMargin = option.topMargin || 0;
			option.rightMargin = option.rightMargin || 0;
			option.bottomMargin = option.bottomMargin || 0;
			option.appendTo = option.appendTo ? option.appendTo : document.body;
			this.option = option;
			return option;
		},
		setPrivateProperty : function() {
			this.type = this.option.type;
			this._isFix = this.option.isFix;
			this._x = this.option.x;
			this._y = this.option.y;
			this._width = this.option.width;
			this._height = this.option.height;
			this._pinUpStyle = this.option.pinUpStyle;
			this._pinDownStyle = this.option.pinDownStyle;
			this._closeStyle = this.option.closeStyle;
			this._leftMargin = this.option.leftMargin;
			this._topMargin = this.option.topMargin;
			this._rightMargin = this.option.rightMargin;
			this._bottomMargin = this.option.bottomMargin;
			this._appId = this.option.appId;
			this._windowId = this.option.windowId;
		},
		getTemplate : function() {
			var id = this.getId();
			var template = '<div id="widget_outer_'	+ id + '" class="win_outer">'
					+ '<div id="widget_inner_' + id + '" class="win_inner" style="z-index:' + this.option.zIndex + '">'
					+ '<div class="win_bg" id="widget_bg_container_'+ id +'"><div class="win_bg_c"></div><div class="win_bg_t"></div><div class="win_bg_r"></div><div class="win_bg_b"></div><div class="win_bg_l"></div><div class="win_bg_rt"></div><div class="win_bg_rb"></div><div class="win_bg_lb"></div><div class="win_bg_lt"></div></div>'
					+ '<div class="win_ctt"><div id="widget_titleBar_' + id	+ '" class="win_ttBar">'
					+ '<div id="widget_titleButtonBar_' + id + '" class="win_btnBox"></div>'
					+ '<div id="widget_title_' + id +'" class="win_tt"></div></div>'
					+ '<div id="widget_body_' + id + '" class="win_body"></div>' 
					+ '</div></div></div>';
			return template;
		},
		initObserver : function() {
			var context = this;
			this._super.initObserver();
			$.extend(this.observer, {
				onMouseoverWindow : function(e) {
					e.stopPropagation();
					$(context._titleBar).show();
					$(context._bg_container).show();
					context.trigger(Widget.EVENT.MouseoverWindow, [context]);
				},
				onMouseoutWindow : function(e) {
					e.stopPropagation();
					$(context._titleBar).hide();
					$(context._bg_container).hide();
					context.trigger(Widget.EVENT.MouseoutWindow, [context]);
				},
				setCurrent : function() {
					context.setCurrent();
				},
				onMousedownWidget : function(e) {
					context._offX = e.clientX;
					context._offY = e.clientY;
				},
				onMouseupWidget : function(e) {
					if (Math.abs(e._offX - e.clientX)
							+ Math.abs(e._offY - e.clientY) < 10) {
						context.trigger(Widget.EVENT.ShortMoveClick, [context]);
					}
				},
				onPinDownButtonClick : function(e) {
					e.preventDefault();
					context.showPinUpButton();
					context.trigger(Widget.EVENT.ClickPinUpButton, [context]);
				},
				onPinUpButtonClick : function(e) {
					e.preventDefault();
					context.showPinDownButton();
					context.trigger(Widget.EVENT.ClickPinDownButton, [context]);
				},
				onRefreshButtonClick : function(e) {
					e.preventDefault();
					context.trigger(Widget.EVENT.ClickRefreshButton, [context]);
				},
				onHomeButtonClick : function(e) {
					e.preventDefault();
					context.trigger(Widget.EVENT.ClickHomeButton);
				},
				onCloseButtonClick : function(e) {
					e.preventDefault();
					context.trigger(Widget.EVENT.ClickCloseButton, [context]);
					setTimeout(function() {
						context.close();
					}, 0);
				},
				onMinButtonClick : function(e) {
					e.preventDefault();
					if (context.option.modeSwitch) {
						context.min();
					}
				}
			});
		},
		initDomReference : function() {
			var id = this.getId();
			this._bg_container = $("#widget_bg_container_" + id)[0];
			this._titleBar = $("#widget_titleBar_" + id)[0];
			this._titleButtonBar = $("#widget_titleButtonBar_" + id)[0];
			this._title = $("#widget_title_" + id)[0];
			this.body = $("#widget_body_" + id)[0];
			this._window_outer = $("#widget_outer_" + id)[0];
		},
		initButtons : function() {
			var context = this;
			var Button = xhcms.ui.Button;
			var g = this._titleButtonBar;
			if (this.option.hasCloseButton) {
				this._closeButton = new Button(
						{
							appendTo : g,
							className : "window_action_button window_close",
							isStopPropagation : true,
							title : "关闭",
							event : {
								click : context.observer.onCloseButtonClick
							}
						});
				this._closeButton.show();
			}
			if (this.option.hasMinButton) {
				this._minButton = new Button(
						{
							appendTo : g,
							className : "window_action_button window_min",
							isStopPropagation : true,
							event : {
								click : context.observer.onMinButtonClick
							}
						});
				this._minButton.show();
			}
			if (this.option.hasRefreshButton) {
				this._refreshButton = new Button(
						{
							appendTo : g,
							className : "window_action_button window_refresh",
							isStopPropagation : true,
							title : "刷新",
							event : {
								click : context.observer.onRefreshButtonClick,
								mousedown : context.observer.setCurrent
							}
						});
				this._refreshButton.show();
			}
			if (this.option.hasHomeButton) {
				this._homeButton = new Button(
						{
							appendTo : g,
							className : "window_action_button window_home",
							isStopPropagation : true,
							title : "主页",
							event : {
								click : context.observer.onHomeButtonClick,
								mousedown : context.observer.setCurrent
							}
						});
				this._homeButton.show();
			}
			if (this.option.hasPinUpButton
					|| this.option.hasPinDownButton) {
				this._pinUpButton = new Button(
						{
							appendTo : g,
							className : "window_action_button window_pinUp",
							isStopPropagation : true,
							title : "置顶",
							event : {
								click : context.observer.onPinUpButtonClick,
								mousedown : context.observer.setCurrent
							}
						});
				this._pinDownButton = new Button(
						{
							appendTo : g,
							className : "window_action_button window_pinDown",
							isStopPropagation : true,
							title : "浮动",
							event : {
								click : context.observer.onPinDownButtonClick,
								mousedown : context.observer.setCurrent
							}
						});
				if (this.option.hasPinUpButton) {
					this._pinUpButton.show();
					this._pinDownButton.hide();
				}
				if (this.option.hasPinDownButton) {
					this._pinUpButton.hide();
					this._pinDownButton.show();
				}
			}
		},
		createEvent : function() {
			this.setEnableDrag();
			$E.on(this.body, "mousedown", this.observer.onMousedownWidget);
			$E.on(this.body, "mouseup", this.observer.onMouseupWidget);
			$E.on(this.container, "mousedown", this.observer.setCurrent);
			$E.on(this.container, "mouseover", this.observer.onMouseoverWindow);
			$E.on(this.container, "mouseout", this.observer.onMouseoutWindow);
			this.addObserver(IWindow.EVENT.CloseWindow, this.close);
		},
		setStyleCurrent : function() {
			$(this.container).addClass("widget_current");
		},
		setStyleNotCurrent : function() {
			$(this.container).removeClass("widget_current");
		}
	};
	Widget = IWindow.extend(Widget.prototype);
	Widget.EVENT = {
		ClickHomeButton: "ClickHomeButton",
		ClickCloseButton: "ClickCloseButton",
		ClickRefreshButton: "ClickRefreshButton",
		ClickPinUpButton: "ClickPinUpButton",
		ClickPinDownButton: "ClickPinDownButton",
		MouseoverWindow: "MouseoverWindow",
		MouseoutWindow: "MouseoutWindow",
		DblClickTitleBar: "DblClickTitleBar",
		ShortMoveClick: "ShortMoveClick"
	};
	Widget.EVENT = $.extend(Widget.EVENT, IWindow.EVENT);
	
	this.Widget = Widget;
});

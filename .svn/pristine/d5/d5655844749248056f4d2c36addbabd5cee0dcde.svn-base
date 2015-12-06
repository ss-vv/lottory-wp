/**
 * 模块xhcms.ui.Panel
 * @param option {id, name, container, body, html}
 * 
 * @example
 * 
 */
Jet().$package("xhcms.ui", function(J) {
	var $ = jQuery, $E = Jooe;
	var Panel = function(){};
	Panel.prototype = {
		init : function(option) {
			option = option || {};
			this.id = option.id;
			this.name = option.name;
			this.container = option.container;
			this.body = option.body || option.container;
			option.html = option.html || "";
			this._zIndex = this._zIndex || $(this.container).css("zIndex");
			if (option.html) {
				this.setHtml(option.html);
			}
			//FIXME show or hide the panel when init ?
			/*
			if ($(this.container).css("display") != "none") {
				this.show();
			} else {
				this.hide();
			}
			*/
		},
		setHtml : function(html) {
			if(this.body){
				this.html = html;
				$(this.body).html(html);
			}
		},
		append : function(child) {
			this.body && $(this.body).append(child);
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
		show : function() {
			$(this.container).show();
			this.trigger(Panel.EVENT.Show, this.getBodySize());
			this._isShow = true;
		},
		hide : function() {
			$(this.container).hide();
			this.trigger(Panel.EVENT.Hide);
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
		getZIndex : function() {			
			return this._zIndex;
		},
		setZIndex : function(zIndex) {
			$(this.container).css("zIndex", zIndex);
			this._zIndex = zIndex;
		},
		setXY : function(x, y) {
			this.setX(x);
			this.setY(y);
		},
		setX : function(left) {
			$(this.container).css("left", left + "px");
		},
		setY : function(top) {
			$(this.container).css("top", top + "px");
		},
		setWidth : function(width) {
			$(this.container).width(width);
		},
		getWidth : function() {
			return $(this.container).width();;
		},
		setHeight : function(height) {
			$(this.container).height(height);
		},
		getHeight : function() {
			$(this.container).height();
		}
	};
	Panel = Jooe.extend(Panel.prototype);
	Panel.EVENT = {
		Show: "Show",
		Hide: "Hide"
	};
	this.Panel = Panel;
});

/**
 * 模块xhcms.ui.PopupBox
 * @param option {id, name, container, body, html, noCatchMouseUp, noCloseOnEsc, }
 * 
 * @example
 * 
 */
Jet().$package("xhcms.ui", function(J) {
	var $ = jQuery, $E = Jooe, instance = null;
	var Panel = this.Panel;
	var PopupBox = function(){};
	PopupBox.prototype = {		
		_enableMultiInstance : false,
		init : function(option) {
			var popup = this;
			this.catchMouseUp = true;
			if (option.noCatchMouseUp) {
				this.catchMouseUp = false;
			}
			this.closeOnEsc = true;
			if (option.noCloseOnEsc) {
				this.closeOnEsc = false;
			}
			this.onDocumentKeydown = function(e) {
				if (e.keyCode === 27) {
					e.preventDefault();
					popup.hide();
				}
			};
			this.onMouseUp = function() {
				if (popup.isShow()) {
					popup.hide();
				}
			};
			this.onDocumentClick = function() {
				popup.hide();
			};
			this.onWindowResize = function() {
				popup.hide();
			};
			
			this._super.init(option);
		},
		enableMultiInstance: function(){
			this._enableMultiInstance = true;
		},
		disableMultiInstance: function(){
			this._enableMultiInstance = false;
		},
		show : function() {
			if (this._enableMultiInstance == false && instance) {
				instance.hide();
			}
			if (this.catchMouseUp) {
				$E.on(document, "mouseup", this.onMouseUp);
			} else {
			}
			if (this.closeOnEsc) {
				$E.on(document, "keydown", this.onDocumentKeydown);
			} else {
			}
			$E.on(document, "click", this.onDocumentClick);
			$E.on(window, "resize", this.onWindowResize);
			if (this._enableMultiInstance == false)
				instance = this;
			this._super.show();
		},
		hide : function() {
			$E.off(document, "click", this.onDocumentClick);
			$E.off(document, "keydown", this.onDocumentKeydown);
			$E.off(window, "resize", this.onWindowResize);
			$E.off(document, "mouseup", this.onMouseUp);
			if (this._enableMultiInstance == false && instance) {
				if (instance !== this) {
					instance.hide();
				}
				instance = null;
			}
			this._super.hide();
		}
	};
	PopupBox = Panel.extend(PopupBox.prototype);
	PopupBox.EVENT = {};
	$.extend(PopupBox.EVENT, Panel.EVENT);
	this.PopupBox = PopupBox;
});

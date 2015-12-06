/**
 * 	xhcms.ui.MessageBox
 *	xhcms.ui.MessageBox.Alert
 *	xhcms.ui.MessageBox.Confirm
 */
Jet().$package("xhcms.ui", function(J) {
	var $ = jQuery, $E = Jooe, windows = [], Layout = null,
	
	observer = {
		onWindowClose : function() {
			MessageBox._globalMask && MessageBox._globalMask.hide();
			windows.length > 0 && windows.shift().show();
		}
	};
	var MessageBox = function(){};
	MessageBox.prototype = {
		_className : "ui_messageBox",
		init : function(option) {
			option = option || {};	
			Layout = option.layout || xhcms.desk.layout;
			var j = {
				title : "\u6e29\u99a8\u63d0\u793a",
				modeSwitch : true,
				dragable : true,
				resize : false,
				width : 370,
				height : 127,
				innerHtml : "",
				hasCloseButton : true,
				isSetCentered : true,
				modal : false,
				bodyBorder : 1,
				lineHeight : "inherit",
				background : "none repeat scroll 0 0 #FFFFFF",
				level : 3
			};
			$.extend(j, option);
			this.Window = Layout.getWindowManager().createWindow("Window", j);
			var id = this.Window.getId();
			var p = "text-align: center;line-height: " + j.lineHeight
					+ ";background:" + j.background + ";";
			if (j.bodyBorder)
				p += "border:" + j.bodyBorder + "px solid #AAAAAA;";
			this.Window.setHtml('<div class="' + this._className
					+ '" id="ui_messageBox_' + id + '" style="' + p
					+ '"></div>');
			this._uiMessageBox = $D.id("ui_messageBox_" + id);
			this._uiMessageBox.innerHTML = j.innerHtml;
			$D.setStyle(this._uiMessageBox, "height", this.Window
					.getBodyHeight()
					- j.bodyBorder * 2 + "px");
			$E.addObserver(this.Window, "close", observer.onWindowClose);
			if (j.modal) {
				this.modal = true;
				if (windows.length > 0) {
					this.Window.hide();
					windows.push(this.Window);
				} else
					this.show();
			} else
				this.show();
		},
		show : function() {
			var h = this.Window.getZIndexLevel();
			if (this.modal) {
				if (!MessageBox._globalMask)
					MessageBox._globalMask = Layout.getMaskLayer();
				MessageBox._globalMask.setZIndex(Layout.getTopZIndex(h));
				MessageBox._globalMask.show();
			}
			this.Window.setZIndex(Layout.getTopZIndex(h));
			this.Window.show();
		}
	};
	MessageBox = Jooe.extend(MessageBox.prototype);
	var Alert = function(){};
	Alert.prototype = {
		init : function(option) {
			option = option || {};	
			Layout = option.layout || xhcms.desk.layout;
			var j = {
				lineHeight : "50px",
				hasOkButton : true,
				autoClose : true
			};
			if (option.innerHtml.length > 34)
				option.lineHeight = "25px";
			J.extend(j, option);
			this._super.init(j);
			j.onAccept
					&& $E.addObserver(this.Window, "clickOkButton",
							function() {
								j.onAccept.apply(this);
								j.autoClose && this.close();
							});
		}
	};
	Alert = MessageBox.extend(Alert.prototyp);
	
	var Confirm = function(){};
	Confirm.prototype = {
		init : function(option) {
			option = option || {};	
			Layout = option.layout || xhcms.desk.layout;
			var j = {
				lineHeight : "50px",
				hasOkButton : true,
				hasCancelButton : true,
				autoClose : true
			};
			if (option.innerHtml.length > 34)
				option.lineHeight = "25px";
			$.extend(j, option);
			this._super.init(j);
			var p = false;
			j.onAccept
					&& $E.addObserver(this.Window, "clickOkButton",
							function() {
								j.onAccept.apply(this);
								p = true;
								j.autoClose && this.close();
							});
			j.onCancel
					&& $E.addObserver(this.Window, "close", function() {
						p || j.onCancel.apply(this);
					});
		}
	};
	Confirm = MessageBox.extend(Confirm.prototype);
	
	this.MessageBox = MessageBox;
	this.MessageBox.Alert = Alert;
	this.MessageBox.Confirm = Confirm;
});

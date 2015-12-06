Jet().$package("xhcms.cms", function(J){
	var $ = jQuery, $E = Jooe, Window = xhcms.ui.Window;
	var _wid = 0;
	
	var editor = function(){};
	editor.prototype = {
		init: function(option){
			var context = this;
			
			var opt = {
				title : "编辑属性",
				appendTo : document.body,
				zIndex:  99999,
				dragable: true,
				width: 400,
				height: 200,
				hasCloseButton: true,
				hasOkButton: true,
				hasCancelButton: true
			};
			$.extend(opt, option);
			
			this.option = opt;
			
			var observer = this.observer;
			this.observer = {
					close: function(){
						context.close();
					},
					clickOkButton: function(e){
						context.clickOkButton();
						return false;
					}
				};
			
			$.extend(this.observer, observer);
			
			this.createDom();
			this.initData();
		},
		show: function(){
			this._editor.show();
		},
		hide: function(){
			this._editor.hide();
		},
		close: function(){
			this.trigger(editor.EVENT.Close, this);
			//remove mask
			$(this._mask).remove();
			for ( var i in this) {
				if (this.hasOwnProperty(i)) {
					delete this[i];
				}
			}
		},
		ClickOkButton: function(){
			var context = this;
			setTimeout(function(){context._editor.close();}, 0);
		},
		clickOkButton: function(){
			this.trigger(editor.EVENT.ClickOkButton, [this]);
		},
		createDom: function(){
			this._editor = new Window(this.option);
			this._editorId = this._editor.getId();
			var html = '<div class="editor"><div class="editor_inner">'+this.getTemplate(this._editorId)+'</div></div>';
			
			this._editor.setHtml(html);
			
			this.body = $("div.editor_inner", this._editor.body)[0];
			
			//create mask div
			var mask = $('<div class="editor_mask" style="z-index: 100000;top: 0px; left: 0px;"></div>');
			mask.appendTo(this._editor.container);
			mask.hide();
			this._mask = mask[0];
			
			//init position
			var x = 0, y = 0;
			var appendTo = (this.option.appendTo === document.body ? window : this.option.appendTo);
			x = $(appendTo).width()/2 - this._editor.getWidth()/2 + $(appendTo).scrollLeft();
			y = $(appendTo).height()/2 - this._editor.getHeight()/2 + $(appendTo).scrollTop();
			y = (y < 0?0:y);
			this._editor.setXY(x,y);
			this._editor.setCurrent();
			if(this.option.zIndex){
				this.setZIndex(this.option.zIndex);
			}
			
			this._editor.addObserver(Window.EVENT.Close, this.observer.close);
			this._editor.addObserver(Window.EVENT.ClickOkButton, this.observer.clickOkButton);
			
		},
		getTemplate: function(id){
			var tpl = '<div style="position: absolute; top: 50%; left: 50%; "><div class="loading32"></div></div>';
			return tpl;
		},
		initData: function(){
			
		},
		setZIndex: function(zIndex){
			this._editor.setZIndex(zIndex);
		},
		getWindow: function(){
			return this._editor;
		},
		setActive: function(){
			$(this._mask).hide();
			//this._editor.setCurrent();
		},
		setInActive: function(){
			$(this._mask).height(this.getHeight());
			$(this._mask).width(this.getWidth());
			$(this._mask).show();
			
			//this._editor.setNotCurrent();
		},
		setHeight: function(height){
			this._editor.setHeight(height);
		},
		setWidth:function(width){
			this._editor.setWidth(width);
		},
		getHeight: function(){
			return this._editor.getHeight();
		},
		getWidth:function(){
			return this._editor.getWidth();
		},
		setXY: function(x, y){
			this._editor.setXY(x,y);
		},
		getX: function(){
			return this._editor.getX();
		},
		getY: function(){
			return this._editor.getY();
		},
		setTitle: function(title){
			this._editor.setTitle(title);
		},
		setHtml: function(html){
			this.body.innerHTML = html;
		},
		appendChild: function(node){
			$(this.body).append(node);
		},
		removeChild: function(node){
			$(node).remove();
		},
		emptyText: function(v){
			if(typeof(v) === 'undefined' || v == null){
				return '';
			}
			return v;
		}
	};
	editor = Jooe.extend(editor.prototype);
	editor.EVENT = {
		Close: "Close",
		ClickOkButton: "ClickOkButton"
	};
	
	var processBox = function(){};
	processBox.prototype = {
		init: function(option){
			var context = this;
						
			var opt = {
				title : "正在执行...",
				zIndex:  99999,
				dragable: true,
				width: 400,
				height: 180,
				hasCloseButton: false,
				hasOkButton: true,
				hasCancelButton: false
			};
			$.extend(opt, option);
			this.onAccept = option.onAccept || false;
			
			this._super.init(opt);
			
			this.setInActive();
		},
		setActive: function(){
			this._editor.showControlArea();
		},
		setInActive: function(){
			this._editor.hideControlArea();
		},
		clickOkButton: function(){
			if(this.onAccept){
				this.onAccept.apply(this);
			}
			return this._super.clickOkButton();
		}
	};
	processBox = editor.extend(processBox.prototype);
	processBox.EVENT = {};
	processBox.EVENT = $.extend(processBox.EVENT, editor.EVENT);
	
	this.Editor = editor;
	this.ProcessBox = processBox;
});
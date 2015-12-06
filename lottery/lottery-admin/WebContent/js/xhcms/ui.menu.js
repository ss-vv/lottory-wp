/**
 * xhcms.ui.Menu
 * @param option {id, name, container, body, html}
 * 
 * @example
 * 
 */
Jet().$package("xhcms.ui", function(J) {
	var $ = jQuery, $E = Jooe;
	var PopupBox = xhcms.ui.PopupBox;
	var _id = 0, _boder = 9;
	var newId = function(){
		return _id++;
	};
	var Menu = function(){};
	Menu.prototype = {
		_container : null,
		_body : null,
		_currentItem : null,
		init : function(option){
			this._menuItemArr = [];
			var context = this;
			this._id = typeof option.id == "undefined" ? newId():option.id;
			this._name = option.name || this._id;
			this._text = option.text || "";
			
			option = option || {};
			option.noCatchMouseUp = option.noCatchMouseUp || false;
			this.option = option;
			this._enableMultiInstance = option.enableMultiInstance || false;
			this._showIcon = option.showIcon || false;
			
			this.createDom();
			this.initDomReference();
			
			option.container = this._container;
			option.body = this._body;
			option.name = option.id = "menu_" + this.getId();
			this._super.init(option);
		},
		getId : function(){
			return this._id;
		},
		getName: function(){
			return this._name;
		},
		getText : function(){
			return this._text;
		},		
		createDom : function(){
			this._container = $('<div id="ui_menu_"' + this.getId() +'" class="ui_menu"></div>');
			
			this._container.appendTo(this.option.appendTo);
			var template = this.getTemplate();
			this._container.html(template);
			this._container = this._container[0];
		},
		initDomReference : function(){
			var id = this.getId();
			this._body = $("#menu_body_"+id)[0];
			this._inner = $("#menu_inner_"+id)[0];
		},
		getInner : function(){
			return this._inner;
		},
		getTemplate : function(){
			var id = this.getId();
			var template = '<div class="menu_inner" id="menu_inner_'+ id +'">'
				+ '<div class="menu_bg"><div class="menu_bg_c"></div><div class="menu_bg_t"></div><div class="menu_bg_r"></div><div class="menu_bg_b"></div><div class="menu_bg_l"></div><div class="menu_bg_rt"></div><div class="menu_bg_rb"></div><div class="menu_bg_lb"></div><div class="menu_bg_lt"></div></div>'
				+ '<div class="menu_body" id="menu_body_'+ id +'"></div></div>';
			return template;
		},
		getItems: function(){
			return this._menuItemArr;
		},
		addItem : function(option){
			if(this._menuItemArr){
				option = option || {};
				option.menu = this;
				option.showIcon = option.showIcon || this._showIcon;
				var menuItem = new MenuItem(option);
				this._menuItemArr.push(menuItem);
				return menuItem;
			}
		},
		removeItem : function(menuItem){
			if(this._menuItemArr){
				var index = $.inArray(menuItem, this._menuItemArr);
				if(index > -1){
					this._menuItemArr[index].destroy();
					this._menuItemArr.splice(index, 1);
				}
			}
		},
		getCurrentItem : function(){
			return this._currentItem;
		},
		setCurrentItem : function(menuItem){
			if(this._currentItem)
				this._currentItem.setNotCurrent();
			this._currentItem = menuItem;
		},
		hide : function(){
			if(this._currentItem)
				this._currentItem.setNotCurrent();
			this._currentItem = null;
			this._super.hide();
		},
		destroy : function(){
			$E.trigger(this, Menu.EVENT.Destroy);
			$.each(this._menuItemArr,
								function(index, e){
									e.destroy();
								});
			this._menuItemArr = null;
			
			if(this._container && this.option.appendTo){
				$(this._container).remove();
			}
			for ( var i in this) {
				if (this.hasOwnProperty(i)) {
					delete this[i];
				}
			}
		}
	};
	Menu = PopupBox.extend(Menu.prototype);
	Menu.EVENT = {
		Destroy: "Destroy"
	};
	$.extend(Menu.EVENT, PopupBox.EVENT);
	
	var MenuItem = function(){};
	MenuItem.prototype = {
		_subMenu : null,
		_text : "",
		_icon : null,
		_node : null,
		_menu : null,
		data : null,
		init : function(option){
			var context = this;
			//option = option || {};
			this.option = option;
			
			this._menu = option.menu;
			this._id = typeof option.id == "undefined" ? newId():option.id;
			this._name = option.name || this._id;
			
			this._text = option.text || "";
			this._icon = option.icon || null;
			this._showIcon = option.showIcon || false;
			this.data = option.data || null;
			
			this.createDom();
			
			this.initDomReference();
			//icon
			if(option.icon)
				$(this._iconNode).css("background", 'url('+(option.icon)+')');
			if(this._showIcon){
				$(this._iconContainer).show();
			}

			this.observer = {
				onClick : function(e){
					var ignore = false;
					var  p = e.target;
					while(p || p == context._node){
						if(context._subMenu != null && p == context._subMenu.container){
							ignore = true;
							break;
						}
						p = p.parentNode;
					}
					if(ignore != true)
						$E.trigger(context, MenuItem.EVENT.Click, [context]);
				},
				onMouseover : function(e){
					if(context._menu && context._menu.getCurrentItem() != context){
						context._menu.setCurrentItem(context);
						context.setCurrent();
					}
				}
			};
			$E.on(this._node, "mouseover", this.observer.onMouseover);
			$E.on(this._node, "click", this.observer.onClick);
			
			if(option.hasSubMenu){
				this.addSubMenu();
			}	
		},
		getId : function(){
			return this._id;
		},
		getName: function(){
			return this._name;
		},
		getItemId : function(){
			return "ui_menuitm_" + this.getId();
		},
		addSubMenu: function(menu){
			$(this._arrowNode).show();
			
			if(menu == null){
				this._subMenu = new Menu({
					enableMultiInstance : true,
					appendTo : this._node,
					noCatchMouseUp : true
				});
				this._subMenu.setZIndex(this._menu.getZIndex());
			}else{
				this._subMenu = menu;
				menu.enableMultiInstance();
				menu.option.appendto = this._node;
				$(this._node).append(menu._container);
			}
			return this._subMenu;
		},
		removeSubMenu: function(){
			var menu = this._subMenu;
			if(menu){
				this._subMenu = false;
				$(this._arrowNode).hide();
			}
			return menu;
		},
		createDom : function(){
			this._node = $('<div id="'+this.getItemId()+'" class="ui_menuitem"></div>');
			this._menu.append(this._node[0]);
			var template = this.getTemplate();
			this._node.html(template);
			this._node = this._node[0];
		},
		getTemplate : function(){
			var id = this.getId();
			var template = '<div class="menuitem_inner" ><div class="menuitem_icon_container" id="menuitem_icon_container_'
					+ id +'"><div class="menuitem_icon" id="menuitem_icon_'+id+'" ></div></div>'
					+ '<div class="menuitem_arrow" id="menuitem_arrow_'+id+'"></div></div>'
					+ '<div class="menuitem_text" id="menuitem_text_'+id+'">'+(this._text)+'</div>';
			return template;
		},
		initDomReference : function(){
			var id = this.getId();
			this._textNode = $("#menuitem_text_"+id)[0];
			this._iconContainer = $("#menuitem_icon_container_" + id)[0];
			this._iconNode = $("#menuitem_icon_"+id)[0];
			this._arrowNode = $("#menuitem_arrow_"+id)[0];
		},
		getXY : function(){
			var a = $(this._node).offset(), b=$(this._menu.getInner()).offset();
			
			return {top:a.top - b.top, left: a.left - b.left};
		},
		getHeight : function(){
			return $(this._node).outerHeight();
		},
		getWidth : function(){
			return $(this._node).outerWidth();
		},
		relocateSubMenu : function(){
			if(this._subMenu == null)
				return;
			var left = 0, top = 0, 
				clientWidth = $(document.body).width(), clientHeight = $(document.body).height(),
				clientXY = $(this._node).offset();
			
			var xy = this.getXY();
			if((clientWidth - clientXY.left - this.getWidth() - _boder) < this._subMenu.getWidth())
				left = xy.left - this._subMenu.getWidth() - _boder;
			else 
				left = xy.left + this.getWidth() + _boder;
			if(clientHeight - clientXY.top < this._subMenu.getHeight()){
				if(clientHeight < this._subMenu.getHeight())
					top = xy.top - clientXY.top + _boder;
				else
					top = xy.top - (clientXY.top + this._subMenu.getHeight() - clientHeight) - _boder;
			}
			else
				top = xy.top;
			
			this._subMenu.setXY(left, top);
		},
		setCurrent : function(){
			$(this._node).addClass("current_menuitem");
			$(this._arrowNode).addClass("menuitem_open_arrow");
			if(this._subMenu){
				this.relocateSubMenu();
				this._subMenu.show();
			}
			$E.trigger(this, MenuItem.EVENT.SetCurrent, [this]);
		},
		setNotCurrent : function(){
			if(this._subMenu){
				this._subMenu.hide();
			}
			$(this._node).removeClass("current_menuitem");
			$(this._arrowNode).removeClass("menuitem_open_arrow");
			
			$E.trigger(this, MenuItem.EVENT.SetNotCurrent, [this]);
		},
		setText : function(text){
			this._text = text;
			this._textNode.html(this._text);
		},
		getText : function(){
			return this._text;
		},
		getNode : function(){
			return this._node;
		},
		getSubMenu : function(){
			return this._subMenu;
		},
		getData : function(){
			return this.data;
		},
		destroy : function(){
			$E.off(this._node, "mouseover", this.observer.onMouseover);
			$E.off(this._node, "click", this.observer.onClick);
			$E.removeObserver(this, MenuItem.EVENT.SetNotCurrent);
			$E.removeObserver(this, MenuItem.EVENT.SetCurrent);
			$E.removeObserver(this, MenuItem.EVENT.Click);
			if(this._subMenu)
				this._subMenu.destroy();
			$(this._node).remove();
			for ( var i in this) {
				if (this.hasOwnProperty(i)) {
					delete this[i];
				}
			}
		}
	};
	MenuItem = Jooe.extend(MenuItem.prototype);
	MenuItem.EVENT = {
		SetNotCurrent: "SetNotCurrent",
		SetCurrent: "SetCurrent",
		Click: "Click"
	};
	
	this.Menu = Menu;
	this.MenuItem = MenuItem;
});

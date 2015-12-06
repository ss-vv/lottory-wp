/**
 * xhcms.ui.Button
 */
Jet().$package("xhcms.ui", function(J){
	var $ = jQuery, $E = Jooe;
	var _id = this._id = 0;
	var Button = function(){};
	Button.prototype = {
		_class : "ui_button",
		_getButtionId : function() {
			return _id++;
		},
		init : function(option) {
			option = option || {};
			var h = this;
			var g = this._id = this._getButtionId();
			var opt = {
				appendTo : document.body,
				className : "",
				text : "",
				title : "",
				id : g
			};
			$.extend(opt, option);
			this._container = option.appendTo;
			this.createDom(opt);
			if (opt.event) {
				this.attachEvent(opt.event);
			}
			if (opt.isStopPropagation) {
				$E.on(this._node, "mousedown", function(j) {
					j.stopPropagation();
				});
				$E.on(this._node, "click", function(j) {
					j.stopPropagation();
				});
			}
		},
		createDom : function(option){			
			this._node = $('<a id = "'+this.getDomId()
							+'" class="'+this._class + ' ' + option.className
							+ '" hidefocus="" href="###"></a>');
			this._node.attr("title", option.title);
			
			this._node.appendTo(option.appendTo);
			this._node.text(option.text);
			this._node = this._node[0];
		},
		attachEvent : function(events) {
			for ( var type in events) {
				$E.on(this._node, type, events[type]);
			}
		},
		removeEvent : function(events) {
			for ( var type in events) {
				$E.off(this._node, type, events[type]);
			}
		},
		hide : function() {
			$(this._node).hide();
			this.trigger(Button.EVENT.Hide);
		},
		show : function() {
			$(this._node).show();
			this.trigger(Button.EVENT.Show);
		},
		setText : function(text) {
			$(this._node).text(text);
		},
		setTitle : function(title) {
			$(this._node).attr("title", title);
		},
		getId : function(){
			return this._id;
		},
		getDomId : function() {
			return "ui_button_" + this.getId();
		},
		getNode : function() {
			return this._node;
		},
		getClientXY: function(){
			var offset = $(this._node).offset();
			return {x: offset.left, y:  offset.top};
		},
		getHeight: function(){
			return $(this._node).height();
		},
		getWidth: function(){
			return $(this._node).width();
		},
		destroy : function(){
			this.trigger(Button.EVENT.Destroy);
			
			this.removeObserver(Button.EVENT.Destroy);
			this.removeObserver(Button.Hide);
			this.removeObserver(Button.Show);
			$(this._node).remove();
			for ( var i in this) {
				if (this.hasOwnProperty(i)) {
					delete this[i];
				}
			}
		},
		setHighlight : function(){
			if(this._node)
				$(this._node).addClass("button_highlight");
		},
		setNotHighlight : function(){
			if(this._node)
				$(this._node).removeClass("button_highlight");
		}
	};
	Button = Jooe.extend(Button.prototype);
	Button.EVENT = {
		Hide: "Hide",
		Show: "Show",
		Destroy: "Destroy"
	};
	this.Button = Button;
});

/**
 * xhcms.ui.RichButton
 */
Jet().$package("xhcms.ui", function(J){
	var $ = jQuery, $E = Jooe;
	var Button = this.Button;
	var RichButton = function(){};
	RichButton.prototype = {
		_class : "ui_richbutton",
		init : function(option){
			var context = this;

			this._super.init(option);
			var _node = context._node;
			this.attachEvent({
				mouseover:function(e){
					$(_node).addClass("button_hover");
				},
				mouseout:function(e){
					$(_node).removeClass("button_hover");
				}
			});
		},
		createDom : function(option){			
			this._node = $('<div id="'+this.getDomId()+'" class="'
							+this._class + ' '+ option.className+'"></div>');
			this._node.attr("title", option.title);
			
			this._innerNode = $('<div class="button_inner"></div>');
			this._innerNode.appendTo(this._node);
			
			this._innerNode.html(option.text);
			this._node.appendTo(option.appendTo);
			
			this._node = this._node[0];
			this._innerNode = this._innerNode[0];
		},
		setText : function(text) {
			$(this._innerNode).text(text);
		},
		setHtml : function(html) {
			$(this._innerNode).html(html);
		}
	};
	RichButton = Button.extend(RichButton.prototype);
	RichButton.EVENT = {};
	$.extend(RichButton.EVENT, Button.EVENT);
	
	this.RichButton = RichButton;
});
/**
 * jQuery工具类plugin。例如为 selector results 提供 isEmpty() 方法。
 * 依赖:mustache 插件.
 */

(function($){
	$.fn.isEmpty = function(){
		return this.length == 0;
	};
	
	$.fn.msgbox = function(msg, options){
		var me = this;
		$.msgbox(msg, $.extend({anchor: me}, options));
	};
	
	/**
	 * 选项:{
	 *  anchor: positioning element
	 * 	success: true|false 决定用成功提示还是失败提示样式，
	 *  timeout: 自动消失时间,
	 *  pos: 'center' 在屏幕中间显示
	 * }
	 */
	$.msgbox = function(msg, options){
		var settings = $.extend({
			success: true
			,timeout: 3000
			//,pos: 'center'
		}, options);
		var anchor = settings.anchor;
		var msgbox = $.mustache(
				'<div class="msgbox">'+
					'<div class="box_content"><span class="{{type_class}}">{{content}}</span></div>'+
				'</div>',{
			content: msg,
			type_class: settings.success?'successtip':'failtip'
		});
		msgbox = $(msgbox);
		$('body').append(msgbox);
		var left = 0;
		var top = 0;
		if (!anchor || settings.pos == 'center'){
			left = $(window).width()/2 + $(document).scrollLeft() - msgbox.outerWidth()/2.3;
			top = $(window).height()/2 + $(document).scrollTop() - msgbox.outerHeight()*1.2;
		}else{
			var anchorPos = anchor.offset();
			left = anchorPos.left - msgbox.outerWidth()/2.3;
			top = anchorPos.top - msgbox.outerHeight()*1.2;
		}
		msgbox.css('left', left);
		msgbox.css('top', top);
		msgbox.show();
		// 自动消失
		setTimeout(function(){
			msgbox.hide();
		}, settings.timeout);
	};
	// alias
	$.msgBox = $.msgbox;
	
	$.isBlankStr = function(str){
		var removed = str.replace(/ +/, "");
		return removed.length == 0;
	};
	
}(jQuery));
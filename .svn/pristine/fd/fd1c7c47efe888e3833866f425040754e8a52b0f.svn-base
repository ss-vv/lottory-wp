/**
 * jQuery工具类plugin。
 * 例如为 selector results 提供 isEmpty() 方法。
 * 依赖:mustache 插件.
 * 
 * @author Yang Bo
 */

(function($){
	function log(msg){
		console.log(msg);
	}
	
	/**
	 * 本方法强制认为str是本地时间，时区为+0800，且str不带时区后缀。
	 * 
	 * 转换为通用格式：Date.parse('1970/01/01 00:00:00 +0800'); 
	 */
	function strToDate(str){
		if (str instanceof Date){
			return str;
		}
		str = str.replace('T', ' ');
		str = str.replace(/-/g, '/');
		str += " +0800";
		var date = new Date(Date.parse(str));

		return date;
	}
	function padding(num){
		if(num>=10){
			return num;
		}else{
			return '0'+num;
		}
	}
	
	$.strToDate = strToDate;
	
	/**
	 * 格式化输出短时间字符串。
	 * 
	 * 根据与当前时间的差距，用不同格式打印时间。
	 * 参数：
	 * 	time 要格式化的时间
	 * 	reference 参考时间，可以不提供，不提供就用当前时间
	 * 
	 * 输出：字符串，可能的格式
	 * 1) 3秒前
	 * 2) 59分钟前
	 * 3) 今天 15:27
	 * 4) 05-07 07:06
	 * 5) 2013-02-01 12:35
	 */
	$.shortTime = function(time, reference){
		var cur = reference || new Date();
		var dateObj = strToDate(time);
		
		var interval = Math.floor((cur - dateObj.getTime())/1000);
		var month = 1+dateObj.getMonth();
		var day = dateObj.getDate();
		var hours = dateObj.getHours();
		var minutes = dateObj.getMinutes();
		month = padding(month);
		day = padding(day);
		hours = padding(hours);
		minutes = padding(minutes);
		
		// 秒
		if (interval>=0 && interval <60){
			return interval+'秒前';
		}
		// 分钟
		if (interval >= 60 && interval < 3600){
			return Math.floor(interval/60) + '分钟前';
		}
		// 今天
		if (cur.getYear()==dateObj.getYear() &&
			cur.getMonth()==dateObj.getMonth() &&
			cur.getDate()==dateObj.getDate()){
			return '今天 '+ hours +':'+ minutes;
		}
		// 同年
		if (cur.getYear() == dateObj.getYear()){
			return month+'-'+day+' '+hours+':'+minutes;
		}
		// 不同年
		var year = dateObj.getFullYear();
		return year+'-'+month+'-'+day+' '+hours+':'+minutes;
	};
	
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
var Map = function() {};
Map.prototype = {};
Map.prototype.keys = function() {
	if(!this.key){
		this.key = [];
	}
	return this.key;
}
Map.prototype.put = function(k,v) {
	if(!this.key){
		this.key = [];
	}
	if(!this[k]){
		this.key.push(k);
	}
	this[k] = v;
};
Map.prototype.get = function(k) {
	return this[k];
};
Map.prototype.remove = function(k) {
	delete this[k];
	for (var i = 0; i < this.key.length; i++) {
		if(this.key[i]==k){
			this.key.splice(i, 1);
			break;
		}
	}
};
Date.prototype.Format = function(fmt)   {  
	  var o = {   
	    "M+" : this.getMonth()+1,                 //月份   
	    "d+" : this.getDate(),                    //日   
	    "h+" : this.getHours(),                   //小时   
	    "m+" : this.getMinutes(),                 //分   
	    "s+" : this.getSeconds(),                 //秒   
	    "q+" : Math.floor((this.getMonth()+3)/3), //季度   
	    "S"  : this.getMilliseconds()             //毫秒   
	  };   
	  if(/(y+)/.test(fmt))   
	    fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));   
	  for(var k in o)   
	    if(new RegExp("("+ k +")").test(fmt))   
	  fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));   
	  return fmt;   
};  
(function($){
    $.extend({
        /**
         * 使用参数格式化字符串
         * source：字符串模式 abcdef{0}-{1}，
         * params：参数数组，参数序号对应模式中的下标
         */
        format: function(source, params) {
            if ( arguments.length == 1 ) 
                return function() {
                    var args = $.makeArray(arguments);
                    args.unshift(source);
                    return $.format.apply( this, args );
                };
            if ( arguments.length > 2 && params.constructor != Array  ) {
                params = $.makeArray(arguments).slice(1);
            }
            if ( params.constructor != Array ) {
                params = [ params ];
            }
            $.each(params, function(i, n) {
                source = source.replace(new RegExp('\\{' + i + '\\}', 'g'), n);
            });
            return source;
        },
        
        /**
         * 将JSON格式的日期字符串解析为日期对象
         * @param s
         */
        parseDate: function(s){
            var v = new RegExp('(\\d{4})\-(\\d{2})\-(\\d{2})T(\\d{2}):(\\d{2}):(\\d{2})').exec(s);
            return new Date(v[1], v[2] - 1, v[3], v[4], v[5], v[6]);
        },
        /**
         * 返回“2014年12月30日 星期一”格式字符串
         * @param s eg:"2014-12-30T13:54:00" 
         */
        parseToYeardayAndWeekday : function (s){
        	var weekCN = ['日','一','二','三','四','五','六'];
        	var fill = function(num){return ((num < 10) ? '0': '') + num};
        	var v = new RegExp('(\\d{4})\-(\\d{2})\-(\\d{2})T(\\d{2}):(\\d{2}):(\\d{2})').exec(s);
            var date = new Date(v[1], v[2] - 1, v[3], v[4], v[5], v[6]);
            var yearDay = date.getFullYear() + '年' + fill(date.getMonth() + 1) + '月' + fill(date.getDate()) + "日";
            var weekDay = " 星期" + weekCN[date.getDay()];
            return yearDay + weekDay;
        },
        /**
         * prefix=“-”，返回“2014-12-30”格式字符串
         * @param s eg:"2014-12-30T13:54:00" 
         */
        parseToYeardayWithPrefix: function (s,prefix){
        	var v = new RegExp('(\\d{4})\-(\\d{2})\-(\\d{2})T(\\d{2}):(\\d{2}):(\\d{2})').exec(s);
        	var date = new Date(v[1], v[2] - 1, v[3], v[4], v[5], v[6]);
        	var fill = function(num){return ((num < 10) ? '0': '') + num};
        	var yearDay = date.getFullYear() + prefix + fill(date.getMonth() + 1) + prefix + fill(date.getDate());
        	return yearDay;
        },
        /**
         * 返回“2014年12月30日”格式字符串
         * @param s eg:"2014-12-30T13:54:00" 
         */
        parseToYearday: function (s){
        	var v = new RegExp('(\\d{4})\-(\\d{2})\-(\\d{2})T(\\d{2}):(\\d{2}):(\\d{2})').exec(s);
            var date = new Date(v[1], v[2] - 1, v[3], v[4], v[5], v[6]);
            var fill = function(num){return ((num < 10) ? '0': '') + num};
            var yearDay = date.getFullYear() + '年' + fill(date.getMonth() + 1) + '月' + fill(date.getDate()) + "日";
            return yearDay;
        },
        roundNumber : function (num,v) {
        	var f_x = parseFloat(num);
        	if (isNaN(f_x)){
        		return num;
        	}
        	f_x = Math.round(f_x*100)/100;
        	var s_x = f_x.toString();
        	var pos_decimal = s_x.indexOf('.');
        	if (pos_decimal < 0){
        		pos_decimal = s_x.length;
        		s_x += '.';
        	}
        	while (s_x.length <= pos_decimal + v){
        		s_x += '0';
        	}
        	return s_x;
        }
    });
})(jQuery);
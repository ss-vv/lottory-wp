(function($){
    $.extend({
        format: function(source, params) {
        	if ( arguments.length == 1 ) 
        		return function() {
        			var args = $.makeArray(arguments);
        			args.unshift(source);
        			return $.format.apply( this, args );
        		};
        	if ( arguments.length > 2 && params && params.constructor != Array  ) {
        		params = $.makeArray(arguments).slice(1);
        	}
        	if (params && params.constructor != Array ) {
        		params = [ params ];
        	}
        	if(params) {
        		$.each(params, function(i, n) {
        			source = source.replace(new RegExp("\\{" + i + "\\}", "g"), n);
        		});
        	}
        	return source;
        },
        
        contain : function(array, element) {
			for (i in array) {
				if (array[i] == element) {
					return true;
				}
			}
			return false;
		},
		
        parseJSONData: function(json, success){
        	if(json.success){
        		if($.isFunction(success)){
        			success(json.data);
        		}
        	}else{
        		var data = json.data, message = '';
        		if(typeof(data.actionErrors) !== 'undefined'){
        			message += data.actionErrors.join(';');
        		}
        		if(typeof(data.fieldErrors) !== 'undefined'){
        			$.each(data.fieldErrors, function(){
        				for(var name in this){
        					message += name + ':' + this[name] + ';';
        				}
        			});
        		}
        		if(message == ''){
        			message = data;
        		}
        		alert(message);
        	}
        },
        
        BLANK_IMG : '../images/s.gif'
    });

    var playCache = [];
    $.extend($.fn, {
    	
    	/**
    	 * option: {
    	 * 	   value: null  // 默认值
    	 * }
    	 * 
    	 */
    	initPlay: function(option){
    		var ctx = this, opt = option || {}, val = opt.defVal || '01_ZC_1', lid = opt.lid || 'JCZQ';
    		if(playCache.length == 0){
    			$.getJSON('/play/aj_ls', {_: new Date().getTime()}, function(json){
    				$.parseJSONData(json, function(data){
    					playCache = data;
    					var html = [], sel;
    					$.each(data, function(i, p){
    						if(p.lotteryId == lid){
    							sel = (p.id == val ? ' selected="true"': '');
    							html.push($.format('<option value="{0}"{2}>{1}</option>', p.id, p.name, sel));
    						}
    					});
    					
    					ctx.html(html.join(''));
    				});
    			});
    		} else {
    			var html = [], sel;
				$.each(playCache, function(i, p){
					if(p.lotteryId == lid){
						sel = (p.id == val ? ' selected="true"': '');
						html.push($.format('<option value="{0}"{2}>{1}</option>', p.id, p.name, sel));
					}
				});
				
				ctx.html(html.join(''));
    		}
    		return this;
    	}
    	
    });
    
    window.options = {
		datepicker: {
			dateFormat: 'yy-mm-dd',
	        changeYear: true,
	        changeMonth: true,
	        prevText: '上月',
	        nextText: '下月',
	        dayNamesMin: ['日','一','二','三','四','五','六'],
	        monthNamesShort: ['一月','二月','三月','四月','五月','六月','七月','八月','九月','十月','十一月','十二月']
		}	
    };
    
    window.options.datetimepicker = $.extend({
    	timeFormat: 'hh:mm:ss',
        currentText: '当前时间',
        closeText: '确定',
        timeText: '时间：',
        hourText: '时：',
        minuteText: '分：'
	}, window.options.datepicker);
    
})(jQuery);
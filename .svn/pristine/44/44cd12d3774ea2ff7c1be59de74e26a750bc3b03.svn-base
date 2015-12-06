(function($){
	var R20 = /%20/g;
    
    function buildParams( prefix, obj, add ) {
    	if ( jQuery.isArray( obj ) && obj.length ) {
    		jQuery.each( obj, function( i, v ) {
    			if(jQuery.isPlainObject(v)){
    				buildParams( prefix + "[" + ( typeof v === "object" || jQuery.isArray(v) ? i : "" ) + "]", v, add );
    			}else{
    				add(prefix, v);
    			}
    			
    		});
    	} else if ( obj != null && typeof obj === "object" ) {
    		if ( jQuery.isArray( obj ) || jQuery.isEmptyObject( obj ) ) {
    			add( prefix, "" );

    		} else {
    			for ( var name in obj ) {
    				buildParams( prefix + "." + name, obj[ name ], add );
    			}
    		}
    	} else {
    		add( prefix, obj );
    	}
    };
    
	function pageLink(curNo, pageNo){
		if(curNo == -1){
			return '<span>...</span>';
		}
		
		if(pageNo == curNo){
			return $.format('<span class="ui-pager-curNo">{0}</span>', pageNo);
		}
		
		return $.format('<a href="javascript:void(0);" title="第{0}页" pn={0}>{0}</a>', pageNo);
	}
	
	$.fn.extend({
		/**
		 * 加载中
		 */
		loading: function(){
			return this.append('<div class="ui-loading"><img class="ui-loading-img" src="/ui/images/loading.gif"/>加载中...</div>');
		},
		
		/**
		 * 加载结束
		 */
		loaded: function(){
			$('div.ui-loading', this).remove();
			return this;
		},
		
		/**
		 * 分页函数
		 * p: paging
		 * callback: 点击分页时调用的函数，参数为pageNo
		 * mini: 是否为迷你型，如果true，则只有"上一页"和"下一页"的按钮
		 */
		pager: function(p, callback, mini){
			var pc = p.pageCount; 	// 总页数
			if(pc == 0){
				this.html('');
				return this;
			}
			
			var	c = 3,				// 左右各需要显示的分页数量
				tc = c * 2 + 3, 	// 显示的分页总数
				cp = p.pageNo,		//当前页号
				h = [];
		
			// 分页信息总览
			h.push($.format('<span class="pager-total">共<b>{0}</b>条记录，分<b>{1}</b>页</span>', p.totalCount, p.pageCount));
			
			// 上一页
			if(cp > 1){
				h.push($.format('<a href="javascript:void(0)" pn={0} class="ui-pager-prev"><b class="ui-icon ui-pager-prev-icon"></b>上一页</a>', cp - 1));
			}
			
			// 分页
			if(!mini){
				if(pc > tc){
					if(cp <= c + 2){
						for(var i = 1; i < tc - 1; i++){
							h.push(pageLink(cp, i));
						}
						h.push(pageLink(-1, 0));
						h.push(pageLink(cp, pc));
					}else{
						h.push(pageLink(cp, 1));
						h.push(pageLink(-1, 0));

						if(cp > (pc - (c + 2))){
							for(var i = pc - tc + 2; i <= pc; i++){
								h.push(pageLink(cp, i));
							}
						}else{
							for(var i = cp - c; i < cp + c; i++){
								h.push(pageLink(cp, i));
							}
							h.push(pageLink(-1, 0));
							h.push(pageLink(cp, pc));
						}
					}
				}else{
					for(var i = 1; i <= pc; i++){
						h.push(pageLink(cp, i));
					}
				}
			}
			
			// 下一页
			if(cp < pc){
				h.push($.format('<a href="javascript:void(0)" pn={0} class="ui-pager-next"><b class="ui-icon ui-pager-next-icon"></b>下一页</a>', cp + 1));
			}
			
			// 添加事件
			this.html(h.join(''));
			this.find('>a').bind('click', function(){
				callback($(this).attr('pn'));
				return false;
			});
			
			return this;
		}
		
	});
	
    $.extend({
    	/**
    	 * 验证是否为数值类型
    	 */
        isNumber: function(o){
            return (o instanceof Number || typeof o == "number");
        },
        
        /**
         * 记录日志
         */
        log: function(message){
        	if(console){
				try{
					console.info(message);
				}catch(Error){
				}
        	}
			return this;
		},
		
		/**
		 * 将json时间"yyyyMMddTHH:mm:ss"格式化成"yyyyMMdd HH:mm"
		 */
		formatTime: function(value){
			return value.replace('T', ' ').substring(0, value.lastIndexOf(':'));
		},
		
		/**
		 * 对url进行处理
		 * path：相对于当前上下文的路径
		 * noCache：是否不缓存，true：添加时间戳防止使用缓存的数据，false：可以使用缓存的数据
		 * 
		 */
		url: function(path, noCache){
			if(noCache != false){
				path += (path.indexOf('?') == -1 ? '?_=' : '&_=') + (new Date().getTime());
			}
			// 取上下文路径
			if(typeof(env) !== 'undefined' && env.context){
				return env.context + path;
			}
			return '/' + path;
		},
		
		/**
		 * 修正png在ie6下的显示问题，png书写时要采用 &lt;img _png="yes" src="..."/&gt;
		 * 
		 */
		amendPNG: function(){
	    	if(!$.browser.msie){
	    		return this;
	    	}
	    	if($.browser.version  < 7 && (document.body.filters)){
	    		$('img[_png="yes"]').each(function(i, img){
	    			var $this = $(this);
	    			$this.css('filter', 'progid:DXImageTransform.Microsoft.AlphaImageLoader(src="' + $this.attr('src') + '", sizingMethod="scale")')
	    				.attr('src', $.BLANK_IMG);
	    		});
	    	}
	    	return this;
	    },
		
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
	     * 显示对话框
	     * message: 要显示的消息
	     * options：对话框的可选属性
	     * second：对话框要显示的时间（秒），即对话框显示second秒后自动关闭
	     */
	    dialog: function(message, options, second){
	    	if(typeof second === 'undefined' && $.isNumber(options)){
	    		second = options;
	    		options = {};
	    	}
			var opts = $.extend(options||{}, {
				title: '提示信息',
				hide: false,
				initFn: function(){
					$('.ui-dialog-text', this.body).html(message);
				}
			});
			
			var $win = $('<div class="ui-dialog"><div class="ui-dialog-text"/></div>')
				.appendTo("body").panel(opts);
			
			if(second != null && second > 0){
				setTimeout(function(){
					$win.panel('destroy');
				}, second * 1000);
			}
		},
		
		/**
		 * 检查字数，ascii码算半个字
		 * s:字符串
		 */
		wordCount: function(s){
			var bytes = 0;
			var chars = 0;
			var ch = 0;
			for(var i = 0, j = s.length; i < j; i++){
				ch = s.charCodeAt(i);
				if(s.charCodeAt(i) < 255){
					bytes++;
				}else{
					chars++;
				}
			}
			return parseInt(bytes / 2) + chars;
		},
        
		/**
		 * 模拟jQuery.param()方法，用于向struts传递json对象。
		 * 对于非对象数组，如{a: [1,2,3]}，将被处理为a=1&a=2&2=3(jQuery.param()处理结果是a[]=1&a[]=2&a[]=3)
		 * 对于非对象数组，如{a: [{x:1,y:2}]}，将被处理为a[0].x=1&a[0].y=2(jQuery.param()处理结果是a[0][x]=1&a[0][y]=2)
		 */
		ajaxParams: function(a) {
			var s = [],
				add = function( key, value ) {
					s[s.length] = encodeURIComponent(key) + '=' + encodeURIComponent(value);
				};
			
			if (jQuery.isArray(a) && (a.jquery && !jQuery.isPlainObject(a))) {
				jQuery.each(a, function() {
					add( this.name, this.value );
				});
			} else {
				for (var prefix in a) {
					buildParams(prefix, a[prefix], add);
				}
			}

			return s.join('&').replace(R20, '+');
		},
		
        BLANK_IMG : '/ui/images/s.gif'
    });
    
    // CWidget
    $.CWidget = function(options, el){
    	if(arguments.length){
    		this._createWidget(options, el);
    	}
    };
    
    $.CWidget.prototype = {
    	// 用于绑定数据、事件
    	widgetName: 'cwidget',
    	
    	options: {},
    	_create: $.noop,
    	_init: $.noop,
    	
    	_createWidget: function(options, el){
    		this.target = $(el).data(this.widgetName, this);
    		this.options = $.extend({}, this.options, options);
    		var self = this;
    		this._create();
    		this._init();  
    	},
    	
    	widget: function(){
    		return this.target;
    	},
    	
    	destroy: function(){
    		this.target.unbind('.' + this.widgetName).removeData(this.widgetName);
    		this.widget().unbind('.' + this.widgetName);
    	},
    	
    	/**
    	 * 设置、获取options属性值
    	 * key为对象时，将key中的属性设置为options
    	 * key为string时，如果value未定义，则返回options中的key值
    	 * 如果value已定义，则设置key的值为value
    	 * 
    	 * @param key
    	 * @param value
    	 * @return
    	 */
    	option: function(key, value){
    		var options = key, self = this;
    		if(arguments.length === 0){
    			// 返回options
    			return $.extend({}, self.options);
    		}
    		
    		if(typeof key === 'string'){
    			if(value === undefined) {
    				// 返回可以的值
    				return this.options[key];
    			}
    			// 设置key的值
    			options = {};
    			options[key] = values;
    		}
    		
    		$.each(options, function(key, value){
    			self._setOption(key, value);
    		});
    	},
    	
    	_setOption: function(key, value){
    		this.options[key] = value;
    		return this;
    	}
    };
    
    $.cwidget = function(name, base, prototype){
    	var ns = name.split('.')[ 0 ], cn;
    	name = name.split('.')[ 1 ];
    	cn = ns + '-' + name;
    	
    	if(!prototype){
    		prototype = base;
    		base = $.CWidget;
    	}
    	
    	$[ ns ] = $[ ns ] || {};
    	$[ ns ][ name ] = function( options, element ) {
    		if ( arguments.length ) {
    			this._createWidget( options, element );
    		}
    	};
    	
    	var basePrototype = new base();
    	basePrototype.options = $.extend({}, basePrototype.options);
    	$[ns][name].prototype = $.extend(basePrototype, {
    		widgetName: name
    	}, prototype);
    	
    	$.cwidget.bridge( name, $[ ns ][ name ] );
    };
    
    $.cwidget.bridge = function( name, object ) {
    	$.fn[name] = function(options){
    		var isMethodCall = (typeof options === 'string'),
    		args = Array.prototype.slice.call(arguments, 1),
    		returnValue = this;
    		
    		options = (!isMethodCall && args.length) ? $.extend.apply(null, [true, options].concat(args)): options;
    		
    		// 不允许调用"_"开头的方法
    		if(isMethodCall && options.substring(0, 1) === '_'){
    			return reutrnValue;
    		}
    		
    		if(isMethodCall){
    			// 调用方法
    			this.each(function(){
    				var instance = $.data(this, name),
    					methodValue = instance && $.isFunction(instance[options])? instance[options].apply(instance, args) : instance;
    				
    				if(methodValue !== instance && methodValue !== undefined){
    					returnValue = methodValue;
    					return false;
    				}
    			});
    		}else{
    			this.each(function(){
    				var instance = $.data(this, name);
    				if(instance){
    					// 对象已存在时，重新初始化
    					if(options){
    						instance.option(options);
    						instance._init();
    					}
    				}else{
    					// 对象不存在，创建新对象
    					$.data(this, name, new object( options, this ) );
    				}
    			});
    		}
    		return returnValue;   		
    	};
    };
    
    
    // UI - Panel
    $.cwidget('ui.panel', {
		
		_createBG: function(){
    		// ie6
    		if(this.options.iframe && $.browser.msie && $.browser.version.charAt(0) == '6'){
    			this.iframe = $('<iframe class="ui-panel-iframe"></iframe>').appendTo(this.target);
    		}
    		this.content = $('<div class="ui-panel-content"></div>').appendTo(this.target);
    		var bg = $('<div class="ui-panel-bg"></div>').appendTo(this.target);
    		$('<div class="ui-panel-bg-t"/><div class="ui-panel-bg-r"/><div class="ui-panel-bg-b"/><div class="ui-panel-bg-l"/>').appendTo(bg);
    		$('<div class="ui-panel-bg-rt"><b class="ui-panel-bg-trbl"/></div><div class="ui-panel-bg-rb"><b class="ui-panel-bg-trbl"/></div><div class="ui-panel-bg-lb"><b class="ui-panel-bg-trbl"/></div><div class="ui-panel-bg-lt"><b class="ui-panel-bg-trbl"/></div>').appendTo(bg);
		},
		
		_createHeader: function(){
			var opts = this.options;
			this.header = $('<div class="ui-panel-header"/>').appendTo(this.content);
			$('<b class="ui-panel-title">' + (opts.title ? opts.title : "") + '</b>').appendTo(this.header);
			
			if(opts.closable){
				$('<a href="javascript:void(0);" class="ui-panel-close" title="关闭">关闭</a>').appendTo(this.header);
			}
		},
		
		_createBody: function(oldBody){
			this.body = $('<div class="ui-panel-body"/>').appendTo(this.content);
			if(oldBody.length > 0){
				oldBody.appendTo(this.body);
			}
			if(this.options.body){
				if(typeof this.options.body === 'string'){
					this.body.append($(this.options.body).detach());
				}
			}
		},
		
		_createModel: function(){
			this.model = $('<div class="ui-panel-model"/>').appendTo('body');
		},
		
		// 添加事件监听
		_addEventListener: function(){
			var self = this, cc = this.options.closeCSS;
			var closeFn = function(){
				self.destroy();

				return false;
			};
			$('a.ui-panel-close', this.header).bind('click.panel', closeFn);
			if(cc != null && $.trim(cc) != ''){
				$('.' + cc, this.body).bind('click.panel', closeFn);
			}
			
			this.target.bind('resize.panel', function(){
				var $this = $(this);
				$('div.ui-panel-bg', $this).height($this.height());
				$('div.ui-panel-bg-l', $this).height($this.height());
				$('div.ui-panel-bg-r', $this).height($this.height());
			});
		},
		
		// 删除事件监听
		_removeEventListener: function(){
			var cc = this.options.closeCSS;
			if(cc != null && $.trim(cc) != ''){
				$('.' + cc, this.body).unbind('.panel');
			}
			
			$('a.ui-panel-close', this.header).unbind('.panel');
			this.target.unbind('resize.panel');
		},
		
		// 创建窗口对象，对于可隐藏的窗口仅执行一次
		_create: function(){
			var opts = this.options, self = this, t = this.target.addClass('ui-panel'), oldBody =  this.target.children().detach();
			
			this._createBG();
			this._createHeader();
			this._createBody(oldBody);
			if(opts.model){
				this._createModel();
			}
		},
		
		// 初始化窗口对象，可隐藏的窗口每次显示的时候都会执行
		_init: function(){
			this._addEventListener();
			if($.isFunction(this.options.initFn)){
				this.options.initFn.apply(this);
			}
			if(this.model){
				var $w = $(window);
				this.model.show().height($w.height()).width($w.width());
			}
			
			this.resize();
		},
		
		resize: function(){
			var t = this.target.show(), opts = this.options,b = this.body;
			
			if(!isNaN(opts.width)){
				t.width(opts.width - (t.outerWidth() - t.innerWidth()));
			}
			b.width(this.content.innerWidth());
			
			if(!isNaN(opts.height)){
				t.height(opts.height - (t.outerHeight() - t.innerHeight()));
			}
			if(this.iframe){
				this.iframe.height(t.height());
			}
			b.height(this.content.innerHeight() - this.header.outerHeight());
			
			this.position();
		},
		
		// 定位
		position: function(){
			var t = this.target, $w = $(window);
			var o = {
				left: ($w.width() - t.outerWidth())/2 + $w.scrollLeft(),
				top: ($w.height() - t.outerHeight())/2 + $w.scrollTop()
			};

			t.css($.extend(o, this.options.offset || {}));
		},
		
		// 释放窗口对象，删除事件监听
		destroy: function(){
			var opts = this.options, t = this.target;
			if($.isFunction(opts.closeFn)){
				opts.closeFn.apply(this);
			}
			
			this._removeEventListener();
			if(opts.hide){
				t.hide();
				if(this.model){
					this.model.hide();
				}
			}else{
				t.remove();
				if(this.model){
					this.model.remove();
				}
			}
		},
		
		options: {
			title: null, 			// 标题
			body: null, 			// 额外内容体
			width: 'auto', 			// 宽度
			height: 'auto', 		// 高度
			offset: null, 			// 定位时的偏移量
			center: true, 			// 是否居中显示
			initFn: $.emptyFn, 		// 额外初始化方法
			closeFn: $.emptyFn, 	// 额外关闭方法
			model: false, 			// 是否使用蒙版
			iframe: true,			// 是否使用iframe			
			hide: true, 			// 关闭时是否仅隐藏
			closable: true,			// 是否显示关闭按钮
			closeCSS: 'vc_cancel'	// 标识额外关闭按钮的css类名
		}
	});
})(jQuery);

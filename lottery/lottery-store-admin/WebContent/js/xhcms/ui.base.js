/**	
 * JET (Javascript Extension Tools) 
 * Copyright (c) 2009, KDV.cn, All rights reserved.
 * Code licensed under the BSD License:
 * http://developer.kdv.cn/jet/license.txt
 *
 * @fileOverview Jet!
 * @version	1.0
 * @author	Kinvix(<a href="mailto:Kinvix@gmail.com">Kinvix@gmail.com</a>)
 * @description 
 * 
 */

/**
 * 拖拽模块
 */
Jet().$package("xhcms.ui", function (J) {
//	var console = {
//			out: function(msg, type){
//				var console = window.console;
//				if(console){
//					switch(type){
//					case 0:
//						if(console.error){
//							console.error(msg);
//						}
//						break;
//					case 1:
//						if(console.info){
//							console.info(msg);
//						}
//						break;
//					case 2:
//						if(console.warn){
//							console.warn(msg);
//						}
//						break;
//					case 3:
//						if(console.debug){
//							console.debug(msg);
//						}
//						break;
//					default:
//						if(console.log){
//							console.log(msg);
//						}
//						break;
//					}
//				}
//			}		
//		};
//		J.console = console;

	
    var $ = jQuery, $E = Jooe;

    var ieSelectFix = function (e) {
        e.preventDefault();
    };
	
	var _clientWidth=false,
		_clientHeight=false,
		_width=false,
		_height=false;
    /**
	 * 拖拽类
	 * 
	 * @memberOf ui
	 * @Class
	 * 
	 * @param {Element} apperceiveEl 监听拖拽动作的元素
	 * @param {Element} effectEl 展现拖拽结果的元素
	 * @param {Object} option 其他选项，如:isLimited,leftMargin...
	 * @returns
	 * 
	 * 
	 */
	var Drag = function(){};
	
    Drag.prototype = {
        init: function (apperceiveEl, effectEl, option) {
            var context = this;
            var curDragElementX, curDragElementY, dragStartX, dragStartY;
            this.apperceiveEl = apperceiveEl;
            option = option || {};
            this.isLimited = option.isLimited || false;
            if (this.isLimited) {
                this._leftMargin = option.leftMargin || 0;
                this._topMargin = option.topMargin || 0;
                this._rightMargin = option.rightMargin || 0;
                this._bottomMargin = option.bottomMargin || 0;
            }

            if (effectEl === false) {
                this.effectEl = false;
            } else {
                this.effectEl = effectEl || apperceiveEl;
            }

            this.dragStart = function (e) {
                e.preventDefault();
                e.stopPropagation();
				
				//缓存高宽
				_clientWidth = effectEl ? $(effectEl).parent().width() : $(context.effectEl).parent().width();
				_clientHeight = effectEl ? $(effectEl).parent().height() : $(context.effectEl).parent().height();
				_width = effectEl ? $(effectEl).width() : $(context.effectEl).width();
				_height = effectEl ? $(effectEl).height(): $(context.effectEl).height();
				
				if($.browser.msie)
				{
					curDragElementX = parseInt($(context.apperceiveEl).css("left")+10) || 0;
					curDragElementY = parseInt($(context.apperceiveEl).css("top")+10) || 0;
				}
				else{
					curDragElementX = parseInt($(context.effectEl).css("left")) || 0;
					curDragElementY = parseInt($(context.effectEl).css("top")) || 0;
				}
                dragStartX = e.pageX;
                dragStartY = e.pageY;
                $E.on(document, "mousemove", context.dragMove);
                $E.on(document, "mouseup", context.dragStop);
                if ($.browser.msie) {
                    $E.on(document.body, "selectstart", ieSelectFix);
                }

                context.trigger(Drag.EVENT.Start, { x: curDragElementX, y: curDragElementY });
                //FIXME trigger click event when drag start, some popup menu need it.
                $(document).trigger("click"); 
            };

            this.dragMove = function (e) {
                var x = curDragElementX + (e.pageX - dragStartX);
                var y = curDragElementY + (e.pageY - dragStartY);
                var isMoved = false;


                if (context.isLimited) {
                    var tempX = _clientWidth - _width - context._rightMargin;
                    if (x > tempX) {
                        x = tempX;
                    }
                    tempX = context._leftMargin;
                    if (x < tempX) {
                        x = tempX;
                    }

                }
                if (context._oldX !== x) {
                    context._oldX = x;
                    if (context.effectEl) {
                    	$(context.effectEl).css("left", x + "px");
                    }
                    isMoved = true;
                }

                if (context.isLimited) {
                    var tempY = _clientHeight - _height - context._bottomMargin;
                    if (y > tempY) {
                        y = tempY;
                    }
                    tempY = context._topMargin;
                    if (y < tempY) {
                        y = tempY;
                    }
                    //J.out("clientHeight: "+_clientHeight+", _height:" + _height+", y:"+y);
                }

                if (context._oldY !== y) {
                    context._oldY = y;
                    if (context.effectEl) {
                        $(context.effectEl).css("top", y + "px");
                    }
                    isMoved = true;
                }


                if (isMoved) {
                    context.trigger(Drag.EVENT.Move, { x: x, y: y });
                }

            };
			
            this.dragStop = function (e) {
            	context.trigger(Drag.EVENT.End, { x: context._oldX, y: context._oldY });
				_clientWidth = false;
				_clientHeight = false;
				_width = false;
				_height = false;
                $E.off(document, "mousemove", context.dragMove);
                $E.off(document, "mouseup", context.dragStop);
                if ($.browser.msie) {
                    $E.off(document.body, "selectstart", ieSelectFix);
                }
                //J.out("end");
            };

            $E.on(this.apperceiveEl, "mousedown", this.dragStart);
        },
        lock: function () {
            $E.off(this.apperceiveEl, "mousedown", this.dragStart);
        },
        unlock: function () {
            $E.on(this.apperceiveEl, "mousedown", this.dragStart);
        },
        show: function () {
            $(this.apperceiveEl).show();
        },
        hide: function () {
            $(this.apperceiveEl).hide();
        }
    };

    Drag = Jooe.extend(Drag.prototype);
    Drag.EVENT ={
    	Start: "Start",
    	Move: "Move",
    	End: "End"
    };
    this.Drag = Drag;
});


/**
 * Resize 模块
 */
Jet().$package("xhcms.ui", function (J) {
    var $ = jQuery,	$E = Jooe;
    var Drag = this.Drag;

    var id = 0;
    var handleNames = {
        t: "t",
        r: "r",
        b: "b",
        l: "l",
        rt: "rt",
        rb: "rb",
        lb: "lb",
        lt: "lt"
    };

    var _clientWidth=false, _clientHeight=false;
    /**
    * resize类
    * 
    * @memberOf ui
    * @Class
    * 
    * @param {Element} apperceiveEl 监听resize动作的元素
    * @param {Element} effectEl 展现resize结果的元素
    * @param {Object} option 其他选项，如:dragProxy,size,minWidth...
    * @returns 
    * 
    * 
    */
    var Resize = function(){};
    Resize.prototype = {
        init: function (apperceiveEl, effectEl, option) {
            var context = this;
            option = option || {};
            this.isLimited = option.isLimited || false;
            if (this.isLimited) {
                this._leftMargin = option.leftMargin || 0;
                this._topMargin = option.topMargin || 0;
                this._rightMargin = option.rightMargin || 0;
                this._bottomMargin = option.bottomMargin || 0;
            }
            
            this.apperceiveEl = apperceiveEl;
            if (effectEl === false) {
                this.effectEl = false;
            } else {
                this.effectEl = effectEl || apperceiveEl;
            }

            this.size = option.size || 5;
            this.minWidth = option.minWidth || 0;
            this.minHeight = option.minHeight || 0;
            this._dragProxy = option.dragProxy;

            this._left = this.getLeft();
            this._top = this.getTop();
            this._width = this.getWidth();
            this._height = this.getHeight();

            this.id = this.getId();

            var styles = {
                t: "cursor:n-resize; z-index:1; left:0; top:-5px; width:100%; height:5px;",
                r: "cursor:e-resize; z-index:1; right:-5px; top:0; width:5px; height:100%;",
                b: "cursor:s-resize; z-index:1; left:0; bottom:-5px; width:100%; height:5px;",
                l: "cursor:w-resize; z-index:1; left:-5px; top:0; width:5px; height:100%;",
                rt: "cursor:ne-resize; z-index:2; right:-5px; top:-5px; width:5px; height:5px;",
                rb: "cursor:se-resize; z-index:2; right:-5px; bottom:-5px; width:5px; height:5px;",
                lt: "cursor:nw-resize; z-index:2; left:-5px; top:-5px; width:5px; height:5px;",
                lb: "cursor:sw-resize; z-index:2; left:-5px; bottom:-5px; width:5px; height:5px;"
            };

            this._onMousedown = function () {				
                context.trigger(Resize.EVENT.Mousedown, { width: context._width, height: context._height });
            };
            this._onDragEnd = function () {
                //J.out("this._width： " + context._width);
			    //J.out("this._height： " + context._height);
                context.trigger(Resize.EVENT.End, {
                    x: context.getLeft(),
                    y: context.getTop(),
                    width: context.getWidth(),
                    height: context.getHeight()
                });
            };

            for (var p in handleNames) {
                var tempEl = $('<div id="window_' + this.id + '_resize_' + handleNames[p]+'"></div>');

                tempEl.appendTo(this.apperceiveEl);
                //tempEl.attr("style", "position:absolute; overflow:hidden; background:url(" + J.path + "assets/images/transparent.gif);" + styles[p]);
                tempEl.attr("style", "position:absolute; overflow:hidden;" + styles[p]);
                if (this._dragProxy) {
                    //$E.on(tempEl, "mousedown", this._onMousedown);
                } else {

                }
                tempEl = tempEl[0];
                this["_dragController_" + handleNames[p]] = new xhcms.ui.Drag(tempEl, false);
            }



            // 左侧
            this._onDragLeftStart = function (e, xy) {
            	_clientWidth = $(window).width();
				_clientHeight =$(window).height();
				context.trigger(Resize.EVENT.Mousedown, { width: context._width, height: context._height });
                context._startLeft = context._left = context.getLeft();
                context._startWidth = context._width = context.getWidth();
            };
            this._onDragLeft = function (e, xy) {
                var w = context._startWidth - xy.x;
                var x = context._startLeft + xy.x;
                if (w < context.minWidth) {
                    w = context.minWidth;
                    x = context._startLeft + (context._startWidth - w);
                }
                if(context.isLimited){
                	if(x < context._leftMargin){
                		w = w - (context._leftMargin - x);
                		x = context._leftMargin;
                	}
                }
                context.setLeft(x);
                context.setWidth(w);
                context.trigger(Resize.EVENT.Resize, { width: context._width, height: context._height });

            };

            // 上侧
            this._onDragTopStart = function (e, xy) {
            	_clientWidth = $(window).width();
				_clientHeight = $(window).height();
				context.trigger(Resize.EVENT.Mousedown, { width: context._width, height: context._height });
                context._startTop = context._top = context.getTop();
                context._startHeight = context._height = context.getHeight();
            };
            this._onDragTop = function (e, xy) {
            	//J.out(xy.x + "," + xy.y);
                var h = context._startHeight - xy.y;
                var y = context._startTop + xy.y;
                if (h < context.minHeight) {
                    h = context.minHeight;
                    y = context._startTop + (context._startHeight - h);
                }
                if(context.isLimited){
                	if(y < context._topMargin){
                		h = h - (context._topMargin - y);
                		y = context._topMargin;
                	}
                }
                context.setTop(y);
                context.setHeight(h);
                context.trigger(Resize.EVENT.Resize, { width: context._width, height: context._height });
            };



            // 右侧
            this._onDragRightStart = function (e, xy) {
            	_clientWidth = $(window).width();
				_clientHeight = $(window).height();
				context.trigger(Resize.EVENT.Mousedown, { width: context._width, height: context._height });
				context._startWidth = context._width = context.getWidth();
                 //context._startWidth = ;
                //context._startWidth = context._width = context.startSize.width; // context.startWidth;
            };
            this._onDragRight = function (e, xy) {
                var w = context._startWidth + xy.x;
                if (w < context.minWidth) {
                    w = context.minWidth;
                }
                if(context.isLimited){
                	if(context.getLeft() + w + context._rightMargin > _clientWidth){
                		w = _clientWidth - context.getLeft() - context._rightMargin;
                	}
                }
                context.setWidth(w);
                context.trigger(Resize.EVENT.Resize, { width: context._width, height: context._height });
            };


            // 下侧
            this._onDragBottomStart = function (e, xy) {
            	_clientWidth = $(window).width();
				_clientHeight = $(window).height();
				context.trigger(Resize.EVENT.Mousedown, { width: context._width, height: context._height });
                context._startHeight = context._height = context.getHeight();
            };
            this._onDragBottom = function (e, xy) {
                var h = context._startHeight + xy.y;
                if (h < context.minHeight) {
                    h = context.minHeight;
                }
                if(context.isLimited){
                	if(context.getTop() + h + context._bottomMargin > _clientHeight){
                		h = _clientHeight - context.getTop() - context._bottomMargin;
                	}
                }
                context.setHeight(h);
                context.trigger(Resize.EVENT.Resize, { width: context._width, height: context._height });
            };

            // 左上
            this._onDragLeftTopStart = function (e, xy) {
                context._onDragLeftStart(e, xy);
                context._onDragTopStart(e, xy);
            };
            this._onDragLeftTop = function (e, xy) {
                context._onDragLeft(e, xy);
                context._onDragTop(e, xy);
            };

            // 左下
            this._onDragLeftBottomStart = function (e, xy) {
                context._onDragLeftStart(e, xy);
                context._onDragBottomStart(e, xy);
            };
            this._onDragLeftBottom = function (e, xy) {
                context._onDragLeft(e, xy);
                context._onDragBottom(e, xy);
            };


            // 右下
            this._onDragRightBottomStart = function (e, xy) {
                context._onDragRightStart(e, xy);
                context._onDragBottomStart(e, xy);
            };
            this._onDragRightBottom = function (e, xy) {
                context._onDragRight(e, xy);
                context._onDragBottom(e, xy);
            };

            // 右上
            this._onDragRightTopStart = function (e, xy) {
                context._onDragRightStart(e, xy);
                context._onDragTopStart(e, xy);
            };
            this._onDragRightTop = function (e, xy) {
                context._onDragRight(e, xy);
                context._onDragTop(e, xy);
            };



            $E.addObserver(this["_dragController_" + handleNames.t], Drag.EVENT.Start, this._onDragTopStart);
            $E.addObserver(this["_dragController_" + handleNames.t], Drag.EVENT.Move, this._onDragTop);
            $E.addObserver(this["_dragController_" + handleNames.t], Drag.EVENT.End, this._onDragEnd);

            $E.addObserver(this["_dragController_" + handleNames.r], Drag.EVENT.Start, this._onDragRightStart);
            $E.addObserver(this["_dragController_" + handleNames.r], Drag.EVENT.Move, this._onDragRight);
            $E.addObserver(this["_dragController_" + handleNames.r], Drag.EVENT.End, this._onDragEnd);

            $E.addObserver(this["_dragController_" + handleNames.b], Drag.EVENT.Start, this._onDragBottomStart);
            $E.addObserver(this["_dragController_" + handleNames.b], Drag.EVENT.Move, this._onDragBottom);
            $E.addObserver(this["_dragController_" + handleNames.b], Drag.EVENT.End, this._onDragEnd);

            $E.addObserver(this["_dragController_" + handleNames.l], Drag.EVENT.Start, this._onDragLeftStart);
            $E.addObserver(this["_dragController_" + handleNames.l], Drag.EVENT.Move, this._onDragLeft);
            $E.addObserver(this["_dragController_" + handleNames.l], Drag.EVENT.End, this._onDragEnd);



            $E.addObserver(this["_dragController_" + handleNames.rb], Drag.EVENT.Start, this._onDragRightBottomStart);
            $E.addObserver(this["_dragController_" + handleNames.rb], Drag.EVENT.Move, this._onDragRightBottom);
            $E.addObserver(this["_dragController_" + handleNames.rb], Drag.EVENT.End, this._onDragEnd);

            $E.addObserver(this["_dragController_" + handleNames.rt], Drag.EVENT.Start, this._onDragRightTopStart);
            $E.addObserver(this["_dragController_" + handleNames.rt], Drag.EVENT.Move, this._onDragRightTop);
            $E.addObserver(this["_dragController_" + handleNames.rt], Drag.EVENT.End, this._onDragEnd);

            $E.addObserver(this["_dragController_" + handleNames.lt], Drag.EVENT.Start, this._onDragLeftTopStart);
            $E.addObserver(this["_dragController_" + handleNames.lt], Drag.EVENT.Move, this._onDragLeftTop);
            $E.addObserver(this["_dragController_" + handleNames.lt], Drag.EVENT.End, this._onDragEnd);

            $E.addObserver(this["_dragController_" + handleNames.lb], Drag.EVENT.Start, this._onDragLeftBottomStart);
            $E.addObserver(this["_dragController_" + handleNames.lb], Drag.EVENT.Move, this._onDragLeftBottom);
            $E.addObserver(this["_dragController_" + handleNames.lb], Drag.EVENT.End, this._onDragEnd);
        },

        setWidth: function (w) {
            $(this.effectEl).width( w );
            this._width = w;
        },
        setHeight: function (h) {
        	$(this.effectEl).height( h );
            this._height = h;
        },

        setLeft: function (x) {
        	$(this.effectEl).css("left", x + "px");
            this._left = x;
        },
        setTop: function (y) {
        	$(this.effectEl).css("top", y + "px");
            this._top = y;
        },


        getWidth: function () {
            return $(this.effectEl).width();
        },
        getHeight: function () {
            return $(this.effectEl).height();
        },

        getLeft: function () {
            return parseInt($(this.effectEl).css("left"));
        },
        getTop: function () {
            return parseInt($(this.effectEl).css("top"));
        },
        getId: function () {
            return id++;
        },

        lock: function () {
            for (var p in handleNames) {
                this["_dragController_" + handleNames[p]].lock();
            }
        },
        unlock: function () {
            for (var p in handleNames) {
                this["_dragController_" + handleNames[p]].unlock();
            }
        },
        show: function () {
            for (var p in handleNames) {
                this["_dragController_" + handleNames[p]].show();
            }
        },
        hide: function () {
            for (var p in handleNames) {
                this["_dragController_" + handleNames[p]].hide();
            }
        }
    };

    Resize = Jooe.extend(Resize.prototype);
    Resize.EVENT = {
    	Start: "Start",
    	Move: "Move",
    	End: "End",
    	Mousedown: "Mousedown",
    	Resize: "Resize"
    };
    
    this.Resize = Resize;
});

/**
 * tab模块
 */
Jet().$package("xhcms.ui", function(J){
	var $ = jQuery, $E = Jooe;
		
		
	/**
	 * Tab类
	 * 
	 * @memberOf ui
	 * 
	 * @param {Element} triggers tab head元素
	 * @param {Element} sheets tab body元素
	 * @param {Object} config 其他选项，如:isLimited,leftMargin...
	 * @returns
	 * 
	 * 
	 */
	var Tab = function(){};
	Tab.prototype = {
		init: function(triggers,sheets,config){
			this.tabs = [];             //tab的集合
			this.currentTab = null;     //当前tab
			this.config = {
				defaultIndex : 0,       //默认的tab索引
				triggerEvent : 'click', //默认的触发事件
				slideEnabled : false,   //是否自动切换
				slideInterval : 5*1000,   //切换时间间隔
				slideDelay : 300,       //鼠标离开tab继续切换的延时
				autoInit : true,        //是否自动初始化
				onShow:function(){ }    //默认的onShow事件处理函数
			};
		
			this.setConfig(config);

			if(triggers && sheets) {
				this.addRange(triggers,sheets);
				if(this.config.autoInit){
					this.doInit();
				}
			}
		},
		/**
		 * 设置config
		 * @param {object} config 配置项如{'slideEnabled':true,'defaultIndex':0,'autoInit':false}
		 */
		setConfig:function(config){
			if(!config) return;
			for(var i in config){
				this.config[i] = config[i];
			}
		},
		/**
		 * 增加tab
		 * @param tab={trigger:aaaa, sheet:bbbb} 
		 * @param {string|HTMLElement} trigger
		 * @param {string|HTMLElement} sheet
		 * */
		add:function(tab){
			if(!tab) return;
			
			if(tab.trigger){
				this.tabs.push(tab);
				$(tab.trigger).show();
			}
		},
		
		/**
		 * 增加tab数组
		 * @param {array} triggers HTMLElement数组
		 * @param {array} sheets HTMLElement数组
		 * */
		addRange:function(triggers, sheets){
			if(!triggers||!sheets) return;
			if(triggers.length && sheets.length && triggers.length == sheets.length){
				for(var i = 0, len = triggers.length; i < len; i++){
					this.add({trigger:triggers[i],sheet:sheets[i]});
				}
			}
		},
		
		/**
		 * 设置tab为当前tab并显示
		 * @param {object} tab  tab对象 {trigger:HTMLElement,sheet:HTMLElement}
		 * */
		select:function(tab){
			if(!tab || (!!this.currentTab && tab.trigger == this.currentTab.trigger)) return;
			if(this.currentTab){
				$(this.currentTab.trigger).removeClass('current');
				if(this.currentTab.sheet){
					$(this.currentTab.sheet).hide();
				}
				
			}
			this.currentTab = tab;
			this.show();
		},
		
		/**
		 * 设置tab为隐藏的
		 * @param {object} tab  tab对象 {trigger:HTMLElement,sheet:HTMLElement}
		 * */
		remove:function(tab){
			if(!tab) return;
			
			
			if(tab.trigger){
				$(tab.trigger).removeClass('current');
				$(tab.trigger).hide();
			}
			if(tab.sheet){
				$(tab.sheet).hide();
			}
			
			var index=this.indexOf(tab);
			this.tabs.splice(index,index);
	
			if(tab.trigger == this.currentTab.trigger){
				if(index==0){
					//this.currentTab=this.tabs[(index+1)];
					this.select(this.tabs[(index+1)]);
				}else{
					//this.currentTab=this.tabs[(index-1)];
					this.select(this.tabs[(index-1)]);
				}
			}
		},
		/**
		 * 显示当前被选中的tab
		 * */
		show:function(){
			
			if(this.currentTab.trigger){
				$(this.currentTab.trigger).show();
			}
			$(this.currentTab.trigger).addClass('current');
			if(this.currentTab.sheet){
				$(this.currentTab.sheet).show();
			}
			this.trigger(Tab.EVENT.Show, [this.currentTab]);

		},
		/**
		 * 自动切换
		 * */
		slide:function(){
			var	config = this.config,
				_this = this,
				intervalId,
				delayId;
			$.each(this.tabs, function(index, tab){
				$E.on(tab.trigger, 'mouseover' , clear);
				$E.on(tab.sheet, 'mouseover' , clear);
				
				$E.on(tab.trigger, 'mouseout' , delay);
				$E.on(tab.sheet, 'mouseout' , delay);
			});
			start();
			function start() {
				var i = _this.indexOf(_this.currentTab);
				if( i == -1 ) return;
				intervalId = window.setInterval(function(){
					var tab = _this.tabs[ ++i % _this.tabs.length ];
					if(tab){
						_this.select(tab);
					}
				},config['slideInterval']);
			}
			function clear() {
				window.clearTimeout(delayId);
				window.clearInterval(intervalId);	
			}
			function delay() {
				delayId = window.setTimeout(start,config['slideDelay']);
			}
		},
		/**
		 * 获取tab在tabs数组中的索引
		 * @param {object} tab  tab对象 {trigger:HTMLElement,sheet:HTMLElement}
		 * */
		indexOf:function(tab){
			for(var i = 0, len = this.tabs.length; i < len; i++){
				if(tab.trigger == this.tabs[i].trigger)
					return i;
			}
			return -1;
		},
		/**
		 * 初始化函数
		 * */
		doInit:function(){
			var config = this.config,
				_this = this;

			$.each(this.tabs, function(index, tab){
				$E.on(tab.trigger,config['triggerEvent'], function(){
					_this.select.call(_this,tab);
				});
				if(tab.sheet){
					$(tab.sheet).hide();
				}
			});
			
			this.select(this.tabs[config['defaultIndex']]);
			if(config['slideEnabled']){
				this.slide();
			}
		}
	};
	Tab = Jooe.extend(Tab.prototype);
	Tab.EVENT = {
		Show: "Show"
	};
	
	this.Tab = Tab;

});


/**
 * MaskLayer模块
 */
Jet().$package("xhcms.ui", function(J){
	var $ = jQuery, $E = Jooe;
		
	/**
	 * MaskLayer 遮罩层类
	 * 
	 * @memberOf ui
	 * @Class
	 * 
	 * @param {Object} option 其他选项，如:zIndex,appendTo...
	 * @returns
	 * 
	 * 
	 */
	var MaskLayer = function(){};
	MaskLayer.prototype = {

		/**
		 * 初始化函数
		 * */
		init:function(option){
			var context = this;
			option = option||{};
			option.zIndex = typeof option.zIndex !== "undefined" ? option.zIndex : 9000000;
			option.appendTo = option.appendTo || document.body;
			
			this.container = $('<div class="maskLayer"></div>');
			this.container.html('\
					<div class="maskBackground"></div>\
					<div id="maskLayerBody"></div>\
				');
			this.setZIndex(option.zIndex);
			this.container.appendTo(option.appendTo);
			this.container = this.container[0];
			
//			var observer = {
//				onMaskLayerClick : function(){
//					$E.notifyObservers(context, MaskLayer.EVENT.Click, context);
//				}
//			};
//			
//			$E.on(this.container, "click", observer.onMaskLayerClick);
			
			this.body = $("#maskLayerBody")[0];
		},
		
		append : function(el){
			$(this.body).append(el);
		},
		
		show : function(){
			$(this.container).height($(this.container).parent().height());
			$(this.container).width($(this.container).parent().width());
			$(this.container).show();
			this.trigger(MaskLayer.EVENT.Show);
			this._isShow = true;
		},
		hide : function(){
			$(this.container).hide();
			this.trigger(MaskLayer.EVENT.Hide);
			this._isShow = false;
		},
		isShow : function(){
			return this._isShow;
		},
		toggleShow : function(){
			if(this.isShow()){
				this.hide();
			}else{
				this.show();
			}
		},
		getZIndex : function(){
			return this._zIndex;
		},
		
		setZIndex : function(zIndex){
			$(this.container).css("zIndex", zIndex);
			this._zIndex = zIndex;
		},
		fadeIn : function(){
			this.show();
		},
		
		fadeOut : function(){
			this.hide();
		},
		
		// 关于
		about : function(){
			
		}
	};
	MaskLayer = Jooe.extend(MaskLayer.prototype);
	MaskLayer.EVENT = {
		Click: "Click",
		Show: "Show",
		Hide: "Hide"
	};
	this.MaskLayer = MaskLayer;
});

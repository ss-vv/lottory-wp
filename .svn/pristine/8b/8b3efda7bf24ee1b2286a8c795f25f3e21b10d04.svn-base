/**
 * 通过jQuery扩展Event接口，扩展Jooe对事件支持。
 * 
 * <pre>
 * 	增加函数： trigger(), addObserver()(可用Jooe.on()), removeObserver()(可用Jooe.off())
 *  用法同jQuery的： trigger, bind, unbind
 *  
 *  可通过检验Jooe.EventDriven来判断是否支持事件驱动。
 * </pre>
 */
;(function(window, undefined){
	var Jooe = window.Jooe;
	if(typeof Jooe !== "undefined"
		&& typeof Jooe.EventDriven === "undefined" ){
		var trigger = function(obj, event, data){
			return jQuery(obj).trigger(event, data);
		};
		var addObserver = function(obj, event, data, func){
			if(func == null){
				return jQuery(obj).bind(event, data);
			}else{
				return jQuery(obj).bind(event, data, func);
			}
		};
		var removeObserver = function(obj, event, func){
			return jQuery(obj).unbind(event, func);
		};
		
		var EventDriven = function(){};
		EventDriven.prototype = {
			trigger: function(event, data){
				return trigger(this, event, data);
			},
			addObserver: function(event, data, func){
				if(func == null)
					return addObserver(this, event, data);
				else
					return addObserver(this, event, data, func);
			},
			removeObserver: function(event, func){
				return removeObserver(this, event, func);
			}
		};
		Jooe.extend(true, EventDriven.prototype);
		
		//设置静态函数
		Jooe.trigger = trigger;
		Jooe.on = Jooe.addObserver = addObserver;
		Jooe.off = Jooe.removeObserver = removeObserver;
		Jooe.EventDriven = EventDriven;
	}
})(window);

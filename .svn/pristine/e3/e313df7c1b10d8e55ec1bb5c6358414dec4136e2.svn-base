;(function($) {
	/**
	 * 投注记录面板
	 */
	var betRecordPanel = function(element, options) {
	    this.$elt = element;
	    this.options = $.extend({}, $.fn.betRecordPanel.defaults, options);
	    this.initialized();
	};
	betRecordPanel.prototype = {
	    initialized : function(){
	    	this.settings = {};
	    	$.extend(this.settings, LT);
	    	this.rend();
	    },
	    rend : function(){
	    	if(this.options.lotteryId) return;
	    	var self = this;
	    	$(self.$elt).append('<div class="loading" style="height: 40px;"></div>');
	        $.ajax({
	        	type:"post",
	        	dataType:'json',
	        	data:self.options,
	    		url:this.options.betRecordUrl,
	    		success : function(json, e) {
	    			if(json) {
	    				self.view($(self.$elt), json.data);
	    				$(self.$elt).find(".loading").remove();
	    			}
	    		},
	    		error:function (data, e){
	    		}
	    	});
	    },
	    view:function(elt,data) {
	    	elt.betRecordView({
				"bet" : data.all,
				"lotteryId":self.options.lotteryId
			});
	    	$("#data-lottery-jz").betRecordView({
	    		"bet" : data.jczq,
	    		"lotteryId":"JCZQ"
	    	});
	    	$("#data-lottery-jl").betRecordView({
	    		"bet" : data.jclq,
	    		"lotteryId":"JCLQ"
	    	});
	    	$("#data-lottery-zc").betRecordView({
	    		"bet" : data.ctzc,
	    		"lotteryId":"CTZC"
	    	});
	    	$("#data-lottery-bd").betRecordView({
	    		"bet" : data.bjdc,
	    		"lotteryId":"BJDC"
	    	});
	    }
	};
	  	
	$.fn.betRecordPanel = function (option) {
		return this.each(function(input_field) {
			var $this = $(this), data = $this.data(option.toString());
		    if (!data) {
		    	$this.data(option.toString(), (data = new betRecordPanel(this, option)));
		    }
		});
	};
	$.fn.betRecordPanel.defaults = {
		betRecordUrl : LT.Settings.URLS.betRecord.ajaxUserBetRecord,
	};
})(window.jQuery);

$(function() {
	$("#betRecord").find("#tab-lottery-qb").lotteryTab({"lotteryId":"", "data-list":"data-lottery-qb"});
	$("#betRecord").find("#tab-lottery-jz").lotteryTab({"lotteryId":"JCZQ", "data-list":"data-lottery-jz"});
	$("#betRecord").find("#tab-lottery-jl").lotteryTab({"lotteryId":"JCLQ", "data-list":"data-lottery-jl"});
	$("#betRecord").find("#tab-lottery-zc").lotteryTab({"lotteryId":"CTZC", "data-list":"data-lottery-zc"});
	$("#betRecord").find("#tab-lottery-bd").lotteryTab({"lotteryId":"BJDC", "data-list":"data-lottery-bd"});
});
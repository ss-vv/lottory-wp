/**
 * 彩种标签JS服务
 */
;(function($) {
	/**
	 * @param options 包含字段
	 *  	lotteryId
	 */
	var lotteryTab = function (element, options) {
		this.$elt = element;
		this.options = options;
		this.init();
	};
	lotteryTab.prototype = {
		init:function() {
			this.settings = {};
			$.extend(this.settings, LT);
			this.rend();
		},
		rend:function() {
			var lotteryId = this.options["lotteryId"];
			var dataList = this.options["data-list"];
			$("#"+dataList).betRecordPanel({"lotteryId":lotteryId});
			this.bind();
		},
		bind:function() {
			var self = this;
			$(self.$elt).click(function() {
				self.layout();
			});
		},
		layout:function() {
			var self = this;
			$(self.$elt).parent().children().removeClass("active");
			$(self.$elt).addClass("active");
			var self_data_Id = "#"+self.options["data-list"];
			$(self_data_Id).parent().children("div").hide();
			$(self_data_Id).show();
		}
	}
	$.fn.lotteryTab = function(option) {
		return this.each(function(input_field) {
			new lotteryTab(this, option);
		});
	};
})(window.jQuery);
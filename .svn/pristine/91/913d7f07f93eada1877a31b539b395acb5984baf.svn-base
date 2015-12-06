;
(function($, undefined) {
	/**
	 * 投注记录晒单对话框
	 */
	var shareDialog = function($this,options,$sourceTarget) {
		this.$body = $this;
		this.$sourceTarget = $sourceTarget;
		this.options = $.extend({}, $.fn.shareDialog.defaults, options);
		this.$shareDialogTmpl = $("#show-scheme-dialog-tmpl").html();
		this.initialized();
	};
	shareDialog.prototype = {
		initialized : function() {
			this.settings = {};
			$.extend(this.settings, LT);
			this.rend();
			this.settings.delegateEvents(this);
		},
		rend : function() {
			
		},
		events : {
			
		},
		reset : function(option,$sourceTarget) {
			
		},
		show : function (){
			this.$shareWindow.modal('show');
			this.$shareContent.focus();
		}
	};
})(window.jQuery);

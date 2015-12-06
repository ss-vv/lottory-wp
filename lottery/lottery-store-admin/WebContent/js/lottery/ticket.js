Jet().$package(function(J){
	var $ = jQuery, $E = Jooe, Editor = xhcms.cms.Editor;
	
	var ticketEditor = function(){};
	ticketEditor.prototype = {
		init: function(option){
			var opt = option || {};
			opt.title = opt.title || "中奖情况";
			this._schemeId = opt.schemeId;
			this._loadUrl = opt.loadUrl;
			this._super.init(opt);
		},
		initData: function(){
			$.getJSON(this._loadUrl, function(ret){
				var ct = $("#ticketTable", this.body);
				$("#ticketTable tr").slice(1).remove();
				
				$.each(ret, function(i, n) {
					var win = "否";
					if (n.winStatus == 5203 || n.winStatus == 3) {
						win = "是";
					}
					var tr = "<tr>"+
								"<td>" + (i+1) + "</td>"+
								"<td>" + n.code + "</td>"+
								"<td>" + win + "</td>"+
								"<td><input type='hidden' value='" + n.afterTaxBonus + "'/>" + n.afterTaxBonus + " 元</td>"+
							 "</tr>";
					ct.append(tr);
				});
				
				var count = 0;
				$("#ticketTable tr:not(:first)").each(function(){
					var bonus = $("td:nth-child(4) input",this).val();
					if( null != bonus && bonus >= 0) {
						count  = parseFloat(count) + parseFloat(bonus) ;
					}
				});
				
				ct.append("<tr><td colspan='4'>合计：" + count + " 元</td></tr>");
			});
		},
		getTemplate: function() {
			return $("#templateTable")[0].text;
		},
		clickOkButton: function(){
			var info = {};
			$(":input",this.body).each(function(e){
				info[this.name] = this.value;
			});
			$.extend(this._info, info);
			return this.trigger(Editor.EVENT.ClickOkButton, this._info);
		}
	};
	ticketEditor = Editor.extend(ticketEditor.prototype);
	ticketEditor.EVENT = $.extend({}, Editor.EVENT);
	
	this.TicketEditor = ticketEditor;

});
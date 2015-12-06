Jet().$package(function(J){
	var $ = jQuery, $E = Jooe, Editor = xhcms.cms.Editor, $base = base_path;
	var mask = null;
	var ctzcPresetEditor = function(){};
	ctzcPresetEditor.prototype = {
		init: function(option){
			mask = new xhcms.ui.MaskLayer({
				zIndex: 10000
			});
			option = option || {};
			option.title = option.title || '编辑期信息';
			this._super.init(option);
			this.setXY(this.getX(),this.getY()+20);
			this.observer = {
				save: function(e, data){
					var url = $base + '/preset/set_ctzc_bonus.do';
					$.get(url, data, function(resp){
						location.reload();
					});
					mask.hide();
					return true;
				}
			};
			
			$E.addObserver(this, Editor.EVENT.ClickOkButton, this.observer.save);
		},
		initData: function(){
			var o = this.option.data;
			var html = '<div class="tbebox tbebox_B">'
				+ '<table class="tbel" cellspacing="0" cellpadding="0">'
				+ '<tr><td class="tdl tar" style="width:100px;">期号：</td><td class="tdr tal">{0}</td></tr>'
				+ '<tr><td class="tdl tar">玩法：</td><td class="tdr tal">{1}</td></tr>'
				+ '<tr><td class="tdl tar">一等奖：</td><td class="tdr tal"><input name="yidengjiang"/>元</td></tr>'
				+ '<tr><td class="tdl tar">二等奖：</td><td class="tdr tal"><input name="erdengjiang"/>元</td></tr>'
				+ '<tr><td class="tdl tar">任九：</td><td class="tdr tal"><input name="renjiu"/> 元</td></tr>'
				+ '<input name="playId" type="hidden" value="{2}"/>'
				+ '<input name="issueNumber" type="hidden" value="{0}"/>'
				+ '</table></div>';
			this.setHtml($.format(html, o.issueNumber,o.playName,o.playId));
			if("4场进球"==o.playName || o.playName=="6场半全"){
				$('[name="erdengjiang"]').closest('tr').remove();
				$('[name="renjiu"]').closest('tr').remove();
			} else if("胜负任九"==o.playName){
				$('[name="yidengjiang"]').closest('tr').remove();
				$('[name="erdengjiang"]').closest('tr').remove();
			} else {
				$('[name="renjiu"]').closest('tr').remove();
			}
			mask.show();
		},
		clickOkButton: function(){
			var data = {};
			$(':input', this.body).each(function(e){
				data[this.name] = $.trim(this.value);
			});
			return this.trigger(Editor.EVENT.ClickOkButton, data);
		},
		Close: function(){
			mask.hide();
		}
	};
	ctzcPresetEditor = Editor.extend(ctzcPresetEditor.prototype);
	this.ctzcPresetEditor = ctzcPresetEditor;
	
});
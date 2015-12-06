Jet().$package(function(J){
	var $ = jQuery, $E = Jooe, Editor = xhcms.cms.Editor, $base=base_path;
	var mask = null;
	var betTimeEditor = function(){};
	betTimeEditor.prototype = {
		init: function(option){
			mask = new xhcms.ui.MaskLayer({
				zIndex: 10000
			});
			option = option || {};
			option.title = option.title || '编辑投注时间';
			option.data = option.data || {};
			this._super.init(option);
			this.setXY(this.getX(),this.getY()+20);
			this.observer = {
				save: function(e, data){
					var reg = /^(([0-1]{1}[0-9]{1})|([2]{1}[0-3]{1}))\:([0-5]{1}[0-9]{1})\:([0-5]{1}[0-9]{1})$/;
					if(!reg.test(data.opentime) || !reg.test(data.endtime)|| !reg.test(data.mtime)){
						alert("时间格式不正确，请检查！");
						return false;
					}
					if(data.opentime >= data.endtime && data.endtimeKua==0){
						alert("开售时间必须早于停售时间！");
						return false;
					}
					$.get($base+"/scheme/save_bet_time.do", data, function(resp){
						if(eval(resp)=="success"){
							alert("保存成功");
							window.location.reload();
						} else {
							alert("保存失败");
						}
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
				+ '<tr><td class="tdl tar" style="width:100px;">周：</td><td class="tdr tal">{0}</td></tr>'
				+ '<tr><td class="tdl tar">开售时间：</td><td class="tdr tal"><input name="opentime" value="{1}"/></td></tr>'
				+ '<tr><td class="tdl tar">停售时间：</td><td class="tdr tal"><input name="endtime" value="{2}"/></td></tr>'
				+ '<tr><td class="tdl tar">机器截止时间：</td><td class="tdr tal"><input name="mtime" value="{3}"/></td></tr>'
				+ '<tr><td class="tdl tar">停售时间是否跨天：</td><td class="tdr tal"><select name="endtimeKua"><option value="0">否</option><option value="1">是</option></select></td></tr>'
				+ '<tr><td class="tdl tar">机器截止时间是否跨天：</td><td class="tdr tal"><select name="mtimeKua"><option value="0">否</option><option value="1">是</option></select></td></tr>'
				+ '<input type="hidden" name="id" value="{4}"/>'
				+ '</table></div>';
			this.setHtml($.format(html, o.week,o.opentime,o.endtime,o.machineOfftime,o.id,o.endtimeKua,o.machineOfftimeKua));
			mask.show();
			if(o.endtimeKua=="是"){
				$("select[name='endtimeKua']").find("option[value='1']").attr("selected","selected");
			} else {
				$("select[name='endtimeKua']").find("option[value='0']").attr("selected","selected");
			}
			if(o.machineOfftimeKua=="是"){
				$("select[name='mtimeKua']").find("option[value='1']").attr("selected","selected");
			} else {
				$("select[name='mtimeKua']").find("option[value='0']").attr("selected","selected");
			}
		},
		clickOkButton: function(){
			var data = {};
			$(':input', this.body).each(function(e){
				data[this.name] = $.trim(this.value);
			});
			$('select', this.body).each(function(e){
				data[this.name] = $.trim(this.value);
			});
			return this.trigger(Editor.EVENT.ClickOkButton, data);
		},
		Close: function(){
			mask.hide();
		}
	};
	betTimeEditor = Editor.extend(betTimeEditor.prototype);
	this.betTimeEditor = betTimeEditor;
});

$(document).ready(function (){
	
});
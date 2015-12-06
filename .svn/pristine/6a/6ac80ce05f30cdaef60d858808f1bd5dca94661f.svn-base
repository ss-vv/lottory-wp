Jet().$package(function(J){
	var $ = jQuery, $E = Jooe, Editor = xhcms.cms.Editor;
	var mask = null;
	var activityEditor = function(){};
	activityEditor.prototype = {
		init: function(option){
			mask = new xhcms.ui.MaskLayer({
				zIndex: 10001
			});
			option = option || {};
			var title = "";
			var url = "";
			if(option.data) {
				title = "编辑活动";
				url = "aj_modify.do";
			} else {
				title = "增加活动";
				url = "aj_add.do";
			}
			option.title = option.title || title;
			option.data = option.data || {id: '', activityName: '', activityDesc: '', status:1, sequenceNumber:0};
			
			this._super.init(option);
			this.setXY(this.getX(),this.getY()+20);
			
			this.observer = {
				save: function(e, data){
					$.post(url, data, function(resp){
						var json = $.parseJSON(resp);
						$.parseJSONData(json, function(data){
							if($.isFunction(option.callback)){
								option.callback();
							}
						});
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
				+ '<table class="tbel" cellspacing="0" cellpadding="0">';
				if(o.id) {
					html = html + '<tr><td class="tdl tar"><span class="fc_red">*</span> 活动编号：</td><td class="tdr tal"><input type="text" name="activityNotifyId" value="{0}" readonly="true"/></td></tr>';
				}
				html = html + '<tr><td class="tdl tar"><span class="fc_red">*</span> 活动名称：</td><td class="tdr tal"><input type="text" name="activityName" value="{1}"/></td></tr>'
				+ '<tr><td class="tdl tar"><span class="fc_red">*</span> 活动内容：</td><td class="tdr tal"><textarea style="height:110px;width:200px" name="activityDesc">' + o.activityDesc + '</textarea></td></tr>'
				+ '<tr><td class="tdl tar"><span class="fc_red">*</span> 状态：</td><td class="tdr tal"><select name="status" value="{2}"><option value="1">有效</option><option value="0">无效</option></select></td></tr>'
				+ '<tr class="last"><td class="tdl tar"><span class="fc_red">*</span> 顺序：</td><td class="tdr tal"><input type="text" name="sequenceNumber" value="{3}"/></td></tr>'
				+ '</table></div>';
			this.setHtml($.format(html, o.id, o.activityName, o.status, o.sequenceNumber));
			if(o.id != ''){
				$('input[name="activityNotifyId"]', this.body).attr('readonly', true);
			}
			setTimeout(function(){
				$('select[name="status"]', this.body).val(o.status);
			}, 1);
			mask.show();
		},
		clickOkButton: function(){
			var data = {};
			$(':input', this.body).each(function(e){
				data[this.name] = $.trim(this.value);
			});
			
			if(data['activityName'] == ''){
				alert('请输入活动名称');
				return false;
			}
			if(data['activityDesc'] == ''){
				alert('请输入活动内容');
				return false;
			}
			if(data['sequenceNumber'] == ''){
				alert('请输入活动顺序');
				return false;
			} else {
				var pattern = /^\d+$/g;
				if(!pattern.exec(data['sequenceNumber'])) {
					alert('活动顺序必须为数字');
					return false;
				}
			}
			return this.trigger(Editor.EVENT.ClickOkButton, data);
		},
		Close: function(){
			mask.hide();
		}
	};
	activityEditor = Editor.extend(activityEditor.prototype);
	this.activityEditor = activityEditor;	
});
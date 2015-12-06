Jet().$package(function(J){
	var $ = jQuery, $E = Jooe, Editor = xhcms.cms.Editor, $base = base_path;
	var mask = null;
	var messageEditor = function(){};
	messageEditor.prototype = {
		init: function(option){
			mask = new xhcms.ui.MaskLayer({
				zIndex: 10001
			});
			option = option || {};
			var title = "";
			var url = "";
			if(option.data) {
				title = "编辑信息";
				url = $base + "aj_modify.do";
			} else {
				title = "增加信息";
				url = $base + "aj_add.do";
			}
			option.title = option.title || title;
			option.data = option.data || {id: '', messageName: '', messageDesc: '', status:1};
			
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
					html = html + '<tr><td class="tdl tar"><span class="fc_red">*</span> 信息编号：</td><td class="tdr tal"><input type="text" name="sysMessageId" value="{0}" readonly="true"/></td></tr>';
				}
				html = html + '<tr><td class="tdl tar"><span class="fc_red">*</span> 信息标题：</td><td class="tdr tal"><input type="text" name="subject" value="{1}"/></td></tr>'
				+ '<tr><td class="tdl tar"><span class="fc_red">*</span> 信息内容：</td><td class="tdr tal"><textarea style="height:110px;width:200px" name="note">' + o.messageDesc + '</textarea></td></tr>'
				+ '<tr><td class="tdl tar"><span class="fc_red">*</span> 状态：</td><td class="tdr tal"><select name="status" value="{2}"><option value="1">有效</option><option value="0">无效</option></select></td></tr>'
				+ '</table></div>';
			this.setHtml($.format(html, o.id, o.messageName, o.status));
			if(o.id != ''){
				$('input[name="sysMessageId"]', this.body).attr('readonly', true);
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
			
			if(data['messageName'] == ''){
				alert('请输入信息名称');
				return false;
			}
			if(data['messageDesc'] == ''){
				alert('请输入信息内容');
				return false;
			}
			return this.trigger(Editor.EVENT.ClickOkButton, data);
		},
		Close: function(){
			mask.hide();
		}
	};
	messageEditor = Editor.extend(messageEditor.prototype);
	this.messageEditor = messageEditor;	
});
Jet().$package(function(J){
	var $ = jQuery, $E = Jooe, Editor = xhcms.cms.Editor;
	var mask = null;
	var optionEditor = function(){};
	optionEditor.prototype = {
		init: function(option){
			mask = new xhcms.ui.MaskLayer({
				zIndex: 10000
			});
			option = option || {};
			option.title = option.title || '编辑玩法选项';
			option.lotteryId = option.lotteryId || 'JCZQ';
			option.data = option.data || {id: '0', playId: option.playId != null ? option.playId : '', value: '', name: '', status: '', note: ''};
			
			this._super.init(option);
			this.setXY(this.getX(),this.getY()+20);
			
			this.observer = {
				save: function(e, data){
					$.post('/option/aj_save.do', data, function(resp){
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
			var ctx = this, o = this.option.data;
			var html = '<div class="tbebox tbebox_B">'
				+ '<table class="tbel" cellspacing="0" cellpadding="0">'
				+ '<tr><td class="tdl tar" width="100"><span class="fc_red">*</span> 玩法选项ID：</td><td class="tdr tal"><input type="text" name="option.id" value="{0}" maxLength="10" size="10"/></td></tr>'
				+ '<tr><td class="tdl tar"><span class="fc_red">*</span> 玩法编号：</td><td class="tdr tal"><select name="option.playId"></select></td></tr>'
				+ '<tr><td class="tdl tar"><span class="fc_red">*</span> 选项值：</td><td class="tdr tal"><input type="text" name="option.value" size="2" maxlength="2" value="{1}"/></td></tr>'
				+ '<tr><td class="tdl tar"><span class="fc_red">*</span> 选项名称：</td><td class="tdr tal"><input type="text" name="option.name" size="16" maxlength="16" value="{2}"/></td></tr>'
				+ '<tr><td class="tdl tar">状态：</td><td class="tdr tal"><input type="radio" name="option.status" size="20" maxlength="16" value="0" checked/>有效 <input type="radio" name="option.status" size="20" maxlength="16" value="1"/>无效</td></tr>'
				+ '<tr class="last"><td class="tdl tar">备注信息：</td><td class="tdr tal"><textarea rows="3" cols="30" name="option.note">{3}</textarea></td></tr>'
				+ '</table></div>';
			this.setHtml($.format(html, o.id, o.value, o.name, o.note));
			$('input[name="option.id"]', this.body).attr('readonly', true);
			$('select[name="option.playId"]', this.body).initPlay({lid: ctx.option.lotteryId, defVal: o.playId});
			$('input[name="option.status"]', this.body).each(function(){
				if(this.value == o.status){
					this.checked = true;
				}
			});
			mask.show();
		},
		clickOkButton: function(){
			var data = {};
			$(':input', this.body).each(function(e){
				if(this.name != 'option.status' || this.checked){
					data[this.name] = $.trim(this.value);
				}
			});
			
			if(data['option.value'] == ''){
				alert('请输入选项值');
				return false;
			}
			if(data['option.name'] == ''){
				alert('请输入选项名称');
				return false;
			}
			return this.trigger(Editor.EVENT.ClickOkButton, data);
		},
		Close: function(){
			mask.hide();
		}
	};
	optionEditor = Editor.extend(optionEditor.prototype);
	this.optionEditor = optionEditor;
	
});
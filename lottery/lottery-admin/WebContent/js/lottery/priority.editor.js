Jet().$package(function(J){
	var $ = jQuery, $E = Jooe, Editor = xhcms.cms.Editor, $base = base_path;
	var mask = null;
	var priorityEditor = function(){};
	priorityEditor.prototype = {
		init: function(option){
			mask = new xhcms.ui.MaskLayer({
				zIndex: 10000
			});
			option = option || {};
			option.title = option.title || '编辑权重';
			option.data = option.data || {id: '', lotteryPlatformAliasName: '', lotteryId: '', priority: -1,priorityOfBigTicket:-1};
			
			this._super.init(option);
			this.setXY(this.getX(),this.getY()+20);
			
			this.observer = {
				save: function(e, data){
					$.get($base + '/ticket/aj_edit_priority.do', data, function(resp){
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
				+ '<table class="tbel" cellspacing="0" cellpadding="0">'
				+ '<tr><td class="tdl tar" width="100"> id：</td><td class="tdr tal">{0}</td></tr>'
				+ '<tr><td class="tdl tar"> 实体店名称：</td><td class="tdr tal">{1}</td></tr>'
				+ '<tr><td class="tdl tar"> 彩种：</td><td class="tdr tal">{2}</td></tr>'
				+ '<tr><td class="tdl tar"> 小额票权重：</td><td class="tdr tal"><input type="text" name="priority" size="2" maxlength="2" value="{3}"/> </td></td></tr>'
				+ '<tr class="last"><td class="tdl tar">大额票权重：</td><td class="tdr tal"><input type="text" name="priorityOfBigTicket" size="2" maxlength="2" value="{4}"/> </td></tr>'
				+ '<input type="hidden" name="id" value="{5}"/>'
				+ '</table></div>';
			this.setHtml($.format(html, o.id, o.lotteryPlatformAliasName, o.lotteryId,o.priority,o.priorityOfBigTicket,o.id));
//			if(o.id != ''){
//				$('input[name="play.id"]', this.body).attr('readonly', true);
//			}	
//			setTimeout(function(){
//				$('select[name="play.lotteryId"]', this.body).val(o.lotteryId);
//			}, 1);
			mask.show();
			
			var $priority = $('input[name="priority"]', this.body);
			$priority.bind('keyup', function(){
				var val = $priority.val();
				if(!/^\d+$/.test(val)){
					$priority.val(/\d+/.exec(val)[0]);
				}
			});
			
			var $priorityOfBigTicket = $('input[name="priorityOfBigTicket"]', this.body);
			$priorityOfBigTicket.bind('keyup', function(){
				var val = $priorityOfBigTicket.val();
				if(!/^\d+$/.test(val)){
					$priorityOfBigTicket.val(/\d+/.exec(val)[0]);
				}
			});
			
		},
		clickOkButton: function(){
			var result = window.confirm("你确定要修改分票吗？修改之前请务必确认！！！")
			if(true != result) {
				return;
			}
			var data = {};
			$(':input', this.body).each(function(e){
				data[this.name] = $.trim(this.value);
			});
			var priority=data['priority'];
			var priorityOfBigTicket=data['priorityOfBigTicket'];
			if(priority == ''){
				alert('请输入小额权重');
				return false;
			}
			if(!/^\d+$/.test(priority)){
				alert('小额权重只能输入非负整数');
				return false;
			}
			if(priorityOfBigTicket == ''){
				alert('请输入大额权重');
				return false;
			}
			if(!/^\d+$/.test(priorityOfBigTicket)){
				alert('大额权重只能输入非负整数');
				return false;
			}
			return this.trigger(Editor.EVENT.ClickOkButton, data);
		},
		Close: function(){
			mask.hide();
		}
	};
	priorityEditor = Editor.extend(priorityEditor.prototype);
	this.priorityEditor = priorityEditor;
	
});
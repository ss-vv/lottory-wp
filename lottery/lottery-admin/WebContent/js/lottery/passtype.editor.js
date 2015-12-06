Jet().$package(function(J){
	var $ = jQuery, $E = Jooe, Editor = xhcms.cms.Editor, $base = base_path;
	var mask = null;
	var passtypeEditor = function(){};
	passtypeEditor.prototype = {
		init: function(option){
			mask = new xhcms.ui.MaskLayer({
				zIndex: 10000
			});
			option = option || {};
			option.title = option.title || '过关方式维护';
			option.playId = option.playId || 0;
			
			this._super.init(option);
			this.setXY(this.getX(),this.getY()+20);
			
			this.observer = {
				save: function(e, data){
					$.post($base + '/passtype/aj_save.do', data, function(resp){
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
			var target = this;
			var html = '<div class="tbebox tbebox_B">'
				+ '<table class="tbel" cellspacing="0" cellpadding="0">'
				+ '<tr><td class="tdl tar" width="100"><span class="fc_red">*</span> 玩法名称：</td><td class="tdr tal">{0}</td></tr>'
				+ '<input type="hidden" name="playId" value="{1}"/>'
				+ '<tr><td class="tdl tar">过关方式：</td><td class="tdr tal">{2}</td></tr>'
				+ '</table></div>';
			$.getJSON($base + '/play/aj_get.do', {playId : this.option.playId}, function(json){
				$.parseJSONData(json, function(play){
					$.getJSON($base + '/passtype/aj_ls.do', {}, function(res){
						var allpass = [];
						$.parseJSONData(res, function(pass){
							//加载全部过关方式
							for(var i = 0, j = pass.length; i < j; i++){
								allpass.push('<label><input type="checkbox" name="passtype" value="' + pass[i].id + '"/><span>' + pass[i].name + '</span></label> ');
							}
							target.setHtml($.format(html, play.name, play.id, allpass.join('')));
							var passes = play.passTypes;
							//设置默认值
							for(var i = 0, j = passes.length; i < j; i++){
								$('input[value="' + passes[i].id + '"]', target.body).attr('checked', true);
							}
							//按场次换行
							for(var i = 1; i <= 8; i++){
								$('input[value^="' + i + '@"]', target.body).last().parent('label').after('<br/>');
							}
						});
					});
					
				});
			});
			mask.show();
		},
		clickOkButton: function(){
			var data = {};
			data['playId'] = $('input[name=playId]').val();
			var p = [];
			$('input[name=passtype]').filter(':checked').each(function(){
				p.push(this.value);
			});
			data['passtype'] = p.join(','); 
			
			return this.trigger(Editor.EVENT.ClickOkButton, data);
		},
		Close: function(){
			mask.hide();
		}
	};
	passtypeEditor = Editor.extend(passtypeEditor.prototype);
	this.passtypeEditor = passtypeEditor;
	
});
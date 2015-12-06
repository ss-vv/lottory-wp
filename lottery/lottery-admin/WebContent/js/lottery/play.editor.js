Jet().$package(function(J){
	var $ = jQuery, $E = Jooe, Editor = xhcms.cms.Editor, $base = base_path;
	var mask = null;
	var playEditor = function(){};
	playEditor.prototype = {
		init: function(option){
			mask = new xhcms.ui.MaskLayer({
				zIndex: 10000
			});
			option = option || {};
			option.title = option.title || '编辑玩法';
			option.data = option.data || {id: '', lotteryId: 'JCZQ', name: '', floorRatio: 0};
			
			this._super.init(option);
			this.setXY(this.getX(),this.getY()+20);
			
			this.observer = {
				save: function(e, data){
					$.post($base + '/play/aj_save.do', data, function(resp){
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
				+ '<tr><td class="tdl tar" width="100"><span class="fc_red">*</span> 玩法编号：</td><td class="tdr tal"><input type="text" name="play.id" value="{0}" maxLength="10" size="10"/></td></tr>'
				+ '<tr><td class="tdl tar"><span class="fc_red">*</span> 彩种：</td><td class="tdr tal"><select name="play.lotteryId"><option value="JCZQ">竞彩足球</option><option value="JCLQ">竞彩篮球</option><option value="JX11">江西十一选五</option><option value="CTZC">传统足彩</option></select></td></tr>'
				+ '<tr><td class="tdl tar"><span class="fc_red">*</span> 玩法名称：</td><td class="tdr tal"><input type="text" name="play.name" size="20" maxlength="16" value="{1}"/></td></tr>'
				+ '<tr class="last"><td class="tdl tar">合买保底比例：</td><td class="tdr tal"><input type="text" name="play.floorRatio" size="2" maxlength="2" value="{2}"/> %</td></tr>'
				+ '</table></div>';
			this.setHtml($.format(html, o.id, o.name, o.floorRatio));
			if(o.id != ''){
				$('input[name="play.id"]', this.body).attr('readonly', true);
			}	
			setTimeout(function(){
				$('select[name="play.lotteryId"]', this.body).val(o.lotteryId);
			}, 1);
			mask.show();
			
			var $floor = $('input[name="play.floorRatio"]', this.body);
			$floor.bind('keyup', function(){
				var val = $floor.val();
				if(!/^\d+$/.test(val)){
					$floor.val(/\d+/.exec(val)[0]);
				}
			});
			
		},
		clickOkButton: function(){
			var data = {};
			$(':input', this.body).each(function(e){
				data[this.name] = $.trim(this.value);
			});
			
			if(data['play.id'] == ''){
				alert('请输入玩法id');
				return false;
			}
			if(data['play.name'] == ''){
				alert('请输入玩法名称');
				return false;
			}
			return this.trigger(Editor.EVENT.ClickOkButton, data);
		},
		Close: function(){
			mask.hide();
		}
	};
	playEditor = Editor.extend(playEditor.prototype);
	this.playEditor = playEditor;
	
});
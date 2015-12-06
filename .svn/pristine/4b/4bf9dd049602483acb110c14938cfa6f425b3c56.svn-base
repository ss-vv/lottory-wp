Jet().$package(function(J){
	var $ = jQuery, $E = Jooe, Editor = xhcms.cms.Editor, $base=base_path;
	var mask = null;
	var matchEditor = function(){};
	matchEditor.prototype = {
		init: function(option){
			mask = new xhcms.ui.MaskLayer({
				zIndex: 10000
			});
			option = option || {};
			option.title = option.title || '编辑赛事';
			//0: 足球    1:篮球
			option.data = option.data || {mtype: 0, id: 0, cnCode: '', leagueName: '', vs: '主队 VS 客队', name: '', offtime: '', playingTime: '', s: 0};
			
			this._super.init(option);
			this.setXY(this.getX(),this.getY()+20);
			
			this.observer = {
				save: function(e, data){
					var url = $base + '/preset/aj_editfb.do';
					if(option.data.mtype == 1){
						url = $base + '/preset/aj_editbb.do';
					}
					$.get(url, data, function(resp){
						$.parseJSONData($.parseJSON(resp), function(data){
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
				+ '<tr><td class="tdl tar" style="width:100px;">赛事ID：</td><td class="tdr tal">{0}</td></tr>'
				+ '<tr><td class="tdl tar">赛事编号：</td><td class="tdr tal">{1}</td></tr>'
				+ '<tr><td class="tdl tar">联赛名称：</td><td class="tdr tal">{2}</td></tr>'
				+ '<tr><td class="tdl tar">{3}：</td><td class="tdr tal">{4}</td></tr>'
				+ '<tr><td class="tdl tar">预计停售时间：</td><td class="tdr tal">{5}</td></tr>'
				+ '<tr><td class="tdl tar">开赛时间：</td><td class="tdr tal">{6}</td></tr>'
				+ '{8}'
				+ '<input type="hidden" name="id" value="{7}"/>'
				+ '</table></div>';
			var score = '';
			var vs = '主队 VS 客队';
			if(o.mtype == 1){
				vs = '客队 VS 主队';
				score = '<tr><td class="tdl tar">全场比分：</td><td class="tdr tal"><input name="finalScorePreset" ' + (o.checkStatus == 1 ? 'readonly="readonly"' : '') + '  value="'+ o.finalScorePreset +'"> 例 100:92</td></tr>';
			}else{
				score = '<tr><td class="tdl tar">半场比分：</td><td class="tdr tal"><input name="halfScorePreset" ' + (o.checkStatus == 1 ? 'readonly="readonly"' : '') + '  value="'+ o.halfScorePreset +'"> 例 1:2</td></tr>'
					+ '<tr><td class="tdl tar">全场比分：</td><td class="tdr tal"><input name="scorePreset" ' + (o.checkStatus == 1 ? "readonly=readonly" : '') + '  value="'+ o.scorePreset +'"> 例 1:3</td></tr>';
			}
			this.setHtml($.format(html, o.id, o.cnCode, o.leagueName, vs, o.name, o.offtime, o.playingTime, o.id, score));
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
			
			return this.trigger(Editor.EVENT.ClickOkButton, data);
		},
		Close: function(){
			mask.hide();
		}
	};
	matchEditor = Editor.extend(matchEditor.prototype);
	this.matchEditor = matchEditor;
	
});
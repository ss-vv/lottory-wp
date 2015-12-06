Jet().$package(function(J){
	var $ = jQuery, $E = Jooe, Editor = xhcms.cms.Editor;
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
					var url = '/match/aj_editfb.do';
					if(option.data.mtype == 1){
						url = '/match/aj_editbb.do';
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
				+ '<tr class="last"><td class="tdl tar">状态：</td><td class="tdr tal"><select name="status">' + 
				+ '<option value="0">停售</option>'
				+ '<option value="1">在售</option>'
				+ '<option value="2">待售</option>'
				+ '<option value="3">进行中</option>'
				+ '<option value="4">已取消</option>'
				+ '<option value="5">已完成</option>'
				+ '</select></td></tr>'
				+ '<input type="hidden" name="id" value="{7}"/>'
				+ '</table></div>';
			var vs = '主队 VS 客队';
			if(o.mtype == 1){
				vs = '客队 VS 主队';
			}
			this.setHtml($.format(html, o.id, o.cnCode, o.leagueName, vs, o.name, o.offtime, o.playingTime, o.id));
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
Jet().$package(function(J){
	var $ = jQuery, $E = Jooe, Editor = xhcms.cms.Editor, $base=base_path;
	var mask = null;
	var ctzcMatchEditor = function(){};
	ctzcMatchEditor.prototype = {
		init: function(option){
			mask = new xhcms.ui.MaskLayer({
				zIndex: 10000
			});
			option = option || {};
			option.title = option.title || '编辑赛事';
			//0: 足球    1:篮球
			option.data = option.data || {mtype: 0, id: 0, leagueName: '', vs: '主队 VS 客队', offtime: '', playingTime: '', s: 0};
			
			this._super.init(option);
			this.setXY(this.getX(),this.getY()+20);
			
			this.observer = {
				save: function(e, data){
					var halfScore = data.halfScore;
					var score = data.score;
					var reg = /^(0|[1-9]{1}\d{0,3})\:(0|[1-9]{1}\d{0,3})$/;
					if(!reg.test(halfScore) || !reg.test(score)){
						alert("请检查比分值格式是否正确！");
						return false;
					}
					var url = $base + '/preset/aj_presetctzc.do';
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
				+ '<tr><td class="tdl tar">联赛名称：</td><td class="tdr tal">{1}</td></tr>'
				+ '<tr><td class="tdl tar">{2}：</td><td class="tdr tal">{3}</td></tr>'
				+ '<tr><td class="tdl tar">预计停售时间：</td><td class="tdr tal">{4}</td></tr>'
				+ '<tr><td class="tdl tar">开赛时间：</td><td class="tdr tal">{5}</td></tr>'
				+ '{7}'
				+ '{6}'
				+ '<input type="hidden" name="id" value="{0}"/>'
				+ '<input type="hidden" name="status" id="status"/>'
				+ '</table></div>';
			var score = '';
			var vs = '主队 VS 客队';
			if(o.mtype == 1){
				vs = '客队 VS 主队';
				score = '<tr><td class="tdl tar">全场比分：</td><td class="tdr tal"><input name="finalScorePreset" ' + (o.checkStatus == 1 ? 'readonly="readonly"' : '') + '  value="'+ o.finalScorePreset +'"> 例 100:92</td></tr>';
			}else{
				score = '<tr><td class="tdl tar">半场比分：</td><td class="tdr tal"><input name="halfScore" ' + (o.checkStatus == 1 ? 'readonly="readonly"' : '') + '  value="'+ o.halfScore +'"> 例 1:2</td></tr>'
					+ '<tr><td class="tdl tar">全场比分：</td><td class="tdr tal"><input name="score" ' + (o.checkStatus == 1 ? "readonly=readonly" : '') + '  value="'+ o.score +'"> 例 1:3</td></tr>';
			}
			/*var status=o.status;
			if($.trim(o.status) == "在售"){
				status=0;
			}else if($.trim(o.status) == "停售"){
				status=-1;
			}else if($.trim(o.status) == "取消"){
				status=4;
			}*/
			//value="+(o.status=='在售'?0:o.status=='取消'?4:-1)+"
			var statusStr="<tr><td class='tdl tar'>状态：</td><td class='tdr tal'>"
				          +"<select name='status'>"
					      + "<option value='0'>在售</option>" 
					      + "<option value='-1'>停售</option>" 
					      + "<option value='4'>取消</option>" 
					      + "</select>"
					      +"</td></tr>";
			this.setHtml($.format(html, o.id, o.leagueName, vs, o.homeAndGuestTeam, o.offtime, o.playingTime, score,statusStr));
			setTimeout(function(){
				$('select[name="status"]', this.body).val(o.status);
				$("#status",this.body).val(o.status);
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
	ctzcMatchEditor = Editor.extend(ctzcMatchEditor.prototype);
	this.ctzcMatchEditor = ctzcMatchEditor;
	
	$("select[name='status']").live('click',function(){
		var v=$(this).val();
		$("#status").val(v);
		/*if(v!=-1){
			$("#status").val(v);
		}else{
			$("#status").val("");
		}*/
	});
});

$(document).ready(function (){
	var issues={};
	$('td[issueNumber]').each(function (){
		if(!issues[$(this).attr("issuenumber")]){
			$("#issue_number_selector").append("<option value='"+$(this).attr("issuenumber")+"'>"+$(this).attr("issuenumber")+"</option>");
			issues[$(this).attr("issuenumber")]=$(this).attr("issuenumber");
		}
	});
	$('#prizes').bind('click', function(){
		var issueNumber=$("#issue_number_selector").val();
        if(confirm('确定开始传统足彩'+issueNumber+'期预兑奖？')){
            jQuery.getJSON('http://admin.davcai.com/lottery/preset/aj_preset_prize_ctzc.do', {
            	issueNumber:issueNumber,
            },function(json){
            	if(json.success && json.data){
            		var list = json.data.presetResultPerPlayIdList;
                	var s = "";
                	var p= {'24_ZC_14':'14场胜负',"25_ZC_R9":"任九","26_ZC_BQ":"6场半全","27_ZC_JQ":"4场进球"};
            		for (var i = 0; i < list.length; i++) {
						var l = list[i];
						s += (p[l.playId] + ":\n" +
								"　　方案总数："+l.totalSchemes+"\n"+
								"　　中奖方案："+l.canAwardSchemes+"\n"+
								"　　未中奖方案："+l.notAwardSchemes+"	\n" +
								"　　跳过方案："+l.skipSchemes+"\t\n"+
								"　　消息："+l.message+
						"\n\n"); 
					}
            		alert(s);
                }else{
                	alert('错误！' + json.data);
                }
            });
        }
    });
});
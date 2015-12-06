Jet().$package(function(ctzc){
	var fontsize="style='font-size:13.5px;'";
    var tmpl = {
        week: '<tr class="trcs"><td colspan="8"><div class="open_close"><b>{0} 星期{1}</b><a class="c-c" href="javascript:void(0)" _w="{2}" _v="0">点击隐藏</a><b _n="cls" class="icns icns16 icns16_arrt"></b></div></td></tr>',
        opt: '<a class="spf" href="javascript:void(0);" _o="{0}" title="删除该项">{1}</a>',
        match: '<tr _m="{0}">'
            + '<td class="tdl"><div class="intd">{1}</div></td>'
            + '<td><div class="intd">{2} VS {3}</div></td>'
            + '<td><div class="intd"><div class="tagbox tagbox-d"><div class="row">{4}</div><span class="clear"/></div></div></td>'
            + '<td style="text-align:center;"><div class="intd"><a href="javascript:void(0);" _m="{0}" title="删除该场比赛">删</a></div></td>'
            + '</tr>',
        matchseed: '<tr _m="{0}">'
            + '<td class="tdl"><div class="intd">{1}</div></td>'
            + '<td><div class="intd">{2} VS {3}</div></td>'
            + '<td><div class="intd"><div class="tagbox tagbox-d"><div class="row">{4}</div><span class="clear"/></div></div></td>'
            + '<td><input type="checkbox" name="dan" _dan="{0}"></td>'
            + '<td style="text-align:center;"><div class="intd"><a href="javascript:void(0);" _m="{0}" title="删除该场比赛">删</a></div></td>'
            + '</tr>',
        pass: '<label class="lbl" _m="{0}" style="display:none;"><input type="checkbox" name="pt" value="{2}"/><span>{0}串{1}</span></label>'
    };    
    var weekCN = ['日','一','二','三','四','五','六'];
    
    var Renderer = function(){};
    Renderer.prototype = {
        render: function(ms){
            var h = [], m = ms[0], date = $.parseDate(m.pTime);
            	//减去11：30
                date.setTime(date.getTime() - 41400000);
                for(var i = 0, len = ms.length; i < len; i++){
                    m = ms[i];
                    var cw = m.code.charAt(0) % 7;
                    if(!(i%2) && (ms.length == 12 || ms.length == 8)){
                    	h.push(this.renderMatch(cw, m, i));
                    }else if(ms.length == 14 || ms.length == 9){
                    	h.push(this.renderMatch(cw, m, i));
                    }
                }
            return h.join('');
        },        
        renderWeek: function(week, date){
            return $.format(tmpl.week, this.formatDate(date), weekCN[week], week);
        },
        renderMatch: function(week, match, i){
            return '';
        },
        optionName: function(o){
            return this.names[o];
        },
        formatDate: function(date){
            return date.getFullYear() + '-' + Renderer.fill(date.getMonth() + 1) + '-' + Renderer.fill(date.getDate());
        }
    };
    Renderer = Jooe.extend(Renderer.prototype);
    Renderer.fill = function(v){
        return ((v < 10) ? '0': '') + v;
    };
    
    var ZC1Renderer = function(){};
    ZC1Renderer.prototype = {
        init: function(option){
            this.matchTpl = '<tr _m="{0}" _w="{6}"><td class="tdl"><div class="intd">{1}</div></td>' +
                    '<td><div class="intd"><span class="sort" style="background-color:{7};color:#ffffff;" _color>{2}</span></div></td>' +
                    '<td><div class="intd"><span _i="0">{3}</span><span _i="1" style="display:none;">{4}</span></div></td>' +
                    '<td><div class="intd" '+fontsize+'>{5}</div></td>' +
                    '<td class="tdr"><div class="intd"><a href="javascript:void(0);" _m="{0}">全</a></div></td>' +
                    '<td>'+
		            	'<div>' + 
		                	'<a href="{8}" target="_blank">亚</a>/' + 
		                	'<a href="{9}" target="_blank">欧</a>/' + 
		                	'<a href="{10}" target="_blank">析</a>'+
		            	'</div>' + 
	            	'</td>' +
	            	'<td>'+
		            	'<div>' + 
		            		'<a class="matchComment" href="javascript:void(0)">写</a>'+
		            	'</div>' + 
	            	'</td>' +
                    '</tr>';
            this.optTpl = '<a _o="{0}" _s="0" class="btnx3 btnx3-a fl" href="javascript:void(0);" style="width:160px;"><span class="btnx3_l"><span class="btnx3_r"><span class="bif"'+fontsize+'>{1} <span class="c-i">{2}</span></span><span class="peil c-l">{3}</span></span></span></a>';
            this.optTpl2 = '<a _o="{0}" _s="0" class="btnx3 btnx3-a c-l fl" href="javascript:void(0);" style="width:100px;"><span class="btnx3_l"><span class="btnx3_r">{1}</span></span></a>';
            this.optionCount = 3;
            this.names = {
                '3': '胜',
                '1': '平',
                '0': '负'
            };
        },
      
        renderMatch: function(w, m, i){
            var odds = m.odds, h = [];    
            var concede = m.score;
            if(concede != 0){
                concede = (concede > 0) ? '(+' + concede + ')' : '(' + concede + ')';
            } else {
                concede = '';
            }
            var analysis = matchForwardUrl(m.id, lottery);
            h.push($.format(this.optTpl, 0, m.hName, concede, odds[0]));
            h.push($.format(this.optTpl2, 1, odds[1]));
            h.push($.format(this.optTpl, 2, m.gName, '', odds[2]));
            return $.format(this.matchTpl, i, m.cnCode, 
        		m.lName, m.deadline.substr(5, 11).replace('T', ' '), 
        		m.pTime.substr(5, 11).replace('T', ' '), 
        		h.join(''), w,m.color, 
            	analysis.aisanUrl, analysis.europeUrl, analysis.overview);
        }
    };
    ZC1Renderer = Renderer.extend(ZC1Renderer.prototype);

    var ZC3Renderer = function(){};
    ZC3Renderer.prototype = {
        init: function(option){
            this.matchTpl = '<tr _m="{0}" _w="{8}"><td rowspan="2" class="tdl"><div class="intd">{1}</div></td>' +
                    '<td rowspan="2"><div class="intd" style="height:62px;"><span class="sort" style="height:62px;line-height:62px;background-color:{9};color:#ffffff;" _color>{2}</span></div></td>' +
                    '<td rowspan="2"><div class="intd"><span _i="0">{3}</span><span _i="1" style="display:none;">{4}</span></div></td>' +
                    '<td rowspan="2"><div class="intd"><div class="hide" '+fontsize+'>{5}<br/>{6}</div></div></td>' +
                    '<td><div class="intd" '+fontsize+'>{7}</div></td>' +
                    '<td class="tdr"><div class="intd"><a href="javascript:void(0);" _m="{0}">全</a></div></td>' +
                    '<td>'+
	                	'<div>' + 
	                		'<a href="{11}" target="_blank">亚</a>/' + 
	                		'<a href="{12}" target="_blank">欧</a>/' + 
	                		'<a href="{13}" target="_blank">析</a>'+
	                	'</div>' + 
                	'</td>' +
	            	'<td>'+
		            	'<div>' + 
		            		'<a class="matchComment" href="javascript:void(0)">写</a>'+
		            	'</div>' + 
	            	'</td>' +
                    '</tr>' +
                    '<tr _m="{10}" _w="{8}">' +
                    '<td><div class="intd" '+fontsize+'>{7}</div></td>' +
                    '<td class="tdr"><div class="intd"><a href="javascript:void(0);" _m="{10}">全</a></div></td>' +
                    '<td>'+
	                	'<div>' + 
	                		'<a href="{11}" target="_blank">亚</a>/' + 
	                		'<a href="{12}" target="_blank">欧</a>/' + 
	                		'<a href="{13}" target="_blank">析</a>'+
	                	'</div>' + 
                	'</td>' +
	            	'<td>'+
		            	'<div>' + 
		            		'<a class="matchComment" href="javascript:void(0)">写</a>'+
		            	'</div>' + 
	            	'</td>' +
                    '</tr>'                    ;
            this.optTpl = '<a _o="{0}" _s="0" class="btnx3 btnx3-a fl" href="javascript:void(0);" style="width:100px;"><span class="btnx3_l"><span class="btnx3_r">{1}</span></span></a>';
            this.optionCount = 8;
            this.names = {
                '0': '0球',
                '1': '1球',
                '2': '2球',
                '3': '3+球'
            };
        },
      
        renderMatch: function(w, m, i){
            var odds = m.odds, h = [];    
            for(var j = 0, k = m.opts.length; j < k; j++){
                 h.push($.format(this.optTpl, j, odds[j] == 3? '3+':odds[j]));
            }
            var analysis = matchForwardUrl(m.id, lottery);
            return $.format(this.matchTpl, 
            		i, 
            		m.cnCode, 
            		m.lName, 
            		m.deadline.substr(5, 11).replace('T', ' '), 
            		m.pTime.substr(5, 11).replace('T', ' '), 
            		m.hName, 
            		m.gName, h.join(''), 
            		w,
            		m.color,
            		i+1,
            		analysis.aisanUrl, 
            		analysis.europeUrl, 
            		analysis.overview);
        }
    };
    ZC3Renderer = Renderer.extend(ZC3Renderer.prototype);
    
    var ZC4Renderer = function(){};
    ZC4Renderer.prototype = {
        init: function(option){
            this.matchTpl = '<tr _m="{0}" _w="{8}"><td rowspan="2" class="tdl"><div class="intd">{1}</div></td>' +
                    '<td rowspan="2"><div class="intd" style="height:62px;"><span class="sort" style="height:62px;line-height:62px;background-color:{9};color:#ffffff;;" _color>{2}</span></div></td>' +
                    '<td rowspan="2"><div class="intd"><span _i="0">{3}</span><span _i="1" style="display:none;">{4}</span></div></td>' +
                    '<td rowspan="2"><div class="intd"><div class="hide" '+fontsize+'>{5} VS {6}</div></td>' +
                    '<td><div class="intd">半场</div></td>' +
                    '<td><div class="intd" '+fontsize+'>{7}</div></td>' +
                    '<td class="tdr"><div class="intd"><a href="javascript:void(0);" _m="{0}">全</a></div></td>' +
                    '<td>'+
	                	'<div>' + 
	                		'<a href="{11}" target="_blank">亚</a>/' + 
	                		'<a href="{12}" target="_blank">欧</a>/' + 
	                		'<a href="{13}" target="_blank">析</a>'+
	                	'</div>' + 
                	'</td>' +
                	'<td>'+
		            	'<div>' + 
		            		'<a class="matchComment" href="javascript:void(0)">写</a>'+
		            	'</div>' + 
	            	'</td>' +
                    '</tr>' +
                    '<tr _m="{10}" _w="{8}">' +
                    '<td><div class="intd">全场</div></td>' +
                    '<td><div class="intd" '+fontsize+'>{7}</div></td>' +
                    '<td class="tdr"><div class="intd"><a href="javascript:void(0);" _m="{10}">全</a></div></td>' +
                    '<td>'+
	                	'<div>' + 
	                		'<a href="{11}" target="_blank">亚</a>/' + 
	                		'<a href="{12}" target="_blank">欧</a>/' + 
	                		'<a href="{13}" target="_blank">析</a>'+
	                	'</div>' + 
                	'</td>' +
	            	'<td>'+
		            	'<div>' + 
		            		'<a class="matchComment" href="javascript:void(0)">写</a>'+
		            	'</div>' + 
	            	'</td>' +
                    '</tr>';
            this.optTpl = '<a _o="{0}" _s="0" class="btnx3 btnx3-a fl" href="javascript:void(0);" style="width:100px;"><span class="btnx3_l"><span class="btnx3_r">{1}</span></span></a>';
            this.optionCount = 9;
            this.names = {
                '3': '胜',
                '1': '平',
                '0': '负'
            };
        },
      
        renderMatch: function(w, m, i){
            var odds = m.odds, h = [];    
            for(var j = 0, k = m.opts.length; j < k; j++){
                h.push($.format(this.optTpl, j, odds[j]));
            }
            var analysis = matchForwardUrl(m.id, lottery);
            return $.format(this.matchTpl, 
            		i, 
            		m.cnCode, 
            		m.lName, 
            		m.deadline.substr(5, 11).replace('T', ' '), 
            		m.pTime.substr(5, 11).replace('T', ' '), 
            		m.hName, 
            		m.gName, 
            		h.join(''), 
            		w,
            		m.color,
            		i+1,
            		analysis.aisanUrl, 
            		analysis.europeUrl, 
            		analysis.overview);
        }
    };
    ZC4Renderer = Renderer.extend(ZC4Renderer.prototype);
    
    var UploadRenderer = function(){};
    UploadRenderer .prototype={
    		init: function(option){
    			this.demo = "<strong>上传内容</strong>";
    			this.ZC_14 = "<strong>上传文本示例</strong><div>13031003310130</div><div>33331003310131</div>11131003310133<br/>13031003300000";
    			this.ZC_R9 = "<strong>上传文本示例</strong><br/>130*1*0*31*1*0<br/>13*310*33*01**<br/>13*310*31*01**<br/>**03*0*331*130";
    			this.ZC_BQ = "<strong>上传文本示例</strong><br/>310310310310<br/>003103103101<br/>333310310310<br/>110310310311";
    			this.ZC_JQ = "<strong>上传文本示例</strong><br/>32123212<br/>01230123<br/>32103210<br/>32223212"; 
    			this.msg = "<br/>最大倍数99倍<br/>最大金额20万<br/>自动滤重数据<br/>自动滤错数据";
            	this.upLoadDomUI = '<table  style="width:998px;height:279px;" border="1" cellpadding="0" cellspacing="0">'
    		        +'<tr>'
    		        +'<td style="width:200px;background-color: #F0F8FD;line-height:2em;"  align="center" ><span>{0}</span></td>'
    		        +'<td style="width:508px;"><label for="asas"></label>'
    		        +'<textarea style="width:500px;height:300px;" name="randomContent"  readonly="readonly"  id="randomContent"></textarea></td>'
    		        +'<td style="width:238px;">'
    		        +'<div style="position:absolute;margin-top:30px;margin-left:-290px;z-index:10;">'
    		        +'<img id="loading" src="http://trade.davcai.com/df/images/loading.gif" style="display:none;">'
    		        +'</div>'
    		        +'<div style="position:relative;">'
    		        +'<input type="text" id="textpath" style="width:80px; vertical-align:middle;"><a class="btnx2 btnx2-b" href="javascript:void(0);" style=" vertical-align:middle;"><span class="inbtn" style="width:65px;">浏览</span></a><input id="fileToUpload" type="file" style="position:absolute; top:0; right:40px; height:24px; filter:alpha(opacity:0);opacity: 0;width:160px " name="upload" onchange="document.getElementById(\'textpath\').value=this.value"/>'
    		        +'</div>'
    		        +'<div  style="padding-top:22px;">'
    		        +'<a class="btnx2 btnx2-b" href="javascript:void(0);"  id="uploadTxt"><span class="inbtn" style="width:150px;">上传文件</span></a>'
    		        +'</div>'
    		        +'<div style="padding-top:22px;">'
    		        +'<a class="btnx2 btnx2-b" _n="clear" href="javascript:void(0);"><span class="inbtn" style="width:150px;">清空列表</span></a>'
    		        +'</div>'
    		        +'</td>'
    		        +'</tr>'
    		        +'</table>';
            },
          
            renderMatch: function(playId){
            	switch (playId) {
				case "24_ZC_14":
					this.demo = this.ZC_14 + this.msg;
					break;
				case "25_ZC_R9":
					this.demo = this.ZC_R9 + this.msg;
					break;
				case "26_ZC_BQ":
					this.demo = this.ZC_BQ + this.msg;
					break;
				case "27_ZC_JQ":
					this.demo = this.ZC_JQ + this.msg;
					break;
				default:
					break;
				}
                return $.format(this.upLoadDomUI,this.demo);
            }
    };
    UploadRenderer =  Renderer.extend(UploadRenderer.prototype);
    
    var SFRandomRenderer = function(){};
    SFRandomRenderer.prototype = {
        init: function(option){
        	this.ranDomUI = '<table  style="width:998px;height:279px;" border="1" cellpadding="0" cellspacing="0">'
		        +'<tr>'
		        +'<td style="width:200px;background-color: #F0F8FD;"  align="center" ><strong>投注提示</strong><br/>最大倍数99倍<br/>最大金额20万</span></td>'
		        +'<td style="width:508px;"><label for="asas"></label>'
		        +'<textarea style="width:500px;height:300px;" name="randomContent"  readonly="readonly"  id="randomContent"></textarea></td>'
		        +'<td style="width:238px;">'
		        +'<div style="position:absolute;margin-top:70px;margin-left:-290px;z-index:10;">'
		        +'<img id="loading" src="http://trade.davcai.com/df/images/loading.gif" style="display:none;">'
		        +'</div>'
		        +'<div>'
		        +'<a class="btnx2 btnx2-b" href="javascript:void(0);"  _n="random1"><span class="inbtn" style="width:150px;">机选一注</span></a>'
		        +'</div>'
		        +'<div style="padding-top:22px;">'
		        +'<a class="btnx2 btnx2-b" href="javascript:void(0);"  _n="random10"><span class="inbtn" style="width:150px;">机选十注</span></a>'
		        +'</div>'
		        +'<div style="padding-top:22px;">'
		        +'<a class="btnx2 btnx2-b" href="javascript:void(0);"  _n="random100"><span class="inbtn" style="width:150px;">机选百注</span></a>'
		        +'</div>'
		        +'<div style="padding-top:22px;">'
		        +'<a class="btnx2 btnx2-b" href="javascript:void(0);"  _n="random1000"><span class="inbtn" style="width:150px;">机选千注</span></a>'
		        +'</div>'
		        +'<div style="padding-top:22px;">'
		        +'<a class="btnx2 btnx2-b" _n="clear" href="javascript:void(0);"><span class="inbtn" style="width:150px;">清空列表</span></a>'
		        +'</div>'
		        +'</td>'
		        +'</tr>'
		        +'</table>';
        },
      
        renderMatch: function(){
            return this.ranDomUI;
        }
    };
    SFRandomRenderer = Renderer.extend(SFRandomRenderer.prototype);
    
    var RandomUI = function(){};
    RandomUI.prototype ={
    		init:function(option){
    			this.playId = option.playId;
                this.renderer = option.renderer;
                this.bet = new Bet(option);
                this.betRandom = option.betRandom || new SF14Random();
                this.$time = option.time.find('[_n]');
                this.$panel = option.panel;
                this.$grid = option.grid;
                this.$row = [];             
                var ctx = this, $dom = $('[_n]', this.$panel);
                this.$form = $dom.filter('[_n="form"]');    // 投注表单            
                this.$mKnob = $dom.filter('[_n="mKnob"]');  // 切换“已选赛事”按钮
                this.$mGrid = $dom.filter('[_n="mGrid"]');  // 已选赛事列表
                this.$bet = $dom.filter('[_n="bet"]');      // 投注按钮      
                this.$mc = $dom.filter('[_n="mc"]');        // 已选赛事数量
                this.$money = $dom.filter('[_n="money"]');  // 投注金额
                this.$bonus = $dom.filter('[_n="bonus"]');  // 最高奖金
                this.$notes = $dom.filter('[_n="notes"]');  // 注数
                this.$seed = $dom.filter('[_n="seeds"]');  // 胆码数
                this.$randomContent = $(); // 随机投注文本域
                this.cache = []; // sel的前一个值
                this.notes = 0; // 随机数注数
                this.offtime = ""; // 期截止时间
                this.$issueNumbersDiv = $("#issueNumbersDiv");  // 期号列表div
                
                var urlSearch=window.location.search.substr(1);
                var issueNo=(urlSearch)?urlSearch.split("=")[1]:"";
                // 加载赛事数据
                $.getJSON(option.url, {playId: this.playId, _: new Date().getTime(),issueNumber:issueNo}, function(json){
                    if(json.success && json.data.ctFBMatchs != null && json.data.ctFBMatchs.length>0){
                    	ctx.renderMatch(ctx.bet.parseMatchs(json.data.ctFBMatchs));
                    }else{
                    	ctx.$bet.css("background","url(/df/images/bet/btn-c.png)");
                    }
                    new IssueInfoUI().makeUI(json.data, ctx);
                	ctx._bind(); // 绑定事件
                });
    		},
    		
            renderMatch: function(matchs){
                for(var i = 0, len = matchs.length; i < len; i++){
                    this.cache[i] = 0;
                }
                this.$grid.html("");
                this.$row = $(this.renderer.renderMatch(this.playId)).appendTo(this.$grid); // 生成界面
                
            },
            
            createRandom:function(num){
            	this.random(num);
            	this._reCompute();
                this.repaint();
            },
            //重绘制文本框
            repaint:function(){
            	this.$randomContent.val("");
            	var content = "";
            	for(var i=0;j = this.betRandom.randomContentSet.all[i];i++){
	        		content = content != ""?content + "\n"+ j:content + j;
            	}
            	this.$randomContent.val(content);
            },
            //计算随机数
            random:function(num){
            	if(typeof(num) != "undefined"){
            		this.betRandom.betRandom(num);
            	}
            	this.notes = this.betRandom.randomContentSet.all.length;
            },
            //清空投注赛事
            removeMatch:function(){
            	this.notes = 0;
            	this.bet.multi=1;
            	this._reCompute();
            	this.betRandom.randomContentSet = new Set();
            	this.$randomContent.val("");
            },
            //重新计算
            _reCompute:function(){
            	this.bet.money = this.bet.multi * this.notes * 2;
            	this.$notes.text(this.notes);
        		this.$money.text(this.bet.money);
        		this.$mc.text(this.notes);
        		
        		$('input[name="multiple"]').val(this.bet.multi);
            },
            _bind:function(){
            	var ctx = this;
            	this.$randomContent = $("#randomContent");
                $('a[_n="random1"]').bind('click', function(e){
                	ctx.createRandom(1);
                    return false;
                });
                $('a[_n="random10"]').bind('click', function(e){
                	ctx.createRandom(10);
                	return false;
                });
                $('a[_n="random100"]').bind('click', function(e){
                	ctx.createRandom(100);
                	return false;
                });
                $('a[_n="random1000"]').bind('click', function(e){
                	ctx.createRandom(1000);
                	return false;
                });
                $('a[_n="clear"]').bind('click', function(e){
                	ctx.removeMatch();
                	return false;
                });
                $('input[name="multiple"]', this.$form).bind('change', function(){
                    // 检查投注倍数
                    var v = $.trim($(this).val());
                    if(!(/^\d+$/g.test(v))){
                        alert('投注倍数必须是正整数');
                        $(this).val('1');
                        ctx.bet.multi = 1;
                        ctx._reCompute();
                        return false;
                    }
                    
                    v = parseInt(v);
                    if(v > 0 && v < 100){
                        ctx.bet.multi = v;
                    }else{
                    	ctx.bet.multi = 1;
                    	alert('投注倍数必须是1~99的整数');
                    }
                    ctx._reCompute();
                });
                
                this._betSubmit();
                
            	this.$bet.bind('click', function(){
            		if($.browser.msie && $.browser.version == 6){
                    	setTimeout(function(){
                    		ctx.$form.submit();
                    	}, 0);
                    } else {
                    	ctx.$form.submit();
                    }
                });
            	$("#loading")
				.ajaxStart(function(){
					$(this).show();
				})
				.ajaxComplete(function(){
					$(this).hide();
				});
            },
            // 投注
            _betSubmit: function(){
            	var ctx = this;
            	ctx.$form.bind('submit', function(e){
            		var multi = parseInt($(':input[name="multiple"]', ctx.$form).val());
            		if(ctx.offtime < new Date()){
                        alert('当前期已截止！请您稍后再投注！');
                        ctx.$bet.css("background","url(/df/images/bet/btn-c.png)"); 
                        return false;
                    }
                    if(ctx.bet.money <= 0){
                        alert('方案内容不能为空！');
                        return false;
                    }
                    if(ctx.bet.money > 200000){
                        alert('机选最高投注金额为20000元');
                        return false;
                    }
                    if(multi > 99){
                        alert('最高可投注99倍');
                        return false;
                    }
                    if(true){
                    	jQuery.event.trigger( "ajaxStart" );
                        ctx.$form.find(':input[name="playId"]').val(ctx.playId);
                        ctx.$form.find(':input[name="matchs"]').val(ctx.betRandom.toBet());
                        ctx.$form.find(':input[name="passTypes"]').val(ctx.bet.getBetPass().join(','));
                        $("<input>").attr("name","issueNumber").attr("type", "hidden").val(ctx.betRandom.issueNo).appendTo(ctx.$form);
                        $("<input>").attr("name","chooseType").attr("type", "hidden").val(2).appendTo(ctx.$form);
                    }else{
                    	e.preventDefault();
                    }
            	});
            }
    };
    RandomUI = Jooe.extend(RandomUI.prototype);
    
    var UploadUI =  function(){};
    UploadUI.prototype={
    		init: function(option){
                this._super.init(option);
    		},
    		_bind:function(){
    			this._super._bind();
    			var ctx=this;
    			$("#uploadTxt").click(function() {
    				// 上传方法
    				$.ajaxFileUpload({
    						url:'/aj_upload.do',  
    						secureuri:false,
    						fileElementId:'fileToUpload',
    						dataType: 'json',
    						data:{playId:ctx.playId,oldContent:ctx.$randomContent.val()},
    						success: function (data, status){
    							if(data!=null && data !="" && data.success){
                            		ctx.betRandom.randomContentSet.setAll(data.data);
                                    ctx.createRandom();
                            	}else{
                            		alert((data==null || data =="")?"上传文件错误！":data.data);
                            	}
    							$("#textpath").val("");
    						},
    						error: function (data, status, e){
    							alert(e);
    						}
    					}
    				);
    			});
    		}
    };
    UploadUI = RandomUI.extend(UploadUI.prototype);
    
    var UI = function(){};
    UI.prototype = {
        init: function(option){
            this.playId = option.playId;
            this.renderer = option.renderer;
            this.bet = new Bet(option);
            this.$time = option.time.find('[_n]');
            this.$panel = option.panel;
            this.$grid = option.grid;
            this.$row = [];
            var ctx = this, $dom = $('[_n]', this.$panel);
            this.$form = $dom.filter('[_n="form"]');    // 投注表单            
            this.$mKnob = $dom.filter('[_n="mKnob"]');  // 切换“已选赛事”按钮
            this.$mGrid = $dom.filter('[_n="mGrid"]');  // 已选赛事列表
            this.$clear = $dom.filter('[_n="clear"]');  // 清除所有已选赛事
            this.$bet = $dom.filter('[_n="bet"]');      // 投注按钮      
            this.$mc = $dom.filter('[_n="mc"]');        // 已选赛事数量
            this.$money = $dom.filter('[_n="money"]');  // 投注金额
            this.$bonus = $dom.filter('[_n="bonus"]');  // 最高奖金
            this.$notes = $dom.filter('[_n="notes"]');  // 注数
            this.$seed = $dom.filter('[_n="seeds"]');  // 胆码数
            this.$issueNumbersDiv = $("#issueNumbersDiv");  // 期号列表div
            this.issueNumber = ""; // 期号
            this.cache = []; // sel的前一个值
            this.offtime = ""; // 期截止时间
            
            this.observer = {
                clickOption: function(){
                    var $this = $(this);
                    ctx._clickOption($this.closest('tr').attr('_m'), $this.attr('_o'));
                    return false;
                },
                removeMatch: function(){
                    ctx._removeMatch($(this).attr('_m'));
                    return false;
                },
                setSeed: function(){
                	//设胆码
                	var seedset = [];
                	if($("input[name='dan']:checkbox:checked").length>8){
                		$(this).attr("checked", false);
                		alert("胆码不能大于8个！");
                		return;
                	}
                	$("input[name='dan']:checkbox:checked").each(function(obj){
                    		seedset.push($(this).attr('_dan'));
                	});
                	ctx._updateSeed(seedset);
                }
            };
            var urlSearch=window.location.search.substr(1);
            var issueNo=(urlSearch)?urlSearch.split("=")[1]:"";
            // 加载赛事数据
            $.getJSON(option.url, {playId: this.playId, _: new Date().getTime(),issueNumber:issueNo}, function(json){
                if(json.success && json.data.ctFBMatchs != null && json.data.ctFBMatchs.length > 0){
                	var playId = json.data.issueInfo.playId;
                	if(playId == '26_ZC_BQ' || playId == '27_ZC_JQ'){
                		ctx.renderMatch(ctx.bet.parseMatchsCopy(json.data.ctFBMatchs));
                	}else{
                		ctx.renderMatch(ctx.bet.parseMatchs(json.data.ctFBMatchs));
                	}
                }else{
                	ctx.$bet.css("background","url(/df/images/bet/btn-c.png)");
                }
                new IssueInfoUI().makeUI(json.data, ctx);
            });
            
            this._bind();
        },
        
        renderMatch: function(matchs){
            for(var i = 0, len = matchs.length; i < len; i++){
                this.cache[i] = 0;
            }
            this.$row = $(this.renderer.render(matchs)).appendTo(this.$grid);
            var ctx = this;

            // 赛事颜色 改变背景和字体颜色
//         console.profile('b');
//            $('span.sort').each(function(i,e){
//	            //背景
//        		if(matchColor[$(e).text()] != ""){
//        			$(e).css({background:matchColor[$(e).text()]});
//        		}
//        		//字体
//           		$(e).css({color:"#ffffff"});
//            });
//         console.profileEnd('b');
            
            // 选项按钮
            $('[_m]').delegate('a[_o]','click', this.observer.clickOption);
            
            // 全选按钮
            $('a[_m]').bind('click', function(){
                ctx._addMatch($(this).attr('_m'));
                return false;
            });
            
            // 赛事选项全清按钮
            $('a[_c]').bind('click', function(){
                ctx._removeMatch($(this).attr('_c'));
                return false;
            });

            // 显示隐藏选项
            $('a[_sh]').bind('click', function(){
                var $this = $(this), $opts = $this.closest('tr').next('tr[_m]');
                if($opts.is(':visible')){
                    $opts.hide();
                    $this.find('[_sh="text"]').text('显示选项');
                    $this.find('[_sh="icon"]').removeClass('icns16_arrt-b').addClass('icns16_arrb-b');
                }else{
                    $opts.show();
                    $this.find('[_sh="text"]').text('隐藏选项');
                    $this.find('[_sh="icon"]').addClass('icns16_arrt-b').removeClass('icns16_arrb-b');
                }
                return false;
            });
            
            // 星期赛事的隐藏和显示
            $('a[_w]').bind('click', function(){
                var $this = $(this), $tr = ctx.$row.filter('[_w="' + $this.attr('_w') + '"]');
                if($this.attr('_v') == '0'){
                    $this.attr('_v', '1').text('点击显示').siblings('b[_n="cls"]').addClass('icns16_arrb');
                    $tr.hide();
                }else{
                    $this.attr('_v', '0').text('点击隐藏').siblings('b[_n="cls"]').removeClass('icns16_arrb');
                    $tr.filter(':not([_s])').show();
                }
                return false;
            });
        },

        repaint: function(){
            var mask = this.bet.mask, ms = this.bet.matchs, c = this.cache,
                $j, $this, sel, o, s, j;
            
            this.$row.filter('[_m]').each(function(){
                $j = $(this), j = $j.attr('_m');
                if(c[j] != ms[j].sel){
                    sel = ms[j].sel;
                    c[j] = sel;
                    if(sel == 0){
                        $j.find('a[_s="1"]').removeClass('btnx3_acti-a').attr('_s', '0');
                    }else if(sel == mask){
                        $j.find('a[_s="0"]').addClass('btnx3_acti-a').attr('_s', '1');
                    }else{
                        $j.find('a[_s]').each(function(){
                            $this = $(this), o = $this.attr('_o'), s = $this.attr('_s');
                            if(sel & Math.pow(2, o)){
                                if(s == '0'){
                                    $this.addClass('btnx3_acti-a').attr('_s', '1');
                                }
                            }else if(s == '1'){
                                $this.removeClass('btnx3_acti-a').attr('_s', '0');
                            }
                        });
                    }
                }
            });
            this.paintMatch();
        },

        paintMatch: function(){
            if(this.$mGrid.is(':visible')){
                this.$mc.text(this.bet.mc);
                var h = [], ms = this.bet.matchs;
                for(var i = 0, len = ms.length; i < len; i++){
                    if(ms[i].sel > 0){
                        h.push(this._renderMatch(i, ms[i]));
                    }
                }
                
                var $ms = $(h.join('')).appendTo($('tbody', this.$mGrid).empty());
                //勾选胆码
                var seedset = this.bet.getSeedSet();

                	$("input[name='dan']:checkbox").each(function(){
                    	if($(this).attr('_dan') in seedset){
                    		$(this).attr("checked",'true');
                    	}
                	});

                // 选项按钮
                $('td a[_o]', $ms).bind('click', this.observer.clickOption);
                // 胆码选项
                if($('[_dan]')){
                	$('[_dan]').bind('click', this.observer.setSeed);
                }
                // 删除赛事按钮
                $('td a[_m]', $ms).bind('click', this.observer.removeMatch);
            }
        },
        
        _bind: function(){
            var ctx = this;
            this.$mKnob.bind('click', function(){
                if(ctx.$mGrid.hasClass('moreorder_acti')){
                    ctx.$mGrid.removeClass('moreorder_acti');
                }else{
                    ctx.$mGrid.addClass('moreorder_acti');
                    ctx.paintMatch();
                }
                return false;
            });
            
            ctx._betSubmit();
            
            this.$bet.bind('click', function(){
            	if($.browser.msie && $.browser.version == 6){
                	setTimeout(function(){
                		ctx.$form.submit();
                	}, 0);
                } else {
                	ctx.$form.submit();
                }
            });
            
            $('input[name="multiple"]', this.$form).bind('change', function(){
                // 检查投注倍数
                var v = $.trim($(this).val());
                if(!(/^\d+$/g.test(v))){
                    alert('投注倍数必须是正整数');
                    $(this).val('1');
                    ctx.bet.multi = 1;
                    ctx._resolve();
                    return false;
                }
                
                v = parseInt(v);
                if(v > 0 && v < 100){
                    ctx.bet.multi = v;
                }else{
                	ctx.bet.multi = 1;
                	$(this).val('1');
                	alert('投注倍数必须是1~99的整数');
                }
                ctx._resolve();
            });
            
            // 赛事全清按钮
            this.$clear.bind('click', function(){
                ctx._clearMatch();
            });
            
            // 时间切换
            this.$time.bind('click', function(){
                ctx._toggleTime($(this).attr('_n'));
            });
        },
        
        _toggleTime: function(n){
            var $a = this.$time.eq(n).find('>a');
            if($a.is(':visible')){
                $a.hide().siblings('span').show();
                this.$time.eq(1-n).find('>a').show().siblings('span').hide();
                var $span = this.$grid.find('span[_i]');
                $span.filter('[_i="' + n + '"]').show();
                $span.filter('[_i="' + (1-n) + '"]').hide();
            }
        },
        
        // 投注赛事的所有选项
        _addMatch: function(i){
            this.bet.addMatch(i);
            this._resolve();
            this.repaint();
        },
        
        // 添加/删除过关方式
        _togglePass: function(i){
            this.bet.togglePass(i);
            this._resolve();
        },
        
        // 投注
        _betSubmit: function(){
        	var ctx = this;
        	ctx.$form.bind('submit', function(e){
        		 var multi = parseInt($(':input[name="multiple"]', ctx.$form).val());
        		 if(ctx.offtime < new Date()){
                     alert('当前期已截止！请您稍后再投注！');
                     ctx.$bet.css("background","url(/df/images/bet/btn-c.png)"); 
                     return false;
                 }
                 if(ctx.bet.money <= 0){
                     alert('请选择赛事');
                     return false;
                 }
                 if(ctx.bet.money > 1000000){
                     alert('最高投注金额为100万元');
                     return false;
                 }
                 if(multi > 99){
                     alert('最高可投注99倍');
                     return false;
                 }
                 if(true){
                	 ctx.$form.find(':input[name="playId"]').val(ctx.playId);
                	 ctx.$form.find(':input[name="matchs"]').val(ctx.bet.toBetMatchs());
                	 ctx.$form.find(':input[name="passTypes"]').val(ctx.bet.getBetPass().join(','));
                	 $("<input>").attr("name","issueNumber").attr("type", "hidden").val(ctx.issueNumber).appendTo(ctx.$form);
                	 $("<input>").attr("name","chooseType").attr("type", "hidden").val(0).appendTo(ctx.$form);
                 }else{
                	 e.preventDefault();
                 }
        	});
        },

        // 点击赛事的某个选项
        _clickOption: function(i, j){
            this.bet.clickOption(i, j);
            this._resolve();
            this.repaint();
        },
        
        // 清除所选赛事
        _clearMatch: function(){
            this.bet.clearMatch();
            this._resolve();
            this.repaint();
        },
        
        // 删除指定投注的赛事
        _removeMatch: function(i){
            this.bet.removeMatch(i);
            this._resolve();
            this.repaint();
        },
        
        //更新胆码
        _updateSeed:function(seedset){
        	this.bet.updateSeed(seedset);
        	this._resolve();
        	this.repaint();
        },
        
        // 计算投注金额和奖金
        _resolve: function(){
            var r = this.bet.resolve();
            this.$money.text(r.money);
            this.$bonus.text(r.bonus);
            this.$notes.text(r.note / this.bet.multi);
            this.$seed.text(r.seeds);
        },
        
        _renderMatch: function(i, m){
        	//只有任选9场复式玩法可以加胆码
			if(this.playId.indexOf('ZC_R9') != -1){
				return $.format(tmpl.matchseed, i, m.cnCode, m.hName, m.gName, this._renderOptions(m));
			}
            return $.format(tmpl.match, i, m.cnCode, m.hName, m.gName, this._renderOptions(m));
        },
        
        _renderOptions: function(m){
            var opt = [], o = m.opts, sel = m.sel, r = this.renderer;
            for(var j = 0, len = o.length; j < len; j++){
                if(sel & (1 << j)){
                    opt.push($.format(tmpl.opt, j, r.optionName(o[j])));
                }
            }
            return opt.join('');
        }
    };
    UI = Jooe.extend(UI.prototype);
    
    /**
     * 过关
     */
    var PassUI = function(){};
    PassUI.prototype = {
        init: function(option){
            this._super.init(option);
            
            $dom = $('[_n]', option.panel);
            this.$pRow = $dom.filter('[_n="pRow"]');
            this.$pGrid = $dom.filter('[_n="pGrid"]');
            this.$pTip = $dom.filter('[_n="pTip"]');

            // 加载允许的过关方式
//            $.getJSON('http://trade.davcai.com/df/aj_lspasstype.do?jsonp=?', {playId: this.playId, _: new Date().getTime()}, function(json){
//                if(json.success && json.data.length > 0){
//                    ctx._renderPass(json.data);
//                }
//            });
        },
        
        repaint: function(){
            this._super.repaint();
            this._refresh();
        },
        
        _renderPass: function(pt){
            var ctx = this, m = 1, i = 0, p, len = pt.length;
            var x = [], y = [], pts = [];
            
            for(; m < 4 && i < len; m++){
                for(; i < len; i++){
                    p = pt[i].id;
                    if(m != p.charAt(0)){
                        break;
                    }
                    pts.push(p);
                    x.push($.format(tmpl.pass, m, p.substr(2), i));
                }
            }
            for(; m < 16 && i < len; m++){
                y.push('<div class="row">');
                for(; i < len; i++){
                    p = pt[i].id;
                    if(m != p.charAt(0)){
                        break;
                    }
                    pts.push(p);
                    y.push($.format(tmpl.pass, m, p.substr(2), i));
                }
                y.push('</div>');
            }
            this.bet.passTypes = pts;
            if(x.length > 0){
                this.$pGrid.before(x.join(''));
            }
            if(y.length > 0){
                this.$pGrid.find('[_n=pExRow]').after(y.join(''));
            }
            this.$pRow.find(':checkbox').bind('click', function(){
                ctx._togglePass($(this).val());
            });
        },
        
        //隐藏显示允许的过关方式选项
        _refresh: function(){
            var mc = this.bet.mc, $this;
            //计算胆码数量
            var seedcount=0;
            var bms = this.bet._getBetMatchs();
            for(var i=0;i<bms.length;i++){
            	if(bms[i].seed){
            		seedcount++;
            	}
            }
            this.$pRow.find('label[_m]').each(function(){
                $this = $(this);
                if($this.attr('_m') <= mc){
                    if($this.is(':hidden')){
                        $this.show();
                    }
                }else if($this.is(':visible')){
                    $this.hide().find(':checkbox').attr('checked', false);
                }
                //取消胆码不支持的玩法
                if($this.attr('_m') < seedcount){
                	if($this.is(':visible')){
                        $this.hide().find(':checkbox').attr('checked', false);
                    }
                }
            });
            this.$pTip.toggle(mc < 2);
            this.$pGrid.toggle(mc > 3);
        },
        
        _bind: function(){
            var ctx = this;
            $('a[_n="pKnob"]', this.$pRow).bind('click', function(){
               if(ctx.$pGrid.hasClass('morechk_acti')){
                   ctx._hide();
               }else{
                   ctx._show();
               }
               return false;
            });
            
            $('a[_n="pHide"]', this.$pRow).bind('click', function(){
                ctx._hide();
                return false;
            });
            
            this._super._bind();
        },
        
        _hide: function(){
            this.$pGrid.removeClass('morechk_acti');
        },
        _show: function(){
            this.$pGrid.addClass('morechk_acti');
            this._refresh();
        }
    };
    PassUI = UI.extend(PassUI.prototype);
    
    var IssueInfoUI = function(){};
    IssueInfoUI.prototype={
    		makeUI:function(data,ctx){
    			if(data.issueInfo !=null){
            		var stopTimeForUser = data.issueInfo.stopTimeForUser;
            		var bonusCode = data.issueInfo.bonusCode;var issueNumber = data.issueInfo.issueNumber;
            		var listStr="";var cur=""; ctx.issueNumber = issueNumber;
            		// 历史期下拉菜单
            		for(i in data.oldIssueInfos){
            			cur=data.oldIssueInfos[i].issueNumber==issueNumber?' class="cur"':cur;
            			listStr='<option value=\"'+data.oldIssueInfos[i].issueNumber+'\" '+ (data.oldIssueInfos[i].issueNumber==issueNumber?' selected=\"selected\" ':' ')+'>20' + data.oldIssueInfos[i].issueNumber + '期</option>'+listStr; 
            		}
            		listStr='<a '+cur+'><span><select onChange="javascript:window.location.search=\'issueNumber=\'+this.options[this.options.selectedIndex].value;"><option>历史期</option>'+listStr+'</select></span></a>';
            		// 在售期下拉菜单
            		for(i in  data.issueInfos){
            			cur=data.issueInfos[i].issueNumber==issueNumber?' class="cur"':'';
            			listStr+='<a '+cur+' href="javascript:void(0);" onClick="javascript:window.location.search=\'issueNumber=' + data.issueInfos[i].issueNumber + '\'" ><span ><b>20' + data.issueInfos[i].issueNumber + '期</b></span></a>'; 
            		}
            		var allBonus=data.issueInfo.bonusInfo.split(";");
            		var first=allBonus[0].split(" ");var seconed=allBonus.length>1?allBonus[1].split(" "):"";
            		listStr+='<span style="padding:5px 0 0 0;">&nbsp;开奖号码：<b style="color:red;">' + (bonusCode==""?'--':bonusCode) + '</b>  开奖信息：<b style="color:red;">'
            		+(bonusCode==""?data.issueInfo.bonusInfo:first[0]+" "+first[1]+"注 "+(first[2]=="无人中奖"?"0":first[2])+"元 "+(seconed==""?"":seconed[0]+" "+seconed[1]+"注 "+seconed[2]+"元 "))+'</b></span>';
            		ctx.$issueNumbersDiv.html("");
            		$(listStr).appendTo(ctx.$issueNumbersDiv);
            		// 显示赛果
            		this.showResult(ctx, bonusCode);
            		// 截至时间
            		$("#issueNumber").text("截止时间：" + stopTimeForUser.replace("T"," ")) ;
            		
            		if(ctx.betRandom){ctx.betRandom.issueNo = ctx.issueNumber;}
            		// 是否截至，按钮是否变灰
            		var time = stopTimeForUser.replace("T"," ").replaceAll("-","/");
            		ctx.offtime =  new Date(time);
            		if(ctx.offtime < new Date()){
            			ctx.$bet.css("background","url(/df/images/bet/btn-c.png)"); 
            		}
            	}else{
            		ctx.$bet.css("background","url(/df/images/bet/btn-c.png)"); 
            	}
    		},
    		showResult:function(ctx, bonusCode){
        		if(bonusCode){
        			var bonus = bonusCode.split(''), bonusKey = {'3':'0', '1':'1', '0':'2'}, jqBonusKey = {'0':'0', '1':'1', '2':'2', '3':'3'},m = 0;
        			ctx.$grid.find('tr[_m]').each(function(){
        				var $this = $(this), curBonus = (bonusCode.length == 8) ? jqBonusKey : bonusKey ;
        				var bon = bonus[$this.attr('_m')] ;
        				if(bon != '*'){
        					ctx._clickOption(m, curBonus[bon]);
        				}else{
        					ctx._addMatch(m);
        				}
        				m++;
        			});
        		}
    		}
    };
    IssueInfoUI = Jooe.extend(IssueInfoUI.prototype);
    
    var BetRandom = function(){};
    BetRandom.prototype={
    		init:function(option){
    			this.opts=option.opts.sort(function(a,b){return a>b?1:-1;}); // 可选项集合
    			this.max=this.opts[option.opts.length-1]; // 单个随机数最大值
    			this.min=this.opts[0]; // 单个随机数最小值
    			this.length=option.length; // 一注的长度
    			this.fill=option.fill || ""; // 填充字符
    			this.fillCount=option.fillCount || 0; // 填充字符出现的次数
    			this.optSet=new Set(); //初始化可选项集合
    			this.randomContentSet = new Set(); // 不重复的随机数集合
    			this.issueNo=""; // 期号
    			this.numTmp = 0; // 临时变量
    			this.resultCount = 0; 
    			
    			this.initOpts(); // 初始化可选项内容
    		},
    		
    		//产生count注 随机数方法
			betRandom:function(count){
				this.resultCount = 0;
				do{
					var opt = [];
					this.random(opt); // 计算一注随机结果
					this.fillRandom(opt); // 如果有填充字符串则进行填充
					this.save(opt); // 保存不重复数据
				}while(this.resultCount < count);
			},
			
			 // 保存不重复数据
			save:function(opt){
				var oneResult = opt.join("");
				if(!(oneResult in this.randomContentSet)){
					this.randomContentSet.add(oneResult);
					this.resultCount++;
				}
			},
			
			//产生一条随机数
			random:function(opt){
				do{
					numTmp = parseInt(Math.random()*(this.max-this.min+1)+this.min);
					if(numTmp in this.optSet){
						opt.push(numTmp);
					}
				}while(opt.length<this.length);
			},
			
			//产生一条内部数据不重复的随机数
			randomNoRepeat:function(result){
				var tmpSet = new Set();
				do{
					numTmp = parseInt(Math.random()*(this.length - 1));
					
					if(!(numTmp in tmpSet)){
						tmpSet.add(numTmp);
						result.push(numTmp);
					}
				}while(result.length<this.fillCount);
			},
			
			//填充数据
			fillRandom:function(opt){
				if(this.fillCount == 0){
					return;
				}
				var result = [];
				this.randomNoRepeat(result);
				
				for(var j=0;j<this.fillCount;j++){
					opt[result[j]] = this.fill;
				}
			},
			
			//组织准备提交的数据
			toBet:function(){
				var result = [];
				var all = this.randomContentSet.all;
				for(var o in all){
					result.push(this.issueNo + ";" +  all[o].split("").join(",") + ";");
				}
				return result.join("|").replaceAll("\\*","-","");
			},
			
			initOpts:function(){
	    		for(var o in this.opts){
	    			this.optSet.add(this.opts[o]);
	    		}
			}
			
    };
    BetRandom = Jooe.extend(BetRandom.prototype);
    
    var SF14Random = function(){};
    SF14Random.prototype={
    		init: function(){
    			this._super.init({
    				length: 14,
    				opts:['3','1','0']
    			});
    		}
    };
    SF14Random = BetRandom.extend(SF14Random.prototype);
    
    var SFR9Random = function(){};
    SFR9Random.prototype={
    		init: function(){
    			this._super.init({
    				length: 14,
    				opts:['3','1','0'],
    				fill:'*',
    				fillCount: 5
    			});
    		}
    };
    SFR9Random = BetRandom.extend(SFR9Random.prototype);
    
    var BQ6CRandom = function(){};
    BQ6CRandom.prototype={
    		init: function(){
    			this._super.init({
    				length: 12,
    				opts:['3','1','0']
    			});
    		}
    };
    BQ6CRandom = BetRandom.extend(BQ6CRandom.prototype);
    
    var JQ4CRandom = function(){};
    JQ4CRandom.prototype={
    		init: function(){
    			this._super.init({
    				length: 8,
    				opts:['3','2','1','0']
    			});
    		}
    };
    JQ4CRandom = BetRandom.extend(JQ4CRandom.prototype);
    
    this.Renderer = Renderer;
    this.UI = UI;
    this.PassUI = PassUI;
    this.RandomUI = RandomUI;
    this.IssueInfoUI = IssueInfoUI;
    this.UploadUI = UploadUI;
    
    this.UploadRenderer = UploadRenderer;
    this.SFRandomRenderer = SFRandomRenderer;
    this.ZC1Renderer = ZC1Renderer;
    this.ZC3Renderer = ZC3Renderer;
    this.ZC4Renderer = ZC4Renderer;
    this.BetRandom = BetRandom;
    this.SF14Random = SF14Random;
    this.SFR9Random = SFR9Random;
    this.BQ6CRandom = BQ6CRandom;
    this.JQ4CRandom = JQ4CRandom;
    
	String.prototype.replaceAll = function(reallyDo, replaceWith, ignoreCase) {  
		if (!RegExp.prototype.isPrototypeOf(reallyDo)) {  
			return this.replace(new RegExp(reallyDo, (ignoreCase ? "gi": "g")), replaceWith);
		} else {  
			return this.replace(reallyDo, replaceWith);  
		}  
	};
});
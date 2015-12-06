/**猜冠军投注*/
var CGJBet = function() {
	
};

CGJBet.prototype = {
	getTemplate:function() {
		var common = 
			'<li name="items" _teamId="{{teamId}}" class="{{isDead}}">'+
		    	'<a href="javascript:void(0);">'+
		        '<img src="{{teamLogo}}" class="flag"/>'+
			    '<span class="country fl font_15">{{teamName}}</span>'+
			    '<span class="rank font_14" title="fifa排名">[{{rank}}]</span>'+
			    '<em class="fr font_15" title="赔率" _odds="{{odds}}">{{odds}}</em>'+
			    '</a>'+
		    '</li>';
		var tpl = 
			'<div class="empty"></div>'+
			'<ul class="form">'+
				'<img src="/images/bet/a.png" />'+
				'{{#aGroup}}'+ common+ '{{/aGroup}}'+
			'</ul>'+
			'<ul class="form">'+
				'<img src="/images/bet/b.png" />'+
				'{{#bGroup}}'+ common+ '{{/bGroup}}'+
			 '</ul>'+
			'<ul class="form">'+
				'<img src="/images/bet/c.png" />'+
				'{{#cGroup}}'+ common+ '{{/cGroup}}'+
			 '</ul>'+
			 '<ul class="form">'+
				'<img src="/images/bet/d.png" />'+
				'{{#dGroup}}'+ common+ '{{/dGroup}}'+
			 '</ul>'+
			 '<div class="emptyb"></div>'+
			'<ul class="form">'+
				'<img src="/images/bet/e.png" />'+
				'{{#eGroup}}'+ common+ '{{/eGroup}}'+
			'</ul>'+
			'<ul class="form">'+
				'<img src="/images/bet/f.png" />'+
				'{{#fGroup}}'+ common+ '{{/fGroup}}'+
			 '</ul>'+
			'<ul class="form">'+
				'<img src="/images/bet/g.png" />'+
				'{{#gGroup}}'+ common+ '{{/gGroup}}'+
			 '</ul>'+
			 '<ul class="form">'+
				'<img src="/images/bet/h.png" />'+
				'{{#hGroup}}'+ common+ '{{/hGroup}}'+
			 '</ul>'
		;
		return tpl;
	},
	_init: function() {
		this.maxValue = "";
		this.matchSelectSum = $("b[_n='mc']");
		this.maxBonusPanel = $("b[_n='maxBonus']");
		this.betMoney = $("b[_n='money']");
		this.betInput = $('input[_n="_betNote"]');
		this.clearAll = $('a[_n="clearAll"]');
		this.betButton = $('a[_n="bet"]');
		this.betForm = $('form[_n="form"]');
	},
	_requestUrlByPlay:function(playId) {
		var url = "";
		if(playId == "jcsjbgj") {
			url = "http://trade.davcai.com/df/aj_jcsjbgj.do?jsonp=?";
		}
		return url;
	},
	ajaxLoadMatch:function(playId) {
		var $this = this;
		var url = this._requestUrlByPlay(playId);
		if(url) {
			$.post(url, {}, function(json) {
				$this._renderMatch(json.data);
			}, 'json');
		}
	},
	_renderMatch:function(jsonData) {
		var result = $.mustache(this.getTemplate(), {
			aGroup:jsonData["A组"],
			bGroup:jsonData["B组"],
			cGroup:jsonData["C组"],
			dGroup:jsonData["D组"],
			eGroup:jsonData["E组"],
			fGroup:jsonData["F组"],
			gGroup:jsonData["G组"],
			hGroup:jsonData["H组"],
			isDead:function() {
				if(this.status == 0 || 
					this.status == 4 || 
					this.status == 5) {
					return "is-dead";
				}
			}
		});
		$("#cjsjbgj_match").append(result);
		this._init();
		this._bindClickEvent($("#cjsjbgj_match"));
	},
	_bindClickEvent:function(container) {
		var $this = this;
		container.find(".form li").click(function() {
			var li = this;
			if($this.isCanBet(li)) {
				$this.clickOption(li, $this);
			}
		});
		this.betInput.change(function() {
			var betNote = $(this).val();
			var check = /^\d+$/g.test(betNote);
			if(check) {
				$this.calcMoney();
			} else {
				$(this).val(1);
				alert("您好^_^，投注倍数必须为整数!");
			}
		});
		this.clearAll.click(function() {
			$this.clearOption();
		});
		this.betButton.click(function() {
			var codeInput = $("input[name='code']", $this.betForm);
			var oddsInput = $("input[name='odds']", $this.betForm);
			if(!$this.selectedMatch || $this.selectedMatch.length <= 0) {
				alert("请选择投注选项!");
				return;
			} else {
				codeInput.val($this.selectedMatch.join(","));
			}
			if(!$this.oddList || $this.oddList.length <= 0) {
				alert("存在非法的投注选项赔率!");
				return;
			} else {
				oddsInput.val($this.oddList.join(","));
			}
			
			$this.betForm.submit();
		});
	},
	clickOption:function(li, ctx) {
		$(li).toggleClass("active");
		var odds = $("em[_odds]",$(li)).attr("_odds");
		var teamId = $(li).attr("_teamId");;
		var isActived =  $(li).attr("class") == "active" ? true : false;
		if(isActived){//选项被激活
			if(!ctx.maxValue){
				ctx.maxValue = odds;
			} else {
				var tmp =  parseInt(ctx.maxValue) > parseInt(odds) ? ctx.maxValue : odds;
				ctx.maxValue = tmp;
			}
			ctx.addMatch(teamId);
			ctx.oddList.push(odds);
		} else {
			ctx.removeMatch(teamId);
			ctx.removeElement(ctx.oddList, odds);
			ctx.maxValue = Math.max.apply(Math, ctx.oddList);
		}
		ctx.calcMoney();
	},
	selectedMatch: [],
	oddList : [],
	addMatch:function(teamId) {
		this.selectedMatch.push(teamId);
	},
	removeMatch:function(teamId) {
		this.selectedMatch.pop(teamId);
	},
	calcMoney:function() {
		var selectCount = this.selectedMatch.length;
		if(selectCount && selectCount > 0) {
			var betNote = this.betInput.val();
			this.matchSelectSum.text(selectCount);
			this.betMoney.text(selectCount * 2 * betNote);
			
			var maxBonus = this.maxValue * 2 * betNote;
			this.maxBonusPanel.text(maxBonus);
		} else {
			this.betInput.val(1);
			this.betMoney.text(0);
			this.maxBonusPanel.text(0);
			this.maxValue = 0;
			this.matchSelectSum.text(0);
		}
	},
	clearOption:function() {
		$(".form li").removeClass("active");
		this.selectedMatch = [];
		this.oddList = [];
		this.maxValue = 0;
		this.calcMoney();
	},
	removeElement:function(array, element) {
		for(var elt in array) {
			if(element == array[elt]) {
				array.splice(elt, 1);
			}
		}
	},
	isCanBet:function(element) {
		var isCanBet = $(element).hasClass("is-dead");
		return isCanBet ? false : true;
	}
};

$(function() {
	var cgjBet = new CGJBet();
	cgjBet.ajaxLoadMatch("jcsjbgj");
});

var dateStrToDate = function(dateStr) {
	var convertDate = null;
	if(dateStr) {
		dateStr = dateStr.replace(/T/g, ' ');
		var mul = dateStr.split("-");
		var year = mul[0];
		var month = parseInt(mul[1]);
		var end = mul[2];
		var before = end.split(" ");
		var day = before[0];
		var mill = before[1].split(":");
		var hour = mill[0];
		var minute = mill[1];
		var seconds = mill[2];
		convertDate = new Date(year, month-1, day, hour, minute, seconds);
	}
	return convertDate;
};

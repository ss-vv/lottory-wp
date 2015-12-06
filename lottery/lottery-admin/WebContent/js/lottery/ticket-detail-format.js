$(function() {
    $('#dataGrid input').bind('click', function(){
        var _this = $(this), _view = $('#detailView td[_row]');
		var cont = $("#cont");
		if(cont.attr("id")) {
			cont.html(_this.attr('_code'));
		}
		var _ticketPlayId = _this.attr('_playId');
		if(_ticketPlayId == "24_ZC_14" || _ticketPlayId == "25_ZC_R9" ||
				_ticketPlayId == "26_ZC_BQ" || _ticketPlayId == "27_ZC_JQ") {
			var ctOptions = _this.attr('_code').split(",");
			var rs = format_ctzc_bet(ctOptions, _ticketPlayId);
			cont.html(rs);
			return;
		}
		
        var oArr = _this.attr('_odds').split('-'), cArr = _this.attr('_code').split('-');
        var h = [], o;
        for(var i = 0, j = cArr.length; i < j; i++){
            if(oArr.length > i && oArr[i] != ''){
				if(-1 != _this.attr('_odds').indexOf("/")) {
					o = _this.attr('_odds').split('/')[i];
				} else {
                    o = oArr[i].replace(/A/g, ' ');
				}
            }else{
                o = '--';
            }
			var c_playId = $("#clickDetail").attr("_playId");
			
			var c_code = cArr[i].substr(0, 4) + " ";
			var c_bet = cArr[i].substr(4);
			var betArr = c_bet.split(":");
			var c_code = BetContent.getWeek(c_code.substr(0, 1)) + c_code.substr(1);
			if(c_playId && (c_playId == "05_ZC_2" || c_playId == "10_LC_2")) {
				var betPlayName = BetContent.getPlayName(betArr[1].toLowerCase(), true);
				var rs = BetContent.convertBetOption(betArr[0], betArr[1], true)
    			c_bet = "(" + betPlayName + ") " + rs;
			} else {
				var betPlayName = BetContent.getPlayName(c_playId, false);
				c_bet = BetContent.convertBetOption(c_bet, c_playId, false)
				c_bet = "(" + betPlayName + ") " + c_bet;
			}
            h.push(jQuery.format('<span class="bet-css">{0}<b class="red">{1}</b> ({2})<br/></span>', 
				c_code, c_bet, o));
        }
        
        _view.filter('[_row="id"]').text(_this.attr('_id'));
        _view.filter('[_row="expectBonus"]').html(_this.attr('_expectBonus') + ' 元(<span class="red">请工作人员注意核对金额</span>)');
        _view.filter('[_row="msg"]').text(_this.attr('_msg'));
        _view.filter('[_row="code"]').html(h.join(''));
            
        return false;
    });
	
	$('#clickDetail').click();
	
});

//format bet options
var format_ctzc_bet = function(options, playId) {
	var result = "";
	if("27_ZC_JQ" == playId) {
		result = format_4cjq(options, playId);
	} else if("26_ZC_BQ" == playId) {
		result = format_6cbq(options, playId);
	} else if("25_ZC_R9" == playId) {
		result = format_rx9c(options, playId);
	} else if("24_ZC_14" == playId) {
		result = format_rx9c(options, playId);
	}
	return result;
};

var format_rx9c = function(options, playId) {
	var result = "";
	if(options.length == 14) {
		var len = options.length;
		var tmp = [];
		for(var index=0; index<len; index++) {
			var opt = options[index];
			opt = opt.replace("3","胜");
			opt = opt.replace("1","平");
			opt = opt.replace("0","负");
			if("-" != opt) {
				opt = "<span class='red'>" + opt + "</span>";
			}
			tmp.push("(" + parseInt(index+1) + ")" + opt);
		}
		result = result + tmp.join("，") + "<br/>";
	}
	return result;
};

var format_4cjq = function(options, playId) {
	var result = "";
	if(options.length == 8) {
		var len = options.length;
		var tmp = [];
		for(var index=0; index<len; index++) {
			var opt = options[index];
			opt = opt.replace("3","3+球");
			opt = opt.replace("2","2球");
			opt = opt.replace("1","1球");
			opt = opt.replace("0","0球");
			if(index == 0 || index % 2 == 0) {
				opt = "（主）" + opt;
			} else {
				opt = "（客）" + opt;
			}
			if(index > 0 && (index+1) % 2 != 0) {
				result = result + tmp.join("，") + "</br>";
				tmp = [];
			}
			tmp.push(opt);
			if(index == len-1 && tmp.length > 0) {
				result = result + tmp.join("，") + "</br>";
			}
		}
	}
	return result;
};

var format_6cbq = function(options, playId) {
	var result = "";
	if(options.length == 12) {
		var len = options.length;
		var tmp = [];
		for(var index=0; index<len; index++) {
			var opt = options[index];
			opt = opt.replace("3","胜");
			opt = opt.replace("1","平");
			opt = opt.replace("0","负");
			if(index == 0 || index % 2 == 0) {
				opt = "（半）" + opt;
			} else {
				opt = "（全）" + opt;
			}
			if(index > 0 && (index+1) % 2 != 0) {
				result = result + tmp.join("，") + "</br>";
				tmp = [];
			}
			tmp.push(opt);
			if(index == len-1 && tmp.length > 0) {
				result = result + tmp.join("，") + "</br>";
			}
		}
	}
	return result;
};


var tpl =
	'{{#odd}}'+
	'<tr class="bor_bottom_2">'+
	'    <td width="16%" align="center">{{name}}</td>'+
	'    <td width="29%" align="center">'+
	'        <table width="100%" class="sl_wrap" border="0" cellspacing="0" cellpadding="0">'+
	'            <tbody>'+
	'                <tr>'+
	'                    <td height="36" align="center" width="30%">'+
	'                        <span>{{initHomeOddsView}}</span>'+
	'                    </td>'+
	'                    <td align="center">'+
	'                        <span class="z">{{initHandicapView}}</span>'+
	'                    </td>'+
	'                    <td align="center" width="30%">'+
	'                        <span>{{initGuestOddsView}}</span>'+
	'                    </td>'+
	'                </tr>'+
	'            </tbody>'+
	'        </table>'+
	'    </td>'+
	'    <td width="29%" align="center">'+
	'        <table width="100%" class="sl_wrap" border="0" cellspacing="0" cellpadding="0">'+
	'            <tbody>'+
	'                <tr>'+
	'                    <td height="36" align="center" width="30%">'+
	'                        <span>{{curHomeOddsView}}</span>'+
	'                    </td>'+
	'                    <td align="center">'+
	'                        <span class="z">{{curHandicapView}}</span>'+
	'                    </td>'+
	'                    <td align="center" width="30%">'+
	'                        <span>{{curGuestOddsView}}</span>'+
	'                    </td>'+
	'                </tr>'+
	'            </tbody>'+
	'        </table>'+
	'    </td>'+
	'    <td align="center">'+
	'        &nbsp;'+
	'    </td>'+
	'    <td width="14%" align="center" height="36" class="sl_wrap">'+
	'        <a href="javascript:void(0)" class="z">主</a>'+
	'        <a href="javascript:void(0)" class="k">客</a>'+
	'        <a href="javascript:void(0)" class="t">同</a>'+
	'    </td>'+
	'</tr>'+
	'{{/odd}}';

var ajaxAsianOdds = function() {
	var matchIdStr = getQueryString("matchId");
	var lotteryType = getQueryString("lotteryType");
	var param = {matchId: matchIdStr, lotteryType : lotteryType};
	$.ajax({
		type:"post",
		dataType:"json",
		url : "/ajax_match_asian_odds",
		data:param,
		success : function(result) {
			$(".loading[asian]").hide();
			if(result && result.data) {
				renderAsianOdds(result.data, lotteryType);
			}
		}
	});
};

var renderAsianOdds = function(data, lotteryType) {
	if(data) {
		$(".contrast_list").find(".homeTeamName").html(data.homeTeamName);
		$(".contrast_list").find(".guestTeamName").html(data.guestTeamName);
	}
	var segment = null;
	var type = new lottery();
	var odd = "";
	if(type.isZC(lotteryType)) {
		odd = data.fbAsianOddList;
	} else if(type.isLC(lotteryType)) {
		odd = data.bbAsianOddList;
	}
	
	segment = $.mustache(tpl, {
		odd:odd,
		initHomeOddsView:function (){
			return oddRound(this.initHomeOdds,2);
		},
		initHandicapView:function (){
			return oddRound(this.initHandicap,2);
		},
		initGuestOddsView:function (){
			return oddRound(this.initGuestOdds,2);
		},
		curHomeOddsView:function (){
			return oddRound(this.curHomeOdds,2);
		},
		curHandicapView:function (){
			return oddRound(this.curHandicap,2);
		},
		curGuestOddsView:function (){
			return oddRound(this.curGuestOdds,2);
		}
	});
	if(segment) {
		$(".asianOdds").html(segment);
	}
};
function oddRound(num,v) {
	var f_x = parseFloat(num);
	if (isNaN(f_x)){
		return num;
	}
	f_x = Math.round(f_x*100)/100;
	var s_x = f_x.toString();
	var pos_decimal = s_x.indexOf('.');
	if (pos_decimal < 0){
		pos_decimal = s_x.length;
		s_x += '.';
	}
	while (s_x.length <= pos_decimal + v){
		s_x += '0';
	}
	return s_x;
}
$(document).ready(function() {
	ajaxAsianOdds();
});
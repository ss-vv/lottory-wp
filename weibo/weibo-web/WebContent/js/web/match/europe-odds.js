var fbTplHead =
	'<table width="100%" border="0" cellspacing="0" cellpadding="0">'+
	'    <thead>'+
	'        <tr bgcolor="#fcecec">'+
	'            <td width="10%" class="bor_right" align="center" height="50" style="cursor:pointer;">序号</td>'+
	'            <td width="16%" align="center" class="bor_right">欧赔公司</td>'+
	'            <td width="29%" align="center" class="bor_right">'+
	'                <table width="100%" border="0" cellspacing="0" cellpadding="0">'+
	'                    <tbody>'+
	'                        <tr>'+
	'                            <td height="24" class="bor_bottom" align="center" colspan="3">欧赔</td>'+
	'                        </tr>'+
	'                        <tr>'+
	'                            <td height="25" align="center" width="33%"><b class="red">胜</b></td>'+
	'                            <td align="center" width="34%"><b class="red">平</b></td>'+
	'                            <td align="center" width="33%"><b class="red">负</b></td>'+
	'                        </tr>'+
	'                    </tbody>'+
	'                </table>'+
	'            </td>'+
	'            <td width="29%" align="center" class="bor_right">'+
	'                <table width="100%" border="0" cellspacing="0" cellpadding="0">'+
	'                    <tbody>'+
	'                        <tr>'+
	'                            <td height="24" class="bor_bottom" align="center" colspan="3">凯利指数</td>'+
	'                        </tr>'+
	'                        <tr>'+
	'                            <td height="25" align="center" width="33%"><b class="red">胜</b></td>'+
	'                            <td align="center" width="34%"><b class="red">平</b></td>'+
	'                            <td align="center" width="33%"><b class="red">负</b></td>'+
	'                        </tr>'+
	'                    </tbody>'+
	'                </table>'+
	'            </td>'+
	'            <td align="center">历史赔率</td>'+
	'        </tr>'+
	'    </thead>'+
	'    <tbody class="europeOdds">'+
	'    </tbody>'+
	'</table>';
var fbTpl =
	'{{#odd}}'+
	'<tr class="bor_bottom_2">'+
	'    <td width="10%" align="center" height="36">'+
	'        <label>'+
	'            <div class="checkbox">'+
	'                <span></span>'+
	'                <span class="label">{{index}}</span>'+
	'            </div>'+
	'        </label>'+
	'    </td>'+
	'    <td width="16%" align="left">{{name}}'+
	'		{{#isElite}}'+
	'		<span class="sprites se_icon">主</span>'+
	'		{{/isElite}}'+
	'    </td>'+
	'    <td width="29%" align="center">'+
	'        <table width="100%" class="sl_wrap" border="0" cellspacing="0" cellpadding="0">'+
	'            <tbody>'+
	'                <tr>'+
	'                    <td height="36" align="center" width="33%">'+
	'                        <span class="sprites sj">{{europeOddWinView}}</span>'+
	'                    </td>'+
	'                    <td align="center" width="34%">'+
	'                        <span>{{europeOddFlatView}}</span>'+
	'                    </td>'+
	'                    <td align="center" width="33%">'+
	'                        <span class="sprites xj">{{europeOddNegativeView}}</span>'+
	'                    </td>'+
	'                </tr>'+
	'            </tbody>'+
	'        </table>'+
	'    </td>'+
	'    <td width="29%" align="center">'+
	'        <table width="100%" class="sl_wrap" border="0" cellspacing="0" cellpadding="0">'+
	'            <tbody>'+
	'                <tr>'+
	'                    <td height="36" align="center" width="33%">'+
	'                        <span class="sprites sj">{{kellyOddWinView}}</span>'+
	'                    </td>'+
	'                    <td align="center" width="34%">'+
	'                        <span>{{kellyOddFlatView}}</span>'+
	'                    </td>'+
	'                    <td align="center" width="33%">'+
	'                        <span class="sprites xj">{{kellyOddNegativeView}}</span>'+
	'                    </td>'+
	'                </tr>'+
	'            </tbody>'+
	'        </table>'+
	'    </td>'+
	'    <td align="center" class="sl_wrap">'+
	'        <a href="javascript:void(0)" class="z">主</a>'+
	'        <a href="javascript:void(0)" class="k">客</a>'+
	'        <a href="javascript:void(0)" class="t">同</a>'+
	'    </td>'+
	'</tr>'+
	'{{/odd}}';

var bbTplHead =
	'<table width="100%" border="0" cellspacing="0" cellpadding="0">'+
	'    <thead>'+
	'        <tr bgcolor="#fcecec">'+
	'            <td width="10%" class="bor_right" align="center" height="50" style="cursor:pointer;">序号</td>'+
	'            <td width="16%" align="center" class="bor_right">欧赔公司</td>'+
	'            <td width="20%" align="center" class="bor_right">'+
	'                <table width="100%" border="0" cellspacing="0" cellpadding="0">'+
	'                    <tbody>'+
	'                        <tr>'+
	'                            <td height="24" class="bor_bottom" align="center" colspan="3">欧赔</td>'+
	'                        </tr>'+
	'                        <tr>'+
	'                            <td height="25" align="center" width="33%"><b class="red">主胜</b></td>'+	
	'                            <td align="center" width="33%"><b class="red">客胜</b></td>'+
	'                        </tr>'+
	'                    </tbody>'+
	'                </table>'+
	'            </td>'+
	'            <td width="20%" align="center" class="bor_right">'+
	'                <table width="100%" border="0" cellspacing="0" cellpadding="0">'+
	'                    <tbody>'+
	'                        <tr>'+
	'                            <td height="24" class="bor_bottom" align="center" colspan="3">胜率</td>'+
	'                        </tr>'+
	'                        <tr>'+
	'                            <td height="25" align="center" width="33%"><b class="red">主</b></td>'+
	'                            <td align="center" width="33%"><b class="red">客</b></td>'+
	'                        </tr>'+
	'                    </tbody>'+
	'                </table>'+
	'            </td>'+
	'            <td width="20%" align="center" class="bor_right">'+
	'                <table width="100%" border="0" cellspacing="0" cellpadding="0">'+
	'                    <tbody>'+
	'                        <tr>'+
	'                            <td height="24" class="bor_bottom" align="center" colspan="3">凯利指数</td>'+
	'                        </tr>'+
	'                        <tr>'+
	'                            <td height="25" align="center" width="33%"><b class="red">胜</b></td>'+
	'                            <td align="center" width="33%"><b class="red">负</b></td>'+
	'                        </tr>'+
	'                    </tbody>'+
	'                </table>'+
	'            </td>'+
	'            <td align="center">历史赔率</td>'+
	'        </tr>'+
	'    </thead>'+
	'    <tbody class="europeOdds">'+
	'    </tbody>'+
	'</table>';

var bbTpl =
	'{{#odd}}'+
	'<tr class="bor_bottom_2">'+
	'    <td width="10%" align="center" height="36">'+
	'        <label>'+
	'            <div class="checkbox">'+
	'                <span></span>'+
	'                <span class="label">{{index}}</span>'+
	'            </div>'+
	'        </label>'+
	'    </td>'+
	'    <td width="12%" align="center">{{name}}'+
	'		{{#isElite}}'+
	'		<span class="sprites se_icon">主</span>'+
	'		{{/isElite}}'+
	'    </td>'+
	'    <td width="20%" align="center">'+
	'        <table width="100%" class="sl_wrap" border="0" cellspacing="0" cellpadding="0">'+
	'            <tbody>'+
	'                <tr>'+
	'                    <td height="36" align="center" width="33%">'+
	'                        <span class="sprites sj">{{homeWinOddsView}}</span>'+
	'                    </td>'+
	'                    <td align="center" width="33%">'+
	'                        <span class="sprites xj">{{guestWinOddsView}}</span>'+
	'                    </td>'+
	'                </tr>'+
	'            </tbody>'+
	'        </table>'+
	'    </td>'+
	'    <td width="20%" align="center">'+
	'        <table width="100%" class="sl_wrap" border="0" cellspacing="0" cellpadding="0">'+
	'            <tbody>'+
	'                <tr>'+
	'                    <td height="36" align="center" width="33%">'+
	'                        <span class="sprites sj">{{homeWinPercentView}}</span>'+
	'                    </td>'+
	'                    <td align="center" width="33%">'+
	'                        <span class="sprites xj">{{guestWinPercentView}}</span>'+
	'                    </td>'+
	'                </tr>'+
	'            </tbody>'+
	'        </table>'+
	'    </td>'+
	'    <td width="20%" align="center">'+
	'        <table width="100%" class="sl_wrap" border="0" cellspacing="0" cellpadding="0">'+
	'            <tbody>'+
	'                <tr>'+
	'                    <td height="36" align="center" width="33%">'+
	'                        <span class="sprites sj">{{kellyHomeWinView}}</span>'+
	'                    </td>'+
	'                    <td align="center" width="33%">'+
	'                        <span class="sprites xj">{{kellyGuestWinView}}</span>'+
	'                    </td>'+
	'                </tr>'+
	'            </tbody>'+
	'        </table>'+
	'    </td>'+
	'    <td width="20%" align="center" class="sl_wrap">'+
	'        <a href="javascript:void(0)" class="z">主</a>'+
	'        <a href="javascript:void(0)" class="k">客</a>'+
	'        <a href="javascript:void(0)" class="t">同</a>'+
	'    </td>'+
	'</tr>'+
	'{{/odd}}';



var ajaxEuropeOdds = function() {
	var matchIdStr = getQueryString("matchId");
	var lotteryType = getQueryString("lotteryType");
	var param = {matchId: matchIdStr, lotteryType : lotteryType};
	$.ajax({
		type:"post",
		dataType:"json",
		url : "/ajax_match_europe_odds",
		data:param,
		success : function(result) {
			$(".loading[europe]").hide();
			if(result && result.data) {
				renderFBEruopeOdds(result.data, lotteryType);
			}
		}
	});
};

var renderFBEruopeOdds = function(data, lotteryType) {
	var segment = null;
	var type = new lottery();
	if(type.isZC(lotteryType)) {
		$(".contrast_list").append($(fbTplHead));
		segment = $.mustache(fbTpl, {
			odd:data,
			isElite:function() {
				if(this.eliteId > 0) 
					return true;
				else
					return false;
			},
			europeOddWinView:function (){return oddRound(this.europeOddWin,2);},
			europeOddFlatView:function (){return oddRound(this.europeOddFlat,2);},
			europeOddNegativeView:function (){return oddRound(this.europeOddNegative,2);},
			kellyOddWinView:function (){return oddRound(this.kellyOddWin,2);},
			kellyOddFlatView:function (){return oddRound(this.kellyOddFlat,2);},
			kellyOddNegativeView:function (){return oddRound(this.kellyOddNegative,2);}
		});
	} else if(type.isLC(lotteryType)) {
		$(".contrast_list").append($(bbTplHead));
		segment = $.mustache(bbTpl, {
			odd:data,
			homeWinOddsView:function() {
				return oddRound(this.homeWinOdds,2);
			},
			guestWinOddsView:function() {
				return oddRound(this.guestWinOdds,2);
			},
			homeWinPercentView:function() {
				return oddRound(this.homeWinPer,2);
			},
			guestWinPercentView:function() {
				return oddRound(this.guestWinPer,2);
			},
			kellyHomeWinView:function() {
				return oddRound(this.kellyHomeWin,2);
			},
			kellyGuestWinView:function() {
				return oddRound(this.kellyGuestWin,2);
			},
		});
	}
	$(".europeOdds").html(segment);
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
	ajaxEuropeOdds();
});
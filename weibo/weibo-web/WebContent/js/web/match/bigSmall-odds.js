var tpl =
	'{{#odd}}'+
	'<tr class="bor_bottom_2">'+
	'    <td width="16%" align="center">{{name}}</td>'+
	'    <td width="29%" align="center">'+
	'        <table width="100%" class="sl_wrap" border="0" cellspacing="0" cellpadding="0">'+
	'            <tbody>'+
	'                <tr>'+
	'                    <td height="36" align="center" width="30%">'+
	'                        <span>{{initBigOdds}}</span>'+
	'                    </td>'+
	'                    <td align="center">'+
	'                        <span class="z">{{initHandicap}}</span>'+
	'                    </td>'+
	'                    <td align="center" width="30%">'+
	'                        <span>{{initSmallOdds}}</span>'+
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
	'                        <span>{{realtimeBigOdds}}</span>'+
	'                    </td>'+
	'                    <td align="center">'+
	'                        <span class="z">{{realtimeHandicap}}</span>'+
	'                    </td>'+
	'                    <td align="center" width="30%">'+
	'                        <span>{{realtimeSmallOdds}}</span>'+
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

var ajaxBigSmallOdds = function() {
	var matchIdStr = getQueryString("matchId");
	var lotteryType = getQueryString("lotteryType");
	var param = {matchId: matchIdStr, lotteryType : lotteryType};
	$.ajax({
		type:"post",
		dataType:"json",
		url : "/ajax_match_bigSmall_odds",
		data:param,
		success : function(result) {
			$(".loading[bigsmall]").hide();
			if(result && result.data) {
				renderBigSmallOdds(result.data, lotteryType);
			}
		}
	});
};

var renderBigSmallOdds = function(data, lotteryType) {
	if(data) {
		$(".contrast_list").find(".homeTeamName").html(data.homeTeamName);
		$(".contrast_list").find(".guestTeamName").html(data.guestTeamName);
	}
	var segment = null;
	var type = new lottery();
	if(type.isLC(lotteryType)) {
		segment = $.mustache(tpl, {
			odd:data.bbOddsBigSmallList
		});
	}
	if(segment) {
		$(".bigSmallOdds").html(segment);
	}
};

$(document).ready(function() {
	ajaxBigSmallOdds();
});
/*下面开始鼠标悬浮提示弹出层*/
var oddsOneCompany = [];
$(function() {
	$(".odds-tip").hide();
	$('[_n="odd-tip"]').mouseenter(function() {
		var X = getElementPosition(this).x;
		var Y = getElementPosition(this).y;
		var i = this.parentNode.rowIndex - 1;
		$("#odds-tip-" + i).show().css("top", Y).css("left", X + 400);
	});

	$('[_n="odd-tip"]').click(function() {
		var i = this.parentNode.rowIndex - 1;
		oddsOneCompany = oddJson[i];
		window.open("oneCompanyEuroOdds","", "width=1000,height=480,top=40,left=100,resizable=no,scrollbars=yes");

	});

	$('[_n="odd-tip"]').mouseleave(function() {
		$(".odds-tip").hide();
	});
	//取变化时间最近的前5个时间标红
	var i=0;
	var timeObject = [];
	$('[_n="changeTime"]').each(function(){
		timeObject[i] = this;
		i++;
	});
	timeObject.sort(function(a,b){
		if($(a).text()>= $(b).text())
			return -1;
		else if($(a).text() < $(b).text()){
			return 1;
		}
	});
	for(i=0;i<timeObject.length;i++){
		if(i>=5)
			break;
		$(timeObject[i]).css({"color":"red"});
	}
	//变化最近的前5个时间标红结束

});
function init(oddJson) {
	var message = [];
	var pop = [];
	// console.log(oddJson);
	message
			.push('<table class="europe-odds" cellpadding="0" cellspacing="0" border="0">');
	message
			.push('<tr style="font-weight:bold" height="30px" bgcolor="#FDF4E0"><td width="162" class="br" style="position:relative;">');
	message.push('</td>');
	message.push('<td width="54"><a href="javascript:void(0)">主胜</a></td>');
	message.push('<td width="54"><a href="javascript:void(0)">和</a></td>');
	message
			.push('<td width="54" class="br"><a href="javascript:void(0)">客胜</a></td>');
	message.push('<td width="56"><a href="javascript:void(0)">主胜率</a></td>');
	message.push('<td width="52"><a href="javascript:void(0)">和率</a></td>');
	message.push('<td width="54"><a href="javascript:void(0)">客胜率</a></td>');
	message
			.push('<td width="54" class="br"><a href="javascript:void(0)">返还率</a></td>');
	message.push('<td width="114" class="br" colspan="3">凯利指数</td>');
	message.push('<td width="67" class="br">变化时间</td>');
	message.push('</tr>');
	for (i = 0; i < oddJson.length; i++) {
		var euroOdds = oddJson[i].euroOdds.split("!");
		var kellyIndex = oddJson[i].kellyIndex.split("!");
		var changeTime = oddJson[i].changeTime.split(",");
		rendJishiOdds(euroOdds, kellyIndex, changeTime[0], message,
				oddJson[i].corpName, i);
		rendsEuroOddsHistory(euroOdds, kellyIndex, changeTime, i,
				oddJson[i].corpName);
	}
	message.push('</table>');
	$("#odds-data").append(message.join(''));

}

/**
 * 渲染即时盘
 * 
 * @param euroOdds
 * @param kellyIndex
 * @param changetime
 * @param message
 */
function rendJishiOdds(euroOdds, kellyIndex, changetime, message, corpName, i) {
	var euro = euroOdds[0].split(",");
	var euro1;
	if (euroOdds.length == 2) {
		euro1 = euroOdds[0].split(",");
	} else {
		euro1 = euroOdds[1].split(",");
	}

	var kelly = kellyIndex[0].split(",");
	message.push('<tr height="30px" bgcolor="'
			+ (i % 2 == 0 ? '#F5F5F5' : '#ffffff') + '">');
	message.push('<td width="140px" class="br" >');
	message.push('<a href="javascript:void(0);" class="odds-pop">' + corpName
			+ '</a>');
	message.push('	<i class="down-arrow"></i>');
	message.push('</td>');
	message.push('<td width="54" _n="odd-tip">');
	message.push('	<a href="javascript:void(0)" class="'
			+ (euro[0] > euro1[0] ? 'color-red' : (euro[0] == euro1[0] ? ''
					: 'color-green')) + '">' + euro[0] + '</a>');
	message.push('</td>');
	message.push('<td width="54" _n="odd-tip">');
	message.push('	<a href="javascript:void(0)" class="'
			+ (euro[1] > euro1[1] ? 'color-red' : (euro[1] == euro1[1] ? ''
					: 'color-green')) + '">' + euro[1] + '</a>');
	message.push('</td>');
	message.push('<td width="54" class="br" _n="odd-tip">');
	message.push('	<a href="javascript:void(0)" class="'
			+ (euro[2] > euro1[2] ? 'color-red' : (euro[2] == euro1[2] ? ''
					: 'color-green')) + '">' + euro[2] + '</a>');
	message.push('</td>');
	message.push('	<td width="56">');
	message.push('<a href="javascript:void(0)">'
			+ (1 * 1.0 / euro[0] * euro[3]).toFixed(2) + '</a>');
	message.push('</td>');
	message.push('<td width="52">');
	message.push('	<a href="javascript:void(0)">'
			+ (1 * 1.0 / euro[1] * euro[3]).toFixed(2) + '</a>');
	message.push('</td>');
	message.push('<td width="54">');
	message.push('	<a href="javascript:void(0)">'
			+ (1 * 1.0 / euro[2] * euro[3]).toFixed(2) + '</a>');
	message.push('</td>');
	message.push('<td width="54" class="br">');
	message.push('	<a href="javascript:void(0)">' + euro[3] + '</a>');
	message.push('</td>');
	message.push('<td>' + kelly[0] + '</td>');
	message.push('<td>' + kelly[1] + '</td>');
	message.push('<td class="br">' + kelly[2] + '</td>');
	message.push('<td width="67" class="br" _n="changeTime">' + (formatDateString(changetime))
			+ '</td>');
	message.push('</tr>');
}

/**
 * 
 * @param euroOdds
 * @param kellyIndex
 *            凯利指数
 * @param changeTime
 *            变化时间
 * @param j
 *            第几行
 */
function rendsEuroOddsHistory(euroOdds, kellyIndex, changeTime, i, corpName) {

	var data = [];
	data
			.push('<div class="odds-tip" id="odds-tip-'
					+ i
					+ '"><table class="odds-tip-table" cellpadding="0" cellspacing="0" border="0">');
	data.push('<tr height="30px" bgcolor="#FFEFC4"><th colspan="4">' + corpName
			+ '指数变化</th></tr>');
	for (i = 0; i < euroOdds.length - 1; i++) {
		var euro = euroOdds[i].split(',');
		var euro1;
		if (i == euroOdds.length - 2) {
			euro1 = euroOdds[i].split(',');
		} else {
			euro1 = euroOdds[i + 1].split(',');
		}

		data.push('<tr height="28px" >');
		data
				.push('<td><a href="javascript:void(0)" class="'
						+ (euro[0] > euro1[0] ? 'color-red'
								: (euro[0] == euro1[0] ? 'color-black'
										: 'color-green')) + '">' + euro[0]
						+ '</a></td>');
		data
				.push('<td><a href="javascript:void(0)" class="'
						+ (euro[1] > euro1[1] ? 'color-red'
								: (euro[1] == euro1[1] ? 'color-black'
										: 'color-green')) + '">' + euro[1]
						+ '</a></td>');
		data
				.push('<td><a href="javascript:void(0)" class="'
						+ (euro[2] > euro1[2] ? 'color-red'
								: (euro[2] == euro1[2] ? 'color-black'
										: 'color-green')) + '">' + euro[2]
						+ '</a></td>');
		data.push('<td>' + (formatDateString(changeTime[i])) + (i==(euroOdds.length-2)?'<font color="blue">(初盘)</font>':'')+'</td>');
		data.push(' </tr>');
		if (i >= 6) {
			break;
		}
	}

	if (euroOdds.length > 8) {
		data.push('<tr height="28px" >')
		data.push('<td colspan="4">点击公司名称查看更多变化</td>');
		data.push(' </tr>');
		// 初盘
		var initOdds = euroOdds[euroOdds.length - 2].split(',');
		data.push('<tr height="28px" >');

		data.push('<td><a href="javascript:void(0)" class="color-black">'
				+ initOdds[0] + '</a></td>');
		data.push('<td><a href="javascript:void(0)" class="color-black">'
				+ initOdds[1] + '</a></td>');
		data.push('<td><a href="javascript:void(0)" class="color-black">'
				+ initOdds[2] + '</a></td>');
		data.push('<td>'
				+ (formatDateString(changeTime[changeTime.length - 2]))
				+ '<font color="blue">(初盘)</font></td>');
		data.push(' </tr>');
	}
	data.push('</table>');
	data.push('</div>');
	$("#odds-data").append(data.join(''));
}
function getElementPosition(el) {
	var obj = el, offset = new Object(), x = 0, y = 0;
	while (obj && obj.tagName != "BODY") {
		x += obj.offsetLeft;
		y += obj.offsetTop;
		obj = obj.offsetParent;
	}
	offset.x = x + 65;// 这里可以加减
	offset.y = y + 10;
	return offset;
}
function formatDateString(timeStr) {
	return timeStr.substring(4, 6) + '-' + timeStr.substring(6, 8) + ' '
			+ timeStr.substring(8, 10) + ':' + timeStr.substring(10, 12)
}

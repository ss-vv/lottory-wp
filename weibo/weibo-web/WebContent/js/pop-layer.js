$(function() {
	$("body").append(upperlower);
	$(".dialog_close").live("click", function() {
		$(".upper-lower").hide();
		$("#all").hide();
	});
});

// 下面开始upper-lower部分
function detailClick(id, obj) {
	var p = obj.parentNode;
	var Y = getElementPosition(p).y;
	var X = getElementPosition(p).x;
	var d = $("#" + id).clone();
	var w = document.body.clientWidth;
	var h = document.body.clientHeight;
	var sh = document.body.scrollTop;
	w = w / 2;
	h = h / 2;
	var upperh = $(".upper-lower").height();
	var nextdiv = $(".upper-lower > div[id]");
	if (nextdiv) {
		nextdiv.remove();
	}
	d.insertBefore(".upper-lower-bottom");
	$(d).show();
	var uh = $(".upper-lower").height();
	var uw = $(".upper-lower").width();
	$(".upper-lower").fadeIn();
	$("#all").show();
}

function getElementPosition(el) {
	var obj = el, offset = new Object(), x = 0, y = 0;
	while (obj && obj.tagName != "BODY") {
		x += obj.offsetLeft;
		y += obj.offsetTop;
		obj = obj.offsetParent;
	}
	offset.x = x;// 这里可以加减
	offset.y = y;
	return offset;
}
/* 上面结束upper-lower部分 */

// -------------下面开始upper-lower部分-----------------------
var upperlower = '<div class="upper-lower">'
		+ '<div class="upper-lower-bottom"></div>' + '</div>';
// ----------upper-lower部分结束------------
// 下面一段是注册登录部分的，获得焦点文本切换颜色部分
$(document).ready(function() {
	$("#username").focus(function() {
		$("#username").css("color", "#000");
	});
	$("#username").blur(function() {
		$("#username").css("color", "#D0D0D0");
	});
});

$(document).ready(function() {
	$("#password").focus(function() {
		$("#password").css("color", "#000");
	});
	$("#password").blur(function() {
		$("#password").css("color", "#D0D0D0");
	});
});
// 下面开始问号提示层
var jczqid = [ "jczq-welcome-question_1", "jczq-welcome-question_2","jczq-welcome-question_3" ];
var jczq = [ "jczq-poptip_1", "jczq-poptip_2", "jczq-poptip_3" ];
var jclqid = [ "jclq-welcome-question_1", "jclq-welcome-question_2","jclq-welcome-question_3" ];
var jclq = [ "jclq-poptip_1", "jclq-poptip_2", "jclq-poptip_3" ];
var bjdcid = [ "bjdc-welcome-question_1", "bjdc-welcome-question_2","bjdc-welcome-question_3" ];
var bjdc = [ "bjdc-poptip_1", "bjdc-poptip_2", "bjdc-poptip_3" ];
var allids = [ jczqid, jclqid, bjdcid ];
var allball = [ jczq, jclq, bjdc ];

$(document).ready(function() {
	for (var i = 0; i < allids.length; i++) {
		for (var k = 0; k < allids[i].length; k++) {
			;(function() {
				var index = k;
				var id = allids[i][k];
				var tid = allball[i][k];
				addEvent(id, tid);
			})();
		}
	}
	function addEvent(id, tid) {
		$("#" + id).mouseover(function() {
			$("#" + tid).show();
		}).mouseout(function() {
			$("#" + tid).hide();
		});
	}
});
// 上面结束问号提示层

$(document).ready(function() {
	$(".welcome-immediately-bet").click(function() {
		$("#pop-outer input[name='referer']").val(
				"http://www.davcai.com/index");
		isLogin();
	});
	$("#charge").click(function() {
		isLogin();
		$("#pop-outer input[name='referer']").val(
				"http://trade.davcai.com/ac/recharge.do");
	});
	// 主页记住我判断
	$("#checkselect").click(function() {
		$(this).toggleClass("welcome-checkbox-not-selected");
		var check = $("#remember_me").attr("checked");
		if (check == "checked") {
			$("#remember_me").attr("checked", false);
		} else {
			$("#remember_me").attr("checked", true);
		}
	});
	var fbid_1 = [ "fbsheng_1", "fbping_1", "fbfu_1" ];
	var fbid_2 = [ "fbsheng_2", "fbping_2", "fbfu_2" ];
	var fbid_3 = [ "fbsheng_3", "fbping_3", "fbfu_3" ];
	var fbid = [ fbid_1, fbid_2, fbid_3 ];
	var bbid_1 = [ "bbsheng_1", "bbfu_1" ];
	var bbid_2 = [ "bbsheng_2", "bbfu_2" ];
	var bbid_3 = [ "bbsheng_3", "bbfu_3" ];
	var bbid = [ bbid_1, bbid_2, bbid_3 ];
	var bdid_1 = [ "bdsheng_1", "bdping_1", "bdfu_1" ];
	var bdid_2 = [ "bdsheng_2", "bdping_2", "bdfu_2" ];
	var bdid_3 = [ "bdsheng_3", "bdping_3", "bdfu_3" ];
	var bdid = [ bdid_1, bdid_2, bdid_3 ];
	var fbminus_1 = [ "fbminus_1", "fbminus_2", "fbminus_3" ];
	var fbplus_1 = [ "fbplus_1", "fbplus_2", "fbplus_3" ];
	var bbminus_1 = [ "bbminus_1", "bbminus_2", "bbminus_3" ];
	var bbplus_1 = [ "bbplus_1", "bbplus_2", "bbplus_3" ];
	var bdminus_1 = [ "bdminus_1", "bdminus_2", "bdminus_3" ];
	var bdplus_1 = [ "bdplus_1", "bdplus_2", "bdplus_3" ];
	// 竞彩足球
	for (var k = 0; k < fbid.length; k++) {
		for (var i = 0; i < fbid[k].length; i++) {
			(function() {
				var t = fbid[k][i];
				var m = fbid[k];
				var q = k;
				$("#" + t).click(function() {
					compute(this, m, q + 1);
				});
			})();
		}
	}
	// 篮球
	for (var k = 0; k < bbid.length; k++) {
		for (var i = 0; i < bbid[k].length; i++) {
			(function() {
				var t = bbid[k][i];
				var m = bbid[k];
				var q = k;
				$("#" + t).click(function() {
					compute(this, m, q + 1);
				});
			})();

		}

	}
	// 北京单场
	for (var k = 0; k < bdid.length; k++) {
		for (var i = 0; i < bdid[k].length; i++) {
			(function() {
				var t = bdid[k][i];
				var m = bdid[k];
				var q = k;
				$("#" + t).click(function() {
					compute(this, m, q + 1);
				});
			})();
		}
	}
	// 足球
	for (var i = 0; i < fbminus_1.length; i++) {
		(function() {
			var t = fbminus_1[i];
			var num = t.substring(t.indexOf("_") + 1);
			var arr = "";
			if (num) {
				if (num == 1) {
					arr = fbid_1;
				} else if (num == 2) {
					arr = fbid_2;
				} else if (num == 3) {
					arr = fbid_3;
				}
			}
			$("#" + t).click(function() {
				minus(num, arr, "fbmoney_" + num, "fbprize_" + num);
			});
		})();
	}
	for (var i = 0; i < fbplus_1.length; i++) {
		(function() {
			var t = fbplus_1[i];
			var num = t.substring(t.indexOf("_") + 1);
			var arr = "";
			if (num) {
				if (num == 1) {
					arr = fbid_1;
				} else if (num == 2) {
					arr = fbid_2;
				} else if (num == 3) {
					arr = fbid_3;
				}
			}
			$("#" + t).click(function() {
				plus(num, arr, "fbmoney_" + num, "fbprize_" + num);
			});
		})();
	}
	// 篮球
	for (var i = 0; i < bbminus_1.length; i++) {
		(function() {
			var t = bbminus_1[i];
			var num = t.substring(t.indexOf("_") + 1);
			var arr = "";
			if (num) {
				if (num == 1) {
					arr = bbid_1;
				} else if (num == 2) {
					arr = bbid_2;
				} else if (num == 3) {
					arr = bbid_3;
				}
			}
			$("#" + t).click(function() {
				bbminus(num, arr);
			});
		})();
	}
	for (var i = 0; i < bbplus_1.length; i++) {
		(function() {
			var t = bbplus_1[i];
			var num = t.substring(t.indexOf("_") + 1);
			var arr = "";
			if (num) {
				if (num == 1) {
					arr = bbid_1;
				} else if (num == 2) {
					arr = bbid_2;
				} else if (num == 3) {
					arr = bbid_3;
				}
			}
			$("#" + t).click(function() {
				bbplus(num, arr);
			});
		})();
	}
	// 北京单场
	for (var i = 0; i < bdminus_1.length; i++) {
		(function() {
			var t = bdminus_1[i];
			var num = t.substring(t.indexOf("_") + 1);
			var arr = "";
			if (num) {
				if (num == 1) {
					arr = bdid_1;
				} else if (num == 2) {
					arr = bdid_2;
				} else if (num == 3) {
					arr = bdid_3;
				}

			}
			$("#" + t).click(function() {
				minus(num, arr, "bdmoney_" + num, "bdprize_" + num);
			});
		})();
	}
	for (var i = 0; i < bdplus_1.length; i++) {
		(function() {
			var t = bdplus_1[i];
			var num = t.substring(t.indexOf("_") + 1);
			var arr = "";
			if (num) {
				if (num == 1) {
					arr = bdid_1;
				} else if (num == 2) {
					arr = bdid_2;
				} else if (num == 3) {
					arr = bdid_3;
				}

			}
			$("#" + t).click(function() {
				plus(num, arr, "bdmoney_" + num, "bdprize_" + num);
			});
		})();
	}
	function minus(t, tmp_arr, money_t, prize_t) {
		var num = $("#" + money_t).attr("fbchoicenum");
		if (num <= 0) {
		} else {
			var arr = [];
			getOdds(arr, tmp_arr);
			var l = arr.length;
			if (l == 1) {
				var oldV = $("#" + money_t).val();
				var add = l * 2;
				if (oldV - add >= l * 2) {

					$("#" + money_t).val(oldV - add);
					var mon = (oldV - add) / l;
					var prize = mon * arr[0];
					if (money_t.indexOf("bd") != -1) {
						prize *= 0.65;
					}
					prize = formatprize(prize);
					$("#" + prize_t).html(prize);
				}
			} else if (l == 2) {
				var oldV = $("#" + money_t).val();
				var add = l * 2;
				if (oldV - add >= l * 2) {
					$("#" + money_t).val(oldV - add);
					var mon = (oldV - add) / l;
					var odd = Math.max(arr[0], arr[1]);
					var prize = mon * odd;
					if (money_t.indexOf("bd") != -1) {
						prize *= 0.65;
					}
					prize = formatprize(prize);
					$("#" + prize_t).html(prize);
				}
			} else if (l == 3) {
				var oldV = $("#" + money_t).val();
				var add = l * 2;
				if (oldV - add >= l * 2) {

					$("#" + money_t).val(oldV - add);
					var mon = (oldV - add) / l;
					var odd = Math.max(arr[0], arr[1], arr[2]);
					var prize = mon * odd;
					if (money_t.indexOf("bd") != -1) {
						prize *= 0.65;
					}
					prize = formatprize(prize);
					$("#" + prize_t).html(prize);
				}
			}
			$("#" + money_t).attr("fbchoicenum", l);
		}
	}
	function plus(t, tmp_arr, money_t, prize_t) {
		var num = $("#" + money_t).attr("fbchoicenum");
		if (num <= 0) {
			alert("请选择一个比赛结果");
		} else {
			var arr = [];
			getOdds(arr, tmp_arr);
			var l = arr.length;
			if (l == 1) {
				var oldV = $("#" + money_t).val();
				var add = l * 2;
				$("#" + money_t).val(add + parseInt(oldV));
				var mon = (add + parseInt(oldV)) / l;
				var prize = mon * arr[0];
				if (money_t.indexOf("bd") != -1) {
					prize *= 0.65;
				}
				prize = formatprize(prize);
				$("#" + prize_t).html(prize);
			} else if (l == 2) {
				var oldV = $("#" + money_t).val();
				var add = l * 2;
				$("#" + money_t).val(add + parseInt(oldV));
				var mon = (add + parseInt(oldV)) / l;
				var odd = Math.max(arr[0], arr[1]);
				var prize = mon * odd;
				if (money_t.indexOf("bd") != -1) {
					prize *= 0.65;
				}
				prize = formatprize(prize);
				$("#" + prize_t).html(prize);
			} else if (l == 3) {

				var oldV = $("#" + money_t).val();
				var add = l * 2;
				$("#" + money_t).val(add + parseInt(oldV));
				var mon = (add + parseInt(oldV)) / l;
				var odd = Math.max(arr[0], arr[1], arr[2]);
				var prize = mon * odd;
				if (money_t.indexOf("bd") != -1) {
					prize *= 0.65;
				}
				prize = formatprize(prize);
				$("#" + prize_t).html(prize);
			}
			$("#" + money_t).attr("fbchoicenum", l);
		}
	}
	// 篮球
	function bbminus(t, tmp_arr) {

		var num = $("#bbmoney_" + t).attr("bbchoicenum");
		if (num <= 0) {
		} else {
			var arr = [];
			getOdds(arr, tmp_arr);
			var l = arr.length;
			if (l == 1) {
				var oldV = $("#bbmoney_" + t).val();
				var add = l * 2;
				if (oldV - add >= l * 2) {
					$("#bbmoney_" + t).val(oldV - add);
					var mon = (oldV - add) / l;
					var prize = mon * arr[0];
					prize = formatprize(prize);
					$("#bbprize_" + t).html(prize);
				}

			} else if (l == 2) {
				var oldV = $("#bbmoney_" + t).val();
				var add = l * 2;
				if (oldV - add >= l * 2) {

					$("#bbmoney_" + t).val(oldV - add);
					var mon = (oldV - add) / l;
					var odd = Math.max(arr[0], arr[1]);
					var prize = mon * odd;
					prize = formatprize(prize);
					$("#bbprize_" + t).html(prize);
				}
			}
			$("#bbmoney_" + t).attr("bbchoicenum", l);
		}
	}
	function bbplus(t, tmp_arr) {

		var num = $("#bbmoney_" + t).attr("bbchoicenum");
		if (num <= 0) {
			alert("请选择一个比赛结果");
		} else {
			var arr = [];
			getOdds(arr, tmp_arr);
			var l = arr.length;
			if (l == 1) {

				var oldV = $("#bbmoney_" + t).val();
				var add = l * 2;
				$("#bbmoney_" + t).val(add + parseInt(oldV));
				var mon = (add + parseInt(oldV)) / l;
				var prize = mon * arr[0];
				prize = formatprize(prize);
				$("#bbprize_" + t).html(prize);

			} else if (l == 2) {
				var oldV = $("#bbmoney_" + t).val();
				var add = l * 2;
				$("#bbmoney_" + t).val(add + parseInt(oldV));
				var mon = (add + parseInt(oldV)) / l;
				var odd = Math.max(arr[0], arr[1]);
				var prize = mon * odd;
				prize = formatprize(prize);
				$("#bbprize_" + t).html(prize);

			}
			$("#bbmoney_" + t).attr("bbchoicenum", l);
		}
	}
});
function compute(obj, arr, index) {
	$(obj).toggleClass("actice");
	var ids = arr;
	var odds = [];
	getOdds(odds, ids);
	if (arr[0] == "fbsheng_1" || arr[0] == "fbsheng_2" || arr[0] == "fbsheng_3") {
		fillData(odds, "fbmoney_" + index, "fbprize_" + index, "fbchoicenum");
	} else if (arr[0] == "bbsheng_1" || arr[0] == "bbsheng_2"
			|| arr[0] == "bbsheng_3") {
		fillData(odds, "bbmoney_" + index, "bbprize_" + index, "bbchoicenum");
	} else if (arr[0] == "bdsheng_1" || arr[0] == "bdsheng_2"
			|| arr[0] == "bdsheng_3") {
		fillData(odds, "bdmoney_" + index, "bdprize_" + index, "bdchoicenum");
	}
}
function getOdds(oddarr, idsarr) {
	for (var i = 0; i < idsarr.length; i++) {
		var tname = $("#" + idsarr[i]).attr("class");
		if ($.trim(tname) == "actice") {
			oddarr[oddarr.length] = $("#" + idsarr[i]).attr("odd");
		}
	}
}
// 计算奖金保留2位小数
function formatprize(prize) {
	if (prize) {
		prize += "";
		var l = prize.substring(prize.indexOf(".") + 1);
		if (l <= 2) {
			return prize;
		} else {
			var h = prize.substring(0, prize.indexOf(".") + 1);
			var t = prize.substr(prize.indexOf(".") + 1, 2);
			return h + "" + t;
		}
	}
}
function fillData(arr, moneyid, prizeid, choicenum) {
	var odds = arr;
	var prize = 0;
	var choice = 0;
	if (odds.length == 1) {
		$("#" + moneyid).val(2);
		prize = 2 * odds[0];
		choice = 1;
	} else if (odds.length == 2) {
		$("#" + moneyid).val(4);
		prize = 2 * Math.max(odds[0], odds[1]);
		choice = 2;
	} else if (odds.length == 3) {

		$("#" + moneyid).val(6);
		prize = 2 * Math.max(odds[0], odds[1], odds[2]);
		choice = 3;
	} else {
		$("#" + moneyid).val(2);
	}
	if (prize != 0) {
		if (moneyid.indexOf("bd") != -1) {
			prize = prize * 0.65;
		}
		prize = formatprize(prize);
	}
	$("#" + prizeid).html(prize + "元");
	$("#" + moneyid).attr(choicenum, choice);
}
function displayLogin() {
	$("#pop-outer").fadeIn(800);
	$("#all").show();
}
// 微博详情页使用
function isLogin() {
	// ajax请求 判断登录状态，并设置相应的头部
	$.getJSON('http://login.davcai.com/loadmsg.do?jsonp=?', function(json) {
		if (json.success && json.data.id > 0) {
			// 获取微博信息,并填充至html片段
			$.getJSON('http://login.davcai.com/ajax_get_weibouser.do?jsonp=?',
					function(data) {
						if (data == null) {
							displayLogin();
						}
					}, 'json');
		} else {
			displayLogin();
		}
	});
}
function login_() {
	var username = $.trim($("#username_").val());
	var password = $.trim($("#password_").val());
	var rememberMe = $("#remember_me_").attr("checked");
	if (username == null || username == "") {
		alert("请输入用户名");
		$("#username_").focus();
		return false;
	}
	if (password == null || password == "") {
		alert("请输入密码");
		$("#password_").focus();
		return false;
	}
	if (rememberMe) {
		$.cookie('username', username, {
			expires : 30
		});
	} else {
		$.cookie('username', null);
	}
	$("#loginForm_").attr("action", "http://login.davcai.com/login.do");
	$("#loginForm_").attr("method", "post");
	$("#loginForm_").submit();
}
function alipayLogin() {
	location.href = "/alipayLogin.do?referer=http://www.davcai.com/index";
}
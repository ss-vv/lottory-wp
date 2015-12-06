//登陆页面 获取投注人数
$(function() {
	// 定时获取投注人数
	//setInterval("changeNum()", 10000);
	changeNum();
})
function changeNum() {
	$.post("betnum.do", function(data) {
		$.each(data, function(index, obj) {
			var num = $("#" + obj["lotteryName"] + "_num").html();
			if (num != obj["betNum"] && obj["betNum"]!=null) {
				$("#" + obj["lotteryName"] + "_n").fadeOut(500).fadeIn(500);
				$("#" + obj["lotteryName"] + "_num").html(
						obj["betNum"] == null ? 0 : obj["betNum"]);
			}
		})

	}, "json")
}
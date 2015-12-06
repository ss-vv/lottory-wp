/**
 * 彩票方案数据
 * @author lei.li@unison.net.cn
 * @Date 2013-01-17
 */
var scheme = {
	pageSize:0,
	pageNo:0,
	totalCount:0,
	pageCount:0,
	nextPageNo:0,
	aj:[],
	//热门晒单推荐
	loadHotSchemeRecommend:function(url, elt) {
		$.getJSON(url, function(paging){
			if(paging){
				scheme.pageSize = paging.maxResults;
				scheme.pageNo = paging.pageNo;
				scheme.totalCount = paging.totalCount;
				scheme.pageCount = paging.pageCount;
				scheme.nextPageNo = paging.nextPageNo;
				if(0 == scheme.totalCount) {
					$(".schemeRow").remove();
					var li = ["<li class='schemeRow'></li>", "<li class='schemeRow'></li>", "<li class='schemeRow'></li>"];
					var row = $(li.join(""));
					$("#"+elt).append(row);
				}
				$.each(paging.results, function(i, obj){
					var scheme = obj.scheme;
					var userScore = obj.userScore;
					var schemeId = scheme.id;
					var sponsorId = scheme.sponsorId;
					var li = [];
					//用户
					var oss = '<a href="http://trade.davcai.com/df/follow/oss.do?uid=' + sponsorId + '">';
					//战绩
					var ws = '<a href="http://trade.davcai.com/df/follow/ws.do?uid=' + sponsorId + '">';
					var zj = [];
					//过关
					var lotteryIcon = [];
					//人气
					var followingCount = scheme.followingCount;
					if(followingCount >= 10) {
						followingCount = '<div class="follow_count">' + followingCount + '</div>';
					}
					//用户跟单操作
					var followOpt = [];
					followOpt.push('<a href="http://trade.davcai.com/ac/follow.do?id=' + schemeId + '" target="_blank" style="margin-right:0px;">跟单</a><em>');
					followOpt.push('</em><a href="http://trade.davcai.com/ac/cm.do?fuid=' + sponsorId + '" target="_blank" style="margin-right:0px;">定制</a>');
					
					if(userScore && userScore.showPic) {
						var showPic = userScore.showPic;
						var picArr = showPic.split("#");
						for(var i in picArr) {
							if(picArr[i].length > 0) {
								zj.push('<img src="http://trade.davcai.com/df/images/score/' + picArr[i] + '" style="width:18px;height:18px;" alt＝战绩_' + picArr[i] + '/>');
							}
						}
					} else {
						zj.push("&nbsp;");
					}
					
					if(scheme.lotteryId == "JCLQ") {
						lotteryIcon.push('<img src="http://trade.davcai.com/df/images/basketball.png" width="18" alt="竞彩篮球_" + scheme.id></img>');
					} else if(scheme.lotteryId = "JCZQ") {
						lotteryIcon.push('<img src="http://trade.davcai.com/df/images/soccerball.png" alt="竞彩足球_" + scheme.id></img>');
					}
					
					li.push("<li style='margin-top:0px' class='schemeRow'>");
					li.push('<span class="width80">' + oss + scheme.sponsor + "</a></span>");
					li.push('<span class="zj">' + ws + zj.join("") + "</a></span>");
					li.push('<span class="width100">' + lotteryIcon.join("") + scheme.play.name +  "</span>");
					li.push('<span class="width80">' + scheme.totalAmount + "元</span>");
					var offtime = scheme.offtime;
					offtime = offtime.substr(0, offtime.lastIndexOf(":"));
					offtime = offtime.replace(/T/g, " ");
					li.push('<span  class="width100">' + offtime + "</span>");
					li.push("<span>" + scheme.maxBonusReturnRatio + "倍</span>");
					var followedRatio = scheme.followedRatio;
					if(0 == followedRatio) {
						followedRatio = "无";
					}
					li.push("<span>" + followedRatio + "</span>");
					li.push("<span>" + followingCount + "</span>");
					li.push('<span  class="width80">' + scheme.followTotalAmount + "元</span>");
					li.push('<span class="width80">' + followOpt.join("|") + "</span>");
					li.push("</li>");
					
					var row = $(li.join(""));
					$("#"+elt).append(row);
				});
			}
		});
	}
};
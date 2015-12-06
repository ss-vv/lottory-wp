(function($) {
	$.fn.extend({
		match$ToHtml: function (option){
			var msgObj = $(this);
			var contents = $(".summary",msgObj);
			contents.each(function (index,element){
				var rContent = $(this).html();
				var regx = /\$.*?\(([a-zA-Z]+)\d+\)\$/g;
				//正则查找“$竞彩足球 周二003 横滨水手 VS 全北现代(JZ1404152003)$”格式
				var rs = rContent.match(regx);
				if (rs) {
					for (var i = 0; i < rs.length; i++) {
						var index = rs[i].indexOf("(JZ");
						if (-1 != index) {
							var end = rs[i].indexOf(")");
							var matchId = rs[i].substring(index + 3, end);
							var t = '<a href="http://www.davcai.com/matches/JCZQ/20'
									+ matchId
									+ '" target="_blank"  _matchFloatCardType="JCZQ" _matchId="20' + matchId + '">'
									+ rs[i] + '</a>';
							rContent = rContent.replace(rs[i], t);
							rContent = rContent.replace("(JZ" + matchId + ")","");
							continue;
						}
						
						index = rs[i].indexOf("(JL");
						if (-1 != index) {
							var matchId = rs[i].substring(index + 3, rs[i].indexOf(")"));
							var t = '<a href="http://www.davcai.com/matches/JCLQ/20'
									+ matchId
									+ '" target="_blank" _matchFloatCardType="JCLQ" _matchId="20' + matchId + '">'
									+ rs[i] + '</a>';
							rContent = rContent.replace(rs[i], t);
							rContent = rContent.replace("(JL" + matchId + ")","");
							continue;
						}
						
						index = rs[i].indexOf("(BD");
						if (-1 != index) {
							var matchId = rs[i].substring(index + 3, rs[i].indexOf(")"));
							var t = '<a href="http://www.davcai.com/matches/BJDC/'
								+ matchId
								+ '" target="_blank" _matchFloatCardType="BJDC" _matchId="' + matchId + '">'
								+ rs[i] + '</a>';
							rContent = rContent.replace(rs[i], t);
							rContent = rContent.replace("(BD" + matchId + ")","");
							continue;
						}
						
						index = rs[i].indexOf("(CZ");
						if (-1 != index) {
							var matchId = rs[i].substring(index + 3, rs[i].indexOf(")"));
							var id = matchId.substring(0,5);
							var mid = matchId.substring(5,6);
							var end = matchId.substring(6);
							var s;
							if(mid == '0'){
								s = '24_ZC_14' + end;
							} else {
								s = '24_ZC_14' + mid  + end;
							}
							s = id + s;
							var t = '<a href="http://www.davcai.com/matches/CTZC/'
									+ s
									+ '" target="_blank"  _matchFloatCardType="CTZC"  _matchId="' + s + '">'
									+ rs[i] + '</a>';
							rContent = rContent.replace(rs[i], t);
							rContent = rContent.replace("(CZ" + matchId + ")","");
							continue;
						}
					}
				}
				$(this).html(rContent);
			});
		}
	});
})(jQuery);
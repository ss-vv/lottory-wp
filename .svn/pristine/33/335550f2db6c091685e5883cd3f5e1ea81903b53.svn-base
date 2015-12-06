;(function($){
    $.extend({
        /**
         * 竞彩投注选项视图
         */
        getJCOptionView: function(option, playType,defaultScore,defaultScore_09LC2) {
            var spf = ['负','平','','胜'];
        	if(playType == "80ZC2"){
            	return spf[option];
            } else if(playType == "01ZC2"){
            	return spf[option] + ((defaultScore > 0) ? ("+"+defaultScore):(defaultScore));
            } else if(playType == "02ZC2"){
            	var a = option.substring(0,1);
				var b = option.substring(1);
				if(a > 8 && b > 8){
					return "平其他";
				}
				if(b > 8){
					return "负其他";
				}
				if(a > 8){
					return "胜其他";
				}
				return a+"-"+b;
            } else if(playType == "03ZC2"){
            	return option >=7 ? "7+球":option+"球";
            } else if(playType == "04ZC2"){
            	var a = option.substring(0,1);
				var b = option.substring(1);
				return spf[a]+"/"+spf[b];
            } else if(playType == "06LC2"){
            	var options = ["","主胜","客胜"];
            	return (defaultScore > 0 ? '+'+defaultScore:defaultScore) +options[parseInt(option)];
            } else if(playType == "07LC2"){
            	var options = ["","主胜","客胜"];
            	return options[option];
            } else if(playType == "08LC2"){
            	var options = ["主胜","客胜"];
            	var options2 = ["","1-5","6-10","11-15","16-20","21-25","26+"];
            	var o = option.split("");
            	return options[o[0]] + options2[o[1]];
            } else if(playType == "09LC2"){
            	var options = ["","大","小"];
            	return options[option]+defaultScore_09LC2;
            }
            return '';
        }
    });
})(jQuery);
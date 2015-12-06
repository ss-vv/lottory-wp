;(function($) {
	var statusTool = function(element, options) {
	}
	statusTool.prototype = {
		status:function(status) {
			switch(status) {
            case 0:
            case 1:
            case 2:
                return "未出票";
            case 5100:
                return "出票成功";
            case 5101:
                return "出票失败";
            case 5202:
                return "未中奖";
            case 5203:
                return "中奖未派奖";
            case 12:
                return "已派奖";
            case 99:
                return "流标";
            case 100:
                return "撤单";
            default:
                return "未知状态";
			}
		},
		buyType:function(type) {
			switch (type) {
			case 0:
				type = "代购";
				break;
			case 1:
				type = "合买";
				break;
			case 2:
				type = "跟单";
				break;
			default:
                type = "未知类型";
			}
			return type;
		},
		detailScheme:function(type, showScheme, lotteryId, schemeId) {
			if(type == 1) {
				return 'http://trade.davcai.com/ac/groupbuy.do?id=' + schemeId;
			} else if(showScheme == 1) {
				return 'http://trade.davcai.com/ac/follow.do?id=' + schemeId;
			} else if(type == 0 && showScheme == 0 && (
				lotteryId == 'JCZQ' || lotteryId == 'JCLQ' ||
				lotteryId == 'CTZC' || lotteryId == 'SSQ' ||
				lotteryId == 'BJDC')) {
				return 'http://trade.davcai.com/ac/betdetail.do?id=' + schemeId;
			} else if(type == 2 && showScheme == 2) {
				return 'http://trade.davcai.com/ac/groupbuy.do?id=' + schemeId;
			} else if(type == 2 && showScheme == 0) {
				return 'http://trade.davcai.com/ac/betdetail.do?id=' + schemeId;
			}
		}
	};
	
	$.extend({
		statusTool:function (option) {
			return new statusTool(this, option);
		}
	});
})(window.jQuery);
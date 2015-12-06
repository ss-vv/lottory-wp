var opt = {
	
};

/**
   * 足球赛果
    	 */
function loadZC(pageNo) {
    var from = $("input[name=from]").val();
    var to = $("input[name=to]").val();
    
    $.getJSON("http://trade.davcai.com/df/aj_fbresult.do?jsonp=?", {'pageNo': pageNo, 'from': from, 'to': to}, function(ret){
		var tab = $('#dataTab');
		$("#dataTab tr").slice(1).remove();
		$("[v=_c]").html(ret.totalCount);
		var arr = [];
		$.each(ret.results, function(i, n) {
			var plays = n['plays'];
			if(plays != null && typeof(plays) != 'undefined'){
				var color = (n.color=='' || n.color==null)?'#393':n.color;
	    	    var html = '<tr>';
	    	    html += '<td class="tdl">' + n.playingTime + '</div></td>';
	    	    html += '<td><div class="intd"><span class="sort" style="background-color:' + color +';color:#ffffff;">' + n.leagueName + '</a></span></div></td>';
	    	    html += '<td><div class="intd">' + n.cnCode + '</div></td>';
	    	    html += '<td><div class="intd"><div class="hide">' + n.homeTeamName + ' VS ' + n.guestTeamName + '</a></div></div></td>';
	    	    html += '<td><div class="intd"><div class="hide">' + n.halfScore + '</a></div></div></td>';
	    	    html += '<td><div class="intd"><div class="hide">' + n.score + '</a></div></div></td>';
	    	    
    	    	
	    	    var zc0101 = plays['01_ZC_1'];
	    	    var zc0102 = plays['01_ZC_2'];
	    	    html += playTemplate(getField(zc0101, 'winOption'),  getField(zc0101, 'winBonus'), getField(zc0102, 'winBonus'));
	    	    
	    	    var zc0202 = plays['02_ZC_2'];
	    	    html += playTemplate2(getField(zc0202, 'winOption'), getField(zc0202, 'winBonus'));
	    	    
	    	    var zc0301 = plays['03_ZC_1'];
	    	    var zc0302 = plays['03_ZC_2'];
	    	    html += playTemplate(getField(zc0301, 'winOption'), getField(zc0301, 'winBonus'), getField(zc0302, 'winBonus'));
	    	    
	    	    var zc0401 = plays['04_ZC_1'];
	    	    var zc0402 = plays['04_ZC_2'];
	    	    html += playTemplate(getField(zc0401, 'winOption'), getField(zc0401, 'winBonus'), getField(zc0402, 'winBonus'));
	
	    	    arr.push(html);
    	    }
    	});
		tab.append(arr.join(''));
        
    	$("#pager").pager({
			pageno: pageNo,
        	pagecount: ret.pageCount,
			first: "首页",
			prev: "上页",
			next: "下页",
			last: "尾页",
            callback: loadZC
    	});
    });
    	
};

/**
   * 篮球赛果
   */
function loadLC(pageNo) {
    var from = $("input[name=from]").val();
    var to = $("input[name=to]").val();
    $.getJSON("http://trade.davcai.com/df/aj_bbresult.do?jsonp=?", {'pageNo': pageNo, 'from': from, 'to': to}, function(ret){
    	var tab = $('#dataTab');
		$("#dataTab tr").slice(1).remove();
		$("[v=_c]").html(ret.totalCount);
		var arr = [];
    	$.each(ret.results, function(i, n) {
    		var plays = n['plays'];
    		if(plays != null && typeof(plays) != 'undefined'){
    			var color = (n.color=='' || n.color==null)?'#393':n.color;
	    	    var html = '<tr>';
	    	    html += '<td class="tdl">' + n.playingTime + '</div></td>';
	    	    html += '<td><div class="intd"><span class="sort" style="background-color:' + color +';color:#ffffff;">' + n.leagueName + '</a></span></div></td>';
	    	    html += '<td><div class="intd">' + n.cnCode + '</div></td>';
	    	    html += '<td><div class="intd"><div class="hide">' + n.guestTeamName + ' VS ' + n.homeTeamName + '</a></div></div></td>';
	    	    html += '<td><div class="intd"><div class="hide">' + n.finalScore + '</a></div></div></td>';
    	    
	    	    var zc0601 = plays['06_LC_1'];
	    	    var zc0602 = plays['06_LC_2'];
	    	    html += playTemplate(getField(zc0601, 'winOption'),  getField(zc0601, 'winBonus'), getField(zc0602, 'winBonus'));
	    	    
	    	    var zc0701 = plays['07_LC_1'];
	    	    var zc0702 = plays['07_LC_2'];
	    	    html += playTemplate(getField(zc0702, 'winOption'), getField(zc0701, 'winBonus'), getField(zc0702,'winBonus'));
	    	    
	    	    var zc0801 = plays['08_LC_1'];
	    	    var zc0802 = plays['08_LC_2'];
	    	    html += playTemplate(getField(zc0802, 'winOption'), getField(zc0801,'winBonus'), getField(zc0802,'winBonus'));
	    	    
	    	    var zc0901 = plays['09_LC_1'];
	    	    var zc0902 = plays['09_LC_2'];
	    	    html += playTemplate(getField(zc0901, 'winOption'), getField(zc0901,'winBonus'), getField(zc0902,'winBonus'));
	
	    	    arr.push(html);
    	    }
    	});
    	tab.append(arr.join(''));
		$("#pager").pager({
			pageno: pageNo,
        	pagecount: ret.pageCount,
			first: "首页",
			prev: "上页",
			next: "下页",
			last: "尾页",
            callback: loadLC
    	});
	});
};

var getField = function(data, field) {
    if (typeof (data) == 'undefined') {
        return '-';
    }
    var value = data[field];
    if (typeof (value) == 'undefined' || value == null) {
        value = '-';
    }
    return value;
};
var playTemplate = function(opt, bonus1, bonus2) {
    if (opt == null) {
        opt = '';
    }
    if (bonus1 == null) {
        bonus1 = '';
    }
    if (bonus2 == null) {
        bonus2 = '';
    }
    return '<td class="tdr"><div class="intd">'
        	+ '<div class="cl c-h" style="width:40px;">' + opt
        	+ '</div>' + '<div class="cc" style="width:78px;">'
        	+ bonus1 + ' / ' + bonus2 + '</div></div></td>';
 };
var playTemplate2 = function(opt, bonus1) {
    if (opt == null) {
        opt = '';
    }
    if (bonus1 == null) {
        bonus1 = '';
    }
    return '<td class="tdr"><div class="intd">'
        	+ '<div class="cl c-h" style="width:40px;">' + opt
        	+ '</div>' + '<div class="cc" style="width:40px;">'
        	+ bonus1 + '</div></div></td>';
};


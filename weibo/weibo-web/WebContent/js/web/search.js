var searchKeys;
$(document).ready(function() {
	bindSearchAction();
	//点击放大镜搜索
	$(".magnifier a").click(function(){
	   var keyWord=$("#head-search").val();
	   var trimWord=$.trim(keyWord);
	    // if(trimWord){
		//   doSearch(trimWord);
	    //  }
	})
});

var barSearchFlag;
//绑定搜索动作
function bindSearchAction(){
	//head-search  old quick-search
	$('#head-search').live("keyup",function(e){
		$this = $(this);
		barSearchFlag = $this.val();
		if($.trim($this.val()) == ""){
			//search_result  old search-result
			$('#search_result').hide();
		} else {
			setTimeout(barSearch($this.val()),1000);
		}
	})

	$("#head-search").live("keypress",function(e){
		//var e = e || window.event;
		if(e.keyCode == 13){ 
			var keys = $(this).val();
			e.preventDefault();
			if(keys != ""){
				doSearch(keys);
			}
		}
	})
}
function doSearch(keys){
	window.location.href='http://www.davcai.com/search_weibo?keys=' + encodeURIComponent(keys);
}

function barSearch(searchKeys){
	 var weiboHeadLi = '<li class="no-menu"><h3>讨论</h3></li>';
	 var weiboSearchLi='<li data-value="" class=""> <a href="http://www.davcai.com/search_weibo?keys={1}">搜索&nbsp;&nbsp;<em>“{0}”</em>&nbsp;&nbsp;相关讨论 </a></li>';
	 var userHeadLi = '<li class="no-menu"><h3>用户</h3></li>';
	 var userSearchLi ='<li class="dropdown-menu-user"><a href="http://www.davcai.com/{2}/profile" class="dropdown-menu-user-logo"><img style="width:36px;height:36px;" src="{0}" /></a><a href="http://www.davcai.com/{2}/profile" class="dropdown-menu-user-name"><em>{1}</em></a></li>';
	 var userSearchLiEnd='<li data-value="" class=""><a href="http://www.davcai.com/search_user?keys={1}">搜索&nbsp;&nbsp;<em>“{0}”</em>&nbsp;&nbsp;相关用户</a></li>';
    $('#search_result').show();
	$.getJSON("http://www.davcai.com/ajax_search_user?keys=" + encodeURIComponent(searchKeys) + '&page=1&size=5&callback=?', function(result) {
		if (result.users.success && barSearchFlag == searchKeys) {
			if(result.users.results.length > 0){
				var users = result.users.results;
				var userLi = "";
				$('#search_result').html("");
				var lis= weiboHeadLi + $.format(weiboSearchLi,searchKeys,encodeURIComponent(searchKeys)) + userHeadLi;
				$('#search_result').append(lis);
				for ( var i = 0; i < users.length; i++) {
					userLi += $.format(userSearchLi,users[i].headImageURL,users[i].nickName,users[i].weiboUserId);
				}
				$('#search_result').append(userLi);
			} else {
				$('#search_result').html("");
				var lis= weiboHeadLi + $.format(weiboSearchLi,searchKeys,encodeURIComponent(searchKeys)) + userHeadLi;
				$('#search_result').append(lis);
			}
			$('#search_result').append($.format(userSearchLiEnd,searchKeys,encodeURIComponent(searchKeys)));
		}
	}, 'json').error(function(e) {
		$.showMessage( "操作失败，请刷新页面重试！");
	});
}
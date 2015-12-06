function ajaxLoadDetail(weiboMsg) {
	var $loading = $('<div class="loading" style="height: 40px; display: none;"></div>');
	$("[_detail]").prepend($loading);
	$loading.show();
	var url = "http://www.davcai.com/ajaxLoadDetail";
	var postId = weiboMsg.postId;
	$.post(url, {
		postId : postId
	}, function(result, e) {
		$loading.hide();
		if (result != null) {
			if (result.success) {
				self.options.posts = result.results;
    			self.options.weiboUserId = result.userId;
    			var $statusList = $(".status-list");
    			$statusList.weiboList(self.options);//渲染微博列表
				//详情页面自动展开
				$(".show_div").trigger('click');
				$("a[_n='comment']").trigger('click');
				if(window.location.href.indexOf("#follow")!=-1 ||
					window.location.href.indexOf("#groupbuy")!=-1 ){
					//自动展开跟单
					$(".bet-follow").trigger('click');
				}
			} else {
				$.showMessage(result.desc);
			}
		}
	}, 'json').error(function(e) {
		return;
	});
}
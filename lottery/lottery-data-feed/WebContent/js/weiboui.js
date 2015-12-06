$(document).ready(function() {
//	
////	var $weiboContent = $("#weiboContent");									// 微博输入框dom
////	var longWeiboWindow = $("#longWeiboModal");					// 长微博窗口
////	var $longWeiboTitle = $("#longWeiboTitle");								// 长微博标题
////	var $longWeiboContent = $("#longWeiboContent");				// 长微博内容
//	var $weiboList = {};
//	var panel = $("#postContainer");
//	var weiboPane = {};
//	var pageCount = 2;
//	
//	var img = new LoadingImg(panel);
//	
//	img.showLoading();
//	$("#publishBtn").attr("disabled",true);
////	$.post("http://www.davcai.com/loadPost.do", {}, function(result, e) {
////			img.hideLoading();
////			if (result != null) {
////				if (result.success) {
////					weiboPane = new WeiboHomeUI({
////						userId : result.userId,
////						panel : panel,
////						posts : result.results
////					});
////					$weiboList = weiboPane.$weiboList;
////					weiboPane.setTimeLine(); // 初始化时间线
////					$("#publishBtn").attr("disabled",false);
////					autoLoad();
////				} else {
////					$.showMessage(result.desc);
////				}
////			}
////		}, 'json').error(function(e) {
////			img.hideLoading();
////			alert("获取微博出错！请刷新页面重试！");
////			return ;
////		});
//	
//		$.ajax({
//			type: "get",
//            url: "http://www.davcai.com/loadPost.do",
//            data: {},
//            dataType: "jsonp",
//            success: function(result){
//            	img.hideLoading();
//    			if (result != null) {
//    				if (result.success) {
//    					weiboPane = new WeiboUI({
//    						userId : result.userId,
//    						panel : panel,
//    						posts : result.results
//    					});
//    					$weiboList = weiboPane.$weiboList;
//    					weiboPane.setTimeLine(); // 初始化时间线
//    					$("#publishBtn").attr("disabled",false);
//    				} else {
//    					$.showMessage(result.desc);
//    				}
//    			}
//            }
//		});
});
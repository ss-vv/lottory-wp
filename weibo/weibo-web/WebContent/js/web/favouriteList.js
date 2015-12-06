$(document).ready(function() {
	var favoriteFormatWeiboDataFunction = function(posts,userId) {
		var results = [];
		for(var i=0 ; i < posts.length ; i++ ){
			var obj = posts[i].weiboMsg;
			var srcwb = false;
			if(!obj.user){
				continue;
			}
			if (obj.sourceWeiboMsg != null && obj.sourceWeiboMsg.id > 0) {
				srcwb = {
						id : obj.sourceWeiboMsg.id,
						ownerId : obj.sourceWeiboMsg.ownerId,
						nickName : obj.sourceUser.nickName,
						title : obj.sourceWeiboMsg.title || '',
						content : obj.sourceWeiboMsg.content || '',
						createTime : obj.sourceCreateTimeFmt,
						commentCount : obj.sourceWeiboMsg.commentCount,
						sourceBetScheme:obj.sourceBetScheme,
						sourceRealFollowers:obj.sourceRealFollowers,
						sourceType:obj.sourceType,
						certificationType:obj.user.certificationType
				};
			}
			results.push({
				id : obj.id,
				ownerId : obj.ownerId,
				nickName : obj.user.nickName || '',
				headImageURL : obj.user.headImageURL || '',
				title : obj.title || '',
				content : obj.content || '',
				srcwb : srcwb,
				createTime : obj.createTimeFmt,
				timeLine : obj.createTime,
				from :  obj.from ||'',
				fromUrl : obj.fromUrl || '',
				forwardCount : obj.forwardCount,
				shareCount : obj.shareCount,
				favoriateCount : obj.favoriateCount,
				commentCount : obj.commentCount,
				likeCount : obj.likeCount,
				isFavourited : obj.favourited,
				isLike : obj.like,
				isCurrentUser : userId !=0 && userId == obj.ownerId,
				favouriteTimeFmt : posts[i].favouriteTimeFmt,
				likeUsers:obj.likeUsers,
				realFollowers:obj.realFollowers	,
				betScheme:obj.betScheme,
				certificationType:obj.user.certificationType		
			});
		}
		return results;
	};
	var $weiboMsgTmpl = $("#favourite-weibo-msg-tmpl");
	var favoriateurl = LT.Settings.URLS.like.ajaxLoadFavoriate;
	function ajaxLoadFavourite(page){
		$('body,html').animate({scrollTop:0},0);
		$('.status-list[favorite]').html('<div class="loading" style="height: 40px;"></div>');
		var url = typeof(page) == "undefined" ? favoriateurl : favoriateurl+'?pageRequest.pageIndex='+page;
		$.post(url, {}, function(result, e) {
			if (result != null) {
				if (result.success) {
					$('.status-list[favorite]').weiboList({
						posts:result.results,
						weiboUserId:result.userId,
						$weiboMsgTmpl:$weiboMsgTmpl,
						formatWeiboDataFunction:favoriteFormatWeiboDataFunction
					});
					var curPage = result.pageRequest.pageIndex;
					var totalPages = result.pageRequest.pageCount;
					$(".status-list[favorite]").find('.loading').remove();
					$('.status-list[favorite]').append(ajaxPager("#",curPage,totalPages));
					var a = $('.status-list[favorite]').find("ul.pager > li > a");
					if(a.length > 0){
						a.click(function(e) {
							e.preventDefault();
							var page = $(this).attr("data-page");
							ajaxLoadFavourite(parseInt(page));
						});
					}
				} else {
					$.showMessage(result.desc);
				}
			}
		}, 'json');																								
	}
	ajaxLoadFavourite(1);
});
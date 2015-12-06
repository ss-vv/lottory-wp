var marrowFormatData =  function(posts,userId) {
	var results = [];
	for(var i=0 ; i < posts.length ; i++ ){
		var obj = posts[i];
		var srcwb = false;
		if (obj.sourceWeibo == null || obj.sourceWeibo.id <= 0) {
			obj.sourceWeibo = obj.sourceWeiboMsg;
		}
		if(obj.sourceWeibo == null) {
			srcwb = null;
		} else {
			srcwb = {
					id : obj.sourceWeibo.id,
					ownerId : obj.sourceWeibo.ownerId,
					nickName : obj.sourceWeibo.user.nickName,
					title : obj.sourceWeibo.title || '',
					content : obj.sourceWeibo.content || '',
					createTime : $.dateFormat.date(new Date(obj.sourceWeibo.createTime), "yyyy-MM-dd HH:mm:ss"),
					commentCount : obj.sourceWeibo.commentCount,
					sourceBetScheme:obj.sourceBetScheme,
					sourceRealFollowers:obj.sourceRealFollowers,
					sourceType:obj.sourceType,
					certificationType:obj.sourceWeibo.user.certificationType
			};
		}
		if(obj && obj.user) {
			results.push({
				id : obj.id,
				ownerId : obj.ownerId,
				nickName : obj.user.nickName || '',
				headImageURL : obj.user.headImageURL || '',
				title : obj.title || '',
				type: obj.type,
				schameId : obj.schameId,
				content : obj.content || '',
				srcwb : srcwb,
				createTime : $.dateFormat.date(new Date(obj.createTime), "yyyy-MM-dd HH:mm:ss"),
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
				tags:obj.tags,
				likeUsers:obj.likeUsers,
				realFollowers:obj.realFollowers,
				betScheme:obj.betScheme,
				certificationType:obj.user.certificationType
			});
		}
	}
	return results;
};
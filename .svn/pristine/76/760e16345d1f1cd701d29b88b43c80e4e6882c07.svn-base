var userTemplate =  '<li style="margin-top:10px;">'
					+'	<div class="headpic" style="float:left;" _userid="{0}">'
					+'	<a href="http://www.davcai.com/{0}/profile"><img id="sponsor_user_headImage" width="50px" height="50px" src="{1}"></a>'
					+'	</div>'
					+'	<div style="position: relative;margin-left: 5px;float: left;">'
					+'	    <a href="http://www.davcai.com/{0}/profile" target="_blank"><span id="sponsor_user_name" _nickname_block="_nickname_block" _userid="{0}">{2}</span></a>'
					+'		<div class="userDes"> 主帖：{6}</div>'
					+'		<div class="userDes"> 关注：{5}  | 粉丝：{4}</div>'
					+'	</div>{3}'
					+'</li>';

var addFriendTemplate = '<div class="addFriendBtn" style="position: relative;float:right"  title="加关注" flag="followFlag" _userId="{0}" id="followBtn">+加关注</div>';
var delFriendTemplate = '<div class="addFriendBtn" style="position: relative;float:right"  title="取消关注" flag="unFollowFlag" _userId="{0}" id="followBtn">取消关注</div>';
var weiboUserId;

$(document).ready(function() {
	$("#status_tab_ul li").click(activeTab);
});

function activeTab(event){
	$this = $(this);
	weiboUserId = $this.attr("_weiboUserId"); 
	$("#status_tab_ul li").attr("class","");
	$this.attr("class","active");
	
	$("#weibos_list").attr("style","display:none");
	$("#comments_list").attr("style","display:none");
	$("#followings_list").attr("style","display:none");
	$("#followers_list").attr("style","display:none");
	
	if($this.attr("_mark") == "weibos_li"){//微博列表
		$("#weibos_list").attr("style","display:true");
		if($.trim($("#weibos_div").find(".status-list").html()) == ""){
			findProfileWeibos(weiboUserId,1);
		}
	} else if($this.attr("_mark") == "comments_li"){//评论列表
		$("#comments_list").attr("style","display:true");
		if($.trim($("#comments_div").html()) == ""){
			findProfileComments(weiboUserId,1);
		}
	} else if($this.attr("_mark") == "followings_li"){//关注列表
		$("#followings_list").attr("style","display:true");
		var lis = $("#followings_ul li");
		if(lis.length == 0){
			findProfileFollowings(weiboUserId,1);
		}
	} else if($this.attr("_mark") == "followers_li"){//粉丝列表
		$("#followers_list").attr("style","display:true");
		var lis = $("#followers_ul li");
		if(lis.length == 0){
			findProfileFollowers(weiboUserId,1);
		}
	}
}

function findProfileWeibos(weiboUserId,page){
	$(".loading",$("#weibos_list")).show();
	$("#weibos_div").find(".status-list").html("");
	$("#weibos_pager").html("");
	$.post("/profile_weibos?weiboUserId=" + weiboUserId + "&page=" + page, function(result , e) {
		$(".loading",$("#weibos_list")).hide();
		if (result != null) {
			if (result.success) {
				$("#weibos_div").find(".status-list").weiboList({
					posts:result.results,
					weiboUserId:result.userId
				});
				var curPage = result.pageRequest.pageIndex;
				var totalPages = result.pageRequest.pageCount;
				$("#weibos_pager").html(ajaxPager("#",curPage,totalPages));
				$("#weibos_pager a").click(findProfileWeibosByPaging);
			} else {
				$.showMessage(result.desc);
			}
		}
	}, 'json');
}
function findProfileWeibosByPaging(event){
	$this = $(this);
	$("#weibos_pager").html("");
	var page = $this.attr("data-page");
	findProfileWeibos(weiboUserId,page);
}


var commentULTemplate = '<ul class="comments-list">{0}</ul>';
var commentLITemplate = '<li _comment="{7}">'
   +'<div class="content" style="margin-left: 0px;">'
   +'	<div class="main-comment">'
   +'		<span class="text">{0}</span>'
   +'	</div>'
   +'	<p>原文：</p>'
   +'	<div class="status">'
   +'		<a href="http://www.davcai.com/{1}/profile" data-name="{2}"><span>{2}</span></a>:'
   +'		<a class="reply-text" href="http://www.davcai.com/{1}/{4}">{3}</a>'
   +'		<div class="meta">'
   +'			<a class="time" href="http://www.davcai.com/{1}/{4}">{5}</a>'
   +'		</div>'
   +'	</div>'
   +'	<div class="meta">'
   +'		<div class="infos">'
   +'			<a class="time createdAtTime" href="http://www.davcai.com/{1}/{4}">{6}</a>'
   +'			<a href="javascript:void(0)" class="reportSpam_comment"></a>'
   +'		</div>'
   +'		<div class="ops">'
   +'			<a href="javascript:void(0)" class="deleteComment last">删除</a>'
   +'		</div>'
   +'	</div>'
   +'</div></li>';

function makeCommetnHtml(results){
	var ul = "";
	var li = "";
	for ( var i = 0; i < results.length; i++) {
		var cmt = results[i];
		var d = new Date();
		d.setTime(cmt.weiboMsg.createTime);
		li += $.format(commentLITemplate,cmt.content,
				cmt.repliedUser.weiboUserId,
				cmt.repliedUser.nickName,
				cmt.weiboMsg.content,
				cmt.weiboMsg.id,
				dateFormate(d,"yyyy-MM-dd hh:mm:ss"),
				cmt.createTime.replace(/T/," "),
				cmt.id);
	}
	ul += $.format(commentULTemplate,li);
	return ul;
}
function dateFormate(date,fmt) { 
    var o = {
            "M+": date.getMonth() + 1, //月份 
            "d+": date.getDate(), //日 
            "h+": date.getHours(), //小时 
            "m+": date.getMinutes(), //分 
            "s+": date.getSeconds(), //秒 
            "q+": Math.floor((date.getMonth() + 3) / 3), //季度 
            "S": date.getMilliseconds() //毫秒 
        };
        if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (date.getFullYear() + "").substr(4 - RegExp.$1.length));
        for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        return fmt;
}
function bindCmtFunction(){
	$(".comments-list li").mouseover(function (e){
		$(".deleteComment",$(this)).css("visibility","visible");
	});
	$(".comments-list li").mouseleave(function (e){
		$(".deleteComment",$(this)).css("visibility","hidden");
	});
	deleteCmtEvent($(".comments-list li"));
}
//删除评论事件
function deleteCmtEvent(comments) {
	var deleteLink = comments.find('.deleteComment');
	deleteLink.on('click', function(event){
		event.preventDefault();
		var self = $(this);
		var commentId = self.closest('li').attr('_comment');
		deleteCmtInFrofile(commentId, self);
	});
}

function deleteCmtInFrofile(commentId, deleteLink) {
	var cmntLi = deleteLink.closest('li');
	$.ajax('http://www.davcai.com/del_cmnt', {
		dataType: 'jsonp',
		data: {
			cid: commentId
		},
		success: function(result){
			if(result.success){
				cmntLi.fadeOut("slow",function (){
					cmntLi.remove();
				});
			}else{
				$.msgBox(result.desc, null, {pos:'center', success: false});
			}
		},
		error: function(result){
			$.msgBox('因网络问题，删除失败。', null, {pos:'center'});
		}
	});
}
function findProfileComments(weiboUserId,page){
	$(".loading",$("#comments_list")).show();
	$("#comments_div").html("");
	$("#comments_pager").html("");
	$.post("/profile_comments?weiboUserId=" + weiboUserId + "&page=" + page, function(result , e) {
		$(".loading",$("#comments_list")).hide();
		if (result != null) {
			if (result.success) {
				var cmtLiHtml = makeCommetnHtml(result.results);
				$("#comments_div").html(cmtLiHtml);
				bindCmtFunction();
				var curPage = result.pageRequest.pageIndex;
				var totalPages = result.totalPages;
				$("#comments_pager").html(ajaxPager("#",curPage,totalPages));
				$("#comments_pager a").click(findProfileCommentsByPaging);
			} else {
				$.showMessage(result.desc);
			}
		}
	}, 'json');
}
function findProfileCommentsByPaging(event){
	$this = $(this);
	$("#comments_div").html("");
	$("#comments_pager").html("");
	var page = $this.attr("data-page");
	findProfileComments(weiboUserId,page);
}
function findProfileFollowings(weiboUserId,page){
	var currentWeiboUserId = $("#current_user_weiboId").attr("weiboUserId");
	$(".loading",$("#followings_list")).show();
	$("#followings_ul").html("");
	$("#followings_pager").html("");
	$.post("/ajax_rltship_getFollowings?weiboUserId=" + weiboUserId + "&page=" + page, function(data , e) {
		$(".loading",$("#followings_list")).hide();
		if (data != null) {
			if (data.success) {
				var users = data.results;
				var html = "";
				for ( var index = 0; index < users.length; index++) {
					var friendStatus = "";
					if(users[index].weiboUserId == currentWeiboUserId){//如果是自己，不显示关注按钮
						friendStatus = "";
					} else {
						if(users[index].beFollowed == true){
							friendStatus = $.format(delFriendTemplate,users[index].weiboUserId);
						} else {
							friendStatus = $.format(addFriendTemplate,users[index].weiboUserId);
						}
					}
					html += $.format(userTemplate,
							users[index].weiboUserId,
							users[index].headImageURL,
							users[index].nickName,
							friendStatus,
							users[index].followerCount,
							users[index].followingCount,
							users[index].weiboCount);
					$("#followings_ul").html(html);
					$(".addFriendBtn").click(function (event, a, b) {
						followAndUnFollow( $(event.target));
					});
					bindMouseInHeadOrNicknameEvent($("#followings_ul"));
					var curPage = data.pageRequest.pageIndex;
					var totalPages = data.pageRequest.pageCount;
					$("#followings_pager").html(ajaxPager("#",curPage,totalPages));
					$("#followings_pager a").click(findProfileFollowingsByPaging);
				}
			} else {
				console.log(data.desc);
			}
		}
	}, 'json');
}
function findProfileFollowingsByPaging(event){
	$this = $(this);
	$("#followings_ul").html("");
	$("#followings_pager").html("");
	var page = $this.attr("data-page");
	findProfileFollowings(weiboUserId,page);
}
function findProfileFollowers(weiboUserId,page){
	var currentWeiboUserId = $("#current_user_weiboId").attr("weiboUserId");
	$(".loading",$("#followers_list")).show();
	$("#followers_ul").html("");
	$("#followers_pager").html("");
	$.post("/ajax_rltship_getFollowers?weiboUserId=" + weiboUserId + "&page=" + page, function(data , e) {
		$(".loading",$("#followers_list")).hide();
		if (data != null) {
			if (data.success) {
				var users = data.results;
				var html = "";
				for ( var index = 0; index < users.length; index++) {
					var friendStatus = "";
					if(users[index].weiboUserId == currentWeiboUserId){//如果是自己，不显示关注按钮
						friendStatus = "";
					} else {
						if(users[index].beFollowed == true){
							friendStatus = $.format(delFriendTemplate,users[index].weiboUserId);
						} else {
							friendStatus = $.format(addFriendTemplate,users[index].weiboUserId);
						}
					}
					html += $.format(userTemplate,
							users[index].weiboUserId,
							users[index].headImageURL,
							users[index].nickName,
							friendStatus,
							users[index].followerCount,
							users[index].followingCount,
							users[index].weiboCount);
					$("#followers_ul").html(html);
					$(".addFriendBtn").click(function (event, a, b) {
						followAndUnFollow( $(event.target));
					});
					bindMouseInHeadOrNicknameEvent($("#followers_ul"));
					var curPage = data.pageRequest.pageIndex;
					var totalPages = data.pageRequest.pageCount;
					$("#followers_pager").html(ajaxPager("#",curPage,totalPages));
					$("#followers_pager a").click(findProfileFollowersByPaging);
				}
			} else {
				console.log(data.desc);
			}
		}
	}, 'json');
}
function findProfileFollowersByPaging(event){
	$this = $(this);
	$("#followers_ul").html("");
	$("#followers_pager").html("");
	var page = $this.attr("data-page");
	findProfileFollowers(weiboUserId,page);
}
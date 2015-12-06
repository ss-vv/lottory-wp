/**
 * 微博通知的js
 * 定义通知的命名空间Notification
 * 
 * */
Jet().$package("Notification",function(J){
	var notifier = this;
	
	var observer = {
		atMe : null,
		commentMe : null,
		myFollowers : null,
		privateMsgs : null
	};
	//最后一次通知的时间
	var noticeLastTime = null;
	var reqInterval = 6000;//6秒
	
	/**
	 * 发请求检查是否有未读消息
	 */
	this.isHaveUnreadMessage = function() {
		var intervalTime = 0;
		if(noticeLastTime) {
			var now = new Date().getTime();
			intervalTime = now - noticeLastTime;
		}
//		console.log("----" + intervalTime);
		if(noticeLastTime && intervalTime <= reqInterval) {
			return;
		}
		noticeLastTime = new Date().getTime();
		var url = "http://www.davcai.com/notification";
		var param = {};
		var type = "jsonp";
		$.post(url, param, function(result) {
			if(result && result.success === true) {
				notifier.changeLabelStatus(result.data);
			} else {
				//check failure
			}
		}, type);
	};
	
	/**
	 * 根据后台返回的通知状态信息，重置“提到我的”，“评论我的”， “粉丝”等标签状态
	 */
	this.changeLabelStatus = function(notice) {
		if(notice) {
			var mentions = notice.mentions;
			var comments = notice.comments;
			var followers = notice.followers;
			var privateMsgs = notice.privateMsgs;
			var createdTime = notice.createdTime;
			if(!observer.atMe) {
				observer.atMe = $("#atMe");
			}
			if(!observer.commentMe) {
				observer.commentMe = $("#commentMe");
			}
			if(!observer.myFollowers) {
				observer.myFollowers = $("#myFollowers");
			}
			if(!observer.privateMsgs) {
				observer.privateMsgs = $("#privateMsg");
			}
			notifier.resetLabelStatus(observer.atMe, mentions);
			notifier.resetLabelStatus(observer.commentMe, comments);
			notifier.resetLabelStatus(observer.myFollowers, followers);
			notifier.resetPrivateMsgStatus(observer.privateMsgs, privateMsgs);
			//notifier.resetNote(observer.privateMsgs,privateMsgs);
		}
	};
	
	/**
	 * 重置首页左侧标签通知状态
	 */
	this.resetLabelStatus = function(noticeContainer, msgCount) {
		var sprites = noticeContainer.find(".sprites");
		if(msgCount > 0) {
			if(sprites.length == 0) {
				if(msgCount > 99) {
					msgCount = 99;
				}
				if(msgCount <= 0) {
					msgCount = '';
				}
				var signal = $('<i class="sprites notice"><span>' + msgCount + '</span></i>');
				noticeContainer.append(signal);
			}
		} else {
			sprites.remove();
		}
	};
	/**
	 * 重置头部私信状态
	 */
	this.resetPrivateMsgStatus = function(noticeContainer, msgCount) {
		if(msgCount > 0) {
			if(msgCount > 99) {
				msgCount = 99;
			}
			var signal = $('<i class="mail-tip"></i><em class="mail-num"><a href="http://www.davcai.com/private_msg" target="_blank">'+msgCount+'</a></em>');
			noticeContainer.html(signal);
			if(msgCount < 10){
				noticeContainer.find(".mail-num").css("margin-left","17px");
			}
		} else {
			noticeContainer.html('');
		}
	};
	
});

$(function() {
	window.onscroll = function() {
		Notification.isHaveUnreadMessage();
	}
	$(window).focus(function() {
		Notification.isHaveUnreadMessage();
	});
});
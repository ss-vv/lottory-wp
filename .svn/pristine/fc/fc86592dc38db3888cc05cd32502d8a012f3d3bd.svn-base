$(document).ready(function() {
	loadHeader();
	var messageWindow = $("#messageWindow"); 				// 消息通知窗口
	var messageContent = $("#messageContent"); 				// 消息通知窗口
	
	// 弹出只有确认按钮的对话框
	$.showMessage = function showMessage(message) {
		messageContent.text(message);
		messageWindow.modal('show');
	};
});

function bindDropdown(){
	$("#load_user_info").mouseout(function(){
		$(this).children(".davcai-user-list").hide();
	}).mouseover(function(){
		$(this).children(".davcai-user-list").show();
	});
}

var showLoading = function(container) {
	container.next('.loading').show();
};

var hideLoading = function(container) {
	container.next('.loading').hide();
};
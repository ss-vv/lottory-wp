$(function() {
	var dialog = {
			open:function(id) {
				$( "#" + id).dialog("open");
			},
			close:function(id) {
				$( "#" + id).dialog("close");
			},
			init:function() {
				return {
					autoOpen: false,
				    height: 600,
				    width: 650,
				    modal: true
				};
			}
		};
	var dialog2 = {
			open:function(id) {
				$( "#" + id).dialog("open");
			},
			close:function(id) {
				$( "#" + id).dialog("close");
			},
			init:function() {
				return {
					autoOpen: false,
					height: 600,
					width: 650,
					modal: true
				};
			}
	};
	$("#push_to_all_dialog").dialog(dialog.init());
	$("#push_to_some_dialog").dialog(dialog2.init());
	$("#push_to_all_btn").click(function (){
		dialog.open("push_to_all_dialog");
	});
	$("#push_to_some_btn").click(function (){
		dialog.open("push_to_some_dialog");
	});
	$('span[_id="submitPrivateMsg"]').click(function () {
		if(!confirm("确定群发该消息吗？点击确定将直接发送")){
			return ;
		}
		var $this = $(this);
		var $form = $this.closest("form");
		var dialogId = $this.closest("form").parent().attr("id");
		$.getJSON($form.attr("action"),$form.serialize(),function (data){
			if(data){
				alert("发送成功");
			} else {
				alert("发送成功");
			}
			dialog.close(dialogId);
			window.location.reload();
		});
	});
});
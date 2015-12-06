/**
 * 约定：serial表示需要序列化的字段
 */
$(function() {
	//初始化并渲染对话框控件
	$( "#add_topic_dialog" ).dialog(dialog.init());
	$( "#edit_topic_dialog" ).dialog(dialog.init());
	$("#add_topic_btn").click(function() {
		dialog.open("add_topic_dialog");
	});
	$("#delete_topic_btn").click(function() {
		var idSet = findCheckedRow("topic_table");
		if(idSet.length > 0) {
			var resp = confirm("确定要删除？话题ID=" + idSet);
			if(resp) {
				var json = {};
				json["topicIdList"] = idSet.join();
				submitData("topic/deleteTopic.do", json, "");
			}
		}
	});
	$("#edit_topic_btn").click(function() {
		var json = {};
		var idSet = findCheckedRow("topic_table");
		if(idSet && idSet.length > 0) {
			json["topic.id"] = idSet.join();
			$.post("topic/getTopic.do", json, function(ret) {
				if(ret.success == false) {
					alert("提交失败");
				} else {
					var topic = ret.data;
					for(var elt in topic) {
						$("#edit_topic_dialog").find("input[serial='" + elt + "']").val(topic[elt]);
						$("#edit_topic_dialog").find("textarea[serial='" + elt + "']").val(topic[elt]);
					}
					dialog.open("edit_topic_dialog");
				}
			},"json");
		}
	});
	
	$(".add-topic-submit").click(function() {
		var formToJson = getJson("add_topic_dialog");
		submitData("topic/addTopic.do", formToJson, "add_topic_dialog");
	});
	$(".edit-topic-submit").click(function() {
		var formToJson = getJson("edit_topic_dialog");
		submitData("topic/updateTopic.do", formToJson, "edit_topic_dialog");
	});
	
	//绑定文件上传form提交
	bindFileUploadAjaxForm("topic-form-add");
	bindFileUploadAjaxForm("topic-form-edit");
	
});

var bindFileUploadAjaxForm = function(formId) {
	var idSelector = "#"+formId;
	$(idSelector).ajaxForm({
		dataType:"json",
		success:function(ret) {
			log(ret);
			var uploadSucc = $(idSelector).find(".upload-result");
			if(null != ret && ret.success == true) {
				$(idSelector).parent().find('input[serial="picUrl"]').val(ret.data);
				uploadSucc.css({"display":"inline-block"});
				uploadSucc.text("图片上传成功.");
			} else {
				uploadSucc.css({"display":"inline-block"});
				if(ret.data != null) {
					uploadSucc.text(ret.data);
				} else {
					uploadSucc.text("图片上传失败.");
				}
			}
		}
	});
};

//获取dialog下所有的字段并转换为json对象
var getJson = function(dialogId) {
	var formToJson = {};
	$.each($("#" + dialogId).children(), function(i, elt) {
		if(null != $(elt).attr("serial")) {
			formToJson["topic." + $(elt).attr("serial")] = $(elt).val();
		}
	});
	return formToJson;
};

//提交数据
var submitData = function(url, json, dialogId) {
	for(var index in json) {
		var elt = json[index];
		if(elt == "") {
			alert("存在无效字段.");
			return;
		}
	}
	$.post(url, json, function(data) {
		if(dialogId) {
			dialog.close(dialogId);
		}
		if(data.success == false) {
			alert("提交失败");
		} else {
			window.location.reload();
		}
	},"json");
};

/**
 * 查找选中的记录并返回ID集合
 * tableId表示数据表格ID
 */
var findCheckedRow = function(tableId) {
	var idSet = [];
	var checkedList = $("#" + tableId).find("span[class='checked']");
	$.each(checkedList, function(i, elt) {
		var topicId = $(elt).find("input").val();
		idSet.push(topicId);
	});
	return idSet;
};

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
		    height: 460,
		    width: 600,
		    modal: true
		};
	}
};



//===================图片上传的JS========================
var upload = function(form) {
	$(form).submit();
};


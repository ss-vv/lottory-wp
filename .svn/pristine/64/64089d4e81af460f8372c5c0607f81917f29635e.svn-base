/**
 * 约定：serial表示需要序列化的字段
 */
/**
 * 获取BasePath
 */
var local_ = (window.location+'').split('/'); 
var basePath = local_[0]+'//'+local_[2]+'/'+local_[3]; 
$(function() {
	//初始化并渲染对话框控件
	$( "#add_topic_app" ).dialog(dialog.init());
	$("#addAppActivity").click(function() {
		selectType($("#type_Id")[0]);
		clearValue();
		dialog.open("add_topic_app");
	});
	$("#deleteAppActviity").click(function() {
		var idSet = findCheckedRow("topic_table");
		if(idSet.length > 0) {
			var resp = confirm("确定要删除？活动ID=" + idSet);
			if(resp) {
				var json = {};
				json["appIdList"] = idSet.join();
				submitData(basePath+"/deleteActivity.do", json, "");
				cleanCheckBox();
			}
		}
	});
	
	$("#putOnApp").click(function() {
		var idSet = findCheckedRow("topic_table");
		if(idSet.length > 0) {
			var resp = confirm("确定要上架？活动ID=" + idSet);
			if(resp) {
				var json = {};
				json["appIdList"] = idSet.join();
				submitData(basePath+"/putOnApp.do", json, "");
				cleanCheckBox();
			}
		}
	});
	
	
	$("#putOffApp").click(function() {
		var idSet = findCheckedRow("topic_table");
		if(idSet.length > 0) {
			var resp = confirm("确定要下架？活动ID=" + idSet);
			if(resp) {
				var json = {};
				json["appIdList"] = idSet.join();
				submitData(basePath+"/putOffApp.do", json, "");
				cleanCheckBox();
			}
		}
	});
	/**
	 * 清空checkbox
	 */
	function cleanCheckBox()
	{
		$("input[name='checkbox']").attr("checked",false);
	}
	
	$("#putDownApp").click(function() {
		var idSet = findCheckedRow("topic_table");
		//测试多选
		if(idSet.length>1)
			{
			alert("一次只能移动一条");
			}
		else
				{
				if(idSet.length > 0) {
					var resp = confirm("确定要下移？活动ID=" + idSet);
					if(resp) {
						var json = {};
						json["appIdList"] = idSet.join();
						submitData(basePath+"/putDownApp.do", json, "");
						cleanCheckBox();
					}
				}
			}
	});
	$("#putUpApp").click(function() {
		var idSet = findCheckedRow("topic_table");
		if(idSet.length>1)
			alert("一次只能移动一条");
		else
			{
		if(idSet.length > 0) {
			var resp = confirm("确定要上移？活动ID=" + idSet);
			if(resp) {
				var json = {};
				json["appIdList"] = idSet.join();
				submitData(basePath+"/putUpApp.do", json, "");
				cleanCheckBox();
			}
		}
		}
	});
	
	$("#putUpUpApp").click(function() {
		var idSet = findCheckedRow("topic_table");
		if(idSet.length>1)
			alert("一次只能置顶一条");
		else
			{
		if(idSet.length > 0) {
			var resp = confirm("确定要置顶？活动ID=" + idSet);
			if(resp) {
				var json = {};
				json["appIdList"] = idSet.join();
				submitData(basePath+"/putUpUpApp.do", json, "");
				cleanCheckBox();
			}
		}
		}
	});
	
	$("#send_app_dialog" ).dialog(dialog.init());
	$("#sendAppActviity").click(function() {
		var url = basePath+"/findGroupids.do";
		var json = {};
		$.post(url, json, function(data) {
			if(data.success == false) {
				alert("查询失败");
			} else {
				var datas = JSON.parse(data.data);
				$("#selectid_send").empty();
				$("#selectid_send").append("<option value=''>请选择群号...</option>");
				$("#selectid_send").append("<option value='ALL'>ALL</option>");
				for(var i = 0;i < datas.length; i++){
					$("#selectid_send").append("<option value='"+datas[i].groupid+"'>"+datas[i].name.substr(1,datas[i].name.length - 2)+"</option>");//entity 变量
				}
				dialog.open("send_app_dialog");
			}
		},"json");
	});
	
	
	
	
	$(".add-topic-submit").click(function() {
		var formToJson = getJson("add_topic_app");
		submitData(basePath+"/addActivity.do", formToJson, "add_topic_app");
	});
	
	$(".send-topic-submit").click(function() {
		var formToJson = getGroupIdJson("send_app_dialog");
		submitData(basePath+"/sendContentToGroup.do", formToJson, "send_app_dialog");
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
				$(idSelector).parent().find('input[serial="imageURL"]').val(ret.data);
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
	$.each($("#" + dialogId).children().children(), function(i, elt) {
		if(null != $(elt).attr("serial")) {
			formToJson["activityPO." + $(elt).attr("serial")] = $(elt).val();
		}
	});
	return formToJson;
};

var getGroupIdJson = function(dialogId) {
	var formToJson = {};
	$.each($("#" + dialogId).children().children(), function(i, elt) {
		if(null != $(elt).attr("serial")) {
			formToJson[$(elt).attr("serial")] = $(elt).val();
		}
	});
	return formToJson;
};

//提交数据
var submitData = function(url, json, dialogId) {
//	for(var index in json) {
//		var elt = json[index];
//		if(elt == "") {
//			alert("存在无效字段.");
//			return;
//		}
//	}
	$.post(url, json, function(data) {
		if(dialogId) {
			dialog.close(dialogId);
		}
		if(data.success == false) {
			alert("提交失败");
		} else {
			alert("提交成功");
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

function selectType (a){
	if(a.value == "0"){ //选择活动
		$("#scheme_Id").hide();
		$("#lottery_id").hide();
		$("#url_Id").show();
		$('input[serial="schemeId"]').attr("value","");
		$("#lotteryTypeId option[value=''] ").attr("selected",true);
		$('input[serial="title"]').attr("value","");
	}
	if(a.value == "1"){ //选择投注
		$("#url_Id").hide();
		$("#scheme_Id").hide();
		$("#lottery_id").show();
		$('input[serial="activityURL"]').attr("value","");
		$('input[serial="schemeId"]').attr("value","");
		$('input[serial="title"]').attr("value","");

	}
	
	if(a.value == "2"){ //选择方案
		$("#lottery_id").hide();
		$("#url_Id").hide();
		$("#scheme_Id").show();
		$('input[serial="activityURL"]').attr("value","");
		$("#lotteryTypeId option[value=''] ").attr("selected",true);
		$('input[serial="title"]').attr("value","");

	}
}
function selectDetailType (a){
	if(a == "0"){ //选择活动
		$("#detail_scheme_Id").hide();
		$("#detail_lottery_id").hide();
		$("#detail_url_Id").show();
	}
	if(a == "1"){ //选择投注
		$("#detail_url_Id").hide();
		$("#detail_scheme_Id").hide();
		$("#detail_lottery_id").show();
	}
	
	if(a == "2"){ //选择方案
		$("#detail_lottery_id").hide();
		$("#detail_url_Id").hide();
		$("#detail_scheme_Id").show();
	}
}

var clearValue = function (){
	$('input[serial="activityURL"]').attr("value","");
	$('input[serial="imageURL"]').attr("value","");
	$("#lotteryTypeId option[value=''] ").attr("selected",true);
	$('input[serial="title"]').attr("value","");
};

function detailActivity(activityId) {
	var json = {};
	$( "#detail_app_dialog" ).dialog(dialog.init());
	if(activityId != null) {
		json["activityPO.id"] = activityId;
		$.post(basePath+"/getActivity.do", json, function(ret) {
			if(ret.success == false) {
				alert("提交失败");
			} else {
				var ac = ret.data;
				var subType = "";
				for(var elt in ac) {
					if(elt == "type"){
						if(ac[elt] == "0"){
							ac[elt] = "活动";
							subType = "0";
						}else if(ac[elt] == "1"){
							ac[elt] = "投注";
							subType = "1";
						}else if(ac[elt] == "2"){
							ac[elt] = "方案";
							subType = "2";
						}
					}
					if(elt == "lotteryType"){
						if(ac[elt] == "0"){
							ac[elt] = "竞足";
						}else if(ac[elt] == "1"){
							ac[elt] = "竞篮";
						}else if(ac[elt] == "2"){
							ac[elt] = "重庆时时彩";
						}else if(ac[elt] == "3"){
							ac[elt] = "江西11选5";
						}else if(ac[elt] == "4"){
							ac[elt] = "14场胜负彩";
						}else if(ac[elt] == "5"){
							ac[elt] = "任选9场";
						}else if(ac[elt] == "6"){
							ac[elt] = "六场半全场";
						}else if(ac[elt] == "7"){
							ac[elt] = "四场进球";
						}
					}
					$("#detail_app_dialog").find("input[serial='" + elt + "']").val(ac[elt]);
				}
				selectDetailType(subType);
				dialog.open("detail_app_dialog");
			}
		},"json");
	}
};

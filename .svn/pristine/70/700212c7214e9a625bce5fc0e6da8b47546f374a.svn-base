$(function(){
	//初始化并渲染对话框控件
	$( "#add_ad_dialog" ).dialog(dialog.init());
	$( "#edit_ad_dialog" ).dialog(dialog.init());
	
	$("#add_ad_btn").click(function() {
		
		dialog.open("add_ad_dialog");
	});
	$(".add-ad-submit").click(function(){
		
		var formToJson = getJson("add_ad_dialog");
		submitData("/weibo/addAd.do", formToJson, "add_ad_dialog");
	});
	bindFileUploadAjaxForm("ad-form-add");
	bindFileUploadAjaxForm("ad-form-edit");
	$("#st").change(function(){
		
		var v=$(this).val();
		$("#status").val(v);
	})
	//上架
	$("#tobead").click(function(){
	
		commonsUpdate("/weibo/putOnAd.do",0);
		
	})
	//下架
	$("#getoffad").click(function(){
		
		commonsUpdate("/weibo/getOffAd.do",-1);
	})
	
	$("#delad").click(function(){
		
		delete_ad("/weibo/deleteAd.do");
	})
	//更新
	 $(".edit-ad-submit").click(function() {
		if(!confirm("确定要修改吗？"))
			return;
		var formToJson = getJson("edit_ad_dialog");
		submitData("/weibo/updateAd.do", formToJson, "edit_ad_dialog");
	});
	//编辑框
	$("#editad").click(function() {
		var json = {};
		var id = check("topic_table");
		if(id && id.length > 0) {
		
			json["ad.id"] = id;
			$.post("/weibo/getAd.do", json, function(ret) {
				if(ret.success == false) {
					alert("提交失败");
				} else {
					var topic = ret.data;
					for(var elt in topic) {
						$("#edit_ad_dialog").find("input[serial='" + elt + "']").val(topic[elt]);
						$("#edit_ad_dialog").find("select[serial='" + elt + "']").val(topic[elt]);
					}
					dialog.open("edit_ad_dialog");
				}
			},"json");
		}
	});
	
})
var delete_ad=function(url){
	var v=$("#topic_table input[type='checkbox']:checked");
	var s=v.size();
	if(v&&s>1){
		alert("只能选择一条广告！");
		return;
	}else if(s==0){
		alert("请选择一条广告!");
		return;
	}
	var st=v.attr("ad_status");
	if(st && st==0){
		
		alert("请将此条广告下架后在删除");
		return;
	}
	if(confirm("确定要删除吗？"))
	updateStatus(url,v.val());
	
	
}
var check=function(s){
	var v=$("#"+s+" input[type='checkbox']:checked");
	var size=v.size();
	if(v &&size>1){
		alert("只能选择一条广告编辑");
		return;
	}else if(size==0){
		
		alert("请选择一条广告编辑");
		return;
	}
	return v.val();
	
}
var commonsUpdate =function(url,status){
	
	var v=$("#topic_table input[type='checkbox']:checked");
	var s=v.size();
	if(v&&s>1){
		alert("只能选择一条广告！");
		return;
	}else if(s==0){
		alert("请选择一条广告!");
		return;
	}
	var st=v.attr("ad_status");
	if(st==status && st==0){
		
		alert("此广告已上架");
		return;
	}else if(st==status && st==-1){
		
		alert("此广告已下架");
		return;
		
	}
	
	updateStatus(url,v.val());
	
}

//获取dialog下所有的字段并转换为json对象
var getJson = function(dialogId) {
	var formToJson = {};
	$.each($("#" + dialogId).children(), function(i, elt) {
		if(null != $(elt).attr("serial")) {
			
			formToJson["ad."+$(elt).attr("serial")] = $(elt).val();
		}
	});
	return formToJson;
};
//上架 下架广告
var updateStatus=function(url,id){
	if(url && id){
		if(id){
		var formToJson = {};
		formToJson["ad.id"]=id;
		}
		$.post(url,formToJson,function(data){
			if(data.success==false){
				alert("提交失败");
			}else{
				
				window.location.reload();
			}
			
		},"json");
		
	}
	
}
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
var bindFileUploadAjaxForm = function(formId) {
	var idSelector = "#"+formId;
	$(idSelector).ajaxForm({
		dataType:"json",
		success:function(ret) {
			log(ret);
			var uploadSucc = $(idSelector).find(".upload-result");
			if(null != ret && ret.success == true) {
				$(idSelector).parent().find('input[serial="picPath"]').val(ret.data);
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
		    height: 230,
		    width: 620,
		    modal: true
		};
	}
};

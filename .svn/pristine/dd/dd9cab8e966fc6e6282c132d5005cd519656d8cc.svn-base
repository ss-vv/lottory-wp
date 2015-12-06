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
				    height: 500,
				    width: 600,
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
					height: 100,
					width: 400,
					modal: true
				};
			}
	};
	
	$("#edit_news_dialog").dialog(dialog.init());
	$("#publish_news_dialog").dialog(dialog2.init());
	
	function getNewsInfo(target){
		var $this = target;
		var tr = $this.parents("td").parent();
		var tds = $("td",tr);
		
		var id = $this.attr("newsId");
		var title = $(tds[1]).html();
		var content = $(tds[2]).text();
		var url = $(tds[3]).text();
		var source =  $(tds[4]).html();
		var newsType =  $(tds[5]).html();
		var extractNews = {};
		extractNews["extractNews.id"]=id;
		extractNews["extractNews.title"]=title;
		extractNews["extractNews.content"]=content;
		extractNews["extractNews.url"]=url;
		extractNews["extractNews.source"]=source;
		extractNews["extractNews.newsType"]=newsType;
		return  extractNews;
	}
	
	$("a[button_type=edit]").click(function() {
		var $this = $(this);
		var tr = $this.parents("td").parent();
		var tds = $("td",tr);
		
		var id = $this.attr("newsId");
		var title = $(tds[1]).html();
		var content = $(tds[2]).text();
		var url = $(tds[3]).text();
		var source =  $(tds[4]).html();
		var newsType =  $(tds[5]).html();
		$("input[serial=id]").val(id);
		$("input[serial=title]").val(title);
		$("textarea[serial=content]").val(content);
		$("input[serial=url]").val(url);
		//source = source.attr("href") + "::" + source.text();
		$("input[serial=source]").val(source);
		$("input[serial=newsType]").val(newsType);
		dialog.open("edit_news_dialog");
		
	});
	$("a[button_type=delete]").click(function() {
		var $this = $(this);
		var news = getNewsInfo($this);
		if(confirm("确认删除该条新闻吗？") == false){
			return ;
		} else {
			$.post('deleteWeiboNews.do', news, function(data) {
				if(data.success == false) {
					alert("删除失败");
				} else {
					var tr = $('#' + news["extractNews.id"].replace(":",""));
					var trs = $("tr",$("#weiboNews_tbody"));
					if(trs.length < 2){
						window.location.reload("force");
					} else {
						tr.fadeOut("slow",function (){
							tr.remove();
						});
					}
				}
			},"json");
		}
	});
	$("a[button_type=publish]").click(function() {
		var $this = $(this);
		var news = getNewsInfo($this);
		$("a[button_type=lotteryUser]").unbind();
		$("a[button_type=lotteryUser]").click(function (){
			news["publishToUserId"] = $(this).attr("weiboUserId");
			$.post('publishWeiboNews.do', news, function(data) {
				if(data.success == false) {
					alert("发布失败");
				} else {
					dialog.close("publish_news_dialog");
					var tr = $('#' + news["extractNews.id"].replace(":",""));
					var trs = $("tr",$("#weiboNews_tbody"));
					if(trs.length < 2){
						window.location.reload("force");
					} else {
						tr.fadeOut("slow",function (){
							tr.remove();
						});
					}
				}
			},"json");
		});	
		dialog.open("publish_news_dialog");
	});
	
	$("a[button_type=publish]").click(function() {
		
	});
	$(".edit-news-submit").click(function() {
		var jsonData = getJson("edit_news_dialog");
		var url = "updateWeiboNews.do";
		dialogId = "edit_news_dialog";
		var newsId = jsonData["extractNews.id"];
		
		for(var index in jsonData) {
			var elt = jsonData[index];
			if(elt == "") {
				alert("存在无效字段.");
				return;
			}
		}
		$.post(url, jsonData, function(data) {
			if(dialogId) {
				dialog.close(dialogId);
			}
			if(data.success == false) {
				alert("提交失败");
			} else {
				alert("提交成功");
				var tr = $('#' + newsId.replace(":",""));
				var tds = $("td",tr);
				$(tds[1]).html(jsonData["extractNews.title"]);
				$(tds[2]).html(jsonData["extractNews.content"]);
			}
		},"json");
	});
	
	//获取dialog下所有的字段并转换为json对象
	var getJson = function(dialogId) {
		var formToJson = {};
		$.each($("#" + dialogId).children(), function(i, elt) {
			if(null != $(elt).attr("serial")) {
				formToJson["extractNews." + $(elt).attr("serial")] = $(elt).val();
			}
		});
		return formToJson;
	};
});
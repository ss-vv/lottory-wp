var local_g = (window.location+'').split('/'); 
var basePath_ = local_g[0]+'//'+local_g[2]+'/'+local_g[3]; 

var select_id = 0;
function test()
{
	
}

$(document).ready(function () {
	$("#close_dig").click(
			function closeDialog()
			{ 
				$("#detail_app_dialog_edit" ).dialog("close");
				$('#okinfo').click();
				window.location.reload();//关闭后刷新
			}
	);
});


$(document).ready(function () {
//	$("#close_dig").click()
//	{
//		window.location.reload();
//	}
}
);

function getData(id)
{
//	alert(id);
	var data = {};
	data["basketballleague.leagueId"] = id;
	$.post(basePath_+"/basketballleagueinfo.do", data, function(ret) {
		if(ret.success == false) {
			alert("查询详情失败");
		} else {
			var dat = ret.data;
			$("#leagueId").text(dat.leagueId);          
			$("#chineseNameAll").text(dat.chineseName);
			$("#chineseName").text(dat.shortName);
			$("#color").text(dat.color); //
			$("#color").css("background-color",dat.color);
			$("#color").css("color","#FFFFFF");
			//编辑框
			$("#leagueId_edit").text(dat.leagueId);          
			$("#edit_chineseNameAll").text(dat.chineseName);
			$("#edit_chineseName").text(dat.shortName);
			$("#color_edit").val(dat.color); 
			
			
			switch(dat.countryId)  //含义和足球不同 有20多的 待确定
			{
			case 1:
				$("#countryId").text("欧洲联赛");
				$("#countryId_edit").text("欧洲联赛");
				break;
			case 2:
				$("#countryId").text("美洲联赛");
				$("#countryId_edit").text("美洲联赛");
				break;
			case 3:
				$("#countryId").text("亚洲联赛");
				$("#countryId_edit").text("亚洲联赛");
				break;
			case 4:
				$("#countryId").text("大洋洲联赛");
				$("#countryId_edit").text("大洋洲联赛");
				break;
			case 5:
				$("#countryId").text("非洲联赛");
				$("#countryId_edit").text("非洲联赛");
				break;
			}
			
			$("#country").text(dat.country);
			$("#currMatchSeason").text(dat.currMatchSeason);
			$("#logoUrl").attr("src",dat.logo);
			$("#countryImg").attr("src","");//数据源还没有国旗字段
			
			//编辑框
			$("#edit_country").text(dat.country);
			$("#edit_currMatchSeason").text(dat.currMatchSeason);
			$("#logoUrl_edit").attr("src",dat.logo);
			$("#countryImg_edit").attr("src","");//数据源还没有国旗字段
			
			
			
		}
	},"json");
}


//详情对话框
function footballinfo(id)
{	
	//alert("详情");
	getData(id);
	$("#detail_app_dialog" ).dialog(dialog.init());
	dialog.open("detail_app_dialog");
}
function editfootball(id)
{
	select_id = id;
	//编辑对话框
	getData(id);
	$("#detail_app_dialog_edit" ).dialog(dialog.init());
	dialog.open("detail_app_dialog_edit");
}

/**
 * 修改字段
 */
$(document).ready(function () {
	
$("span[id^='edit_']").live('dblclick',
function(){
    var span = $(this);
    var this_id = span.attr('id');
    var this_class = span.attr('class');
    //alert("需要修改的字段id"+this_id);
    var text = span.text();
    span.text="";
    var inputtxt = $("<input style='width:150px;height:30px;' class=\""+this_class+"\" />").val(text);
    inputtxt.blur(
    		function()
    		{
    			var newtext = inputtxt.val();
    			var data = {};
    			//alert(select_id);
    			data["basketballleague.leagueId"] = select_id;
    			data["key"] = span.attr("dataname");
    			
    			data["newdata"] = newtext;
    			//alert("修改为"+newtext);//    根据id ajax 修改字段数据  footballleagueedit
    			$.post(basePath_+"/basketballleagueedit.do", data, function(ret) {
    				if(ret.success == false) {
    					alert("查询详情失败");
    				} else {
    					$('#okinfo').click();
    				}
    			},"json");
    			
    			 
    			var newspan = $("<span class=\""+this_class+"\" id=\""+this_id+"\"></span>");
    			
    			newspan.html(newtext);
    			inputtxt.after(newspan);
    			inputtxt.remove();
    		}
    );
    span.after(inputtxt);
    span.remove();
});

});


$(document).ready(function () {
	var color_update = $("#color_edit");
	color_update.blur(function()
			{
				var newtext = color_update.val();
				var data = {};
				//alert(select_id);
				data["basketballleague.leagueId"] = select_id;
				data["key"] = "color";
				data["newdata"] = "#"+newtext;
				//alert("修改为"+newtext);//    根据id ajax 修改字段数据  footballleagueedit
				$.post(basePath_+"/basketballleagueedit.do", data, function(ret) {
					if(ret.success == false) {
						alert("查询详情失败");
					} else {
						$('#okinfo').click();
					}
				},"json");
		
			});
});

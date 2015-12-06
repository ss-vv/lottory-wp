	
var local_g = (window.location+'').split('/'); 
var basePath_ = local_g[0]+'//'+local_g[2]+'/'+local_g[3]; 
	/**
	 * 加载群信息
	 */	
	$(document).ready(function(){
		var url = basePath_+"/findGroupids.do";
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
			}
		},"json");
	});

	
	$(document).ready(function(){
        $("#sendMsg").bind("click",
            function() {                  
        	var gid = document.getElementById("selectid_send").value;
        	var text_con = document.getElementById("text_content").value;
        	if(text_con == null || text_con == "")
        		{
        			alert("内容不能为空");
        		}
        	else
        		{
        		var formToJson = {};
    			formToJson["groupid"] = gid;
    			formToJson["content"] = text_con;
    			
    			$.post(basePath_+"/sendContentToGroup.do", formToJson, function(data) {
    				if(data.success == false) {
    					alert("发送失败");
    				} else {
    					alert("发送成功");
    					//window.location.reload();
    				}
    			},"json");
        		}
			
          });
});

	
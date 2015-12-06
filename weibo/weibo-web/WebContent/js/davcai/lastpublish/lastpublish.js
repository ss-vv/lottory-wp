//最新发表  lastpublish   最热讨论hotdiscuss  
$(function(){
	//最热讨论
	  $.ajax({  
		type : "post", 
        url : "/ajax-hot-discuss",  
        dataType : 'json',
        success : function(data){
        	appendElementForLastPublish(data,"hotdiscuss");
        }  
      });
	  //最新发表
	    $.ajax({  
			type : "post", 
	        url : "/ajax-latest-publish",  
	        dataType : 'json',
	        success : function(data){
	        	appendElementForLastPublish(data,"lastpublish");
	        }  
	      });
})
var lastPublish_hotDiscuss_len=3;
function appendElementForLastPublish(data,$el){

	if(data && data.results){
		var general_header="general_header_"+$el;
        var general_header=eval(general_header);
		$("#"+$el).append(general_header);
		for(var i=0;i<data.results.length;i++){
			if(i<lastPublish_hotDiscuss_len){
				var result=data.results[i];
				$("#"+$el).append('<p class="hot-discuss-list" id=lp_'+i+'></p>');
				if(result.tags){
					$("#"+$el+" #lp_"+i).append('<span class="tag-name" style="background:'+result.tags[0].bgcolor+';color:'+result.tags[0].color+'">'+result.tags[0].name+'</span>');
					$("#"+$el+" #lp_"+i).append('<span class="tag-name" style="background:'+result.tags[1].bgcolor+';color:'+result.tags[1].color+'">'+result.tags[1].name+'</span>');
				}else{
					$("#"+$el+" #lp_"+i).append('<span class="tag-name color05">讨论</span>');
				}
				var content=$('<a href="http://www.davcai.com/'+result.user.weiboUserId+'/'+result.id+'" class="discuss-content">'+result.user.nickName+'&nbsp;:&nbsp;'+result.content+'</a>');
				$("#"+$el+" #lp_"+i).append(content);
				$("#"+$el+" #lp_"+i+" a").emotionsToHtml();
				var isHava_img=$("#"+$el+" #lp_"+i+" a").find("img");
				if(isHava_img){
					//isHava_img.width("20px").height("20px");
				}
	            $("#"+$el).append('<p class="hot-discuss-operate" id=op_'+i+'></p>');
	            var fcl=$("#"+$el+" #op_"+i);
	            fcl.append('<a>'+forwardCount(result)+'|</a>&nbsp;')
	            fcl.append('<a>'+commentCount(result)+'|</a>&nbsp;');
	            fcl.append('<a>'+likeCount(result)+'</a>');
	            $("#"+$el).append('<hr class="discuss-operate-bottom">');
			}
		}
	}
}
function forwardCount(result){
	var str_forward="转发";
	if(result.forwardCount && result.forwardCount>0){
		
		str_forward+="（"+result.forwardCount+"）";
	}else{
		str_forward+="（0）&nbsp;"
	}
	return str_forward;
}
function commentCount(result){
	var str_comment="评论";
	if(result.commentCount && result.commentCount>0){
		str_comment+="（"+result.commentCount+"）";
	}else{
		str_comment+="（0）&nbsp;"
	}
	return str_comment;
}
function likeCount(result){
	var str_like="赞";
	if(result.likeCount && result.likeCount >0){
		str_like+="（"+result.likeCount+"）";
	}else{
		str_like+="（0）&nbsp;"
	}
	return str_like;
}

var general_header_lastpublish='<h4 class="title">最新发表</h4>'
	 	                      +'<h6 class="right-more"><a href="/latestPublish">更多</a></h6>'
    	 	                  +'<hr class="hr0">';
var general_header_hotdiscuss='<h4 class="title">最热讨论</h4>'
	 	                     +'<h6 class="right-more"><a href="/hotDiscuss">更多</a></h6>'
	 	                     +'<hr class="hr0">';
//右侧推荐比赛
$(function(){
	var url="http://trade.davcai.com/df/bet/";

	  $.ajax({  
		type : "post",  
        url : "/recommend/ajax_recommend_match",  
        dataType : 'json',
        success : function(data){
        	appendElementForRecommend(data);
        }  
    });
})
function appendElementForRecommend(data){
	  if(data && data.length>0){
   	   $(".davcai-today-commend").append(general);
   	   for(var i=0;i<data.length;i++){
   		  var div='<div class="today-commend-content" id=recommend_'+i+'></div>';
   		  $(".davcai-today-commend").append(div);
   		  var lottery=data[i].lotteryId;
   		  var lotteryName_=getlotteryName(data[i].lotteryId);
   		  var guest=data[i].guestTeamName;
   		  var host=data[i].hostTeamName;
   		  var deadtime=getDeadTime(data[i].entrustDeadline);
   		  var odds_array=getOdds(data[i].odds);
   		  var options_=getOptions_(data[i].options);
   		  var ops_array=getCurrentOn(data[i].lotteryId,data[i].betMatchRecPO);
   		  $("#recommend_"+i).append("<h6 class='name'>"+lotteryName_+"</h6>")
   		  $("#recommend_"+i).append("<label class='time-end'>截止时间："+deadtime+"</label>");
   		  $("#recommend_"+i).append('<ul class="today-commend-content-team"></ul>');
   		  if(lottery!="JCLQ"){
   			//胜
   			  $("#recommend_"+i+" .today-commend-content-team").append(
       				  '<li _odd='+odds_array[0]+' class="today-commend-content-team-left '+getCurrentIsOn(ops_array,3)+'" op=3><p class="content-team-name">'+host+'<i></i></p> <p class="content-team-odds">'+odds_array[0]+'</p></li>');
       		  if(options_==3){
       			  //平
       			  $("#recommend_"+i+" .today-commend-content-team").append(
       				  '<li _odd='+odds_array[1]+' class="today-commend-content-team-middle '+getCurrentIsOn(ops_array,1)+'" op=1>'+odds_array[1]+'</li>'); 
       		  }
       		  //负
       		  $("#recommend_"+i+" .today-commend-content-team").append(
       				  '<li _odd='+odds_array[2]+' class="today-commend-content-team-right '+getCurrentIsOn(ops_array,0)+'" op=0> <p class="content-team-name">'+guest+'</p><p class="content-team-odds">'+odds_array[2]+'</p></li>');
   		  }else{
   			  //负
   			  $("#recommend_"+i+" .today-commend-content-team").append(
       				  '<li _odd='+odds_array[1]+' class="today-commend-content-team-left '+getCurrentIsOn(ops_array,1)+' "op=1><p class="content-team-name">'+guest+'<i></i></p> <p class="content-team-odds">'+odds_array[1]+'</p></li>');
   			  //只占位
   			  $("#recommend_"+i+" .today-commend-content-team").append('<div class="bp_null"></div>');
   			  //胜
   			  $("#recommend_"+i+" .today-commend-content-team").append('<li _odd='+odds_array[0]+' op="2" class="today-commend-content-team-right '+getCurrentIsOn(ops_array,2)+' "> <p class="content-team-name">'+host+'</p><p class="content-team-odds">'+odds_array[0]+'</p></li>');
   			  
   		  }
   		  $("#recommend_"+i).append('<p class="commend-content-money">金额：<i id=money_'+i+'>'+(ops_array.length*2)+'</i>元</p>');
   		  $("#recommend_"+i).append('<p class="commend-content-highprize">最高奖金：<i id=prize_'+i+'></i>元</p>');
   		  $("#recommend_"+i).append('<a href="javascript:void(0);" id=sub_'+i+' class="bet-button"></a>');
   		  var matchinfo={};
   		  matchinfo.lotteryId=lottery;
   		  matchinfo.playId=data[i].betMatchRecPO.playId;
   		  matchinfo.matchId=data[i].betMatchRecPO.matchId;
   		  matchinfo.code=getCode(lottery,data[i].betMatchRecPO.code);
   		  matchinfo.deadlinetime=getDeadTime_(data[i].entrustDeadline);
   		  $("#sub_"+i).data("matchinfo",matchinfo);
   		   prepareSubmit(i);
   		   prepareComputePrize(i);
   	   }
      }
	
}
//计算初始状态的奖金和投注金额
function prepareComputePrize(i){
	  var e=$("#recommend_"+i+" li[class*=current]");
		  var array_=new Array();
		  $.each(e,function(index,value){
			array_[index]=$(value).attr("_odd");
		  });
		  var m_odd=max_(array_);
		  var p='';
		  if(lottery=="BJDC"){
			 p= m_odd*2*0.65;
		  }else{
			  p=m_odd*2
		  }
		    p=p.toFixed(2);
		    if(isNaN(p)){
			 p=0;
		   }
		  $("#prize_"+i).html(p);
		  computePrize(i,lottery);
}
//判断投注的赛事时候合法
function prepareSubmit(index){
	 (function(){
		  var q=index;
		  $("#sub_"+q).live("click",function(){
			 var selects=$("#recommend_"+q+" li[class*=current]");
			  if(selects.size()==0){
				 alert("请选择一个赛果！"); 
			  }else{
				 var info= $(this).data("matchinfo");            				 
				 var now=new Date();
				 if(now>=info.deadlinetime){
					 alert("投注时间已截止！");
				 }else{
					 var url_="http://trade.davcai.com/df/bet/";
					 var v="";
					 if(info.lotteryId !="BJDC"){
						 v=(info.lotteryId+"_"+info.playId).toLowerCase(); 
					 }else{
						 var index=info.playId.lastIndexOf("_");
						 var play=info.playId;
						 play=play.substring(index+1);
						 v=(info.lotteryId+"_"+play).toLowerCase();
					 }
					
					 $("#recommend_").attr("action",url_+v+".html");
					 var seed='';
					 $.each(selects,function(index,value){
						 seed+=$(value).attr("op");
					 }) 
					 $("#recommend_code").val(info.code+''+seed);
					 $("#recommend_matchid").val(info.matchId);
					 $("#recommend_playid").val(info.playId); 
					 $("#recommend_").submit();
				 }
			  }
			  
		  }) 
	  })()
	
}
//绑定点击事件 计算奖金  投注金额
function computePrize(index,lottery){
	(function(){
		  var k=index;
		  var t_lottery=lottery;
		  $("#recommend_"+k+" li").live("click",function(){
			  $(this).toggleClass("current");
			  var eles=$("#recommend_"+k+" li[class*=current]");
			  var len=eles.size();
			  var array=new Array();
			  $.each(eles,function(index,value){
				array[index]=$(value).attr("_odd");
			  })
			  var s=array.join(",");
			  var t=max_(array);
			  $("#money_"+k).html(len*2);
			  if(isNaN(t)){
				  t=0
			  }
			  $("#prize_"+k).html(t*2);
		  })
		  
	  })()
	
}
//计算做大的赔率
 var max_=function(arr){
		  
		  if(arr && arr.length>0){
			   var n=0;
			  for(var m=0;m<arr.length;m++){
				  
				  if(!n){
					  n=arr[m];
				  }else{
					  if(arr[m]>n){
						  n=arr[m];
					  }
				  }
				  
			  }
		      return n;
		  }
		  
	  };
//推荐竞彩名称
function getlotteryName(lotteryId){
	if(lotteryId){
		
		return lotteryId=="JCZQ"?"竞彩足球":lotteryId=="JCLQ"?"竞彩篮球":lotteryId=="BJDC"?"北京单场":"";
	}
	
}
//截止时间
function getDeadTime(time){
	if(time){
		var array=time.split("T");
		var d=array[0].split("-");
		var t=array[1].split(":");
		if(d.length>0 && t.length>0){
			
			return d[1]+"-"+d[2]+" "+t[0]+":"+t[1]; 
		}
	}
}
//获取截止时间的times
function getDeadTime_(time){
	if(time){
		var array=time.split("T");
		var d=array[0].split("-");
		var t=array[1].split(":");
		if(d.length>0 && t.length>0){
			var date=new Date();
			date.setFullYear(d[0]);
			date.setMonth(d[1]-1);
			date.setDate(d[2]);
			date.setHours(t[0]);
			date.setMinutes(t[1]);
			date.setSeconds(t[2]);
			return date;//.getTime();
		}
	}
	
}
//可选项
function getOptions_(ops){
	if(ops){
		return ops.split(",").length;
	}
}

//将选项转化为数组
function getCurrentOn(lotteryid,rec){
	if(lotteryid && rec){
		var op='';
		if(lotteryid=="BJDC"){
			op=rec.code.substring(3);
		}else{
			op=rec.code.substring(4);
		}
		var array=new Array();
		for(var i=0;i<op.length;i++){
			
			array[i]=op.charAt(i);
		}
		return array;
	}
}
//获取code
function getCode(lottery,code){
	
	if(lottery && code){
		if(lottery == "BJDC"){
			return code.substring(0,3);
		}else{
			return code.substring(0,4);
		}
	}
}
//是否选中
function getCurrentIsOn(opt_array,op){
	 var current='';
	   
     if(opt_array){
    	  for(var i=0;i<opt_array.length;i++){
    		  if(opt_array[i]==op){
    			 current="current"; 
    		  }
    	  }
      }
      return current;
}
//获取赔率数组
function getOdds(odds){
	if(odds){
		return odds.split(",");
	}
}
var  general='<h4 class="title">今日推荐</h4>'
	         +'<h6 class="right-more"><a href="http://www.davcai.com/recommend/category_ALL" target="_blank">更多</a></h6>'
	 	     +'<hr class="hr0">'
	 	     +'<form id="recommend_" method="post" action="">'
	 	     +'<input type="hidden" name="bmrpo.code" id="recommend_code"/>'
	 	     +'<input type="hidden" name="bmrpo.matchId" id="recommend_matchid"/>'
	 	     +'<input type="hidden" name="bmrpo.playId" id="recommend_playid"/>'
	 	     +'<input type="hidden" name="bmrpo.seed" id="recommend_seed" value="0"/>'
	 	     +'</form>';
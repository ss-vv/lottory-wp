
$(document).ready(function(){
  $(".welcome-close-screen").click(function(){
    $(".welcome-boot-screen").hide("500");
  });
});

/*上面这段是开机画面welcome-close-screen部分*/


//menu
$(document).ready(function(){

  $(".welcome-client li.mainlevel").mouseover(function(){
  $(this).find('ul').show();//you can give it a speed
  });
  $(".welcome-client li.mainlevel").mouseout(function(){
  $(this).find('ul').hide();
  });
  
});

/*上面这段是 客户端下载部分*/




/*下面开始立即投注里面的输入数字*/
$(function(){
    $("#fbmoney").blur(function(){
	       
      var fbmoneyn= $(this).attr("fbchoicenum");
	    var fbmoneyv=$(this).val();
		if(isNaN(fbmoneyv)){
		    $(this).val(parseInt(fbmoneyn)*2);
		}else{
		   var lu=fbmoneyv%(fbmoneyn*2);
		   if(lu!=0){
		      $(this).val(parseInt(fbmoneyn)*2);
		   }
		}

	  });
});
/*这段是竞彩足球 输入框的部分*/


$(function(){
    $("#bbmoney").blur(function(){
	       
      var bbmoneyn= $(this).attr("bbchoicenum");
	    var bbmoneyv=$(this).val();
		if(isNaN(bbmoneyv)){
		    $(this).val(parseInt(bbmoneyn)*2);
		}else{
		   var lu=bbmoneyv%(bbmoneyn*2);
		   if(lu!=0){
		      $(this).val(parseInt(bbmoneyn)*2);
		   }
		}

	  });
});
/*这段是竞彩篮 输入框的部分*/



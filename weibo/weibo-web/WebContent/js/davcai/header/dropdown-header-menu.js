$(function(){
	$(".lottery-sun-middle").slideUp("slow");
	$(".lottery-sun-middle:eq(0)").slideDown("slow");
    $(".lottery-sun-top").bind("click",function(){
    	 var img=$(this).find("img");
    	 var def=img.attr("def");
            if(def=="1"){
            	$(this).next().slideUp("slow");
            	img.attr({"src":"../../../images/davcai/header/red-right-triangle.png","def":"0"});  	
            }else if(def=="0"){
            	$(this).next().slideDown("slow");
            	img.attr({"src":"../../../images/davcai/header/red-bottom-triangle.png","def":"1"});
            }
		});
 });
/*上面这段是点击lottery-sun-top展开下拉页面部分*/

/*下面开始lottery-nav-outer下面的选项卡切换*/
$(function(){
	$(".davcai-jl").hide();
	$(".davcai-zc").hide();
	$(".davcai-bd").hide();
	$(".davcai-ssq").hide();
	 $("#davcai-jz").click(function(){        /*竞足*/
		$(".davcai-jz").show();
		$(".davcai-jl").hide();
		$(".davcai-zc").hide();
		$(".davcai-bd").hide();
		$(".davcai-ssq").hide();
	});

	 $("#davcai-jl").click(function(){         /*竞篮*/   
		$(".davcai-jl").show();
		$(".davcai-jz").hide();
		$(".davcai-zc").hide();
		$(".davcai-bd").hide();
		$(".davcai-ssq").hide();
	});
	 $("#davcai-zc").click(function(){          /*足彩*/
		$(".davcai-zc").show();
		$(".davcai-jz").hide();
		$(".davcai-jl").hide();
		$(".davcai-bd").hide();
		$(".davcai-ssq").hide();
	});
	$("#davcai-bd").click(function(){          /*北单*/
		$(".davcai-bd").show();
		$(".davcai-jz").hide();
		$(".davcai-jl").hide();
		$(".davcai-zc").hide();
		$(".davcai-ssq").hide();
	});
	$("#davcai-ssq").click(function(){          /*双色球*/
		$(".davcai-ssq").show();
		$(".davcai-jz").hide();
		$(".davcai-jl").hide();
		$(".davcai-zc").hide();
		$(".davcai-bd").hide();
	});
});

/*上面结束lottery-nav-outer下面的选项卡切换*/

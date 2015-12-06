$(document).ready(function() {
	$('.dropdown').mouseout(function() {
		$(this).children(".dropdown-menu").hide();
	}).mouseover(function() {
		$(this).children(".dropdown-menu").show();
	});

	//彩种设置
	$(".groups > li").click(function() {
		var $this = $(this);
		var lotteryId = $(this).attr("id");
		$.each($(".groups > li"	), function(i, n) {
			if(lotteryId == $(n).attr("id")) {
				$this.addClass("lotteryButton");
				$("div[class='portfolio-wrapper " + $(n).attr("id") + "']").show();
			} else {
				$(n).removeClass("lotteryButton");
				$(n).removeClass("active");
				$("div[class='portfolio-wrapper " + $(n).attr("id") + "']").hide();
			}
		});
	});
	
	//展开赛事
	$(".expand").click(function() {
		var cont = $(this).text();
		if("展开" == cont) {
			$(this).text("收起");
			$(this).parent().parent().parent().next().css({display:"inline-table"});
		} else {
			$(this).text("展开");
			$(this).parent().parent().parent().next().css({display:"none"});
		}
	});
	$(".last").click(function() {
		$(this).prev().click();
	});
	
	//插入表情
    $("#message_face").jqfaceedit({
    	txtAreaObj:$("#status-box"),
    	containerObj:$('#emotionArea'),
    	top:30,
    	left:-40
    });
    
    //显示表情
	$("#show_face").click(function(){
		$('.show_emotion').html($('#status-box').val()).emotionsToHtml();
	});
	
	//推荐/晒单/合买按钮切换
	$(".lotteryScheme > li").click(function() {
		var $this = $(this);
		var lotteryId = $(this).attr("id");
		$.each($(".lotteryScheme > li"	), function(i, n) {
			if(lotteryId == $(n).attr("id")) {
				$this.addClass("scheme");				
			} else {
				$(n).removeClass("scheme");
				$(n).removeClass("active");
			}
		});
	});
	
	//首页右侧widget:晒单、合买按钮切换
	$(".shai-he > li").click(function() {
		var $this = $(this);
		var clz = $this.attr("class");
		if(clz.indexOf("shai") >= 0) {
			if($this.hasClass("no-curr")) {
				$this.next().addClass("no-curr");
				$this.next().removeClass("curr");
				$this.removeClass("no-curr");
				$this.addClass("curr");
				$(".data-shai").show();
				$(".data-he").hide();
			} else {
				
			}
		} else {
			$this.prev().removeClass("curr");
			$this.prev().addClass("no-curr");
			$this.addClass("curr");
			$this.removeClass("no-curr");
			
			$(".data-shai").hide();
			$(".data-he").show();
		}
	});
	
});











































































































































































































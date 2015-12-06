$(function() {
	fadeInFAQ();
	
	$('#faq').click(function() {
		var display = $('#faq_sub').css("display");
		if("block" == display) {
			$('#faq_sub').fadeOut("slow");
			$(this).find("img").attr("src", "/df/images/fup.png");
		} else {
			$('#faq_sub').fadeIn("slow");
			$(this).find("img").attr("src", "/df/images/fdown.png");
		}
	});
	
	$("#faq_sub").children("li").click(function() {
//		$(this).addClass("active-bg");
		$(".faq_content").children("li").css("display", "none");
		var clz = $(this).attr("handler");
		if(clz) {
			$(".faq_content").find("." + clz + "_li").css("display", "block");
		}
	});
});

var fadeInFAQ = function() {
	$('#faq_sub').fadeIn("slow");
};
$(document).ready(function() {
	var jczqMatchs = $("#jczq_matchs");
	var jclqMatchs = $("#jclq_matchs");
	$(".match-nav .li-bg").mouseover(function() {
		jczqMatchs.css("margin-top","78px");
		jclqMatchs.css("margin-top","78px");
		$(".jj-layer").show();
	});
	$(".match-nav .li-bg").mouseout(function() {
		jczqMatchs.css("margin-top","0px");
		jclqMatchs.css("margin-top","0px");
		$(".jj-layer").hide();
	});
	
	$(".jj-nav-sp").mouseover(function() {
		$(".jj-nav-sp ul").show();
	});
	$(".jj-nav-sp").mouseout(function() {
		$(".jj-nav-sp ul").hide();
	});
	
	$('.jj-nav-calendar').datepicker({
        dateFormat: 'yy-mm-dd',
        prevText: '上月',
        nextText: '下月',
        dayNamesMin: ['日','一','二','三','四','五','六'],
        monthNames: ['一月','二月','三月','四月','五月','六月','七月','八月','九月','十月','十一月','十二月']
    });
});

//赛事后面的小三角，点击聚焦的实话，旋转180度
$(function(){
   $(".jj-nav-match").bind("mouseover", function() {
      $(".icon_dwn").addClass("flipy");
   }).bind("mouseout", function() {
    $(".icon_dwn").removeClass("flipy");	
   });
});

//竞猜胜平负SP后面的小三角，点击聚焦的实话，旋转180度
$(function(){
   $(".jj-nav-sp").bind("mouseover", function() {
      $(".icon_dwnb").addClass("flipy");
   }).bind("mouseout", function() {
    $(".icon_dwnb").removeClass("flipy");	
   });
 });

//日历框后面的小三角，点击聚焦的实话，旋转180度
$(function(){
   $(".jj-nav-time").bind("mouseover", function() {
      $(".icon_dwnc").addClass("flipy");
   }).bind("mouseout", function() {
    $(".icon_dwnc").removeClass("flipy");	
   });
 });
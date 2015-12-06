$(function() {
	var userTmp = 
		'<div id="gallery">'
	   +'    <span class="image-wrapper">'
	   +'        <a href="http://www.davcai.com/{0}/profile" title="{1}">'
	   +'       <img alt="{1}" src="{2}"  height="100" width="100"></a>'
	   +'   </span>'
	   +'   <div id="caption" class="caption-container">'
	   +'       <div id="image-frame">'
	   +'           <span class="point"><span class="sky"></span></span>'
	   +'           <div class="image-content">'
	   +'               <h2>{1}</h2>'
	   +'               <div style="font-size: 12px;line-height: 24px;color: #888;" class="item_desc">'
	   +'                   {3}'
	   +'               </div>'
	   +'           </div>'
	   +'       </div>'
	   +'   </div>'
	   +'</div>'
	var detailLi = $("#show-recommend-user-detail");
	var index = 0;
	function showRcmdUserDetail(){
		index ++;
		if(index > 11){
			index = 0;
		}
		var li = $("li",$("#show-recommend-users"))[index];
		var li = $(li);
		var weiboUserId = li.attr("weiboUserId");
		var nickName = li.attr("nickName");
		var headImageURL = li.attr("headImageURL");
		var individualResume = li.attr("resume");
		var html = $.format(userTmp,weiboUserId,nickName,headImageURL,individualResume);
		detailLi.html(html);
		setTimeout(function (){
			$("#gallery").fadeOut(500,showRcmdUserDetail);
		},3000);
	}
	showRcmdUserDetail();
});


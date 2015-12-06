$(document).ready(function (){
	var tmpl = '<li  class="niuqison">'
			+'		<a target="_blank" href="http://www.davcai.com/{{weiboUserId}}/profile"><img class="niu_pic" src="{{headImageURL}}" style="width: 53px;height: 53px;"></a>'
			+'		<span  class="niu_name">'
			+'			<a target="_blank" href="http://www.davcai.com/{{weiboUserId}}/profile" style="">{{nickName}}</a>'
			+'		</span>{{{certificationView}}}'
			+'		<span class="niu_button">	{{{followBtn}}} </span>'
			+'		</div>'
			+'	</li>';
	var followBtnTmpl = '<button title="加关注" _userid="{{weiboUserId}}" flag="followFlag" style="" class="addFriendBtn">+加关注</button>'; 
	var unFollowBtnTmpl = '<button class="addFriendBtn" title="取消关注" _userid="{{weiboUserId}}" flag="unFollowFlag">取消关注</button>'; 
	var $analyzeUserDiv = $('[_id="analyze_user_ul_id"]');
	if($analyzeUserDiv.length > 0){
		$.getJSON("/recommend/get_analyze_expert",function (data){
			if(data.length > 0){
				var html="";
				for ( var i = 0; i < data.length; i++) {
					var nickName = data[i].nickName;
					var weiboUserId = data[i].weiboUserId;
					var headImageURL = data[i].headImageURL;
					var beFollowed  = data[i].beFollowed;
					var certificationType = data[i].certificationType;
					html += $.mustache(tmpl,{
						nickName:nickName,
						weiboUserId:weiboUserId,
						headImageURL:headImageURL,
						followBtn: beFollowed ? $.mustache(unFollowBtnTmpl,{
							weiboUserId:weiboUserId
						}):$.mustache(followBtnTmpl,{
							weiboUserId:weiboUserId
						}),
						certificationView:function (){
							if(certificationType == 1){
								return '<i class="niu" title="大V彩牛人" style="background:url(\'../images/niu.png\') 50% no-repeat;width: 32px;height: 15px;position: relative;display: inline-block;float: right;margin-top: -20px;margin-right: 0px;"></i>';
							} else {
								return '';
							}
						}
					});
				}
				$analyzeUserDiv.html(html);
				//给关注按钮绑定关注和取消关注事件
				$(".addFriendBtn",$analyzeUserDiv).click(function (event, a, b) {
					followAndUnFollow( $(event.target));
				});
				$('[_id="oneKeyFollow"]').click(function (){
					var $this = $(this);
					$this.attr("disabled",true);
					var lis = $analyzeUserDiv.find("li");
					lis.each(function (){
						$(this).find('.addFriendBtn[flag="followFlag"]').trigger("click");
					});
				});
			} else {
				$analyzeUserDiv.closest("li").hide();
			}
		});
	}
});
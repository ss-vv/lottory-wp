$(function (){
	$("#interest_user_list").interestUser({"id":"#interest_user_list","listSize":3,"hidden":true,"winningList":true,"_url":LT.Settings.URLS.user_relationship.interest_user});
	$("#interest_user_list").parent().find(".change a").click(function (){
		$("#interest_user_list").interestUser({
			changeUser:true
		});
	});
	$("#winning_user_list").interestUser({"id":"#winning_user_list","listSize":3,"hidden":false,"_url":LT.Settings.URLS.winningnews.winningnew});
	isLoginForAddBtn();
});

(function ($){
	var interestUser = function ($el,option){
		this.options = $.extend({},$.fn.interestUser.defaults,option);
		this.$el = $el;
		this.$tmpl = $("#interest-user-tmpl");
		this.$loading = $el.find(".loading");
		this.init();
	};
	interestUser.prototype = {
		init:function (){
			var $this = this;
			$.ajax({  
				type : "post",  
		        url : $this.options._url,//eval($($this.options.id).attr("_url")),  
		        dataType : 'json',
		        success : function(data){
		        	$this.data = data;  
		        	$this.render();
		        	$this.bind();
		        }  
		    });
		},
		render:function (){
			this.$loading.hide();
			if(this.data.results.length < 1){
				this.$el.html('<li style=" text-align:center">'//position: relative;
						+ '没有啦'
						+ '</li>');
				return ;
			}
			var $this_=this;
			//解析结果
			var afterAnalysis=function(){
				var results=$this_.data.results;
				
				for(var i=0;i<results.length;i++){
					//转换为中文显示
					if(results[i].lotteryId == "JCZQ"){
						
						results[i].lotteryId="竞彩足球";
					}else if(results[i].lotteryId == "JCLQ"){
						
						results[i].lotteryId="竞彩篮球";
					}else if(results[i].lotteryId == "BJDC"){
						
						results[i].lotteryId="北京单场";
					}
					//转换名称率
					if(results[i].ratio){
						var hit=results[i].ratio>0?parseInt(results[i].ratio * 100)+"%":"";
						results[i].ratio=hit;
						
					}
					if(results[i].winRate){
						var winRate=parseInt(results[i].winRate*100)+"%";
						results[i].winRate=winRate;
					}
					
					if(results[i].bonus_){
						var bonus=parseInt(results[i].bonus_);
						if(bonus>10000){
							var bwan=parseInt(bonus/10000);
							var bqian=parseInt(bonus%10000/1000);
							results[i].bonus_=bwan+"."+bqian+"万元"
						}else{
							results[i].bonus_=bonus+"元";
							
						}
						
					}
					if(results[i].totalBonus){
						var totalBonus=results[i].totalBonus;
						totalBonus=parseInt(totalBonus);

						if(totalBonus>10000){
							var wan=parseInt(totalBonus/10000);
							var qian=parseInt(totalBonus%10000/1000);
							results[i].totalBonus=wan+"."+qian+"万元"
						}else{
							results[i].totalBonus=parseInt(totalBonus)+"元";
						}
						
					}
				}
				
				return results;
			}
			var html = $.mustache(this.$tmpl.html(),{
				users:afterAnalysis(),
				certificationFlag:function (){
					if(this.certificationType == 1){
						return $("#certification-flag-tmpl").html();
					}
				}
			});
			this.$el.append(html);//所有li插入推荐列表
			var $lis = this.$el.find("li");
			for(var i=0; i<$lis.length; i++){//隐藏索引超过maxSize的部分
				if(i >= this.options.maxSize){
					$($lis[i]).hide();
				}
			}
		},
		bind:function (){			
			var $el = this.$el;
			var $hidden=this.options.hidden;
			var maxSize = this.options.maxSize;
			var $followContainer = this.$el.find("[_id='follow-container-div']");
			//添加“加关注”按钮
			$followContainer.each(function (){
				
				var $this = $(this);
				$this.addFollowBtn({
					flag:'follow',
					display:$hidden,//关注后是否隐藏
					userId:$this.attr("_userId"),
					success:function ($this,$op){ //关注成功回调
						var $li = $this.closest("li");
						var $next = $li.next();
						var $hidden=$op;
						if($hidden == true){
							$li.fadeOut("slow",function (){
								$li.remove();
								var $lis = $el.find("li");
								if($lis.length < 1){
									$el.html('<li style="text-align:center">'//position: relative;
											+ '没有了 '
											+ '</li>');
								} else if($lis.length > maxSize) {
									for(var i=0;i<$lis.length;i++){
										var _li = $($lis[i]);
										if(!_li.is(':visible')){
											break;
										}
									}
									if($next.find(".interest-name").text() ==
										$($lis[i]).find(".interest-name").text()){
										$next.show();
									} else {
										$next.before($($lis[i]).show());
									}
								}
							});
							
						}
						
					}
				});
			});
		},
		refresh:function(option){
			this.options = $.extend({},this.options,option);
			if(this.options.changeUser){
				var lis = this.$el.find('li');
				if(lis.length <=3){
					return '';
				}
				var i=0;
				for(;i<lis.length;i++){
					var $li = $(lis[i]);
					if($li.is(':visible')){
						break;
					}
				}
				i+=3;//需要显示的第一个用户索引
				var j=0;//已经选到的用户数
				var u = new Array();
				for(;i<lis.length && j < 3;i++,j++){
					u.push(i);
				}
				if(u.length <3){
					for(i=0;i<lis.length;i++){
						u.push(i);
						if(u.length >=3){
							break;
						}
					}
				}
				lis.each(function (){
					$(this).hide();
				});
				for(i=0;i<u.length;i++){
					$(lis[u[i]]).show();
				}
			}
		}
	}
	$.fn.interestUser = function (option){
		if(option.listSize){
			$.fn.interestUser.defaults={maxSize:option.listSize};	
		}
		return this.each(function() {
			var $this = $(this),
				data = $this.data('interestUser');
	          if (!data) {
	        	  $this.data('interestUser', (data = new interestUser($this, option)));
	          } else {
	        	  data.refresh(option);
	          }
        });
	};
	$.fn.interestUser.defaults = {
		maxSize:6
	};
})(window.jQuery);

;(function($) {
	var betRecordView = function(element, options) {
		this.$elt = $(element);
		this.options = $.extend({}, $.fn.betRecordView.defaults, options);
		this.$tpl = $("#bet-record-tpl");
		this.$lottery = new lottery();
	    this.init();
	}
	betRecordView.prototype = {
		init:function() {
			this.rend();
		},
		rend:function() {
			if(typeof this.options.bet == 'string') {
				return;
			}
			var self = this;
			var dataFlag = '';
			if(this.options.bet) {
				var len = this.options.bet.length;
				if(len && len > 0) {
					dataFlag = true;
				}
			}
			var segment = $.mustache(this.$tpl.html(), {
				scheme:this.options.bet,
				tips:dataFlag,
				_lotteryId:self.options.lotteryId,
				betUrl:function() {
					var lotteryId = self.options.lotteryId;
					switch(lotteryId) {
					case '':
					case 'JCZQ':
		                return "http://trade.davcai.com/df/bet/jczq_80_zc_2.html";
					case 'JCLQ':
						return "http://trade.davcai.com/df/bet/jclq_06_lc_2.html";
					case 'CTZC':
						return "http://trade.davcai.com/df/bet/ctzc_24_zc_14.html";
					case 'BJDC':
						return "http://trade.davcai.com/df/bet/bjdc_spf.html";
					}
				},
				_showScheme:function() {
					var isPublishShow = this.isPublishShow;
					var showScheme = this.showScheme;
					if(showScheme == "1" || isPublishShow == "1") {
						return true;
					}
					return false;
				},
				_isShowAction:function() {
					if(this.lotteryId == "JCZQ" || this.lotteryId == "JCLQ") {
						return '';
					}
					return true;
				},
				_type:function() {
					return $.statusTool().buyType(this.type);
				},
				_status:function() {
					if(this.type == 1 && (this.status == 0 || 
							this.status == 1 || this.status == 2)) {
						return "进度" + this.progress + "%";
					} else {
						return $.statusTool().status(this.status);
					}
				},
				_lotteryName:function() {
					return self.$lottery.getName(this.lotteryId);
				},
				_createdTime:function() {
					return this.createdTime.substr(0, 10)
				},
				_winAmount:function() {
					if(this.status == 0 || 
						this.status == 1 || 
						this.status == 2 ||
						this.status == 5101) {
						return '--';
					}
					if(this.betRecordId && this.betRecordId > 0) {
						return this.groupWinAmount + "元";
					}
					return this.afterTaxBonus + "元";
				},
				_buyAmount:function() {
					if(this.betRecordId && this.betRecordId > 0) {
						return this.groupBetMount;
					}
					return this.buyAmount;
				},
				_detailSchemeUrl:function() {
					return $.statusTool().detailScheme(this.type, this.showScheme, 
							this.lotteryId, this.id);
				},
				switchHomeGuest:function() {
					if(this.lotteryId == "JCLQ") {
						return '客队VS主队';
					}
					return '主队VS客队';
				},
	    		switchHomeGuest:function() {
	    			if(this.lotteryId == "JCLQ") {
	    				return '客队VS主队';
	    			}
	    			return '主队VS客队';
	    		}
			});
			
			$(self.$elt).empty();
			$(self.$elt).append(segment);
			
			var schemeList = this.options.bet;
			var schemeViews = $(self.$elt).find('[data-type="scheme-view"]');
			for(var i=0; i<schemeList.length; i++) {
				var scheme = schemeList[i];
				$(schemeViews[i]).schemeView({
					scheme:scheme,
					tmpl:"#new-record-bet-scheme-tmpl"
				});
			}
			
			this.bind();
		},
		bind:function() {
			this.showScheme();
		},
		showScheme:function() {
			var self = this;
			//投注记录展开收起事件
			$(self.$elt).find(".lottery-sun-top").click(function(e) {
				var target = e.target || e.srcTarget; 
				var isA = $(target).is("a");
				if(isA) {
					var type = $(target).attr("type");
					if(type == "shareScheme") {
						var isShow = $(target).attr("isShow");
						if(!isShow || isShow == "false") {
							$.msgbox('方案需要晒单之后才能进行分享', $(target), {success:false});
							return;
						}
					}
					self.schemeAction($(target));
					return;
				}
				var jquery$this = $(this);
				var icon_right = "/assets/js/bet/images/red-right-triangle.png";
				var icon_down = "/assets/js/bet/images/red-bottom-triangle.png";
				var displayFlag = $(this).next().css("display");
				//保证快速点击时也能保证箭头和方案详情数据的一致性
				if(displayFlag == 'none') {
					$(this).next().slideDown("slow");
					$(this).find("img").attr({src:icon_down});
				} else {
					$(this).next().slideUp("slow");
					$(this).find("img").attr({src:icon_right});
				}
				
				var schemeType = $(this).attr("data-type");
				var schemeId = $(this).attr("data-scheme-id");
				var showScheme = $(this).attr("data-show-scheme");
				var recordElt = $(this);
				var betSchemeDetail = recordElt.parent().find('[data-type="bet-scheme-detail"]');
				var isData = betSchemeDetail.attr("_is_data");
				if(isData && isData == "true") {
					return;
				}
				
//				$(self).append('<div class="loading" style="height: 40px;"></div>');
//				$.ajax({
//		        	type:"post",
//		        	dataType:'json',
//		        	data:{"schemeId":schemeId, "schemeType":schemeType, "showScheme":showScheme},
//		    		url:self.options.ajaxViewBetScheme,
//		    		success : function(json, e) {
//		    			if(json && json.data) {
//		    				betSchemeDetail.schemeView({
//			        			scheme:json.data,
//			        			tmpl:"#bet-record-scheme-tmpl"
//			        		});
//		    				betSchemeDetail.attr("_is_data", "true");
//		    				jquery$this.next().slideDown("slow");
//		    				$(self).find(".loading").remove();
//		    			}
//		    		},
//		    		error:function (data, e){
//		    		}
//		    	});
			});
			//查看更多投注记录
			$(self.$elt).find("._seeMore").click(function() {
				var _lotteryId = $(this).attr("lotteryId");
				window.location.href = "http://trade.davcai.com/ac/record/bet.do";
			});
		},
		schemeAction:function(action) {
			var type = action.attr("type");
			var schemeId = action.attr("_schemeid");
			if(type == "detailScheme") {
				return;
			} else if(type == "showScheme") {
				var param = {"schemeId":schemeId};
				jQuery.ajax({
					type:"post",
					url:"http://www.davcai.com/bet/aj_showSchemeWeibo.do",
					data:param,
					dataType:"json",
					success: function(msg){
						var result = "失败";
						if(msg) {
							if(msg.success == true) {
								$.msgbox("晒单请求已发出", action, {success:true});
								var weiboUrl = msg.data.weiboUrl;
								action.parent().find("a[type='shareScheme']").attr("isshow", "true");
								action.parent().find("a[type='shareScheme']").attr("shareurl", weiboUrl);
								return;
							} else {
								result = msg.data.desc;
							}
						}
						$.msgbox(result, action, {success:false});
		            }
				});
				return;
			}
		}
	};
	
	$.fn.betRecordView = function (option) {
		return this.each(function(input_field) {
			new betRecordView(this, option);
		});
	};
	$.fn.betRecordView.defaults = {
		ajaxViewBetScheme : LT.Settings.URLS.betRecord.ajaxViewBetScheme,
	};
})(window.jQuery);
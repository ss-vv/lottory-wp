(function($)
{
    var cometd = $.cometd;

    function _connectionEstablished() {
    	log('CometD连接建立');
    }

    function _connectionBroken()
    {
    	log('CometD连接异常');
    }

    function _connectionClosed()
    {
    	log('CometD连接关闭');
    }

    // Function that manages the connection status with the Bayeux server
    var _connected = false;
    function _metaConnect(message)
    {
        if (cometd.isDisconnected())
        {
            _connected = false;
            _connectionClosed();
            return;
        }

        var wasConnected = _connected;
        _connected = message.successful === true;
        if (!wasConnected && _connected)
        {
            _connectionEstablished();
        }
        else if (wasConnected && !_connected)
        {
            _connectionBroken();
        }
    }
    var DEBUG = false;
    var log = function() {
    	if(DEBUG == true && console.log) {
    		console.log(arguments[0]);
    	}
    }
    function print() {  
    	var ele = "";  
    	var obj = arguments[0];
    	for (var property in obj) {  
    		ele = ele + "\n "+property + ": " + obj[property] ;  
    	}
    	log(ele);
	}

    function unsubscribe(subcribtion,channel){
    	cometd.unsubscribe(subcribtion, 
			function(unsubscribeReply)
			{
	    		if(unsubscribeReply.successful){
	    			log(channel + ' 取消订阅成功');
	    		}
			}
    	);
    }
   
    function subscribeChannels(channels){
    	if(channels){
    		for(var index in channels){
          		subscribeChannel(channels[index]);//订阅各channel
          	}
    	}
    }
    
 // Function invoked when first contacting the server and
    // when the server has lost the state of this client
    function _metaHandshake(handshake)
    {
        if (handshake.successful === true)
        {
            cometd.batch(function(){
            	log("retry sub channelList:" + channelList);
            	subscribeChannels(channelList);
            });
        }
    }

    // Disconnect when the page unloads
    $(window).unload(function()
    {
        cometd.disconnect(true);
    });

   var cometURL = "http://push.davcai.com/push-server/comet";
    cometd.configure({
        url: cometURL,
        logLevel: 'error'
    });
    
    cometd.addListener('/meta/handshake', _metaHandshake);
    cometd.addListener('/meta/connect', _metaConnect);
    cometd.handshake();
    
	//绑定新推送消息事件到使用推送消息的模块
	$("body").bind("newPushMessage",
		function(e,match){
			if(match.type && match.matchId){
				var matchChannelIdentifer = 'match_' + match.type + '_' + match.matchId;
				var score;
				if(match.type == "BASKETBALL"){
					score= match.guestScore+ ":" +match.homeScore ;
				}else{
					score= match.homeScore+ ":" +match.guestScore ;
				}
				 
				var matchStatus = match.state;
				var channelIdenf = $("[channelIdentifer="+matchChannelIdentifer+"]");
				
				log(channelIdenf.text())
				var progress = channelIdenf.find(".pgs");
				var scorePanel = channelIdenf.find("span");
				
				if(matchStatus == "0") {
					scorePanel.addClass("score-reset");
					score = '未开赛';
				} else {
					scorePanel.removeClass("score-reset");
					var stateDesc = match.stateDesc;
					var status = '';
					if(match.type == "FOOTBALL" && match.playingTime > 0) {
						status = '<span class="playingTime">' + match.playingTime + '</span>' + 
							'<img src="/assets/js/bet/images/in.gif" />';
					} else if(match.type == "FOOTBALL") {
						status = stateDesc;
					} else if(match.type == "BASKETBALL") {
						//time = match.remainTime;
						status = stateDesc;
					}
					progress.html(status);
					//progress.html(30);
					callbackPlayingTime(channelIdenf);
				}
				scorePanel.text(score + "");
        	}
		}
	);
	
	var channelList = [];
	//绑定订阅新渠道事件到推送消息的模块
	$("body").bind(
		"subscribeNewChannel",
		function(e,channelObj){
			channelList.push(channelObj);
			subscribeChannel(channelObj);
		}
	);
	
    function subscribeChannel(channelObj){
    	if(channelObj&&channelObj.channel){
        	var subcribtion=null;
        	var additional={
    			actionType:'init',
    			matchType:channelObj.matchType,
    			matchId:channelObj.matchId + ''
        	};
        	subcribtion= cometd.subscribe(
    			channelObj.channel, 
    			function(message){
    				log('message callback :');
    				print(message.data)
                	if(message.data){
                		//触发自定义的newPushMessage事件
                		$("body").trigger("newPushMessage",message.data);
                		if(message.data.shouldUnsubscribe 
                				|| message.data.state == "-1") {
                			unsubscribe(subcribtion,channelObj.channel);
                		}
                	}
                },
                additional,
                function(subscribeReply){
                	if(subscribeReply.successful){
                		log(channelObj.channel + '订阅成功');
                	}
                }
            );
    	}
    }
    
    var array_contains = function(source, obj) {
        var i = source.length;
        while (i--) {
            if (source[i] === obj) {
                return true;
            }
        }
        return false;
    };
    //calculate playingTime
    var channelIds = [];
    function callbackPlayingTime(channelIdList) {
    	var channelId = channelIdList[0];
    	var id = $(channelId).attr("channelidentifer");
    	var isHave = array_contains(channelIds, id);
    	if(true == isHave) {
    		return;
    	} else {
    		channelIds.push(id);
    	}
    	var calcInter =  window.setInterval(function() {
    		var obj = $(channelId).find("span.playingTime");
			var prgTime = parseInt($(obj).text());
			if(!prgTime || prgTime > 100 || prgTime <= 0) {
				channelIdList.find("span.playingTime").text('');
				window.clearInterval(calcInter);
			} else {
				var prgTime = prgTime + 1;
				channelIdList.find("span.playingTime").text(prgTime);
			}
    	}, 60000);
    }
    
})(jQuery);
(function($)
{
    var cometd = $.cometd;

    $(document).ready(function()
    {
    	//绑定新推送消息事件到使用推送消息的模块
    	$("body").bind(
        		"newPushMessage",
        		function(e,match){
        			if(match.type&&match.matchId){
                		var matchChannelIdentifer='match_'+match.type+'_'+match.matchId;
                    	$("[channelIdentifer="+matchChannelIdentifer+"]").find('#home-team #score').text(match.homeScore);
                    	$("[channelIdentifer="+matchChannelIdentifer+"]").find('#guest-team #score').text(match.guestScore);
                	}
        		}
    	);
    	
    	//显示新比赛div
    	function showNewMatch(channelObj){
    		console.log(channelObj);
    	}
    	
    	//绑定订阅新渠道事件到推送消息的模块
    	$("body").bind(
        		"subscribeNewChannel",
        		function(e,channelObj){
        			subscribeChannel(channelObj);
        			showNewMatch(channelObj);
        		}
    	);
    	//触发订阅新渠道事件
    	$("#subscribeNewChannel").delegate("button","click",function(){
    		var newChannelName=$("#newChannelName").val();
    		var matchId=$("#matchId").val();
    		var matchType=$("#matchType").val();
    		var newChannel={
    				channel:newChannelName,
    				matchId:matchId,
    				matchType:matchType
    				};
    		$("#subscribeButton").trigger("subscribeNewChannel",newChannel);
    	});
    	
    	
        function _connectionEstablished()        {
           $('#status').append('<div>CometD连接建立</div>');
        }

        function _connectionBroken()
        {
            $('#status').append('<div>CometD连接异常</div>');
        }

        function _connectionClosed()
        {
            $('#status').append('<div>CometD连接关闭</div>');
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
        
        function unsubscribe(subcribtion,channel){
        	cometd.unsubscribe(subcribtion, 
        			function(unsubscribeReply)
        			{
        	    		if(unsubscribeReply.successful){
        	    			var message='<div>'+channel+'取消订阅成功</div>';
                    		$('#status').append(message);
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
        
        function subscribeChannel(channelObj){
        	if(channelObj&&channelObj.channel){
            	var subcribtion=null;
            	var additional={
//            			actionType:'initOdds',
            			matchType:channelObj.matchType,
            			matchId:channelObj.matchId
            	};
            	subcribtion= cometd.subscribe(
            			channelObj.channel, 
	            			function(message){
	                        	if(message.data){
	                        		$("body").trigger("newPushMessage",message.data);//触发自定义的newPushMessage事件
	                        		//showMatchScore(message.data);
	                        		if(message.data.shouldUnsubscribe){
	                        			unsubscribe(subcribtion,channelObj.channel);
	                        		}
	                        		
	                        	}
	                        },
	                        additional,
	                        function(subscribeReply){
	                        	if(subscribeReply.successful){
	                        		var message='<div>'+channelObj.channel+'订阅成功</div>';
	                        		$('#status').append(message);
	                        	}
	                        }
                        );
        	}
        	
        }
        
        
        // Function invoked when first contacting the server and
        // when the server has lost the state of this client
        function _metaHandshake(handshake)
        {
            if (handshake.successful === true)
            {
                cometd.batch(function()
                {
                	var channels=[  
                	              {
                	            	  channel:'/publish/odds/JCZQ',
                	              }
                	           ];
                	subscribeChannels(channels);
                });
            }
        }

        // Disconnect when the page unloads
        $(window).unload(function()
        {
            cometd.disconnect(true);
        });

        var cometURL = "http://182.92.191.193:18080/push-server/comet";
//        cometURL = "http://localhost:18080/push-server/comet";
//        var cometURL = "http://192.168.199.195:18080/push-server/comet";
        cometd.configure({
            url: cometURL,
            logLevel: 'debug'
        });

        cometd.addListener('/meta/handshake', _metaHandshake);
        cometd.addListener('/meta/connect', _metaConnect);

        cometd.handshake();
        
    });
})(jQuery);

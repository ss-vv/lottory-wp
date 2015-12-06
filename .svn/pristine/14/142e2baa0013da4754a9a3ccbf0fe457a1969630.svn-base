;(function($){
    var cometd = $.cometd;
    var _connected = false;
    var DEBUG = false;
    var subcribtionMap = new Map();
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
    function _connectionEstablished() {
    	log('CometD连接建立');
    }
    function _connectionBroken(){
    	log('CometD连接异常');
    }
    function _connectionClosed(){
    	log('CometD连接关闭');
    }
    function _metaConnect(message){
        if (cometd.isDisconnected()){
            _connected = false;
            _connectionClosed();
            return;
        }
        var wasConnected = _connected;
        _connected = message.successful === true;
        if (!wasConnected && _connected){
            _connectionEstablished();
        } else if (wasConnected && !_connected){
            _connectionBroken();
        }
    }
    function unsubscribe(subcribtion,channelObj){
    	cometd.unsubscribe(subcribtion, 
			function(unsubscribeReply){
	    		if(unsubscribeReply.successful){
	    			console.log(channelObj.channel + '取消订阅成功');
	    		} else {
	    			console.log(channelObj.channel + '取消订阅失败');
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
    
    function _metaHandshake(handshake){
        if (handshake.successful === true)
        {
            cometd.batch(function(){
            	log("retry sub channelList:" + channelList);
            	subscribeChannels(channelList);
            });
        }
    }
    $(window).unload(function(){
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
	var channelList = [];
	//绑定订阅新渠道事件到推送消息的模块
	$("body").bind("subscribeNewChannel",function(e,channelObj){
		channelList.push(channelObj);
		subscribeChannel(channelObj);
	});
	$("body").bind("unsubscribeChannel",function(e,channelObj){
		subcribtion = subcribtionMap.get(channelObj.channel);
		if(subcribtion){
			unsubscribe(subcribtion,channelObj);
			subcribtionMap.put(channelObj.channel, null);
		}
	});
    function subscribeChannel(channelObj){
    	if(channelObj&&channelObj.channel){
        	var subcribtion=null;
        	var additional={};
        	subcribtion= cometd.subscribe(channelObj.channel, 
	        	function(message){
					log('message callback :');
					print(message.data)
	            	if(message.data){
	            		//触发渠道订阅handeler
	            		$("body").trigger(channelObj.handelerName,message.data);
	            	}
	            },
	            additional,
	            function(subscribeReply){
	            	if(subscribeReply.successful){
	            		console.log(channelObj.channel + '订阅成功');
	            	} else {
	            		console.log(channelObj.channel + '订阅失败');
	            	}
	            }
	        );
        	subcribtionMap.put(channelObj.channel, subcribtion);
    	}
    }
})(jQuery);
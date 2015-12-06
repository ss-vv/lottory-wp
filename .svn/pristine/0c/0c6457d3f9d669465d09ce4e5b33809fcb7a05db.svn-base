$(document).ready(function() {
	// 文件上传
	var uploadEl = $('#uploadImage');
	var sessionId = uploadEl.attr('_s');
	// 创建文件上传插件
	uploadEl.uploadify({
		'swf' : '/uploadify/uploadify.swf',
		'uploader' : '/upload-wb-image?JSESSIONID='
				+ sessionId,
		'width' : 51,
		'height' : 30,
		'buttonText' : '图片',
		'fileSizeLimit' : '10MB',
		'fileTypeDesc' : '图像文件',
        'fileTypeExts' : '*.jpg; *.png',
		// Put your options here
		'onUploadSuccess' : function(file, data,
				response) {
			var ret = $.parseJSON(data);
			_wbEditor.execCommand('insertimage', {
				src : ret.imageURL,
				alt : "贴图"
			});
			$('#weibo_image_progress').html('');
			$('#quick-search').focus();
			_wbEditor.focus(true);
		},
		'onUploadProgress' : function(file,
				bytesUploaded, bytesTotal,
				totalBytesUploaded, totalBytesTotal) {
			$('#weibo_image_progress').html('正在上传...'
					+ parseInt(parseInt(bytesUploaded)/ parseInt(bytesTotal)* 100)+ '%');
		}
	});
	// 开启 @人名 自动完成功能
	_wbEditor.ready(function() {
		var ifr = $('#ueditor_0')[0];
		var doc = ifr.contentDocument;
		if (!doc) {// 由于IE6，IE7不支持contentDocument，故做兼容处理
			doc = ifr.contentWindow.document;
		}
		var ifrBody = doc.body;
		WB_API.addNickNameAtWho(ifrBody);
		var a = $("#weiboContent");
		var b = $("iframe", a).contents().find("body");
		b.css("font-family", "Microsoft YaHei");
		b.css("font-size", "13px");
	});
	
	// 插入表情
    $("#message_face").jqfaceedit({
    	ueditor: _wbEditor,
    	containerObj:$('#emotion_icons'),
    	top:30,
    	left:-40
    });
	
	function checkPostText(){
		if(_wbEditor){
			var ingoneHtml = true;
			var length = _wbEditor.getContentLength(ingoneHtml);
			return length > 1000;
		}else{
			return false;	// 没有控件总认为有效. TODO: 加上错误记录
		}
	}
	// 发新微博按钮
	$("#publishBtn").click(function (event, a, b) {
		var $this = $(this);
		if(checkPostText()){
			$this.msgbox("字数过多，请删减后再发。", {success:false});
			return false;
		}
		var wb = _wbEditor.getPlainTxt().replace(/[ \n]+/, '');
		if(wb.length == 0){
			$this.msgbox("请输入内容再提交。", {success:false});
			return false;
		}
		$this.attr("disabled",true);
		var options = {
				url : LT.Settings.URLS.weibo.publish,
				type : "POST",
				dataType:"json",  
                success : function(result) { 
                	if (result != null) {
        				if (result.success) {
        					if(!result.results || result.results[0].id < 1){
    							$this.attr("disabled",false);
    							alert("发布失败！");
    							return;
    						}
							$("#publishForm").clearForm();
							_wbEditor.setContent('');
							_wbEditor.focus();
							$this.msgbox("发布成功", {success:true});
        				} else {
        					$.showMessage(result.desc);
        				}
        			}
					$this.attr("disabled",false);
                },
				error: function(XmlHttpRequest, textStatus, errorThrown){
					$.showMessage(errorThrown.message);
                    $this.attr("disabled",false);
                }
		};
		$("#publishForm").ajaxSubmit(options);
	});
	
//	// 显示长微博新建窗口
//	$("#postNewLong").click(function() {
//		$("#longPostId").val(0);
//		$longWeiboTitle.val("");
//		curPanel.setLongEditor("");
//		longWeiboWindow.modal('show');
//		$("#longWeiboTitle").focus();
//	});
	
//	// 发布长微博按钮
//	$("#longWeiboBtn").click(function (event, a, b) {
//		var $this = $(this);
//		if(weiboUI[curLotteryId] == null || weiboUI[curLotteryId].userId <1){
//			$.msgbox('请登录',$this);
//			window.location.href = "http://www.davcai.com/welcome";
//			return;
//		}
//		weiboUI.allposts.editor.sync();
//		if($.trim(weiboUI.allposts.$longWeiboContent.val()) == "" ){
//			alert("请输入内容……");
//			return;
//		}
//		$this.attr("disabled",true);
//		if($("#longPostId").val() != "0"){							
//			curPanel.editLongWeibo(); 		// 修改长微博
//		}else{
//			weiboUI.allposts.postLongWeibo();		// 发长微博
//		}
//	});
    //绑定点击赛事按钮事件
	WB_API.showMatchAreaDelegateEvents();
});
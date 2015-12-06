var showSchemeSloganKey = "show_scheme_slogan";
var maximumWords = 1000;
var _wbEditor = null;

var loadShowSchemeEditor = function() {
	_wbEditor = UE.getEditor('weiboContent', {
		toolbars:[],
		contextMenu:[],
		maximumWords: maximumWords,
		wordCountMsg: '还可以输入{#leave}个字符',
		wordOverFlowMsg: '<span style="color:red;">字数已超，请删减</span>',
		elementPathEnabled: false,
		customDomain: true,
		pasteplain: true,
		initialFrameHeight:80
	});
	
	bindShowSchemeEditorEvent();
};

var bindShowSchemeEditorEvent = function() {
	// 设置焦点
	_wbEditor.ready(function(){
		//失去焦点事件
		_wbEditor.addListener("blur",function() {
			var editorCont = _wbEditor.getContent();
			jQuery.cookie(showSchemeSloganKey, editorCont);
		});
		var showSchemeContent = jQuery.cookie(showSchemeSloganKey);
		if(showSchemeContent) {
			_wbEditor.setContent(showSchemeContent);
		}
		_wbEditor.focus(true);
	});
};


var removeShowSchemeSlogan = function() {
	jQuery.removeCookie(showSchemeSloganKey);
};
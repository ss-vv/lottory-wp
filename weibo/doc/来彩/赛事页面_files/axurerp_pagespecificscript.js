for(var i = 0; i < 110; i++) { var scriptId = 'u' + i; window[scriptId] = document.getElementById(scriptId); }

$axure.eventManager.pageLoad(
function (e) {

});

widgetIdToShowFunction['u2'] = function() {
var e = windowEvent;

if (true) {

	BringToFront("u2");

}

}

if (bIE) document.getElementById('u2').attachEvent("onmousedown", function(e) { StartDragWidget(e, 'u2'); });
else {
    document.getElementById('u2').addEventListener("mousedown", function(e) { StartDragWidget(e, 'u2'); }, true);
    document.getElementById('u2').addEventListener("touchstart", function(e) { StartDragWidget(e, 'u2'); }, true);
}

widgetIdToStartDragFunction['u2'] = function() {
var e = windowEvent;

if (true) {

}

}

widgetIdToDragFunction['u2'] = function() {
var e = windowEvent;

if (true) {

	MoveWidgetBy('u2',widgetDragInfo.xDelta,widgetDragInfo.yDelta,'none',500);

}

}

widgetIdToDragDropFunction['u2'] = function() {
var e = windowEvent;

if (true) {

	MoveWidgetBy('u2',widgetDragInfo.xDelta,widgetDragInfo.yDelta,'none',500);

}

}
gv_vAlignTable['u86'] = 'center';u102.tabIndex = 0;

u102.style.cursor = 'pointer';
$axure.eventManager.click('u102', function(e) {

if (true) {

	self.location.href=$axure.globalVariableProvider.getLinkUrl('投注.html');

}
});
gv_vAlignTable['u102'] = 'top';gv_vAlignTable['u16'] = 'center';gv_vAlignTable['u55'] = 'top';gv_vAlignTable['u77'] = 'center';u93.tabIndex = 0;

u93.style.cursor = 'pointer';
$axure.eventManager.click('u93', function(e) {

if (true) {

	NewTab($axure.globalVariableProvider.getLinkUrl('推荐.html'), "");

}
});
gv_vAlignTable['u93'] = 'top';gv_vAlignTable['u62'] = 'center';gv_vAlignTable['u53'] = 'top';document.getElementById('u87_img').tabIndex = 0;

u87.style.cursor = 'pointer';
$axure.eventManager.click('u87', function(e) {

if (true) {

	SetPanelVisibility('u84','hidden','none',500);

}
});
gv_vAlignTable['u1'] = 'center';
u7.style.cursor = 'pointer';
$axure.eventManager.click('u7', function(e) {

if (true) {

;

	SetPanelVisibility('u2','hidden','none',500);

}
});
gv_vAlignTable['u66'] = 'top';gv_vAlignTable['u104'] = 'top';gv_vAlignTable['u30'] = 'top';gv_vAlignTable['u8'] = 'top';gv_vAlignTable['u60'] = 'top';gv_vAlignTable['u17'] = 'top';gv_vAlignTable['u64'] = 'top';gv_vAlignTable['u100'] = 'center';gv_vAlignTable['u49'] = 'top';gv_vAlignTable['u79'] = 'top';gv_vAlignTable['u81'] = 'top';gv_vAlignTable['u97'] = 'center';gv_vAlignTable['u108'] = 'top';gv_vAlignTable['u71'] = 'center';gv_vAlignTable['u45'] = 'center';gv_vAlignTable['u36'] = 'top';gv_vAlignTable['u75'] = 'top';gv_vAlignTable['u58'] = 'top';
u37.style.cursor = 'pointer';
$axure.eventManager.click('u37', function(e) {

if (true) {

	SetPanelVisibility('u2','','none',500);

}
});
u92.tabIndex = 0;

u92.style.cursor = 'pointer';
$axure.eventManager.click('u92', function(e) {

if (true) {

	self.location.href=$axure.globalVariableProvider.getLinkUrl('主页.html');

}
});
gv_vAlignTable['u92'] = 'top';gv_vAlignTable['u83'] = 'center';gv_vAlignTable['u95'] = 'center';gv_vAlignTable['u22'] = 'top';
u13.style.cursor = 'pointer';
$axure.eventManager.click('u13', function(e) {

if (true) {

;

	SetPanelVisibility('u2','hidden','none',500);

}
});
gv_vAlignTable['u52'] = 'center';gv_vAlignTable['u47'] = 'top';gv_vAlignTable['u90'] = 'center';gv_vAlignTable['u73'] = 'top';gv_vAlignTable['u20'] = 'top';gv_vAlignTable['u50'] = 'top';gv_vAlignTable['u106'] = 'top';gv_vAlignTable['u28'] = 'top';gv_vAlignTable['u24'] = 'top';gv_vAlignTable['u54'] = 'top';gv_vAlignTable['u69'] = 'top';gv_vAlignTable['u78'] = 'top';gv_vAlignTable['u4'] = 'center';
u6.style.cursor = 'pointer';
$axure.eventManager.click('u6', function(e) {

if (true) {

	SetPanelVisibility('u2','hidden','none',500);

}
});
gv_vAlignTable['u91'] = 'top';gv_vAlignTable['u35'] = 'center';gv_vAlignTable['u26'] = 'top';gv_vAlignTable['u65'] = 'top';
$axure.eventManager.mouseover('u82', function(e) {
if (!IsTrueMouseOver('u82',e)) return;
if (true) {

	SetPanelVisibility('u84','','none',500);

	BringToFront("u84");

}
});

u12.style.cursor = 'pointer';
$axure.eventManager.click('u12', function(e) {

if (true) {

	SetPanelVisibility('u2','hidden','none',500);

}
});

u42.style.cursor = 'pointer';
$axure.eventManager.click('u42', function(e) {

if (true) {

	SetPanelVisibility('u43','','none',500);

}
});
gv_vAlignTable['u33'] = 'center';gv_vAlignTable['u72'] = 'top';gv_vAlignTable['u63'] = 'top';gv_vAlignTable['u67'] = 'top';gv_vAlignTable['u88'] = 'center';gv_vAlignTable['u57'] = 'center';u101.tabIndex = 0;

u101.style.cursor = 'pointer';
$axure.eventManager.click('u101', function(e) {

if (true) {

	NewTab($axure.globalVariableProvider.getLinkUrl('精华.html'), "");

}
});
gv_vAlignTable['u101'] = 'top';gv_vAlignTable['u10'] = 'center';gv_vAlignTable['u14'] = 'top';gv_vAlignTable['u74'] = 'top';gv_vAlignTable['u59'] = 'top';gv_vAlignTable['u98'] = 'top';gv_vAlignTable['u80'] = 'top';
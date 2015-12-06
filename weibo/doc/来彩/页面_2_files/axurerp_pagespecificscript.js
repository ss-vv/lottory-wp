for(var i = 0; i < 38; i++) { var scriptId = 'u' + i; window[scriptId] = document.getElementById(scriptId); }

$axure.eventManager.pageLoad(
function (e) {

});
gv_vAlignTable['u16'] = 'top';gv_vAlignTable['u28'] = 'top';gv_vAlignTable['u8'] = 'top';gv_vAlignTable['u30'] = 'top';gv_vAlignTable['u6'] = 'top';gv_vAlignTable['u32'] = 'top';gv_vAlignTable['u14'] = 'top';gv_vAlignTable['u4'] = 'top';gv_vAlignTable['u37'] = 'center';gv_vAlignTable['u26'] = 'top';gv_vAlignTable['u10'] = 'top';gv_vAlignTable['u12'] = 'top';document.getElementById('u7_img').tabIndex = 0;

u7.style.cursor = 'pointer';
$axure.eventManager.click('u7', function(e) {

if (true) {

	MoveWidgetTo('u1', GetNum('0'), GetNum('0'),'linear',500);

	MoveWidgetTo('u35', GetNum('0'), GetNum('50'),'linear',500);

}
});
gv_vAlignTable['u24'] = 'top';gv_vAlignTable['u18'] = 'top';gv_vAlignTable['u20'] = 'top';gv_vAlignTable['u22'] = 'top';document.getElementById('u33_img').tabIndex = 0;

u33.style.cursor = 'pointer';
$axure.eventManager.click('u33', function(e) {

if (true) {

	MoveWidgetBy('u1', GetNum('0'), GetNum('50'),'linear',500);

	MoveWidgetBy('u35', GetNum('0'), GetNum('100'),'linear',500);

}
});
gv_vAlignTable['u34'] = 'center';
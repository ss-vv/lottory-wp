$(function(){
	loadInboxCount('/ac/inboxCount.do');
});
function loadInboxCount(url){
	$.getJSON(url, function(data){
		if(data != null) {
			if(data.success){
				var htmlContent = '<span class="c-i">('+data.data+')</span>';
				$('#inboxCount').html(htmlContent);
				$('#menuInboxCount').html(htmlContent);
			}
		}
	});
}
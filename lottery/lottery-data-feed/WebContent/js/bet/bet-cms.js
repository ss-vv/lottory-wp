var IS_DEBUG_MODE = false;
function log(msg) {
	if(IS_DEBUG_MODE) {
		console.log(msg);
	}
}

function set_nav_class(){
        var nav = $('#info').attr('nav');
        $('a[_nav="'+nav+'"]').addClass('cur');
}

$(document).ready(function(){
        set_nav_class();
});

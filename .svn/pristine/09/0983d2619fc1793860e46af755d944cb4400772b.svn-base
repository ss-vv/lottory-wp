/**placeholder Hack*/
$(document).ready(function() {
	var inputArr = $('.placeholderHack');
	if(null != inputArr && inputArr.length > 0) {
		$.each(inputArr, function(i, n) {
			var input = $(n),
			defaultValue = input.attr('value'),
			text = input.attr('placeholder'),
			supportPlaceholder = 'placeholder' in document.createElement('input');
			placeholder = function(input,text,defaultValue) {
				input.css({color:'graytext'});
				if (defaultValue == '') {
					input.attr('value',text);
				}
				input.focus(function(){
					if(input.attr('value') === text){
						input.attr('value','');
						input.css({color:''});
					}
				});
				input.keydown(function(){
					if(input.attr('value') === text){
						input.attr('value','');
					}
				});
				input.blur(function(){
					if(input.attr('value') === ''){
						input.attr('value',text);
						input.css({color:'graytext'});
					}
				});
			};
			if (!supportPlaceholder && input.attr('type') === 'text' && text) {
				placeholder(input,text,defaultValue);
			}
		});
	}
});

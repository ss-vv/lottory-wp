$(document).ready(function(){
	list_daily_topics();
});

function today(){
	return $.dateFormat.date(new Date(), "yyyy-MM-dd");
}

function list_daily_topics(){
	$.ajax('http://www.davcai.com/ajax/tp_list?callback=?&date='+today(), {
		dataType: 'jsonp',
		success: function(data, status, jqXHR){
			render_daily_topics(data);
		},
		error: function(jqXHR, status, error){
			//alert(error);
		}
	});
}

function render_daily_topics(pageResults){
	var results = pageResults.results;
	var template =
	"<ul>{{#topics}}"+
	"<li>"+
		'<a href="/{{weibo.ownerId}}/{{weibo.id}}" target="_blank"><span class="show-cont">{{title}}</span></a>'+
		'<span class="show-time">{{create_fmt}}</span>'+
	'</li>{{/topics}}'+
	'</ul>';
	var segment = $.mustache(template, {
		topics: results.splice(0,5), 
		create_fmt: function(){
			var d = new Date(this.weibo.createTime);
			return $.dateFormat.date(d, 'yyyy-MM-dd');
		}
	});
	$('#daily_topics > ul').html(segment);
}
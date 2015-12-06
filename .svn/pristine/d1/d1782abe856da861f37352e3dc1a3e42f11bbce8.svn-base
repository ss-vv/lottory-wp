var pageBody = {
		ul_start:'<ul class="pager">',
		ul_end:	'</ul>',
		pre:'<li class="last" style="float:left"><a data-page="{0}" href="{1}&page={0}" ><span>上一页</span><i></i></a></li>',
		next:'<li class="next" style="float:left"><a data-page="{0}" href="{1}&page={0}" ><span>下一页</span><i></i></a></li>',
		pageActive:'<li class="active" style="float:left"><a data-page="{0}" href="{1}&page={0}" >{0}</a></li>',
		page:'<li style="float:left"><a data-page="{0}" href="{1}&page={0}" >{0}</a></li>',
		pageOmit:'<li  style="float:left">...</li>'
};


function ajaxPager(url, curPage, totalPage){
	var prev = curPage - 1;
	if(prev < 1 ){
		prev = 1;
	}
	var next = curPage + 1;
	if(next > totalPage){
		next = totalPage;
	}
	var width = 2;
	var ulHtml = pageBody.ul_start;
	if(prev != curPage){
		ulHtml += $.format(pageBody.pre,prev,url);
	}
	var array = paging(curPage, totalPage, width);
	for ( var i = 0; i < array.length; i++) {
		if(array[i] == "" || array[i] == " "){
			continue;
		}
		if(array[i] == -1){
			ulHtml += pageBody.pageOmit;
		} else {
			if(array[i] == curPage){
				ulHtml += $.format(pageBody.pageActive,array[i],url);
			} else {
				ulHtml += $.format(pageBody.page,array[i],url);
			}
		}
	}
	if(next != totalPage){
		ulHtml += $.format(pageBody.next,next,url);
	}
	return ulHtml;
}

function paging(currentPage, totalPages, width) {
	var pages = '';
	if (totalPages <= 1){
		return pages;
	}
	var index = currentPage;
	var remains = width+1;
	while(index > 0){
		pages = index + ' ' + pages;
		index--;
		remains--;
		if (remains == 0) {
			break;
		}
	}
	if (remains == 0 && index > 1){
		pages = '-1 '  + pages;	// ... 标志页号
		pages = '1 '  + pages;
	}else{
		var tmpArr = pages.split(" ");
		if (tmpArr[0] != 1){
			pages = '1 '  + pages;
		}
	}
	index = currentPage+1;
	remains = width;
	while(index <= totalPages){
		pages = pages + " " +index;
		index++;
		remains--;
		if (remains == 0) {
			break;
		}
	}
	pages = $.trim(pages);
	if (remains == 0 && index < totalPages){
		pages += ' -1';	// ... 标志页号
		pages += ' ' + totalPages;
	}else{
		var tmpArr = pages.split(" ");
		if (tmpArr[tmpArr.length-1] != totalPages){
			pages += ' ' + totalPages;
		}
	}
	return pages.split(' ');
}
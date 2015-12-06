/**
 * jQuery pager plugin
 *
 * Version 1.0 (2012/1/31)
 * 
 * Example:
 *   
 *   callback = function(no) {
 *         
 *     //initialization pager
 *     $("#pager").pager({
 *         pageno : no,
 *         pagecount : data.pageCount,
 *         callback : callback
 *     });
 *     $("#view").html("Current page " + no);
 *   }
 *
 *   $(function() {
 *       callback(1);
 *   });
 * 
 */
(function($) {
    // 渲染分页元素
    function renderpager(opts) {
        var $pager = $('<ul class="pages"></ul>');
        
        var pageno = parseInt(opts.pageno), pagecount = parseInt(opts.pagecount), display = parseInt(opts.display);
        
        // 添加 first previous 按钮
        $pager.append(renderButton(opts.first, opts)).append(renderButton(opts.prev, opts));

        // 计算分段
        var startPoint = 1, endPoint = 1;
        
        var half = parseInt(display / 2);
        if (pageno - half > 0) {
        	startPoint = (pageno - half);
		} else {
			startPoint = 1;
		}
		if (startPoint + display > pagecount) {
			endPoint = pagecount + 1;
			startPoint = endPoint - display;
		} else {
			endPoint = startPoint + display;
		}
		if(startPoint < 1){
			startPoint = 1;
		}
        // 渲染 v1, v2, v3 ... 按钮
        for (var page = startPoint; page < endPoint; page++) {
            var currentButton = $('<li class="page-number">' + (page) + '</li>');
            page == pageno ? currentButton.addClass('pgCurrent') : currentButton.click(function() {opts.callback(this.firstChild.data); });
            currentButton.appendTo($pager);
        }

        // 添加 next last 按钮.
        $pager.append(renderButton(opts.next, opts)).append(renderButton(opts.last, opts));
        
        if (opts.jump) {
        	var $Input = $('<input class="ipt" type="text" />');
        	var $Btn = $('<li class="pgNext">' + opts.go + '</li>');
        	$Btn.click(function() { 
        		var val = $Input.val();
        		opts.callback((val > pagecount ? pagecount : (val < 1 ? 1 : val) ));
        	});
        	$pager.append($Input).append($Btn);
        }
        return $pager;
    }

    function renderButton(btn, opts) {
    	var pageno = parseInt(opts.pageno), pagecount = parseInt(opts.pagecount);
    	
        var $Btn = $('<li class="pgNext">' + btn + '</li>');
        var destPage = 1;

        // 页码设置
        switch (btn) {
            case opts.first:
                destPage = 1;
                break;
            case opts.prev:
                destPage = pageno - 1;
                break;
            case opts.next:
                destPage = pageno + 1;
                break;
            case opts.last:
                destPage = pagecount;
                break;
        }

        // 添加按钮事件,无效的按钮置灰.
        if (btn == opts.first || btn == opts.prev) {
        	pageno <= 1 ? $Btn.addClass('pgEmpty') : $Btn.click(function() { opts.callback(destPage); });
        }
        else {
        	pageno >= pagecount ? $Btn.addClass('pgEmpty') : $Btn.click(function() { opts.callback(destPage); });
        }

        return $Btn;
    }
    
    $.fn.pager = function(options) {
        var opts = $.extend({}, $.fn.pager.defaults, options);
        return this.each(function() {
        	// 清空目标元素, 渲染分页元素
            $(this).empty().append(renderpager(opts));
            
            // 设置鼠标移动的光标
            $('.pages li').mouseover(function() { document.body.style.cursor = "pointer"; }).mouseout(function() { document.body.style.cursor = "auto"; });
        });
    };
    
    // 参数配置
    $.fn.pager.defaults = {
        pageno: 1,
        pagecount: 1,
        display: 5,
        first: "first",
        prev: "prev",
		next: "next",
		last: "last",
		go: "go",
		jump: true,
		callback: function(currenPage){}
    };

})(jQuery);
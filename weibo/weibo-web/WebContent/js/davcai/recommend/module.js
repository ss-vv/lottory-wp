//下面开始问号提示层
$(document).ready(function(){
      $(".blue-mark-tips").mouseover(function(){
         var tid=$(this).attr('tid');
         $("#blue-mark-tips-tan-"+tid).show();
    });
      $(".blue-mark-tips").mouseout(function(){
        var tid=$(this).attr('tid');
        $("#blue-mark-tips-tan-"+tid).hide();
    });
});
//上面结束问号提示层

//下面这段是 奇数行 偶数行隔行变色
  $(document).ready(function(){
	     //奇数行
      $(".davcai-red-people-list li:odd").css({
           "background-color":"#F8F8F8"
         });
       //偶数行
       $(".davcai-red-people-list li:even").css({
            "background-color":"#ffffff"
        });
    });

//上面这段是 奇数行 偶数行隔行变色
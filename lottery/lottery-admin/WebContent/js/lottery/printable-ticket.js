$(document).ready(function (){
	$("#playIds").find("option").each(function (){
		if($(this).val()==playId){
			$(this).attr("selected","selected");
		}
	});
	var postForm = function (){
		$("#qForm").submit();
	};
	var hideMaskLayer = function(){
		$('#dialog').dialog('close');
	}
	var showMaskLayer = function(){
		$('#dialog').html('操作正在执行,请稍候...');
		$('#dialog').dialog({
			modal: true
		});
		$(".ui-dialog-titlebar-close").hide();
	};
	$("#playIds").change(function (){
		postForm();
	});
	$("#states").change(function (){
		postForm();			
	}); 
	$("#selectAll").click(function (){
		if($(this).attr("checked") == "checked"){
			$('[ticket-checkbox="true"]').attr("checked",true);
		} else {
			$('[ticket-checkbox="true"]').attr("checked",false);
		}
	});
	$('#buyTicketBtn').click(function (){
		var isSelect = false;
		$('[ticket-checkbox="true"]').each(function (){
			if($(this).attr("checked") == "checked"){
				isSelect = true;
			}
		});
		if(isSelect){
			var $form = $("#doForm");
			var chk_value =[];
			$('[ticket-checkbox="true"]').each(function (){
				if($(this).attr("checked") == "checked"){
					chk_value.push($(this).val()); 
				}
			});
			showMaskLayer();
			$.ajax("http://admin.davcai.com/lottery/ticket/aj_batch_set_ticket_succ.do",{
				data:{
					alreadyExpoetTicketIds:chk_value,
					lotteryPlatformId:$form.find('[name="lotteryPlatformId"]').val()
				},
				success:function (data){
					hideMaskLayer();
					if(data){
						alert("操作成功！");
					} else {
						alert("操作失败！");
					}
					postForm();
				},
				type: "POST",
				dataType:'json',
				traditional:true
			});
		} else {
			alert("请选择要处理的票");
		}
	});
	$('#cancelTicketBtn').click(function (){
		var isSelect = false;
		$('[ticket-checkbox="true"]').each(function (){
			if($(this).attr("checked") == "checked"){
				isSelect = true;
			}
		});
		if(isSelect){
			var $form = $("#doForm");
			var chk_value =[];
			$('[ticket-checkbox="true"]').each(function (){
				if($(this).attr("checked") == "checked"){
					chk_value.push($(this).val()); 
				}
			});
			showMaskLayer();
			$.ajax("http://admin.davcai.com/lottery/ticket/aj_batch_set_ticket_cancel.do",{
				data:{
					alreadyExpoetTicketIds:chk_value,
					lotteryPlatformId:$form.find('[name="lotteryPlatformId"]').val()
				},
				success:function (data){
					hideMaskLayer();
					if(data){
						alert("操作成功！");
					} else {
						alert("操作失败！");
					}
					location.reload();
				},
				type: "POST",
				dataType:'json',
				traditional:true
			});
		} else {
			alert("请选择要处理的票");
		}
	});
	$('#exportBtn').click(function (){
		var isSelect = false;
		$('[ticket-checkbox="true"]').each(function (){
			if($(this).attr("checked") == "checked"){
				isSelect = true;
			}
		});
		if(isSelect){
			var $form = $("#doForm");
			var chk_value =[];
			$('[ticket-checkbox="true"]').each(function (){
				if($(this).attr("checked") == "checked"){
					chk_value.push($(this).val()); 
				}
			});
			showMaskLayer();
			$.ajax("http://admin.davcai.com/lottery/ticket/exportPintableTicket.do",{
				data:{
					printableTicketIds:chk_value,
					lotteryId:$form.find('[name="lotteryId"]').val(),
					playId:$form.find('[name="playId"]').val(),
					lotteryPlatformId:$form.find('[name="lotteryPlatformId"]').val()
				},
				success:function (data){
					hideMaskLayer();
					if(data){
						$('#download_dialog').html('导出成功，下载地址：<a target="_blank" href="'+data+'">'+data +"</a><br/><br/><br/>提示，关闭窗口前请先下载文件！");
						$('#download_dialog').dialog({
							close: function( event, ui ) {
								postForm();
							},
							modal: true
						});
						
					} else {
						alert("操作失败！");
						postForm();
					}
				},
				type: "POST",
				dataType:'json',
				traditional:true
			});
		} else {
			alert("请选择要处理的票");
		}
	});
	$('#exportToBtn').click(function (){
		var isSelect = false;
		$('[ticket-checkbox="true"]').each(function (){
			if($(this).attr("checked") == "checked"){
				isSelect = true;
			}
		});
		if(isSelect){
			var shitidianname = $("#export_to_shitidian").find("option[value='"+$("#export_to_shitidian").val()+"']").text();
			if(!confirm("确定将票到实体店：  "+shitidianname + "  吗？")){
				return false;
			}
			var $form = $("#doForm");
			var chk_value =[];
			$('[ticket-checkbox="true"]').each(function (){
				if($(this).attr("checked") == "checked"){
					chk_value.push($(this).val()); 
				}
			});
			showMaskLayer();
			$.ajax("http://admin.davcai.com/lottery/ticket/exportPintableTicket.do",{
				data:{
					printableTicketIds:chk_value,
					lotteryId:$form.find('[name="lotteryId"]').val(),
					playId:$form.find('[name="playId"]').val(),
					lotteryPlatformId:$form.find('[name="lotteryPlatformId"]').val(),
					exportToShitidian:$("#export_to_shitidian").val()
				},
				success:function (data){
					hideMaskLayer();
					if(data){
						$('#download_dialog').html('导出成功，下载地址：<a target="_blank" href="'+data+'">'+data +"</a><br/><br/><br/>提示，关闭窗口前请先下载文件！");
						$('#download_dialog').dialog({
							close: function( event, ui ) {
								postForm();
							},
							modal: true
						});
						
					} else {
						alert("操作失败！");
						postForm();
					}
				},
				type: "POST",
				dataType:'json',
				traditional:true
			});
		} else {
			alert("请选择要处理的票");
		}
	});
});
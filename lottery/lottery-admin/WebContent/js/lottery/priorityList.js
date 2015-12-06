$(document).ready(function (){
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
	$("#lotteryIdSel").change(function (){
		postForm();
	});
	$("#lotteryPlatformIdSel").change(function (){
		postForm();			
	}); 
	$("#selectAll").click(function (){
		if($(this).attr("checked") == "checked"){
			$('[priority-checkbox="true"]').attr("checked",true);
		} else {
			$('[priority-checkbox="true"]').attr("checked",false);
		}
	});
	
	$('[checkbox4Tr="true"]').click(function(){
		var _tr = $(this).closest('tr');
		if($(this).attr("checked") == "checked"){
			$('[priority-checkbox="true"]',_tr).attr("checked",true);
		} else {
			$('[priority-checkbox="true"]',_tr).attr("checked",false);
		}
	});
	
	$('#batchEditSaveBtn').click(function (){
		var result = window.confirm("你确定要修改分票吗？修改之前请务必确认！！！")
		if(true != result) {
			return;
		}
		var isSelect = false;
		$('[priority-checkbox="true"]').each(function (){
			if($(this).attr("checked") == "checked"){
				isSelect = true;
			}
		});
		if(isSelect){
			
			var $form = $("#batchEditForm");
			var chk_id_value =[];
			$('[name="selectedPriorityIds"][priority-checkbox="true"]').each(function (){
				if($(this).attr("checked") == "checked"){
					chk_id_value.push($(this).val()); 
				}
			});
			var chk_priority_value =[];
			$('[name="priority"][priority-checkbox="true"]').each(function (){
				if($(this).attr("checked") == "checked"){
					chk_priority_value.push($(this).val()); 
				}
			});
			var chk_priorityOfBigTicket_value =[];
			$('[name="priorityOfBigTicket"][priority-checkbox="true"]').each(function (){
				if($(this).attr("checked") == "checked"){
					chk_priorityOfBigTicket_value.push($(this).val()); 
				}
			});
			showMaskLayer();
			$.ajax("http://admin.davcai.com/lottery/ticket/aj_batch_set_priority.do",{
				data:{
					selectedIds:chk_id_value,
					selectedPrioritys:chk_priority_value,
					selectedPriorityOfBigTickets:chk_priorityOfBigTicket_value,
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
			alert("请选择要处理的项");
		}
	});
	$('#batchEditBtn').click(function (){
		$('#dataGrid th[batchEditOpt="true"]').show();
		$('#dataGrid td[batchEditOpt="true"]').show();
		$('#dataGrid th[batchEditOpt="false"]').hide();
		$('#dataGrid td[batchEditOpt="false"]').hide();
		$('#cacelBatchEditBtn').show();
		$('#batchEditSaveBtn').show();
		$('#batchEditBtn').hide();
		$("#batchEditForm input").removeAttr("readonly"); 
		$("#batchEditForm input").css("background-color","yellow"); 
	});
	
	$('#cacelBatchEditBtn').click(function (){
		postForm();
		$('#dataGrid th[batchEditOpt="true"]').hide();
		$('#dataGrid td[batchEditOpt="true"]').hide();
		$('#dataGrid th[batchEditOpt="false"]').show();
		$('#dataGrid td[batchEditOpt="false"]').show();
		$('#cacelBatchEditBtn').hide();
		$('#batchEditSaveBtn').hide();
		$('#batchEditBtn').show();
		$("#batchEditForm input").attr({ readonly: 'true' }); 
		$("#batchEditForm input").css("background-color","white"); 
		
	});
	
	var $priority = $('input[name="priority"]', this.body);
	$priority.bind('keyup', function(){
		var val = $(this).val();
		var orignVal=$(this).attr("orignValue");
		if(val == ''){
			alert('请输入小额权重');
		}
		if(!/^\d+$/.test(val)){
			$(this).val(orignVal);
			alert("小额权重只允许输入非负整数");
		}
	});
	
	var $priorityOfBigTicket = $('input[name="priorityOfBigTicket"]', this.body);
	$priorityOfBigTicket.bind('keyup', function(){
		var val = $(this).val();
		var orignVal=$(this).attr("orignValue");
		if(val == ''){
			alert('请输入大额权重');
		}
		if(!/^\d+$/.test(val)){
			$(this).val(orignVal);
			alert("大额权重只允许输入非负整数");
		}
	});
	
});
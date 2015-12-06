$(document).ready(function(){
    // 新table
    var $selectedVoucher = $("#selectedVoucher");
    // 旧table
    var $viewVoucher = $("#viewVoucher");
    var $voucherId = $("[name='voucherId']");
    var $isUseVoucher = $("[name='isUseVoucher']");
    
	$.toggenDiv = function toggenDiv(){
		$("#voucherListDiv").toggle();
		$("#sign").toggle();
		$("#signSum").toggle();
	}
	
	$.toggenTable = function toggenTable(){
        $("#viewAll").toggle();
        $("[name='readTips']").toggle();
        $selectedVoucher.toggle();
        $viewVoucher.toggle();
        $("#help_box_tips").toggle();
	}
	
	function checkAmount(voucherPrice, grant){
		var amount = parseFloat($.trim($('#aIpt').val()));
		var price = parseFloat(voucherPrice);
		var grantPrice = parseFloat(grant);

		/*if(!(/^\d+(\.\d{1,2})?$/.test(amount)) || amount <= 0){

            alert('请输入有效充值金额：如123或1.23');
            $('#aIpt').focus();
            return false;
        }
        if(parseFloat(amount) < 2.00){
            alert('最小充值金额为2元');
            $('#aIpt').focus();
            return false;
        }
        if(amount < price ){
            alert('充值金额小于优惠劵限制金额，不能使用！');
            $('#aIpt').focus();
            return false;
        }*/
        $("#rechargePrice").text(amount);
        $("#grantPrice").text(grantPrice);
        $("span[name='totalPrice']").text(amount + grantPrice);
		return true;
	}
	
	// 使用优惠劵
	$.useVoucher = function useVoucher(voucherPrice, grant, target, voucherId, limit){
		if(limit != "" && limit != "web"){
			alert("此优惠劵只能在客户端使用！");
			return;
		}
		// 检查充值金额
		if(!checkAmount(voucherPrice, grant)){
			return;
		}
		// 设定将要使用的优惠id
		$voucherId.val(voucherId);
		$isUseVoucher.val(true);
		// clone要使用的优惠劵
        $selectedVoucher.append($(target).closest("tr").clone());
        var $btn = $selectedVoucher.find("span.btn_voucher");
        $btn.text("更改优惠劵");
        $btn.click(function (event, a, b) {
        	$.toggenTable();
        	$selectedVoucher.html(null);
        	$voucherId.val("");
        	$isUseVoucher.val(false);
        });
        // 显示将使用的优惠劵
        $.toggenTable();
	}
	
	$("[_n='useVoucher']").click(function (event, a, b) {
		var $this = $(this); 
		$.useVoucher($this.attr('_price'), $this.attr('_grant'), $this, $this.attr('_id'), $this.attr('_limit'));
    });
});

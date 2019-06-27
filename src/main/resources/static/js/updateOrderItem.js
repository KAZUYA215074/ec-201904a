/**
 * 
 */

$(function(){
	
	$(":input").bind('keyup mouseup', function () {
		  //値が変更されたときの処理
		var ip = $("#IP").val();
		var index = $(this).attr("id");
		var value = $(this).val();
		var csrf = $("input[name='_csrf']").val();
		var id = $("input[name='orderItemId"+index+"']").val();
		if(value>0){
			$.ajax({
				url : "update",
				dataType : "json",
				type : 'POST',
				data:{
					_csrf : csrf,
					orderItemId : id,
					quantity : value
				},
				timeout: 10000,
			}).done(function(result){
				$("#subTotal"+index).html(result.subTotal);
				$(".tax").html(result.tax);
				$(".calcTotalPrice").html(result.calcTotalPrice);
			}).fail(function(XMLHttpRequest, textStatus, errorThrown){
				console.log("error");
			})
		}
	});
	
	
});

//onchange="submit(this.form)"
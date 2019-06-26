/**
 * 
 */

$(function(){
	
	$(":input").bind('keyup mouseup', function () {
		  //値が変更されたときの処理
		var index = $(this).attr("id");
		var value = $(this).val();
		var csrf = $("input[name='_csrf']").val();
		var id = $("input[name='orderItemId"+index+"']").val();
		$.ajax({
			url : "http://localhost:8080/ec-201904a/cart/update",
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
	});
	
	
});

//onchange="submit(this.form)"
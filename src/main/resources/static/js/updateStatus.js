/**
 * 
 */

$(function(){
	$("input[type='button']").click(function(){
		
		var id = $(this).next().val();
		var _csrf = $("input[name='_csrf']").val();
		var value = $("#status"+id).val();
		if(value>0){
			$.ajax({
				url : "update",
				dataType : "json",
				type : 'POST',
				data:{
					_csrf : _csrf,
					orderId : id,
					status : value
				},
				timeout: 10000,
			}).done(function(result){
				$(".status_"+id).html(result.status);
			}).fail(function(XMLHttpRequest, textStatus, errorThrown){
				console.log("error");
			});
		}
	});
	
});
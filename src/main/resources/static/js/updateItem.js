/**
 * 
 */

$(function(){
	
	$(":input").change(function(){
		var itemId = $(this).val();
		var isCheck = $(this).prop('checked');
		var csrf = $("input[name='_csrf']").val();
		console.log("change");
		console.log(itemId);
		console.log(isCheck);
		console.log(csrf);
		
		$.ajax({
			url : "http://localhost:8080/ec-201904a/admin/updateItem",
			dataType : "json",
			type : 'POST',
			data:{
				_csrf : csrf,
				itemId : itemId,
				isCheck : isCheck
			},
			timeout: 10000,
		}).done(function(result){
			if(isCheck){				
				$("#img_"+itemId).show();
			}else{
				$("#img_"+itemId).hide();
			}
		}).fail(function(XMLHttpRequest, textStatus, errorThrown){
			console.log("error");
		})
		
		
	})
	
	
});

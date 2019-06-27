/**
 * 
 */

$(function(){
	
	$(":input").change(function(){
		var itemId = $(this).val();
		var isCheck = $(this).prop('checked');
		var csrf = $("input[name='_csrf']").val();
		
		$.ajax({
			url : "updateItem",
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

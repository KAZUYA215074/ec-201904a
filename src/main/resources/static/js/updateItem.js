/**
 * 
 */

$(function(){
	
	$(":image").click(function(){
		
		var itemId = $(this).val();
		if($(this).next().val()=="true"){
			var isCheck = false;
		}else{
			var isCheck = true;
		}
		console.log(isCheck);
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
			if(!isCheck){				
				$("#false_"+itemId).show();
				$("#true_"+itemId).hide();
			}else{
				$("#true_"+itemId).show();
				$("#false_"+itemId).hide();
			}
			
		}).fail(function(XMLHttpRequest, textStatus, errorThrown){
			console.log("error");
		})
		return false;
	})
	
	
});

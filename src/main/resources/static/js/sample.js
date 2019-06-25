$(function() {
	$('input[value="1"]').prop('checked', true); //初回のチェックをつける
	$("#bank").css("display", "none");	//非表示処理
	$("#credit").css("display", "none");	//非表示処理

	$('input[name=paymentMethod]:radio').on("change", function(val) {
		var radioval = $(this).val();	//ラジオボタンの状態(value値を取得)
		if (radioval == "1") {
			$("#bank").css("display", "block");
			$("#credit").css("display", "none");
		} else if (radioval == "2") {
			$("#bank").css("display", "none");
			$("#credit").css("display", "block");
		}
	});
});
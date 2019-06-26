var sizeMappingPrice = {
    "M": Number($("#priceM").html().replace(/[^0-9]/g, '')), 
    "L": Number($("#priceL").html().replace(/[^0-9]/g, ''))
};

var toppingMappingPrice = {
    "M": 200, 
    "L": 300
};

let calcSumPrice = () => {
    var size = $("input[name=size]:checked").val();
    var selectedToppingNum = $("input[class='toppingCheckbox checkbox01-input']:checked").length;
    var quantitiy = $(".form-control").val();
    var price = (toppingMappingPrice[size]
		        * selectedToppingNum
		        + sizeMappingPrice[size])
		        * quantitiy;
    $("#total-price").text("この商品金額：" + Number(price).toLocaleString() + "円(税抜)");
}

$(function () {
    calcSumPrice()

    $("input[name=size]:radio").click(function () {
        calcSumPrice()
    })

    $(".toppingCheckbox").change(function () {
        calcSumPrice()
    })

    $(".form-control").change(function () {
        calcSumPrice()
    })
});
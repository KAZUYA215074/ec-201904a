var sizeMappingPrice = {
    "M": 1380, 
    "L": 2380
};

var toppingMappingPrice = {
    "M": 200, 
    "L": 300
};

let calcSumPrice = () => {
    var size = $("input[name=size]:checked").val()
    var selectedToppingNum = $("input[class=toppingCheckbox]:checked").length;
    var quantitiy = $(".form-control").val()
    var price = (toppingMappingPrice[size]
		        * selectedToppingNum
		        + sizeMappingPrice[size])
		        * quantitiy;
    $("#total-price").text("この商品金額：" + price + "円(税抜)");
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
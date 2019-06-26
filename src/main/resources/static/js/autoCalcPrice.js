var pizaPriceSizeMap = {
    "M": Number($("#priceM").text()),
    "L": Number($("#priceL").text())
}

var toppingMappingPrice = {
    "M": 200, 
    "L": 300
};

let calcSumPrice = () => {
    var size = $("input[name=size]:checked").val()
    var pizaPrice = $(".price:checked").text()
    var selectedToppingNum = $(".toppingCheckbox input[type=checkbox]:checked").length;
    var quantitiy = $(".form-control").val()
    var price = (toppingMappingPrice[size]
		        * selectedToppingNum
		        + pizaPriceSizeMap[size])
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
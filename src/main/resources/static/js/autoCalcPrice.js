var toppingMappingPrice = {
    "M": 200, 
    "L": 300
};

let calcSumPrice = () => {
    var pizaPriceSizeMap = {
        "M": Number($("#priceM").text()),
        "L": Number($("#priceL").text())
    }
    var size = $("input[name=size]:checked").val()
    var pizaPrice = $(".price:checked").text()
    var selectedToppingNum = $(".toppingCheckbox input[type=checkbox]:checked").length;
    var quantitiy = $(".form-control").val()
    var price = (toppingMappingPrice[size]
		        * selectedToppingNum
		        + pizaPriceSizeMap[size])
		        * quantitiy;
    console.log(toppingMappingPrice[size]);
    console.log("size: " + size);
    console.log("pizaPrice: " + pizaPriceSizeMap[size]);
    console.log("selectedToppingNum: " + selectedToppingNum);
    console.log("quantitiy: " + quantitiy);
    console.log("price: " + price);

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
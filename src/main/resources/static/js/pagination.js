$(function() {
    $('.pizzas').pagination({
        itemElement : '> .pizza',// アイテムの要素
        displayItemCount  : 3,
        paginationClassName            : 'pagination',
		paginationInnerClassName       : 'pagination',
		prevNextPageBtnMode            : false,
		firstEndPageBtnMode            : false,
    });
});
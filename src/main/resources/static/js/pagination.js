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

$(function() {
	$('.histories').pagination({
		itemElement : '> .history',// アイテムの要素
		displayItemCount  : 3,
		paginationClassName            : 'pagination',
		paginationInnerClassName       : 'pagination',
		prevNextPageBtnMode            : false,
		firstEndPageBtnMode            : false,
		setPaginationMode              : 'prepend',
	});
});

$(function() {
    $('.orders').pagination({
        itemElement : '> .order',// アイテムの要素
        displayItemCount  : 3,
        paginationClassName            : 'pagination',
		paginationInnerClassName       : 'pagination',
		prevNextPageBtnMode            : false,
		firstEndPageBtnMode            : false,
    });
});
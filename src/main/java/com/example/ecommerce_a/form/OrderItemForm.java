package com.example.ecommerce_a.form;

import java.util.Arrays;

/**
 * 注文詳細を受け取るフォーム.
 * 
 * @author Makoto
 *
 */
public class OrderItemForm {

	/** アイテムID */
	private Integer itemId;
	/** 数量 */
	private Integer quantity;
	/** サイズ */
	private Character size;
	/** トッピングリスト */
	private Integer[] toppingIdList;

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Character getSize() {
		return size;
	}

	public void setSize(Character size) {
		this.size = size;
	}

	public Integer[] getToppingIdList() {
		return toppingIdList;
	}

	public void setToppingIdList(Integer[] toppingIdList) {
		this.toppingIdList = toppingIdList;
	}

	@Override
	public String toString() {
		return "OrderItemForm [itemId=" + itemId + ", quantity=" + quantity + ", size=" + size + ", toppingIdList="
				+ Arrays.toString(toppingIdList) + "]";
	}

}

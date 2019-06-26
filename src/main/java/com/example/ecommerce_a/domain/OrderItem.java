package com.example.ecommerce_a.domain;

import java.util.List;

/**
 * 注文商品のドメインです.
 * 
 * @author risa.nazato
 *
 */
public class OrderItem {
	/** ID */
	private Integer id;
	/** アイテムID */
	private Integer itemId;
	/** 注文ID */
	private Integer orderId;
	/** 数量 */
	private Integer quantity;
	/** サイズ */
	private Character size;
	/** 商品 */
	private Item item;
	/** トッピングリスト */
	private List<OrderTopping> orderToppingList;

	public OrderItem() {
	}

	public OrderItem(Integer id, Integer itemId, Integer orderId, Integer quantity, Character size, Item item,
			List<OrderTopping> orderToppingList) {
		super();
		this.id = id;
		this.itemId = itemId;
		this.orderId = orderId;
		this.quantity = quantity;
		this.size = size;
		this.item = item;
		this.orderToppingList = orderToppingList;
	}

	@Override
	public String toString() {
		return "OrderItem [id=" + id + ", itemId=" + itemId + ", orderId=" + orderId + ", quantity=" + quantity
				+ ", size=" + size + ", item=" + item + ", orderToppingList=" + orderToppingList + "]";
	}

	/**
	 * 注文された商品の合計金額を返す.
	 * 
	 * @return 合計金額
	 */
	public int getSubTotal() {
		int total = 0;
		if (size == 'L') {
			for (OrderTopping orderTopping : orderToppingList) {
				total += orderTopping.getTopping().getPriceL();
			}
			total += item.getPriceL();
		} else if (size == 'M') {
			for (OrderTopping orderTopping : orderToppingList) {
				total += orderTopping.getTopping().getPriceM();
			}
			total += item.getPriceM();
		}
		return total = total * quantity;
	}
	
	/**
	 * トッピングの合計を計算する.
	 * 
	 * @return トッピング合計金額
	 */
	public int getSubTotalTopping() {
		if(this.orderToppingList==null||this.orderToppingList.size()==0) {
			return 0;
		}else {
			int total = 0;
			for(OrderTopping orderTopping:this.orderToppingList) {
				if(getSize()=='M') {
					total += orderTopping.getTopping().getPriceM();
				}else if(getSize()=='L'){
					total += orderTopping.getTopping().getPriceL();
				}
			}
			return total;
		}
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
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

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public List<OrderTopping> getOrderToppingList() {
		return orderToppingList;
	}

	public void setOrderToppingList(List<OrderTopping> orderToppingList) {
		this.orderToppingList = orderToppingList;
	}

}

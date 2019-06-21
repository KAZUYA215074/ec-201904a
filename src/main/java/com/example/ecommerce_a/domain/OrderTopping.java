package com.example.ecommerce_a.domain;

/**
 * 注文トッピングのドメイン.
 * 
 * @author risa.nazato
 *
 */
public class OrderTopping {

	/** ID */
	private Integer id;
	/** トッピングID */
	private Integer toppingId;
	/** 注文ID */
	private Integer orderItemId;
	/** トッピング */
	private Topping topping;

	public OrderTopping() {
	}

	public OrderTopping(Integer id, Integer toppingId, Integer orderId, Topping topping) {
		super();
		this.id = id;
		this.toppingId = toppingId;
		this.orderItemId = orderId;
		this.topping = topping;
	}

	@Override
	public String toString() {
		return "OrderTopping [id=" + id + ", toppingId=" + toppingId + ", orderId=" + orderItemId + ", topping="
				+ topping + "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getToppingId() {
		return toppingId;
	}

	public void setToppingId(Integer toppingId) {
		this.toppingId = toppingId;
	}

	public Integer getOrderItemId() {
		return orderItemId;
	}

	public void setOrderItemId(Integer orderItemId) {
		this.orderItemId = orderItemId;
	}

	public Topping getTopping() {
		return topping;
	}

	public void setTopping(Topping topping) {
		this.topping = topping;
	}

}

package com.example.ecommerce_a.domain;

/**
 * クレジットカードの情報を表すドメイン.
 * 
 * @author Makoto
 *
 */
public class CreditCardInfo {

	/** ユーザーID */
	private Integer userId;
	/** 注文番号 */
	private String orderNumber;
	/** 合計金額 */
	private String amount;
	/** カード番号 */
	private String cardNumber;
	/** カード有効期限(年) */
	private String cardExpYear;
	/** カード有効期限(月) */
	private String cardExpMonth;
	/** カード名義人 */
	private String cardName;
	/** セキュリティコード */
	private String cardCVV;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getCardExpYear() {
		return cardExpYear;
	}

	public void setCardExpYear(String cardExpYear) {
		this.cardExpYear = cardExpYear;
	}

	public String getCardExpMonth() {
		return cardExpMonth;
	}

	public void setCardExpMonth(String cardExpMonth) {
		this.cardExpMonth = cardExpMonth;
	}

	public String getCardName() {
		return cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}

	public String getCardCVV() {
		return cardCVV;
	}

	public void setCardCVV(String cardCVV) {
		this.cardCVV = cardCVV;
	}

	@Override
	public String toString() {
		return "CreditCardInfo [userId=" + userId + ", orderNumber=" + orderNumber + ", amount=" + amount
				+ ", cardNumber=" + cardNumber + ", cardExpYear=" + cardExpYear + ", cardExpMonth=" + cardExpMonth
				+ ", cardName=" + cardName + ", cardCVV=" + cardCVV + "]";
	}

}

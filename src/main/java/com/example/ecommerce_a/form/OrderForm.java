package com.example.ecommerce_a.form;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.example.ecommerce_a.domain.CreditcardInfo;
import com.example.ecommerce_a.domain.User;

/**
 * 注文する際のお届け先登録時に使用するフォーム
 * 
 * @author risa.nazato
 *
 */
public class OrderForm {

	/** 宛先氏名 */
	@NotBlank(message = "お名前を入力してください")
	private String destinationName;
	/** 宛先Eメール */
	@Email(message = "メールアドレスの形式ではありません")
	@NotBlank(message = "メールアドレスを入力してください")
	private String destinationEmail;
	/** 宛先郵便番号 */
	// @NotBlank(message = "郵便番号を入力してください")
	@Pattern(regexp = "^\\d{3}\\-?\\d{4}$", message = "郵便番号を入力したください")
	private String destinationZipcode;
	/** 宛先住所 */
	@NotBlank(message = "住所を入力してください")
	private String destinationAddress;
	/** 宛先TEL */
	@NotBlank(message = "電話番号を入力してください")
	@Pattern(regexp = "[0-9]*", message = "電話番号を入力してください")
	@Size(max = 11, message = "電話番号を入力してください")
	private String destinationTel;
	/** 配達日 */
	@NotEmpty(message = "配達日時を入力してください")
	private String deliveryDate;
	/** 配達時間 */
	@NotEmpty(message = "配達日時を入力してください")
	private String deliveryTime;
	/** 支払方法 */
	private String paymentMethod;
	/** ユーザ */
	private User user;
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

	/**
	 * formのクレジットカード情報をCreditcardInfoに入れて返す.
	 * 
	 * @param orderNumber オーダーID
	 * @return クレジットカード情報
	 */
	public CreditcardInfo createCreditcardInfo() {
		CreditcardInfo creditCardInfo = new CreditcardInfo();
		creditCardInfo.setOrder_number(orderNumber);
		creditCardInfo.setAmount(amount);
		creditCardInfo.setCard_number(cardNumber);
		creditCardInfo.setCard_exp_year(cardExpYear);
		creditCardInfo.setCard_exp_month(cardExpMonth);
		creditCardInfo.setCard_name(cardName);
		creditCardInfo.setCard_cvv(cardCVV);

		return creditCardInfo;
	}

	/**
	 * 注文日をDate型で返す.
	 * 
	 * @return 注文年月日
	 */
	public Date getDateOrderDate() throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date formatDate = sdf.parse(this.deliveryDate);
		return formatDate;
	}

	/**
	 * 支払方法をInteger型で返す.
	 * 
	 * @return 支払方法
	 */
	public Integer getIntPaymentMethod() {
		return Integer.parseInt(paymentMethod);
	}

	public String getDestinationName() {
		return destinationName;
	}

	public void setDestinationName(String destinationName) {
		this.destinationName = destinationName;
	}

	public String getDestinationEmail() {
		return destinationEmail;
	}

	public void setDestinationEmail(String destinationEmail) {
		this.destinationEmail = destinationEmail;
	}

	public String getDestinationZipcode() {
		return destinationZipcode;
	}

	public void setDestinationZipcode(String destinationZipcode) {
		this.destinationZipcode = destinationZipcode;
	}

	public String getDestinationAddress() {
		return destinationAddress;
	}

	public void setDestinationAddress(String destinationAddress) {
		this.destinationAddress = destinationAddress;
	}

	public String getDestinationTel() {
		return destinationTel;
	}

	public void setDestinationTel(String destinationTel) {
		this.destinationTel = destinationTel;
	}

	public String getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public String getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(String deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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
		return "OrderForm [destinationName=" + destinationName + ", destinationEmail=" + destinationEmail
				+ ", destinationZipcode=" + destinationZipcode + ", destinationAddress=" + destinationAddress
				+ ", destinationTel=" + destinationTel + ", deliveryDate=" + deliveryDate + ", deliveryTime="
				+ deliveryTime + ", paymentMethod=" + paymentMethod + ", user=" + user + ", orderNumber=" + orderNumber
				+ ", amount=" + amount + ", cardNumber=" + cardNumber + ", cardExpYear=" + cardExpYear
				+ ", cardExpMonth=" + cardExpMonth + ", cardName=" + cardName + ", cardCVV=" + cardCVV + "]";
	}

}

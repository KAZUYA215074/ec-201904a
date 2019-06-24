package com.example.ecommerce_a.domain;

/**
 * CreditCard APIを叩いた時のレスポンスを表すドメイン.
 * 
 * @author Makoto
 *
 */
public class ResponceCreditCardServerInfo {

	/** ステータス */
	private String status;
	/** メッセージ */
	private String message;
	/** エラーコード */
	private String errorCode;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	@Override
	public String toString() {
		return "ResponceCreditCardServerInfo [status=" + status + ", message=" + message + ", errorCode=" + errorCode
				+ "]";
	}

}

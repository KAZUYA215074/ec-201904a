package com.example.ecommerce_a.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * ユーザー情報登録時に使うフォーム.
 * 
 * @author yuki
 *
 */
public class InsertUserForm {
	
	/** ID */
	private Integer id;
	/** 名前 */
	@NotBlank(message="名前を入力してください")
	private String name;
	/** メールアドレス */
	@NotBlank(message="メールアドレスを入力してください")
	@Email(message="メールアドレスの形式ではありません")
	private String mailAddress;
	/** パスワード */
	@NotBlank(message="パスワードを入力してください")
	@Size(min = 6, max = 12, message="パスワードは６〜１２文字で入力してください")
	private String password;
	/** 確認用パスワード */
	@NotBlank(message="パスワードを入力してください")
	@Size(min = 6, max = 12, message="パスワードは６〜１２文字で入力してください")
	private String checkedpassword;
	/** 郵便番号 */
	@NotBlank(message="郵便番号を入力してください")
	private String zipCode;
	/** 住所 */
	@NotBlank(message="住所を入力してください")
	private String address;
	/** 電話番号 */
	@NotBlank(message="電話番号を入力してください")
	private String telephone;
	


	@Override
	public String toString() {
		return "InsertUserForm [id=" + id + ", name=" + name + ", mailAddress=" + mailAddress + ", password=" + password
				+ ", checkedpassword=" + checkedpassword + ", zipCode=" + zipCode + ", address=" + address
				+ ", telephone=" + telephone + "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMailAddress() {
		return mailAddress;
	}

	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getCheckedpassword() {
		return checkedpassword;
	}

	public void setCheckedpassword(String checkedpassword) {
		this.checkedpassword = checkedpassword;
	}
	

}

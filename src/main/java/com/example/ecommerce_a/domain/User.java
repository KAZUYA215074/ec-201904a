package com.example.ecommerce_a.domain;

/**
 * ユーザ情報のドメイン.
 * 
 * @author yuki.maekawa
 *
 */
public class User {
	
	/** ID */
	private Integer id;
	/** 名前 */
	private String name;
	/** メールアドレス */
	private String mailAddress;
	/** パスワード */
	private String password;
	/** 郵便番号 */
	private String zipCode;
	/** 住所 */
	private String address;
	/** 電話番号 */
	private String telephone;
	
	public User() {
	}
	public User(Integer id, String name, String mailAddress, String password, String zipCode, String address,
			String telephone) {
		super();
		this.id = id;
		this.name = name;
		this.mailAddress = mailAddress;
		this.password = password;
		this.zipCode = zipCode;
		this.address = address;
		this.telephone = telephone;
	}


	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", mailAddress=" + mailAddress + ", password=" + password
				+ ", zipCode=" + zipCode + ", address=" + address + ", telephone=" + telephone + "]";
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
	

}

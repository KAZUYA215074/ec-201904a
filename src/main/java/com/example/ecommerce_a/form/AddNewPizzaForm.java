package com.example.ecommerce_a.form;

import javax.validation.constraints.NotBlank;

import org.springframework.web.multipart.MultipartFile;

public class AddNewPizzaForm {
	/**商品名*/
	@NotBlank(message="商品名を入力してください")
	private String name;
	/**商品説明*/
	@NotBlank(message="商品説明を入力してください")
	private String description;
	/**Mサイズの値段*/
	@NotBlank(message="価格を入力してください")
	private String priceM;
	/**Lサイズの値段*/
	@NotBlank(message="価格を入力してください")
	private String priceL;
	/**画像パス*/
	private MultipartFile imagePath;
	@Override
	public String toString() {
		return "AddNewPizzaForm [name=" + name + ", description=" + description + ", priceM=" + priceM + ", priceL="
				+ priceL + ", imagePath=" + imagePath + "]";
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPriceM() {
		return priceM;
	}
	public void setPriceM(String priceM) {
		this.priceM = priceM;
	}
	public String getPriceL() {
		return priceL;
	}
	public void setPriceL(String priceL) {
		this.priceL = priceL;
	}
	public MultipartFile getImagePath() {
		return imagePath;
	}
	public void setImagePath(MultipartFile imagePath) {
		this.imagePath = imagePath;
	}
	
	

}

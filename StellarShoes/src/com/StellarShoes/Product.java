package com.StellarShoes;

public class Product {
	private int productID;
	private String name;
	private double size;
	private String color;
	private double price;
	private int categoryId;
	private String imgUrl;
	private String description;
	private int quantity;
	
	
	public Product() {}
	
	/**
	 * @param productID
	 * @param name
	 * @param size
	 * @param color
	 * @param price
	 * @param categoryId
	 * @param imgUrl
	 * @param description
	 * @param quantity
	 */
	public Product(int productID, String name, double size, String color, double price, int categoryId, String imgUrl,
			String description, int quantity) {
		super();
		this.productID = productID;
		this.name = name;
		this.size = size;
		this.color = color;
		this.price = price;
		this.categoryId = categoryId;
		this.imgUrl = imgUrl;
		this.description = description;
		this.quantity = quantity;
	}

	public int getProductID() {
		return productID;
	}
	public void setProductID(int productID) {
		this.productID = productID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getSize() {
		return size;
	}
	public void setSize(double size) {
		this.size = size;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	
	
	
	
}

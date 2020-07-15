package com.StellarShoes;

public class Product {
	private int productID;
	private String name;
	private int size;
	private String color;
	private double price;
	private int categoryId;
	private String imgUrl;
	private String description;
	
	
	
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
	 */
	public Product(int productID, String name, int size, String color, double price, int categoryId, String imgUrl,
			String description) {
		super();
		this.productID = productID;
		this.name = name;
		this.size = size;
		this.color = color;
		this.price = price;
		this.categoryId = categoryId;
		this.imgUrl = imgUrl;
		this.description = description;
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
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
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
	
	
	
	
	
}

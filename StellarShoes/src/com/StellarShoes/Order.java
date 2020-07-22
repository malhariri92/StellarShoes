package com.StellarShoes;

import java.util.ArrayList;
import java.util.List;

public class Order {
   
	private int orderID;
	
	private String status;
	
	private String fName;
	
	private String lastName;
	
	private Address address = new Address();

	private List<Product> products = new ArrayList<>();
	
	private double total;
	
	private String date;
	
	public Order() {}

	/**
	 * @param orderID
	 * @param status
	 * @param fName
	 * @param lastName
	 * @param address
	 * @param products
	 * @param total
	 */
	public Order(int orderID, String status, String fName, String lastName, Address address, List<Product> products,
			double total, String date) {
		super();
		this.orderID = orderID;
		this.status = status;
		this.fName = fName;
		this.lastName = lastName;
		this.address = address;
		this.products = products;
		this.total = total;
		this.date = date;
	}

	public int getOrderID() {
		return orderID;
	}

	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	
	
    
	
	
}

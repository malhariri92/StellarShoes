package com.StellarShoes;

public class Customer {
	private String fName;
	private String lastName;
	private int customerID;
	private String email;
	private String phone;
	private String password;
	private Address address;

	/**
	 * @param fName
	 * @param lastName
	 * @param customerID
	 * @param email
	 * @param phone
	 * @param address
	 */
	public Customer(String fName, String lastName, int customerID, String email, String phone, String password, Address address) {
		super();
		this.fName = fName;
		this.lastName = lastName;
		this.customerID = customerID;
		this.email = email;
		this.phone = phone;
		this.password = password;
		this.address = address;
	}
	public Customer() {}

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

	public int getCustomerID() {
		return customerID;
	}

	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

}

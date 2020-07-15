package com.StellarShoes;

public class Employee {
	private String fName;
	private String lastName;
	private int employeeID;
	private String email;
	private String password;
	
	
	
	
	
	public Employee() {}





	/**
	 * @param fName
	 * @param lastName
	 * @param employeeID
	 * @param email
	 * @param password
	 */
	public Employee(String fName, String lastName, int employeeID, String email, String password) {
		super();
		this.fName = fName;
		this.lastName = lastName;
		this.employeeID = employeeID;
		this.email = email;
		this.password = password;
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





	public int getEmployeeID() {
		return employeeID;
	}





	public void setEmployeeID(int employeeID) {
		this.employeeID = employeeID;
	}





	public String getEmail() {
		return email;
	}





	public void setEmail(String email) {
		this.email = email;
	}





	public String getPassword() {
		return password;
	}





	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
}
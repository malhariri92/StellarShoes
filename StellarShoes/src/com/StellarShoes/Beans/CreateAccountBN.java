package com.StellarShoes.Beans;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.StellarShoes.*;
import com.StellarShoes.utils.DatabaseConnector;
import com.StellarShoes.utils.LoginValidator;
import com.StellarShoes.utils.Messages;
import com.StellarShoes.utils.PasswordValidator;

/**
 * A managed bean class to carry out the create account functionality.
 * @author Mutasem Alhariri 
 *         07/04/2020
 *         Version 1.0
 *
 */
public class CreateAccountBN implements Serializable{
	
	private static final long serialVersionUID = 1L;
private static final String createSQL = "INSERT INTO CUSTOMER (FIRST_NAME, LAST_NAME, EMAIL_ADDRESS, PHONE_NUMBER,"
		+ " PASSWORD) "
		+ "VALUES (?, ?, ?, ?, ?)";

private static final String createAdminSQL = "INSERT INTO ADMIN (FIRST_NAME, EMAIL_ADDRESS, EMPLOYEE_ID,"
		+ " PASSWORD) "
		+ "VALUES (?, ?, ?, ?)";

private static final String addressSQL = "Insert INTO ADDRESS (CUSTOMER_ID, STREET_ADDRESS1, STREET_ADDRESS2, "
		+ "CITY, STATE, ZIP) VALUES (?, ?, ?, ?, ?, ?)";

private static final String pswRequirments = "Password must be between 8 and 13 " + 
		" characters and must contain at least one of each of the following: uppercase, lowercase," + 
		" digit, and one of these special characters (/<>@#!%+&^)." + 
		" Password cannot contain white spaces.";

private static final String emailInUse = "This email is already in use. If you forgot you password," + 
		                                 " please go to the login page and select forgot password.";

private String email2;
private String password2;

private Customer customer = new Customer();
private Address address = new Address();
private Employee employee = new Employee();


public CreateAccountBN() {}


/**
 * To insert a new customer into the customers table.
 * @return createAccount page with error messages if invalid data was entered, login page with success message otherwise.
 */
public String registerCustomer() {

	if(PasswordValidator.validate(customer.getPassword()) == false) {
	
		Messages.show(pswRequirments);
		return "createAccount";
	}
	
	if(!password2.equals(customer.getPassword())) {
		Messages.show("Passwords mismatch: the two passwords must be identical.");
		return "createAccount";
	}
	if(!email2.equals(customer.getEmail())) {
		Messages.show("Emails mismatch: the two emails must be identical.");
		return "createAccount";
	}
	
	if(!LoginValidator.exist(customer.getEmail())) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try  {
			conn = DatabaseConnector.getConnection();
			pstmt = conn.prepareStatement(createSQL);
			pstmt.setString(1, customer.getfName());
			pstmt.setString(2, customer.getLastName());
			pstmt.setString(3, customer.getEmail());
			pstmt.setString(4, customer.getPhone());
			pstmt.setString(5, customer.getPassword());
			
			int rs = pstmt.executeUpdate();
			if (rs > 0) {
			   if(insertAddress()) {
				Messages.show("Your Account has been successfully created!"
						+ " Please use your email and password to log in.");
				return "login";
			   }else {
				   deleteCustomer();
				   return "createAccount"; 
			   }
			} 
	       pstmt.close();
		} catch (SQLException ex) {
			
			System.out.println("error -->" + ex.getMessage());
			
			
		} finally {
			DatabaseConnector.close(conn);
		}
		
		
	}else {
		Messages.show(emailInUse);
	}
	return "createAccount";
	}

/**
 * To insert a new admin into the admin table.
 * @return createAdminAccount page and error messages if invalid data was entered, login page and success message otherwise. 
 */
public String registerAdmin() {

	if(PasswordValidator.validate(employee.getPassword()) == false) {
	
		Messages.show(pswRequirments);
		return "createAdminAccount";
	}
	
	if(!password2.equals(employee.getPassword())) {
		Messages.show("Passwords mismatch: the two passwords must be identical.");
		return "createAccount";
	}
	if(!email2.equals(employee.getEmail())) {
		Messages.show("Emails mismatch: the two emails must be identical.");
		return "createAdminAccount";
	}
	if(!LoginValidator.isEmployee(employee.getEmail(),employee.getEmployeeID())) {
		
		Messages.show("There is no employee with this email or employee ID! Cannot Create Admin Account.");
		return "createAdminAccount";
	}
	if(!LoginValidator.exist(employee.getEmail())) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try  {
			conn = DatabaseConnector.getConnection();
			pstmt = conn.prepareStatement(createAdminSQL);
			pstmt.setString(1, employee.getfName());
			pstmt.setString(2, employee.getEmail());
			pstmt.setInt(3, employee.getEmployeeID());
			pstmt.setString(4, employee.getPassword());
			
			int rs = pstmt.executeUpdate();
			if (rs > 0) {
			  
				Messages.show("Your Account has been successfully created!"
						+ " Please use your email and password to log in.");
				return "login";
			  
			} 
	       pstmt.close();
		} catch (SQLException ex) {
			
			System.out.println("error from registerAdmin -->" + ex.getMessage());
			
			
		} finally {
			DatabaseConnector.close(conn);
		}
		
		
	}else {
		Messages.show(emailInUse);
	}
	return "createAdminAccount";
	}

/**
 * To insert a new address for the new customer.
 * @return True if the new address is successfully created, false otherwise.
 */
public boolean insertAddress() {
	setCustomerID();
	
	Connection conn = null;
	PreparedStatement pstmt = null;
	
	try  {
		conn = DatabaseConnector.getConnection();
		pstmt = conn.prepareStatement(addressSQL);
		pstmt.setInt(1, customer.getCustomerID());
		pstmt.setString(2, address.getAddress1());
		pstmt.setString(3, address.getAddress2());
		pstmt.setString(4, address.getCity());
		pstmt.setString(5, address.getState());
		pstmt.setInt(6, address.getZip());
		
		int rs = pstmt.executeUpdate();
		if (rs > 0) {

			return true;
		} 
       pstmt.close();
	} catch (SQLException ex) {
		
		System.out.println("error -->" + ex.getMessage());
		
		
	} finally {
		DatabaseConnector.close(conn);
	}
	Messages.show("An error has occurred, please try again later.");
	return false;
}

/**
 * To get the new customer's ID from the customers table. will be called after the new customer is inserted.
 */
private void setCustomerID() {
	Connection conn = null;
	PreparedStatement pstmt = null;
	
	try  {
		conn = DatabaseConnector.getConnection();
		pstmt = conn.prepareStatement("SELECT CUSTOMER_ID FROM CUSTOMER WHERE EMAIL_ADDRESS = ? AND PASSWORD = ?");
		pstmt.setString(1, customer.getEmail());
		pstmt.setString(2, customer.getPassword());
		
		
		ResultSet rs = pstmt.executeQuery();
		if (rs.next()) {
		 customer.setCustomerID(rs.getInt(1)) ;
		} 
		rs.close();
       pstmt.close();
	} catch (SQLException ex) {
		
		System.out.println("error -->" + ex.getMessage());
		
		
	} finally {
		DatabaseConnector.close(conn);
	}	
}

/**
 * To delete the customer from the customers table. will be called inserting the address fails.
 */
private void deleteCustomer() {
	Connection conn = null;
	PreparedStatement pstmt = null;
	
	try  {
		conn = DatabaseConnector.getConnection();
		pstmt = conn.prepareStatement("DELETE FROM CUSTOMER WHERE EMAIL_ADDRESS = ? AND PASSWORD = ?");
		pstmt.setString(1, customer.getEmail());
		pstmt.setString(2, customer.getPassword());
		
		
		int rs = pstmt.executeUpdate();
		
       pstmt.close();
	} catch (SQLException ex) {
		
		System.out.println("error -->" + ex.getMessage());
		
		
	} finally {
		DatabaseConnector.close(conn);
	}	
}




public Customer getCustomer() {
	return customer;
}



public void setCustomer(Customer customer) {
	this.customer = customer;
}



public Address getAddress() {
	return address;
}



public void setAddress(Address address) {
	this.address = address;
}



public String getEmail2() {
	return email2;
}

public void setEmail2(String email2) {
	this.email2 = email2;
}

public String getPassword2() {
	return password2;
}

public void setPassword2(String password2) {
	this.password2 = password2;
}



public Employee getEmployee() {
	return employee;
}



public void setEmployee(Employee employee) {
	this.employee = employee;
}

	
}






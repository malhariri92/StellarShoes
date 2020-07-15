
package com.StellarShoes.Beans;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.http.HttpSession;

import com.StellarShoes.*;
import com.StellarShoes.utils.*;

public class AccountManager implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String email;
	private String password;
	private Customer customer = null;
	private boolean isLoggedin = false;
	public static Customer sessionCustomer = null;
	private static final String customerSql = "Select c.FIRST_NAME, c.LAST_NAME, c.CUSTOMER_ID, c.PHONE_NUMBER, "
			+ " a.STREET_ADDRESS1, a.STREET_ADDRESS2, a.CITY, a.STATE, a.ZIP FROM Customer c "
			+ "LEFT JOIN ADDRESS a "
			+ "ON c.CUSTOMER_ID = a.CUSTOMER_ID "
			+ "WHERE c.EMAIL_ADDRESS = ?";
    
	
	private static final String incorrectPasswordMsg = "Incorrect password! Password must be between 8 and 13 " + 
			"characters and must contain at least one of each of the following: uppercase, lowercase, " + 
			"digit, and one of these special characters (/<>@#!%+&^). " + 
			"Password cannot contain white spaces.";
	
	public AccountManager() {
	}

	public String login() {
		
		if(PasswordValidator.validate(password) == false) {
			
			Messages.show(incorrectPasswordMsg);
			return "login";
		}
		
		if(email.endsWith("@stellarshoes.com")) {
			
			if(LoginValidator.validateAdmin(email, password)) {
				
				HttpSession session = SessionManager.getSession();
				session.setAttribute("admin", email);
				
				return "adminHome?faces-redirect=true";
			}
			else {
				
				
				Messages.show("Incorrect username or password!");
				return "login";
			}
		}
		
		else {
			if(LoginValidator.validateCustomer(email, password)) {
				HttpSession session = SessionManager.getSession();
				session.setAttribute("customer", email);
				
				getCustomerinfo();			
			isLoggedin = true;
			return "success?faces-redirect=true";
		   } else {
			
			Messages.show("Incorrect username or password!");
			
			return "login";
		   }
		}
		
	}
	//logout method, invalidate session
		public String logout() {
			HttpSession session = SessionManager.getSession();
			session.invalidate();
			isLoggedin = false;
			email = "";
			password = "";
			customer = null;
			sessionCustomer = null;
			return "login.xhtml?faces-redirect=true";
		}
		
	
		
	private void getCustomerinfo() {
		boolean valid = LoginValidator.validateCustomer(email, password);
		 if(valid && customer == null) {
			Connection conn = null;
			PreparedStatement pstmt = null;
	
			try  {
				conn = DatabaseConnector.getConnection();
				pstmt = conn.prepareStatement(customerSql);
				pstmt.setString(1, email);
			    
				ResultSet rs = pstmt.executeQuery();
				if (rs.next()) {
				Address address = new Address(rs.getString(5),rs.getString(6),
						rs.getString(7),rs.getString(8),rs.getInt(9));
				customer = new Customer(rs.getString(1), rs.getString(2), rs.getInt(3), email, rs.getString(4), 
						password, address);
				sessionCustomer = customer;
				}

				rs.close();
			} catch (SQLException ex) {
				
				System.out.println("error -->" + ex.getMessage());
				
			} finally {
				DatabaseConnector.close(conn);
			}
		}
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

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public static Customer getSessionCustomer() {
		return sessionCustomer;
	}

	public static void setSessionCustomer(Customer sessionCustomer) {
		AccountManager.sessionCustomer = sessionCustomer;
	}

	public boolean isLoggedin() {
		return isLoggedin;
	}

	public void setLoggedin(boolean isLoggedin) {
		this.isLoggedin = isLoggedin;
	}

	

	
}

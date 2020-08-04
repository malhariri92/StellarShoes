
package com.StellarShoes.Beans;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import com.StellarShoes.*;
import com.StellarShoes.utils.*;

/**
 * A managed bean class to carry out the login/logout process.
 * @author Mutasem Alhariri
 *         07/04/2020
 *         Version 1.0
 *
 */
public class AccountManager implements Serializable {

	private static final long serialVersionUID = 1L;

	private String email;
	private String password;
	private Customer customer = null;
	private Employee employee = null;
	private boolean isLoggedin = false;
	public static Customer sessionCustomer = null; //To be used by other classes to get logged in customer info.
	
	private static final String customerSql = "Select c.FIRST_NAME, c.LAST_NAME, c.CUSTOMER_ID, c.PHONE_NUMBER, "
			+ " a.STREET_ADDRESS1, a.STREET_ADDRESS2, a.CITY, a.STATE, a.ZIP FROM Customer c " + "LEFT JOIN ADDRESS a "
			+ "ON c.CUSTOMER_ID = a.CUSTOMER_ID " + "WHERE c.EMAIL_ADDRESS = ?";
	
	private static final String adminSql = "SELECT FIRST_NAME FROM ADMIN WHERE EMAIL_ADDRESS = ? AND PASSWORD =?";

	private static final String incorrectPasswordMsg = "Incorrect password! Password must be between 8 and 13 "
			+ "characters and must contain at least one of each of the following: uppercase, lowercase, "
			+ "digit, and one of these special characters (/<>@#!%+&^). " + "Password cannot contain white spaces.";

	private String pageId;

	public AccountManager() {
	}

	/**
	 * To login users by creating new session.
	 * 
	 * @return login.xhtml if the data is valid, manageAccount.xhtml if valid
	 *         customer, otherwise adminHome.xhtml
	 */
	public String login() {

		if (PasswordValidator.validate(password) == false) {

			Messages.show(incorrectPasswordMsg);
			return "login";
		}

		if (email.endsWith("@stellarshoes.com")) {

			if (LoginValidator.validateAdmin(email, password)) {

				HttpSession session = SessionManager.getSession();
				session.setAttribute("admin", email);

				getAdminInfo();

				return "adminHome?faces-redirect=true";
			} else {

				Messages.show("Incorrect username or password!");
				return "login";
			}

		} else if (LoginValidator.validateCustomer(email, password)) {
			HttpSession session = SessionManager.getSession();
			session.setAttribute("customer", email);

			getCustomerinfo();

			isLoggedin = true;
			FacesContext fc = FacesContext.getCurrentInstance();
			Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
			pageId = params.get("pageId");

			if (pageId != null) {
				return "checkout?faces-redirect=true";
			} else {

				return "manageAccount?faces-redirect=true";
			}
		} else {

			Messages.show("Incorrect username or password!");

			return "login";
		}
	}

	/**
	 * To logout a user by invalidating the session.
	 * 
	 * @return
	 */
	public String logout() {
		HttpSession session = SessionManager.getSession();
		session.invalidate();
		isLoggedin = false;
		email = "";
		password = "";
		customer = null;
		employee = null;
		sessionCustomer = null;
		return "login.xhtml?faces-redirect=true";
	}

	/**
	 * To get the logged in customer information from the database.
	 */
	private void getCustomerinfo() {
		boolean valid = LoginValidator.validateCustomer(email, password);
		if (valid && customer == null) {
			Connection conn = null;
			PreparedStatement pstmt = null;

			try {
				conn = DatabaseConnector.getConnection();
				pstmt = conn.prepareStatement(customerSql);
				pstmt.setString(1, email);

				ResultSet rs = pstmt.executeQuery();
				if (rs.next()) {
					Address address = new Address(rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8),
							rs.getInt(9));
					customer = new Customer(rs.getString(1).strip(), rs.getString(2).strip(), rs.getInt(3), email,
							rs.getString(4), password, address);
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

	/**
	 * To get the current logged in admin's information from the database.
	 */
	private void getAdminInfo() {
		boolean valid = LoginValidator.validateAdmin(email, password);
		if (valid && employee == null) {
			Connection conn = null;
			PreparedStatement pstmt = null;

			try {
				conn = DatabaseConnector.getConnection();
				pstmt = conn.prepareStatement(adminSql);
				pstmt.setString(1, email);
				pstmt.setString(2, password);
				ResultSet rs = pstmt.executeQuery();
				if (rs.next()) {
					employee = new Employee(rs.getString(1), null, 0, email, password);
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

	public String getPageId() {
		return pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

}

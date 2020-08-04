package com.StellarShoes.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

/**
 * To validate user information for login purposes.
 * @author Mutasem Alhariri 
 *         07/04/2020
 *         Version 1.0
 *
 */
public class LoginValidator {

	
	private static final String customerSQL = "SELECT * from CUSTOMER where EMAIL_ADDRESS = ? AND PASSWORD = ?";
	private static final String employeeSQL = "SELECT * from Employee where EMAIL_ADDRESS = ? AND EMPLOYEE_ID = ?";
	private static final String adminSQL = "SELECT * from ADMIN where EMAIL_ADDRESS = ? AND PASSWORD = ?";
	private static  String customerExistSQL = "Select * from CUSTOMER WHERE EMAIL_ADDRESS = ?";
	private static  String adminExistSQL = "Select * from ADMIN WHERE EMAIL_ADDRESS = ?";
	private static  String existSQL;
	
	
	
	/**
	 * To check if the customer is using a correct email and password.
	 * @param user contains the email entered by the customer.
	 * @param password contains the password entered by the customer.
	 * @return true if the the email and password are valid, false otherwise.
	 */
	public static boolean validateCustomer(String user, String password) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try  {
			conn = DatabaseConnector.getConnection();
			pstmt = conn.prepareStatement(customerSQL);
			pstmt.setString(1, user);
			pstmt.setString(2, password);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
			
				return true;
			}

			rs.close();
			pstmt.close();
		} catch (SQLException ex) {
			
			System.out.println("Login error -->" + ex.getMessage());
			
			return false;
		} finally {
			DatabaseConnector.close(conn);
		}
		return false;
	}
	
	/**
	 * To check if the customer is using a correct email and password.
	 * @param user contains the email entered by the customer.
	 * @param password contains the password entered by the customer.
	 * @return true if the the email and password are valid, false otherwise.
	 */
	public static boolean validateAdmin(String user, String password) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try  {
			conn = DatabaseConnector.getConnection();
			pstmt = conn.prepareStatement(adminSQL);
			pstmt.setString(1, user);
			pstmt.setString(2, password);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
			
				return true;
			}

			rs.close();
			pstmt.close();
		} catch (SQLException ex) {
			
			System.out.println("error from -->" + ex.getMessage());
			
			return false;
		} finally {
			DatabaseConnector.close(conn);
		}
		return false;
	}
	/**
	 * to check if the employee who is trying to create an account already has an account.  
	 * @param email the email entered by the employee.
	 * @return true if the email in use, false otherwise
	 */
	public static boolean exist(String email) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		if(email.endsWith("@stellarshoes.com")) {
			
			existSQL = adminExistSQL;
					
		} else {
			existSQL = customerExistSQL;
		}
		
		try  {
			conn = DatabaseConnector.getConnection();
			pstmt = conn.prepareStatement(existSQL);
			pstmt.setString(1, email);
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				return true;
			}

		} catch (SQLException ex) {
			
			System.out.println("error -->" + ex.getMessage());
			
			return false;
		} finally {
			DatabaseConnector.close(conn);
		}
		return false;
	}
	
	
	/**
	 * To check if the entered information matches an existing employee in the employee table.
	 * @param email the email entered by the employee.
	 * @param employeeID the employee ID entered by the employee.
	 * @return
	 */
	public static boolean isEmployee(String email, int employeeID) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try  {
			conn = DatabaseConnector.getConnection();
			pstmt = conn.prepareStatement(employeeSQL);
			pstmt.setString(1, email);
			pstmt.setInt(2, employeeID);
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				return true;
			}

		} catch (SQLException ex) {
			
			System.out.println("error -->" + ex.getMessage());
			
			return false;
		} finally {
			DatabaseConnector.close(conn);
		}
		return false;
	}
	}


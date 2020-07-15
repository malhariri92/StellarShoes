package com.StellarShoes.Beans;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.StellarShoes.*;
import com.StellarShoes.utils.DatabaseConnector;
import com.StellarShoes.utils.Messages;

public class Checkout implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private static final String paymentSQL = "SELECT PAYMENT_METHOD, CARD_HOLDER, CARD_NUMBER, CVV, EXPIRATION_DATE "
			+ "FROM PAYMENT WHERE CUSTOMER_ID = ?";
	
	private static final String addressSQL = "SELECT STREET_ADDRESS1, STREET_ADDRESS2, CITY, STATE, ZIP "
			+ "FROM ADDRESS WHERE CUSTOMER_ID = ?";
			
	private Customer customer = AccountManager.sessionCustomer == null ? null: AccountManager.sessionCustomer ;
	
	private Address address = customer == null ? new Address() : getCustomerAddress();
	
	private Payment payment = customer == null ? new Payment() : getPaymentInfo();
	
	private int cvv2;
	
	public Checkout() {}
	
	
	
	
	private Address getCustomerAddress() {
		
		Address addr = null;
		Connection conn = null;
		PreparedStatement pstmt = null;

		try  {
			conn = DatabaseConnector.getConnection();
			pstmt = conn.prepareStatement(addressSQL);
			pstmt.setInt(1, customer.getCustomerID());
		    
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
			 addr = new Address(rs.getString(1),rs.getString(2),
					rs.getString(3),rs.getString(4),rs.getInt(5));
			
			}

			rs.close();
		} catch (SQLException ex) {
			
			System.out.println("error from getCustomerAddress() -->" + ex.getMessage());
			
		} finally {
			DatabaseConnector.close(conn);
		}
		return addr;
	}
	
	
private Payment getPaymentInfo() {
		
		Payment pmt = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;

		try  {
			conn = DatabaseConnector.getConnection();
			pstmt = conn.prepareStatement(paymentSQL);
			pstmt.setInt(1, customer.getCustomerID());
		    
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
			 pmt = new Payment(rs.getInt(1),rs.getString(2),
					rs.getLong(3),rs.getInt(4),rs.getString(5));
			
			}

			rs.close();
		} catch (SQLException ex) {
			
			System.out.println("error getPaymentInfo()  -->" + ex.getMessage());
			
		} finally {
			DatabaseConnector.close(conn);
		}
		return pmt;
	}
public void openLoginDlg() {
    Messages.show("will open login page");
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




	public int getCvv2() {
		return cvv2;
	}




	public void setCvv2(int cvv2) {
		this.cvv2 = cvv2;
	}




	public Payment getPayment() {
		return payment;
	}




	public void setPayment(Payment payment) {
		this.payment = payment;
	}
	

	
	}


package com.StellarShoes.Beans;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.StellarShoes.*;
import com.StellarShoes.utils.DatabaseConnector;
import com.StellarShoes.utils.Messages;

public class Checkout implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private static final String paymentSQL = "SELECT PAYMENT_ID, PAYMENT_METHOD, CARD_HOLDER, CARD_NUMBER, CVV, EXPIRATION_DATE "
			+ "FROM PAYMENT WHERE CARD_NUMBER = ? AND CVV = ?";
	
	private static final String addressSQL = "SELECT STREET_ADDRESS1, STREET_ADDRESS2, CITY, STATE, ZIP "
			+ "FROM ADDRESS WHERE CUSTOMER_ID = ?";
	
	private static final  String insertCustomer = "INSERT INTO CUSTOMER (FIRST_NAME, LAST_NAME, PHONE_NUMBER) "			
			+ "VALUES (?, ?, ?)";
	
	private static final String newCustomerID = "Select CUSTOMER_ID from CUSTOMER WHERE PHONE_NUMBER = ?";
	
	private static final String insertPaymentSQL = "INSERT INTO PAYMENT (PAYMENT_METHOD, CARD_HOLDER, CARD_NUMBER, CVV,"
			+ " EXPIRATION_DATE, STREET_ADDRESS1, STREET_ADDRESS2, CITY, STATE, ZIP) "
			+ "VALUES (?, ?, ?, ? ,?, ?, ?, ?, ?, ?)";
	
	private static final String orderSQL = "INSERT INTO ORDERS (CUSTOMER_ID, PAYMENT_ID, DATE, TOTAL_AMOUNT, STATUS) "
			+ "VALUES (?, ?, ?, ?, ?)";
	
	private static final String getOrderID = "SELECT ORDER_ID FROM ORDERS WHERE PAYMENT_ID = ?";
	
	private final String insertAddressSQL = "INSERT INTO ADDRESS (CUSTOMER_ID, STREET_ADDRESS1, STREET_ADDRESS2, CITY, STATE, ZIP) "
			+ "VALUES (?, ?, ?, ?, ?, ?)";
	
	private static final  String orderDetailsSQL = "INSERT INTO ORDER_DETAILS (ORDER_ID, SHOE_ID, QUANTITY, SHOE_SIZE) "			
			+ "VALUES (?, ?, ?, ?)";
	
	private Customer customer;
	
	private Address address;
	
	private Address billingAddress = new Address();
	
	private boolean sameAddresses = true;
	
	private Payment payment  = new Payment();
	
	private int cvv2;
	
	private int selectedMethod;
	
	private Product product;
	private int orderID;
	private int customerID;
	
	
	public Checkout() {
		
		 if(AccountManager.sessionCustomer != null) {
			 customer = AccountManager.sessionCustomer; 
			 address = getCustomerAddress();
			 
		 } else {
			 customer = new Customer();
			 address = new Address();
		 }   
	}
	
	public String placeOrder(double total, List<Product> products) {
		
		if(customer.getCustomerID() == 0) {
			if(placeNewCustomerOrder(total) && inserOrderDetails(products)) {
				OrderBN.assignOrder(orderID, payment);
				return "confirmation?faces-redirect=true";
			}
			
			
		} else {
                 if(placeExistingCustomerOrder(total) && inserOrderDetails(products)) {
                	 OrderBN.assignOrder(orderID, payment);
				return "confirmation?faces-redirect=true";
			}
		}
		Messages.show("Something went wrong please try again later!");
		return null;
	}
	
	
	
	
	
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
	
	
private void getPaymentID() {
		
		Connection conn = null;
		PreparedStatement pstmt = null;

		try  {
			conn = DatabaseConnector.getConnection();
			pstmt = conn.prepareStatement(paymentSQL);
			pstmt.setString(1, payment.getCardNumber());
			pstmt.setInt(2, payment.getCvv());
		    
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
			payment.setPaymentID(rs.getInt(1));
			
			}

			rs.close();
		} catch (SQLException ex) {
			
			System.out.println("error getPaymentID()  -->" + ex.getMessage());
			
		} finally {
			DatabaseConnector.close(conn);
		}
	}
      
       
    public boolean placeExistingCustomerOrder(double total) {
    	
    	
    	if(!address.getAddress1().equals(getCustomerAddress().getAddress1())) {
    		insertNewAddress();
    	}
    	
    	if(insertPayment()) {
    		customerID = customer.getCustomerID();
    		Connection conn = null;
    		PreparedStatement pstmt = null;
    		
    		try  {
    			conn = DatabaseConnector.getConnection();
    			pstmt = conn.prepareStatement(orderSQL);
    			pstmt.setInt(1, customerID);
    			pstmt.setInt(2, payment.getPaymentID());
    			pstmt.setString(3, getDate());
    			pstmt.setDouble(4, total);
    			pstmt.setString(5, "A");
    			
    			
    			int rs = pstmt.executeUpdate();
    			if (rs > 0) {
    				return true;
    				
    			} 
    			
    	       pstmt.close();
    		} catch (SQLException ex) {
    			
    			System.out.println("error --> from place existing customer order" + ex.getMessage());
    			
    			
    		} finally {
    			DatabaseConnector.close(conn);
    		}
    	}
    	return false;
    }
    
    
    public boolean placeNewCustomerOrder(double total) {
    	
    	if (insertNewAddress() && insertPayment()) {
    		Connection conn = null;
    		PreparedStatement pstmt = null;
    		
    		try  {
    			conn = DatabaseConnector.getConnection();
    			pstmt = conn.prepareStatement(orderSQL);
    			pstmt.setInt(1, customerID);
    			pstmt.setInt(2, payment.getPaymentID());
    			pstmt.setString(3, getDate());
    			pstmt.setDouble(4, total);
    			pstmt.setString(5, "A");
    			
    			
    			int rs = pstmt.executeUpdate();
    			if (rs > 0) {
    				return true;
    				
    			} 
    			
    	       pstmt.close();
    		} catch (SQLException ex) {
    			
    			System.out.println("error --> from place new customer order" + ex.getMessage());
    			
    			
    		} finally {
    			DatabaseConnector.close(conn);
    		}
    	}
    	
    	return false;
    }   
    
    private boolean insertPayment() {
    	boolean result = false;
    	Connection conn = null;
		PreparedStatement pstmt = null;
		
		if(sameAddresses) {
			billingAddress = address;
		}
		try  {
			conn = DatabaseConnector.getConnection();
			pstmt = conn.prepareStatement(insertPaymentSQL);
			pstmt.setInt(1, selectedMethod);
			pstmt.setString(2, payment.getCardHolder());
			pstmt.setString(3, payment.getCardNumber());
			pstmt.setInt(4, payment.getCvv());
			pstmt.setString(5, payment.getExpiration());
			pstmt.setString(6, billingAddress.getAddress1());
			pstmt.setString(7, billingAddress.getAddress2());
			pstmt.setString(8, billingAddress.getCity());
			pstmt.setString(9, billingAddress.getState());
			pstmt.setInt(10, billingAddress.getZip());
			
			int rs = pstmt.executeUpdate();
			if (rs > 0) {
				getPaymentID();
				result = true;
			} 
	       pstmt.close();
		} catch (SQLException ex) {
			
			System.out.println("error --> from insert payment" + ex.getMessage());
			
			
		} finally {
			DatabaseConnector.close(conn);
		}
		
		
    	return result ;
    }
	


	public int insertNewCustomer() {
    	Connection conn = null;
		PreparedStatement pstmt = null;
		int ID = 0;
		try  {
			conn = DatabaseConnector.getConnection();
			pstmt = conn.prepareStatement(insertCustomer);
			pstmt.setString(1, customer.getfName());
			pstmt.setString(2, customer.getLastName());
			pstmt.setString(3, customer.getPhone());

			int rs = pstmt.executeUpdate();
			if (rs > 0) {
			 ID = getNewCustomerID(); 
			} 
	       pstmt.close();
		} catch (SQLException ex) {
			
			System.out.println("error -->" + ex.getMessage());
			
			
		} finally {
			DatabaseConnector.close(conn);
		}
		
		
    	return ID ;
    }
    

    public boolean insertNewAddress() {
    	if(customer.getCustomerID() == 0) {
    		customerID = insertNewCustomer();
    	}else {
    	  customerID = customer.getCustomerID();	
    	}
    	
    	boolean result = false;
    	Connection conn = null;
		PreparedStatement pstmt = null;
		
		try  {
			conn = DatabaseConnector.getConnection();
			pstmt = conn.prepareStatement(insertAddressSQL);
			pstmt.setInt(1, customerID);
			pstmt.setString(2, address.getAddress1());
			pstmt.setString(3, address.getAddress2());
			pstmt.setString(4, address.getCity());
			pstmt.setString(5, address.getState());
			pstmt.setInt(6, address.getZip());
			
			
			int rs = pstmt.executeUpdate();
			if (rs > 0) {
				
			  result = true;
			} 
			
			pstmt.close();
	       
		} catch (SQLException ex) {
			
			System.out.println("error from insertNewAddress -->" + ex.getMessage());
			
			
		} finally {
			DatabaseConnector.close(conn);
			
		}
		return result;
    }
    
    
       
	private int getNewCustomerID() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try  {
			conn = DatabaseConnector.getConnection();
			pstmt = conn.prepareStatement(newCustomerID);
			pstmt.setString(1, customer.getPhone());

			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
			 customerID = rs.getInt(1);
			 
			} 
			rs.close();
	       pstmt.close();
		} catch (SQLException ex) {
			
			System.out.println("error from get new customerID -->" + ex.getMessage());
			
			
		} finally {
			DatabaseConnector.close(conn);
		}	
		return customerID;
	}
	
	
	
     public boolean inserOrderDetails(List<Product> products) {
	 boolean result = false;
	  getNewOrderID();
	  
	  int quantity = 1;
	  
	  for(Product item : products) {
		  Connection conn = null;
		  PreparedStatement pstmt = null; 
		  
		  try  {
				conn = DatabaseConnector.getConnection();
				pstmt = conn.prepareStatement(orderDetailsSQL);
				pstmt.setInt(1, orderID);
				pstmt.setInt(2, item.getProductID());
				pstmt.setInt(3, quantity);
				pstmt.setDouble(4, item.getSize());
				
				int rs = pstmt.executeUpdate();
				if (rs > 0) {
					
				  result = true;
				} 
				
				pstmt.close();
		       
			} catch (SQLException ex) {
				
				System.out.println("error from insert order details -->" + ex.getMessage());
				
				
			} finally {
				DatabaseConnector.close(conn);
				
			}
	  }
	 return result;
 }

	private void getNewOrderID() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try  {
			conn = DatabaseConnector.getConnection();
			pstmt = conn.prepareStatement(getOrderID);
			pstmt.setInt(1, payment.getPaymentID());

			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
			 orderID = rs.getInt(1);
			 
			} 
			rs.close();
	       pstmt.close();
		} catch (SQLException ex) {
			
			System.out.println("error from get new customerID -->" + ex.getMessage());
			
			
		} finally {
			DatabaseConnector.close(conn);
		}	
}


	private String getDate() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
		   LocalDateTime date = LocalDateTime.now(); 
		   String strDate = dtf.format(date);
		   return strDate;
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
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getSelectedMethod() {
		return selectedMethod;
	}


	public void setSelectedMethod(int selectedMethod) {
		this.selectedMethod = selectedMethod;
	}


	public boolean isSameAddresses() {
		return sameAddresses;
	}


	public void setSameAddresses(boolean sameAddresses) {
		this.sameAddresses = sameAddresses;
	}


	public Address getBillingAddress() {
		return billingAddress;
	}


	public void setBillingAddress(Address billingAddress) {
		this.billingAddress = billingAddress;
	}

	public int getOrderID() {
		return orderID;
	}

	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}


}


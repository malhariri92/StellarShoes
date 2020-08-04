package com.StellarShoes.Beans;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;

import com.StellarShoes.*;
import com.StellarShoes.utils.DatabaseConnector;
import com.StellarShoes.utils.Messages;
import com.StellarShoes.utils.PasswordValidator;

/**
 * To carry out account management tasks for logged in customers.
 * @author Mutasem Alhariri 
 *         07/04/2020
 *         Version 1.0
 *
 */
public class ManageAccount implements Serializable{

	private static final long serialVersionUID = 1L;

	private static final String orderSql = "select order_id, TOTAL_AMOUNT, date, STATUS from  ORDERS "
			+ "WHERE CUSTOMER_ID = ? AND STATUS = 'A'";
	
	private static final String detailsSql = "SELECT d.SHOE_ID, d.QUANTITY, d.SHOE_SIZE, s.SHOE_NAME, s.SHOE_COLOR, "
			+ "SHOE_PRICE, s.IMAGE_URL from ORDER_DETAILS as d\r\n" + 
			"Left join SHOE as s on d.SHOE_ID = s.SHOE_ID \r\n" + 
			"Where d.ORDER_ID = ?";
	private static final String cancelOrderSql = "update orders set status = 'C' where order_id = ?";
	
	private static final String changeAddressSql = "UPDATE ADDRESS SET STREET_ADDRESS1 = ? STREET_ADDRESS2 = ? CITY = ? "
			+ "STATE = ? ZIP = ? WHERE CUSTOMER_ID = ?";
	
	private static final String changePasswordSql = "UPDATE CUSTOMER SET PASSWORD = ? WHERE CUSTOMER_ID = ?";
	
	private static final String pswRequirments = "Password must be between 8 and 13 " + 
			" characters and must contain at least one of each of the following: uppercase, lowercase," + 
			" digit, and one of these special characters (/<>@#!%+&^)." + 
			" Password cannot contain white spaces.";
	
	private Customer customer = new Customer();
	
	private Order order = new Order();
	
	private List<Order> orders = new ArrayList<>();
	
	private Address newAddress = new Address();
	
	private String oldPassword;
	
	private String password1;
	private String password2;
	
	private boolean passwordChanged = false;
	
	/**
	 * To get the information of the logged in customer.
	 */
	public ManageAccount() {
		
		customer = AccountManager.sessionCustomer;
	}


	/**
	 * To get a list of active orders for the current logged in customer.
	 * @return A list of the active orders.
	 */
	public List<Order> myOrders(){
		orders.clear();
		List<Product> products = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		try  {
			conn = DatabaseConnector.getConnection();
			
			pstmt = conn.prepareStatement(orderSql);
			pstmt.setInt(1,customer.getCustomerID()); 
			
			ResultSet rs = pstmt.executeQuery();
					
			while(rs.next()) {
				
				order = new Order(rs.getInt(1), rs.getString(4), null,
						null, null, products, rs.getDouble(2), rs.getString(3));
				
				orders.add(order);
				products = new ArrayList<>();
			}
			
           pstmt.close();
           rs.close();
           
		} catch (SQLException ex) {
			
			System.out.println("error from loadOrders -->" + ex.getMessage());
		} finally {
			DatabaseConnector.close(conn);
		}
		getDetalis();
		return orders;
	}
	
	
    /**
     * To retrieve orders details from the order_details table for each order in the active orders list.
     */
	private void getDetalis() {
		for(Order o : orders) {
			
            
			   Connection conn = null;
				PreparedStatement pstmt = null;
				
				try  {
					conn = DatabaseConnector.getConnection();
					
					pstmt = conn.prepareStatement(detailsSql);
		            pstmt.setInt(1, o.getOrderID());	            
		            
		            
		            ResultSet rs = pstmt.executeQuery();
					
					while(rs.next()) {
						
						Product item = new Product(rs.getInt(1), rs.getString(4), rs.getDouble(3), rs.getString(5), 
								rs.getDouble(6), 0, rs.getString(7), null, rs.getInt(2));
						o.getProducts().add(item);
					
					} 
					
		          pstmt.close();
		          rs.close();
				} catch (SQLException ex) {
					
					System.out.println("error from get details() -->" + ex.getMessage());
				} finally {
					DatabaseConnector.close(conn);
				}
		}
		
	}
    
	/**
	 * To change the status of an order from active to canceled.
	 * @param orderID the order Id for which the status will be changed.
	 * @return the manageAccount page with success message if the status was changed,
	 *  the manageAccount page with error message otherwise.
	 */
    public String cancelOrder(int orderID) {
    	Connection conn = null;
		PreparedStatement pstmt = null;
		try  {
			conn = DatabaseConnector.getConnection();
			
			pstmt = conn.prepareStatement(cancelOrderSql);
			pstmt.setInt(1,orderID); 
			 
			
			int rs = pstmt.executeUpdate();
					
			if(rs > 0) {
			Messages.show("Your order has been canceled!");	
			
			FacesContext context = FacesContext.getCurrentInstance();
			   context.getExternalContext().getFlash().setKeepMessages(true);
			return "manageAccount?faces-redirect=true";
			}
			
           pstmt.close();   
           
		} catch (SQLException ex) {
			
			System.out.println("error from cancel order -->" + ex.getMessage());
		} finally {
			DatabaseConnector.close(conn);
		}
		
		FacesContext context = FacesContext.getCurrentInstance();
		   context.getExternalContext().getFlash().setKeepMessages(true);
		return "manageAccount?faces-redirect=true";
    }

    /**
     * To change the address of the current logged in customer.
     * @return the manageAccount page with success message if the address was changed,
	 *  the manageAccount page with error message otherwise.
     */
    public String changeAddress() {
    	System.out.println(newAddress.getAddress1() +" " +newAddress.getAddress2() +" "+newAddress.getCity()
    	+" "+newAddress.getState()+" "+newAddress.getZip());
    	
    	Connection conn = null;
		PreparedStatement pstmt = null;
		try  {
			conn = DatabaseConnector.getConnection();
			
			pstmt = conn.prepareStatement(changeAddressSql);
			
			pstmt.setString(1,newAddress.getAddress1()); 
			pstmt.setString(2,newAddress.getAddress2());
			pstmt.setString(3,newAddress.getCity());
			pstmt.setString(4,newAddress.getState());
			pstmt.setInt(5,newAddress.getZip()); 
			pstmt.setInt(6, customer.getCustomerID());
			
			int rs = pstmt.executeUpdate();
					
			if(rs > 0) {
			Messages.show("Your address has been successfully changed.");	
				
			} else {
				Messages.show("An error has occurred please try again later.");
			}
			
           pstmt.close();   
           
		} catch (SQLException ex) {
			
			System.out.println("error from cancel order -->" + ex.getMessage());
		} finally {
			DatabaseConnector.close(conn);
		}
		
		FacesContext context = FacesContext.getCurrentInstance();
		   context.getExternalContext().getFlash().setKeepMessages(true);
		return "manageAccount?faces-redirect=true";
    }
    
    /**
     * To change the password of the current logged in customer.
     * @return the manageAccount page with success message if the password was changed,
	 *  the manageAccount page with error message otherwise.
     */
    public String changePassword() {
    	
    	if(!oldPassword.equals(customer.getPassword())){
    		Messages.show("Your current password is not correct.");
    		return null;
    	}
    	if(!PasswordValidator.validate(password1)) {
    		Messages.show(pswRequirments);
    		return null;
    	}
    	if(!password1.equals(password2)) {
    		Messages.show("Password does not match.");
    		return null;
    	}
    	
    	Connection conn = null;
		PreparedStatement pstmt = null;
		try  {
			conn = DatabaseConnector.getConnection();
			
			pstmt = conn.prepareStatement(changePasswordSql);
			
			pstmt.setString(1,password1); 
			pstmt.setInt(2,customer.getCustomerID());
			
			int rs = pstmt.executeUpdate();
					
			if(rs > 0) {
			passwordChanged = true;
			return null;
				
			} else {
				Messages.show("An error has occurred please try again later.");
			}
			
           pstmt.close();   
           
		} catch (SQLException ex) {
			
			System.out.println("error from cancel order -->" + ex.getMessage());
		} finally {
			DatabaseConnector.close(conn);
		}
		
		
		return "manageAccount";
    }
    
	public Customer getCustomer() {
		return customer;
	}



	public void setCustomer(Customer customer) {
		this.customer = customer;
	}



	public Order getOrder() {
		return order;
	}



	public void setOrder(Order order) {
		this.order = order;
	}



	public List<Order> getOrders() {
		return orders;
	}



	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}



	public Address getNewAddress() {
		return newAddress;
	}



	public void setNewAddress(Address newAddress) {
		this.newAddress = newAddress;
	}



	public String getOldPassword() {
		return oldPassword;
	}



	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}



	public String getPassword1() {
		return password1;
	}



	public void setPassword1(String password1) {
		this.password1 = password1;
	}



	public String getPassword2() {
		return password2;
	}



	public void setPassword2(String password2) {
		this.password2 = password2;
	}
	
	public boolean isPasswordChanged() {
		return passwordChanged;
	}

	public void setPasswordChanged(boolean passwordChanged) {
		this.passwordChanged = passwordChanged;
	}
	
}

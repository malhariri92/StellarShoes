package com.StellarShoes.Beans;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;


import com.StellarShoes.Address;
import com.StellarShoes.Order;
import com.StellarShoes.Product;
import com.StellarShoes.utils.DatabaseConnector;
import com.StellarShoes.utils.Messages;

/**
 * A managed bean class to carry out the administrative tasks regarding orders.
 * @author Mutasem Alhariri 
 *         07/04/2020
 *         Version 1.0
 *
 */
public class ManageOrders implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Order order = new Order();
	
	
	private String newStatus;
	private List<Order> orders = new ArrayList<>();
		
	private static final String orderSql = "SELECT o.ORDER_ID, o.STATUS, c.FIRST_NAME, c.LAST_NAME, "
			+ "a.STREET_ADDRESS1, a.STREET_ADDRESS2, a.CITY, a.STATE, a.ZIP\r\n" + 
			"FROM orders as o\r\n" + 
			"JOIN CUSTOMER as c ON (o.CUSTOMER_ID = c.CUSTOMER_ID)\r\n" + 
			"JOIN ADDRESS as a ON (o.CUSTOMER_ID = a.CUSTOMER_ID) "
			+ "WHERE o.STATUS = 'A'";
	
	private static final String detailsSql = "SELECT d.SHOE_ID, d.QUANTITY, d.SHOE_SIZE, s.SHOE_NAME, s.SHOE_COLOR, "
			+ "SHOE_PRICE, s.IMAGE_URL from ORDER_DETAILS as d\r\n" + 
			"Left join SHOE as s on d.SHOE_ID = s.SHOE_ID \r\n" + 
			"Where d.ORDER_ID = ?";
	
	private static final String statusSql = "UPDATE ORDERS SET STATUS = ? WHERE ORDER_ID = ?";
	
	public ManageOrders() {};
	
	
	/**
	 * To get a list of all active orders from the orders table.
	 * @return a list of all active orders.
	 */
	public List<Order> loadOrders(){
		orders.clear();
		
		List<Product> products = new ArrayList<>();
		Connection conn = null;
		Statement stmt = null;
		try  {
			conn = DatabaseConnector.getConnection();
			
			stmt = conn.createStatement();

			ResultSet rs = stmt.executeQuery(orderSql);
					
			while(rs.next()) {
				
				Address address = new Address(rs.getString(5),rs.getString(6),
						rs.getString(7),rs.getString(8),rs.getInt(9));
				
				order = new Order(rs.getInt(1), rs.getString(2), rs.getString(3),
						rs.getString(4), address, products, 0, null);
				
				orders.add(order);
				products = new ArrayList<>();
			}
			
           stmt.close();
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
     * To get the details of each order in the active orders list.
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
	 * To get the new value of the order status from the adminOrders page.
	 * @param event a value change event will be triggered when the user select a new status.
	 */
	public void handleChange(AjaxBehaviorEvent event){ 
		newStatus =(String) ((UIOutput)event.getSource()).getValue();
	    System.out.println("New value: " +newStatus);
	}

	/**
	 * To change the status of an order in the orders table.
	 * @param orderID The ID of the order which its status to be changed.
	 * @return
	 */
    public String changeStatus(int orderID) {
    	
    	Connection conn = null;
		PreparedStatement pstmt = null;
		
		try  {
			conn = DatabaseConnector.getConnection();
			
			pstmt = conn.prepareStatement(statusSql);
			pstmt.setString(1, newStatus);
            pstmt.setInt(2, orderID);	            
            
            
            int rs = pstmt.executeUpdate();
			
			if(rs > 0) {
				
				Messages.show("New status has been saved.");

			} else {
				Messages.show("Something went wrong. please try again.");
			}
			
          pstmt.close();
         
		} catch (SQLException ex) {
			
			
		} finally {
			DatabaseConnector.close(conn);
		}
  
    	FacesContext context = FacesContext.getCurrentInstance();
		   context.getExternalContext().getFlash().setKeepMessages(true);
    	return "adminOrders?faces-redirect=true";
    	
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



	public String getNewStatus() {
		return newStatus;
	}



	public void setNewStatus(String newStatus) {
		this.newStatus = newStatus;
	}

    

	
	
	
}

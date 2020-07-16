package com.StellarShoes.Beans;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import com.StellarShoes.Product;
import com.StellarShoes.utils.DatabaseConnector;
import com.StellarShoes.utils.Messages;

public class Item implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private int itemID;
	private Product product;
	private double newPrice;
	private String newColor;
	private final String sql = "SELECT s.SHOE_ID, s.SHOE_NAME, s.SHOE_SIZE, "
			+ "s.SHOE_COLOR, s.SHOE_PRICE, s.CATEGORY_ID, "
			+ "s.IMAGE_URL, c.DESCRIPTION FROM SHOE as s "
			+ "LEFT JOIN CATEGORY as c "
			+ "ON s.CATEGORY_ID = c.CATEGORY_ID "
			+ "Where SHOE_ID = ?";
	
	private static final String editSQL = "UPDATE SHOE "
			+ "SET SHOE_COLOR = ?,IMAGE_URL = ?, SHOE_PRICE = ? "
			+ "WHERE SHOE_ID =?";
	private static final String removeSQL = " ALTER TABLE CART_DETAILS\r\n" + 
			"DROP CONSTRAINT FK_SHOE_ID " + 
			"DELETE from SHOE WHERE SHOE_ID =?";
	
	
	
	public Item() {}
	
	
	public Product getShoeInfo() {
	
     HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
     itemID = Integer.parseInt(request.getParameter("itemID"));
     
     Connection conn = null;
		PreparedStatement pstmt = null;
		
		try  {
			conn = DatabaseConnector.getConnection();
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,itemID);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				product = new Product(rs.getInt(1), rs.getString(2), 0, rs.getString(4),
						rs.getDouble(5), rs.getInt(6), rs.getString(7), rs.getString(8));
							
			}
			
       pstmt.close();
        rs.close();
        
		} catch (SQLException ex) {
			
			System.out.println("error from getShoeInfo() -->" + ex.getMessage());
		} finally {
			DatabaseConnector.close(conn);
		}
		
		return product;

	}
	
	/**
	 * Will be called from he Admin invetory page to remove products from the database
	 * @param itemID the product ID in the database used in the sql statement 
	 * @return the admin page 
	 */
	public String deleteItem(int itemID) {
		System.out.println("deleted " +itemID);
//	     System.out.println("here is the product ID: " +itemID);
//		   Connection conn = null;
//			PreparedStatement pstmt = null;
//			
//			try  {
//				conn = DatabaseConnector.getConnection();
//				
//				pstmt = conn.prepareStatement(removeSQL);
//	            pstmt.setInt(1,itemID);
//				int rs = pstmt.executeUpdate();
//				
//				if(rs > 0) {
//					
//				Messages.show("One item was removed successfully");
//				} else {
//					Messages.show("no effect");
//				}
//				
//	          pstmt.close();
//	   
//			} catch (SQLException ex) {
//				
//				System.out.println("error from deleteItem() -->" + ex.getMessage());
//			} finally {
//				DatabaseConnector.close(conn);
//			}
			Messages.show("Product with ID "+itemID+" was deleted");
			return "adminHome";
		}
	
	/**
	 * 
	 * @param shoe the product to be edited
	 * @return admin page 
     */
	public String editItem(Product item) {
		product = item;
		product.setColor(newColor);
		product.setPrice(newPrice);
		newPrice = 0.0;
		newColor = null;
	     System.out.println("here is the new Color: " +product.getColor() 
	     +"\n new price "+product.getPrice() + " itemID "+product.getProductID());
	     
//		   Connection conn = null;
//			PreparedStatement pstmt = null;
//			
//			try  {
//				conn = DatabaseConnector.getConnection();
//				
//				pstmt = conn.prepareStatement(editSQL);
//	            pstmt.setString(1,shoe.getColor());
//	            pstmt.setString(2,shoe.getImgUrl());
//	            pstmt.setDouble(3, shoe.getPrice());
//	            pstmt.setInt(4, shoe.getProductID());
//				int rs = pstmt.executeUpdate();
//				
//				if(rs > 0) {
//					
//				Messages.show("Your changes were saved");
//				} else {
//					Messages.show("no effect");
//				}
//				
//	          pstmt.close();
//	   
//			} catch (SQLException ex) {
//				
//				System.out.println("error from editItem() -->" + ex.getMessage());
//			} finally {
//				DatabaseConnector.close(conn);
//			}
//			
			return "adminHome?faces-redirect=true";
		}
	
	   
	   
	 
	   

	public int getItemID() {
		return itemID;
	}


	public void setItemID(int itemID) {
		this.itemID = itemID;
	}


	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	
	public double getNewPrice() {
		return newPrice;
	}

	public void setNewPrice(double newPrice) {
		this.newPrice = newPrice;
	}

	public String getNewColor() {
		return newColor;
	}

	public void setNewColor(String newColor) {
		this.newColor = newColor;
	}
	
}

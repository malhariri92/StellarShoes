package com.StellarShoes.Beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;

import com.StellarShoes.Product;
import com.StellarShoes.utils.DatabaseConnector;
import com.StellarShoes.utils.Messages;

/**
 * A managed bean class to carry out the administrative tasks regarding products.
 * @author Mutasem Alhariri 
 *         07/04/2020
 *         Version 1.0
 *
 */
public class ManageProduct {
	
	private static final String productSQL = "SELECT s.SHOE_ID, s.SHOE_NAME, s.SHOE_SIZE, "
			+ "s.SHOE_COLOR, s.SHOE_PRICE, s.CATEGORY_ID, "
			+ "s.IMAGE_URL, c.DESCRIPTION FROM SHOE as s "
			+ "LEFT JOIN CATEGORY as c "
			+ "ON s.CATEGORY_ID = c.CATEGORY_ID";
	
	private static final String editSQL = "UPDATE SHOE "
			+ "SET SHOE_COLOR = ?,IMAGE_URL = ?, SHOE_PRICE = ? "
			+ "WHERE SHOE_ID =?";
	
	private static final String insertProductSQL = "INSERT INTO SHOE (SHOE_NAME, SHOE_SIZE, CATEGORY_ID, SHOE_COLOR, IMAGE_URL, SHOE_PRICE) "
			+ "VALUES (?, null, ?, ?, ?, ?)";
	private static final String removeSQL = "Alter table ORDER_DETAILS DROP CONSTRAINT FK_SHOE_ID "
			+ "DELETE from SHOE WHERE SHOE_ID = ?";
	
	private double newPrice;
	private String newColor;
	private String newImageUrl;
	
    private Product product = new Product();
	
	
	public ManageProduct() {}
	

	/**
	 * To insert new products into the product table.
	 * @return the adminProducts page with success message if the new product was inserted, 
	 * the adminProducts page with error messages otherwise.
	 */
	public String addItem() {
		
		System.out.println("here is the new Color: " +product.getColor() 
	     +"\n new price "+product.getPrice() + " newURL " + product.getImgUrl() + " itemID "+product.getProductID()
	     + " product name " + product.getName() + " categoryID " + product.getCategoryId());
		
		   Connection conn = null;
			PreparedStatement pstmt = null;
			
			try  {
				conn = DatabaseConnector.getConnection();
				
				pstmt = conn.prepareStatement(insertProductSQL);
	            pstmt.setString(1, product.getName());	            
	            pstmt.setInt(2,product.getCategoryId());
	            pstmt.setString(3, product.getColor());
	            pstmt.setString(4, product.getImgUrl());
	            pstmt.setDouble(5, product.getPrice());
	            
				int rs = pstmt.executeUpdate();
				
				if(rs > 0) {
					
				Messages.show(product.getName() +" has been successfully added.");
				} else {
					Messages.show("An error has occurred. Please try again");
				}
				
	          pstmt.close();
	   
			} catch (SQLException ex) {
				
				System.out.println("error from Add Item() -->" + ex.getMessage());
			} finally {
				DatabaseConnector.close(conn);
			}
		   
		    product = new Product();
		    newPrice = 0.0;
			newColor = null;
			newImageUrl = null;
			
		   FacesContext context = FacesContext.getCurrentInstance();
		   context.getExternalContext().getFlash().setKeepMessages(true);
		   
			return "adminProducts?faces-redirect=true";
	}
	
	/**
	 * To get a list of all products in the products table.
	 * @return A list of all products.
	 */
	public List<Product> getProductsList() {
		Connection conn = null;
		Statement stmt = null;
		List<Product> products = new ArrayList<>();
		try  {
			conn = DatabaseConnector.getConnection();
			
			stmt = conn.createStatement();

			ResultSet rs = stmt.executeQuery(productSQL);
			
			while(rs.next()) {
				
			Product	product = new Product(rs.getInt(1), rs.getString(2), 0, rs.getString(4),
						rs.getDouble(5), rs.getInt(6), rs.getString(7), rs.getString(8), 0);
				
				products.add(product);
			}
			
           stmt.close();
           rs.close();
           
		} catch (SQLException ex) {
			
			System.out.println("error -->" + ex.getMessage());
		} finally {
			DatabaseConnector.close(conn);
		}
		
		return products;
	}
	
	/**
	 * To update the details of a product in the product table.
	 * @param item the product which its details will be updated.
	 * @return the adminProducts page with success message if the details are updated, 
	 * the adminProducts page with error messages otherwise.
	 */
	public String editItem(Product item) {
		product = item;
		
		if(newPrice != 0) {
			
			if(newPrice >= 20) {
				
			product.setPrice(newPrice);
			
			}else {
				Messages.show("Please enter a valid price. Prices must be between $20 and $200");
				return "adminProducts";
			}
		}
		
		if(newColor != null && newColor != "") { product.setColor(newColor); }
		if(newImageUrl != null && newImageUrl != "") {product.setImgUrl(newImageUrl);}
        
		if(newPrice == 0 && newColor == null && newImageUrl == null) {
			product = new Product();
			return null;
		}
		newPrice = 0.0;
		newColor = null;
		newImageUrl = null;
		
	     System.out.println("here is the new Color: " +product.getColor() 
	     +"\n new price "+product.getPrice() + "newURL " + product.getImgUrl() + " itemID "+product.getProductID());
	     
		   Connection conn = null;
			PreparedStatement pstmt = null;
			
			try  {
				conn = DatabaseConnector.getConnection();
				
				pstmt = conn.prepareStatement(editSQL);
	            pstmt.setString(1,product.getColor());
	            pstmt.setString(2,product.getImgUrl());
	            pstmt.setDouble(3, product.getPrice());
	            pstmt.setInt(4, product.getProductID());
				int rs = pstmt.executeUpdate();
				
				if(rs > 0) {
					
				Messages.show("Your changes have been saved!");
				} else {
					Messages.show("An error has occurred. Please try again");
				}
				
	          pstmt.close();
	   
			} catch (SQLException ex) {
				
				System.out.println("error from editItem() -->" + ex.getMessage());
			} finally {
				DatabaseConnector.close(conn);
			}
  		   
  		    product = new Product();
  		   FacesContext context = FacesContext.getCurrentInstance();
		   context.getExternalContext().getFlash().setKeepMessages(true);
		   
			return "adminProducts?faces-redirect=true";
		}
	
	
	/**
	 * Will be called from he Admin invetory page to remove products from the database
	 * @param itemID the product ID in the database used in the sql statement 
	 * @return the adminProducts page with success message if the product was deleted, 
	 * the adminProducts page with error messages otherwise.
	 */
	public String deleteItem(int itemID) {
		System.out.println("deleted " +itemID);
	     System.out.println("here is the product ID: " +itemID);
		   Connection conn = null;
			PreparedStatement pstmt = null;
			
			try  {
				conn = DatabaseConnector.getConnection();
				
				pstmt = conn.prepareStatement(removeSQL);
	            pstmt.setInt(1,itemID);
				int rs = pstmt.executeUpdate();
				
				if(rs > 0) {
					
					Messages.show("Product with ID "+itemID+" was deleted");
				} else {
					Messages.show("You cannot remove this product!");
				}
				
	          pstmt.close();
	   
			} catch (SQLException ex) {
				
				System.out.println("error from deleteItem() -->" + ex.getMessage());
			} finally {
				DatabaseConnector.close(conn);
			}
			FacesContext context = FacesContext.getCurrentInstance();
			   context.getExternalContext().getFlash().setKeepMessages(true);
			
			return "adminProducts?faces-redirect=true";
		}
	
	/**
	 * To get today's date.
	 * @return today's date.
	 */
	public String getDate() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");  
		   LocalDateTime date = LocalDateTime.now(); 
		   String strDate = dtf.format(date);
		   return strDate;
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


	public String getNewImageUrl() {
		return newImageUrl;
	}


	public void setNewImageUrl(String newImageUrl) {
		this.newImageUrl = newImageUrl;
	}

	
}

package com.StellarShoes.Beans;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.StellarShoes.Product;
import com.StellarShoes.utils.DatabaseConnector;

public class ManageProduct {
	
	private static final String productSQL = "SELECT s.SHOE_ID, s.SHOE_NAME, s.SHOE_SIZE, "
			+ "s.SHOE_COLOR, s.SHOE_PRICE, s.CATEGORY_ID, "
			+ "s.IMAGE_URL, c.DESCRIPTION FROM SHOE as s "
			+ "LEFT JOIN CATEGORY as c "
			+ "ON s.CATEGORY_ID = c.CATEGORY_ID";
	
	private String newPrice;
	private String newColor;
    private Product item = new Product();
	
	
	public ManageProduct() {}
	

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
						rs.getDouble(5), rs.getInt(6), rs.getString(7), rs.getString(8));
				
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
	
	public String editItem(int ID) {
		for(Product product : getProductsList()) {
			if(product.getProductID() == ID) {
				item = product;
				item.setPrice(Double.parseDouble(newPrice));
				item.setColor(newColor);
			}
		}
	     System.out.println("here is the new Color: " +item.getColor() 
	     +"\n new price "+item.getPrice() + " itemID "+item.getProductID());
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
	
	

	public String getNewPrice() {
		return newPrice;
	}

	public void setNewPrice(String newPrice) {
		this.newPrice = newPrice;
	}

	public String getNewColor() {
		return newColor;
	}

	public void setNewColor(String newColor) {
		this.newColor = newColor;
	}
}

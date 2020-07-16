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
	
	
    private Product product = new Product();
	
	
	public ManageProduct() {}
	

//	public List<Product> getProductsList() {
//		Connection conn = null;
//		Statement stmt = null;
//		List<Product> products = new ArrayList<>();
//		try  {
//			conn = DatabaseConnector.getConnection();
//			
//			stmt = conn.createStatement();
//
//			ResultSet rs = stmt.executeQuery(productSQL);
//			
//			while(rs.next()) {
//				
//			Product	product = new Product(rs.getInt(1), rs.getString(2), 0, rs.getString(4),
//						rs.getDouble(5), rs.getInt(6), rs.getString(7), rs.getString(8));
//				
//				products.add(product);
//			}
//			
//           stmt.close();
//           rs.close();
//           
//		} catch (SQLException ex) {
//			
//			System.out.println("error -->" + ex.getMessage());
//		} finally {
//			DatabaseConnector.close(conn);
//		}
//		
//		return products;
//	}
	
	public void assignProduct(Product shoe) {
		   product = shoe; 
		   System.out.println("this is the id " +product.getProductID() + "name " + product.getName());
		   
		   System.out.println("here is the new Color: " +product.getColor() 
		     +"\n new price "+product.getPrice() + " itemID "+product.getProductID());
	   }
public void print() {
	System.out.println("this is the id " +product.getProductID() + "name " + product.getName());
	   
	   System.out.println("here is the new Color: " +product.getColor() 
	     +"\n new price "+product.getPrice() + " itemID "+product.getProductID());
}

	public Product getProduct() {
		return product;
	}


	public void setProduct(Product product) {
		this.product = product;
	}
	
	

	
}

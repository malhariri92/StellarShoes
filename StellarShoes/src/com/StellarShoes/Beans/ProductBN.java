package com.StellarShoes.Beans;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.StellarShoes.*;
import com.StellarShoes.utils.DatabaseConnector;

/**
 * A managed bean class to display all products.
 * @author Mutasem Alhariri 
 *         07/04/2020
 *         Version 1.0
 *
 */
public class ProductBN implements Serializable {
	
	private static final long serialVersionUID = 1L;

	
	private static final String productSQL = "SELECT s.SHOE_ID, s.SHOE_NAME, s.SHOE_SIZE, "
			+ "s.SHOE_COLOR, s.SHOE_PRICE, s.CATEGORY_ID, "
			+ "s.IMAGE_URL, c.DESCRIPTION FROM SHOE as s "
			+ "LEFT JOIN CATEGORY as c "
			+ "ON s.CATEGORY_ID = c.CATEGORY_ID";
	
	private List<Product> products = new ArrayList<>();
    private Product product;
	public ProductBN() {
		
	}

	/**
	 * To get a list of all products from the products table.
	 * @return a list of products.
	 */
	public List<Product> loadProducts() {
		Connection conn = null;
		Statement stmt = null;
		products.clear();
		try  {
			conn = DatabaseConnector.getConnection();
			
			stmt = conn.createStatement();

			ResultSet rs = stmt.executeQuery(productSQL);
			
			while(rs.next()) {
				
				product = new Product(rs.getInt(1), rs.getString(2), 0, rs.getString(4),
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
	
	

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	
	
	

}
	
	
	



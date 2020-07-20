package com.StellarShoes.Beans;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import com.StellarShoes.Product;
import com.StellarShoes.utils.DatabaseConnector;

public class Item implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private int itemID;
	private Product product;
	
	private final String sql = "SELECT s.SHOE_ID, s.SHOE_NAME, s.SHOE_SIZE, "
			+ "s.SHOE_COLOR, s.SHOE_PRICE, s.CATEGORY_ID, "
			+ "s.IMAGE_URL, c.DESCRIPTION FROM SHOE as s "
			+ "LEFT JOIN CATEGORY as c "
			+ "ON s.CATEGORY_ID = c.CATEGORY_ID "
			+ "Where SHOE_ID = ?";
	
	
	
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

	
	
	
}

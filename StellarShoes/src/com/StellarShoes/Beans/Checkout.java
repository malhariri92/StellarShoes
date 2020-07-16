package com.StellarShoes.Beans;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;

import com.StellarShoes.*;
import com.StellarShoes.utils.DatabaseConnector;
import com.StellarShoes.utils.Messages;

public class Checkout implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private static final String paymentSQL = "SELECT PAYMENT_METHOD, CARD_HOLDER, CARD_NUMBER, CVV, EXPIRATION_DATE "
			+ "FROM PAYMENT WHERE CUSTOMER_ID = ?";
	
	private static final String addressSQL = "SELECT STREET_ADDRESS1, STREET_ADDRESS2, CITY, STATE, ZIP "
			+ "FROM ADDRESS WHERE CUSTOMER_ID = ?";
			
	private Customer customer ;
	
	private Address address;
	
	private Payment payment;
	
	private int cvv2;
	private double subTotal;
	private final double taxRate = 0.0575;
	private double tax;
	private double total;
	private Product product;
	private List<Product> cartList = new ArrayList<>();
	
	
	public Checkout() {
		
		customer = AccountManager.sessionCustomer == null ? null: AccountManager.sessionCustomer ;
		
	    address = customer == null ? new Address() : getCustomerAddress();
		
	    payment = customer == null ? new Payment() : getPaymentInfo();
	   
	    cartList = new ArrayList<>();
	    
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
     

public String addToCart(Product item) {
	product = item;
	   cartList.add(product);
	   
	   addCost(product);
	   
	   
	   Messages.show(product.getName()+" was added to your cart successfully!");
	   
	   FacesContext context = FacesContext.getCurrentInstance();
	   context.getExternalContext().getFlash().setKeepMessages(true);
	   
	    return "item?itemID="+product.getProductID()+"faces-redirect=true"; 
}

    public String removeFromCart(Product item) {
	   int index = 0;
	   for(Product product : cartList) {
		   if(product.equals(item)) {
			   
			 index = cartList.indexOf(product);
			 break;
			 
		   }
		   
	   }
	   cartList.remove(index);
	   subtractCost(item);
	   
	   return "checkout1?faces-redirect=true";  
	  
}
    private void addCost(Product item) {
       subTotal += product.getPrice();
 	   
 	   tax = subTotal * taxRate;
 	    
 	   total = subTotal + tax;
 	   
 	  formatCost();
    }
     
    
    private void subtractCost(Product item) {
    	   subTotal -= product.getPrice();
	 	   
	 	   tax = subTotal * taxRate;
	 	    
	 	   total = subTotal - tax;
	 	   
	 	  formatCost();	
    }
    
    
    private void formatCost() {
    	DecimalFormat df = new DecimalFormat("#.00");
    	
    	subTotal = Double.parseDouble(df.format(subTotal));
    	tax = Double.parseDouble(df.format(tax));
    	total = Double.parseDouble(df.format(total));
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


	public List<Product> getCartList() {
		return cartList;
	}


	public void setCartList(List<Product> cartList) {
		this.cartList = cartList;
	}




	public double getSubTotal() {
		return subTotal;
	}




	public void setSubTotal(double subTotal) {
		this.subTotal = subTotal;
	}




	public double getTax() {
		return tax;
	}




	public void setTax(double tax) {
		this.tax = tax;
	}




	public double getTotal() {
		return total;
	}




	public void setTotal(double total) {
		this.total = total;
	}

	
	}


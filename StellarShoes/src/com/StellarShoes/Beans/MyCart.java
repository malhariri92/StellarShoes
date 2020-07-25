package com.StellarShoes.Beans;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import com.StellarShoes.Product;
import com.StellarShoes.utils.Messages;

public class MyCart implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private double subTotal;
	private final double taxRate = 0.0575;
	private double tax;
	private double total;
	private Product product;
	private List<Product> cartList = new ArrayList<>();
	
	
	public String addToCart(Product item) {
		   product = item;
		   
		   if(item.getSize() == 0.0) {
			   FacesContext facesContext = FacesContext.getCurrentInstance();
				FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please select a size",null);
				facesContext.addMessage(null, facesMessage);
		   } else {
			   cartList.add(item);
			   
			   System.out.println(item.getSize());
			   addCost(item);
			   Messages.show(item.getName()+" was added to your cart successfully!");

		   }
   
		   FacesContext context = FacesContext.getCurrentInstance();
		   context.getExternalContext().getFlash().setKeepMessages(true);
		   
		    return "item?itemID="+product.getProductID()+"faces-redirect=true"; 
	}

	    public String removeFromCart(Product item) {
		   int index = 0;
		   for(Product product : cartList) {
			   if(product.getName().equals(item.getName()) && product.getColor().equals(item.getColor())) {
				   
				 index = cartList.indexOf(product);
				 break;
				 
			   }
			   
		   }
		   cartList.remove(index);
		   subtractCost(item);
		   
		   return "checkout1?faces-redirect=true";  
		  
	}
	    private void addCost(Product item) {
	       subTotal += item.getPrice();
	 	   
	 	   tax = subTotal * taxRate;
	 	    
	 	   total = subTotal + tax;
	 	   
	 	  formatCost();
	    }
	     
	    
	    private void subtractCost(Product item) {
	    	   subTotal -= item.getPrice();
		 	   
		 	   tax = subTotal * taxRate;
		 	    
		 	   total = subTotal + tax;
		 	   
		 	  formatCost();	
	    }
	     public void emptyCart() {
	    	 cartList.clear();
	    	 total = subTotal = tax = 0 ;
	     }
	    
	    private void formatCost() {
	    	DecimalFormat df = new DecimalFormat("#.00");
	    	
	    	subTotal = Double.parseDouble(df.format(subTotal));
	    	tax = Double.parseDouble(df.format(tax));
	    	total = Double.parseDouble(df.format(total));
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
		public Product getProduct() {
			return product;
		}

		public void setProduct(Product product) {
			this.product = product;
		}

}

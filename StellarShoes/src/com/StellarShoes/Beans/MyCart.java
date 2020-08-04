package com.StellarShoes.Beans;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import com.StellarShoes.Product;
import com.StellarShoes.utils.Messages;

/**
 * A managed bean class to manage the products in the shopping cart.
 * @author Mutasem Alhariri 
 *         07/04/2020
 *         Version 1.0
 *
 */
public class MyCart implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private double subTotal;
	private final double taxRate = 0.0575;
	private double tax;
	private double total;
	private Product product;
	private List<Product> cartList = new ArrayList<>();
	
	/**
	 * To add the selected product to the cartList
	 * @param item
	 * @return the item page with success message if the product was added to the list, 
	 * the item page with error message otherwise.
	 */
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

	
	    /**
	     * To remove a product from the shopping cart list
	     * @param item the item to be removed
	     * @return the myCart page.
	     */
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
		   
		   return "myCart?faces-redirect=true";  
		  
	}
	    /**
	     * To add the cost of the product added to the shopping cart list.
	     * @param item the product which its cost will be added to the total.
	     */
	    private void addCost(Product item) {
	       subTotal += item.getPrice();
	 	   
	 	   tax = subTotal * taxRate;
	 	    
	 	   total = subTotal + tax;
	 	   
	 	  formatCost();
	    }
	     
	    /**
	     * To subtract the cost of the product removed from the shopping cart list.
	     * @param item the product which its cost will be subtracted from the tota.
	     */
	    private void subtractCost(Product item) {
	    	   subTotal -= item.getPrice();
		 	   
		 	   tax = subTotal * taxRate;
		 	    
		 	   total = subTotal + tax;
		 	   
		 	  formatCost();	
	    }
	    
	    /**
	     * To clean up the shopping cart list after a successful order.
	     */
	     public void emptyCart() {
	    	 cartList.clear();
	    	 total = subTotal = tax = 0 ;
	     }
	    
	     /**
	      * To format the subTotal, total, and tax to display two decimal places.
	      */
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

package com.StellarShoes.Beans;

import java.io.Serializable;

import com.StellarShoes.Payment;

/**
 * A simple managed bean class to display order's information after successful orders.
 * @author Mutasem Alhariri 
 *         07/04/2020
 *         Version 1.0
 *
 */
public class OrderBN implements Serializable{

	private static final long serialVersionUID = 1L;
	
	
	private int orderID, paymentID;
    private static int oID, pID;
    
	public OrderBN() {
		
		orderID = oID;
		paymentID = pID;
		oID = 0;
		pID = 0;
	}

	/**
	 * Will be called from the checkout class to get the placed order information.
	 * @param orID the ID of the last placed order.
	 * @param payment the payment of the last placed order.
	 */
	public static void assignOrder(int orID, Payment payment) {
		
		oID = orID;
		pID = payment.getPaymentID();
	}
	
	public int getOrderID() {
		return orderID;
	}


	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}


	public int getPaymentID() {
		return paymentID;
	}


	public void setPaymentID(int paymentID) {
		this.paymentID = paymentID;
	}


	public static int getoID() {
		return oID;
	}


	public static void setoID(int oID) {
		OrderBN.oID = oID;
	}


	public static int getpID() {
		return pID;
	}


	public static void setpID(int pID) {
		OrderBN.pID = pID;
	}

	
	
	
	
	
}

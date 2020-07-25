package com.StellarShoes.Beans;

import java.io.Serializable;

import com.StellarShoes.Payment;

public class OrderBN implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private int orderID, paymentID;
    private static int oID, pID;
    
	public OrderBN() {
		
		orderID = oID;
		paymentID = pID;
		oID = 0;
		pID = 0;
	}

	
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

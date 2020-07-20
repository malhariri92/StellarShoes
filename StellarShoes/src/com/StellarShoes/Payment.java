package com.StellarShoes;

public class Payment {
    private int paymentID;
	private int method;
	private String cardHolder;
	private String cardNumber;
	private int cvv;
	private String expiration;
	
	public Payment() {}

	/**
	 * @param method
	 * @param cardHolder
	 * @param cardNumber
	 * @param cvv
	 * @param expiration
	 */
	public Payment(int paymentID,int method, String cardHolder, String cardNumber, int cvv, String expiration) {
		super();
		this.paymentID = paymentID;
		this.method = method;
		this.cardHolder = cardHolder;
		this.cardNumber = cardNumber;
		this.cvv = cvv;
		this.expiration = expiration;
	}
	

	public int getPaymentID() {
		return paymentID;
	}

	public void setPaymentID(int paymentID) {
		this.paymentID = paymentID;
	}

	public int getMethod() {
		return method;
	}

	public void setMethod(int method) {
		this.method = method;
	}

	public String getCardHolder() {
		return cardHolder;
	}

	public void setCardHolder(String cardHolder) {
		this.cardHolder = cardHolder;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public int getCvv() {
		return cvv;
	}

	public void setCvv(int cvv) {
		this.cvv = cvv;
	}

	public String getExpiration() {
		return expiration;
	}

	public void setExpiration(String expiration) {
		this.expiration = expiration;
	}
	
	
	
}

package com.StellarShoes;

public class Payment {

	private int method;
	private String cardHolder;
	private long cardNumber;
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
	public Payment(int method, String cardHolder, long cardNumber, int cvv, String expiration) {
		super();
		this.method = method;
		this.cardHolder = cardHolder;
		this.cardNumber = cardNumber;
		this.cvv = cvv;
		this.expiration = expiration;
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

	public long getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(long cardNumber) {
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

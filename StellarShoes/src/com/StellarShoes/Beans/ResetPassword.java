package com.StellarShoes.Beans;

import com.StellarShoes.utils.*;

/**
 * A simple managed bean class to carry out the reset password functionality.
 * @author Mutasem Alhariri 
 *         07/04/2020
 *         Version 1.0
 *
 */
public class ResetPassword {

	private final String sentMsg =  "We’ve sent you an email with a link to finish resetting your password. " + 
			             			"Can’t find the email? Try checking your spam folder. " + 
			                        "If you still can’t log in, have us resend the email or contact your administrator.";
	
	private final String notRegistered = "The email you entered does not match any record! "
			+ "You can create a new account by clicking the creat account link below. ";
			
	
	private String email;
	
	public ResetPassword() {}
	
	
	/**
	 * To check if the entered email is associated with any user in the customer table.
	 * @return the forgotPassword page with success message if the user exists, 
	 * the forgotPassword page with error message otherwise.
	 */
	public String isRegistered() {
		
		if(LoginValidator.exist(email)) {
			Messages.show(sentMsg);
			return "forgotPassword";
		}else {
			
			Messages.show(notRegistered);
			return "forgotPassword";
		}
	}
	
	public String gotMessage() {
		
		return "contact";
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
	
}

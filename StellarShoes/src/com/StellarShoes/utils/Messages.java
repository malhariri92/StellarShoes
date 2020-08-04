package com.StellarShoes.utils;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 * A simple class to display messages.
 * @author Mutasem Alhariri 
 *         07/04/2020
 *         Version 1.0
 *
 */
public class Messages {

	/**
	 * To display messages to the user.
	 * @param msg the message to be displayed.
	 */
	public static void show(String msg) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		FacesMessage facesMessage = new FacesMessage(msg);
		facesContext.addMessage(null, facesMessage);
	}
}

package com.StellarShoes.utils;

import javax.faces.context.FacesContext;

import javax.servlet.http.HttpSession;

/**
 * A simple class to obtain the current session.
 * @author Mutasem Alhariri 
 *         07/04/2020
 *         Version 1.0
 *
 */
public class SessionManager {
	
	/**
	 * Gets the current session if any.
	 * @return the current session.
	 */
	public static HttpSession getSession() {
		return (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(false);
	}
	
	
}

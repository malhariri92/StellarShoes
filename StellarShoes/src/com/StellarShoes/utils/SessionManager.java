package com.StellarShoes.utils;

import javax.faces.context.FacesContext;

import javax.servlet.http.HttpSession;

public class SessionManager {
	
	
	public static HttpSession getSession() {
		return (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(false);
	}
	
	
}

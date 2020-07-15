package com.StellarShoes.utils;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class Messages {

	
	public static void show(String msg) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		FacesMessage facesMessage = new FacesMessage(msg);
		facesContext.addMessage(null, facesMessage);
	}
}

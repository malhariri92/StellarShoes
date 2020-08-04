package com.StellarShoes.utils;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * A web filter to determine which page to return based on the requested url and the login status.
 * @author Mutasem Alhariri 
 *         07/04/2020
 *         Version 1.0
 *
 */
@WebFilter(filterName = "AuthFilter", urlPatterns = { "*.xhtml" })
public class LoginFilter implements Filter{
	
	private HttpServletRequest httpRequest;
	private static final String[] customerLoginRequiredURLs = {"/changeAddress.xhtml",
            "/manageAccount.xhtml"};
	
	private static final String[] adminLoginRequiredURLs = {"/adminHome.xhtml", "/adminProducts.xhtml",
			"/adminOrders.xhtml"};
	public LoginFilter() {};
	
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}
     
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws ServletException {
		try {

			httpRequest  = (HttpServletRequest) request;
			HttpServletResponse resp = (HttpServletResponse) response;
			
			resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); 
	        resp.setHeader("Pragma", "no-cache"); 
	        resp.setHeader("Expires", "0");
	        
			//returns a session only if there is one associated with the request.
			HttpSession session  = httpRequest.getSession(false);
			
			boolean isAdminLoggedIn = (session != null && session.getAttribute("admin") != null);
			boolean isCustomerLoggedIn = (session != null && session.getAttribute("customer") != null);
			
			String loginURI = httpRequest.getContextPath() + "/faces/login";
			
	        boolean isLoginRequest = httpRequest.getRequestURI().equals(loginURI);
	        
	        boolean isLoginPage = httpRequest.getRequestURI().endsWith("login.xhtml");
	        	        
	        if((!isAdminLoggedIn && isAdminLoginRequired()) || (!isCustomerLoggedIn && isCustomerLoginRequired())) {
	        	
	        	resp.sendRedirect(httpRequest.getContextPath() + "/faces/login.xhtml");
	        	
	        } else if (isLoginRequest || isLoginPage) {
	            // the admin is already logged in and he's trying to login again
	            // then forward to the admin homepage
	        	if(isAdminLoggedIn) {
	        	resp.sendRedirect(httpRequest.getContextPath() + "/faces/adminHome.xhtml");
	        	}  else if(isCustomerLoggedIn) {
	        		// the customer is already logged in and he's trying to login again
		            // then forward to the manage account page
		        	resp.sendRedirect(httpRequest.getContextPath() + "/faces/manageAccount.xhtml");
	        	} else {
		            // for other requested pages that require authentication
		            // or the user is already logged in, continue to the destination
		            chain.doFilter(request, response);
		        }
	 
	        } 
	        else {
	            // for other requested pages that do not require authentication
	            // continue to the destination
	            chain.doFilter(request, response);
	        }
	            
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * To check if the requested page is restricted to logged in customers.
	 * @return True if the page is restricted, false otherwise.
	 */
	private boolean isCustomerLoginRequired() {
        String requestURL = httpRequest.getRequestURL().toString();
 
        for (String loginRequiredURL : customerLoginRequiredURLs) {
            if (requestURL.contains(loginRequiredURL)) {
                return true;
            }
        }
 
        return false;
    }
	
	/**
	 * To check if the requested page is restricted to logged in admins.
	 * @return true if the page is restricted, false otherwise.
	 */
	private boolean isAdminLoginRequired() {
        String requestURL = httpRequest.getRequestURL().toString();
 
        for (String loginRequiredURL : adminLoginRequiredURLs) {
            if (requestURL.contains(loginRequiredURL)) {
                return true;
            }
        }
 
        return false;
    }

	@Override
	public void destroy() {
}
}

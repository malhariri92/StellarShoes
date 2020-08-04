package com.StellarShoes.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A simple class to check if passwords meet the requirements.
 * @author Mutasem Alhariri 
 *         07/04/2020
 *         Version 1.0
 *
 */
public class PasswordValidator {

	private static final String passwordPattern = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[/<>@#!%+&^])(?=\\S+$).{8,13}";
	private static Pattern pattern = Pattern.compile(passwordPattern);
    private static Matcher matcher;
	
    /**
     * To check if the password matches a specific pattern.
     * @param password the password to be matched with the pattern.
     * @return True if the password matches the pattern, false otherwise.
     */
	public static boolean validate(String password) {
		
		matcher = pattern.matcher(password);
        return matcher.matches();
	}
	
	
}

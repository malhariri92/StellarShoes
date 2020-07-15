package com.StellarShoes.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidator {

	private static final String passwordPattern = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[/<>@#!%+&^])(?=\\S+$).{8,13}";
	private static Pattern pattern = Pattern.compile(passwordPattern);
    private static Matcher matcher;
	
	public static boolean validate(String password) {
		
		matcher = pattern.matcher(password);
        return matcher.matches();
	}
	
	
}

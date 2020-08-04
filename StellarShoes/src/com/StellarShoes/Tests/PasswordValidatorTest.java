package com.StellarShoes.Tests;

import static org.junit.Assert.*;

import org.junit.Test;

import com.StellarShoes.utils.PasswordValidator;

/**
 * To test the Validate method functionality.
 * @author Mutasem Alhariri 
 *         07/04/2020
 *         Version 1.0
 *
 */
public class PasswordValidatorTest {

	private static final String pass1 = "Abcd765#";
	private static final String pass2 = "fdss9#";
	private static final String pass3 = "Acr9879998777677777333^";
	private static final String pass4 = "Abc76541";
	private static final String pass5 = "77776444";
	private static final String pass6 = "gfkitlro#";
	private static final String pass7 = "ADHIGFKSK76@3";

	@Test
	public void testValidate() {
		assertTrue(PasswordValidator.validate(pass1));
		assertFalse(PasswordValidator.validate(pass2));
		assertFalse(PasswordValidator.validate(pass3));
		assertFalse(PasswordValidator.validate(pass4));
		assertFalse(PasswordValidator.validate(pass5));
		assertFalse(PasswordValidator.validate(pass6));
		assertFalse(PasswordValidator.validate(pass7));
		
	}

}

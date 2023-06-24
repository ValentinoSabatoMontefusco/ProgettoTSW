package com.code_fanatic.control;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

import org.eclipse.jdt.core.compiler.InvalidInputException;

import com.mysql.cj.xdevapi.Schema.Validation;



public class SecurityUtilities {

	private static HashSet<String> validInputs = new HashSet<String>(Arrays.asList("user_username ASC", "user_username DESC",
												"order_date ASC", "order_date DESC"));
	
	
	public SecurityUtilities() {
//		validInputs.add("user_username ASC");
//		validInputs.add("user_username DESC");
//		validInputs.add("order_date ASC");
//		validInputs.add("order_date DESC");
		
	}
	public static String toHash(String string) {
		
		MessageDigest digest;
		String hashword = null;
		try {
			digest = MessageDigest.getInstance("SHA-512");
			byte[] hash = digest.digest(string.getBytes(StandardCharsets.UTF_8));
			
			hashword = "";
			
		for (int i = 0; i < hash.length; i++) {
			
			hashword += Integer.toHexString( (hash[i] & 0xFF) | 0x100).toLowerCase().substring(1,3);
		}
			
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return hashword;
		
	}
	
	public static String sanitize(String input) {
		
		if (validInputs.contains(input))
			return input;
		return "order_date DESC";
	}
}

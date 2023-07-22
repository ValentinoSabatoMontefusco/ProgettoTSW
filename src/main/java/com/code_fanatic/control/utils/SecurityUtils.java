package com.code_fanatic.control.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.jdt.core.compiler.InvalidInputException;

import com.code_fanatic.control.admin.OrdersRecapServlet;
import com.mysql.cj.xdevapi.Schema.Validation;



public class SecurityUtils {

	private static HashSet<String> validInputs = new HashSet<String>(Arrays.asList("user_username ASC", "user_username DESC",
												"order_date ASC", "order_date DESC", "name ASC", "name DESC",
												"product_id ASC", "product_id DESC", "create_time ASC", "create_time DESC"));
	
	private static final Logger LOGGER = Logger.getLogger(OrdersRecapServlet.class.getName());
	
	public SecurityUtils() {
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
			LOGGER.log(Level.SEVERE, e.getMessage());
		}
		
		return hashword;
		
	}
	
	public static String sanitizeForOrder(String input) {
		
		if (validInputs.contains(input))
			return input;
		return "order_date DESC";
	}
	
	public static String sanitizeForCourse(String input) {
		
		if (validInputs.contains(input))
			return input;
		return "name ASC";
	}
	
	public static String sanitizeForComment(String input) {
		
		if (validInputs.contains(input))
			return input;
		return "id ASC";
	}
}

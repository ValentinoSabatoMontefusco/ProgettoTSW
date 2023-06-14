package com.code_fanatic.control;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;



public class SecurityUtilities {

	
	public SecurityUtilities() {
		
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
}

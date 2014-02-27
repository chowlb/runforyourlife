package com.chowlb.runforyourlife;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import android.util.Base64;
import android.util.Log;

public class Utils{
	private static byte[] key = {
        0x74, 0x68, 0x69, 0x73, 0x49, 0x73, 0x41, 0x53, 0x65, 0x63, 0x72, 0x65, 0x74, 0x4b, 0x65, 0x79
	};
	private static SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
	
	
	public static int getNumberDigits(String inString) {
		if(isEmpty(inString)) {
			return 0;
		}
		int numDigits = 0;
		int length = inString.length();
		for(int i = 0; i<length; i++) {
			if(Character.isDigit(inString.charAt(i))) {
				numDigits++;
			}
		}
		return numDigits;
	}
	
	public static int getNumberUpperCase(String inString) {
		int numUpper = 0;
		int length = inString.length();
		for(int i = 0; i<length; i++) {
			if(Character.isUpperCase(inString.charAt(i))) {
				numUpper++;
			}
		}
		return numUpper;
	}

	public static int getNumberLowerCase(String inString) {
		int numLower = 0;
		int length = inString.length();
		for(int i = 0; i<length; i++) {
			if(Character.isLowerCase(inString.charAt(i))) {
				numLower++;
			}
		}
		return numLower;
	}
	
	public static int getNumberSpecial(String inString) {
		int numSpec = 0;
		int length = inString.length();
		Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(inString);
		for(int i = 0; i<length; i++) {
			if(m.find()) {
				numSpec++;
			}
		}
		return numSpec;
	}
	
	public static boolean isEmpty(String inString) {
		return inString ==null || inString.length() == 0;
	}
	
	public static int getPasswordStrength(String password) {
        if (password == null) {
        	return 0;
        }
            
        if (password.length() < 8) { 
        	return 0;
        } 
        
        if (getNumberLowerCase(password)<1){
        	return 0;
        }
        
        if (getNumberUpperCase(password)<1){
        	return 0;
        }
        
        if (getNumberDigits(password)<1){
        	return 0;
        }
        
        if(getNumberSpecial(password)<1) {
        	return 0;
        }
        
        return 7;
	}     
	
	public static String encryptPassword(String plainPassword) {

		try
        {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return new String (Base64.encode(cipher.doFinal(plainPassword.getBytes()), Base64.DEFAULT));
        }
        catch (Exception e)
        {
            Log.e("chowlb", "Error while encrypting : " + e);
        }
        return null;
	}

	public static String decryptPassword(String encPassword) {

		 try
	        {
	            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
	            cipher.init(Cipher.DECRYPT_MODE, secretKey);
	            return new String(cipher.doFinal(Base64.decode(encPassword, Base64.DEFAULT)));

	        }
	        catch (Exception e)
	        {
	            Log.e("chowlb", "Error while decrypting : " + e);

	        }
	        return null;
	}
}


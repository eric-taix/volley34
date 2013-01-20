/**
 * 
 */
package org.jared.android.volley.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Classe utilitaire pour calculer un MD5
 * @author eric.taix@gmail.com
 */
public class MD5 {
	
	public static String calcMD5(String s) {
	    try {
	        MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
	        digest.update(s.getBytes());
	        byte messageDigest[] = digest.digest();

	        StringBuffer sb = new StringBuffer();
	        for (int i = 0; i < messageDigest.length; i++) {
	            byte b = messageDigest[i];
	            String hex = Integer.toHexString((int) 0x00FF & b);
	            if (hex.length() == 1) {
	                sb.append("0"); 
	            }
	            sb.append(hex);
	        }

	        return sb.toString();
	    }
	    catch (NoSuchAlgorithmException e) {
	        // exception
	    }

	    return "";
	}
}

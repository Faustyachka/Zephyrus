package com.zephyrus.wind.helpers;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
																						// REVIEW: documentation expected
public class SHAHashing {
	
	public static String getHash(String password) 
			throws NoSuchAlgorithmException, UnsupportedEncodingException
    {
 
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(password.getBytes("UTF-8"));
 
        byte byteData[] = md.digest();

        //convert the byte to hex format
        StringBuffer hexString = new StringBuffer();
    	for (int i=0;i<byteData.length;i++) {
    		String hex=Integer.toHexString(0xff & byteData[i]);
   	     	if(hex.length()==1) hexString.append('0');
   	     	hexString.append(hex);
    	}
    	
    	return hexString.toString();
    }
}

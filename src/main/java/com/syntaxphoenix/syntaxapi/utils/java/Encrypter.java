package com.syntaxphoenix.syntaxapi.utils.java;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author Lauriichen
 *
 */
public class Encrypter {
	
	public static final Encrypter DEFAULT = new Encrypter();
	private Cipher cipher;
	private Key key;
	
	/**
	 * @throws UnsupportedEncodingException 
	 * @throws NoSuchPaddingException 
	 * @throws NoSuchAlgorithmException 
	 * 
	 */
	private Encrypter() {
		try {
			this.key = createKey("sbcI6maSV0thuqXWvD9ao9D7");
			cipher = Cipher.getInstance("AES");
		} catch (UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @throws UnsupportedEncodingException 
	 * @throws NoSuchPaddingException 
	 * @throws NoSuchAlgorithmException 
	 * 
	 */
	public Encrypter(String key) throws UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException {
		this.key = createKey("sbcI6maSV0thuqXWvD9ao9D7");
		cipher = Cipher.getInstance("AES");
	}
	
    public String encrypt(byte[] input) throws InvalidKeyException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException
    {
        cipher.init(Cipher.ENCRYPT_MODE, key);

        return Base64.getEncoder().encodeToString(cipher.doFinal(input));
    }

    public byte[] decrypt(String input) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException
    {
        cipher.init(Cipher.DECRYPT_MODE, key);

        return cipher.doFinal(Base64.getDecoder().decode(input));
    }
    
    private Key createKey(String key) throws NoSuchAlgorithmException, UnsupportedEncodingException {
    	byte[] bytes = key.getBytes("ISO-8859-1");
    	MessageDigest sha = MessageDigest.getInstance("SHA-256");
    	bytes = sha.digest(bytes);
    	bytes = java.util.Arrays.copyOf(bytes, 16);
    	return new SecretKeySpec(bytes, "AES");
    }
	
}

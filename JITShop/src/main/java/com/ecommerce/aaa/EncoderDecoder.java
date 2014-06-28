package com.ecommerce.aaa;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class EncoderDecoder {
	private static final String PRIVATE_KEY_FILE = "Private.key";  

	private PublicKey VariablePublicKey;
	private PrivateKey VariablePrivateKey;
	private String pubModuls;
	private String pubExponent;
	private BigInteger privModuls;
	private BigInteger privExponent;
	

	
	public void generateKeys(){
		KeyPairGenerator kpg;
	    try {
	        kpg = KeyPairGenerator.getInstance("RSA");
	        kpg.initialize(2048);
	        KeyPair kp = kpg.genKeyPair();
	        VariablePublicKey = kp.getPublic();
	        VariablePrivateKey = kp.getPrivate();
	    } catch(NoSuchAlgorithmException e) {
	    }
	}
	
	public void createPubModExp(){
		// receiving public key from where you store it
	    KeyFactory fact;
	    // initializing public key variable
	    RSAPublicKeySpec pub = new RSAPublicKeySpec(BigInteger.ZERO, BigInteger.ZERO);
	    RSAPrivateKeySpec priv = new RSAPrivateKeySpec(BigInteger.ZERO, BigInteger.ZERO);
	    
	    try {
	        fact = KeyFactory.getInstance("RSA");
	        pub = fact.getKeySpec(VariablePublicKey,    RSAPublicKeySpec.class);
	        priv = fact.getKeySpec(VariablePrivateKey,   RSAPrivateKeySpec.class);
	    } catch(NoSuchAlgorithmException e1) {
	    } catch(InvalidKeySpecException e) {
	    }
		// now you should pass Modulus string onto your html(jsp) in such way
		pubModuls = pub.getModulus().toString(16);
		pubExponent = pub.getPublicExponent().toString(16);
		// send somehow this String to page, so javascript can use it
		privModuls = priv.getModulus();
		privExponent = priv.getPrivateExponent();
		
		try {
			//saveKeys(PRIVATE_KEY_FILE, priv.getModulus(), priv.getPrivateExponent());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	}
	

	 
	
	

	public String decription(String ajaxSentPassword, String privModule, String privExponent){
		 Cipher cipher;
		 BigInteger passwordInt = new BigInteger(ajaxSentPassword, 16);
		 byte[] dectyptedText = new byte[1];
		 byte[] byteArray = new byte[256];
		    if (passwordInt.toByteArray().length > 256) {
		        for (int i=1; i<257; i++) {
		            byteArray[i-1] = passwordInt.toByteArray()[i];
		        }
		    } else {
		        byteArray = passwordInt.toByteArray();
		    }
		 
		 try {
		   cipher = javax.crypto.Cipher.getInstance("RSA");
		   byte[] passwordBytes = byteArray;
		   PrivateKey privateKey = readPrivateKeyFromFile(privModule, privExponent);
		   cipher.init(Cipher.DECRYPT_MODE, privateKey);
		   dectyptedText = cipher.doFinal(passwordBytes);
		   } catch(NoSuchAlgorithmException e) {
		   } catch(NoSuchPaddingException e) { 
		   } catch(InvalidKeyException e) {
		   } catch(IllegalBlockSizeException e) {
		   } catch(Exception e) {
		   }
		   String passwordNew = new String(dectyptedText);
		   return passwordNew;
	}
	 

	    
	
	/** 
	  * read Public Key From File 
	  * @param fileName 
	  * @return 
	  * @throws IOException 
	  */  
	 public PrivateKey readPrivateKeyFromFile(String smodulus, String sexponent) throws Exception{  
	  try {  
	      BigInteger modulus = new BigInteger(smodulus);
	      BigInteger exponent = new BigInteger(sexponent); 
	     
	      //Get Private Key  
	      RSAPrivateKeySpec rsaPrivateKeySpec = new RSAPrivateKeySpec(modulus, exponent);  
	      KeyFactory fact = KeyFactory.getInstance("RSA");  
	      PrivateKey privateKey = fact.generatePrivate(rsaPrivateKeySpec);  
	              
	      return privateKey;  
	        
	  } catch (Exception e) {  
	   e.printStackTrace();  
	  }  
	  return null;  
	 }
	
	public String getpubModuls() {
		return pubModuls;
	}
	
	public String getpubExponent() {
		return pubExponent;
	}

	public BigInteger getPrivModuls() {
		return privModuls;
	}

	public BigInteger getPrivExponent() {
		return privExponent;
	}
	
}

package nl.utwente.trimm.group42.extras;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class EncryptionPassStronger {
	public static byte[] saveSalt;
	public static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[16];
        random.nextBytes(bytes);
        saveSalt=bytes;
        return bytes;
    }
	public static byte[] HashPassStr(String pass,byte[] salt) {
		byte[] hash = null;
	KeySpec spec = new PBEKeySpec(pass.toCharArray(), salt, 60000, 256);
	try {
		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
		hash = factory.generateSecret(spec).getEncoded();
		
	} catch (NoSuchAlgorithmException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (InvalidKeySpecException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return hash;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//EncryptionPassStronger.HashPassStr("firstrunner18");

	}

}

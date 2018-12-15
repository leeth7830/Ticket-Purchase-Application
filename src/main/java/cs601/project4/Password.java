package cs601.project4;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Random;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * Password class that handles hashing, salting, and decoding to authenticate a user
 * @author Tae Hyon Lee
 *
 */
public class Password {
	
	private static final Random RANDOM = new SecureRandom();
	private static final int KEY_LENGTH = 256;
	private static final int ITERATIONS = 10000;
	
	/**
	 * generates salt
	 * @return
	 */
	public static byte[] getNextSalt() {
	    byte[] salt = new byte[16];
	    RANDOM.nextBytes(salt);
	    return salt;
	  }
	
	/**
	 * hashes a password with salt
	 * @param password
	 * @param salt
	 * @return
	 */
	public static byte[] hash(char[] password, byte[] salt) {
	    PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH);
	    Arrays.fill(password, Character.MIN_VALUE);
	    try {
	      SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1"); //Use PBKDF2WithHmacSHA1 Encryption
	      return skf.generateSecret(spec).getEncoded();
	    } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
	      throw new AssertionError("Error while hashing a password: " + e.getMessage(), e);
	    } finally {
	      spec.clearPassword();
	    }
	 }
	
	/**
	 * Checks if the password is the same as the hashed password in the databse
	 * @param password
	 * @param salt
	 * @param expectedHash
	 * @return
	 */
	public static boolean isExpectedPassword(char[] password, byte[] salt, byte[] expectedHash) {
	    byte[] pwdHash = hash(password, salt);
	    Arrays.fill(password, Character.MIN_VALUE);
	    if (pwdHash.length != expectedHash.length) return false;
	    for (int i = 0; i < pwdHash.length; i++) {
	      if (pwdHash[i] != expectedHash[i]) return false;
	    }
	    return true;
	 }
}

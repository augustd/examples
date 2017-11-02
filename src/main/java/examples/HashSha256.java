package examples;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Usage: 
 *   java -classpath Examples-1.0-SNAPSHOT.jar examples.HashSha256 "string to hash"
 * 
 * @author august
 */
public class HashSha256 {
    
    public static void main(String[] args) {
        String toHash = args[0];
        
        System.out.println("Hashing: " + toHash);

        String cipherText = hash(toHash);
        System.out.println("Hashed: " + cipherText);
    }
    
    public static String hash(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            
            md.update(input.getBytes("UTF-8")); 
            byte[] digest = md.digest();
            
            return hexFormat(digest);
            
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(HashSha256.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(HashSha256.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return "";
    } 
    
    public static String hexFormat(byte[] input) {
        return String.format("%064x", new java.math.BigInteger(1, input));
    }
    
}

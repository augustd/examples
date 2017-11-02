package examples;

import static examples.HashSha256.hexFormat;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * Usage: 
 *   java -classpath Examples-1.0-SNAPSHOT.jar examples.HmacSha256 "string to hash" key
 * 
 * @author august
 */
public class HmacSha256 {
    
    public static void main(String[] args) {
        String toHash = args[0];
        String key    = args[1];
        
        System.out.println("Hashing:  " + toHash);
        System.out.println("With Key: " + key);

        String cipherText = hmac(toHash, key);
        System.out.println("Hashed: " + cipherText);
    }
    
    public static String hmac(String input, String key) {
        try {
            Mac sha256HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");
            sha256HMAC.init(secretKey);
            
            byte[] digest = sha256HMAC.doFinal(input.getBytes("UTF-8"));
            return hexFormat(digest);
            
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(HmacSha256.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(HmacSha256.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(HmacSha256.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
}

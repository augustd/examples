package examples;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Test if Java.net.URL will recognize a malicious URL as valid. 
 * 
 * @author adetlefsen
 */
public class TestUrl {
    
    public static void main(String[] args) {
        
        try {
            URL url = new URL("http://a<img%0Csrc=x%0Conerror=alert(document.domain)>.hsts.pro/r.php?u=//hsts.pro/referer.php");
            System.out.println("domain: " + url.getHost());
        } catch (MalformedURLException ex) {
            Logger.getLogger(TestUrl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}

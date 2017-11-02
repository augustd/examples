package examples;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import org.apache.commons.codec.binary.Base64;

/**
 * Adapted from https://www.mkyong.com/java/java-digital-signatures-example/
 * 
 * NOTE: Be sure to run examples.GenerateKeys first to create the keypair. 
 * 
 * Usage: 
 *   java -classpath Examples-1.0-SNAPSHOT.jar examples.RSASignature "message to sign"
 * 
 * Use examples.RSASignatureVerify to verify the signature. 
 *
 * @author august
 */
public class RSASignature {

    private List<byte[]> list;

    //The constructor of Message class builds the list that will be written to the file.
    //The list consists of the message and the signature.
    public RSASignature(String data, String keyFile) throws InvalidKeyException, Exception {
        list = new ArrayList<byte[]>();
        list.add(data.getBytes());
        list.add(sign(data, keyFile));
    }

    //The method that signs the data using the private key that is stored in keyFile path
    public byte[] sign(String data, String keyFile) throws InvalidKeyException, Exception {
        Signature rsa = Signature.getInstance("SHA1withRSA");
        rsa.initSign(getPrivate(keyFile));
        rsa.update(data.getBytes());
        return rsa.sign();
    }

    //Method to retrieve the Private Key from a file
    public PrivateKey getPrivate(String filename) throws Exception {
        byte[] keyBytes = Files.readAllBytes(new File(filename).toPath());
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(spec);
    }

    //Method to write the List of byte[] to a file
    private void writeToFile(String filename) throws FileNotFoundException, IOException {
        String filename1 = filename + ".txt";
        File f = new File(filename1);
        f.getParentFile().mkdirs();
        FileOutputStream out = new FileOutputStream(filename1);
        out.write(list.get(0));
        out.close();
        
        String filename2 = filename + ".signature.txt";
        File fs = new File(filename2);
        fs.getParentFile().mkdirs();
        FileOutputStream outs = new FileOutputStream(filename2);
        outs.write( Base64.encodeBase64String(list.get(1)).getBytes() );
        outs.close();
        
        System.out.println("Your files are ready.");
    }

    public static void main(String[] args) throws InvalidKeyException, IOException, Exception {
        String data = (args.length > 0) ? args[0] : JOptionPane.showInputDialog("Type your message here");

        new RSASignature(data, "KeyPair/privateKey").writeToFile("MyData/SignedData");
    }
}

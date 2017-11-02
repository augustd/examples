package examples;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;

/**
 * Adapted from https://www.mkyong.com/java/java-digital-signatures-example/
 *
 * NOTE: Be sure to run examples.GenerateKeys first to create the keypair. 
 * NOTE: Be sure to run examples.RSASignature first to create the signature. 
 * 
 * Usage: 
 *   java -classpath Examples-1.0-SNAPSHOT.jar examples.RSASignatureVerify
 * 
 * @author august
 */
public class RSASignatureVerify {

    private final List<byte[]> list = new ArrayList<>();

    @SuppressWarnings("unchecked")
    //The constructor of VerifyMessage class retrieves the byte arrays from the File
    //and prints the message only if the signature is verified.
    public RSASignatureVerify(String filename, String keyFile) throws Exception {
        
        Path path = Paths.get(filename + ".txt");
        byte[] data = Files.readAllBytes(path);
        
        
        //ByteArrayInputStream in = new ByteArrayInputStream(new FileInputStream(filename + ".txt"));
        //this.list = (List<byte[]>) in.readObject();
        this.list.add(data); //(byte[])in.readObject() );
        //in.close();
        
        Path paths = Paths.get(filename + ".signature.txt");
        byte[] datas = Base64.decodeBase64(Files.readAllBytes(paths));
        //ObjectInputStream ins = new ObjectInputStream(new FileInputStream(filename + "signature.txt"));
        //this.list = (List<byte[]>) in.readObject();
        this.list.add( datas ); //(byte[])ins.readObject() );
        //ins.close();
        

        System.out.println(verifySignature(list.get(0), list.get(1), keyFile) ? "VERIFIED MESSAGE"
                + "\n----------------\n" + new String(list.get(0)) : "Could not verify the signature.");
    }

    //Method for signature verification that initializes with the Public Key,
    //updates the data to be verified and then verifies them using the signature
    private boolean verifySignature(byte[] data, byte[] signature, String keyFile) throws Exception {
        Signature sig = Signature.getInstance("SHA1withRSA");
        sig.initVerify(getPublic(keyFile));
        sig.update(data);

        return sig.verify(signature);
    }

    //Method to retrieve the Public Key from a file
    public PublicKey getPublic(String filename) throws Exception {
        byte[] keyBytes = Files.readAllBytes(new File(filename).toPath());
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(spec);
    }

    public static void main(String[] args) throws Exception {
        new RSASignatureVerify("MyData/SignedData", "KeyPair/publicKey");
    }
}

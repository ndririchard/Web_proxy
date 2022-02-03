package utility;

import java.security.*;
import javax.crypto.*;
import logger.Logger;

public class Utility {
    private static Utility _utility;
    private KeyPair pair;
    //constructor
    private Utility(){
        this.generateKey();
        Logger.instance().info("Utility", "New helper is available");
    }
    public static Utility instance(){
        if(_utility == null){
            _utility = new Utility();
        }
        return _utility;
    }
    // setters and getters
    private void setPair(KeyPair pair) {
        this.pair = pair;
    }
    
    public KeyPair getPair(){
        return this.pair;
    }
    // properties
    private void generateKey(){
        KeyPairGenerator generator;
        try {
            generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(4096);
            KeyPair pair = generator.generateKeyPair();
            this.setPair(pair);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }  
    }
    
    public byte[] encrypt(byte[] req){
        this.generateKey();
         // create the cypher for encryption
         Cipher encryptCipher;
         byte[] encryptedMessageBytes = null;
        try {
            encryptCipher = Cipher.getInstance("RSA/ECB/OAEPWITHSHA-512ANDMGF1PADDING");
            encryptCipher.init(Cipher.ENCRYPT_MODE, this.getPair().getPublic());
            // encrypt the data
            encryptedMessageBytes = encryptCipher.doFinal(req);
        } catch (NoSuchAlgorithmException|NoSuchPaddingException|InvalidKeyException|IllegalBlockSizeException|
                BadPaddingException e) {
            e.printStackTrace();
        }
        
        return encryptedMessageBytes;
    }

    public byte[] decrypt(byte[] req){
        Cipher decryptCipher;
        byte[] decryptedMessageBytes = null;
        try {
            decryptCipher = Cipher.getInstance("RSA/ECB/OAEPWITHSHA-512ANDMGF1PADDING");
            decryptCipher.init(Cipher.DECRYPT_MODE, this.getPair().getPrivate());
            
            decryptedMessageBytes = decryptCipher.doFinal(req);
            System.out.println("okay");
        }  catch (NoSuchAlgorithmException|NoSuchPaddingException|InvalidKeyException|IllegalBlockSizeException|
                    BadPaddingException e) {
                e.printStackTrace();
            }
        return decryptedMessageBytes;
    }
}

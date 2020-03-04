package pauloamcosta.com.bankapi.resources.utils;

import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;

@Component
public class AESEncryption {

    private static final String ENCTYPE = "AES";
    private static String keyValueStr = "icefiretestpaulo";


    public String encrypt(String data) throws Exception {
        Key key = generateKey();
        Cipher cipher = Cipher.getInstance(ENCTYPE);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encVal = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encVal);
    }

    public String decrypt(String encryptedData) throws Exception {
        Key key = generateKey();
        Cipher cipher = Cipher.getInstance(ENCTYPE);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decValue = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
        return new String(decValue);
    }

    private Key generateKey() throws Exception {
        return new SecretKeySpec(keyValueStr.getBytes(), ENCTYPE);
    }
}
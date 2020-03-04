package pauloamcosta.com.bankapi.resources.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.*;


@RunWith(SpringRunner.class)
public class AESEncryptionTests {

    @InjectMocks
    AESEncryption aesEncryption;

    String beforeEncrypt = "testString";
    String beforeDecrypt = "twzp44UdLpNjjoBAAEFmGQ==";

    @Test
    public void encryptAGivenString() throws Exception {
        String encrypted =  aesEncryption.encrypt(beforeEncrypt);
       assertThat(encrypted).isEqualTo(beforeDecrypt);
    }

    @Test
    public void decryptAGivenString() throws Exception {
        String decrypted =  aesEncryption.decrypt(beforeDecrypt);
        assertThat(decrypted).isEqualTo(beforeEncrypt);
    }
}

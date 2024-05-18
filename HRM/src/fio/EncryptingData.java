package fio;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
* @author Duc Linh
*/
public class EncryptingData {

	private static String encrypt(String input, SecretKey secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(input.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    private static String decrypt(String input, SecretKey secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(input));
        return new String(decryptedBytes);
    }
	public String settingcrypto(String st ,boolean mode) throws Exception {
		String rs=null;
		String secretKeyString ="mecAudiBmwtoYotahOndasuzukilamBo";
		SecretKey secretKey = new SecretKeySpec(secretKeyString.getBytes(),"AES" );
		if(mode) {
			rs = encrypt(st,secretKey);
		}else {
			rs= decrypt(st, secretKey);
		}
						
		return rs;
	}
}

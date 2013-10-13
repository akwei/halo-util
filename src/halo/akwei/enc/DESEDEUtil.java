package halo.akwei.enc;

import java.io.UnsupportedEncodingException;
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class DESEDEUtil {

	private Cipher encryptCipher = null;

	private Cipher decryptCipher = null;

	public static final String DES_ECB_PKCS5Padding = "DESede/ECB/PKCS5Padding";

	public DESEDEUtil(byte[] keyBytes, String transformation) {
		try {
			Key key = new SecretKeySpec(keyBytes, "DESede");
			encryptCipher = Cipher.getInstance(transformation);
			encryptCipher.init(Cipher.ENCRYPT_MODE, key);
			decryptCipher = Cipher.getInstance(transformation);
			decryptCipher.init(Cipher.DECRYPT_MODE, key);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static byte[] getKey(String key) {
		return key.substring(0, 8).getBytes();
	}

	public byte[] encrypt(byte[] data) {
		try {
			return encryptCipher.doFinal(data);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public byte[] decrypt(byte[] data) {
		try {
			return decryptCipher.doFinal(data);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args) throws UnsupportedEncodingException {
		String key = "123456781234567812345678";
		String text = "你好，我爱你，中国，abcd!-=39458*&^";
		DESEDEUtil desedeUtil = new DESEDEUtil(key.getBytes(),
		        DES_ECB_PKCS5Padding);
		byte[] data = desedeUtil.encrypt(text.getBytes("utf-8"));
		System.out.println(BASE64Util.encode(data));
	}
}
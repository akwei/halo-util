package halo.akwei.enc;

import java.io.UnsupportedEncodingException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class AESUtil {

	private static final String transformation = "AES/ECB/PKCS5Padding";

	private Cipher encCipher;

	private Cipher decCipher;

	public AESUtil(byte[] key) {
		SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
		try {
			encCipher = Cipher.getInstance(transformation);
			encCipher.init(Cipher.ENCRYPT_MODE, secretKey);
			decCipher = Cipher.getInstance(transformation);// 创建密码器
			decCipher.init(Cipher.DECRYPT_MODE, secretKey);// 初始化
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public byte[] encrypt(byte[] data) {
		try {
			return this.encCipher.doFinal(data);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public byte[] decrypt(byte[] data) {
		try {
			return this.decCipher.doFinal(data);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args) throws UnsupportedEncodingException {
		AESUtil aesUtil = new AESUtil("1234567812345678".getBytes());
		byte[] encData = BASE64Util
		        .decode("oO7khHummGsw6dFPKu83C8blEjdVUDbhf2ii4c0ET5HoOIL5kClQR7gj/rP04fSj");
		byte[] decData = aesUtil.decrypt(encData);
		System.out.println(new String(decData, "utf-8"));
	}
}

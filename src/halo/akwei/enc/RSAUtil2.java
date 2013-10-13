package halo.akwei.enc;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

public class RSAUtil2 {

	public static final String KEY_ALGORITHM = "RSA";

	/**
	 * RSA最大加密明文大小
	 */
	private static final int MAX_ENCRYPT_BLOCK = 117;

	/**
	 * RSA最大解密密文大小
	 */
	private static final int MAX_DECRYPT_BLOCK = 128;

	private static String ALGORITHM = "RSA/ECB/PKCS1Padding";

	public static final int KEYSIZE = 1024;

	private byte[] publicKeyBytes;

	private byte[] privateKeyBytes;

	private byte[] modulusBytes;

	private Cipher encryptCipher;

	private Cipher decryptCipher;

	public RSAUtil2() {
	}

	public RSAUtil2(byte[] publicKeyBytes, byte[] privateKeyBytes,
	        byte[] modulusBytes) throws Exception {
		super();
		this.publicKeyBytes = publicKeyBytes;
		this.privateKeyBytes = privateKeyBytes;
		this.modulusBytes = modulusBytes;
		Key publicKey = buildPublicKey(this.publicKeyBytes);
		Key privateKey = buildPrivateKey(this.privateKeyBytes);
		this.encryptCipher = Cipher.getInstance(ALGORITHM);
		this.encryptCipher.init(Cipher.ENCRYPT_MODE, publicKey);
		this.decryptCipher = Cipher.getInstance(ALGORITHM);
		this.decryptCipher.init(Cipher.DECRYPT_MODE, privateKey);
	}

	public void genKeyPair() throws Exception {
		KeyPairGenerator keyPairGen = KeyPairGenerator
		        .getInstance(KEY_ALGORITHM);
		keyPairGen.initialize(1024);
		KeyPair keyPair = keyPairGen.generateKeyPair();
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		this.publicKeyBytes = publicKey.getEncoded();
		this.privateKeyBytes = privateKey.getEncoded();
		this.modulusBytes = publicKey.getModulus().toByteArray();
		this.encryptCipher = Cipher.getInstance(ALGORITHM);
		this.encryptCipher.init(Cipher.ENCRYPT_MODE, publicKey);
		this.decryptCipher = Cipher.getInstance(ALGORITHM);
		this.decryptCipher.init(Cipher.DECRYPT_MODE, privateKey);
	}

	public byte[] getPublicKeyBytes() {
		return publicKeyBytes;
	}

	public byte[] getPrivateKeyBytes() {
		return privateKeyBytes;
	}

	public byte[] getModulusBytes() {
		return modulusBytes;
	}

	public static RSAUtil2 createRSAUtilWithRandomKeys() throws Exception {
		KeyPairGenerator kpg;
		try {
			kpg = KeyPairGenerator.getInstance("RSA");
		}
		catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
		kpg.initialize(KEYSIZE, new SecureRandom());
		KeyPair kp = kpg.generateKeyPair();
		Key publicKey = kp.getPublic();
		Key privateKey = kp.getPrivate();
		RSAPublicKey rsp = (RSAPublicKey) kp.getPublic();
		BigInteger bint = rsp.getModulus();
		RSAUtil2 rsaUtil = new RSAUtil2(publicKey.getEncoded(),
		        privateKey.getEncoded(), bint.toByteArray());
		return rsaUtil;
	}

	public byte[] encrypt(byte[] data) throws Exception {
		int inputLen = data.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		// 对数据分段加密
		while (inputLen - offSet > 0) {
			if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
				cache = this.encryptCipher.doFinal(data, offSet,
				        MAX_ENCRYPT_BLOCK);
			}
			else {
				cache = this.encryptCipher.doFinal(data, offSet, inputLen
				        - offSet);
			}
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * MAX_ENCRYPT_BLOCK;
		}
		byte[] encryptedData = out.toByteArray();
		out.close();
		return encryptedData;
	}

	public byte[] decrypt(byte[] data) throws Exception {
		int inputLen = data.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		// 对数据分段解密
		while (inputLen - offSet > 0) {
			if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
				cache = this.decryptCipher
				        .doFinal(data, offSet, MAX_DECRYPT_BLOCK);
			}
			else {
				cache = this.decryptCipher
				        .doFinal(data, offSet, inputLen - offSet);
			}
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * MAX_DECRYPT_BLOCK;
		}
		byte[] decryptedData = out.toByteArray();
		out.close();
		return decryptedData;
	}

	private PublicKey buildPublicKey(byte[] bytes) throws Exception {
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(bytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PublicKey publicKey = keyFactory.generatePublic(keySpec);
		return publicKey;
	}

	private PrivateKey buildPrivateKey(byte[] bytes) throws Exception {
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(bytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
		return privateKey;
	}

	public byte[] sign(byte[] bytes, String algorithm) {
		try {
			PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(this.privateKeyBytes);
			KeyFactory keyf = KeyFactory.getInstance("RSA");
			PrivateKey priKey = keyf.generatePrivate(priPKCS8);
			Signature signature = Signature.getInstance(algorithm);
			signature.initSign(priKey);
			signature.update(bytes);
			byte[] data = signature.sign();
			return data;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public boolean checkSign(byte[] bytes, byte[] signData, String algorithm) {
		try {
			PublicKey pubKey = buildPublicKey(this.publicKeyBytes);
			Signature signature = Signature.getInstance(algorithm);
			signature.initVerify(pubKey);
			signature.update(bytes);
			return signature.verify(signData);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args) throws Exception {
		// rsa();
		rersa();
	}

	@SuppressWarnings("unused")
	private static void rsa() throws Exception {
		RSAUtil2 rsaUtil = RSAUtil2.createRSAUtilWithRandomKeys();
		String publicKey = BASE64Util.encode(rsaUtil.getPublicKeyBytes());
		String privateKey = BASE64Util.encode(rsaUtil.getPrivateKeyBytes());
		String modulus = BASE64Util.encode(rsaUtil.getModulusBytes());
		String text = "hello1234567890";
		byte[] data = text.getBytes("utf-8");
		String b64 = BASE64Util.encode(rsaUtil.encrypt(data));
		System.out.println("b64:" + b64);
		data = rsaUtil.decrypt(BASE64Util.decode(b64));
		text = new String(data, "utf-8");
		System.out.println("text:" + text);
		System.out.println("public:" + publicKey);
		System.out.println("private:" + privateKey);
		System.out.println("modulus:" + modulus);
	}

	private static void rersa() throws Exception {
		System.out.println("begin");
		// String b64 =
		// "X80dEQLRVjD2SrOpPOSemb5Px4F08xQb+WEEoAwJASZiUxC7vKV2mNZq0Khzre59AsNzhM6IGEgdf7ww8aZ50AAPTRu7FrV0kUO62St+DHtEktlt0o+JoXTKF0gfFOnIzGxwsKrvh3bDtSiZTMLw/nt+aC+3TTLa8qqrVwFYrRQ=";
		// byte[] pubKeyBytes = BASE64Util
		// .decode("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCqmLhPyW+UhQ7g3Am9i7UPNieOUM0ylXmvFyWv+C+S3s4ReACtAJs9R78gIlLCWQ069HOKb/yc45WLVpdg+tfSpZ1sqz0LukfRYJ0wApOY5O43Xk9XJ+AOKpQRsBPSDCTA+oukOSO03CInwqaKju/pHn6FLM0flpdh5k96FBEZlQIDAQAB");
		// byte[] prvKeyBytes = BASE64Util
		// .decode("MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAKqYuE/Jb5SFDuDcCb2LtQ82J45QzTKVea8XJa/4L5LezhF4AK0Amz1HvyAiUsJZDTr0c4pv/JzjlYtWl2D619KlnWyrPQu6R9FgnTACk5jk7jdeT1cn4A4qlBGwE9IMJMD6i6Q5I7TcIifCpoqO7+kefoUszR+Wl2HmT3oUERmVAgMBAAECgYAHo9VFRXkoxpi2+0O4aGRQbRVyay31YPpVHA1FBzFiO3YxFBW7qbZKFUd68e6Q45oTFNWD0qYWhJ6y4crfvfwiUSRT3H7CuBU8kSa9hq8upZRv0DxCJrBHFsiPKbs9XjS1JI+VF6Z1RYN+qjCQR/VrVWayUKj9sWWYl6pk5BKtlQJBAN8bQC2Y3t9RJP2H/WM0XGCc95irIrIwBEj8PEZPv44scyb1dJD7fgLoXZTGeWG3g7d92M+u+JQtU0k6n8/RQUcCQQDDv5Qgm28omJcSHObJ4AlAGgCmq8J2smm78YD0rWKy0hHheqdk3CmX1CjBstgsoshqk36gsGUUGGrJIV08LNxDAkEAhRgEmS7xBdeqcXSGvH+n/BqnufOdpx7t73xJmMhKRc5Og5c6CqN88M8dxHsCPkDz2qrAhGSk9gB0BuNqItd6UQJBAMB/qSr70C/LB12foHJMaKxqJdPCO2XBBGv4DooP7GzQWxczKGTugdS2pjH+L8qNdqwgSjm53l0JuhH7RpYDw8MCQB5pqcwFioi/bfL0i7KHQa9i2tDuO7qV1XAlPppZsGZLdHFBjvY0T3zjfQ2px5x8aWUfUUXn33hE2PBI4axF9Ng=");
		// RSAUtil2 rsaUtil = new RSAUtil2(pubKeyBytes, prvKeyBytes, null);
		// byte[] data = rsaUtil.decrypt(BASE64Util.decode(b64));
		// System.out.println(new String(data, "utf-8"));
		//
		RSAUtil2 rsaUtil2 = new RSAUtil2();
		rsaUtil2.genKeyPair();
		String pub = BASE64Util.encode(rsaUtil2.getPublicKeyBytes());
		String prv = BASE64Util.encode(rsaUtil2.getPrivateKeyBytes());
		System.out.println("pub : " + pub);
		System.out.println("prv : " + prv);
		byte[] encData = rsaUtil2.encrypt("你好".getBytes("utf-8"));
		System.out.println(BASE64Util.encode(encData));
		byte[] decData = rsaUtil2.decrypt(encData);
		System.out.println(new String(decData, "utf-8"));
	}
}

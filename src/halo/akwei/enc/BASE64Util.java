package halo.akwei.enc;

import org.apache.commons.codec.binary.Base64;

public class BASE64Util {

	private BASE64Util() {//
	}

	public static byte[] decode(String s) {
		return Base64.decodeBase64(s);
	}

	public static String encode(byte[] data) {
		return Base64.encodeBase64String(data);
	}

	public static void main(String[] args) {
		try {
			System.out.println(BASE64Util.encode("123".getBytes("UTF-8")));
			System.out.println(BASE64Util
			        .decode("5LqU5LiA5Yqz5Yqo6IqCaGVsbG8xMjM2NTQ="));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}

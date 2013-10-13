package halo.akwei.enc;

import java.io.UnsupportedEncodingException;

public class HEXUtil {

	private static final String[] hexStrings;
	static {
		hexStrings = new String[256];
		for (int i = 0; i < 256; i++) {
			StringBuilder d = new StringBuilder(2);
			char ch = Character.forDigit(((byte) i >> 4) & 0x0F, 16);
			d.append(Character.toUpperCase(ch));
			ch = Character.forDigit((byte) i & 0x0F, 16);
			d.append(Character.toUpperCase(ch));
			hexStrings[i] = d.toString();
		}
	}

	public static String byte2Hex(byte[] b) {
		StringBuilder d = new StringBuilder(b.length * 2);
		for (byte aB : b) {
			d.append(hexStrings[aB & 0xFF]);
		}
		return d.toString();
	}

	private static byte[] hex2byte(byte[] b, int offset, int len) {
		byte[] d = new byte[len];
		for (int i = 0; i < len * 2; i++) {
			int shift = i % 2 == 1 ? 0 : 4;
			d[i >> 1] |= Character.digit((char) b[offset + i], 16) << shift;
		}
		return d;
	}

	public static byte[] hex2byte(String s, String charsetName) {
		if (s.length() % 2 == 0) {
			try {
				if (charsetName == null) {
					return hex2byte(s.getBytes(), 0, s.length() >> 1);
				}
				return hex2byte(s.getBytes(charsetName), 0, s.length() >> 1);
			}
			catch (UnsupportedEncodingException e) {
				throw new RuntimeException(e);
			}
		}
		// Padding left zero to make it even size #Bug raised by tommy
		return hex2byte("0" + s, charsetName);
	}
	public static void main(String[] args) {
	    byte[] data=hex2byte("B2", "utf-8");
	    System.out.println(data.length);
    }
}

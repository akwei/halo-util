package halo.akwei.util;

import java.util.Random;

public class RandomUtil {

	private static char[] ch = new char[] { 'a', 'b', 'c', 'd', 'e', 'f', 'g',
	        'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
	        'u', 'v', 'w', 'x', 'y', 'z' };

	private RandomUtil() {
	}

	public static int getRandomInt(int n) {
		Random random = new Random();
		return random.nextInt(n);
	}

	public static String getRandom(int len) {
		Random r = new Random();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < len; i++) {
			sb.append(r.nextInt(10));
		}
		return sb.toString();
	}

	public static String getRandomString(int len) {
		Random r = new Random();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < len; i++) {
			int a = r.nextInt(2);
			if (a == 0) {
				sb.append(r.nextInt(10));
			}
			else {
				sb.append(ch[r.nextInt(ch.length)]);
			}
		}
		return sb.toString();
	}
}
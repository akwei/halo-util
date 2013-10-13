package halo.akwei.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.oro.text.regex.MalformedPatternException;
import org.apache.oro.text.regex.PatternCompiler;
import org.apache.oro.text.regex.PatternMatcher;
import org.apache.oro.text.regex.Perl5Compiler;
import org.apache.oro.text.regex.Perl5Matcher;

public class DataUtil {

	public static final String DEFAULTCHARSET = "UTF-8";

	private DataUtil() {
	}

	public static boolean isNumberAndChar(String value) {
		org.apache.oro.text.regex.Pattern numberAndCharPattern = null;
		PatternMatcher numberAndCharPatternMatcher = null;
		PatternCompiler compiler = null;
		try {
			compiler = new Perl5Compiler();
			numberAndCharPattern = compiler.compile("^[a-zA-Z0-9]+",
			        Perl5Compiler.CASE_INSENSITIVE_MASK);
			numberAndCharPatternMatcher = new Perl5Matcher();
		}
		catch (MalformedPatternException e) {
			throw new RuntimeException(e);
		}
		return numberAndCharPatternMatcher.matches(value, numberAndCharPattern);
	}

	public static boolean isNumberAndCharAndDotAnd_(String value) {
		org.apache.oro.text.regex.Pattern numberAndCharPattern = null;
		PatternMatcher numberAndCharPatternMatcher = null;
		PatternCompiler compiler = null;
		try {
			compiler = new Perl5Compiler();
			numberAndCharPattern = compiler.compile("^[a-zA-Z0-9\\._]+",
			        Perl5Compiler.CASE_INSENSITIVE_MASK);
			numberAndCharPatternMatcher = new Perl5Matcher();
		}
		catch (MalformedPatternException e) {
			throw new RuntimeException(e);
		}
		return numberAndCharPatternMatcher.matches(value, numberAndCharPattern);
	}

	public static boolean isEmpty(String value) {
		if (value == null || value.trim().length() == 0) {
			return true;
		}
		return false;
	}

	public static boolean isNotEmpty(String value) {
		if (value != null && value.trim().length() > 0) {
			return true;
		}
		return false;
	}

	public static boolean isMobile(String mobile) {
		if (isEmpty(mobile)) {
			return false;
		}
		if (mobile.length() != 11) {
			return false;
		}
		return true;
	}

	public static String urlEncoder(String value) {
		if (value != null) {
			try {
				return URLEncoder.encode(value, DEFAULTCHARSET);
			}
			catch (UnsupportedEncodingException e) {
				throw new RuntimeException(e);
			}
		}
		return "";
	}

	public static String urlEncoder(String value, String charset) {
		if (value != null) {
			try {
				return URLEncoder.encode(value, charset);
			}
			catch (UnsupportedEncodingException e) {
				throw new RuntimeException(e);
			}
		}
		return "";
	}

	public static String urlDecoder(String value) {
		if (value != null) {
			try {
				return URLDecoder.decode(value, DEFAULTCHARSET);
			}
			catch (UnsupportedEncodingException e) {
				throw new RuntimeException(e);
			}
		}
		return "";
	}

	public static String urlDecoder(String value, String charset) {
		if (value != null) {
			try {
				return URLDecoder.decode(value, charset);
			}
			catch (UnsupportedEncodingException e) {
				throw new RuntimeException(e);
			}
		}
		return "";
	}

	public static String subString(String str, int len) {
		if (str == null) {
			return null;
		}
		if (str.length() <= len) {
			return str;
		}
		return str.substring(0, len);
	}

	public static <T> List<T> subList(List<T> list, int begin, int size) {
		if (list.size() - 1 < begin) {
			return new ArrayList<T>();
		}
		if (list.size() <= size) {
			return list.subList(begin, begin + list.size());
		}
		return list.subList(begin, begin + size);
	}
}
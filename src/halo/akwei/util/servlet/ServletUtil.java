package halo.akwei.util.servlet;

import halo.akwei.util.DataUtil;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

public class ServletUtil {

	private ServletUtil() {
	}

	private static final String HTTP_METHOD_POST = "post";

	public static String CHARSET_SOURCE = "ISO-8859-1";

	public static String CHARSET_TARGET = "UTF-8";

	public static final String USER_AGENT = "user-agent";

	public static final String ACCEPT = "accept";

	private static final String PAGE_ATTR_KEY = "page";

	public static boolean NEED_CHARSET_ENCODE = true;

	public static String LOCALE_ATTR_KEY = "halo_local_attr";

	/**
	 * 返回指定名字的header字段
	 * 
	 * @param request
	 * @param name
	 * @return
	 */
	public static String getHeader(HttpServletRequest request, String name) {
		String value = request.getHeader(name);
		if (value == null) {
			return "";
		}
		return value.trim();
	}

	/**
	 * 返回当前请求的ua信息
	 * 
	 * @param request
	 * @return
	 */
	public static String getUA(HttpServletRequest request) {
		return getHeader(request, USER_AGENT);
	}

	public static boolean isPc(HttpServletRequest request) {
		if (getUA(request).indexOf("android") != -1) {
			return false;
		}
		if (getUA(request).indexOf("iphone") != -1) {
			return false;
		}
		String accept = getAccept(request);
		if (accept == null || accept.indexOf("wap") != -1
		        || accept.indexOf("j2me") != -1
		        || accept.indexOf("text/vnd") != -1
		        || accept.indexOf("wml") != -1) {
			return false;
		}
		return true;
	}

	public static boolean isWap(HttpServletRequest request) {
		if (getUA(request).indexOf("android") != -1) {
			return true;
		}
		if (getUA(request).indexOf("iphone") != -1) {
			return true;
		}
		String accept = getAccept(request);
		if (accept.indexOf("wap") != -1 || accept.indexOf("j2me") != -1
		        || accept.indexOf("text/vnd") != -1
		        || accept.indexOf("wml") != -1) {
			return true;
		}
		return false;
	}

	/**
	 * 返回当前请求的accept信息
	 * 
	 * @param request
	 * @return
	 */
	public static String getAccept(HttpServletRequest request) {
		return getHeader(request, ACCEPT);
	}

	public static void sendHtml(HttpServletResponse response, Object value)
	        throws IOException {
		response.setContentType("text/html;charset=UTF-8");
		write(response, value.toString());
	}

	public static void write(HttpServletResponse response, String value)
	        throws IOException {
		Writer writer = null;
		try {
			writer = response.getWriter();
			writer.write(value);
		}
		catch (IOException e) {
			e.printStackTrace();
			throw e;
		}
	}

	public static Number[] getNumbers(HttpServletRequest request, String key) {
		String[] v = request.getParameterValues(key);
		if (v == null) {
			return null;
		}
		Number[] t = new Number[v.length];
		for (int i = 0; i < v.length; i++) {
			if (v[i].trim().equals("")) {
				t[i] = 0;
			}
			else {
				t[i] = new BigDecimal(v[i]);
			}
		}
		return t;
	}

	public static Number getNumber(HttpServletRequest request, String key,
	        Number num) {
		String t = request.getParameter(key);
		if (t == null) {
			return num;
		}
		try {
			return new BigDecimal(t);
		}
		catch (Exception e) {
			return 0;
		}
	}

	public static Number getNumber(HttpServletRequest request, String key) {
		try {
			String t = request.getParameter(key);
			if (t == null || t.equals("")) {
				return 0;
			}
			return new BigDecimal(t);
		}
		catch (Exception e) {
			return 0;
		}
	}

	public static String getServerInfo(HttpServletRequest request) {
		StringBuilder sb = new StringBuilder();
		sb.append(request.getScheme());
		sb.append("://");
		sb.append(request.getServerName());
		int port = request.getServerPort();
		if (port != 80) {
			sb.append(":").append(port);
		}
		sb.append(request.getContextPath());
		return sb.toString();
	}

	public static Cookie getCookie(HttpServletRequest request, String name) {
		Cookie[] cs = request.getCookies();
		if (cs == null) {
			return null;
		}
		for (Cookie cookie : cs) {
			if (cookie.getName().equals(name)) {
				return cookie;
			}
		}
		return null;
	}

	public static String getStringRow(HttpServletRequest request, String key, String defaultValue) {
		String s = getStringRow(request, key);
		if (s == null) {
			return defaultValue;
		}
		return s;
	}

	public static String getStringRow(HttpServletRequest request, String key) {
		String s = getString(request, key);
		if (s != null) {
			s = s.replaceAll("\r", "").replaceAll("\n", "");
		}
		return s;
	}

	public static String getStringRowAndSetAttr(HttpServletRequest request, String key) {
		String v = getStringRow(request, key);
		if (v != null) {
			request.setAttribute(key, v);
		}
		return v;
	}

	public static String getStringRowAndSetAttr(HttpServletRequest request, String key,
	        String defaultValue) {
		String s = getStringRow(request, key, defaultValue);
		request.setAttribute(key, s);
		return s;
	}

	public static String getStringAndSetAttr(HttpServletRequest request, String key,
	        String defaultValue) {
		String s = getString(request, key, defaultValue);
		request.setAttribute(key, s);
		return s;
	}

	public static String getStringAndSetAttr(HttpServletRequest request, String key) {
		String s = getString(request, key);
		if (s != null) {
			request.setAttribute(key, s);
		}
		return s;
	}

	public static String getString(HttpServletRequest request, String key, String defaultValue) {
		String s = getString(request, key);
		if (s == null) {
			return defaultValue;
		}
		return s;
	}

	public static String getString(HttpServletRequest request, String key) {
		String t = request.getParameter(key);
		if (t == null) {
			return null;
		}
		t = t.trim();
		if (t.length() == 0) {
			return "";
		}
		if (request.getMethod().equalsIgnoreCase(HTTP_METHOD_POST)) {
			return t;
		}
		try {
			return new String(t.getBytes(CHARSET_SOURCE), CHARSET_TARGET);
		}
		catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	public static int getPage(HttpServletRequest request) {
		int page = getInt(request, PAGE_ATTR_KEY);
		if (page < 1) {
			page = 1;
		}
		request.setAttribute(PAGE_ATTR_KEY, page);
		return page;
	}

	public static long getLong(HttpServletRequest request, String key) {
		return getNumber(request, key).longValue();
	}

	public static long getLongAndSetAttr(HttpServletRequest request, String key) {
		long v = getLong(request, key);
		request.setAttribute(key, v);
		return v;
	}

	public static long getLong(HttpServletRequest request, String key, long num) {
		return getNumber(request, key, num).longValue();
	}

	public static long getLongSetAttr(HttpServletRequest request, String key, long num) {
		long v = getLong(request, key, num);
		request.setAttribute(key, v);
		return v;
	}

	public static int getInt(HttpServletRequest request, String key) {
		return getNumber(request, key).intValue();
	}

	public static int getInt(HttpServletRequest request, String key, long num) {
		return getNumber(request, key, num).intValue();
	}

	public static byte getByte(HttpServletRequest request, String key) {
		return getNumber(request, key).byteValue();
	}

	public static byte getByte(HttpServletRequest request, String key, long num) {
		return getNumber(request, key, num).byteValue();
	}

	public static double getDouble(HttpServletRequest request, String key) {
		return getNumber(request, key).doubleValue();
	}

	public static double getDouble(HttpServletRequest request, String key,
	        double num) {
		return getNumber(request, key, num).doubleValue();
	}

	public static void setAttrFromParameter(HttpServletRequest request, String key) {
		String v = getString(request, key);
		if (v != null) {
			request.setAttribute(key, v);
		}
	}

	public static void setAttrEncString(HttpServletRequest request, String key, String value,
	        String charsetName) {
		if (value != null) {
			request.setAttribute(key, value);
			request.setAttribute("enc_" + key, DataUtil.urlEncoder(value, charsetName));
		}
	}

	public static void setAttrEncStringFromParameter(HttpServletRequest request, String key,
	        String charsetName) {
		String v = getString(request, key);
		if (v != null) {
			setAttrEncString(request, key, v, charsetName);
		}
	}

	public static void setSessionAttr(HttpServletRequest request, String name,
	        Object value) {
		request.getSession().setAttribute(name, value);
	}

	public static Object getSessionValue(HttpServletRequest request, String name) {
		return request.getSession().getAttribute(name);
	}

	public static Object getRequestValue(HttpServletRequest request, String name) {
		return request.getAttribute(name);
	}

	public static void removeSessionAttr(HttpServletRequest request, String name) {
		request.getSession().removeAttribute(name);
	}

	public static String getRemoteAddr(HttpServletRequest request) {
		String ip = null;
		boolean getIp = false;
		for (String ipGetter : ipGetterArray) {
			ip = request.getHeader(ipGetter);
			if (isGetIPFmt(ip)) {
				getIp = true;
				break;
			}
		}
		if (!getIp) {
			ip = request.getRemoteAddr();
		}
		if (ip != null && ip.indexOf(",") != -1) {
			ip = ip.split(",")[0];
		}
		return ip;
	}

	private static String[] ipGetterArray = { "X-Forwarded-For", "Proxy-Client-IP",
	        "WL-Proxy-Client-IP", "X-Real-IP" };

	private static boolean isGetIPFmt(String ip) {
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			return false;
		}
		return true;
	}

	public static byte[] getUploadBytes(HttpServletRequest request, String name) throws
	        IOException {
		if (request instanceof MultipartHttpServletRequest) {
			MultipartHttpServletRequest mreq = (MultipartHttpServletRequest) request;
			MultipartFile file = mreq.getFile(name);
			if (file == null) {
				return null;
			}
			return file.getBytes();
		}
		return null;
	}

	public static List<byte[]> getUploadBytesList(HttpServletRequest request, String name)
	        throws IOException {
		List<byte[]> list = new ArrayList<byte[]>();
		if (request instanceof MultipartHttpServletRequest) {
			MultipartHttpServletRequest mreq = (MultipartHttpServletRequest) request;
			List<MultipartFile> mlist = mreq.getFiles(name);
			for (MultipartFile file : mlist) {
				if (file != null) {
					list.add(file.getBytes());
				}
			}
		}
		return list;
	}
}
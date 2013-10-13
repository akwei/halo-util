package halo.akwei.util.servlet;


import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class HKHttpServletRequest extends HttpServletRequestWrapper {

	private HttpServletRequest _Request;

	public static boolean needConverCharset = true;

	private static final String sourceCharsetName = "ISO-8859-1";

	public static String targetCharsetName = "UTF-8";

	public HKHttpServletRequest(HttpServletRequest request) {
		super(request);
		this._Request = request;
	}

	@Override
	public String getParameter(String name) {
		String v = ServletUtil.getString(this._Request, name);
		return v;
	}

	@Override
	public String[] getParameterValues(String name) {
		if (this.getMethod().equalsIgnoreCase("post")) {
			return super.getParameterValues(name);
		}
		String[] vs = super.getParameterValues(name);
		if (vs == null || vs.length == 0) {
			return vs;
		}
		String[] vs_cp = new String[vs.length];
		int i = 0;
		for (String s : vs) {
			vs_cp[i] = this.getUTF8Value(s);
			i++;
		}
		return vs_cp;
	}

	private String getUTF8Value(String v) {
		if (v == null) {
			return null;
		}
		if (!needConverCharset) {
			return v.trim();
		}
		try {
			return new String(v.trim().getBytes(sourceCharsetName), targetCharsetName);
		}
		catch (UnsupportedEncodingException e) {
			return null;
		}
	}
}

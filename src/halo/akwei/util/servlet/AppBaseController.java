package halo.akwei.util.servlet;

import javax.servlet.http.HttpServletRequest;

public class AppBaseController {

	public long getLong(HttpServletRequest request, String key) {
		return ServletUtil.getLong(request, key);
	}

	public long getLong(HttpServletRequest request, String key, long defValue) {
		return ServletUtil.getLong(request, key, defValue);
	}

	public long getLongAndSetAttr(HttpServletRequest request, String key) {
		return ServletUtil.getLongAndSetAttr(request, key);
	}

	public int getInt(HttpServletRequest request, String key) {
		return ServletUtil.getInt(request, key);
	}

	public int getInt(HttpServletRequest request, String key, int defValue) {
		return ServletUtil.getInt(request, key, defValue);
	}

	public double getDouble(HttpServletRequest request, String key, double defValue) {
		return ServletUtil.getDouble(request, key, defValue);
	}

	public double getDouble(HttpServletRequest request, String key) {
		return ServletUtil.getDouble(request, key);
	}

	public String getString(HttpServletRequest request, String key) {
		return ServletUtil.getString(request, key);
	}

	public String getString(HttpServletRequest request, String key, String defValue) {
		return ServletUtil.getString(request, key, defValue);
	}

	public String getStringRow(HttpServletRequest request, String key) {
		return ServletUtil.getStringRow(request, key);
	}

	public String getStringRow(HttpServletRequest request, String key, String defValue) {
		return ServletUtil.getStringRow(request, key, defValue);
	}

	public void setAttrFromParameter(HttpServletRequest request, String key) {
		ServletUtil.setAttrFromParameter(request, key);
	}

	public void setAttrEncString(HttpServletRequest request, String key, String value,
	        String charsetName) {
		ServletUtil.setAttrEncString(request, key, value, charsetName);
	}

	public void setAttrEncStringFromParameter(HttpServletRequest request, String key,
	        String charsetName) {
		ServletUtil.setAttrEncStringFromParameter(request, key, charsetName);
	}

	public void setAttrEncString(HttpServletRequest request, String key, String value) {
		ServletUtil.setAttrEncString(request, key, value, "utf-8");
	}

	public void setAttrEncStringFromParameter(HttpServletRequest request, String key) {
		ServletUtil.setAttrEncStringFromParameter(request, key, "utf-8");
	}

	public String getStringRowAndSetAttr(HttpServletRequest request, String key) {
		return ServletUtil.getStringRowAndSetAttr(request, key);
	}

	public String getStringRowAndSetAttr(HttpServletRequest request, String key,
	        String defaultValue) {
		return ServletUtil.getStringRowAndSetAttr(request, key, defaultValue);
	}

	public String getStringAndSetAttr(HttpServletRequest request, String key,
	        String defaultValue) {
		return ServletUtil.getStringAndSetAttr(request, key, defaultValue);
	}

	public String getStringAndSetAttr(HttpServletRequest request, String key) {
		return ServletUtil.getStringAndSetAttr(request, key);
	}
}
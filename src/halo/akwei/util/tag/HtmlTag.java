package halo.akwei.util.tag;

import halo.akwei.util.DataUtil;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

public class HtmlTag extends BodyTagSupport {

	private static final long serialVersionUID = 2444660484543529830L;

	private String value;

	private boolean singleLine;

	private boolean textarea;

	private boolean encode;

	public void setEncode(boolean encode) {
		this.encode = encode;
	}

	public void setTextarea(boolean textarea) {
		this.textarea = textarea;
	}

	public void setSingleLine(boolean singleLine) {
		this.singleLine = singleLine;
	}

	public void setValue(String value) {
		this.value = value;
	}

	protected void adapter(JspWriter writer) throws IOException {
		if (DataUtil.isEmpty(this.value)) {
			return;
		}
		if (singleLine) {
			String v = toHtmlSingleLine(this.value);
			if (this.encode) {
				v = DataUtil.urlEncoder(v);
			}
			writer.append(v);
			return;
		}
		if (this.textarea) {
			String v = toHtml(this.value).replaceAll("<br/>",
			        "\n");
			if (this.encode) {
				v = DataUtil.urlEncoder(v);
			}
			writer.append(v);
			return;
		}
		String v = toHtml(this.value);
		if (this.encode) {
			v = DataUtil.urlEncoder(v);
		}
		writer.append(v);
	}

	public static String toHtml(String str) {
		if (str == null) {
			return null;
		}
		if (str.trim().length() == 0) {
			return str;
		}
		return str.replaceAll("&", "&amp;").replaceAll("<", "&lt;")
		        .replaceAll(">", "&gt;").replaceAll("\n", "<br/>")
		        .replaceAll("\r", "").replaceAll("\"", "&quot;")
		        .replaceAll("'", "&#39;");
	}

	public static String toHtmlSingleLine(String str) {
		if (str == null) {
			return null;
		}
		if (str.trim().length() == 0) {
			return str;
		}
		return str.replaceAll("&", "&amp;").replaceAll("<", "&lt;")
		        .replaceAll(">", "&gt;").replaceAll("\n", "")
		        .replaceAll("\r", "").replaceAll("\"", "&quot;")
		        .replaceAll("'", "&#39;");
	}

	@Override
	public int doEndTag() throws JspException {
		try {
			adapter(pageContext.getOut());
		}
		catch (Exception e) {
			throw new JspException(e);
		}
		return super.doEndTag();
	}
}
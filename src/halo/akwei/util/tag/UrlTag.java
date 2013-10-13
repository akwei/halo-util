package halo.akwei.util.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

public class UrlTag extends BodyTagSupport {

	private static final long serialVersionUID = -2326054354483866678L;

	private String baseUrl;

	private String suffix;

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	protected void adapter(JspWriter writer) throws IOException {
		StringBuilder sb = new StringBuilder();
		sb.append(this.baseUrl);
		sb.append("?");
		sb.append(suffix);
		writer.append(sb.toString());
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
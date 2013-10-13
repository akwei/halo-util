package halo.akwei.util.tag;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

public class TimestampTag extends BodyTagSupport {

	private static final long serialVersionUID = 5795694184815848914L;

	private long value;

	private boolean second = false;

	private String pattern;

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public void setSecond(boolean second) {
		this.second = second;
	}

	public void setValue(long value) {
		this.value = value;
	}

	protected void adapter(JspWriter writer) throws IOException {
		if (this.value <= 0) {
			return;
		}
		long time;
		if (second) {
			time = this.value * 1000;
		}
		else {
			time = this.value;
		}
		if (pattern == null || pattern.trim().length() == 0) {
			return;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		Date date = new Date();
		date.setTime(time);
		writer.append(sdf.format(date));
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
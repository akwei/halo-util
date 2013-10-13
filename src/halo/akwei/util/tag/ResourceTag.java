package halo.akwei.util.tag;

import halo.akwei.util.SpringUtil;
import halo.akwei.util.servlet.ServletUtil;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.springframework.context.support.ResourceBundleMessageSource;

public class ResourceTag extends BodyTagSupport {

	private static final long serialVersionUID = 6199347080958919104L;

	private String beanId;

	private String key;

	public void setBeanId(String beanId) {
		this.beanId = beanId;
	}

	public void setKey(String key) {
		this.key = key;
	}

	protected void adapter(JspWriter writer) throws IOException {
		ResourceBundleMessageSource ms = (ResourceBundleMessageSource) SpringUtil.instance()
		        .getBean(this.beanId);
		HttpServletRequest request = (HttpServletRequest) this.pageContext.getRequest();
		Locale locale = (Locale) request.getAttribute(ServletUtil.LOCALE_ATTR_KEY);
		if (locale == null) {
			locale = Locale.CHINESE;
		}
		writer.append(ms.getMessage(key, null, key, locale));
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
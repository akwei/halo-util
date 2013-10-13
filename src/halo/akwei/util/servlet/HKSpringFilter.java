package halo.akwei.util.servlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class HKSpringFilter implements Filter {

	private String targetCharsetName = "UTF-8";

	private boolean needConverCharset = true;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		String s_needConverCharset = filterConfig.getInitParameter("needConverCharset");
		if (s_needConverCharset != null) {
			this.needConverCharset = Boolean.valueOf(s_needConverCharset);
		}
		String s_targetCharsetName = filterConfig.getInitParameter("targetCharsetName");
		if (s_targetCharsetName != null) {
			targetCharsetName = s_targetCharsetName;
		}
		HKHttpServletRequest.needConverCharset = this.needConverCharset;
		HKHttpServletRequest.targetCharsetName = this.targetCharsetName;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
	        throws IOException, ServletException {
		HKHttpServletRequest httpServletRequest = new HKHttpServletRequest(
		        (HttpServletRequest) request);
		request.setAttribute("ctx_path", httpServletRequest.getContextPath());
		chain.doFilter(httpServletRequest, response);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}
}

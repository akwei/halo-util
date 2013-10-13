package halo.akwei.util.servlet;

import halo.akwei.util.JsonUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

public class SpringWebUtil {

	public static void sendValue(HttpServletResponse response, String value)
	        throws IOException {
		response.getWriter().write(value);
	}

	public static ModelAndView getErrorResponse(Map<String, Object> map, String functionName,
	        int error_code, String errorMsg) {
		map.put("error_code", error_code);
		map.put("error_msg", errorMsg);
		return getResponse(map, functionName);
	}

	public static ModelAndView getResponse(Map<String, Object> map, String functionName) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/response");
		mav.addObject("response", JsonUtil.build(map));
		mav.addObject("functionName", functionName);
		return mav;
	}

	public static ModelAndView getErrorResponse(String functionName, int error_code, String errorMsg) {
		Map<String, Object> map = new HashMap<String, Object>();
		return getErrorResponse(map, functionName, error_code, errorMsg);
	}

	public static void setOpMessage(HttpServletRequest request, String msg) {
		ServletUtil.setSessionAttr(request, "hk_server_msg", msg);
	}

	public static void setOpMessageInRequest(HttpServletRequest request) {
		String msg = getOpMessage(request);
		request.setAttribute("hk_server_msg", msg);
	}

	public static String getOpMessage(HttpServletRequest request) {
		String name = "hk_server_msg";
		String msg = (String) ServletUtil.getSessionValue(request, name);
		ServletUtil.removeSessionAttr(request, name);
		return msg;
	}
}

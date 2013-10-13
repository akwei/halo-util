package halo.akwei.util;

import org.codehaus.jackson.map.ObjectMapper;

import java.util.List;
import java.util.Map;

public class JsonUtil {

	private JsonUtil() {
	}

	/**
	 * 将对象转化为json
	 * 
	 * @param obj List 或者 Map 类型
	 * @return
	 */
	public static String build(Object obj) {
		if (!(obj instanceof List) && !(obj instanceof Map)) {
			throw new RuntimeException("obj must be List or Map");
		}
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(obj);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 将json转化为 List 或者 Map
	 * 
	 * @param json
	 * @param cls Map.class or List.class
	 * @return
	 */
	public static <T> Object parse(String json, Class<T> cls) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(json, cls);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
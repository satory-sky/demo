package au.com.serenity.ms.jsonapi;

import com.google.common.base.CaseFormat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ErrorObject extends ConcurrentHashMap<String, Object> {

	final List<Map<String, Object>> errors = new ArrayList<>();

	public ErrorObject() {
		this.put("errors", errors);
	}

	public ErrorObject attribute(String status, String property, String title, String message) {
		Map<String, Object> error = new HashMap<>();
		error.put("status", status);
		error.put("detail", message);
		error.put("title", title);
		
		Map<String, Object> pointer = new HashMap<>();
		pointer.put("pointer", "data/attributes/" + CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, property));
		error.put("source", pointer);

		this.errors.add(error);
		return this;
	}
}
package au.com.serenity.ms.jsonapi;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(Include.NON_NULL)
public class JsonApiObject {

	private Map<String, String> meta;
	private Map<String, Object> data;
	private Map<String, Object> included;
	
	public JsonApiObject() {
		data = new HashMap<String, Object>();
	}
	public Map<String, String> getMeta() {
		return meta;
	}
	public void setMeta(Map<String, String> meta) {
		this.meta = meta;
	}
	public Map<String, Object> getData() {
		return data;
	}
	public void setData(Map<String, Object> data) {
		this.data = data;
	}
	public Map<String, Object> getIncluded() {
		return included;
	}
	
	public void setIncluded(Map<String, Object> included) {
		this.included = included;
	}
	
	public JsonApiObject setAttribute(String name, Object value) {
		@SuppressWarnings("unchecked")
		Map<String, Object> attributes = (Map<String, Object>) data.get("attributes");
		if(attributes == null) {
			attributes = new HashMap<String, Object>();
			data.put("attributes", attributes);
		}
		attributes.put(name, value);
		return this;
	}
}

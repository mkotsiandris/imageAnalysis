package image.models;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

public class ImageResult {

	public Map<String, String> properties = new HashMap<String, String>();
	private Map<String, Callable<Object>> callables = new HashMap<String, Callable<Object>>();

	public String getProperty(String key) {
		return properties.get(key);
	}

	public void setProperty(String key, String value) {
		properties.put(key, value);
	}
}

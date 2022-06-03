
public class pair {
	private String key;
	private Object value;
	
	public pair(String key, Object value ) {
		this.setKey(key);
		this.setValue(value);
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}
	public void increment() {
		value = Integer.toString(Integer.parseInt((String) value) +1);
	}
	
	
	
}

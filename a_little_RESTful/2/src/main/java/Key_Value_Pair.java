
public class Key_Value_Pair
{
	private User key;
	private int value;
	public Key_Value_Pair(User key, int value) {
		super();
		this.key = key;
		this.value = value;
	}
	public User getKey() {
		return key;
	}
	public void setKey(User key) {
		this.key = key;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public Key_Value_Pair() {
		super();
	}

}

package utils.global;

public class LongContainer {
	private long value = 0;
	
	public LongContainer(long value) {
		this.value = value;
	}
	public void add(long amount) {
		this.value += amount;
	}
	public long getValue() {
		return value;
	}
	public void setValue(long value) {
		this.value = value;
	}
}
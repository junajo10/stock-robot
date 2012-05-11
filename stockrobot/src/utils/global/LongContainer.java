package utils.global;

/**
 * A simple container class.
 * @author Daniel
 */
public class LongContainer {
	private long value = 0;
	
	/**
	 * Creates a longcontainer and sets its value
	 * @param value The value this container will have
	 */
	public LongContainer(long value) {
		this.value = value;
	}
	/**
	 * Adds amount to the container, amount can be negative.
	 * @param amount Amount to add
	 */
	public void add(long amount) {
		this.value += amount;
	}
	/**
	 * @return Returns the value from this container.
	 */
	public long getValue() {
		return value;
	}
	/**
	 * Overwrites this containers value
	 * @param value The value this container will have.
	 */
	public void setValue(long value) {
		this.value = value;
	}
}
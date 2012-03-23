package generic;

/**
 * @author Daniel
 *
 * @param <L> The left object type
 * @param <R> The right object type
 * 
 * Just a simple tuple class
 */
public class Pair<L, R> {
	private L left;
	private R right;
	
	public Pair(L left, R right) {
		this.left = left;
		this.right = right;
	}
	/**
	 * @return Returns the left side of the tuple
	 */
	public L getLeft() {
		return left;
	}
	/**
	 * @return Returns the right side of the tuple
	 */
	public R getRight() {
		return right;
	}
}

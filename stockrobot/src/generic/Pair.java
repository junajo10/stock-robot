package generic;

public class Pair<T,R> {
	private T left;
	private R right;
	
	public Pair(T left, R right) {
		this.left = left;
		this.right = right;
	}
	public T getLeft() {
		return left;
	}
	public R getRight() {
		return right;
	}
}

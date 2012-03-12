package generic;

import java.util.Iterator;

public class ArrayFixedSize<T> implements Iterable<T> {
	int maxSize;
	int size;
	int ptr;
	T[] array;
	
	@SuppressWarnings("unchecked")
	public ArrayFixedSize (int maxSize) {
		this.maxSize = maxSize;
		array = (T[])new Object[maxSize];
	}
	public boolean add(T t) {
		array[ptr%maxSize] = t;
		size++;
		ptr++;
		return true;
	}
	public T get(int id) {
		if (id > maxSize)
			throw new IllegalArgumentException("ADSFASDF");
		if (size < maxSize) {
			return array[id];
		}
		else {
			return array[(id+ptr)%maxSize];
		}
		
	}
	@Override
	public Iterator<T> iterator() {
		return new Iterator<T>() {
			int i = 0;
			@Override
			public boolean hasNext() {
				return i < maxSize && i < size;
			}

			@Override
			public T next() {
				i++;
				return get(i-1);
			}

			@Override
			public void remove() {
				
			}
		};
	}
}

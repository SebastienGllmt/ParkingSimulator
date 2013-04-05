package abebooks;
public abstract class AbstractHeap<T extends Comparable<T>> {

	/**
	 * Inserts a given value into the heap and returns whether or not it was inserted successfully.
	 * @param value - The value to add
	 * @return whether or not it was added successfully
	 */
	public abstract boolean insert(T value);

	/**
	 * @return the top element and removes it from the heap
	 */
	public abstract T delete();

	/**
	 * @return but does not remove the top element
	 */
	public abstract T peek();

	/**
	 * @return whether or not the heap is empty
	 */
	public abstract boolean isEmpty();
}

package abebooks;
import java.util.NoSuchElementException;

enum HeapType {
	MIN(1), MAX(-1);

	private int type;

	/**
	 * Enums for heap type for easy creation of min/max heap
	 * @param type
	 */
	private HeapType(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}
}

public class Heap<T extends Comparable<T>> extends AbstractHeap<T> {

	private T[] elements; //array representing the heap
	private int endPointer; //pointer to the next empty slot in the heap
	private final int TYPE; //Whether the heap is a min/max heap
	private boolean resizable; //whether or not the heap should resize when it is out of room

	/**
	 * Creates either a min or max heap of a given size
	 * @param TYPE - Whether the heap is a min/max heap (see HeapType enum)
	 * @param size - The starting size of the heap
	 * @param resizable - Whether or not the heap should double in size when it runs out of room
	 */
	public Heap(HeapType TYPE, int size, boolean resizable) {
		this.TYPE = TYPE.getType();
		this.elements = (T[]) new Comparable[size];
		this.endPointer = 0;
	}

	/**
	 * Insert an element into the heap
	 * Note: Will either resize the array or return false if no room is left
	 * @param value - The value to insert
	 * @return whether or not the insert was successful
	 */
	public boolean insert(T value) {
		if (!isFull()) {
			elements[endPointer] = value;
			bubbleUp(); //sort based on new element
			endPointer++;
			return true;
		} else if (resizable){
			T[] elementsCopy = (T[]) new Comparable[elements.length<<1]; //double heap allocation size
			System.arraycopy(elements, 0, elementsCopy, 0, elements.length);
			elements = elementsCopy;
			return insert(value); //retry inserting value
		} else{
			return false;
		}
	}
	
	/**
	 * Deletes and returns the top element in the heap
	 * @return the top element
	 * @throws NoSuchElementException if the 
	 */
	public T delete() {
		if (isEmpty()) {
			throw new NoSuchElementException("Can not delete off an empty heap");
		} else {
			T value = elements[0];
			endPointer--;
			elements[0] = elements[endPointer];
			elements[endPointer] = null;
			bubbleDown(); //Reheapifies to maintain the heap sorted
			return value;
		}
	}

	/**
	 * Sorts the heap upon new entry insertion
	 */
	private void bubbleUp() {
		if (endPointer == 0) { //stop if heap only contains one element
			return;
		}
		int position = endPointer;
		int parentIndex = (position - 1) >> 1;

		while (parentIndex >= 0 && elements[parentIndex].compareTo(elements[position]) == TYPE) { //while parent exists and needs to be swapped
			swap(parentIndex, position);
			position = parentIndex;
			parentIndex = (parentIndex - 1) >> 1;
		}
	}

	private void bubbleDown() {
		if (isEmpty()) {
			return;
		} else {
			int position = 0;
			while (position < endPointer) {
				int left = (position << 1) + 1;
				int right = (position << 1) + 2;

				int child = left; //initially assume left child is best swap
				
				if (right < endPointer && elements[left].compareTo(elements[right]) == TYPE) { // if right is a valid swap and better swap than left
					child = right;
				}
				if (child < endPointer && elements[position].compareTo(elements[child]) == TYPE) { // whether or not a swap should be made
					swap(child, position);
					position = child;
				} else {
					break; //heap is now sorted
				}
			}
		}
	}

	/**
	 * Swaps two indexes in the heap
	 * Note: swap(a, b) is equivalent to swap(b, a)
	 * @param index1 - The index of the first element
	 * @param index2 - The index of the second element
	 */
	private void swap(int index1, int index2) {
		T temp = elements[index1];
		elements[index1] = elements[index2];
		elements[index2] = temp;
	}

	/**
	 * Returns but does not remove the top element in the heap
	 */
	public T peek() {
		return elements[0];
	}

	/**
	 * @return whether or not the heap is empty
	 */
	public boolean isEmpty() {
		return size()==0;
	}
	
	/**
	 * @return the max number of elements the heap can currently hold without resized
	 */
	public int maxSize(){
		return elements.length;
	}
	
	/**
	 * @return the current number of elements in the heap
	 */
	public int size(){
		return endPointer;
	}
	
	/**
	 * @return whether or not the heap is currently full
	 */
	public boolean isFull(){
		return size()==maxSize();
	}

	/**
	 * Returns a very simple string representation of the underlying heap
	 */
	@Override
	public String toString() {
		String s = "";
		for (int i = 0; i < endPointer; i++) {
			if (i == endPointer - 1) {
				s += elements[i].toString();
			} else {
				s += elements[i].toString() + " ";
			}
		}
		return s;
	}
}

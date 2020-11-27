package io.cubyz.util;

import java.lang.reflect.Array;
import java.util.Comparator;

/** 
 * A faster list implementation.<br>
 * Velocity is reached by sacrificing bound checks, by keeping some additional memory<br>
 * (When removing elements they are not necessarily cleared from the array) and through direct data access.
**/

public class IntFastList {

	public int[] array;
	public int size = 0;

	@SuppressWarnings("unchecked")
	public IntFastList(int initialCapacity) {
		array = (int[])new int[ initialCapacity];
	}

	public IntFastList() {
		this(10);
	}

	@SuppressWarnings("unchecked")
	public void increaseSize(int increment) {
		int[] newArray = (int[])new int[ array.length + increment];
		System.arraycopy(array, 0, newArray, 0, array.length);
		array = newArray;
	}

	@SuppressWarnings("unchecked")
	public void trimToSize() {
		int[] newArray = (int[])new int[ size];
		System.arraycopy(array, 0, newArray, 0, size);
		array = newArray;
	}

	@SuppressWarnings("unchecked")
	public int[] toArray() {
		int[] newArray = (int[])new int[ size];
		System.arraycopy(array, 0, newArray, 0, size);
		return newArray;
	}
	
	public void set(int index, int obj) {
		array[index] = obj;
	}
	
	public void add(int obj) {
		if (size == array.length)
			increaseSize(array.length/2 + 1);
		array[size] = obj;
		size++;
	}
	
	public void add(int[] obj) {
		if (size + obj.length == array.length)
			increaseSize(Math.max(array.length*3/2, array.length + obj.length));
		for(int o : obj) {
			array[size] = o;
			size++;
		}
	}
	
	public void removeInd(int index) {
		System.arraycopy(array, index+1, array, index, array.length-index-1);
		size--;
	}
	
	public void removeEl(int t) {
		for(int i = size-1; i >= 0; i--) {
			if(array[i] == t)
				removeInd(i); // Don't break here in case of multiple occurrence.
		}
	}
	
	public boolean contains(int t) {
		for(int i = size-1; i >= 0; i--) {
			if(array[i] == t)
				return true;
		}
		return false;
	}
	
	public boolean isEmpty() {
		return size == 0;
	}
	
	/**
	 * Sort using Quick Sort algorithm.
	 * @param comp comparator
	 */
	public void sort(Comparator comp) {
		if (size > 1) {
			sort(comp, 0, size-1);
		}
	}
	
	/**
	 * Sort using Quick Sort algorithm.
	 * @param comp comparator
	 * @param l index of the left-most element in the to sorting area.
	 * @param r index of the right-most element in the to sorting area.
	 */
	public void sort(Comparator comp, int l, int r) {
		if(l >= r) return;
		int i = l, j = r;
		
		int x = array[(l+r)/2];
		while (true) {
			while (comp.compare( array[i], x) < 0) {
				i++;
			}
			while (comp.compare(x,  array[j]) < 0) {
				j--;
			}
			if (i <= j) {
				int temp = array[i];
				array[i] = array[j];
				array[j] = temp;
				i++;
				j--;
			}
			if (i > j) {
				break;
			}
		}
		if (l < j) {
			sort(comp, l, j);
		}
		if (i < r) {
			sort(comp, i, r);
		}
	}
	
	/**
	 * Sets the size to 0, meaning {@link RenderList#trimToSize()} should be called in order to free memory.
	 */
	public void clear() {
		size = 0;
	}

}

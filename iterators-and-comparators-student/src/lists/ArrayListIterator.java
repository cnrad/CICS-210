/*
 * Copyright 2023 Marc Liberatore.
 */

package lists;

import java.util.Iterator;
import java.util.NoSuchElementException;

class ArrayListIterator<E> implements Iterator<E> {
    private int index;
    private List<E> list;

    // Implement constructor
    public ArrayListIterator(List<E> list) {
        this.list = list;
        this.index = 0;
    }

    @Override
    public boolean hasNext() {
        // If it throws an IndexOutOfBounds error, there is no next element
        try {
            list.get(index);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public E next() {
        // End of list
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        // Get element at index and THEN increment it as well
        return list.get(index++);
    }
}

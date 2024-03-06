/*
 * Copyright 2023 Marc Liberatore.
 */

package lists;

import java.util.Iterator;

public class ArrayList<E> implements List<E> {
    // Note: do not declare any additional instance variables
    E[] array;
    int size;

    public ArrayList() {
        size = 0;
        array = (E[]) new Object[10];
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        for (int i = 0; i < size; i++) {
            result = prime * result + array[i].hashCode();
        }
        result = prime * result + size;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof List))
            return false;
        List other = (List) obj;
        if (this.size != other.size())
            return false;

        for (int i = 0; i < this.size; i++) {
            if (!this.array[i].equals(other.get(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public E get(int index) throws IndexOutOfBoundsException {
        if (index >= this.size || index < 0) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }

        return this.array[index];
    }

    @Override
    public void add(E e) {
        // Increase array size if necessary, Java convention is to increase by 1.5
        if (this.size == this.array.length) {
            E[] newArray = (E[]) new Object[(int) (array.length * 1.5)];
            for (int i = 0; i < this.array.length; i++) {
                newArray[i] = this.array[i];
            }
            this.array = newArray;
        }

        this.array[this.size] = e;
        this.size++;
    }

    @Override
    public void add(int index, E e) throws IndexOutOfBoundsException {
        if (index > this.size || index < 0) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }

        // Increase array size if necessary, Java convention is to increase by 1.5
        if (this.size == this.array.length) {
            E[] newArray = (E[]) new Object[(int) (array.length * 1.5)];
            for (int i = 0; i < this.array.length; i++) {
                newArray[i] = this.array[i];
            }
            this.array = newArray;
        }

        // Move the items right by one, creating space for the item to be added
        for (int i = this.size; i > index; i--) {
            this.array[i] = this.array[i - 1];
        }

        this.array[index] = e;
        this.size++;
    }

    @Override
    public E remove(int index) throws IndexOutOfBoundsException {
        if (index >= this.size || index < 0) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }

        E removedItem = this.array[index];

        // Move the items left by one, overwriting the removed item
        for (int i = index; i < this.size; i++) {
            this.array[i] = this.array[i + 1];
        }

        this.size--;
        return removedItem;
    }

    @Override
    public E set(int index, E e) throws IndexOutOfBoundsException {
        if (index >= this.size || index < 0) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }

        E oldData = this.array[index];
        this.array[index] = e;
        return oldData;
    }

    @Override
    public int indexOf(E e) {
        for (int i = 0; i < this.size; i++) {
            if (this.array[i].equals(e)) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public Iterator<E> iterator() {
        Iterator<E> iterator = new ArrayListIterator<E>(this);

        return iterator;
    }
}
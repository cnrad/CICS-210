/*
 * Copyright 2023 Marc Liberatore.
 */

package lists;

public class LinkedList<E> implements List<E> {
    // Note: do not declare any additional instance variables
    Node<E> head;
    Node<E> tail;
    int size;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        Node<E> n = head;
        while (n != null) {
            result = prime * result + head.data.hashCode();
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
        if (size != other.size())
            return false;

        Node<E> n = head;

        for (int i = 0; i < size; i++) {
            if (!n.data.equals(other.get(i))) {
                return false;
            }
            n = n.next;
        }

        return true;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public E get(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= this.size || this.head == null)
            throw new IndexOutOfBoundsException("Index out of bounds");

        Node<E> n = this.head;

        for (int i = 0; i < index; i++) {
            // If the linked list doesn't go to the specified index
            if (n.next == null)
                throw new IndexOutOfBoundsException("Index out of bounds");
            n = n.next;
        }

        return n.data;
    }

    @Override
    public void add(E e) {
        Node<E> n = new Node<E>(e);

        if (this.head == null) {
            this.head = n;
            this.tail = n;
        } else {
            this.tail.next = n;
            this.tail = n;
        }

        this.size++;
    }

    @Override
    public void add(int index, E e) throws IndexOutOfBoundsException {
        if (index < 0 || index > this.size)
            throw new IndexOutOfBoundsException("Index out of bounds");

        Node<E> newNode = new Node<E>(e);

        // If list is empty
        if (this.head == null) {
            this.head = newNode;
            this.tail = newNode;
        }

        Node<E> n = this.head;

        // Handle case at beginning of list
        if (index == 0) {
            newNode.next = this.head;
            this.head = newNode;
        }

        // Handle case at end of list
        if (index == this.size)
            this.tail = newNode;

        // Handle every other case
        // We use index - 1 because we need to access the previous one in order to point
        // 'next' to the added one
        for (int i = 0; i < index - 1; i++) {
            // If the linked list doesn't go to the specified index
            if (n.next == null)
                throw new IndexOutOfBoundsException("Index out of bounds");

            n = n.next;
        }

        // Insert the node
        Node<E> temp = n.next;
        n.next = newNode;
        newNode.next = temp;

        this.size++;
    }

    @Override
    public E remove(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= this.size || this.head == null)
            throw new IndexOutOfBoundsException("Index out of bounds");

        Node<E> n = this.head;

        if (index == 0) {
            this.head = this.head.next;
            this.size--;
            return n.data;
        }

        // Handle every other case
        for (int i = 0; i < index; i++) {
            // If the linked list doesn't go to the specified index
            if (n.next == null)
                throw new IndexOutOfBoundsException("Index out of bounds");

            n = n.next;
        }

        Node<E> removedNode = n;
        n = n.next;

        this.size--;
        return removedNode.data;
    }

    @Override
    public E set(int index, E e) throws IndexOutOfBoundsException {
        if (index < 0 || index >= this.size || this.head == null)
            throw new IndexOutOfBoundsException("Index out of bounds");

        // Handle case at beginning of list
        if (index == 0) {
            Node<E> oldNode = this.head;
            this.head.data = e;
            return oldNode.data;
        }

        // Handle case at end of list
        if (index == this.size) {
            Node<E> oldNode = this.tail;
            this.tail.data = e;
            return oldNode.data;
        }

        Node<E> n = this.head;
        for (int i = 0; i < index; i++) {
            // If the linked list doesn't go to the specified index
            if (n.next == null)
                throw new IndexOutOfBoundsException("Index out of bounds");

            n = n.next;
        }

        Node<E> oldNode = n;
        n.data = e;

        return oldNode.data;
    }

    @Override
    public int indexOf(E e) {
        Node<E> n = this.head;

        for (int i = 0; i < this.size; i++) {
            if (n.data.equals(e)) {
                return i;
            }
            // If the linked list doesn't go to the specified index
            if (n.next == null)
                return -1;

            n = n.next;
        }

        return -1;
    }
}

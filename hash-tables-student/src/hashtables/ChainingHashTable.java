package hashtables;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * An implementation of HashTable.
 * 
 * This implementation uses chaining to resolve collisions. Chaining means
 * the underlying array stores references to growable structures (like
 * LinkedLists) that we expect to remain small in size. When there is a
 * collision, the element is added to the end of the growable structure. It
 * must search the entire growable structure whenever checking membership
 * or removing elements.
 * 
 * This implementation maintains a capacity equal to 2^n - 1 for some positive
 * integer n. When the load factor exceeds 0.75, the next add() triggers a
 * resize by incrementing n (by one). For example, when n=3, then capacity=7.
 * When size=6, then load factor ~=0.86. The addition of the seventh item would
 * trigger a resize, increasing the capacity of the array to 15.
 */
public class ChainingHashTable<E> implements HashTable<E> {

    // Type safety doesn't seem to like generic arrays!
    LinkedList<E>[] table;

    /**
     * Instantiate a new hash table. The initial capacity should be 7.
     */
    public ChainingHashTable() {
        table = new LinkedList[7];
    }

    /**
     * Instantiate a new hash table. The initial capacity should be
     * at least sufficient to hold n elements, but must be one less
     * than a power of two.
     */
    public ChainingHashTable(int n) {
        int space = (int) Math.pow(2, (int) Math.ceil(Math.log(n + 1) / Math.log(2)));
        table = new LinkedList[space - 1];
    }

    @Override
    public int capacity() {
        return table.length;
    }

    @Override
    public int size() {
        int sum = 0;

        for (int i = 0; i < table.length; i++) {
            if (table[i] != null) {
                sum += table[i].size();
            }
        }

        return sum;
    }

    @Override
    public double loadFactor() {
        return (double) this.size() / this.capacity();
    }

    @Override
    public boolean add(E e) {
        // Enlarge the array
        if (this.loadFactor() > 0.75) {
            LinkedList<E>[] oldTable = table;
            table = new LinkedList[oldTable.length * 2 + 1];

            for (int i = 0; i < oldTable.length; i++) {
                if (oldTable[i] != null) {
                    for (E element : oldTable[i]) {
                        this.add(element);
                    }
                }
            }
        }

        int index = e.hashCode() % table.length;

        if (table[index] == null) {
            // Newly inserted
            table[index] = new LinkedList<E>();
            table[index].add(e);
            return true;
        } else {
            // If the LinkedList has the element, overwrite it, return false
            if (table[index].contains(e)) {
                table[index].set(table[index].indexOf(e), e);
                return false;
            } else {
                // Element is still newly inserted, return true
                table[index].add(e);
                return true;
            }
        }
    }

    @Override
    public boolean remove(E e) {
        int index = e.hashCode() % table.length;

        if (table[index] == null)
            return false;

        return table[index].remove(e);
    }

    @Override
    public boolean contains(E e) {
        for (int i = 0; i < table.length; i++) {
            if (table[i] != null && table[i].contains(e)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public E get(E e) {
        if (!this.contains(e))
            return null;

        int index = e.hashCode() % table.length;
        return table[index].get(table[index].indexOf(e));
    }

    @Override
    public Iterator<E> iterator() {
        // Return a new Iterator instance, defined by us
        return new Iterator<E>() {
            int currentIndex = 0;
            // Current pos keeps track of which LinkedList we are in, and is an .iterator()
            // on the List
            Iterator<E> currentPos = null;

            @Override
            public boolean hasNext() {
                if (currentPos != null && currentPos.hasNext()) {
                    return true;
                }

                // Step through each list, and each list entry, one by one
                for (int i = currentIndex; i < table.length; i++) {
                    // If the table is not null, set the new currentPos iterator
                    if (table[i] != null) {
                        currentPos = table[i].iterator();
                        currentIndex = i + 1;
                        return true;
                    }
                }

                return false;
            }

            @Override
            public E next() {
                // We can just call this because it's an iterator
                return currentPos.next();
            }
        };
    }
}
/*
 * Copyright 2023 Marc Liberatore.
 */
package hashmaps;

import java.util.HashSet;
import java.util.Set;

import javax.swing.text.html.HTMLDocument.Iterator;

import hashtables.ChainingHashTable;

/**
 * An implementation of a SimpleMap, built using the ChainingHashTable and
 * SimpleMapEntry classes. This class should behave similarly to the built-in
 * java.util.HashMap, though it is much simpler!
 */
public class SimpleHashMap<K, V> implements SimpleMap<K, V> {
    ChainingHashTable<SimpleMapEntry<K, V>> table;

    public SimpleHashMap() {
        table = new ChainingHashTable<SimpleMapEntry<K, V>>();
    }

    @Override
    public int size() {
        return table.size();
    }

    @Override
    public void put(K k, V v) {
        table.add(new SimpleMapEntry<K, V>(k, v));
    }

    @Override
    public V get(K k) {
        SimpleMapEntry<K, V> entry = table.get(new SimpleMapEntry<K, V>(k, null));

        return entry != null ? entry.v : null;
    }

    @Override
    public V getOrDefault(K k, V defaultValue) {
        V v = this.get(k);

        return v != null ? v : defaultValue;
    }

    @Override
    public V remove(K k) {
        V val = this.get(k);

        table.remove(new SimpleMapEntry<K, V>(k, null));

        return val;
    }

    @Override
    public Set<K> keys() {
        Set<K> keys = new HashSet<K>();
        // Loop through the objects because we have an .iterator() method on it
        for (SimpleMapEntry<K, V> entry : table) {
            keys.add(entry.k);
        }

        return keys;
    }
}
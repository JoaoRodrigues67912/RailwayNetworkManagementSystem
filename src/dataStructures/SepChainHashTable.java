package dataStructures;
import dataStructures.Exceptions.*;
/**
 * Separate Chaining Hash table implementation
 * @author AED  Team
 * @author João Rodrigues (67912) jsl.rodrigues@campus.fct.unl.pt
 * @author João Pedro Fernandes (68180) jpcb.fernandes@campus.fct.unl.pt
 * @version 1.0
 * @param <K> Generic Key, must extend comparable
 * @param <V> Generic Value
 */

public class SepChainHashTable<K extends Comparable<K>, V>
        extends HashTable<K,V>
{
    /**
     * Serial Version UID of the Class.
     */
    static final long serialVersionUID = 0L;

    /**
     * The array of dictionaries.
     */
    protected Dictionary<K,V>[] table;


    /**
     * Constructor of an empty separate chaining hash table,
     * with the specified initial capacity.
     * Each position of the array is initialized to a new ordered list
     * maxSize is initialized to the capacity.
     * @param capacity defines the table capacity.
     */
    @SuppressWarnings("unchecked")
    public SepChainHashTable( int capacity ) {
        int arraySize = HashTable.nextPrime((int) (1.1 * capacity));
        table = (Dictionary<K,V>[]) new Dictionary[arraySize];
        for ( int i = 0; i < arraySize; i++ ){
            table[i] = new OrderedDoubleList<K,V>();
        }
        maxSize = capacity;
        currentSize = 0;
    }


    public SepChainHashTable( ) {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Returns the hash value of the specified key.
     * @param key to be encoded
     * @return hash value of the specified key
     */
    protected int hash( K key ) {
        return Math.abs( key.toString().toLowerCase().hashCode() ) % table.length;
    }

    @Override
    public V find( K key ) {
        return table[ this.hash(key) ].find(key);
    }

    @Override
    public V insert( K key, V value ) {
        if ( this.isFull() ){
            this.rehash();
        }
        V oldValue = table[this.hash(key)].insert(key, value);
        if(oldValue == null){
            currentSize++;
        }
        return oldValue;
    }

    @Override
    public V remove( K key ) {
        V removedValue = table[this.hash(key)].remove(key);
        if(removedValue != null){
            currentSize--;
        }
        return removedValue;
    }

    @Override
    public Iterator<Entry<K,V>> iterator( ) {
        return new SepChainHashTableIterator();
    }

    @SuppressWarnings("unchecked")
    protected void rehash() {
        Dictionary<K, V>[] oldTable = table;
        int newCapacity = HashTable.nextPrime(2 * table.length);
        table = (Dictionary<K, V>[]) new Dictionary[newCapacity];
        for (int i = 0; i < newCapacity; i++) {
            table[i] = new OrderedDoubleList<>();
        }
        for (Dictionary<K, V> bucket : oldTable) {
            if (bucket != null) {
                Iterator<Entry<K, V>> iterator = bucket.iterator();
                while (iterator.hasNext()) {
                    Entry<K, V> entry = iterator.next();
                    this.insert(entry.getKey(), entry.getValue());
                }
            }
        }
    }

    private class SepChainHashTableIterator implements Iterator<Entry<K, V>> {
        static final long serialVersionUID = 0L;
        private int currentIndex = 0;
        private Iterator<Entry<K, V>> currentBucketIterator = null;
        public SepChainHashTableIterator() {
            advanceToNextBucket();
        }
        private void advanceToNextBucket() {
            while (currentIndex < table.length) {
                if (table[currentIndex] != null && !table[currentIndex].isEmpty()) {
                    currentBucketIterator = table[currentIndex].iterator();
                    return;
                }
                currentIndex++;
            }
            currentBucketIterator = null;
        }

        @Override
        public boolean hasNext() {
            return currentBucketIterator != null && currentBucketIterator.hasNext();
        }

        @Override
        public Entry<K, V> next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Entry<K, V> nextEntry = currentBucketIterator.next();
            if (!currentBucketIterator.hasNext()) {
                currentIndex++;
                advanceToNextBucket();
            }
            return nextEntry;
        }
        public void rewind() {
            currentIndex = 0;
            currentBucketIterator = null;
            advanceToNextBucket();
        }
    }
}

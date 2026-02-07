package dataStructures;

import dataStructures.Exceptions.*;
/**
 * @author João Rodrigues (67912) jsl.rodrigues@campus.fct.unl.pt
 * @author João Pedro Fernandes (68180) jpcb.fernandes@campus.fct.unl.pt
 * @param <K> Generic key
 * @param <V> Generic Value
 */
public class DictionaryClass<K,V> implements Dictionary<K,V> {

    static final long serialVersionUID = 0L;
    private static final int INITIAL_CAPACITY = 10;
    protected Entry<K, V>[] entries;
    protected int size;

    @SuppressWarnings("unchecked")
    public DictionaryClass(){
        entries = new Entry[INITIAL_CAPACITY];
        size = 0;
    }
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public V find(K key) {
        for(int i = 0; i < size; i++){
            if(entries[i].getKey().toString().equalsIgnoreCase(key.toString())){
                return entries[i].getValue();
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private void grow(){
        Entry<K,V>[] newEntries = new Entry[entries.length * 2];
        for(int i = 0; i < entries.length; i++){
            newEntries[i] = entries[i];
        }
        entries = newEntries;
    }

    @Override
    public V insert(K key, V value) {
       /** for(int i = 0; i < size; i++){
            if(entries[i].getKey().equals(key)){
                V oldValue = entries[i].getValue();
                entries[i].getValue() = value;
                return oldValue;
            }
        }*/
        if(size == entries.length){
            grow();
        }
        entries[size++] = new EntryClass<K, V>(key, value);
        return null;
    }

    @Override
    public V remove(K key) {
        for(int i = 0; i < size;i++){
            if(entries[i].getKey().toString().equalsIgnoreCase(key.toString())){
                V oldValue = entries[i].getValue();
                for(int j = i; j < size - 1;j++){
                    entries[j] = entries[j + 1];
                }
                entries[--size] = null;
                return oldValue;
            }
        }
        return null;
    }

    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new DictionaryIteratorClass();
    }
    private class DictionaryIteratorClass implements Iterator<Entry<K,V>> {
        static final long serialVersionUID = 0L;
        private int currentIndex = 0;
        @Override
        public boolean hasNext() {
            return currentIndex < size;
        }

        @Override
        public Entry<K, V> next() throws NoSuchElementException {
            if(!hasNext())
                throw new NoSuchElementException();
            return entries[currentIndex++];
        }

        @Override
        public void rewind() {
            currentIndex = 0;
        }
    }
}

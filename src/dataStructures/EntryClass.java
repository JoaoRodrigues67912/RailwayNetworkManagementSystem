package dataStructures;
/**
* @author João Rodrigues (67912) jsl.rodrigues@campus.fct.unl.pt
 * @author João Pedro Fernandes (68180) jpcb.fernandes@campus.fct.unl.pt
 * **/
public class EntryClass<K, V> implements Entry<K,V> {

    static final long serialVersionUID = 0L;
    private K key;
    private V value;

    public EntryClass(K key, V value){
        this.key = key;
        this.value = value;
    }

    @Override
    public K getKey() {
        return key;
    }

    @Override
    public V getValue() {
        return value;
    }
}

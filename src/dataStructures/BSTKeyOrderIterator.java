package dataStructures;
import dataStructures.Exceptions.*;
/**
* @author João Rodrigues (67912) jsl.rodrigues@campus.fct.unl.pt
 * @author João Pedro Fernandes (68180) jpcb.fernandes@campus.fct.unl.pt
**/
class BSTKeyOrderIterator<K,V> implements Iterator<Entry<K,V>> {

    static final long serialVersionUID = 0L;


    protected BSTNode<Entry<K,V>> root;

    protected Stack<BSTNode<Entry<K,V>>> p;


    BSTKeyOrderIterator(BSTNode<Entry<K,V>> root){
        this.root=root;
        rewind();
    }

    private void pushPathToMinimum(BSTNode<Entry<K,V>> node) {
        while (node != null) {
            p.push(node);
            node = node.getLeft();
        }
    }

    //O(1) para todos os casos
    public boolean hasNext(){
        return !p.isEmpty();
    }


    public Entry<K,V> next( ) throws NoSuchElementException {
        if (!hasNext()) throw new NoSuchElementException();
        else {
            BSTNode<Entry<K, V>> current = p.pop();
            if (current.getRight() != null) {
                pushPathToMinimum(current.getRight());
            }
            return current.getElement();
        }
    }

    public void rewind( ){
        p = new StackInList<BSTNode<Entry<K,V>>>();
        pushPathToMinimum(root);
    }
}


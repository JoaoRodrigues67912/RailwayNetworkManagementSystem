package dataStructures;


/**
 * AVL tree implementation
 *
 * @author AED team
 * @author João Rodrigues (67912) jsl.rodrigues@campus.fct.unl.pt
 * @author João Pedro Fernandes (68180) jpcb.fernandes@campus.fct.unl.pt
 * @version 1.0
 *
 * @param <K> Generic type Key, must extend comparable
 * @param <V> Generic type Value
 */
public class AVLTree<K extends Comparable<K>, V>
        extends AdvancedBSTree<K,V> implements OrderedDictionary<K,V> {

    static final long serialVersionUID = 0L;

    AVLTree(AVLNode<Entry<K,V>> node) {
        root = node;
    }

    public AVLTree() {
        this(null);
    }

    /**
     * Rebalance method called by insert and remove.  Traverses the path from
     * zPos to the root. For each node encountered, we recompute its height
     * and perform a trinode restructuring if it's unbalanced.
     * the rebalance is completed with O(log n) running time
     */
    void rebalance(AVLNode<Entry<K,V>> zPos) {
        // Improve if possible...
        while (zPos!=null) {  // traverse up the tree towards the root
            zPos.setHeight();
            if (!zPos.isBalanced()) {
                // perform a trinode restructuring at zPos's tallest grandchild
                //If yPos (zPos.tallerChild()) denote the child of zPos with greater height.
                //Finally, let xPos be the child of yPos with greater height
                AVLNode<Entry<K,V>> xPos = zPos.tallerChild().tallerChild();

                zPos = (AVLNode<Entry<K, V>>) restructure(xPos); // tri-node restructure (from parent class)
                ((AVLNode<Entry<K, V>>) zPos.getLeft()).setHeight();  // recompute heights
                ((AVLNode<Entry<K, V>>) zPos.getRight()).setHeight();
                zPos.setHeight();
            }
            zPos = (AVLNode<Entry<K, V>>) zPos.getParent();
        }
    }

    @Override
    public V insert( K key, V value ) {
        AVLNode<Entry<K, V>> node = (AVLNode<Entry<K, V>>) findNode(key);
        if (node == null || node.getElement().getKey().compareTo(key) != 0) {
            AVLNode<Entry<K, V>> newNode = new AVLNode<>(new EntryClass<>(key, value));
            linkSubtreeInsert(newNode, node);
            currentSize++;
            if(node != null) {
                rebalance(newNode);
            }
            return null;
        } else {
            V oldValue = node.getElement().getValue();
            node.setElement(new EntryClass<>(key, value));
            return oldValue;
        }
    }

    @Override
    public V remove( K key ) {
        BSTNode<Entry<K,V>> node = this.findNode(key);
        if ( node == null || node.getElement().getKey().compareTo(key) != 0 ){
            return null;}
        else
        {
            V oldValue = node.getElement().getValue();
            if ( node.getLeft() == null )
                // The left subtree is empty.
                this.linkSubtreeRemove(node.getRight(), node.getParent(),node);
            else if ( node.getRight() == null )
                // The right subtree is empty.
                this.linkSubtreeRemove(node.getLeft(), node.getParent(),node);
            else
            {
                // Node has 2 children. Replace the node's entry with
                // the 'minEntry' of the right subtree.
                BSTNode<Entry<K,V>> minNode = this.minNode(node.getRight());
                node.setElement( minNode.getElement() );
                // Remove the 'minEntry' of the right subtree.
                this.linkSubtreeRemove(minNode.getRight(), minNode.getParent(),minNode);
            }
            currentSize--;
            if(node.getParent() != null)
                rebalance((AVLNode<Entry<K, V>>) node.getParent());
            return oldValue;
        }
    }
}


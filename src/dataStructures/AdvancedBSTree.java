package dataStructures;

/**
 * Advanced BSTree Data Type implementation
 * @author AED team
 * @author João Rodrigues (67912) jsl.rodrigues@campus.fct.unl.pt
 * @author João Pedro Fernandes (68180) jpcb.fernandes@campus.fct.unl.pt
 *
 * @version 1.0
 * @param <K> Generic type Key, must extend comparable
 * @param <V> Generic type Value
 */
public abstract class AdvancedBSTree<K extends Comparable<K>, V> extends BinarySearchTree<K,V> {

    static final long serialVersionUID = 0L;

    /**
     * Performs a single left rotation rooted at Y node.
     * Node X was a  right  child  of Y before the  rotation,
     * then Y becomes the left child of X after the rotation.
     * @param Y - root of the rotation
     * @pre: Y has a right child
     */
    protected void rotateLeft( BSTNode<Entry<K,V>> Y) {
        BSTNode<Entry<K, V>> X = Y.getRight();
        Y.setRight(X.getLeft());
        if (X.getLeft() != null) {
            X.getLeft().setParent(Y);
        }
        if (Y.getParent() == null) {
            root = X;
            X.setParent(null);
        } else if (Y == Y.getParent().getLeft()) {
            Y.getParent().setLeft(X);
            X.setParent(Y.getParent());
        } else {
            Y.getParent().setRight(X);
            X.setParent(Y.getParent());
        }
        X.setLeft(Y);
        Y.setParent(X);
    }


    /**
     * Performs a single right rotation rooted at Y node.
     * Node X was a  left  child  of Y before the  rotation,
     * then Y becomes the right child of X after the rotation.
     * @param Y - root of the rotation
     * @pre: Y has a left child
     */
    protected void rotateRight( BSTNode<Entry<K,V>> Y) {
        BSTNode<Entry<K,V>> X = Y.getLeft();
        Y.setLeft(X.getRight());
        if(X.getRight() != null){
           X.getRight().setParent(Y);
        }
        if (Y.getParent() == null) {
            root = X;
            X.setParent(null);
        } else if (Y == Y.getParent().getLeft()) {
            Y.getParent().setLeft(X);
            X.setParent(Y.getParent());
        } else {
            Y.getParent().setRight(X);
            X.setParent(Y.getParent());
        }
        X.setRight(Y);
        Y.setParent(X);
    }

    /**
     * Performs a tri-node restructuring (a single or double rotation rooted at X node).
     * Assumes the nodes are in one of following configurations:
     *
     * @param X - root of the rotation
     * <pre>
     *          z=c       z=c        z=a         z=a
     *         /  \      /  \       /  \        /  \
     *       y=b  t4   y=a  t4    t1  y=c     t1  y=b
     *      /  \      /  \           /  \         /  \
     *    x=a  t3    t1 x=b        x=b  t4       t2 x=c
     *   /  \          /  \       /  \             /  \
     *  t1  t2        t2  t3     t2  t3           t3  t4
     * </pre>
     * @return the new root of the restructured subtree
     */
    protected BSTNode<Entry<K,V>> restructure(BSTNode<Entry<K,V>> X) {
        BSTNode<Entry<K,V>> Y = X.getParent();
        BSTNode<Entry<K,V>> Z = Y.getParent();
        BSTNode<Entry<K, V>> newRoot;

        if (X == Y.getLeft() && Y == Z.getLeft()) {
            rotateRight(Z);
            newRoot = Y;
        } else if (X == Y.getRight() && Y == Z.getRight()) {
            rotateLeft(Z);
            newRoot = Y;
        } else if (X == Y.getLeft() && Y == Z.getRight()) {
            rotateRight(Y);
            rotateLeft(Z);
            newRoot = X;
        } else {
            rotateLeft(Y);
            rotateRight(Z);
            newRoot = X;
        }
        return newRoot;
    }

}

package dataStructures;
import dataStructures.Exceptions.*;
import java.io.Serializable;
/**
* @author João Rodrigues (67912) jsl.rodrigues@campus.fct.unl.pt
 * @author João Pedro Fernandes (68180) jpcb.fernandes@campus.fct.unl.pt
**/

public class DoubleLinkedList<E> implements List<E> {

    /**
     * Serial Version of the Class
     */
    static final long serialVersionUID = 0L;

     protected DoubleListNode<E> head;
     protected DoubleListNode<E> tail;
     protected int size;

     public DoubleLinkedList(){
         head = null;
         tail = null;
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
    public Iterator<E> iterator() {
        return new DoubleListIterator<>(head, tail);
    }

    @Override
    public E getFirst() throws EmptyListException {
        if(this.isEmpty())
            throw new EmptyListException();
        return head.getElement();
    }

    @Override
    public E getLast() throws EmptyListException {
        if(this.isEmpty())
            throw new EmptyListException();
        return tail.getElement();
    }

    /**
     * Returns the node at the specified position in the list.
     * Pre-condition: position ranges from 0 to size-1
     * @param position - position of list element to be returned
     * @return Node<E> at position
     */
    protected DoubleListNode<E> getNode(int position){
         DoubleListNode<E> node;
         if(position <= (size - 1) / 2){
             node = head;
             for(int i = 0; i < position; i++)
                 node = node.getNext();
         }else{
             node = tail;
             for(int i = size -1; i > position; i--)
                 node = node.getPrevious();
         }
         return node;
    }

    @Override
    public E get(int position) throws InvalidPositionException {
        if(position < 0 || position >= size)
            throw new InvalidPositionException();
        return getNode(position).getElement();
    }

    @Override
    public int find(E element) {
        DoubleListNode<E> node = head;
        int position = 0;
        while( node != null && !node.getElement().equals(element)){
            node = node.getNext();
            position++;
        }
        if( node == null)
            return -1;
        else
            return position;
    }

    @Override
    public void addFirst(E element) {
        DoubleListNode<E> newNode = new DoubleListNode<E>(element, null, head);
        if(this.isEmpty())
            tail = newNode;
        else
            head.setPrevious(newNode);
        head = newNode;
        size++;
    }

    @Override
    public void addLast(E element) {
        DoubleListNode<E> newNode = new DoubleListNode<E>(element, tail, null);
        if(this.isEmpty())
            head = newNode;
        else
            tail.setNext(newNode);
        tail = newNode;
        size++;
    }

    /**
     * Inserts the specified element at the specified position in the list.
     * Pre-condition: position ranges from 1 to size-1.
     * @param position - middle position where to insert element
     * @param element - element to be inserted at the middle position
     */
    protected void addMiddle(int position, E element){
        DoubleListNode<E> prevNode = this.getNode(position - 1);
        DoubleListNode<E> nextNode = prevNode.getNext();
        DoubleListNode<E> newNode = new DoubleListNode<E>(element, prevNode, nextNode);
        prevNode.setNext(newNode);
        if(nextNode != null)
            nextNode.setPrevious(newNode);
        else
            tail = newNode;

        size++;
    }

    @Override
    public void add(int position, E element) throws InvalidPositionException {
        if(position < 0 || position > size)
            throw new InvalidPositionException();

        if(position == 0)
            this.addFirst(element);
        else if(position == size)
            this.addLast(element);
        else
            this.addMiddle(position, element);
    }

    /**
     * Removes the first node in the list.
     * Pre-condition: the list is not empty.
     */
    protected void removeFirstNode(){
        head = head.getNext();
        if(head == null)
            tail = null;
        else
            head.setPrevious(null);
        size--;
    }
    @Override
    public E removeFirst() throws EmptyListException {
        if(this.isEmpty())
            throw new EmptyListException();

        E element = head.getElement();
        removeFirstNode();
        return element;
    }

    /**
     * Removes the last node in the list.
     * Pre-condition: the list is not empty.
     */
    protected void removeLastNode(){
        tail = tail.getPrevious();
        if(tail == null)
            head = null;
        else
            tail.setNext(null);
        size--;
    }

    @Override
    public E removeLast() throws EmptyListException {
        if(this.isEmpty())
            throw new EmptyListException();

        E element = tail.getElement();
        this.removeLastNode();
        return element;
    }

    /**
     * Removes the specified node from the list.
     * Pre-condition: the node is neither the head nor the tail of the list.
     * @param node - middle node to be removed
     */
    protected void removeMiddleNode(DoubleListNode<E> node){
        DoubleListNode<E> prevNode = node.getPrevious();
        DoubleListNode<E> nextNode = node.getNext();

        if(prevNode != null)
            prevNode.setNext(nextNode);
        if(nextNode != null)
            nextNode.setPrevious(prevNode);

        node.setPrevious(null);
        node.setNext(null);
        size--;
    }

    @Override
    public E remove(int position) throws InvalidPositionException {
        if(position < 0 || position >= size )
            throw new InvalidPositionException();

        if(position == 0)
            return this.removeFirst();
        else if(position == size - 1)
            return this.removeLast();
        else{
            DoubleListNode<E> nodeToRemove = getNode(position);
            E element = nodeToRemove.getElement();
            removeMiddleNode(nodeToRemove);
            return element;
        }
    }

    /**
     * Returns the node with the first occurrence of the specified element
     * in the list, if the lu+ist contains the element.
     * Otherwise, returns null.
     * @param element - element to be searched
     * @return Node<E> where element was found, null if not found
     */
    protected DoubleListNode<E> findNode(E element){
        DoubleListNode<E> currentNode = head;
        while(currentNode != null){
            if(currentNode.getElement().equals(element))
                return currentNode;
            currentNode = currentNode.getNext();
        }
        return null;
    }

    @Override
    public boolean remove(E element) {
        DoubleListNode<E> node = this.findNode(element);
        if(node == null)
            return false;
        else{
            if(node == head)
                this.removeFirstNode();
            else if(node == tail)
                this.removeLastNode();
            else
                this.removeMiddleNode(node);
            return true;
        }
    }
}

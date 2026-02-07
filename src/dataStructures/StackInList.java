package dataStructures;
import dataStructures.Exceptions.*;
/**
 * @author João Rodrigues (67912) jsl.rodrigues@campus.fct.unl.pt
 * @author João Pedro Fernandes (68180) jpcb.fernandes@campus.fct.unl.pt
 */
public class StackInList<E> implements Stack<E>
{

    static final long serialVersionUID = 0L;


    // Memory of the stack: a list.
    protected List<E> list;

    public StackInList( ) {
        list = new DoubleLinkedList<E>();
    }

    // Returns true iff the stack contains no elements.
    public boolean isEmpty( ) {
        return list.isEmpty();
    }

    // Returns the number of elements in the stack.
    public int size( ) {
        return list.size();
    }

    // Returns the element at the top of the stack.
    public E top( ) throws EmptyStackException {
        if ( list.isEmpty() )
            throw new EmptyStackException();

        return list.getFirst();
    }

    // Inserts the specified element onto the top of the stack.
    public void push( E element ) {
        list.addFirst(element);
    }

    // Removes and returns the element at the top of the stack.
    public E pop( ) throws EmptyStackException {
        if ( list.isEmpty() )
            throw new EmptyStackException();

        return list.removeFirst();
    }
}


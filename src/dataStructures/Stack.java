package dataStructures;

import dataStructures.Exceptions.*;
import java.io.Serializable;

/**
 * Stack Abstract Data Type
 * Includes description of general methods for the Stack with the LIFO discipline.
 * @author AED  Team
 * @author João Rodrigues (67912) jsl.rodrigues@campus.fct.unl.pt
 * @author João Pedro Fernandes (68180) jpcb.fernandes@campus.fct.unl.pt
 * @version 1.0
 * @param <E> Generic Element
 *
 */
public interface Stack<E> extends Serializable
{

    /**
     *  Returns true iff the stack contains no elements.
     * @return true iff the stack contains no elements, false otherwise
     */
    public boolean isEmpty( );

    /**
     *  Returns the number of elements in the stack.
     * @return number of elements in the stack
     */
    int size( );

    /**
     *  Returns the element at the top of the stack.
     * @return element at top of stack
     * @throws EmptyStackException when stack = 0
     */
    E top( ) throws EmptyStackException;

    /**
     *  Inserts the specified <code>element</code> onto the top of the stack.
     * @param element element to be inserted onto the stack
     */
    void push( E element );

    /**
     *  Removes and returns the element at the top of the stack.
     * @return element removed from top of stack
     * @throws EmptyStackException when stack = 0
     */
    E pop( ) throws EmptyStackException;

}


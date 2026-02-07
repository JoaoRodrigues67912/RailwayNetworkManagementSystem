package Network;

import dataStructures.AVLTree;
import dataStructures.Entry;
import dataStructures.Iterator;

import java.io.Serializable;

/**
 * @author João Rodrigues (67912) jsl.rodrigues@campus.fct.unl.pt
 * @author João Pedro Fernandes (68180) jpcb.fernandes@campus.fct.unl.pt
 */
public interface Station extends Serializable {

    /**
     * Adds a new line to the station.
     * @param line line to be added.
     */
    void addLine(Line line);

    /**
     *Gets the name of the station.
     * @return the name of the station as a string.
     */
    String getName();

    /**
     *Provides an iterator for the lines associated with this station.
     * @return an Iterator of Line objects.
     */
    Iterator<Entry<String, Line>>listLines();

    /**
     *Removes a line from the station.
     * @param line line to be removed.
     */
    void removeLine(Line line);

    /**
     * Determines if the station has no lines.
     * @return True, if it has no lines,false otherwise.
     */
    boolean hasNoLines();

    /**
     * Adds a train in a station.
     * @param st StationTime object to add.
     */
    public void addTrain(StationTime st);

    /**
     * Removes a train from a station.
     * @param key Station object to remove.
     */
    public void removeTrain(StationTime key);

    /**
     * Returns an iterator of all the trains in a station.
     * @return Iterator of all the trains in a station.
     */
    public Iterator<Entry<StationTime, StationTime>> listTrains();
}

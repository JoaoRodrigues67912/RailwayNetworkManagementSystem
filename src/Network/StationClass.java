package Network;

import dataStructures.*;

/**
 *  @author João Rodrigues (67912) jsl.rodrigues@campus.fct.unl.pt
 *  @author João Pedro Fernandes (68180) jpcb.fernandes@campus.fct.unl.pt
 * Represents a train station with a name and a list of lines that pass through the station.
 * Implements the Station interface
 */
public class StationClass implements Station{

    private final String name;
    private final AVLTree<String, Line> lines;
    private final AVLTree<StationTime, StationTime> trains;

    /**
     * Constructs a StationClass object with the specified station name.
     * @param stationName the name of the station.
     */
    public StationClass(String stationName){
        name = stationName;
        lines = new AVLTree<String, Line>();
        trains = new AVLTree<StationTime, StationTime>();
    }

    public String getName(){
        return name;
    }

    public void addLine(Line line){
        lines.insert(line.getName() ,line);
    }
    public Iterator<Entry<String, Line>> listLines(){
        return lines.iterator();
    }

    public void removeLine(Line line) {
        lines.remove(line.getName());
    }

    public boolean hasNoLines(){
        return lines.isEmpty();
    }
    public void addTrain(StationTime st){
        trains.insert(st, st);
    }
    public void removeTrain(StationTime key){
        trains.remove(key);
    }
    public Iterator<Entry<StationTime, StationTime>> listTrains(){
        return trains.iterator();
    }
}


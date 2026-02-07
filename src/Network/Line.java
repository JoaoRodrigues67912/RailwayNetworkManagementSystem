package Network;

import dataStructures.DoubleLinkedList;
import dataStructures.Iterator;

import java.io.Serializable;

/**
 * @author João Rodrigues (67912) jsl.rodrigues@campus.fct.unl.pt
 * @author João Pedro Fernandes (68180) jpcb.fernandes@campus.fct.unl.pt
 */
public interface Line extends Serializable {

    /**
     * Gets the name of the line.
     * @return the name of the line as a string.
     */
    String getName();

    /**
     * Provides an iterator of stations.
     * @return an Iterator of Station objects.
     */
    Iterator<Station> listStations();

    /**
     * Inserts a schedule to the line.
     * @param schedule schedule to be added.
     */
    void insertSchedule(Schedule schedule);

    /**
     * Checks if the line has the station.
     * @param stationName the name of the station to be checked.
     * @return True if it has the station, false otherwise.
     */
    boolean hasStation(String stationName);

    /**
     * Checks if the first station of the scheduleStations is a terminal station.
     * @param scheduleStations DoubleLinkedList of station names in the schedule.
     * @return True if the first station matches either terminal station of the line.
     */
    boolean validTerminalLine(DoubleLinkedList<String> scheduleStations);

    /**
     * Checks if a certain line has all the stations that are provided.
     * @param station List of stations.
     * @return True if it has all stations, false otherwise.
     */
    boolean hasAllStations(DoubleLinkedList<String> station);
    /**
     * Validates the order of stations in a schedule based on the line's station order.
     * @Pre: validTerminalLine(scheduleStations);
     * @param scheduleStations stations to be checked.
     * @return True if the station order in scheduleStations matches the order in this line, false otherwise.
     */
    boolean validOrder(DoubleLinkedList<String> scheduleStations);

    /**
     * Removes a schedule from the line.
     * @Pre: scheduleExists(stationName, time).
     * @param stationName The name of the station for which the schedule should be removed.
     * @param time The specific time of the schedule entry to remove.
     */
    void removeSchedule(String stationName, Time time);

    /**
     * Checks if a schedule exists for a specific station at a given time and returns its position.
     * @param stationName The name of the station to search for in the schedule list.
     * @param time The time of the schedule entry to look for.
     * @return The index of the matching schedule entry if it exists; -1 if no match is found.
     */
    int scheduleExists(String stationName, Time time);

    /**
     * Returns an iterator for schedules associated with the specified station.
     * @param stationName The name of the station for which schedules are listed.
     * @return An iterator over the schedules matching the given station.
     */
    Iterator<Schedule> listSchedules(String stationName);

    /**
     * Checks if the specified station is a terminal station.
     * @param stationName station to be checked.
     * @return True if it is, false otherwise.
     */
    boolean terminalStationExists(String stationName);

    /**
     * Finds the best schedule from a list of schedules that meets the specified departure and arrival stations
     * and is on or before a specified arrival time.
     * @param departureStation name of the departure station.
     * @param arrivalStation name of the arrival station.
     * @param time the time to arrive.
     * @return an iterator over the best schedule, or an empty iterator if no valid schedule is found.
     */
    Iterator<Schedule> bestSchedule(String departureStation, String arrivalStation, Time time);

    /**
     * Checks if the line as both stations, departure and arrival.
     * @param departureStation name of the departure station.
     * @param arrivalStation name of the arrival station.
     * @return True if it has both, false otherwise.
     */
    boolean hasStations(String departureStation, String arrivalStation);

    /**
     * Checks if a train overtakes another one when created.
     * @param s list of the station's names.
     * @param t list of Times a train passes in each station.
     * @return true if the new train overtakes, and false otherwise.
     */
    boolean overtake(DoubleLinkedList<String> s, DoubleLinkedList<Time> t);
    /**
     * Checks if a train departures at the same time and stations as others.
     * @param s list of the station's names.
     * @param t list of Times a train passes in each station.
     * @return true if it departs at the same time and false otherwise.
     */
    boolean sameDepartureTime(DoubleLinkedList<String> s, DoubleLinkedList<Time> t);

    /**
     * Removes all the schedules in the stations a line has when the line is removed.
     */
    void removeSchedules();

}

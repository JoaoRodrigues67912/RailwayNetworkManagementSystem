package Network;

import Network.Exceptions.*;
import dataStructures.*;

import java.io.Serializable;

/**
 * @author João Rodrigues (67912) jsl.rodrigues@campus.fct.unl.pt
 * @author João Pedro Fernandes (68180) jpcb.fernandes@campus.fct.unl.pt
 */
public interface Network extends Serializable {

    /**
     * Inserts a new line with the specified name and list of station names.
     * @param lineName name of the lines.
     * @param stations list of stations to add in line.
     * @throws LineAlreadyExistsException if a line with the specified name already exists.
     */
    void insertLine(String lineName, DoubleLinkedList<String> stations) throws LineAlreadyExistsException;

    /**
     * Provides an iterator over the stations of the specified line.
     * @param lineName name of the line.
     * @return An iterator of stations.
     * @throws LineDoesNotExistException if a line with the specified name does not exist.
     */
    Iterator<Station> listStations(String lineName) throws LineDoesNotExistException;

    /**
     * Provides an iterator over the lines of the specified station.
     * @param stationName name of the station.
     * @return An iterator of lines.
     * @throws StationDoesNotExistException if the station with the specified name does not exist.
     */
    Iterator<Entry<String, Line>> listLines(String stationName) throws StationDoesNotExistException;

    /**
     * Removes a line.
     * @param lineName name of the line.
     * @throws LineDoesNotExistException if a line with the specified name does not exist.
     */
    void removeLine(String lineName) throws LineDoesNotExistException;

    /**
     * Inserts a schedule for a train on a specified line.
     * @param lineName name of the line.
     * @param trainNumber number of the train
     * @param stations list of stations
     * @param time a list of times corresponding to each station in the schedule.
     * @throws LineDoesNotExistException if a line with the specified name does not exist.
     * @throws InvalidScheduleException if the schedule is invalid.
     */
    void insertSchedule(String lineName, int trainNumber, DoubleLinkedList<String> stations,
                        DoubleLinkedList<Time> time) throws LineDoesNotExistException, InvalidScheduleException;

    /**
     * Removes a schedule in the specified line, identified by a station and a time.
     * @param lineName name of the line.
     * @param stationName name of the station.
     * @param time time associated with the station.
     * @throws LineDoesNotExistException if a line with the specified name does not exist.
     * @throws ScheduleDoesNotExistException if the scheduled does not exist.
     */
    void removeSchedule(String lineName, String stationName, Time time)throws LineDoesNotExistException,
            ScheduleDoesNotExistException;

    /**
     * Provides an iterator over the schedules of the specified line and station.
     * @param lineName name of the line.
     * @param stationName name of the station.
     * @return An iterator of schedules
     * @throws LineDoesNotExistException if a line with the specified name does not exist.
     * @throws TerminalDoesNotExistException if the station is not a terminal station.
     */
    Iterator<Schedule> listSchedulesLine(String lineName, String stationName)throws LineDoesNotExistException,
            TerminalDoesNotExistException;

    /**
     * Provides an iterator of all the trains that pass a station.
     * @param stationName Name of the station requested.
     * @return An iterator of Entry< StationTime, StationTime >;
     * @throws StationDoesNotExistException
     */
    Iterator<Entry<StationTime, StationTime>> listTrains(String stationName) throws StationDoesNotExistException;

    /**
     * Provides an iterator over the best schedule of the specified line.
     * @param lineName name of the line.
     * @param departureStation name of the departure station.
     * @param arrivalStation name of the arrival station.
     * @param time time to arrive.
     * @return An iterator over the best schedules.
     * @throws LineDoesNotExistException if a line with the specified name does not exist.
     * @throws DepartureStationDoesNotExistException if the departure stations does not exist.
     * @throws ImpossibleRouteException if the route is impossible.
     */
    Iterator<Schedule> bestSchedule(String lineName, String departureStation,String arrivalStation, Time time)
            throws LineDoesNotExistException, DepartureStationDoesNotExistException, ImpossibleRouteException;
}

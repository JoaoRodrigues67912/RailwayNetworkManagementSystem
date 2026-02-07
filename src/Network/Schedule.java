package Network;

import dataStructures.Iterator;

import java.io.Serializable;

/**
 * @author João Rodrigues (67912) jsl.rodrigues@campus.fct.unl.pt
 * @author João Pedro Fernandes (68180) jpcb.fernandes@campus.fct.unl.pt
 */
public interface Schedule extends Serializable {

    /**
     * Checks if the terminal station in the schedule matches the specified station name and time.
     * @param stationName the name of the station to compare.
     * @param time the time to compare.
     * @return True if it is equal, false otherwise.
     */
    boolean isScheduleEqual(String stationName, Time time);

    /**
     * Gets the train number.
     * @return an integer identifying the train.
     */
    int getTrainNumber();

    /**
     *Gets the name of the terminal station.
     * @return the name of the terminal station as a string.
     */
    String getTerminalLine();

    /**
     * Returns the time of the terminal line.
     * @return Time object.
     */
    Time getTerminalLineTime();

    /**
     *Provides an iterator over the stationTimes in the schedule.
     * @return an iterator<StationTime> for iterating through the schedule's station times.
     */
    Iterator<StationTime> listStationTime();

    /**
     * Checks if the schedule has the station.
     * @param stationName name of the station to be checked.
     * @return True, if it has the station, false otherwise.
     */
    boolean scheduleHasStation(String stationName);

    /**
     * Gets the time of the station.
     * @Pre: the station name exists in the schedule.
     * @param stationName the name of the station
     * @return Time of the station
     */
    Time getTimeStation(String stationName);

    /**
     * Checks if the schedule includes both specified departure and arrival stations.
     * @param departureStation name of the departure station.
     * @param arrivalStation name of the arrival station.
     * @return True if it has both stations, false otherwise.
     */
    boolean hasStations(String departureStation, String arrivalStation);

    /**
     * Checks if the departure station occurs before the arrival station in the schedule.
     * @Pre: hasStations(departure, arrival).
     * @param departure name of the departure station
     * @param arrival name of the arrival station.
     * @return True if departure occurs before arrival, false otherwise.
     */
    boolean inOrder(String departure, String arrival);
}

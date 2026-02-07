package Network;

import java.io.Serializable;

/**
 * @author João Rodrigues (67912) jsl.rodrigues@campus.fct.unl.pt
 * @author João Pedro Fernandes (68180) jpcb.fernandes@campus.fct.unl.pt
 */
public interface StationTime extends Serializable, Comparable<StationTime> {

    /**
     * Gets the time associated with this station.
     * @return a Time object representing the scheduled time for this station.
     */
    Time getTime();

    /**
     *Gets the name of the station.
     * @return the station name as a string.
     */
    String getStationName();

    /**
     * Gets the train number.
     * @return the train number.
     */
    int getTrainNumber();

    /**
     *Checks if the provided station name and time are equal to this station's name and time.
     * @param stationName the station name to compare
     * @param time the time to compare
     * @return True if both the station name and the time are equal, false otherwise.
     */
    boolean isEqual(String stationName, Time time);

    /**
     * Returns the time formatted as a string in "HH:mm" format.
     * @return a formatted string representing the time.
     */
    String getStringTime();
}

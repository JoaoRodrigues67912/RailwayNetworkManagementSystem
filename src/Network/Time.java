package Network;

import java.io.Serializable;

/**
 * @author João Rodrigues (67912) jsl.rodrigues@campus.fct.unl.pt
 * @author João Pedro Fernandes (68180) jpcb.fernandes@campus.fct.unl.pt
 */
public interface Time extends Serializable {

    /**
     * Gets the hour of this.time.
     * @return the hour as an integer (0-23)
     */
    int getHour();

    /**
     * Gets the minutes of this.time.
     * @return the minutes as an integer (0-59).
     */
    int getMinutes();

    /**
     * Validates if a given time is greater than this.time.
     * @param t time to validate
     * @return True if the given time is greater than this.time, false otherwise.
     */
    boolean validTime(Time t);

    /**
     * Checks if a given time is equal to this.time
     * @param t time to compare with this.time
     * @return True if the time is equal, false otherwise.
     */
    boolean isTimeEqual(Time t);

    /**
     * Checks if this.time is less than the given time.
     * @param t time to compare with this.time.
     * @return True if this.time is less, false otherwise.
     */
    boolean isTimeLess(Time t);

    /**
     * Checks if this.time is higher than the given time.
     * @param t time to compare with this.time.
     * @return True if this.time is higher, false otherwise.
     */
    public boolean isTimeHigher(Time t);

}

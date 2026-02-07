package Network;
/**
 * @author João Rodrigues (67912) jsl.rodrigues@campus.fct.unl.pt
 * @author João Pedro Fernandes (68180) jpcb.fernandes@campus.fct.unl.pt
 * A class that represents a specific time with hours and minutes.
 * Implements the Time interface.
 */
public class TimeClass implements Time{

    private final int hours;
    private final int minutes;

    /**
     * Constructs a TimeClass object with specified hours and minutes.
     * @param hours the hour of the time(0-23).
     * @param minutes the minutes of the time (0-59).
     */
    public TimeClass(int hours, int minutes) {
        this.hours = hours;
        this.minutes = minutes;
    }

    public int getHour(){
        return hours;
    }

    public int getMinutes(){
        return minutes;
    }


    public boolean validTime(Time t){
        if(t.getHour() < this.getHour())
            return false;
        else if(t.getHour() == this.getHour() && t.getMinutes() < this.getMinutes())
            return false;
        else if(isTimeEqual(t))
            return false;
        return true;
    }
    public boolean isTimeEqual(Time t){
        return (this.getHour() == t.getHour() && this.getMinutes() == t.getMinutes());
    }
    public boolean isTimeLess(Time t) {
        return (this.getHour() < t.getHour() || (this.getHour() == t.getHour() && this.getMinutes() < t.getMinutes()));
    }
    public boolean isTimeHigher(Time t) {
        return (this.getHour() > t.getHour() || (this.getHour() == t.getHour() && this.getMinutes() > t.getMinutes()));

    }
}

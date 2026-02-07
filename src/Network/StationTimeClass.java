package Network;
/**
 * @author João Rodrigues (67912) jsl.rodrigues@campus.fct.unl.pt
 * @author João Pedro Fernandes (68180) jpcb.fernandes@campus.fct.unl.pt
 *
 * Represents a station's scheduled time, including the station's name, specific time and it's train number.
 * Implements the StationTime interface.
 */
public class StationTimeClass implements StationTime{

    private static final String STRING_FORMAT = "%02d:%02d" ;

    private final String stationName;
    private final Time time;
    private final int trainNumber;

    /**
     * Constructs a StationTimeClass object with the specified station name and time.
     * @param stationName the name of the station.
     * @param time the time associated with the station.
     */
    public StationTimeClass(String stationName, Time time, int trainNumber){
        this.stationName = stationName;
        this.time = time;
        this.trainNumber = trainNumber;
    }

    public Time getTime(){
        return time;
    }
    public int getTrainNumber(){
        return trainNumber;
    }
    public String getStringTime(){
        return String.format(STRING_FORMAT, time.getHour(), time.getMinutes());
    }

    public String getStationName(){
        return stationName;
    }
    public boolean isEqual(String stationName, Time time){
        return (this.getStationName().equalsIgnoreCase(stationName) && this.getTime().isTimeEqual(time));
    }

    @Override
    public int compareTo(StationTime st) {
        if(this.time.isTimeLess(st.getTime())){
            return -1;
        }else if(this.time.isTimeHigher(st.getTime())){
            return 1;
        }else{
            return Integer.compare(this.trainNumber, st.getTrainNumber());
        }
    }
}

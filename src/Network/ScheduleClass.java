package Network;

import dataStructures.DoubleLinkedList;
import dataStructures.Iterator;
/**
 * @author João Rodrigues (67912) jsl.rodrigues@campus.fct.unl.pt
 * @author João Pedro Fernandes (68180) jpcb.fernandes@campus.fct.unl.pt
 * Represents a train schedule, including a train number and a list of station times.
 * Implements the Schedule interface.
 */
public class ScheduleClass implements Schedule{

    private final int trainNumber;
    private final DoubleLinkedList<StationTime> schedule;

    /**
     * Constructs a ScheduleClass object with the specified train number and it schedules.
     * @param trainNumber unique number that identifying the train.
     * @param schedule a double-linked list containing the scheduled times for each station.
     */
    public ScheduleClass(int trainNumber, DoubleLinkedList<StationTime> schedule){
        this.trainNumber = trainNumber;
        this.schedule = schedule;
    }

    public int getTrainNumber(){
        return trainNumber;
    }
    public boolean isScheduleEqual(String stationName, Time time) {
        return schedule.get(0).isEqual(stationName, time);
    }

    public String getTerminalLine(){
        return schedule.getFirst().getStationName();
    }

    public Time getTerminalLineTime(){
        return schedule.getFirst().getTime();
    }

    public Iterator<StationTime> listStationTime(){
        return schedule.iterator();
    }
    public boolean scheduleHasStation(String stationName) {
        for(int i = 0; i < schedule.size(); i++) {
            if(schedule.get(i).getStationName().equalsIgnoreCase(stationName))
                return true;
        }
        return false;
    }
    public boolean hasStations(String departureStation, String arrivalStation) {
        return(scheduleHasStation(departureStation) && scheduleHasStation(arrivalStation));
    }

    public Time getTimeStation(String stationName){
        Time time = null;
        for(int i = 0; i < schedule.size();i++){
            if(schedule.get(i).getStationName().equalsIgnoreCase(stationName))
                time = schedule.get(i).getTime();
        }
        return time;
    }

    public boolean inOrder(String departure, String arrival){
        for(int i = 0; i < schedule.size(); i++) {
            String station = schedule.get(i).getStationName();
            if(station.equalsIgnoreCase(arrival) && !station.equalsIgnoreCase(departure))
                return false;
            if(station.equalsIgnoreCase(departure) && !station.equalsIgnoreCase(arrival))
                return true;
        }
        return true;
    }
}

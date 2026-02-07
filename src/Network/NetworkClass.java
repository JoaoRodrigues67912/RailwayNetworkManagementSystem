package Network;
import Network.Exceptions.*;
import dataStructures.*;

/**
 * @author João Rodrigues (67912) jsl.rodrigues@campus.fct.unl.pt
 * @author João Pedro Fernandes (68180) jpcb.fernandes@campus.fct.unl.pt
 * The NetworkClass manages the lines and the stations.
 * Implements the Network interface.
 */
public class NetworkClass implements Network{

    private final Dictionary<String, Station> stations;
    private final Dictionary<String, Line> lines;

    public NetworkClass(){
        stations = new SepChainHashTable<String, Station>();
        lines = new SepChainHashTable<String, Line>();
    }


    public void insertLine(String lineName, DoubleLinkedList<String> stations) throws LineAlreadyExistsException {
        lineAlreadyExist(lineName);
        DoubleLinkedList<Station> tmp = new DoubleLinkedList<Station>();
        for(int i = 0; i < stations.size(); i++){
            String stationName = stations.get(i);
            if(!stationExists(stationName)){
                Station s = new StationClass(stationName);
                this.stations.insert(stationName, s);
                tmp.addLast(s);
            }else{
                tmp.addLast(this.stations.find(stationName));
            }
        }
        LineClass line = new LineClass(lineName,tmp);
        lines.insert(lineName, line);
        addLineInStation(tmp, line);
    }

    /**
     * Adds a line in each station of the list.
     * @param stations list of stations
     * @param line line to be added.
     */
    private void addLineInStation(DoubleLinkedList<Station> stations, Line line) {
        for (int i = 0; i < stations.size(); i++) {
            stations.get(i).addLine(line);
        }
    }

    public Iterator<Station> listStations(String lineName) throws LineDoesNotExistException {
        lineDoesNotExist(lineName);
        return lines.find(lineName).listStations();
    }
    public Iterator<Entry<String, Line>> listLines(String stationName) throws StationDoesNotExistException {
        stationDoesNotExist(stationName);
        return stations.find(stationName).listLines();
    }
    public void removeLine(String lineName) throws LineDoesNotExistException {
        lineDoesNotExist(lineName);
        Line lineToRemove = lines.find(lineName);
        Iterator<Station> it = lineToRemove.listStations();
        while(it.hasNext()){
            Station s = it.next();
            s.removeLine(lineToRemove);
            if(s.hasNoLines())
                stations.remove(s.getName());
        }
        lineToRemove.removeSchedules();
        lines.remove(lineName);
    }

    public void insertSchedule(String lineName, int trainNumber, DoubleLinkedList<String> stations,
                               DoubleLinkedList<Time> time) throws LineDoesNotExistException,
            InvalidScheduleException{
        lineDoesNotExist(lineName);
        invalidSchedule(lineName, stations, time);

        DoubleLinkedList<StationTime> schedule = new DoubleLinkedList<>();
        for(int i = 0; i < stations.size();i++){
            StationTime s = new StationTimeClass(this.stations.find(stations.get(i)).getName(), time.get(i), trainNumber);
            schedule.addLast(s);
            this.stations.find(stations.get(i)).addTrain(s);
        }
        Schedule schedule1 = new ScheduleClass(trainNumber, schedule);
        lines.find(lineName).insertSchedule(schedule1);
    }

    public void removeSchedule(String lineName, String stationName, Time time) throws LineDoesNotExistException,
            ScheduleDoesNotExistException {
        lineDoesNotExist(lineName);
        scheduleDosNotExist(lineName, stationName, time);

        lines.find(lineName).removeSchedule(stationName, time);
    }
    public Iterator<Schedule> listSchedulesLine(String lineName, String stationName)
            throws LineDoesNotExistException, TerminalDoesNotExistException{
        lineDoesNotExist(lineName);
        terminalStationDoesNotExist(lineName, stationName);

        return lines.find(lineName).listSchedules(stationName);
    }

    public Iterator<Entry<StationTime, StationTime>> listTrains(String stationName) throws StationDoesNotExistException{
        stationDoesNotExist(stationName);

        return stations.find(stationName).listTrains();
    }
    public Iterator<Schedule> bestSchedule(String lineName, String departureStation,String arrivalStation, Time time)
            throws LineDoesNotExistException, DepartureStationDoesNotExistException, ImpossibleRouteException {
        lineDoesNotExist(lineName);
        departureDoesNotExist(lineName, departureStation);
        impossibleRoute(lineName,departureStation, arrivalStation, time);

        return lines.find(lineName).bestSchedule(departureStation,arrivalStation, time);
    }

    /**
     * Checks if the line exist.
     * @param lineName name of the line.
     * @return True if it exists, false otherwise.
     */
    private boolean lineExists(String lineName) {
        return lines.find(lineName) != null;
    }

    /**
     * Checks if the station exist.
     * @param stationName name of the station.
     * @return True if it exists, false otherwise.
     */
    private boolean stationExists(String stationName) {
        return stations.find(stationName) != null;
    }

    /**
     * Checks if a list of times is in a valid sequential order.
     * @param time list of time.
     * @return True if is valid, false otherwise.
     */
    private boolean validHours(DoubleLinkedList<Time> time) {
        for(int i = 1; i < time.size(); i++){
            Time t1 = time.get(i - 1);
            Time t2 = time.get(i);
            if(!t1.validTime(t2)){
                return false;}
        }
        return true;
    }

    /**
     * Checks if there are any duplicate station names in the provided list of stations.
     * @param stations list of stations.
     * @return True if it has duplicate stations, false otherwise.
     */
    private boolean sameStations(DoubleLinkedList<String> stations) {
       DoubleLinkedList<String> s = new DoubleLinkedList<>();
       for(int i = 0; i < stations.size(); i++) {
           String station = stations.get(i);
           if(s.find(station) != -1){
               return true;
           }
           s.addLast(stations.get(i));
       }
       return false;
    }

    /**
     * Checks if line exists.
     * @param lineName name of the line.
     * @throws LineAlreadyExistsException if the line already exists.
     */
    private void lineAlreadyExist(String lineName) throws LineAlreadyExistsException {
        if(lineExists(lineName))
            throw new LineAlreadyExistsException();
    }

    /**
     * Checks if the line does not exist.
     * @param lineName name of the line.
     * @throws LineDoesNotExistException if the line does not exist.
     */
    private void lineDoesNotExist(String lineName) throws LineDoesNotExistException {
        if(!lineExists(lineName))
            throw new LineDoesNotExistException();
    }

    /**
     * Checks if the station does not exist.
     * @param stationName name of the station.
     * @throws StationDoesNotExistException if the station does not exist.
     */
    private void stationDoesNotExist(String stationName) throws StationDoesNotExistException {
        if(!stationExists(stationName))
            throw new StationDoesNotExistException();
    }

    /**
     * Checks if the schedule is invalid in the specified line.
     * @param lineName name of the line.
     * @param s list of stations to insert in the schedule.
     * @param t list of the times corresponding to each station.
     * @throws InvalidScheduleException if the scheduled is invalid.
     */
    private void invalidSchedule(String lineName, DoubleLinkedList<String> s, DoubleLinkedList<Time> t)
            throws InvalidScheduleException{
        Line line = lines.find(lineName);
        if(!line.validTerminalLine(s) || !line.hasAllStations(s) || !validHours(t) || sameStations(s) ||
                !line.validOrder(s) || line.sameDepartureTime(s, t)|| line.overtake(s,t)){
            throw new InvalidScheduleException();
        }
    }

    /**
     * Checks if the schedule in the specified line, identified by the station and the time exists.
     * @param lineName name of the line.
     * @param stationName name of the station
     * @param time time.
     * @throws ScheduleDoesNotExistException if the schedule does not exist.
     */
    private void scheduleDosNotExist(String lineName, String stationName, Time time)
            throws ScheduleDoesNotExistException {
        if(lines.find(lineName).scheduleExists(stationName, time) == -1){
            throw new ScheduleDoesNotExistException();
        }
    }

    /**
     * Checks if the station in the specified line is a terminal station.
     * @param lineName name of the line.
     * @param stationName name of the station.
     * @throws TerminalDoesNotExistException if the terminal station does not exist.
     */
    private void terminalStationDoesNotExist(String lineName, String stationName)
            throws TerminalDoesNotExistException{
        if(!lines.find(lineName).terminalStationExists(stationName))
            throw new TerminalDoesNotExistException();
    }

    /**
     * Checks if the departure station exists.
     * @param lineName name of the line.
     * @param departureStation name of the departure station.
     * @throws DepartureStationDoesNotExistException if the departure station does not exist.
     */
    private void departureDoesNotExist(String lineName, String departureStation)
            throws DepartureStationDoesNotExistException{
        if(!lines.find(lineName).hasStation(departureStation))
            throw new DepartureStationDoesNotExistException();
    }

    /**
     * Checks if the route is impossible.
     * @param lineName name of the line.
     * @param departureStation name of the departure station.
     * @param arrivalStation name of the arrival station.
     * @param time time to arrive.
     * @throws ImpossibleRouteException if the route is impossible.
     */
    private void impossibleRoute(String lineName, String departureStation, String arrivalStation, Time time)
            throws ImpossibleRouteException {
        Iterator<Schedule> it = lines.find(lineName).bestSchedule(departureStation,arrivalStation, time);
        if(!lines.find(lineName).hasStations(departureStation, arrivalStation) || !it.hasNext())
            throw new ImpossibleRouteException();
    }
}


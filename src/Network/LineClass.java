package Network;

import dataStructures.*;

/**
 * @author João Rodrigues (67912) jsl.rodrigues@campus.fct.unl.pt
 * @author João Pedro Fernandes (68180) jpcb.fernandes@campus.fct.unl.pt
 * Represents a train line with a name, list of stations and the schedules.
 * Implements the Line interface.
 */
public class LineClass implements Line {

    private final DoubleLinkedList<Station> stations;
    private final DoubleLinkedList<Schedule> schedules = new DoubleLinkedList<>();

    private final String lineName;

    public LineClass(String lineName, DoubleLinkedList<Station> stations) {
        this.lineName = lineName;
        this.stations = stations;
    }

    public String getName() {
        return lineName;
    }

    public Iterator<Station> listStations() {
        return stations.iterator();
    }

    public void insertSchedule(Schedule schedule) {
        schedules.addLast(schedule);
    }

    public boolean hasStation(String stationName) {
        for (int i = 0; i < stations.size(); i++) {
            if (stations.get(i).getName().equalsIgnoreCase(stationName))
                return true;
        }
        return false;
    }

    public boolean validTerminalLine(DoubleLinkedList<String> scheduleStations) {
        String s = scheduleStations.getFirst();
        return s.equalsIgnoreCase(stations.getFirst().getName()) || s.equalsIgnoreCase(stations.getLast().getName());
    }

    public boolean hasAllStations(DoubleLinkedList<String> station) {
        for (int i = 0; i < station.size(); i++) {
            boolean found = false;
            for (int j = 0; j < stations.size(); j++) {
                if (station.get(i).equalsIgnoreCase(stations.get(j).getName())) {
                    found = true;
                    break;
                }
            }
            if (!found)
                return false;
        }
        return true;
    }

    public boolean validOrder(DoubleLinkedList<String> scheduleStations) {
        boolean valid = true;
        Dictionary<String, Integer> stationsPos = new DictionaryClass<>();
        Iterator<Station> it = listStations();
        int pos = 0;
        while (it.hasNext()) {
            Station s = it.next();
            stationsPos.insert(s.getName(), pos++);
        }
        for (int i = 2; i < scheduleStations.size(); i++) {
            int pos1 = stationsPos.find(scheduleStations.get(i - 1));
            int pos2 = stationsPos.find(scheduleStations.get(i));
            if (scheduleStations.getFirst().equalsIgnoreCase(stations.getFirst().getName())) {
                if (pos2 < pos1)
                    valid = false;
            } else {
                if (pos2 > pos1)
                    valid = false;
            }
        }
        return valid;
    }

    public void removeSchedule(String stationName, Time time) {
        int pos = scheduleExists(stationName, time);
        Iterator<StationTime> it = schedules.get(pos).listStationTime();
        while (it.hasNext()) {
            StationTime st = it.next();
            for (int i = 0; i < stations.size(); i++) {
                if (stations.get(i).getName().equals(st.getStationName())) {
                    stations.get(i).removeTrain(st);
                }
            }
        }
        schedules.remove(pos);
    }

    public int scheduleExists(String stationName, Time time) {
        int pos = -1;
        for (int i = 0; i < schedules.size(); i++) {
            if (schedules.get(i).isScheduleEqual(stationName, time))
                pos = i;
        }
        return pos;
    }

    public Iterator<Schedule> listSchedules(String stationName){
        DoubleLinkedList<Schedule> list = new DoubleLinkedList<>();
        for (int i = 0; i < schedules.size(); i++) {
            if (schedules.get(i).getTerminalLine().equalsIgnoreCase(stationName)) {
                list.addLast(schedules.get(i));
            }
        }
        return list.iterator();
    }

    public boolean terminalStationExists(String stationName) {
        return (stations.getFirst().getName().equalsIgnoreCase(stationName) ||
                stations.getLast().getName().equalsIgnoreCase(stationName));
    }

    public Iterator<Schedule> bestSchedule(String departure, String arrival, Time time) {
        DoubleLinkedList<Schedule> bestSchedule = new DoubleLinkedList<>();
        for (int i = 0; i < schedules.size(); i++) {
            Time t = schedules.get(i).getTimeStation(arrival);
            if (schedules.get(i).hasStations(departure, arrival) && schedules.get(i).inOrder(departure, arrival) &&
                    (t.isTimeEqual(time) || t.isTimeLess(time)))
                bestSchedule.addLast(schedules.get(i));
        }
        while (bestSchedule.size() > 1) {
            Time compare = bestSchedule.get(0).getTimeStation(arrival);
            if (compare.isTimeLess(bestSchedule.get(1).getTimeStation(arrival)))
                bestSchedule.remove(0);
            else
                bestSchedule.remove(1);
        }
        return bestSchedule.iterator();
    }

    public boolean hasStations(String departureStation, String arrivalStation) {
        for (int i = 0; i < schedules.size(); i++) {
            if (schedules.get(i).hasStations(departureStation, arrivalStation))
                return true;
        }
        return false;
    }

    public boolean overtake(DoubleLinkedList<String> s, DoubleLinkedList<Time> t) {
        boolean overtake = false;
        boolean before;
        for (int i = 0; i < schedules.size(); i++) {
            before = false;
            Iterator<StationTime> it = schedules.get(i).listStationTime();
            if (schedules.get(i).getTerminalLine().equals(s.getFirst())) {
                if (schedules.get(i).getTerminalLineTime().isTimeHigher(t.get(0))) {
                    before = true;
                }
                while (it.hasNext() && !overtake) {
                    StationTime next = it.next();
                    for (int j = 1; j < s.size(); j++) {
                        if (s.get(j).equals(next.getStationName())) {
                            if (t.get(j).isTimeLess(next.getTime()) != before || t.get(j).isTimeEqual(next.getTime())) {
                                overtake = true;
                            }
                            break;
                        }
                    }
                }
            }
        }
        return overtake;
    }

    public boolean sameDepartureTime(DoubleLinkedList<String> s, DoubleLinkedList<Time> t) {
        for (int i = 0; i < schedules.size(); i++) {
            if (s.getFirst().equals(schedules.get(i).getTerminalLine())) {
                if (t.getFirst().isTimeEqual(schedules.getFirst().getTerminalLineTime()))
                    return true;
            }
        }
        return false;
    }
    public void removeSchedules() {
        for(int i = 0; i < schedules.size(); i++) {
            Schedule s = schedules.get(i);
            Iterator<StationTime> it = s.listStationTime();
            while(it.hasNext()){
                StationTime st = it.next();
                String stationName = st.getStationName();
                for(int j = 0; j < stations.size(); j++){
                    if(stations.get(j).getName().equals(stationName)){
                        stations.get(j).removeTrain(st);
                    }
                }
            }
        }
    }
}

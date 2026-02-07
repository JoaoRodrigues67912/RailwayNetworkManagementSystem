import Network.*;
import Network.Exceptions.*;
import dataStructures.*;

import java.io.*;
import java.util.Scanner;

/**
 * @author João Rodrigues (67912) jsl.rodrigues@campus.fct.unl.pt
 * @author João Pedro Fernandes (68180) jpcb.fernandes@campus.fct.unl.pt
 */

public class Main {


    private static final String FILE_NAME = "storedNetwork.dat";
    private static final String EXIT = "TA";
    private static final String INSERT_LINE = "IL";
    private static final String REMOVE_LINE = "RL";
    private static final String LIST_STATIONS_LINE = "CL";
    private static final String LIST_LINES_STATION = "CE";
    private static final String INSERT_SCHEDULE = "IH";
    private static final String REMOVE_SCHEDULE = "RH";
    private static final String LIST_SCHEDULES_LINE = "CH";
    private static final String TRAINS_STATION = "LC";
    private static final String BEST_TIMETABLE = "MH";

    //OUTPUT
    private static final String LINE_EXISTS = "Linha existente.\n";
    private static final String INSERTION_SUCCESS = "Inserção de linha com sucesso.\n";
    private static final String LINE_DOES_NOT_EXIST = "Linha inexistente.\n";
    private static final String STATION_DOES_NOT_EXIST = "Estação inexistente.\n";
    private static final String REMOVED_LINE = "Remoção de linha com sucesso.\n";
    private static final String INVALID_SCHEDULE = "Horário inválido.\n";
    private static final String SCHEDULE_ADDED = "Criação de horário com sucesso.\n";
    private static final String SCHEDULE_REMOVED = "Remoção de horário com sucesso.\n";
    private static final String SCHEDULE_DOES_NOT_EXIST = "Horário inexistente.\n";
    private static final String DEPARTURE_STATION_DOES_NOT_EXIST = "Estação de partida inexistente.\n";
    private static final String IMPOSSIBLE_ROUTE = "Percurso impossível.\n";
    private static final String QUIT = "Aplicação terminada.\n";
    private static final String LIST_TRAIN = "Comboio %d %s\n";




    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String comm;
        Network network = load();
        do{
            comm = in.next().toUpperCase();
            executeCommand(in, comm, network);
        }while(!comm.equals(EXIT));
        save(network);
        in.close();
    }

    private static void executeCommand(Scanner in, String comm, Network network){
        switch (comm){
            case INSERT_LINE -> insertLine(in, network);
            case REMOVE_LINE -> removeLine(in, network);
            case LIST_STATIONS_LINE -> listStationsLine(in, network);
            case LIST_LINES_STATION -> listLinesStation(in, network);
            case INSERT_SCHEDULE -> insertSchedule(in, network);
            case REMOVE_SCHEDULE -> removeSchedule(in, network);
            case LIST_SCHEDULES_LINE -> listSchedulesLine(in, network);
            case TRAINS_STATION -> trainsStation(in, network);
            case BEST_TIMETABLE -> bestTimetable(in, network);
            case EXIT -> System.out.print(QUIT);
        }
    }

    private static void insertLine(Scanner in, Network network){
        String lineName = in.nextLine().trim();
        String stationName;
        DoubleLinkedList<String> stations = new DoubleLinkedList<>();
        do{
            stationName = in.nextLine().trim();
            if(!stationName.isEmpty()){
                stations.addLast(stationName);
            }
        }while(!stationName.isEmpty());
        try{
            network.insertLine(lineName,stations);
            System.out.print(INSERTION_SUCCESS);
        }catch(LineAlreadyExistsException e){
            System.out.print(LINE_EXISTS);
        }
    }

    private static void removeLine(Scanner in, Network network){
        String lineName = in.nextLine().trim();
        try{
            network.removeLine(lineName);
            System.out.print(REMOVED_LINE);
        }catch(LineDoesNotExistException e){
            System.out.print(LINE_DOES_NOT_EXIST);
        }
    }
    private static void listStationsLine(Scanner in, Network network){
        String lineName = in.nextLine().trim();
        try{
            Iterator<Station> it = network.listStations(lineName);
            while(it.hasNext())
                System.out.println(it.next().getName());
        }catch(LineDoesNotExistException e) {
            System.out.print(LINE_DOES_NOT_EXIST);
        }
    }


    private static void listLinesStation(Scanner in, Network network) {
        String stationName = in.nextLine().trim();
        try {
            Iterator<Entry<String, Line>> it = network.listLines(stationName);
            while (it.hasNext())
                System.out.println(it.next().getValue().getName());
        } catch (StationDoesNotExistException e) {
            System.out.print(STATION_DOES_NOT_EXIST);
        }
    }

    private static void insertSchedule(Scanner in, Network network){
        String lineName = in.nextLine().trim();
        int trainNumber = in.nextInt();
        in.nextLine(); String input; String stationName = "";
        DoubleLinkedList<String> stations = new DoubleLinkedList<>();
        DoubleLinkedList<Time> hours = new DoubleLinkedList<>();
        do{
            input = in.nextLine().trim();
            String[] parts = input.split(" ");
            String time = parts[parts.length - 1];
            for(int i = 0; i < parts.length - 1;i++){
                stationName += parts[i];
                if(i < parts.length - 2)
                    stationName += " ";
            }
            if(!stationName.isEmpty()) {
                stations.addLast(stationName);
                hours.addLast(readTime(time));
            }
            stationName = "";
        }while(!input.isEmpty());
        try{
            network.insertSchedule(lineName,trainNumber,stations,hours);
            System.out.print(SCHEDULE_ADDED);
        }catch(LineDoesNotExistException e){
            System.out.print(LINE_DOES_NOT_EXIST);
        }catch(InvalidScheduleException e) {
            System.out.print(INVALID_SCHEDULE);
        }
    }

    /**
     * Parses a time string and returns a Time object representing the time.
     * @param time the time in string
     * @return Time object representing the string.
     */
    private static Time readTime(String time){
        String[] parts = time.split(":");
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);
        return new TimeClass(hours, minutes);
    }

    private static void removeSchedule(Scanner in, Network network){
        String lineName = in.nextLine().trim();
        String input = in.nextLine();
        String stationName = "";
        String[] parts = input.split(" ");
        String time = parts[parts.length - 1];
        for(int i = 0; i < parts.length - 1;i++){
            stationName += parts[i];
            if(i < parts.length - 2)
                stationName += " ";
        }
        try{
            network.removeSchedule(lineName, stationName, readTime(time));
            System.out.print(SCHEDULE_REMOVED);
        }catch(LineDoesNotExistException e){
            System.out.print(LINE_DOES_NOT_EXIST);
        }catch(ScheduleDoesNotExistException e){
            System.out.print(SCHEDULE_DOES_NOT_EXIST);
        }
    }
    private static void listSchedulesLine(Scanner in, Network network){
        String lineName = in.nextLine().trim();
        String stationName = in.nextLine().trim();

        try{
            Iterator<Schedule> it = network.listSchedulesLine(lineName, stationName);
            while(it.hasNext()){
                Schedule s = it.next();
                System.out.println(s.getTrainNumber());
                Iterator<StationTime> aux = s.listStationTime();
                while(aux.hasNext()){
                    StationTime st = aux.next();
                    System.out.println(st.getStationName() + " " + st.getStringTime());
                }
            }
        }catch(LineDoesNotExistException e) {
            System.out.print(LINE_DOES_NOT_EXIST);
        }catch(TerminalDoesNotExistException e){
            System.out.print(DEPARTURE_STATION_DOES_NOT_EXIST);
        }
    }

    private static void trainsStation(Scanner in, Network network){
        String stationName = in.nextLine().trim();

        try{
            Iterator<Entry<StationTime, StationTime>> it = network.listTrains(stationName);
            while(it.hasNext()){
                Entry<StationTime, StationTime> e = it.next();
                System.out.printf(LIST_TRAIN, e.getValue().getTrainNumber(), e.getValue().getStringTime());
            }
        }catch(StationDoesNotExistException e){
            System.out.print(STATION_DOES_NOT_EXIST);
        }
    }
    private static void bestTimetable(Scanner in, Network network){
        String lineName = in.nextLine().trim();
        String departure = in.nextLine().trim();
        String arrival = in.nextLine().trim();
        String time = in.nextLine();

        try{
            Iterator<Schedule> it = network.bestSchedule(lineName, departure, arrival,readTime(time));
            while(it.hasNext()){
                Schedule s = it.next();
                System.out.println(s.getTrainNumber());
                Iterator<StationTime> aux = s.listStationTime();
                while(aux.hasNext()){
                    StationTime st = aux.next();
                    System.out.println(st.getStationName() + " " + st.getStringTime());
                }
            }
        }catch(LineDoesNotExistException e){
            System.out.print(LINE_DOES_NOT_EXIST);
        }catch(DepartureStationDoesNotExistException e){
            System.out.print(DEPARTURE_STATION_DOES_NOT_EXIST);
        }catch(ImpossibleRouteException e){
            System.out.print(IMPOSSIBLE_ROUTE);
        }
    }


    /**
     * Saves the given Network object to a file using serialization.
     * @param network Network object to be saved.
     */
    private static void save(Network network){
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME));
            out.writeObject(network);
            out.flush();
            out.close();
        }catch(IOException e){}
    }

    /**
     * Loads a Network object from a file using deserialization.
     * @return the deserialized Network object, or a new empty Network if loading fails.
     */
    private static Network load(){
        try{
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME));
            Network network = (Network) in.readObject();
            in.close();
            return network;
        }catch(IOException | ClassNotFoundException e){
            return new NetworkClass();
        }
    }
}
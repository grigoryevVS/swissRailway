package ru.javaschool.clientMain;


import org.apache.log4j.Logger;
import ru.javaschool.database.criteria.Request;
import ru.javaschool.database.criteria.Response;
import ru.javaschool.database.criteria.SampleObject;
import ru.javaschool.database.criteria.ScheduleConstraints;
import ru.javaschool.database.entities.*;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class ClientSocket {
    private static final Logger logger = Logger.getLogger(ClientSocket.class);

    private static volatile ClientSocket instance;

    private ClientSocket() {
    }

    public static ClientSocket getInstance() {
        if (instance == null) {
            synchronized (ClientSocket.class) {
                if (instance == null) {
                    instance = new ClientSocket();
                }
            }
        }
        return instance;
    }

    public Response getResponse(Request request) {

        Response resultResponse = null;
        try {
            Socket clientSock = new Socket("localhost", 8080);
            ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(clientSock.getOutputStream()));
            out.writeObject(request);
            out.flush();

            ObjectInputStream in = new ObjectInputStream(clientSock.getInputStream());
            resultResponse = (Response) in.readObject();

        } catch (IOException e) {
            logger.error((e.getMessage()));
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            logger.error((e.getMessage()));
            e.printStackTrace();
        } catch (NullPointerException e) {
            logger.error(e.getMessage());
        }
        return resultResponse;
    }

    @SuppressWarnings("unchecked")
    public List<Train> getAllTrains() {   // release
        Response response = ClientSocket.getInstance().getResponse(new Request("Get all trains"));

        List<Train> resultList = null;
        if (!response.getIsProblem()) {
            resultList = (List<Train>) response.getRespBody();
        } else {
            // TODO error
        }
        return resultList;

    }

    public String createTrain(Train train) {
        Response response = ClientSocket.getInstance().getResponse(new Request("Create train", train));
        String result = "Success!";
        if (response.getIsProblem())
            result = response.getTitle();
        return result;
    }

    public String createStation(Station station) {
        Response response = ClientSocket.getInstance().getResponse(new Request("Create station", station));
        String result = "Success!";
        if (response.getIsProblem())
            result = response.getTitle();
        return result;
    }

    public String createSchedule(Schedule schedule) {
        Response response = ClientSocket.getInstance().getResponse(new Request("Create schedule", schedule));
        String result = "Success!";
        if (response.getIsProblem()) {
            result = response.getTitle();
        }
        return result;
    }

    public String createRoute(Route route) {
        Response response = ClientSocket.getInstance().getResponse(new Request("Create route", route));
        String result = "Success!";
        if (response.getIsProblem()) {
            result = response.getTitle();
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    public List<Schedule> getAllSchedule() {      // release
        Response response = ClientSocket.getInstance().getResponse(new Request("Get all schedule"));

        List<Schedule> resultList = null;
        if (!response.getIsProblem()) {
            resultList = (List<Schedule>) response.getRespBody();
        } else {
            // TODO error
        }
        return resultList;
    }

    @SuppressWarnings("unchecked")
    public List<Schedule> getRevisedSchedule(ScheduleConstraints condition) {
        Response response = ClientSocket.getInstance().getResponse(new Request("Get revised schedule", condition));

        List<Schedule> resultList = null;
        if (!response.getIsProblem()) {
            resultList = (List<Schedule>) response.getRespBody();
        } else {
            // TODO error

        }
        return resultList;
    }

    public boolean checkLoginAvailable(EmployeeData ed) {        // release

        Response response = ClientSocket.getInstance().getResponse(new Request("Check authorization", ed));
        return (Boolean) response.getRespBody();
    }

    @SuppressWarnings("unchecked")
    public List<Passenger> getRegisteredPassengers(Schedule schedule) {
        Response response = ClientSocket.getInstance().getResponse(new Request("Get registered passengers", schedule));
        List<Passenger> result = null;
        if (!response.getIsProblem()) {
            result = (List<Passenger>) response.getRespBody();
        } else {
            // TODO error
        }
        return result;
    }

    public Response buyTicket(long scheduleId, Passenger passenger) {

        return ClientSocket.getInstance().getResponse
                (new Request(("Buy ticket"), new SampleObject<Long, Passenger>(scheduleId, passenger)));
    }

    public Station getStationByName(String name) {
        Response response = ClientSocket.getInstance().getResponse(new Request("Get station by name", name));

        Station resultStation = null;
        if (!response.getIsProblem()) {
            resultStation = (Station) response.getRespBody();
        } else {
            // TODO error
        }
        return resultStation;
    }

    @SuppressWarnings("unchecked")
    public List<Station> getAllStations() {
        Response response = ClientSocket.getInstance().getResponse(new Request("Get all stations"));

        List<Station> resultList = null;
        if (!response.getIsProblem()) {
            resultList = (List<Station>) response.getRespBody();
        } else {
            // TODO error
        }
        return resultList;
    }

    @SuppressWarnings("unchecked")
    public List<Route> getAllRoutes() {
        Response response = ClientSocket.getInstance().getResponse(new Request("Get all routes"));

        List<Route> resultList = null;
        if (!response.getIsProblem()) {
            resultList = (List<Route>) response.getRespBody();
        } else {
            // TODO error
        }
        return resultList;
    }

    public Schedule getScheduleById(Long id) {
        Response response = ClientSocket.getInstance().getResponse(new Request("Get schedule by id",id));
        return (Schedule) response.getRespBody();
    }
}

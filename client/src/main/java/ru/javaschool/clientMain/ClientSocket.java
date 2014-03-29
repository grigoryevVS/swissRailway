package ru.javaschool.clientMain;


import org.apache.log4j.Logger;
import ru.javaschool.database.criteria.Request;
import ru.javaschool.database.criteria.Response;
import ru.javaschool.database.criteria.ScheduleConstraints;
import ru.javaschool.database.entities.*;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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

    private Response getResponse(Request request) {

        Response resultResponse = null;
        try {
            Socket clientSock = new Socket("localhost", 4444);
            ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(clientSock.getOutputStream()));
            out.writeObject(request);
            out.flush();
            clientSock.shutdownOutput();

            ObjectInputStream in = new ObjectInputStream(clientSock.getInputStream());
            resultResponse = (Response) in.readObject();

        } catch (IOException e) {
            logger.error("IOException" + e);
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            logger.error("ClassFound exception" + e);
            e.printStackTrace();
        } catch (NullPointerException e) {
            logger.error("Server not started" + e);
        }
        return resultResponse;
    }

    public List<Train> getAllTrains() {   // release
        Response response = new ClientSocket().getResponse(new Request("Get all trains"));

        List<Train> resultList = null;
        if (!response.getIsProblem()) {
            resultList = (List<Train>) response.getRespBody();
        } else {
            // some actions for handling problem
        }
        return resultList;

    }

    public String createTrain(Train train) {  // release
        Response response = new ClientSocket().getResponse(new Request("Create train", train));
        String result = "Success!";
        if (response.getIsProblem()) {
            result = response.getTitle();
        } else {
            // some actions for handling problem
        }
        return result;
    }

    public String createStation(Station station) {   // release

        Response response = new ClientSocket().getResponse(new Request("Create station", station));
        String result = "Success!";
        if (response.getIsProblem()) {
            result = response.getTitle();
        } else {
            // some actions for handling problem
        }
        return result;
    }

    public List<Schedule> getAllSchedule() {      // release
        Response response = new ClientSocket().getResponse(new Request("Get all schedule"));

        List<Schedule> resultList = null;
        if (!response.getIsProblem()) {
            resultList = (List<Schedule>) response.getRespBody();
        } else {
            // some actions for handling problem
        }
        return resultList;
    }

    public List<Schedule> getRevisedSchedule(ScheduleConstraints condition) {
        Response response = new ClientSocket().getResponse(new Request("Get revised schedule"));

        List<Schedule> resultList = null;
        if (!response.getIsProblem()) {
            resultList = (List<Schedule>) response.getRespBody();
        } else {
            // some actions for handling problem
        }
        return resultList;

    }

    public boolean checkLoginAvailable(EmployeeData ed) {        // release

        Response response = new ClientSocket().getResponse(new Request("Check authorization", ed));
        return (Boolean) response.getRespBody();
    }

    public List<Passenger> getRegisteredPassengers(Schedule schedule) {
        Response response = ClientSocket.getInstance().getResponse(new Request("Get registered passengers", schedule));
        List<Passenger> result = null;
        if (!response.getIsProblem()) {
            result = (List<Passenger>) response.getRespBody();
        }
        return result;
    }
}

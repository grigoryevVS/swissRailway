package ru.javaschool.services;


import org.apache.log4j.Logger;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class);

    public static void main(String[] args) {

//        logger.debug("Inside main()");
//        EmployeeService s = new EmployeeService();
//        s.createTrain();
//        List<Train> trainList = s.getTrainList();
//        for (Train item : trainList) {
//            System.out.println(item.toString());
//        }
        //s.createStation();

        /*
        package ru.javaschool.clientMain;


import org.apache.log4j.Logger;
import ru.javaschool.database.criteria.Request;
import ru.javaschool.database.criteria.Response;
import ru.javaschool.database.criteria.ScheduleConstraints;
import ru.javaschool.database.entities.EmployeeData;
import ru.javaschool.database.entities.Schedule;
import ru.javaschool.database.entities.Station;
import ru.javaschool.database.entities.Train;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class ClientSocket {
    private static final Logger logger = Logger.getLogger(ClientSocket.class);

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

    public static List<Train> getAllTrains(){   // release
        Response response = new ClientSocket().getResponse(new Request("Get all trains"));

        List<Train> resultList = null;
        if(!response.getIsProblem()){
            resultList = (List<Train>) response.getRespBody();
        } else{
            // some actions for handling problem
        }
        return resultList;

    }

    public static String createTrain(Train train){  // release
        Response response = new ClientSocket().getResponse(new Request("Create train", train));
        String result = "Success!";
        if(response.getIsProblem()){
            result = response.getTitle();
        } else{
            // some actions for handling problem
        }
        return result;
    }

    public static String createStation(Station station) {   // release

        Response response = new ClientSocket().getResponse(new Request("Create station", station));
        String result = "Success!";
        if(response.getIsProblem()){
            result = response.getTitle();
        } else{
            // some actions for handling problem
        }
        return result;
    }

    public static List<Schedule> getAllSchedule(){      // release
        Response response = new ClientSocket().getResponse(new Request("Get all schedule"));

        List<Schedule> resultList = null;
        if(!response.getIsProblem()){
            resultList = (List<Schedule>) response.getRespBody();
        } else{
            // some actions for handling problem
        }
        return resultList;
    }

    public static List<Schedule> getRevisedSchedule(ScheduleConstraints condition){
        Response response = new ClientSocket().getResponse(new Request("Get revised schedule"));

        List<Schedule> resultList = null;
        if(!response.getIsProblem()){
            resultList = (List<Schedule>) response.getRespBody();
        } else{
            // some actions for handling problem
        }
        return resultList;

    }

    public static boolean checkLoginAvailable(EmployeeData ed) {        // release

       Response response = new ClientSocket().getResponse(new Request("Check authorization", ed));

        return (Boolean) response.getRespBody();
    }
}

         */

    }
}
